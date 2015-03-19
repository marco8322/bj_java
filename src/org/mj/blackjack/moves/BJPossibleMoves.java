package org.mj.blackjack.moves;

import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.rules.BJRules;

import java.util.Collection;

/**
 * Created by marcojacques on 2015-03-18.
 *
 * Computes the possible moves
 */
public interface BJPossibleMoves
{
    Collection<BJMove> getPossibleMoves(BJHand currentHand, int numberOfSplits, BJRules rules);
}
