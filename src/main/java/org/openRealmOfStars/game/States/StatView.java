package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.StatisticPanel;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;

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

    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(GuiStatics.getFontCubellanSmaller());
    tabs.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER);
    tabs.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);

    StatisticPanel statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getMilitary().getGalaxyData());
    String[] names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_MILITARY, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getPlanets().getGalaxyData());
    names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_PLANETS, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getPopulation().getGalaxyData());
    names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_POPULATION, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getCultural().getGalaxyData());
    names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_CULTURAL, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getCredit().getGalaxyData());
    names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_CREDIT, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getResearch().getGalaxyData());
    names = new String[map.getPlayerList().getCurrentMaxPlayers()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_RESEARCH, statPanel);

    base.add(tabs, BorderLayout.CENTER);
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
