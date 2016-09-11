package org.openRealmOfStars.player.combat;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.PixelsToMapCoordinate;

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
 * Mouse Listener for Combat Map
 * 
 */

public class CombatMapMouseListener extends MouseAdapter implements 
    MouseMotionListener{


  /**
   * Combat for mouse listener
   */
  private Combat combat;
  
  /**
   * Map panel which to use
   */
  private MapPanel mapPanel;
  
  /**
   * Infopanel next to map panel
   */
  private BattleInfoPanel battleInfoPanel;
  
  /**
   * Is route being planned
   */
  private boolean routePlanning;
  
  /**
   * Which component is in used
   */
  private int componentUse;

  /**
   * Constructor for StarMap Mouse Listener
   * @param starMap StarMap which to use.
   * @param panel Map Panel where Star Map is being drawn.
   */
  public CombatMapMouseListener(Combat combat, MapPanel panel, BattleInfoPanel
      battleInfoPanel) {
    this.combat = combat;
    this.mapPanel = panel;
    this.battleInfoPanel = battleInfoPanel;
    this.componentUse = -1;
  }
  
  
  /**
   * Last know coordinates
   */
  private PixelsToMapCoordinate coord;
  
  /**
   * Active ship under mouse cursor
   */
  private CombatShip activeShip;
  
 
  
  @Override
  public void mouseExited(MouseEvent e) {
    coord = null;
  }

  


  public void mouseMoved(MouseEvent e) {
    coord= new PixelsToMapCoordinate(mapPanel.getLastDrawnX(),
          mapPanel.getLastDrawnY(),e.getX(),e.getY(),mapPanel.getOffsetX(),mapPanel.getOffsetY(),
          mapPanel.getViewPointX(),mapPanel.getViewPointY(),true);
      if (!coord.isOutOfPanel()) {
        combat.setCursorPos(coord.getMapX(), coord.getMapY());
        battleInfoPanel.showShip(combat.getCurrentShip().getShip());
        activeShip =combat.getShipFromCoordinate(coord.getMapX(), coord.getMapY());
      }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    coord= new PixelsToMapCoordinate(mapPanel.getLastDrawnX(),
        mapPanel.getLastDrawnY(),e.getX(),e.getY(),mapPanel.getOffsetX(),mapPanel.getOffsetY(),
        mapPanel.getViewPointX(),mapPanel.getViewPointY(),true);
    if (!coord.isOutOfPanel() && combat.getAnimation() == null) {
      if (componentUse != -1) {
        CombatShip ship = combat.getCurrentShip();
        ShipComponent weapon = ship.getShip().getComponent(componentUse);
        if (weapon != null && weapon.isWeapon() && !ship.isComponentUsed(componentUse)) {
          CombatShip target = combat.getShipFromCoordinate(combat.getCursorX(), combat.getCursorY());
          if (target !=  null && combat.isClearShot(combat.getCurrentShip(), target)) {
            int accuracy = ship.getShip().getHitChance(weapon);
            accuracy = accuracy-target.getShip().getDefenseValue();
            int value=1; // Not even a dent
            if (DiceGenerator.getRandom(1, 100)<=accuracy) {
              value = target.getShip().damageBy(weapon);
            }
            combat.setAnimation(new CombatAnimation(ship, target, weapon, value));
            ship.useComponent(componentUse);
            componentUse = -1;
            combat.setComponentUse(-1);
          }
        }
      } else {
        int dist = (int) StarMap.getDistance(combat.getCurrentShip().getX(), 
            combat.getCurrentShip().getY(), coord.getMapX(), coord.getMapY());
        if (dist == 1 && !combat.isBlocked(coord.getMapX(), coord.getMapY()) &&
            combat.getCurrentShip().getMovesLeft() > 0) {
          combat.getCurrentShip().setX(coord.getMapX());
          combat.getCurrentShip().setY(coord.getMapY());
          combat.getCurrentShip().setMovesLeft(combat.getCurrentShip().getMovesLeft()-1);
        }
      }
    }

  }


  public boolean isRoutePlanning() {
    return routePlanning;
  }


  public void setRoutePlanning(boolean routePlanning) {
    this.routePlanning = routePlanning;
  }



  public int getComponentUse() {
    return componentUse;
  }



  public void setComponentUse(int componentUse) {
    this.componentUse = componentUse;
  }




  public CombatShip getActiveShip() {
    return activeShip;
  }




  public void setActiveShip(CombatShip activeShip) {
    this.activeShip = activeShip;
  }

  
}
