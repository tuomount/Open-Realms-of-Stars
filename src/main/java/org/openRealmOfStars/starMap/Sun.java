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
   * Sun's Center x coordinate
   */
  private int centerX;
  /**
   * Sun's Center y coordinate
   */
  private int centerY;

  /**
   * Create the Sun with name and center coordinates
   * @param cx X coordinate
   * @param cy Y coordinate
   * @param name Sun name if null then random generator applied
   */
  public Sun(final int cx, final int cy, final String name) {
    this.centerX = cx;
    this.centerY = cy;
    this.name = name;
  }

  /**
   * Create the Sun with name and center coordinates
   * @param cx X coordinate
   * @param cy Y coordinate
   * @param nameGenerator random generator
   */
  public Sun(final int cx, final int cy, final RandomSystemNameGenerator nameGenerator){
    this(cx, cy, nameGenerator.generate());
  }

  public String getName() {
    return name;
  }

  public int getCenterX() {
    return centerX;
  }

  public int getCenterY() {
    return centerY;
  }

  @Override
  public String toString() {
    return name + " X:" + centerX + " Y:" + centerY;
  }

}
