package org.openRealmOfStars.AI.PathFinding;

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
 * Single path point
 *
 */

public class PathPoint {

  /**
   * Point X coordinate
   */
  private int x;
  /**
   * Point Y coordinate
   */
  private int y;
  /**
   * Distance to target
   */
  private double distance;

  /**
   * Constructor for PathPoint
   * @param x X coordinate
   * @param y Y Coordinate
   * @param distance Distance
   */
  public PathPoint(final int x, final int y, final double distance) {
    this.x = x;
    this.y = y;
    this.distance = distance;
  }

  /**
   * Get X coordinate for Path Point
   * @return X coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Set X coordinate for Path Point
   * @param x X coordinate
   */
  public void setX(final int x) {
    this.x = x;
  }

  /**
   * Get Y coordinate for Path Point
   * @return Y coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Set Y coordinate for Path Point
   * @param y Y coordinate
   */
  public void setY(final int y) {
    this.y = y;
  }

  /**
   * Get distance to target point
   * @return Distance to target as a double
   */
  public double getDistance() {
    return distance;
  }

  /**
   * Set distance to target
   * @param distance to target
   */
  public void setDistance(final double distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    return "X: " + x + " Y: " + y + " Dist: " + distance;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof PathPoint) {
      PathPoint point = (PathPoint) obj;
      if (this.x == point.getX() && this.y == point.getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Magic multiplier to get hash code
   */
  private static final int MULTIPLIER = 9731;

  @Override
  public int hashCode() {
    int result = x;
    result = MULTIPLIER * result + y;
    return result;
  }
}
