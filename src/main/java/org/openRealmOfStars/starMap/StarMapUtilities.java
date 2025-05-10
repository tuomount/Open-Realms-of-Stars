package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.WinningStrategy;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Star map utilities
 *
 */
public final class StarMapUtilities {

  /**
   * Hiding the constructor
   */
  private StarMapUtilities() {
    // Nothing to do
  }

  /**
   * Check if there can be fitted solar system
   * @param solarSystem where to check solar systems
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   * @param sunDistance distance between suns
   * @return How many solar system sectors found
   */
  public static int isSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int maxX, final int maxY, final int sunDistance) {
    int result = 0;
    for (int y = -sunDistance; y < sunDistance; y++) {
      for (int x = -sunDistance; x < sunDistance; x++) {
        if (x + sx >= 0 && y + sy >= 0 && x + sx < maxX && y + sy < maxY) {
          result = result + solarSystem[sx + x][sy + y];
        }
      }
    }
    return result;
  }

  /**
   * Get how full universe is solar systems
   * @param solarSystem Solar system map
   * @param maxX Maximum size of X
   * @param maxY Maximum size of Y
   * @return How many percent universe is full
   */
  public static int getSystemFullness(final int[][] solarSystem, final int maxX,
      final int maxY) {
    int result = 0;
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (solarSystem[x][y] == 1) {
          result++;
        }
      }
    }
    int total = (maxX - 2 * StarMap.SOLAR_SYSTEM_WIDTH)
        * (maxY - 2 * StarMap.SOLAR_SYSTEM_WIDTH);
    result = result * 100 / total;
    return result;
  }

  /**
   * Set solar system on solar system map
   * @param solarSystem Map where place solar system
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   * @return return update solarsystem map
   */
  public static int[][] setSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int maxX, final int maxY) {
    for (int y = -StarMap.SOLAR_SYSTEM_WIDTH;
         y < StarMap.SOLAR_SYSTEM_WIDTH; y++) {
      for (int x = -StarMap.SOLAR_SYSTEM_WIDTH;
           x < StarMap.SOLAR_SYSTEM_WIDTH; x++) {
        if (x + sx >= 0 && y + sy >= 0 && x + sx < maxX && y + sy < maxY) {
          solarSystem[sx + x][sy + y] = 1;
        }
      }
    }
    return solarSystem;
  }

  /**
   * Add player's reputation that he or she has dropped nuclear bombs
   * @param starMap StarMap containing all the diplomacies
   * @param nuker PlayerInfo who is nuking.
   */
  public static void addNukeReputation(final StarMap starMap,
      final PlayerInfo nuker) {
    addReputation(starMap, nuker, DiplomacyBonusType.NUKED);
  }

  /**
   * Add player's reputation that he or she has declared a war
   * @param starMap StarMap containing all the diplomacies
   * @param attacker PlayerInfo who is attacking.
   * @param defender PlayerInfo who is being attacked.
   */
  public static void addWarDeclatingReputation(final StarMap starMap,
      final PlayerInfo attacker, final PlayerInfo defender) {
    int defenderIndex = starMap.getPlayerList().getIndex(defender);
    if (!attacker.getDiplomacy().hasCasusBelli(defenderIndex)) {
      addReputation(starMap, attacker, DiplomacyBonusType.WAR_DECLARTION);
    }
    int index = starMap.getPlayerList().getIndex(attacker);
    if (index != -1) {
      DiplomacyBonusList list = defender.getDiplomacy().getDiplomacyList(
          index);
      list.addBonus(DiplomacyBonusType.WAR_DECLARATION_AGAINST_US,
          defender.getRace());
    }
  }

  /**
   * Add reputation of embargoImposer and embargoAgree depending of
   * how much other realm's like or dislake embargoImposed
   * @param starMap StarMap containig all the diplomacy information
   * @param embargoImposer Realm who was imposer
   * @param embargoAgree Realm who agreed on embargo
   * @param embargoImposed Realm who were imposed to embargo
   */
  public static void addEmbargoReputation(final StarMap starMap,
      final PlayerInfo embargoImposer, final PlayerInfo embargoAgree,
      final PlayerInfo embargoImposed) {
    int imposer = starMap.getPlayerList().getIndex(embargoImposer);
    int imposed = starMap.getPlayerList().getIndex(embargoImposed);
    int agree = starMap.getPlayerList().getIndex(embargoAgree);
    int maxPlayer = starMap.getPlayerList().getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
      PlayerInfo player = starMap.getPlayerList().getPlayerInfoByIndex(i);
      if (i == imposer) {
        DiplomacyBonusList list = player.getDiplomacy()
            .getDiplomacyList(imposed);
        list.addBonus(DiplomacyBonusType.EMBARGO, player.getRace());
      }
      if (i == agree) {
        DiplomacyBonusList list = player.getDiplomacy()
            .getDiplomacyList(imposed);
        list.addBonus(DiplomacyBonusType.EMBARGO, player.getRace());
      }
      if (i == imposed) {
        DiplomacyBonusList list = player.getDiplomacy()
            .getDiplomacyList(imposer);
        list.addBonus(DiplomacyBonusType.EMBARGO, player.getRace());
        list = player.getDiplomacy()
            .getDiplomacyList(agree);
        list.addBonus(DiplomacyBonusType.EMBARGO, player.getRace());
      }
      if (i != imposed && i != imposer && i != agree) {
        // Going through all realms, including the one which were not
        // part of the deal. If those realms liked imposed then
        // imposer/agree gets negative bonus. If they hated imposed then
        // imposer/agree gets positive bonus.
        int liking = player.getDiplomacy().getLiking(imposed);
        if (liking > 0) {
          DiplomacyBonusList list = player.getDiplomacy()
              .getDiplomacyList(imposer);
          list.addBonus(DiplomacyBonusType.DISLIKED_EMBARGO, player.getRace());
          list = player.getDiplomacy().getDiplomacyList(agree);
          list.addBonus(DiplomacyBonusType.DISLIKED_EMBARGO, player.getRace());
        }
        if (liking < 0) {
          DiplomacyBonusList list = player.getDiplomacy()
              .getDiplomacyList(imposer);
          list.addBonus(DiplomacyBonusType.LIKED_EMBARGO, player.getRace());
          list = player.getDiplomacy().getDiplomacyList(agree);
          list.addBonus(DiplomacyBonusType.LIKED_EMBARGO, player.getRace());
        }
      }
    }
  }
  /**
   * Add player's repuation that he or she has done something, usually bad.
   * @param starMap StarMap containing all the diplomacies
   * @param actor PlayerInfo who is acting.
   * @param bonusType Diplomacy Bonus which is being added to all players
   */
  private static void addReputation(final StarMap starMap,
      final PlayerInfo actor, final DiplomacyBonusType bonusType) {
    int index = starMap.getPlayerList().getIndex(actor);
    int maxPlayer = starMap.getPlayerList().getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
      PlayerInfo player = starMap.getPlayerByIndex(i);
      if (i != index) {
        DiplomacyBonusList list = player.getDiplomacy()
            .getDiplomacyList(index);
        list.addBonus(bonusType, player.getRace());
      }
    }
  }

  /**
   * Get voting support attitude.
   * @param attitude Attitude
   * @param vote VotingType
   * @return Voting support value
   */
  public static int getVotingSupportAccordingAttitude(final Attitude attitude,
      final VotingType vote) {
    if (vote == VotingType.RULER_OF_GALAXY) {
      return 0;
    }
    int[] voteValues = null;
    if (attitude == Attitude.AGGRESSIVE) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, -10, -10, -20, -5, 5};
    }
    if (attitude == Attitude.BACKSTABBING) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, -5, -5, -10, 5, 0};
    }
    if (attitude == Attitude.DIPLOMATIC) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, 10, 5, 10, 5, -5};
    }
    if (attitude == Attitude.EXPANSIONIST) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, 5, 5, 10, 5, -5};
    }
    if (attitude == Attitude.LOGICAL) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, 0, 0, 5, 5, 0};
    }
    if (attitude == Attitude.MERCHANTICAL) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, 0, 20, 10, -10, 0};
    }
    if (attitude == Attitude.MILITARISTIC) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, -10, -10, -5, 0, 10};
    }
    if (attitude == Attitude.PEACEFUL) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, 20, 10, 20, 5, -10};
    }
    if (attitude == Attitude.SCIENTIFIC) {
      /*
       * First is always zero
       * BAN_NUCLEAR_WEAPONS
       * BAN_PRIVATEER_SHIPS
       * GALACTIC_PEACE
       * TAXATION_OF_RICHEST_REALM
       * SECOND_CANDIDATE_MILITARY
       */
      voteValues = new int[]{0, -5, -5, 5, 0, 0};
    }
    if (voteValues != null && vote.getIndex() > 0
        && vote.getIndex() < voteValues.length) {
      return voteValues[vote.getIndex()];
    }
    return 0;
  }
  /**
   * Get Voting support value. Negative value is for
   * NO and positive value is for yes.
   * Abstain value for zero.
   * @param info PlayerInfo
   * @param vote Voting
   * @param map StarMap
   * @return Voting support value Positive value for Yes and Negative for no.
   */
  public  static int getVotingSupport(final PlayerInfo info, final Vote vote,
      final StarMap map) {
    int result = 0;
    result = getVotingSupportAccordingAttitude(info.getAttitude(),
        vote.getType());
    result = result + getVotingSupportAccordingAttitude(
        info.getRace().getAttitude(), vote.getType());
    // Species with radiosynthesis don't benefit that much from banning nukes
    if (vote.getType() == VotingType.BAN_NUCLEAR_WEAPONS
        && info.getRace().hasTrait(TraitIds.RADIOSYNTHESIS)) {
      result -= 20;
    }
    if (vote.getType() == VotingType.BAN_PRIVATEER_SHIPS) {
      // Mercantile races are likely to benefit from less privateers
      if (info.getRace().hasTrait(TraitIds.MERCANTILE)) {
        result += 10;
      }
      int privateerFleets = 0;
      for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
        Fleet fleet = info.getFleets().getByIndex(i);
        if (fleet.isPrivateerFleet()) {
          privateerFleets++;
        }
      }
      if (privateerFleets == 0) {
        // Haven't got fleets yet, so banning is beneficial
        result = result + 10;
      } else if (privateerFleets < 5) {
        // Got some but not much, more would be nice
        result = result - 5;
      } else if (privateerFleets > 10) {
        // Got enough so those can be now banned
        result = result + 5;
      }
    }
    if (vote.getType() == VotingType.GALACTIC_PEACE) {
      if (info.getDiplomacy().getNumberOfWar() > 0
          && !info.getGovernment().isImmuneToHappiness()) {
        result = result + 5;
      }
      if (info.getTotalWarFatigue() < 0) {
        result = result + 5;
      }
    }
    if (vote.getType() == VotingType.TAXATION_OF_RICHEST_REALM) {
      int index = map.getWealthyIndex(info);
      int max = map.getPlayerList().getCurrentMaxRealms();
      if (index == 1) {
        result = result - 30;
      } else if (index == max) {
        result = result + 20;
      } else if (index <= max / 2) {
        int value = max / 2 - (index - 1);
        int half = max / 2;
        result = result - (30 * value / half);
      } else {
        result = result + (20 * index / max);
      }
      int richest = map.getWealthyIndex(true);
      if (info.getDiplomacy().isAlliance(richest)) {
        result = result - 20;
      }
      if (info.getDiplomacy().isDefensivePact(richest)) {
        result = result - 15;
      }
      if (info.getDiplomacy().isTradeAlliance(richest)) {
        result = result - 10;
      }
      if (info.getDiplomacy().isWar(richest)) {
        result = result + 20;
      }
      if (info.getDiplomacy().isTradeEmbargo(richest)) {
        result = result + 15;
      }
      if (info.getDiplomacy().getLiking(richest) == Diplomacy.LIKE) {
        result = result - 5;
      }
      if (info.getDiplomacy().getLiking(richest) == Diplomacy.FRIENDS) {
        result = result - 10;
      }
      if (info.getDiplomacy().getLiking(richest) == Diplomacy.DISLIKE) {
        result = result + 5;
      }
      if (info.getDiplomacy().getLiking(richest) == Diplomacy.HATE) {
        result = result + 10;
      }
      int poorest = map.getWealthyIndex(false);
      if (info.getDiplomacy().isAlliance(poorest)) {
        result = result + 20;
      }
      if (info.getDiplomacy().isDefensivePact(poorest)) {
        result = result + 15;
      }
      if (info.getDiplomacy().isTradeAlliance(poorest)) {
        result = result + 10;
      }
      if (info.getDiplomacy().isWar(poorest)) {
        result = result - 20;
      }
      if (info.getDiplomacy().isTradeEmbargo(poorest)) {
        result = result - 15;
      }
      if (info.getDiplomacy().getLiking(poorest) == Diplomacy.LIKE) {
        result = result + 5;
      }
      if (info.getDiplomacy().getLiking(poorest) == Diplomacy.FRIENDS) {
        result = result + 10;
      }
      if (info.getDiplomacy().getLiking(poorest) == Diplomacy.DISLIKE) {
        result = result - 5;
      }
      if (info.getDiplomacy().getLiking(poorest) == Diplomacy.HATE) {
        result = result - 10;
      }
    }
    if (vote.getType() == VotingType.SECOND_CANDIDATE_MILITARY) {
      int mostMilitary = map.getMilitaryHighest();
      int myIndex = map.getPlayerList().getIndex(info);
      if (mostMilitary != -1) {
        if (mostMilitary == myIndex) {
          result = result + 40;
        } else {
          if (info.getDiplomacy().isAlliance(mostMilitary)) {
            result = result + 30;
          }
          if (info.getDiplomacy().isDefensivePact(mostMilitary)) {
            result = result + 20;
          }
          if (info.getDiplomacy().isTradeAlliance(mostMilitary)) {
            result = result + 10;
          }
          if (info.getDiplomacy().isWar(mostMilitary)) {
            result = result - 20;
          }
          if (info.getDiplomacy().isTradeEmbargo(mostMilitary)) {
            result = result - 10;
          }
          if (info.getDiplomacy().getLiking(mostMilitary) == Diplomacy.LIKE) {
            result = result + 5;
          }
          if (info.getDiplomacy().getLiking(mostMilitary)
              == Diplomacy.FRIENDS) {
            result = result + 10;
          }
          if (info.getDiplomacy().getLiking(mostMilitary)
              == Diplomacy.DISLIKE) {
            result = result - 5;
          }
          if (info.getDiplomacy().getLiking(mostMilitary) == Diplomacy.HATE) {
            result = result - 10;
          }
        }
      }
      int mostTower = map.getSecondCandidateForTower();
      if (mostTower != -1) {
        if (mostTower == myIndex) {
          result = result - 40;
        } else {
          if (info.getDiplomacy().isAlliance(mostTower)) {
            result = result - 30;
          }
          if (info.getDiplomacy().isDefensivePact(mostTower)) {
            result = result - 20;
          }
          if (info.getDiplomacy().isTradeAlliance(mostTower)) {
            result = result - 10;
          }
          if (info.getDiplomacy().isWar(mostTower)) {
            result = result + 20;
          }
          if (info.getDiplomacy().isTradeEmbargo(mostTower)) {
            result = result + 10;
          }
          if (info.getDiplomacy().getLiking(mostTower) == Diplomacy.LIKE) {
            result = result - 5;
          }
          if (info.getDiplomacy().getLiking(mostTower)
              == Diplomacy.FRIENDS) {
            result = result - 10;
          }
          if (info.getDiplomacy().getLiking(mostTower)
              == Diplomacy.DISLIKE) {
            result = result + 5;
          }
          if (info.getDiplomacy().getLiking(mostTower) == Diplomacy.HATE) {
            result = result + 10;
          }
        }
      }
    }
    if (vote.getType() == VotingType.RULER_OF_GALAXY) {
      int first = vote.getOrganizerIndex();
      int second = vote.getSecondCandidateIndex();
      int myIndex = map.getPlayerList().getIndex(info);
      if (first == myIndex) {
        result = result + 80;
      } else if (second == myIndex) {
        result = result - 80;
      }
      if (info.getDiplomacy().isAlliance(first)) {
        result = result + 40;
      }
      if (info.getDiplomacy().isAlliance(second)) {
        result = result - 40;
      }
      if (info.getDiplomacy().isDefensivePact(first)) {
        result = result + 20;
      }
      if (info.getDiplomacy().isDefensivePact(second)) {
        result = result - 20;
      }
      if (info.getDiplomacy().isTradeAlliance(first)) {
        result = result + 10;
      }
      if (info.getDiplomacy().isTradeAlliance(second)) {
        result = result - 10;
      }
      if (info.getDiplomacy().isTradeEmbargo(first)) {
        result = result - 10;
      }
      if (info.getDiplomacy().isTradeEmbargo(second)) {
        result = result + 10;
      }
      if (info.getDiplomacy().isWar(first)) {
        result = result - 30;
      }
      if (info.getDiplomacy().isWar(second)) {
        result = result + 30;
      }
      if (info.getDiplomacy().getLiking(first) == Diplomacy.FRIENDS) {
        result = result + 10;
      }
      if (info.getDiplomacy().getLiking(first) == Diplomacy.LIKE) {
        result = result + 5;
      }
      if (info.getDiplomacy().getLiking(first) == Diplomacy.DISLIKE) {
        result = result - 5;
      }
      if (info.getDiplomacy().getLiking(first) == Diplomacy.HATE) {
        result = result - 10;
      }
      if (info.getDiplomacy().getLiking(second) == Diplomacy.FRIENDS) {
        result = result - 10;
      }
      if (info.getDiplomacy().getLiking(second) == Diplomacy.LIKE) {
        result = result - 5;
      }
      if (info.getDiplomacy().getLiking(second) == Diplomacy.DISLIKE) {
        result = result + 5;
      }
      if (info.getDiplomacy().getLiking(second) == Diplomacy.HATE) {
        result = result + 10;
      }
      if (Math.abs(result) < 10) {
        boolean notKnowEnoughFirst = false;
        boolean notKnowEnoughSecond = false;
        DiplomacyBonusList bonusList = info.getDiplomacy().getDiplomacyList(
            first);
        if (bonusList != null && bonusList.getNumberOfMeetings() < 4) {
          notKnowEnoughFirst = true;
        }
        bonusList = info.getDiplomacy().getDiplomacyList(second);
        if (bonusList != null && bonusList.getNumberOfMeetings() < 4) {
          notKnowEnoughSecond = true;
        }
        if (notKnowEnoughFirst && notKnowEnoughSecond) {
          result = 0;
        }
      }
    }


    return result;
  }
  /**
   * Calculates escape coordinates for combat if defender escapes.
   * @param defender Defender's coordinate
   * @param attacker Attacker's coordinate
   * @return Coordinate or null if attacking is too far away
   */
  public static Coordinate getEscapeCoordinates(final Coordinate defender,
      final Coordinate attacker) {
    int mx = defender.getX() - attacker.getX();
    int my = defender.getY() - attacker.getY();
    if (mx > 1) {
      mx = 1;
    }
    if (my > 1) {
      my = 1;
    }
    if (mx < -1) {
      mx = -1;
    }
    if (my < -1) {
      my = -1;
    }
    return new Coordinate(defender.getX() + mx, defender.getY() + my);
  }

  /**
   * Spread deadly virus to planet.
   * @param info Realm which is spreading the virus
   * @param planet Planet where virus spreads.
   */
  public static void spreadDeadlyVirus(final PlayerInfo info,
      final Planet planet) {
    if (planet.getPlanetPlayerInfo() != null
        && planet.getPlanetPlayerInfo() != info
        && !planet.getPlanetPlayerInfo().getTechList().hasTech(
        TechType.Improvements, "Deadly virus")) {
      StringBuilder sb = new StringBuilder();
      sb.append("Deadly virus outbreaks at ");
      sb.append(planet.getName());
      String relation = info.getDiplomacy().getDiplomaticRelation(
          planet.getPlanetOwnerIndex());
      if (relation.equals(Diplomacy.WAR)) {
        sb.append(" during war operations between ");
      } else if (relation.equals(Diplomacy.TRADE_ALLIANCE)) {
        sb.append(" via trading between ");
      } else if (relation.equals(Diplomacy.DEFENSIVE_PACT)) {
        sb.append(" via trading and military training between ");
      } else if (relation.equals(Diplomacy.ALLIANCE)) {
        sb.append(" via close trading between ");
      } else if (relation.equals(Diplomacy.PEACE)) {
        sb.append(" via peacuful conversations between ");
      } else if (relation.equals(Diplomacy.TRADE_EMBARGO)) {
        sb.append(" via secret negotiations between ");
      } else {
        sb.append(". Rumors suggest that disease spread from ");
      }
      sb.append(info.getEmpireName());
      sb.append(". ");
      if (planet.getPlanetPlayerInfo().getRace().isRoboticRace()) {
        sb.append("Luckly planet is occupied by a robotic race that is"
            + " immune to deadly viruses. This does not affect to"
            + "planet in anyway.");
      } else {
        sb.append("Planet is immediately placed on guarantee to stop "
            + "the virus spreading. Bad news is that only one population "
            + "is immune to virus. Most of the population is dead. ");
        int pop = planet.getTotalPopulation();
        pop = pop - 1;
        for (int i = 0; i < pop; i++) {
          // Null as starmap since governor should not die for virus.
          planet.killOneWorker("deadly virus", "deadly virus", null);
        }
        planet.getPlanetPlayerInfo().getTechList().addTech(
            TechFactory.createImprovementTech("Deadly virus", 4));
        sb.append("Genetic code of virus is saved and stored carefully.");
      }
      Message message = new Message(MessageType.PLANETARY, sb.toString(),
          Icons.getIconByName(Icons.ICON_DEATH));
      message.setCoordinate(planet.getCoordinate());
      info.getMsgList().addUpcomingMessage(message);
      message = new Message(MessageType.PLANETARY, sb.toString(),
          Icons.getIconByName(Icons.ICON_DEATH));
      message.setCoordinate(planet.getCoordinate());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(message);
    }
  }
  /**
   * Make trade with ships. This gives credits for players involving in trade.
   * @param diplomacy Diplomacy for player trading with planet.
   *                  This can be null. It just then means that ship is
   *                  returning to home world.
   * @param fleet Fleet involved in trade
   * @param planet Planet where trade happens
   * @param info Player who is doing the trade.
   * @param starYear when this happens.
   * @param newsData NewsCorpData
   */
  public static void doTradeWithShips(final DiplomacyBonusList diplomacy,
      final Fleet fleet, final Planet planet, final PlayerInfo info,
      final NewsCorpData newsData, final int starYear) {
    if (diplomacy != null
        && (diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
        || diplomacy.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
        || diplomacy.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
      int credits = fleet.doTrade(planet, info);
      if (credits == 0) {
        return;
      }
      if (credits > 1) {
        credits = credits / 2;
      }
      credits = credits + info.getGovernment().getTradeBonus();
      if (fleet.getCommander() != null
          && fleet.getCommander().hasPerk(Perk.TRADER)) {
        credits++;
      }
      if (credits > 0) {
        if (info.getRace().hasTrait(TraitIds.MERCANTILE)) {
          credits = credits * 3 / 2;
        }
        info.setTotalCredits(info.getTotalCredits() + credits);
        if (fleet.getCommander() != null) {
          fleet.getCommander().addExperience(credits);
          fleet.getCommander().getStats().addOne(StatType.NUMBER_OF_TRADES);
        }
        planet.getPlanetPlayerInfo().setTotalCredits(
            planet.getPlanetPlayerInfo().getTotalCredits()
            + credits);
        Message msg = new Message(MessageType.PLANETARY,
            fleet.getName() + " made trade with " + planet.getName()
            + ". Each party gained " + credits + " credits.",
            Icons.getIconByName(Icons.ICON_CREDIT));
        msg.setCoordinate(planet.getCoordinate());
        info.getMsgList().addUpcomingMessage(msg);
        planet.getPlanetPlayerInfo().getMsgList()
            .addUpcomingMessage(msg);
        if (!diplomacy.isBonusType(DiplomacyBonusType.TRADE_FLEET)
            && newsData != null) {
          diplomacy.addBonus(DiplomacyBonusType.TRADE_FLEET, info.getRace());
          newsData.addNews(NewsFactory.makeTradeNews(info, planet));
        }
        if (info.getTechList().hasTech(TechType.Improvements, "Deadly virus")
            && !planet.getPlanetPlayerInfo().getTechList().hasTech(
                TechType.Improvements, "Deadly virus")) {
          int chance = DiceGenerator.getRandom(99);
          if (chance < 10) {
            spreadDeadlyVirus(info, planet);
            newsData.addNews(NewsFactory.makeDeadlyVirusNews(planet, info,
                starYear));
          }
        }
      }
    } else if (diplomacy == null) {
      int credits = fleet.doTrade(planet, info);
      credits = credits + info.getGovernment().getTradeBonus();
      if (fleet.getCommander() != null
          && fleet.getCommander().hasPerk(Perk.TRADER)) {
        credits++;
      }
      if (credits > 0) {
        if (info.getRace().hasTrait(TraitIds.MERCANTILE)) {
          credits = credits * 3 / 2;
        }
        if (fleet.getCommander() != null) {
          fleet.getCommander().addExperience(credits);
          fleet.getCommander().getStats().addOne(StatType.NUMBER_OF_TRADES);
        }
        info.setTotalCredits(info.getTotalCredits() + credits);
        Message msg = new Message(MessageType.PLANETARY,
            fleet.getName() + " came back to homeworld "
            + planet.getName() + " with " + credits + " credits.",
            Icons.getIconByName(Icons.ICON_CREDIT));
        msg.setCoordinate(planet.getCoordinate());
        info.getMsgList().addUpcomingMessage(msg);
      }
    }
  }

  /**
   * Calculate culture score limit
   * @param sizeX Map size in X coordinate
   * @param sizeY Map size in Y coordinate
   * @param lastTurn Which is the last turn when scoring is done
   * @param multiplier Multiplier -1, 0, 1, 2, 3
   * @return Culture score limit
   */
  public static int calculateCultureScoreLimit(final int sizeX,
      final int sizeY, final int lastTurn, final int multiplier) {
    int totalSize = sizeX * sizeY;
    int turnMultiplier = lastTurn / 100;
    int turnModifier = 0;
    if (totalSize <= 50 * 50) {
      turnModifier = 0;
    } else if (totalSize <= 75 * 75) {
      turnModifier = 50;
    } else if (totalSize <= 128 * 128) {
      turnModifier = 100;
    } else if (totalSize <= 160 * 160) {
      turnModifier = 150;
    } else if (totalSize <= 200 * 200) {
      turnModifier = 200;
    } else if (totalSize <= 256 * 256) {
      turnModifier = 250;
    }
    int result = (lastTurn + turnModifier) * turnMultiplier + lastTurn;
    // 75%
    if (multiplier == 0) {
      result = result * 3 / 4;
    }
    // 150%
    if (multiplier == 2) {
      result = result * 3 / 2;
    }
    // 200%
    if (multiplier == 3) {
      result = result * 2;
    }
    if (result < 600) {
      result = 600;
    }
    if (multiplier == -1) {
      result = -1;
    }
    return result;
  }

  /**
   * Get number of admires realm has.
   * Admire is when another realm likes your realm
   * and you are not in war or trade embargo.
   * @param admireTarget Which realm is going to check how many admires it has
   * @param players Realms in list
   * @return Number of admires.
   */

  public static int getNumberOfAdmires(final int admireTarget,
      final PlayerList players) {
    int admires = 0;
    for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
      if (i == admireTarget) {
        continue;
      }
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info == null) {
        continue;
      }
      Diplomacy diplomacy = info.getDiplomacy();
      if (diplomacy == null) {
        continue;
      }
      if (diplomacy.getLiking(admireTarget) >= 0
          && !diplomacy.isWar(admireTarget)
          && !diplomacy.isTradeEmbargo(admireTarget)
          && diplomacy.isTradeAlliance(admireTarget)) {
        admires++;
      }
    }
    return admires;
  }
  /**
   * Calculate required tower limit for diplomatic victory start.
   * @param sizeX Map size in X coordinate
   * @param sizeY Map size in Y coordinate
   * @return Tower Limit
   */
  public static int calculateRequireTowerLimit(final int sizeX,
      final int sizeY) {
    int totalSize = sizeX * sizeY;
    int limit = 0;
    if (totalSize <= 50 * 50) {
      limit = 1;
    } else if (totalSize <= 75 * 75) {
      limit = 2;
    } else if (totalSize <= 128 * 128) {
      limit = 3;
    } else if (totalSize <= 160 * 160) {
      limit = 4;
    } else if (totalSize <= 200 * 200) {
      limit = 5;
    } else if (totalSize <= 256 * 256) {
      limit = 6;
    }
    return limit;
  }

  /**
   * Upgrade single ship. This will check if upgrade is possible due
   * same hull type and that planet has enough resources to do upgrade.
   * @param ship Ship to upgrade
   * @param stat Which ship stat to upgrade
   * @param info Realm which is doing the upgrade
   * @param planet Planet where upgrade is going to be done.
   * @return True if upgrade was done.
   */
  public static boolean upgradeShip(final Ship ship, final ShipStat stat,
      final PlayerInfo info, final Planet planet) {
    boolean result = false;
    if (ship != null && stat != null
        && ship.getHull().getName().equals(
            stat.getDesign().getHull().getName())
        && ship.getColonist() == 0 && ship.getMetal() == 0) {
      int cost = ship.getUpgradeCost(stat.getDesign());
      int metalCost = ship.getUpgradeMetalCost(stat.getDesign());
      if (metalCost > planet.getMetal()
          + planet.getAmountMetalInGround()) {
        metalCost = 0;
        cost = 0;
      }
      if (metalCost > planet.getMetal()) {
        int left = metalCost - planet.getMetal();
        metalCost = planet.getMetal();
        cost = cost + left * 2;
      }
      if (cost > 0 && cost <= info.getTotalCredits()) {
        planet.setMetal(planet.getMetal() - metalCost);
        info.setTotalCredits(info.getTotalCredits() - cost);
        ShipStat oldStat = info.getShipStatByName(ship.getName());
        if (oldStat != null) {
          oldStat.setNumberOfInUse(oldStat.getNumberOfInUse() - 1);
          stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
          stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
          ship.setName(stat.getDesign().getName());
          ship.upgradeComponents(stat.getDesign().getComponentList());
          ship.fixShip(true);
          result = true;
        }
      }
    }
    return result;
  }
  /**
   * Search string from a list. Returns true if list contains searched
   * string.
   * @param list List where to search.
   * @param search String to search
   * @return True if found
   */
  public static boolean listContains(final String[] list,
      final String search) {
    for (String str : list) {
      if (str.equals(search)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Best free available planet for colonization.
   * @param map StarMap
   * @param info Realm which is doing the search
   * @return Best free planets. Index 0 contains the best.
   */
  public static Planet[] getBestFreePlanets(final StarMap map,
      final PlayerInfo info) {
    ArrayList<Planet> tempList = new ArrayList<>();
    ArrayList<Planet> bestList = new ArrayList<>();
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == null
          && info.getSectorVisibility(planet.getCoordinate())
          > PlayerInfo.UNCHARTED && !planet.isGasGiant()) {
        tempList.add(planet);
      }
    }
    int index = map.getPlayerList().getIndex(info);
    Coordinate coord = map.calculateCenterOfRealm(index);
    info.setCenterRealm(coord);
    while (tempList.size() > 0) {
      Planet bestPlanet = null;
      int bestValue = -999;
      int divider = 1;
      if (map.getMaxX() < 75) {
        divider = 1;
      } else if (map.getMaxX() < 130) {
        divider = 2;
      } else if (map.getMaxX() < 180) {
        divider = 3;
      } else {
        divider = 4;
      }
      for (Planet planet : tempList) {
        int value = planet.evaluatePlanetValue(info, coord, divider);
        if (planet.isHomeWorld()) {
          value = value + 10;
        }
        if (value > bestValue) {
          bestPlanet = planet;
          bestValue = value;
        }
      }
      tempList.remove(bestPlanet);
      bestList.add(bestPlanet);
    }
    return bestList.toArray(new Planet[bestList.size()]);
  }
  /**
   * Get Best conquerable planets. This is done based on planet's value.
   * @param map StarMap
   * @param info Realm which is doing the search
   * @return Best conquerable planets. Index 0 contains the best.
   */
  public static Planet[] getBestConquerablePlanets(final StarMap map,
      final PlayerInfo info) {
    ArrayList<Planet> tempList = new ArrayList<>();
    ArrayList<Planet> bestList = new ArrayList<>();
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetPlayerInfo() != info
          && info.getSectorVisibility(planet.getCoordinate())
          > PlayerInfo.UNCHARTED && !planet.isGasGiant()) {
        tempList.add(planet);
      }
    }
    int index = map.getPlayerList().getIndex(info);
    Coordinate coord = map.calculateCenterOfRealm(index);
    info.setCenterRealm(coord);
    while (tempList.size() > 0) {
      Planet bestPlanet = null;
      int bestValue = -999;
      int divider = 1;
      if (map.getMaxX() < 75) {
        divider = 1;
      } else if (map.getMaxX() < 130) {
        divider = 2;
      } else if (map.getMaxX() < 180) {
        divider = 3;
      } else {
        divider = 4;
      }
      for (Planet planet : tempList) {
        int value = planet.evaluatePlanetValue(info, coord, divider);
        if (value > bestValue) {
          bestPlanet = planet;
          bestValue = value;
        }
      }
      tempList.remove(bestPlanet);
      bestList.add(bestPlanet);
    }
    return bestList.toArray(new Planet[bestList.size()]);
  }

  /**
   * Makes governor guide adjustments for planet.
   * @param map StarMap
   * @param realm Realm making adjustments
   */
  public static void makeGovernorGuideAdjustments(final StarMap map,
      final PlayerInfo realm) {
    int planets = 0;
    int[] target = new int[6];
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == realm
          && planet.getGovernor() != null) {
        planets++;
      }
    }
    if (realm.getStrategy() == WinningStrategy.GENERIC) {
      target[Planet.RESEARCH_PLANET] = 1;
      target[Planet.CULTURE_PLANET] = 0;
      target[Planet.POPULATION_PLANET] = 0;
      target[Planet.CREDIT_PLANET] = 0;
      target[Planet.MILITARY_PLANET] = 0;
      target[Planet.GENERALIST_PLANET] = 0;
    }
    if (realm.getStrategy() == WinningStrategy.CONQUER) {
      int leftOver = planets % 2;
      target[Planet.RESEARCH_PLANET] = planets / 2;
      target[Planet.CULTURE_PLANET] = 0;
      target[Planet.POPULATION_PLANET] = 0;
      target[Planet.CREDIT_PLANET] = 0;
      target[Planet.MILITARY_PLANET] = planets / 2 + leftOver;
      target[Planet.GENERALIST_PLANET] = 0;
    }
    if (realm.getStrategy() == WinningStrategy.CULTURAL) {
      int leftOver = planets % 2;
      target[Planet.RESEARCH_PLANET] = planets / 2;
      target[Planet.CULTURE_PLANET] = planets / 2 + leftOver;
      target[Planet.POPULATION_PLANET] = 0;
      target[Planet.CREDIT_PLANET] = 0;
      target[Planet.MILITARY_PLANET] = 0;
      target[Planet.GENERALIST_PLANET] = 0;
    }
    if (realm.getStrategy() == WinningStrategy.DIPLOMATIC) {
      int leftOver = planets % 4;
      target[Planet.RESEARCH_PLANET] = planets / 4;
      target[Planet.CULTURE_PLANET] = 0;
      target[Planet.POPULATION_PLANET] = planets / 4;
      target[Planet.CREDIT_PLANET] = planets / 4;
      target[Planet.MILITARY_PLANET] = planets / 4;
      target[Planet.GENERALIST_PLANET] = 0;
      if (leftOver > 0) {
        target[Planet.POPULATION_PLANET]++;
        leftOver--;
      }
      if (leftOver > 0) {
        target[Planet.RESEARCH_PLANET]++;
        leftOver--;
      }
      if (leftOver > 0) {
        target[Planet.CREDIT_PLANET]++;
        leftOver--;
      }
    }
    if (realm.getStrategy() == WinningStrategy.POPULATION) {
      int leftOver = planets % 2;
      target[Planet.RESEARCH_PLANET] = planets / 2;
      target[Planet.CULTURE_PLANET] = 0;
      target[Planet.POPULATION_PLANET] = planets / 2 + leftOver;
      target[Planet.CREDIT_PLANET] = 0;
      target[Planet.MILITARY_PLANET] = 0;
      target[Planet.GENERALIST_PLANET] = 0;
    }
    if (realm.getStrategy() == WinningStrategy.SCIENCE) {
      target[Planet.RESEARCH_PLANET] = planets;
      target[Planet.CULTURE_PLANET] = 0;
      target[Planet.POPULATION_PLANET] = 0;
      target[Planet.CREDIT_PLANET] = 0;
      target[Planet.MILITARY_PLANET] = 0;
      target[Planet.GENERALIST_PLANET] = 0;
    }
    for (Planet planet : map.getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == realm
          && planet.getGovernor() != null) {
        int guide = planet.getGuideSuggestion();
        if (target[guide] > 0) {
          planet.setGovernorGuide(guide);
          target[guide]--;
        } else {
          if (guide == Planet.GENERALIST_PLANET) {
            boolean guideSet = false;
            for (int i = 1; i < target.length; i++) {
              if (target[i] > 0) {
                planet.setGovernorGuide(i);
                target[i]--;
                guideSet = true;
                break;
              }
            }
            if (!guideSet) {
              planet.setGovernorGuide(guide);
            }
          }
        }
      }
    }

  }

  /**
   * Calculate direction based on coordinate system.
   * Return direction in degree.
   *
   * @param sx Start X coordinate
   * @param sy Start Y Coordinate
   * @param ex End X coordinate
   * @param ey End Y coordinate
   * @return Direction in degree
   */
  public static int getDirection(final int sx, final int sy, final int ex,
      final int ey) {
    final double radToDeg = 57.29578;
    double mx = Math.abs(ex - sx);
    double my = Math.abs(ey - sy);
    /**
     * Quadrant
     *         |
     *      1  |  0
     * --------|-------
     *      2  |  3
     *         |
     */
    if (ex == sx) {
      if (sy > ey) {
        return 90;
      }
      return 270;
    }
    if (ey == sy) {
      if (sx > ex) {
        return 180;
      }
      return 0;
    }
    int quadrant;
    if (sx < ex) {
      if (sy < ey) {
        quadrant = 3;
      } else {
        quadrant = 0;
      }
    } else {
      if (sy < ey) {
        quadrant = 2;
      } else {
        quadrant = 1;
      }
    }
    double rad = Math.atan(my / mx);
    double degree = rad * radToDeg;
    if (quadrant == 1) {
      degree = degree + 90;
    }
    if (quadrant == 2) {
      degree = degree + 180;
    }
    if (quadrant == 3) {
      degree = degree + 270;
    }
    return (int) degree;
  }
}
