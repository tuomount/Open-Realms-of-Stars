package org.openRealmOfStars.player.combat;

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
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
   * Build shipList in initiative order
   * @param fleet1 Player1 fleet
   * @param fleet2 Player2 fleet
   * @param info1 Player1 info
   * @param info2 Player2 Info
   */
  public Combat(Fleet fleet1, Fleet fleet2, PlayerInfo info1, PlayerInfo info2) {
    Ship[] ships = fleet1.getShips();
    int index = 0;
    shipList = new ArrayList<>();
    for (Ship ship : ships) {      
      CombatShip combatShp = new CombatShip(ship, info1, 
          getStartPos(index, 0, true), getStartPos(index, 0, false),false);
      shipList.add(combatShp);
      index++;
    }
    ships = fleet2.getShips();
    index = 0;
    for (Ship ship : ships) {
      CombatShip combatShp = new CombatShip(ship, info2, 
          getStartPos(index, 1, true), getStartPos(index, 1, false),true);
      shipList.add(combatShp);
      index++;
    }
    Collections.sort(shipList);
    index = 0;    
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
    return shipList.get(shipIndex);
  }
  
  public void nextShip() {
    shipIndex++;
    if (shipList.size() == shipIndex) {
      shipIndex = 0;
    }
    CombatShip ship = getCurrentShip();
    ship.setMovesLeft(ship.getShip().getTacticSpeed());
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
  
}
