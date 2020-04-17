package org.openRealmOfStars.player.diplomacy;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Attitude scoring for rulers
*/
public class AttitudeScore implements Comparable<AttitudeScore> {

  /**
   * Attitude
   */
  private Attitude attitude;
  /**
   * Scoring value
   */
  private int value;

  /**
   * Constructor for Attitude score
   * @param attitude Attitude
   */
  public AttitudeScore(final Attitude attitude) {
    this.attitude = attitude;
    value = 0;
  }
  @Override
  public int compareTo(final AttitudeScore arg0) {
    return this.value - arg0.value;
  }

  /**
   * Get Attitude
   * @return Attitude
   */
  public Attitude getAttitude() {
    return attitude;
  }

  /**
   * Get value for Attitude
   * @return the value
   */
  public int getValue() {
    return value;
  }
  /**
   * Set Value for attitude
   * @param value the value to set
   */
  public void setValue(final int value) {
    this.value = value;
  }


}
