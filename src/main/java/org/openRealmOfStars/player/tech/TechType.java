package org.openRealmOfStars.player.tech;

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
 * Enum for tech types
 *
 */

public enum TechType {
  /**
   *  Combat tech including weapons, defense turret, bombs etc
   */
  Combat,
  /**
   * Defense tech including armor, shields, shield generators
   */
  Defense,
  /**
   * Hull tech including new ship hulls
   */
  Hulls,
  /**
   * Improvement tech including planetary improvements
   */
  Improvements,
  /**
   * Propulsion tech including engines and powersources
   */
  Propulsion,
  /**
   * Electronics tech including scanners, jammer, cloaking devices etc.
   */
  Electrics;

  /**
   * Get Tech type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case Combat:
      return 0;
    case Defense:
      return 1;
    case Hulls:
      return 2;
    case Improvements:
      return 3;
    case Propulsion:
      return 4;
    case Electrics:
      return 5;
    default:
      throw new IllegalArgumentException("Unexpected Tech type!");
    }
  }

  /**
   * Return Tech Type by index
   * @param index This must be between 0-5
   * @return Tech Type
   */
  public static TechType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return TechType.Combat;
    case 1:
      return TechType.Defense;
    case 2:
      return TechType.Hulls;
    case 3:
      return TechType.Improvements;
    case 4:
      return TechType.Propulsion;
    case 5:
      return TechType.Electrics;
    default:
      throw new IllegalArgumentException("Unexpected Tech type!");
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case Combat:
      return "Combat";
    case Defense:
      return "Defense";
    case Hulls:
      return "Hulls";
    case Improvements:
      return "Improvements";
    case Propulsion:
      return "Propulsion";
    case Electrics:
      return "Electronics";
    default:
      return "Error - Unknown";
    }
  }

}
