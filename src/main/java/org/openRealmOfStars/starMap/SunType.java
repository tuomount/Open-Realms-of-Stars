package org.openRealmOfStars.starMap;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2023  Tuomo Untinen
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
* Sun type
*
*/
public enum SunType {
  /**
   * Red star with least radiation.
   */
  RED_STAR,
  /**
   * Blue star with most radiation.
   */
  BLUE_STAR,
  /**
   * Yellow star with medium radiation.
   */
  YELLOW_STAR;

  /**
   * Get random sun type.
   * @return Suntype
   */
  public static SunType getRandomType() {
    int value = DiceGenerator.getRandom(0, 2);
    switch (value) {
      default:
      case 0: return RED_STAR;
      case 1: return BLUE_STAR;
      case 2: return YELLOW_STAR;
    }
  }
}
