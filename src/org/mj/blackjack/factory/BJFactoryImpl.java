package org.mj.blackjack.factory;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;
import org.mj.blackjack.moves.BJPossibleMoves;
import org.mj.blackjack.moves.BJStandardPossibleMoves;

import java.util.Stack;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Simple factory implementation for BJ objects
 */
public class BJFactoryImpl
    implements BJFactory
{
    @Override
    public BJHand createHand()
    {
        return new BJHandImpl();
    }

    public BJCardDeck createCardDeck(int values[])
    {
        assert values != null;

        BJCard[] cards = new BJCard[values.length];
        for( int i = 0; i < values.length; ++i )
        {
            cards[i] = new BJCardImpl(values[i]);
        }

        return new BJCardDeckImpl(cards);
    }

    @Override
    public BJPossibleMoves createPossibleMovesComputer()
    {
        return new BJStandardPossibleMoves();
    }
}
