package org.openRealmOfStars.AI.Mission;

import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

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
 * Mission handling for AI
 *
 */
public final class MissionHandling {

  /**
   * Just hiding MissionHandling constructor
   */
  private MissionHandling() {
    // Hiding mission handling constructor
  }

  /**
   * Handle exploring mission
   * @param mission Exploring mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleExploring(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.EXPLORE) {
      String ignoreSun = null;
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        // Fleet has encounter obstacle, taking a detour round it
        Sun sun = game.getStarMap().locateSolarSystem(fleet.getX(),
            fleet.getY());
        if (sun != null && sun.getName().equals(mission.getSunName())) {
          // Fleet is in correct solar system, starting explore execution mode
          mission.setPhase(MissionPhase.EXECUTING);
          fleet.setaStarSearch(null);
        } else {
          makeReroute(game, fleet, info, mission);
        }
      }
      if (mission.getPhase() == MissionPhase.EXECUTING) {
        mission.setMissionTime(mission.getMissionTime() + 1);
        boolean missionComplete = false;
        if (mission.getMissionTime() >= info.getRace().getAIExploringAmount()) {
          // Depending on race it decides enough is enough
          fleet.setaStarSearch(null);
          ignoreSun = mission.getSunName();
          missionComplete = true;
        }
        if (fleet.getaStarSearch() == null) {
          Sun sun = null;
          if (missionComplete) {
            sun = game.getStarMap().getNearestSolarSystem(fleet.getX(),
                fleet.getY(), info, fleet, ignoreSun);
            if (!sun.getName().equals(mission.getSunName())) {
              mission.setTarget(sun.getCenterCoordinate());
              fleet.setRoute(new Route(fleet.getX(), fleet.getY(),
                  mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
              mission.setSunName(sun.getName());
              mission.setPhase(MissionPhase.TREKKING);
              // Starting the new exploring mission
              mission.setMissionTime(0);
              return;
            }
          } else {
            sun = game.getStarMap().getSunByName(mission.getSunName());
          }
          PathPoint point = info.getUnchartedSector(sun, fleet);
          if (point != null) {
            mission.setTarget(new Coordinate(point.getX(), point.getY()));
            AStarSearch search = new AStarSearch(game.getStarMap(),
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY());
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRegularMoves(game, fleet, info);
          }
        } else {
          makeRegularMoves(game, fleet, info);
        }
      }
    } // End Of Explore
  }

  /**
   * Handle Colonize mission
   * @param mission Colonize mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleColonize(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.COLONIZE) {
      if (mission.getPhase() == MissionPhase.LOADING) {
        // Loading colonist
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        if (planet.getPlanetPlayerInfo() == info) {
          Ship[] ships = fleet.getShips();
          int colony = 0;
          for (int i = 0; i < ships.length; i++) {
            if (ships[i].isColonyModule()) {
              colony = i;
              break;
            }
          }
          if (planet.getTotalPopulation() > 2 && planet.takeColonist()
              && ships[colony].getFreeCargoColonists() > 0) {
            // One colonist on board, ready to go trekking
            ships[colony].setColonist(ships[colony].getColonist() + 1);
            mission.setPhase(MissionPhase.TREKKING);
            Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                mission.getY(), fleet.getFleetFtlSpeed());
            fleet.setRoute(route);
          }
          if (planet.getTotalPopulation() > 3 && planet.takeColonist()
              && ships[colony].getFreeCargoColonists() > 0) {
            ships[colony].setColonist(ships[colony].getColonist() + 1);
          }
        }
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        // Target acquired
        mission.setPhase(MissionPhase.EXECUTING);
        Ship ship = fleet.getColonyShip();
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        if (ship != null && planet != null
            && planet.getPlanetPlayerInfo() == null) {
          // Make sure that ship is really colony and there is planet to
          // colonize
          planet.setPlanetOwner(game.getStarMap().getAiTurnNumber(), info);
          if (info.getRace() == SpaceRace.MECHIONS) {
            planet.setWorkers(Planet.PRODUCTION_WORKERS, ship.getColonist());
          } else {
            planet.setWorkers(Planet.PRODUCTION_FOOD, ship.getColonist());
          }
          // Remove the ship and AI just colonized planet
          info.getMissions().remove(mission);
          fleet.removeShip(ship);
          if (fleet.getNumberOfShip() == 0) {
            // Remove also empty fleet
            info.getFleets().recalculateList();
          }
          ShipStat stat = game.getStarMap().getCurrentPlayerInfo()
              .getShipStatByName(ship.getName());
          stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
        }

      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
    } // End of colonize

  }

  /**
   * Handle Colonize mission
   * @param mission Colonize mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleAttack(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.ATTACK) {
      if (mission.getPhase() == MissionPhase.PLANNING
          && mission.getTargetPlanet() != null && info.getMissions()
              .isAttackMissionLast(mission.getX(), mission.getY())) {
        int bombers = 0;
        int trooper = 0;
        int military = 0;
        for (Ship ship : fleet.getShips()) {
          if (ship.hasBombs()) {
            bombers++;
          }
          if (ship.isTrooperShip()) {
            trooper++;
          }
          if (ship.getTotalMilitaryPower() > 0) {
            military++;
          }
        }
        if (military >= info.getRace().getAIMinimumAttackShips()
            && (bombers + trooper) > info.getRace()
                .getAIMinimumConquerShips()) {
          mission.setPhase(MissionPhase.EXECUTING);
          Planet planet = game.getStarMap()
              .getPlanetByName(mission.getTargetPlanet());
          if (planet != null) {
            mission.setTarget(planet.getCoordinate());
            fleet.setRoute(new Route(fleet.getX(), fleet.getY(), planet.getX(),
                planet.getY(), fleet.getFleetFtlSpeed()));
          }
        }
      }
      if (mission.getPhase() == MissionPhase.LOADING) {
        // Loading Troops
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        if (planet.getPlanetPlayerInfo() == info) {
          Ship[] ships = fleet.getShips();
          int trooper = 0;
          for (int i = 0; i < ships.length; i++) {
            if (ships[i].isTrooperModule()) {
              trooper = i;
              break;
            }
          }
          if (planet.getTotalPopulation() > 2 && planet.takeColonist()
              && ships[trooper].getFreeCargoColonists() > 0) {
            // One Troops on board, ready to go trekking
            ships[trooper].setColonist(ships[trooper].getColonist() + 1);
            mission.setPhase(MissionPhase.TREKKING);
            Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                mission.getY(), fleet.getFleetFtlSpeed());
            fleet.setRoute(route);
          }
          while (planet.getTotalPopulation() > 3 && planet.takeColonist()
              && ships[trooper].getFreeCargoColonists() > 0) {
            ships[trooper].setColonist(ships[trooper].getColonist() + 1);
          }
        }
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && mission.getTargetPlanet() == null && fleet.getX() == mission.getX()
          && fleet.getY() == mission.getY()) {
        // Target acquired, merge fleet to bigger attack group
        mergeFleets(fleet, info);
        info.getMissions().remove(mission);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
      if (mission.getPhase() == MissionPhase.EXECUTING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        // Target acquired, mission completed!
        info.getMissions().remove(mission);
      } else if (mission.getPhase() == MissionPhase.EXECUTING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
    } // End of Attack
  }

  /**
   * Handle Defend mission
   * @param mission Defend mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleDefend(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.DEFEND) {
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        // Target acquired
        mission.setPhase(MissionPhase.EXECUTING);
        // Set defending route
        fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(),
            fleet.getY(), 0));
      } else if (mission.getPhase() == MissionPhase.EXECUTING) {
        mission.setMissionTime(mission.getMissionTime() + 1);
        if (mission.getMissionTime() >= info.getRace().getAIDefenseUpdate()) {
          // New defender is needed
          mission.setMissionTime(0);
          mission.setPhase(MissionPhase.PLANNING);
        }
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
    }
  }

  /**
   * Merge fleet with in same space and starting with same fleet names
   * @param fleet Fleet where to merge
   * @param info PlayerInfo for both fleets
   */
  public static void mergeFleets(final Fleet fleet, final PlayerInfo info) {
    // Merging fleets
    String[] part = fleet.getName().split("#");
    for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
      // Merge fleets in same space with same starting of fleet name
      Fleet mergeFleet = info.getFleets().getByIndex(j);
      if (mergeFleet != fleet && mergeFleet.getX() == fleet.getX()
          && mergeFleet.getY() == fleet.getY()
          && mergeFleet.getName().startsWith(part[0])) {
        for (int k = 0; k < mergeFleet.getNumberOfShip(); k++) {
          Ship ship = mergeFleet.getShipByIndex(k);
          if (ship != null) {
            fleet.addShip(ship);
          }
        }
        info.getFleets().remove(j);
        break;
      }
    }
  }

  /**
   * Handle diplomacy between two AI players
   * @param game Game class
   * @param info First player as PlayerInfo
   * @param secondIndex Second player as a index
   */
  public static void handleDiplomacyBetweenAis(final Game game,
      final PlayerInfo info, final int secondIndex) {
    // For Ai players make offer
    int index = game.getStarMap().getPlayerList().getIndex(info);
    DiplomaticTrade trade = new DiplomaticTrade(game.getStarMap(),
        index, secondIndex);
    trade.generateOffer();
    if (trade.isOfferGoodForBoth()
        || trade.getFirstOffer().isTypeInOffer(NegotiationType.WAR)) {
      // Another party accepts it or it is war
      trade.doTrades();
    } else {
      SpeechType type = trade.getSpeechTypeByOffer();
      Attitude attitude = info.getAiAttitude();
      int liking = info.getDiplomacy().getLiking(secondIndex);
      int warChance = DiplomaticTrade.getWarChanceForDecline(type, attitude,
          liking);
      int value = DiceGenerator.getRandom(99);
      if (value < warChance) {
        trade.generateEqualTrade(NegotiationType.WAR);
        trade.doTrades();
        //TODO Add NewCorp about the war
      }
    }
    trade.updateMeetingNumbers();
  }

  /**
   * Make fleet to move. This checks if there is a fleet in point where moving
   * and checks if there is a war between these players. If not then AI
   * will start diplomacy.
   * @param game Game
   * @param point Point where to move
   * @param info PlayerInfo who is moving
   * @param fleet Fleet which is moving
   */
  private static void makeFleetMove(final Game game, final PathPoint point,
      final PlayerInfo info, final Fleet fleet) {
    StarMap map = game.getStarMap();
    AStarSearch search = fleet.getaStarSearch();
    Fleet fleetAtTarget = map.getFleetByCoordinate(point.getX(), point
        .getY());
    boolean war = false;
    if (fleetAtTarget != null) {
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      war = map.isWarBetween(info, infoAtTarget);
    }
    if (war || fleetAtTarget != null) {
      // Not blocked so fleet is moving
      game.fleetMakeMove(info, fleet, point.getX(), point.getY());
      search.nextMove();
    } else {
      fleet.setMovesLeft(0);
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      if (infoAtTarget != null) {
        if (infoAtTarget.isHuman()) {
          game.changeGameState(GameState.DIPLOMACY_VIEW, info);
        } else {
          int index = map.getPlayerList().getIndex(infoAtTarget);
          handleDiplomacyBetweenAis(game, info, index);
        }
      }
    }
  }

  /**
   * Make fleet to move. This checks if there is a fleet in point where moving
   * and checks if there is a war between these players. If not then AI
   * will start diplomacy.
   * @param game Game
   * @param nx X-Coordinate where to move
   * @param ny Y-Coordinate where to move
   * @param info Playerinfo who owns the fleet
   * @param fleet Fleet which is moving
   */
  public static void makeFleetMove(final Game game, final int nx, final int ny,
      final PlayerInfo info, final Fleet fleet) {
    StarMap map = game.getStarMap();
    Fleet fleetAtTarget = map.getFleetByCoordinate(nx, ny);
    boolean war = false;
    if (fleetAtTarget != null) {
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      war = map.isWarBetween(info, infoAtTarget);
    }
    if (war || fleetAtTarget != null) {
      // Not blocked so fleet is moving
      game.fleetMakeMove(info, fleet, nx, ny);
    } else {
      fleet.setMovesLeft(0);
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      if (infoAtTarget != null) {
        if (infoAtTarget.isHuman()) {
          game.changeGameState(GameState.DIPLOMACY_VIEW, info);
        } else {
          int index = map.getPlayerList().getIndex(infoAtTarget);
          handleDiplomacyBetweenAis(game, info, index);
        }
      }
    }
  }
  /**
   * Make Regular moves according A Star Search path finding
   * @param game Game used to get access star map and planet lists
   * @param fleet Fleet to move
   * @param info Player who controls the fleet
   */
  private static void makeRegularMoves(final Game game, final Fleet fleet,
      final PlayerInfo info) {
    AStarSearch search = fleet.getaStarSearch();
    for (int mv = 0; mv < fleet.getMovesLeft(); mv++) {
      PathPoint point = search.getMove();
      if (point != null
          && !game.getStarMap().isBlocked(point.getX(), point.getY())) {
        makeFleetMove(game, point, info, fleet);
      }
    }
    fleet.setMovesLeft(0);
    if (search.isLastMove()) {
      fleet.setaStarSearch(null);
    }
  }

  /**
   * Make Reroute before FTL
   * @param game Game used to get access star map and planet lists
   * @param fleet Fleet to move
   * @param info PlayerInfo
   * @param mission Which mission
   */
  private static void makeReroute(final Game game, final Fleet fleet,
      final PlayerInfo info, final Mission mission) {
    // Fleet has encounter obstacle, taking a detour round it
    if (fleet.getaStarSearch() == null) {
      // No A star search made yet, so let's do it
      AStarSearch search = new AStarSearch(game.getStarMap(), fleet.getX(),
          fleet.getY(), mission.getX(), mission.getY(), 7);
      search.doSearch();
      search.doRoute();
      fleet.setaStarSearch(search);
      makeRerouteBeforeFTLMoves(game, fleet, info, mission);
    } else {
      makeRerouteBeforeFTLMoves(game, fleet, info, mission);
    }
  }

  /**
   * Make reroute moves while on mission
   * @param game Game used to get access star map and planet lists
   * @param fleet Fleet to move
   * @param info PlayerInfo
   * @param mission Which mission
   */
  private static void makeRerouteBeforeFTLMoves(final Game game,
      final Fleet fleet, final PlayerInfo info, final Mission mission) {
    AStarSearch search = fleet.getaStarSearch();
    for (int mv = 0; mv < fleet.getMovesLeft(); mv++) {
      PathPoint point = search.getMove();
      if (point != null
          && !game.getStarMap().isBlocked(point.getX(), point.getY())) {
        makeFleetMove(game, point, info, fleet);
      }
    }
    fleet.setMovesLeft(0);
    if (search.isLastMove()) {
      if (search.getTargetDistance() > 0) {
        fleet.setRoute(new Route(fleet.getX(), fleet.getY(), mission.getX(),
            mission.getY(), fleet.getFleetFtlSpeed()));
      }
      fleet.setaStarSearch(null);
    }

  }

}
