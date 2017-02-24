package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.StatisticPanel;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;

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
 * Statistical view for showing player stats
 *
 */

public class StatView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Create new stat view
   * @param listener Action Listener
   */
  public StatView(final ActionListener listener) {
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Statistics");

    StatisticPanel statPanel = new StatisticPanel();
    int[][] data = new int[8][3];
    data[0][0] = 1;
    data[0][1] = 2;
    data[0][2] = 4;
    data[1][0] = 3;
    data[1][1] = 2;
    data[1][2] = 5;
    data[2][0] = 5;
    data[2][1] = 6;
    data[2][2] = 9;
    data[3][0] = 7;
    data[3][1] = 4;
    data[3][2] = 1;
    data[4][0] = 2;
    data[4][1] = 4;
    data[4][2] = 8;
    data[5][0] = 9;
    data[5][1] = 6;
    data[5][2] = 3;
    data[6][0] = 11;
    data[6][1] = 10;
    data[6][2] = 11;
    data[7][0] = 4;
    data[7][1] = 8;
    data[7][2] = 64;
    statPanel.setData(data);
    String[] names = new String[8];
    for (int i = 0; i < names.length; i++) {
      names[i] = SpaceRaceUtility.getRandomName(
          SpaceRaceUtility.getRandomRace());
    }
    statPanel.setYDataNames(names);
    base.add(statPanel, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    // updatePanel();
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Handle actions for stat view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    // Nothing to do yet
  }


}
