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

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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

    /**
     * Calculate distance between two coordinates.
     * @param otherCoordinate The other coordinate
     * @return the distance
     */
    public double calculateDistance(Coordinate otherCoordinate) {
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
    public boolean isValidCoordinate(Coordinate maxCoordinate) {
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
}
