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
 * Planet's temperature type. Optiomal temperature allows space populate planet
 * more efficiently. Suboptiomal or wrong temperature prevents living there.
 *
 */
public enum TemperatureType {

  /** Completely frozen planet, not habitable planet. -273C - -225C */
  FROZEN,
  /** Artic planet, can contain caves or some hot springs, -225C - -100C */
  ARCTIC,
  /** Cold planet, which maybe have even warm season,
   * but mostly cold -99C - 5C*/
  COLD,
  /** Temperate planet, mostly nice place to be, there can be cold and warm
   *  places. 5C -24C
   */
  TEMPERATE,
  /** Warm planet, maybe the poles can be a cold. 25C - 40C*/
  TROPICAL,
  /** Hot planet, requires specialized flora and fauna to survive. 41C - 99C*/
  HOT,
  /** Planet with lot of volcanic activity and very hot places. No cool places
   * found. 100C - 500C
   */
  VOLCANIC,
  /** Unhabitable planet. over 500C */
  INFERNO;

  /**
   * Get temperature as an integer value.
   * @return int
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Get temperature type based on index.
   * @param index Index
   * @return TemperatureType
   */
  public static TemperatureType getByIndex(final int index) {
    if (index >= 0 && index < values().length) {
      return values()[index];
    } else {
      throw new IllegalArgumentException("Unknown temperature");
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case FROZEN: return "frozen";
      case ARCTIC: return "arctic";
      case COLD: return "cold";
      case TEMPERATE: return "temperate";
      case TROPICAL: return "tropical";
      case HOT: return "hot";
      case VOLCANIC: return "volcanic";
      case INFERNO: return "inferno";
      default:
        throw new IllegalArgumentException("Unknown temperature");
    }
  }

}
