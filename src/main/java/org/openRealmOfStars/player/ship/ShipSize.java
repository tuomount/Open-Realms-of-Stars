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
 * Ship sizes
 *
 */
public enum ShipSize {
  /**
   * Smallest and fastest ships in combat
   */
  SMALL,
  /**
   * Medium size ships
   */
  MEDIUM,
  /**
   * Large ships
   */
  LARGE,
  /**
   * Huge size ships. Slowest ships in combat
   */
  HUGE;

  /**
   * Get ShipSize index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case SMALL:
      return 0;
    case MEDIUM:
      return 1;
    case LARGE:
      return 2;
    case HUGE:
      return 3;
    default:
      return 0;
    }
  }

  /**
   * Get Mass by shipSize.
   * This is relative mass, so small is eight times smaller than huge.
   * Small: 1
   * Medium: 2
   * Large: 4
   * Huge: 8
   * @return int
   */
  public int getMass() {
    switch (this) {
    case SMALL:
      return 1;
    case MEDIUM:
      return 2;
    case LARGE:
      return 4;
    case HUGE:
      return 8;
    default:
      return 1;
    }
  }

  /**
   * Return ShipSize by index
   * @param index This must be between 0-3
   * @return Ship size
   */
  public static ShipSize getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return ShipSize.SMALL;
    case 1:
      return ShipSize.MEDIUM;
    case 2:
      return ShipSize.LARGE;
    case 3:
      return ShipSize.HUGE;
    default:
      return ShipSize.SMALL;
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case SMALL:
      return "Small";
    case MEDIUM:
      return "Medium";
    case LARGE:
      return "Large";
    case HUGE:
      return "Huge";
    default:
      return "Error - Unknown";
    }

  }

}
