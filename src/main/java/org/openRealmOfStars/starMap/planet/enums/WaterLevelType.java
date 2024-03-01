package org.openRealmOfStars.starMap.planet.enums;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 Tuomo Untinen
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
 * Enums for planet's water level types.
 * This tells how much water is on planet which affects on how much food
 * it produces.
 *
 */
public enum WaterLevelType {
  /** Planet without any water */
  BARREN,
  /** Desert planet, producing just one food */
  DESERT,
  /** Arid planet, planet is producing 2 foods */
  ARID,
  /** Humid planet, planet is producing 3 foods */
  HUMID,
  /** Planet of seas and lakes. Planet is producing 4 foods */
  MARINE,
  /** Planet of oceans. Planet is producing 5 foods */
  OCEAN;

  /**
   * Get water level as an integer value.
   * @return int
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Get water level type based on index.
   * @param index Index
   * @return WaterLevelType
   */
  public static WaterLevelType getByIndex(final int index) {
    if (index >= 0 && index < values().length) {
      return values()[index];
    }
    throw new IllegalArgumentException("Unknown water level type");
  }

  @Override
  public String toString() {
    switch (this) {
      case BARREN: return "barren";
      case DESERT: return "desert";
      case ARID: return "arid";
      case HUMID: return "humid";
      case MARINE: return "marine";
      case OCEAN: return "ocean";
      default:
        throw new IllegalArgumentException("Unknown water level type");
    }
  }

  /**
   * Get Water level type based on String
   * @param str Water Level as a String
   * @return WaterLevelType
   */
  public static WaterLevelType getByString(final String str) {
    for (WaterLevelType type : WaterLevelType.values()) {
      if (type.toString().equalsIgnoreCase(str)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unexpected water level type, "
        + str + ".");
  }
  /**
   * Get amount food planet produces.
   * @return Food amount
   */
  public int getPlanetFoodProd() {
    switch (this) {
      case BARREN: return 0;
      case DESERT: return 1;
      case ARID: return 2;
      case HUMID: return 3;
      case MARINE: return 4;
      case OCEAN: return 5;
      default:
        throw new IllegalArgumentException("Unknown water level type");
    }
  }
}
