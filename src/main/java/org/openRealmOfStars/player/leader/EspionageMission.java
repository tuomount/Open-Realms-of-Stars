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
   * Get Espionage mission name
   * @return name as String.
   */
  public String getName() {
    switch (this) {
      default:
      case SABOTAGE: return "SABOTAGE";
      case ASSASSIN_GOVERNOR: return "ASSASIN";
      case DEMOLISH_BUILDING: return "DEMOLISH";
      case GAIN_TRUST: return "GAIN_TRUST";
      case STEAL_CREDIT: return "STEAL_CREDIT";
      case STEAL_TECH: return "STEAL_TECH";
      case TERRORIST_ATTACK: return "TERRORIST_ATTACK";
    }
  }

  /**
   * Get Espionage mission based by string.
   * If mission is not found this will then return gain trust.
   * @param name Mission name as a String
   * @return Espionage Mission.
   */
  public static EspionageMission getMission(final String name) {
    if (name.equals(SABOTAGE.getName())) {
      return SABOTAGE;
    }
    if (name.equals(ASSASSIN_GOVERNOR.getName())) {
      return ASSASSIN_GOVERNOR;
    }
    if (name.equals(DEMOLISH_BUILDING.getName())) {
      return DEMOLISH_BUILDING;
    }
    if (name.equals(GAIN_TRUST.getName())) {
      return GAIN_TRUST;
    }
    if (name.equals(STEAL_CREDIT.getName())) {
      return STEAL_CREDIT;
    }
    if (name.equals(STEAL_TECH.getName())) {
      return STEAL_TECH;
    }
    if (name.equals(TERRORIST_ATTACK.getName())) {
      return TERRORIST_ATTACK;
    }
    return EspionageMission.GAIN_TRUST;
  }

  @Override
  public String toString() {
    switch (this) {
      default:
      case SABOTAGE: return "Sabotage";
      case ASSASSIN_GOVERNOR: return "Assassin governor";
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
          + "If succeed production loses half of the production. "
          + "Worst case: Commander dies";
      case ASSASSIN_GOVERNOR: return "Assasin the governor. "
          + "Worst case: Commander dies";
      case DEMOLISH_BUILDING: return "Demolish single building."
          + "Worst case: Commander dies";
      case GAIN_TRUST: return "Get diplomatic bonus if succeed. "
          + "Worst case: Diplomatic reputation is losed.";
      case STEAL_CREDIT: return "Steal credit from the planet."
          + "Worst case: Commander dies";
      case STEAL_TECH: return "Steal tech from the planet"
          + "Worst case: Commander dies";
      case TERRORIST_ATTACK: return "Destroying building and population."
          + "Worst case: Commander dies and war starts";
    }
  }

  /**
   * Get experience reward for commander when successfully espionage
   * done.
   * @return experience reward
   */
  public int getExperienceReward() {
    switch (this) {
      default:
      case SABOTAGE: return 40;
      case ASSASSIN_GOVERNOR: return 150;
      case DEMOLISH_BUILDING: return 100;
      case GAIN_TRUST: return 10;
      case STEAL_CREDIT: return 50;
      case STEAL_TECH: return 50;
      case TERRORIST_ATTACK: return 200;
    }
  }
}
