package org.mj.blackjack.main;

import org.mj.blackjack.card.BJKnownFirstCardsCardDeck;
import org.mj.blackjack.moves.BJMove;

import javax.xml.bind.annotation.*;

/**
 * Created by marcojacques on 2015-04-14.
 *
 * XML class for BJ arguments
 */
@XmlRootElement(name = "bj_args")
@XmlAccessorType(value = XmlAccessType.NONE)
public class BJMainArgs
{
    @XmlElement(name = "playerHand")
    public int playerHand;

    @XmlElement(name = "kind")
    public BJKnownFirstCardsCardDeck.Kind kind;

    @XmlElement(name = "nextMove")
    public BJMove nextMove;

    @XmlElement(name = "dealerCard")
    public int dealerCard;

    @XmlElement(name = "numberDecks")
    public int numberDecks;

    @XmlElement(name = "hitSoft17")
    public boolean hitSoft17;

    public BJMainArgs()
    {
        // Assign default values
        //
        playerHand = 19;
        kind = BJKnownFirstCardsCardDeck.Kind.Normal;
        nextMove = BJMove.STAY;
        dealerCard = 10;
        numberDecks = 6;
        hitSoft17 = false;
    }
}
