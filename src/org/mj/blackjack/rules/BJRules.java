package org.mj.blackjack.rules;

import org.mj.blackjack.hand.BJHand;

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

    boolean mayReSplitAces();

    int payBlackjack(int bet);

    int paySurrender(int bet);
}
