package org.openRealmOfStars.player.leader;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019, 2020 Tuomo Untinen
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
* Leader gender. This only affects title's leader gets.
*
*/
public enum Gender {

  /**
   * Leader has no gender.
   * Mechions do not have gender.
   */
  NONE(0),
  /**
   * Leader is male
   */
  MALE(1),
  /**
   * Leader is female.
   */
  FEMALE(2);

  /**
   * Create gender type.
   * @param index Index for gender
   */
  Gender(final int index) {
    this.index = index;
  }

  /**
   * Index for gender.
   */
  private int index;

  /**
   * Get gender by index
   * @param index Gender index
   * @return gender
   */
  public static Gender getByIndex(final int index) {
    if (index >= 0 && index < Gender.values().length) {
      return Gender.values()[index];
    }
    return NONE;
  }
  /**
   * Return index of gender.
   * @return Index of gender
   */
  public int getIndex() {
    return index;
  }

  @Override
  public String toString() {
    switch (this) {
      default:
      case NONE: return "none";
      case MALE: return "male";
      case FEMALE: return "female";
    }
  }

}
