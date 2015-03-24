package org.mj.blackjack.player;

/**
 * Created by marcojacques on 15-03-12.
 *
 * Simple implementation of a blackjack player
 */
public class BJPlayerImpl
        implements BJPlayer
{
    private int bank;
    private int initialBet = 0;

    public BJPlayerImpl(int initialMoney)
    {
        bank = initialMoney;
    }

    @Override
    public void addMoney(int money)
    {
        bank += money;
    }

    @Override
    public void removeMoney(int money)
    {
        bank -= money;
    }

    @Override
    public int getMoneyAmount()
    {
        return bank;
    }

    @Override
    public void setInitialBet(int money)
    {
        initialBet = money;
    }

    @Override
    public int getInitialBet()
    {
        return initialBet;
    }
}
