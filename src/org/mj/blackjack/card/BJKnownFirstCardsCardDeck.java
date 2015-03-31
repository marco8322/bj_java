package org.mj.blackjack.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by marcojacques on 2015-03-30.
 *
 * Card deck with player and dealer known cards
 */
public class BJKnownFirstCardsCardDeck
    extends BJMultipleCardDecks
{
    private final List<Integer> possibleFirstCards;
    private final int dealerCard;
    private final int playerTotal;

    public enum Kind
    {
        Normal,
        Split,
        Soft
    }

    public BJKnownFirstCardsCardDeck(int playerTotal, Kind kind, int dealerCard, int numberDecks)
    {
        super(numberDecks);

        this.playerTotal = playerTotal;
        this.dealerCard = dealerCard;
        possibleFirstCards = new ArrayList<Integer>(13);

        switch( kind )
        {
            case Normal:
            {
                assert playerTotal >= 5 && playerTotal <= 19;
                int maxCard = (playerTotal > 11) ? 10 : playerTotal - 2;
                int minCard = (playerTotal < 13) ? 2 : 2 + (playerTotal - 12);
                for( int i = minCard; i <= maxCard; ++ i )
                {
                    if( playerTotal / 2 == i )
                    {
                        if( playerTotal % 2 == 0 )
                        {
                            continue;
                        }
                    }

                    possibleFirstCards.add(i);
                    if( i == 10 )
                    {
                        possibleFirstCards.add(i);
                        possibleFirstCards.add(i);
                        possibleFirstCards.add(i);
                    }
                }
                break;
            }

            case Split:
                //throw new IllegalStateException("Not implemented yet");
            {
                assert playerTotal >= 4 && playerTotal <= 22;
                assert playerTotal % 2 == 0;

                possibleFirstCards.add(playerTotal/2);
                break;
            }

            case Soft:
            {
                assert playerTotal >= 13 && playerTotal <= 20;
                possibleFirstCards.add(11);
                possibleFirstCards.add(playerTotal-11);
                break;
            }

            default:
                assert false;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void shuffle()
    {
        super.shuffle();

        int idx = this.cards.size();

        // Find first card of player
        //
        while(!possibleFirstCards.contains(cards.get(--idx).getValue()) );
        int firstCardValue = cards.get(idx).getValue();
        Collections.swap(cards, 0, idx);
        --idx;

        // Find dealer card
        //
        while(dealerCard != cards.get(--idx).getValue());
        Collections.swap(cards, 1, idx);
        --idx;

        // Find second card
        //
        int secondCard = playerTotal - firstCardValue;
        while(secondCard !=cards.get(--idx).getValue());
        Collections.swap(cards, 2, idx);
    }
}
