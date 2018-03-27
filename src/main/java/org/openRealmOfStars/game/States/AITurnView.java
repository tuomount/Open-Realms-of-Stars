package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionHandling;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
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
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
   * Constructor for main menu
   * @param game Game used to get access star map and planet lists
   */
  public AITurnView(final Game game) {
    this.game = game;
    Planet planet = new Planet(new Coordinate(1, 1), "Random Planet", 1, false);
    if (DiceGenerator.getRandom(100) < 10) {
      planet.setPlanetType(PlanetTypes.getRandomPlanetType(true));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(PlanetTypes.getRandomPlanetType(false));
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
      textAnim = 0;
    }
  }

  /**
   * Handle missions for AI player and single fleet
   * @param fleet Fleet for doing the missing
   * @param info PlayerInfo
   */
  private void handleMissions(final Fleet fleet, final PlayerInfo info) {
    if (!fleet.allFixed()) {
      // Make fleet to fix itself
      fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(),
          fleet.getY(), Route.ROUTE_FIX));
      return;
    }
    Mission mission = info.getMissions().getMissionForFleet(fleet.getName());
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
      default:
        throw new IllegalArgumentException("Unknown mission type for AI!");
      }
    } else {
      // No mission for fleet yet
      if (fleet.isScoutFleet()) {
        // Scout fleet should go to explore
        Sun sun = game.getStarMap().getNearestSolarSystem(fleet.getX(),
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
      if (fleet.isPrivateerFleet()) {
        // Privateer fleet should go to explore and rob trade ships
        Sun sun = game.getStarMap().getNearestSolarSystem(fleet.getX(),
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
      if (fleet.isColonyFleet()) {
        mission = info.getMissions().getMission(MissionType.COLONIZE,
            MissionPhase.PLANNING);
        if (mission != null) {
          mission.setPhase(MissionPhase.LOADING);
          mission.setFleetName(fleet.getName());
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
      info.getMissions().add(createGatherMission(mission, coord,
          Mission.ASSAULT_TYPE));
      info.getMissions().add(createGatherMission(mission, coord,
          Mission.ASSAULT_TYPE));
      if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.BOMBER_TYPE));
      }
      info.getMissions().add(createGatherMission(mission, coord,
          Mission.TROOPER_TYPE));
      Attitude attitude = info.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        if (!info.bomberTrooperAvailable()) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.TROOPER_TYPE));
        }
      } else if (attitude == Attitude.MILITARISTIC) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.BOMBER_TYPE));
        }
      } else if (attitude == Attitude.EXPANSIONIST) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        if (!info.bomberTrooperAvailable()) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.TROOPER_TYPE));
        }
      } else if (attitude == Attitude.BACKSTABBING) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.BOMBER_TYPE));
        }
      } else if (attitude == Attitude.LOGICAL) {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        if (!info.bomberTrooperAvailable()) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.TROOPER_TYPE));
        }
        if (info.bombersAvailable() && !info.bomberTrooperAvailable()) {
          info.getMissions().add(createGatherMission(mission, coord,
              Mission.BOMBER_TYPE));
        }
      } else {
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
        info.getMissions().add(createGatherMission(mission, coord,
            Mission.ASSAULT_TYPE));
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
      info.getMissions().add(mission);
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
    FleetTileInfo[][] fleetTiles = map.getFleetTiles();
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
            if (info.getMissions().getDeployStarbaseMission(x, y) == null) {
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
              if (info.getMissions().getDeployStarbaseMission(x, y) == null) {
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
                if (list.isBonusType(DiplomacyBonusType.IN_WAR)) {
                  addDestroyStarbaseMission(new Coordinate(x, y), info);
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
    if (info != null && !info.isHuman()) {
      for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
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
                  // TODO: Privateet should have a different diplomacy
                  continue;
                }
                if (fleetOwner.isHuman()) {
                  SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
                  game.changeGameState(GameState.DIPLOMACY_VIEW, fleet);
                  return;
                }
                MissionHandling.handleDiplomacyBetweenAis(game, info, i,
                   fleet);
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
      for (Planet planet : planets) {
        if (planet.getRadiationLevel() <= info.getRace().getMaxRad()
            && planet.getPlanetPlayerInfo() == null && !planet.isGasGiant()
            && info.getSectorVisibility(planet.getCoordinate())
            == PlayerInfo.VISIBLE) {
          // New planet to colonize, adding it to mission list
          Mission mission = new Mission(MissionType.COLONIZE,
              MissionPhase.PLANNING, planet.getCoordinate());
          if (info.getMissions().getColonizeMission(mission.getX(),
              mission.getY()) == null) {
            // No colonize mission for this planet found, so adding it.
            info.getMissions().add(mission);
          }

        }
        if (planet.getRadiationLevel() <= info.getRace().getMaxRad()
            && planet.getPlanetPlayerInfo() != null
            && planet.getPlanetPlayerInfo() != info && !planet.isGasGiant()) {
          if (info.getSectorVisibility(planet.getCoordinate())
            == PlayerInfo.VISIBLE) {
            PlayerInfo owner = planet.getPlanetPlayerInfo();
            int ownerIndex = game.getStarMap().getPlayerList().getIndex(owner);
            DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                ownerIndex);
            if (list != null && list.isBonusType(DiplomacyBonusType.IN_WAR)) {
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
                    ownerIndex, null);
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
            if (owner != null) {
              int ownerIndex = game.getStarMap().getPlayerList().getIndex(
                  owner);
              DiplomacyBonusList list = info.getDiplomacy().getDiplomacyList(
                  ownerIndex);
              if (list != null
                  && list.isBonusType(DiplomacyBonusType.IN_WAR)) {
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
            } else {
              if (planet.getRadiationLevel() <= info.getRace().getMaxRad()
                  && !planet.isGasGiant()) {
                // New planet to colonize, adding it to mission list
                Mission mission = new Mission(MissionType.COLONIZE,
                    MissionPhase.PLANNING, planet.getCoordinate());
                if (info.getMissions().getColonizeMission(mission.getX(),
                    mission.getY()) == null) {
                  // No colonize mission for this planet found, so adding it.
                  info.getMissions().add(mission);
                }
              }
            }
          }
        }
      }
    }

  }

  /**
   * Handle single AI Fleet. If fleet was last then increase AI turn number
   * and set aiFleet to null.
   */
  public void handleAIFleet() {
    PlayerInfo info = game.getPlayers()
        .getPlayerInfoByIndex(game.getStarMap().getAiTurnNumber());
    if (info != null && !info.isHuman()
        && game.getStarMap().getAIFleet() != null) {
      // Handle fleet

      Fleet fleet = game.getStarMap().getAIFleet();
      if (fleet != null) {
        MissionHandling.mergeFleets(fleet, info);
        Mission mission = info.getMissions().getMission(MissionType.COLONIZE,
            MissionPhase.PLANNING);
        if (mission != null && fleet.getColonyShip() != null) {
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
        if (mission != null && fleet.getStarbaseShip() != null) {
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
        // All fleets have moved. Checking the new possible planet
        searchPlanetsForMissions();
        // Searching for fleet which has crossed the borders
        searchForBorderCrossing();
        searchDeepSpaceAnchors();
        game.getStarMap().setAIFleet(null);
        game.getStarMap()
            .setAiTurnNumber(game.getStarMap().getAiTurnNumber() + 1);
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
                  fleet.getRoute().getY())) {
                // Not blocked so fleet is moving
                MissionHandling.makeFleetMove(game, fleet.getRoute().getX(),
                    fleet.getRoute().getY(), info, fleet);
                if (fleet.getRoute().isEndReached()) {
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
                // Movement was blocked, giving a message
                fleet.setRoute(null);
                Message msg = new Message(MessageType.FLEET,
                    fleet.getName()
                        + " has encouter obstacle and waiting for more orders.",
                    Icons.getIconByName(Icons.ICON_HULL_TECH));
                msg.setMatchByString(fleet.getName());
                msg.setCoordinate(fleet.getCoordinate());
                info.getMsgList().addNewMessage(msg);
              }
            }

          } else {
            Message msg = new Message(MessageType.FLEET,
                fleet.getName() + " is waiting for orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setMatchByString(fleet.getName());
            msg.setCoordinate(fleet.getCoordinate());
            info.getMsgList().addNewMessage(msg);
          }
          if (fleet.isStarBaseDeployed()) {
            fleet.setMovesLeft(0);
            // TODO handle starbase specific components
            for (Ship ship : fleet.getShips()) {
               info.setTotalCredits(info.getTotalCredits()
                   + ship.getTotalCreditBonus());
               ship.setCulture(ship.getCulture()
                   + ship.getTotalCultureBonus());
               // FIXME Research is done in starmap see row 1720
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
    for (int i = 0; i < game.getStarMap().getPlanetList().size(); i++) {
      Planet planet = game.getStarMap().getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
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
        planet.updateOneTurn(enemyOrbiting);
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
    game.getStarMap().updateEspionage();
    game.getStarMap().setTurn(game.getStarMap().getTurn() + 1);
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
      // TODO take the cost of faking military
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
    if (game.getStarMap().getTurn() == game.getStarMap()
        .getScoreVictoryTurn()) {
      // Game is in the end
      NewsCorpData newsData = game.getStarMap().getNewsCorpData();
      NewsData news = NewsFactory.makeScoreNewsAtEnd(game.getStarMap());
      newsData.addNews(news);
      GalacticEvent event = new GalacticEvent(news.getNewsText());
      game.getStarMap().getHistory().addEvent(event);
    }
    game.getStarMap().getNewsCorpData().clearNewsList();
  }

  /**
   * Handle actions for AI Turn view
   * Since AI Turn View can be null while handling the all the AI. AI handling
   * variables are stored in StarMap. These variables are AIFleet and
   * AITurnNumber.
   * @param arg0 ActionEvent
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
      updateText();
      if (game.getStarMap().getAIFleet() == null) {
        game.getStarMap().handleAIResearchAndPlanets();
        game.getStarMap().handleFakingMilitarySize();
      } else {
        handleAIFleet();
        if (game.getGameState() != GameState.AITURN) {
          // If last player is calling diplomacy screen
          // it is not run due the line 556. There fore
          // we need to exit at this point!
          return;
        }
      }
      if (game.getStarMap().isAllAIsHandled()) {
        updateStarMapToNextTurn();
        for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
          // Handle player research at end of turn
          PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
          info.getTechList().updateResearchPointByTurn(game.getStarMap()
              .getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH, i),
              info);
        }
        game.getStarMap().clearAITurn();
        if (game.getStarMap().getNewsCorpData().isNewsToShow()) {
          game.changeGameState(GameState.NEWS_CORP_VIEW);
        } else {
          game.changeGameState(GameState.STARMAP);
        }
      }
    }

  }

}
