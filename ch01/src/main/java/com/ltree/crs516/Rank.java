package com.ltree.crs516;

import java.util.Random;

public enum Rank {
	ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), 
	SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), 
	JACK("J"), QUEEN("Q"), KING("K");

	static final int MAX = KING.ordinal() + 1;
	private final String name;

	public String getName() {
		return name;
	}

	public int getRankNumber() {
		return ordinal() + 1;
	}

	/** Construct a Rank object.
	 * Enum constructors should always be private!
	 * @param name
	 */
	private Rank(String name) {
		this.name = name;
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

	public String toString() {
		return name;
	}
}
