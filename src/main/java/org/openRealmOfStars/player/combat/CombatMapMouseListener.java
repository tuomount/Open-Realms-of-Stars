package org.openRealmOfStars.player.combat;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.starMap.Coordinate;
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

public class CombatMapMouseListener extends MouseAdapter
    implements MouseMotionListener {

  /**
   * Combat for mouse listener
   */
  private Combat combat;

  /**
   * Map panel which to use
   */
  private MapPanel mapPanel;

  /**
   * Info panel next to map panel
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
   * @param combat Combat
   * @param panel Map Panel where Star Map is being drawn.
   * @param battleInfoPanel Battle Info Panel
   */
  public CombatMapMouseListener(final Combat combat, final MapPanel panel,
      final BattleInfoPanel battleInfoPanel) {
    this.combat = combat;
    this.mapPanel = panel;
    this.battleInfoPanel = battleInfoPanel;
    this.componentUse = -1;
    this.shipDamage = null;
  }

  /**
   * Last know coordinates
   */
  private PixelsToMapCoordinate coord;

  /**
   * Active ship under mouse cursor
   */
  private CombatShip activeShip;

  /**
   * ShipDamage information
   */
  private ShipDamage shipDamage;

  @Override
  public void mouseExited(final MouseEvent e) {
    coord = null;
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    Coordinate center = new Coordinate(mapPanel.getLastDrawnX(),
        mapPanel.getLastDrawnY());
    coord = new PixelsToMapCoordinate(center, e.getX(), e.getY(),
        mapPanel.getOffsetX(), mapPanel.getOffsetY(), mapPanel.getViewPointX(),
        mapPanel.getViewPointY(), true);
    if (!coord.isOutOfPanel()) {
      combat.setCursorPos(coord.getMapX(), coord.getMapY());
      // battleInfoPanel.showShip(combat.getCurrentShip().getShip());
      activeShip = combat.getShipFromCoordinate(coord.getMapX(),
          coord.getMapY());
    }
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    Coordinate center = new Coordinate(mapPanel.getLastDrawnX(),
        mapPanel.getLastDrawnY());
    coord = new PixelsToMapCoordinate(center, e.getX(), e.getY(),
        mapPanel.getOffsetX(), mapPanel.getOffsetY(), mapPanel.getViewPointX(),
        mapPanel.getViewPointY(), true);
    if (!coord.isOutOfPanel() && combat.getAnimation() == null) {
      if (componentUse != -1) {
        CombatShip ship = combat.getCurrentShip();
        ShipComponent weapon = ship.getShip().getComponent(componentUse);
        if (weapon != null && weapon.isWeapon()
            && !ship.isComponentUsed(componentUse)) {
          CombatShip target = combat.getShipFromCoordinate(combat.getCursorX(),
              combat.getCursorY());
          if (target != null
              && combat.isClearShot(combat.getCurrentShip(), target)) {
            int accuracy = ship.getShip().getHitChance(weapon)
                + ship.getBonusAccuracy();
            accuracy = accuracy - target.getShip().getDefenseValue();
            shipDamage = new ShipDamage(1, "Attack missed!");
            if (DiceGenerator.getRandom(1, 100) <= accuracy) {
              shipDamage = target.getShip().damageBy(weapon);
            }
            shipDamage.ready();
            combat.setAnimation(new CombatAnimation(ship, target, weapon,
                shipDamage.getValue()));
            ship.useComponent(componentUse);
            battleInfoPanel.useComponent(componentUse);
            componentUse = -1;
            combat.setComponentUse(-1);
          }
        }
      } else {
        Coordinate currentShipCoordinate = new Coordinate(
            combat.getCurrentShip().getX(), combat.getCurrentShip().getY());
        Coordinate coordinate = new Coordinate(coord.getMapX(),
            coord.getMapY());
        int distance = (int) currentShipCoordinate.calculateDistance(
            coordinate);
        if (distance == 1 && !combat.isBlocked(coord.getMapX(),
            coord.getMapY())
            && combat.getCurrentShip().getMovesLeft() > 0) {
          SoundPlayer.playEngineSound();
          combat.getCurrentShip().setX(coord.getMapX());
          combat.getCurrentShip().setY(coord.getMapY());
          combat.getCurrentShip()
              .setMovesLeft(combat.getCurrentShip().getMovesLeft() - 1);
        }
      }
    }

  }

  /**
   * Is Route being planned
   * @return True if route is being planned by player
   */
  public boolean isRoutePlanning() {
    return routePlanning;
  }

  /**
   * Set route planning
   * @param routePlanning boolean
   */
  public void setRoutePlanning(final boolean routePlanning) {
    this.routePlanning = routePlanning;
  }

  /**
   * Which component was used.
   * @return Component index or -1 if not used
   */
  public int getComponentUse() {
    return componentUse;
  }

  /**
   * Set which component was used
   * @param componentUse Component index or -1 if not used
   */
  public void setComponentUse(final int componentUse) {
    this.componentUse = componentUse;
  }

  /**
   * Get currently active ship
   * @return Combat Ship
   */
  public CombatShip getActiveShip() {
    return activeShip;
  }

  /**
   * Set Active ship
   * @param activeShip Combat ship
   */
  public void setActiveShip(final CombatShip activeShip) {
    this.activeShip = activeShip;
  }

  /**
   * Get Ship damage
   * @return ShipDamge
   */
  public ShipDamage getShipDamage() {
    return shipDamage;
  }

  /**
   * Set ship damage
   * @param shipDamage ShipDamage
   */
  public void setShipDamage(final ShipDamage shipDamage) {
    this.shipDamage = shipDamage;
  }

}
