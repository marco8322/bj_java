package org.mj.blackjack.card;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Simple implementation of a card deck
 */
public class BJCardDeckImpl
    implements BJCardDeck
{
    private final BJCard[] cards;
    private int currCard;

    public BJCardDeckImpl(BJCard[] cards)
    {
        assert cards != null;

        this.cards = new BJCard[cards.length];
        System.arraycopy(cards, 0, this.cards, 0, cards.length);

        currCard = 0;
    }

    @Override
    public BJCard nextCard()
    {
        assert currCard < cards.length;

        return cards[currCard++];
    }

    @Override
    public void shuffle()
    {
        // nothing to do
    }
}
