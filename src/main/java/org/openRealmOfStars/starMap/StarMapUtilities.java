package org.openRealmOfStars.starMap;

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
 * Star map utilities
 *
 */
public class StarMapUtilities {

  /**
   * Check if there can be fitted solar system
   * @param solarSystem where to check solar systems
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   * @param sunDistance distance between suns
   * @return How many solar system sectors found
   */
  public static int isSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int maxX, final int maxY, final int sunDistance) {
    int result = 0;
    for (int y = -sunDistance; y < sunDistance; y++) {
      for (int x = -sunDistance; x < sunDistance; x++) {
        if (x + sx >= 0 && y + sy >= 0 && x + sx < maxX && y + sy < maxY) {
          result = result + solarSystem[sx + x][sy + y];
        }
      }
    }
    return result;
  }

  /**
   * Get how full universe is solar systems
   * @param solarSystem Solar system map
   * @param maxX Maximum size of X
   * @param maxY Maximum size of Y
   * @return How many percent universe is full
   */
  public static int getSystemFullness(final int[][] solarSystem, final int maxX,
      final int maxY) {
    int result = 0;
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (solarSystem[x][y] == 1) {
          result++;
        }
      }
    }
    int total = (maxX - 2 * StarMapStatics.SOLARSYSTEMWIDTH)
        * (maxY - 2 * StarMapStatics.SOLARSYSTEMWIDTH);
    result = result * 100 / total;
    return result;
  }

  /**
   * Set solar system on solar system map
   * @param solarSystem Map where place solar system
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   */
  public static void setSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int maxX, final int maxY) {
    for (int y = -StarMapStatics.SOLARSYSTEMWIDTH; y < StarMapStatics.SOLARSYSTEMWIDTH; y++) {
      for (int x = -StarMapStatics.SOLARSYSTEMWIDTH; x < StarMapStatics.SOLARSYSTEMWIDTH; x++) {
        if (x + sx >= 0 && y + sy >= 0 && x + sx < maxX && y + sy < maxY) {
          solarSystem[sx + x][sy + y] = 1;
        }
      }
    }

  }

}
