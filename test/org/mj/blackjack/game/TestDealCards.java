package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.player.BJPlayer;
import org.mj.blackjack.player.BJPlayerImpl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Unit tests for dealing cards at the BJ game
 */
public class TestDealCards
    extends TestCase
{
    public void testDealCards()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory);

        BJPlayer bjPlayer1 = new BJPlayerImpl(200);
        BJPlayer bjPlayer2 = new BJPlayerImpl(400);

        bjPlayer1.setInitialBet(100);
        bjPlayer2.setInitialBet(150);

        List<BJGame.PlayerHands> playerHandses = new LinkedList<BJGame.PlayerHands>();
        BJHand dealerHand = game.dealCards(
                Arrays.asList(bjPlayer1, bjPlayer2),
                factory.createCardDeck(new int[]{2,3,4,5,6,7}),
                playerHandses
        );

        assertEquals(2, playerHandses.size());

        assertSame(bjPlayer1, playerHandses.get(0).getPlayer());
        assertEquals(1, playerHandses.get(0).getHandsWithBets().size());
        assertEquals(100, playerHandses.get(0).getHandsWithBets().get(0).bet);
        assertEquals(2, playerHandses.get(0).getHandsWithBets().get(0).hand.getNbCards());
        assertEquals(7, playerHandses.get(0).getHandsWithBets().get(0).hand.getCard(0).getValue());
        assertEquals(4, playerHandses.get(0).getHandsWithBets().get(0).hand.getCard(1).getValue());
        assertEquals(11, playerHandses.get(0).getHandsWithBets().get(0).hand.getTotalValue());


        assertSame(bjPlayer2, playerHandses.get(1).getPlayer());
        assertEquals(1, playerHandses.get(1).getHandsWithBets().size());
        assertEquals(150, playerHandses.get(1).getHandsWithBets().get(0).bet);
        assertEquals(2, playerHandses.get(1).getHandsWithBets().get(0).hand.getNbCards());
        assertEquals(6, playerHandses.get(1).getHandsWithBets().get(0).hand.getCard(0).getValue());
        assertEquals(3, playerHandses.get(1).getHandsWithBets().get(0).hand.getCard(1).getValue());
        assertEquals(9, playerHandses.get(1).getHandsWithBets().get(0).hand.getTotalValue());

        assertEquals(2, dealerHand.getNbCards());
        assertEquals(5, dealerHand.getCard(0).getValue());
        assertEquals(2, dealerHand.getCard(1).getValue());
        assertEquals(7, dealerHand.getTotalValue());
    }
}
