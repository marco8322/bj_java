package org.mj.blackjack.card;

import java.util.Collection;
import java.util.Stack;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Simple implementation of a card deck
 */
public class BJCardDeckImpl
    implements BJCardDeck
{
    private final Stack<BJCard> cards;

    public BJCardDeckImpl(Collection<? extends BJCard> cards)
    {
        assert cards != null;

        this.cards = new Stack<BJCard>();
        this.cards.addAll(cards);
    }

    @Override
    public BJCard nextCard()
    {
        assert !cards.empty();

        return cards.pop();
    }
}
