package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatMapMouseListener;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017  Tuomo Untinen
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
 * Battle view for handling space combat
 *
 */
public class BattleView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Maximum number of lines in logger
   */
  static final int MAX_LOG_NUMBER = 11;

  /**
   * Initial message shown in logger.
   */
  static final String INITIAL_LOG_MESSAGE = "Combat started...";

  /**
   * MapPanel for drawing the star map
   */
  private MapPanel mapPanel;

  /**
   * Current combat
   */
  private Combat combat;

  /**
   * Star map where combat happens
   */
  private StarMap map;

  /**
   * Info panel on east side
   */
  private BattleInfoPanel infoPanel;

  /**
   * Combat map mouse listener
   */
  private CombatMapMouseListener combatMapMouseListener;

  /**
   * Delay count for AI, since it's too fast for humans
   */
  private int delayCount;

  /**
   * 2 Seconds with 75ms animation timer
   */
  private static final int MAX_DELAY_COUNT = 30;

  /**
   * End button
   */
  private SpaceButton endButton;

  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Text area containing combat log
   */
  private InfoTextArea logArea;

  /**
   * Text log
   */
  private Logger textLogger;

  /**
   * Battle view for space combat
   * @param combat Combat
   * @param map Star Map
   * @param listener ActionListener
   */
  public BattleView(final Combat combat, final StarMap map,
      final ActionListener listener) {
    this.combat = combat;
    this.map = map;
    initBattleView(listener);
  }

  /**
   * Battle view for space combat
   * @param fleet1 First fleet in combat
   * @param player1 First player in combat
   * @param fleet2 Second fleet in combat
   * @param player2 Second player in combat
   * @param map Star map
   * @param listener ActionListener
   */
  public BattleView(final Fleet fleet1, final PlayerInfo player1,
      final Fleet fleet2, final PlayerInfo player2, final StarMap map,
      final ActionListener listener) {
    this.map = map;
    combat = new Combat(fleet1, fleet2, player1, player2);
    Coordinate combatCoord = combat.getCombatCoordinates();
    Planet planet = map.getPlanetByCoordinate(combatCoord.getX(),
        combatCoord.getY());
    combat.setPlanet(planet);
    initBattleView(listener);
  }

  /**
   * Init Battle view
   * @param listener Action Listener
   */
  private void initBattleView(final ActionListener listener) {
    BlackPanel base = new BlackPanel();
    mapPanel = new MapPanel(true);
    mapPanel.setSize(Combat.MAX_X * ShipImage.MAX_WIDTH,
        Combat.MAX_Y * ShipImage.MAX_HEIGHT);
    mapPanel.drawBattleMap(combat, combat.getPlayer1(), this.map);

    textArea = new InfoTextArea(10, 30);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    infoPanel = new BattleInfoPanel(combat.getCurrentShip().getShip(), textArea,
        listener);

    combatMapMouseListener = new CombatMapMouseListener(combat, mapPanel,
        infoPanel);
    mapPanel.addMouseListener(combatMapMouseListener);
    mapPanel.addMouseMotionListener(combatMapMouseListener);

    InfoPanel bottom = new InfoPanel();
    bottom.setTitle(null);
    bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
    endButton = new SpaceButton("End round 0  ",
        GameCommands.COMMAND_END_BATTLE_ROUND);
    endButton.addActionListener(listener);
    bottom.add(endButton);

    bottom.add(Box.createRigidArea(new Dimension(10, 10)));
    logArea = new InfoTextArea(10, 30);
    logArea.setEditable(false);
    logArea.setLineWrap(true);
    bottom.add(logArea);
    textLogger = new Logger(MAX_LOG_NUMBER);
    bottom.add(Box.createRigidArea(new Dimension(10, 10)));

    this.setLayout(new BorderLayout());
    base.add(mapPanel, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);
    this.add(infoPanel, BorderLayout.EAST);
    this.add(bottom, BorderLayout.SOUTH);
    delayCount = 0;
    textLogger.addLog(INITIAL_LOG_MESSAGE);
  }

  /**
   * Update panels on BattleView
   */
  public void updatePanels() {
    if (combatMapMouseListener.getActiveShip() != null) {
      textArea.setText(combatMapMouseListener.getActiveShip().getDescription());
    } else {
      textArea.setText("");
    }
    StringBuilder sb = new StringBuilder();
    for (int i = textLogger.size() - 1; i >= 0; i--) {
      sb.append(textLogger.getMessage(i));
      if (i != 0) {
        sb.append("\n");
      }
    }
    logArea.setText(sb.toString());
    endButton.setText("End round " + combat.getTotalRounds());
    this.repaint();
  }

  /**
   * Get Info Text Area on screen bottom
   * @return InfoTextArea
   */
  public InfoTextArea getBottomTextArea() {
    return textArea;
  }

  /**
   * Handle AI
   */
  private void handleAI() {
    if (combat.handleAI(textLogger, infoPanel)) {
      combatMapMouseListener.setComponentUse(-1);
      infoPanel.showShip(combat.getCurrentShip().getShip());

    }
  }

  /**
   * End battle round
   */
  private void endRound() {
    if (combat.endRound()) {
      endButton.setText("End combat");
      map.getFleetTiles(true);
    }
    combatMapMouseListener.setComponentUse(-1);
    infoPanel.showShip(combat.getCurrentShip().getShip());
    this.repaint();
  }

  /**
   * Is Combat ended or not. Return true if combat has ended.
   * @return true if combat has ended
   */
  public boolean isCombatEnded() {
    return combat.isCombatOver();
  }

  /**
   * Handle actions for battle view
   * @param arg0 Active Event
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (isCombatEnded()) {
        endButton.setText("End combat");
        map.getFleetTiles(true);
      }
      if (combatMapMouseListener.getShipDamage() != null
          && combatMapMouseListener.getShipDamage().isReady()) {
        String[] logs = combatMapMouseListener.getShipDamage().getMessage()
            .split("\n");
        for (String log : logs) {
          textLogger.addLog(log);
        }
        combatMapMouseListener.getShipDamage().logged();
      }
      if (combat.getAnimation() == null) {
        delayCount++;
        if (delayCount >= MAX_DELAY_COUNT) {
          delayCount = 0;
        }
        if (!combat.getCurrentShip().getPlayer().isHuman() && delayCount == 0
            && !isCombatEnded()) {
          handleAI();
        }
      }
      updatePanels();
      mapPanel.drawBattleMap(combat, map.getCurrentPlayerInfo(), map);
      mapPanel.repaint();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_END_BATTLE_ROUND)
        && !isCombatEnded() && combat.getCurrentShip().getPlayer().isHuman()) {
      SoundPlayer.playMenuSound();
      endRound();
    }
    if (arg0.getActionCommand().startsWith(GameCommands.COMMAND_COMPONENT_USE)
        && combat.getCurrentShip().getPlayer().isHuman()) {
      String number = arg0.getActionCommand()
          .substring(GameCommands.COMMAND_COMPONENT_USE.length());
      int index = Integer.valueOf(number);

      if (combat.getComponentUse() != index
          && combat.getCurrentShip().getShip().componentIsWorking(index)) {
        combatMapMouseListener.setComponentUse(index);
        combat.setComponentUse(index);
      } else {
        combatMapMouseListener.setComponentUse(-1);
        combat.setComponentUse(-1);
      }
    }

  }

}
