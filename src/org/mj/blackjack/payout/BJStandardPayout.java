package org.mj.blackjack.payout;

/**
 * Created by marcojacques on 2015-03-23.
 *
 * Standard payouts for blackjack
 */
public class BJStandardPayout
        implements BJPayout
{
    @Override
    public int payoutBlackjack(int value)
    {
        // 3-to-2
        return value + (3 * value) / 2;
    }

    @Override
    public int payoutWin(int value)
    {
        return 2 * value;
    }

    @Override
    public int payoutPush(int value)
    {
        return value;
    }

    @Override
    public int payoutSurrender(int value)
    {
        return value / 2;
    }

    @Override
    public int payoutLost(int value)
    {
        return 0;
    }

    @Override
    public int payoutInsurance(int value)
    {
        // 2-to-1
        //
        return value + 2 * value;
    }
}
