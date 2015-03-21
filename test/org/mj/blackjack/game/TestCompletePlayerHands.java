package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.moves.BJMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.moves.BJStandardPossibleMoves;
import org.mj.blackjack.player.BJPlayerImpl;
import org.mj.blackjack.rules.BJStandardRules;

import java.util.Collection;
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
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{8,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(new BJMove[]{BJMove.STAY});

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(17, handToCheck.getTotalValue());
        assertEquals(2, handToCheck.getNbCards());
    }

    /**
     * Test HIT and STAY
     */
    public void testHitAndStay()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(new BJMove[]{BJMove.HIT, BJMove.STAY});

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(6)})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(19, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(6, handToCheck.getCard(2).getValue());
    }

    /**
     * Test HIT which busts
     */
    public void testHitAndBust()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(new BJMove[]{BJMove.HIT});

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(9)})
        );

        assertFalse(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck.getState());
        assertEquals(22, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(9, handToCheck.getCard(2).getValue());
    }

    /**
     * Test HIT which gets 21
     */
    public void testHitAnd21()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(new BJMove[]{BJMove.HIT});

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(8)})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(21, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(8, handToCheck.getCard(2).getValue());
    }

    private List<BJGame.PlayerHands> createHands(int[][] cards)
    {
        List<BJGame.PlayerHands> playerHands = new LinkedList<BJGame.PlayerHands>();

        for( int[] handValues : cards )
        {
            BJGame.PlayerHands ph = new BJGame.PlayerHands(new BJPlayerImpl(100));
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

    private BJNextMove createBJNextMove(final BJMove[] nextMoves)
    {
        return new BJNextMove()
        {
            private int nbCalls = 0;

            @Override
            public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
            {
                assertTrue(nbCalls < nextMoves.length);
                assertTrue(possibleMoves.contains(nextMoves[nbCalls]));

                return nextMoves[nbCalls++];
            }
        };
    }
}
