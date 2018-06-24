package org.openRealmOfStars.AI.Mission;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;

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
 * Test for Mission class
 */

public class MissionTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMission() {
    Coordinate coordinate = Mockito.mock(Coordinate.class);
    Mockito.when(coordinate.getX()).thenReturn(3);
    Mockito.when(coordinate.getY()).thenReturn(5);
    Mission mission = new Mission(MissionType.ATTACK, MissionPhase.PLANNING,
        coordinate); 
    assertEquals(MissionType.ATTACK, mission.getType());
    assertEquals(MissionPhase.PLANNING, mission.getPhase());
    assertEquals(0,mission.getMissionTime());
    mission.setFleetName("Test Fleet");
    mission.setMissionTime(1);
    mission.setPlanetBuilding("Test building");
    mission.setSunName("Test Sun");
    mission.setTargetPlanet("Test target");
    assertEquals("Attack - Planning\n"
        + "Coordinate:3,5\n"
        + "Building Planet:Test building\n"
        + "Fleet:Test Fleet\n"
        + "Time:1\n"
        + "Target planet:Test target", mission.toString());
    assertEquals("Test Fleet",mission.getFleetName());
    assertEquals(1,mission.getMissionTime());
    assertEquals("Test building",mission.getPlanetBuilding());
    assertEquals("",mission.getSunName());
    mission.setPhase(MissionPhase.TREKKING);
    assertEquals(MissionPhase.TREKKING,mission.getPhase());
    assertEquals("Test target", mission.getTargetPlanet());
    mission.setType(MissionType.COLONIZE);
    assertEquals(MissionType.COLONIZE,mission.getType());
    assertEquals(3,mission.getX());
    assertEquals(5,mission.getY());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMission2() {
    Coordinate coordinate = Mockito.mock(Coordinate.class);
    Mockito.when(coordinate.getX()).thenReturn(3);
    Mockito.when(coordinate.getY()).thenReturn(5);
    Mission mission = new Mission(MissionType.EXPLORE, MissionPhase.PLANNING,
        coordinate); 
    assertEquals(MissionType.EXPLORE, mission.getType());
    assertEquals(MissionPhase.PLANNING, mission.getPhase());
    assertEquals(0,mission.getMissionTime());
    mission.setFleetName("Test Fleet");
    mission.setMissionTime(1);
    mission.setPlanetBuilding("Test building");
    mission.setSunName("Test Sun");
    mission.setPlanetGathering("Gather Test");
    assertEquals("Explore - Planning\n"
        + "Coordinate:3,5\n"
        + "Building Planet:Test building\n"
        + "Fleet:Test Fleet\n"
        + "Time:1\n"
        + "Solar:Test Sun", mission.toString());
    assertEquals(null, mission.getPlanetGathering());
    assertEquals("Test Fleet",mission.getFleetName());
    assertEquals(1,mission.getMissionTime());
    assertEquals("Test building",mission.getPlanetBuilding());
    assertEquals("Test Sun",mission.getSunName());
    mission.setPhase(MissionPhase.TREKKING);
    assertEquals(MissionPhase.TREKKING,mission.getPhase());
    mission.setTargetPlanet("Test target");
    assertEquals("Test target", mission.getTargetPlanet());
    mission.setType(MissionType.GATHER);
    assertEquals(MissionType.GATHER, mission.getType());
    assertEquals("", mission.getSunName());
    mission.setShipType("TestShip");
    assertEquals("TestShip", mission.getShipType());
    assertEquals(3,mission.getX());
    assertEquals(5,mission.getY());
    mission.setPlanetGathering("Gather Test");
    assertEquals("Gather Test", mission.getPlanetGathering());
    assertEquals("Gather - Traveling\n"
        + "Coordinate:3,5\n"
        + "Building Planet:Test building\n"
        + "Fleet:Test Fleet\n"
        + "Time:1\n"
        + "Gather planet:Gather Test\n"
        + "Shiptype:TestShip\n"
        + "Target planet:Test target", mission.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeMission() {
    Coordinate coordinate = Mockito.mock(Coordinate.class);
    Mockito.when(coordinate.getX()).thenReturn(3);
    Mockito.when(coordinate.getY()).thenReturn(5);
    Mission mission = new Mission(MissionType.TRADE_FLEET, MissionPhase.PLANNING,
        coordinate); 
    assertEquals(MissionType.TRADE_FLEET, mission.getType());
    assertEquals(MissionPhase.PLANNING, mission.getPhase());
    assertEquals(0,mission.getMissionTime());
    mission.setFleetName("Test Fleet");
    mission.setMissionTime(1);
    mission.setPlanetBuilding("Test building");
    mission.setTargetPlanet("Target I");
    assertEquals("Trade fleet - Planning\n"
        + "Coordinate:3,5\n"
        + "Building Planet:Test building\n"
        + "Fleet:Test Fleet\n"
        + "Time:1\n"
        + "Target planet:Target I", mission.toString());
  }

}
