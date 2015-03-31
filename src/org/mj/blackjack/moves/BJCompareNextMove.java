package org.mj.blackjack.moves;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.hand.BJHand;

import java.util.Collection;

/**
 * Created by marcojacques on 2015-03-30.
 *
 * Next move when comparing moves.  This takes the first move
 */
public class BJCompareNextMove
    implements BJNextMove
{
    private final BJMove firstMove;
    private boolean firstMoveTaken;
    BJNextMove nextMove = new BJBasicNextMove();

    public BJCompareNextMove(BJMove firstMove)
    {
        this.firstMove = firstMove;
        firstMoveTaken = false;
    }

    @Override
    public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
    {
        if( !firstMoveTaken )
        {
            assert possibleMoves.contains(firstMove);

            firstMoveTaken = true;
            return firstMove;

        }
        return nextMove.getNextMove(playerHand, dealerFaceCard, possibleMoves);
    }

    public void reset()
    {
        firstMoveTaken = false;
    }
}
