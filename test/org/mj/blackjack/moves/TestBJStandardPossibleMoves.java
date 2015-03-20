package org.mj.blackjack.moves;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.rules.BJRules;
import org.mj.blackjack.rules.BJStandardRules;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by marcojacques on 2015-03-19.
 *
 * Test the standard class for computing the possible moves
 */
public class TestBJStandardPossibleMoves
    extends TestCase
{
    public void testWithDoubleNoSpiltNoSurrender()
    {
        BJRules rules = new BJStandardRules(false, 3, false);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(10));
        hand.addCard(new BJCardImpl(5));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                 hand, 0, rules
                        )
                );

        assertEquals(3, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
    }

    public void testWithDoubleWithSpiltNoSurrender()
    {
        BJRules rules = new BJStandardRules(false, 3, false);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(5));
        hand.addCard(new BJCardImpl(5));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 0, rules
                        )
                );

        assertEquals(4, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
        assertTrue(possibleMoves.contains(BJMove.SPLIT));
    }

    public void testWithDoubleNoSpiltWithSurrender()
    {
        BJRules rules = new BJStandardRules(false, 3, true);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(10));
        hand.addCard(new BJCardImpl(5));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 0, rules
                        )
                );

        assertEquals(4, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
        assertTrue(possibleMoves.contains(BJMove.SURRENDER));
    }


    public void testWithDoubleWithSpiltWithSurrender()
    {
        BJRules rules = new BJStandardRules(false, 3, true);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(5));
        hand.addCard(new BJCardImpl(5));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 0, rules
                        )
                );

        assertEquals(5, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
        assertTrue(possibleMoves.contains(BJMove.SURRENDER));
        assertTrue(possibleMoves.contains((BJMove.SPLIT)));
    }


    public void testWith3Cards()
    {
        BJRules rules = new BJStandardRules(false, 3, true);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(6));
        hand.addCard(new BJCardImpl(5));
        hand.addCard(new BJCardImpl(3));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 0, rules
                        )
                );

        assertEquals(2, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
    }


    public void testAfterSplitNoReSplitPossible()
    {
        BJRules rules = new BJStandardRules(false, 3, true);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(6));
        hand.addCard(new BJCardImpl(5));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 1, rules
                        )
                );

        assertEquals(3, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
    }


    public void testAfterSplitReSplitPossible()
    {
        BJRules rules = new BJStandardRules(false, 3, true);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(6));
        hand.addCard(new BJCardImpl(6));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 1, rules
                        )
                );

        assertEquals(4, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
        assertTrue(possibleMoves.contains(BJMove.SPLIT));
    }

    public void testAfterSplitMaxSplitsDone()
    {
        BJRules rules = new BJStandardRules(false, 1, true);
        BJPossibleMoves possibleMovesComputer = new BJStandardPossibleMoves();
        BJHand hand = new BJHandImpl();

        hand.addCard(new BJCardImpl(6));
        hand.addCard(new BJCardImpl(6));

        Set<BJMove> possibleMoves =
                new TreeSet<BJMove>(
                        possibleMovesComputer.getPossibleMoves(
                                hand, 1, rules
                        )
                );

        assertEquals(3, possibleMoves.size());
        assertTrue(possibleMoves.contains(BJMove.HIT));
        assertTrue(possibleMoves.contains(BJMove.STAY));
        assertTrue(possibleMoves.contains(BJMove.DOUBLE));
    }
}
