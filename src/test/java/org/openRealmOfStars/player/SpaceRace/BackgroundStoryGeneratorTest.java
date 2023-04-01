package org.openRealmOfStars.player.SpaceRace;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.Leader;

/**
*
* Open Realm of Stars game project 
* Copyright (C) 2023 Tuomo Untinen
*
* This program is free software; you can redistribute it and/or modify it under
* the terms of the GNU General Public License as published by the Free Software
* Foundation; either version 2 of the License, or (at your option) any later
* version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
*
* You should have received a copy of the GNU General Public License along with
* this program; if not, see http://www.gnu.org/licenses/
*
*
* Tests for Background story generator
*
*/
public class BackgroundStoryGeneratorTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateBackgroundStoryForHumans() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Human federation");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.FEDERATION);
    Leader leader = Mockito.mock(Leader.class);
    Mockito.when(leader.getName()).thenReturn("Rodolf Rednose");
    Mockito.when(leader.getCallName()).thenReturn("President Rodolf Rednose");
    Mockito.when(info.getRuler()).thenReturn(leader);

    Planet startingPlanet = Mockito.mock(Planet.class);
    Mockito.when(startingPlanet.getName()).thenReturn("Earth III");
    Mockito.when(startingPlanet.getPlanetType())
        .thenReturn(PlanetTypes.WATERWORLD9);
    int starYear = 2400;
    String result = BackgroundStoryGenerator.generateBackgroundStory(
        info, startingPlanet, starYear);
    assertEquals(true, result.contains("Rednose"));
    assertEquals(true, result.contains("federation"));
    assertEquals(true, result.contains("Humans"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateBackgroundStoryForTerrans() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Terran Utopia");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.UTOPIA);
    Leader leader = Mockito.mock(Leader.class);
    Mockito.when(leader.getName()).thenReturn("Rodolf Rednose");
    Mockito.when(leader.getCallName()).thenReturn("President Rodolf Rednose");
    Mockito.when(info.getRuler()).thenReturn(leader);

    Planet startingPlanet = Mockito.mock(Planet.class);
    Mockito.when(startingPlanet.getName()).thenReturn("Mars");
    Mockito.when(startingPlanet.getPlanetType())
        .thenReturn(PlanetTypes.DESERTWORLD1);
    int starYear = 2400;
    String result = BackgroundStoryGenerator.generateBackgroundStory(
        info, startingPlanet, starYear);
    assertEquals(true, result.contains("Rednose"));
    assertEquals(true, result.contains("utopia"));
    assertEquals(true, result.contains("Terrans"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateBackgroundStoryForSporks() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Sporks kingdom");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Leader leader = Mockito.mock(Leader.class);
    Mockito.when(leader.getName()).thenReturn("Max Power");
    Mockito.when(leader.getCallName()).thenReturn("King Max Power");
    Mockito.when(info.getRuler()).thenReturn(leader);

    Planet startingPlanet = Mockito.mock(Planet.class);
    Mockito.when(startingPlanet.getName()).thenReturn("Mars");
    Mockito.when(startingPlanet.getPlanetType())
        .thenReturn(PlanetTypes.CARBONWORLD1);
    int starYear = 2400;
    String result = BackgroundStoryGenerator.generateBackgroundStory(
        info, startingPlanet, starYear);
    assertEquals(true, result.contains("Max Power"));
    assertEquals(true, result.contains("kingdom"));
    assertEquals(true, result.contains("Sporks"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateBackgroundStoryForSagritarrian() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Sagittarian Feudalism");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.FEUDALISM);
    Leader leader = Mockito.mock(Leader.class);
    Mockito.when(leader.getName()).thenReturn("Max Power");
    Mockito.when(leader.getCallName()).thenReturn("King Max Power");
    Mockito.when(info.getRuler()).thenReturn(leader);

    Planet startingPlanet = Mockito.mock(Planet.class);
    Mockito.when(startingPlanet.getName()).thenReturn("Mars");
    Mockito.when(startingPlanet.getPlanetType())
        .thenReturn(PlanetTypes.CARBONWORLD2);
    int starYear = 2400;
    String result = BackgroundStoryGenerator.generateBackgroundStory(
        info, startingPlanet, starYear);
    assertEquals(true, result.contains("Max Power"));
    assertEquals(true, result.contains("feudalism"));
    assertEquals(true, result.contains("Sagittarians"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateBackgroundStoryForMechions() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Mechion AI");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Leader leader = Mockito.mock(Leader.class);
    Mockito.when(leader.getName()).thenReturn("Droid D-9");
    Mockito.when(leader.getCallName()).thenReturn("Main loop Droid D-9");
    Mockito.when(info.getRuler()).thenReturn(leader);

    Planet startingPlanet = Mockito.mock(Planet.class);
    Mockito.when(startingPlanet.getName()).thenReturn("Alpha Muert");
    Mockito.when(startingPlanet.getPlanetType())
        .thenReturn(PlanetTypes.IRONWORLD1);
    int starYear = 2400;
    String result = BackgroundStoryGenerator.generateBackgroundStory(
        info, startingPlanet, starYear);
    assertEquals(true, result.contains("D-9"));
    assertEquals(true, result.contains("AI"));
    assertEquals(true, result.contains("Mechions"));
  }

}
