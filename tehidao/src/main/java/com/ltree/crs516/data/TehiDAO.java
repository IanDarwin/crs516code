package com.ltree.crs516.data;

import com.ltree.crs516.controller.TehiGame;

public interface TehiDAO {

	// Preferred API
	void saveGame(TehiGame dto);
	void loadGame(TehiGame game);
	
}
