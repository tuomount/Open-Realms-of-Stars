package org.openRealmOfStars.player.leader;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2024 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.Government;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Helper class for working with government's Rulers.
 *
 * Methods for selecting next ruler for the realm are
 * here primarily. More generic methods for working with Leaders,
 * like position assignment, are in LeaderUtility class.
 *
 * @see LeaderUtility
 */
public final class RulerUtility {

  /** Hidden constructor. */
  private RulerUtility() {
  }

  /**
   * Get Ruler title for leader and government type.
   *
   * @param gender Ruler gender
   * @param government Government type
   * @return Ruler title
   */
  public static String getRulerTitle(final Gender gender,
      final Government government) {
    if (gender == Gender.FEMALE) {
      return government.getRulerTitleFemale();
    }
    return government.getRulerTitleMale();
  }

  /**
   * Get How strong leader is.
   * This is used in horde and clan goverments to determine how
   * good candidate leader is for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getStrongPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 17 && leader.getAge() < 25) {
      result = 15 + leader.getAge();
    }
    if (leader.getAge() > 24 && leader.getAge() < 31) {
      result = 70 - leader.getAge();
    }
    if (leader.getAge() > 30 && leader.getAge() < 40) {
      result = 68 - leader.getAge();
    }
    if (leader.getAge() > 39 && leader.getAge() < 50) {
      result = 65 - leader.getAge();
    }
    if (leader.getAge() > 49 && leader.getAge() < 60) {
      result = 60 - leader.getAge();
    }
    if (leader.getAge() > 59) {
      result = 0;
    }
    if (leader.getParent() != null) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.COMBAT_MASTER)) {
      result = result + 32;
    }
    if (leader.hasPerk(Perk.COMBAT_TACTICIAN)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.DISCIPLINE)) {
      result = result + 22;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.COUNTER_AGENT)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 25;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 40;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 50;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 35;
    }
    if (leader.hasPerk(Perk.WEAK_LEADER)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 7;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get Strongest leader for ruler.
   * @param realm PlayerInfo
   * @param  xenophobe If true allows only original space race rulers.
   * @return Strongest leader in realm.
   */
  public static Leader getStrongestLeader(final PlayerInfo realm,
      final boolean xenophobe) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getStrongPoints(leader);
      if (xenophobe && leader.getRace() != realm.getRace()) {
        score = 0;
      }
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Realm has heir only too young heirs.
   * @param realm Realm to check
   * @return True if all heirs are too young to rule.
   */
  public static boolean hasTooYoungHeirs(final PlayerInfo realm) {
    boolean hasHeirs = false;
    boolean onlyTooYoungHeirs = true;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getParent() != null) {
        if (leader.getJob() == Job.DEAD || leader.getJob() == Job.PRISON) {
          continue;
        }
        hasHeirs = true;
        if (leader.getJob() != Job.TOO_YOUNG) {
          onlyTooYoungHeirs = false;
        }
      }
    }
    if (hasHeirs && onlyTooYoungHeirs) {
      return true;
    }
    return false;
  }

  /**
   * Get heir leader for ruler.
   * @param realm PlayerInfo
   * @return heir leader in realm.
   */
  public static Leader getNextHeir(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getStrongHeirPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get next possible heir leader for ruler.
   * @param realm PlayerInfo
   * @return heir leader in realm.
   */
  public static Leader getNextPossbileHeir(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getStrongHeirPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get How strong heir is.
   * This is used in kingdom and empire goverments to determine how
   * good candidate leader is for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getStrongHeirPoints(final Leader leader) {
    int result = 0;
    if (leader.getParent() != null) {
      result = leader.getAge() * 4;
      if (leader.hasPerk(Perk.COMBAT_MASTER)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.COMBAT_TACTICIAN)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.DISCIPLINE)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.CHARISMATIC)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.COUNTER_AGENT)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.CORRUPTED)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.MILITARISTIC)) {
        result = result + 2;
      }
      if (leader.hasPerk(Perk.POWER_HUNGRY)) {
        result = result + 10;
      }
      if (leader.hasPerk(Perk.WEALTHY)) {
        result = result + 10;
      }
      if (leader.hasPerk(Perk.WARLORD)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.WEAK_LEADER)) {
        result = result - 10;
      }
      if (leader.hasPerk(Perk.EXPLORER)) {
        result = result + 1;
      }
      if (leader.hasPerk(Perk.SKILLFUL)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.INCOMPETENT)) {
        result = result - 10;
      }
      if (leader.hasPerk(Perk.CONVICT)) {
        result = result - 10;
      }
    }
    return result;
  }

  /**
   * Get best business man as leader. Used for enterprise and guild ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getBusinessPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 2;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 15;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 30;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.TRADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 20;
    }
    return result;
  }

  /**
   * Get CEO leader for ruler.
   * @param realm PlayerInfo
   * @return ceo leader in realm.
   */
  public static Leader getNextCeo(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getBusinessPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get democratic scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getDemocraticPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 2;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 2;
    }
    if (leader.getAge() > 60) {
      result = result + 2;
    }
    if (leader.getAge() > 70) {
      result = result - 2;
    }
    if (leader.getAge() > 80) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 15;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.ARTISTIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    // Simulates voting for leader
    result = result + DiceGenerator.getRandom(20);
    return result;
  }

  /**
   * Get best democratic ruler.
   * @param realm Realm who is evaluating new ruler
   * @return Democratic ruler
   */
  public static Leader getNextDemocraticRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getDemocraticPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get scientist scores for leader.
   * @param leader Leader to evaluate
   * @return Scientist score
   */
  private static int getScientistPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 1;
    }
    if (leader.getAge() > 40) {
      result = result + 1;
    }
    if (leader.getAge() > 50) {
      result = result + 1;
    }
    if (leader.getAge() > 60) {
      result = result + 1;
    }
    if (leader.getAge() > 70) {
      result = result + 1;
    }
    if (leader.getAge() > 80) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 25;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.ARTISTIC)) {
      result = result + 2;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 15;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
      result = result + 30;
    }
    if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.SCANNER_EXPERT)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.FTL_ENGINEER)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.MASTER_ENGINEER)) {
      result = result + 3;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 1;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get best scientist leader.
   * @param realm Realm who is evaluating best scientist
   * @return Best scientist
   */
  public static Leader getBestScientist(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getScientistPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get federation scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getFederationPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 1;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 2;
    }
    if (leader.getAge() > 60) {
      result = result + 2;
    }
    if (leader.getAge() > 70) {
      result = result + 2;
    }
    if (leader.getAge() > 80) {
      result = result - 2;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 30;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.INDUSTRIAL)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MINER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 15;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.AGGRESSIVE)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    // Simulates voting for leader
    result = result + DiceGenerator.getRandom(20);
    return result;
  }

  /**
   * Get best federation ruler.
   * @param realm Realm who is evaluating new ruler
   * @return Federation ruler
   */
  public static Leader getNextFederationRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getFederationPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get hegemony scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getHegemonyPoints(final Leader leader) {
    int result = 0;
    if (leader.getAge() > 30) {
      result = result + 2;
    }
    if (leader.getAge() > 40) {
      result = result + 2;
    }
    if (leader.getAge() > 50) {
      result = result + 1;
    }
    if (leader.getAge() > 60) {
      result = result - 1;
    }
    if (leader.getAge() > 70) {
      result = result - 1;
    }
    if (leader.getAge() > 80) {
      result = result - 2;
    }
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 5;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.FTL_ENGINEER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MASTER_ENGINEER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MICRO_MANAGER)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.SCANNER_EXPERT)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.WEAK_LEADER)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.MAD)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get best hegemony ruler.
   * @param realm Realm who is evaluating new ruler
   * @param xenophobe If true only original space race is allowed to be ruler.
   * @return Hegemony ruler
   */
  public static Leader getNextHegemonyRuler(final PlayerInfo realm,
      final boolean xenophobe) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getHegemonyPoints(leader);
      if (xenophobe && leader.getRace() != realm.getRace()) {
        score = 0;
      }
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get air scores for ruler.
   * @param leader Leader to evaluate
   * @return Strong score
   */
  private static int getAiPoints(final Leader leader) {
    int result = 0;
    if (leader.hasPerk(Perk.CHARISMATIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.WEALTHY)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CRUEL)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.MERCHANT)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      result = result - 15;
    }
    if (leader.hasPerk(Perk.AGGRESSIVE)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SLOW_LEARNER)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.REPULSIVE)) {
      result = result - 15;
    }
    if (leader.hasPerk(Perk.WEAK_LEADER)) {
      result = result - 20;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      result = result + 20;
    }
    if (leader.hasPerk(Perk.SKILLFUL)) {
      result = result + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.CONVICT)) {
      result = result - 10;
    }
    return result;
  }

  /**
   * Get best ai ruler.
   * @param realm Realm who is evaluating new ruler
   * @return AI ruler
   */
  public static Leader getNextAiRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG
          || leader.getJob() == Job.PRISON) {
        continue;
      }
      int score = getAiPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
  }

  /**
   * Get Next ruler from the leader pool.
   * @param realm PlayerInfo
   * @return Next leader or null if not available.
   */
  public static Leader getNextRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    switch (realm.getGovernment().getRulerSelection()) {
      default:
      case STRONG_RULER: {
        bestLeader = getStrongestLeader(realm,
            realm.getGovernment().isXenophopic());
        break;
      }
      case HEIR_TO_THRONE: {
        bestLeader = getNextHeir(realm);
        if (bestLeader == null && hasTooYoungHeirs(realm)) {
          Message msg = new Message(MessageType.LEADER,
              realm.getEmpireName() + " has heirs but all are too young to be"
                  + " ruler. This is difficult time...",
                  Icons.getIconByName(Icons.ICON_RULER));
          realm.getMsgList().addUpcomingMessage(msg);
          break;
        }
        if (bestLeader == null) {
          bestLeader = getStrongestLeader(realm,
              realm.getGovernment().isXenophopic());
        }
        break;
      }
      case CEO_AS_A_RULER: {
        bestLeader = getNextCeo(realm);
        break;
      }
      case ELECTION_TYPE1: {
        bestLeader = getNextDemocraticRuler(realm);
        break;
      }
      case ELECTION_TYPE2: {
        bestLeader = getNextFederationRuler(realm);
        break;
      }
      case HEGEMONIA_RULER: {
        bestLeader = getNextHegemonyRuler(realm,
            realm.getGovernment().isXenophopic());
        break;
      }
      case AI_RULER: {
        bestLeader = getNextAiRuler(realm);
        break;
      }
    }
    return bestLeader;
  }

}
