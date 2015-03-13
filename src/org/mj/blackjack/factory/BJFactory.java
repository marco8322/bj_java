package org.mj.blackjack.factory;

import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.player.BJPlayer;

/**
 * Created by marcojacques on 15-03-08.
 *
 * Interface for representing factory objects for BJ
 */
public interface BJFactory
{
    BJPlayer createPlayer(String name);

    BJPlayer createDealer();

    BJHand createHand(BJPlayer player);
}
