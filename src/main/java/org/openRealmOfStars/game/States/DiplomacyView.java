package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Diplomacy View for between two players: Human and AI
*
*/
public class DiplomacyView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;
  /**
   * Player Info for human
   */
  private PlayerInfo human;
  /**
   * Player Info for AI
   */
  private PlayerInfo ai;

  /**
   * Star Map containing important data for diplomacy
   */
  private StarMap starMap;

  /**
   * Diplomacy View constructor
   * @param info1 Human player PlayerInfo
   * @param info2 AI player PlayerInfo
   * @param map StarMap
   * @param listener ActionListener
   */
  public DiplomacyView(final PlayerInfo info1, final PlayerInfo info2,
      final StarMap map, final ActionListener listener) {
    human = info1;
    ai = info2;
    starMap = map;
    RaceImagePanel aiImg = new RaceImagePanel();
    aiImg.setRaceToShow(ai.getRace().getNameSingle());
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Get human playerinfo
   * @return Human playerinfo
   */
  public PlayerInfo getHuman() {
    return human;
  }

  /**
   * Get starmap
   * @return StarMap
   */
  public StarMap getStarMap() {
    return starMap;
  }
}
