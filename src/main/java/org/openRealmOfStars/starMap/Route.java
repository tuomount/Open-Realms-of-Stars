package org.openRealmOfStars.starMap;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018,2021,2022 Tuomo Untinen
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
 * Route handling routines
 *
 */
public class Route {

  /**
   * Start X coordinate
   */
  private double startX;
  /**
   * Start Y coordinate
   */
  private double startY;
  /**
   * End X coordinate
   */
  private double endX;
  /**
   * End Y coordinate
   */
  private double endY;

  /**
   * FTL speed
   */
  private int ftlSpeed;

  /**
   * Use this as FTL speed to set fleet to defend
   */
  public static final int ROUTE_DEFEND = 0;

  /**
   * Use this as FTL speed to set fleet to fix itself
   */
  public static final int ROUTE_FIX = -1;

  /**
   * Use this as FTL speed to set fleet when it has bombed planet.
   */
  public static final int ROUTE_BOMBED = -2;

  /**
   * Constructor for route
   * @param sx Start X coordinate
   * @param sy Start Y coordinate
   * @param ex End X coordinate
   * @param ey End Y coordinate
   * @param speed FTL Speed
   */
  public Route(final int sx, final int sy, final int ex, final int ey,
      final int speed) {
    this.startX = sx;
    this.startY = sy;
    this.endX = ex;
    this.endY = ey;
    this.ftlSpeed = speed;
  }

  /**
   * Calculate estimate how long routing takes
   * @return Number of turns routing takes
   */
  public int timeEstimate() {
    int speed = getFtlSpeed();
    if (speed == 0) {
      speed = getRegularSpeed();
    }
    if (speed > 0) {
      return getDistance() / speed;
    }
    return 0;
  }

  /**
   * Route just for defending.
   * @return true if defending
   */
  public boolean isDefending() {
    if (ftlSpeed == ROUTE_DEFEND) {
      return true;
    }
    return false;
  }

  /**
   * Route just for fixing the fleet
   * @return true if fixing the fleet
   */
  public boolean isFixing() {
    if (ftlSpeed == ROUTE_FIX) {
      return true;
    }
    return false;
  }

  /**
   * Route just for bombing the planet under fleet sector
   * @return true if bombing the planet
   */
  public boolean isBombing() {
    if (ftlSpeed == ROUTE_BOMBED) {
      return true;
    }
    return false;
  }

