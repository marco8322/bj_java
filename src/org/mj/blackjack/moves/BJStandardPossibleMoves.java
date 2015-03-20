package org.mj.blackjack.moves;

import org.mj.blackjack.hand.BJHand;
import org.mj.blackjack.rules.BJRules;

import java.util.*;

/**
 * Created by marcojacques on 2015-03-18.
 *
 * Standard implementation for the possible moves
 */
public class BJStandardPossibleMoves
        implements BJPossibleMoves
{
    @Override
    public Collection<BJMove> getPossibleMoves(BJHand currentHand, int numberOfSplits, BJRules rules)
    {
        // Usually, hit and stay are possible
        //
        List<BJMove> possibleMoves = new LinkedList<BJMove>();
        possibleMoves.add(BJMove.HIT);
        possibleMoves.add(BJMove.STAY);

        int nbCards = currentHand.getNbCards();
        if( nbCards == 2 )
        {
            // Check if surrender is possible.  If allowed and we did not split already.
            //
            if( rules.surrenderAllowed() && numberOfSplits == 0)
            {
                possibleMoves.add(BJMove.SURRENDER);
            }

            if( rules.splitAllowed() && currentHand.mayBeSplit() && numberOfSplits < rules.maxNumberSplits() )
            {
                possibleMoves.add(BJMove.SPLIT);
            }
        }

        // Check for double
        //
        if( rules.isDoubleAllowed(currentHand) && ( numberOfSplits == 0 || rules.doubleAllowedAfterSplit() ) )
        {
            possibleMoves.add(BJMove.DOUBLE);
        }

        return possibleMoves;
    }
}
