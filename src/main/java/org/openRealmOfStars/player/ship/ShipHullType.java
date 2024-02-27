package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
 */

/**
 * Ship hull Type
 */
public enum ShipHullType {
  /**
   * Normal ship with weapons, no cargo or privateering module allowed
   */
  NORMAL,
  /**
   * No weapons or privateering module allowed, but cargo or colony/troop
   * module allowed
   */
  FREIGHTER,
  /**
   * No weapons or cargo allowed, but one faster FTL
   */
  PROBE,
  /**
   * Cargo or privateering module allowed but starbase modules
   * are allowed
   */
  STARBASE,
  /**
   * Weapons, cargo, colony/troop module allowed and privateering module too
   */
  PRIVATEER,
  /**
   * No enginesCargo or privateering module allowed but starbase modules
   * are allowed. These cannot move and are always built on planet.
   */
  ORBITAL;


  /**
   * Get ShipHullType index
   * @return int
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Return ShipHullType by index
   * @param index This must be between 0-4
   * @return Ship hull type
   */
  public static ShipHullType getTypeByIndex(final int index) {
    if (index >= 0 && index < values().length) {
      return values()[index];
    } else {
      throw new IllegalArgumentException("Unexpected ship hull type!");
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case NORMAL:
      return "Normal";
    case FREIGHTER:
      return "Freighter";
    case PROBE:
      return "Probe";
    case STARBASE:
      return "Starbase";
    case PRIVATEER:
      return "Privateer";
    case ORBITAL:
      return "Orbital";
    default:
      throw new IllegalArgumentException("Unexpected ship hull type!");
    }
  }

  /**
   * Get Hull type description as a string
   * @return String
   */
  public String getDescription() {
    switch (this) {
    case NORMAL:
      return toString() + ", All weapons allowed. No cargo.";
    case FREIGHTER:
      return toString() + ", no weapons allowed. Cargo ship";
    case PROBE:
      return toString() + ", no weapons allowed. FTL speed faster.";
    case STARBASE:
      return toString() + ", Starbase components";
    case PRIVATEER:
      return toString() + ", Weapons, cargo and privateering.";
    case ORBITAL:
      return toString() + ", No engine, starbase components";
    default:
      throw new IllegalArgumentException("Unexpected ship hull type!");
    }
  }

}
