package com.mj.blackjack.rules;

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
}
