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
 * Created by marcojacques on 2015-03-14.
 *
 * Unit test for checking if dealer or players have blackjacks
 */
public class TestCheckBlackjacks
    extends TestCase
{
    public void testHaveBlackjacks1()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory);

        BJHand dealerHand = createHand(new int[]{10, 11});

        List<BJGame.PlayerHands> playerHands = new LinkedList<BJGame.PlayerHands>();
        playerHands.add(createPlayerHands(new int[]{11, 10}));
        playerHands.add(createPlayerHands(new int[]{10, 10}));
        playerHands.add(createPlayerHands(new int[]{10, 11}));

        game.checkBlackjacks(dealerHand, playerHands);

        assertEquals(BJHand.State.BLACKJACK, dealerHand.getState());
        assertEquals(BJHand.State.BLACKJACK, playerHands.get(0).getHandsWithBets().get(0).hand.getState());
        assertEquals(BJHand.State.MAY_HIT, playerHands.get(1).getHandsWithBets().get(0).hand.getState());
        assertEquals(BJHand.State.BLACKJACK, playerHands.get(2).getHandsWithBets().get(0).hand.getState());
    }

    public void testHaveBlackjacks2()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory);

        BJHand dealerHand = createHand(new int[]{8, 11});

        List<BJGame.PlayerHands> playerHands = new LinkedList<BJGame.PlayerHands>();
        playerHands.add(createPlayerHands(new int[]{2, 10}));
        playerHands.add(createPlayerHands(new int[]{11, 10}));
        playerHands.add(createPlayerHands(new int[]{3, 11}));

        game.checkBlackjacks(dealerHand, playerHands);

        assertEquals(BJHand.State.MAY_HIT, dealerHand.getState());
        assertEquals(BJHand.State.MAY_HIT, playerHands.get(0).getHandsWithBets().get(0).hand.getState());
        assertEquals(BJHand.State.BLACKJACK, playerHands.get(1).getHandsWithBets().get(0).hand.getState());
        assertEquals(BJHand.State.MAY_HIT, playerHands.get(2).getHandsWithBets().get(0).hand.getState());
    }

    private BJHand createHand(int values[])
    {
        BJHand hand = new BJHandImpl();

        for (int value : values) {
            hand.addCard(new BJCardImpl(value));
        }

        return hand;
    }

    private BJGame.PlayerHands createPlayerHands(int values[])
    {
        BJGame.PlayerHands ph = new BJGame.PlayerHands(new BJPlayerImpl(100));
        ph.addHand(createHand(values));

        return ph;
    }
}
