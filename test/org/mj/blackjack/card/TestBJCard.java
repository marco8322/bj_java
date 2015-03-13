package org.mj.blackjack.card;

import junit.framework.TestCase;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Unit tests for card implementations
 */
public class TestBJCard
    extends TestCase
{
    public void testBJCardImpl()
    {
        BJCard card1 = new BJCardImpl(2);
        assertEquals(2, card1.getValue());

        BJCard card2 = new BJCardImpl(11);
        assertEquals(11, card2.getValue());
    }
}