  /**
   * Move a bit closed to end
   * @param starMap to check if sector is block or not. If this is null
   *        then blocked check is not done
   * @return true if move was possible and false if not
   */
  public boolean makeNextMove(final StarMap starMap) {
    int speed = getFtlSpeed();
    if (speed == 0) {
      speed = getRegularSpeed();
    }
    for (int i = 0; i < speed; i++) {
      if (getDistance() > 0) {
        if (starMap == null) {
          startX = startX + getMx();
          startY = startY + getMy();
        } else if (!starMap.isBlocked((int) (startX + getMx()), (int) (startY
                + getMy()))) {
          startX = startX + getMx();
          startY = startY + getMy();
        } else {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Is route's end reached?
   * @return boolean
   */
  public boolean isEndReached() {
    if (isDefending() || isFixing()) {
      // fleet has special route
      return false;
    }
    if (Math.round(endX) == Math.round(startX)
        && Math.round(endY) == Math.round(startY)) {
      return true;
    }
    return false;
  }

  /**
   * Get route on map. Returns byte array size of map. 1 where route dot should
   * be drawn
   * @param maxX Map X size
   * @param maxY Map Y size.
   * @return byte array
   */
  public byte[][] getRouteOnMap(final int maxX, final int maxY) {
    byte[][] result = new byte[maxX][maxY];
    double originalStartX = startX;
    double originalStartY = startY;
    double tmpX = startX;
    double tmpY = startY;
    for (int i = 0; i < getDistance() + 1; i++) {
      startX = tmpX;
      startY = tmpY;
      int sx = getX();
      int sy = getY();
      if (sx >= 0 && sy >= 0 && sx < maxX && sy < maxY) {
        result[sx][sy] = 1;
      }
      startX = startX + getMx();
      startY = startY + getMy();
      tmpX = startX;
      tmpY = startY;
      startX = originalStartX;
      startY = originalStartY;

    }
    return result;
  }

  /**
   * Get current position for X coordinate
   * @return X coordinate
   */
  public int getX() {
    return (int) Math.round(startX);
  }

  /**
   * Get current position for Y coordinate
   * @return Y coordinate
   */
  public int getY() {
    return (int) Math.round(startY);
  }

  /**
   * Route dot image
   */
  private static BufferedImage routeDot;

  /**
   * Get route dot image
   * @return BufferedImage
   */
  public static BufferedImage getRouteDot() {
    if (routeDot == null) {
      routeDot = IOUtilities
          .loadImage(Icons.class.getResource(
              "/resources/images/ftl_routedot.png"));
    }
    return routeDot;
  }

  /**
   * Green Route dot image
   */
  private static BufferedImage greenRouteDot;

  /**
   * Get green route dot image
   * @return BufferedImage
   */
  public static BufferedImage getGreenRouteDot() {
    if (greenRouteDot == null) {
      greenRouteDot = IOUtilities
          .loadImage(Icons.class.getResource(
              "/resources/images/green_routedot.png"));
    }
    return greenRouteDot;
  }

  /**
   * Yellow Route dot image
   */
  private static BufferedImage yellowRouteDot;

  /**
   * Get yellow route dot image
   * @return BufferedImage
   */
  public static BufferedImage getYellowRouteDot() {
    if (yellowRouteDot == null) {
      yellowRouteDot = IOUtilities
          .loadImage(Icons.class.getResource("/resources/images/routedot.png"));
    }
    return yellowRouteDot;
  }

  /**
   * Route repair image
   */
  private static BufferedImage repairDot;

  /**
   * Get repair image
   * @return BufferedImage
   */
  public static BufferedImage getRepairDot() {
    if (repairDot == null) {
      repairDot = IOUtilities
          .loadImage(Icons.class.getResource("/resources/images/repair.png"));
    }
    return repairDot;
  }
  /**
   * Get Defense image
   * @return BufferedImage
   */
  public static BufferedImage getDefenseDot() {
    if (defenseDot == null) {
      defenseDot = IOUtilities
          .loadImage(Icons.class.getResource("/resources/images/defense.png"));
    }
    return defenseDot;
  }

  /**
   * Route bombed image
   */
  private static BufferedImage bombedDot;

  /**
   * Get bombed image
   * @return BufferedImage
   */
  public static BufferedImage getBombedDot() {
    if (bombedDot == null) {
      bombedDot = IOUtilities
          .loadImage(Icons.class.getResource("/resources/images/bombed.png"));
    }
    return bombedDot;
  }

  /**
   * Route defense image
   */
  private static BufferedImage defenseDot;

  /* Need for repository */

  /**
   * Get current position for X coordinate
   * @return X coordinate
   */
  public double getStartX() {
    return startX;
  }

  /**
   * Set current position for X coordinate
   * @param startX current position for X coordinate
   */
  public void setStartX(final double startX) {
    this.startX = startX;
  }

  /**
   * Get current position for Y coordinate
   * @return Y coordinate
   */
  public double getStartY() {
    return startY;
  }

  /**
   * Set current position for Y coordinate
   * @param startY current position for Y coordinate
   */
  public void setStartY(final double startY) {
    this.startY = startY;
  }

  /**
   * Get end X coordinate
   * @return End X coordinate
   */
  public double getEndX() {
    return endX;
  }

  /**
   * Set end X coordinate
   * @param endX End X coordinate
   */
  public void setEndX(final double endX) {
    this.endX = endX;
  }

  /**
   * Get end Y coordinate
   * @return End Y coordinate
   */
  public double getEndY() {
    return endY;
  }

  /**
   * Set end Y coordinate
   * @param endY End Y coordinate
   */
  public void setEndY(final double endY) {
    this.endY = endY;
  }

  /**
   * Get Ftl speed of route.
   * @return ftl speed of route.
   */
  public int getFtlSpeed() {
    return ftlSpeed & 0xff;
  }
  /**
   * Set FTL speed of route.
   * @param speed FTL Speed.
   */
  public void setFtlSpeed(final int speed) {
    if (speed > 0 && speed < 256) {
      ftlSpeed = speed;
    }
  }
  /**
   * Get regular speed of route.
   * @return regular speed of route.
   */
  public int getRegularSpeed() {
    return ftlSpeed >> 8 & 0xff;
  }
  /**
   * Set regular speed of route.
   * @param speed regular Speed.
   */
  public void setRegularSpeed(final int speed) {
    if (speed > 0 && speed < 256) {
      ftlSpeed = speed << 8;
    }
  }
  /**
   * Get Raw value for route.
   * @return raw value
   */
  public int getRawValue() {
    return ftlSpeed;
  }

  /**
   * Set raw value for whole route.
   * @param value Raw value
   */
  public void setRawValue(final int value) {
    this.ftlSpeed = value;
  }

  /**
   * Get internal movement speed
   * @return internal movement speed
   */
  public double getMx() {
    int distance = getDistance();
    double mx;
    if (distance > 0) {
      mx = (endX - startX) / distance;
    } else {
      mx = 0;
    }
    return mx;
  }

  /**
   * Get internal movement speed
   * @return internal movement speed
   */
  public double getMy() {
    int distance = getDistance();
    double my;
    if (distance > 0) {
      my = (endY - startY) / distance;
    } else {
      my = 0;
    }
    return my;
  }

  /**
   * Get distance in coordinates
   * @return distance in coordinates
   */
  public int getDistance() {
    int distance;
    double dx = Math.abs(startX - endX);
    double dy = Math.abs(startY - endY);
    distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    return distance;
  }

}
