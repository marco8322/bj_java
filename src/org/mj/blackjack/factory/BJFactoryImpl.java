package org.mj.blackjack.factory;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.card.BJCardDeck;
import org.mj.blackjack.card.BJCardDeckImpl;
import org.mj.blackjack.card.BJCardImpl;
import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.hand.BJHandImpl;

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

        Stack<BJCard> cards = new Stack<BJCard>();
        for( int val : values )
        {
            cards.push(new BJCardImpl(val));
        }

        return new BJCardDeckImpl(cards);
    }
}
