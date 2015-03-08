package com.mj.blackjack.card;

/**
 * Created by marcojacques on 15-03-03.
 *
 * This represents cards for a blackjack hand
 */
public interface BJCard
{
    /**
     * Return the value associated to the card.
     *
     * If the card is an ace, it should return 11.
     *
     * @return a value between 2 and 11
     */
    int getValue();
}
