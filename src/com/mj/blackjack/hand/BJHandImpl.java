package com.mj.blackjack.hand;

import com.mj.blackjack.card.BJCard;
import com.mj.blackjack.player.BJPlayer;

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
    private final BJPlayer player;
    private List<BJCard> cards;
    private int totalValue;
    private boolean softHand;
    private State state;

    public BJHandImpl(BJPlayer player)
    {
        assert player != null;

        this.player = player;
        this.cards = new LinkedList<BJCard>();
        this.totalValue = 0;
        this.softHand = false;
        this.state = State.MAY_HIT;
    }

    @Override
    public BJPlayer getPlayer()
    {
        return player;
    }

    @Override
    public void addCard(BJCard card)
    {
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
        if( totalValue == 21 )
        {
            state = State.STAY;
        }
        else if( totalValue > 21 )
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
        return cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue();
    }

    @Override
    public BJHand splitHand()
    {
        assert mayBeSplit();

        BJHand newHand = new BJHandImpl(this.player);
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
}
