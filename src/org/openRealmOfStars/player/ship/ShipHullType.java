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
 * Ship hull Type
 * 
 */
public enum ShipHullType {
  /**
   * Normal ship with weapons, no cargo or privateering module allowed
   */
  NORMAL,
  /**
   * No weapons or privateering module allowed, but cargo or colony/troop module allowed
   */
  FREIGHTER,
  /**
   * No weapons or cargo allowed, but one faster FTL
   */
  PROBE,
  /**
   * No engines, cargo or privateering module allowed but starbase modules are allowed
   */
  STARBASE,
  /**
   * Weapons, cargo, colony/troop module allowed and privateering module too
   */
  PRIVATEER;
  
  /**
   * Get ShipHullType index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case NORMAL: return 0;
    case FREIGHTER: return 1;
    case PROBE: return 2;
    case STARBASE: return 3;
    case PRIVATEER: return 4;
    }
    return 0;
  }
  
  /**
   * Return ShipHullType by index
   * @param index This must be between 0-4
   * @return Ship hull type
   */
  public static ShipHullType getTypeByIndex(int index) {
    switch (index) {
    case 0: return ShipHullType.NORMAL;
    case 1: return ShipHullType.FREIGHTER;
    case 2: return ShipHullType.PROBE;
    case 3: return ShipHullType.STARBASE;
    case 4: return ShipHullType.PRIVATEER;
    }
    return ShipHullType.NORMAL;
  }

  @Override
  public String toString() {
    switch (this) {
    case NORMAL: return "Normal";
    case FREIGHTER: return "Freighter";
    case PROBE: return "Probe";
    case STARBASE: return "Starbase";
    case PRIVATEER: return "Privateer";
    }
    return "Error - Unknown";

  }

}
