package org.mj.blackjack.moves;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.hand.BJHand;

import java.util.Collection;

/**
 * Created by marcojacques on 2015-03-09.
 *
 * Get the next move for the hand
 */
public interface BJNextMove
{
    BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves);
}
