package org.openRealmOfStars.player.ship;


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
 * Ship hull factory
 * 
 */
public class ShipHullFactory {

  /**
   * Current maximum ShipHull for whole game.
   * Remember to increase this when new ship hull is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_SHIPHULL = 31;
  

  /**
   * Create ShipHull with matching name
   * @param name Ship hull name
   * @return ShipHull or null if not found
   */
  public static ShipHull createByName(String name) {
    ShipHull tmp = null;
    for (int i=0;i<MAX_SHIPHULL;i++) {
      tmp = createShipHull(i);
      if ((tmp != null) && (tmp.getName().equalsIgnoreCase(name))) {
        return tmp;
      }
    }
    return null;
  }

  
  /**
   * Create ship hull with index
   * @param index For creating a new ship hull
   * @return ShipHull if index found otherwise null
   */
  public static ShipHull createShipHull(int index) {
    ShipHull tmp = null;
    if (index == 0) {
      tmp = new ShipHull(index, "Scout Mk1", 4, 1, ShipHullType.NORMAL,
          ShipSize.SMALL,5,5);
      return tmp;
    }
    if (index == 1) {
      tmp = new ShipHull(index, "Destroyer Mk1", 6, 1, ShipHullType.NORMAL,
          ShipSize.MEDIUM,6,8);
      return tmp;
    }
    if (index == 2) {
      tmp = new ShipHull(index, "Colony", 4, 1, ShipHullType.FREIGHTER,
          ShipSize.MEDIUM,4,6);
      return tmp;
    }
    if (index == 3) {
      tmp = new ShipHull(index, "Probe", 4, 1, ShipHullType.PROBE, 
          ShipSize.SMALL,2,2);
      return tmp;
    }
    if (index == 4) {
      tmp = new ShipHull(index, "Small freighter", 6, 1, ShipHullType.FREIGHTER,
          ShipSize.MEDIUM,4,6);
      return tmp;
    }
    if (index == 5) {
      tmp = new ShipHull(index, "Small starbase Mk1", 4, 1, ShipHullType.STARBASE,
          ShipSize.SMALL,6,6);
      return tmp;
    }
    if (index == 6) {
      tmp = new ShipHull(index, "Destroyer Mk2", 6, 2, ShipHullType.NORMAL,
          ShipSize.MEDIUM,6,10);
      return tmp;
    }
    if (index == 7) {
      tmp = new ShipHull(index, "Small starbase Mk2", 4, 2, ShipHullType.STARBASE,
          ShipSize.SMALL,6,8);
      return tmp;
    }
    if (index == 8) {
      tmp = new ShipHull(index, "Corvette Mk1", 5, 1, ShipHullType.NORMAL,
          ShipSize.SMALL,5,6);
      return tmp;
    }
    if (index == 9) {
      tmp = new ShipHull(index, "Medium freighter", 8, 1, ShipHullType.FREIGHTER,
          ShipSize.LARGE,6,12);
      return tmp;
    }
    if (index == 10) {
      tmp = new ShipHull(index, "Medium starbase", 6, 2, ShipHullType.STARBASE,
          ShipSize.MEDIUM,8,10);
      return tmp;
    }
    if (index == 11) {
      tmp = new ShipHull(index, "Scout Mk2", 4, 2, ShipHullType.NORMAL,
          ShipSize.SMALL,4,8);
      return tmp;
    }
    if (index == 12) {
      tmp = new ShipHull(index, "Cruiser", 8, 2, ShipHullType.NORMAL,
          ShipSize.LARGE,8,14);
      return tmp;
    }
    if (index == 13) {
      tmp = new ShipHull(index, "Battleship Mk1", 10, 2, ShipHullType.NORMAL, 
          ShipSize.HUGE,20,20);
      return tmp;
    }
    if (index == 14) {
      tmp = new ShipHull(index, "Privateer Mk1", 6, 2, ShipHullType.PRIVATEER,
          ShipSize.MEDIUM,8,8);
      return tmp;
    }
    if (index == 15) {
      tmp = new ShipHull(index, "Large freighter", 10, 2, ShipHullType.FREIGHTER,
          ShipSize.HUGE,12,20);
      return tmp;
    }
    if (index == 16) {
      tmp = new ShipHull(index, "Large starbase", 8, 3, ShipHullType.STARBASE,
          ShipSize.LARGE,15,15);
      return tmp;
    }
    if (index == 17) {
      tmp = new ShipHull(index, "Corvette Mk2", 5, 2, ShipHullType.NORMAL,
          ShipSize.SMALL,6,8);
      return tmp;
    }
    if (index == 18) {
      tmp = new ShipHull(index, "Battle cruiser Mk2", 9, 2, ShipHullType.NORMAL,
          ShipSize.LARGE,12,12);
      return tmp;
    }
    if (index == 19) {
      tmp = new ShipHull(index, "Privateer Mk2", 8, 2, ShipHullType.PRIVATEER,
          ShipSize.LARGE,12,14);
      return tmp;
    }
    if (index == 20) {
      tmp = new ShipHull(index, "Scout Mk3", 4, 3, ShipHullType.NORMAL,
          ShipSize.SMALL,8,10);
      return tmp;
    }
    if (index == 21) {
      tmp = new ShipHull(index, "Massive freighter", 12, 2, ShipHullType.FREIGHTER,
          ShipSize.HUGE,14,25);
      return tmp;
    }
    if (index == 22) {
      tmp = new ShipHull(index, "Massive starbase", 10, 4, ShipHullType.STARBASE,
          ShipSize.HUGE,25,25);
      return tmp;
    }
    if (index == 23) {
      tmp = new ShipHull(index, "Corvette Mk3", 5, 3, ShipHullType.NORMAL,
          ShipSize.SMALL,9,10);
      return tmp;
    }
    if (index == 24) {
      tmp = new ShipHull(index, "Destroyer Mk3", 6, 3, ShipHullType.NORMAL,
          ShipSize.MEDIUM,10,10);
      return tmp;
    }
    if (index == 25) {
      tmp = new ShipHull(index, "Battleship Mk2", 10, 3, ShipHullType.NORMAL,
          ShipSize.HUGE,20,25);
      return tmp;
    }
    if (index == 26) {
      tmp = new ShipHull(index, "Privateer Mk3", 8, 3, ShipHullType.PRIVATEER,
          ShipSize.LARGE,15,18);
      return tmp;
    }
    if (index == 27) {
      tmp = new ShipHull(index, "Battle cruiser Mk3", 9, 3, ShipHullType.NORMAL,
          ShipSize.LARGE,18,18);
      return tmp;
    }
    if (index == 28) {
      tmp = new ShipHull(index, "Scout Mk4", 4, 4, ShipHullType.NORMAL,
          ShipSize.SMALL,10,10);
      return tmp;
    }
    if (index == 29) {
      tmp = new ShipHull(index, "Destroyer Mk4", 6, 4, ShipHullType.NORMAL,
          ShipSize.MEDIUM,12,12);
      return tmp;
    }
    if (index == 30) {
      tmp = new ShipHull(index, "Capital ship", 10, 4, ShipHullType.NORMAL,
          ShipSize.HUGE,30,30);
      return tmp;
    }

    return tmp;
  }

}
