package org.mj.blackjack.game;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.moves.BJMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.payout.BJPayout;
import org.mj.blackjack.player.BJPlayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 15-03-05.
 *
 * Main class for playing a blackjack game
 */
public class BJGame
{
    private final boolean DEBUG = false;

    /**
     * Private fields
     */
    private final BJFactory factory;
    private final BJSettings settings;

    /**
     * Constructor: takes the factory object
     *
     * @param factory: factory object
     */
    public BJGame(BJFactory factory, BJSettings settings)
    {
        this.factory = factory;
        this.settings = settings;
    }

    /**
     * Inner class for an hand with a bet
     */
    static class HandWithBet
    {
        final BJHand hand;
        int bet;

        HandWithBet(BJHand hand, int bet)
        {
            this.hand = hand;
            this.bet = bet;
        }
    }

    /**
     * Inner class for having the players with their hands
     */
    static class PlayerHands
    {
        final BJPlayer player;
        final List<HandWithBet> handsWithBets;

        PlayerHands(BJPlayer player)
        {
            this.player = player;
            this.handsWithBets = new LinkedList<HandWithBet>();
        }

        void addHand(BJHand hand)
        {
            handsWithBets.add(new HandWithBet(hand, player.getInitialBet()));
        }

        BJPlayer getPlayer()
        {
            return player;
        }

        List<? extends HandWithBet> getHandsWithBets()
        {
            return handsWithBets;
        }
    }

    /**
     * Entry point for the game
     *
     * @param players: list of players
     * @param cardDeck: the card decks
     */
    public void playGame(List<BJPlayer> players, BJCardDeck cardDeck, BJNextMove nextMove)
    {
        // Deal cards
        //
        List<PlayerHands> playerHands = new LinkedList<PlayerHands>();
        BJHand dealerHand = dealCards(players, cardDeck, playerHands);

        if( DEBUG )
        {
            System.out.println("\nSTART GAME");
            System.out.println("Dealer card: " + dealerHand.getCard(0).getValue());
            System.out.println("Player: " + playerHands.get(0).getHandsWithBets().get(0).hand.debugString());
        }

        // Check blackjack for dealer and players
        //
        checkBlackjacks(dealerHand, playerHands);

        // TODO: insurance...

        // If dealer does not have blackjack, play the hands
        //
        boolean shouldDealerPlay = false;
        if( dealerHand.getTotalValue() != 21 )
        {
            shouldDealerPlay = completePlayerHands(
                    playerHands,
                    dealerHand.getCard(0),
                    nextMove,
                    cardDeck
            );
        }
        else
        {
            if( DEBUG )
            {
                System.out.println("\nDEALER HAS BLACKJACK");
            }
        }

        // Dealer plays hand
        //
        if( shouldDealerPlay )
        {
            playDealerHand(dealerHand, cardDeck);
        }

        // Payout
        //
        doPayouts(dealerHand, playerHands);
    }


    /**
     * Do the initial dealing of the hands.  This will return the hand for the dealer
     *
     * @param players: the list of players
     * @param cardDeck: the card deck to use
     * @param playerHands: (output) the player hands
     * @return the dealer hand
     */
    BJHand dealCards(List<? extends BJPlayer> players,
                     BJCardDeck cardDeck,
                     List<PlayerHands> playerHands)
    {
        // Create the dealer hand
        //
        BJHand dealerHand = factory.createHand();


        // Create the players hands
        //
        for( BJPlayer player : players )
        {
            PlayerHands aPlayerHands = new PlayerHands(player);
            aPlayerHands.addHand(factory.createHand());
            player.removeMoney(player.getInitialBet());
            playerHands.add(aPlayerHands);
        }

        // Deal 2 cards to each player + the dealer
        //
        for( int i = 0; i < 2; ++i )
        {
            for( PlayerHands aPlayerHands : playerHands )
            {
                aPlayerHands.getHandsWithBets().get(0).hand.addCard(cardDeck.nextCard());
            }

            dealerHand.addCard(cardDeck.nextCard());
        }

        // Return the dealer hand...  the players hands are in the "playerHands" list
        //
        return dealerHand;
    }


    /**
     * Check if blackjacks on dealer and player hands
     *
     * @param dealerHand: the dealer hand
     * @param playerHands: the player hands
     */
    void checkBlackjacks(
            BJHand dealerHand,
            List<PlayerHands> playerHands
    )
    {
        // Check dealer hand first
        //
        boolean dealerHasBJ = dealerHand.getTotalValue() == 21;
        if( dealerHasBJ )
        {
            dealerHand.setState(BJHand.State.BLACKJACK);
        }

        for( PlayerHands ph : playerHands )
        {
            BJHand hand = ph.getHandsWithBets().get(0).hand;

            if( hand.getTotalValue() == 21 )
            {
                hand.setState(BJHand.State.BLACKJACK);
            }
            else if( dealerHasBJ )
            {
                hand.setState(BJHand.State.STAY);
            }
        }
    }

