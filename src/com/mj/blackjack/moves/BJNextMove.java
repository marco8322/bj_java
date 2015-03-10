package com.mj.blackjack.moves;

import com.mj.blackjack.card.BJCard;
import com.mj.blackjack.hand.BJHand;

import java.util.Set;

/**
 * Created by marcojacques on 2015-03-09.
 *
 * Get the next move for the hand
 */
public interface BJNextMove
{
    BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Set<? extends BJMove> possibleMoves);
}
