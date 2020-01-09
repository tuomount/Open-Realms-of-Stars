package org.openRealmOfStars.player.leader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2019,2020 Tuomo Untinen
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
 * Test for Leader utility class
 */
public class LeaderUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMechionAi() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMothoidEmpire() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MOTHOIDS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerSporkKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

}
