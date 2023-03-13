package org.openRealmOfStars.player.leader.stats;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021-2023 Tuomo Untinen
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
* Leader Stat type
*
*/
public enum StatType {

  /**
   * Total amount of star years as ruler.
   */
  RULER_REIGN_LENGTH,
  /**
   * How many times has been selected for ruler.
   */
  NUMBER_OF_RULER,
  /**
   * How many times has been in space battles.
   */
  NUMBER_OF_BATTLES,
  /**
   * How many times has done espionage missions.
   */
  NUMBER_OF_ESPIONAGE,
  /**
   * How many space anomalies have been studied.
   */
  NUMBER_OF_ANOMALY,
  /**
   * How many star years have been as commander.
   */
  COMMANDER_LENGTH,
  /**
   * How many star years have been as governor.
   */
  GOVERNOR_LENGTH,
  /**
   * How many times have been in jail.
   */
  NUMBER_OF_JAIL_TIME,
  /**
   * How many star years have been in jail.
   */
  JAIL_TIME,
  /**
   * How many times have killed another leader.
   */
  KILLED_ANOTHER_LEADER,
  /**
   * How many times has been in space battle with pirates/privateers.
   */
  NUMBER_OF_PIRATE_BATTLES,
  /**
   * How many times has done privateering.
   */
  NUMBER_OF_PRIVATEERING,
  /**
   * How many times has done trading.
   */
  NUMBER_OF_TRADES,
  /**
   * How many ships has been build by governor.
   */
  NUMBER_OF_SHIPS_BUILT,
  /**
   * How many buildings has been build by governor.
   */
  NUMBER_OF_BUILDINGS_BUILT,
  /**
   * How much population has growth when governor.
   */
  POPULATION_GROWTH,
  /**
   * How many diplomatic trades has done as a ruler.
   */
  DIPLOMATIC_TRADE,
  /**
   * How many times has made war as a ruler.
   */
  WAR_DECLARATIONS,
  /**
   * How many artifacts leader has been researched.
   */
  RESEARCH_ARTIFACTS;

  /**
   * Get Stat Type as byte.
   * @return Byte value for stat type.
   */
  public byte getAsByte() {
    switch (this) {
      case RULER_REIGN_LENGTH: return 0;
      case NUMBER_OF_RULER: return 1;
      case NUMBER_OF_BATTLES: return 2;
      case NUMBER_OF_ESPIONAGE: return 3;
      case NUMBER_OF_ANOMALY: return 4;
      case COMMANDER_LENGTH: return 5;
      case GOVERNOR_LENGTH: return 6;
      case NUMBER_OF_JAIL_TIME: return 7;
      case JAIL_TIME: return 8;
      case KILLED_ANOTHER_LEADER: return 9;
      case NUMBER_OF_PIRATE_BATTLES: return 10;
      case NUMBER_OF_PRIVATEERING: return 11;
      case NUMBER_OF_TRADES: return 12;
      case NUMBER_OF_SHIPS_BUILT: return 13;
      case NUMBER_OF_BUILDINGS_BUILT: return 14;
      case POPULATION_GROWTH: return 15;
      case DIPLOMATIC_TRADE: return 16;
      case WAR_DECLARATIONS: return 17;
      case RESEARCH_ARTIFACTS: return 18;
      default:
        throw new IllegalArgumentException("Unexpected StatType:"
            + this.toString());
    }
  }

  /**
   * Get StatType based on index number.
   * @param index Stat index number.
   * @return StatType.
   */
  public static StatType getBasedOnIndex(final int index) {
    switch (index) {
      case 0: return RULER_REIGN_LENGTH;
      case 1: return NUMBER_OF_RULER;
      case 2: return NUMBER_OF_BATTLES;
      case 3: return NUMBER_OF_ESPIONAGE;
      case 4: return NUMBER_OF_ANOMALY;
      case 5: return COMMANDER_LENGTH;
      case 6: return GOVERNOR_LENGTH;
      case 7: return NUMBER_OF_JAIL_TIME;
      case 8: return JAIL_TIME;
      case 9: return KILLED_ANOTHER_LEADER;
      case 10: return NUMBER_OF_PIRATE_BATTLES;
      case 11: return NUMBER_OF_PRIVATEERING;
      case 12: return NUMBER_OF_TRADES;
      case 13: return NUMBER_OF_SHIPS_BUILT;
      case 14: return NUMBER_OF_BUILDINGS_BUILT;
      case 15: return POPULATION_GROWTH;
      case 16: return DIPLOMATIC_TRADE;
      case 17: return WAR_DECLARATIONS;
      case 18: return RESEARCH_ARTIFACTS;
      default:
        throw new IllegalArgumentException("Unexpected StatType:"
            + index);
    }
  }
}
