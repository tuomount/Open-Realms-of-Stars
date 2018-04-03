package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.starMap.StarMap;

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
 * History view showing history events
 *
 */
public class HistoryView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Starmap containing history and starmap itself
   */
  private StarMap map;

  /**
   * Map Panel handling for map drawing
   */
  private MapPanel mapPanel;

  /**
   * Constructor for history view
   * @param starMap StarMap containing the history
   * @param listener ActionListener
   */
  public HistoryView(final StarMap starMap, final ActionListener listener) {
    map = starMap;
    this.setLayout(new BorderLayout());
    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setLayout(new BorderLayout());
    centerPanel.setTitle("History at turn " + map.getTurn());
    mapPanel = new MapPanel(false);
    mapPanel.setScale(Tile.SCALED_NORMAL);
    centerPanel.add(mapPanel, BorderLayout.CENTER);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Done with history",
        GameCommands.COMMAND_DONE_HISTORY);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);


    // Add panels to base
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Handle actions for History view
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      mapPanel.drawHistoryMap(this.map);
      mapPanel.repaint();
    }
  }
}
