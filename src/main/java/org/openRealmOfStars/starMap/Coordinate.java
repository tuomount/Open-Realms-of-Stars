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
 * Coordinate
 *
 */
public class Coordinate {

    /** The X value ot the coordinate */
    private int x;
    /** The Y value ot the coordinate */
    private int y;

    /**
     * Constructor for Coordinate
     * @param x The X value ot the coordinate
     * @param y The Y value ot the coordinate
     */
    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for Coordinate
     * @param coordinate The coordinate to copy it
     */
    public Coordinate(final Coordinate coordinate) {
        this(coordinate.getX(), coordinate.getY());
    }

    /**
     * Get the X value ot the coordinate
     * @return the X value ot the coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Set the X value of the coordinate
     * @param x the X value of the coordinate
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Get the Y value ot the coordinate
     * @return the Y value ot the coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Set the Y value of the coordinate
     * @param y the Y value of the coordinate
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Calculate distance between two coordinates.
     * @param otherCoordinate The other coordinate
     * @return the distance
     */
    public double calculateDistance(final Coordinate otherCoordinate) {
        int xDistance = Math.abs(otherCoordinate.getX() - getX());
        int yDistance = Math.abs(otherCoordinate.getY() - getY());
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

    /**
     * Check if coordinates are valid for this StarMap
     * @param maxCoordinate Tha maximum coordinate
     * @return true if valid and false if invalid
     * @TODO: A bit confusing that maxCoordinate is not a valid coordinate
     */
    public boolean isValidCoordinate(final Coordinate maxCoordinate) {
        if (getX() >= 0 && getY() >= 0
                && getX() < maxCoordinate.getX()
                && getY() < maxCoordinate.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Coordinate(" + x + ", " + y + ")";
    }

    /**
     * Check if two coordinate matches
     * @param coordinate Which is being matched
     * @return true if two coordinates are same, otherwise false
     */
    public boolean sameAs(final Coordinate coordinate) {
      if (coordinate.getX() == this.getX()
          && coordinate.getY() == this.getY()) {
        return true;
      }
      return false;
    }
}
