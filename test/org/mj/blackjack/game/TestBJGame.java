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
}
