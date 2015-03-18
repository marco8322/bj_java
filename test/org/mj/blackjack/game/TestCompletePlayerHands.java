package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.player.BJPlayerImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 2015-03-17.
 *
 * Unit test for completing player hands
 */
public class TestCompletePlayerHands
    extends TestCase
{
    /**
     * Simple STAY action
     */
    public void testSimpleStay()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory);

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{8,9}
                }
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                null /* rules */,
                null /* next move */,
                null /* Card deck */
        );
    }

    private List<BJGame.PlayerHands> createHands(int[][] cards)
    {
        List<BJGame.PlayerHands> playerHands = new LinkedList<BJGame.PlayerHands>();

        for( int[] handValues : cards )
        {
            BJGame.PlayerHands ph = new BJGame.PlayerHands(new BJPlayerImpl(100));
            playerHands.add(ph);

            BJHand hand = new BJHandImpl();
            for ( int value : handValues )
            {
                hand.addCard(new BJCardImpl(value));
            }
        }

        return playerHands;
    }
}
