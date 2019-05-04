package org.openRealmOfStars.starMap;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017  Tuomo Untinen
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
   */
  public static void addWarDeclatingReputation(final StarMap starMap,
      final PlayerInfo attacker) {
    addReputation(starMap, attacker, DiplomacyBonusType.WAR_DECLARTION);
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
   * @param info PlayerInfo
   * @param vote Voting
   * @return Voting support value
   */
  public  static int getVotingSupport(final PlayerInfo info, final Vote vote) {
    int result = 0;
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
   * Make trade with ships. This gives credits for players involving in trade.
   * @param diplomacy Diplomacy for player trading with planet.
   *                  This can be null. It just then means that ship is
   *                  returning to home world.
   * @param fleet Fleet involved in trade
   * @param planet Planet where trade happens
   * @param info Player who is doing the trade.
   * @param newsData NewsCorpData
   */
  public static void doTradeWithShips(final DiplomacyBonusList diplomacy,
      final Fleet fleet, final Planet planet, final PlayerInfo info,
      final NewsCorpData newsData) {
    if (diplomacy != null
        && (diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
        || diplomacy.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
        || diplomacy.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
      int credits = fleet.doTrade(planet, info);
      if (credits > 1) {
        credits = credits / 2;
      }
      credits = credits + info.getGovernment().getTradeBonus();
      if (credits > 0) {
        if (info.getRace() == SpaceRace.SCAURIANS) {
          credits = credits * 3 / 2;
        }
        info.setTotalCredits(info.getTotalCredits() + credits);
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
      }
    } else if (diplomacy == null) {
      int credits = fleet.doTrade(planet, info);
      credits = credits + info.getGovernment().getTradeBonus();
      if (credits > 0) {
        if (info.getRace() == SpaceRace.SCAURIANS) {
          credits = credits * 3 / 2;
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
}
