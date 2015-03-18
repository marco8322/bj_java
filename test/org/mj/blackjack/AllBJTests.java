package org.mj.blackjack;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.mj.blackjack.card.TestBJCard;
import org.mj.blackjack.card.TestBJCardDeck;
import org.mj.blackjack.game.TestDealCards;
import org.mj.blackjack.hand.TestBJHand;

/**
 * Created by marcojacques on 15-03-12.
 *
 * All blackjack unit tests
 */
public class AllBJTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(TestBJHand.class);
        suite.addTestSuite(TestBJCard.class);
        suite.addTestSuite(TestBJCardDeck.class);
        suite.addTestSuite(TestDealCards.class);

        return suite;
    }

}