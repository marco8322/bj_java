package org.mj.blackjack.moves;

import org.mj.blackjack.card.BJCard;
import org.mj.blackjack.hand.BJHand;

import java.util.Collection;

/**
 * Created by marcojacques on 2015-03-29.
 *
 * Basic next move.
 *
 * Anything over 17, we stay
 * Split 8 and aces
 * Always double 11
 * Double 10 only with dealer card less than 9
 * For card with 7 to 11, anything 16 and under, we hit, otherwise, we stay
 */
public class BJBasicNextMove
        implements BJNextMove
{
    @Override
    public BJMove getNextMove(BJHand playerHand, BJCard dealerFaceCard, Collection<? extends BJMove> possibleMoves)
    {
        int dealerValue = dealerFaceCard.getValue();

        // Splits...
        //
        if( playerHand.mayBeSplit() && possibleMoves.contains(BJMove.SPLIT) )
        {
            switch( playerHand.getCard(0).getValue() )
            {
                case 8:
                case 11:
                    return BJMove.SPLIT;

                case 2:
                case 3:
                case 7:
                    if( dealerValue <= 7 ) return BJMove.SPLIT;
                    break;

                case 6:
                    if( dealerValue <= 6 ) return BJMove.SPLIT;
                    break;

                case 4:
                    if( dealerValue == 5 || dealerValue == 6 ) return BJMove.SPLIT;
                    break;

                case 9:
                    if( dealerValue < 7 || dealerValue == 8 || dealerValue == 9 )
                    {
                        return BJMove.SPLIT;
                    }
            }
        }

        // Soft value
        //
        int totalValue = playerHand.getTotalValue();
        if( playerHand.isSoftHand() )
        {
            switch( totalValue )
            {
                case 12:
                    return BJMove.HIT;

                case 13:
                case 14:
                    if( ( dealerValue == 5 || dealerValue == 6) && possibleMoves.contains(BJMove.DOUBLE) )
                    {
                        return BJMove.DOUBLE;
                    }
                    else
                    {
                        return BJMove.HIT;
                    }

                case 15:
                case 16:
                    if( ( dealerValue >= 4 && dealerValue <= 6)  && possibleMoves.contains(BJMove.DOUBLE) )
                    {
                        return BJMove.DOUBLE;
                    }
                    else
                    {
                        return BJMove.HIT;
                    }

                case 17:
                    if( ( dealerValue >= 3 && dealerValue <= 6) && possibleMoves.contains(BJMove.DOUBLE) )
                    {
                        return BJMove.DOUBLE;
                    }
                    else
                    {
                        return BJMove.HIT;
                    }

                case 18:
                    if( ( dealerValue >= 3 && dealerValue <= 6) && possibleMoves.contains(BJMove.DOUBLE) )
                    {
                        return BJMove.DOUBLE;
                    }
                    else if( dealerValue > 9 )
                    {
                        return BJMove.HIT;
                    }
                    else
                    {
                        return BJMove.STAY;
                    }
            }
        }

        // Other cases for double 9,10,11
        //
        if( possibleMoves.contains(BJMove.DOUBLE) )
        {
            switch( totalValue )
            {
                case 9:
                    if( dealerValue >= 3 && dealerValue <= 6 )
                    {
                        return BJMove.DOUBLE;
                    }
                    break;

                case 10:
                    if( dealerValue <= 9 )
                    {
                        return BJMove.DOUBLE;
                    }
                    break;

                case 11:
                    if( dealerValue <= 10 )
                    {
                        return BJMove.DOUBLE;
                    }
                    break;
            }
        }

        // 12/13 exceptions
        //
        if( totalValue == 12 && ( dealerValue == 2 || dealerValue == 3 ) )
        {
            return BJMove.HIT;
        }

        if( totalValue <= 11 || ( dealerValue >= 7 && (totalValue <= 16)) )
        {
            return BJMove.HIT;
        }

        return BJMove.STAY;
    }
}
