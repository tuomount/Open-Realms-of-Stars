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
* Leader Stats
*
*/
public class LeaderStats {
  /**
   * Ruler reign length in turns.
   */
  private int reignLength;
  /**
   * How many times leader has been ruler.
   */
  private int numberOfRulers;
  /**
   * How many battles leader have had.
   */
  private int numberOfBattles;
  /**
   * How many espionage mission leader have had.
   */
  private int numberOfEspionage;
  /**
   * How many times leader has explored space anomaly.
   */
  private int numberOfAnomaly;
  /**
   * Total number of turn leader has been commander.
   */
  private int commanderLength;
  /**
   * Total number of turn leader has been governor.
   */
  private int governorLength;
  /**
   * Total number of jail times for leader.
   */
  private int numberOfJailTime;
  /**
   * Total number of turns in jail.
   */
  private int jailTime;
  /**
   * Total number of killed another leaders.
   */
  private int killedAnotherLeader;
  /**
   * How many battles leader have had against pirates/privateers.
   */
  private int numberOfPirateBattles;
  /**
   * How many privateer leader has done.
   */
  private int numberOfPrivateers;
  /**
   * How many trades leader have had.
   */
  private int numberOfTrades;

  /**
   * How many ships has been built as a governor.
   */
  private int numberOfShipsBuilt;
  /**
   * How many buildings has been built as a governor.
   */
  private int numberOfBuildingsBuilt;
  /**
   * How much population has growth during governor.
   */
  private int populationGrowth;
  /**
   * How many diplomatic trades has been done as a ruler.
   */
  private int diplomaticTrades;
  /**
   * How many war declaration has been done as a ruler.
   */
  private int warDeclarations;
  /**
   * Constructor of leader stats.
   */
  public LeaderStats() {
    reignLength = 0;
    numberOfRulers = 0;
    numberOfBattles = 0;
    numberOfEspionage = 0;
    numberOfAnomaly = 0;
    commanderLength = 0;
    governorLength = 0;
    numberOfJailTime = 0;
    jailTime = 0;
    killedAnotherLeader = 0;
    numberOfPirateBattles = 0;
    numberOfPrivateers = 0;
    numberOfTrades = 0;
    numberOfShipsBuilt = 0;
    numberOfBuildingsBuilt = 0;
    populationGrowth = 0;
    diplomaticTrades = 0;
    warDeclarations = 0;
  }

  /**
   * Set Stat value.
   * @param type Stat type which to set.
   * @param value Value where to set.
   */
  public void setStat(final StatType type, final int value) {
    if (value >= 0 && value <= 65535) {
      switch (type) {
        case RULER_REIGN_LENGTH: reignLength = value; break;
        case NUMBER_OF_RULER: numberOfRulers = value; break;
        case NUMBER_OF_BATTLES: numberOfBattles = value; break;
        case NUMBER_OF_ESPIONAGE: numberOfEspionage = value; break;
        case NUMBER_OF_ANOMALY: numberOfAnomaly = value; break;
        case COMMANDER_LENGTH: commanderLength = value; break;
        case GOVERNOR_LENGTH: governorLength = value; break;
        case NUMBER_OF_JAIL_TIME: numberOfJailTime = value; break;
        case JAIL_TIME: jailTime = value; break;
        case KILLED_ANOTHER_LEADER: killedAnotherLeader = value; break;
        case NUMBER_OF_PIRATE_BATTLES: numberOfPirateBattles = value; break;
        case NUMBER_OF_PRIVATEERING: numberOfPrivateers = value; break;
        case NUMBER_OF_TRADES: numberOfTrades = value; break;
        case NUMBER_OF_SHIPS_BUILT: numberOfShipsBuilt = value; break;
        case NUMBER_OF_BUILDINGS_BUILT: numberOfBuildingsBuilt = value; break;
        case POPULATION_GROWTH: populationGrowth = value; break;
        case DIPLOMATIC_TRADE: diplomaticTrades = value; break;
        case WAR_DECLARATIONS: warDeclarations = value; break;
        default: throw new IllegalArgumentException("Unexpected stat type: "
            + type.toString());
      }
    }
  }

  /**
   * Get leader stat for certain stat type.
   * @param type Stat type for get.
   * @return Stat value.
   */
  public int getStat(final StatType type) {
    switch (type) {
      case RULER_REIGN_LENGTH: return reignLength;
      case NUMBER_OF_RULER: return numberOfRulers;
      case NUMBER_OF_BATTLES: return numberOfBattles;
      case NUMBER_OF_ESPIONAGE: return numberOfEspionage;
      case NUMBER_OF_ANOMALY: return numberOfAnomaly;
      case COMMANDER_LENGTH: return commanderLength;
      case GOVERNOR_LENGTH: return governorLength;
      case NUMBER_OF_JAIL_TIME: return numberOfJailTime;
      case JAIL_TIME: return jailTime;
      case KILLED_ANOTHER_LEADER: return killedAnotherLeader;
      case NUMBER_OF_PIRATE_BATTLES: return numberOfPirateBattles;
      case NUMBER_OF_PRIVATEERING: return numberOfPrivateers;
      case NUMBER_OF_TRADES: return numberOfTrades;
      case NUMBER_OF_SHIPS_BUILT: return numberOfShipsBuilt;
      case NUMBER_OF_BUILDINGS_BUILT: return numberOfBuildingsBuilt;
      case POPULATION_GROWTH: return populationGrowth;
      case DIPLOMATIC_TRADE: return diplomaticTrades;
      case WAR_DECLARATIONS: return warDeclarations;
      default: throw new IllegalArgumentException("Unexpected stat type: "
          + type.toString());
    }
  }
}
