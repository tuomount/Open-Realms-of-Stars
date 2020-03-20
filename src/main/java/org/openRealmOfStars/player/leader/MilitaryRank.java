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
* Military Rank information
*
*/
public enum MilitaryRank {
  /**
   * Leader has not been assinged into fleet commander
   */
  CIVILIAN(0, "Civilian"),
  /**
   * Military rank when leader is assigned as fleet commander
   */
  ENSIGN(1, "Ensign"),
  /**
   * Military rank when leader gains 1st level as fleet commander
   */
  LIEUTENANT(2, "Lieutenant"),
  /**
   * Military rank when leader gains 2nd level as fleet commander
   */
  CAPTAIN(3, "Captain"),
  /**
   * Military rank when leader gains 3rd level as fleet commander
   */
  COLONEL(4, "Colonel"),
  /**
   * Military rank when leader gains 4th level as fleet commander
   */
  COMMANDER(5, "Commander"),
  /**
   * Military rank when leader gains 5th or more levels as fleet commander
   */
  ADMIRAL(6, "Admiral");

  /**
   * Create Military Rank enumeration
   * @param index Index number for Military rank
   * @param name Textual name for military rank
   */
  MilitaryRank(final int index, final String name) {
    this.index = index;
    this.name = name;
  }

  /**
   * Get military rank by index;
   * @param index Index to get perk
   * @return Perk
   */
  public static MilitaryRank getByIndex(final int index) {
    if (index >= 0 && index < MilitaryRank.values().length) {
      return MilitaryRank.values()[index];
    }
    if (index >= MilitaryRank.values().length) {
      return MilitaryRank.ADMIRAL;
    }
    return MilitaryRank.CIVILIAN;
  }
  /**
   * Index number for military rank
   */
  private int index;

  /**
   * Military rank name
   */
  private String name;

  /**
   * Get military rank index.
   * @return Index
   */
  public int getIndex() {
    return index;
  }

  @Override
  public String toString() {
    return name;
  }
}
