package org.openRealmOfStars.player.combat;

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.StarMap;


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
 * All the combat data, containing ships in single list in initiative order
 * 
 */

public class Combat {

  
  /**
   * Maximum combat map size
   */
  public final static int MAX_X=9;
  /**
   * Maximum, combat map size
   */
  public final static int MAX_Y=9;
  /**
   * Ships in list in initiative order
   */
  private ArrayList<CombatShip> shipList;
  
  /**
   * Current ship index
   */
  private int shipIndex;
  
  /**
   * Cursor position X coordinate
   */
  private int cursorX;
  /**
   * Cursor position Y coordinate
   */
  private int cursorY;
  
  /**
   * Which component is in used
   */
  private int componentUse;

  /**
   * Combat animation
   */
  private CombatAnimation animation;
  
  /**
   * First player's fleet
   */
  private Fleet fleet1;
  /**
   * Second player's fleet
   */
  private Fleet fleet2;
  
  private PlayerInfo winner;
  
  private PlayerInfo info1;
  
  private PlayerInfo info2;
  
  /**
   * Build shipList in initiative order
   * @param fleet1 Player1 fleet
   * @param fleet2 Player2 fleet
   * @param info1 Player1 info
   * @param info2 Player2 Info
   */
  public Combat(Fleet fleet1, Fleet fleet2, PlayerInfo info1, PlayerInfo info2) {
    this.fleet1 = fleet1;
    this.fleet2 = fleet2;
    this.info1 = info1;
    this.info2 = info2;
    Ship[] ships = fleet1.getShips();
    int index = 0;
    shipList = new ArrayList<>();
    for (Ship ship : ships) {      
      ShipStat stat = info1.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfCombats(stat.getNumberOfCombats()+1);
      }
      CombatShip combatShp = new CombatShip(ship, info1, 
          getStartPos(index, 0, true), getStartPos(index, 0, false),false);
      shipList.add(combatShp);
      index++;
    }
    ships = fleet2.getShips();
    index = 0;
    for (Ship ship : ships) {
      ShipStat stat = info2.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfCombats(stat.getNumberOfCombats()+1);
      }
      CombatShip combatShp = new CombatShip(ship, info2, 
          getStartPos(index, 1, true), getStartPos(index, 1, false),true);
      shipList.add(combatShp);
      index++;
    }
    Collections.sort(shipList);
    index = 0;
    componentUse = -1;
    animation = null;
    winner = null;
  }
  
  /**
   * Get the winner. 
   * @return PlayerInfo who won or null
   */
  public PlayerInfo getWinner() {
    return winner;
  }
  
  /**
   * Is clear shot from shooter to target with used weapon
   * @param shooter Shooter's combat ship
   * @param target Target's combat Ship
   * @return True if shot is possible to shoot
   */
  public boolean isClearShot(CombatShip shooter, CombatShip target) {
    boolean result = false;
    ShipComponent weapon = shooter.getShip().getComponent(componentUse);
    if (weapon != null && weapon.isWeapon()) {  
      double sx = shooter.getX();
      double sy = shooter.getY();
      double endX = target.getX();
      double endY = target.getY();;
      double mx;
      double my;
      double dx = Math.abs(sx-endX);
      double dy = Math.abs(sy-endY);
      int distance = (int) dy;
      if (dx > dy) {
        distance = (int) dx;
      }
      if (distance > 0) {
        mx = (endX-sx)/distance;
        my = (endY-sy)/distance;
      } else {
        mx = 0;
        my = 0;
      }
      if (weapon.getWeaponRange() >= distance && distance > 0) {
        for (int i=0;i<distance+1;i++) {
          sx = sx +mx;
          sy = sy+my;
          int ix = (int) Math.round(sx);
          int iy = (int) Math.round(sy);
          if (isBlocked(ix,iy) && (ix != target.getX() || iy != target.getY())) {
            result = false;
            break;
          }
          if (ix == target.getX() && iy == target.getY()) {
            result = true;
          }
        }
      }

    }

    return result;
  }
  
  /**
   * Get most powerful ship opponent has or null
   * @param info Player Info who is doing the comparison.
   * @return CombatShip or null
   */
  public CombatShip getMostPowerfulShip(PlayerInfo info) {
    int power = 0;
    CombatShip result=null;
    for (CombatShip ship : shipList) {
      if (ship.getPlayer() != info) {
        if (ship.getShip().getTotalMilitaryPower()>power) {
          result =ship;
          power = result.getShip().getTotalMilitaryPower();
        }
      }
    }
    return result;
  }
  
  /**
   * Destroy one single ship from fleets and combat
   * @param ship Combat ship
   */
  public void destroyShip(CombatShip ship) {
    if (fleet1.isShipInFleet(ship.getShip())) {
      fleet1.removeShip(ship.getShip());
      ShipStat stat = animation.getShooter().getPlayer()
          .getShipStatByName(animation.getShooter().getShip().getName());
      if (stat != null) {
        stat.setNumberOfKills(stat.getNumberOfKills()+1);
      }
      stat = ship.getPlayer().getShipStatByName(ship.getShip().getName());
      if (stat != null) {
        stat.setNumberOfLoses(stat.getNumberOfLoses()+1);
        stat.setNumberOfInUse(stat.getNumberOfInUse()-1);
      }      
      shipList.remove(ship);
    } else if (fleet2.isShipInFleet(ship.getShip())) {
      fleet2.removeShip(ship.getShip());
      shipList.remove(ship);
    }
  }
  
  /**
   * Get the closest enemy ship
   * @param info Player info who is doing the comparison
   * @param center Where to start looking the closet one
   * @return CombatShip or null
   */
  public CombatShip getClosestEnemyShip(PlayerInfo info, CombatShip center) {
    double maxDist = 0;
    CombatShip result=null;
    for (CombatShip ship : shipList) {
      if (ship.getPlayer() != info) {
        double dist = StarMap.getDistance(center.getX(), center.getY(), ship.getX(),
            ship.getY());
        if (dist >maxDist) {
          result =ship;
          maxDist = dist;
        }
      }
    }
    return result;

  }
  
  /**
   * Set cursor position
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setCursorPos(int x,int y) {
    cursorX = x;
    cursorY = y;
  }
  
  /**
   * Is coordinate valid position on combat map
   * @param x X Coordinate
   * @param y Y Coordinate
   * @return true if valid otherwise false
   */
  public boolean isValidPos(int x, int y) {
    if (x >= 0 && y >= 0 && x < MAX_X && y < MAX_Y) {
      return true;
    }
    return false;
  }
  
  /**
   * Is coordinate blocked or not
   * @param x X coordinate
   * @param y Y coordinate
   * @return True if blocked false otherwise
   */
  public boolean isBlocked(int x, int y) {
    for (CombatShip ship : shipList) {
      if (x == ship.getX() && y == ship.getY()) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get Cursor X coordinate
   * @return X coordinate
   */
  public int getCursorX() {
    return cursorX;
  }

  /**
   * Get Cursor Y coordinate
   * @return Y coordinate
   */
  public int getCursorY() {
    return cursorY;
  }

  /**
   * Get Current Ship
   * @return CombatShip
   */
  public CombatShip getCurrentShip() {
    if (shipList.size() <= shipIndex) {
      shipIndex = 0;
    }
    return shipList.get(shipIndex);
  }
  
  public void nextShip() {
    shipIndex++;
    if (shipList.size() <= shipIndex) {
      shipIndex = 0;
    }
    CombatShip ship = getCurrentShip();
    ship.reInitShipForRound();
  }
  
  /**
   * Get ship from combat coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @return CombatShip
   */
  public CombatShip getShipFromCoordinate(int x, int y) {
    for (CombatShip ship : shipList) {
      if (ship.getX() == x && ship.getY() == y) {
        return ship;
      }
    }
    return null;
  }
  
  
  /**
   * Get starting position for ships
   * @param index Ship Index in fleet
   * @param team Which player it belong 0 on bottom, 1 on top
   * @param x X coordinate or Y coordinate
   * @return Coordinate
   */
  private int getStartPos(int index, int team, boolean x) {
    if (team == 0) {
      switch (index) {
      case 0: {
        if (x) {
          return 4;
        } else {
          return 7;
        }
      }
      case 1: {
        if (x) {
          return 3;
        } else {
          return 7;
        }
      }
      case 2: {
        if (x) {
          return 5;
        } else {
          return 7;
        }
      }
      case 3: {
        if (x) {
          return 2;
        } else {
          return 7;
        }
      }
      case 4: {
        if (x) {
          return 6;
        } else {
          return 7;
        }
      }
      case 5: {
        if (x) {
          return 4;
        } else {
          return 8;
        }
      }
      case 6: {
        if (x) {
          return 1;
        } else {
          return 7;
        }
      }
      case 7: {
        if (x) {
          return 7;
        } else {
          return 7;
        }
      }
      case 8: {
        if (x) {
          return 3;
        } else {
          return 8;
        }
      }
      case 9: {
        if (x) {
          return 5;
        } else {
          return 8;
        }
      }
      case 10: {
        if (x) {
          return 2;
        } else {
          return 8;
        }
      }
      case 11: {
        if (x) {
          return 6;
        } else {
          return 8;
        }
      }
      case 12: {
        if (x) {
          return 0;
        } else {
          return 7;
        }
      }
      case 13: {
        if (x) {
          return 8;
        } else {
          return 7;
        }
      }
      case 14: {
        if (x) {
          return 1;
        } else {
          return 8;
        }
      }
      case 15: {
        if (x) {
          return 7;
        } else {
          return 8;
        }
      }
      case 16: {
        if (x) {
          return 0;
        } else {
          return 8;
        }
      }
      case 17: {
        if (x) {
          return 8;
        } else {
          return 8;
        }
      }
      }
    } else {
      // Second team on top side
      switch (index) {
      case 0: {
        if (x) {
          return 4;
        } else {
          return 1;
        }
      }
      case 1: {
        if (x) {
          return 3;
        } else {
          return 1;
        }
      }
      case 2: {
        if (x) {
          return 5;
        } else {
          return 1;
        }
      }
      case 3: {
        if (x) {
          return 2;
        } else {
          return 1;
        }
      }
      case 4: {
        if (x) {
          return 6;
        } else {
          return 1;
        }
      }
      case 5: {
        if (x) {
          return 4;
        } else {
          return 0;
        }
      }
      case 6: {
        if (x) {
          return 1;
        } else {
          return 1;
        }
      }
      case 7: {
        if (x) {
          return 7;
        } else {
          return 1;
        }
      }
      case 8: {
        if (x) {
          return 3;
        } else {
          return 0;
        }
      }
      case 9: {
        if (x) {
          return 5;
        } else {
          return 0;
        }
      }
      case 10: {
        if (x) {
          return 2;
        } else {
          return 0;
        }
      }
      case 11: {
        if (x) {
          return 6;
        } else {
          return 0;
        }
      }
      case 12: {
        if (x) {
          return 0;
        } else {
          return 1;
        }
      }
      case 13: {
        if (x) {
          return 8;
        } else {
          return 1;
        }
      }
      case 14: {
        if (x) {
          return 1;
        } else {
          return 0;
        }
      }
      case 15: {
        if (x) {
          return 7;
        } else {
          return 0;
        }
      }
      case 16: {
        if (x) {
          return 0;
        } else {
          return 0;
        }
      }
      case 17: {
        if (x) {
          return 8;
        } else {
          return 0;
        }
      }
      }
      
    }
    // This should not happen
    return 4;
  }

  /**
   * Hand winner fleet stats
   * @param fleet
   * @param info
   */
  private void handleWinner(Fleet fleet, PlayerInfo info) {
    for (Ship ship : fleet.getShips()) {
      ShipStat stat = info.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfVictories(stat.getNumberOfVictories()+1);
      }
    }
  }
  
  /**
   * Handle End combat
   */
  public void handleEndCombat() {
    if (winner != null && info1 == winner) {
      handleWinner(fleet1, info1);
      fleet1.setPos(fleet2.getX(), fleet2.getY());
      int index =info2.Fleets().getIndexByName(fleet2.getName());
      if (index != -1) {
        info2.Fleets().remove(index);
      }
    }
    if (winner != null && info2 == winner) {
      handleWinner(fleet2, info2);
      fleet2.setPos(fleet1.getX(), fleet1.getY());
      int index =info1.Fleets().getIndexByName(fleet1.getName());
      if (index != -1) {
        info1.Fleets().remove(index);
      }
    }
  }
  
  /**
   * Is Combat over or not yet
   * @return True if combat is over
   */
  public boolean isCombatOver() {
    PlayerInfo first = null;
    boolean moreThanOnePlayer = false;
    boolean militaryPower = false;
    for (CombatShip ship : shipList) {
      if (first == null) {
        first = ship.getPlayer();
      }
      if (first != ship.getPlayer()) {
        moreThanOnePlayer = true;
      }
      if (ship.getShip().getTotalMilitaryPower()>0) {
        militaryPower = true;
      }
    }
    if (!moreThanOnePlayer) {
      winner = first;
      // There is no combat with one player
      return true;
    }
    if (!militaryPower) {
      // No reason to continue with non military ships
      return true;
    }
    return false;
  }
  
  public int getComponentUse() {
    return componentUse;
  }

  public void setComponentUse(int componentUse) {
    this.componentUse = componentUse;
  }

  public CombatAnimation getAnimation() {
    return animation;
  }

  public void setAnimation(CombatAnimation animation) {
    this.animation = animation;
  }
  
}
