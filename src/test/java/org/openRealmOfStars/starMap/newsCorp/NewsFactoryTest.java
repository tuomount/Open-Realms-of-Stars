package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.starMap.StarMap;
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

}
