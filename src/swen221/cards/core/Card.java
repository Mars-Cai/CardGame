package swen221.cards.core;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7748683434873751532L;

	/**
	 * Represents a card suit.
	 *
	 * @author David J. Pearce
	 *
	 */
	public enum Suit {
		HEARTS, CLUBS, DIAMONDS, SPADES;
	}

	/**
	 * Represents the different card "numbers".
	 *
	 * @author David J. Pearce
	 *
	 */
	public enum Rank {
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
	}

	// =======================================================
	// Card stuff
	// =======================================================

	private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
	private Rank rank; // 2 <= number <= 14 (ACE)

	/**
	 * Construct a card in the given suit, with a given number
	 *
	 * @param suit
	 *            --- between 0 (HEARTS) and 3 (SPADES)
	 * @param number
	 *            --- between 2 and 14 (ACE)
	 */
	public Card(Suit suit, Rank number) {
		this.suit = suit;
		this.rank = number;
	}

	/**
	 * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
	 *
	 * @return
	 */
	public Suit suit() {
		return suit;
	}

	/**
	 * Get the number of this card, between 2 and 14 (ACE).
	 *
	 * @return
	 */
	public Rank rank() {
		return rank;
	}

	private static String[] suits = { "Hearts", "Clubs", "Diamonds", "Spades" };
	private static String[] ranks = { "2 of ", "3 of ", "4 of ", "5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ",
			"Jack of ", "Queen of ", "King of ", "Ace of " };

	public String toString() {
		return ranks[rank.ordinal()] + suits[suit.ordinal()];
	}

	@Override
	public int compareTo(Card o) { // compare suit first and then rank
		int c1 = this.suit.ordinal() * 100;
		c1 = c1 + this.rank.ordinal() + 2;
		int c2 = o.suit.ordinal() * 100;
		c2 = c2 + o.rank.ordinal() + 2;
		if (c1 > c2)
			return 1;
		if (c1 < c2)
			return -1;
		else
			return 0;
	}

	public int hashCode() {	//compare rank first and then suit
		int S = this.rank.ordinal() * 100; // suit goes first any card in hearts is always less than than any card in
											// clubs
		return S + this.suit.ordinal(); // then add the rank
	}

	public boolean equals(Card o) {
		if (this.hashCode() == o.hashCode()) {
			return true;
		}
		return false;
	}
}
