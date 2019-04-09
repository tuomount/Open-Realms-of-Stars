package org.openRealmOfStars.starMap.vote;

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
* Voting type
*
*/
public enum VotingType {

  /**
   * Galactic olympic participation decision.
   */
  GALACTIC_OLYMPIC_PARTICIPATE,
  /**
   * Should nuclear weapons be banned from new designs.
   */
  BAN_NUCLEAR_WEAPONS,
  /**
   * Should privateer ships be banned from new designs.
   */
  BAN_PRIVATEER_SHIPS,
  /**
   * All WAR relations would be turned into peace.
   */
  GALACTIC_PEACE,
  /**
   * Richest realm would give one credit for poorest for 20 turns.
   */
  TAXATION_OF_RICHEST_REALM,
  /**
   * Second candidate for military power or most of United Galaxy Towers
   */
  SECOND_CANDIDATE_MILITARY,
  /**
   * Vote between second candidate and first candidate to be the ruler.
   */
  RULER_OF_GALAXY;

  /**
   * Get index as int for voting type
   * @return Voting type
   */
  public int getIndex() {
    switch (this) {
      case GALACTIC_OLYMPIC_PARTICIPATE: return 0;
      case BAN_NUCLEAR_WEAPONS: return 1;
      case BAN_PRIVATEER_SHIPS: return 2;
      case GALACTIC_PEACE: return 3;
      case TAXATION_OF_RICHEST_REALM: return 4;
      case SECOND_CANDIDATE_MILITARY: return 5;
      case RULER_OF_GALAXY: return 6;
      default: return 0;
    }
  }

  /**
   * Get voting type by given index.
   * @param index Voting type index
   * @return VotingType
   */
  public static VotingType getTypeByIndex(final int index) {
    switch (index) {
      case 0: return GALACTIC_OLYMPIC_PARTICIPATE;
      case 1: return BAN_NUCLEAR_WEAPONS;
      case 2: return BAN_PRIVATEER_SHIPS;
      case 3: return GALACTIC_PEACE;
      case 4: return TAXATION_OF_RICHEST_REALM;
      case 5: return SECOND_CANDIDATE_MILITARY;
      case 6: return RULER_OF_GALAXY;
      default: return GALACTIC_OLYMPIC_PARTICIPATE;
    }
  }

  /**
   * Get index as int for voting type
   * @return Voting type
   */
  public String getDescription() {
    switch (this) {
      case GALACTIC_OLYMPIC_PARTICIPATE: return "Galactic Olympic"
          + " participation";
      case BAN_NUCLEAR_WEAPONS: return "Ban nuclear weapons";
      case BAN_PRIVATEER_SHIPS: return "Ban privateer ships";
      case GALACTIC_PEACE: return "Galactic wide peace";
      case TAXATION_OF_RICHEST_REALM: return "Taxation of richest realm";
      case SECOND_CANDIDATE_MILITARY: return "Second candidate with military"
          + " power";
      case RULER_OF_GALAXY: return "Ruler of Galaxy";
      default: return "Unknown";
    }
  }

}
