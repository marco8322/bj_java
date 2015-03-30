package org.mj.blackjack.main;

import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJMultipleCardDecks;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.game.BJGame;
import org.mj.blackjack.game.BJSettings;
import org.mj.blackjack.game.BJSettingsImpl;
import org.mj.blackjack.moves.BJBasicNextMove;
import org.mj.blackjack.moves.BJNextMove;
import org.mj.blackjack.moves.BJRandomNextMove;
import org.mj.blackjack.moves.BJStandardPossibleMoves;
import org.mj.blackjack.payout.BJStandardPayout;
import org.mj.blackjack.player.BJPlayer;
import org.mj.blackjack.player.BJPlayerImpl;
import org.mj.blackjack.rules.BJStandardRules;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by marcojacques on 2015-03-29.
 *
 * Main class for running BJ simulations
 */
public class BJRandomMain
{
    public static void main(String[] args)
    {
        final int MAX_LOOPS = 100000;

        BJNextMove nextMove = new BJBasicNextMove();
        BJCardDeck cardDeck = new BJMultipleCardDecks(6);
        BJSettings settings = new BJSettingsImpl(
                new BJStandardPossibleMoves(),
                new BJStandardRules(false, 3, false),
                new BJStandardPayout()
        );

        BJFactory factory = new BJFactoryImpl();

        long totalAmount = 0;

        for( int i = 0; i < MAX_LOOPS; ++i )
        {
            BJPlayer player = new BJPlayerImpl(0);

            player.setInitialBet(100);

            BJGame game = new BJGame(factory, settings);
            cardDeck.shuffle();

            game.playGame(Arrays.asList(player), cardDeck, nextMove);
            totalAmount += player.getMoneyAmount();

            //System.out.println("After hand: " + player.getMoneyAmount() + ", total: " + totalAmount);
        }

        System.out.println();
        System.out.println("Total: " + totalAmount);

        double average = (((double)totalAmount) / (double)MAX_LOOPS);
        System.out.println(new DecimalFormat("#0.00").format(average));
    }
}
