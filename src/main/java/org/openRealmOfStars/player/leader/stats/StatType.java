package org.openRealmOfStars.player.leader.stats;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
   * Total amount of turns as ruler.
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
   * How many turns have been as commander.
   */
  COMMANDER_LENGTH,
  /**
   * How many turns have been as governor.
   */
  GOVERNOR_LENGTH,
  /**
   * How many times have been in jail.
   */
  NUMBER_OF_JAIL_TIME,
  /**
   * How many turns have been in jail.
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
  WAR_DECLARATIONS;

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
      default:
        throw new IllegalArgumentException("Unexpected StatType:"
            + this.toString());
    }
  }
}
