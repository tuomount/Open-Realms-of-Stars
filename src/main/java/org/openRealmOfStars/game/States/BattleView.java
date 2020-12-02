package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatMapMouseListener;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
   * Max timer count value.
   */
  private static final int MAX_TIMER_COUNT = 40;
  /**
   * End button
   */
  private SpaceButton endButton;

  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Text area containing overload information.
   */
  private InfoTextArea overloadInfo;

  /**
   * Text area containing combat log
   */
  private InfoTextArea logArea;

  /**
   * Text log
   */
  private Logger textLogger;
  /**
   * Loop count how many times, alarm will be played.
   */
  private int loopCount;
  /**
   * How many times event must happen before dropping loopCount;
   */
  private int timerCount;

  /**
   * Battle view for space combat
   * @param combat Combat
   * @param map Star Map
   * @param listener ActionListener
   */
  public BattleView(final Combat combat, final StarMap map,
      final ActionListener listener) {
    MusicPlayer.playCombatMusic();
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
    MusicPlayer.playCombatMusic();
    this.map = map;
    Coordinate escapePosition = StarMapUtilities.getEscapeCoordinates(
        fleet2.getCoordinate(), fleet1.getCoordinate());
    if (escapePosition != null
        && !map.isBlocked(escapePosition.getX(), escapePosition.getY())) {
      PlayerInfo info = map.isBlockedByFleet(escapePosition.getX(),
          escapePosition.getY());
      if (info != null && info != player2) {
        escapePosition = null;
      }
    } else {
      escapePosition = null;
    }
    combat = new Combat(fleet1, fleet2, player1, player2, escapePosition);
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
    if (listener instanceof Game) {
      Game game = (Game) listener;
      mapPanel = new MapPanel(game);
      loopCount = 5;
      timerCount = MAX_TIMER_COUNT;
    } else {
      mapPanel = new MapPanel(true);
      loopCount = 0;
      timerCount = 0;
    }
    mapPanel.setSize(Combat.MAX_X * ShipImage.MAX_WIDTH,
        Combat.MAX_Y * ShipImage.MAX_HEIGHT);
    mapPanel.drawBattleMap(combat, combat.getPlayer1(), this.map);

    textArea = new InfoTextArea(10, 30);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    overloadInfo = new InfoTextArea(3, 30);
    overloadInfo.setEditable(false);
    overloadInfo.setLineWrap(true);
    infoPanel = new BattleInfoPanel(combat.getCurrentShip().getShip(), textArea,
        overloadInfo, listener);

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
    int numberOfRows = 10;
    if (GuiStatics.isLargerFonts()) {
      numberOfRows = MAX_LOG_NUMBER;
    }
    logArea = new InfoTextArea(numberOfRows, 30);
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
    if (combat.getCurrentShip() != null) {
      infoPanel.showShip(combat.getCurrentShip().getShip());
      updateOverloadInfo();
    }
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
      if (combat.getCurrentShip() != null) {
        infoPanel.showShip(combat.getCurrentShip().getShip());
        updateOverloadInfo();
      }
    }
  }

  /**
   * End battle round
   */
  private void endRound() {
    if (combat.endRound(textLogger)) {
      endButton.setText("End combat");
      map.getFleetTiles(true);
    }
    combatMapMouseListener.setComponentUse(-1);
    if (combat.getCurrentShip() != null) {
      infoPanel.showShip(combat.getCurrentShip().getShip());
      updateOverloadInfo();
    }
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
   * Handle overload info, updates text and colors.
   */
  public void updateOverloadInfo() {
    if (combat.getCurrentShip().getEnergyReserve() > 0) {
      overloadInfo.setTextColor(GuiStatics.COLOR_GREEN_TEXT,
          GuiStatics.COLOR_GREEN_TEXT_DARK);
    } else if (combat.getCurrentShip().getEnergyReserve() < 0) {
      overloadInfo.setTextColor(GuiStatics.COLOR_RED_TEXT,
          GuiStatics.COLOR_RED_TEXT_DARK);
    } else {
      overloadInfo.setTextColor(GuiStatics.COLOR_YELLOW_TEXT,
          GuiStatics.COLOR_YELLOW_TEXT_DARK);
    }
    overloadInfo.setText(
        combat.getCurrentShip().getOverloadInformation());
    overloadInfo.repaint();
  }
  /**
   * Handle actions for battle view
   * @param arg0 Active Event
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (loopCount > 0) {
        if (timerCount == MAX_TIMER_COUNT) {
          SoundPlayer.playSound(SoundPlayer.ALARM);
        }
        timerCount--;
        if (timerCount <= 0) {
          loopCount--;
          timerCount = MAX_TIMER_COUNT;
        }
      }
      if (combatMapMouseListener.isEscaped()) {
        combatMapMouseListener.setComponentUse(-1);
        if (combat.getCurrentShip() != null) {
          infoPanel.showShip(combat.getCurrentShip().getShip());
          overloadInfo.setText(
              combat.getCurrentShip().getOverloadInformation());
        }
        this.repaint();
        combatMapMouseListener.setEscaped(false);
      }
      if (isCombatEnded()) {
        endButton.setText("End combat");
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
        if (combat.getCurrentShip() != null
            && !combat.getCurrentShip().getPlayer().isHuman() && delayCount == 0
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
        && !isCombatEnded() && combat.getCurrentShip() != null
        &&  combat.getCurrentShip().getPlayer().isHuman()) {
      SoundPlayer.playMenuSound();
      endRound();
    }
    if (arg0.getActionCommand().startsWith(GameCommands.COMMAND_COMPONENT_USE)
        && combat.getCurrentShip() != null
        && combat.getCurrentShip().getPlayer().isHuman()) {
      SoundPlayer.playMenuSound();
      String number = arg0.getActionCommand()
          .substring(GameCommands.COMMAND_COMPONENT_USE.length());
      int index = Integer.valueOf(number);

      if (combat.getComponentUse() != index
          && combat.getCurrentShip().getShip().componentIsWorking(index)) {
        combatMapMouseListener.setComponentUse(index);
        combat.setComponentUse(index);
        ShipComponent component = combat.getCurrentShip().getShip(
            ).getComponent(index);
        if (combat.getCurrentShip().getShip().isStarBase()
            && !combat.getCurrentShip().getShip().getFlag(
                Ship.FLAG_STARBASE_DEPLOYED)
            && component.isWeapon()) {
          textLogger.addLog("Undeployed Starbase cannot use weapons!");
          combatMapMouseListener.setComponentUse(-1);
          combat.setComponentUse(-1);
          return;
        }
        if (combat.handleOverloading(textLogger, index)) {
          updateOverloadInfo();
          infoPanel.useComponent(index);
          combatMapMouseListener.setComponentUse(-1);
          combat.setComponentUse(-1);
        } else if (!component.isWeapon() && !component.isPrivateer()
            && !component.isTractor()) {
          combatMapMouseListener.setComponentUse(-1);
          combat.setComponentUse(-1);
        }
      } else {
        combatMapMouseListener.setComponentUse(-1);
        combat.setComponentUse(-1);
      }
    }

  }

  /**
   * Return combat from battle view.
   * @return Combat from battle view
   */
  public Combat getCombat() {
    return combat;
  }

}
