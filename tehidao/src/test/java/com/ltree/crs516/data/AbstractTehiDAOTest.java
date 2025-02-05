package com.ltree.crs516.data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.ltree.crs516.controller.TehiGame;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public abstract class AbstractTehiDAOTest {

	protected TehiDAO subject;
	protected TehiGame game;
	protected TehiHand hand;
	protected CardDeck deck;
	
	abstract TehiDAO getTehiDAO();

	@Before
	public void setUp() throws Exception {
		subject = getTehiDAO();
		game = new TehiGame();
		hand = new TehiHand(game);
		deck = new CardDeck();
		deck.shuffle();
		for (int i=0; i<5;i++) {
			hand.add(deck.deal());
		}
	}

	@Test
	public void testSaveThenLoad() {
		subject.saveGame(game);
		TehiGame newGame = new TehiGame();
		subject.loadGame(newGame);
		assertTrue(game.getState().getStateName() == newGame.getState().getStateName());
		assertEquals(game.getDeck(), newGame.getDeck());
	}
}
