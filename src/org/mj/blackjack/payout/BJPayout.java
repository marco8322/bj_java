package org.mj.blackjack.payout;

/**
 * Created by marcojacques on 2015-03-23.
 *
 * Interface for blackjack payouts.  The value should include the initial bet.
 */
public interface BJPayout
{
    int payoutBlackjack(int value);

    int payoutWin(int value);

    int payoutPush(int value);

    int payoutSurrender(int value);

    int payoutLost(int value);

    int payoutInsurance(int value);
}
