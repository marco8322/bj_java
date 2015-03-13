package org.mj.blackjack.hand;

import org.mj.blackjack.card.BJCard;

/**
 * Created by marcojacques on 15-03-03.
 *
 * Defines hands for blackjack
 */

public interface BJHand
{
    /**
     * State of the hand
     */
    enum State
    {
        MAY_HIT,
        BLACKJACK,
        STAY,
        BUSTED,
        SURRENDER
    }

    /**
     * Add a card to the hand
     *
     * @param card: card to add
     */
    void addCard(BJCard card);

    /**
     * Return the total value hand.   If this is a soft hand, it returns
     * the total as if the ace was 11.
     *
     * @return total value
     */
    int getTotalValue();

    /**
     * Return true if this is a soft hand (i.e. got an ace that may be viewed as 1 or 11)
     *
     * @return boolean value
     */
    boolean isSoftHand();

    /**
     * Return true if this is an hand with 2 cards with the same value
     *
     * @return true if the hand may be split
     */
    boolean mayBeSplit();

    /**
     * Split the hand...
     *
     * @return return new hand
     */
    BJHand splitHand();

    /**
     * Set the state for this hand
     */
    void setState(State state);

    /**
     * Return the state for this hand
     *
     * @return state
     */
    State getState();

    /**
     * Return the number of cards
     *
     * @return number of cards
     */
    int getNbCards();

    /**
     * Get a card from the hand
     *
     * @param i: which card
     * @return the card
     */
    BJCard getCard(int i);
}
