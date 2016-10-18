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
   * Calculate distance between two coordinates
   * @param x1 first coordinate's X
   * @param y1 first coordinate's Y
   * @param x2 second coordinate's X
   * @param y2 second coordinate's Y
   * @return distance as double
   */
  public static double getDistance(int x1,int y1, int x2, int y2) {
    double result = 0;
    int mx = Math.abs(x2-x1);
    int my = Math.abs(y2-y1);
    result = Math.sqrt(mx*mx+my*my);
    return result;
  }

  /**
   * Check if there can be fitted solar system
   * @param solarSystem where to check solar systems
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   * @return How many solar system sectors found
   */
  public static int isSolarSystem(int[][] solarSystem, int sx, int sy, 
      int maxX, int maxY) {
    int result = 0;
    for (int y = -StarMapStatics.SOLARSYSTEMWIDTH; 
        y < StarMapStatics.SOLARSYSTEMWIDTH;y++) {
      for (int x = -StarMapStatics.SOLARSYSTEMWIDTH; 
          x < StarMapStatics.SOLARSYSTEMWIDTH;x++) {
        if (x+sx >= 0 && y+sy >= 0 && x+sx < maxX && y+sy<maxY ) {
          result = result + solarSystem[sx+x][sy+y];
        }
      }
    }
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
  public static void setSolarSystem(int[][] solarSystem, int sx, int sy,
      int maxX, int maxY) {
    for (int y = -StarMapStatics.SOLARSYSTEMWIDTH; 
        y < StarMapStatics.SOLARSYSTEMWIDTH;y++) {
      for (int x = -StarMapStatics.SOLARSYSTEMWIDTH; 
          x < StarMapStatics.SOLARSYSTEMWIDTH;x++) {
        if (x+sx >= 0 && y+sy >= 0 && x+sx < maxX && y+sy<maxY ) {
          solarSystem[sx+x][sy+y] = 1;
        }
      }
    }

  }

}
