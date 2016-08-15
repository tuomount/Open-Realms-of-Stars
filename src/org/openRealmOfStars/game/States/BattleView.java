package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;

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
 * Battle view for handling space combat
 * 
 */
public class BattleView extends BlackPanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Battle view for space combat
   * @param fleet1 First fleet in combat
   * @param player1 First player in combat
   * @param fleet2 Second fleet in combat
   * @param player2 Second player in combat
   * @param listener ActionListener
   */
  public BattleView(Fleet fleet1, PlayerInfo player1, Fleet fleet2,
      PlayerInfo player2, ActionListener listener) {
    BlackPanel base = new BlackPanel();
    this.setLayout(new BorderLayout());
    this.add(base,BorderLayout.CENTER);

  }

  
  /**
   * Update panels on BattleView
   */
  public void updatePanels() {
  }

  /**
   * Handle actions for battle view
   * @param arg0 Active Event
   */
  public void handleActions(ActionEvent arg0) {

  }

}
