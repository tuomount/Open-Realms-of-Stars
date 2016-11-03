package org.openRealmOfStars.starMap;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.utilities.IOUtilities;

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
   * Internal movement speed
   */
  private double mx;
  /**
   * Internal movement speed
   */
  private double my;

  /**
   * Distance in coordinates
   */
  private int distance;

  /**
   * Use this as FTL speed to set fleet to defend
   */
  public static final int ROUTE_DEFEND = 0;

  /**
   * Use this as FTL speed to set fleet to fix itself
   */
  public static final int ROUTE_FIX = -1;

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
    double dx = Math.abs(startX - endX);
    double dy = Math.abs(startY - endY);
    distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    if (distance > 0) {
      mx = (endX - startX) / distance;
      my = (endY - startY) / distance;
    } else {
      mx = 0;
      my = 0;
    }
  }

  /**
   * Read route from DataInputStream
   * @param dis Data Input Stream
   * @throws IOException if there is any problem with DataInputStream
   */
  public Route(final DataInputStream dis) throws IOException {
    startX = dis.readDouble();
    startY = dis.readDouble();
    endX = dis.readDouble();
    endY = dis.readDouble();
    mx = dis.readDouble();
    my = dis.readDouble();
    ftlSpeed = dis.readInt();
    distance = dis.readInt();
  }

  /**
   * Save Route to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveRoute(final DataOutputStream dos) throws IOException {
    dos.writeDouble(startX);
    dos.writeDouble(startY);
    dos.writeDouble(endX);
    dos.writeDouble(endY);
    dos.writeDouble(mx);
    dos.writeDouble(my);
    dos.writeInt(ftlSpeed);
    dos.writeInt(distance);
  }

  /**
   * Calculate estimate how long routing takes
   * @return Number of turns routing takes
   */
  public int timeEstimate() {
    double dx = Math.abs(startX - endX);
    double dy = Math.abs(startY - endY);
    if (dx > dy) { return (int) Math.ceil(dx / ftlSpeed); }
    return (int) Math.ceil(dy / ftlSpeed);
  }

  /**
   * Route just for defending.
   * @return true if defending
   */
  public boolean isDefending() {
    if (ftlSpeed == ROUTE_DEFEND) { return true; }
    return false;
  }

  /**
   * Route just for fixing the fleet
   * @return true if fixing the fleet
   */
  public boolean isFixing() {
    if (ftlSpeed == ROUTE_FIX) { return true; }
    return false;
  }

  /**
   * Move a bit closed to end
   */
  public void makeNextMove() {
    for (int i = 0; i < ftlSpeed; i++) {
      if (distance > 0) {
        startX = startX + mx;
        startY = startY + my;
        distance--;
      }
    }
  }

  /**
   * Is route's end reached?
   * @return boolean
   */
  public boolean isEndReached() {
    if (ftlSpeed < 1) {
      // fleet has special route
      return false;
    }
    if (Math.round(endX) == Math.round(startX)
        && Math.round(endY) == Math.round(startY)) { return true; }
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
    byte result[][] = new byte[maxX][maxY];
    double sx = startX;
    double sy = startY;
    for (int i = 0; i < distance + 1; i++) {
      if (sx >= 0 && sy >= 0 && sx < maxX && sy < maxY) {
        result[(int) Math.round(sx)][(int) Math.round(sy)] = 1;
      }
      sx = sx + mx;
      sy = sy + my;

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
          .loadImage(Icons.class.getResource("/resources/images/routedot.png"));
    }
    return routeDot;
  }

}
