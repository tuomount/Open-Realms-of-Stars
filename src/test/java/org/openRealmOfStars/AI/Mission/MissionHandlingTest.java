package org.openRealmOfStars.AI.Mission;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.starMap.Coordinate;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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

}
