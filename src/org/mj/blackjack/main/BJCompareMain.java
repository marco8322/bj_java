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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by marcojacques on 2015-03-29.
 *
 * Main class for running BJ simulations
 */
public class BJCompareMain
{
    public static void main(String[] cmdLineArgs)
    {
        final int MAX_LOOPS = 2000000;

        BJMainArgs args = parseCommandLine(cmdLineArgs);

        System.out.println("Player hand: " + args.playerHand);
        System.out.println("Kind: " + args.kind);
        System.out.println("Number of decks: " + args.numberDecks);
        System.out.println("Hit soft 17: " + args.hitSoft17);

        for( int dealerCard : args.dealerCard )
        {
            System.out.println();
            for (BJMove move : args.nextMove)
            {
                BJCompareNextMove nextMove = new BJCompareNextMove(move);
                BJCardDeck cardDeck = new BJKnownFirstCardsCardDeck(
                        args.playerHand, args.kind,
                        dealerCard, args.numberDecks
                );

                BJSettings settings = new BJSettingsImpl(
                        new BJStandardPossibleMoves(),
                        new BJStandardRules(args.hitSoft17, 3, false),
                        new BJStandardPayout()
                );

                BJFactory factory = new BJFactoryImpl();

                long totalAmount = 0;

                for (int i = 0; i < MAX_LOOPS; ++i)
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

                System.out.print("Dealer card: " + dealerCard + ", Next move: " + move + ", RESULT = ");
                //System.out.println();
                //System.out.println("Total: " + totalAmount);

                double average = (((double) totalAmount) / (double) MAX_LOOPS);
                System.out.println(new DecimalFormat("#0.00").format(average));
                //System.out.println();
            }
        }
    }

    private static BJMainArgs parseCommandLine(String[] cmdLineArgs)
    {
        File file = new File(cmdLineArgs[0]);
        if( !file.exists() )
        {
            System.out.println("File does not exist: " + file.getName());
            System.exit(1);
        }

        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(BJMainArgs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (BJMainArgs) jaxbUnmarshaller.unmarshal(file);
        }
        catch (JAXBException e)
        {
            System.out.println("Exception reading XML file: " + e.getMessage());
            System.exit(1);
        }

        return new BJMainArgs();
    }

    /*private static Args parseCommandLine(String[] args)
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
    } */
}
