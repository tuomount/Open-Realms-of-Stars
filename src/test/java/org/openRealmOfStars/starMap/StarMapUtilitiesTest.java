package org.openRealmOfStars.starMap;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016,2017 Tuomo Untinen
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
* Tests for StarMapUtilites calls
*
*/
public class StarMapUtilitiesTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTrade() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.GREYANS, 2, 1);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.doTrade(planet, info)).thenReturn(10);
    DiplomacyBonusList diplomacy = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE))
        .thenReturn(true);
    StarMapUtilities.doTradeWithShips(diplomacy, fleet, planet, info, null);
    assertEquals(5, info.getTotalCredits());
    assertEquals(5, info2.getTotalCredits());
    StarMapUtilities.doTradeWithShips(null, fleet, planet, info, null);
    assertEquals(15, info.getTotalCredits());
    assertEquals(5, info2.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTrade2() {
    PlayerInfo info = new PlayerInfo(SpaceRace.SCAURIANS, 2, 0);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.GREYANS, 2, 1);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.doTrade(planet, info)).thenReturn(10);
    DiplomacyBonusList diplomacy = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE))
        .thenReturn(true);
    StarMapUtilities.doTradeWithShips(diplomacy, fleet, planet, info, null);
    assertEquals(7, info.getTotalCredits());
    assertEquals(7, info2.getTotalCredits());
    StarMapUtilities.doTradeWithShips(null, fleet, planet, info, null);
    assertEquals(22, info.getTotalCredits());
    assertEquals(7, info2.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTrade3() {
    PlayerInfo info = new PlayerInfo(SpaceRace.SCAURIANS, 2, 0);
    info.setEmpireName("Traders of Universum");
    PlayerInfo info2 = new PlayerInfo(SpaceRace.GREYANS, 2, 1);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(new Coordinate(5, 5));
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info2);
    Mockito.when(planet.getName()).thenReturn("Market");
    Mockito.when(planet.getImageInstructions()).thenReturn(ImageInstruction.PLANET_IRONWORLD1);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.doTrade(planet, info)).thenReturn(10);
    DiplomacyBonusList diplomacy = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE))
        .thenReturn(true);
    NewsCorpData newsData = new NewsCorpData(2);
    assertEquals(0, newsData.getUpcomingNews().length);
    StarMapUtilities.doTradeWithShips(diplomacy, fleet, planet, info, newsData);
    assertEquals(7, info.getTotalCredits());
    assertEquals(7, info2.getTotalCredits());
    assertEquals(1, newsData.getUpcomingNews().length);
    assertEquals(true, newsData.getUpcomingNews()[0].getNewsText().contains(
        "Traders of Universum"));
    assertEquals(true, newsData.getUpcomingNews()[0].getNewsText().contains(
        "Market"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureScoreLimit() {
    int limit = StarMapUtilities.calculateCultureScoreLimit(50, 50, 200, 0);
    assertEquals(400, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(75, 75, 200, 1);
    assertEquals(600, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(128, 128, 300, 1);
    assertEquals(1050, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(160, 160, 300, 1);
    assertEquals(1200, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(200, 200, 400, 1);
    assertEquals(1650, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(256, 256, 400, 1);
    assertEquals(1800, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(256, 256, 400, 2);
    assertEquals(2700, limit);
    limit = StarMapUtilities.calculateCultureScoreLimit(160, 160, 300, 3);
    assertEquals(2400, limit);
  }
}
