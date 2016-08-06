package org.openRealmOfStars.player.fleet;

import java.util.ArrayList;

import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Route;

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
 * Fleet for handling list of ships
 * 
 */

public class Fleet {
  
  /**
   * List of ships in fleet
   */
  private ArrayList<Ship> ships;  
  
  /**
   * Fleet's X coordinate
   */
  private int x;
  
  /**
   * Fleet's y coordinate
   */
  private int y;
  
  /**
   * Fleet name
   */
  private String name;
  
  /**
   * How many moves fleet has left
   */
  public int movesLeft;
  
  /**
   * Route for fleet to move with FLT speed
   */
  private Route route;
  
  /**
   * Constructor for fleet
   */
  public Fleet(Ship firstShip,int x, int y) {
    ships = new ArrayList<>();
    ships.add(firstShip);
    setPos(x, y);
    setName("Fleet #-1");
    setRoute(null);
  }
  
  /**
   * Add new ship to list
   * @param ship to add
   */
  public void addShip(Ship ship) {
    if (ship != null) {
      ships.add(ship);
    }
  }

  /**
   * Remove ship fro list
   * @param ship to remove
   */
  public void removeShip(Ship ship) {
    if (ship != null) {
      ships.remove(ship);
    }
  }
  
  /**
   * Get the number of ships in list
   * @return Number of ships in list
   */
  public int getNumberOfShip() {
    return ships.size();
  }
  
  /**
   * Get ship by index or return null
   * @param index to find
   * @return Ship or null
   */
  public Ship getShipByIndex(int index) {
    if (index >= 0 && index < ships.size()) {
      return ships.get(index);
    } 
    return null;
  }

  /**
   * Get all the fleet ships
   * @return Ships in array
   */
  public Ship[] getShips() {
    return ships.toArray(new Ship[ships.size()]);
  }
  
  /**
   * Get fleet's X coordinate in star map
   * @return X coordinate
   */
  public int getX() {
    return x;    
  }
  
  /**
   * Get fleet's Y coordinate in star map
   * @return Y coordinate
   */
  public int getY() {
    return y;
  }
  
  /**
   * Set fleet's Coordinates
   * @param x
   * @param y
   */
  public void setPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get first ship from the fleet
   * @return Ship
   */
  public Ship getFirstShip() {
    return ships.get(0);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Get Fleet speed
   * @return Speed
   */
  public int getFleetSpeed() {
    int speed = 999;
    for (Ship ship : ships) {
      int shipSpeed = ship.getSpeed();
      if (shipSpeed < speed) {
        speed = shipSpeed;
      }
    }
    if (speed == 999) {
      speed = 0;
    }
    return speed;
  }
  
  /**
   * Get Fleet FTL speed
   * @return Speed
   */
  public int getFleetFtlSpeed() {
    int speed = 999;
    for (Ship ship : ships) {
      int shipSpeed = ship.getFtlSpeed();
      if (shipSpeed < speed) {
        speed = shipSpeed;
      }
    }
    if (speed == 999) {
      speed = 0;
    }
    return speed;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append("\n");
    sb.append("Speed: ");
    sb.append(getFleetSpeed());
    sb.append(" FTL: ");
    sb.append(getFleetFtlSpeed());
    sb.append("\n");
    for (Ship ship : ships) {
      sb.append(ship.getName());
      sb.append("\n");
    }
    return sb.toString();
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route route) {
    this.route = route;
  }
  
  /**
   * Get total amount of metal cargo
   * @return Metal cargo for fleet
   */
  public int getTotalCargoMetal() {
    int result = 0;
    for (Ship ship : ships) {
      result = result +ship.getMetal();
    }
    return result;
  }

  /**
   * Get total amount of colonist cargo
   * @return Colonist cargo for fleet
   */
  public int getTotalCargoColonist() {
    int result = 0;
    for (Ship ship : ships) {
      result = result +ship.getColonist();
    }
    return result;
  }

  /**
   * Get total free cargo space for metal
   * @return free cargo space
   */
  public int getFreeSpaceForMetal() {
    int result = 0;
    for (Ship ship : ships) {
      result = result +ship.getFreeCargoMetal();
    }
    return result;
  }

  /**
   * Get total free cargo space for colonist
   * @return free cargo space
   */
  public int getFreeSpaceForColonist() {
    int result = 0;
    for (Ship ship : ships) {
      result = result +ship.getFreeCargoColonists();
    }
    return result;
  }
  
  /** 
   * Add single colonist for ship
   */
  public void addColonist() {
    for (Ship ship : ships) {
      if (ship.getFreeCargoColonists()>0) {
        ship.setColonist(ship.getColonist()+1);
        return;
      }
    }
  }

  /** 
   * Add 10 metal for ship
   */
  public void addMetal() {
    for (Ship ship : ships) {
      if (ship.getFreeCargoMetal()>9) {
        ship.setMetal(ship.getMetal()+10);
        return;
      }
    }
  }

  /** 
   * Remove single colonist for ship
   */
  public void removeColonist() {
    for (Ship ship : ships) {
      if (ship.getColonist() > 0) {
        ship.setColonist(ship.getColonist()-1);
        return;
      }
    }
  }
  
  /**
   * Get first colony ship from the fleet
   * @return Colony ship or null
   */
  public Ship getColonyShip() {
    for (Ship ship : ships) {
      if (ship.getColonist() > 0 && ship.isColonyShip()) {
        return ship;
      }
    }
    return null;
  }

  /** 
   * Remove 10 metal for ship
   */
  public void removeMetal() {
    for (Ship ship : ships) {
      if (ship.getMetal()>9) {
        ship.setMetal(ship.getMetal()-10);
        return;
      }
    }
  }

}
