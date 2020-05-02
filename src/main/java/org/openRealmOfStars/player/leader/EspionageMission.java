package org.openRealmOfStars.player.leader;

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
* Espionage mission for leaders.
*
*/
public enum EspionageMission {
  /**
   * Sabotage current production.
   *  If succeed production loses half of the production.
   */
  SABOTAGE(40, 50),
  /**
   * Assasing the governor.
   */
  ASSASSIN_GOVERNOR(20, 60),
  /**
   * Steal tech which your realm does not have.
   */
  STEAL_TECH(50, 50),
  /**
   * Steal credit from planet's cash
   */
  STEAL_CREDIT(60, 40),
  /**
   * Terrorist attack. This will kill both people and destroy
   * buildings.
   */
  TERRORIST_ATTACK(40, 70),
  /**
   * Demolish single building but avoid killing people.
   */
  DEMOLISH_BUILDING(20, 60),
  /**
   * If succeed diplomatic bonus is gained towards realm.
   */
  GAIN_TRUST(80, 20);

  /**
   * Create espionage mission
   * @param baseChance Base chance for succeed
   * @param baseDetection Base detection
   */
  EspionageMission(final int baseChance, final int baseDetection) {
    this.baseChance = baseChance;
    this.baseDetection = baseDetection;
  }

  /**
   * Base chance for succeed.
   */
  private int baseChance;
  /**
   * Base detection for getting caught.
   * Mission can be still successful but agent can be get caught.
   */
  private int baseDetection;
  /**
   * @return the baseChance
   */
  public int getBaseChance() {
    return baseChance;
  }
  /**
   * @return the baseDetection
   */
  public int getBaseDetection() {
    return baseDetection;
  }

  /**
   * Get Espionage mission name as a string.
   * @return String
   */
  public String getName() {
    switch (this) {
      default:
      case SABOTAGE: return "Sabotage";
      case ASSASSIN_GOVERNOR: return "Assasin governor";
      case DEMOLISH_BUILDING: return "Demolish";
      case GAIN_TRUST: return "Gain trust";
      case STEAL_CREDIT: return "Steal credit";
      case STEAL_TECH: return "Steal tech";
      case TERRORIST_ATTACK: return "Terrorist attack";
    }
  }

  /**
   * Get Espionage mission name as a string.
   * @return String
   */
  public String getDescription() {
    switch (this) {
      default:
      case SABOTAGE: return "Sabotage current production. "
          + "If succeed production loses half of the production.";
      case ASSASSIN_GOVERNOR: return "Assasin the governor.";
      case DEMOLISH_BUILDING: return "Demolish single building";
      case GAIN_TRUST: return "Get diplomatic bonus if succeed";
      case STEAL_CREDIT: return "Steal credit from the planet.";
      case STEAL_TECH: return "Steal tech from the planet";
      case TERRORIST_ATTACK: return "Destroying building and population.";
    }
  }

}
