package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.moves.BJMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.moves.BJStandardPossibleMoves;
import org.mj.blackjack.rules.BJStandardRules;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 2015-03-17.
 *
 * Unit test for completing player hands
 */
public class TestCompletePlayerHands
    extends TestCase
{
    /**
     * Simple STAY action
     */
    public void testSimpleStay()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null
            )
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{8, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.STAY));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(17, handToCheck.getTotalValue());
        assertEquals(2, handToCheck.getNbCards());
    }

    /**
     * Test HIT and STAY
     */
    public void testHitAndStay()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{4, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.HIT, BJMove.STAY));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(6)})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(19, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(6, handToCheck.getCard(2).getValue());
    }

    /**
     * Test HIT which busts
     */
    public void testHitAndBust()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{4, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.HIT));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(9)})
        );

        assertFalse(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck.getState());
        assertEquals(22, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(9, handToCheck.getCard(2).getValue());
    }

    /**
     * Test HIT which gets 21
     */
    public void testHitAnd21()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{4, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.HIT));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(8)})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(21, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(8, handToCheck.getCard(2).getValue());
    }


    /**
     * Test double, get 20
     */
    public void testDoubleAndStayOn20()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{4, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.DOUBLE));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(7)})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(20, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(7, handToCheck.getCard(2).getValue());

        assertEquals(200, playerHands.get(0).getHandsWithBets().get(0).bet);

    }



    /**
     * Test double, get 21
     */
    public void testDoubleAndStayOn21()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{4, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.DOUBLE));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(8)})
        );

        assertTrue(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck.getState());
        assertEquals(21, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(8, handToCheck.getCard(2).getValue());

        assertEquals(200, playerHands.get(0).getHandsWithBets().get(0).bet);

    }


    /**
     * Test double, get 22 (bust)
     */
    public void testDoubleAndBustOn22()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{4, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(Arrays.asList(BJMove.DOUBLE));

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(new BJCard[]{new BJCardImpl(9)})
        );

        assertFalse(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck.getState());
        assertEquals(22, handToCheck.getTotalValue());
        assertEquals(3, handToCheck.getNbCards());
        assertEquals(9, handToCheck.getCard(2).getValue());

        assertEquals(200, playerHands.get(0).getHandsWithBets().get(0).bet);
    }

    /**
     * Test simple split, no aces
     */
    public void testSimpleSplit()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.STAY, BJMove.HIT, BJMove.STAY)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(8),
                                new BJCardImpl(3),
                                new BJCardImpl(7)
                        }
                )
        );

        assertTrue(isAnyStay);
        assertEquals(2, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck1.getState());
        assertEquals(17, handToCheck1.getTotalValue());
        assertEquals(2, handToCheck1.getNbCards());
        assertEquals(8, handToCheck1.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(0).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.STAY, handToCheck2.getState());
        assertEquals(19, handToCheck2.getTotalValue());
        assertEquals(3, handToCheck2.getNbCards());
        assertEquals(3, handToCheck2.getCard(1).getValue());
        assertEquals(7, handToCheck2.getCard(2).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(1).bet);
    }

    /**
     * Test simple split, no aces, one is busted
     */
    public void testSimpleSplitOneHandBusted()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.HIT, BJMove.HIT, BJMove.STAY)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(8),
                                new BJCardImpl(5),
                                new BJCardImpl(7),
                                new BJCardImpl(2)
                        }
                )
        );

        assertTrue(isAnyStay);
        assertEquals(2, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck1.getState());
        assertEquals(22, handToCheck1.getTotalValue());
        assertEquals(3, handToCheck1.getNbCards());
        assertEquals(8, handToCheck1.getCard(1).getValue());
        assertEquals(5, handToCheck1.getCard(2).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(0).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.STAY, handToCheck2.getState());
        assertEquals(18, handToCheck2.getTotalValue());
        assertEquals(3, handToCheck2.getNbCards());
        assertEquals(7, handToCheck2.getCard(1).getValue());
        assertEquals(2, handToCheck2.getCard(2).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(1).bet);
    }

    /**
     * Test simple split, no aces, both hands busted
     */
    public void testSimpleSplitBothHandsBusted()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.HIT, BJMove.HIT)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(8),
                                new BJCardImpl(5),
                                new BJCardImpl(7),
                                new BJCardImpl(6)
                        }
                )
        );

        assertFalse(isAnyStay);
        assertEquals(2, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck1.getState());
        assertEquals(22, handToCheck1.getTotalValue());
        assertEquals(3, handToCheck1.getNbCards());
        assertEquals(8, handToCheck1.getCard(1).getValue());
        assertEquals(5, handToCheck1.getCard(2).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(0).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck2.getState());
        assertEquals(22, handToCheck2.getTotalValue());
        assertEquals(3, handToCheck2.getNbCards());
        assertEquals(7, handToCheck2.getCard(1).getValue());
        assertEquals(6, handToCheck2.getCard(2).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(1).bet);
    }

    /**
     * Test split aces
     */
    public void testSplitAces()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{11, 11}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SPLIT)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(8),
                                new BJCardImpl(3)
                        }
                )
        );

        assertTrue(isAnyStay);
        assertEquals(2, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck1.getState());
        assertEquals(19, handToCheck1.getTotalValue());
        assertEquals(2, handToCheck1.getNbCards());
        assertEquals(8, handToCheck1.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(0).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.STAY, handToCheck2.getState());
        assertEquals(14, handToCheck2.getTotalValue());
        assertEquals(2, handToCheck2.getNbCards());
        assertEquals(3, handToCheck2.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(1).bet);
    }


    /**
     * Test simple split, no aces, one is busted
     */
    public void testReSplit()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.STAY, BJMove.SPLIT, BJMove.STAY, BJMove.STAY)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(8),
                                new BJCardImpl(9),
                                new BJCardImpl(7),
                                new BJCardImpl(2)
                        }
                )
        );

        assertTrue(isAnyStay);
        assertEquals(3, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-200, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck1.getState());
        assertEquals(17, handToCheck1.getTotalValue());
        assertEquals(2, handToCheck1.getNbCards());
        assertEquals(8, handToCheck1.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(0).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.STAY, handToCheck2.getState());
        assertEquals(16, handToCheck2.getTotalValue());
        assertEquals(2, handToCheck2.getNbCards());
        assertEquals(7, handToCheck2.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(1).bet);

        BJHand handToCheck3 = playerHands.get(0).getHandsWithBets().get(2).hand;
        assertEquals(BJHand.State.STAY, handToCheck3.getState());
        assertEquals(11, handToCheck3.getTotalValue());
        assertEquals(2, handToCheck3.getNbCards());
        assertEquals(2, handToCheck3.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(2).bet);
    }

    /**
     * Test split + double
     */
    public void testSplitDouble()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.STAY, BJMove.DOUBLE)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(8),
                                new BJCardImpl(2),
                                new BJCardImpl(10)
                        }
                )
        );

        assertTrue(isAnyStay);
        assertEquals(2, playerHands.get(0).getHandsWithBets().size());
        assertEquals(-200, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck1.getState());
        assertEquals(17, handToCheck1.getTotalValue());
        assertEquals(2, handToCheck1.getNbCards());
        assertEquals(8, handToCheck1.getCard(1).getValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(0).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.STAY, handToCheck2.getState());
        assertEquals(21, handToCheck2.getTotalValue());
        assertEquals(3, handToCheck2.getNbCards());
        assertEquals(2, handToCheck2.getCard(1).getValue());
        assertEquals(10, handToCheck2.getCard(2).getValue());
        assertEquals(200, playerHands.get(0).getHandsWithBets().get(1).bet);
    }

    /**
     * Test surrender
     */
    public void testSurrender()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, true),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 7}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SURRENDER)
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{}
                )
        );

        assertFalse(isAnyStay);
        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.SURRENDER, handToCheck1.getState());
        assertEquals(16, handToCheck1.getTotalValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);
    }


    /**
     * Test for checking that we do not have a split on the first hand and first move
     */
    public void testFailNoSplitFirstHand()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 7}
                }
        );

        BJNextMove bjNextMove = createInvalidBJNextMove(
                Arrays.asList(BJMove.SPLIT)
        );

        try
        {
            game.completePlayerHands(
                    playerHands,
                    new BJCardImpl(10),
                    bjNextMove,
                    new BJCardDeckImpl(
                            new BJCard[]{}
                    )
            );

            fail();
        }
        catch (InvalidBJNextMove e)
        {
            assertEquals(BJMove.SPLIT, e.invalidMove);
        }
    }

    /**
     * Test for checking that we do not have a split after the max number of splits have been reached
     */
    public void testFailNoSplitMaxSplits()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 2, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = createInvalidBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.STAY, BJMove.SPLIT, BJMove.SPLIT)
        );

        try
        {
            game.completePlayerHands(
                    playerHands,
                    new BJCardImpl(10),
                    bjNextMove,
                    new BJCardDeckImpl(
                            new BJCard[]{
                                    new BJCardImpl(7),
                                    new BJCardImpl(9),
                                    new BJCardImpl(9),
                                    new BJCardImpl(8)
                            }
                    )
            );

            fail();
        }
        catch (InvalidBJNextMove e)
        {
            assertEquals(BJMove.SPLIT, e.invalidMove);
        }
    }

    /**
     * Test for checking that we do not have a double after a first hit
     */
    public void testFailNoDouble()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 3}
                }
        );

        BJNextMove bjNextMove = createInvalidBJNextMove(
                Arrays.asList(BJMove.HIT, BJMove.DOUBLE)
        );

        try
        {
            game.completePlayerHands(
                    playerHands,
                    new BJCardImpl(10),
                    bjNextMove,
                    new BJCardDeckImpl(
                            new BJCard[]{new BJCardImpl(2)}
                    )
            );

            fail();
        }
        catch (InvalidBJNextMove e)
        {
            assertEquals(BJMove.DOUBLE, e.invalidMove);
        }
    }

    /**
     * Test for checking that we do not have a surrender if not allowed
     */
    public void testFailNoSurrenderNotAllowed()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 3}
                }
        );

        BJNextMove bjNextMove = createInvalidBJNextMove(
                Arrays.asList(BJMove.SURRENDER)
        );

        try
        {
            game.completePlayerHands(
                    playerHands,
                    new BJCardImpl(10),
                    bjNextMove,
                    new BJCardDeckImpl(
                            new BJCard[]{new BJCardImpl(2)}
                    )
            );

            fail();
        }
        catch (InvalidBJNextMove e)
        {
            assertEquals(BJMove.SURRENDER, e.invalidMove);
        }
    }

    /**
     * Test for checking that we do not have a surrender after a first hit
     */
    public void testFailNoSurrenderAfterFirstHit()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, true),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 3}
                }
        );

        BJNextMove bjNextMove = createInvalidBJNextMove(
                Arrays.asList(BJMove.HIT, BJMove.SURRENDER)
        );

        try
        {
            game.completePlayerHands(
                    playerHands,
                    new BJCardImpl(10),
                    bjNextMove,
                    new BJCardDeckImpl(
                            new BJCard[]{new BJCardImpl(2)}
                    )
            );

            fail();
        }
        catch (InvalidBJNextMove e)
        {
            assertEquals(BJMove.SURRENDER, e.invalidMove);
        }
    }

    /**
     * Test for checking that we do not have a surrender after a first hit
     */
    public void testFailNoSurrenderAfterSplit()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, true),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 9}
                }
        );

        BJNextMove bjNextMove = createInvalidBJNextMove(
                Arrays.asList(BJMove.SPLIT, BJMove.SURRENDER)
        );

        try
        {
            game.completePlayerHands(
                    playerHands,
                    new BJCardImpl(10),
                    bjNextMove,
                    new BJCardDeckImpl(
                            new BJCard[]{new BJCardImpl(6), new BJCardImpl(5)}
                    )
            );

            fail();
        }
        catch (InvalidBJNextMove e)
        {
            assertEquals(BJMove.SURRENDER, e.invalidMove);
        }
    }

    /**
     * Test with multi-players
     */
    public void testMultiplePlayers1()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, true),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 7},
                        new int[]{3, 3},
                        new int[]{9, 6}
                }
        );

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(
                        BJMove.STAY,
                        BJMove.SPLIT, BJMove.STAY, BJMove.STAY,
                        BJMove.HIT
                )
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(6), new BJCardImpl(8),
                                new BJCardImpl(10)
                        }
                )
        );

        assertTrue(isAnyStay);
        assertEquals(3, playerHands.size());

        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        assertEquals(2, playerHands.get(1).getHandsWithBets().size());
        assertEquals(-100, playerHands.get(1).getPlayer().getMoneyAmount());

        assertEquals(1, playerHands.get(2).getHandsWithBets().size());
        assertEquals(0, playerHands.get(2).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck1.getState());
        assertEquals(16, handToCheck1.getTotalValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(1).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.STAY, handToCheck2.getState());
        assertEquals(9, handToCheck2.getTotalValue());
        assertEquals(100, playerHands.get(1).getHandsWithBets().get(0).bet);

        BJHand handToCheck3 = playerHands.get(1).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.STAY, handToCheck3.getState());
        assertEquals(11, handToCheck3.getTotalValue());
        assertEquals(100, playerHands.get(1).getHandsWithBets().get(1).bet);

        BJHand handToCheck4 = playerHands.get(2).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck4.getState());
        assertEquals(25, handToCheck4.getTotalValue());
        assertEquals(100, playerHands.get(2).getHandsWithBets().get(0).bet);
    }


    /**
     * Test with multi-players, all busted or blackjack
     */
    public void testMultiplePlayers2()
    {
        BJFactory factory = new BJFactoryImpl();
        BJGame game = new BJGame(factory, new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, true),
                null)
        );

        List<BJGame.PlayerHands> playerHands = TestGameUtil.createHands(
                new int[][]{
                        new int[]{9, 7},
                        new int[]{9, 9},
                        new int[]{10, 11}
                }
        );

        playerHands.get(2).getHandsWithBets().get(0).hand.setState(BJHand.State.BLACKJACK);

        BJNextMove bjNextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(
                        BJMove.HIT,
                        BJMove.SPLIT, BJMove.HIT, BJMove.DOUBLE
                )
        );

        boolean isAnyStay = game.completePlayerHands(
                playerHands,
                new BJCardImpl(10),
                bjNextMove,
                new BJCardDeckImpl(
                        new BJCard[]{
                                new BJCardImpl(6),
                                new BJCardImpl(8), new BJCardImpl(6),
                                new BJCardImpl(3), new BJCardImpl(10)
                        }
                )
        );

        assertFalse(isAnyStay);
        assertEquals(3, playerHands.size());

        assertEquals(1, playerHands.get(0).getHandsWithBets().size());
        assertEquals(0, playerHands.get(0).getPlayer().getMoneyAmount());

        assertEquals(2, playerHands.get(1).getHandsWithBets().size());
        assertEquals(-200, playerHands.get(1).getPlayer().getMoneyAmount());

        assertEquals(1, playerHands.get(2).getHandsWithBets().size());
        assertEquals(0, playerHands.get(2).getPlayer().getMoneyAmount());

        BJHand handToCheck1 = playerHands.get(0).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck1.getState());
        assertEquals(22, handToCheck1.getTotalValue());
        assertEquals(100, playerHands.get(0).getHandsWithBets().get(0).bet);

        BJHand handToCheck2 = playerHands.get(1).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck2.getState());
        assertEquals(23, handToCheck2.getTotalValue());
        assertEquals(100, playerHands.get(1).getHandsWithBets().get(0).bet);

        BJHand handToCheck3 = playerHands.get(1).getHandsWithBets().get(1).hand;
        assertEquals(BJHand.State.BUSTED, handToCheck3.getState());
        assertEquals(22, handToCheck3.getTotalValue());
        assertEquals(200, playerHands.get(1).getHandsWithBets().get(1).bet);

        BJHand handToCheck4 = playerHands.get(2).getHandsWithBets().get(0).hand;
        assertEquals(BJHand.State.BLACKJACK, handToCheck4.getState());
        assertEquals(21, handToCheck4.getTotalValue());
        assertEquals(100, playerHands.get(2).getHandsWithBets().get(0).bet);
    }

    /**
     * Exception for testing if we got the fact that we have an invalid move here
     */
    private static class InvalidBJNextMove
        extends RuntimeException
    {
        final BJMove invalidMove;

        InvalidBJNextMove(BJMove invalidMove_)
        {
            invalidMove = invalidMove_;
        }
    }


    /**
     * Creates a next move object which checks if a move is not a valid one.
     * The check is done only on the last provided move (permits to get more cards
     * or action to check invalid moves afterwards)
     *
     * @param nextMoves_: the list of moves
     * @return the "invalid" BJNextMove object
     */
    private BJNextMove createInvalidBJNextMove(final List<BJMove> nextMoves_)
    {
        return new BJNextMove()
        {
            private List<BJMove> nextMoves = new LinkedList<BJMove>(nextMoves_);

            @Override
            public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
            {
                assertFalse(nextMoves.isEmpty());
                BJMove nextMove = nextMoves.remove(0);

                if( nextMoves.isEmpty() )
                {
                    if( possibleMoves.contains(nextMove) )
                    {
                        fail();
                        return null;
                    }
                    else
                    {
                        throw new InvalidBJNextMove(nextMove);
                    }
                }
                else
                {
                    assertTrue(possibleMoves.contains(nextMove));

                    return nextMove;
                }
            }
        };
    }
}
