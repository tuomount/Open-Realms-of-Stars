package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatAnimation;
import org.openRealmOfStars.player.combat.CombatMapMouseListener;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.Logger;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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

  private static final long serialVersionUID = 1L;
  static final int MAX_LOG_NUMBER = 11;
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
   * aStar search for AI
   */
  private AStarSearch aStar;

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
   * Combat has ended
   */
  private boolean combatEnded;

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
  public BattleView(Combat combat, StarMap map, ActionListener listener) {
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
  public BattleView(Fleet fleet1, PlayerInfo player1, Fleet fleet2,
      PlayerInfo player2, StarMap map, ActionListener listener) {
    this.map = map;
    combat = new Combat(fleet1, fleet2, player1, player2);
    initBattleView(listener);
  }

  /**
   * Init Battle view
   * @param listener Action Listener
   */
  private void initBattleView(ActionListener listener) {
    BlackPanel base = new BlackPanel();
    mapPanel = new MapPanel(true);
    mapPanel.setSize(Combat.MAX_X*ShipImage.MAX_WIDTH, 
        Combat.MAX_Y*ShipImage.MAX_HEIGHT);
    mapPanel.drawBattleMap(combat, combat.getPlayer1(), this.map);

    textArea = new InfoTextArea(10,30);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    infoPanel = new BattleInfoPanel(combat.getCurrentShip().getShip(),textArea,listener);

    combatMapMouseListener = new CombatMapMouseListener(combat, mapPanel, infoPanel);
    mapPanel.addMouseListener(combatMapMouseListener);
    mapPanel.addMouseMotionListener(combatMapMouseListener);
    
    InfoPanel bottom = new InfoPanel();
    bottom.setTitle(null);
    bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
    endButton = new SpaceButton("End round", GameCommands.COMMAND_END_BATTLE_ROUND);
    endButton.addActionListener(listener);
    bottom.add(endButton);
    
    bottom.add(Box.createRigidArea(new Dimension(10,10)));
    logArea = new InfoTextArea(10,30);
    logArea.setEditable(false);
    logArea.setLineWrap(true);
    bottom.add(logArea);
    textLogger = new Logger(MAX_LOG_NUMBER);
    bottom.add(Box.createRigidArea(new Dimension(10,10)));


    
    this.setLayout(new BorderLayout());
    base.add(mapPanel,BorderLayout.CENTER);
    this.add(base,BorderLayout.CENTER);
    this.add(infoPanel,BorderLayout.EAST);
    this.add(bottom,BorderLayout.SOUTH);
    aStar = null;
    delayCount = 0;
    combatEnded = false;
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
    for (int i = textLogger.size()-1; i>=0; i--) {
      sb.append(textLogger.getMessage(i));
      if (i!=0) {
        sb.append("\n");
      }
    }
    logArea.setText(sb.toString());
    this.repaint();
  }
  
  public InfoTextArea getBottomTextArea() {
    return textArea;
  }

  /**
   * Handle AI shooting
   * @param ai AI which is shooting
   * @param target shooting target
   * @return true if shooting was actually done
   */
  private boolean handleAIShoot(CombatShip ai, CombatShip target) {
    if (target !=  null) {
      int nComp = ai.getShip().getNumberOfComponents();
      for (int i=0;i<nComp;i++) {
        ShipComponent weapon = ai.getShip().getComponent(i);
        combat.setComponentUse(i);
        if (weapon != null && weapon.isWeapon() && !ai.isComponentUsed(i) &&
            combat.isClearShot(ai, target) && ai.getShip().componentIsWorking(i)) {
          int accuracy = ai.getShip().getHitChance(weapon)+ai.getBonusAccuracy();
          accuracy = accuracy-target.getShip().getDefenseValue();
          ShipDamage shipDamage = new ShipDamage(1, "Attack missed!");
          if (DiceGenerator.getRandom(1, 100)<=accuracy) {
            shipDamage = target.getShip().damageBy(weapon);
          }
          String[] logs = shipDamage.getMessage().split("\n");
          for (String log : logs) {
            textLogger.addLog(log);
          }
          combat.setAnimation(new CombatAnimation(ai, target, weapon, shipDamage.getValue()));
          ai.useComponent(i);
          infoPanel.useComponent(i);
          ai.setAiShotsLeft(ai.getAiShotsLeft()-1);
          combat.setComponentUse(-1);
          return true;
        }
      }
    }
    return false;
  }
  
  /**
   * Handle AI
   */
  private void handleAI() {
       PlayerInfo info = combat.getCurrentShip().getPlayer();
    CombatShip deadliest = combat.getMostPowerfulShip(info);
    CombatShip closest = combat.getClosestEnemyShip(info, combat.getCurrentShip());
    CombatShip ai = combat.getCurrentShip();
    boolean shot = false;
    int range = ai.getShip().getMaxWeaponRange();
    if (deadliest != null ) {
      if (ai.getShip().getTotalMilitaryPower() > deadliest.getShip().getTotalMilitaryPower()) {
        range = ai.getShip().getMinWeaponRange();
      }
      int distance = (int) Math.round(StarMapUtilities.getDistance(ai.getX(), ai.getY(), 
        deadliest.getX(), deadliest.getY()));
      if (range < distance-ai.getMovesLeft() && closest != null) {
        shot = handleAIShoot(ai, closest);
      }
    } else if (closest != null) {
      shot = handleAIShoot(ai, closest);
    }
    if (aStar == null) {
      if (deadliest != null) {
        aStar = new AStarSearch(combat, combat.getCurrentShip(),deadliest, range);
      }
      if (aStar == null && closest != null) {
        aStar = new AStarSearch(combat, combat.getCurrentShip(),closest, range);
      } 
      if (aStar != null && aStar.doSearch()) {
        aStar.doRoute();
      } else {
        // Could not found route for deadliest or closest one
        endRound();
        return;
      }
    }
    PathPoint point = aStar.getMove();
    if (ai.getShip().getTacticSpeed()==0) {
      shot = handleAIShoot(ai, deadliest);
    }
    if (point != null && !combat.isBlocked(point.getX(), point.getY()) && ai.getMovesLeft() > 0) {
      shot = handleAIShoot(ai, deadliest);
      if (!shot) {
        // Not moving after shooting
        ai.setMovesLeft(ai.getMovesLeft()-1);
        ai.setX(point.getX());
        ai.setY(point.getY());
        aStar.nextMove();
      }
      handleAIShoot(ai, deadliest);
    } else {
      // Path is blocked
      ai.setMovesLeft(0);
    }
    if ((ai.getMovesLeft() == 0 || aStar.isLastMove()) &&
        combat.getAnimation() == null) {
      if (ai.getAiShotsLeft() > 0) {
        // We still got more shots left, let's shoot the deadliest
        shot = handleAIShoot(ai, deadliest);
        if (!shot) {
          // Deadliest wasn't close enough, let's shoot the closest
          closest = combat.getClosestEnemyShip(info, combat.getCurrentShip());
          shot = handleAIShoot(ai, closest);
          if (!shot) {
            // Even closest was too far away, ending the turn now
            aStar = null;
            endRound();
          }
        }
      } else {
        aStar = null;
        endRound();
      }
    }
  }
  
  /**
   * End battle round
   */
  private void endRound() {
    combat.setComponentUse(-1);
    combatMapMouseListener.setComponentUse(-1);
    combat.nextShip();
    infoPanel.showShip(combat.getCurrentShip().getShip());
    if (combat.isCombatOver()) {
      endButton.setText("End combat");
      combatEnded = true;
      combat.handleEndCombat();
    }
    this.repaint();
  }
  
  public boolean isCombatEnded() {
    return combatEnded;
  }
  
  /**
   * Handle actions for battle view
   * @param arg0 Active Event
   */
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_ANIMATION_TIMER) ) {
      if (combatMapMouseListener.getShipDamage() != null &&
          combatMapMouseListener.getShipDamage().isReady()) {
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
        if (!combat.getCurrentShip().getPlayer().isHuman() && delayCount == 0 &&
            !combatEnded) {
          handleAI();
        }
      }
      updatePanels();
      mapPanel.drawBattleMap(combat, map.getCurrentPlayerInfo(), map);
      mapPanel.repaint();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_END_BATTLE_ROUND)
        && !combatEnded && combat.getCurrentShip().getPlayer().isHuman()) {
        endRound();
    }
    if (arg0.getActionCommand().startsWith(GameCommands.COMMAND_COMPONENT_USE)
        && combat.getCurrentShip().getPlayer().isHuman()) {
      String number = arg0.getActionCommand().substring(GameCommands.COMMAND_COMPONENT_USE.length());
      int index = Integer.valueOf(number);
      
      if (combat.getComponentUse() != index &&
          combat.getCurrentShip().getShip().componentIsWorking(index)) {
        combatMapMouseListener.setComponentUse(index);
        combat.setComponentUse(index);
      } else {
        combatMapMouseListener.setComponentUse(-1);
        combat.setComponentUse(-1);
      }
    }

  }
  
}
