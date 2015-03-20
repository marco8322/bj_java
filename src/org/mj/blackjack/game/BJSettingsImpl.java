package org.mj.blackjack.game;

import org.mj.blackjack.moves.BJPossibleMoves;
import org.mj.blackjack.rules.BJRules;

/**
 * Created by marcojacques on 2015-03-19.
 *
 * Implementation of the BJ settings
 */
public class BJSettingsImpl
        implements BJSettings
{
    private final BJPossibleMoves possibleMoves;
    private final BJRules rules;

    public BJSettingsImpl(BJPossibleMoves possibleMoves, BJRules rules)
    {
        this.possibleMoves = possibleMoves;
        this.rules = rules;
    }

    @Override
    public BJPossibleMoves getPossibleMovesComputer()
    {
        return possibleMoves;
    }

    @Override
    public BJRules getRules()
    {
        return rules;
    }
}
