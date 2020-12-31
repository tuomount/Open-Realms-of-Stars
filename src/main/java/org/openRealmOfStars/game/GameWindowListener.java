package org.openRealmOfStars.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.openRealmOfStars.ambient.BridgeCommandType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2020 Tuomo Untinen
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
 * Window adapter for handling window events
 *
 */
public class GameWindowListener extends WindowAdapter {

  /**
   * Game frame.
   */
  private Game game;
  /**
   * Constructor for GameWindowListener
   * @param game Game frame.
   */
  public GameWindowListener(final Game game) {
    this.game = game;
  }
  @Override
  public void windowClosing(final WindowEvent arg0) {
    game.setBridgeCommand(BridgeCommandType.WARM_WHITE);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // Nothing to do
    }
    System.exit(0);
  }

}
