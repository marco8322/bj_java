package org.mj.blackjack.rules;

import org.mj.blackjack.hand.BJHand;

/**
 * Created by marcojacques on 2015-03-19.
 *
 * Standard rules for blackjack.
 *
 * Here we parameterize the following:
 *
 * - if surrender is allowed
 * - if dealer hits on soft 17
 * - the number of splits allowed
 *
 * The following hold:
 *
 * - splits are allowed
 * - doubles are allowed after splits
 * - aces cannot re-split or double or hit
 * - double only on 2 cards
 */
public class BJStandardRules
        implements BJRules
{
    private final boolean dealerHitsSoft17;
    private final int maxSplitsAllowed;
    private final boolean isSurrenderAllowed;

    public BJStandardRules(
            boolean dealerHitsSoft17,
            int maxSplitsAllowed,
            boolean isSurrenderAllowed
    )
    {
        this.dealerHitsSoft17 = dealerHitsSoft17;
        this.maxSplitsAllowed = maxSplitsAllowed;
        this.isSurrenderAllowed = isSurrenderAllowed;
    }

    @Override
    public boolean splitAllowed()
    {
        return true;
    }

    @Override
    public int maxNumberSplits()
    {
        return maxSplitsAllowed;
    }

    @Override
    public boolean isDoubleAllowed(BJHand bjHand)
    {
        return bjHand.getNbCards() == 2;
    }

    @Override
    public boolean surrenderAllowed()
    {
        return isSurrenderAllowed;
    }

    @Override
    public boolean doesDealerHitOnSoft17()
    {
        return dealerHitsSoft17;
    }

    @Override
    public boolean doubleAllowedAfterSplit()
    {
        return true;
    }

    @Override
    public boolean onlyOneCardAfterSplittingAces()
    {
        return true;
    }

    @Override
    public boolean mayReSplitAces()
    {
        return false;
    }

    @Override
    public int payBlackjack(int bet)
    {
        return 0;
    }

    @Override
    public int paySurrender(int bet)
    {
        return 0;
    }
}
