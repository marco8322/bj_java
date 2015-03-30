package org.mj.blackjack.hand;

import org.mj.blackjack.card.BJCard;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marcojacques on 15-03-03.
 *
 * Implementation of a hand
 */
public class BJHandImpl
        implements BJHand
{
    private List<BJCard> cards;
    private int totalValue;
    private boolean softHand;
    private State state;

    public BJHandImpl()
    {
        this.cards = new LinkedList<BJCard>();
        this.totalValue = 0;
        this.softHand = false;
        this.state = State.MAY_HIT;
    }


    @Override
    public void addCard(BJCard card)
    {
        if( state != State.MAY_HIT )
        {
            throw new IllegalStateException("Cannot add new card... must have MAY_HIT status");
        }

        int cardValue = card.getValue();
        cards.add(card);

        if( cardValue == 11 )
        {
            if( !softHand && totalValue < 11 )
            {
                totalValue += 11;
                softHand = true;
            }
            else
            {
                totalValue += 1;
            }
        }
        else
        {
            totalValue += cardValue;
        }

        if( softHand && totalValue > 21 )
        {
            softHand = false;
            totalValue -= 10;
        }

        // Change the state...
        //
        // NOTE: for == 21, we do not change the state, as in some games, we might allow to play further
        //
        if( totalValue > 21 )
        {
            state = State.BUSTED;
        }
    }

    @Override
    public int getTotalValue()
    {
        return totalValue;
    }

    @Override
    public boolean isSoftHand()
    {
        return softHand;
    }

    @Override
    public boolean mayBeSplit()
    {
        return cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue() &&
                state == State.MAY_HIT;
    }

    @Override
    public BJHand splitHand()
    {
        if(!mayBeSplit())
        {
            throw new IllegalStateException("Cannot split hand");
        }

        BJHand newHand = new BJHandImpl();
        newHand.addCard(cards.get(1));

        cards.remove(1);
        totalValue -= cards.get(0).getValue();

        if( totalValue == 1 )
        {
            totalValue += 10;
            softHand = true;
        }

        return newHand;
    }

    @Override
    public void setState(State state)
    {
        assert state != null;

        if( this.state != State.MAY_HIT )
        {
            throw new IllegalStateException("Cannot change state");
        }

        this.state = state;
    }

    @Override
    public State getState()
    {
        return state;
    }

    @Override
    public int getNbCards()
    {
        return cards.size();
    }

    @Override
    public BJCard getCard(int i)
    {
        return cards.get(i);
    }

    public String debugString()
    {
        String result = "Total: " + getTotalValue();
        for( int i = 0; i < getNbCards(); ++i )
        {
            result += " ," + getCard(i).getValue();
        }

        return result;
    }
}
