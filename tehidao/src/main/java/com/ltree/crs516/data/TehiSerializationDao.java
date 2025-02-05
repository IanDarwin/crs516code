package com.ltree.crs516.data;

import com.ltree.crs516.controller.TehiGame;

import java.io.*;

///
/// DAO to save and reload game
///
/// Fields are:
/// game.getState();
/// game.getDeck());
/// game.getPlayerHand();
/// game.getSystemHand();
/// game.getCumulativePlayerScore();
/// game.getCumulativeSystemScore();
///
/// Too bad the game wasn't originally designed with a single object
/// that could be saved/loaded.
///
public class TehiSerializationDao implements TehiDAO {

    final static String SAVE_FILE_NAME = "tehigame.save";

    @Override
    public void saveGame(TehiGame game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_NAME))){
            oos.writeObject(game.getState());
            // TODO Fill in the rest
        } catch (IOException e) {
            throw new RuntimeException("I/O Error Saving Game to " + SAVE_FILE_NAME, e);
        }
    }

    @Override
    public void loadGame(TehiGame game) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_NAME))){
            game.setState((TehiGame.GameState) ois.readObject());
            // TODO Fill in the rest
        } catch (IOException e) {
            throw new RuntimeException("I/O Error Saving Game to " + SAVE_FILE_NAME, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Fatal Error: Could not find class!", e);
        }
    }
}
