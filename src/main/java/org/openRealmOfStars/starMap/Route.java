package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2026 Tuomo Untinen
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

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
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
   * Possible next points in route.
   */
  private ArrayList<Coordinate> nextPoints;
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
   * Use this as FTL speed to set fleet when it has bombed planet.
   */
  public static final int ROUTE_EXPLORED = -3;

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
    nextPoints = null;
  }

  /**
   * Private constructor for copy method.
   * @param sx Start x coordinate
   * @param sy Start y coordinate
   * @param ex End X coordinate
   * @param ey End Y coordinate
   * @param actualSpeed Raw speed
   */
  private Route(final double sx, final double sy, final double ex,
      final double ey, final int actualSpeed) {
    this.startX = sx;
    this.startY = sy;
    this.endX = ex;
    this.endY = ey;
    this.ftlSpeed = actualSpeed;
    nextPoints = null;
  }

  /**
   * Creates a copy of route.
   * @return Copy of route
   */
  public Route copy() {
    Route result = new Route(this.startX, this.getY(), this.endX, this.endY,
        this.getRawValue());
    if (this.nextPoints != null) {
      for (Coordinate coord : this.nextPoints) {
        result.addNewPoint(coord);
      }
    }
    return result;
  }
  /**
   * Get array list of next points. This will return null if there is
   * only single point in the route.
   * @return ArrayList of next points or null.
   */
  public ArrayList<Coordinate> getNextPoints() {
    return nextPoints;
  }

  /**
   * Add new point for route. This will make route to multipoint route.
   * If route was not multipoint before, this will change it to multipoint.
   * @param coord Next point in coordinates.
   */
  public void addNewPoint(final Coordinate coord) {
    if (nextPoints == null) {
      nextPoints = new ArrayList<>();
    }
    nextPoints.add(coord);
  }

  /**
   * Calculate estimate how long routing takes
   * @return Number of star years routing takes
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
   * Route just for exploring the planet under fleet sector
   * @return true if exploring the planet
   */
  public boolean isExploring() {
    if (ftlSpeed == ROUTE_EXPLORED) {
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
    if (speed == 0 && getRegularSpeed() > 0) {
      speed = 1;
    }
    return makeNextMove(starMap, speed);
  }

  /**
   * Move a bit closed to end
   * @param starMap to check if sector is block or not. If this is null
   *        then blocked check is not done
   * @param speed Movement speed
   * @return true if move was possible and false if not
   */
  public boolean makeNextMove(final StarMap starMap, final int speed) {
    for (int i = 0; i < speed; i++) {
      if (getDistance() > 0) {
        double dx = startX + getMx();
        double dy = startY + getMy();
        int sx = (int) Math.round(dx);
        int sy = (int) Math.round(dy);
        if (starMap == null) {
          startX = dx;
          startY = dy;
        } else if (!starMap.isBlocked(sx, sy)) {
          startX = dx;
          startY = dy;
        } else {
          return false;
        }
        if (endOfPoint() && nextPoints != null) {
          startX = endX;
          startY = endY;
          endX = nextPoints.get(0).getX();
          endY = nextPoints.get(0).getY();
          nextPoints.remove(0);
          if (nextPoints.size() == 0) {
            nextPoints = null;
          }
        }
      }
    }
    return true;
  }

  /**
   * Check if route is being blocked by map object. This is done only if
   * route is simple from point A to point B.
   * @param starMap StarMap
   * @return blocked coordinate or null if no blocks
   */
  public Coordinate checkBlockedCoordinate(final StarMap starMap) {
    double origStartX = startX;
    double origStartY = startY;
    if (starMap.isBlocked(getX(), getY())) {
      return new Coordinate(getX(), getY());
    }
    if (nextPoints == null) {
      while (!isEndReached()) {
        if (!makeNextMove(starMap, 1)) {
          int tempX = (int) Math.round(startX + getMx());
          int tempY = (int) Math.round(startY + getMy());
          startX = origStartX;
          startY = origStartY;
          return new Coordinate(tempX, tempY);
        }
      }
    }
    startX = origStartX;
    startY = origStartY;
    return null;
  }
  /**
   * Has end of one path point received.
   * @return boolean
   */
  private boolean endOfPoint() {
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
   * Is route's end reached?
   * @return boolean
   */
  public boolean isEndReached() {
    if (isDefending() || isFixing()) {
      // fleet has special route
      return false;
    }
    if (Math.round(endX) == Math.round(startX)
        && Math.round(endY) == Math.round(startY)
        && nextPoints == null) {
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
    boolean ftlRoute = true;
    if (getRegularSpeed() > 0) {
      ftlRoute = false;
    }
    double originalStartX = startX;
    double originalStartY = startY;
    double tmpX = startX;
    double tmpY = startY;
    if (nextPoints == null) {
      for (int i = 0; i < getDistance() + 1; i++) {
        startX = tmpX;
        startY = tmpY;
        int sx = getX();
        int sy = getY();
        if (sx >= 0 && sy >= 0 && sx < maxX && sy < maxY) {
          if (ftlRoute) {
            result[sx][sy] = 1;
          } else {
            if (i <= getRegularSpeed()) {
              result[sx][sy] = 2;
            } else {
              result[sx][sy] = 3;
            }
          }
        }
        tmpX = startX + getMx();
        tmpY = startY + getMy();
        startX = originalStartX;
        startY = originalStartY;
      }
    } else {
      double sdx = startX;
      double sdy = startY;
      int ex = (int) Math.round(endX);
      int ey = (int) Math.round(endY);
      int index = 0;
      if (nextPoints != null) {
        for (int i = 0; i < getDistance(); i++) {
          sdx = sdx + getMx(index);
          sdy = sdy + getMy(index);
          int sx = (int) Math.round(sdx);
          int sy = (int) Math.round(sdy);
          if (sx >= 0 && sy >= 0 && sx < maxX && sy < maxY) {
            if (ftlRoute) {
              result[sx][sy] = 1;
            } else {
              if (i <= getRegularSpeed()) {
                result[sx][sy] = 2;
              } else {
                result[sx][sy] = 3;
              }
            }
          }
          if (sx == ex && sy == ey) {
            index++;
            sdx = sx;
            sdy = sy;
            if (index <= nextPoints.size()) {
              ex = nextPoints.get(index - 1).getX();
              ey = nextPoints.get(index - 1).getY();
            }
          }
        }
      }
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
   * Small route dot image
   */
  private static BufferedImage smallRouteDot;

  /**
   * Get route dot image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getRouteDot(final int zoomLevel) {
    if (routeDot == null) {
      routeDot = IOUtilities
          .loadImage("/resources/images/ftl_routedot.png");
      smallRouteDot = GuiStatics.scaleToHalf(routeDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallRouteDot;
    }
    return routeDot;
  }

  /**
   * Green Route dot image
   */
  private static BufferedImage greenRouteDot;
  /**
   * Small green Route dot image
   */
  private static BufferedImage smallGreenRouteDot;

  /**
   * Get green route dot image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getGreenRouteDot(final int zoomLevel) {
    if (greenRouteDot == null) {
      greenRouteDot = IOUtilities
          .loadImage("/resources/images/green_routedot.png");
      smallGreenRouteDot = GuiStatics.scaleToHalf(greenRouteDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallGreenRouteDot;
    }
    return greenRouteDot;
  }

  /**
   * Yellow Route dot image
   */
  private static BufferedImage yellowRouteDot;
  /**
   * Smal yellow Route dot image
   */
  private static BufferedImage smallYellowRouteDot;

  /**
   * Get yellow route dot image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getYellowRouteDot(final int zoomLevel) {
    if (yellowRouteDot == null) {
      yellowRouteDot = IOUtilities
          .loadImage("/resources/images/routedot.png");
      smallYellowRouteDot = GuiStatics.scaleToHalf(yellowRouteDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallYellowRouteDot;
    }
    return yellowRouteDot;
  }

  /**
   * Route repair image
   */
  private static BufferedImage repairDot;
  /**
   * Small route repair image
   */
  private static BufferedImage smallRepairDot;

  /**
   * Get repair image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getRepairDot(final int zoomLevel) {
    if (repairDot == null) {
      repairDot = IOUtilities
          .loadImage("/resources/images/repair.png");
      smallRepairDot = GuiStatics.scaleToHalf(repairDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallRepairDot;
    }
    return repairDot;
  }
  /**
   * Get Defense image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getDefenseDot(final int zoomLevel) {
    if (defenseDot == null) {
      defenseDot = IOUtilities
          .loadImage("/resources/images/defense.png");
      smallDefenseDot = GuiStatics.scaleToHalf(defenseDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallDefenseDot;
    }
    return defenseDot;
  }

  /**
   * Route bombed image
   */
  private static BufferedImage bombedDot;
  /**
   * Small route bombed image
   */
  private static BufferedImage smallBombedDot;

  /**
   * Get bombed image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getBombedDot(final int zoomLevel) {
    if (bombedDot == null) {
      bombedDot = IOUtilities
          .loadImage("/resources/images/bombed.png");
      smallBombedDot = GuiStatics.scaleToHalf(bombedDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallBombedDot;
    }
    return bombedDot;
  }

  /**
   * Route explored image
   */
  private static BufferedImage exploredDot;
  /**
   * Small route explored image
   */
  private static BufferedImage smallExploredDot;

  /**
   * Get explored image
   * @param zoomLevel Zoomlevel for getting correct size image.
   * @return BufferedImage
   */
  public static BufferedImage getExploredDot(final int zoomLevel) {
    if (exploredDot == null) {
      exploredDot = IOUtilities
          .loadImage("/resources/images/explored.png");
      smallExploredDot = GuiStatics.scaleToHalf(exploredDot);
    }
    if (zoomLevel < Tile.ZOOM_NORMAL) {
      return smallExploredDot;
    }
    return exploredDot;
  }

  /**
   * Route defense image
   */
  private static BufferedImage defenseDot;
  /**
   * Small route defense image
   */
  private static BufferedImage smallDefenseDot;

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
    if (ftlSpeed > 0 && ftlSpeed < 256) {
      return ftlSpeed;
    }
    return 0;
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
    if (ftlSpeed > 255) {
      return ftlSpeed >> 8 & 0xff;
    }
    return 0;
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
    int distance = getDistance(0);
    double mx;
    if (distance > 0) {
      mx = (endX - startX) / distance;
    } else {
      mx = 0;
    }
    return mx;
  }

  /**
   * Get movement of X coordinate based on index.
   * @param index Index to route
   * @return Movement of X
   */
  private double getMx(final int index) {
    double distance = getDistance(index);
    double mx;
    if (distance > 0) {
      if (index == 0) {
        mx = (endX - startX) / distance;
      } else if (index == 1) {
        mx = (nextPoints.get(0).getX() - endX) / distance;
      } else {
        mx = (nextPoints.get(index - 1).getX()
            - nextPoints.get(index - 2).getX()) / distance;
      }
    } else {
      mx = 0;
    }
    return mx;
  }
  /**
   * Get movement of Y coordinate based on index.
   * @param index Index to route
   * @return Movement of Y
   */
  private double getMy(final int index) {
    double distance = getDistance(index);
    double my;
    if (distance > 0) {
      if (index == 0) {
        my = (endY - startY) / distance;
      } else if (index == 1) {
        my = (nextPoints.get(0).getY() - endY) / distance;
      } else {
        my = (nextPoints.get(index - 1).getY()
            - nextPoints.get(index - 2).getY()) / distance;
      }
    } else {
      my = 0;
    }
    return my;
  }
  /**
   * Get internal movement speed
   * @return internal movement speed
   */
  public double getMy() {
    int distance = getDistance(0);
    double my;
    if (distance > 0) {
      my = (endY - startY) / distance;
    } else {
      my = 0;
    }
    return my;
  }

  /**
   * Calculate single distance between two pathpoints.
   * Index 0 is for first path point.
   * @param index Pathpoint index
   * @return Distance between two points
   */
  public int getDistance(final int index) {
    int distance = 0;
    if (index == 0 || nextPoints == null) {
      double dx = Math.abs(startX - endX);
      double dy = Math.abs(startY - endY);
      distance = (int) dy;
      if (dx > dy) {
        distance = (int) dx;
      }
    }
    if (nextPoints != null) {
      if (index == 1) {
        double dx = Math.abs(endX - nextPoints.get(0).getX());
        double dy = Math.abs(endY - nextPoints.get(0).getY());
        distance = (int) dy;
        if (dx > dy) {
          distance = (int) dx;
        }
      } else if (index > 1) {
        double dx = Math.abs(nextPoints.get(index - 2).getX()
            - nextPoints.get(index - 1).getX());
        double dy = Math.abs(nextPoints.get(index - 2).getY()
            - nextPoints.get(index - 1).getY());
        distance = (int) dy;
        if (dx > dy) {
          distance = (int) dx;
        }
      }
    }
    return distance;
  }
  /**
   * Get full distance in coordinates
   * @return distance in coordinates
   */
  public int getDistance() {
    int distance;
    if (nextPoints == null) {
      distance = getDistance(0);
    } else {
      double dx = Math.abs(startX - endX);
      double dy = Math.abs(startY - endY);
      distance = (int) dy;
      if (dx > dy) {
        distance = (int) dx;
      }
      int px = (int) Math.round(endX);
      int py = (int) Math.round(endY);
      for (Coordinate coord : nextPoints) {
        int dix = Math.abs(px - coord.getX());
        int diy = Math.abs(py - coord.getY());
        if (dix > diy) {
          distance = distance + dix;
        } else {
          distance = distance + diy;
        }
        px = coord.getX();
        py = coord.getY();
      }
    }
    return distance;
  }

}
