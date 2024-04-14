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

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.AttitudeScore;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.Government;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.government.trait.GovTraitIds;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.SocialSystem;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
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
   * Mental type perk
   */
  public static final int PERK_TYPE_MENTAL = 5;
  /**
   * Perks which is gained via actions.
   */
  public static final int PERK_TYPE_GAINED = 6;

  /**
   * Hidden constructor.
   */
  private LeaderUtility() {
    // Hiding the constructor.
  }

  /**
   * Get heir name with parent's surname
   * @param heirName Heir name
   * @param parentName Parent name
   * @return heirname with parent's surname
   */
  public static String getParentSurname(final String heirName,
      final String parentName) {
    String[] heirNames = heirName.split(" ");
    String[] parentNames = parentName.split(" ");
    if (parentNames.length >= 2 && heirNames.length >= 2) {
      return heirNames[0] + " "
          + parentNames[parentNames.length - 1];
    }
    return heirName;
  }

  /**
   * Create new leader based on 3 parameters.
   * @param info Realm who is creating new leader.
   * @param planet Home planet for leader. This can be null.
   * @param level Leader leve. Note this can be LEVEL_START_RULER for
   *        realm starting ruler.
   * @return Leader
   */
  public static Leader createLeader(final PlayerInfo info, final Planet planet,
      final int level) {
    Gender gender = DiceGenerator.pickRandom(info.getRace().getGenders());
    // Adjust gender for starting rulers based on social system
    if (level == LEVEL_START_RULER
        && (info.getGovernment() == GovernmentType.EMPIRE
          || info.getGovernment() == GovernmentType.KINGDOM)) {
      if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
        gender = Gender.MALE;
      }
      if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
        gender = Gender.FEMALE;
      }
    }

    Leader leader = new Leader(NameGenerator.generateName(
        info.getRace().getNameGeneratorType(), gender));
    leader.setGender(gender);
    leader.setRace(info.getRace());
    leader.setExperience(0);
    if (planet != null) {
      leader.setHomeworld(planet.getName());
    } else {
      leader.setHomeworld("Unknown");
    }
    leader.setJob(Job.UNASSIGNED);
    leader.setTitle("");
    if (level == LEVEL_START_RULER) {
      leader.setLevel(1);
      leader.setAge(30 + DiceGenerator.getRandom(20));
      if (leader.getRace().getLifeSpan() < 80) {
        // Low life span starts about 10 years younger as starting ruler
        leader.setAge(25 + DiceGenerator.getRandom(10));
      }
    } else {
      leader.setLevel(level);
      leader.setAge(23 + DiceGenerator.getRandom(15));
    }

    for (int i = 0; i < leader.getLevel(); i++) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_GOOD);
      var newPerk = DiceGenerator.pickRandom(newPerks);
      leader.addPerk(newPerk);
      if (DiceGenerator.getRandom(99) < 10) {
        newPerks = getNewPerks(leader, PERK_TYPE_BAD);
        newPerk = DiceGenerator.pickRandom(newPerks);
        leader.addPerk(newPerk);
      }
    }
    return leader;
  }

  /**
   * Recruit leader from recruit leader pool. This will find best planet to
   * hire and cost for it. After this realm will have new leader ready to use.
   * This method will also return hired leader or null if hire is not possible.
   * @param map StarMap
   * @param info Realm who is doing the hire
   * @param leaderJob Which job hire the leader. Null for generic.
   * @return Leader or null
   */
  public static Leader recruiteLeader(final StarMap map,
      final PlayerInfo info, final Job leaderJob) {
    RecruitableLeader[] leaders = LeaderUtility.buildRecuitableLeaderPool(map,
        info);
    int leaderCost = LeaderUtility.leaderRecruitCost(info);
    RecruitableLeader bestLeader = null;
    int bestScore = -999;
    for (RecruitableLeader leader : leaders) {
      int score = -999;
      if (leaderJob == null) {
        score = leader.getLeader().calculateLeaderGenericScore();
      } else {
        score = leader.getLeader().calculateLeaderScore(leaderJob);
      }
      if (leader.usePopulation()) {
        Planet planet = map.getPlanetByName(leader.getLeader().getHomeworld());
        if (planet != null) {
          if (planet.getTotalPopulation() - 1 >= info.getRace()
              .getMinimumPopulationForLeader()) {
            score = score + 1;
          }
          if (planet.getTotalPopulation() - 2 >= info.getRace()
              .getMinimumPopulationForLeader()) {
            score = score + 1;
          }
        }
      } else {
        score = score + 1;
      }
      if (leader.getCost() <= leaderCost) {
        score = score + 1;
      }
      if (leader.getCost() > leaderCost * 2) {
        score = score - 1;
      }
      if (leader.getCost() > info.getTotalCredits()) {
        // No credits for hiring.
        score = -1;
      }
      if (score > bestScore) {
        bestScore = score;
        bestLeader = leader;
      }
    }
    if (bestLeader != null) {
      leaderCost = bestLeader.getCost();
      if (info.getTotalCredits() >= leaderCost) {
        Leader leader = bestLeader.getLeader();
        leader.assignJob(Job.UNASSIGNED, info);
        info.getLeaderPool().add(leader);
        int realmIndex = bestLeader.getRealmIndex();
        PlayerInfo realm = map.getPlayerByIndex(realmIndex);
        realm.removeRecruitLeader(leader);
        if (realm == info) {
          info.setTotalCredits(info.getTotalCredits() - leaderCost);
          Planet planet = map.getPlanetByName(leader.getHomeworld());
          if (planet != null) {
            planet.takeColonist();
          }
        } else {
          info.setTotalCredits(info.getTotalCredits() - leaderCost);
          realm.setTotalCredits(realm.getTotalCredits() + leaderCost);
          Message msg = new Message(MessageType.LEADER, info.getEmpireName()
              + " hire leader called " + leader.getCallName() + " from "
              + realm.getEmpireName() + " with " + leaderCost + " credits."
              + " This leader is from " + leader.getHomeworld() + ".",
              Icons.getIconByName(Icons.ICON_GOVERNOR));
          realm.getMsgList().addUpcomingMessage(msg);
        }
        return bestLeader.getLeader();
      }
    }
    return null;
  }

  /**
   * Build Leader pool for recruit
   * @param map StarMap
   * @param player PlayerInfo which realm to add leader pool.
   * @return Leader pool for recruit
   */
  public static Leader[] buildLeaderPool(final StarMap map,
      final PlayerInfo player) {
    ArrayList<Leader> leaders = new ArrayList<>();
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == player) {
        int level = 1;
        int xp = 0;
        for (Building building : planet.getBuildingList()) {
          if (building.getName().equals("Barracks")) {
            xp = 50;
          }
          if (building.getName().equals("Space academy")) {
            level++;
          }
        }
        Leader leader = LeaderUtility.createLeader(player, planet, level);
        leader.setExperience(xp);
        leader.assignJob(Job.UNASSIGNED, player);
        if (planet.getTotalPopulation() >= player.getRace()
            .getMinimumPopulationForLeader()) {
          leaders.add(leader);
        }
      }
    }
    ArrayList<Leader> currentLeaders = player.getLeaderRecruitPool();
    for (Leader leader : leaders) {
      boolean leaderFromPlanetAlready = false;
      for (Leader comparison : currentLeaders) {
        if (leader.getHomeworld().equals(comparison.getHomeworld())) {
          leaderFromPlanetAlready = true;
        }
      }
      if (!leaderFromPlanetAlready) {
        player.addRecruitLeader(leader);
      }
    }
    ArrayList<Leader> removeLeader = new ArrayList<>();
    for (Leader leader : player.getLeaderRecruitPool()) {
      Planet homePlanet = map.getPlanetByName(leader.getHomeworld());
      if (homePlanet != null
          && homePlanet.getTotalPopulation() < player.getRace()
              .getMinimumPopulationForLeader()) {
        removeLeader.add(leader);
      }
    }
    for (Leader leader : removeLeader) {
      player.removeRecruitLeader(leader);
    }
    return player.getLeaderRecruitPool().toArray(
        new Leader[player.getLeaderRecruitPool().size()]);
  }

  /**
   * Build Leader pool for recruit
   * @param map StarMap
   * @param player PlayerInfo which realm to add leader pool.
   * @return Leader pool for recruitable leaders
   */
  public static RecruitableLeader[] buildRecuitableLeaderPool(final StarMap map,
      final PlayerInfo player) {
    ArrayList<RecruitableLeader> leaders = new ArrayList<>();
    Leader[] leaderArray = buildLeaderPool(map, player);
    int leaderCost = LeaderUtility.leaderRecruitCost(player);
    int index = map.getPlayerList().getIndex(player);
    for (Leader leader : leaderArray) {
      RecruitableLeader recruit = new RecruitableLeader(leader, leaderCost,
          true, index);
      leaders.add(recruit);
    }
    if (!player.getGovernment().isXenophopic()) {
      for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
        PlayerInfo info = map.getPlayerByIndex(i);
        if (info != player) {
          if (player.getDiplomacy().isAlliance(i)
              || player.getDiplomacy().isDefensivePact(i)) {
            leaderArray = buildLeaderPool(map, info);
            for (Leader leader : leaderArray) {
              RecruitableLeader recruit = new RecruitableLeader(leader,
                  leaderCost * 2, false, i);
              leaders.add(recruit);
            }
          } else if (player.getDiplomacy().isTradeAlliance(i)) {
            leaderArray = buildLeaderPool(map, info);
            for (Leader leader : leaderArray) {
              RecruitableLeader recruit = new RecruitableLeader(leader,
                  leaderCost * 3, false, i);
              leaders.add(recruit);
            }
          }
        }
      }
    }
    return leaders.toArray(
        new RecruitableLeader[leaders.size()]);
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
            Mission mission = player.getMissions().getMissionForFleet(
                fleet.getName(), MissionType.ESPIONAGE_MISSION);
            if (mission != null) {
              // Espionage mission underwork, not changing
              return false;
            }
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
        if (!player.isHuman()) {
          Mission mission = player.getMissions().getMissionForFleet(
              activeFleet.getName());
          if (mission != null && mission.getType() == MissionType.SPY_MISSION) {
            mission.setType(MissionType.ESPIONAGE_MISSION);
            mission.setPhase(MissionPhase.TREKKING);
          }
        }
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
   * @return Array of added new perks.
   */
  public static Perk[] addRandomPerks(final Leader leader) {
    boolean jobBasedPerkAdded = false;
    ArrayList<Perk> addedPerks = new ArrayList<>();
    Perk[] goodPerks = getNewPerks(leader, PERK_TYPE_GOOD);
    if (DiceGenerator.getRandom(99) < 60 || goodPerks.length == 0) {
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
        var newPerk = DiceGenerator.pickRandom(newPerks);
        leader.addPerk(newPerk);
        addedPerks.add(newPerk);
        jobBasedPerkAdded = true;
      }
    }
    if (!jobBasedPerkAdded && goodPerks.length > 0) {
      var goodPerk = DiceGenerator.pickRandom(goodPerks);
      leader.addPerk(goodPerk);
      addedPerks.add(goodPerk);
    }
    if (DiceGenerator.getRandom(99) < 10) {
      Perk[] newPerks = getNewPerks(leader, PERK_TYPE_BAD);
      if (newPerks.length > 0) {
        var newPerk = DiceGenerator.pickRandom(newPerks);
        leader.addPerk(newPerk);
        addedPerks.add(newPerk);
      }
    }
    return addedPerks.toArray(new Perk[0]);
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
        if (value >= realm.getRace().getMinimumPopulationForLeader()) {
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
      sb.append(RulerUtility.getRulerTitle(leader.getGender(),
          realm.getGovernment()));
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
    if (leader.getJob() == Job.UNASSIGNED || leader.getJob() == Job.PRISON) {
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
      if (perkType == PERK_TYPE_MENTAL && !perk.isMentalPerk()) {
        continue;
      }
      if (perk.isGainedPerk()) {
        continue;
      }

      boolean alreadyHas = leader.hasPerk(perk);
      if (!perk.isPerkAllowedForRace(leader.getRace())) {
        alreadyHas = true;
      }

      if (!alreadyHas) {
        list.add(perk);
      }
    }
    return list.toArray(new Perk[list.size()]);
  }

  /**
   * Get random living leader from realm.
   * @param realm Realm whose leaders are being looked for.
   * @return Leader or null
   */
  public static Leader getRandomLivingLeader(final PlayerInfo realm) {
    ArrayList<Leader> tmpList = new ArrayList<>();
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() != Job.DEAD) {
        tmpList.add(leader);
      }
    }
    if (tmpList.size() > 0) {
      return DiceGenerator.pickRandom(tmpList);
    }
    return null;
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
      case PRISON: {
        return Icons.getIconByName(Icons.ICON_PRISON);
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

  /**
   * Get ruler attitude
   * @param leader Leader for getting the attitude
   * @return Attitude
   */
  public static Attitude getRulerAttitude(final Leader leader) {
    AttitudeScore aggressive = new AttitudeScore(Attitude.AGGRESSIVE);
    AttitudeScore backstabbing = new AttitudeScore(Attitude.BACKSTABBING);
    AttitudeScore diplomatic = new AttitudeScore(Attitude.DIPLOMATIC);
    AttitudeScore expansionist = new AttitudeScore(Attitude.EXPANSIONIST);
    AttitudeScore logical = new AttitudeScore(Attitude.LOGICAL);
    AttitudeScore merchantical = new AttitudeScore(Attitude.MERCHANTICAL);
    AttitudeScore militaristic = new AttitudeScore(Attitude.MILITARISTIC);
    AttitudeScore peaceful = new AttitudeScore(Attitude.PEACEFUL);
    AttitudeScore scientific = new AttitudeScore(Attitude.SCIENTIFIC);
    for (Perk perk : leader.getPerkList()) {
      if (perk == Perk.ACADEMIC || perk == Perk.SCIENTIST
          || perk == Perk.ARCHAEOLOGIST) {
        scientific.setValue(scientific.getValue() + 5);
      }
      if (perk == Perk.ARTISTIC) {
        peaceful.setValue(peaceful.getValue() + 1);
      }
      if (perk == Perk.CHARISMATIC) {
        peaceful.setValue(peaceful.getValue() + 1);
        diplomatic.setValue(peaceful.getValue() + 5);
      }
      if (perk == Perk.CHATTERBOX) {
        diplomatic.setValue(diplomatic.getValue() + 1);
      }
      if (perk == Perk.NEGOTIATOR) {
        diplomatic.setValue(diplomatic.getValue() + 1);
      }
      if (perk == Perk.COMBAT_MASTER) {
        aggressive.setValue(aggressive.getValue() + 3);
        militaristic.setValue(militaristic.getValue() + 1);
        peaceful.setValue(peaceful.getValue() - 1);
      }
      if (perk == Perk.COMBAT_TACTICIAN) {
        aggressive.setValue(aggressive.getValue() + 3);
        militaristic.setValue(militaristic.getValue() + 1);
        peaceful.setValue(peaceful.getValue() - 1);
      }
      if (perk == Perk.CORRUPTED) {
        backstabbing.setValue(backstabbing.getValue() + 1);
      }
      if (perk == Perk.CONVICT) {
        backstabbing.setValue(backstabbing.getValue() + 1);
      }
      if (perk == Perk.CRUEL) {
        backstabbing.setValue(backstabbing.getValue() + 5);
        aggressive.setValue(aggressive.getValue() + 1);
      }
      if (perk == Perk.COUNTER_AGENT || perk == Perk.DISCIPLINE) {
        militaristic.setValue(militaristic.getValue() + 1);
      }
      if (perk == Perk.EXPLORER) {
        expansionist.setValue(expansionist.getValue() + 5);
        scientific.setValue(scientific.getValue() + 1);
      }
      if (perk == Perk.FTL_ENGINEER || perk == Perk.SCANNER_EXPERT) {
        expansionist.setValue(expansionist.getValue() + 3);
        scientific.setValue(scientific.getValue() + 1);
      }
      if (perk == Perk.MASTER_ENGINEER) {
        expansionist.setValue(expansionist.getValue() + 1);
        scientific.setValue(scientific.getValue() + 2);
      }
      if (perk == Perk.GOOD_LEADER) {
        logical.setValue(logical.getValue() + 3);
        peaceful.setValue(peaceful.getValue() + 1);
        diplomatic.setValue(peaceful.getValue() + 1);
      }
      if (perk == Perk.INDUSTRIAL) {
        logical.setValue(logical.getValue() + 1);
        merchantical.setValue(merchantical.getValue() + 1);
      }
      if (perk == Perk.AGRICULTURAL || perk == Perk.MINER) {
        merchantical.setValue(merchantical.getValue() + 1);
      }
      if (perk == Perk.MERCHANT || perk == Perk.TRADER) {
        merchantical.setValue(merchantical.getValue() + 5);
      }
      if (perk == Perk.MILITARISTIC) {
        militaristic.setValue(militaristic.getValue() + 5);
        peaceful.setValue(peaceful.getValue() - 5);
      }
      if (perk == Perk.PACIFIST) {
        peaceful.setValue(peaceful.getValue() + 5);
        diplomatic.setValue(diplomatic.getValue() + 1);
        militaristic.setValue(militaristic.getValue() - 5);
        aggressive.setValue(aggressive.getValue() - 5);
      }
      if (perk == Perk.POWER_HUNGRY) {
        aggressive.setValue(aggressive.getValue() + 3);
        backstabbing.setValue(backstabbing.getValue() + 1);
      }
      if (perk == Perk.REPULSIVE) {
        backstabbing.setValue(backstabbing.getValue() + 5);
        diplomatic.setValue(diplomatic.getValue() - 5);
      }
      if (perk == Perk.ADDICTED) {
        backstabbing.setValue(backstabbing.getValue() + 3);
      }
      if (perk == Perk.WEAK_LEADER) {
        aggressive.setValue(aggressive.getValue() - 5);
        militaristic.setValue(militaristic.getValue() - 5);
        peaceful.setValue(peaceful.getValue() + 5);
      }
      if (perk == Perk.SECRET_AGENT || perk == Perk.SPY_MASTER) {
        logical.setValue(logical.getValue() + 3);
      }
      if (perk == Perk.SLOW_LEARNER || perk == Perk.STUPID) {
        scientific.setValue(scientific.getValue() - 1);
      }
      if (perk == Perk.WARLORD) {
        backstabbing.setValue(backstabbing.getValue() + 1);
        aggressive.setValue(aggressive.getValue() + 5);
      }
      if (perk == Perk.WEALTHY) {
        merchantical.setValue(merchantical.getValue() + 1);
        diplomatic.setValue(diplomatic.getValue() + 1);
      }
      if (perk == Perk.PEACEFUL) {
        peaceful.setValue(peaceful.getValue() + 5);
        aggressive.setValue(aggressive.getValue() - 5);
        militaristic.setValue(militaristic.getValue() - 5);
      }
      if (perk == Perk.LOGICAL) {
        logical.setValue(logical.getValue() + 5);
      }
      if (perk == Perk.AGGRESSIVE) {
        aggressive.setValue(aggressive.getValue() + 5);
      }
      if (perk == Perk.MAD) {
        backstabbing.setValue(backstabbing.getValue() + 5);
        peaceful.setValue(peaceful.getValue() - 5);
        diplomatic.setValue(diplomatic.getValue() - 5);
      }
      if (perk == Perk.SKILLFUL) {
        logical.setValue(logical.getValue() + 1);
      }
      if (perk == Perk.INCOMPETENT) {
        backstabbing.setValue(backstabbing.getValue() + 1);
        logical.setValue(logical.getValue() - 1);
      }
    }
    ArrayList<AttitudeScore> scores = new ArrayList<>();
    scores.add(scientific);
    scores.add(peaceful);
    scores.add(merchantical);
    scores.add(expansionist);
    scores.add(backstabbing);
    scores.add(aggressive);
    scores.add(militaristic);
    scores.add(logical);
    scores.add(diplomatic);
    Collections.sort(scores, Collections.reverseOrder());
    if (scores.get(0).getValue() > 0) {
      return scores.get(0).getAttitude();
    }
    return null;
  }

  /**
   * Get ruler attitude
   * @param leader Leader for getting the attitude
   * @param secondary Backup for secondary realm attitude
   * @return Attitude
   */
  public static Attitude getRulerAttitude(final Leader leader,
      final Attitude secondary) {
    Attitude attitude = getRulerAttitude(leader);
    if (attitude != null) {
      return attitude;
    }
    return secondary;
  }

  /**
   * Handle leader release from espionage missage.
   * Leader was caught but realm decided to release leader.
   * @param info Realm who was trying espionage
   * @param planet Planet where espionage was tried
   * @param fleet Fleet which leader is commanding
   * @param message Message for two realms.
   * @param starMap StarMap
   */
  public static void handleLeaderReleased(final PlayerInfo info,
      final Planet planet, final Fleet fleet, final String message,
      final StarMap starMap) {
    Message msg = new Message(MessageType.LEADER, message,
        Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
    msg.setCoordinate(planet.getCoordinate());
    msg.setMatchByString(fleet.getCommander().getName());
    info.getMsgList().addUpcomingMessage(msg);
    msg = msg.copy();
    msg.setMatchByString(planet.getName());
    planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
    starMap.getHistory().addEvent(NewsFactory.makeLeaderEvent(
        fleet.getCommander(), info, starMap, msg.getMessage()));
  }

  /**
   * Handle leader killed because of espionage mission.
   * Leader may escaped due wealthy perk.
   * @param info Realm who was trying espionage
   * @param planet Planet where espionage was tried
   * @param fleet Fleet which leader is commanding
   * @param escapedMsg Message visible if leader escapes
   * @param killedMsg Message visible if leader is killed
   * @param game Games for adding news about killed leader.
   */
  public static void handleLeaderKilled(final PlayerInfo info,
      final Planet planet, final Fleet fleet, final String escapedMsg,
      final String killedMsg, final Game game) {
    if (fleet.getCommander().hasPerk(Perk.WEALTHY)) {
      fleet.getCommander().useWealth();
      Message msg = new Message(MessageType.LEADER, escapedMsg,
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    } else {
      Message msg = new Message(MessageType.LEADER, killedMsg,
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      fleet.getCommander().setJob(Job.DEAD);
      int starYear = game.getStarMap().getStarYear();
      NewsData news = NewsFactory.makeLeaderDies(fleet.getCommander(),
          info, "execution by "
              + planet.getPlanetPlayerInfo().getEmpireName(),
          starYear);
      if (game.getStarMap().hasHumanMet(info)
          || game.getStarMap().hasHumanMet(planet.getPlanetPlayerInfo())) {
        game.getStarMap().getNewsCorpData().addNews(news);
      }
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      fleet.setCommander(null);
    }
  }

  /**
   * Handle leader prisoned because of espionage mission.
   * Leader may escaped due wealthy perk.
   * @param info Realm who was trying espionage
   * @param planet Planet where espionage was tried
   * @param fleet Fleet which leader is commanding
   * @param escapedMsg Message visible if leader escapes
   * @param prisonMsg Message visible if leader is prisoned
   * @param shortReason Short reason for prisoning
   * @param time sentence time in star years.
   * @param game Games for adding news about killed leader.
   */
  public static void handleLeaderPrison(final PlayerInfo info,
      final Planet planet, final Fleet fleet, final String escapedMsg,
      final String prisonMsg, final String shortReason, final int time,
      final Game game) {
    if (fleet.getCommander().hasPerk(Perk.WEALTHY)) {
      fleet.getCommander().useWealth();
      Message msg = new Message(MessageType.LEADER, escapedMsg,
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    } else {
      Message msg = new Message(MessageType.LEADER, prisonMsg,
          Icons.getIconByName(Icons.ICON_PRISON));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      NewsData news = NewsFactory.makeLeaderPrisoned(fleet.getCommander(),
          info, shortReason, prisonMsg, time);
      if (game.getStarMap().hasHumanMet(info)) {
        game.getStarMap().getNewsCorpData().addNews(news);
      }
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      fleet.getCommander().setJob(Job.PRISON);
      fleet.getCommander().setTimeInJob(time);
      fleet.getCommander().addPerk(Perk.CONVICT);
      fleet.getCommander().getStats().addOne(StatType.NUMBER_OF_JAIL_TIME);
      fleet.setCommander(null);
    }
  }

  /**
   * Is leader with power hungry perk ready to kill ruler?
   * @param government Government type
   * @return True if ready to kill
   */
  public static boolean isPowerHungryReadyForKill(
      final GovernmentType government) {
    if (government == GovernmentType.CLAN
        || government == GovernmentType.EMPIRE
        || government == GovernmentType.HEGEMONY
        || government == GovernmentType.HIERARCHY
        || government == GovernmentType.HIVEMIND
        || government == GovernmentType.HORDE
        || government == GovernmentType.KINGDOM
        || government == GovernmentType.NEST
        || government == GovernmentType.FEUDALISM
        || government == GovernmentType.REGIME) {
      return true;
    }
    return false;
  }

  /**
   * Is leader with power hungry perk ready to kill ruler?
   * @param government Government type
   * @return True if ready to kill
   */
  public static boolean isPowerHungryReadyForKill(
      final Government government) {
    if (government.hasTrait(GovTraitIds.RULER_ASSASINATION)) {
      return true;
    }
    return false;
  }

}
