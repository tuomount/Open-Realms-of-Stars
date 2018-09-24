package org.openRealmOfStars.AI.Mission;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.game.States.AITurnView;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017, 2018 Tuomo Untinen
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
* Mission Handling test
*
*/
public class MissionHandlingTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGatherAssault() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.GATHER, MissionPhase.BUILDING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    mission.setShipType(Mission.ASSAULT_TYPE);
    FleetList fleetList = new FleetList();
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Ship assaultShip = Mockito.mock(Ship.class);
    Mockito.when(assaultShip.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(assaultShip.hasBombs()).thenReturn(false);
    Mockito.when(assaultShip.isTrooperShip()).thenReturn(false);
    Ship troopShip = Mockito.mock(Ship.class);
    Mockito.when(troopShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(troopShip.hasBombs()).thenReturn(false);
    Mockito.when(troopShip.isTrooperShip()).thenReturn(true);
    Ship bombShip = Mockito.mock(Ship.class);
    Mockito.when(bombShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(bombShip.hasBombs()).thenReturn(true);
    Mockito.when(bombShip.isTrooperShip()).thenReturn(false);
    Fleet fleet = new Fleet(assaultShip, 5, 5);
    fleet.addShip(troopShip);
    fleet.addShip(bombShip);
    fleetList.add(fleet);
    MissionHandling.findGatheringShip(mission, info);
    assertEquals("Gather Assault #0", mission.getFleetName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGatherTrooper() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.GATHER, MissionPhase.BUILDING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    mission.setShipType(Mission.TROOPER_TYPE);
    FleetList fleetList = new FleetList();
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Ship assaultShip = Mockito.mock(Ship.class);
    Mockito.when(assaultShip.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(assaultShip.hasBombs()).thenReturn(false);
    Mockito.when(assaultShip.isTrooperShip()).thenReturn(false);
    Ship troopShip = Mockito.mock(Ship.class);
    Mockito.when(troopShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(troopShip.hasBombs()).thenReturn(false);
    Mockito.when(troopShip.isTrooperShip()).thenReturn(true);
    Ship bombShip = Mockito.mock(Ship.class);
    Mockito.when(bombShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(bombShip.hasBombs()).thenReturn(true);
    Mockito.when(bombShip.isTrooperShip()).thenReturn(false);
    Fleet fleet = new Fleet(assaultShip, 5, 5);
    fleet.addShip(troopShip);
    fleet.addShip(bombShip);
    fleetList.add(fleet);
    MissionHandling.findGatheringShip(mission, info);
    assertEquals("Gather Trooper #0", mission.getFleetName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGatherBomber() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.GATHER, MissionPhase.BUILDING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    mission.setShipType(Mission.BOMBER_TYPE);
    FleetList fleetList = new FleetList();
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Ship assaultShip = Mockito.mock(Ship.class);
    Mockito.when(assaultShip.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(assaultShip.hasBombs()).thenReturn(false);
    Mockito.when(assaultShip.isTrooperShip()).thenReturn(false);
    Ship troopShip = Mockito.mock(Ship.class);
    Mockito.when(troopShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(troopShip.hasBombs()).thenReturn(false);
    Mockito.when(troopShip.isTrooperShip()).thenReturn(true);
    Ship bombShip = Mockito.mock(Ship.class);
    Mockito.when(bombShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(bombShip.hasBombs()).thenReturn(true);
    Mockito.when(bombShip.isTrooperShip()).thenReturn(false);
    Fleet fleet = new Fleet(assaultShip, 5, 5);
    fleet.addShip(troopShip);
    fleet.addShip(bombShip);
    fleetList.add(fleet);
    MissionHandling.findGatheringShip(mission, info);
    assertEquals("Gather Bomber #0", mission.getFleetName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDeployStarbase() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.DEPLOY_STARBASE, MissionPhase.LOADING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Ship starbase = Mockito.mock(Ship.class);
    Mockito.when(starbase.getTotalMilitaryPower()).thenReturn(20);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.STARBASE);
    Mockito.when(starbase.getHull()).thenReturn(hull);
    Fleet fleet = new Fleet(starbase, 5, 5);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Game game = Mockito.mock(Game.class);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.DEEP_SPACE_ANCHOR2);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(Mockito.anyInt(), Mockito.anyInt())).thenReturn(tile);
    Mockito.when(game.getStarMap()).thenReturn(map);
    MissionHandling.handleDeployStarbase(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDestroyStarbase() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.DESTROY_STARBASE, MissionPhase.PLANNING,
        new Coordinate(5, 5));
    mission.setTargetPlanet("Coordinate 5, 5");
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(missionList.noMoreGatherMissions(Mockito.anyString()))
    .thenReturn(true);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.NORMAL);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    Fleet fleet = new Fleet(ship, 5, 5);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Game game = Mockito.mock(Game.class);
    MissionHandling.handleDestroyStarbase(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTrade() {
    StarMap map = Mockito.mock(StarMap.class);
    Planet planetTrader = Mockito.mock(Planet.class);
    Mockito.when(planetTrader.getName()).thenReturn("Trader I");
    Mockito.when(planetTrader.getCoordinate()).thenReturn(new Coordinate(6, 5));
    PlayerInfo owner = Mockito.mock(PlayerInfo.class);
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(owner.getMsgList()).thenReturn(msgList);
    Mockito.when(planetTrader.getPlanetPlayerInfo()).thenReturn(owner);
    Planet planetHome = Mockito.mock(Planet.class);
    Mockito.when(planetHome.getName()).thenReturn("Homeworld I");
    Mockito.when(planetHome.getCoordinate()).thenReturn(new Coordinate(10, 10));
    Mockito.when(map.getPlanetByCoordinate(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(planetTrader);
    Mockito.when(map.getPlanetByName("Trader I")).thenReturn(planetTrader);
    Mockito.when(map.getPlanetByName("Homeworld I")).thenReturn(planetHome);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_ALLIANCE)).thenReturn(true);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(diplomacyList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Mission mission = new Mission(MissionType.TRADE_FLEET, MissionPhase.LOADING,
        new Coordinate(6, 5));
    mission.setTargetPlanet("Trader I");
    mission.setPlanetBuilding("Homeworld I");
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(missionList.noMoreGatherMissions(Mockito.anyString()))
    .thenReturn(true);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.NORMAL);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(ship.doTrade(planetTrader, info)).thenReturn(10);
    Fleet fleet = new Fleet(ship, 5, 5);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Game game = Mockito.mock(Game.class);
    Mockito.when(game.getStarMap()).thenReturn(map);
    MissionHandling.handleTrade(mission, fleet, info, game);
    assertEquals(MissionPhase.LOADING, mission.getPhase());
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(null);
    MissionHandling.handleTrade(mission, fleet, info, game);
    assertEquals(MissionPhase.TREKKING, mission.getPhase());
    fleet.setPos(new Coordinate(10, 10));
    MissionHandling.handleTrade(mission, fleet, info, game);
    assertEquals(MissionPhase.LOADING, mission.getPhase());
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNearByFleet() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getMilitaryValue()).thenReturn(6);
    Mockito.when(fleet2.getCoordinate()).thenReturn(new Coordinate(6, 7));
    Mockito.when(map.getFleetByCoordinate(6, 7)).thenReturn(fleet2);
    Mockito.when(game.getStarMap()).thenReturn(map);
    Fleet fleet = Mockito.mock(Fleet.class);
    Coordinate fleetCoord = new Coordinate(5, 7);
    Mockito.when(fleet.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(10);
    Fleet targetFleet = MissionHandling.getNearByFleet(info, game, fleet, 1);
    assertEquals(fleet2, targetFleet);
    Fleet fleet3 = Mockito.mock(Fleet.class);
    Mockito.when(fleet3.getMilitaryValue()).thenReturn(7);
    Mockito.when(fleet3.getCoordinate()).thenReturn(new Coordinate(4, 6));
    Mockito.when(map.getFleetByCoordinate(4, 6)).thenReturn(fleet3);
    targetFleet = MissionHandling.getNearByFleet(info, game, fleet, 1);
    assertEquals(fleet2, targetFleet);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPrivateering() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mission mission = new Mission(MissionType.PRIVATEER, MissionPhase.TREKKING,
        new Coordinate(2, 2));
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getMaxX()).thenReturn(50);
    Mockito.when(map.getMaxY()).thenReturn(50);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getMilitaryValue()).thenReturn(6);
    Mockito.when(fleet2.getCoordinate()).thenReturn(new Coordinate(6, 7));
    Mockito.when(map.getFleetByCoordinate(6, 7)).thenReturn(fleet2);
    Mockito.when(game.getStarMap()).thenReturn(map);
    Fleet fleet = Mockito.mock(Fleet.class);
    Coordinate fleetCoord = new Coordinate(5, 7);
    Mockito.when(fleet.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(7);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(10);
    Mockito.when(fleet.getMovesLeft()).thenReturn(1);
    AStarSearch search = Mockito.mock(AStarSearch.class);
    Mockito.when(fleet.getaStarSearch()).thenReturn(search);
    Fleet targetFleet = MissionHandling.getNearByFleet(info, game, fleet, 1);
    assertEquals(fleet2, targetFleet);
    MissionHandling.handlePrivateering(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCleaning() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    MissionList missionList = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(missionList);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleetList.getByName("Test fleet")).thenReturn(fleet);
    Mission mission = new Mission(MissionType.PRIVATEER, MissionPhase.TREKKING,
        new Coordinate(2, 2));
    mission.setFleetName("Test fleet");
    Mission missionPlan = new Mission(MissionType.COLONIZE, MissionPhase.PLANNING,
        new Coordinate(2, 2));
    Mission mission2 = new Mission(MissionType.COLONY_EXPLORE, MissionPhase.TREKKING,
        new Coordinate(2, 2));
    mission2.setFleetName("NoSuch");
    missionList.add(mission);
    missionList.add(missionPlan);
    missionList.add(mission2);
    assertEquals(3, missionList.getSize());
    MissionHandling.cleanMissions(info);
    assertEquals(2, missionList.getSize());
    missionList.add(missionPlan);
    missionList.add(mission2);
    assertEquals(4, missionList.getSize());
    MissionHandling.cleanMissions(info);
    assertEquals(3, missionList.getSize());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testColonyExplore() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mission mission = new Mission(MissionType.COLONY_EXPLORE, MissionPhase.EXECUTING,
        new Coordinate(2, 2));
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getMaxX()).thenReturn(50);
    Mockito.when(map.getMaxY()).thenReturn(50);
    Mockito.when(game.getStarMap()).thenReturn(map);
    Fleet fleet = Mockito.mock(Fleet.class);
    Coordinate fleetCoord = new Coordinate(5, 7);
    Mockito.when(fleet.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(7);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(0);
    Mockito.when(fleet.getMovesLeft()).thenReturn(1);
    AStarSearch search = Mockito.mock(AStarSearch.class);
    Mockito.when(fleet.getaStarSearch()).thenReturn(search);
    MissionHandling.handleColonyExplore(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
    mission.setPhase(MissionPhase.TREKKING);
    MissionHandling.handleColonyExplore(mission, fleet, info, game);
    assertEquals(MissionPhase.TREKKING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpyMission() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.TEUTHIDAES);
    Mission mission = new Mission(MissionType.SPY_MISSION, MissionPhase.TREKKING,
        new Coordinate(2, 2));
    mission.setTargetPlanet("Planet I");
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Coordinate coord = new Coordinate(2, 2);    
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(3);
    Mockito.when(planet.getCoordinate()).thenReturn(coord);
    CulturePower culture = Mockito.mock(CulturePower.class);
    Mockito.when(culture.getHighestCulture()).thenReturn(3);
    Mockito.when(map.getMaxX()).thenReturn(50);
    Mockito.when(map.getMaxY()).thenReturn(50);
    Mockito.when(map.getPlanetByName(Mockito.anyString())).thenReturn(planet);
    Mockito.when(map.getSectorCulture(Mockito.anyInt(), Mockito.anyInt())).thenReturn(culture);
    Mockito.when(game.getStarMap()).thenReturn(map);
    Fleet fleet = Mockito.mock(Fleet.class);
    Coordinate fleetCoord = new Coordinate(5, 7);
    Mockito.when(fleet.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(7);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(0);
    Mockito.when(fleet.getMovesLeft()).thenReturn(1);
    AStarSearch search = Mockito.mock(AStarSearch.class);
    Mockito.when(fleet.getaStarSearch()).thenReturn(search);
    MissionHandling.handleSpyMission(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMergingFleets() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CENTAURS, 2, 0);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Coordinate fleetCoord = new Coordinate(5, 7);
    Mockito.when(fleet1.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet1.getX()).thenReturn(5);
    Mockito.when(fleet1.getY()).thenReturn(7);
    Mockito.when(fleet1.getNumberOfShip()).thenReturn(2);
    Mockito.when(fleet1.getName()).thenReturn("Defender #0");
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet2.getX()).thenReturn(5);
    Mockito.when(fleet2.getY()).thenReturn(7);
    Mockito.when(fleet2.getNumberOfShip()).thenReturn(1);
    Mockito.when(fleet2.getName()).thenReturn("Defender #1");
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(fleet2.getShipByIndex(0)).thenReturn(ship);
    info.getFleets().add(fleet1);
    info.getFleets().add(fleet2);
    assertEquals(2, info.getFleets().getNumberOfFleets());
    MissionHandling.mergeFleets(fleet1, info);
    assertEquals(1, info.getFleets().getNumberOfFleets());
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMergingFleetsTooBig() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CENTAURS, 2, 0);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Coordinate fleetCoord = new Coordinate(5, 7);
    Mockito.when(fleet1.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet1.getX()).thenReturn(5);
    Mockito.when(fleet1.getY()).thenReturn(7);
    Mockito.when(fleet1.getNumberOfShip()).thenReturn(10);
    Mockito.when(fleet1.getName()).thenReturn("Defender #0");
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCoordinate()).thenReturn(fleetCoord);
    Mockito.when(fleet2.getX()).thenReturn(5);
    Mockito.when(fleet2.getY()).thenReturn(7);
    Mockito.when(fleet2.getNumberOfShip()).thenReturn(11);
    Mockito.when(fleet2.getName()).thenReturn("Defender #1");
    info.getFleets().add(fleet1);
    info.getFleets().add(fleet2);
    assertEquals(2, info.getFleets().getNumberOfFleets());
    MissionHandling.mergeFleets(fleet1, info);
    assertEquals(2, info.getFleets().getNumberOfFleets());
  }
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPrivateeringNoMoreSunToExplore() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "npePrivateer.save");
    Game game = new Game(false);
    game.setLoadedGame(starMap);
    AITurnView aiTurnView = new AITurnView(game);
    ActionEvent arg0 = Mockito.mock(ActionEvent.class);
    Mockito.when(arg0.getActionCommand()).thenReturn(GameCommands.COMMAND_ANIMATION_TIMER);
    game.changeGameState(GameState.STARMAP);
    game.changeGameState(GameState.AITURN);
    int turnNumber = starMap.getTurn();
    turnNumber++;
    // Safety measure to end running the AI loop
    int loopCount = 10000;
    while (true) {
      loopCount--;
      if (loopCount == 0) {
        assertFalse(true);
      }
      aiTurnView.handleActions(arg0);
      if (game.getGameState() == GameState.DIPLOMACY_VIEW) {
        game.changeGameState(GameState.AITURN);
      } else if (game.getGameState() == GameState.COMBAT) {
        break;
      } else if (game.getGameState() != GameState.AITURN) {
        assertEquals(turnNumber, starMap.getTurn());
      }
    }
    assertEquals(GameState.COMBAT, game.getGameState());
  }

}
