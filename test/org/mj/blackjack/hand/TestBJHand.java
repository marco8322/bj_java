package org.mj.blackjack.hand;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;

/**
 * Created by marcojacques on 15-03-03.
 *
 * Unit test for blackjack hands.
 */
public class TestBJHand
    extends TestCase
{
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
        BJHand aHand = new BJHandImpl();

        // Test init
        //
        assertEquals(0, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        // Add one card
        //
        aHand.addCard(getCard(4));
        assertEquals(4, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        // Add another card
        //
        aHand.addCard(getCard(5));
        assertEquals(9, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        // Add another card
        //
        aHand.addCard(getCard(10));
        assertEquals(19, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());
    }

    public void testSoftHand1()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(11));
        assertEquals(11, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(6));
        assertEquals(17, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(5));
        assertEquals(12, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(2));
        assertEquals(14, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(11));
        assertEquals(15, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());
    }

    public void testSoftHand2()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(7));
        assertEquals(7, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(11));
        assertEquals(18, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(3));
        assertEquals(21, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(11));
        assertEquals(12, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());
    }

    public void testSplit1()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(4));
        aHand.addCard(getCard(4));

        assertEquals(8, aHand.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertTrue(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        BJHand aHand2 = aHand.splitHand();

        assertEquals(4, aHand.getTotalValue());
        assertEquals(4, aHand2.getTotalValue());
        assertFalse(aHand.isSoftHand());
        assertFalse(aHand2.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());
        assertEquals(BJHand.State.MAY_HIT, aHand2.getState());
    }

    public void testSplit2()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(11));
        aHand.addCard(getCard(11));

        assertEquals(12, aHand.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertTrue(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        BJHand aHand2 = aHand.splitHand();

        assertEquals(11, aHand.getTotalValue());
        assertEquals(11, aHand2.getTotalValue());
        assertTrue(aHand.isSoftHand());
        assertTrue(aHand2.isSoftHand());
        assertFalse(aHand.mayBeSplit());
        assertFalse(aHand.mayBeSplit());
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());
        assertEquals(BJHand.State.MAY_HIT, aHand2.getState());
    }

    public void testBust()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(10));
        aHand.addCard(getCard(8));
        assertEquals(BJHand.State.MAY_HIT, aHand.getState());

        aHand.addCard(getCard(8));
        assertEquals(BJHand.State.BUSTED, aHand.getState());

        try
        {
            aHand.addCard(getCard(9));
            fail();
        }
        catch( IllegalStateException e )
        {
            // OK...
        }
    }

    public void testBlackjack()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(10));
        aHand.addCard(getCard(11));

        aHand.setState(BJHand.State.BLACKJACK);
        assertEquals(BJHand.State.BLACKJACK, aHand.getState());

        try
        {
            aHand.addCard(getCard(9));
            fail();
        }
        catch( IllegalStateException e )
        {
            // OK...
        }
    }

    public void testSurrender()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(10));
        aHand.addCard(getCard(6));

        aHand.setState(BJHand.State.SURRENDER);
        assertEquals(BJHand.State.SURRENDER, aHand.getState());

        try
        {
            aHand.addCard(getCard(9));
            fail();
        }
        catch( IllegalStateException e )
        {
            // OK...
        }
    }

    public void testStayNonSplit()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(10));
        aHand.addCard(getCard(7));

        aHand.setState(BJHand.State.STAY);
        assertEquals(BJHand.State.STAY, aHand.getState());

        try
        {
            aHand.addCard(getCard(9));
            fail();
        }
        catch( IllegalStateException e )
        {
            // OK...
        }
    }

    public void testStaySplit()
    {
        BJHand aHand = new BJHandImpl();

        aHand.addCard(getCard(7));
        aHand.addCard(getCard(7));
        assertTrue(aHand.mayBeSplit());

        aHand.setState(BJHand.State.STAY);
        assertEquals(BJHand.State.STAY, aHand.getState());

        assertFalse(aHand.mayBeSplit());

        try
        {
            aHand.splitHand();
            fail();
        }
        catch( IllegalStateException e )
        {
            // OK...
        }
    }
}
