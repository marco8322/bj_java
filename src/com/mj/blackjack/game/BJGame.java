package com.mj.blackjack.game;

import com.mj.blackjack.card.BJCardDeck;
import com.mj.blackjack.hand.BJHand;
import com.mj.blackjack.player.BJPlayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 15-03-05.
 */
public class BJGame
{
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


    BJHand dealCards(List<? extends BJPlayer> players,
                     List<PlayerHands> playerHands)
    {
        return null;
    }
}
