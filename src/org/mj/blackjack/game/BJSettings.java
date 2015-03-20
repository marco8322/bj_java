package org.mj.blackjack.game;

import org.mj.blackjack.moves.BJPossibleMoves;
import org.mj.blackjack.rules.BJRules;

/**
 * Created by marcojacques on 2015-03-19.
 *
 * Settings for blackjack... rules, possible moves, etc
 */
public interface BJSettings
{
    BJPossibleMoves getPossibleMovesComputer();

    BJRules getRules();
}
