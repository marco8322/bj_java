package com.mj.blackjack.rules;

import com.mj.blackjack.hand.BJHand;
import com.mj.blackjack.moves.BJMove;

import java.util.Set;

/**
 * Created by marcojacques on 15-03-05.
 *
 * Interface for some rules used in the game
 */
public interface BJRules
{
    boolean splitAllowed();

    int maxNumberSplits();

    boolean surrenderAllowed();

    boolean doesDealerHitOnSoft17();

    boolean doubleAllowedAfterSplit();

    Set<BJMove> getPossibleMoves(BJHand hand, int numberSplitsDone);
}
