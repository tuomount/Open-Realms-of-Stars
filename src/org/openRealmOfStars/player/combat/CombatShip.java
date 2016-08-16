package org.openRealmOfStars.player.combat;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.Ship;

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
 * Single ship in combat
 * 
 */

public class CombatShip {

  /**
   * Ship information
   */
  private Ship ship;
  
  /**
   * Ship's X coordinate in combat
   */
  private int x;

  /**
   * Ship's Y coordinate in combat
   */
  private int y;
  
  /**
   * Player whom owns the ship
   */
  private PlayerInfo player;
  
  /**
   * Constructor for Combat ship
   * @param ship Ship to put in combat
   * @param player Player who owns the ship
   * @param x Ship's X coordinate in combat map
   * @param y Ship's Y coordinate in combat map
   */
  public CombatShip(Ship ship, PlayerInfo player, int x, int y) {
    this.ship = ship;
    this.x = x;
    this.y = y;
    this.player = player;
  }

  public Ship getShip() {
    return ship;
  }

  public void setShip(Ship ship) {
    this.ship = ship;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public PlayerInfo getPlayer() {
    return player;
  }

  public void setPlayer(PlayerInfo player) {
    this.player = player;
  }
  
  
}
