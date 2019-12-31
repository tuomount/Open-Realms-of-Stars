package org.openRealmOfStars.player.leader;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Leader Job type
*
*/
public enum Job {

  /**
   * Leader is unassigned
   */
  UNASSIGNED(0, "Unassigned"),
  /**
   * Leader is realm ruler
   */
  RULER(1, "Ruler"),
  /**
   * Leader is planet governor
   */
  GOVERNOR(2, "Governor"),
  /**
   * Leader is fleet commander
   */
  COMMANDER(3, "Fleet commander"),
  /**
   * Leader is dead and no longer useable
   */
  DEAD(4, "Dead"),
  /**
   * Leader is heir, but too young to use yet
   */
  TOO_YOUNG(5, "Too young");

  /**
   * Job Constructor.
   * @param index Job index
   * @param name Job name
   */
  Job(final int index, final String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * Get Job by index
   * @param index Job index
   * @return Job
   */
  public static Job getByIndex(final int index) {
    if (index > 0 && index < Job.values().length) {
      return Job.values()[index];
    }
    return UNASSIGNED;
  }
  /**
   * Index for job type
   */
  private int index;
  /**
   * Job name.
   */
  private String name;
  /**
   * Get Job ined
   * @return the index
   */
  public int getIndex() {
    return index;
  }
  /**
   * Get the Job name.
   * @return the name
   */
  public String getName() {
    return name;
  }

}
