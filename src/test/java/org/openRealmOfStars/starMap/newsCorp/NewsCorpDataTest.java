package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* News Corporation Data Test
*
*/
public class NewsCorpDataTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatValid() {
    NewsCorpData data = new NewsCorpData(8);
    if (data.getCredit() == null) {
      fail("Credit is null!");
    }
    if (data.getCultural() == null) {
      fail("Cultural is null!");
    }
    if (data.getMilitary() == null) {
      fail("Military is null!");
    }
    if (data.getResearch() == null) {
      fail("Research is null!");
    }
    if (data.getPlanets() == null) {
      fail("Planets are null!");
    }
    if (data.getPopulation() == null) {
      fail("Population is null!");
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatPlanets() {
    NewsCorpData data = new NewsCorpData(3);
    Planet unhabitated = Mockito.mock(Planet.class);
    Mockito.when(unhabitated.getPlanetOwnerIndex()).thenReturn(-1);
    Planet planet1 = Mockito.mock(Planet.class);
    Mockito.when(planet1.getPlanetOwnerIndex()).thenReturn(0);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);

    ArrayList<Planet> list = new ArrayList<>();
    list.add(unhabitated);
    list.add(unhabitated);
    list.add(planet2);
    list.add(planet1);
    list.add(unhabitated);
    list.add(planet3);
    list.add(unhabitated);
    list.add(planet2);
    list.add(planet3);
    list.add(unhabitated);
    list.add(planet3);
    list.add(planet2);
    list.add(planet2);
    list.add(unhabitated);
    data.calculatePlanets(list);
    int[][] value = data.getPlanets().getGalaxyData();
    assertEquals(1,value[0][0]);
    assertEquals(4,value[1][0]);
    assertEquals(3,value[2][0]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatPopulation() {
    NewsCorpData data = new NewsCorpData(2);
    Planet unhabitated = Mockito.mock(Planet.class);
    Mockito.when(unhabitated.getPlanetOwnerIndex()).thenReturn(-1);
    Planet planet1 = Mockito.mock(Planet.class);
    Mockito.when(planet1.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet1.getTotalPopulation()).thenReturn(4);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getTotalPopulation()).thenReturn(3);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet3.getTotalPopulation()).thenReturn(1);

    ArrayList<Planet> list = new ArrayList<>();
    list.add(unhabitated);
    list.add(unhabitated);
    list.add(planet1);
    list.add(planet1);
    list.add(unhabitated);
    list.add(planet2);
    list.add(unhabitated);
    list.add(planet3);
    list.add(unhabitated);
    data.calculatePopulation(list);
    int[][] value = data.getPopulation().getGalaxyData();
    assertEquals(8,value[0][0]);
    assertEquals(4,value[1][0]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatMilitary() {
    NewsCorpData data = new NewsCorpData(2);
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Fleet militaryFleet = Mockito.mock(Fleet.class);
    Mockito.when(militaryFleet.getMilitaryValue()).thenReturn(10);
    Fleet colonFleet = Mockito.mock(Fleet.class);
    Mockito.when(colonFleet.getMilitaryValue()).thenReturn(0);
    FleetList fleetList1 = Mockito.mock(FleetList.class);
    Mockito.when(fleetList1.getNumberOfFleets()).thenReturn(3);
    Mockito.when(fleetList1.getByIndex(0)).thenReturn(militaryFleet);
    Mockito.when(fleetList1.getByIndex(1)).thenReturn(colonFleet);
    Mockito.when(fleetList1.getByIndex(2)).thenReturn(militaryFleet);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info1.getFleets()).thenReturn(fleetList1);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info1);
    FleetList fleetList2 = Mockito.mock(FleetList.class);
    Mockito.when(fleetList2.getNumberOfFleets()).thenReturn(5);
    Mockito.when(fleetList2.getByIndex(0)).thenReturn(militaryFleet);
    Mockito.when(fleetList2.getByIndex(1)).thenReturn(colonFleet);
    Mockito.when(fleetList2.getByIndex(2)).thenReturn(militaryFleet);
    Mockito.when(fleetList2.getByIndex(3)).thenReturn(colonFleet);
    Mockito.when(fleetList2.getByIndex(4)).thenReturn(militaryFleet);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getFleets()).thenReturn(fleetList2);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info2);
    
    data.calculateMilitary(players);;
    int[][] value = data.getMilitary().getGalaxyData();
    assertEquals(20,value[0][0]);
    assertEquals(30,value[1][0]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatCredit() {
    NewsCorpData data = new NewsCorpData(5);
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(5);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info1.getTotalCredits()).thenReturn(34);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info1);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getTotalCredits()).thenReturn(55);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info2);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getTotalCredits()).thenReturn(1);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info3);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getTotalCredits()).thenReturn(123);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info4);
    PlayerInfo info5 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info5.getTotalCredits()).thenReturn(67);
    Mockito.when(players.getPlayerInfoByIndex(4)).thenReturn(info5);
    
    data.calculateCredit(players);
    int[][] value = data.getCredit().getGalaxyData();
    assertEquals(34,value[0][0]);
    assertEquals(55,value[1][0]);
    assertEquals(1,value[2][0]);
    assertEquals(123,value[3][0]);
    assertEquals(67,value[4][0]);
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatInValid() {
    NewsCorpData data = new NewsCorpData(-1);
    // Should not never reach here!
    data.getCredit();
  }

}
