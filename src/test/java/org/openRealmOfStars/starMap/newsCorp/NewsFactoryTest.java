package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.history.History;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.randomEvent.BadRandomType;
import org.openRealmOfStars.starMap.randomEvent.GoodRandomType;
import org.openRealmOfStars.starMap.randomEvent.RandomEvent;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.Votes;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.Athlete;
import org.openRealmOfStars.starMap.vote.sports.Sports;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;

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
  public void testRandomEvent() {
    RandomEvent event = Mockito.mock(RandomEvent.class);
    Mockito.when(event.getImageInstructions()).thenReturn("BACKGROUND(BLACK)");
    Mockito.when(event.getBadType()).thenReturn(BadRandomType.SOLAR_ACTIVITY_INCREASE);
    Mockito.when(event.getGoodType()).thenReturn(GoodRandomType.SOLAR_ACTIVITY_DIMISHED);
    Mockito.when(event.getText()).thenReturn("Test event text");
    NewsData news = NewsFactory.makeRandomEventNews(event);
    assertEquals("Test event text", news.getNewsText());
    assertNotEquals(null, news.getImageInstructions());
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
    History history = Mockito.mock(History.class);
    Mockito.when(map.getHistory()).thenReturn(history);
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
  public void testDefensivePactActivation() {
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(aggressor.getAiAttitude()).thenReturn(Attitude.AGGRESSIVE);
    String[] list = {"Empire of defense", "King of defense"};
    NewsData news = NewsFactory.makeDefensiveActivation(aggressor, list);
    assertEquals(true, news.getImageInstructions().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        "Empire of defense"));
    assertEquals(true, news.getNewsText().contains(
        "King of defense"));
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
  public void testUnitedGalaxyTowerRace() {
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeUnitedGalaxyTowerRaceTie(peaceMaker, acceptor);
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
  public void testUnitedGalaxyTowerBuilding() {
    PlayerInfo builder = Mockito.mock(PlayerInfo.class);
    Mockito.when(builder.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(builder.getRace()).thenReturn(SpaceRace.CENTAURS);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD3.getImageInstructions());
    Mockito.when(planet.getName()).thenReturn("Planet I");
    NewsData news = NewsFactory.makeUnitedGalaxyTowerNews(builder, planet);
    assertEquals(true, news.getImageInstructions().contains("UNITED GALAXY TOWER"));
    assertEquals(true, news.getNewsText().contains("Empire of Test"));
    assertEquals(true, news.getNewsText().contains("United Galaxy Tower"));
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
  public void testGalacticSportsArena() {
    PlayerInfo builder = Mockito.mock(PlayerInfo.class);
    Mockito.when(builder.getEmpireName()).thenReturn("Empire of Test");
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD2.getImageInstructions());
    Mockito.when(planet.getName()).thenReturn("Test planet");
    NewsData news = NewsFactory.makeGalacticSportsNews(builder, planet);
    assertEquals(true, news.getImageInstructions().contains("SPORT"));
    assertEquals(true, news.getNewsText().contains(
        builder.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticSportsArenaDestroyed() {
    PlayerInfo builder = Mockito.mock(PlayerInfo.class);
    Mockito.when(builder.getEmpireName()).thenReturn("Empire of Test");
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD2.getImageInstructions());
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(builder);
    Mockito.when(planet.getName()).thenReturn("Test planet");
    NewsData news = NewsFactory.makeNoGalacticSportsNews(planet, true);
    assertEquals(true, news.getImageInstructions().contains("SPORT")
        || news.getImageInstructions().contains("CANCELLATION!"));
    assertEquals(true, news.getNewsText().contains(
        planet.getName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticSportsArenaBombed() {
    PlayerInfo builder = Mockito.mock(PlayerInfo.class);
    Mockito.when(builder.getEmpireName()).thenReturn("Empire of Test");
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD2.getImageInstructions());
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(null);
    Mockito.when(planet.getName()).thenReturn("Test planet");
    NewsData news = NewsFactory.makeNoGalacticSportsNews(planet, false);
    assertEquals(true, news.getImageInstructions().contains("SPORT")
        || news.getImageInstructions().contains("CANCELLATION!"));
    assertEquals(true, news.getNewsText().contains(
        planet.getName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticSportsEnding() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getImageInstructions()).thenReturn(
        PlanetTypes.WATERWORLD2.getImageInstructions());
    Mockito.when(planet.getName()).thenReturn("Test planet");
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getPlanetName()).thenReturn("Test planet");
    Sports sports = Mockito.mock(Sports.class);
    Athlete[] atheletes = new Athlete[4];
    atheletes[0] = Mockito.mock(Athlete.class);
    Mockito.when(atheletes[0].getRealm()).thenReturn(info);
    atheletes[1] = Mockito.mock(Athlete.class);
    Mockito.when(atheletes[1].getRealm()).thenReturn(info);
    atheletes[2] = Mockito.mock(Athlete.class);
    Mockito.when(atheletes[2].getRealm()).thenReturn(info);
    atheletes[3] = Mockito.mock(Athlete.class);
    Mockito.when(atheletes[3].getRealm()).thenReturn(info);
    Mockito.when(sports.getAthletes()).thenReturn(atheletes);
    NewsData news = NewsFactory.makeGalacticSportsEndingNews(vote, sports, planet);
    assertEquals(true, news.getImageInstructions().contains("OLYMPICS"));
    assertEquals(true, news.getNewsText().contains(
        planet.getName()));
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
  public void testTradeEmbargo() {
    PlayerInfo offerer = Mockito.mock(PlayerInfo.class);
    Mockito.when(offerer.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    PlayerInfo embargoed = Mockito.mock(PlayerInfo.class);
    Mockito.when(embargoed.getEmpireName()).thenReturn("Clan of Evil");
    NewsData news = NewsFactory.makeTradeEmbargoNews(offerer, acceptor,
        embargoed, null);
    assertEquals(true, news.getImageInstructions().contains(
        offerer.getEmpireName()));
    assertEquals(true, news.getImageInstructions().contains(
        embargoed.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        offerer.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        acceptor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        embargoed.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDefensePact() {
    PlayerInfo peaceMaker = Mockito.mock(PlayerInfo.class);
    Mockito.when(peaceMaker.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo acceptor = Mockito.mock(PlayerInfo.class);
    Mockito.when(acceptor.getEmpireName()).thenReturn("Democracy of Defender");
    NewsData news = NewsFactory.makeDefensivePactNews(peaceMaker, acceptor, null);
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
  public void testLostNews() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    NewsData news = NewsFactory.makeLostNews(info);
    assertEquals(true, news.getImageInstructions().contains(
        "Human"));
    assertEquals(true, news.getNewsText().contains(info.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateNews() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Space pirates");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPACE_PIRATE);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList list = Mockito.mock(PlayerList.class);
    Mockito.when(list.getBoardPlayer()).thenReturn(info);
    Mockito.when(map.getPlayerList()).thenReturn(list);
    
    NewsData news = NewsFactory.makeSpacePiratesNews(map);
    assertEquals(true, news.getImageInstructions().contains(
        "SPACE PIRATES"));
    assertEquals(true, news.getNewsText().contains("space pirates"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatNewsHalfway() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Alliance of Test");
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.SCAURIANS);
    GalaxyStat stat = Mockito.mock(GalaxyStat.class);
    Mockito.when(stat.getBiggest()).thenReturn(0);
    Mockito.when(stat.getSecond()).thenReturn(1);
    NewsCorpData data = Mockito.mock(NewsCorpData.class);
    Mockito.when(data.isFirstStats()).thenReturn(false);
    Mockito.when(data.getMilitary()).thenReturn(stat);
    Mockito.when(data.getCredit()).thenReturn(stat);
    Mockito.when(data.getCultural()).thenReturn(stat);
    Mockito.when(data.getPlanets()).thenReturn(stat);
    Mockito.when(data.getPopulation()).thenReturn(stat);
    Mockito.when(data.getResearch()).thenReturn(stat);
    Mockito.when(data.generateScores()).thenReturn(stat);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getNewsCorpData()).thenReturn(data);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    NewsData news = NewsFactory.makeScoreNewsHalf(map);
    assertEquals(true, news.getImageInstructions().contains(
        "RACE FOR GREATEST REALM IN HALFWAY!"));
    assertEquals(true, news.getNewsText().contains(
        "Empire of Test is the greatest realm in whole galaxy at the moment!"
        + " Second greatest realm is Alliance of Test"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatNewsLastQuarter() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Alliance of Test");
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.SCAURIANS);
    GalaxyStat stat = Mockito.mock(GalaxyStat.class);
    Mockito.when(stat.getBiggest()).thenReturn(0);
    Mockito.when(stat.getSecond()).thenReturn(1);
    NewsCorpData data = Mockito.mock(NewsCorpData.class);
    Mockito.when(data.isFirstStats()).thenReturn(false);
    Mockito.when(data.getMilitary()).thenReturn(stat);
    Mockito.when(data.getCredit()).thenReturn(stat);
    Mockito.when(data.getCultural()).thenReturn(stat);
    Mockito.when(data.getPlanets()).thenReturn(stat);
    Mockito.when(data.getPopulation()).thenReturn(stat);
    Mockito.when(data.getResearch()).thenReturn(stat);
    Mockito.when(data.generateScores()).thenReturn(stat);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getNewsCorpData()).thenReturn(data);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    NewsData news = NewsFactory.makeScoreNewsLastQuarter(map);
    assertEquals(true, news.getImageInstructions().contains(
        "RACE FOR GREATEST REALM!"));
    assertEquals(true, news.getNewsText().contains(
        "Empire of Test is the greatest realm in whole galaxy at the moment!"
        + " Second greatest realm is Alliance of Test"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatNewsEnd() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Alliance of Test");
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.SCAURIANS);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.hasAlliance()).thenReturn(false);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    GalaxyStat stat = Mockito.mock(GalaxyStat.class);
    Mockito.when(stat.getBiggest()).thenReturn(0);
    Mockito.when(stat.getSecond()).thenReturn(1);
    NewsCorpData data = Mockito.mock(NewsCorpData.class);
    Mockito.when(data.isFirstStats()).thenReturn(false);
    Mockito.when(data.getMilitary()).thenReturn(stat);
    Mockito.when(data.getCredit()).thenReturn(stat);
    Mockito.when(data.getCultural()).thenReturn(stat);
    Mockito.when(data.getPlanets()).thenReturn(stat);
    Mockito.when(data.getPopulation()).thenReturn(stat);
    Mockito.when(data.getResearch()).thenReturn(stat);
    Mockito.when(data.generateScores()).thenReturn(stat);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getNewsCorpData()).thenReturn(data);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    Mockito.when(players.getCurrentMaxRealms()).thenReturn(2);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    NewsData news = NewsFactory.makeScoreNewsAtEnd(map);
    assertEquals(true, news.getImageInstructions().contains(
        "THE GREATEST REALM ALL TIME!"));
    assertEquals(true, news.getNewsText().contains(
        "Empire of Test is the greatest realm in whole galaxy!"
        + " Second greatest realm is Alliance of Test !"));
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
    Mockito.when(data.generateScores()).thenReturn(stat);
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
    NewsData news = NewsFactory.makePlanetConqueredNews(aggressor, defender, planet, null);
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
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(9);
    PlayerInfo aggressor = Mockito.mock(PlayerInfo.class);
    Mockito.when(aggressor.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo defender = Mockito.mock(PlayerInfo.class);
    Mockito.when(defender.getEmpireName()).thenReturn("Democracy of Defender");
    Mockito.when(defender.getRace()).thenReturn(SpaceRace.HUMAN);
    NewsData news = NewsFactory.makePlanetConqueredNews(aggressor, defender, planet,
        "nuclear bombs, raised to 9");
    assertEquals(true, news.getNewsText().contains(
        aggressor.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        defender.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(planet.getName()));
    assertEquals(true, news.getNewsText().contains("nuclear"));
    assertEquals(true, news.getNewsText().contains("raised to 9"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureVictory() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(500);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getCulturalValue()).thenReturn(50);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCulturalValue()).thenReturn(100);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getByIndex(0)).thenReturn(fleet1);
    Mockito.when(fleetList.getByIndex(1)).thenReturn(fleet2);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(2);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire II");
    Mockito.when(info2.getFleets()).thenReturn(fleetList);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire III");
    Mockito.when(info3.getFleets()).thenReturn(fleetList);
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getFleets()).thenReturn(fleetList);
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    boolean[] broadcasters = new boolean[4];
    broadcasters[0] = true;
    broadcasters[1] = true;
    broadcasters[2] = true;
    broadcasters[3] = true;
    NewsData data = NewsFactory.makeCulturalVictoryNewsAtEnd(map, broadcasters);
    assertNotNull(data);
    assertEquals(true, data.getNewsText().contains("Empire III"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureVictoryNoBroadcasting() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(500);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getCulturalValue()).thenReturn(50);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCulturalValue()).thenReturn(100);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getByIndex(0)).thenReturn(fleet1);
    Mockito.when(fleetList.getByIndex(1)).thenReturn(fleet2);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(2);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire II");
    Mockito.when(info2.getFleets()).thenReturn(fleetList);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire III");
    Mockito.when(info3.getFleets()).thenReturn(fleetList);
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getFleets()).thenReturn(fleetList);
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    boolean[] broadcasters = new boolean[4];
    broadcasters[0] = false;
    broadcasters[1] = false;
    broadcasters[2] = false;
    broadcasters[3] = false;
    NewsData data = NewsFactory.makeCulturalVictoryNewsAtEnd(map, broadcasters);
    assertEquals(null, data);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureVictoryDisabled() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(5);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(300);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getCulturalValue()).thenReturn(50);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCulturalValue()).thenReturn(100);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getByIndex(0)).thenReturn(fleet1);
    Mockito.when(fleetList.getByIndex(1)).thenReturn(fleet2);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(2);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire II");
    Mockito.when(info2.getFleets()).thenReturn(fleetList);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire III");
    Mockito.when(info3.getFleets()).thenReturn(fleetList);
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getFleets()).thenReturn(fleetList);
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    Mockito.when(map.getScoreCulture()).thenReturn(-1);
    boolean[] broadcasters = new boolean[4];
    broadcasters[0] = true;
    broadcasters[1] = true;
    broadcasters[2] = true;
    broadcasters[3] = true;
    NewsData data = NewsFactory.makeCulturalVictoryNewsAtEnd(map, broadcasters);
    assertNull(data);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureVictoryNoVictory() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(5);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(200);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getCulturalValue()).thenReturn(50);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCulturalValue()).thenReturn(100);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getByIndex(0)).thenReturn(fleet1);
    Mockito.when(fleetList.getByIndex(1)).thenReturn(fleet2);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(2);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire II");
    Mockito.when(info2.getFleets()).thenReturn(fleetList);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire III");
    Mockito.when(info3.getFleets()).thenReturn(fleetList);
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getFleets()).thenReturn(fleetList);
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    boolean[] broadcasters = new boolean[4];
    broadcasters[0] = true;
    broadcasters[1] = true;
    broadcasters[2] = true;
    broadcasters[3] = true;
    NewsData data = NewsFactory.makeCulturalVictoryNewsAtEnd(map, broadcasters);
    assertNull(data);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureVictoryAlliance() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(5);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(200);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Fleet fleet1 = Mockito.mock(Fleet.class);
    Mockito.when(fleet1.getCulturalValue()).thenReturn(50);
    Fleet fleet2 = Mockito.mock(Fleet.class);
    Mockito.when(fleet2.getCulturalValue()).thenReturn(100);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(fleetList.getByIndex(0)).thenReturn(fleet1);
    Mockito.when(fleetList.getByIndex(1)).thenReturn(fleet2);
    Mockito.when(fleetList.getNumberOfFleets()).thenReturn(2);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(1);
    Diplomacy diplomacy2 = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy2.getAllianceIndex()).thenReturn(0);
    Diplomacy diplomacy3 = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy3.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire II");
    Mockito.when(info2.getFleets()).thenReturn(fleetList);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy2);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire III");
    Mockito.when(info3.getFleets()).thenReturn(fleetList);
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy3);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getFleets()).thenReturn(fleetList);
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy3);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    boolean[] broadcasters = new boolean[4];
    broadcasters[0] = true;
    broadcasters[1] = true;
    broadcasters[2] = true;
    broadcasters[3] = true;
    NewsData data = NewsFactory.makeCulturalVictoryNewsAtEnd(map, broadcasters);
    assertNotNull(data);
    assertEquals(true, data.getNewsText().contains("Empire I"));
    assertEquals(true, data.getNewsText().contains("Empire II"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDominationVictory() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(300);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire II");
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire III");
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    NewsData data = NewsFactory.makeDominationVictoryNewsAtEnd(map);
    assertNotNull(data);
    assertEquals(true, data.getNewsText().contains("Empire II"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDominationVictoryAlliance() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(0);
    Mockito.when(planet.getCulture()).thenReturn(200);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet II");
    Mockito.when(planet2.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(1);
    Mockito.when(planet2.getCulture()).thenReturn(200);
    Planet planet3 = Mockito.mock(Planet.class);
    Mockito.when(planet3.getName()).thenReturn("Planet III");
    Mockito.when(planet3.getPlanetOwnerIndex()).thenReturn(2);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(2);
    Mockito.when(planet3.getCulture()).thenReturn(300);
    Planet planet4 = Mockito.mock(Planet.class);
    Mockito.when(planet4.getName()).thenReturn("Planet IV");
    Mockito.when(planet4.getPlanetOwnerIndex()).thenReturn(1);
    Mockito.when(planet.getHomeWorldIndex()).thenReturn(3);
    Mockito.when(planet4.getCulture()).thenReturn(200);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    planets.add(planet3);
    planets.add(planet4);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(2);
    Diplomacy diplomacy2 = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy2.getAllianceIndex()).thenReturn(1);
    Diplomacy diplomacy3 = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy3.getAllianceIndex()).thenReturn(-1);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire I");
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy3);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Empire Centaurs");
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Empire Homarians");
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy2);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Empire IV");
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy3);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    Mockito.when(playerList.getPlayerInfoByIndex(2)).thenReturn(info3);
    Mockito.when(playerList.getPlayerInfoByIndex(3)).thenReturn(info4);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(info);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(info2);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(info3);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(info4);
    NewsData data = NewsFactory.makeDominationVictoryNewsAtEnd(map);
    assertNotNull(data);
    assertEquals(true, data.getNewsText().contains("Empire Centaurs"));
    assertEquals(true, data.getNewsText().contains("Empire Homarians"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScientificAchievement() {
    PlayerInfo maker = Mockito.mock(PlayerInfo.class);
    Mockito.when(maker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(maker.getRace()).thenReturn(SpaceRace.HUMAN);
    Building building = Mockito.mock(Building.class);
    Mockito.when(building.getName()).thenReturn("Awesome building");
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetType()).thenReturn(PlanetTypes.IRONWORLD6);
    Mockito.when(planet.getImageInstructions()).thenReturn(PlanetTypes.IRONWORLD6.getImageInstructions());
    NewsData news = NewsFactory.makeScientificAchivementNews(maker, planet, building);
    assertEquals(true, news.getNewsText().contains(
        planet.getName()));
    assertEquals(true, news.getNewsText().contains(
        maker.getEmpireName()));
    assertEquals(true, news.getNewsText().contains(
        building.getName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScientificAchievement2() {
    PlayerInfo maker = Mockito.mock(PlayerInfo.class);
    Mockito.when(maker.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(maker.getRace()).thenReturn(SpaceRace.HUMAN);
    Building building = null;
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet I");
    Mockito.when(planet.getPlanetType()).thenReturn(PlanetTypes.IRONWORLD6);
    Mockito.when(planet.getImageInstructions()).thenReturn(PlanetTypes.IRONWORLD6.getImageInstructions());
    NewsData news = NewsFactory.makeScientificAchivementNews(maker, planet, building);
    assertEquals(true, news.getNewsText().contains(
        planet.getName()));
    assertEquals(true, news.getNewsText().contains(
        maker.getEmpireName()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScientificVictory() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    PlayerInfo winner = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(winner.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(winner.getEmpireName()).thenReturn("Empire of Homarian");
    Mockito.when(winner.getRace()).thenReturn(SpaceRace.HOMARIANS);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Planet planet2 = Mockito.mock(Planet.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    Mockito.when(planet.getPlanetType()).thenReturn(PlanetTypes.CARBONWORLD1);
    Mockito.when(planet2.getPlanetType()).thenReturn(PlanetTypes.ARTIFICIALWORLD1);
    Building[] buildings = new Building[2];
    buildings[0] = Mockito.mock(Building.class);
    buildings[1] = Mockito.mock(Building.class);
    Mockito.when(buildings[1].getScientificAchievement()).thenReturn(true);
    Mockito.when(planet.getBuildingList()).thenReturn(buildings);
    Mockito.when(planet2.getBuildingList()).thenReturn(buildings);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(winner);
    Mockito.when(map.getScoreResearch()).thenReturn(2);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    NewsData news = NewsFactory.makeScientificVictoryNewsAtEnd(map);
    assertNotNull(news);
    assertEquals(true, news.getNewsText().contains("Empire of Homarian"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScientificVictoryAlliance() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    PlayerInfo winner = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(winner.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(winner.getEmpireName()).thenReturn("Empire of Homarian");
    Mockito.when(winner.getRace()).thenReturn(SpaceRace.HOMARIANS);
    PlayerInfo ally = Mockito.mock(PlayerInfo.class);
    Mockito.when(ally.getEmpireName()).thenReturn("Empire of Centaurs");
    Mockito.when(ally.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(ally);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Planet planet = Mockito.mock(Planet.class);
    Planet planet2 = Mockito.mock(Planet.class);
    ArrayList<Planet> planets = new ArrayList<>();
    planets.add(planet);
    planets.add(planet2);
    Mockito.when(planet.getPlanetType()).thenReturn(PlanetTypes.CARBONWORLD1);
    Mockito.when(planet2.getPlanetType()).thenReturn(PlanetTypes.ARTIFICIALWORLD1);
    Building[] buildings = new Building[2];
    buildings[0] = Mockito.mock(Building.class);
    buildings[1] = Mockito.mock(Building.class);
    Mockito.when(buildings[1].getScientificAchievement()).thenReturn(true);
    Mockito.when(planet.getBuildingList()).thenReturn(buildings);
    Mockito.when(planet2.getBuildingList()).thenReturn(buildings);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(winner);
    Mockito.when(map.getScoreResearch()).thenReturn(2);
    Mockito.when(map.getPlanetList()).thenReturn(planets);
    NewsData news = NewsFactory.makeScientificVictoryNewsAtEnd(map);
    assertNotNull(news);
    assertEquals(true, news.getNewsText().contains("Empire of Homarian"));
    assertEquals(true, news.getNewsText().contains("Empire of Centaurs"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDiplomaticVictory() {
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getScoreDiplomacy()).thenReturn(2);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    PlayerInfo winner = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(winner.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(winner.getEmpireName()).thenReturn("Empire of Homarian");
    Mockito.when(winner.getRace()).thenReturn(SpaceRace.HOMARIANS);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(-1);
    PlayerInfo second = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy2 = Mockito.mock(Diplomacy.class);
    Mockito.when(second.getDiplomacy()).thenReturn(diplomacy2);
    Mockito.when(second.getEmpireName()).thenReturn("Empire of Humans");
    Mockito.when(second.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(diplomacy2.getAllianceIndex()).thenReturn(-1);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(winner);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(second);
    
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    ArrayList<Vote> listVotes = new ArrayList<>();
    Mockito.when(votes.getVotes()).thenReturn(listVotes);
    Mockito.when(votes.getFirstCandidate()).thenReturn(2);
    Mockito.when(votes.getSecondCandidate()).thenReturn(3);
    Vote vote = Mockito.mock(Vote.class);
    listVotes.add(vote);
    Mockito.when(vote.getResult(2)).thenReturn(VotingChoice.VOTED_NO);
    Mockito.when(vote.getType()).thenReturn(VotingType.RULER_OF_GALAXY);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(55);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(32);
    NewsData news = NewsFactory.makeDiplomaticVictoryNewsAtEnd(map);
    assertNotNull(news);
    assertEquals(true, news.getNewsText().contains("Empire of Homarian"));
    assertEquals(true, news.getNewsText().contains("63 per cent"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDiplomaticVictoryAlliance() {
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getScoreDiplomacy()).thenReturn(2);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(4);
    PlayerInfo winner = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(winner.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(winner.getEmpireName()).thenReturn("Empire of Homarian");
    Mockito.when(winner.getRace()).thenReturn(SpaceRace.HOMARIANS);
    Mockito.when(diplomacy.getAllianceIndex()).thenReturn(1);
    PlayerInfo alliance = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy3 = Mockito.mock(Diplomacy.class);
    Mockito.when(alliance.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(alliance.getEmpireName()).thenReturn("Empire of Centaurs");
    Mockito.when(alliance.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(diplomacy3.getAllianceIndex()).thenReturn(2);
    PlayerInfo second = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy2 = Mockito.mock(Diplomacy.class);
    Mockito.when(second.getDiplomacy()).thenReturn(diplomacy2);
    Mockito.when(second.getEmpireName()).thenReturn("Empire of Humans");
    Mockito.when(second.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(diplomacy2.getAllianceIndex()).thenReturn(-1);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(alliance);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(winner);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(second);
    
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(map.getVotes()).thenReturn(votes);
    ArrayList<Vote> listVotes = new ArrayList<>();
    Mockito.when(votes.getVotes()).thenReturn(listVotes);
    Mockito.when(votes.getFirstCandidate()).thenReturn(2);
    Mockito.when(votes.getSecondCandidate()).thenReturn(3);
    Vote vote = Mockito.mock(Vote.class);
    listVotes.add(vote);
    Mockito.when(vote.getResult(2)).thenReturn(VotingChoice.VOTED_YES);
    Mockito.when(vote.getType()).thenReturn(VotingType.RULER_OF_GALAXY);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(22);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(42);
    NewsData news = NewsFactory.makeDiplomaticVictoryNewsAtEnd(map);
    assertNotNull(news);
    assertEquals(true, news.getNewsText().contains("Empire of Homarian"));
    assertEquals(true, news.getNewsText().contains("65 per cent"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingNews() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_NUCLEAR_WEAPONS);
    Mockito.when(vote.getTurnsToVote()).thenReturn(20);
    NewsData news = NewsFactory.makeVotingNews(vote, null, null);
    assertEquals(true, news.getNewsText().contains(VotingType.BAN_NUCLEAR_WEAPONS.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingEndedNews() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_NUCLEAR_WEAPONS);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(50);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(25);
    NewsData news = NewsFactory.makeVotingEndedNews(vote, VotingChoice.VOTED_NO,
        null, null);
    assertEquals(true, news.getNewsText().contains(
        VotingType.BAN_NUCLEAR_WEAPONS.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingEndedNewsBanPrivateer() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_PRIVATEER_SHIPS);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(50);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(25);
    NewsData news = NewsFactory.makeVotingEndedNews(vote, VotingChoice.VOTED_YES,
        null, null);
    assertEquals(true, news.getNewsText().contains(
        VotingType.BAN_PRIVATEER_SHIPS.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingEndedNewsPeace() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.GALACTIC_PEACE);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(50);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(25);
    NewsData news = NewsFactory.makeVotingEndedNews(vote, VotingChoice.VOTED_YES,
        null, null);
    assertEquals(true, news.getNewsText().contains(
        VotingType.GALACTIC_PEACE.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingEndedNewsTax() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.TAXATION_OF_RICHEST_REALM);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(50);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(25);
    NewsData news = NewsFactory.makeVotingEndedNews(vote, VotingChoice.VOTED_YES,
        null, null);
    assertEquals(true, news.getNewsText().contains(
        VotingType.TAXATION_OF_RICHEST_REALM.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingEndedNewsMilitary() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.SECOND_CANDIDATE_MILITARY);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(50);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(25);
    NewsData news = NewsFactory.makeVotingEndedNews(vote, VotingChoice.VOTED_YES,
        null, null);
    assertEquals(true, news.getNewsText().contains(
        VotingType.SECOND_CANDIDATE_MILITARY.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingEndedNewsRuler() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.RULER_OF_GALAXY);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_YES)).thenReturn(50);
    Mockito.when(vote.getVotingAmounts(VotingChoice.VOTED_NO)).thenReturn(25);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info1.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info1.getEmpireName()).thenReturn("Test of Sporks");
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(info2.getEmpireName()).thenReturn("Monarcy of Centaurs");
    NewsData news = NewsFactory.makeVotingEndedNews(vote, VotingChoice.VOTED_YES,
        info1, info2);
    assertEquals(true, news.getNewsText().contains(
        VotingType.RULER_OF_GALAXY.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingBanPrivateersNews() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_PRIVATEER_SHIPS);
    Mockito.when(vote.getTurnsToVote()).thenReturn(20);
    NewsData news = NewsFactory.makeVotingNews(vote, null, null);
    assertEquals(true, news.getNewsText().contains(VotingType.BAN_PRIVATEER_SHIPS.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingPeaceNews() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.GALACTIC_PEACE);
    Mockito.when(vote.getTurnsToVote()).thenReturn(20);
    NewsData news = NewsFactory.makeVotingNews(vote, null, null);
    assertEquals(true, news.getNewsText().contains(VotingType.GALACTIC_PEACE.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingTaxNews() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.TAXATION_OF_RICHEST_REALM);
    Mockito.when(vote.getTurnsToVote()).thenReturn(20);
    NewsData news = NewsFactory.makeVotingNews(vote, null, null);
    assertEquals(true, news.getNewsText().contains(VotingType.TAXATION_OF_RICHEST_REALM.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingSecondCanditdateNews() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.SECOND_CANDIDATE_MILITARY);
    Mockito.when(vote.getTurnsToVote()).thenReturn(20);
    NewsData news = NewsFactory.makeVotingNews(vote, null, null);
    assertEquals(true, news.getNewsText().contains(VotingType.SECOND_CANDIDATE_MILITARY.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingNews2() {
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.RULER_OF_GALAXY);
    Mockito.when(vote.getTurnsToVote()).thenReturn(20);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    NewsData news = NewsFactory.makeVotingNews(vote, info, info);
    assertEquals(true, news.getNewsText().contains(VotingType.RULER_OF_GALAXY.getDescription()));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticSecretaryNes() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getEmpireName()).thenReturn("Empire of Test");
    NewsData news = NewsFactory.makeSecretaryOfGalaxyNews(realm);
    assertEquals(true, news.getNewsText().contains("Empire of Test"));
  }

}
