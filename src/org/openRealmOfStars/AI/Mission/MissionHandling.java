package org.openRealmOfStars.AI.Mission;

import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
public class MissionHandling {


  /**
   * Handle exploring mission
   * @param mission Exploring mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting starmap and planet
   */
  public static void handleExploring(Mission mission, Fleet fleet, 
      PlayerInfo info, Game game) {
    if (mission != null) {
      if (mission.getType() == MissionType.EXPLORE) {
        if (mission.getPhase() == MissionPhase.TREKKING && fleet.getRoute() == null) {
          // Fleet has encounter obstacle, taking a detour round it
          Sun sun = game.getStarMap().locateSolarSystem(fleet.getX(), fleet.getY());
          if (sun != null && sun.getName() == mission.getSunName()) {
            // Fleet is in correct solar system, starting explore execution mode
            mission.setPhase(MissionPhase.EXECUTING);
            fleet.setaStarSearch(null);
          } else if (fleet.getaStarSearch() == null) {
            // No A star search made yet, so let's do it
            AStarSearch search = new AStarSearch(game.getStarMap(), 
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(), 7);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRerouteBeforeFTLMoves(game,fleet, info, mission);
          } else {
            makeRerouteBeforeFTLMoves(game,fleet,info,mission);
          }
        } 
        if (mission.getPhase() == MissionPhase.EXECUTING) {
          mission.setMissionTime(mission.getMissionTime()+1);
          if (mission.getMissionTime() >= info.getRace().getAIExploringAmount()) {
            // Depending on race it decides enough is enough
            fleet.setaStarSearch(null);
          }
          if (fleet.getaStarSearch() == null) {
            Sun sun = game.getStarMap().getNearestSolarSystem(fleet.getX(), fleet.getY(),info,fleet);
            if (!sun.getName().equals(mission.getSunName())) {
              mission.setTarget(sun.getCenterX(), sun.getCenterY());
              fleet.setRoute(new Route(fleet.getX(), fleet.getY(), 
                  mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
              mission.setSunName(sun.getName());
              mission.setPhase(MissionPhase.TREKKING);
              return;
            }
            PathPoint point = info.getUnchartedSector(sun, fleet);
            if (point != null) {
              mission.setTarget(point.getX(), point.getY());
              AStarSearch search = new AStarSearch(game.getStarMap(), 
                  fleet.getX(), fleet.getY(), mission.getX(), mission.getY());
              search.doSearch();
              search.doRoute();
              fleet.setaStarSearch(search);
              makeRegularMoves(game,fleet, info);
            }
          } else {
            makeRegularMoves(game,fleet, info);
          }
        }
      } // End Of Explore
    }
  }

  /**
   * Handle Colonize mission
   * @param mission Colonize mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting starmap and planet
   */
  public static void handleColonize(Mission mission, Fleet fleet, 
      PlayerInfo info, Game game) {
    if (mission != null) {
      if (mission.getType() == MissionType.COLONIZE) {
        if (mission.getPhase() == MissionPhase.LOADING) {
          // Loading colonist
          Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(), fleet.getY());
          if (planet.getPlanetPlayerInfo() == info) {
            Ship[] ships = fleet.getShips();
            int colony = 0; 
            for (int i=0;i<ships.length;i++) {
              if (ships[i].isColonyModule()) {
                colony = i;
                break;
              }
            }
            if (planet.getTotalPopulation() > 2 && planet.takeColonist() && ships[colony].getFreeCargoColonists() > 0) {
              // One colonist on board, ready to go trekking
              ships[colony].setColonist(ships[colony].getColonist()+1);
              mission.setPhase(MissionPhase.TREKKING);
              Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                  mission.getY(), fleet.getFleetFtlSpeed());
              fleet.setRoute(route);
            }
            if (planet.getTotalPopulation() > 3 && planet.takeColonist() && ships[colony].getFreeCargoColonists() > 0) {
              ships[colony].setColonist(ships[colony].getColonist()+1);
            }
          }
        }
        if (mission.getPhase() == MissionPhase.TREKKING &&
            fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
          // Target acquired
          mission.setPhase(MissionPhase.EXECUTING);
          Ship ship = fleet.getColonyShip();
          Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(), fleet.getY());
          if (ship != null && planet != null &&
              planet.getPlanetPlayerInfo() == null) {
            // Make sure that ship is really colony and there is planet to colonize
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
              info.Fleets().recalculateList();
            }
            ShipStat stat = game.getStarMap().getCurrentPlayerInfo()
                .getShipStatByName(ship.getName());
            stat.setNumberOfInUse(stat.getNumberOfInUse()-1);
          }

          
        } else if (mission.getPhase() == MissionPhase.TREKKING &&
            fleet.getRoute() == null) {
          // Fleet has encounter obstacle, taking a detour round it
          if (fleet.getaStarSearch() == null) {
            // No A star search made yet, so let's do it
            AStarSearch search = new AStarSearch(game.getStarMap(), 
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(), 7);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRerouteBeforeFTLMoves(game,fleet, info, mission);
          } else {
            makeRerouteBeforeFTLMoves(game,fleet,info,mission);
          }
        } 
      } // End of colonize

    }
  }

  /**
   * Handle Colonize mission
   * @param mission Colonize mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting starmap and planet
   */
  public static void handleAttack(Mission mission, Fleet fleet, 
      PlayerInfo info, Game game) {
    if (mission != null) {
      if (mission.getType() == MissionType.ATTACK) {
        if (mission.getPhase() == MissionPhase.PLANNING && mission.getTargetPlanet() != null) {
          if (info.getMissions().isAttackMissionLast(mission.getX(), mission.getY())) {
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
             if (ship.getTotalMilitaryPower()>0) {
               military++;
             }
            }
            if (military >= info.getRace().getAIMinimumAttackShips() 
                && (bombers+trooper)>info.getRace().getAIMinimumConquerShips() ) {
              mission.setPhase(MissionPhase.EXECUTING);
              Planet planet = game.getStarMap().getPlanetByName(mission.getTargetPlanet());
              if (planet != null) {
                mission.setTarget(planet.getX(), planet.getY());
              }
            }
          }
        }
        if (mission.getPhase() == MissionPhase.LOADING) {
          // Loading Troops
          Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(), fleet.getY());
          if (planet.getPlanetPlayerInfo() == info) {
            Ship[] ships = fleet.getShips();
            int trooper = 0; 
            for (int i=0;i<ships.length;i++) {
              if (ships[i].isTrooperModule()) {
                trooper = i;
                break;
              }
            }
            if (planet.getTotalPopulation() > 2 && planet.takeColonist() && ships[trooper].getFreeCargoColonists() > 0) {
              // One Troops on board, ready to go trekking
              ships[trooper].setColonist(ships[trooper].getColonist()+1);
              mission.setPhase(MissionPhase.TREKKING);
              Route route = new Route(fleet.getX(), fleet.getY(), mission.getX(),
                  mission.getY(), fleet.getFleetFtlSpeed());
              fleet.setRoute(route);
            }
            while (planet.getTotalPopulation() > 3 && planet.takeColonist() && ships[trooper].getFreeCargoColonists() > 0) {
              ships[trooper].setColonist(ships[trooper].getColonist()+1);
            }
          }
        }
        if (mission.getPhase() == MissionPhase.TREKKING && mission.getTargetPlanet() == null &&
            fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
          // Target acquired, merge fleet to bigger attack group
          mergeFleets(fleet, info);
          info.getMissions().remove(mission);
        } else if (mission.getPhase() == MissionPhase.TREKKING &&
            fleet.getRoute() == null) {
          // Fleet has encounter obstacle, taking a detour round it
          if (fleet.getaStarSearch() == null) {
            // No A star search made yet, so let's do it
            AStarSearch search = new AStarSearch(game.getStarMap(), 
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(), 7);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRerouteBeforeFTLMoves(game,fleet, info, mission);
          } else {
            makeRerouteBeforeFTLMoves(game,fleet,info,mission);
          }
        } 
        if (mission.getPhase() == MissionPhase.EXECUTING &&
            fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
          // Target acquired, 
        } else if (mission.getPhase() == MissionPhase.EXECUTING &&
            fleet.getRoute() == null) {
          // Fleet has encounter obstacle, taking a detour round it
          if (fleet.getaStarSearch() == null) {
            // No A star search made yet, so let's do it
            AStarSearch search = new AStarSearch(game.getStarMap(), 
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(), 7);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRerouteBeforeFTLMoves(game,fleet, info, mission);
          } else {
            makeRerouteBeforeFTLMoves(game,fleet,info,mission);
          }
        } 
      } // End of Attack

    }
  }

  /**
   * Handle Defend mission
   * @param mission Defend mission, does nothing if type is wrong
   * @param fleet Fleet on mission
   * @param info PlayerInfo
   * @param game Game for getting starmap and planet
   */
  public static void handleDefend(Mission mission, Fleet fleet, 
      PlayerInfo info, Game game) {
    if (mission != null) {
      if (mission.getType() == MissionType.DEFEND) {
        if (mission.getPhase() == MissionPhase.TREKKING &&
            fleet.getX() == mission.getX() && fleet.getY() == mission.getY()) {
          // Target acquired
          mission.setPhase(MissionPhase.EXECUTING);
          // Set defending route
          fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(), fleet.getY(), 0));
        } else if (mission.getPhase() ==  MissionPhase.EXECUTING) {
          mission.setMissionTime(mission.getMissionTime()+1);
          if (mission.getMissionTime() >= info.getRace().getAIDefenseUpdate()) {
            // New defender is needed
            mission.setMissionTime(0);
            mission.setPhase(MissionPhase.PLANNING);
          }
        } else if (mission.getPhase() == MissionPhase.TREKKING &&
            fleet.getRoute() == null) {
          // Fleet has encounter obstacle, taking a detour round it
          if (fleet.getaStarSearch() == null) {
            // No A star search made yet, so let's do it
            AStarSearch search = new AStarSearch(game.getStarMap(), 
                fleet.getX(), fleet.getY(), mission.getX(), mission.getY(), 7);
            search.doSearch();
            search.doRoute();
            fleet.setaStarSearch(search);
            makeRerouteBeforeFTLMoves(game,fleet, info, mission);
          } else {
            makeRerouteBeforeFTLMoves(game,fleet,info,mission);
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
  public static void mergeFleets(Fleet fleet, PlayerInfo info) {
    // Merging fleets
    String[] part = fleet.getName().split("#");          
    for (int j=0;j<info.Fleets().getNumberOfFleets();j++) {
      // Merge fleets in same space with same starting of fleet name
      Fleet mergeFleet = info.Fleets().getByIndex(j);
      if (mergeFleet != fleet && mergeFleet.getX() == fleet.getX() &&
          mergeFleet.getY() == fleet.getY() && mergeFleet.getName()
          .startsWith(part[0])) {
        for (int k=0;k<mergeFleet.getNumberOfShip();k++) {
          Ship ship = mergeFleet.getShipByIndex(k);
          if (ship != null) {
            fleet.addShip(ship);
          }
        }
        info.Fleets().remove(j);
        break;
      }
    }    
  }

  
  /**
   * Make Regular moves according A Star Search path finding
   * @param game Game used to get access starmap and planet lists
   * @param fleet Fleet to move
   * @param info Player who controls the fleet
   */
  private static void makeRegularMoves(Game game, Fleet fleet, PlayerInfo info) {
    AStarSearch search = fleet.getaStarSearch();
    for (int mv = 0;mv<fleet.movesLeft;mv++) {
      PathPoint point = search.getMove();
      if (point != null) {
        if (!game.getStarMap().isBlocked(point.getX(), point.getY())) {
        //   Not blocked so fleet is moving
          game.fleetMakeMove(info, fleet, point.getX(), point.getY());
          search.nextMove();
        }
      }
    }
    fleet.movesLeft = 0;
    if (search.isLastMove()) {
      fleet.setaStarSearch(null);
    }

  }

  /**
   * Make reroute moves while on mission
   * @param game Game used to get access starmap and planet lists
   * @param fleet Fleet to move
   * @param info PlayerInfo
   * @param mission Which mission
   */
  private static void makeRerouteBeforeFTLMoves(Game game,Fleet fleet, 
      PlayerInfo info, Mission mission) {
    AStarSearch search = fleet.getaStarSearch();
    for (int mv = 0;mv<fleet.movesLeft;mv++) {
      PathPoint point = search.getMove();
      if (point != null && !game.getStarMap().isBlocked(point.getX(), point.getY())) {
        //   Not blocked so fleet is moving
        game.fleetMakeMove(info, fleet, point.getX(), point.getY());
        search.nextMove();
      }
    }
    fleet.movesLeft = 0;
    if (search.isLastMove()) {
      if (search.getTargetDistance() > 0) {
        fleet.setRoute(new Route(fleet.getX(), fleet.getY(), 
            mission.getX(), mission.getY(), fleet.getFleetFtlSpeed()));
      }
      fleet.setaStarSearch(null);
    }

  }

}
