package org.mj.blackjack.moves;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.hand.BJHand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by marcojacques on 2015-03-29.
 *
 * Implementation which just takes a random choice for the next move, without any logic
 */
public class BJRandomNextMove
        implements BJNextMove
{
    private Random random;

    public BJRandomNextMove()
    {
        random = new Random();
    }

    @Override
    public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
    {
        List<BJMove> allMoves = new ArrayList<BJMove>(possibleMoves);

        return allMoves.get(random.nextInt(possibleMoves.size()));
    }
}
