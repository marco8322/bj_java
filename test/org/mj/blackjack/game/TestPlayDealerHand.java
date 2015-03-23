package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.rules.BJStandardRules;

/**
 * Created by marcojacques on 2015-03-23.
 *
 * Unit test for playing dealer hand
 */
public class TestPlayDealerHand
    extends TestCase
{
    /**
     * Go until 18
     */
    public void test18()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        null,
                        new BJStandardRules(false, 3, false)
                )
        );

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(4));
        dealerHand.addCard(new BJCardImpl(6));

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{new BJCardImpl(8)}
        );

        game.playDealerHand(dealerHand, cardDeck);
        assertEquals(18, dealerHand.getTotalValue());
        assertEquals(3, dealerHand.getNbCards());
        assertEquals(8, dealerHand.getCard(2).getValue());
    }

    /**
     * Dealer has soft 18 already, no cards to deal
     */
    public void testSoft18NoDeal()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        null,
                        new BJStandardRules(false, 3, false)
                )
        );

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(11));
        dealerHand.addCard(new BJCardImpl(7));

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{}
        );

        game.playDealerHand(dealerHand, cardDeck);
        assertEquals(18, dealerHand.getTotalValue());
        assertEquals(2, dealerHand.getNbCards());
    }

    /**
     * Dealer has soft 17, cannot deal by the rules
     */
    public void testSoft17NoDeal()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        null,
                        new BJStandardRules(false, 3, false)
                )
        );

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(11));
        dealerHand.addCard(new BJCardImpl(6));

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{}
        );

        game.playDealerHand(dealerHand, cardDeck);
        assertEquals(17, dealerHand.getTotalValue());
        assertEquals(2, dealerHand.getNbCards());
    }

    /**
     * Dealer has soft 17, should deal by the rules
     */
    public void testSoft17ShouldDeal()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        null,
                        new BJStandardRules(true, 3, false)
                )
        );

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(11));
        dealerHand.addCard(new BJCardImpl(6));

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{new BJCardImpl(10)}
        );

        game.playDealerHand(dealerHand, cardDeck);
        assertEquals(17, dealerHand.getTotalValue());
        assertEquals(3, dealerHand.getNbCards());
        assertEquals(10, dealerHand.getCard(2).getValue());
    }
}
