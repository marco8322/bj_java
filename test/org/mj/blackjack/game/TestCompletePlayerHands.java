package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.moves.BJMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.moves.BJStandardPossibleMoves;
import org.mj.blackjack.player.BJPlayerImpl;
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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{8,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.STAY));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.HIT, BJMove.STAY));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.HIT));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.HIT));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.DOUBLE));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.DOUBLE));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{4,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(Arrays.asList(BJMove.DOUBLE));

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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{9,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(
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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{9,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(
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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{9,9}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(
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
                new BJStandardRules(false, 3, false))
        );

        List<BJGame.PlayerHands> playerHands = createHands(
                new int[][]{
                        new int[]{11,11}
                }
        );

        BJNextMove bjNextMove = createBJNextMove(
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
     * TODO:
     *
     * - Test multiple splits
     * - Test split + double
     * - Test surrender
     *
     * Fail tests for invalid choice for all possible moves
     */


    /**
     * Create the hands
     *
     * @param cards: the cards to use
     * @return the player hands structure
     */
    private List<BJGame.PlayerHands> createHands(int[][] cards)
    {
        List<BJGame.PlayerHands> playerHands = new LinkedList<BJGame.PlayerHands>();

        for( int[] handValues : cards )
        {
            BJGame.PlayerHands ph = new BJGame.PlayerHands(new BJPlayerImpl(100));
            playerHands.add(ph);

            ph.getPlayer().setInitialBet(100);

            BJHand hand = new BJHandImpl();
            for ( int value : handValues )
            {
                hand.addCard(new BJCardImpl(value));
            }

            ph.addHand(hand);
        }

        return playerHands;
    }

    /**
     * Create a next move object for testing purpose
     *
     * @param nextMoves_: list of next moves
     * @return list of moves
     */
    private BJNextMove createBJNextMove(final List<BJMove> nextMoves_)
    {
        return new BJNextMove()
        {
            private List<BJMove> nextMoves = new LinkedList<BJMove>(nextMoves_);

            @Override
            public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
            {
                assertFalse(nextMoves.isEmpty());

                BJMove nextMove = nextMoves.remove(0);
                assertTrue(possibleMoves.contains(nextMove));

                return nextMove;
            }
        };
    }
}
