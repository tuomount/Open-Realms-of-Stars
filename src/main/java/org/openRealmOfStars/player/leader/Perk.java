package org.openRealmOfStars.player.leader;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019-2023 Tuomo Untinen
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
 */

import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.trait.TraitIds;

/**
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
  ADDICTED(23, "Addicted", "Leader dies signifigantly younger than others."),
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
      + " information on own realm's fleets."),
  /**
   * Leader is exceptionally good health.
   */
  HEALTHY(32, "Healthy", "Leader lives significantly longer than others."),
  /**
   * Leader has habit to be aggressive.
   */
  AGGRESSIVE(33, "Aggressive", "Leader is aggressive."
      + " +1 Initiave when commander, -1 Diplomacy"),
  /**
   * Leader has habit to be peaceful.
   */
  PEACEFUL(34, "Peaceful", "Leader is peaceful."
      + " -1 Initiave when commander, +1 Diplomacy when leader."),
  /**
   * Leader has habit to be logical.
   */
  LOGICAL(35, "Logical", "Leader is logical. No side effects."),
  /**
   * Leader has habit to be mad.
   */
  MAD(36, "Mad", "Leader is mad. -2 Diplomacy bonus when leader is ruler."),
  /**
   * Ruler perk for diplomatic. All diplomatic trades get +1.
   */
  DIPLOMATIC(37, "Diplomatic", "+1 Diplomacy bonus when leader is ruler."),
  /**
   * Perk which gives bonus on every job.
   */
  SKILLFUL(38, "Skillful", "+1 Diplomacy bonus, +5 accuracy,"
      + " +1 credit when governor"),
  /**
   * Perk which gives negative bonus on every job.
   */
  INCOMPETENT(39, "Incompetent", "-1 Diplomacy bonus, -5 accuracy,"
      + " -1 credit when governor"),
  /**
   * Perk which tells that leader has been in jail.
   * Might affect if he/she/it will get as a ruler.
   */
  CONVICT(40, "Convict", "Leader has been in jail"),
  /**
   * Perk which tells that leader has killed too young heir.
   */
  CRUEL(41, "Cruel", "Child killer, -2 diplomacy and -1 happiness"),
  /**
   * Perk which allows leader more to component overloads during combat.
   */
  MASTER_ENGINEER(42, "Master engineer", "Reduces overload failure by 2."),
  /**
   * Increases ancient artifact study by 2.
   */
  ARCHAEOLOGIST(43, "Archaeologist", "Increase ancient artifact study by 2."),
  /**
   * Gain more experience on away missions on unexplored planets.
   */
  TREKKER(44, "Trekker", "Gain more experience on away missions on"
      + " unexplored planets."),
  /**
   * +1 espionage bonus against every realm.
   */
  NEGOTIATOR(45, "Negotiator", "+1 espionage bonus against every realm."),
  /**
   * Gain more experience when finding new planets.
   */
  CARTOGRAPHER(46, "Cartographer", "Gain more experience when finding new"
      + " planets."),
  /**
   * Higher chance for critical hit in combat.
   */
  WAR_HERO(47, "War hero", "Higher chance for critical hit in combat.");



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
        || this == Perk.GOOD_LEADER
        || this == Perk.DIPLOMATIC
        || this == Perk.SKILLFUL
        || this == Perk.NEGOTIATOR) {
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
        || this == Perk.GOOD_LEADER
        || this == Perk.SKILLFUL) {
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
        || this == Perk.TRADER
        || this == Perk.SKILLFUL
        || this == Perk.MASTER_ENGINEER
        || this == Perk.TREKKER
        || this == Perk.CARTOGRAPHER
        || this == Perk.WAR_HERO) {
      return true;
    }
    return false;
  }
  /**
   * Is perk allowed for certain race.
   * @param race SpaceRace
   * @return True if perk is allowed
   */
  public boolean isPerkAllowedForRace(final SpaceRace race) {
    boolean result = true;
    if (this == Perk.AGRICULTURAL && !race.isEatingFood()) {
      // Agricultural perk has no use for races that don't eat food.
      result = false;
    }
    if (this == Perk.ADDICTED && race.isRoboticRace()) {
      // Addicted is not allowed for robots.
      result = false;
    }
    if (this == Perk.HEALTHY && race.isRoboticRace()) {
      // Robotic races cannot be healthy
      result = false;
    }
    if (this == Perk.CHARISMATIC && race.hasTrait(TraitIds.DISGUSTING)) {
      // Disgusting races cannot be charismatic
      result = false;
    }
    if (this == Perk.SKILLFUL && race.isRoboticRace()) {
      // Robots cannot be skillful
      result = false;
    }
    if (this == Perk.INCOMPETENT && race.isRoboticRace()) {
      // Robots cannot be incompetent
      result = false;
    }
    return result;
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
        || this == Perk.CHATTERBOX
        || this == Perk.AGGRESSIVE
        || this == Perk.PEACEFUL
        || this == Perk.LOGICAL
        || this == Perk.MAD
        || this == Perk.INCOMPETENT) {
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
        || this == Perk.WEALTHY
        || this == Perk.HEALTHY
        || this == Perk.ARCHAEOLOGIST) {
      return true;
    }
    return false;
  }
  /**
   * Is perk mental perk or something else
   * @return True if mental perk
   */
  public boolean isMentalPerk() {
    if (this == Perk.AGGRESSIVE
        || this == Perk.MAD
        || this == Perk.PEACEFUL
        || this == Perk.LOGICAL
        || this == Perk.ADDICTED
        || this == Perk.PACIFIST) {
      return true;
    }
    return false;
  }
  /**
   * Is perk gained via action.
   * @return True if gained only via action.
   */
  public boolean isGainedPerk() {
    if (this == Perk.CONVICT
        || this == Perk.CRUEL
        || this == Perk.NEGOTIATOR
        || this == Perk.WAR_HERO) {
      return true;
    }
    return false;
  }
  /**
   * Get Perk score for estimating best leader.
   * @param leaderJob Job what leader is going to do.
   * @return Perk score
   */
  public int getPerkScore(final Job leaderJob) {
    int result = 0;
    if (leaderJob == Job.COMMANDER && isFleetCommanderPerk()) {
      result = result + 2;
    }
    if (leaderJob == Job.GOVERNOR && isGovernorPerk()) {
      result = result + 2;
    }
    if (leaderJob == Job.RULER && isRulerPerk()) {
      result = result + 2;
    }
    if (isGenericPerk()) {
      result = result + 1;
    }
    if (isBadPerk()) {
      result = result - 1;
    }
    if (this == POWER_HUNGRY) {
      result = result - 2;
    }
    return result;
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

  /**
   * Is perk visible with certain knowledge bonus.
   * Knowledge bonus is between 0-10.
   * @param knowledgeBonus Value between 0-10
   * @return True if perk is visible.
   */
  public boolean isKnownPerk(final int knowledgeBonus) {
    switch (this) {
      case CHARISMATIC:
      case CHATTERBOX:
      case CONVICT:
      case CRUEL:
      case REPULSIVE:
      case ACADEMIC: {
        return true;
      }
      case PEACEFUL:
      case STUPID:
      case WEALTHY:
      case WAR_HERO:
      case DIPLOMATIC: {
        if (knowledgeBonus > 0) {
          return true;
        }
        break;
      }
      case POWER_HUNGRY:
      case LOGICAL:
      case SKILLFUL:
      case MAD:
      case INCOMPETENT: {
        if (knowledgeBonus > 1) {
          return true;
        }
        break;
      }
      case CORRUPTED:
      case GOOD_LEADER:
      case MILITARISTIC:
      case PACIFIST:
      case SLOW_LEARNER:
      case SCIENTIST:
      case WARLORD:
      case ARCHAEOLOGIST:
      case AGGRESSIVE: {
        if (knowledgeBonus > 2) {
          return true;
        }
        break;
      }
      case DISCIPLINE:
      case TRADER:
      case NEGOTIATOR:
      case MERCHANT:
      case WEAK_LEADER:
      case HEALTHY:
      case ADDICTED: {
        if (knowledgeBonus > 3) {
          return true;
        }
        break;
      }
      case FTL_ENGINEER:
      case MASTER_ENGINEER:
      case CARTOGRAPHER:
      case TREKKER:
      case EXPLORER: {
        if (knowledgeBonus > 4) {
          return true;
        }
        break;
      }
      case MICRO_MANAGER:
      case ARTISTIC: {
        if (knowledgeBonus > 5) {
          return true;
        }
        break;
      }
      case MINER:
      case AGRICULTURAL:
      case INDUSTRIAL: {
        if (knowledgeBonus > 6) {
          return true;
        }
        break;
      }
      case SCANNER_EXPERT:
      case COMBAT_TACTICIAN:
      case COMBAT_MASTER: {
        if (knowledgeBonus > 7) {
          return true;
        }
        break;
      }
      case SPY_MASTER: {
        if (knowledgeBonus > 8) {
          return true;
        }
        break;
      }
      case SECRET_AGENT:
      case COUNTER_AGENT: {
        if (knowledgeBonus > 9) {
          return true;
        }
        break;
      }
      default: {
        return false;
      }
    }
    return false;
  }
}
