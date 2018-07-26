package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;


import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Fleet trade view for handling trading for human player
*
*/
public class FleetTradeView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Planet to show, if any
   */
  private Planet planet;
  /**
   * Fleet to show
   */
  private Fleet fleet;

  /**
   * PlayerInfo
   */
  private PlayerInfo info;

  /**
   * Background image
   */
  private BigImagePanel imgBase;

  /**
   * Starmap, needed for deploying the starbase
   */
  private StarMap starMap;

  /**
   * Fleet Trade view constructor
   * @param map StarMap
   * @param info PlayerInfo who is about to trade
   * @param planet Origin planet of trade route
   * @param fleet Trade Fleet
   * @param listener ActionListener
   */
  public FleetTradeView(final StarMap map, final PlayerInfo info,
      final Planet planet, final Fleet fleet,
      final ActionListener listener) {
    this.starMap = map;
    this.planet = planet;
    this.info = info;
    this.fleet = fleet;

    // Background image
    imgBase = new BigImagePanel(planet, true, null);
    this.setLayout(new BorderLayout());

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
    this.add(imgBase, BorderLayout.CENTER);
  }

  /**
   * Get StarMap
   * @return StarMap
   */
  public StarMap getMap() {
    return starMap;
  }
  /**
   * Get Planet
   * @return Planet
   */
  public Planet getPlanet() {
    return planet;
  }

  /**
   * Get Fleet
   * @return Fleet
   */
  public Fleet getFleet() {
    return fleet;
  }

  /**
   * Get Player info
   * @return PlayerInfo
   */
  public PlayerInfo getPlayerInfo() {
    return info;
  }
}
