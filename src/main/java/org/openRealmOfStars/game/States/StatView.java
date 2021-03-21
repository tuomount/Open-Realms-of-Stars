package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.StatisticPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017-2019,2021 Tuomo Untinen
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
   * Back button
   */
  private SpaceButton backBtn;
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
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    String[] names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_MILITARY, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getPlanets().getGalaxyData());
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_PLANETS, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getPopulation().getGalaxyData());
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_POPULATION, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getCultural().getGalaxyData());
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    statPanel.setVictoryScoreLimit(
        StarMapUtilities.calculateCultureScoreLimit(map.getMaxX(),
            map.getMaxY(), map.getScoreVictoryTurn(), map.getScoreCulture()));
    tabs.add(NewsCorpData.STAT_CULTURAL, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getCredit().getGalaxyData());
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_CREDIT, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().getResearch().getGalaxyData());
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_RESEARCH, statPanel);

    statPanel = new StatisticPanel();
    statPanel.setData(map.getNewsCorpData().generateScores().getGalaxyData());
    statPanel.setTurnDistance(NewsCorpData.NEWS_PUBLISH_RATE);
    names = new String[map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < names.length; i++) {
      names[i] = map.getPlayerByIndex(i).getEmpireName();
    }
    statPanel.setYDataNames(names);
    tabs.add(NewsCorpData.STAT_SCORE, statPanel);

    tabs.add("Relations", createRelationPanel(map, names));
    tabs.add("Victory conditions", createWinningPanel(map, names));
    if (map.isGameEnded()) {
      tabs.add("Ship designs", createShipStatPanel(map, names));
    }
    base.add(tabs, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    backBtn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    backBtn.addActionListener(listener);
    bottomPanel.add(backBtn, BorderLayout.CENTER);

    // updatePanel();
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Set back button text
   * @param text New text for back button
   */
  public void setBackButtonText(final String text) {
    backBtn.setText(text);
  }

  /**
   * Get back button text
   * @return text
   */
  public String getBackButtonText() {
    return backBtn.getText();
  }

  /**
   * Get highest ship stat.
   * @param realm PlayerINfo
   * @param statType 0: Builts, 1: In use, 2: Combats, 3: Victories,
   *                 4: Lost, 5: Kills, 6: Military power
   * @return ShipStat or null
   */
  private ShipStat getHighestShipStat(final PlayerInfo realm,
      final int statType) {
    int value = -1;
    int index = -1;
    for (int j = 0; j < realm.getNumberOfShipStats(); j++) {
      ShipStat stat = realm.getShipStat(j);
      if (statType == 0 && stat.getNumberOfBuilt() > value) {
        value = stat.getNumberOfBuilt();
        index = j;
      }
      if (statType == 1 && stat.getNumberOfInUse() > value) {
        value = stat.getNumberOfInUse();
        index = j;
      }
      if (statType == 2 && stat.getNumberOfCombats() > value) {
        value = stat.getNumberOfCombats();
        index = j;
      }
      if (statType == 3 && stat.getNumberOfVictories() > value) {
        value = stat.getNumberOfVictories();
        index = j;
      }
      if (statType == 4 && stat.getNumberOfLoses() > value) {
        value = stat.getNumberOfLoses();
        index = j;
      }
      if (statType == 5 && stat.getNumberOfKills() > value) {
        value = stat.getNumberOfKills();
        index = j;
      }
      if (statType == 6 && stat.getDesign().getTotalMilitaryPower() > value) {
        value = stat.getDesign().getTotalMilitaryPower();
        index = j;
      }
    }
    if (index != -1) {
      return realm.getShipStat(index);
    }
    return null;
  }
  /**
   * Ship stat panel
   * @param map StarMap
   * @param names Player empire names
   * @return BlackPanel with winning information
   */
  public BlackPanel createShipStatPanel(final StarMap map,
      final String[] names) {
    BlackPanel panel = new BlackPanel();
    int rows = map.getPlayerList().getCurrentMaxRealms() + 1;
    int cols = 7;
    panel.setLayout(new GridLayout(rows, cols));
    panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    TransparentLabel label = new TransparentLabel(null, "", false, true);
    panel.add(label);
    label = new TransparentLabel(null, "Most built one", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>How many of these designs were built."
        + "</html>");
    panel.add(label);
    label = new TransparentLabel(null, "Most powerful", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>Which design has highest military power."
        + "</html>");
    panel.add(label);
    label = new TransparentLabel(null, "Most combats", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>Which design had most of combats.</html>");
    panel.add(label);
    label = new TransparentLabel(null, "Most victorious", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>Which design had survived most time of"
        + " combats or bombings.</html>");
    panel.add(label);
    label = new TransparentLabel(null, "Most lost", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>Which design had most of destructions by enemy"
        + " in combats or bombings.</html>");
    panel.add(label);
    label = new TransparentLabel(null, "Most kills", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>Which design has most kills in"
        + " combats.</html>");
    panel.add(label);
    // Loop where to add player labels
    for (int i = 0; i < names.length; i++) {
      // Realm name
      label = new TransparentLabel(null, names[i], true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
      PlayerInfo realm = map.getPlayerByIndex(i);
      ShipStat stat = getHighestShipStat(realm, 0);
      label = new TransparentLabel(null, stat.getDesign().getName() + " - "
          + stat.getNumberOfBuilt(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      ImageLabel image = new ImageLabel(
          ShipImages.getByRace(stat.getDesign().getHull().getRace())
          .getShipImage(stat.getDesign().getHull().getImageIndex()), true);
      BlackPanel invis = new BlackPanel();
      invis.setLayout(new GridLayout(0, 2));
      invis.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      invis.add(image);
      invis.add(label);
      panel.add(invis);

      stat = getHighestShipStat(realm, 6);
      label = new TransparentLabel(null, stat.getDesign().getName() + " - "
          + stat.getDesign().getTotalMilitaryPower(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      image = new ImageLabel(
          ShipImages.getByRace(stat.getDesign().getHull().getRace())
          .getShipImage(stat.getDesign().getHull().getImageIndex()), true);
      invis = new BlackPanel();
      invis.setLayout(new GridLayout(0, 2));
      invis.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      invis.add(image);
      invis.add(label);
      panel.add(invis);

      stat = getHighestShipStat(realm, 2);
      label = new TransparentLabel(null, stat.getDesign().getName() + " - "
          + stat.getNumberOfCombats(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      image = new ImageLabel(
          ShipImages.getByRace(stat.getDesign().getHull().getRace())
          .getShipImage(stat.getDesign().getHull().getImageIndex()), true);
      invis = new BlackPanel();
      invis.setLayout(new GridLayout(0, 2));
      invis.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      invis.add(image);
      invis.add(label);
      panel.add(invis);

      stat = getHighestShipStat(realm, 3);
      label = new TransparentLabel(null, stat.getDesign().getName() + " - "
          + stat.getNumberOfVictories(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      image = new ImageLabel(
          ShipImages.getByRace(stat.getDesign().getHull().getRace())
          .getShipImage(stat.getDesign().getHull().getImageIndex()), true);
      invis = new BlackPanel();
      invis.setLayout(new GridLayout(0, 2));
      invis.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      invis.add(image);
      invis.add(label);
      panel.add(invis);

      stat = getHighestShipStat(realm, 4);
      label = new TransparentLabel(null, stat.getDesign().getName() + " - "
          + stat.getNumberOfLoses(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      image = new ImageLabel(
          ShipImages.getByRace(stat.getDesign().getHull().getRace())
          .getShipImage(stat.getDesign().getHull().getImageIndex()), true);
      invis = new BlackPanel();
      invis.setLayout(new GridLayout(0, 2));
      invis.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      invis.add(image);
      invis.add(label);
      panel.add(invis);

      stat = getHighestShipStat(realm, 5);
      label = new TransparentLabel(null, stat.getDesign().getName() + " - "
          + stat.getNumberOfKills(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      image = new ImageLabel(
          ShipImages.getByRace(stat.getDesign().getHull().getRace())
          .getShipImage(stat.getDesign().getHull().getImageIndex()), true);
      invis = new BlackPanel();
      invis.setLayout(new GridLayout(0, 2));
      invis.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      invis.add(image);
      invis.add(label);
      panel.add(invis);
    }

    return panel;
  }

  /**
   * Create Winning panel
   * @param map StarMap
   * @param names Player empire names
   * @return BlackPanel with winning information
   */
  public BlackPanel createWinningPanel(final StarMap map,
      final String[] names) {
    BlackPanel panel = new BlackPanel();
    int rows = map.getPlayerList().getCurrentMaxRealms() + 1;
    int cols = 2;
    boolean cultureLabels = false;
    boolean dominationLabels = false;
    boolean scienceLabels = false;
    boolean diplomacyLabels = false;
    boolean populationLabels = false;
    if (map.getScoreCulture() != -1) {
      cols++;
      cultureLabels = true;
    }
    if (map.getScoreConquer() == 1
        && map.getPlayerList().getCurrentMaxRealms() > 3) {
      cols++;
      dominationLabels = true;
    }
    if (map.getScoreResearch() > 0) {
      cols++;
      scienceLabels = true;
    }
    if (map.getScoreDiplomacy() > 0) {
      cols++;
      diplomacyLabels = true;
    }
    if (map.getScorePopulation() > 0) {
      cols++;
      populationLabels = true;
    }
    panel.setLayout(new GridLayout(rows, cols));
    panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    TransparentLabel label = new TransparentLabel(null, "", false, true);
    panel.add(label);
    int maxTurns = map.getScoreVictoryTurn();
    int maxAdmires = (map.getPlayerList().getCurrentMaxRealms() - 1) / 2;
    int votesLeft = map.getScoreDiplomacy() + 1;
    for (Vote vote : map.getVotes().getVotes()) {
      if ((vote.getType() == VotingType.BAN_NUCLEAR_WEAPONS
          || vote.getType() == VotingType.BAN_PRIVATEER_SHIPS
          || vote.getType() == VotingType.GALACTIC_PEACE
          || vote.getType() == VotingType.RULER_OF_GALAXY
          || vote.getType() == VotingType.SECOND_CANDIDATE_MILITARY
          || vote.getType() == VotingType.TAXATION_OF_RICHEST_REALM)
          && vote.getTurnsToVote() == 0) {
        votesLeft--;
      }
    }
    label = new TransparentLabel(null, "Highest score at "
        + maxTurns + " turns", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    label.setToolTipText("<html>Winner is the one with"
        + " highest score<br> when last turn of the game has played.</html>");
    panel.add(label);
    if (cultureLabels) {
      label = new TransparentLabel(null, "Culture", true, true);
      label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      label.setToolTipText("<html>Cultural victory requreis to"
          + " have highest culture and over certain limit.<br>"
          + " Realm also has to be broadcasting culture and<br>"
          + " have enough cultural admires to win by culture.</html>");
      panel.add(label);
    }
    if (dominationLabels) {
      label = new TransparentLabel(null, "Domination", true, true);
      label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      label.setToolTipText("<html>Domination victory is achieved when"
          + " enough home worlds have been conquered.</html>");
      panel.add(label);
    }
    if (scienceLabels) {
      label = new TransparentLabel(null, "Science Achievements", true, true);
      label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      label.setToolTipText("<html>Scientific achievements must be built on"
          + " same planet.</html>");
      panel.add(label);
    }
    if (diplomacyLabels) {
      label = new TransparentLabel(null, "Diplomatic ("
          + votesLeft + " votes left)", true, true);
      label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      label.setToolTipText("<html>One realm must first built enough"
          + " United Galaxy Towers.<br> Then voting starts and final"
          + " voting is for which realm is going to be"
          + " Galactic Secretary.</html>");
      panel.add(label);
    }
    if (populationLabels) {
      label = new TransparentLabel(null, "Population", true, true);
      label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      int limit = 0;
      switch (map.getScorePopulation()) {
        case 1: limit = 40; break;
        case 2: limit = 50; break;
        case 3: limit = 60; break;
        case 4: limit = 70; break;
        default: limit = 50; break;
      }
      label.setToolTipText("<html>Population victory is achieved when realm "
          + " has " + limit + "% of whole galaxy population.<br>Also "
          + "100 turns must have been passed when this victory is possible."
          + "</html>");
      panel.add(label);
    }
    // Precalculations for scoring
    GalaxyStat scoreStat = map.getNewsCorpData().generateScores();
    int maxCulture = StarMapUtilities.calculateCultureScoreLimit(map.getMaxX(),
        map.getMaxY(), map.getScoreVictoryTurn(), map.getScoreCulture());
    int towerLimit = StarMapUtilities.calculateRequireTowerLimit(map.getMaxX(),
        map.getMaxY());
    boolean[] broadcasters = new boolean[
        map.getPlayerList().getCurrentMaxRealms()];
    int[] homeworlds = new int[map.getPlayerList().getCurrentMaxRealms()];
    int[] achievements = new int[map.getPlayerList().getCurrentMaxRealms()];
    int[] towers = new int[map.getPlayerList().getCurrentMaxRealms()];
    int[] population = new int[map.getPlayerList().getCurrentMaxRealms()];
    int galaxyPopulation = 0;
    for (int i = 0; i < map.getPlanetList().size(); i++) {
      Planet planet = map.getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null
          && planet.broadcaster()
          && planet.getPlanetOwnerIndex() < broadcasters.length) {
        broadcasters[planet.getPlanetOwnerIndex()] = true;
      }
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetOwnerIndex() < homeworlds.length
          && planet.getHomeWorldIndex() != -1) {
        homeworlds[planet.getPlanetOwnerIndex()]++;
      }
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetOwnerIndex() < population.length) {
        galaxyPopulation = galaxyPopulation + planet.getTotalPopulation();
        population[planet.getPlanetOwnerIndex()] =
            population[planet.getPlanetOwnerIndex()]
            + planet.getTotalPopulation();
      }
      // Count scientific achievements for planets
      int count = 0;
      if (planet.getPlanetType() == PlanetTypes.ARTIFICIALWORLD1) {
        count++;
      }
      for (Building building : planet.getBuildingList()) {
        if (building.getScientificAchievement()) {
          count++;
        }
      }
      if (planet.getPlanetOwnerIndex() > -1
          && planet.getPlanetOwnerIndex() < achievements.length
          && count > achievements[planet.getPlanetOwnerIndex()]) {
        achievements[planet.getPlanetOwnerIndex()] = count;
      }
      if (planet.getPlanetOwnerIndex() > -1
          && planet.getPlanetOwnerIndex() < achievements.length
          && planet.hasTower()) {
        towers[planet.getPlanetOwnerIndex()]++;
      }
    }
    // Loop where to add player labels
    for (int i = 0; i < names.length; i++) {
      // Realm name
      label = new TransparentLabel(null, names[i], true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
      int value = scoreStat.getLatest(i);
      // Scoring victory
      label = new TransparentLabel(null, String.valueOf(value), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
      // Cultural victory
      if (cultureLabels) {
        StringBuilder sb = new StringBuilder();
        sb.append(map.getNewsCorpData().getCultural().getLatest(i));
        sb.append("/");
        sb.append(maxCulture);
        if (broadcasters[i]) {
          sb.append(" Broadcasting");
        }
        value = map.getPlayerByIndex(i).getDiplomacy().getNumberOfAdmires();
        if (value > 0) {
          sb.append(" ");
          sb.append(value);
          sb.append("/");
          sb.append(maxAdmires);
          sb.append(" admires");
        }
        label = new TransparentLabel(null, sb.toString(), true, true);
        label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
        panel.add(label);
      }
      // Domination victory
      if (dominationLabels) {
        int limit = map.getPlayerList().getCurrentMaxRealms() / 2;
        if (limit < 3) {
          limit = 3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(homeworlds[i]);
        sb.append("/");
        sb.append(limit);
        sb.append(" homeworlds");
        label = new TransparentLabel(null, sb.toString(), true, true);
        label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
        panel.add(label);
      }
      if (scienceLabels) {
        int limit = map.getScoreResearch();
        StringBuilder sb = new StringBuilder();
        sb.append(achievements[i]);
        sb.append("/");
        sb.append(limit);
        sb.append(" on single planet");
        label = new TransparentLabel(null, sb.toString(), true, true);
        label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
        panel.add(label);
      }
      if (diplomacyLabels) {
        StringBuilder sb = new StringBuilder();
        sb.append(towers[i]);
        sb.append("/");
        sb.append(towerLimit);
        sb.append(" United Galaxy Towers");
        label = new TransparentLabel(null, sb.toString(), true, true);
        label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
        panel.add(label);
      }
      if (populationLabels) {
        StringBuilder sb = new StringBuilder();
        sb.append(population[i]);
        sb.append("/");
        sb.append(galaxyPopulation);
        value = population[i] * 100 / galaxyPopulation;
        sb.append(" (");
        sb.append(value);
        sb.append(" %)");
        label = new TransparentLabel(null, sb.toString(), true, true);
        label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
        panel.add(label);
      }
    }
    return panel;
  }
  /**
   * Create Relation panel
   * @param map StarMap
   * @param names Player empire names
   * @return BlackPanel with relation information
   */
  public BlackPanel createRelationPanel(final StarMap map,
      final String[] names) {
    BlackPanel panel = new BlackPanel();
    int rowsNCols = map.getPlayerList().getCurrentMaxRealms() + 1;
    panel.setLayout(new GridLayout(rowsNCols, rowsNCols));
    TransparentLabel label = new TransparentLabel(null, "", false, true);
    panel.add(label);
    for (int i = 0; i < names.length; i++) {
      label = new TransparentLabel(null, names[i], true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
    }
    for (int i = 0; i < names.length; i++) {
      label = new TransparentLabel(null, names[i], true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
      for (int j = 0; j < names.length; j++) {
        PlayerInfo info = map.getPlayerByIndex(i);
        String relation = info.getDiplomacy().getDiplomaticRelation(j);
        ImageLabel img = new ImageLabel(GuiStatics.RELATION_ALLIANCE, true);
        img.setImage(null);
        if (relation.equals(Diplomacy.DEFENSIVE_PACT)) {
          img = new ImageLabel(GuiStatics.DEFENSIVE_PACT, true);
          img.setToolTipText(
              "<html>Defensive pact allows all ships move on parties "
              + "sectors.<br>"
              + "This allows trading vessels to visit parties planets.<br>"
              + "If member is being attacked then attacker also "
              + "attacks other parties</html>");
        }
        if (relation.equals(Diplomacy.ALLIANCE)) {
          img = new ImageLabel(GuiStatics.RELATION_ALLIANCE, true);
          img.setToolTipText(
              "<html>Alliance allows all ships move on parties sectors.<br>"
              + "This allows trading vessels to visit parties planets.<br>"
              + "Alliance also grants defensive pact protection and"
              + " espionage trade.<br>"
              + "Parties in alliance win the game together."
              + "</html>");
        }
        if (relation.equals(Diplomacy.TRADE_ALLIANCE)) {
          img = new ImageLabel(GuiStatics.RELATION_TRADE_ALLIANCE, true);
          img.setToolTipText(
              "<html>Trade alliance allows all non military<br>"
              + "ships move on parties sectors.<br>"
              + "This allows trading vessels to visit parties planets."
              + "</html>");
        }
        if (relation.equals(Diplomacy.TRADE_EMBARGO)) {
          img = new ImageLabel(GuiStatics.RELATION_TRADE_EMBARGO, true);
          img.setToolTipText(
              "<html>Trade embargo forbids forming trade alliance.<br>"
              + "Declaring trade embargo gives diplomatic bonuses"
              + " towards third parties.</html>");
        }
        if (relation.equals(Diplomacy.WAR)) {
          img = new ImageLabel(GuiStatics.RELATION_WAR, true);
          img.setToolTipText(
              "<html>War allows freely to have combat between parties.<br>"
              + "Declaring war gives negative diplomatic bonus towards<br>"
              + "third party.</html>");
        }
        if (relation.equals(Diplomacy.PEACE)) {
          img = new ImageLabel(GuiStatics.RELATION_PEACE, true);
          img.setToolTipText(
              "<html>Two parties are in peace.<br>"
              + "This does not allow ships move on parties sectors.</html>");
        }
        if (relation.isEmpty()
            && info.getDiplomacy().getDiplomacyList(j) != null) {
          img = new ImageLabel(GuiStatics.RELATION_UNKNOWN, true);
          img.setToolTipText(
              "<html>Two parties have not met yet<br>"
              + " or made any official relation.</html>");
        }
        img.setCenter(true);
        panel.add(img);
      }
    }
    return panel;
  }

  /**
   * Handle actions for stat view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    // Nothing to do yet
  }


}
