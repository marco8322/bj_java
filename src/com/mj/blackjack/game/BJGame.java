package com.mj.blackjack.game;

import com.mj.blackjack.card.BJCardDeck;
import com.mj.blackjack.factory.BJFactory;
import com.mj.blackjack.hand.BJHand;
import com.mj.blackjack.player.BJPlayer;

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
    public void playGame(List<BJPlayer> players, BJCardDeck cardDeck)
    {
        // Deal cards
        //

        // Check blackjack for dealer
        //

        // If not blackjack, play the hands
        //

        // Dealer plays hand
        //

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
}
