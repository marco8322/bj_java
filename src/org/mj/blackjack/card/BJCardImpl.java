package org.mj.blackjack.card;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Implementation of a card.  This takes only a value
 */
public class BJCardImpl
    implements BJCard
{
    private final int value;

    public BJCardImpl(int value)
    {
        assert value >= 2 && value <= 11;

        this.value = value;
    }

    @Override
    public int getValue()
    {
        return value;
    }
}
