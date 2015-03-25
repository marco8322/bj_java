package org.mj.blackjack.game;

import junit.framework.TestCase;
import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.moves.BJMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.moves.BJStandardPossibleMoves;
import org.mj.blackjack.payout.BJStandardPayout;
import org.mj.blackjack.player.BJPlayer;
import org.mj.blackjack.player.BJPlayerImpl;
import org.mj.blackjack.rules.BJStandardRules;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 2015-03-23.
 *
 * Unit test of blackjack game
 */
public class TestBJGame
    extends TestCase
{
    /**
     * Player has blackjack
     */
    public void testPlayerBlackjack()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 3, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(10), new BJCardImpl(4),
                        new BJCardImpl(11), new BJCardImpl(8)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.<BJMove>asList()
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(150, players.get(0).getMoneyAmount());
    }

    /**
     * Dealer has blackjack
     */
    public void testDealerBlackjack()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 3, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(1).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(10), new BJCardImpl(10), new BJCardImpl(10),
                        new BJCardImpl(10), new BJCardImpl(11), new BJCardImpl(11)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.<BJMove>asList()
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(-100, players.get(0).getMoneyAmount());
        assertEquals(0, players.get(1).getMoneyAmount());
    }

    /**
     * Test with hit/stay
     */
    public void testHit()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 3, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(1).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(2).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(3).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(10), new BJCardImpl(10), new BJCardImpl(6), new BJCardImpl(5), new BJCardImpl(10),
                        new BJCardImpl(4), new BJCardImpl(5), new BJCardImpl(7), new BJCardImpl(4), new BJCardImpl(9),
                        new BJCardImpl(8), new BJCardImpl(5), new BJCardImpl(6), new BJCardImpl(4)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(
                        BJMove.HIT,
                        BJMove.HIT, BJMove.STAY,
                        BJMove.HIT, BJMove.STAY,
                        BJMove.HIT, BJMove.STAY
                        )
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(-100, players.get(0).getMoneyAmount());
        assertEquals(100, players.get(1).getMoneyAmount());
        assertEquals(0, players.get(2).getMoneyAmount());
        assertEquals(-100, players.get(3).getMoneyAmount());
    }

    /**
     * Test with doubles
     */
    public void testDouble()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 3, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(1).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(2).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(3).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(2), new BJCardImpl(9), new BJCardImpl(10), new BJCardImpl(9), new BJCardImpl(10),
                        new BJCardImpl(9), new BJCardImpl(3), new BJCardImpl(5), new BJCardImpl(4), new BJCardImpl(6),
                        new BJCardImpl(6), new BJCardImpl(8), new BJCardImpl(8), new BJCardImpl(5), new BJCardImpl(2)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.DOUBLE, BJMove.DOUBLE, BJMove.DOUBLE, BJMove.DOUBLE)
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(-200, players.get(0).getMoneyAmount());
        assertEquals(200, players.get(1).getMoneyAmount());
        assertEquals(-200, players.get(2).getMoneyAmount());
        assertEquals(0, players.get(3).getMoneyAmount());
    }

    /**
     * Test with split
     */
    public void testSplit()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 1, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(1).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(2).setInitialBet(100);
        players.add(new BJPlayerImpl(0));
        players.get(3).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(2), new BJCardImpl(9), new BJCardImpl(5), new BJCardImpl(4), new BJCardImpl(10),
                        new BJCardImpl(2), new BJCardImpl(9), new BJCardImpl(5), new BJCardImpl(4), new BJCardImpl(6),
                        new BJCardImpl(6), new BJCardImpl(10), new BJCardImpl(7), new BJCardImpl(11),
                        new BJCardImpl(8), new BJCardImpl(7),
                        new BJCardImpl(10), new BJCardImpl(4), new BJCardImpl(4), new BJCardImpl(10),
                        new BJCardImpl(5), new BJCardImpl(6), new BJCardImpl(6), new BJCardImpl(6),
                        new BJCardImpl(2)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(
                        BJMove.SPLIT, BJMove.DOUBLE, BJMove.DOUBLE,
                        BJMove.SPLIT, BJMove.STAY, BJMove.STAY,
                        BJMove.SPLIT, BJMove.HIT, BJMove.STAY, BJMove.HIT, BJMove.STAY,
                        BJMove.SPLIT, BJMove.HIT, BJMove.STAY, BJMove.DOUBLE
                )
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(200, players.get(0).getMoneyAmount());
        assertEquals(-200, players.get(1).getMoneyAmount());
        assertEquals(200, players.get(2).getMoneyAmount());
        assertEquals(-300, players.get(3).getMoneyAmount());
    }

    /**
     * Player surrender
     */
    public void testSurrender()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 3, true),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(10), new BJCardImpl(4),
                        new BJCardImpl(6), new BJCardImpl(8)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.SURRENDER)
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(-50, players.get(0).getMoneyAmount());
    }


    /**
     * Test dealer hit soft 17
     */
    public void testDealerHitSoft17()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(true, 3, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(10), new BJCardImpl(6),
                        new BJCardImpl(8), new BJCardImpl(11),
                        new BJCardImpl(2)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.STAY)
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(-100, players.get(0).getMoneyAmount());
    }


    /**
     * Test dealer stay soft 17
     */
    public void testDealerStaySoft17()
    {
        BJGame game = new BJGame(
                new BJFactoryImpl(),
                new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(false, 3, false),
                        new BJStandardPayout()
                )
        );

        List<BJPlayer> players = new LinkedList<BJPlayer>();
        players.add(new BJPlayerImpl(0));
        players.get(0).setInitialBet(100);

        BJCardDeck cardDeck = new BJCardDeckImpl(
                new BJCard[]{
                        new BJCardImpl(10), new BJCardImpl(6),
                        new BJCardImpl(8), new BJCardImpl(11)
                }
        );

        BJNextMove nextMove = TestGameUtil.createBJNextMove(
                Arrays.asList(BJMove.STAY)
        );

        game.playGame(players, cardDeck, nextMove);

        assertEquals(100, players.get(0).getMoneyAmount());
    }
}
