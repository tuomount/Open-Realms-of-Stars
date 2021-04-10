package org.openRealmOfStars.AI.Mission;

import java.util.ArrayList;

import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.AI.PlanetHandling.PlanetHandling;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.game.States.PlanetBombingView;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationOffer;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.espionage.EspionageUtility;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
   * Search for nearby fleet owned by other player. Search is
   * done inside the distance. Fleet must have less military power
   * than the fleet which is doing the search.
   * @param info Player who is doing the search
   * @param game Game
   * @param fleet Fleet whos is searching
   * @param distance Maximum search distance
   * @return Found nearby fleet or null
   */
  public static Fleet getNearByFleet(final PlayerInfo info,
      final Game game, final Fleet fleet, final int distance) {
    StarMap starMap = game.getStarMap();
    Coordinate center = fleet.getCoordinate();
    Fleet targetFleet = null;
    for (int y = -distance; y <= distance; y++) {
      for (int x = -distance; x <= distance; x++) {
        if (x == 0 && y == 0) {
          continue;
        }
        Fleet tmpFleet = starMap.getFleetByCoordinate(center.getX() + x,
            center.getY() + y);
        if (tmpFleet != null
            && info != starMap.getPlayerInfoByFleet(tmpFleet)
            && tmpFleet.getMilitaryValue() < fleet.getMilitaryValue()) {
          if (targetFleet == null) {
            targetFleet = tmpFleet;
          } else {
            double tmpDist = center.calculateDistance(
                tmpFleet.getCoordinate());
            double targetDist = center.calculateDistance(
                targetFleet.getCoordinate());
            if (tmpDist < targetDist) {
              targetFleet = tmpFleet;
            }
          }
        }
      }
    }
    return targetFleet;
  }

  /**
   * Search for nearby space anomaly. Search is
   * done inside the distance.
   * @param info Player who is doing the search
   * @param game Game
   * @param fleet Fleet whos is searching
   * @param distance Maximum search distance
   * @return Space anomaly coordinate or null
   */
  public static Coordinate getNearByAnomaly(final PlayerInfo info,
      final Game game, final Fleet fleet, final int distance) {
    StarMap starMap = game.getStarMap();
    Coordinate center = fleet.getCoordinate();
    Coordinate targetCoord = null;
    for (int y = -distance; y <= distance; y++) {
      for (int x = -distance; x <= distance; x++) {
        if (x == 0 && y == 0) {
          continue;
        }
        Tile tile = starMap.getTile(center.getX() + x, center.getY() + y);
        if (tile != null && tile.isSpaceAnomaly()) {
          if (targetCoord == null) {
            targetCoord = new Coordinate(center.getX() + x, center.getY() + y);
          } else {
            Coordinate tmpCoord = new Coordinate(center.getX() + x,
                center.getY() + y);
            double tmpDist = center.calculateDistance(tmpCoord);
            double targetDist = center.calculateDistance(targetCoord);
            if (tmpDist < targetDist) {
              targetCoord = tmpCoord;
            }
          }
        }
      }
    }
    return targetCoord;
  }

  /**
   * Handle privateering mission
   * @param mission Privateering mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handlePrivateering(final Mission mission,
      final Fleet fleet, final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.PRIVATEER) {
      Fleet targetFleet = getNearByFleet(info, game, fleet,
          fleet.getMovesLeft());
      if (targetFleet != null) {
        mission.setPhase(MissionPhase.EXECUTING);
        fleet.setRoute(null);
        AStarSearch search = new AStarSearch(game.getStarMap(),
            fleet.getX(), fleet.getY(), targetFleet.getX(), targetFleet.getY(),
            false);
        search.doSearch();
        search.doRoute();
        fleet.setaStarSearch(search);
      } else {
        Coordinate targetAnomaly = getNearByAnomaly(info, game, fleet,
            fleet.getMovesLeft());
        if (targetAnomaly != null) {
          mission.setPhase(MissionPhase.EXECUTING);
          fleet.setRoute(null);
          AStarSearch search = new AStarSearch(game.getStarMap(),
              fleet.getX(), fleet.getY(), targetAnomaly.getX(),
              targetAnomaly.getY(), false);
          search.doSearch();
          search.doRoute();
          fleet.setaStarSearch(search);
        }
      }
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
        if (mission.getMissionTime()
            >= info.getRace().getAIExploringAmount() * 2) {
          // Depending on race it decides enough is enough
          fleet.setaStarSearch(null);
        }
        if (fleet.getaStarSearch() == null) {
          Sun sun = null;
          int leastLiked = info.getDiplomacy().getLeastLiking();
          if (info.isBoard()) {
            leastLiked = DiceGenerator.getRandom(
                game.getStarMap().getPlayerList().getCurrentMaxRealms() - 1);
          }
          sun = game.getStarMap().getNearestSolarSystemForLeastLiked(
              fleet.getX(), fleet.getY(), info, leastLiked);
          if (!sun.getName().equals(mission.getSunName())) {
            mission.setTarget(sun.getCenterCoordinate());
            fleet.setRoute(new Route(fleet.getX(), fleet.getY(),
                mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
            mission.setSunName(sun.getName());
            mission.setPhase(MissionPhase.TREKKING);
            // Change solar system for privateering mission
            mission.setMissionTime(0);
            return;
          }
          PathPoint point = info.getUnchartedSector(sun, fleet);
          if (targetFleet != null) {
            point = new PathPoint(targetFleet.getX(), targetFleet.getY(),
                fleet.getCoordinate().calculateDistance(
                    targetFleet.getCoordinate()));
          }
          if (point != null) {
            mission.setTarget(new Coordinate(point.getX(), point.getY()));
            AStarSearch search = new AStarSearch(game.getStarMap(),
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(),
                false);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRegularMoves(game, fleet, info);
          }
        } else {
          makeRegularMoves(game, fleet, info);
        }
      }
      scrapTooManyShips(fleet, info, game);
    } // End Of Privateering
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
      if (mission.getPhase() == MissionPhase.LOADING) {
        Sun sun = game.getStarMap().getAboutNearestSolarSystem(fleet.getX(),
            fleet.getY(), info, fleet, ignoreSun);
        if (sun == null) {
          Planet home = game.getStarMap().getClosestHomePort(info,
              fleet.getCoordinate());
          if (home == null) {
            info.getMissions().remove(mission);
            return;
          }
          mission.setType(MissionType.MOVE);
          mission.setTarget(home.getCoordinate());
          mission.setPhase(MissionPhase.PLANNING);
          mission.setTargetPlanet(home.getName());
        } else {
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
        }
      }
      if (mission.getPhase() == MissionPhase.TREKKING) {
        Coordinate targetAnomaly = getNearByAnomaly(info, game, fleet,
            fleet.getMovesLeft());
        if (targetAnomaly != null) {
          // Focus on anomalies
          fleet.setRoute(null);
          AStarSearch search = new AStarSearch(game.getStarMap(),
              fleet.getX(), fleet.getY(), targetAnomaly.getX(),
              targetAnomaly.getY(), false);
          search.doSearch();
          search.doRoute();
          fleet.setaStarSearch(search);
          makeRegularMoves(game, fleet, info);
          return;
        }
      }
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
        Coordinate targetAnomaly = getNearByAnomaly(info, game, fleet,
            fleet.getMovesLeft());
        if (targetAnomaly != null) {
          // Focus on anomalies
          fleet.setRoute(null);
          AStarSearch search = new AStarSearch(game.getStarMap(),
              fleet.getX(), fleet.getY(), targetAnomaly.getX(),
              targetAnomaly.getY(), false);
          search.doSearch();
          search.doRoute();
          fleet.setaStarSearch(search);
          mission.setMissionTime(mission.getMissionTime() - 1);
        }
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
            sun = game.getStarMap().getAboutNearestSolarSystem(fleet.getX(),
                fleet.getY(), info, fleet, ignoreSun);
            if (sun == null) {
              Planet home = game.getStarMap().getClosestHomePort(info,
                  fleet.getCoordinate());
              if (home == null) {
                info.getMissions().remove(mission);
                return;
              }
              mission.setType(MissionType.MOVE);
              mission.setTarget(home.getCoordinate());
              mission.setPhase(MissionPhase.PLANNING);
              mission.setTargetPlanet(home.getName());
            } else {
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
            }
          } else {
            sun = game.getStarMap().getSunByName(mission.getSunName());
          }
          PathPoint point = info.getBestUnchartedSector(sun, fleet);
/*          if (point == null) {
            point = info.getClosestUnchartedSector(sun, fleet);
            if (DiceGenerator.getRandom(99) < 50) {
              // Split exploring fleets a bit by selecting
              // point by another method.
              point = info.getUnchartedSector(sun, fleet);
            }
          }*/
          if (point != null) {
            mission.setTarget(new Coordinate(point.getX(), point.getY()));
            AStarSearch search = new AStarSearch(game.getStarMap(),
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(),
                false);
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
   * Handle colony explore mission.
   * Similar to regular exploration mission, except it only
   * explores starting solar system.
   * @param mission Colony explore mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleColonyExplore(final Mission mission,
      final Fleet fleet, final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.COLONY_EXPLORE) {
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        // Target acquired, mission complete
        info.getMissions().remove(mission);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        Planet planet = game.getStarMap().getPlanetByName(
            mission.getPlanetBuilding());
        if (planet != null) {
          // This should be always set
          mission.setTarget(planet.getCoordinate());
        }
        makeReroute(game, fleet, info, mission);
      }
      if (mission.getPhase() == MissionPhase.EXECUTING) {
        mission.setMissionTime(mission.getMissionTime() + 1);
        boolean missionComplete = false;
        if (mission.getMissionTime() >= info.getRace().getAIExploringAmount()) {
          // Depending on race it decides enough is enough
          fleet.setaStarSearch(null);
          missionComplete = true;
        }
        if (fleet.getaStarSearch() == null) {
          Sun sun = null;
          if (missionComplete) {
            // going back to home
            mission.setPhase(MissionPhase.TREKKING);
            return;
          }
          sun = game.getStarMap().getSunByName(mission.getSunName());
          PathPoint point = info.getUnchartedSector(sun, fleet);
          if (point != null) {
            mission.setTarget(new Coordinate(point.getX(), point.getY()));
            AStarSearch search = new AStarSearch(game.getStarMap(),
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(),
                true);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRegularMoves(game, fleet, info);
          }
        } else {
          makeRegularMoves(game, fleet, info);
        }
      }
    } // End Of Colony Explore
  }

  /**
   * Clean mission which are assigned to fleet but fleet is no longer
   * available.
   * @param info Player whose mission are about to be clean
   */
  public static void cleanMissions(final PlayerInfo info) {
    ArrayList<Mission> missionsDelete = new ArrayList<>();
    for (int i = 0; i < info.getMissions().getSize(); i++) {
      Mission mission = info.getMissions().getMissionByIndex(i);
      if (mission.getFleetName() != null) {
        Fleet fleet = info.getFleets().getByName(mission.getFleetName());
        if (fleet == null && mission.getPhase() != MissionPhase.PLANNING
            && mission.getPhase() != MissionPhase.BUILDING) {
          missionsDelete.add(mission);
        }
      }
    }
    for (Mission mission : missionsDelete) {
      info.getMissions().remove(mission);
    }
  }

  /**
   * Find free colonizable and closet planet.
   * @param info Realm who is doing the search
   * @param planets All Planet
   * @param fleet Where fleet is
   * @return Planet or null
   */
  public static Planet findFreeColonizablePlanet(final PlayerInfo info,
      final ArrayList<Planet> planets, final Fleet fleet) {
    Planet result = null;
    double distance = 999;
    for (Planet planet : planets) {
      if (planet.getTotalRadiationLevel() <= info.getRace().getMaxRad()
          && planet.getPlanetPlayerInfo() == null && !planet.isGasGiant()
          && info.getSectorVisibility(planet.getCoordinate())
          >= PlayerInfo.FOG_OF_WAR) {
        double dist = fleet.getCoordinate().calculateDistance(
            planet.getCoordinate());
        if (dist < distance) {
          result = planet;
          distance = dist;
        }
      }
    }
    return result;
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
        if (planet == null) {
          mission.setPhase(MissionPhase.TREKKING);
        }
        if (planet != null && planet.getPlanetPlayerInfo() == info) {
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
        if (planet != null && planet.getPlanetPlayerInfo() == null
            && planet.getRadiationLevel() <= info.getRace().getMaxRad()) {
          // On top of planet waiting for colonization.
          mission.setTarget(planet.getCoordinate());
          mission.setPhase(MissionPhase.TREKKING);
        }
      }
      if ((mission.getPhase() == MissionPhase.TREKKING
          || mission.getPhase() == MissionPhase.EXECUTING)
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
            if (fleet.getCommander() != null) {
              fleet.getCommander().assignJob(Job.UNASSIGNED, info);
              fleet.setCommander(null);
            }
            // Remove also empty fleet
            info.getFleets().recalculateList();
          }
          ShipStat stat = info.getShipStatByName(ship.getName());
          if (stat != null) {
            stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
          }
          EventOnPlanet event = new EventOnPlanet(EventType.PLANET_COLONIZED,
              planet.getCoordinate(),
              planet.getName(), game.getStarMap().getAiTurnNumber());
          event.setText(info.getEmpireName()
              + " colonized planet " + planet.getName()
              + ". ");
          game.getStarMap().getHistory().addEvent(event);
          planet.eventActivation();
        } else {
          Mission planningColony = info.getMissions()
              .getBestColonizeMissionPlanning(game.getStarMap(),
              info, fleet.getCoordinate());
          if (planningColony != null) {
            mission.setTarget(new Coordinate(planningColony.getX(),
                planningColony.getY()));
            mission.setPhase(MissionPhase.TREKKING);
            fleet.setRoute(null);
            info.getMissions().remove(planningColony);
          } else {
            Planet newTarget = findFreeColonizablePlanet(info,
                game.getStarMap().getPlanetList(), fleet);
            if (newTarget != null) {
              mission.setTarget(newTarget.getCoordinate());
              mission.setPhase(MissionPhase.TREKKING);
              fleet.setRoute(null);
            } else {
              Planet homePort = game.getStarMap().getClosestHomePort(info,
                  fleet.getCoordinate());
              if (homePort != null) {
                fleet.setRoute(null);
                mission.setTarget(homePort.getCoordinate());
                mission.setTargetPlanet(homePort.getName());
                mission.setMissionTime(0);
                mission.setPhase(MissionPhase.PLANNING);
                mission.setType(MissionType.MOVE);
              }
            }
          }
        }

      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        Coordinate coord = new Coordinate(mission.getX(), mission.getY());
        if (info.getSectorVisibility(coord) == PlayerInfo.VISIBLE) {
          Planet planet = game.getStarMap().getPlanetByCoordinate(coord.getX(),
              coord.getY());
          if (planet.getPlanetOwnerIndex() != -1) {
            // Planet has been colonized so no longer colonization mission.
            Planet homePort = game.getStarMap().getClosestHomePort(info,
                fleet.getCoordinate());
            if (homePort != null) {
              mission.setTarget(homePort.getCoordinate());
              mission.setTargetPlanet(homePort.getName());
              mission.setMissionTime(0);
              mission.setPhase(MissionPhase.PLANNING);
              mission.setType(MissionType.MOVE);
            }
          }
        }
        makeReroute(game, fleet, info, mission);
      }
    } // End of colonize

  }

  /**
   * Handle Deploy starbase mission
   * @param mission Deploy starbase mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleDeployStarbase(final Mission mission,
      final Fleet fleet, final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.DEPLOY_STARBASE) {
      if (mission.getPhase() == MissionPhase.LOADING) {
        mission.setPhase(MissionPhase.TREKKING);
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        // Target acquired
        mission.setPhase(MissionPhase.EXECUTING);
        Tile tile = game.getStarMap().getTile(fleet.getX(), fleet.getY());
        if (tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
            || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2)) {
          Fleet starbaseFleet = null;
          for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
            Fleet ite = info.getFleets().getByIndex(i);
            if (ite.isStarBaseDeployed()
                && ite.getX() == fleet.getX() && ite.getY() == fleet.getY()) {
              starbaseFleet = ite;
            }
          }
          boolean startBuilding = false;
          for (Ship ship : fleet.getShips()) {
            if (ship.getHull().getHullType() == ShipHullType.STARBASE) {
              if (ship.getHull().getName().equals("Artificial planet")) {
                startBuilding = true;
              }
              fleet.removeShip(ship);
              if (starbaseFleet != null) {
                starbaseFleet.addShip(ship);
                ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
              } else {
                starbaseFleet = new Fleet(ship, fleet.getX(), fleet.getY());
                FleetList fleetList = info.getFleets();
                ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
                fleetList.add(starbaseFleet);
                starbaseFleet.setName(fleetList.generateUniqueName(
                    "Deep Space"));
              }
            }
          }
          if (startBuilding) {
            game.getStarMap().createArtificialPlanet(starbaseFleet, info);
          }
          // Remove the mission
          info.getMissions().remove(mission);
          if (fleet.getNumberOfShip() == 0) {
            if (fleet.getCommander() != null) {
              fleet.getCommander().assignJob(Job.UNASSIGNED, info);
              fleet.setCommander(null);
            }
           // Remove also empty fleet
            info.getFleets().recalculateList();
          }
        }
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        Coordinate coord = new Coordinate(mission.getX(), mission.getY());
        if (info.getSectorVisibility(coord) == PlayerInfo.VISIBLE) {
          Fleet anotherFleet = game.getStarMap().getFleetByCoordinate(
              mission.getX(), mission.getY());
          if (anotherFleet != null) {
            Mission newTarget = info.getMissions().getMission(
                MissionType.DEPLOY_STARBASE, MissionPhase.PLANNING);
            if (newTarget != null) {
              newTarget.setFleetName(fleet.getName());
              newTarget.setPhase(MissionPhase.TREKKING);
              Route route = new Route(fleet.getX(), fleet.getY(),
                  newTarget.getX(), newTarget.getY(), fleet.getFleetFtlSpeed());
              fleet.setRoute(route);
              info.getMissions().remove(mission);
            } else {
              // Anchor has been taken, no more deploy starbase mission
              Planet homePort = game.getStarMap().getClosestHomePort(info,
                  fleet.getCoordinate());
              mission.setTarget(homePort.getCoordinate());
              mission.setTargetPlanet(homePort.getName());
              mission.setMissionTime(0);
              mission.setPhase(MissionPhase.PLANNING);
              mission.setType(MissionType.MOVE);
            }
          }
        }
        makeReroute(game, fleet, info, mission);
      }
    } // End of Deploy starbase
  }

  /**
   * Handle Move mission
   * @param mission Move mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleMove(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.MOVE) {
      if (mission.getPhase() == MissionPhase.PLANNING) {
        mission.setMissionTime(0);
      }
      if (mission.getPhase() != MissionPhase.TREKKING) {
        Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
            mission.getY(), fleet.getFleetFtlSpeed());
        fleet.setRoute(route);
        mission.setPhase(MissionPhase.TREKKING);
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        // Target acquired, mission complete
        info.getMissions().remove(mission);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        mission.setMissionTime(mission.getMissionTime() + 1);
        makeReroute(game, fleet, info, mission);
      }
    } // End of move
  }

  /**
   * Handle Intercept mission
   * @param mission Intercept mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleIntercept(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.INTERCEPT) {
      if (mission.getPhase() != MissionPhase.TREKKING) {
        mission.setPhase(MissionPhase.TREKKING);
        fleet.setaStarSearch(null);
        fleet.setRoute(null);
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() != mission.getX() || fleet.getY() != mission.getY()) {
        AStarSearch search = new AStarSearch(game.getStarMap(), fleet.getX(),
            fleet.getY(), mission.getX(), mission.getY(), true);
        search.doSearch();
        search.doRoute();
        fleet.setaStarSearch(search);
        makeRegularMoves(game, fleet, info);
      }
    } // End of intercept
  }

  /**
   * Handle Trade mission
   * @param mission Trade mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleTrade(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.TRADE_FLEET) {
      Planet previousTarget = game.getStarMap()
          .getPlanetByCoordinate(mission.getX(), mission.getY());
      int playerIndex = previousTarget.getPlanetOwnerIndex();
      if (playerIndex != -1
          && info.getDiplomacy().getDiplomaticRelation(playerIndex).equals(
              Diplomacy.WAR)) {
        Message msg = new Message(MessageType.FLEET,
            fleet.getName() + " has stopped trade due war and returning to"
                + " home planet.", Icons.getIconByName(Icons.ICON_TROOPS));
        msg.setCoordinate(fleet.getCoordinate());
        msg.setMatchByString(fleet.getName());
        info.getMsgList().addUpcomingMessage(msg);
        info.getMissions().remove(mission);
        // Make fleet return to home
        Planet homePlanet = game.getStarMap().getPlanetByName(
            mission.getPlanetBuilding());
        Mission moveBack = new Mission(MissionType.MOVE,
            MissionPhase.TREKKING, homePlanet.getCoordinate());
        moveBack.setPlanetBuilding(mission.getPlanetBuilding());
        moveBack.setTargetPlanet(mission.getPlanetBuilding());
        moveBack.setFleetName(fleet.getName());
        info.getMissions().add(moveBack);
        return;
      }
      if (mission.getPhase() == MissionPhase.LOADING) {
        Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
            mission.getY(), fleet.getFleetFtlSpeed());
        fleet.setRoute(route);
        Planet homePlanet = game.getStarMap().getPlanetByCoordinate(
            fleet.getX(), fleet.getY());
        if (homePlanet != null && homePlanet.getPlanetPlayerInfo() == info) {
          fleet.doTrade(homePlanet, info);
        }
        mission.setPhase(MissionPhase.TREKKING);
      }
      Coordinate targetCoord = new Coordinate(mission.getX(), mission.getY());
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getCoordinate().calculateDistance(targetCoord) <= 1) {
        // Target acquired, let's do trade
        mission.setPhase(MissionPhase.EXECUTING);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
      if (mission.getPhase() == MissionPhase.EXECUTING) {
        previousTarget = game.getStarMap()
            .getPlanetByCoordinate(mission.getX(), mission.getY());
        playerIndex = previousTarget.getPlanetOwnerIndex();
        DiplomacyBonusList diplomacy = info.getDiplomacy().getDiplomacyList(
            playerIndex);
        StarMapUtilities.doTradeWithShips(diplomacy, fleet, previousTarget,
            info, game.getStarMap().getNewsCorpData());
        if (mission.getTargetPlanet().equals(previousTarget.getName())) {
          Planet homePlanet = game.getStarMap().getPlanetByName(
              mission.getPlanetBuilding());
          mission.setTarget(homePlanet.getCoordinate());
        } else if (mission.getPlanetBuilding().equals(
            previousTarget.getName())) {
          Planet targetPlanet = game.getStarMap().getPlanetByName(
              mission.getTargetPlanet());
          mission.setTarget(targetPlanet.getCoordinate());
        }
        mission.setPhase(MissionPhase.LOADING);
      }
    } // End of trade
  }

  /**
   * Handle diplomatic mission
   * @param mission Diplomatic mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleDiplomaticMission(final Mission mission,
      final Fleet fleet, final PlayerInfo info, final Game game) {
    if (mission != null
        && mission.getType() == MissionType.DIPLOMATIC_DELEGACY) {
      if (mission.getPhase() == MissionPhase.LOADING) {
        if (mission.getX() == game.getStarMap().getMaxX() / 2
            && mission.getY() == game.getStarMap().getMaxY() / 2) {
          Planet target = game.getStarMap().getClosetPlanetFromCoordinate(
              fleet.getCoordinate(), mission.getTargetRealmName(), info);
          if (target != null) {
            mission.setTarget(target.getCoordinate());
          } else {
            Coordinate coord = game.getStarMap().getClosetSectorFromCoordinate(
                fleet.getCoordinate(), mission.getTargetRealmName(), info);
            if (coord != null) {
              mission.setTarget(coord);
            }
          }
        }
        mission.setPhase(MissionPhase.TREKKING);
        mission.setMissionTime(0);
      }
      Coordinate targetCoord = new Coordinate(mission.getX(), mission.getY());
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getCoordinate().calculateDistance(targetCoord) <= 1) {
        // Target acquired, let's do trade
        mission.setPhase(MissionPhase.EXECUTING);
        mission.setMissionTime(0);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        if (mission.getX() == game.getStarMap().getMaxX() / 2
            && mission.getY() == game.getStarMap().getMaxY() / 2) {
          Planet target = game.getStarMap().getClosetPlanetFromCoordinate(
              fleet.getCoordinate(), mission.getTargetRealmName(), info);
          if (target != null) {
            mission.setTarget(target.getCoordinate());
          } else {
            Coordinate coord = game.getStarMap().getClosetSectorFromCoordinate(
                fleet.getCoordinate(), mission.getTargetRealmName(), info);
            if (coord != null) {
              mission.setTarget(coord);
            }
          }
        }
        makeReroute(game, fleet, info, mission);
      }
      if (mission.getPhase() == MissionPhase.EXECUTING) {
        mission.setMissionTime(mission.getMissionTime() + 1);
        if (mission.getMissionTime() >= 5) {
          // Diplomatic trading should have happened
          // Return closest home port
          Planet homePort = game.getStarMap().getClosestHomePort(info,
              fleet.getCoordinate());
          if (homePort != null) {
            mission.setType(MissionType.MOVE);
            mission.setTarget(homePort.getCoordinate());
            mission.setPhase(MissionPhase.TREKKING);
            Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                mission.getY(), fleet.getFleetFtlSpeed());
            fleet.setRoute(route);
          } else {
            // No home port so just remove the mission
            info.getMissions().remove(mission);
          }
          return;
        }
      }
    } // End of diplomatic mission.
  }

  /**
   * Handle Gather mission
   * @param mission Gather mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleGather(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.GATHER) {
      if (mission.getPhase() != MissionPhase.TREKKING) {
        Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
            mission.getY(), fleet.getFleetFtlSpeed());
        fleet.setRoute(route);
        mission.setPhase(MissionPhase.TREKKING);
      }
      if (mission.getPhase() == MissionPhase.LOADING) {
        // Loading Troops
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        Ship[] ships = fleet.getShips();
        int trooper = -1;
        for (int i = 0; i < ships.length; i++) {
          if (ships[i].isTrooperModule()) {
            trooper = i;
            break;
          }
        }
        if (trooper < 0) {
          // Not a trooper so, moving just next phase
          mission.setPhase(MissionPhase.TREKKING);
        }
        if (planet == null && trooper >= 0) {
          if (fleet.getTotalCargoColonist() > 0) {
            mission.setPhase(MissionPhase.TREKKING);
          } else {
            Planet homePort = game.getStarMap().getClosestHomePort(info,
                fleet.getCoordinate());
            if (homePort != null) {
              mission.setType(MissionType.MOVE);
              mission.setTarget(homePort.getCoordinate());
            }
          }
        } else if (planet != null && planet.getPlanetPlayerInfo() == info
            && trooper >= 0) {
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
          && fleet.getX() == mission.getX()
          && fleet.getY() == mission.getY()) {
        // Target acquired, mission complete
        String attackFleetName = "Attacker of " +  mission.getTargetPlanet();
        Fleet attackFleet = info.getFleets().getByName(attackFleetName);
        if (attackFleet == null) {
          if (info.getFleets().isUniqueName(attackFleetName, fleet)) {
            fleet.setName(attackFleetName);
          } else {
            fleet.setName(info.getFleets().generateUniqueName(
                attackFleetName));
          }
        } else {
          fleet.setName(info.getFleets().generateUniqueName(
              attackFleetName));
          mergeFleets(attackFleet, info);
        }
        info.getMissions().remove(mission);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
    } // End of Gather

  }


  /**
   * Handle Attack mission
   * @param mission Attack mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleAttack(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.ATTACK) {
      if (mission.getPhase() == MissionPhase.PLANNING
          && mission.getTargetPlanet() != null && info.getMissions()
              .noMoreGatherMissions(mission.getTargetPlanet())) {
        mission.setPhase(MissionPhase.TREKKING);
        Planet planet = game.getStarMap()
            .getPlanetByName(mission.getTargetPlanet());
        if (planet != null) {
          mission.setTarget(planet.getCoordinate());
          fleet.setRoute(new Route(fleet.getX(), fleet.getY(), planet.getX(),
              planet.getY(), fleet.getFleetFtlSpeed()));
        }
        Ship[] ships = fleet.getShips();
        for (int i = 0; i < ships.length; i++) {
          if (ships[i].isTrooperModule() && ships[i].getColonist() == 0) {
            // Empty trooper found so we need to load it first
            mission.setPhase(MissionPhase.LOADING);
            break;
          }
        }
      }
      if (mission.getPhase() == MissionPhase.PLANNING
          && mission.getPlanetBuilding() != null) {
        Planet planet = game.getStarMap().getPlanetByName(
            mission.getPlanetBuilding());
        if (planet != null
            && !planet.getCoordinate().sameAs(fleet.getCoordinate())) {
          AStarSearch search = new AStarSearch(game.getStarMap(), fleet.getX(),
              fleet.getY(), planet.getX(), planet.getY(), true);
          search.doSearch();
          search.doRoute();
          fleet.setaStarSearch(search);
          makeRegularMoves(game, fleet, info);

        }
      }
      if (mission.getPhase() == MissionPhase.LOADING) {
        // Loading Troops
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        if (planet == null) {
          if (fleet.getTotalCargoColonist() > 0) {
            mission.setPhase(MissionPhase.TREKKING);
          } else {
            Planet homePort = game.getStarMap().getClosestHomePort(info,
                fleet.getCoordinate());
            if (homePort != null) {
              mission.setType(MissionType.MOVE);
              mission.setTarget(homePort.getCoordinate());
            }
          }
        } else if (planet.getPlanetPlayerInfo() == info) {
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
      if (mission.getPhase() == MissionPhase.TREKKING) {
        Planet planet = game.getStarMap().getPlanetByCoordinate(mission.getX(),
            mission.getY());
        if (planet != null && planet.getPlanetPlayerInfo() != null
            && !info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
          // Planet owner has been changed
          // Return closest home port
          Planet homePort = game.getStarMap().getClosestHomePort(info,
              fleet.getCoordinate());
          if (homePort != null) {
            mission.setType(MissionType.MOVE);
            mission.setTarget(homePort.getCoordinate());
            mission.setPhase(MissionPhase.TREKKING);
            Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                mission.getY(), fleet.getFleetFtlSpeed());
            fleet.setRoute(route);
          } else {
            // No home port so just remove the mission
            info.getMissions().remove(mission);
          }
          return;
        }
        if (fleet.getX() == mission.getX()
          && fleet.getY() == mission.getY()) {
          // Target acquired, initiating attack
          mission.setPhase(MissionPhase.EXECUTING);
        } else if (mission.getPhase() == MissionPhase.TREKKING
            && fleet.getRoute() == null) {
          makeReroute(game, fleet, info, mission);
        }
      }
      if (mission.getPhase() == MissionPhase.EXECUTING
          && fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        if (planet == null) {
          return;
        }
        if (planet.getPlanetPlayerInfo() != null
            && !info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
          // Planet owner has been changed
          // Return closest home port
          Planet homePort = game.getStarMap().getClosestHomePort(info,
              fleet.getCoordinate());
          if (homePort != null) {
            mission.setType(MissionType.MOVE);
            mission.setTarget(homePort.getCoordinate());
            mission.setPhase(MissionPhase.TREKKING);
            Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                mission.getY(), fleet.getFleetFtlSpeed());
            fleet.setRoute(route);
          } else {
            // No home port so just remove the mission
            info.getMissions().remove(mission);
          }
          return;
        }
        // Target acquired, mission completed!
        info.getMissions().remove(mission);
        if (planet.getPlanetPlayerInfo() != null
            && planet.getPlanetPlayerInfo().isHuman()) {
          // Bombing human planet
          int attackerIndex = game.getStarMap().getPlayerList().getIndex(info);
          PlanetBombingView bombView = new PlanetBombingView(planet, fleet,
              info, attackerIndex, game);
          game.changeGameState(GameState.PLANETBOMBINGVIEW, bombView);
        } else {
          // Bombing AI planet
          PlanetBombingView bombingView = new PlanetBombingView(planet, fleet,
              info, game.getStarMap().getPlayerList().getIndex(info), game);
          bombingView.setStarMap(game.getStarMap());
          bombingView.handleAiToAiAttack();
        }
      } else if (mission.getPhase() == MissionPhase.EXECUTING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
      scrapTooManyShips(fleet, info, game);
    } // End of Attack
  }

  /**
   * Handle Destroy starbase mission
   * @param mission Destroy starbase mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleDestroyStarbase(final Mission mission,
      final Fleet fleet, final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.DESTROY_STARBASE) {
      if (mission.getPhase() == MissionPhase.PLANNING
          && mission.getTargetPlanet() != null && info.getMissions()
              .noMoreGatherMissions(mission.getTargetPlanet())) {
          mission.setPhase(MissionPhase.TREKKING);
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getX() == mission.getX()
          && fleet.getY() == mission.getY()) {
        // Target acquired, merge fleet to bigger attack group
        mission.setPhase(MissionPhase.EXECUTING);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
      if (mission.getPhase() == MissionPhase.EXECUTING
          && fleet.getX() == mission.getX()
          && fleet.getY() == mission.getY()) {
        // Target acquired, mission completed!
        info.getMissions().remove(mission);
      }
      scrapTooManyShips(fleet, info, game);
    } // End of destroy starbase
  }

  /**
   * Method to scrap ships. Checks if ships cost too much
   * to maintain and then remove one from the fleet.
   * @param fleet Fleet where to scrap
   * @param info Player Info
   * @param game Game itself.
   */
  public static void scrapTooManyShips(final Fleet fleet,
      final PlayerInfo info, final Game game) {
    int capacity = game.getStarMap().getTotalFleetCapacity(info);
    double currentCapacity = info.getFleets().getTotalFleetCapacity();
    if (currentCapacity > capacity) {
      int shipCost = (int) Math.floor(currentCapacity - capacity);
      int index = game.getPlayers().getIndex(info);
      int credPlus = game.getStarMap().getTotalProductionByPlayerPerTurn(
          Planet.PRODUCTION_CREDITS, index);
      if (credPlus < shipCost && shipCost > 0
          && info.getTotalCredits() / shipCost < 5) {
        Ship ship = fleet.getScrableShip(info.getObsoleteShips());
        if (ship != null) {
          fleet.removeShip(ship);
          ShipStat stat = info.getShipStatByName(ship.getName());
          if (stat != null) {
            stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
          }
        }
      }
    }
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
    scrapTooManyShips(fleet, info, game);
  }

  /**
   * Handle Spy mission
   * @param mission Spy mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleSpyMission(final Mission mission, final Fleet fleet,
      final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.SPY_MISSION) {
      if (mission.getPhase() == MissionPhase.LOADING) {
        Mission newPlan = PlanetHandling.createSpyShipMission(info,
            game.getStarMap());
        if (newPlan != null) {
          // New target to set up
          mission.setTarget(new Coordinate(newPlan.getX(), newPlan.getY()));
          mission.setTargetPlanet(newPlan.getTargetPlanet());
          mission.setPhase(MissionPhase.TREKKING);
        }
      }
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        // Fleet has encounter obstacle, taking a detour round it
        Planet planet = game.getStarMap().getPlanetByName(
            mission.getTargetPlanet());
        CulturePower culture = game.getStarMap().getSectorCulture(
            fleet.getX(), fleet.getY());
        if (planet != null && culture != null
            && planet.getPlanetOwnerIndex() == culture.getHighestCulture()
            && culture.getHighestCulture() > -1) {
          // Fleet has found correct player sector, start spying
          mission.setPhase(MissionPhase.EXECUTING);
          mission.setMissionTime(0);
          fleet.setaStarSearch(null);
        } else {
          makeReroute(game, fleet, info, mission);
        }
      }
      if (mission.getPhase() == MissionPhase.EXECUTING) {
        // Trying to keep as away as possible from planet
        // but staying on culture sector.
        // Keeps moving a bit so it would look like
        // exploration ship.
        for (int i = 0; i < fleet.getMovesLeft(); i++) {
          CulturePower cultureUp = game.getStarMap().getSectorCulture(
              fleet.getX(), fleet.getY() - 1);
          CulturePower cultureDown = game.getStarMap().getSectorCulture(
              fleet.getX(), fleet.getY() + 1);
          CulturePower cultureLeft = game.getStarMap().getSectorCulture(
              fleet.getX() - 1, fleet.getY());
          CulturePower cultureRight = game.getStarMap().getSectorCulture(
              fleet.getX() + 1, fleet.getY());
          Planet planet = game.getStarMap().getPlanetByName(
              mission.getTargetPlanet());
          double distance = 0;
          Coordinate target = null;
          if (planet != null) {
            if (cultureUp != null
                && cultureUp.getHighestCulture() == planet.getPlanetOwnerIndex()
                && cultureUp.getHighestCulture() > -1
                && planet.getCoordinate().calculateDistance(
                    fleet.getCoordinate().getDirection(Coordinate.UP))
                    > distance) {
              distance = planet.getCoordinate().calculateDistance(
                  fleet.getCoordinate().getDirection(Coordinate.UP));
              target = fleet.getCoordinate().getDirection(Coordinate.UP);
            }
            if (cultureRight != null
                && cultureRight.getHighestCulture()
                == planet.getPlanetOwnerIndex()
                && cultureRight.getHighestCulture() > -1
                && planet.getCoordinate().calculateDistance(
                    fleet.getCoordinate().getDirection(Coordinate.RIGHT))
                    > distance) {
              distance = planet.getCoordinate().calculateDistance(
                  fleet.getCoordinate().getDirection(Coordinate.RIGHT));
              target = fleet.getCoordinate().getDirection(Coordinate.RIGHT);
            }
            if (cultureDown != null
                && cultureDown.getHighestCulture()
                == planet.getPlanetOwnerIndex()
                && cultureDown.getHighestCulture() > -1
                && planet.getCoordinate().calculateDistance(
                    fleet.getCoordinate().getDirection(Coordinate.DOWN))
                    > distance) {
              distance = planet.getCoordinate().calculateDistance(
                  fleet.getCoordinate().getDirection(Coordinate.DOWN));
              target = fleet.getCoordinate().getDirection(Coordinate.DOWN);
            }
            if (cultureLeft != null
                && cultureLeft.getHighestCulture()
                == planet.getPlanetOwnerIndex()
                && cultureLeft.getHighestCulture() > -1
                && planet.getCoordinate().calculateDistance(
                    fleet.getCoordinate().getDirection(Coordinate.LEFT))
                    > distance) {
              distance = planet.getCoordinate().calculateDistance(
                  fleet.getCoordinate().getDirection(Coordinate.LEFT));
              target = fleet.getCoordinate().getDirection(Coordinate.LEFT);
            }
            if (planet.getPlanetPlayerInfo() == info) {
              mission.setPhase(MissionPhase.LOADING);
            }
          }
          if (target != null) {
            fleet.setPos(target);
          }
        }
        fleet.setMovesLeft(0);
        mission.setMissionTime(mission.getMissionTime() + 1);
        if (fleet.getCommander() != null
            && DiceGenerator.getRandom(40) < mission.getMissionTime()) {
          mission.setPhase(MissionPhase.TREKKING);
          mission.setType(MissionType.ESPIONAGE_MISSION);
          mission.setEspionageType(EspionageMission.NOT_SELECTED_YET);
          mission.setMissionTime(0);
        }
      }
    } // End Of Spy mission
  }

  /**
   * Handle Espionage mission
   * @param mission Espionage mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting star map and planet
   */
  public static void handleEspionageMission(final Mission mission,
      final Fleet fleet, final PlayerInfo info, final Game game) {
    if (mission != null && mission.getType() == MissionType.ESPIONAGE_MISSION) {
      if (mission.getPhase() == MissionPhase.LOADING) {
        Mission newPlan = PlanetHandling.createSpyShipMission(info,
            game.getStarMap());
        if (newPlan != null) {
          // New target to set up
          mission.setTarget(new Coordinate(newPlan.getX(), newPlan.getY()));
          mission.setTargetPlanet(newPlan.getTargetPlanet());
          mission.setPhase(MissionPhase.TREKKING);
        }
      }
      Coordinate targetCoord = new Coordinate(mission.getX(), mission.getY());
      if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getCoordinate().calculateDistance(targetCoord) <= 1) {
        // Target acquired, let's do trade
        mission.setPhase(MissionPhase.EXECUTING);
      } else if (mission.getPhase() == MissionPhase.TREKKING
          && fleet.getRoute() == null) {
        makeReroute(game, fleet, info, mission);
      }
      if (mission.getPhase() == MissionPhase.EXECUTING) {
        if (fleet.getCommander() != null) {
          Planet planet = game.getStarMap().getPlanetByName(
              mission.getTargetPlanet());
          if (planet != null && planet.getPlanetPlayerInfo() != null
              && info != planet.getPlanetPlayerInfo()) {
            EspionageMission[] allowedTypes =
                EspionageUtility.getAvailableMissionTypes(planet, info);
            EspionageMission selectedType = mission.getEspionageType();
            boolean ok = false;
            for (EspionageMission type : allowedTypes) {
              if (selectedType == type) {
                ok = true;
              }
            }
            if (!info.isHuman()
                && (selectedType == null
                   || selectedType == EspionageMission.NOT_SELECTED_YET)
                && allowedTypes.length > 0) {
              selectedType = scoreEspionageMission(info, planet, allowedTypes,
                  fleet, game.getStarMap());
              ok = true;
            }
            if (ok) {
              fleet.getCommander().getStats().addOne(
                  StatType.NUMBER_OF_ESPIONAGE);
              boolean somethingHappened = false;
              int success = EspionageUtility.calculateSuccess(planet, fleet,
                  selectedType);
              if (DiceGenerator.getRandom(100) < success) {
                // Succeed.
                handleSuccessfulEspionage(selectedType, planet, fleet, info,
                    game);
                somethingHappened = true;
              }
              int caught = EspionageUtility.calculateDetectionSuccess(planet,
                  fleet, selectedType);
              if (DiceGenerator.getRandom(100) < caught) {
                // Caught
                handleCaughtEspionage(selectedType, planet, fleet, info,
                    game, somethingHappened);
                somethingHappened = true;
              }
              if (!somethingHappened) {
                Message msg = new Message(MessageType.LEADER,
                    fleet.getCommander().getCallName()
                    + " failed during espionage mission against "
                    + planet.getPlanetPlayerInfo().getEmpireName()
                    + ". "
                    + fleet.getCommander().getCallName()
                    + " avoided being caught tough...",
                    Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
                msg.setCoordinate(planet.getCoordinate());
                msg.setMatchByString(fleet.getCommander().getName());
                info.getMsgList().addUpcomingMessage(msg);
              }
            }
          }
        }
        // Espionage has been done, removing it.
        info.getMissions().remove(mission);
        if (!info.isHuman()) {
          Mission newPlan = PlanetHandling.createSpyShipMission(info,
              game.getStarMap());
          if (newPlan != null) {
            newPlan.setPhase(MissionPhase.TREKKING);
            newPlan.setFleetName(fleet.getName());
            info.getMissions().add(newPlan);
          }
        }
      }
    } // End Of Espionage mission
  }

  /**
   * Get espionage risk factor for AI attitude.
   * @param attitude Attitude
   * @return Risk factor from 1 to 6. 6 is taking highest risks.
   */
  private static int getEspionageRiskFactor(final Attitude attitude) {
    int riskFactor = 2;
    switch (attitude) {
      case BACKSTABBING: {
        riskFactor = 6;
        break;
      }
      case AGGRESSIVE: {
        riskFactor = 5;
        break;
      }
      case MILITARISTIC: {
        riskFactor = 4;
        break;
      }
      case EXPANSIONIST: {
        riskFactor = 3;
        break;
      }
      case DIPLOMATIC:
      case PEACEFUL: {
        riskFactor = 1;
        break;
      }
      default:
      case LOGICAL:
      case SCIENTIFIC:
      case MERCHANTICAL: {
        riskFactor = 2;
        break;
      }
    }
    return riskFactor;
  }
  /**
   * Score espionage mission
   * @param info Realm doing espionage
   * @param planet Planet where to do espionage
   * @param allowedTypes Allowed espionage types
   * @param fleet Fleet doing espionage
   * @param starMap Full Starmap
   * @return EspionageMission, never null.
   */
  private static EspionageMission scoreEspionageMission(final PlayerInfo info,
      final Planet planet, final EspionageMission[] allowedTypes,
      final Fleet fleet, final StarMap starMap) {
    int[] scores = new int[allowedTypes.length];
    int target = planet.getPlanetOwnerIndex();
    int infoIndex = starMap.getPlayerList().getIndex(info);
    Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
    int riskFactor = getEspionageRiskFactor(attitude);
    int sum = 0;
    for (int i = 0; i < allowedTypes.length; i++) {
      int success = EspionageUtility.calculateSuccess(planet, fleet,
          allowedTypes[i]);
      int caught = EspionageUtility.calculateDetectionSuccess(planet,
          fleet, allowedTypes[i]);
      if (allowedTypes[i] == EspionageMission.ASSASSIN_GOVERNOR
          || allowedTypes[i] == EspionageMission.DEMOLISH_BUILDING) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 50;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 0;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 25;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 10;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.STEAL_CREDIT) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 50;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 20;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 25;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 40;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 10;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 20;
        }
      }
      if (allowedTypes[i] == EspionageMission.STEAL_TECH) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case EXPANSIONIST:
          case BACKSTABBING: {
            scores[i] = 40;
            break;
          }
          case DIPLOMATIC:
          case MERCHANTICAL:
          case PEACEFUL: {
            scores[i] = 30;
            break;
          }
          default:
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 50;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 20;
        }
      }
      if (allowedTypes[i] == EspionageMission.TERRORIST_ATTACK) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 50;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 0;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 20;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 15;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 5;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.SABOTAGE
          || allowedTypes[i] == EspionageMission.DEMOLISH_BUILDING) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 60;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 5;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 30;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 10;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.FALSE_FLAG) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 60;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 5;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 30;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 10;
            break;
          }
        }
        int difference = starMap.getMilitaryDifference(infoIndex, target);
        if (difference > 100) {
          scores[i] = scores[i] + 30;
        } else if (difference > 80) {
          scores[i] = scores[i] + 20;
        } else if (difference > 50) {
          scores[i] = scores[i] + 10;
        } else if (difference > 0) {
          scores[i] = scores[i] + 0;
        } else {
          scores[i] = scores[i] - 15;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isPeace(target)) {
          scores[i] = scores[i] + 5;
        }
        if (info.getDiplomacy().isTradeAlliance(target)) {
          scores[i] = scores[i] - 5;
        }
        if (info.getDiplomacy().isDefensivePact(target)) {
          scores[i] = scores[i] - 10;
        }
        if (info.getDiplomacy().isAlliance(target)) {
          scores[i] = scores[i] - 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.DEADLY_VIRUS) {
        switch (attitude) {
          case BACKSTABBING: {
            scores[i] = 40;
            break;
          }
          case AGGRESSIVE: {
            scores[i] = 20;
            break;
          }
          case MILITARISTIC: {
            scores[i] = 10;
            break;
          }
          default:
          case MERCHANTICAL:
          case LOGICAL:
          case SCIENTIFIC:
          case EXPANSIONIST:
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 0;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 5;
          Mission mission = info.getMissions().getAttackMission(
              planet.getName());
          if (mission != null && mission.getPhase() == MissionPhase.TREKKING) {
            scores[i] = scores[i] + 15;
          }
          if (mission != null && mission.getPhase() == MissionPhase.EXECUTING) {
            scores[i] = scores[i] + 20;
          }
        }
        if (info.getDiplomacy().isPeace(target)) {
          scores[i] = scores[i] - 10;
        }
        if (info.getDiplomacy().isTradeAlliance(target)) {
          scores[i] = scores[i] - 15;
        }
        if (info.getDiplomacy().isDefensivePact(target)) {
          scores[i] = scores[i] - 20;
        }
        if (info.getDiplomacy().isAlliance(target)) {
          scores[i] = scores[i] - 20;
        }
      }
      int likingOpposite = 1;
      if (allowedTypes[i] == EspionageMission.GAIN_TRUST) {
        // Liking bonus is actually reversed for gain trust mission.
        likingOpposite = -1;
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC: {
            scores[i] = 0;
            break;
          }
          case BACKSTABBING: {
            scores[i] = 40;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 60;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 20;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 40;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 40;
            break;
          }
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] - 40;
        }
        if (planet.getPlanetPlayerInfo().getDiplomacy()
            .getLiking(infoIndex) < 0) {
          scores[i] = scores[i] + 80;
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = 0;
        }
      }
      // Adds liking for scores.
      switch (info.getDiplomacy().getLiking(planet.getPlanetOwnerIndex())) {
        case Diplomacy.HATE: {
          scores[i] = scores[i] + 30 * likingOpposite;
          break;
        }
        case Diplomacy.DISLIKE: {
          scores[i] = scores[i] + 15 * likingOpposite;
          break;
        }
        default:
        case Diplomacy.NEUTRAL: {
          scores[i] = scores[i] + 5 * likingOpposite;
          break;
        }
        case Diplomacy.LIKE: {
          scores[i] = scores[i] - 10 * likingOpposite;
          break;
        }
        case Diplomacy.FRIENDS: {
          scores[i] = scores[i] - 30 * likingOpposite;
          break;
        }
      }
      // Subtract possible caught chance adjusted by risk factor
      scores[i] = scores[i] - (caught / riskFactor);
      // Scores should not go above success rate.
      if (scores[i] > success) {
        scores[i] = success;
      }
      if (scores[i] < 0) {
        scores[i] = 0;
      }
      sum = sum + scores[i];
    }
    if (sum > 0) {
      int rand = DiceGenerator.getRandom(sum - 1);
      int total = 0;
      for (int i = 0; i < scores.length; i++) {
        if (rand < total + scores[i]) {
          return allowedTypes[i];
        }
        total = total + scores[i];
      }
    }
    // Just making sure that null is never returned.
    EspionageMission selectedType = allowedTypes[DiceGenerator.getRandom(
        allowedTypes.length - 1)];
    return selectedType;
  }

  /**
   * Handle caughtespionage mission.
   * @param type EspionageMission type
   * @param planet Planet where to do espionage
   * @param fleet Fleet who is doing the espionage
   * @param info Realm who is doing the espionage
   * @param game Full game information.
   * @param espionageSucceed If true then espionage mission succeed, but now
   *        leader will also get caught.
   */
  private static void handleCaughtEspionage(final EspionageMission type,
      final Planet planet, final Fleet fleet, final PlayerInfo info,
      final Game game, final boolean espionageSucceed) {
    int infoIndex = game.getStarMap().getPlayerList().getIndex(info);
    boolean war = planet.getPlanetPlayerInfo().getDiplomacy().isWar(infoIndex);
    boolean tradeWar = planet.getPlanetPlayerInfo().getDiplomacy()
        .isTradeEmbargo(infoIndex);
    if (type == EspionageMission.GAIN_TRUST) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
        LeaderUtility.handleLeaderReleased(info, planet, fleet,
            fleet.getCommander().getCallName() + " from "
                + info.getEmpireName() + " caught by "
                + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
                + " espionage mission. Since it espionage mission was gaining"
                + " trust " + fleet.getCommander().getCallName()
                + " was released.", game.getStarMap());
      }
    }
    if (type == EspionageMission.STEAL_CREDIT) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || attitude == Attitude.MERCHANTICAL
          || war || tradeWar) {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was steal credits. ";
        String endText = "";
        if (war) {
          endText = " Execution was done because of war times.";
        } else if (tradeWar) {
          endText = " Execution was done because of revenge of trade war.";
        }
        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " execution by using massive amount of credits. ",
            startText
            + fleet.getCommander().getCallName() + " was executed by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + "." + endText, game);
      } else {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was steal credits. ";
        LeaderUtility.handleLeaderPrison(info, planet, fleet, startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " prison time by using massive amount of credits. ",
            startText
            + planet.getPlanetPlayerInfo().getEmpireName() + " decided to"
            + " prison " + fleet.getCommander().getCallName()
            + " because of stealing.", "Steal credits", 5, game);
      }
    }
    if (type == EspionageMission.STEAL_TECH) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || attitude == Attitude.SCIENTIFIC
          || war || tradeWar) {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was steal technology. ";
        String endText = "";
        if (war) {
          endText = " Execution was done because of war times.";
        } else if (tradeWar) {
          endText = " Execution was done because of revenge of trade war.";
        }
        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " execution by using massive amount of credits. ",
            startText
            + fleet.getCommander().getCallName() + " was executed by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + "." + endText, game);
      } else {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was steal technology. ";
        LeaderUtility.handleLeaderPrison(info, planet, fleet, startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " prison time by using massive amount of credits. ",
            startText
            + planet.getPlanetPlayerInfo().getEmpireName() + " decided to"
            + " prison " + fleet.getCommander().getCallName()
            + " because of stealing technology.", "Steal technology", 10,
            game);
      }
    }
    if (type == EspionageMission.SABOTAGE) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || war || tradeWar) {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was sabotage. ";
        String endText = "";
        if (war) {
          endText = " Execution was done because of war times.";
        } else if (tradeWar) {
          endText = " Execution was done because of revenge of trade war.";
        }
        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " execution by using massive amount of credits.",
            startText
            + fleet.getCommander().getCallName() + " was executed by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + "." + endText, game);
      } else {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was sabotage. ";
        LeaderUtility.handleLeaderPrison(info, planet, fleet, startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " prison time by using massive amount of credits. ",
            startText
            + planet.getPlanetPlayerInfo().getEmpireName() + " decided to"
            + " prison " + fleet.getCommander().getCallName()
            + " because of sabotage.", "Sabotage", 8, game);
      }
    }
    if (type == EspionageMission.DEMOLISH_BUILDING) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || war || tradeWar) {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was demolish building. ";
        String endText = "";
        if (war) {
          endText = " Execution was done because of war times.";
        } else if (tradeWar) {
          endText = " Execution was done because of revenge of trade war.";
        }
        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " execution by using massive amount of credits. ",
            startText
            + fleet.getCommander().getCallName() + " was executed by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + "." + endText, game);
      } else {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was demolish building ";
        LeaderUtility.handleLeaderPrison(info, planet, fleet, startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " prison time by using massive amount of credits. ",
            startText
            + planet.getPlanetPlayerInfo().getEmpireName() + " decided to"
            + " prison " + fleet.getCommander().getCallName()
            + " because of demolish building.", "Demolish building", 10, game);
      }
    }
    if (type == EspionageMission.ASSASSIN_GOVERNOR) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
      if (attitude == Attitude.PEACEFUL
          && !war && !tradeWar) {
        LeaderUtility.handleLeaderReleased(info, planet, fleet,
            fleet.getCommander().getCallName() + " from "
                + info.getEmpireName() + " caught by "
                + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
                + " espionage mission. Main goal was assassin governor. "
                + planet.getPlanetPlayerInfo().getEmpireName() + " decided to"
                + " release " + fleet.getCommander().getCallName()
                + ".", game.getStarMap());
      } else {
        String startText = fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " caught by "
            + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
            + " espionage mission. Main goal was assassin governor. ";
        String endText = "";
        if (war) {
          endText = " Execution was done because of war times.";
        } else if (tradeWar) {
          endText = " Execution was done because of revenge of trade war.";
        }
        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText
            + fleet.getCommander().getCallName() + " was able to escape"
            + " from " + planet.getPlanetPlayerInfo().getEmpireName()
            + " execution by using massive amount of credits.",
            startText
            + fleet.getCommander().getCallName() + " was executed by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + "." + endText, game);
      }
    }
    if (type == EspionageMission.TERRORIST_ATTACK) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      String startText = fleet.getCommander().getCallName() + " from "
          + info.getEmpireName() + " caught by "
          + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
          + " espionage mission. Main goal was terrorist attack on planet. ";
      String endText = "";
      if (war) {
        endText = " Execution was done because of war times.";
      } else if (tradeWar) {
        endText = " Execution was done because of revenge of trade war.";
      }
      LeaderUtility.handleLeaderKilled(info, planet, fleet,
          startText
          + fleet.getCommander().getCallName() + " was able to escape"
          + " from " + planet.getPlanetPlayerInfo().getEmpireName()
          + " execution by using massive amount of credits.",
          startText
          + fleet.getCommander().getCallName() + " was executed by "
          + planet.getPlanetPlayerInfo().getEmpireName()
          + "." + endText, game);
      if (!info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
        DiplomaticTrade trade = new DiplomaticTrade(game.getStarMap(),
            game.getPlayers().getIndex(info),
            game.getPlayers().getIndex(planet.getPlanetPlayerInfo()));
        trade.generateEqualTrade(NegotiationType.WAR);
        boolean casusBelli = info.getDiplomacy().hasCasusBelli(
            game.getPlayers().getIndex(planet.getPlanetPlayerInfo()));
        StarMapUtilities.addWarDeclatingReputation(game.getStarMap(), info,
            planet.getPlanetPlayerInfo());
        NewsData newsData = NewsFactory.makeWarNews(info,
            planet.getPlanetPlayerInfo(), planet, game.getStarMap(),
            casusBelli);
        game.getStarMap().getNewsCorpData().addNews(newsData);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeDiplomaticEvent(planet, newsData));
        trade.doTrades();
        PlayerInfo defender = planet.getPlanetPlayerInfo();
        String[] list = defender.getDiplomacy().activateDefensivePact(
            game.getStarMap(), info);
        if (list != null) {
          game.getStarMap().getNewsCorpData().addNews(
              NewsFactory.makeDefensiveActivation(info, list));
        }
      }
    }
    if (type == EspionageMission.FALSE_FLAG) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      Ship ship = null;
      String startText = fleet.getCommander().getCallName() + " from "
          + info.getEmpireName() + " caught by "
          + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
          + " espionage mission. Main goal was false flag mission on planet.";
      if (!espionageSucceed) {
        ship = fleet.getShipForFalseFlag();
        startText = startText + " " + ship.getName()
          + " was destroyed during mission.";
      }
      startText = startText + " Eventually "
          + fleet.getCommander().getCallName() + " was caught by "
          + planet.getPlanetPlayerInfo().getEmpireName() + ". ";
      String endText = "";
      int index = game.getStarMap().getPlayerList().getIndex(info);
      boolean casusBelli = planet.getPlanetPlayerInfo().getDiplomacy()
          .hasCasusBelli(index);
      if (casusBelli) {
        endText = " Execution was done because of spy operation"
            + " was considered as act of war.";
      } else if (tradeWar) {
        endText = " Execution was done because of revenge of trade war.";
      } else {
        endText = " Execution was done because of acts of conspiracy.";
      }
      LeaderUtility.handleLeaderKilled(info, planet, fleet,
          startText
          + fleet.getCommander().getCallName() + " was able to escape"
          + " from " + planet.getPlanetPlayerInfo().getEmpireName()
          + " execution by using massive amount of credits.",
          startText
          + fleet.getCommander().getCallName() + " was executed by "
          + planet.getPlanetPlayerInfo().getEmpireName()
          + "." + endText, game);
      if (casusBelli) {
        // War will be made if defender has casus belli against attack
        // Attacker will any way declare it, so that reputation
        // drop goes to attacker.
        DiplomaticTrade trade = new DiplomaticTrade(game.getStarMap(),
            game.getPlayers().getIndex(info),
            game.getPlayers().getIndex(planet.getPlanetPlayerInfo()));
        casusBelli = info.getDiplomacy().hasCasusBelli(
            game.getPlayers().getIndex(planet.getPlanetPlayerInfo()));
        trade.generateEqualTrade(NegotiationType.WAR);
          game.getPlayers().getIndex(planet.getPlanetPlayerInfo());
        StarMapUtilities.addWarDeclatingReputation(game.getStarMap(), info,
            planet.getPlanetPlayerInfo());
        NewsData newsData = NewsFactory.makeWarNews(info,
            planet.getPlanetPlayerInfo(), planet, game.getStarMap(),
            casusBelli);
        game.getStarMap().getNewsCorpData().addNews(newsData);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
          planet, newsData));
        trade.doTrades();
        PlayerInfo defender = planet.getPlanetPlayerInfo();
        String[] list = defender.getDiplomacy().activateDefensivePact(
            game.getStarMap(), info);
        if (list != null) {
          game.getStarMap().getNewsCorpData().addNews(
              NewsFactory.makeDefensiveActivation(info, list));
        }
      }
      if (ship != null) {
        fleet.removeShip(ship);
        info.getFleets().recalculateList();
      }
      if (fleet.getCommander() != null && fleet.getNumberOfShip() == 0) {
        // Fleet's last ship was destroyed but commander
        // escaped from execution.
        fleet.getCommander().setJob(Job.UNASSIGNED);
      }
    }
    if (type == EspionageMission.DEADLY_VIRUS) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
            planet.getPlanetPlayerInfo().getRace());
      }
      String startText = fleet.getCommander().getCallName() + " from "
          + info.getEmpireName() + " caught by "
          + planet.getPlanetPlayerInfo().getEmpireName() + " while doing"
          + " espionage mission. Main goal was spreading virus on planet. ";
      String endText = "";
      if (war) {
        endText = " Execution was done because of war times.";
      } else if (tradeWar) {
        endText = " Execution was done because of revenge of trade war.";
      } else {
        endText = " Spreading deadly virus was considered as attack against "
            + planet.getPlanetPlayerInfo().getEmpireName() + ".";
      }
      LeaderUtility.handleLeaderKilled(info, planet, fleet,
          startText
          + fleet.getCommander().getCallName() + " was able to escape"
          + " from " + planet.getPlanetPlayerInfo().getEmpireName()
          + " execution by using massive amount of credits.",
          startText
          + fleet.getCommander().getCallName() + " was executed by "
          + planet.getPlanetPlayerInfo().getEmpireName()
          + "." + endText, game);
      if (!info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
        DiplomaticTrade trade = new DiplomaticTrade(game.getStarMap(),
            game.getPlayers().getIndex(info),
            game.getPlayers().getIndex(planet.getPlanetPlayerInfo()));
        trade.generateEqualTrade(NegotiationType.WAR);
        boolean casusBelli = info.getDiplomacy().hasCasusBelli(
            game.getPlayers().getIndex(planet.getPlanetPlayerInfo()));
        StarMapUtilities.addWarDeclatingReputation(game.getStarMap(), info,
            planet.getPlanetPlayerInfo());
        NewsData newsData = NewsFactory.makeWarNews(info,
            planet.getPlanetPlayerInfo(), planet, game.getStarMap(),
            casusBelli);
        game.getStarMap().getNewsCorpData().addNews(newsData);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeDiplomaticEvent(planet, newsData));
        trade.doTrades();
        PlayerInfo defender = planet.getPlanetPlayerInfo();
        String[] list = defender.getDiplomacy().activateDefensivePact(
            game.getStarMap(), info);
        if (list != null) {
          game.getStarMap().getNewsCorpData().addNews(
              NewsFactory.makeDefensiveActivation(info, list));
        }
      }
    }

  }

  /**
   * Handle successful espionage mission.
   * @param type EspionageMission type
   * @param planet Planet where to do espionage
   * @param fleet Fleet who is doing the espionage
   * @param info Realm who is doing the espionage
   * @param game Full game information.
   */
  private static void handleSuccessfulEspionage(final EspionageMission type,
      final Planet planet, final Fleet fleet, final PlayerInfo info,
      final Game game) {
    int infoIndex = game.getStarMap().getPlayerList().getIndex(info);
    if (type == EspionageMission.GAIN_TRUST) {
      DiplomacyBonusList diplomacy = planet.getPlanetPlayerInfo()
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
            planet.getPlanetPlayerInfo().getRace());
        Message msg = new Message(MessageType.LEADER,
            fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " has gained trust against "
            + planet.getPlanetPlayerInfo().getEmpireName() + ". This was "
            + "gained via espionage mission.",
            Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(fleet.getCommander().getName());
        info.getMsgList().addUpcomingMessage(msg);
        fleet.getCommander().setExperience(
            fleet.getCommander().getExperience() + type.getExperienceReward());
        game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
            fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      }
    }
    if (type == EspionageMission.STEAL_CREDIT) {
      int totalCredits = planet.getPlanetPlayerInfo().getTotalCredits();
      if (totalCredits > 0) {
        int stolen = totalCredits / 10;
        // Planet full value affects how much credit is stolen.
        int value = planet.getFullLevel();
        stolen = stolen * 100 / value;
        if (stolen == 0) {
          stolen = 1;
        }
        planet.getPlanetPlayerInfo().setTotalCredits(totalCredits - stolen);
        info.setTotalCredits(info.getTotalCredits() + stolen);
        Message msg = new Message(MessageType.LEADER,
            fleet.getCommander().getCallName()  + " from "
            + info.getEmpireName() + " has stolen " + stolen
            + " credits from " + planet.getPlanetPlayerInfo().getEmpireName()
            + ". This was gained via espionage mission.",
            Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(fleet.getCommander().getName());
        info.getMsgList().addUpcomingMessage(msg);
        fleet.getCommander().setExperience(
            fleet.getCommander().getExperience()
            + type.getExperienceReward());
        game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
            fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      }
    }
    if (type == EspionageMission.STEAL_TECH) {
      Tech[] stealableTechs = EspionageUtility.getStealableTech(planet, info);
      if (stealableTechs.length > 0) {
        int index = DiceGenerator.getRandom(0, stealableTechs.length - 1);
        Tech tech = stealableTechs[index];
        info.getTechList().addTech(stealableTechs[index]);
        Message msg = new Message(MessageType.LEADER,
            fleet.getCommander().getCallName() + " from "
              + info.getEmpireName() + " has stolen "
              + tech.getName() + " technology from "
              + planet.getPlanetPlayerInfo().getEmpireName()
              + ". This was gained via espionage mission.",
              Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(fleet.getCommander().getName());
        info.getMsgList().addUpcomingMessage(msg);
        fleet.getCommander().setExperience(
            fleet.getCommander().getExperience() + type.getExperienceReward());
        game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
            fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      }
    }
    if (type == EspionageMission.SABOTAGE) {
      planet.setProdResource(planet.getProdResource() / 2);
      Message msg = new Message(MessageType.LEADER,
          fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " sabotage "
            + planet.getUnderConstruction().getName() + " project "
            + " at planet " + planet.getName() + ". Planet is owned by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + ". This was done via espionage mission.",
            Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      fleet.getCommander().setExperience(
          fleet.getCommander().getExperience() + type.getExperienceReward());
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    }
    if (type == EspionageMission.DEMOLISH_BUILDING) {
      int index = DiceGenerator.getRandom(0,
          planet.getBuildingList().length - 1);
      Building building = planet.getBuildingList()[index];
      planet.removeBuilding(building);
      Message msg = new Message(MessageType.LEADER,
          fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " demolish "
            + building.getName() + " at planet "
            + planet.getName() + ". Planet is owned by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + ". This was done via espionage mission.",
            Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      NewsData news = NewsFactory.makeBuildingDestroyedNews(planet, building,
          "");
      game.getStarMap().getNewsCorpData().addNews(news);
      fleet.getCommander().setExperience(
          fleet.getCommander().getExperience() + type.getExperienceReward());
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    }
    if (type == EspionageMission.TERRORIST_ATTACK) {
      int index = DiceGenerator.getRandom(0,
          planet.getBuildingList().length - 1);
      Building building = planet.getBuildingList()[index];
      planet.removeBuilding(building);
      String killedTxt = "";
      if (planet.getTotalPopulation() > 1) {
        planet.killOneWorker();
        killedTxt = "Also population was killed during terrorist attack. ";
      }
      Message msg = new Message(MessageType.LEADER,
          fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " destroys "
            + building.getName() + " at planet "
            + planet.getName() + ". " + killedTxt + "Planet is owned by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + ". This was done via espionage mission.",
            Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      NewsData news = NewsFactory.makeBuildingDestroyedNews(planet, building,
          killedTxt);
      game.getStarMap().getNewsCorpData().addNews(news);
      fleet.getCommander().setExperience(
          fleet.getCommander().getExperience() + type.getExperienceReward());
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    }
    if (type == EspionageMission.ASSASSIN_GOVERNOR) {
      Leader governor = planet.getGovernor();
      if (governor.hasPerk(Perk.WEALTHY)) {
        governor.useWealth();
        Message msg = new Message(MessageType.LEADER,
            fleet.getCommander().getCallName() + " from "
              + info.getEmpireName() + " tried to assasins "
              + governor.getCallName() + " at planet " + planet.getName()
              + " but failed. Planet is owned by "
              + planet.getPlanetPlayerInfo().getEmpireName()
              + ". This was done via espionage mission.",
              Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(fleet.getCommander().getName());
        info.getMsgList().addUpcomingMessage(msg);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
            fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
        msg = new Message(MessageType.LEADER,
            governor.getCallName() + " was tried to kill "
              + " at planet " + planet.getName()
              + ". Governor's expensive protection gear saved "
              + governor.getGender().getHisHer() + " life.",
              Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(governor.getName());
        planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      } else {
        planet.setGovernor(null);
        governor.setJob(Job.DEAD);
        fleet.getCommander().getStats().addOne(StatType.KILLED_ANOTHER_LEADER);
        Message msg = new Message(MessageType.LEADER,
            fleet.getCommander().getCallName() + " from "
              + info.getEmpireName() + " assasins "
              + governor.getCallName() + " at planet " + planet.getName()
              + ". Planet is owned by "
              + planet.getPlanetPlayerInfo().getEmpireName()
              + ". This was done via espionage mission.",
              Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(fleet.getCommander().getName());
        info.getMsgList().addUpcomingMessage(msg);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
            fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
        msg = new Message(MessageType.LEADER,
            governor.getCallName() + " is killed "
              + " at planet " + planet.getName()
              + ". This was probably assasination.",
              Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(governor.getName());
        planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
        NewsData news = NewsFactory.makeLeaderDies(governor,
            planet.getPlanetPlayerInfo(), "assasination");
        game.getStarMap().getNewsCorpData().addNews(news);
        fleet.getCommander().setExperience(
            fleet.getCommander().getExperience() + type.getExperienceReward());
      }
    }
    if (type == EspionageMission.FALSE_FLAG) {
      DiplomacyBonusList diplomacy = info.getDiplomacy().getDiplomacyList(
          planet.getPlanetOwnerIndex());
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.FALSE_FLAG, info.getRace());
      }
      Ship ship = fleet.getShipForFalseFlag();
      Message msg = new Message(MessageType.LEADER,
          fleet.getCommander().getCallName() + " from "
          + info.getEmpireName() + " makes successfull false flag"
          + " operation against "
          + planet.getPlanetPlayerInfo().getEmpireName() + ". Ship called "
          + ship.getName() + " was destroyed during the mission."
          + " This mission happened next to " + planet.getName() + ".",
          Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
      msg = new Message(MessageType.PLANETARY, ship.getName()
          + " was destroyed from " + info.getEmpireName() + " fleet. "
          + info.getEmpireName() + " claim that "
          + planet.getPlanetPlayerInfo().getEmpireName()
          + " destroyed it. This event happened next to "
          + planet.getName() + ". ", Icons.getIconByName(Icons.ICON_DEATH));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(planet.getName());
      planet.getPlanetPlayerInfo().getMsgList().addUpcomingMessage(msg);
      fleet.removeShip(ship);
      String location = "escaped to another ship";
      Leader leader = fleet.getCommander();
      if (fleet.getCommander() != null && fleet.getNumberOfShip() == 0) {
        // Fleet's last ship was destroyed so commander escaped to
        // friendly planet
        fleet.getCommander().setJob(Job.UNASSIGNED);
        location = "escaped to closest friendly planet";
      }
      NewsData news = NewsFactory.makeLeaderFalseFlag(leader, info,
          planet.getPlanetPlayerInfo(), location);
      game.getStarMap().getNewsCorpData().addNews(news);
      leader.setExperience(
          leader.getExperience() + type.getExperienceReward());
      info.getFleets().recalculateList();
    }
    if (type == EspionageMission.DEADLY_VIRUS) {
      Message msg = new Message(MessageType.LEADER,
          fleet.getCommander().getCallName() + " from "
            + info.getEmpireName() + " spreads "
            + " deadly virus on " + planet.getName() + ". "
            + "Almost every population of planet is being killed."
            + " Planet is owned by "
            + planet.getPlanetPlayerInfo().getEmpireName()
            + ". This was done via espionage mission.",
            Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(fleet.getCommander().getName());
      info.getMsgList().addUpcomingMessage(msg);
      StarMapUtilities.spreadDeadlyVirus(info, planet);
      game.getStarMap().getNewsCorpData().addNews(
          NewsFactory.makeDeadlyVirusNews(planet, info));
      fleet.getCommander().setExperience(
          fleet.getCommander().getExperience() + type.getExperienceReward());
      game.getStarMap().getHistory().addEvent(NewsFactory.makeLeaderEvent(
          fleet.getCommander(), info, game.getStarMap(), msg.getMessage()));
    }
  }

  /**
   * Find scout ship for mission. Possible mission types are
   * exploring and diplomatic delegacy.
   * @param info PlayerInfo whose ships are being searched
   * @param mission Mission where ship is going to be assinged.
   */
  public static void findScoutShipForMission(final PlayerInfo info,
      final Mission mission) {
    for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
      Fleet fleet = info.getFleets().getByIndex(j);
      Mission fleetMission = info.getMissions().getMissionForFleet(
          fleet.getName());
      if (fleetMission == null
          || mission.getType() == MissionType.DIPLOMATIC_DELEGACY
             && fleetMission.getType() == MissionType.MOVE
             && fleetMission.getMissionTime() > 4) {
        for (Ship ship : fleet.getShips()) {
          Fleet newFleet = null;
          if (ship.isScoutShip()) {
            if (fleet.getNumberOfShip() > 1) {
              fleet.removeShip(ship);
              newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
            } else {
              newFleet = fleet;
              if (fleetMission != null
                  && mission.getType() == MissionType.DIPLOMATIC_DELEGACY
                  && fleetMission.getType() == MissionType.MOVE) {
                info.getMissions().remove(fleetMission);
              }
            }
          }
          if (newFleet != null) {
            if (mission.getType() == MissionType.EXPLORE) {
              newFleet.setName(info.getFleets()
                  .generateUniqueName("Scout"));
              mission.setFleetName(newFleet.getName());
              mission.setPhase(MissionPhase.LOADING);
            }
            if (mission.getType() == MissionType.DIPLOMATIC_DELEGACY) {
              newFleet.setName(info.getFleets()
                  .generateUniqueName(newFleet.getFirstShip().getName()));
              mission.setFleetName(newFleet.getName());
              mission.setPhase(MissionPhase.LOADING);
            }
          }
        }
      }
    }
  }
  /**
   * Find ship for gathering mission
   * @param mission Gathering mission
   * @param info Player whose fleets are searched
   */
  public static void findGatheringShip(final Mission mission,
      final PlayerInfo info) {
    if (!mission.getShipType().isEmpty()) {
      for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
        Fleet fleet = info.getFleets().getByIndex(j);
        if (info.getMissions().getMissionForFleet(fleet.getName()) == null) {
          for (Ship ship : fleet.getShips()) {
            Fleet newFleet = null;
            if (mission.getShipType().equals(Mission.ASSAULT_TYPE)
                && ship.getTotalMilitaryPower() > 0 && !ship.isStarBase()) {
              if (fleet.getNumberOfShip() > 1) {
                fleet.removeShip(ship);
                newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
              } else {
                newFleet = fleet;
              }
            }
            if (mission.getShipType().equals(Mission.BOMBER_TYPE)
                && ship.hasBombs()) {
              if (fleet.getNumberOfShip() > 1) {
                fleet.removeShip(ship);
                newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
              } else {
                newFleet = fleet;
              }
            }
            if (mission.getShipType().equals(Mission.TROOPER_TYPE)
                && ship.isTrooperShip()) {
              if (fleet.getNumberOfShip() > 1) {
                fleet.removeShip(ship);
                newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
              } else {
                newFleet = fleet;
              }
            }
            if (newFleet != null) {
              String fleetName;
              for (int k = 0; k < info.getFleets().getNumberOfFleets(); k++) {
                fleetName = "Gather " + mission.getShipType() + " #" + k;
                if (info.getFleets().isUniqueName(fleetName, newFleet)) {
                  newFleet.setName(fleetName);
                  mission.setFleetName(fleetName);
                  mission.setPhase(MissionPhase.TREKKING);
                  // Found correct ship from fleet
                  return;
                }
              }
            }

          }
        }
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
    if (fleet.isScoutFleet() || fleet.isColonyFleet()
        || fleet.isStarBaseDeployed()) {
      // Do not merge scout fleets or colony fleets or deployed starbase
      // Starbases are merged while deploying.
      return;
    }
    for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
      // Merge fleets in same space with same starting of fleet name
      Fleet mergeFleet = info.getFleets().getByIndex(j);
      if (mergeFleet != fleet && mergeFleet.getX() == fleet.getX()
          && mergeFleet.getY() == fleet.getY()
          && mergeFleet.getName().startsWith(part[0])
          && fleet.getNumberOfShip() + mergeFleet.getNumberOfShip()
          <= Fleet.MAX_FLEET_SIZE) {
        for (int k = 0; k < mergeFleet.getNumberOfShip(); k++) {
          Ship ship = mergeFleet.getShipByIndex(k);
          if (ship != null) {
            fleet.addShip(ship);
          }
        }
        info.getMissions().deleteMissionForFleet(mergeFleet.getName());
        info.getFleets().remove(j);
        break;
      }
    }
  }

  /**
   * Adds possible tutorial text for player in index 0.
   * @param game Game class
   * @param meetingPlace can be planet or fleet where meeting happened
   * @param tutorialIndex Tutorial index
   */
  public static void addPossibleTutorial(final Game game,
      final Object meetingPlace, final int tutorialIndex) {
    if (Game.getTutorial() != null && game.getStarMap().isTutorialEnabled()) {
      String tutorialText = Game.getTutorial().showTutorialText(tutorialIndex);
      if (tutorialText != null) {
        Message msg = new Message(MessageType.INFORMATION, tutorialText,
            Icons.getIconByName(Icons.ICON_TUTORIAL));
        if (meetingPlace instanceof Planet) {
          Planet planet = (Planet) meetingPlace;
          if (game.getStarMap().getPlayerByIndex(0).getSectorVisibility(
              planet.getCoordinate()) == PlayerInfo.VISIBLE) {
            msg.setCoordinate(planet.getCoordinate());
          } else {
            Coordinate center = new Coordinate(game.getStarMap().getMaxX() / 2,
                game.getStarMap().getMaxY() / 2);
            msg.setCoordinate(center);
          }
        }
        if (meetingPlace instanceof Fleet) {
          Fleet fleet = (Fleet) meetingPlace;
          if (game.getStarMap().getPlayerByIndex(0).getSectorVisibility(
              fleet.getCoordinate()) == PlayerInfo.VISIBLE) {
            msg.setCoordinate(fleet.getCoordinate());
          } else {
            Coordinate center = new Coordinate(game.getStarMap().getMaxX() / 2,
                game.getStarMap().getMaxY() / 2);
            msg.setCoordinate(center);
          }
        }
        game.getPlayers().getPlayerInfoByIndex(0).getMsgList()
            .addNewMessage(msg);
      }
    }
  }
  /**
   * Handle diplomacy between two AI players
   * @param game Game class
   * @param info First player as PlayerInfo
   * @param secondIndex Second player as a index
   * @param fleet Fleet which crossed then border
   * @param meetingPlace can be planet or fleet where meeting happened
   */
  public static void handleDiplomacyBetweenAis(final Game game,
      final PlayerInfo info, final int secondIndex, final Fleet fleet,
      final Object meetingPlace) {
    // For Ai players make offer
    int index = game.getStarMap().getPlayerList().getIndex(info);
    DiplomaticTrade trade = new DiplomaticTrade(game.getStarMap(),
        index, secondIndex);
    if (fleet == null) {
      trade.generateOffer();
    } else {
      trade.generateRecallFleetOffer(fleet);
    }
    if (trade.getFirstOffer() != null
      && trade.getFirstOffer().isTypeInOffer(NegotiationType.WAR)
      && info.getDiplomacy().isWar(secondIndex)) {
      // These two already has a war so just stopping here
      return;
    }
    if (trade.isOfferGoodForBoth()
        || trade.getFirstOffer().isTypeInOffer(NegotiationType.WAR)) {
      // Another party accepts it or it is war
      trade.doTrades();
      if (trade.getFirstOffer().isTypeInOffer(NegotiationType.WAR)) {
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        boolean casusBelli = info.getDiplomacy().hasCasusBelli(secondIndex);
        StarMapUtilities.addWarDeclatingReputation(game.getStarMap(), info,
            defender);
        NewsData newsData = NewsFactory.makeWarNews(info, defender, fleet,
            game.getStarMap(), casusBelli);
        game.getStarMap().getNewsCorpData().addNews(newsData);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
            meetingPlace, newsData));
        String[] defenseList = defender.getDiplomacy().activateDefensivePact(
            game.getStarMap(), info);
        if (defenseList != null) {
          game.getStarMap().getNewsCorpData().addNews(
              NewsFactory.makeDefensiveActivation(info, defenseList));
        }
        addPossibleTutorial(game, meetingPlace, 105);
      }
      if (trade.getFirstOffer().isTypeInOffer(NegotiationType.ALLIANCE)) {
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        NewsData newsData = NewsFactory.makeAllianceNews(info, defender,
            fleet);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeDiplomaticEvent(meetingPlace, newsData));
        game.getStarMap().getNewsCorpData().addNews(newsData);
        addPossibleTutorial(game, meetingPlace, 103);
      }
      if (trade.getFirstOffer().isTypeInOffer(NegotiationType.DEFENSIVE_PACT)) {
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        NewsData newsData = NewsFactory.makeDefensivePactNews(info, defender,
            fleet);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeDiplomaticEvent(meetingPlace, newsData));
        game.getStarMap().getNewsCorpData().addNews(newsData);
        addPossibleTutorial(game, meetingPlace, 102);
      }
      if (trade.getFirstOffer().isTypeInOffer(NegotiationType.TRADE_ALLIANCE)) {
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        NewsData newsData = NewsFactory.makeTradeAllianceNews(info, defender,
            fleet);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeDiplomaticEvent(meetingPlace, newsData));
        game.getStarMap().getNewsCorpData().addNews(newsData);
        addPossibleTutorial(game, meetingPlace, 101);
      }
      if (trade.getFirstOffer().isTypeInOffer(NegotiationType.PEACE)) {
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        NewsData newsData = NewsFactory.makePeaceNews(info, defender,
            fleet);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
            meetingPlace, newsData));
        game.getStarMap().getNewsCorpData().addNews(newsData);
        info.getMissions().removeAttackAgainstPlayer(defender,
            game.getStarMap());
        defender.getMissions().removeAttackAgainstPlayer(info,
            game.getStarMap());
        addPossibleTutorial(game, meetingPlace, 100);
      }
      if (trade.getFirstOffer().isTypeInOffer(NegotiationType.TRADE_EMBARGO)) {
        NegotiationOffer offer = trade.getFirstOffer().getEmbargoOffer();
        PlayerInfo realm = offer.getRealm();
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        StarMapUtilities.addEmbargoReputation(game.getStarMap(), info,
            defender, realm);
        NewsData newsData = NewsFactory.makeTradeEmbargoNews(info, defender,
            realm, meetingPlace);
        game.getStarMap().getNewsCorpData().addNews(newsData);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
            meetingPlace, newsData));
        addPossibleTutorial(game, meetingPlace, 104);
      }
    } else {
      SpeechType type = trade.getSpeechTypeByOffer();
      Attitude attitude = info.getAiAttitude();
      int liking = info.getDiplomacy().getLiking(secondIndex);
      boolean casusBelli = info.getDiplomacy().hasCasusBelli(secondIndex);
      int warChance = DiplomaticTrade.getWarChanceForDecline(type, attitude,
          liking, casusBelli);
      if (info.getDiplomacy().getNumberOfWar() == 0
          && info.getGovernment().hasWarHappiness()) {
        // War monger governments like war
        warChance = warChance + 20;
      }
      int totalWarFatigue = info.getTotalWarFatigue();
      if (totalWarFatigue < -3) {
        warChance = warChance - 30;
      }
      if (totalWarFatigue < -2) {
        warChance = warChance - 20;
      }
      if (totalWarFatigue < -1) {
        warChance = warChance - 10;
      }
      if (totalWarFatigue < 0) {
        warChance = warChance - 5;
      }
      int value = DiceGenerator.getRandom(99);
      if (value < warChance && !info.getDiplomacy().isWar(secondIndex)) {
        trade.generateEqualTrade(NegotiationType.WAR);
        trade.doTrades();
        PlayerInfo defender = game.getStarMap().getPlayerByIndex(secondIndex);
        StarMapUtilities.addWarDeclatingReputation(game.getStarMap(), info,
            defender);
        NewsData newsData = NewsFactory.makeWarNews(info, defender, fleet,
            game.getStarMap(), casusBelli);
        game.getStarMap().getNewsCorpData().addNews(newsData);
        game.getStarMap().getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
            meetingPlace, newsData));
        String[] defenseList = defender.getDiplomacy().activateDefensivePact(
            game.getStarMap(), info);
        if (defenseList != null) {
          game.getStarMap().getNewsCorpData().addNews(
              NewsFactory.makeDefensiveActivation(info, defenseList));
        }
        addPossibleTutorial(game, meetingPlace, 105);
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
      if (info == infoAtTarget) {
        fleetAtTarget = null;
      }
      war = map.isWarBetween(info, infoAtTarget);
      if (fleet.isPrivateerFleet()) {
        war = true;
      }
    }
    if (war || fleetAtTarget == null) {
      // Not blocked so fleet is moving
      game.fleetMakeMove(info, fleet, point.getX(), point.getY());
      search.nextMove();
    } else {
      fleet.setMovesLeft(0);
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      if (infoAtTarget != null) {
        int index = map.getPlayerList().getIndex(infoAtTarget);
        boolean nothingToTrade = info.getDiplomacy().getDiplomacyList(index)
            .isBonusType(DiplomacyBonusType.NOTHING_TO_TRADE);
        if (infoAtTarget.isHuman()) {
          if (!nothingToTrade) {
            SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
            game.changeGameState(GameState.DIPLOMACY_VIEW, info);
          }
        } else if (!info.isHuman()) {
          Planet planet = map.getPlanetByCoordinate(point.getX(),
              point.getY());
          if (planet != null && planet.getPlanetOwnerIndex() == index) {
            handleDiplomacyBetweenAis(game, info, index, null, planet);
          } else {
            handleDiplomacyBetweenAis(game, info, index, null, fleet);
          }
        } else {
          Message msg = new Message(MessageType.FLEET,
              "Fleet encounter another fleet while moving in FTL!",
              Icons.getIconByName(Icons.ICON_HULL_TECH));
          msg.setCoordinate(fleet.getCoordinate());
          info.getMsgList().addNewMessage(msg);
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
    if (fleetAtTarget != null) {
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      if (info == infoAtTarget) {
        fleetAtTarget = null;
      }
    }
    if (fleetAtTarget == null) {
      // Not blocked so fleet is moving
      game.fleetMakeMove(info, fleet, nx, ny);
    } else {
      fleet.setMovesLeft(0);
      // Making reroute
      fleet.setRoute(null);
      PlayerInfo infoAtTarget = map.getPlayerInfoByFleet(fleetAtTarget);
      if (infoAtTarget != null) {
        int index = map.getPlayerList().getIndex(infoAtTarget);
        if (infoAtTarget.isHuman()) {
          Message msg = new Message(MessageType.FLEET,
              "Fleet " + fleetAtTarget.getName() + " evaded bumping in FTL with"
                  + " fleet " + fleet.getName() + "!",
              Icons.getIconByName(Icons.ICON_HULL_TECH));
          msg.setCoordinate(fleetAtTarget.getCoordinate());
          infoAtTarget.getMsgList().addNewMessage(msg);
        } else if (!info.isHuman() && !infoAtTarget.isBoard()
            && !info.isBoard()) {
          Planet planet = map.getPlanetByCoordinate(nx, ny);
          if (planet != null && planet.getPlanetOwnerIndex() == index) {
            handleDiplomacyBetweenAis(game, info, index, null, planet);
          } else {
            handleDiplomacyBetweenAis(game, info, index, null, fleet);
          }
        } else if (info.getMissions().getMissionForFleet(fleet.getName())
            == null) {
          Message msg = new Message(MessageType.FLEET,
              "Fleet encounter another fleet while moving in FTL!",
              Icons.getIconByName(Icons.ICON_HULL_TECH));
          msg.setCoordinate(fleet.getCoordinate());
          info.getMsgList().addNewMessage(msg);
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
    if (search != null) {
      int movesLeft = fleet.getMovesLeft();
      for (int mv = 0; mv < movesLeft; mv++) {
        PathPoint point = search.getMove();
        if (point != null
            && !game.getStarMap().isBlocked(point.getX(), point.getY())) {
          makeFleetMove(game, point, info, fleet);
        }
      }
    }
    fleet.setMovesLeft(0);
    if (search == null || search.isLastMove()) {
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
    if (fleet.getRoute() == null) {
      // Fleet has encounter obstacle, taking a detour round it
      if (fleet.getaStarSearch() == null) {
        // No A star search made yet, so let's do it
        AStarSearch search = new AStarSearch(game.getStarMap(), fleet.getX(),
            fleet.getY(), mission.getX(), mission.getY(), 7, true);
        search.doSearch();
        search.doRoute();
        fleet.setaStarSearch(search);
        makeRerouteBeforeFTLMoves(game, fleet, info, mission);
      } else {
        makeRerouteBeforeFTLMoves(game, fleet, info, mission);
      }
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
    int moves = fleet.getMovesLeft();
    for (int mv = 0; mv < moves; mv++) {
      PathPoint point = search.getMove();
      if (point != null
          && !game.getStarMap().isBlocked(point.getX(), point.getY())) {
        makeFleetMove(game, point, info, fleet);
      }
      if (fleet.getMovesLeft() == 0) {
        break;
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
