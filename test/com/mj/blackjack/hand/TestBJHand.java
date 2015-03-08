package com.mj.blackjack.hand;

import com.mj.blackjack.card.BJCard;
import com.mj.blackjack.player.BJPlayer;
import junit.framework.TestCase;

/**
 * Created by marcojacques on 15-03-03.
 *
 * Unit test for blackjack hands.
 */
public class TestBJHand
    extends TestCase
{
    private BJPlayer getPlayer()
    {
        return new BJPlayer()
        {
        };
    }

    private BJCard getCard(final int value)
    {
        return new BJCard()
        {
            @Override
            public int getValue()
            {
                return value;
            }
        };
    }

    public void testSimple()
    {
        BJPlayer aPlayer = getPlayer();
        BJHand aHand = new BJHandImpl(aPlayer);

        // Test init
        //
        assertSame(aPlayer, aHand.getPlayer());
        assertEquals(0, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        // Add one card
        //
        aHand.addCard(getCard(4));
        assertEquals(4, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        // Add another card
        //
        aHand.addCard(getCard(5));
        assertEquals(9, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        // Add another card
        //
        aHand.addCard(getCard(10));
        assertEquals(19, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
    }

    public void testSoftHand1()
    {
        BJHand aHand = new BJHandImpl(getPlayer());

        aHand.addCard(getCard(11));
        assertEquals(11, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(6));
        assertEquals(17, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(5));
        assertEquals(12, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(2));
        assertEquals(14, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(11));
        assertEquals(15, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
    }

    public void testSoftHand2()
    {
        BJHand aHand = new BJHandImpl(getPlayer());

        aHand.addCard(getCard(7));
        assertEquals(7, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(11));
        assertEquals(18, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(3));
        assertEquals(21, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());

        aHand.addCard(getCard(11));
        assertEquals(12, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
    }

    public void testSplit1()
    {
        BJHand aHand = new BJHandImpl(getPlayer());

        aHand.addCard(getCard(4));
        aHand.addCard(getCard(4));

        assertEquals(8, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertTrue(aHand.mayBeSplit());

        BJHand aHand2 = aHand.splitHand();

        assertSame(aHand.getPlayer(), aHand2.getPlayer());
        assertEquals(4, aHand.getTotalValue());
        assertEquals(4, aHand2.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand2.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertFalse(aHand.mayBeSplit());
    }

    public void testSplit2()
    {
        BJHand aHand = new BJHandImpl(getPlayer());

        aHand.addCard(getCard(11));
        aHand.addCard(getCard(11));

        assertEquals(12, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertTrue(aHand.mayBeSplit());

        BJHand aHand2 = aHand.splitHand();

        assertSame(aHand.getPlayer(), aHand2.getPlayer());
        assertEquals(11, aHand.getTotalValue());
        assertEquals(11, aHand2.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertTrue(aHand2.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertFalse(aHand.mayBeSplit());
    }
}
