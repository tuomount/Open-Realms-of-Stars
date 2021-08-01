package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import org.openRealmOfStars.AI.AiThread;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionHandling;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
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
import org.openRealmOfStars.player.WinningStrategy;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
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
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.MilitaryRank;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.KarmaType;
import org.openRealmOfStars.starMap.PirateDifficultLevel;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.Row;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.ScoreBoard;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.randomEvent.RandomEvent;
import org.openRealmOfStars.starMap.randomEvent.RandomEventUtility;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.Votes;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.Athlete;
import org.openRealmOfStars.starMap.vote.sports.Sports;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

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
  private static final int LIMIT_COLONIZATIONS = 6;
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
    BufferedImage orbital = null;
    if (DiceGenerator.getRandom(99) < 33) {
      if (DiceGenerator.getRandom(99) < 70) {
        planet.setOrbital(ShipGenerator.generateRandomOrbital());
      } else {
        orbital = PlanetTypes.getRandomPlanetType(false, true, true).getImage();
      }
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Enemy turn");
    if (orbital != null) {
      imgBase.setCustomOrbital(orbital);
    }
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
      case ESPIONAGE_MISSION:
        MissionHandling.handleEspionageMission(mission, fleet, info, game);
        break;
      case DIPLOMATIC_DELEGACY:
        MissionHandling.handleDiplomaticMission(mission, fleet, info, game);
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
      if (fleet.isTradeFleet()) {
        mission = info.getMissions().getMission(MissionType.TRADE_FLEET,
            MissionPhase.PLANNING);
        if (mission != null) {
          Planet planet = game.getStarMap().getPlanetByCoordinate(fleet.getX(),
              fleet.getY());
          if (planet == null) {
            // Trade ship is not on home port
            planet = game.getStarMap().getClosestHomePort(info,
                fleet.getCoordinate());
            if (planet != null) {
              mission = new Mission(MissionType.MOVE, MissionPhase.PLANNING,
                  planet.getCoordinate());
              mission.setFleetName(fleet.getName());
              mission.setTargetPlanet(planet.getName());
              mission.setTarget(planet.getCoordinate());
              info.getMissions().add(mission);
              // Moving trade ship to nearest home port
              return;
            }
            // Trade ship does not have home port
            // Odds look pretty bad now...
            // Just returning
            return;
          }
          mission.setPhase(MissionPhase.LOADING);
          mission.setFleetName(fleet.getName());
          mission.setPlanetBuilding(planet.getName());
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
      if (fleet.getNumberOfShip() == 0
          && fleet.getCommander() != null) {
        fleet.getCommander().assignJob(Job.UNASSIGNED, info);
        fleet.setCommander(null);
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
    String attackFleetName = "Attacker of " +  mission.getTargetPlanet();
    mission.setFleetName(attackFleetName);
    if (info.getMissions().getDestroyStarbaseMission(
        "Starbase " + coordinate.toString()) == null) {
      // No Destroy starbase mission for this starbase found, so adding it.
      info.getMissions().add(mission);
      addGatherMission(info, mission);
    }
  }

  /**
   * Find closest coordinate
   * @param coordinates List of coordinates
   * @param coordinate Coordinate where to start looking
   * @return Closest coordinate or null
   */
  private Coordinate findClosestCoordinate(
      final ArrayList<Coordinate> coordinates, final Coordinate coordinate) {
    double bestDistance = 9999;
    Coordinate bestCoord = null;
    for (Coordinate coord : coordinates) {
      double dist = coordinate.calculateDistance(coord);
      if (dist < bestDistance) {
        bestCoord = coord;
        bestDistance = dist;
      }
    }
    return bestCoord;
  }
  /**
   * Find best(Closest) coordinate where to deploy starbase
   * @param coordinates Deep space anchor coordinates in list
   * @param info Realm looking for DSA
   */
  private void findBestDeployStarbase(final ArrayList<Coordinate> coordinates,
      final PlayerInfo info) {
    Coordinate coord = findClosestCoordinate(coordinates,
        info.getCenterRealm());
    if (coord != null) {
      Mission mission = new Mission(MissionType.DEPLOY_STARBASE,
          MissionPhase.PLANNING, coord);
      info.getMissions().add(mission);
    }
  }
  /**
   * Find best(Closest) coordinate where to destroy starbase
   * @param coordinates Deep space anchor coordinates in list
   * @param info Realm looking for DSA
   */
  private void findBestDestroyStarbase(final ArrayList<Coordinate> coordinates,
      final PlayerInfo info) {
    Coordinate coord = findClosestCoordinate(coordinates,
        info.getCenterRealm());
    if (coord != null) {
      addDestroyStarbaseMission(coord, info);
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
    ArrayList<Coordinate> deployStarbase = new ArrayList<>();
    ArrayList<Coordinate> destroyStarbase = new ArrayList<>();
    for (int y = 0; y < map.getMaxY(); y++) {
      for (int x = 0; x < map.getMaxX(); x++) {
        Tile tile = map.getTile(x, y);
        if ((tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
            || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2))
            && info.getSectorVisibility(new Coordinate(x, y))
            > PlayerInfo.UNCHARTED) {
          if (fleetTiles[x][y] == null) {
            if (info.getMissions().getDeployStarbaseMission(x, y) == null
                && deployStarbases < LIMIT_DEPLOY_STARBASES) {
              // No deploy starbase mission for this planet found, so adding it.
              Coordinate coord = new Coordinate(x, y);
              deployStarbase.add(coord);
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
              if (info.getMissions().getDeployStarbaseMission(x, y) == null
                  && deployStarbases < LIMIT_DEPLOY_STARBASES) {
                // No deploy starbase mission for this planet found,
                // so adding it.
                Coordinate coord = new Coordinate(x, y);
                deployStarbase.add(coord);
              }
            } else if (fleet.isStarBaseDeployed()
                  && info.getSectorVisibility(new Coordinate(x, y))
                  == PlayerInfo.VISIBLE && infoAt != info) {
                int index = fleetTiles[x][y].getPlayerIndex();
                DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                    index);
                if (list.isBonusType(DiplomacyBonusType.IN_WAR)
                    && destroyStarbases < LIMIT_DESTROY_STARBASES
                    || infoAt.isBoard()) {
                  destroyStarbase.add(new Coordinate(x, y));
                }
            } else if (fleet.isStarBaseDeployed()
                && info.getSectorVisibility(new Coordinate(x, y))
                == PlayerInfo.VISIBLE && infoAt == info) {
              if (info.getMissions().getDeployStarbaseMission(x, y) == null
                  && deployStarbases < LIMIT_DEPLOY_STARBASES
                  && info.isBiggerStarbases()
                  && fleet.getNumberOfShip() < Fleet.MAX_STARBASE_SIZE) {
                // No secondary starbase deploy mission found for this anchor
                Coordinate coord = new Coordinate(x, y);
                deployStarbase.add(coord);
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
                    && destroyStarbases < LIMIT_DESTROY_STARBASES
                    || infoAt.isBoard()) {
                  // AI already has war against that player so,
                  // adding destroy mission
                  destroyStarbase.add(new Coordinate(x, y));
                }
              }
            }
          }
        }
      }
    }
    findBestDeployStarbase(deployStarbase, info);
    findBestDestroyStarbase(destroyStarbase, info);
  }

  /**
   * Get Chance for diplomacy when seeing another realm's fleet
   * inside borders.
   * @param nothingToTrade If there is nothing to trade flag
   * @param info Realm which is deciding if diplomacy should start
   * @param targetIndex Target realm's index
   * @return chance of diplomacy 0 - 100.
   */
  private int getChanceForDiplomacy(final boolean nothingToTrade,
      final PlayerInfo info, final int targetIndex) {
    int result = 0;
    Attitude attitude = info.getAiAttitude();
    switch (attitude) {
      case AGGRESSIVE: result = 10; break;
      case BACKSTABBING: result = 30; break;
      case DIPLOMATIC: result = 60; break;
      case EXPANSIONIST: result = 30; break;
      case LOGICAL: result = 40; break;
      case MERCHANTICAL: result = 50; break;
      case MILITARISTIC: result = 10; break;
      case PEACEFUL: result = 50; break;
      case SCIENTIFIC: result = 30; break;
      default: result = 30; break;
    }
    if (info.getTotalWarFatigue() < -2
        && info.getDiplomacy().isWar(targetIndex)) {
      result = result + 50;
    }
    if (info.getStrategy() == WinningStrategy.DIPLOMATIC) {
      result = result + 20;
    }
    if (info.getStrategy() == WinningStrategy.CULTURAL) {
      result = result + 20;
    }
    if (result > 100) {
      result = 100;
    }
    if (nothingToTrade) {
      result = 0;
    }
    return result;
  }
  /**
   * Search for interceptable fleets on current AI's sectors.
   */
  public void searchForInterceptFleets() {
    PlayerInfo info = game.getPlayers()
        .getPlayerInfoByIndex(game.getStarMap().getAiTurnNumber());
    if (info != null && !info.isHuman() && !info.isBoard()) {
      info.cleanInterceptableFleetList();
      int maxPlayer = game.getPlayers().getCurrentMaxRealms();
      for (int i = 0; i < maxPlayer; i++) {
        PlayerInfo fleetOwner = game.getPlayers().getPlayerInfoByIndex(i);
        if (fleetOwner != info && info.getDiplomacy().isWar(i)) {
          int numberOfFleets = fleetOwner.getFleets().getNumberOfFleets();
          for (int j = 0; j < numberOfFleets; j++) {
            Fleet fleet = fleetOwner.getFleets().getByIndex(j);
            int detect = info.getSectorCloakDetection(fleet.getX(),
                fleet.getY());
            byte visibility = info.getSectorVisibility(fleet.getCoordinate());
            if (detect >= fleet.getFleetCloackingValue()
                && visibility == PlayerInfo.VISIBLE) {
              CulturePower culture = game.getStarMap().getSectorCulture(
                  fleet.getX(), fleet.getY());
              if (culture.getHighestCulture() == game.getStarMap()
                  .getAiTurnNumber()) {
                info.addInterceptableFleet(fleet);
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
                int fleetOwnerIndex = game.getPlayers().getIndex(fleetOwner);
                int type = Diplomacy.getBorderCrossingType(info, fleet,
                    fleetOwnerIndex);
                boolean nothingToTrade = info.getDiplomacy().getDiplomacyList(
                    fleetOwnerIndex).isBonusType(
                    DiplomacyBonusType.NOTHING_TO_TRADE);
                if (info.getDiplomacy().isTradeAlliance(i) && military == 0
                    && type != DiplomacyView.AI_ESPIONAGE) {
                  // Non military ship and trade alliance
                  int chance = getChanceForDiplomacy(nothingToTrade, info,
                      fleetOwnerIndex);
                  if (DiceGenerator.getRandom(99) > chance) {
                    continue;
                  }
                }
                if ((info.getDiplomacy().isAlliance(i)
                    || info.getDiplomacy().isDefensivePact(i))
                    && military >= 0
                    && type != DiplomacyView.AI_ESPIONAGE) {
                  // (non)military ship and alliance
                  int chance = getChanceForDiplomacy(nothingToTrade, info,
                      fleetOwnerIndex);
                  if (DiceGenerator.getRandom(99) > chance) {
                    continue;
                  }
                }
                if (info.getDiplomacy().isWar(i)) {
                  // War no diplomacy screen then
                  int chance = getChanceForDiplomacy(nothingToTrade, info,
                      fleetOwnerIndex);
                  if (DiceGenerator.getRandom(99) > chance) {
                    continue;
                  }
                }
                if (fleet.isPrivateerFleet()) {
                  continue;
                }
                if (fleetOwner.isHuman()) {
                  SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
                  game.changeGameState(GameState.DIPLOMACY_VIEW, fleet);
                  return;
                }
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
   * Find best colony mission for realm.
   * @param map StarMap
   * @param colonyMissions Array list of possible colony missions.
   * @param info Realm which is selecting.
   */
  private void findBestColonyMission(final StarMap map,
      final ArrayList<Mission> colonyMissions, final PlayerInfo info) {
    int bestScore = 0;
    Mission bestMission = null;
    for (Mission mission : colonyMissions) {
      Planet planet = map.getPlanetByCoordinate(mission.getX(), mission.getY());
      if (planet != null) {
        int worldValue = info.getWorldTypeValue(
            planet.getPlanetType().getWorldType());
        int size = planet.getGroundSize();
        double dist = info.getCenterRealm().calculateDistance(
            new Coordinate(mission.getX(), mission.getY()));
        double maxDistPoints = map.getMaxX() / 4;
        int score = (int) (maxDistPoints - dist);
        if (score < -10) {
          score = -10;
        }
        score = score + size * 2;
        worldValue = (worldValue - 50) / 3;
        score = score + worldValue;
        if (score > bestScore) {
          bestScore = score;
          bestMission = mission;
        }
      }
    }
    if (bestMission != null) {
      info.getMissions().addHighestPriority(bestMission);
      Mission mission = info.getMissions().getMission(
          MissionType.COLONY_EXPLORE, MissionPhase.EXECUTING);
      if (mission != null) {
        // Colony ship was exploring, calling it back to home
        mission.setPhase(MissionPhase.TREKKING);
      }
    }
  }

  /**
   * Find closest planet from list which is closest for coordinate
   * @param planets Array list of possible planets.
   * @param coordinate Coordinate where to calculate distance
   * @return Closest planet or null if list was empty.
   */
  private Planet findClosestPlanet(
      final ArrayList<Planet> planets, final Coordinate coordinate) {
    double bestDistance = 9999;
    Planet bestPlanet = null;
    for (Planet planet : planets) {
      double dist = coordinate.calculateDistance(planet.getCoordinate());
      if (dist < bestDistance) {
        bestPlanet = planet;
        bestDistance = dist;
      }
    }
    return bestPlanet;
  }

  /**
   * Find best(Closest) planet where to attack.
   * @param planets Planet list
   * @param info Realm looking for planet
   */
  private void findBestAttackPlanet(final ArrayList<Planet> planets,
      final PlayerInfo info) {
    Planet planet = findClosestPlanet(planets, info.getCenterRealm());
    if (planet != null) {
      addAttackMission(planet, info);
    }
  }
  /**
   * Find best(Closest) planet where to trade.
   * @param planets Planet list
   * @param info Realm looking for planet
   */
  private void findBestTradePlanet(final ArrayList<Planet> planets,
      final PlayerInfo info) {
    Planet planet = findClosestPlanet(planets, info.getCenterRealm());
    if (planet != null) {
      addTradeMission(planet, info);
    }
  }
  /**
   * Handle Space pirate search for black hole.
   */
  public void searchForBlackHole() {
    PlayerInfo board = game.getPlayers().getBoardPlayer();
    if (board != null && !board.getTechList().hasTech("Tractor beam")) {
      StarMap map = game.getStarMap();
      int centerx = map.getMaxX() / 2;
      int centery = map.getMaxY() / 2;
      Coordinate center = new Coordinate(centerx, centery);
      int closest = -1;
      double distance = 9999.0;
      for (int i = 0; i < board.getFleets().getNumberOfFleets(); i++) {
        Fleet fleet = board.getFleets().getByIndex(i);
        double value = fleet.getCoordinate().calculateDistance(center);
        if (value < distance) {
          closest = i;
          distance = value;
        }
      }
      if (closest != -1) {
        Fleet fleet = board.getFleets().getByIndex(closest);
        Mission mission = board.getMissions().getMissionForFleet(
            fleet.getName());
        if (mission != null) {
          mission.setTarget(center);
          mission.setType(MissionType.MOVE);
          mission.setPhase(MissionPhase.TREKKING);
        } else {
          mission = new Mission(MissionType.MOVE, MissionPhase.TREKKING,
              center);
          mission.setFleetName(fleet.getName());
          board.getMissions().add(mission);
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
          MissionType.COLONIZE, MissionPhase.PLANNING);
      int attacks = info.getMissions().getNumberOfMissionTypes(
          MissionType.ATTACK);
      int maxRad = info.getRace().getMaxRad();
      if (info.getTechList().isTech("Radiation dampener")) {
        maxRad++;
      }
      if (info.getTechList().isTech("Radiation well")) {
        maxRad++;
      }
      ArrayList<Mission> colonyMissions = new ArrayList<>();
      ArrayList<Planet> attackMissions = new ArrayList<>();
      ArrayList<Planet> tradeMissions = new ArrayList<>();
      for (Planet planet : planets) {
        if (planet.getTotalRadiationLevel() <= maxRad
            && planet.getPlanetPlayerInfo() == null && !planet.isGasGiant()
            && info.getSectorVisibility(planet.getCoordinate())
            == PlayerInfo.VISIBLE) {
          // New planet to colonize, adding it to mission list
          Mission mission = new Mission(MissionType.COLONIZE,
              MissionPhase.PLANNING, planet.getCoordinate());
          if (info.getMissions().getColonizeMission(mission.getX(),
              mission.getY()) == null && colonizations < LIMIT_COLONIZATIONS) {
            // No colonize mission for this planet found, so adding it.
            colonyMissions.add(mission);
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
            colonyMissions.add(mission);
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
              attackMissions.add(planet);
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
                tradeMissions.add(planet);
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
              attackMissions.add(planet);
            }
            if (list != null
                && (list.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
                || list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
                || list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
              // Got new map part maybe in trade and found planet owned by
              // player which is being in alliance
              tradeMissions.add(planet);
            }
          }
          if (info.getStrategy() == WinningStrategy.CONQUER
              && planet.getHomeWorldIndex() > -1) {
            int index1 = game.getStarMap().getPlayerList().getIndex(info);
            int index2 = planet.getPlanetOwnerIndex();
            int neededDifference = 150;
            if (info.getDiplomacy().isAlliance(index2)) {
              neededDifference = 1200;
            }
            if (info.getDiplomacy().isDefensivePact(index2)) {
              neededDifference = 600;
            }
            if (info.getDiplomacy().isTradeAlliance(index2)) {
              neededDifference = 300;
            }
            switch (info.getAiAttitude()) {
              case MILITARISTIC:
              case BACKSTABBING:
              case AGGRESSIVE: neededDifference = neededDifference / 4; break;
              case EXPANSIONIST:
              case LOGICAL: neededDifference = neededDifference / 2; break;
              case DIPLOMATIC:
              case MERCHANTICAL:
              case PEACEFUL:
              case SCIENTIFIC:
              default: {
                neededDifference = neededDifference - 10;
                break;
              }
            }
            if (game.getStarMap().getMilitaryDifference(
                index1, index2) > neededDifference && attacks < LIMIT_ATTACKS) {
              addAttackMission(planet, info);
            }
          }
        } // End of owned planet handling
      } // End of for loop of planets
      findBestColonyMission(game.getStarMap(), colonyMissions, info);
      findBestAttackPlanet(attackMissions, info);
      findBestTradePlanet(tradeMissions, info);
    }
  }

  /**
   * Get closes intercept mission.
   * @param origin Origin fleet which doing intercept mission
   * @param info Realm who is planning interceptiong
   * @param map Starmap
   * @return Interceptable fleet or null none available.
   */
  public Fleet getClosestInterceptMission(final Fleet origin,
      final PlayerInfo info, final StarMap map) {
    int military = origin.getMilitaryValue();
    int speed = origin.getMovesLeft();
    if (!origin.allFixed() || info.isHuman()) {
      return null;
    }
    Mission mission = info.getMissions().getMissionForFleet(origin.getName());
    if (mission != null && (mission.getType() == MissionType.COLONIZE
         || mission.getType() == MissionType.COLONY_EXPLORE
         || mission.getType() == MissionType.DEPLOY_STARBASE
         || mission.getType() == MissionType.DIPLOMATIC_DELEGACY
         || mission.getType() == MissionType.TRADE_FLEET)) {
      // This missions will not be detoured for intercepting
      return null;
    }

    for (Fleet fleet : info.getInterceptableFleets()) {
      if (fleet.getMilitaryValue() < military) {
        double dist = fleet.getCoordinate().calculateDistance(
            origin.getCoordinate());
        if (dist <= speed) {
          AStarSearch search = new AStarSearch(map, origin.getX(),
              origin.getY(), fleet.getX(), fleet.getY(), true);
          if (search.doSearch()) {
            if (mission != null
                && mission.getType() == MissionType.DEFEND
                && mission.getPhase() == MissionPhase.EXECUTING) {
              Planet planet = map.getPlanetByCoordinate(origin.getX(),
                  origin.getY());
              if (planet != null) {
                // Defending fleet will not leave post if there is
                // another attacking fleet too close the planet
                for (Fleet attacker : info.getInterceptableFleets()) {
                  if (fleet != attacker) {
                    int topSpeed = attacker.getFleetFtlSpeed();
                    if (attacker.getFleetSpeed() > topSpeed) {
                      topSpeed = attacker.getFleetSpeed();
                    }
                    if (attacker.getCoordinate().calculateDistance(
                        planet.getCoordinate()) < topSpeed) {
                      // Another fleet is too close
                      return null;
                    }
                  }
                }
              }
              mission.setPhase(MissionPhase.TREKKING);
            }
            if (mission != null
                && mission.getType() == MissionType.SPY_MISSION
                && mission.getPhase() == MissionPhase.EXECUTING) {
              mission.setPhase(MissionPhase.TREKKING);
            }
            return fleet;
          }
        }
      }
    }
    return null;
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
        if (fleet.isStarBaseDeployed() && fleet.getRoute() == null) {
          fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(),
              fleet.getY(), Route.ROUTE_DEFEND));
        }
        Mission mission = info.getMissions().getMission(MissionType.COLONIZE,
            MissionPhase.PLANNING);
        Mission fleetMission = info.getMissions().getMissionForFleet(
            fleet.getName());
        if (mission != null && fleet.getColonyShip() != null
            && fleetMission == null) {
          Ship ship = fleet.getColonyShip();
          if (fleet.getNumberOfShip() == 0
              && fleet.getCommander() != null) {
            fleet.getCommander().assignJob(Job.UNASSIGNED, info);
            fleet.setCommander(null);
          }
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
          if (fleet.getNumberOfShip() == 0
              && fleet.getCommander() != null) {
            fleet.getCommander().assignJob(Job.UNASSIGNED, info);
            fleet.setCommander(null);
          }
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
          Fleet interceptFleet = getClosestInterceptMission(
              game.getStarMap().getAIFleet(), info, game.getStarMap());
          if (interceptFleet != null) {
            Mission intercept = new Mission(MissionType.INTERCEPT,
                MissionPhase.TREKKING, interceptFleet.getCoordinate());
            MissionHandling.handleIntercept(intercept,
                game.getStarMap().getAIFleet(), info, game);
          } else {
            handleMissions(game.getStarMap().getAIFleet(), info);
          }
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
        } else {
          searchForBlackHole();
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
   * Update single pirate tech category
   * @param pirates Board player info
   * @param difficulty Pirate difficulty level
   * @param type Tech Type which to upgrade
   */
  private void updateSinglePirateTech(final PlayerInfo pirates,
      final PirateDifficultLevel difficulty, final TechType type) {
    int level = pirates.getTechList().getTechLevel(type);
    if (pirates.getTechList().isUpgradeable(type)) {
      level++;
      if (level > 10) {
        level = 10;
      }
    }
    // Very easy does not get tech upgrades
    if (difficulty == PirateDifficultLevel.EASY
        && DiceGenerator.getRandom(100) < 50) {
      addRandomPirateTech(pirates, type, level);
    } else if (difficulty == PirateDifficultLevel.NORMAL) {
      addRandomPirateTech(pirates, type, level);
    } else if (difficulty == PirateDifficultLevel.HARD) {
      addRandomPirateTech(pirates, type, level);
      if (DiceGenerator.getRandom(100) < 50) {
        addRandomPirateTech(pirates, type, level);
      }
    } else if (difficulty == PirateDifficultLevel.VERY_HARD) {
      addRandomPirateTech(pirates, type, level);
      addRandomPirateTech(pirates, type, level);
      if (DiceGenerator.getRandom(100) < 50) {
        addRandomPirateTech(pirates, type, level);
      }
    }
  }
  /**
   * Update and add more pirates to starmap.
   * @param pirates Board player info
   * @param difficulty Pirate Difficulty Level
   * @param addLairs add pirate lairs only
   * @return True if pirate ships were added.
   */
  public boolean updateSpacePirates(final PlayerInfo pirates,
      final PirateDifficultLevel difficulty, final boolean addLairs) {
    updateSinglePirateTech(pirates, difficulty, TechType.Combat);
    updateSinglePirateTech(pirates, difficulty, TechType.Defense);
    updateSinglePirateTech(pirates, difficulty, TechType.Propulsion);
    updateSinglePirateTech(pirates, difficulty, TechType.Improvements);
    updateSinglePirateTech(pirates, difficulty, TechType.Hulls);
    updateSinglePirateTech(pirates, difficulty, TechType.Electrics);
    Research.handleShipDesigns(pirates);
    boolean added = false;
    int numberOfFleets = pirates.getFleets().getNumberOfFleets();
    for (int i = 0; i < numberOfFleets; i++) {
      Fleet fleet = pirates.getFleets().getByIndex(i);
      if (fleet != null && fleet.isStarBaseDeployed()) {
        added = true;
        if (addLairs) {
          game.getStarMap().addSpacePirateLair(fleet.getX(), fleet.getY(),
              pirates);
        } else {
          game.getStarMap().addSpacePirateLair(fleet.getX(), fleet.getY(),
              pirates);
          game.getStarMap().addSpacePirate(fleet.getX(), fleet.getY(),
              pirates);
        }
      }
    }
    return added;
  }

  /**
   * Update and add more pirates to starmap.
   * @param pirates Board player info
   * @param difficulty Pirate Difficulty Level
   * @param turnNumber Turn number
   * @return True if pirate ships were added.
   */
  public boolean updateSpacePirates(final PlayerInfo pirates,
      final PirateDifficultLevel difficulty, final int turnNumber) {
    boolean result = false;
    if (difficulty == PirateDifficultLevel.VERY_EASY) {
      if (turnNumber == 50) {
        result = updateSpacePirates(pirates, difficulty, true);
      } else if (turnNumber % 50 == 0) {
        result = updateSpacePirates(pirates, difficulty, false);
      }
    }
    if (difficulty == PirateDifficultLevel.EASY) {
      if (turnNumber == 50) {
        result = updateSpacePirates(pirates, difficulty, true);
      } else if (turnNumber % 50 == 0) {
        result = updateSpacePirates(pirates, difficulty, false);
      }
    }
    if (difficulty == PirateDifficultLevel.NORMAL) {
      if (turnNumber == 50) {
        result = updateSpacePirates(pirates, difficulty, true);
      } else if (turnNumber == 100) {
        result = updateSpacePirates(pirates, difficulty, false);
      } else if (turnNumber > 100 && turnNumber % 40 == 0) {
        result = updateSpacePirates(pirates, difficulty, false);
      }
    }
    if (difficulty == PirateDifficultLevel.HARD) {
      if (turnNumber % 50 == 0) {
        result = updateSpacePirates(pirates, difficulty, true);
      } else if (turnNumber > 40 && turnNumber % 40 == 0) {
        result = updateSpacePirates(pirates, difficulty, false);
      }
    }
    if (difficulty == PirateDifficultLevel.VERY_HARD) {
      if (turnNumber % 50 == 0) {
        result = updateSpacePirates(pirates, difficulty, false);
      } else if (turnNumber % 40 == 0) {
        result = updateSpacePirates(pirates, difficulty, true);
      }
    }
    return result;
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
        if (game.getStarMap().getVotes().getFirstCandidate() == second) {
          second = game.getStarMap().getNewsCorpData().getMilitary()
              .getSecond();
        }
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
    if (game.getStarMap().getVotes().firstCandidateSelected()) {
      if (game.getStarMap().getVotes().getNextImportantVote() == null) {
        int turns = game.getStarMap().getScoreVictoryTurn() * 5 / 100;
        Vote vote = game.getStarMap().getVotes().generateNextVote(
            game.getStarMap().getScoreDiplomacy() + 1,
            game.getStarMap().getPlayerList().getCurrentMaxRealms(), turns);
        if (vote != null) {
          // Vote has been already added to list in generateNextVote()
          NewsData news = null;
          if (vote.getType() == VotingType.RULER_OF_GALAXY) {
            PlayerInfo firstCandidate = game.getStarMap().getPlayerByIndex(
                game.getStarMap().getVotes().getFirstCandidate());
            PlayerInfo secondCandidate = game.getStarMap().getPlayerByIndex(
                game.getStarMap().getVotes().getSecondCandidate());
            news = NewsFactory.makeVotingNews(vote, firstCandidate,
                secondCandidate);

          } else {
            news = NewsFactory.makeVotingNews(vote, null, null);
          }
          game.getStarMap().getNewsCorpData().addNews(news);
          GalacticEvent event = new GalacticEvent(news.getNewsText());
          game.getStarMap().getHistory().addEvent(event);
        } else {
          ErrorLogger.log("Next vote was null!");
        }
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
            if (game.getStarMap().getVotes().getFirstCandidate() == second) {
              second = game.getStarMap().getNewsCorpData().getMilitary()
                  .getSecond();
            }
            Vote voteSecond = new Vote(VotingType.SECOND_CANDIDATE,
                game.getPlayers().getCurrentMaxRealms(), 0);
            voteSecond.setOrganizerIndex(second);
            game.getStarMap().getVotes().getVotes().add(voteSecond);
            vote.setSecondCandidateIndex(second);
          }
          // Vote has been already added to list in generateNextVote()
          if (vote.getType() == VotingType.RULER_OF_GALAXY) {
            PlayerInfo firstCandidate = game.getStarMap().getPlayerByIndex(
                game.getStarMap().getVotes().getFirstCandidate());
            PlayerInfo secondCandidate = game.getStarMap().getPlayerByIndex(
                game.getStarMap().getVotes().getSecondCandidate());
            news = NewsFactory.makeVotingNews(vote, firstCandidate,
                secondCandidate);
          } else {
            news = NewsFactory.makeVotingNews(vote, null, null);
          }
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
      if (vote.getTurnsToVote() == 1) {
        for (int i = 0;
            i < game.getStarMap().getPlayerList().getCurrentMaxRealms(); i++) {
          Message msg = new Message(MessageType.INFORMATION,
              "One turn left for voting, remember make your vote.",
              Icons.getIconByName(Icons.ICON_CULTURE));
          PlayerInfo info = game.getStarMap().getPlayerList()
              .getPlayerInfoByIndex(i);
          info.getMsgList().addNewMessage(msg);
        }
      }
      if (vote.getTurnsToVote() == 0) {
        doVoting(vote, game.getPlayers());
        handlePromises(vote, game.getPlayers());
        NewsData news = null;
        if (vote.getType() == VotingType.RULER_OF_GALAXY) {
          PlayerInfo firstCandidate = game.getStarMap().getPlayerByIndex(
              game.getStarMap().getVotes().getFirstCandidate());
          PlayerInfo secondCandidate = game.getStarMap().getPlayerByIndex(
              game.getStarMap().getVotes().getSecondCandidate());
          VotingChoice choice = vote.getResult(
              game.getStarMap().getVotes().getFirstCandidate());
          news = NewsFactory.makeVotingEndedNews(vote, choice, firstCandidate,
              secondCandidate);
        } else {
          VotingChoice choice = vote.getResult(
              game.getStarMap().getVotes().getFirstCandidate());
          news = NewsFactory.makeVotingEndedNews(vote, choice, null, null);
        }
        game.getStarMap().getNewsCorpData().addNews(news);
        GalacticEvent event = new GalacticEvent(news.getNewsText());
        game.getStarMap().getHistory().addEvent(event);

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
   * Handle olympic games.
   * @param map StarMap
   */
  private static void handleOlympicGames(final StarMap map) {
    for (Vote vote : map.getVotes().getVotableVotes()) {
      if (vote.getTurnsToVote() > 0
          && vote.getType() == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
        if (vote.getTurnsToVote() == 1) {
          vote.setTurnsToVote(0);
          Planet[] bestPlanets = new Planet[
              map.getPlayerList().getCurrentMaxRealms()];
          for (Planet planet : map.getPlanetList()) {
            if (planet.getPlanetOwnerIndex() != -1
                && planet.getPlanetOwnerIndex() < bestPlanets.length) {
              int index = planet.getPlanetOwnerIndex();
                if (bestPlanets[index] == null) {
                  bestPlanets[index] = planet;
                } else if (planet.getTroopPower() > bestPlanets[index]
                    .getTroopPower()) {
                  bestPlanets[index] = planet;
                }
            }
          }
          Sports sports = new Sports();
          for (int i = 0; i < bestPlanets.length; i++) {
            if (vote.getChoice(i) == VotingChoice.VOTED_YES
                && bestPlanets[i] != null) {
              Athlete athlete = new Athlete(bestPlanets[i].getName(),
                  bestPlanets[i].getPlanetPlayerInfo());
              athlete.setBonus(bestPlanets[i].getTroopPowerBonus());
              sports.add(athlete);
            }
          }
          sports.handleSports();
          Planet planet = map.getPlanetByName(vote.getPlanetName());
          if (planet != null && sports.getAthletes().length > 0) {
            NewsData newsData = NewsFactory.makeGalacticSportsEndingNews(vote,
                sports, planet);
            map.getNewsCorpData().addNews(newsData);
            EventOnPlanet event = new EventOnPlanet(EventType.PLANET_BUILDING,
                planet.getCoordinate(), planet.getName(),
                planet.getPlanetOwnerIndex());
            event.setText(newsData.getNewsText());
            map.getHistory().addEvent(event);
            // Organizer gains culture 10 per each realm participated
            planet.setCulture(
                planet.getCulture() + sports.getAthletes().length * 10);
            planet = map.getPlanetByName(
                sports.getAthletes()[0].getPlanetName());
            if (planet != null) {
              // Winning planet gains culture 10
              planet.setCulture(
                  planet.getCulture() + 10);
            }
          }
          handleOlympicDiplomacyBonus(vote, map.getPlayerList());
        } else {
          vote.setTurnsToVote(vote.getTurnsToVote() - 1);
          Planet planet = map.getPlanetByName(vote.getPlanetName());
          if (planet != null) {
            NewsData newsData = null;
            if (planet.howManyBuildings("Galactic sports center") == 0) {
              newsData = NewsFactory.makeNoGalacticSportsNews(planet, true);
            }
            if (planet.getTotalPopulation() == 0) {
              newsData = NewsFactory.makeNoGalacticSportsNews(planet, false);
            }
            if (newsData != null) {
              map.getNewsCorpData().addNews(newsData);
              EventOnPlanet event = new EventOnPlanet(
                  EventType.PLANET_BUILDING, planet.getCoordinate(),
                  planet.getName(), planet.getPlanetOwnerIndex());
              event.setText(newsData.getNewsText());
              map.getHistory().addEvent(event);
            }
          }
        }
      }
    }
  }

  /**
   * Handle Olympic games diplomacy bonus.
   * @param vote Olympic game vote
   * @param playerList PlayerList
   */
  public static void handleOlympicDiplomacyBonus(final Vote vote,
      final PlayerList playerList) {
    PlayerInfo organizer = playerList.getPlayerInfoByIndex(
        vote.getOrganizerIndex());
    ArrayList<Integer> embargoList = new ArrayList<>();
    if (organizer != null) {
      for (int i = 0; i < playerList.getCurrentMaxRealms();
          i++) {
        if (i == vote.getOrganizerIndex()) {
          continue;
        }
        if (vote.getChoice(i) == VotingChoice.VOTED_NO) {
          organizer.getDiplomacy().getDiplomacyList(i).addBonus(
              DiplomacyBonusType.DNS_OLYMPICS, organizer.getRace());
          embargoList.add(i);
        } else if (vote.getChoice(i) == VotingChoice.VOTED_YES) {
          organizer.getDiplomacy().getDiplomacyList(i).addBonus(
              DiplomacyBonusType.OLYMPICS, organizer.getRace());
          PlayerInfo info = playerList.getPlayerInfoByIndex(i);
          if (info != null) {
            info.getDiplomacy().getDiplomacyList(
                vote.getOrganizerIndex()).addBonus(
                    DiplomacyBonusType.OLYMPICS, info.getRace());
          }
        }
      }
    }
    if (embargoList.size() > 0) {
      for (int i = 0; i < embargoList.size(); i++) {
        PlayerInfo info = playerList.getPlayerInfoByIndex(embargoList.get(i));
        if (info != null) {
          for (int j = 0; j < embargoList.size(); j++) {
            if (i != j) {
              info.getDiplomacy().getDiplomacyList(embargoList.get(j)).addBonus(
                  DiplomacyBonusType.OLYMPICS_EMBARGO, info.getRace());
            }
          }
        }
      }
    }

  }
  /**
   * Handle olympic participation voting.
   * This may alter voting choices in ongoing participation
   * votes of galactic olympic games.
   * @param votes Votes list
   * @param players PlayerList
   */
  public static void handleOlympicParticipation(final Votes votes,
      final PlayerList players) {
    for (Vote vote : votes.getVotableVotes()) {
      if (vote.getTurnsToVote() > 0
          && vote.getType() == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
        VotingChoice[] oldChoices = new VotingChoice[
            players.getCurrentMaxRealms()];
        for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
          oldChoices[i] = vote.getChoice(i);
        }
        int organizer = vote.getOrganizerIndex();
        for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
          PlayerInfo info = players.getPlayerInfoByIndex(i);
          if (info != null) {
            if (info.isHuman()) {
              vote.setNumberOfVotes(i, 1);
              continue;
            }
            if (i == organizer) {
              vote.setChoice(i, VotingChoice.VOTED_YES);
              vote.setNumberOfVotes(i, 1);
              continue;
            }
            int participateBonus = info.getDiplomacy().getLiking(organizer)
                * 10;
            if (info.getDiplomacy().isWar(organizer)) {
              participateBonus = participateBonus - 25;
            }
            if (info.getDiplomacy().isTradeEmbargo(organizer)) {
              participateBonus = participateBonus - 20;
            }
            if (info.getDiplomacy().isTradeAlliance(organizer)) {
              participateBonus = participateBonus + 10;
            }
            if (info.getDiplomacy().isDefensivePact(organizer)) {
              participateBonus = participateBonus + 15;
            }
            if (info.getDiplomacy().isAlliance(organizer)) {
              participateBonus = participateBonus + 20;
            }
            if (participateBonus == 0 && vote.getTurnsToVote() > 1
              && DiceGenerator.getRandom(vote.getTurnsToVote()) > 0) {
                continue;
            }
            for (int j = 0; j < oldChoices.length; j++) {
              if (i != j) {
                if (oldChoices[j] == VotingChoice.VOTED_YES) {
                  participateBonus = participateBonus
                      + info.getDiplomacy().getLiking(organizer) * 3;
                }
                if (oldChoices[j] == VotingChoice.VOTED_NO) {
                  participateBonus = participateBonus
                      - info.getDiplomacy().getLiking(organizer) * 3;
                }
              }
            }
            if (participateBonus > 0) {
              vote.setChoice(i, VotingChoice.VOTED_YES);
              vote.setNumberOfVotes(i, 1);
              continue;
            }
            if (participateBonus < 0) {
              vote.setChoice(i, VotingChoice.VOTED_NO);
              vote.setNumberOfVotes(i, 1);
              continue;
            }
            if (participateBonus == 0 && vote.getTurnsToVote() == 1) {
              // AI couldn't decide if wants to participate,
              // but no good reason not to join
              vote.setChoice(i, VotingChoice.VOTED_YES);
              vote.setNumberOfVotes(i, 1);
            }
          }
        }
      }
    }
  }

  /**
   * Handle Power hungry leader trying to kill ruler.
   * @param leader Power hungry leader
   * @param realm Which ruler is being targeted
   */
  private void handlePowerHungryKill(final Leader leader,
      final PlayerInfo realm) {
    // Power hungry leaders try to kill current leader
    // Calculating chance for killing
    int chance = 5;
    if (leader.getAge() > realm.getRuler().getAge()) {
      chance = chance + leader.getAge() - realm.getRuler().getAge();
    }
    if (leader.hasPerk(Perk.AGGRESSIVE)) {
      chance = chance + 5;
    }
    if (leader.hasPerk(Perk.CORRUPTED)) {
      chance = chance + 3;
    }
    if (leader.hasPerk(Perk.MAD)) {
      chance = chance + 5;
    }
    if (leader.hasPerk(Perk.MILITARISTIC)) {
      chance = chance + 5;
    }
    if (leader.hasPerk(Perk.ADDICTED)) {
      chance = chance + 3;
    }
    if (leader.hasPerk(Perk.LOGICAL)) {
      chance = chance - 3;
    }
    if (leader.hasPerk(Perk.DIPLOMATIC)) {
      chance = chance - 3;
    }
    if (leader.hasPerk(Perk.PEACEFUL)) {
      chance = chance - 5;
    }
    if (leader.hasPerk(Perk.SECRET_AGENT)) {
      chance = chance + 3;
    }
    if (leader.hasPerk(Perk.STUPID)) {
      chance = chance + 5;
    }
    if (leader.hasPerk(Perk.INCOMPETENT)) {
      chance = chance + 3;
    }
    if (leader.hasPerk(Perk.WARLORD)) {
      chance = chance + 5;
    }
    if (leader.hasPerk(Perk.PACIFIST)) {
      chance = 0;
    }
    if (DiceGenerator.getRandom(1, 100) < chance) {
      // Assassination happens
      if (realm.getRuler().hasPerk(Perk.WEALTHY)) {
        Message msg = new Message(MessageType.LEADER,
            realm.getRuler().getCallName()
                + " has paid massive amount of credits to make "
                + realm.getRuler().getGender().getHisHer() + " life is"
                + " protected against assasinations.",
            Icons.getIconByName(Icons.ICON_DEATH));
        msg.setMatchByString("Index:" + realm.getLeaderIndex(
            realm.getRuler()));
        realm.getMsgList().addUpcomingMessage(msg);
        realm.getRuler().useWealth();
        // Assassin is going to die because of treason
        leader.setJob(Job.DEAD);
        msg = new Message(MessageType.LEADER,
            leader.getCallName()
                + " has died at age of " + leader.getAge()
                + " due treason against " + realm.getEmpireName()
                + "! ",
            Icons.getIconByName(Icons.ICON_DEATH));
        msg.setMatchByString("Index:" + realm.getLeaderIndex(leader));
        realm.getMsgList().addUpcomingMessage(msg);
        String reason;
        switch (DiceGenerator.getRandom(2)) {
          case 0:
          default: {
            reason = "execution because of treason";
            break;
          }
          case 1: {
              reason = "execution because attempted assasination of "
                 + realm.getRuler().getTitle();
            break;
          }
          case 2: {
              reason = "execution because attempted assasination of "
                 + realm.getRuler().getCallName();
            break;
          }
        }
        NewsData news = NewsFactory.makeLeaderDies(leader, realm,
            reason);
        game.getStarMap().getNewsCorpData().addNews(news);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeLeaderEvent(leader, realm, game.getStarMap(),
            news));

      } else {
        realm.getRuler().setJob(Job.DEAD);
        // Leader gains experience for succeesfull assasination
        leader.setExperience(leader.getExperience() + 50);
        leader.getStats().addOne(StatType.KILLED_ANOTHER_LEADER);
        Message msg = new Message(MessageType.LEADER,
            realm.getRuler().getCallName()
                + " has died at age of " + realm.getRuler().getAge()
                + ". Death was caused by internal power struggle.",
            Icons.getIconByName(Icons.ICON_DEATH));
        msg.setMatchByString("Index:" + realm.getLeaderIndex(
            realm.getRuler()));
        realm.getMsgList().addUpcomingMessage(msg);
        String reason;
        switch (DiceGenerator.getRandom(2)) {
          case 0:
          default: {
            if (realm.getRuler().getRace() == SpaceRace.MECHIONS) {
              reason = "overload of regular expressions";
            } else if (realm.getRuler().getRace() == SpaceRace.REBORGIANS) {
              reason = "burnt cyber interface";
            } else {
              reason = "poison drink";
            }
            break;
          }
          case 1: {
            if (realm.getRuler().getRace() == SpaceRace.MECHIONS
                || realm.getRuler().getRace() == SpaceRace.LITHORIANS) {
              reason = "heavy object crushing the body";
            } else if (realm.getRuler().getRace() == SpaceRace.REBORGIANS) {
              reason = "heavy object smashing the body";
            } else {
              reason = "heavy object hitting "
                  + realm.getRuler().getGender().getHisHer()
                  + " head";
            }
            break;
          }
          case 2: {
            if (realm.getRuler().getRace() == SpaceRace.MECHIONS) {
              reason = "shot to the head";
            } else if (realm.getRuler().getRace() == SpaceRace.REBORGIANS
                || realm.getRuler().getRace() == SpaceRace.LITHORIANS) {
              reason = "explosion to the chest";
            } else {
              reason = "blade in "
                  + realm.getRuler().getGender().getHisHer() + " back";
            }
            break;
          }
        }
        NewsData news = NewsFactory.makeLeaderDies(realm.getRuler(), realm,
            reason);
        game.getStarMap().getNewsCorpData().addNews(news);
        game.getStarMap().getHistory().addEvent(
            NewsFactory.makeLeaderEvent(realm.getRuler(), realm,
                game.getStarMap(), news));
        realm.setRuler(null);
      }
    }
  }
  /**
   * Ruler will free one prisoner.
   * @param realm Realm who is handling leaders currently.
   */
  public void rulerFreePrisoner(final PlayerInfo realm) {
    Leader prisoner = null;
    int bestValue = 0;
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.PRISON) {
        int value = 0;
        switch (leader.getMilitaryRank()) {
          default:
          case CIVILIAN: {
            value = 0;
            break;
          }
          case ENSIGN: {
            value = 1;
            break;
          }
          case LIEUTENANT: {
            value = 2;
            break;
          }
          case CAPTAIN: {
            value = 4;
            break;
          }
          case COLONEL: {
            value = 8;
            break;
          }
          case COMMANDER: {
            value = 16;
            break;
          }
          case ADMIRAL: {
            value = 32;
            break;
          }
        }
        if (leader.getParent() != null) {
          value = value + 8;
        }
        if (leader.hasPerk(Perk.CORRUPTED)) {
          value = value + 2;
        }
        if (leader.hasPerk(Perk.WEALTHY)) {
          value = value + 8;
        }
        if (leader.hasPerk(Perk.PACIFIST)) {
          value = value - 8;
        }
        if (leader.hasPerk(Perk.PEACEFUL)) {
          value = value - 8;
        }
        if (leader.hasPerk(Perk.SPY_MASTER)) {
          value = value + 4;
        }
        if (leader.hasPerk(Perk.INCOMPETENT)) {
          value = value - 16;
        }
        if (leader.hasPerk(Perk.STUPID)) {
          value = value - 4;
        }
        if (leader.hasPerk(Perk.MILITARISTIC)) {
          value = value + 1;
        }
        if (leader.hasPerk(Perk.AGGRESSIVE)) {
          value = value + 1;
        }
        if (value > bestValue) {
          prisoner = leader;
          bestValue = value;
        }
      }
    }
    if (prisoner != null) {
      NewsData news = NewsFactory.makeLeaderFreed(prisoner, realm);
      game.getStarMap().getNewsCorpData().addNews(news);
      game.getStarMap().getHistory().addEvent(
          NewsFactory.makeLeaderEvent(prisoner, realm,
          game.getStarMap(), news));
      prisoner.setJob(Job.UNASSIGNED);
      prisoner.setTimeInJob(0);
      prisoner.addPerk(Perk.CORRUPTED);
      int realmIndex = game.getPlayers().getIndex(realm);
      for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
        if (realmIndex != i) {
          PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
          DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
              realmIndex);
          if (list != null) {
            list.addBonus(DiplomacyBonusType.FREED_CONVICT, info.getRace());
          }
        }
      }
    }
  }
  /**
   * Handle leaders getting older.
   * Handle also ruler leader experience
   * @param realm Realm whose leaders are being handled
   */
  public void handleLeaders(final PlayerInfo realm) {
    Leader heir = null;
    if (realm.getRuler() == null) {
      Leader ruler = LeaderUtility.getNextRuler(realm);
      if (ruler != null) {
        LeaderUtility.assignLeaderAsRuler(ruler, realm, game.getStarMap());
        if (realm.getRuler() != null) {
          realm.getRuler().getStats().addOne(StatType.NUMBER_OF_RULER);
          Message msg = new Message(MessageType.LEADER,
              ruler.getCallName()
                  + " has selected as ruler for " + realm.getEmpireName(),
              Icons.getIconByName(Icons.ICON_RULER));
          msg.setMatchByString("Index:" + realm.getLeaderIndex(ruler));
          realm.getMsgList().addUpcomingMessage(msg);
          NewsData news = NewsFactory.makeNewRulerNews(ruler, realm);
          game.getStarMap().getNewsCorpData().addNews(news);
          game.getStarMap().getHistory().addEvent(
              NewsFactory.makeLeaderEvent(realm.getRuler(), realm,
              game.getStarMap(), news));
          if (Game.getTutorial() != null
              && game.getStarMap().isTutorialEnabled()) {
                String tutorialText = Game.getTutorial().showTutorialText(129);
            if (tutorialText != null) {
              msg = new Message(MessageType.INFORMATION,
                  tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
              realm.getMsgList().addUpcomingMessage(msg);
            }
            tutorialText = Game.getTutorial().showTutorialText(121);
            if (tutorialText != null) {
              msg = new Message(MessageType.INFORMATION,
                  tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
              realm.getMsgList().addUpcomingMessage(msg);
            }
          }
        }
      }
    }
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() != Job.DEAD) {
        // First getting older
        leader.setAge(leader.getAge() + 1);
        if (leader.getJob() == Job.TOO_YOUNG && leader.getAge() >= 18) {
          leader.assignJob(Job.UNASSIGNED, realm);
        }
        if (leader.getJob() == Job.PRISON) {
          leader.setTimeInJob(leader.getTimeInJob() - 1);
          leader.getStats().addOne(StatType.JAIL_TIME);
          if (DiceGenerator.getRandom(99) < 10) {
            // Small change to get little bit experience in prison.
            leader.setExperience(leader.getExperience()
                + DiceGenerator.getRandom(1, 3));
          }
          if (leader.getTimeInJob() <= 0) {
            String change = "";
            if (leader.hasPerk(Perk.CORRUPTED)
                && DiceGenerator.getRandom(99) < 30) {
              // Leader can lose corruption perk if sit whole sentence.
              leader.removeCorruption();
              change = " " + leader.getName()
                  + " has learned valuable lesson while in prison.";
            }
            Message msg = new Message(MessageType.INFORMATION,
                leader.getCallName() + " has done "
                + leader.getGender().getHisHer() + " prison sentence and "
                + " has sent free from prison. " + leader.getName()
                + " can be assigned for tasks again." + change,
                Icons.getIconByName(Icons.ICON_PRISON));
            realm.getMsgList().addUpcomingMessage(msg);
            leader.setJob(Job.UNASSIGNED);
            leader.setTimeInJob(0);
          }
        } else {
          leader.setTimeInJob(leader.getTimeInJob() + 1);
        }
        // Checking the mortality
        int lifeExpection = leader.getRace().getLifeSpan();
        if (leader.hasPerk(Perk.ADDICTED)) {
          lifeExpection = lifeExpection - lifeExpection / 5;
        }
        if (leader.hasPerk(Perk.HEALTHY)) {
          lifeExpection = lifeExpection + lifeExpection / 5;
        }
        if (leader.getAge() > lifeExpection) {
          int chance = leader.getAge() - lifeExpection;
          if (DiceGenerator.getRandom(1, 100) <= chance) {
            if (leader.hasPerk(Perk.WEALTHY)) {
              Message msg = new Message(MessageType.LEADER,
                  leader.getCallName()
                      + " has paid massive amount of credits to save "
                      + leader.getGender().getHisHer() + " life. "
                      + leader.getCallName()
                      + " was about to die for old age.",
                  Icons.getIconByName(Icons.ICON_DEATH));
              msg.setMatchByString("Index:" + realm.getLeaderIndex(leader));
              realm.getMsgList().addUpcomingMessage(msg);
              leader.useWealth();
            } else {
              if (Game.getTutorial() != null
                  && game.getStarMap().isTutorialEnabled()) {
                    String tutorialText = Game.getTutorial().showTutorialText(
                        127);
                  if (tutorialText != null) {
                    Message msg = new Message(MessageType.INFORMATION,
                        tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
                    realm.getMsgList().addUpcomingMessage(msg);
                  }
              }
              leader.setJob(Job.DEAD);
              Message msg = new Message(MessageType.LEADER,
                  leader.getCallName()
                      + " has died at age of " + leader.getAge(),
                  Icons.getIconByName(Icons.ICON_DEATH));
              msg.setMatchByString("Index:" + realm.getLeaderIndex(leader));
              realm.getMsgList().addUpcomingMessage(msg);
              String reason;
              switch (DiceGenerator.getRandom(2)) {
                case 0:
                default: {
                  reason = "old age";
                  break;
                }
                case 1: {
                  if (leader.getRace() != SpaceRace.MECHIONS) {
                    reason = "heart attack";
                  } else {
                    reason = "burnt CPU";
                  }
                  break;
                }
                case 2: {
                  if (leader.hasPerk(Perk.ADDICTED)) {
                    reason = "substance overdose";
                  } else {
                    reason = "natural causes";
                  }
                  break;
                }
              }
              NewsData news = NewsFactory.makeLeaderDies(leader, realm,
                  reason);
              game.getStarMap().getNewsCorpData().addNews(news);
              game.getStarMap().getHistory().addEvent(
                  NewsFactory.makeLeaderEvent(leader, realm, game.getStarMap(),
                  news));
              if (realm.getRuler() == leader) {
                realm.setRuler(null);
              }
            }
          }
        }
        if (realm.getRuler() != null
            && leader.getJob() != Job.RULER && leader.getJob() != Job.PRISON
            && leader.hasPerk(Perk.POWER_HUNGRY)
            && LeaderUtility.isPowerHungryReadyForKill(
                realm.getGovernment())) {
          handlePowerHungryKill(leader, realm);
        }
        if (leader.getJob() == Job.COMMANDER) {
          leader.getStats().addOne(StatType.COMMANDER_LENGTH);
        }
        if (leader.getJob() == Job.COMMANDER
            && leader.hasPerk(Perk.CORRUPTED)) {
          realm.setTotalCredits(realm.getTotalCredits() - 1);
        }
        if (leader.getJob() == Job.GOVERNOR) {
          leader.getStats().addOne(StatType.GOVERNOR_LENGTH);
        }
        if (leader.getJob() == Job.UNASSIGNED) {
          Message msg = new Message(MessageType.LEADER,
                  leader.getCallName() + " is unassigned!",
                  Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setMatchByString("Index:" + realm.getLeaderIndex(leader));
          realm.getMsgList().addUpcomingMessage(msg);
          if (Game.getTutorial() != null
              && game.getStarMap().isTutorialEnabled()) {
                String tutorialText = Game.getTutorial().showTutorialText(
                    130);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION,
                    tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
                realm.getMsgList().addUpcomingMessage(msg);
              }
          }
        }
      } else if (realm.getRuler() == leader) {
        realm.setRuler(null);
      }
      if (leader.getJob() == Job.RULER) {
        leader.getStats().addOne(StatType.RULER_REIGN_LENGTH);
        int numberOfPlanet = 0;
        int numberOfStarbases = 0;
        Planet firstPlanet = null;
        if (leader.hasPerk(Perk.MERCHANT)) {
          realm.setTotalCredits(realm.getTotalCredits() + 1);
        }
        if (leader.hasPerk(Perk.CORRUPTED)) {
          realm.setTotalCredits(realm.getTotalCredits() - 1);
          if (DiceGenerator.getRandom(99) < 10) {
            rulerFreePrisoner(realm);
          }
        }
        for (Planet planet : game.getStarMap().getPlanetList()) {
          if (planet.getPlanetPlayerInfo() == realm) {
            numberOfPlanet++;
            if (firstPlanet == null) {
              firstPlanet = planet;
            }
          }
        }
        int heirs = 0;
        if (realm.getGovernment().hasHeirs()) {
          for (Leader leaderAsHeir :realm.getLeaderPool()) {
            if (leaderAsHeir.getParent() != null
                && leaderAsHeir.getJob() != Job.DEAD) {
              heirs++;
            }
          }
          int chance = 2;
          if ((realm.getGovernment() == GovernmentType.CLAN
               || realm.getGovernment() == GovernmentType.HORDE)
              && heirs == 0) {
            chance = 4;
          }
          if ((realm.getGovernment() == GovernmentType.EMPIRE
              || realm.getGovernment() == GovernmentType.KINGDOM
              || realm.getGovernment() == GovernmentType.NEST)
              && heirs == 0) {
            chance = 10;
          }
          if ((realm.getGovernment() == GovernmentType.EMPIRE
              || realm.getGovernment() == GovernmentType.KINGDOM)
              && heirs > 1) {
            chance = 4;
          }
          if (leader.getRace() == SpaceRace.MECHIONS
              || leader.getRace() == SpaceRace.REBORGIANS) {
            // Mechions or Reborgians do not get heirs
            chance = 0;
          }
          if (DiceGenerator.getRandom(99) < chance && firstPlanet != null
              && heirs < 3) {
            if (Game.getTutorial() != null
                && game.getStarMap().isTutorialEnabled()) {
                  String tutorialText = Game.getTutorial().showTutorialText(
                      126);
                if (tutorialText != null) {
                  Message msg = new Message(MessageType.INFORMATION,
                      tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
                  realm.getMsgList().addUpcomingMessage(msg);
                }
            }
            heir = LeaderUtility.createLeader(realm, firstPlanet, 1);
            heir.setAge(0);
            heir.setParent(leader);
            String[] heirNames = heir.getName().split(" ");
            String[] parentNames = leader.getName().split(" ");
            if (parentNames.length >= 2 && heirNames.length >= 2) {
              heir.setName(heirNames[0] + " "
                  + parentNames[parentNames.length - 1]);
            }
            heir.setJob(Job.TOO_YOUNG);
            heir.setTitle(LeaderUtility.createTitleForLeader(heir, realm));
            NewsData news = NewsFactory.makeHeirNews(heir, realm);
            game.getStarMap().getNewsCorpData().addNews(news);
            game.getStarMap().getHistory().addEvent(
                NewsFactory.makeLeaderEvent(leader, realm, game.getStarMap(),
                news));
          }
        }
        for (int i = 0; i < realm.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = realm.getFleets().getByIndex(i);
          if (fleet.isStarBaseDeployed()) {
            numberOfStarbases++;
          }
        }
        leader.setExperience(
            leader.getExperience() + numberOfPlanet * 4 + numberOfStarbases);
        if (realm.getGovernment().reignTime() > -1
            && leader.getTimeInJob() >= realm.getGovernment().reignTime()) {
          leader.setJob(Job.UNASSIGNED);
          realm.setRuler(null);
          game.getStarMap().getNewsCorpData().addNews(
              NewsFactory.makeElectionNews(leader, realm));
        }
      }
      int required = leader.getRequiredExperience();
      if (leader.getExperience() >= required) {
        if (Game.getTutorial() != null
        && game.getStarMap().isTutorialEnabled()) {
          String tutorialText = Game.getTutorial().showTutorialText(124);
          if (tutorialText != null) {
            Message msg = new Message(MessageType.INFORMATION, tutorialText,
                Icons.getIconByName(Icons.ICON_TUTORIAL));
            realm.getMsgList().addUpcomingMessage(msg);
          }
        }
        leader.setLevel(leader.getLevel() + 1);
        leader.setExperience(leader.getExperience() - required);
        LeaderUtility.addRandomPerks(leader);
        Message msg = new Message(MessageType.LEADER,
            leader.getCallName()
                + " has reached to a new level. ",
            LeaderUtility.getIconBasedOnLeaderJob(leader));
        msg.setMatchByString("Index:" + realm.getLeaderIndex(leader));
        realm.getMsgList().addUpcomingMessage(msg);
        if (leader.getJob() == Job.COMMANDER
            && leader.getMilitaryRank() != MilitaryRank.CIVILIAN) {
          if (Game.getTutorial() != null
              && game.getStarMap().isTutorialEnabled()) {
                String tutorialText = Game.getTutorial().showTutorialText(125);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                realm.getMsgList().addUpcomingMessage(msg);
              }
          }
          leader.setMilitaryRank(MilitaryRank.getByIndex(
              leader.getMilitaryRank().getIndex() + 1));
          leader.setTitle(LeaderUtility.createTitleForLeader(leader, realm));
        }
      }
    }
    if (heir != null) {
      realm.getLeaderPool().add(heir);
    }
  }
  /**
   * Update whole star map to next turn
   */
  public void updateStarMapToNextTurn() {
    game.getStarMap().resetCulture();
    int richest = game.getStarMap().getNewsCorpData().getCredit().getBiggest();
    int poorest = game.getStarMap().getNewsCorpData().getCredit().getSmallest();
    for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
      PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
      if (info != null) {
        info.estimateBestTechWorld(game.getStarMap().getPlanetList());
        info.setRandomEventOccured(null);
        if (!info.isBoard()) {
          removeBannedShipDesigns(info,
              game.getStarMap().getVotes().areNukesBanned(),
              game.getStarMap().getVotes().arePrivateersBanned());
          if (richest == i
              && game.getStarMap().getVotes().isTaxationOfRichestEnabled()) {
            PlayerInfo poorInfo = game.getPlayers().getPlayerInfoByIndex(
                poorest);
            if (poorInfo != null && info.getTotalCredits() > 0) {
              poorInfo.setTotalCredits(poorInfo.getTotalCredits() + 1);
              info.setTotalCredits(info.getTotalCredits() - 1);
            }
          }
        }
        handleLeaders(info);
        info.getDiplomacy().updateDiplomacyLastingForTurn();
        info.resetVisibilityDataAfterTurn();
        info.getMsgList().clearMessages();
        for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = info.getFleets().getByIndex(j);
          if (fleet.getRoute() != null && fleet.getRoute().isBombing()) {
            // Clean bombing route.
            fleet.setRoute(null);
          }
          fleet.resetShields();
          if (fleet.getCommander() != null) {
            if (fleet.getCommander().getJob() == Job.DEAD) {
              fleet.setCommander(null);
            } else {
              CulturePower culture = game.getStarMap().getSectorCulture(
                  fleet.getX(), fleet.getY());
              int enemyIndex = culture.getHighestCulture();
              if (enemyIndex > -1 && enemyIndex != i
                  && !info.getDiplomacy().isAlliance(enemyIndex)
                  && !info.getDiplomacy().isDefensivePact(enemyIndex)) {
                fleet.getCommander().setExperience(
                    fleet.getCommander().getExperience() + 10);
              }
              if (fleet.getCommander().getAge() == 50
                  && fleet.getCommander().getMilitaryRank()
                  == MilitaryRank.ENSIGN) {
                fleet.getCommander().setMilitaryRank(MilitaryRank.LIEUTENANT);
                fleet.getCommander().setExperience(
                    fleet.getCommander().getExperience() + 50);
                Message msg = new Message(MessageType.LEADER,
                    fleet.getCommander().getCallName()
                        + " has gained military rank lieutenant. ",
                    LeaderUtility.getIconBasedOnLeaderJob(
                        fleet.getCommander()));
                msg.setMatchByString("Index:"
                        + info.getLeaderIndex(fleet.getCommander()));
                info.getMsgList().addUpcomingMessage(msg);
              }
              fleet.getCommander().setExperience(
                  fleet.getCommander().getExperience()
                  + fleet.getNumberOfShip() * fleet.getCommander().getLevel());
            }
          } else {
            if (Game.getTutorial() != null
                && game.getStarMap().isTutorialEnabled()
                && game.getStarMap().getTurn() > 15) {
              String tutorialText = Game.getTutorial().showTutorialText(123);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION,
                    tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addUpcomingMessage(msg);
              }
            }
          }
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
                Mission mission = info.getMissions().getMissionForFleet(
                    fleet.getName());
                if (mission != null && mission.getType() == MissionType.MOVE
                    && mission.getPhase() == MissionPhase.TREKKING) {
                  mission.setMissionTime(mission.getMissionTime() + 1);
                }
                if (fleet.getRoute() != null && fleet.getRoute().isEndReached()
                    && mission == null) {
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
    // This information will be used later when checking the cultural winning
    boolean[] broadcasters = new boolean[game.getStarMap().getPlayerList()
                           .getCurrentMaxRealms()];
    for (int i = 0; i < game.getStarMap().getPlanetList().size(); i++) {
      Planet planet = game.getStarMap().getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        if (planet.getPlanetOwnerIndex() < numberOfPlanets.length) {
          numberOfPlanets[planet.getPlanetOwnerIndex()]++;
          if (planet.hasTower()) {
            towers[planet.getPlanetOwnerIndex()]++;
          }
        }
        if (planet.broadcaster()
            && planet.getPlanetOwnerIndex() < broadcasters.length) {
          broadcasters[planet.getPlanetOwnerIndex()] = true;
          for (int j = 0; j < game.getPlayers().getCurrentMaxRealms(); j++) {
            PlayerInfo realm = game.getPlayers().getPlayerInfoByIndex(j);
            if (realm != null
              && realm.getSectorVisibility(
                  planet.getCoordinate()) == PlayerInfo.UNCHARTED) {
              realm.setSectorVisibility(planet.getCoordinate().getX(),
                  planet.getCoordinate().getY(),
                  PlayerInfo.FOG_OF_WAR);
            }
          }
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
    if (game.getStarMap().getTurn() > 0) {
      handleDiplomaticVotes(towers);
    }
    handleOlympicParticipation(game.getStarMap().getVotes(),
        game.getPlayers());
    handleOlympicGames(game.getStarMap());
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
    if (game.getStarMap().getTurn() == 1 && Game.getTutorial() != null
        && game.getStarMap().isTutorialEnabled()) {
      String tutorialText = Game.getTutorial().showTutorialText(12);
      if (tutorialText != null) {
        Message msg = new Message(MessageType.INFORMATION, tutorialText,
            Icons.getIconByName(Icons.ICON_TUTORIAL));
        game.getPlayers().getPlayerInfoByIndex(0).getMsgList()
          .addNewMessage(msg);
      }
    }
    GameLengthState newState = game.getStarMap().getGameLengthState();
    if (game.getStarMap().getTurn() > 1) {
      PlayerInfo board = game.getPlayers().getBoardPlayer();
      boolean pirateNews = false;
      if (oldState != newState && board != null) {
        pirateNews = updateSpacePirates(board,
            game.getStarMap().getPirateDifficulty(), false);
      }
      if (board != null && !pirateNews) {
        pirateNews = updateSpacePirates(board,
            game.getStarMap().getPirateDifficulty(),
            game.getStarMap().getTurn());
      }
      if (pirateNews) {
        NewsCorpData newsData = game.getStarMap().getNewsCorpData();
        NewsData news = NewsFactory.makeSpacePiratesNews(game.getStarMap());
        newsData.addNews(news);
        GalacticEvent event = new GalacticEvent(news.getNewsText());
        game.getStarMap().getHistory().addEvent(event);
      }
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
    if (game.getStarMap().getTurn() > 0) {
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
      // Using the broadcasters information here
      NewsData news = NewsFactory.makeCulturalVictoryNewsAtEnd(
          game.getStarMap(), broadcasters);
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
      news = NewsFactory.makeDiplomaticVictoryNewsAtEnd(game.getStarMap());
      if (news != null) {
        GalacticEvent event = new GalacticEvent(news.getNewsText());
        game.getStarMap().getHistory().addEvent(event);
        game.getStarMap().setGameEnded(true);
        NewsCorpData newsData = game.getStarMap().getNewsCorpData();
        newsData.addNews(news);
      }
      news = NewsFactory.makePopulationVictoryNewsAtEnd(game.getStarMap());
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
    }
    game.getStarMap().getNewsCorpData().clearNewsList();
    game.getStarMap().updateWinningStrategies();
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
   * Get realm by karma.
   * @param bad True for bad karma and false for good karma
   * @return PlayerInfo or null if not able to find realm.
   */
  public PlayerInfo getRealmByKarma(final boolean bad) {
    StarMap map = game.getStarMap();
    GalaxyStat stat = map.getNewsCorpData().generateScores();
    ScoreBoard scoreBoard = new ScoreBoard();
    for (int i = 0; i < stat.getMaxPlayers(); i++) {
      Row row = new Row(stat.getLatest(i), i);
      scoreBoard.add(row);
    }
    scoreBoard.sort();
    int index = -1;
    if (map.getKarmaType() == KarmaType.FIRST_AND_LAST) {
      if (bad) {
        index = scoreBoard.getRow(0).getRealm();
      } else {
        index = scoreBoard.getRow(scoreBoard.getNumberOfRows() - 1).getRealm();
      }
    }
    if (map.getKarmaType() == KarmaType.SECOND_FIRST_AND_LAST) {
      if (bad) {
        index = scoreBoard.getRow(DiceGenerator.getRandom(0, 1)).getRealm();
      } else {
        int minValue = scoreBoard.getNumberOfRows() - 2;
        int maxValue = scoreBoard.getNumberOfRows() - 1;
        index = scoreBoard.getRow(DiceGenerator.getRandom(minValue, maxValue))
            .getRealm();
      }
    }
    if (map.getKarmaType() == KarmaType.BALANCED) {
      int half = scoreBoard.getNumberOfRows() / 2;
      if (bad) {
        index = scoreBoard.getRow(DiceGenerator.getRandom(0,
            half - 1)).getRealm();
      } else {
        int minValue = half;
        int maxValue = scoreBoard.getNumberOfRows() - 1;
        index = scoreBoard.getRow(DiceGenerator.getRandom(minValue, maxValue))
            .getRealm();
      }
    }
    if (map.getKarmaType() == KarmaType.RANDOM) {
      index = DiceGenerator.getRandom(0,
          map.getPlayerList().getCurrentMaxRealms());
    }
    if (map.getKarmaType() == KarmaType.ONLY_GOODS_FOR_LAST) {
      int half = scoreBoard.getNumberOfRows() / 2;
      if (bad) {
        return null;
      }
      int minValue = half;
      int maxValue = scoreBoard.getNumberOfRows() - 1;
      index = scoreBoard.getRow(DiceGenerator.getRandom(minValue, maxValue))
          .getRealm();
    }
    if (map.getKarmaType() == KarmaType.RANDOM_GOOD_ONES) {
      if (bad) {
        return null;
      }
      index = DiceGenerator.getRandom(0,
          map.getPlayerList().getCurrentMaxRealms());
    }
    if (index != -1) {
      return map.getPlayerByIndex(index);
    }
    return null;
  }

  /**
   * Handle galaxy karma.
   */
  public void handleGalaxyKarma() {
    StarMap map = game.getStarMap();
    if (map.getKarmaType() != KarmaType.DISABLED) {
      map.setBadKarmaCount(map.getBadKarmaCount() + map.getKarmaSpeed());
      map.setGoodKarmaCount(map.getGoodKarmaCount() + map.getKarmaSpeed());
      int badChance = map.getBadKarmaCount() / 10;
      if (DiceGenerator.getRandom(1, 100) < badChance) {
        PlayerInfo info = getRealmByKarma(true);
        if (info != null) {
          RandomEvent event = RandomEventUtility.createBadRandomEvent(info);
          if (RandomEventUtility.handleRandomEvent(event, map)) {
            map.setBadKarmaCount(map.getBadKarmaCount() / 2);
            if (event.isNewsWorthy()) {
              NewsData news = NewsFactory.makeRandomEventNews(event);
              map.getNewsCorpData().addNews(news);
            }
            info.setRandomEventOccured(event);
          }
        }
      }
      int goodChance = map.getGoodKarmaCount() / 10;
      if (DiceGenerator.getRandom(1, 100) < goodChance) {
        PlayerInfo info = getRealmByKarma(false);
        if (info != null && info.getRandomEventOccured() == null) {
          RandomEvent event = RandomEventUtility.createGoodRandomEvent(info);
          if (RandomEventUtility.handleRandomEvent(event, map)) {
            map.setGoodKarmaCount(map.getGoodKarmaCount() / 2);
            if (event.isNewsWorthy()) {
              NewsData news = NewsFactory.makeRandomEventNews(event);
              map.getNewsCorpData().addNews(news);
            }
            info.setRandomEventOccured(event);
          }
        }
      }
    }
  }
  /**
   * Handle Ai Turn
   * @return True when turn has finished or need to change state
   */
  public boolean handleAiTurn() {
    if (game.getStarMap().getAIFleet() == null) {
      game.getStarMap().handleAIResearchAndPlanets();
      searchForInterceptFleets();
      game.getStarMap().handleDiplomaticDelegacies();
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
      if (game.getStarMap().getTurn() > 0) {
        handleGalaxyKarma();
      }
      for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
        // Handle player research at end of turn
        PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
        info.getTechList().updateResearchPointByTurn(game.getStarMap()
            .getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH, i),
            info, game.getStarMap().getScoreVictoryTurn(),
            game.getStarMap().isTutorialEnabled());
        int creditFlow = game.getStarMap().getTotalProductionByPlayerPerTurn(
            Planet.PRODUCTION_CREDITS, i);
        handleLowCreditWarning(info, creditFlow);
        // Handle war fatigue for player
        GovernmentType government = info.getGovernment();
        if (!government.isImmuneToHappiness()) {
          int warResistance = government.getWarResistance();
          if (info.getRuler() != null
              && info.getRuler().hasPerk(Perk.WARLORD)) {
            warResistance++;
          }
          if (info.getRuler() != null
              && info.getRuler().hasPerk(Perk.WEAK_LEADER)) {
            warResistance--;
          }
          boolean fatigued = false;
          int wars = info.getDiplomacy().getNumberOfWar();
          int warFatigueValue = info.getTotalWarFatigue();
          if (wars > 0 && wars > warResistance) {
            int fatigue = info.getWarFatigue();
            fatigue = fatigue - wars + warResistance;
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
            int warFatigueValueAfter = info.getTotalWarFatigue();
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
      synchronized (aiThread) {
        if (!aiThread.isStarted()) {
          aiThread.start();
        }
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
