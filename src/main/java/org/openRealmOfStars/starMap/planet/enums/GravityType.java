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
 * Gravity types for planets.
 */
public enum GravityType {
  /**
   * Low gravity, most like with small and medium planets.
   */
  LOW_GRAVITY,
  /**
   * Normal gravity, most like with medium and large planets.
   */
  NORMAL_GRAVITY,
  /**
   * High gravity, most like with large and huge planets.
   */
  HIGH_GRAVITY;

  /**
   * Get gravity as an integer value.
   * @return int
   */
  public int getIndex() {
    switch (this) {
      case LOW_GRAVITY: return 1;
      case NORMAL_GRAVITY: return 2;
      case HIGH_GRAVITY: return 3;
      default: throw new IllegalArgumentException("Unknown gravity");
    }
  }
}
