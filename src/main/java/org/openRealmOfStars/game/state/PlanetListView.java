package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.PlanetInfoLabel;
import org.openRealmOfStars.gui.labels.UncolonizedPlanetInfoLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Construction;

/**
*
* Planet List view for OROS.
* Showing all the planets in single view.
*
*/
public class PlanetListView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Realm planets in array
   */
  private Planet[] planets;
  /**
   * free planets in array
   */
  private Planet[] freePlanets;
  /**
   * List of planet info label matching to planets list.
   */
  private PlanetInfoLabel[] planetInfo;
  /**
   * Realm whos planets are shown
   */
  private PlayerInfo info;
  /**
   * Starmap
   */
  private StarMap map;

  /**
   * Constructor for PlanetListView.
   * @param realm Realm information whose planets are in list
   * @param map StarMap for getting the planets
   * @param listener ActionListener
   */
  public PlanetListView(final PlayerInfo realm, final StarMap map,
      final ActionListener listener) {
    info = realm;
    this.map = map;
    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(GuiFonts.getFontCubellanSmaller());
    tabs.setForeground(GuiStatics.getCoolSpaceColorDarker());
    tabs.setBackground(GuiStatics.getDeepSpaceDarkColor());
    tabs.add("Colonized planets", createColonizedPlanets(listener));
    tabs.add("Uncolonized planets", createUncolonizedPlanets(listener));
    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setLayout(new BorderLayout());
    centerPanel.setTitle(realm.getEmpireName());
    centerPanel.add(tabs, BorderLayout.CENTER);
    this.add(centerPanel, BorderLayout.CENTER);
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
   * Create list for colonized planets.
   * @param listener Action listener
   * @return Scroll
   */
  private JScrollPane createColonizedPlanets(final ActionListener listener) {
    ArrayList<Planet> tempList = new ArrayList<>();
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == info) {
        tempList.add(planet);
      }
    }
    if (tempList.size() < 30) {
      for (int i = 0; i < 30 - tempList.size(); i++) {
        tempList.add(null);
      }
    }
    planets = tempList.toArray(new Planet[tempList.size()]);
    planetInfo = new PlanetInfoLabel[planets.length];
    this.setLayout(new BorderLayout());
    EmptyInfoPanel base = new EmptyInfoPanel();
    base.setLayout(new GridLayout(0, 1));
    for (int i = 0; i < planets.length; i++) {
      Planet planet = planets[i];
      PlanetInfoLabel label = new PlanetInfoLabel(planet, listener);
      planetInfo[i] = label;
      base.add(label);
    }
    JScrollPane scroll = new JScrollPane(base);
    scroll.setBorder(null);
    scroll.setBackground(GuiStatics.getPanelBackground());
    return scroll;
  }
  /**
   * Create list for uncolonized planets.
   * @param listener Action listener
   * @return Scroll
   */
  private JScrollPane createUncolonizedPlanets(final ActionListener listener) {
    freePlanets = StarMapUtilities.getBestFreePlanets(map, info);
    ArrayList<Planet> tempList = new ArrayList<>();
    for (Planet planet : freePlanets) {
      tempList.add(planet);
    }
    if (freePlanets.length < 30) {
      for (int i = 0; i < 30 - freePlanets.length; i++) {
        tempList.add(null);
      }
    }
    freePlanets = tempList.toArray(new Planet[0]);
    this.setLayout(new BorderLayout());
    EmptyInfoPanel base = new EmptyInfoPanel();
    base.setLayout(new GridLayout(0, 1));
    for (int i = 0; i < freePlanets.length; i++) {
      Planet planet = freePlanets[i];
      UncolonizedPlanetInfoLabel label = new UncolonizedPlanetInfoLabel(planet,
          info, listener);
      base.add(label);
    }
    JScrollPane scroll = new JScrollPane(base);
    scroll.setBorder(null);
    scroll.setBackground(GuiStatics.getPanelBackground());
    return scroll;
  }

  /**
   * Get Realm whose planet being shown.
   * @return PlayerInfo
   */
  public PlayerInfo getRealm() {
    return info;
  }

  /**
   * Get Planet by name
   * @param name Planet name to search
   * @return index, -1 if not found
   */
  private int getPlanetByName(final String name) {
    for (int i = 0; i < planets.length; i++) {
      Planet planet = planets[i];
      if (planet != null && planet.getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }
  /**
   * Handle actions for Planet List view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .contains(GameCommands.COMMAND_PRODUCTION_LIST)) {
      String[] temp = arg0.getActionCommand().split("\\|");
      int index = getPlanetByName(temp[0]);
      if (index != -1) {
        Planet planet = planets[index];
        Construction building = planetInfo[index].getSelectedConstruction();
        if (building != null) {
          planet.setUnderConstruction(building);
          planetInfo[index].updateTimeEstimate();
          SoundPlayer.playMenuSound();
        }
      }
    }
  }

}
