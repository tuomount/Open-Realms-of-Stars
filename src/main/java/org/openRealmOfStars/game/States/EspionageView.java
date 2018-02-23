package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.player.PlayerInfo;


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
* Espionage view for spying and espionage
*
*/
public class EspionageView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Player info
   */
  private PlayerInfo player;

  /**
   * Espionage View constructor
   * @param info PlayerInfo who is doing the spying
   * @param listener Action Listener
   */
  public EspionageView(final PlayerInfo info,  final ActionListener listener) {
    player = info;
    this.setLayout(new BorderLayout());

    InfoPanel topPanel = new InfoPanel();
    topPanel.setTitle("Faking the military size for GNBC");

    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setTitle("Espionage");

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
    this.add(centerPanel, BorderLayout.CENTER);
    if (topPanel != null) {
      this.add(topPanel, BorderLayout.NORTH);
    }
  }

  /**
   * Get Player Info
   * @return PlayerInfo given in constructor
   */
  public PlayerInfo getPlayer() {
    return player;
  }
}
