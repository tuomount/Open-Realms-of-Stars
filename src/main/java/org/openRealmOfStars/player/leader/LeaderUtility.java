package org.openRealmOfStars.player.leader;

import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SocialSystem;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

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
* Leader Uitlity
*
*/
public final class LeaderUtility {

  /**
   * Special level for marking start ruler for realm.
   */
  public static final int LEVEL_START_RULER = -1;

  /**
   * Good perk type
   */
  public static final int PERK_TYPE_GOOD = 0;
  /**
   * Bad perk type
   */
  public static final int PERK_TYPE_BAD = 1;
  /**
   * Governor type perk
   */
  public static final int PERK_TYPE_GOVERNOR = 2;
  /**
   * Ruler type perk
   */
  public static final int PERK_TYPE_RULER = 3;
  /**
   * Commander type perk
   */
  public static final int PERK_TYPE_COMMANDER = 4;
  /**
   * Hidden constructor.
   */
  private LeaderUtility() {
    // Hiding the constructor.
  }

  /**
   * Create new leader based on 3 parameters.
   * @param info Realm who is creating new leader.
   * @param planet Home planet for leader.
   * @param level Leader leve. Note this can be LEVEL_START_RULER for
   *        realm starting ruler.
   * @return Leader
   */
  public static Leader createLeader(final PlayerInfo info, final Planet planet,
      final int level) {
    Gender gender = Gender.NONE;
    if (info.getRace() != SpaceRace.MECHIONS) {
      if (level == LEVEL_START_RULER) {
        if (info.getGovernment() == GovernmentType.EMPIRE
            || info.getGovernment() == GovernmentType.KINGDOM) {
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.EQUAL) {
            gender = Gender.getRandom();
          }
        } else {
          gender = Gender.getRandom();
        }
      } else {
        gender = Gender.getRandom();
      }
    }
    Leader leader = new Leader(NameGenerator.generateName(info.getRace(),
        gender));
    leader.setGender(gender);
    leader.setRace(info.getRace());
    leader.setExperience(0);
    leader.setHomeworld(planet.getName());
    leader.setJob(Job.UNASSIGNED);
    leader.setTitle("");
    if (level == LEVEL_START_RULER) {
      leader.setLevel(1);
      leader.setAge(30 + DiceGenerator.getRandom(20));
      if (leader.getRace().getLifeSpan() < 80) {
        // Low life span starts about 10 years younger as starting ruler
        leader.setAge(25 + DiceGenerator.getRandom(10));
      }
      if (leader.getRace() == SpaceRace.MECHIONS) {
        leader.setAge(4 + DiceGenerator.getRandom(10));
      }
    } else {
      leader.setLevel(level);
      leader.setAge(23 + DiceGenerator.getRandom(15));
      if (leader.getRace() == SpaceRace.MECHIONS) {
        // Mechion leaders are always almost brand new ones.
        leader.setAge(1);
      }
    }
    for (int i = 0; i < leader.getLevel(); i++) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_GOOD);
      int index = DiceGenerator.getRandom(newPerks.length - 1);
      leader.getPerkList().add(newPerks[index]);
      if (DiceGenerator.getRandom(99)  < 10) {
        newPerks = getNewPerks(leader, PERK_TYPE_BAD);
        index = DiceGenerator.getRandom(newPerks.length - 1);
        leader.getPerkList().add(newPerks[index]);
      }
    }
    return leader;
  }

  /**
   * Recruite leader to leader pool. This will find best planet to hire
   * and cost for it. After this realm will have new leader ready to use.
   * This method will also return hired leader or null if hire is not possible.
   * @param planets Array of all planets
   * @param info Realm who is doing the hire
   * @return Leader or null
   */
  public static Leader recruiteLeader(final ArrayList<Planet> planets,
      final PlayerInfo info) {
    Leader leader = null;
    Planet trainingPlanet = LeaderUtility.getBestLeaderTrainingPlanet(
        planets, info);
    int leaderCost = LeaderUtility.leaderRecruitCost(info);
    if (trainingPlanet != null && info.getTotalCredits() >= leaderCost) {
      info.setTotalCredits(info.getTotalCredits() - leaderCost);
      int level = 1;
      int xp = 0;
      for (Building building : trainingPlanet.getBuildingList()) {
        if (building.getName().equals("Barracks")) {
          xp = 50;
        }
        if (building.getName().equals("Space academy")) {
          level++;
        }
      }
      leader = LeaderUtility.createLeader(info, trainingPlanet, level);
      leader.setExperience(xp);
      leader.assignJob(Job.UNASSIGNED, info);
      info.getLeaderPool().add(leader);
      trainingPlanet.takeColonist();
    }
    return leader;
  }

  /**
   * Assign leader for target job.
   * @param leader Leader to assign
   * @param player Realm aka PlayerInfo
   * @param planets List of all planets in starmap
   * @param target Planet or fleet.
   * @return True if assign was successful.
   */
  public static boolean assignLeader(final Leader leader,
      final PlayerInfo player, final ArrayList<Planet> planets,
      final Object target) {
    boolean result = false;
    Planet activePlanet = null;
    Fleet activeFleet = null;
    if (target instanceof Planet) {
      activePlanet = (Planet) target;
    }
    if (target instanceof Fleet) {
      activeFleet = (Fleet) target;
    }
    if (leader != null && (leader.getJob() == Job.UNASSIGNED
        || leader.getJob() == Job.COMMANDER
        || leader.getJob() == Job.GOVERNOR)) {
      if (leader.getJob() == Job.COMMANDER) {
        for (int i = 0; i < player.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = player.getFleets().getByIndex(i);
          if (fleet.getCommander() == leader) {
            fleet.setCommander(null);
            break;
          }
        }
      }
      if (leader.getJob() == Job.GOVERNOR) {
        for (Planet planet : planets) {
          if (planet.getGovernor() == leader) {
            planet.setGovernor(null);
          }
        }
      }
      if (activePlanet != null) {
        if (activePlanet.getGovernor() != null) {
          activePlanet.getGovernor().assignJob(Job.UNASSIGNED, player);
        }
        activePlanet.setGovernor(leader);
        result = true;
      }
      if (activeFleet != null) {
        if (activeFleet.getCommander() != null) {
          activeFleet.getCommander().assignJob(Job.UNASSIGNED, player);
        }
        activeFleet.setCommander(leader);
        leader.setTitle(LeaderUtility.createTitleForLeader(leader, player));
        result = true;
      }
    }
    return result;
  }
  /**
   * Adds random perks.
   * 60% new perk is related to current job.
   * 40% any good perk is added.
   * After adding good perk there is 10% that also bad perk is added.
   * @param leader who will get new perk
   */
  public static void addRandomPerks(final Leader leader) {
    boolean jobBasedPerkAdded = false;
    if (DiceGenerator.getRandom(99) < 60) {
      // Add Perk based on job
      Perk[] newPerks = null;
      if (leader.getJob() == Job.RULER) {
        newPerks = getNewPerks(leader, PERK_TYPE_RULER);
      }
      if (leader.getJob() == Job.GOVERNOR) {
        newPerks = getNewPerks(leader, PERK_TYPE_GOVERNOR);
      }
      if (leader.getJob() == Job.COMMANDER) {
        newPerks = getNewPerks(leader, PERK_TYPE_COMMANDER);
      }
      if (newPerks != null && newPerks.length > 0) {
        int index = DiceGenerator.getRandom(newPerks.length - 1);
        leader.getPerkList().add(newPerks[index]);
        jobBasedPerkAdded = true;
      }
    }
    if (!jobBasedPerkAdded) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_GOOD);
      int index = DiceGenerator.getRandom(newPerks.length - 1);
      leader.getPerkList().add(newPerks[index]);
    }
    if (DiceGenerator.getRandom(99)  < 10) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_BAD);
      int index = DiceGenerator.getRandom(newPerks.length - 1);
      leader.getPerkList().add(newPerks[index]);
    }
  }

  /**
   * Get best leader training planet for realm
   * @param planets Array of planet
   * @param realm Realm who is about to train leader
   * @return Best planet or null if nothing is available.
   */
  public static Planet getBestLeaderTrainingPlanet(
      final ArrayList<Planet> planets, final PlayerInfo realm) {
    Planet result = null;
    int bestPlanetValue = 0;
    for (Planet planet : planets) {
      if (planet.getPlanetPlayerInfo() == realm) {
        int value = planet.getTotalPopulation();
        if (value > 4) {
          for (Building building : planet.getBuildingList()) {
            if (building.getName().equals("Barracks")) {
              value = value + 20;
            }
            if (building.getName().equals("Space academy")) {
              value = value + 40;
            }
          }
          if (value > bestPlanetValue) {
            bestPlanetValue = value;
            result = planet;
          }
        }
      }
    }
    return result;
  }
  /**
   * Calculate leader recruit cost.
   * @param realm PlayerInfo
   * @return Leader recruit cost.
   */
  public static int leaderRecruitCost(final PlayerInfo realm) {
    int result = 0;
    int leaders = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getParent() == null && (leader.getJob() == Job.COMMANDER
          || leader.getJob() == Job.GOVERNOR
          || leader.getJob() == Job.RULER
          || leader.getJob() == Job.UNASSIGNED)) {
        leaders++;
      }
    }
    int cost = realm.getGovernment().leaderRecruitCost();
    if (leaders < realm.getGovernment().leaderPoolLimit()) {
      return cost;
    }
    if (leaders < 11) {
      return cost * leaders;
    }
    result = cost * leaders;
    if (result < 100) {
      result = result + 100;
    }
    return result;
  }
  /**
   * Create Title for leader
   * @param leader Leader to whom to create title
   * @param realm Realm where leader belongs to.
   * @return Title name as string.
   */
  public static String createTitleForLeader(final Leader leader,
      final PlayerInfo realm) {
    StringBuilder sb = new StringBuilder();
    if (leader.getJob() == Job.RULER) {
      switch (realm.getGovernment()) {
        default:
        case DEMOCRACY:
        case FEDERATION:
        case REPUBLIC:
        case ALLIANCE: {
          sb.append("President");
          break;
        }
        case EMPIRE: {
          if (leader.getGender() == Gender.FEMALE) {
            sb.append("Empiress");
          } else {
            sb.append("Emperor");
          }
          break;
        }
        case KINGDOM: {
          if (leader.getGender() == Gender.FEMALE) {
            sb.append("Queen");
          } else {
            sb.append("King");
          }
          break;
        }
        case HORDE:
        case MECHANICAL_HORDE:
        case CLAN: {
          sb.append("Chief");
          break;
        }
        case ENTERPRISE: {
          sb.append("CEO");
          break;
        }
        case GUILD:
        case HEGEMONY: {
          sb.append("Leader");
          break;
        }
        case AI: {
          sb.append("Main Process");
          break;
        }
        case NEST:
        case HIVEMIND: {
          sb.append("Master");
          break;
        }
        case HIERARCHY: {
          if (leader.getGender() == Gender.FEMALE) {
            sb.append("Lady");
          } else {
            sb.append("Lord");
          }
          break;
        }
      }
    }
    if (leader.getJob() == Job.COMMANDER) {
      if (leader.getMilitaryRank() == MilitaryRank.CIVILIAN) {
        leader.setMilitaryRank(MilitaryRank.ENSIGN);
      }
      sb.append(leader.getMilitaryRank().toString());
    }
    if (leader.getJob() == Job.GOVERNOR) {
      if (leader.getParent() != null) {
        if (leader.getGender() == Gender.FEMALE) {
          sb.append("Princess");
        } else {
          sb.append("Prince");
        }
      } else {
        sb.append("Governor");
      }
    }
    if (leader.getJob() == Job.TOO_YOUNG) {
      if (leader.getGender() == Gender.FEMALE) {
        sb.append("Princess");
      } else {
        sb.append("Prince");
      }
    }
    if (leader.getJob() == Job.UNASSIGNED) {
      if (leader.getParent() != null) {
        if (leader.getGender() == Gender.FEMALE) {
          sb.append("Princess");
        } else {
          sb.append("Prince");
        }
      } else if (leader.getMilitaryRank() != MilitaryRank.CIVILIAN) {
        sb.append(leader.getMilitaryRank().toString());
      } else {
       sb.append("");
      }
    }
    // Dead leader keeps it previous title, no need to change it.
    return sb.toString();
  }
  /**
   * Get list of new perks that leader is missing.
   * @param leader Leader whose perks to check.
   * @param perkType Perk type good, bad, ruler etc.
   * @return Array of new perks.
   */
  public static Perk[] getNewPerks(final Leader leader, final int perkType) {
    ArrayList<Perk> list = new ArrayList<>();
    for (Perk perk : Perk.values()) {
      if (perkType == PERK_TYPE_GOOD && perk.isBadPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_BAD && !perk.isBadPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_RULER && !perk.isRulerPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_GOVERNOR && !perk.isGovernorPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_COMMANDER && !perk.isFleetCommanderPerk()) {
        continue;
      }
      boolean alreadyHas = false;
      for (Perk leaderPerk : leader.getPerkList()) {
        if (perk == leaderPerk) {
          alreadyHas = true;
          break;
        }
      }
      if (!alreadyHas) {
        list.add(perk);
      }
    }
    return list.toArray(new Perk[list.size()]);
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
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      result = result + 25;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      result = result - 40;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 50;
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
    return result;
  }

  /**
   * Get Strongest leader for ruler.
   * @param realm PlayerInfo
   * @return Strongest leader in realm.
   */
  public static Leader getStrongestLeader(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getStrongPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
        bestLeader = leader;
        value = score;
      }
    }
    return bestLeader;
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
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getStrongHeirPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
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
      if (leader.hasPerk(Perk.WARLORD)) {
        result = result + 3;
      }
      if (leader.hasPerk(Perk.WEAK_LEADER)) {
        result = result - 10;
      }
      if (leader.hasPerk(Perk.EXPLORER)) {
        result = result + 1;
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
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getBusinessPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
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
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getDemocraticPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
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
    if (leader.hasPerk(Perk.WARLORD)) {
      result = result + 10;
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
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getFederationPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
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
      result = result + 10;
    }
    if (leader.hasPerk(Perk.GOOD_LEADER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.SCIENTIST)) {
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
    if (leader.hasPerk(Perk.CORRUPTED)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.EXPLORER)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.FTL_ENGINEER)) {
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
    return result;
  }

  /**
   * Get best hegemony ruler.
   * @param realm Realm who is evaluating new ruler
   * @return Hegemony ruler
   */
  public static Leader getNextHegemonyRuler(final PlayerInfo realm) {
    Leader bestLeader = null;
    int value = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getHegemonyPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
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
    if (leader.hasPerk(Perk.ACADEMIC)) {
      result = result + 10;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      result = result - 10;
    }
    if (leader.hasPerk(Perk.POWER_HUNGRY)) {
      result = result + 40;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
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
      if (leader.getJob() == Job.DEAD || leader.getJob() == Job.TOO_YOUNG) {
        continue;
      }
      int score = getAiPoints(leader);
      if (score > value) {
        bestLeader = leader;
        value = score;
      }
      if (bestLeader == null) {
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
    switch (realm.getGovernment()) {
      default:
      case CLAN:
      case HORDE:
      case MECHANICAL_HORDE:
      case HIERARCHY:
      case HIVEMIND:
      case NEST: {
        bestLeader = getStrongestLeader(realm);
        break;
      }
      case EMPIRE:
      case KINGDOM: {
        bestLeader = getNextHeir(realm);
        if (bestLeader == null) {
          bestLeader = getStrongestLeader(realm);
        }
        break;
      }
      case GUILD:
      case ENTERPRISE: {
        bestLeader = getNextCeo(realm);
        break;
      }
      case DEMOCRACY:
      case ALLIANCE: {
        bestLeader = getNextDemocraticRuler(realm);
        break;
      }
      case FEDERATION:
      case REPUBLIC: {
        bestLeader = getNextFederationRuler(realm);
        break;
      }
      case HEGEMONY: {
        bestLeader = getNextHegemonyRuler(realm);
        break;
      }
      case AI: {
        bestLeader = getNextAiRuler(realm);
        break;
      }
    }
    return bestLeader;
  }

  /**
   * Assign Leader as a realm ruler.
   * @param ruler Leader to assign as ruler
   * @param realm Realm which is about to get new ruler.
   * @param map StarMap for going through planets.
   */
  public static void assignLeaderAsRuler(final Leader ruler,
      final PlayerInfo realm, final StarMap map) {
    if (ruler != null && (ruler.getJob() == Job.UNASSIGNED
        || ruler.getJob() == Job.COMMANDER
        || ruler.getJob() == Job.GOVERNOR
        || ruler.getJob() == Job.RULER)) {
      if (ruler.getJob() == Job.COMMANDER) {
        for (int i = 0; i < realm.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = realm.getFleets().getByIndex(i);
          if (fleet.getCommander() == ruler) {
            fleet.setCommander(null);
            break;
          }
        }
      }
      if (ruler.getJob() == Job.GOVERNOR) {
        for (Planet planet : map.getPlanetList()) {
          if (planet.getGovernor() == ruler) {
            planet.setGovernor(null);
          }
        }
      }
      realm.setRuler(ruler);
      ruler.setTimeInJob(0);
    }
  }

  /**
   * Get Icon for leader based on leader current job.
   * @param leader Leader to get icon
   * @return Icon16x16
   */
  public static Icon16x16 getIconBasedOnLeaderJob(final Leader leader) {
    switch (leader.getJob()) {
      case RULER: {
        return Icons.getIconByName(Icons.ICON_RULER);
      }
      case COMMANDER: {
        return Icons.getIconByName(Icons.ICON_COMMANDER);
     }
      case GOVERNOR: {
        return Icons.getIconByName(Icons.ICON_GOVERNOR);
      }
      case DEAD: {
        return Icons.getIconByName(Icons.ICON_DEATH);
      }
      default:
      case UNASSIGNED: {
        return Icons.getIconByName(Icons.ICON_AIRLOCK_OPEN);
      }
      case TOO_YOUNG: {
        return Icons.getIconByName(Icons.ICON_TOO_YOUNG);
      }
    }
  }
}
