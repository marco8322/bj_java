package com.mj.blackjack.game;

import com.mj.blackjack.card.BJCard;
import com.mj.blackjack.card.BJCardDeck;
import com.mj.blackjack.factory.BJFactory;
import com.mj.blackjack.hand.BJHand;
import com.mj.blackjack.moves.BJMove;
import com.mj.blackjack.moves.BJNextMove;
import com.mj.blackjack.player.BJPlayer;
import com.mj.blackjack.rules.BJRules;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 15-03-05.
 *
 * Main class for playing a blackjack game
 */
public class BJGame
{
    /**
     * Private fields
     */
    private final BJFactory factory;

    /**
     * Constructor: takes the factory object
     *
     * @param factory: factory object
     */
    public BJGame(BJFactory factory)
    {
        this.factory = factory;
    }

    /**
     * Inner class for having the players with their hands
     */
    class PlayerHands
    {
        final BJPlayer player;
        final List<BJHand> hands;

        PlayerHands(BJPlayer player)
        {
            this.player = player;
            this.hands = new LinkedList<BJHand>();
        }

        void addHand(BJHand hand)
        {
            hands.add(hand);
        }

        BJPlayer getPlayer()
        {
            return player;
        }

        List<? extends BJHand> getHands()
        {
            return hands;
        }
    }

    /**
     * Entry point for the game
     *
     * @param players: list of players
     * @param cardDeck: the card decks
     */
    public void playGame(List<BJPlayer> players, BJCardDeck cardDeck, BJRules rules, BJNextMove nextMove)
    {
        // Deal cards
        //
        List<PlayerHands> playerHands = new LinkedList<PlayerHands>();
        BJHand dealerHand = dealCards(players, cardDeck, playerHands);

        // Check blackjack for dealer and players
        //
        checkBlackjacks(dealerHand, playerHands);

        // TODO: insurance...

        // If dealer does not have blackjack, play the hands
        //
        if( dealerHand.getTotalValue() != 21 )
        {
            completePlayerHands(
                    playerHands,
                    dealerHand.getCard(0),
                    rules,
                    nextMove,
                    cardDeck
            );
        }

        // Dealer plays hand
        //
        playDealerHand(dealerHand, rules, cardDeck);

        // Payout
        //

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
        BJHand dealerHand = factory.createHand(factory.createDealer());


        // Create the players hands
        //
        for( BJPlayer player : players )
        {
            PlayerHands aPlayerHands = new PlayerHands(player);
            aPlayerHands.addHand(factory.createHand(player));

            playerHands.add(aPlayerHands);
        }

        // Deal 2 cards to each player + the dealer
        //
        for( int i = 0; i < 2; ++i )
        {
            for( PlayerHands aPlayerHands : playerHands )
            {
                aPlayerHands.getHands().get(0).addCard(cardDeck.nextCard());
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
        if( dealerHand.getTotalValue() == 21 )
        {
            dealerHand.setState(BJHand.State.BLACKJACK);
        }

        for( PlayerHands ph : playerHands )
        {
            if( ph.getHands().get(0).getTotalValue() == 21 )
            {
                ph.getHands().get(0).setState(BJHand.State.BLACKJACK);
            }
        }
    }

    void completePlayerHands(List<PlayerHands> playerHands,
                             BJCard dealerFaceCard,
                             BJRules rules,
                             BJNextMove nextMove,
                             BJCardDeck cardDeck)
    {

        for( PlayerHands ph : playerHands )
        {
            int numberSplitsDone = 0;
            List<BJHand> allHands = new LinkedList<BJHand>();
            BJHand hand = ph.getHands().get(0);
            if (hand.getState() == BJHand.State.MAY_HIT)
            {
                allHands.add(hand);
            }

            while( !allHands.isEmpty() )
            {
                BJHand currentHand = allHands.remove(0);
                while( currentHand.getState() == BJHand.State.MAY_HIT )
                {
                    // Add a card right away for after a split
                    //
                    if( currentHand.getNbCards() == 1 )
                    {
                        currentHand.addCard(cardDeck.nextCard());
                    }

                    BJMove moveToMake = nextMove.getNextMove(
                            currentHand,
                            dealerFaceCard,
                            rules.getPossibleMoves(currentHand, numberSplitsDone)
                    );

                    // TODO: add bets
                    //
                    switch( moveToMake )
                    {
                        case HIT:
                            currentHand.addCard(cardDeck.nextCard());
                            break;

                        case STAY:
                            currentHand.setState(BJHand.State.STAY);
                            break;

                        case DOUBLE:
                            currentHand.addCard(cardDeck.nextCard());
                            if( currentHand.getState() == BJHand.State.MAY_HIT )
                            {
                                currentHand.setState(BJHand.State.STAY);
                            }
                            break;

                        case SURRENDER:
                            currentHand.setState(BJHand.State.SURRENDER);
                            break;

                        case SPLIT:
                        {
                            BJHand newHand = hand.splitHand();
                            allHands.add(newHand);
                            break;
                        }

                        default:
                            throw new IllegalStateException("NOT IMPLEMENTED");
                    }
                }
            }
        }
    }

    void playDealerHand(BJHand dealerHand, BJRules rules, BJCardDeck cardDeck)
    {
        boolean mustHitSoft17 = rules.doesDealerHitOnSoft17();

        for(;;)
        {
            int total = dealerHand.getTotalValue();

            if( total > 17 ) break;
            if( total == 17 && (!mustHitSoft17 || !dealerHand.isSoftHand())) break;

            dealerHand.addCard(cardDeck.nextCard());
        }
    }
}
