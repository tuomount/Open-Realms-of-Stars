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
* Leader perk enumeration
*
*/
public enum Perk {

  /**
   * Ruler perk for militaristic.
   * +1 Fleet capacity when leader is ruler.
   */
  MILITARISTIC(0, "Militaristic", "+1 Fleet capacity when leader is ruler."),
  /**
   * Ruler perk for Warlord.
   * +1 war resistance when leader is ruler.
   */
  WARLORD(1, "Warlord", "+1 war resistance when leader is ruler."),
  /**
   * Ruler perk for charismatic. All diplomatic trades get +1.
   */
  CHARISMATIC(2, "Charismatic", "+1 Diplomacy bonus when leader is ruler."),
  /**
   * Governor perk for agricultural.
   * +1 Food production when leader is governor
   */
  AGRICULTURAL(3, "Agricultural",
      "+1 Food production when leader is governor"),
  /**
   * Governor perk for mining.
   * +1 Metal production when leader is governor
   */
  MINER(4, "Miner", "+1 Metal production when leader is governor"),
  /**
   * Governor perk for industrial.
   * +1 Production production when leader is governor
   */
  INDUSTRIAL(5, "Industrial",
      "+1 Production production when leader is governor"),
  /**
   * Ruler/Governor perk for scientist.
   * +1 Research production when leader is governor or ruler
   */
  SCIENTIST(6, "Scientist",
      "+1 Research production when leader is governor or ruler"),
  /**
   * Governor perk for culture.
   * +1 Culture production when leader is governor
   */
  ARTISTIC(7, "Artistic", "+1 Culture production when leader is governor"),
  /**
   * Ruler/Governor perk for credit.
   * +1 Credit prodcution when leader is governor or ruler
   */
  MERCHANT(8, "Merchant",
      "+1 Credit prodcution when leader is governor or ruler"),
  /**
   * Governor/Fleet commander perk for increasing troop power.
   * +25% troop power when leader is governor or fleet commander
   */
  DISCIPLINE(9, "Discipline",
      "+25% troop power when leader is governor or fleet commander"),
  /**
   * Governor/ruler perk for happiness.
   * +1 Happiness when leader is governor or ruler
   */
  GOOD_LEADER(10, "Good leader",
      "+1 Happiness when leader is governor or ruler"),
  /**
   * Fleet commander perk for regular speed.
   * +1 Speed when leader is fleet commander
   */
  EXPLORER(11, "Explorer", "+1 Speed when leader is fleet commander"),
  /**
   * Fleet commander perk for FTL speed.
   * +1 FTL Speed when leader is fleet commander
   */
  FTL_ENGINEER(12, "FTL Engineer",
      "+1 FTL Speed when leader is fleet commander"),
  /**
   * Fleet commander perk for combat initiative.
   * +1 Combat initiative when leader is fleet commander
   */
  COMBAT_TACTICIAN(13, "Combat tactician",
      "+1 Combat initiative when leader is fleet commander"),
  /**
   * Fleet commander perk for increase scanning range.
   */
  SCANNER_EXPERT(14, "Scanner expert", "+1 Scanning range"),
  /**
   * Fleet commander perk for detecting cloacked fleets.
   */
  COUNTER_AGENT(15, "Counter agent", "+5 Cloaking detection"),
  /**
   * Fleet commander perk for increased hit accuracy
   */
  COMBAT_MASTER(16, "Combat master", "+5 accuracy"),
  /**
   * Fleet commander perk for improving espionage.
   * +1 Espionage value when leader is fleet commander
   */
  SPY_MASTER(17, "Spy master",
      "+1 Espionage value when leader is fleet commander"),
  /**
   * Fleet commander perk for improving cloacking value.
   *  +5 Cloacking value when leader is fleet commander
   */
  SECRET_AGENT(18, "Secret agent",
      "+5 Cloacking value when leader is fleet commander"),
  /**
   * Fleet commander perk for better trading.
   *  +1 extra credit from trading when leader is fleet commander
   */
  TRADER(19, "Trader",
      "+1 extra credit from trading when leader is fleet commander"),
  /**
   * Stupid perk. -1 Research when leader is governor or ruler
   */
  STUPID(20, "Stupid", "-1 Research when leader is governor or ruler"),
  /**
   * Micro manager perk for governors.
   *  -1 Production production when leader is governor
   */
  MICRO_MANAGER(21, "Micro manager",
      "-1 Production production when leader is governor"),
  /**
   * Corrupted leader steal credit from realm.
   *  -1 Credit when leader is on duty
   */
  CORRUPTED(22, "Corrupted", "-1 Credit when leader is on duty"),
  /**
   * Addicted leader dies earlier than other.
   */
  ADDICTED(23, "Addicted", "Leader dies signifigantly younger than other"),
  /**
   * Pacifist leader.
   * -1 Fleet capacity when leader is ruler and cannot be assigned as
   * Fleet commander for military ships
   */
  PACIFIST(24, "Pacifist",
      "-1 Fleet capacity when leader is ruler and cannot be assigned as"
      + " Fleet commander for military ships"),
  /**
   * Weak leader bad perk. -1 war resistance when leader is ruler
   */
  WEAK_LEADER(25, "Weak leader", "-1 war resistance when leader is ruler"),
  /**
   * Slow learner bad perk. Experience requirement is doubled for next level
   */
  SLOW_LEARNER(26, "Slow learner",
      "Experience requirement is doubled for next level"),
  /**
   * Repulsive bad perk. -1 Diplomatic when leader is ruler
   */
  REPULSIVE(27, "Repulsive", "-1 Diplomatic when leader is ruler"),
  /**
   * Academic generic perk. Experience requirement is halved for next level
   */
  ACADEMIC(28, "Academic", "Experience requirement is halved for next level"),
  /**
   * Power hungry generic perk. Leader tries to get as ruler by any means.
   */
  POWER_HUNGRY(29, "Power hungry",
      "Leader tries to get as ruler by any means."),
  /**
   * Wealthy generic perk. Leader can pay this to avoid his/her/its death.
   */
  WEALTHY(30, "Wealthy", "Leader has massive amount of riches. This helps"
      + " leader to get away from tough situations."),
  /**
   * Perk which gives espionage bonus for other realms.
   */
  CHATTERBOX(31, "Chatterbox", "Ruler accidentaly tells about espionage"
      + " information on own realm's fleets.");


