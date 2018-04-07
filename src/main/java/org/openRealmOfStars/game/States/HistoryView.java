package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
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
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Space label containing turn and event info
   */
  private SpaceLabel turnLabel;

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
    mapPanel.setHistoryCultures(starMap.getHistory().calculateCulture(0,
        starMap));
    centerPanel.add(mapPanel, BorderLayout.CENTER);
    InfoPanel infoPanel = new InfoPanel();
    infoPanel.setLayout(new BorderLayout());
    infoPanel.setTitle("History Event");
    textArea = new InfoTextArea(5, 10);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    textArea.setCharacterWidth(7);
    infoPanel.add(textArea, BorderLayout.CENTER);
    EmptyInfoPanel panel = new EmptyInfoPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    IconButton iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_YEAR,
        this);
    iBtn.setToolTipText("Previous year");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_EVENT,
        this);
    iBtn.setToolTipText("Previous event");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    turnLabel = new SpaceLabel("Turn 1000/1000 Event 1000/1000");
    panel.add(turnLabel);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_EVENT,
        this);
    iBtn.setToolTipText("Next event");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_YEAR,
        this);
    iBtn.setToolTipText("Next year");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    EmptyInfoPanel panel2 = new EmptyInfoPanel();
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
    panel2.add(panel);
    infoPanel.add(panel2, BorderLayout.SOUTH);
    centerPanel.add(infoPanel, BorderLayout.SOUTH);

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
