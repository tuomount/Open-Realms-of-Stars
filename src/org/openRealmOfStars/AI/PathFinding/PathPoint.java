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
  public PathPoint(int x, int y, double distance) {
    this.x = x;
    this.y = y;
    this.distance = distance;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("X: ");
    sb.append(x);
    sb.append(" Y: ");
    sb.append(y);
    sb.append(" Dist: ");
    sb.append(distance);
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PathPoint) {
      PathPoint point = (PathPoint) obj;
      if (this.x == point.getX() && this.y == point.getY()) {
        return true;
      }
    }
    return false;
  }
}
