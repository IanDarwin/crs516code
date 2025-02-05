package com.ltree.crs516.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import com.ltree.crs516.controller.TehiGame;
import com.ltree.crs516.controller.TehiGame.StateName;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public class TehiFileBasedDAO implements TehiDAO {

	private final static String SAVE_DIR = "data";
	private final static String DELIM = File.separator;
	private final static String SAVED_DECK = SAVE_DIR + DELIM + "savedDeck";
	private final static String SAVED_STATE = SAVE_DIR + DELIM + "savedState";
	private final static String SAVED_PLAYER_HAND = SAVE_DIR + DELIM + "savedPlayerHand";
	private final static String SAVED_SYSTEM_HAND = SAVE_DIR + DELIM + "savedSystemHand";
	private final static String SAVED_SCORES = SAVE_DIR + DELIM + "savedScores";

	@Override
	public void saveGame(TehiGame game) {
		new File(SAVE_DIR).mkdirs(); // directory must exist
		saveDeck(game.getDeck());
		savePlayerHand(game.getPlayerHand());
		saveSystemHand(game.getSystemHand());
		saveScores(game.getCumulativePlayerScore(),
			game.getCumulativeSystemScore());
	}

	@Override
	public void loadGame(TehiGame game) {
		int scores[] = loadScores();
		game.setDeck(loadDeck());
		// game handles setState() internally
		game.setPlayerHand(loadPlayerHand(game));
		game.setSystemHand(loadSystemHand(game));
		game.setCumulativePlayerScore(scores[0]);
		game.setCumulativeSystemScore(scores[1]);
	}

	public StateName loadState() {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				SAVED_STATE));) {
			String stateAsString = reader.readLine();
			return TehiGame.StateName.valueOf(stateAsString);
		} catch (IOException e) {
			throw new DataException("Didn't read: " + e, e);
		}
	}
	
	public void saveState(StateName stateName) {
		try (PrintWriter writer = new PrintWriter(new File(SAVED_STATE));) {
			writer.print(stateName.name());
		} catch (FileNotFoundException e) {
			throw new DataException("Didn't save: " + e, e);
		}
	}

	public void saveDeck(CardDeck deck) {
//TODO 1: Get an  ObjectOutputStream that writes to the file called SAVED_DECK.
//and write the deck to that file. CardDeck is serializable. If you don't 
//remember how to write to an ObjectOutputStream take a look at the other save methods below.

		try (ObjectOutputStream os  = new ObjectOutputStream(new FileOutputStream(SAVED_DECK))) {
			os.writeObject(deck);
		} catch (IOException e) {
			throw new DataException("Didn't save: " + e, e);
		}
	}

	public CardDeck loadDeck() {
//TODO 2: Get an  ObjectInputStream chained to a FileInputStream that reads from the file SAVED_DECK. 
//Read the object stored in that file and cast it to CardDeck. 
//Call setImages() on the CardDeck to make the PlayingCards reload their images.
//Return a reference to the CardDeck.		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVED_DECK))) {
			return (CardDeck) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DataException("Didn't load: " + e, e);
		}
	}
			
	private void saveHand(TehiHand playerHand, File savedFile) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savedFile));){
			oos.writeObject(playerHand);
		} catch (IOException e) {
			throw new DataException("Didn't save: " + e, e);
		}
	}

	private TehiHand getHand(File savedFile, TehiGame game) {
		TehiHand hand = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedFile));) {
			hand = (TehiHand)ois.readObject();			
		} catch (IOException | ClassNotFoundException e) {
			throw new DataException("Didn't load: " + e, e);
		}
		hand.setImages();
		hand.setGame(game);
		return hand;
	}

	public void saveSystemHand(TehiHand systemHand) {
		saveHand(systemHand, new File(SAVED_SYSTEM_HAND));
	}

	public TehiHand loadSystemHand(TehiGame game) {
		return getHand(new File(SAVED_SYSTEM_HAND), game);
	}

	public void savePlayerHand(TehiHand playerHand) {
		saveHand(playerHand, new File(SAVED_PLAYER_HAND));
	}

	public TehiHand loadPlayerHand(TehiGame game) {
		return getHand(new File(SAVED_PLAYER_HAND), game);
	}

	public void saveScores(int cumulativePlayerScore, int cumulativeSytemScore)  {
		try (PrintWriter writer = new PrintWriter(SAVED_SCORES);) {
				writer.println(cumulativePlayerScore + ":"
						+ cumulativeSytemScore);
			} catch (FileNotFoundException e) {
				throw new DataException("Didn't save: " + e, e);
			}
	}

	public int[] loadScores() {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				SAVED_SCORES));) {
			String[] scoresAsStrings = reader.readLine().split(":");
			return new int[]{Integer.parseInt(scoresAsStrings[0]), Integer.parseInt(scoresAsStrings[1])};
		} catch (IOException e) {
			throw new DataException("Didn't save: " + e, e);
		}
	}
}
