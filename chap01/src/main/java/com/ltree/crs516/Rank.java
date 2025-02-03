package com.ltree.crs516;

import java.util.Random;

public enum Rank {
	ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), 
	SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10), 
	JACK("J", 11), QUEEN("Q", 12), KING("K", 13);

	final static int MAX = KING.ordinal() + 1;

	private String name;
	private int rankNumber;
	private static Random random = new Random();

	public String getName() {
		return name;
	}

	public int getRankNumber() {
		return rankNumber;
	}

	private Rank(String name, int rankNumber) {
		this.name = name;
		this.rankNumber = rankNumber;
	}

	/**
	 * Converts an int to a Rank. Name shortened
	 * 
	 * @param rankInt The 1-origin rank (ACE=1, KING=13, etc)
	 * @return The corresponding Rank object.
	 */
	public static Rank intToRank(int rankInt) {
		if (rankInt < 1 || rankInt > MAX) {
			throw new IllegalArgumentException(
					String.format("convertIntToRank: %d must be in 1..%d", rankInt, MAX));
		}
		return values()[rankInt - 1];
	}

	/**
	 * Randomly selects a rank.
	 * @return
	 */
	public static Rank randomRank() {
	    return values()[random.nextInt(MAX)];
	}

	public String toString() {
		return name;
	}
	
}
