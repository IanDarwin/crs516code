package com.ltree.crs516.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;

import com.ltree.crs516.data.TehiDAO;
import com.ltree.crs516.data.TehiSerializationDao;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public final class TehiGame extends Observable implements Serializable {

	private static final long serialVersionUID = -3144204334570162767L;
	private int cumulativePlayerScore;
	private int cumulativeSystemScore;
	private CardDeck deck = new CardDeck();
	private boolean gameOver;
	private TehiHand playerHand;
	private GameState state;
	private TehiHand systemHand;
	private transient final GameState INIT = new Initial(); 
	private transient  final GameState DEAL = new Deal();
	private transient  final GameState STAND_OR_SWAP = new StandOrSwap();
	private transient  final GameState END = new EndGame();

	//TODO 1: create an instance of TehiFileBasedDAO and store the 
	//reference in the dao field below.
	private transient TehiDAO dao = new TehiSerializationDao();

	public TehiGame() {
		deck.shuffle();
		playerHand = new TehiHand(TehiGame.this);
		systemHand = new TehiHand(TehiGame.this);
		changeState(StateName.INIT);
	}

	public void save() {
		
		dao.saveGame(this);
	}

	public void load() throws IOException {

		dao.loadGame(this);
		
		changeState(getState().getStateName(), false);
	}

	/**
	 * Rather than use an abstract class we shall use an interface with
	 * default methods. Implementing classes are then free to extend another 
	 * class.
	 * @author crs516 Development team.
	 *
	 */
	public interface GameState {

		StateName getStateName();

		default String getDealButtonText() {
			return "Deal";
		}

		String getGamePaneText();

		default boolean isDealButtonEnabled() {
			return false;
		}

		default boolean isPlayerPanelActive() {
			return false;
		}

		default boolean isStandButtonEnabled() {
			return false;
		}

		default boolean isSystemPanelActive() {
			return false;
		}

		default boolean isSystemPanelVisible() {
			return false;
		}

		void transition();

	}


	/**
	 * Represents the Deal state.
	 * @author crs516 development team.
	 *
	 */
	class Deal implements GameState, Serializable {

		@Override
		public StateName getStateName() {
			return StateName.DEAL;
		}

		@Override
		public String getGamePaneText() {
			if (getDeck().isEmpty()) {
				return "</p>"
						+ "The deck is empty so you have to 'Stand' button to stand on the hand you have.";
			}
			return "</p>"
			+ "Click on a card to swap or click the 'Stand' button to stand on the hand you have.";
		}

		@Override
		public boolean isPlayerPanelActive() {
			return true;
		}

		@Override
		public boolean isStandButtonEnabled() {
			return true;
		}

		@Override
		public void transition() {
			if (gameOver) {
				changeState(StateName.END);
			} else if (deck.size() < 10) {
				// not enough cards
				changeState(StateName.END);
			} else {
				playerHand = new TehiHand(TehiGame.this);
				systemHand = new TehiHand(TehiGame.this);
			}
		}
	}


	/**
	 * Represents the EndGame state.
	 * @author crs516 development team.
	 *
	 */
	class EndGame implements GameState, Serializable {

		@Override
		public StateName getStateName() {
			return StateName.END;
		}

		@Override
		public String getDealButtonText() {
			return "Game Over";
		}

		@Override
		public String getGamePaneText() {
			StringBuilder builder = new StringBuilder("<html><ul>");
			builder.append("<li>Your cumulative score: "
					+ cumulativePlayerScore + "</li>");
			builder.append("<li>System's cumulative score: "
					+ cumulativeSystemScore + "</li></ul>");
			if (cumulativePlayerScore > cumulativeSystemScore) {
				builder.append("<center><h2>You won the game!</h2></center>");
			} else {
				builder.append("<center><h2>The system won the game!</h2></center>");
			}
			return builder.toString();
		}

		@Override
		public boolean isDealButtonEnabled() {
			return false;
		}

		@Override
		public boolean isSystemPanelVisible() {
			return true;
		}

		@Override
		public void transition() {
			gameOver = true;
		}
	}


	/**
	 * Represents the Initial state.
	 * @author crs516 development team.
	 *
	 */
	class Initial implements GameState, Serializable {

		@Override
		public StateName getStateName() {
			return StateName.INIT;
		}

		@Override
		public String getGamePaneText() {
			return "<center><h2>Welcome to Tehi!</h2></center>"
					+ "Essential Rules:<br/>"
					+ "Hands of five playing cards are dealt to both you and the system. "
					+ "Hand values are determined by multiplying the number of \"eyes\" on "
					+ "the face cards by the total rank values of the non-face cards. You stand on "
					+ "the hand as dealt or draw (swap) one card by clicking on it.";
		}

		@Override
		public boolean isPlayerPanelActive() {
			return true;
		}

		@Override
		public boolean isStandButtonEnabled() {
			return true;
		}

		@Override
		public boolean isSystemPanelActive() {
			return false;
		}

		@Override
		public void transition() {
			deck = new CardDeck();
			deck.shuffle();
			gameOver = false;
		}

	}

	/**
	 * Represents the States Stand and Swap
	 * (the logic is identical).
	 * @author crs516 development team.
	 *
	 */
	class StandOrSwap implements GameState, Serializable {

		private StateName stateName;

		@Override
		public StateName getStateName() {
			return stateName;
		}

		void setStateName(StateName name) {
			stateName = name;
		}

		@Override
		public String getGamePaneText() {
			int playerScore = playerHand.getScore();
			int systemScore = systemHand.getScore();
			cumulativePlayerScore += playerScore;
			cumulativeSystemScore += systemScore;
			StringBuilder builder = new StringBuilder("<html><ul>");
			builder.append("<li>Your score: " + playerScore + "</li>");
			builder.append("<li>System's score: " + systemScore + "</li></ul>");
			if (playerScore > systemScore) {
				builder.append("<center><h2>You won this round!</h2></center>");
			} else {
				builder.append("<p>The system won this round!</p>");
			}
			builder.append("<ul><li>Your cumulative score: "
					+ cumulativePlayerScore + "</li>");
			builder.append("<li>System's cumulative score: "
					+ cumulativeSystemScore + "</li></ul>");
			if (deck.size() > 9) {
				builder.append("<p>Click the 'Deal' button for a new round!");
			}
			return builder.toString();
		}

		@Override
		public boolean isDealButtonEnabled() {
			return true;
		}

		@Override
		public boolean isSystemPanelVisible() {
			return true;
		}

		@Override
		public void transition() {
			if (deck.size() < 10) {
				changeState(StateName.END);
			}
		}
	}

	/**
	 * Names of states. Note that Swap and Stand are distinct even though they 
	 * share a state class type.
	 * @author crs516 development team.
	 *
	 */
	public enum StateName {
		DEAL, END, INIT, STAND, SWAP
	}

	public void changeState(StateName stateName) {
		changeState(stateName, true);
	}

	public void changeState(StateName stateName, boolean doTransition) {
		switch (stateName) {
		case INIT:
			state =INIT;
			break;
		case DEAL:
			state = DEAL;
			break;
		case STAND:
		case SWAP:
			state = STAND_OR_SWAP;
			((StandOrSwap) state).setStateName(stateName);
			break;
		case END:
			state = END;
			break;
		default:
			throw new IllegalArgumentException("No such state, " + stateName);
		}
		if(doTransition){
			state.transition();
		}
		setChanged();
		notifyObservers();
	}

	public String getDealButtonText() {
		return state.getDealButtonText();
	}

	public CardDeck getDeck() {
		return deck;
	}

	public String getGamePaneText() {
		return state.getGamePaneText();
	}

	public TehiHand getPlayerHand() {
		return playerHand;
	}

	public GameState getState() {
		return state;
	}

	public TehiHand getSystemHand() {
		return systemHand;
	}

	public boolean isDealButtonEnabled() {
		return state.isDealButtonEnabled();
	}

	public boolean isPlayerPanelActive() {
		return state.isPlayerPanelActive();
	}

	public boolean isStandButtonEnabled() {
		return state.isStandButtonEnabled();
	}

	public boolean isSystemPanelActive() {
		return state.isSystemPanelActive();
	}

	public boolean isSystemPanelVisible() {
		return state.isSystemPanelVisible();
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}

	public void setPlayerHand(TehiHand playerHand) {
		this.playerHand = playerHand;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void setSystemHand(TehiHand systemHand) {
		this.systemHand = systemHand;
	}

	public int getCumulativeSystemScore() {
		return cumulativeSystemScore;
	}
	public void setCumulativeSystemScore(int cumulativeSytemScore) {
		this.cumulativeSystemScore = cumulativeSytemScore;
	}

	public int getCumulativePlayerScore() {
		return cumulativePlayerScore;
	}

	public void setCumulativePlayerScore(int cumulativePlayerScore) {
		this.cumulativePlayerScore = cumulativePlayerScore;
	}
}