    /**
     * Complete the player hands
     *
     * @param playerHands: list of players with their hands
     * @param dealerFaceCard: the dealer face card
     * @param nextMove: the next move getter
     * @param cardDeck: the card deck
     * @return true if there is any hands that stayed (requires the dealer to deal)
     */
    boolean completePlayerHands(List<PlayerHands> playerHands,
                                BJCard dealerFaceCard,
                                BJNextMove nextMove,
                                BJCardDeck cardDeck)
    {
        boolean anyHandStay = false;

        for( PlayerHands ph : playerHands )
        {
            int numberSplitsDone = 0;
            List<HandWithBet> allHandsWithBets = new LinkedList<HandWithBet>();
            HandWithBet handWithBet = ph.getHandsWithBets().get(0);
            allHandsWithBets.add(handWithBet);

            while( !allHandsWithBets.isEmpty() )
            {
                HandWithBet currentHandWithBet = allHandsWithBets.remove(0);
                BJHand currentHand = currentHandWithBet.hand;
                while( currentHand.getState() == BJHand.State.MAY_HIT )
                {
                    // Add a card right away for after a split
                    //
                    if( currentHand.getNbCards() == 1 )
                    {
                        currentHand.addCard(cardDeck.nextCard());

                        // Stop if we cannot re-split aces
                        //
                        if( currentHand.getCard(0).getValue() == 11
                                && !settings.getRules().mayReSplitAces() )
                        {
                            currentHand.setState(BJHand.State.STAY);
                            break;
                        }
                    }

                    BJMove moveToMake = nextMove.getNextMove(
                            currentHandWithBet.hand,
                            dealerFaceCard,
                            settings.getPossibleMovesComputer().getPossibleMoves(
                                    currentHandWithBet.hand, numberSplitsDone,
                                    settings.getRules()
                            )
                    );

                    if( DEBUG )
                    {
                        System.out.println("Next move: " + moveToMake.toString());
                    }

                    switch( moveToMake )
                    {
                        case HIT:
                            currentHand.addCard(cardDeck.nextCard());
                            if( currentHand.getTotalValue() == 21 )
                            {
                                currentHand.setState(BJHand.State.STAY);
                            }
                            break;

                        case STAY:
                            currentHand.setState(BJHand.State.STAY);
                            break;

                        case DOUBLE:
                        {
                            int initialBet = ph.getPlayer().getInitialBet();
                            currentHand.addCard(cardDeck.nextCard());
                            ph.getPlayer().removeMoney(initialBet);
                            currentHandWithBet.bet += initialBet;

                            if (currentHand.getState() == BJHand.State.MAY_HIT)
                            {
                                currentHand.setState(BJHand.State.STAY);
                            }
                            break;
                        }

                        case SURRENDER:
                            currentHand.setState(BJHand.State.SURRENDER);
                            break;

                        case SPLIT:
                        {
                            int initialBet = ph.getPlayer().getInitialBet();
                            BJHand newHand = currentHand.splitHand();
                            ph.getPlayer().removeMoney(initialBet);
                            ph.addHand(newHand);
                            allHandsWithBets.add(ph.getHandsWithBets().get(ph.getHandsWithBets().size()-1));
                            ++numberSplitsDone;
                            break;
                        }

                        default:
                            throw new IllegalStateException("NOT IMPLEMENTED");
                    }

                    if( DEBUG )
                    {
                        System.out.println("Hand: " + currentHand.debugString());
                    }
                }

                // If at least one hand has stay, we need the dealer to deal
                //
                if( currentHand.getState() == BJHand.State.STAY )
                {
                    anyHandStay = true;
                }
            }
        }

        return anyHandStay;
    }


    /**
     * Play the dealer hand
     *
     * @param dealerHand: the dealer hand
     * @param cardDeck: the card deck
     */
    void playDealerHand(BJHand dealerHand, BJCardDeck cardDeck)
    {
        boolean mustHitSoft17 = settings.getRules().doesDealerHitOnSoft17();

        for(;;)
        {
            int total = dealerHand.getTotalValue();

            if( total > 17 ) break;
            if( total == 17 && (!mustHitSoft17 || !dealerHand.isSoftHand())) break;

            dealerHand.addCard(cardDeck.nextCard());
        }

        if( DEBUG )
        {
            System.out.println("DEALER: " + dealerHand.debugString());
        }
    }

    /**
     * Do the payouts
     *
     * @param dealerHand: the dealer hand
     * @param playerHands: the player hands
     */
    void doPayouts(BJHand dealerHand, List<PlayerHands> playerHands)
    {
        int dealerTotal = dealerHand.getTotalValue();
        boolean dealerHasBlackjack = (dealerHand.getState() == BJHand.State.BLACKJACK);
        BJPayout payout = settings.getPayout();

        for( PlayerHands ph : playerHands )
        {
            BJPlayer player = ph.getPlayer();

            for( HandWithBet handWithBet : ph.getHandsWithBets() )
            {
                int bet = handWithBet.bet;
                BJHand hand = handWithBet.hand;

                switch( hand.getState() )
                {
                    case BLACKJACK:
                    {
                        if( !dealerHasBlackjack )
                        {
                            // Pays blackjack
                            //
                            player.addMoney(payout.payoutBlackjack(bet));
                        }
                        else
                        {
                            // push
                            //
                            player.addMoney(payout.payoutPush(bet));
                        }

                        break;
                    }

                    case BUSTED:  // nothing to do
                        player.addMoney(payout.payoutLost(bet));
                        break;

                    case STAY:
                    {
                        int playerTotal = hand.getTotalValue();
                        if( !dealerHasBlackjack )
                        {
                            if( dealerTotal > 21 )
                            {
                                player.addMoney(payout.payoutWin(bet));
                            }
                            else if( playerTotal > dealerTotal )
                            {
                                player.addMoney(payout.payoutWin(bet));
                            }
                            else if ( playerTotal == dealerTotal )
                            {
                                player.addMoney(payout.payoutPush(bet));
                            }
                            else
                            {
                                player.addMoney(payout.payoutLost(bet));
                            }
                        }

                        break;
                    }

                    case SURRENDER:
                    {
                        assert !dealerHasBlackjack;
                        player.addMoney(payout.payoutSurrender(bet));
                        break;
                    }

                    default:
                        throw new IllegalStateException("SHOULD NOT BE HERE FOR PAYOUT");
                }
            }
        }
    }
}
