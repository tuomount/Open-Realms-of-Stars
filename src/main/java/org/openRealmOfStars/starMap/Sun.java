package org.openRealmOfStars.starMap;

import org.openRealmOfStars.utilities.RandomSystemNameGenerator;

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
 * Class sun
 *
 */

public class Sun {

  /**
   * Sun's name
   */
  private String name;

  /**
   * Sun's Center coordinate
   */
  private Coordinate centerCoordinate;

  /**
   * Create the Sun with name and center coordinates
   * @param coordinate coordinate
   * @param name Sun name if null then random generator applied
   */
  public Sun(final Coordinate coordinate, final String name) {
    this.centerCoordinate = new Coordinate(coordinate);
    this.name = name;
  }

  /**
   * Create the Sun with name and center coordinates
   * @param coordinate coordinate
   * @param nameGenerator random generator
   */
  public Sun(final Coordinate coordinate,
    final RandomSystemNameGenerator nameGenerator) {
    this(coordinate, nameGenerator.generate());
  }

  /**
   * Get the Sun's name
   * @return Sun's name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the Sun's Center X coordinate
   * @return Sun's Center X coordinate
   */
  public int getCenterX() {
    return centerCoordinate.getX();
  }

  /**
   * Get the Sun's Center X coordinate
   * @return Sun's Center X coordinate
   */
  public int getCenterY() {
    return centerCoordinate.getY();
  }

  /**
   * Get the Sun's Center coordinate
   * @return Sun's Center coordinate
   */
  public Coordinate getCenterCoordinate() {
    return new Coordinate(centerCoordinate);
  }

  @Override
  public String toString() {
    return name + " X:" + centerCoordinate.getX() + " Y:"
           + centerCoordinate.getY();
  }

}
