package org.mj.blackjack.rules;

import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.moves.BJMove;

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

    boolean isDoubleAllowed(BJHand bjHand);

    boolean surrenderAllowed();

    boolean doesDealerHitOnSoft17();

    boolean doubleAllowedAfterSplit();

    boolean onlyOneCardAfterSplittingAces();

    boolean mayReSplitAces();

    Set<BJMove> getPossibleMoves(BJHand hand, int numberSplitsDone);

    int payBlackjack(int bet);

    int paySurrender(int bet);
}
