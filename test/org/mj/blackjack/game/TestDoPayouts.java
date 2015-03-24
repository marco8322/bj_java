package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.payout.BJStandardPayout;

import java.util.List;

/**
 * Created by marcojacques on 2015-03-23.
 *
 * Test the payouts from a BJ game
 */
public class TestDoPayouts
    extends TestCase
{
    /**
     * Test payout of player with blackjack, dealer does not have blackjack
     */
    public void testPlayerHasBlackjack()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,11}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.BLACKJACK);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(9));
        dealerHand.addCard(new BJCardImpl(8));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(250, playerHands.get(0).getPlayer().getMoneyAmount());
    }


    /**
     * Test payout of player with blackjack, dealer does have blackjack
     */
    public void testPlayerHasBlackjackDealerHasBlackjack()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,11}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.BLACKJACK);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(11));
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.setState(BJHand.State.BLACKJACK);

        game.doPayouts(dealerHand, playerHands);

        assertEquals(100, playerHands.get(0).getPlayer().getMoneyAmount());
    }

    /**
     * Test payout of player standing, beat dealer
     */
    public void testPlayerStandsBeatDealer()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,10}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.STAY);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(9));
        dealerHand.addCard(new BJCardImpl(10));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(200, playerHands.get(0).getPlayer().getMoneyAmount());
    }


    /**
     * Test payout of player standing, push dealer
     */
    public void testPlayerStandsPush()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,10}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.STAY);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.addCard(new BJCardImpl(10));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(100, playerHands.get(0).getPlayer().getMoneyAmount());
    }


    /**
     * Test payout of player standing, lose to dealer
     */
    public void testPlayerStandsLose()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,9}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.STAY);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.addCard(new BJCardImpl(10));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());
    }


    /**
     * Test payout of player busted
     */
    public void testPlayerBusted()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,9,5}
                }
        );

        //playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.BUSTED);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.addCard(new BJCardImpl(10));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());
    }


    /**
     * Test payout of player surrender
     */
    public void testPlayerSurrender()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,6}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.SURRENDER);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.addCard(new BJCardImpl(10));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(50, playerHands.get(0).getPlayer().getMoneyAmount());
    }

    /**
     * Test multiple hands
     */
    public void testPlayerMultipleHands()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,9}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.STAY);

        BJHand secondHand = new BJHandImpl();
        secondHand.addCard(new BJCardImpl(10));
        secondHand.addCard(new BJCardImpl(3));
        secondHand.addCard(new BJCardImpl(8));
        secondHand.setState(BJHand.State.STAY);

        playerHands.get(0).addHand(secondHand);
        playerHands.get(0).getHandsWithBets().get(1).bet += 100;
        playerHands.get(0).getPlayer().removeMoney(200);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.addCard(new BJCardImpl(8));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(400, playerHands.get(0).getPlayer().getMoneyAmount());
    }


    /**
     * Test payout of multiple players
     */
    public void testMultiplePlayers()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(null, null, new BJStandardPayout())
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{10,8},
                        new int[]{10,6},
                        new int[]{9,8,5},
                        new int[]{10,11}
                }
        );

        playerHands.get(0).getHandsWithBets().get(0).hand.setState(BJHand.State.STAY);
        playerHands.get(1).getHandsWithBets().get(0).hand.setState(BJHand.State.STAY);
        playerHands.get(3).getHandsWithBets().get(0).hand.setState(BJHand.State.BLACKJACK);

        BJHand dealerHand = new BJHandImpl();
        dealerHand.addCard(new BJCardImpl(10));
        dealerHand.addCard(new BJCardImpl(7));

        game.doPayouts(dealerHand, playerHands);

        assertEquals(200, playerHands.get(0).getPlayer().getMoneyAmount());
        assertEquals(0, playerHands.get(1).getPlayer().getMoneyAmount());
        assertEquals(0, playerHands.get(2).getPlayer().getMoneyAmount());
        assertEquals(250, playerHands.get(3).getPlayer().getMoneyAmount());
    }
}
