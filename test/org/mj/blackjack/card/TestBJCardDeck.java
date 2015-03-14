package org.mj.blackjack.card;

import junit.framework.TestCase;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Unit test for card decks
 */
public class TestBJCardDeck
    extends TestCase
{
    public void testBJCardDeckImpl()
    {
        BJCardDeck cardDeck1 = new BJCardDeckImpl(
                new BJCard[]{new BJCardImpl(2), new BJCardImpl(3), new BJCardImpl(4)}
        );

        assertEquals(2, cardDeck1.nextCard().getValue());
        assertEquals(3, cardDeck1.nextCard().getValue());
        assertEquals(4, cardDeck1.nextCard().getValue());
    }
}
