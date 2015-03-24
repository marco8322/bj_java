package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.moves.BJMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.player.BJPlayerImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 2015-03-23.
 *
 * Util functions for testing BJ game
 */
public class TestGameUtil
{
    /**
     * Create the hands
     *
     * @param cards: the cards to use
     * @return the player hands structure
     */
    static List<BJGame.PlayerHands> createHands(int[][] cards)
    {
        List<BJGame.PlayerHands> playerHands = new LinkedList<BJGame.PlayerHands>();

        for( int[] handValues : cards )
        {
            BJGame.PlayerHands ph = new BJGame.PlayerHands(new BJPlayerImpl(0));
            playerHands.add(ph);

            ph.getPlayer().setInitialBet(100);

            BJHand hand = new BJHandImpl();
            for ( int value : handValues )
            {
                hand.addCard(new BJCardImpl(value));
            }

            ph.addHand(hand);
        }

        return playerHands;
    }

    /**
     * Create a next move object for testing purpose
     *
     * @param nextMoves_: list of next moves
     * @return list of moves
     */
    static BJNextMove createBJNextMove(final List<BJMove> nextMoves_)
    {
        return new BJNextMove()
        {
            private List<BJMove> nextMoves = new LinkedList<BJMove>(nextMoves_);

            @Override
            public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
            {
                TestCase.assertFalse(nextMoves.isEmpty());

                BJMove nextMove = nextMoves.remove(0);
                TestCase.assertTrue(possibleMoves.contains(nextMove));

                return nextMove;
            }
        };
    }
}