  /**
   * Constructor of perk enumeration
   * @param index Index number
   * @param name Perk name
   * @param desc Perk description
   */
  Perk(final int index, final String name, final String desc) {
    this.index = index;
    this.name = name;
    this.description = desc;
  }

  /**
   * Get Perk by index;
   * @param index Index to get perk
   * @return Perk
   */
  public static Perk getByIndex(final int index) {
    if (index >= 0 && index < Perk.values().length) {
      return Perk.values()[index];
    }
    return Perk.MILITARISTIC;
  }
  /**
   * Is perk ruler perk or something else
   * @return True if ruler perk
   */
  public boolean isRulerPerk() {
    if (this == Perk.MILITARISTIC
        || this == Perk.WARLORD
        || this == Perk.CHARISMATIC
        || this == Perk.SCIENTIST
        || this == Perk.MERCHANT
        || this == Perk.GOOD_LEADER) {
      return true;
    }
    return false;
  }
  /**
   * Is perk governor perk or something else
   * @return True if governor perk
   */
  public boolean isGovernorPerk() {
    if (this == Perk.AGRICULTURAL
        || this == Perk.MINER
        || this == Perk.INDUSTRIAL
        || this == Perk.SCIENTIST
        || this == Perk.ARTISTIC
        || this == Perk.MERCHANT
        || this == Perk.DISCIPLINE
        || this == Perk.GOOD_LEADER) {
      return true;
    }
    return false;
  }
  /**
   * Is perk fleet commander perk or something else
   * @return True if fleet commander perk
   */
  public boolean isFleetCommanderPerk() {
    if (this == Perk.DISCIPLINE
        || this == Perk.EXPLORER
        || this == Perk.FTL_ENGINEER
        || this == Perk.COMBAT_TACTICIAN
        || this == Perk.COMBAT_MASTER
        || this == Perk.COUNTER_AGENT
        || this == Perk.SCANNER_EXPERT
        || this == Perk.SPY_MASTER
        || this == Perk.SECRET_AGENT
        || this == Perk.TRADER) {
      return true;
    }
    return false;
  }
  /**
   * Is perk bad perk or something else
   * @return True if bad perk
   */
  public boolean isBadPerk() {
    if (this == Perk.STUPID
        || this == Perk.MICRO_MANAGER
        || this == Perk.CORRUPTED
        || this == Perk.ADDICTED
        || this == Perk.PACIFIST
        || this == Perk.WEAK_LEADER
        || this == Perk.SLOW_LEARNER
        || this == Perk.REPULSIVE
        || this == Perk.CHATTERBOX) {
      return true;
    }
    return false;
  }
  /**
   * Is perk generic perk or something else
   * @return True if generic perk
   */
  public boolean isGenericPerk() {
    if (this == Perk.ACADEMIC
        || this == Perk.POWER_HUNGRY
        || this == Perk.WEALTHY) {
      return true;
    }
    return false;
  }
  /**
   * Perk Index.
   */
  private int index;
  /**
   * Description about the perk
   */
  private String description;
  /**
   * Perk name
   */
  private String name;
  /**
   * Get the Perk index
   * @return the index
   */
  public int getIndex() {
    return index;
  }
  /**
   * Get the perk description.
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get perk name as string
   * @return the name
   */
  public String getName() {
    return name;
  }
}
