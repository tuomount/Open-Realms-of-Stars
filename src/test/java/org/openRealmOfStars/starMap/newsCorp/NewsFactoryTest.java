package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
* News Factory Tests
*
*/
public class NewsFactoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWar() {
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeWarNews(aggressor, defender, null, null);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWarOnPlanet() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeWarNews(aggressor, defender, planet, null);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBorderCrossing() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.isMultipleBorderCrossing(1)).thenReturn(true);
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getIndex(defender)).thenReturn(1);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    NewsData news = NewsFactory.makeWarNews(aggressor, defender, planet, null);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(false, news.getNewsText().contains("border crossing"));
    news = NewsFactory.makeWarNews(aggressor, defender, planet, map);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("border crossing"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWarWithAggressiveAggressor() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.IRONWORLD3.getImageInstructions());
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(aggressor.getAiAttitude()).thenReturn(Attitude.AGGRESSIVE);
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeWarNews(aggressor, defender, planet, null);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("aggressive"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWarWithMilitaristicAggressor() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(aggressor.getAiAttitude()).thenReturn(Attitude.MILITARISTIC);
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeWarNews(aggressor, defender, planet, null);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("militaristic"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWarWithPeacefulAggressor() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.IRONWORLD2.getImageInstructions());
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(aggressor.getAiAttitude()).thenReturn(Attitude.PEACEFUL);
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeWarNews(aggressor, defender, planet, null);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("peace"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeace() {
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makePeaceNews(peaceMaker, acceptor, null);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeaceWithAggressiveMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    Mockito.when(planet.getName()).thenReturn("Planet I");
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.AGGRESSIVE);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makePeaceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("aggressive"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeaceWithDiplomaticMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.DIPLOMATIC);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.ICEWORLD1.getImageInstructions());
    NewsData news = NewsFactory.makePeaceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("diplomatic"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPeaceWithPeacefulMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.PEACEFUL);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makePeaceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("peace"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeAlliance() {
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeTradeAllianceNews(peaceMaker, acceptor, null);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeAllianceWithMerchanticalMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    Mockito.when(planet.getName()).thenReturn("Planet I");
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.MERCHANTICAL);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeTradeAllianceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("interest to trade"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeAllianceWithDiplomaticMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD2.getImageInstructions());
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.DIPLOMATIC);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeTradeAllianceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("diplomatic"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeAllianceWithPeacefulMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.IRONWORLD1.getImageInstructions());
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.PEACEFUL);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeTradeAllianceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("peace"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAlliance() {
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeAllianceNews(peaceMaker, acceptor, null);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAllianceWithDiplomaticMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD4.getImageInstructions());
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.DIPLOMATIC);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeAllianceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("diplomatic"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAllianceWithPeacefulMaker() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD1.getImageInstructions());
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(peaceMaker.getAiAttitude()).thenReturn(Attitude.PEACEFUL);
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeAllianceNews(peaceMaker, acceptor, planet);
    assertEquals(true, news.getImageInstructions().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains("planet"));
    assertEquals(true, news.getNewsText().contains(
        peaceMaker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("peace"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatNews() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    NewsCorpData data = Mockito.mock(NewsCorpData.class);
    Mockito.when(data.isFirstStats()).thenReturn(true);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getNewsCorpData()).thenReturn(data);
    
    NewsData news = NewsFactory.makeStatNews(map);
    assertEquals(true, news.getImageInstructions().contains(
        "FIRST STATISTICAL RESEARCH DONE!"));
    assertEquals(true, news.getNewsText().contains("GBNC has done first"
        + " statistical research about Realms in Stars."));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatNewsAfterFirstOne() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    GalaxyStat stat = Mockito.mock(GalaxyStat.class);
    Mockito.when(stat.getBiggest()).thenReturn(0);
    NewsCorpData data = Mockito.mock(NewsCorpData.class);
    Mockito.when(data.isFirstStats()).thenReturn(false);
    Mockito.when(data.getMilitary()).thenReturn(stat);
    Mockito.when(data.getCredit()).thenReturn(stat);
    Mockito.when(data.getCultural()).thenReturn(stat);
    Mockito.when(data.getPlanets()).thenReturn(stat);
    Mockito.when(data.getPopulation()).thenReturn(stat);
    Mockito.when(data.getResearch()).thenReturn(stat);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getNewsCorpData()).thenReturn(data);
    Mockito.when(map.getPlayerByIndex(Mockito.anyInt())).thenReturn(info);
    boolean[] newsText = new boolean[6];
    int count = 10000;
    do {
      count--;
      NewsData news = NewsFactory.makeStatNews(map);
      if (news.getImageInstructions().contains("GREATEST MILITARY!")) {
        newsText[0] = true;
        assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
        assertEquals(true, news.getNewsText().contains("greatest military power"));
      }
      if (news.getImageInstructions().contains("WEALTHIEST REALM!")) {
        newsText[1] = true;
        assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
        assertEquals(true, news.getNewsText().contains("wealthiest realm"));
      }
      if (news.getImageInstructions().contains("MOST OF PLANETS!")) {
        newsText[2] = true;
        assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
        assertEquals(true, news.getNewsText().contains("most of the colonized planets"));
      }
      if (news.getImageInstructions().contains("MOST OF PEOPLE!")) {
        newsText[3] = true;
        assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
        assertEquals(true, news.getNewsText().contains("most of the people "));
      }
      if (news.getImageInstructions().contains("MOST CULTURAL POWER!")) {
        newsText[4] = true;
        assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
        assertEquals(true, news.getNewsText().contains("the biggest cultural"));
      }
      if (news.getImageInstructions().contains("MOST SCIENTIFIC REALM!")) {
        newsText[5] = true;
        assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
        assertEquals(true, news.getNewsText().contains("the biggest scientific"));
      }
      if (count == 0) {
        // Check which text did not found
        assertEquals(true, newsText[0]);
        assertEquals(true, newsText[1]);
        assertEquals(true, newsText[2]);
        assertEquals(true, newsText[3]);
        assertEquals(true, newsText[4]);
        assertEquals(true, newsText[5]);
        // This will always assert
        assertFalse(true);
      }
    } while (!newsText[0] && !newsText[1] && !newsText[2]
        && !newsText[3] && !newsText[4] && !newsText[5]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testConquerPlanet() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    Mockito.when(defender.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.CARBONWORLD1.getImageInstructions());
    NewsData news = NewsFactory.makePlanetConqueredNews(aggressor, defender, planet, false);
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testConquerPlanetWithNukes() {
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.SILICONWORLD1.getImageInstructions());
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getRadiationLevel()).thenReturn(9);
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    Mockito.when(defender.getRace()).thenReturn(SpaceRace.HUMAN);
    NewsData news = NewsFactory.makePlanetConqueredNews(aggressor, defender, planet, true);
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("nuclear"));
    assertEquals(true, news.getNewsText().contains("raised to 9"));
  }

}
