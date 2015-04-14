package org.mj.blackjack.main;

import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.card.BJKnownFirstCardsCardDeck;
import org.mj.blackjack.factory.BJFactory;
import org.mj.blackjack.factory.BJFactoryImpl;
import org.mj.blackjack.game.BJGame;
import org.mj.blackjack.game.BJSettings;
import org.mj.blackjack.game.BJSettingsImpl;
import org.mj.blackjack.moves.BJCompareNextMove;
import org.mj.blackjack.moves.BJMove;
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
public class BJCompareMain
{
    private static class Args
    {
        private int playerHand;
        private BJKnownFirstCardsCardDeck.Kind kind;
        private BJMove nextMove;
        private int dealerCard;
        private int numberDecks;

        Args(int playerHand_,
             BJKnownFirstCardsCardDeck.Kind kind_,
             BJMove nextMove_,
             int dealerCard_,
             int numberDecks_
        )
        {
            playerHand = playerHand_;
            kind = kind_;
            nextMove = nextMove_;
            dealerCard = dealerCard_;
            numberDecks = numberDecks_;
        }
    }

    public static void main(String[] args)
    {
        final int MAX_LOOPS = 100000;

        Args parsedArgs = parseCommandLine(args);

        BJCompareNextMove nextMove = new BJCompareNextMove(parsedArgs.nextMove);
        BJCardDeck cardDeck = new BJKnownFirstCardsCardDeck(
                parsedArgs.playerHand, parsedArgs.kind,
                parsedArgs.dealerCard, parsedArgs.numberDecks
        );

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
            nextMove.reset();

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

    private static Args parseCommandLine(String[] args)
    {
        int playerHand = 18;
        BJKnownFirstCardsCardDeck.Kind kind = BJKnownFirstCardsCardDeck.Kind.Normal;
        BJMove nextMove = BJMove.STAY;
        int dealerCard = 10;
        int numberDecks = 6;

        for( String arg: args )
        {
            String[] nameValue = arg.split(":");
            if( nameValue.length != 2 )
            {
                System.out.println("Invalid argument syntax");
                System.exit(1);
            }

            String name = nameValue[0];
            String value = nameValue[1];

            if( name.equals("playerHand") )
            {
                playerHand = Integer.parseInt(value);
            }
            else if( name.equals("kind") )
            {
                kind = BJKnownFirstCardsCardDeck.Kind.valueOf(value);
                if( kind == null )
                {
                    System.out.println("Invalid kind: " + value);
                    System.exit(1);
                }
            }
            else if( name.equals("nextMove") )
            {
                nextMove = BJMove.valueOf(value);

                if( nextMove == null )
                {
                    System.out.println("Invalid next value: " + value);
                    System.exit(1);
                }
            }
            else if( name.equals("dealerCard") )
            {
                dealerCard = Integer.parseInt(value);
            }
            else if( name.equals("numberDecks") )
            {
                numberDecks = Integer.parseInt(value);
            }
            else
            {
                System.out.println("Invalid argument name: " + name);
                System.exit(1);
            }
        }

        return new Args(playerHand, kind, nextMove, dealerCard, numberDecks);
    }
}
