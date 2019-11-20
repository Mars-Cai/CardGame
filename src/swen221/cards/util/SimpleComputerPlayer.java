package swen221.cards.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import swen221.cards.core.Card;
import swen221.cards.core.Card.Suit;
import swen221.cards.core.Hand;
import swen221.cards.core.Player;
import swen221.cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 *
 * @author David J. Pearce
 *
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1250898704328404314L;

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {
		Iterable<Card> PossibleCards; // potential cards
		Card FirstCard = null;
		Card NextCard = null;
		Card highestNow =null;
		if (trick.getCardsPlayed().isEmpty()) { // check if this is the first one
			PossibleCards = this.player.getHand(); // get the highest card of my hand
			NextCard = getTheHighestCard(PossibleCards, trick);
			// }
		} else if (trick.getCardsPlayed().size() == 3) {	//I am the last one
			FirstCard = trick.getCardsPlayed().get(0);
			highestNow = getTheHighestCard(trick.getCardsPlayed(), trick);	//get the highest card for now
			if (!this.player.getHand().matches(FirstCard.suit()).isEmpty()) {
				PossibleCards = this.player.hand.matches(FirstCard.suit());
				for (Card c : PossibleCards) { // I have same suit cards and I must win, just lay the smallest card to win
					if (getHigher(c,highestNow,trick).equals(c)) {
						NextCard = c;
						break;
					}
				}
				NextCard = getTheSmallestCard(PossibleCards, trick);// I cannot win
			}else {
				PossibleCards = this.player.getHand();
				NextCard = getTheHighestCard(PossibleCards, trick); // get my highest card of same cards
				if(getHigher(NextCard,highestNow,trick).equals(NextCard)) {
					for (Card c : PossibleCards) {
						if (getHigher(c,highestNow,trick).equals(c)){
							NextCard = c;
							break;
						}
					}
				}else {	// I cannot win
					NextCard = getTheSmallestCard(PossibleCards, trick);
				}
			}
		} else {// when I am not the first one
			FirstCard = trick.getCardsPlayed().get(0); // get the last card
			if (!this.player.hand.matches(FirstCard.suit()).isEmpty()) {// if I have same suit cards
				PossibleCards = this.player.hand.matches(FirstCard.suit());
				NextCard = getTheHighestCard(PossibleCards, trick); // get my highest card of same cards
				highestNow = getTheHighestCard(trick.getCardsPlayed(), trick);
				if(getHigher(NextCard,highestNow,trick).equals(highestNow)) {
					NextCard = getTheSmallestCard(PossibleCards, trick);
				}
			} else {// when I have no same suit cards
				PossibleCards = this.player.getHand(); // choose the highest card of my hand
				NextCard = getTheHighestCard(PossibleCards, trick);
				highestNow = getTheHighestCard(trick.getCardsPlayed(), trick);
				if(getHigher(NextCard,highestNow,trick).equals(highestNow)) {
					NextCard = getTheSmallestCard(PossibleCards, trick);
				}
			}
		}
		return NextCard;
	}

	public Card getTheHighestCard(Iterable<Card> s, Trick trick) { // check every card and get the highest one
		Iterator<Card> it = s.iterator();
		Card Highest = it.next();
		while (it.hasNext()) {
			Card next = it.next();
			Highest = getHigher(Highest, next, trick);
		}
		return Highest;
	}

	public Card getTheSmallestCard(Iterable<Card> s, Trick trick) {// check every card and get the smallest one
		Iterator<Card> it = s.iterator();
		Card Smallest = it.next();
		while (it.hasNext()) {
			Card next = it.next();
			Smallest = getSmaller(Smallest, next, trick);
		}
		return Smallest;
	}

	public Card getHigher(Card c1, Card c2, Trick trick) { // compare two cards and get bigger one
		int v1 = c1.hashCode();
		int v2 = c2.hashCode();
		if (trick.getTrumps() != null) { // if there is Trumps, it should be considered
			if (c1.suit().equals(trick.getTrumps())) {
				v1 += 2000;
			}
			if (c2.suit().equals(trick.getTrumps())) {
				v2 += 2000;
			}
		}
		if (v1 > v2)
			return c1;
		else
			return c2;
	}

	public Card getSmaller(Card c1, Card c2, Trick trick) { // compare two cards and get smaller one
		int v1 = c1.hashCode();
		int v2 = c2.hashCode();
		if (trick.getTrumps() != null) { // if there is Trumps, it should be considered
			if (c1.suit().equals(trick.getTrumps())) {
				v1 += 2000;
			}
			if (c2.suit().equals(trick.getTrumps())) {
				v2 += 2000;
			}
		}
		if (v1 < v2)
			return c1;
		else
			return c2;
	}
}
