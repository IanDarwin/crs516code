package com.ltree.crs516;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RankTest {

	@Test
	public void testRankToIntGood() {
		assertEquals(Rank.ACE, Rank.intToRank(1));
		assertEquals(Rank.JACK, Rank.intToRank(11));
		assertEquals(Rank.KING, Rank.intToRank(13));
	}

	@Test
	public void testRankToIntBad() {
		assertThrows(IllegalArgumentException.class, () -> Rank.intToRank(42));
	}

	@Test
	public void testRankToIntNotZeroOrigin() {
		assertThrows(IllegalArgumentException.class, () -> Rank.intToRank(0));
	}
}
