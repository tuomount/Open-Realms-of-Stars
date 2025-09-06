package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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

/**
 * Coordinate on a StarMap.
 * Objects of this class are immutable.
 */
public class Coordinate {

    /** The X value of the coordinate */
    private final int x;
    /** The Y value of the coordinate */
    private final int y;

    /** Direction NONE */
    public static final int NONE = -1;
    /** Direction up */
    public static final int UP = 0;
    /** Direction up right */
    public static final int UP_RIGHT = 1;
    /** Direction right */
    public static final int RIGHT = 2;
    /** Direction down right */
    public static final int DOWN_RIGHT = 3;
    /** Direction down */
    public static final int DOWN = 4;
    /** Direction down left */
    public static final int DOWN_LEFT = 5;
    /** Direction left */
    public static final int LEFT = 6;
    /** Direction up left */
    public static final int UP_LEFT = 7;

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
     * Get new coordinate from old one according the direction
     * @param direction UP, RIGHT, DOWN, LEFT are allowd
     * @return New coordinate according direction or old one
     */
    public Coordinate getDirection(final int direction) {
      switch (direction) {
        case UP: return new Coordinate(getX(), getY() - 1);
        case RIGHT: return new Coordinate(getX() + 1, getY());
        case DOWN: return new Coordinate(getX(), getY() + 1);
        case LEFT: return new Coordinate(getX() - 1, getY());
        default: return this;
      }
    }

    /**
     * Get position of coordinate against current coordinate.
     * @param coord Coordinate to compate
     * @return int Position
     */
    public int getPosition(final Coordinate coord) {
      int pos = NONE;
      int mx = coord.getX() - this.getX();
      int my = coord.getY() - this.getY();
      if (mx == 0 && my == 0) {
        return pos;
      }
      if (Math.abs(mx) == Math.abs(my)) {
        if (my < 0 && mx < 0) {
          pos = UP_LEFT;
        }
        if (my < 0 && mx > 0) {
          pos = UP_RIGHT;
        }
        if (my > 0 && mx < 0) {
          pos = DOWN_LEFT;
        }
        if (my > 0 && mx > 0) {
          pos = DOWN_RIGHT;
        }
      }
      if (mx == 0) {
        if (my < 0) {
          pos = UP;
        }
        if (my > 0) {
          pos = DOWN;
        }
      }
      if (my == 0) {
        if (mx < 0) {
          pos = LEFT;
        }
        if (mx > 0) {
          pos = RIGHT;
        }
      }
      if (pos == NONE) {
        double dist = this.calculateDistance(coord);
        int distInt = (int) dist;
        if (distInt > Math.abs(mx) && distInt > Math.abs(my)) {
          if (my < 0 && mx < 0) {
            pos = UP_LEFT;
          }
          if (my < 0 && mx > 0) {
            pos = UP_RIGHT;
          }
          if (my > 0 && mx < 0) {
            pos = DOWN_LEFT;
          }
          if (my > 0 && mx > 0) {
            pos = DOWN_RIGHT;
          }
        }
        if (pos == NONE) {
          if (distInt == Math.abs(mx)) {
            if (mx < 0) {
              pos = LEFT;
            }
            if (mx > 0) {
              pos = RIGHT;
            }
          }
          if (distInt == Math.abs(my)) {
            if (my < 0) {
              pos = UP;
            }
            if (my > 0) {
              pos = DOWN;
            }
          }
        }
      }
      return pos;
    }
    /**
     * Get the X value ot the coordinate
     * @return the X value ot the coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y value ot the coordinate
     * @return the Y value ot the coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Calculate distance between two coordinates.
     * @param otherCoordinate The other coordinate
     * @return the distance
     */
    public double calculateDistance(final Coordinate otherCoordinate) {
        int xDistance = otherCoordinate.x - x;
        int yDistance = otherCoordinate.y - y;
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

    /**
     * Check if coordinates are valid for this StarMap.
     * Since maxCoordinate contains the map size so the maximum coordinate
     * is one less that map size.
     * @param maxCoordinate The maximum size of the StarMap
     * @return true if valid and false if invalid
     */
    public boolean isValidCoordinate(final Coordinate maxCoordinate) {
        return x >= 0 && y >= 0 && x < maxCoordinate.x && y < maxCoordinate.y;
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

    /**
     * Check if two coordinate matches. Give coordinates separate values.
     * @param x2 X coordinate to check
     * @param y2 Y coordiante to check
     * @return true if two coordinates are same, otherwise false
     */
    public boolean sameAs(final int x2, final int y2) {
      if (x2 == this.getX()
          && y2 == this.getY()) {
        return true;
      }
      return false;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof Coordinate)) {
        return false;
      }

      Coordinate other = (Coordinate) obj;
      return sameAs(other);
    }

}
