package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import org.openRealmOfStars.AI.AiThread;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionHandling;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.AI.Research.Research;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetType;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2019 Tuomo Untinen
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
 * AI turn view for Open Realm of Stars.
 * This class also handles what to do before ending the turn.
 * So all planet resources, scanning and research are done here.
 *
 */
public class AITurnView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Limit for destroy starbases mission
   */
  private static final int LIMIT_DESTROY_STARBASES = 4;
  /**
   * Limit for deploy starbases mission
   */
  private static final int LIMIT_DEPLOY_STARBASES = 4;
  /**
   * Limit for attack missions
   */
  private static final int LIMIT_ATTACKS = 4;
  /**
   * Limit for colonization missions
   */
  private static final int LIMIT_COLONIZATIONS = 4;
  /**
   * Text for showing human player
   */
  private TransparentLabel label;

  /**
   * Reference to the game
   */
  private Game game;

  /**
   * Text animation
   */
  private int textAnim;

  /**
   * Target coordinate for mission
   */
  private int cx;
  /**
   * Target coordinate for mission
   */
  private int cy;

  /**
   * Separate thread doing the AI handling
   */
  private AiThread aiThread;
  /**
   * Boolean for showing view at least for a while
   */
  private boolean readyToMove;
  /**
   * Next State after thread is ready
   */
  private GameState nextState;

  /**
   * Next state object
   */
  private Object nextStateObject;
  /**
   * Constructor for main menu
   * @param game Game used to get access star map and planet lists
   */
  public AITurnView(final Game game) {
    this.game = game;
    Planet planet = new Planet(new Coordinate(1, 1), "Random Planet", 1, false);
    planet.setPlanetType(PlanetTypes.getRandomPlanetType(true, true, true));
    if (planet.getPlanetType().isGasGiant()) {
      planet.setGasGiant(true);
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Enemy turn");
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 500)));

    label = new TransparentLabel(invisible, "Please wait.....");
    label.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    invisible.add(label);
    imgBase.add(invisible);
    this.add(imgBase, BorderLayout.CENTER);
    textAnim = 0;
    aiThread = new AiThread(this);
    readyToMove = false;
  }

  /**
   * Set Text for AI Turn View
   * @param text Text to set
   */
  public void setText(final String text) {
    label.setText(text);
  }

  /**
   * Update animated text on AI Turn View
   */
  public void updateText() {
    switch (textAnim) {
    case 0:
      setText("Please wait.....");
      break;
    case 1:
      setText("Please wait*....");
      break;
    case 2:
      setText("Please wait.*...");
      break;
    case 3:
      setText("Please wait..*..");
      break;
    case 4:
      setText("Please wait...*.");
      break;
    case 5:
      setText("Please wait....*");
      break;
    default:
      setText("Please wait.....");
    }
    textAnim++;
    if (textAnim > 5) {
      readyToMove = true;
      textAnim = 0;
    }
  }

  /**
   * Handle missions for AI player and single fleet
   * @param fleet Fleet for doing the missing
   * @param info PlayerInfo
   */
  private void handleMissions(final Fleet fleet, final PlayerInfo info) {
    if (!fleet.allFixed() && !info.isHuman()) {
      // Make fleet to fix itself
      fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(),
          fleet.getY(), Route.ROUTE_FIX));
      return;
    }
    Mission mission = info.getMissions().getMissionForFleet(fleet.getName());
    if (mission == null && info.isHuman()) {
      return;
    }
    if (mission != null) {
      switch (mission.getType()) {
      case COLONIZE:
        MissionHandling.handleColonize(mission, fleet, info, game);
        break;
      case EXPLORE:
        MissionHandling.handleExploring(mission, fleet, info, game);
        break;
      case DEFEND:
        MissionHandling.handleDefend(mission, fleet, info, game);
        break;
      case ATTACK:
        MissionHandling.handleAttack(mission, fleet, info, game);
        break;
      case MOVE:
        MissionHandling.handleMove(mission, fleet, info, game);
        break;
      case GATHER:
        MissionHandling.handleGather(mission, fleet, info, game);
        break;
      case DEPLOY_STARBASE:
        MissionHandling.handleDeployStarbase(mission, fleet, info, game);
        break;
      case DESTROY_STARBASE:
        MissionHandling.handleDestroyStarbase(mission, fleet, info, game);
        break;
      case TRADE_FLEET:
        MissionHandling.handleTrade(mission, fleet, info, game);
        break;
      case PRIVATEER:
        MissionHandling.handlePrivateering(mission, fleet, info, game);
        break;
      case COLONY_EXPLORE:
        MissionHandling.handleColonyExplore(mission, fleet, info, game);
        break;
      case SPY_MISSION:
        MissionHandling.handleSpyMission(mission, fleet, info, game);
        break;
      default:
        throw new IllegalArgumentException("Unknown mission type for AI!");
      }
    } else {
      // No mission for fleet yet
      if (fleet.isScoutFleet() && !info.isBoard()) {
        // Scout fleet should go to explore
        Sun sun = game.getStarMap().getAboutNearestSolarSystem(fleet.getX(),
            fleet.getY(), info, fleet, null);
        mission = new Mission(MissionType.EXPLORE, MissionPhase.TREKKING,
            sun.getCenterCoordinate());
        mission.setFleetName(fleet.getName());
        mission.setSunName(sun.getName());
        info.getMissions().add(mission);
        fleet.setRoute(new Route(fleet.getX(), fleet.getY(), mission.getX(),
            mission.getY(), fleet.getFleetFtlSpeed()));
        // Mission assigned continue...
        return;
      }
      if (fleet.getStarbaseShip() != null && !fleet.isStarBaseDeployed()) {
        // Unused starbase found, trying to find mission for it.
        mission = info.getMissions().getMission(MissionType.DEPLOY_STARBASE,
            MissionPhase.PLANNING);
        if (mission != null) {
          mission.setFleetName(fleet.getName());
          mission.setPhase(MissionPhase.TREKKING);
          // Mission assigned continue...
          return;
        }
      }
      if (fleet.isColonyFleet()
          && game.getStarMap().getGameLengthState()
          == GameLengthState.START_GAME
          && game.getStarMap().getTurn() < 2) {
        // Colony fleet should go to explore
        Sun sun = game.getStarMap().getNearestSolarSystem(fleet.getX(),
            fleet.getY(), info, fleet, null);
        Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        mission = new Mission(MissionType.COLONY_EXPLORE,
            MissionPhase.EXECUTING, sun.getCenterCoordinate());
        mission.setFleetName(fleet.getName());
        mission.setSunName(sun.getName());
        if (planet != null) {
          mission.setPlanetBuilding(planet.getName());
        }
        mission.setTarget(fleet.getCoordinate());
        info.getMissions().add(mission);
        // Mission assigned continue...
        return;
      }
      if (fleet.isPrivateerFleet()
          || info.isBoard() && !fleet.isStarBaseDeployed()) {
        // Privateer fleet should go to explore and rob trade ships
        Sun sun = game.getStarMap().getAboutNearestSolarSystem(fleet.getX(),
            fleet.getY(), info, fleet, null);
        mission = new Mission(MissionType.PRIVATEER, MissionPhase.TREKKING,
            sun.getCenterCoordinate());
        mission.setFleetName(fleet.getName());
        mission.setSunName(sun.getName());
        info.getMissions().add(mission);
        fleet.setRoute(new Route(fleet.getX(), fleet.getY(), mission.getX(),
            mission.getY(), fleet.getFleetFtlSpeed()));
        // Mission assigned continue...
        return;
      }
      if (fleet.getColonyShip(false) != null && fleet.getNumberOfShip() > 1) {
        Ship colonyShip = fleet.getColonyShip(false);
        Fleet colonyFleet = new Fleet(colonyShip, fleet.getX(),
            fleet.getY());
        String str = info.getFleets().generateUniqueName("Colony");
        colonyFleet.setName(str);
        fleet.removeShip(colonyShip);
        info.getFleets().add(colonyFleet);
      }
      if (fleet.isColonyFleet()) {
        mission = info.getMissions().getMission(MissionType.COLONIZE,
            MissionPhase.PLANNING);
        if (mission != null) {
          Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
              fleet.getY());
          if (planet == null) {
            // Colony ship is not on home port
            planet = game.getStarMap().getClosestHomePort(info,
                fleet.getCoordinate());
            if (planet != null) {
              mission = new Mission(MissionType.MOVE, MissionPhase.PLANNING,
                  planet.getCoordinate());
              mission.setFleetName(fleet.getName());
              mission.setTargetPlanet(planet.getName());
              mission.setTarget(planet.getCoordinate());
              info.getMissions().add(mission);
              // Moving colony to nearest home port
              return;
            }
            // Colony does not have colonist nor home port
            // Odds look pretty bad now...
            // Just returning
            return;
          }
          mission.setPhase(MissionPhase.LOADING);
          mission.setFleetName(fleet.getName());
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
              ships[colony].setColonist(ships[colony].getColonist() + 1);
              mission.setPhase(MissionPhase.TREKKING);
              Route route = new Route(fleet.getX(), fleet.getY(),
                  mission.getX(), mission.getY(), fleet.getFleetFtlSpeed());
              fleet.setRoute(route);
            }
            if (planet.getTotalPopulation() > 3 && planet.takeColonist()
                && ships[colony].getFreeCargoColonists() > 0) {
              ships[colony].setColonist(ships[colony].getColonist() + 1);
            }
          }
          // Mission assigned continue...
          return;
        }
      }
      mission = info.getMissions().getGatherMission(Mission.BOMBER_TYPE);
      Ship ship = fleet.getBomberShip();
      if (mission != null && ship != null) {
        fleet.removeShip(ship);
        Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
        newFleet.setName(info.getFleets().generateUniqueName("Gather"));
        mission.setFleetName(newFleet.getName());
        mission.setPhase(MissionPhase.LOADING);
        info.getFleets().add(newFleet);
        // Created a new fleet with new mission, old one still might exists
      }
      mission = info.getMissions().getGatherMission(Mission.TROOPER_TYPE);
      ship = fleet.getTrooperShip();
      if (mission != null && ship != null) {
        fleet.removeShip(ship);
        Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
        newFleet.setName(info.getFleets().generateUniqueName("Gather"));
        mission.setFleetName(newFleet.getName());
        mission.setPhase(MissionPhase.LOADING);
        info.getFleets().add(newFleet);
        // Created a new fleet with new mission, old one still might exists
      }
      mission = info.getMissions().getGatherMission(Mission.ASSAULT_TYPE);
      ship = fleet.getAssaultShip();
      if (mission != null && ship != null) {
        fleet.removeShip(ship);
        Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
        newFleet.setName(info.getFleets().generateUniqueName("Gather"));
        mission.setFleetName(newFleet.getName());
        mission.setPhase(MissionPhase.TREKKING);
        info.getFleets().add(newFleet);
        // Created a new fleet with new mission, old one still might exists
      }
      mission = info.getMissions().getGatherMission(Mission.ASSAULT_SB_TYPE);
      ship = fleet.getAssaultShip();
      if (mission != null && ship != null) {
        fleet.removeShip(ship);
        Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
        newFleet.setName(info.getFleets().generateUniqueName("Gather"));
        mission.setFleetName(newFleet.getName());
        mission.setPhase(MissionPhase.TREKKING);
        info.getFleets().add(newFleet);
        // Created a new fleet with new mission, old one still might exists
      }
      // Just cleaning the old if needed
      info.getFleets().recalculateList();
    }

  }

  /**
   * Calculate Attack rendevue sector coordinate
   * @param info Player who is going to attack
   * @param x Target planet x coordinate
   * @param y Target planet y coordinate
   */
  public void calculateAttackRendevuezSector(final PlayerInfo info, final int x,
      final int y) {
    int amount = 1;
    cx = x;
    cy = y;
    for (Planet planet : game.getStarMap().getPlanetList()) {
      if (planet.getPlanetPlayerInfo() == info) {
        cx = cx + planet.getX();
        cy = cy + planet.getY();
        if (planet.getHomeWorldIndex() != -1) {
          // Double the effect of home world
          cx = cx + planet.getX();
          cy = cy + planet.getY();
        }
        amount++;
      }
    }
    cx = cx / amount;
    cy = cy / amount;
    if (game.getStarMap().isBlocked(cx, cy)) {
      int loop = 100;
      while (loop > 0) {
        int ax = DiceGenerator.getRandom(-5, 5);
        int ay = DiceGenerator.getRandom(-5, 5);
        loop--;
        if (!game.getStarMap().isBlocked(cx + ax, cy + ay)) {
          loop = 0;
          cx = cx + ax;
          cy = cy + ay;
        }

      }
    }
  }

  /**
   * Create gather mission for attack mission
   * @param mission Attack mission
   * @param coord Coordinate where to gather attackers
   * @param shipType Ship type to gather
   * @return Gather mission
   */
  private static Mission createGatherMission(final Mission mission,
      final Coordinate coord, final String shipType) {
    if (mission.getType() == MissionType.DESTROY_STARBASE) {
      Mission gatherMission = new Mission(MissionType.GATHER,
          MissionPhase.PLANNING, coord);
      gatherMission.setPlanetGathering(mission.getPlanetBuilding());
      gatherMission.setShipType(Mission.ASSAULT_SB_TYPE);
      gatherMission.setTargetPlanet(mission.getTargetPlanet());
      return gatherMission;
    }
    Mission gatherMission = new Mission(MissionType.GATHER,
        MissionPhase.PLANNING, coord);
    gatherMission.setPlanetGathering(mission.getPlanetBuilding());
    gatherMission.setShipType(shipType);
    gatherMission.setTargetPlanet(mission.getTargetPlanet());
    return gatherMission;
  }

  /**
   * Add gathering mission for attack
   * @param info Player whom is attacking
   * @param mission Attack mission
   */
  public static  void addGatherMission(final PlayerInfo info,
      final Mission mission) {
    if (mission.getType() == MissionType.ATTACK) {
      Coordinate coord = new Coordinate(mission.getX(), mission.getY());
      info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
          Mission.ASSAULT_TYPE), mission);
      info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
          Mission.ASSAULT_TYPE), mission);
      if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.BOMBER_TYPE), mission);
      }
      info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
          Mission.TROOPER_TYPE), mission);
      Attitude attitude = info.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE) {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        if (!info.bomberTrooperAvailable()) {
          info.getMissions().addPriorityAfter(createGatherMission(mission,
              coord, Mission.TROOPER_TYPE), mission);
        }
      } else if (attitude == Attitude.MILITARISTIC) {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
          info.getMissions().addPriorityAfter(createGatherMission(mission,
              coord, Mission.BOMBER_TYPE), mission);
        }
      } else if (attitude == Attitude.EXPANSIONIST) {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        if (!info.bomberTrooperAvailable()) {
          info.getMissions().addPriorityAfter(createGatherMission(mission,
              coord, Mission.TROOPER_TYPE), mission);
        }
      } else if (attitude == Attitude.BACKSTABBING) {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
          info.getMissions().addPriorityAfter(createGatherMission(mission,
              coord, Mission.BOMBER_TYPE), mission);
        }
      } else if (attitude == Attitude.LOGICAL) {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        if (!info.bomberTrooperAvailable()) {
          info.getMissions().addPriorityAfter(createGatherMission(mission,
              coord, Mission.TROOPER_TYPE), mission);
        }
        if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
          info.getMissions().addPriorityAfter(createGatherMission(mission,
              coord, Mission.BOMBER_TYPE), mission);
        }
      } else {
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
        info.getMissions().addPriorityAfter(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE), mission);
      }
    }
    if (mission.getType() == MissionType.DESTROY_STARBASE) {
      Coordinate coord = new Coordinate(mission.getX(), mission.getY());
      info.getMissions().add(createGatherMission(mission, coord,
          Mission.ASSAULT_SB_TYPE));
      Attitude attitude = info.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.MILITARISTIC) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_SB_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_SB_TYPE));
      } else if (attitude == Attitude.LOGICAL) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_SB_TYPE));
      } else {
        if (DiceGenerator.getRandom(1) == 0) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.ASSAULT_SB_TYPE));
        }
      }
    }
  }
  /**
   * Adding attack mission to mission list
   * @param planet Planet where to attack
   * @param info Player who is attacking
   */
  public void addAttackMission(final Planet planet, final PlayerInfo info) {
    // New planet to conquer, adding it to mission list
    Mission mission = new Mission(MissionType.ATTACK,
        MissionPhase.PLANNING, planet.getCoordinate());
    calculateAttackRendevuezSector(info, planet.getX(), planet.getY());
    Planet homeWorld = game.getStarMap().getClosestHomePort(info,
        planet.getCoordinate());
    mission.setFleetName("Attacker of " + planet.getName());
    if (homeWorld == null) {
      mission.setTarget(new Coordinate(cx, cy));
      mission.setTargetPlanet(planet.getName());
      mission.setPlanetBuilding("Deep space " + cx + "," + cy);
    } else {
      mission.setTarget(homeWorld.getCoordinate());
      mission.setTargetPlanet(planet.getName());
      mission.setPlanetBuilding(homeWorld.getName());
    }
    if (info.getMissions().getAttackMission(planet.getName()) == null) {
      // No attack mission for this planet found, so adding it.
      if (planet.getHomeWorldIndex() != -1) {
        info.getMissions().addHighestPriority(mission);
      } else {
        info.getMissions().add(mission);
      }
      addGatherMission(info, mission);
    }
  }
  /**
   * Adding trade mission to mission list
   * @param planet Planet where to trade
   * @param info Player who is trading
   */
  public void addTradeMission(final Planet planet, final PlayerInfo info) {
    // New planet to trade, adding it to mission list
    Mission mission = new Mission(MissionType.TRADE_FLEET,
        MissionPhase.PLANNING, planet.getCoordinate());
      mission.setTarget(planet.getCoordinate());
      mission.setTargetPlanet(planet.getName());
    if (info.getMissions().getTradeMission(planet.getName()) == null) {
      // No trade mission for this planet found, so adding it.
      info.getMissions().add(mission);
    }
  }

  /**
   * Adding Destroy Starbase mission to mission list
   * @param coordinate coordinate where to attack
   * @param info Player who is attacking
   */
  public void addDestroyStarbaseMission(final Coordinate coordinate,
      final PlayerInfo info) {
    // Starbase to destory
    Mission mission = new Mission(MissionType.DESTROY_STARBASE,
        MissionPhase.PLANNING, coordinate);
    calculateAttackRendevuezSector(info, coordinate.getX(), coordinate.getY());
    Planet homeWorld = game.getStarMap().getClosestHomePort(info, coordinate);
    if (homeWorld == null) {
      mission.setTarget(new Coordinate(cx, cy));
      mission.setTargetPlanet("Starbase " + coordinate.toString());
      mission.setPlanetBuilding("Deep space " + cx + "," + cy);
    } else {
      mission.setTarget(homeWorld.getCoordinate());
      mission.setTargetPlanet("Starbase " + coordinate.toString());
      mission.setPlanetBuilding(homeWorld.getName());
    }
    if (info.getMissions().getDestroyStarbaseMission(
        "Starbase " + coordinate.toString()) == null) {
      // No Destroy starbase mission for this starbase found, so adding it.
      info.getMissions().add(mission);
      addGatherMission(info, mission);
    }
  }

  /**
   * Search deep space anchors
   */
  public void searchDeepSpaceAnchors() {
    StarMap map = game.getStarMap();
    PlayerInfo info = game.getPlayers()
        .getPlayerInfoByIndex(game.getStarMap().getAiTurnNumber());
    int destroyStarbases = info.getMissions().getNumberOfMissionTypes(
        MissionType.DESTROY_STARBASE);
    int deployStarbases = info.getMissions().getNumberOfMissionTypes(
        MissionType.DEPLOY_STARBASE);
    FleetTileInfo[][] fleetTiles = map.getFleetTiles();
    if (deployStarbases >= LIMIT_DEPLOY_STARBASES
        && destroyStarbases >= LIMIT_DESTROY_STARBASES) {
      return;
    }
    for (int y = 0; y < map.getMaxY(); y++) {
      for (int x = 0; x < map.getMaxX(); x++) {
        Tile tile = map.getTile(x, y);
        if ((tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
            || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2))
            && info.getSectorVisibility(new Coordinate(x, y))
            > PlayerInfo.UNCHARTED) {
          if (fleetTiles[x][y] == null) {
            Mission mission = new Mission(MissionType.DEPLOY_STARBASE,
                MissionPhase.PLANNING, new Coordinate(x, y));
            if (info.getMissions().getDeployStarbaseMission(x, y) == null
                && deployStarbases < LIMIT_DEPLOY_STARBASES) {
              // No deploy starbase mission for this planet found, so adding it.
              info.getMissions().add(mission);
            }
          } else {
            // There is fleet already in the tile
            PlayerInfo infoAt = game.getPlayers().getPlayerInfoByIndex(
                fleetTiles[x][y].getPlayerIndex());
            Fleet fleet = infoAt.getFleets().getByIndex(
                fleetTiles[x][y].getFleetIndex());
            if (!fleet.isStarBaseDeployed()
                && info.getSectorVisibility(new Coordinate(x, y))
                == PlayerInfo.VISIBLE && infoAt != info) {
              Mission mission = new Mission(MissionType.DEPLOY_STARBASE,
                  MissionPhase.PLANNING, new Coordinate(x, y));
              if (info.getMissions().getDeployStarbaseMission(x, y) == null
                  && deployStarbases < LIMIT_DEPLOY_STARBASES) {
                // No deploy starbase mission for this planet found,
                // so adding it.
                info.getMissions().add(mission);
              }
            } else if (fleet.isStarBaseDeployed()
                  && info.getSectorVisibility(new Coordinate(x, y))
                  == PlayerInfo.VISIBLE && infoAt != info) {
                int index = fleetTiles[x][y].getPlayerIndex();
                DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                    index);
                if (list.isBonusType(DiplomacyBonusType.IN_WAR)
                    && destroyStarbases < LIMIT_DESTROY_STARBASES) {
                  addDestroyStarbaseMission(new Coordinate(x, y), info);
                }
            } else if (fleet.isStarBaseDeployed()
                && info.getSectorVisibility(new Coordinate(x, y))
                == PlayerInfo.VISIBLE && infoAt == info) {
              Mission mission = new Mission(MissionType.DEPLOY_STARBASE,
                  MissionPhase.PLANNING, new Coordinate(x, y));
              if (info.getMissions().getDeployStarbaseMission(x, y) == null
                  && deployStarbases < LIMIT_DEPLOY_STARBASES
                  && info.isBiggerStarbases()
                  && fleet.getNumberOfShip() < Fleet.MAX_STARBASE_SIZE) {
                // No secondary starbase deploy mission found for this anchor
                info.getMissions().add(mission);
              }
            } else if (fleet.isStarBaseDeployed()
                && info.getSectorVisibility(new Coordinate(x, y))
                == PlayerInfo.FOG_OF_WAR && infoAt != info) {
              // Anchor is under Fog of war, but Maybe espionage
              // Will detect that is already occupied.
              int index = fleetTiles[x][y].getPlayerIndex();
              EspionageList espionage = info.getEspionage().getByIndex(index);
              if (espionage.isFleetTypeRecognized(FleetType.STARBASE)) {
                // There is already starbase know by espionage
                DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                    index);
                if (list.isBonusType(DiplomacyBonusType.IN_WAR)
                    && destroyStarbases < LIMIT_DESTROY_STARBASES) {
                  // AI already has war against that player so,
                  // adding destroy mission
                  addDestroyStarbaseMission(new Coordinate(x, y), info);
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * Search for fleets that have crossed the borders
   */
  public void searchForBorderCrossing() {
    PlayerInfo info = game.getPlayers()
        .getPlayerInfoByIndex(game.getStarMap().getAiTurnNumber());
    if (info != null && !info.isHuman() && !info.isBoard()) {
      int maxPlayer = game.getPlayers().getCurrentMaxRealms();
      for (int i = 0; i < maxPlayer; i++) {
        PlayerInfo fleetOwner = game.getPlayers().getPlayerInfoByIndex(i);
        if (fleetOwner != info) {
          int numberOfFleets = fleetOwner.getFleets().getNumberOfFleets();
          for (int j = 0; j < numberOfFleets; j++) {
            Fleet fleet = fleetOwner.getFleets().getByIndex(j);
            int military = fleet.getMilitaryValue();
            int detect = info.getSectorCloakDetection(fleet.getX(),
                fleet.getY());
            byte visibility = info.getSectorVisibility(fleet.getCoordinate());
            if (detect >= fleet.getFleetCloackingValue()
                && visibility == PlayerInfo.VISIBLE) {
              CulturePower culture = game.getStarMap().getSectorCulture(
                  fleet.getX(), fleet.getY());
              if (culture.getHighestCulture() == game.getStarMap()
                  .getAiTurnNumber()) {
                if (info.getDiplomacy().isTradeAlliance(i) && military == 0) {
                  // Non military ship and trade alliance
                  continue;
                }
                if ((info.getDiplomacy().isAlliance(i)
                    || info.getDiplomacy().isDefensivePact(i))
                    && military >= 0) {
                  // (non)military ship and alliance
                  continue;
                }
                if (info.getDiplomacy().isWar(i)) {
                  // War no diplomacy screen then
                  continue;
                }
                if (fleet.isPrivateerFleet()) {
                  continue;
                }
                if (fleetOwner.isHuman()) {
                  SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
                  game.changeGameState(GameState.DIPLOMACY_VIEW, fleet);
                  return;
                }
                int fleetOwnerIndex = game.getPlayers().getIndex(fleetOwner);
                int type = Diplomacy.getBorderCrossingType(info, fleet,
                    fleetOwnerIndex);
                if (type == DiplomacyView.AI_ESPIONAGE) {
                  info.getDiplomacy().getDiplomacyList(fleetOwnerIndex)
                      .addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
                          info.getRace());
                }
                if (type == DiplomacyView.AI_BORDER_CROSS) {
                  info.getDiplomacy().getDiplomacyList(fleetOwnerIndex)
                      .addBonus(DiplomacyBonusType.BORDER_CROSSED,
                          info.getRace());
                }
                MissionHandling.handleDiplomacyBetweenAis(game, info, i,
                   fleet, fleet);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Search newly found planets for different missions.
   * This methods locates colonizable planets, attackable planets
   * and planets with to trade.
   */
  public void searchPlanetsForMissions() {
    PlayerInfo info = game.getPlayers()
        .getPlayerInfoByIndex(game.getStarMap().getAiTurnNumber());
    if (info != null && !info.isHuman()) {
      ArrayList<Planet> planets = game.getStarMap().getPlanetList();
      int colonizations = info.getMissions().getNumberOfMissionTypes(
          MissionType.COLONIZE);
      int attacks = info.getMissions().getNumberOfMissionTypes(
          MissionType.ATTACK);
      for (Planet planet : planets) {
        if (planet.getTotalRadiationLevel() <= info.getRace().getMaxRad()
            && planet.getPlanetPlayerInfo() == null && !planet.isGasGiant()
            && info.getSectorVisibility(planet.getCoordinate())
            == PlayerInfo.VISIBLE) {
          // New planet to colonize, adding it to mission list
          Mission mission = new Mission(MissionType.COLONIZE,
              MissionPhase.PLANNING, planet.getCoordinate());
          if (info.getMissions().getColonizeMission(mission.getX(),
              mission.getY()) == null && colonizations < LIMIT_COLONIZATIONS) {
            // No colonize mission for this planet found, so adding it.
            info.getMissions().addHighestPriority(mission);
            mission = info.getMissions().getMission(MissionType.COLONY_EXPLORE,
                MissionPhase.EXECUTING);
            if (mission != null) {
              // Colony ship was exploring, calling it back to home
              mission.setPhase(MissionPhase.TREKKING);
            }
          }
        }
        if (planet.getTotalRadiationLevel() <= info.getRace().getMaxRad()
            && !planet.isGasGiant()
            && planet.getPlanetPlayerInfo() == null
            && info.getSectorVisibility(planet.getCoordinate())
            == PlayerInfo.FOG_OF_WAR) {
          // New planet to colonize, adding it to mission list
          Mission mission = new Mission(MissionType.COLONIZE,
              MissionPhase.PLANNING, planet.getCoordinate());
          if (info.getMissions().getColonizeMission(mission.getX(),
              mission.getY()) == null && colonizations < LIMIT_COLONIZATIONS) {
            // No colonize mission for this planet found, so adding it.
            info.getMissions().addHighestPriority(mission);
          }
        }
        if (planet.getTotalRadiationLevel() <= info.getRace().getMaxRad()
            && planet.getPlanetPlayerInfo() != null
            && planet.getPlanetPlayerInfo() != info && !planet.isGasGiant()) {
          if (info.getSectorVisibility(planet.getCoordinate())
            == PlayerInfo.VISIBLE) {
            PlayerInfo owner = planet.getPlanetPlayerInfo();
            int ownerIndex = game.getStarMap().getPlayerList().getIndex(owner);
            DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                ownerIndex);
            if (list != null && list.isBonusType(DiplomacyBonusType.IN_WAR)
                && attacks < LIMIT_ATTACKS) {
              addAttackMission(planet, info);
            } else {
              if (owner.isHuman()) {
                boolean nothingToTrade = info.getDiplomacy()
                    .getDiplomacyList(ownerIndex).isBonusType(
                        DiplomacyBonusType.NOTHING_TO_TRADE);
                if (!nothingToTrade) {
                  // For human start diplomacy view
                  SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
                  game.changeGameState(GameState.DIPLOMACY_VIEW, info);
                }
              } else {
                MissionHandling.handleDiplomacyBetweenAis(game, info,
                    ownerIndex, null, planet);
              }
              if (list != null
                  && (list.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
                  || list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
                  || list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
                // Got new map part maybe in trade and found planet owned by
                // player which is being in alliance
                addTradeMission(planet, info);
              }
            }
          }
          if (info.getSectorVisibility(planet.getCoordinate())
              == PlayerInfo.FOG_OF_WAR) {
            PlayerInfo owner = planet.getPlanetPlayerInfo();
            int ownerIndex = game.getStarMap().getPlayerList().getIndex(
                owner);
            DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                ownerIndex);
            if (list != null
                && list.isBonusType(DiplomacyBonusType.IN_WAR)
                && attacks < LIMIT_ATTACKS) {
              // Got new map part maybe in trade and found planet owned by
              // player which is being at war now.
              addAttackMission(planet, info);
            }
            if (list != null
                && (list.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
                || list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
                || list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
              // Got new map part maybe in trade and found planet owned by
              // player which is being in alliance
              addTradeMission(planet, info);
            }
          }
        } // End of owned planet handling
      } // End of for loop of planets
    }
  }

  /**
   * Handle single AI Fleet. If fleet was last then increase AI turn number
   * and set aiFleet to null.
   */
  public void handleAIFleet() {
    PlayerInfo info = game.getPlayers()
        .getPlayerInfoByIndex(game.getStarMap().getAiTurnNumber());
    if (info != null && info.isHuman()
        && game.getStarMap().getAIFleet() != null) {
      handleMissions(game.getStarMap().getAIFleet(), info);
      game.getStarMap().setAIFleet(info.getFleets().getNext());
      if (info.getFleets().getIndex() == 0) {
        MissionHandling.cleanMissions(info);
        game.getStarMap().setAIFleet(null);
        game.getStarMap()
            .setAiTurnNumber(game.getStarMap().getAiTurnNumber() + 1);
      }

    }
    if (info != null && !info.isHuman()
        && game.getStarMap().getAIFleet() != null) {
      // Handle fleet

      Fleet fleet = game.getStarMap().getAIFleet();
      if (fleet != null) {
        MissionHandling.mergeFleets(fleet, info);
        Mission mission = info.getMissions().getMission(MissionType.COLONIZE,
            MissionPhase.PLANNING);
        Mission fleetMission = info.getMissions().getMissionForFleet(
            fleet.getName());
        if (mission != null && fleet.getColonyShip() != null
            && fleetMission == null) {
          Ship ship = fleet.getColonyShip();
          Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
          fleet.removeShip(ship);
          info.getFleets().add(newFleet);
          fleet = newFleet;
          fleet.setName(info.getFleets().generateUniqueName("Colony"));
          info.getFleets().recalculateList();
          mission.setPhase(MissionPhase.LOADING);
          mission.setFleetName(fleet.getName());
        }
        mission = info.getMissions().getMission(MissionType.DEPLOY_STARBASE,
            MissionPhase.PLANNING);
        if (mission != null && fleet.getStarbaseShip() != null
            && fleetMission == null) {
          Ship ship = fleet.getStarbaseShip();
          Fleet newFleet = new Fleet(ship, fleet.getX(), fleet.getY());
          fleet.removeShip(ship);
          info.getFleets().add(newFleet);
          fleet = newFleet;
          fleet.setName(info.getFleets().generateUniqueName("Deep space"));
          info.getFleets().recalculateList();
          mission.setPhase(MissionPhase.TREKKING);
          mission.setFleetName(fleet.getName());
        }
        if (!fleet.isStarBaseDeployed()) {
          handleMissions(fleet, info);
        }
      }
      game.getStarMap().setAIFleet(info.getFleets().getNext());
      if (info.getFleets().getIndex() == 0) {
        MissionHandling.cleanMissions(info);
        if (!info.isBoard()) {
          // All fleets have moved. Checking the new possible planet
          searchPlanetsForMissions();
          // Searching for fleet which has crossed the borders
          searchForBorderCrossing();
          searchDeepSpaceAnchors();
        }
        game.getStarMap().setAIFleet(null);
        game.getStarMap()
            .setAiTurnNumber(game.getStarMap().getAiTurnNumber() + 1);
      }

    }

  }

  /**
   * Add random pirate tech
   * @param pirates PlayerInfo for board player
   * @param type Tech Type
   * @param level Tech level
   */
  private void addRandomPirateTech(final PlayerInfo pirates,
      final TechType type, final int level) {
    TechList techList = pirates.getTechList();
    Tech tech = TechFactory.createRandomTech(type, level,
        techList.getListForTypeAndLevel(type, level));
    if (tech != null) {
      techList.addTech(tech);
    }
  }

  /**
   * Update and add more pirates to starmap
   * @param pirates Board player info
   * @param level to update
   * @param justAddMore If true this will add just pirate military ships
   * @return True if added pirates
   */
  public boolean updateSpacePirates(final PlayerInfo pirates,
      final int level, final boolean justAddMore) {
    if (!justAddMore) {
      addRandomPirateTech(pirates, TechType.Combat, level);
      addRandomPirateTech(pirates, TechType.Combat, level);
      addRandomPirateTech(pirates, TechType.Combat, level);
      addRandomPirateTech(pirates, TechType.Defense, level);
      addRandomPirateTech(pirates, TechType.Defense, level);
      addRandomPirateTech(pirates, TechType.Propulsion, level);
      addRandomPirateTech(pirates, TechType.Propulsion, level);
      addRandomPirateTech(pirates, TechType.Improvements, level);
      addRandomPirateTech(pirates, TechType.Improvements, level);
      addRandomPirateTech(pirates, TechType.Hulls, level);
      addRandomPirateTech(pirates, TechType.Hulls, level);
      addRandomPirateTech(pirates, TechType.Electrics, level);
      addRandomPirateTech(pirates, TechType.Electrics, level);
      Research.handleShipDesigns(pirates);
    }
    boolean added = false;
    int numberOfFleets = pirates.getFleets().getNumberOfFleets();
    for (int i = 0; i < numberOfFleets; i++) {
      Fleet fleet = pirates.getFleets().getByIndex(i);
      if (fleet.isStarBaseDeployed()) {
        game.getStarMap().addSpacePirate(fleet.getX(), fleet.getY(), pirates);
        added = true;
        if (!justAddMore) {
          game.getStarMap().addSpacePirateLair(fleet.getX(), fleet.getY(),
              pirates);
          added = true;
        }
      }
    }
    return added;
  }

  /**
   * Update and add more pirates to starmap
   * @param pirates Board player info
   * @param newState Current state which
   * @param justAddMore If true this will add just pirate military ships
   * @return True if added pirates
   */
  public boolean updateSpacePirates(final PlayerInfo pirates,
      final GameLengthState newState, final boolean justAddMore) {
    if (newState == GameLengthState.EARLY_GAME && !justAddMore) {
      addRandomPirateTech(pirates, TechType.Combat, 2);
      addRandomPirateTech(pirates, TechType.Combat, 3);
      addRandomPirateTech(pirates, TechType.Combat, 3);
      addRandomPirateTech(pirates, TechType.Defense, 2);
      addRandomPirateTech(pirates, TechType.Defense, 3);
      addRandomPirateTech(pirates, TechType.Defense, 3);
      addRandomPirateTech(pirates, TechType.Improvements, 2);
      addRandomPirateTech(pirates, TechType.Propulsion, 2);
      addRandomPirateTech(pirates, TechType.Propulsion, 2);
      addRandomPirateTech(pirates, TechType.Propulsion, 3);
      addRandomPirateTech(pirates, TechType.Propulsion, 3);
      addRandomPirateTech(pirates, TechType.Hulls, 3);
      addRandomPirateTech(pirates, TechType.Hulls, 3);
      addRandomPirateTech(pirates, TechType.Electrics, 1);
      addRandomPirateTech(pirates, TechType.Electrics, 1);
      addRandomPirateTech(pirates, TechType.Electrics, 2);
      addRandomPirateTech(pirates, TechType.Electrics, 2);
    }
    if (newState == GameLengthState.MIDDLE_GAME && !justAddMore) {
      addRandomPirateTech(pirates, TechType.Combat, 4);
      addRandomPirateTech(pirates, TechType.Combat, 4);
      addRandomPirateTech(pirates, TechType.Combat, 4);
      addRandomPirateTech(pirates, TechType.Combat, 5);
      addRandomPirateTech(pirates, TechType.Combat, 5);
      addRandomPirateTech(pirates, TechType.Combat, 5);
      addRandomPirateTech(pirates, TechType.Defense, 4);
      addRandomPirateTech(pirates, TechType.Defense, 4);
      addRandomPirateTech(pirates, TechType.Defense, 5);
      addRandomPirateTech(pirates, TechType.Defense, 5);
      addRandomPirateTech(pirates, TechType.Improvements, 2);
      addRandomPirateTech(pirates, TechType.Improvements, 3);
      addRandomPirateTech(pirates, TechType.Propulsion, 4);
      addRandomPirateTech(pirates, TechType.Propulsion, 4);
      addRandomPirateTech(pirates, TechType.Propulsion, 5);
      addRandomPirateTech(pirates, TechType.Propulsion, 5);
      addRandomPirateTech(pirates, TechType.Hulls, 4);
      addRandomPirateTech(pirates, TechType.Hulls, 4);
      addRandomPirateTech(pirates, TechType.Hulls, 4);
      addRandomPirateTech(pirates, TechType.Hulls, 5);
      addRandomPirateTech(pirates, TechType.Electrics, 4);
      addRandomPirateTech(pirates, TechType.Electrics, 4);
      addRandomPirateTech(pirates, TechType.Electrics, 5);
    }
    if (newState == GameLengthState.LATE_GAME && !justAddMore) {
      addRandomPirateTech(pirates, TechType.Combat, 5);
      addRandomPirateTech(pirates, TechType.Combat, 5);
      addRandomPirateTech(pirates, TechType.Combat, 6);
      addRandomPirateTech(pirates, TechType.Combat, 6);
      addRandomPirateTech(pirates, TechType.Combat, 6);
      Tech tech = pirates.getTechList().getBestWeapon();
      if (tech.getLevel() < 6) {
        addRandomPirateTech(pirates, TechType.Combat, 6);
      }
      addRandomPirateTech(pirates, TechType.Defense, 5);
      addRandomPirateTech(pirates, TechType.Defense, 6);
      addRandomPirateTech(pirates, TechType.Defense, 6);
      addRandomPirateTech(pirates, TechType.Improvements, 3);
      addRandomPirateTech(pirates, TechType.Improvements, 4);
      addRandomPirateTech(pirates, TechType.Propulsion, 5);
      addRandomPirateTech(pirates, TechType.Propulsion, 6);
      addRandomPirateTech(pirates, TechType.Propulsion, 6);
      addRandomPirateTech(pirates, TechType.Propulsion, 6);
      addRandomPirateTech(pirates, TechType.Hulls, 5);
      addRandomPirateTech(pirates, TechType.Hulls, 5);
      addRandomPirateTech(pirates, TechType.Hulls, 6);
      addRandomPirateTech(pirates, TechType.Electrics, 5);
      addRandomPirateTech(pirates, TechType.Electrics, 6);
      addRandomPirateTech(pirates, TechType.Electrics, 6);
    }
    if (newState == GameLengthState.END_GAME && !justAddMore) {
      addRandomPirateTech(pirates, TechType.Combat, 7);
      addRandomPirateTech(pirates, TechType.Combat, 7);
      addRandomPirateTech(pirates, TechType.Combat, 7);
      addRandomPirateTech(pirates, TechType.Combat, 8);
      addRandomPirateTech(pirates, TechType.Combat, 8);
      addRandomPirateTech(pirates, TechType.Combat, 8);
      addRandomPirateTech(pirates, TechType.Defense, 7);
      addRandomPirateTech(pirates, TechType.Defense, 7);
      addRandomPirateTech(pirates, TechType.Defense, 8);
      addRandomPirateTech(pirates, TechType.Defense, 8);
      addRandomPirateTech(pirates, TechType.Improvements, 6);
      addRandomPirateTech(pirates, TechType.Improvements, 7);
      addRandomPirateTech(pirates, TechType.Propulsion, 7);
      addRandomPirateTech(pirates, TechType.Propulsion, 7);
      addRandomPirateTech(pirates, TechType.Propulsion, 8);
      addRandomPirateTech(pirates, TechType.Propulsion, 8);
      addRandomPirateTech(pirates, TechType.Hulls, 6);
      addRandomPirateTech(pirates, TechType.Hulls, 7);
      addRandomPirateTech(pirates, TechType.Hulls, 7);
      addRandomPirateTech(pirates, TechType.Hulls, 8);
      addRandomPirateTech(pirates, TechType.Hulls, 8);
      addRandomPirateTech(pirates, TechType.Electrics, 7);
      addRandomPirateTech(pirates, TechType.Electrics, 7);
      addRandomPirateTech(pirates, TechType.Electrics, 8);
      addRandomPirateTech(pirates, TechType.Electrics, 8);
    }
    if (!justAddMore) {
      Research.handleShipDesigns(pirates);
    }
    boolean added = false;
    int numberOfFleets = pirates.getFleets().getNumberOfFleets();
    for (int i = 0; i < numberOfFleets; i++) {
      Fleet fleet = pirates.getFleets().getByIndex(i);
      if (fleet.isStarBaseDeployed()) {
        game.getStarMap().addSpacePirate(fleet.getX(), fleet.getY(), pirates);
        added = true;
        if (!justAddMore) {
          game.getStarMap().addSpacePirateLair(fleet.getX(), fleet.getY(),
              pirates);
          added = true;
        }
      }
    }
    return added;
  }

  /**
   * Get Promise bonus for single vote.
   * @param voter Realms who is voting
   * @param realms All the realms
   * @return Bonus for voting decision.
   */
  private int getPromiseBonus(final PlayerInfo voter, final PlayerList realms) {
    int result = 0;
    int voterIndex = realms.getIndex(voter);
    for (int i = 0; i < realms.getCurrentMaxRealms(); i++) {
      if (i != voterIndex) {
        PlayerInfo info = realms.getPlayerInfoByIndex(i);
        DiplomacyBonusList bonusList = info.getDiplomacy().getDiplomacyList(
            voterIndex);
        if (bonusList != null) {
          if (bonusList.isBonusType(DiplomacyBonusType.PROMISED_VOTE_YES)) {
            int bonus = voter.getDiplomacy().getLiking(i);
            if (bonus > 0) {
              if (voter.getDiplomacy().isAlliance(i)) {
                bonus = bonus + 2;
              }
              if (voter.getDiplomacy().isDefensivePact(i)) {
                bonus = bonus + 2;
              }
              if (voter.getDiplomacy().isTradeAlliance(i)) {
                bonus = bonus + 1;
              }
              result = result + bonus * 5;
            }
          }
          if (bonusList.isBonusType(DiplomacyBonusType.PROMISED_VOTE_NO)) {
            int bonus = voter.getDiplomacy().getLiking(i);
            if (bonus > 0) {
              if (voter.getDiplomacy().isAlliance(i)) {
                bonus = bonus + 2;
              }
              if (voter.getDiplomacy().isDefensivePact(i)) {
                bonus = bonus + 2;
              }
              if (voter.getDiplomacy().isTradeAlliance(i)) {
                bonus = bonus + 1;
              }
              result = result - bonus * 5;
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * Do actual voting for AI players. Calculates vote for human player too.
   * @param vote Vote
   * @param realms All the realms
   */
  private void doVoting(final Vote vote, final PlayerList realms) {
    for (int i = 0; i < realms.getCurrentMaxRealms(); i++) {
      int numberOfVotes = game.getStarMap().getTotalNumberOfPopulation(i);
      vote.setNumberOfVotes(i, numberOfVotes);
      PlayerInfo info = realms.getPlayerInfoByIndex(i);
      if (!info.isHuman()) {
        int value = StarMapUtilities.getVotingSupport(info, vote,
            game.getStarMap());
        value = value + getPromiseBonus(info, realms);
        if (value < 0) {
          vote.setChoice(i, VotingChoice.VOTED_NO);
        } else if (value > 0) {
          vote.setChoice(i, VotingChoice.VOTED_YES);
        } else {
          value = DiceGenerator.getRandom(1);
          if (value == 0) {
            vote.setChoice(i, VotingChoice.VOTED_YES);
          } else {
            vote.setChoice(i, VotingChoice.VOTED_NO);
          }
        }
      }
    }
    if (vote.getType() == VotingType.SECOND_CANDIDATE_MILITARY) {
      int drawRuler = game.getStarMap().getVotes().getFirstCandidate();
      VotingChoice result = vote.getResult(drawRuler);
      if (result == VotingChoice.VOTED_YES) {
        int second = game.getStarMap().getNewsCorpData().getMilitary()
            .getBiggest();
        Vote voteSecond = new Vote(VotingType.SECOND_CANDIDATE,
            game.getPlayers().getCurrentMaxRealms(), 0);
        voteSecond.setOrganizerIndex(second);
        game.getStarMap().getVotes().getVotes().add(voteSecond);
      } else if (result == VotingChoice.VOTED_NO) {
        int second = game.getStarMap().getSecondCandidateForTower();
        Vote voteSecond = new Vote(VotingType.SECOND_CANDIDATE,
            game.getPlayers().getCurrentMaxRealms(), 0);
        voteSecond.setOrganizerIndex(second);
        game.getStarMap().getVotes().getVotes().add(voteSecond);
      }
    }
    if (vote.getType() == VotingType.GALACTIC_PEACE) {
      int drawRuler = game.getStarMap().getVotes().getFirstCandidate();
      VotingChoice result = vote.getResult(drawRuler);
      if (result == VotingChoice.VOTED_YES) {
        for (int i = 0; i < realms.getCurrentMaxRealms(); i++) {
          PlayerInfo info = realms.getPlayerInfoByIndex(i);
          for (int j = 0; j < realms.getCurrentMaxRealms(); j++) {
            if (info.getDiplomacy().isWar(j)) {
              DiplomaticTrade trade = new DiplomaticTrade(game.getStarMap(), i,
                 j);
              trade.generateEqualTrade(NegotiationType.PEACE);
              trade.doTrades();
              PlayerInfo defender = game.getStarMap().getPlayerByIndex(j);
              NewsData newsData = NewsFactory.makePeaceNews(info, defender,
                  null);
              game.getStarMap().getHistory().addEvent(
                  NewsFactory.makeDiplomaticEvent(null, newsData));
              game.getStarMap().getNewsCorpData().addNews(newsData);
              info.getMissions().removeAttackAgainstPlayer(defender,
                  game.getStarMap());
              defender.getMissions().removeAttackAgainstPlayer(info,
                  game.getStarMap());
            }
          }
        }
      }
    }
  }

  /**
   * Handle promises. This should be called after voting.
   * @param vote Actual vote
   * @param realms All the realms
   */
  private void handlePromises(final Vote vote, final PlayerList realms) {
    for (int i = 0; i < realms.getCurrentMaxRealms(); i++) {
      PlayerInfo info = realms.getPlayerInfoByIndex(i);
      for (int j = 0; j < realms.getCurrentMaxRealms(); j++) {
        DiplomacyBonusList bonusList = info.getDiplomacy().getDiplomacyList(j);
        if (bonusList != null) {
          bonusList.checkPromise(vote.getChoice(j), info.getRace());
        }
      }
    }
  }
  /**
   * Handle diplomatic votes like arrange new votes and handle the end game.
   * @param towers How many tower each realm has
   */
  private void handleDiplomaticVotes(final int[] towers) {
    if (game.getStarMap().getScoreDiplomacy() == 0) {
      // Diplomacy voting has been disabled.
      return;
    }
    if (game.getStarMap().getVotes().firstCandidateSelected()
        && game.getStarMap().getVotes().getNextImportantVote() == null) {
      int turns = game.getStarMap().getScoreVictoryTurn() * 5 / 100;
      Vote vote = game.getStarMap().getVotes().generateNextVote(
          game.getStarMap().getScoreDiplomacy() + 1,
          game.getStarMap().getPlayerList().getCurrentMaxRealms(), turns);
      if (vote != null) {
        game.getStarMap().getVotes().getVotes().add(vote);
        NewsData news = NewsFactory.makeVotingNews(vote);
        game.getStarMap().getNewsCorpData().addNews(news);
      } else {
        ErrorLogger.log("Next vote was null!");
      }
    } else {
      int mostTowers = -1;
      int towerCount = 0;
      int secondIndex = -1;
      int towerLimit = StarMapUtilities.calculateRequireTowerLimit(
          game.getStarMap().getMaxX(), game.getStarMap().getMaxY());
      boolean tie = false;
      for (int i = 0; i < towers.length; i++) {
        if (towers[i] >= towerLimit) {
          if (towers[i] > towerCount && mostTowers != i) {
            mostTowers = i;
            towerCount = towers[i];
            tie = false;
          } else if (towers[i] == towerCount && mostTowers != -1) {
            tie = true;
            secondIndex = i;
          }
        }
      }
      if (!tie && mostTowers != -1) {
        Vote vote = new Vote(VotingType.FIRST_CANDIDATE,
            game.getPlayers().getCurrentMaxRealms(), 0);
        vote.setOrganizerIndex(mostTowers);
        PlayerInfo secretary = game.getPlayers().getPlayerInfoByIndex(
            mostTowers);
        NewsData news = NewsFactory.makeSecretaryOfGalaxyNews(secretary);
        game.getStarMap().getNewsCorpData().addNews(news);
        game.getStarMap().getVotes().getVotes().add(vote);
        int turns = game.getStarMap().getScoreVictoryTurn() * 5 / 100;
        vote = game.getStarMap().getVotes().generateNextVote(
            game.getStarMap().getScoreDiplomacy() + 1,
            game.getStarMap().getPlayerList().getCurrentMaxRealms(), turns);
        if (vote != null) {
          if (vote.getType() == VotingType.RULER_OF_GALAXY) {
            // First vote is rule of galaxy,
            // so second candidate is strongest military
            int second = game.getStarMap().getNewsCorpData().getMilitary()
                .getBiggest();
            Vote voteSecond = new Vote(VotingType.SECOND_CANDIDATE,
                game.getPlayers().getCurrentMaxRealms(), 0);
            voteSecond.setOrganizerIndex(second);
            game.getStarMap().getVotes().getVotes().add(voteSecond);
            vote.setSecondCandidateIndex(second);
          }
          // Vote has been already added to list in generateNextVote()
          news = NewsFactory.makeVotingNews(vote);
          game.getStarMap().getNewsCorpData().addNews(news);
        } else {
          ErrorLogger.log("First vote was null!");
        }
      }
      if (tie && game.getStarMap().getTurn() % 10 == 0) {
        PlayerInfo first = game.getPlayers().getPlayerInfoByIndex(mostTowers);
        PlayerInfo second = game.getPlayers().getPlayerInfoByIndex(
            secondIndex);
        NewsData news = NewsFactory.makeUnitedGalaxyTowerRaceTie(first,
            second);
        game.getStarMap().getNewsCorpData().addNews(news);
      }
    }
    Vote vote = game.getStarMap().getVotes().getNextImportantVote();
    if (vote != null && vote.getTurnsToVote() > 0) {
      vote.setTurnsToVote(vote.getTurnsToVote() - 1);
      if (vote.getTurnsToVote() == 0) {
        doVoting(vote, game.getPlayers());
        handlePromises(vote, game.getPlayers());
      }
    }
  }

  /**
   * Remove banned ship desigsn
   * @param info PlayerInfo
   * @param banNukes True if nukes are banned
   * @param banPrivateer true if privateers are banned
   */
  public void removeBannedShipDesigns(final PlayerInfo info,
      final boolean banNukes, final boolean banPrivateer) {
    if (!banNukes || !banPrivateer) {
      return;
    }
    for (ShipStat stat : info.getShipStatList()) {
      if (banNukes && stat.getDesign().isNuclearBomberShip()) {
        stat.setObsolete(true);
      }
      if (banPrivateer && stat.getDesign().isPrivateer()) {
        stat.setObsolete(true);
      }
    }

  }
  /**
   * Update whole star map to next turn
   */
  public void updateStarMapToNextTurn() {
    game.getStarMap().resetCulture();
    for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
      PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
      if (info != null) {
        if (!info.isBoard()) {
          removeBannedShipDesigns(info,
              game.getStarMap().getVotes().areNukesBanned(),
              game.getStarMap().getVotes().arePrivateersBanned());
        }
        info.getDiplomacy().updateDiplomacyLastingForTurn();
        info.resetVisibilityDataAfterTurn();
        info.getMsgList().clearMessages();
        for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = info.getFleets().getByIndex(j);
          if (fleet.getRoute() != null) {
            if (fleet.getRoute().isFixing()) {
              Planet planet = game.getStarMap()
                  .getPlanetByCoordinate(fleet.getX(), fleet.getY());
              if (planet != null && planet.getPlanetPlayerInfo() == info
                  && planet.hasSpacePort()) {
                // Fully repair ships if planet has space port
                fleet.fixFleetShips(true);
              } else {
                fleet.fixFleetShips(false);
              }

              if (fleet.allFixed()) {
                fleet.setRoute(null);
                Message msg = new Message(MessageType.FLEET,
                    fleet.getName()
                        + " has been fixed and waiting for orders.",
                    Icons.getIconByName(Icons.ICON_HULL_TECH));
                msg.setMatchByString(fleet.getName());
                msg.setCoordinate(fleet.getCoordinate());
                info.getMsgList().addNewMessage(msg);
              }
            } else if (fleet.getMovesLeft() > 0
                && !fleet.isStarBaseDeployed()) {
              // Make sure fleet can actually move
              if (fleet.getRoute().makeNextMove(game.getStarMap())
                  && !game.getStarMap().isBlocked(fleet.getRoute().getX(),
                  fleet.getRoute().getY())
                  && !game.getStarMap().isDangerous(fleet.getRoute().getX(),
                      fleet.getRoute().getY())) {
                // Not blocked so fleet is moving
                MissionHandling.makeFleetMove(game, fleet.getRoute().getX(),
                    fleet.getRoute().getY(), info, fleet);
                if (fleet.getRoute() != null && fleet.getRoute().isEndReached()
                    && info.getMissions().getMissionForFleet(
                        fleet.getName()) == null) {
                  // End is reached giving a message
                  fleet.setRoute(null);
                  Message msg = new Message(MessageType.FLEET,
                      fleet.getName()
                          + " has reached it's target and waiting for orders.",
                      Icons.getIconByName(Icons.ICON_HULL_TECH));
                  msg.setMatchByString(fleet.getName());
                  msg.setCoordinate(fleet.getCoordinate());
                  info.getMsgList().addNewMessage(msg);
                }
              } else {
                fleet.setRoute(null);
                if (info.getMissions().getMissionForFleet(
                    fleet.getName()) == null) {
                  // Movement was blocked, giving a message
                  Message msg = new Message(MessageType.FLEET,
                      fleet.getName()
                      + " has encouter obstacle and waiting for more orders.",
                      Icons.getIconByName(Icons.ICON_HULL_TECH));
                  msg.setMatchByString(fleet.getName());
                  msg.setCoordinate(fleet.getCoordinate());
                  info.getMsgList().addNewMessage(msg);
                }
              }
            }

          } else if (info.getMissions().getMissionForFleet(
              fleet.getName()) == null) {
            Message msg = new Message(MessageType.FLEET,
                fleet.getName() + " is waiting for orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setMatchByString(fleet.getName());
            msg.setCoordinate(fleet.getCoordinate());
            info.getMsgList().addNewMessage(msg);
          }
          if (fleet.isStarBaseDeployed()) {
            fleet.setMovesLeft(0);
            // handle starbase specific components
            for (Ship ship : fleet.getShips()) {
               info.setTotalCredits(info.getTotalCredits()
                   + ship.getTotalCreditBonus());
               ship.setCulture(ship.getCulture()
                   + ship.getTotalCultureBonus());
               // Research is done in starmap
               // see getTotalProductionByPlayerPerTurn method.
               // This is called in AITurnView handleActions which updates
               // Research
            }
          } else {
            fleet.setMovesLeft(fleet.getFleetSpeed());
          }
          game.getStarMap().doFleetScanUpdate(info, fleet, null);
          if (fleet.getCulturalValue() > 0 && fleet.isStarBaseDeployed()) {
         // Recalculate culture for the map for each player
            game.getStarMap().calculateCulture(fleet.getX(), fleet.getY(),
                fleet.getCulturalValue(), i);
          }
        }
      }
    }
    int[] numberOfPlanets = new int[game.getStarMap().getPlayerList()
                                    .getCurrentMaxRealms()];
    int[] towers = new int[game.getStarMap().getPlayerList()
                           .getCurrentMaxRealms()];
    for (int i = 0; i < game.getStarMap().getPlanetList().size(); i++) {
      Planet planet = game.getStarMap().getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        numberOfPlanets[planet.getPlanetOwnerIndex()]++;
        if (planet.hasTower()) {
          towers[planet.getPlanetOwnerIndex()]++;
        }
        boolean enemyOrbiting = false;
        Fleet fleetOrbiting = game.getStarMap().getFleetByCoordinate(
            planet.getX(), planet.getY());
        if (fleetOrbiting != null) {
          PlayerInfo enemy = game.getStarMap().getPlayerInfoByFleet(
              fleetOrbiting);
          if (enemy != info) {
            enemyOrbiting = true;
          }
        }
        // Update each planet one by one
        planet.updateOneTurn(enemyOrbiting, game.getStarMap());
        int index = game.getPlayers().getIndex(info);
        if (index > -1) {
          // Recalculate culture for the map for each player
          game.getStarMap().calculateCulture(planet.getX(), planet.getY(),
              planet.getCulture(), index);
        }
        // Fleets and planets do the scan
        game.getStarMap().doFleetScanUpdate(info, null, planet);
      }
    }
    handleDiplomaticVotes(towers);
    boolean terminateNews = false;
    for (int i = 0; i < numberOfPlanets.length; i++) {
      if (numberOfPlanets[i] == 0) {
        boolean lost = false;
        for (int j = 0;
            j < game.getStarMap().getPlayerList().getCurrentMaxRealms(); j++) {
          PlayerInfo info = game.getStarMap().getPlayerList()
              .getPlayerInfoByIndex(j);
          DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(i);
          if (list != null) {
            lost = list.addBonus(DiplomacyBonusType.REALM_LOST,
                info.getRace());
            if (lost) {
              PlayerInfo realm = game.getStarMap().getPlayerList()
                  .getPlayerInfoByIndex(i);
              if (!terminateNews) {
                terminateNews = true;
                NewsData news = NewsFactory.makeLostNews(realm);
                game.getStarMap().getNewsCorpData().addNews(news);
              }
            }
          }
        }
      }
    }
    game.getStarMap().updateEspionage();
    game.getStarMap().getHistory().updateCultureEventMap(game.getStarMap());
    GameLengthState oldState = game.getStarMap().getGameLengthState();
    game.getStarMap().setTurn(game.getStarMap().getTurn() + 1);
    GameLengthState newState = game.getStarMap().getGameLengthState();
    PlayerInfo board = game.getPlayers().getBoardPlayer();
    boolean pirateNews = false;
    if (oldState != newState && board != null && game.getStarMap()
        .getScoreVictoryTurn() <= 400) {
      pirateNews = updateSpacePirates(board, newState, false);
    }
    if (game.getStarMap().getTurn() % 100 == 0
        && board != null && game.getStarMap().getScoreVictoryTurn() > 400
        && !pirateNews) {
      int level = game.getStarMap().getScoreVictoryTurn() / 100;
      level++;
      if (level >= 10) {
        level = 10;
      }
      pirateNews = updateSpacePirates(board, level, false);
    }
    if (game.getStarMap().getTurn() % 50 == 0 && !pirateNews
        && board != null) {
      // Just adding more pirates
      pirateNews = updateSpacePirates(board, newState, true);
    }
    if (pirateNews) {
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      NewsData news = NewsFactory.makeSpacePiratesNews(game.getStarMap());
      newsData.addNews(news);
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
    }
    game.getStarMap().getHistory().addTurn(game.getStarMap().getTurn());
    if (game.getStarMap().getTurn() % NewsCorpData.NEWS_PUBLISH_RATE == 0) {
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      newsData.calculateCredit(game.getStarMap().getPlayerList());
      newsData.calculateCulture(game.getStarMap().getPlanetList(),
          game.getStarMap().getPlayerList());
      boolean lastTurn = false;
      if (game.getStarMap().getTurn() == game.getStarMap()
          .getScoreVictoryTurn()) {
        lastTurn = true;
      }
      game.getStarMap().getPlayerList().handlePlayerFakeMilitaryCost();
      newsData.calculateMilitary(game.getStarMap().getPlayerList(), lastTurn);
      newsData.calculateResearch(game.getStarMap().getPlayerList());
      newsData.calculatePlanets(game.getStarMap().getPlanetList());
      newsData.calculatePopulation(game.getStarMap().getPlanetList());
      NewsData news = NewsFactory.makeStatNews(game.getStarMap());
      newsData.addNews(news);
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
    }
    if (game.getStarMap().getTurn() == game.getStarMap()
        .getScoreVictoryTurn() / 2) {
      // Game is in halfway
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      NewsData news = NewsFactory.makeScoreNewsHalf(
          game.getStarMap());
      newsData.addNews(news);
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
    }
    if (game.getStarMap().getTurn() == game.getStarMap()
        .getScoreVictoryTurn() * 3 / 4) {
      // Game is in last quarter
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      NewsData news = NewsFactory.makeScoreNewsLastQuarter(
          game.getStarMap());
      newsData.addNews(news);
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
    }
    NewsData news = NewsFactory.makeCulturalVictoryNewsAtEnd(
        game.getStarMap());
    if (news != null) {
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
      game.getStarMap().setGameEnded(true);
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      newsData.addNews(news);
    }
    news = NewsFactory.makeDominationVictoryNewsAtEnd(game.getStarMap());
    if (news != null) {
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
      game.getStarMap().setGameEnded(true);
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      newsData.addNews(news);
    }
    news = NewsFactory.makeScientificVictoryNewsAtEnd(game.getStarMap());
    if (news != null) {
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
      game.getStarMap().setGameEnded(true);
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      newsData.addNews(news);
    }
    if (game.getStarMap().getTurn() == game.getStarMap()
        .getScoreVictoryTurn()) {
      // Game is in the end
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      news = NewsFactory.makeScoreNewsAtEnd(game.getStarMap());
      newsData.addNews(news);
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
      game.getStarMap().setGameEnded(true);
    }
    game.getStarMap().getNewsCorpData().clearNewsList();
  }

  /**
   * Handle warning about low credit flow
   * @param info Player to war
   * @param creditFlow Current credit flow
   */
  protected static void handleLowCreditWarning(final PlayerInfo info,
      final int creditFlow) {
    if (creditFlow < 0 && info.getTotalCredits() <= -5 * creditFlow) {
      Message msg = new Message(MessageType.INFORMATION,
          "Your realm is running low on credits!",
          Icons.getIconByName(Icons.ICON_CREDIT));
      info.getMsgList().addNewMessage(msg);
    }
  }
  /**
   * Handle Ai Turn
   * @return True when turn has finished or need to change state
   */
  public boolean handleAiTurn() {
    if (game.getStarMap().getAIFleet() == null) {
      game.getStarMap().handleAIResearchAndPlanets();
      game.getStarMap().handleFakingMilitarySize();
    } else {
      handleAIFleet();
      if (getNextState() != null) {
        return true;
      }
      if (game.getGameState() != GameState.AITURN) {
        // If last player is calling diplomacy screen
        // it is not run due the line 556. There fore
        // we need to exit at this point!
        return true;
      }
    }
    if (game.getStarMap().isAllAIsHandled()) {
      updateStarMapToNextTurn();
      for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
        // Handle player research at end of turn
        PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
        info.getTechList().updateResearchPointByTurn(game.getStarMap()
            .getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH, i),
            info, game.getStarMap().getScoreVictoryTurn());
        int creditFlow = game.getStarMap().getTotalProductionByPlayerPerTurn(
            Planet.PRODUCTION_CREDITS, i);
        handleLowCreditWarning(info, creditFlow);
        // Handle war fatigue for player
        GovernmentType government = info.getGovernment();
        if (!government.isImmuneToHappiness()) {
          boolean fatigued = false;
          int wars = info.getDiplomacy().getNumberOfWar();
          int warFatigueValue = info.getWarFatigue()
              / info.getRace().getWarFatigueResistance();
          if (wars > 0 && wars > government.getWarResistance()) {
            int fatigue = info.getWarFatigue();
            fatigue = fatigue - wars + government.getWarResistance();
            fatigued = true;
            info.setWarFatigue(fatigue);
          }
          if (info.getTotalCredits() < 0) {
            int fatigue = info.getWarFatigue();
            fatigue = fatigue + info.getTotalCredits() * 5;
            info.setWarFatigue(fatigue);
            info.setTotalCredits(0);
            fatigued = true;
            Message msg = new Message(MessageType.INFORMATION,
                "Realm credits has run out. This increased unhappiness!",
                Icons.getIconByName(Icons.ICON_CREDIT));
            info.getMsgList().addNewMessage(msg);
          }
          if (!fatigued && info.getWarFatigue() < 0) {
            int fatigue = info.getWarFatigue();
            int dec = fatigue / 4;
            if (dec > 0) {
              dec = -1;
            }
            fatigue = fatigue - dec;
            info.setWarFatigue(fatigue);
            if (info.getWarFatigue() == 0) {
              Message msg = new Message(MessageType.INFORMATION,
                  "War fatigue has ended!",
                  Icons.getIconByName(Icons.ICON_HAPPY));
              info.getMsgList().addNewMessage(msg);
            }
          } else {
            int warFatigueValueAfter = info.getWarFatigue()
                / info.getRace().getWarFatigueResistance();
            if (warFatigueValueAfter < warFatigueValue) {
              Message msg = new Message(MessageType.INFORMATION,
                  "People is getting more tired of war!",
                  Icons.getIconByName(Icons.ICON_SAD));
              info.getMsgList().addNewMessage(msg);
            }
          }
        } else {
          if (info.getTotalCredits() < 0) {
            int fatigue = info.getWarFatigue();
            fatigue = fatigue + info.getTotalCredits();
            info.setWarFatigue(fatigue);
            info.setTotalCredits(0);
            Message msg = new Message(MessageType.INFORMATION,
                "Realm credits has run out."
                + " This will cause building to collapse!",
                Icons.getIconByName(Icons.ICON_CREDIT));
            info.getMsgList().addNewMessage(msg);
          }
        }
      }
      game.getStarMap().clearAITurn();
      return true;
    }
    return false;
  }
  /**
   * Handle actions for AI Turn view
   * Since AI Turn View can be null while handling the all the AI. AI handling
   * variables are stored in StarMap. These variables are AIFleet and
   * AITurnNumber.
   * @param arg0 ActionEvent
   */
  public synchronized void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
      updateText();
      if (!aiThread.isStarted()) {
        aiThread.start();
      }
      if (!aiThread.isRunning() && aiThread.isStarted()) {
        if (getNextState() != null) {
          game.changeGameState(getNextState(), getNextStateObject());
          setNextState(null, null);
          return;
        }
        if (readyToMove) {
          if (game.getStarMap().getNewsCorpData().isNewsToShow()) {
            game.changeGameState(GameState.NEWS_CORP_VIEW);
          } else {
            game.changeGameState(GameState.STARMAP);
          }
        }
      }
    }
  }

  /**
   * Is AI Turn view's AI thread is running
   * @return True if AI thread is being run, otherwise false
   */
  public boolean isThreaded() {
    if (aiThread.isRunning() && aiThread.isStarted()) {
      return true;
    }
    return false;
  }

  /**
   * Get next state.
   * @return the nextState
   */
  public GameState getNextState() {
    return nextState;
  }

  /**
   * Get next state object
   * @return the nextStateObject
   */
  public Object getNextStateObject() {
    return nextStateObject;
  }

  /**
   * Set next game state
   * @param state the nextState to set
   * @param dataObject the nextState data object
   */
  public void setNextState(final GameState state, final Object dataObject) {
    this.nextState = state;
    this.nextStateObject = dataObject;
  }

}
