package org.openRealmOfStars.game;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Key Event Dispatcher for the game
 * 
 */
public class GameKeyAdapter implements KeyEventDispatcher {

  
  
  @Override
  public boolean dispatchKeyEvent(KeyEvent arg0) {
    if (game.gameState == GameState.STARMAP && arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
      game.changeGameState(GameState.MAIN_MENU);
      return true;
    }
    return false;
  }

  
  
  /**
   * Reference to the game
   */
  private Game game;
  
  public GameKeyAdapter(Game game) {
    super();
    this.game = game;
  }
}
