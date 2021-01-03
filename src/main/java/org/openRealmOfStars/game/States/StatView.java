package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

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
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017-2019 Tuomo Untinen
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
    tabs.add("Winnnig", createWinningPanel(map, names));
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
   * Create Winning panel
   * @param map StarMap
   * @param names Player empire names
   * @return BlackPanel with winning information
   */
  public BlackPanel createWinningPanel(final StarMap map,
      final String[] names) {
    BlackPanel panel = new BlackPanel();
    int rows = map.getPlayerList().getCurrentMaxRealms() + 1;
    int cols = 3;
    panel.setLayout(new GridLayout(rows, cols));
    panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    TransparentLabel label = new TransparentLabel(null, "", false, true);
    panel.add(label);
    int maxTurns = map.getScoreVictoryTurn();
    label = new TransparentLabel(null, "Highest score at "
        + maxTurns + " turns", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    panel.add(label);
    label = new TransparentLabel(null, "Culture", true, true);
    label.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    panel.add(label);
    GalaxyStat scoreStat = map.getNewsCorpData().generateScores();
    int maxCulture = StarMapUtilities.calculateCultureScoreLimit(map.getMaxX(),
        map.getMaxY(), map.getScoreVictoryTurn(), map.getScoreCulture());
    boolean[] broadcasters = new boolean[
        map.getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < map.getPlanetList().size(); i++) {
      Planet planet = map.getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null
          && planet.broadcaster()
          && planet.getPlanetOwnerIndex() < broadcasters.length) {
        broadcasters[planet.getPlanetOwnerIndex()] = true;
      }
    }
    for (int i = 0; i < names.length; i++) {
      label = new TransparentLabel(null, names[i], true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
      int value = scoreStat.getLatest(i);
      label = new TransparentLabel(null, String.valueOf(value), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
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
        sb.append(" admires");
      }
      label = new TransparentLabel(null, sb.toString(), true, true);
      label.setForeground(StatisticPanel.PLAYER_COLORS[i]);
      panel.add(label);
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
