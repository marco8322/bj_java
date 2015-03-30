package org.mj.blackjack.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by marcojacques on 2015-03-25.
 *
 * Implementation of multiple card decks
 */
public class BJMultipleCardDecks
    implements BJCardDeck
{
    private static int values[] = {2,3,4,5,6,7,8,9,10,10,10,10,11};
    private final List<BJCard> cards;
    private int idxCard;
    private final Random random;

    public BJMultipleCardDecks(int nbDecks)
    {
        random = new Random();
        idxCard = 0;
        cards = new ArrayList<BJCard>(4 * nbDecks * values.length);

        for( int i = 0; i < 4 * nbDecks; ++i )
        {
            for( int v : values )
            {
                cards.add(new BJCardImpl(v));
            }
        }
    }

    public void shuffle()
    {
        Collections.shuffle(cards, random);
        Collections.shuffle(cards, random);
        Collections.shuffle(cards, random);

        idxCard = 0;
    }

    @Override
    public BJCard nextCard()
    {
        return cards.get(idxCard++);
    }
}
