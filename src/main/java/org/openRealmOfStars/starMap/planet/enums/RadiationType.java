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
 * Planet's radiation type.
 *
 */
public enum RadiationType {

  /** No radiation on planet. */
  NO_RADIATION,
  /** Low radiation on planet */
  LOW_RADIATION,
  /** High radiation */
  HIGH_RADIATION,
  /** Very high radiation */
  VERY_HIGH_RAD;

  /**
   * Get radiation type as an integer value.
   * @return int
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Get radiation type based on index.
   * @param index Index
   * @return RadiationType
   */
  public static RadiationType getByIndex(final int index) {
    if (index >= 0 && index < values().length) {
      return values()[index];
    } else {
      throw new IllegalArgumentException("Unknown radiation type");
    }
  }

  /**
   * Food amount from radiosynthesis.
   * @return Food amount
   */
  public int getRadiosynthesisFood() {
    switch (this) {
      case NO_RADIATION: return 1;
      case LOW_RADIATION: return 4;
      case HIGH_RADIATION: return 7;
      case VERY_HIGH_RAD: return 10;
      default: throw new IllegalArgumentException("Unknown radiation type");
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case NO_RADIATION: return "no radiation";
      case LOW_RADIATION: return "low radiation";
      case HIGH_RADIATION: return "high radiation";
      case VERY_HIGH_RAD: return "very high radiation";
      default:
        throw new IllegalArgumentException("Unknown radiation type");
    }
  }

}
