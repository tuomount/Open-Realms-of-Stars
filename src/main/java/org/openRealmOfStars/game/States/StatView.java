package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.StatisticPanel;
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
   * @param map StarMap which contains players and planet lists.
   * @param listener Action Listener
   */
  public StatView(final StarMap map, final ActionListener listener) {
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Statistics");

    StatisticPanel statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getMilitary().getGalaxyData());
    String[] names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
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
