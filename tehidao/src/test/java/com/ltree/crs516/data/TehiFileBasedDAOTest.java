package com.ltree.crs516.data;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import com.ltree.crs516.controller.TehiGame;

public class TehiFileBasedDAOTest extends AbstractTehiDAOTest {

	@Override
	TehiDAO getTehiDAO() {
		return new TehiFileBasedDAO();
	}
	
	@Test
	public void testSaveAndGetState() {
		for (TehiGame.StateName stateName : TehiGame.StateName.values()) {
			((TehiFileBasedDAO)subject).saveState(stateName);

			assertEquals(stateName, ((TehiFileBasedDAO)subject).loadState());

		}

	}

	@Test
	public void testSaveAndGetDeck() {
		((TehiFileBasedDAO)subject).saveDeck(deck);

		assertEquals(deck, ((TehiFileBasedDAO)subject).loadDeck());

	}

	@Test
	public void testSaveAndGetSystemHand() {
		((TehiFileBasedDAO)subject).saveSystemHand(hand);
		assertEquals(hand, ((TehiFileBasedDAO)subject).loadSystemHand(game));
	}

	@Test
	public void testSaveAndGetPlayerHand() {
		((TehiFileBasedDAO)subject).savePlayerHand(hand);
		assertEquals(hand, ((TehiFileBasedDAO)subject).loadPlayerHand(game));
	}
	
	private Random random = new Random();

	@Test
	public void testSaveAndGetScores() {
		int playerScore = random.nextInt();
		int systemScore = random.nextInt();
		((TehiFileBasedDAO)subject).saveScores(playerScore, systemScore);

		int[] scores = ((TehiFileBasedDAO)subject).loadScores();
		assertEquals(playerScore, scores[0]);
		assertEquals(systemScore, scores[1]);

	}


}
