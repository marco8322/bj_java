package org.mj.blackjack.player;

/**
 * Created by marcojacques on 15-03-03.
 *
 * Interface for player
 */
public interface BJPlayer
{
    void addMoney(int money);

    void removeMoney(int money);

    int getMoneyAmount();

    void setInitialBet(int money);

    void addDoubleBet(int money);

    int getInitialBet();
}
