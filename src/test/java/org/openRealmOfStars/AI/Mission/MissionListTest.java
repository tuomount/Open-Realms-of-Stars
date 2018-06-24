package org.openRealmOfStars.AI.Mission;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

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
* Mission List test
*
*/
public class MissionListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testToStrings() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.toString()).thenReturn("Mission 1 toString");
    Mission mission2 = Mockito.mock(Mission.class);
    Mockito.when(mission2.toString()).thenReturn("Mission 2 toString");
    list.add(mission);
    list.add(mission2);
    assertEquals("Mission 0:\n"
        + "Mission 1 toString\n\n"
        + "Mission 1:\n"
        + "Mission 2 toString\n\n", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDestroyStarbaseMission() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.DESTROY_STARBASE);
    Mockito.when(mission.getTargetPlanet()).thenReturn("Target I");
    list.add(mission);
    assertEquals(mission, list.getDestroyStarbaseMission("Target I"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAttackMission() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.ATTACK);
    Mockito.when(mission.getTargetPlanet()).thenReturn("Target I");
    list.add(mission);
    assertEquals(mission, list.getAttackMission("Target I"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeMission() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.TRADE_FLEET);
    Mockito.when(mission.getTargetPlanet()).thenReturn("Target I");
    list.add(mission);
    assertEquals(mission, list.getTradeMission("Target I"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGatherMissions() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission.getShipType()).thenReturn(Mission.ASSAULT_TYPE);
    Mockito.when(mission.getTargetPlanet()).thenReturn("Target I");
    Mission mission2 = Mockito.mock(Mission.class);
    Mockito.when(mission2.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission2.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission2.getShipType()).thenReturn(Mission.BOMBER_TYPE);
    Mockito.when(mission2.getTargetPlanet()).thenReturn("Target I");
    Mission mission3 = Mockito.mock(Mission.class);
    Mockito.when(mission3.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission3.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission3.getShipType()).thenReturn(Mission.TROOPER_TYPE);
    Mockito.when(mission3.getTargetPlanet()).thenReturn("Target I");
    assertEquals(true, list.noMoreGatherMissions("Target I"));
    list.add(mission);
    list.add(mission2);
    list.add(mission3);
    assertEquals(mission, list.getMissionByIndex(0));
    assertEquals(mission2, list.getMissionByIndex(1));
    assertEquals(mission3, list.getMissionByIndex(2));
    assertEquals(false, list.noMoreGatherMissions("Target I"));
    assertEquals(mission, list.getGatherMission(Mission.ASSAULT_TYPE));
    assertEquals(mission2, list.getGatherMission(Mission.BOMBER_TYPE));
    assertEquals(mission3, list.getGatherMission(Mission.TROOPER_TYPE));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGatherMissions2() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission.getShipType()).thenReturn(Mission.ASSAULT_TYPE);
    Mockito.when(mission.getTargetPlanet()).thenReturn("Target I");
    Mission mission2 = Mockito.mock(Mission.class);
    Mockito.when(mission2.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission2.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission2.getShipType()).thenReturn(Mission.BOMBER_TYPE);
    Mockito.when(mission2.getTargetPlanet()).thenReturn("Target I");
    Mission mission3 = Mockito.mock(Mission.class);
    Mockito.when(mission3.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission3.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission3.getShipType()).thenReturn(Mission.TROOPER_TYPE);
    Mockito.when(mission3.getTargetPlanet()).thenReturn("Target I");
    assertEquals(true, list.noMoreGatherMissions("Target I"));
    list.addPriorityAfter(mission, mission);
    list.addPriorityAfter(mission2, mission);
    list.addPriorityAfter(mission3, mission);
    assertEquals(false, list.noMoreGatherMissions("Target I"));
    assertEquals(mission, list.getMissionByIndex(0));
    assertEquals(mission3, list.getMissionByIndex(1));
    assertEquals(mission2, list.getMissionByIndex(2));
    assertEquals(mission, list.getGatherMission(Mission.ASSAULT_TYPE));
    assertEquals(mission2, list.getGatherMission(Mission.BOMBER_TYPE));
    assertEquals(mission3, list.getGatherMission(Mission.TROOPER_TYPE));
    assertEquals(3, list.getNumberOfMissionTypes(MissionType.GATHER));
    assertEquals(0, list.getNumberOfMissionTypes(MissionType.ATTACK));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRemoveAttackMissions() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission.getShipType()).thenReturn(Mission.ASSAULT_TYPE);
    Mockito.when(mission.getTargetPlanet()).thenReturn("Target I");
    Mission mission2 = Mockito.mock(Mission.class);
    Mockito.when(mission2.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission2.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission2.getShipType()).thenReturn(Mission.BOMBER_TYPE);
    Mockito.when(mission2.getTargetPlanet()).thenReturn("Target I");
    Mission mission3 = Mockito.mock(Mission.class);
    Mockito.when(mission3.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission3.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission3.getShipType()).thenReturn(Mission.TROOPER_TYPE);
    Mockito.when(mission3.getTargetPlanet()).thenReturn("Target I");
    Mission mission4 = Mockito.mock(Mission.class);
    Mockito.when(mission4.getType()).thenReturn(MissionType.DESTROY_STARBASE);
    Mockito.when(mission4.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission4.getTargetPlanet()).thenReturn("Starbase Coordinate(8, 9)");
    Mission mission5 = Mockito.mock(Mission.class);
    Mockito.when(mission5.getType()).thenReturn(MissionType.GATHER);
    Mockito.when(mission5.getPhase()).thenReturn(MissionPhase.PLANNING);
    Mockito.when(mission5.getShipType()).thenReturn(Mission.ASSAULT_SB_TYPE);
    Mockito.when(mission5.getTargetPlanet()).thenReturn("Starbase Coordinate(8, 9)");
    Mission mission6 = Mockito.mock(Mission.class);
    Mockito.when(mission6.getType()).thenReturn(MissionType.TRADE_FLEET);
    Mockito.when(mission6.getTargetPlanet()).thenReturn("Target I");
    list.add(mission);
    list.add(mission2);
    list.add(mission3);
    list.add(mission4);
    list.add(mission5);
    list.add(mission6);
    assertEquals(4, list.getNumberOfMissionTypes(MissionType.GATHER));
    assertEquals(1, list.getNumberOfMissionTypes(MissionType.DESTROY_STARBASE));
    assertEquals(1, list.getNumberOfMissionTypes(MissionType.TRADE_FLEET));
    assertEquals(6, list.getSize());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    StarMap map = Mockito.mock(StarMap.class);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.isStarBaseDeployed()).thenReturn(true);
    Mockito.when(map.getPlanetByName("Target I")).thenReturn(planet);
    Mockito.when(map.getFleetByCoordinate(Mockito.anyInt(), Mockito.anyInt())).thenReturn(fleet);
    Mockito.when(map.getPlayerInfoByFleet(fleet)).thenReturn(info);
    list.removeAttackAgainstPlayer(info, map);
    assertEquals(1, list.getSize());
    assertEquals(0, list.getNumberOfMissionTypes(MissionType.GATHER));
    assertEquals(0, list.getNumberOfMissionTypes(MissionType.DESTROY_STARBASE));
    assertEquals(1, list.getNumberOfMissionTypes(MissionType.TRADE_FLEET));
  }

}
