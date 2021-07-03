package org.openRealmOfStars.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.game.States.AITurnView;
import org.openRealmOfStars.game.tutorial.TutorialList;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.KarmaType;
import org.openRealmOfStars.starMap.PirateDifficultLevel;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017-2019,2021 Tuomo Untinen
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
* Test for Game class, mainly to test static methods
*
*/
public class GameTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResearchWiki() {
    // One might think this is bad test, but this
    // actual make sure that each tech has at least
    // one component, hull or improvement.
    String wikiPage = Game.printTechWiki();
    assertEquals(true, wikiPage.contains("Combat"));
    assertEquals(true, wikiPage.contains("Defense"));
    assertEquals(true, wikiPage.contains("Hulls"));
    assertEquals(true, wikiPage.contains("Improvement"));
    assertEquals(true, wikiPage.contains("Propulsion"));
    assertEquals(true, wikiPage.contains("Electronics"));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTutorialRead() throws IOException {
    Game.readTutorial("src/test/resources/tutorial.txt");
    TutorialList list = Game.getTutorial();
    assertEquals("Text", list.get(0).getText());
    assertEquals("Test Text", list.get(1).getText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameVeryShort() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(100);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    System.out.println(newsData[newsData.length - 1].getNewsText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameShort() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(200);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    System.out.println(newsData[newsData.length - 1].getNewsText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameMedium() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(4);
    config.setScoringVictoryTurns(400);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    System.out.println(newsData[newsData.length - 1].getNewsText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameMediumWith8Realms() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    int planets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int maxPlanets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    for (Planet planet : game.getStarMap().getPlanetList()) {
      if (planet.getPlanetOwnerIndex() != -1) {
        planets[planet.getPlanetOwnerIndex()]++;
      }
      for (int j = 0; j < game.getPlayers().getCurrentMaxPlayers(); j++) {
        if (planet.getRadiationLevel() <= game.getPlayers()
            .getPlayerInfoByIndex(j).getRace().getMaxRad()
            && planet.getPlanetOwnerIndex() == -1) {
          maxPlanets[j]++;
        }
      }
    }
    for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
      System.out.println(i + ": "
          + game.getPlayers().getPlayerInfoByIndex(i).getEmpireName()
          + " - planets " + planets[i] + "/" + maxPlanets[i]);
    }
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    System.out.println(newsData[newsData.length - 1].getNewsText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameMediumWith12Realms() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(12);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setSolarSystemDistance(7, 2);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setSpacePiratesDifficulty(PirateDifficultLevel.EASY);
    config.setSpacePiratesLevel(2);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    int planets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int maxPlanets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    for (Planet planet : game.getStarMap().getPlanetList()) {
      if (planet.getPlanetOwnerIndex() != -1) {
        planets[planet.getPlanetOwnerIndex()]++;
      }
      for (int j = 0; j < game.getPlayers().getCurrentMaxPlayers(); j++) {
        if (planet.getRadiationLevel() <= game.getPlayers()
            .getPlayerInfoByIndex(j).getRace().getMaxRad()
            && planet.getPlanetOwnerIndex() == -1) {
          maxPlanets[j]++;
        }
      }
    }
    for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
      System.out.println(i + ": "
          + game.getPlayers().getPlayerInfoByIndex(i).getEmpireName()
          + " - planets " + planets[i] + "/" + maxPlanets[i]);
    }
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    System.out.println(newsData[newsData.length - 1].getNewsText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameMediumWith8RealmsOneLithorian() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setRace(0, SpaceRace.LITHORIANS);
    config.setPlayerGovernment(0, GovernmentType.EMPIRE);
    config.setPlayerName(0, "Empire of Lithorian");
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    int planets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int maxPlanets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    for (Planet planet : game.getStarMap().getPlanetList()) {
      if (planet.getPlanetOwnerIndex() != -1) {
        planets[planet.getPlanetOwnerIndex()]++;
      }
      for (int j = 0; j < game.getPlayers().getCurrentMaxPlayers(); j++) {
        if (planet.getRadiationLevel() <= game.getPlayers()
            .getPlayerInfoByIndex(j).getRace().getMaxRad()
            && planet.getPlanetOwnerIndex() == -1) {
          maxPlanets[j]++;
        }
      }
    }
    for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
      System.out.println(i + ": "
          + game.getPlayers().getPlayerInfoByIndex(i).getEmpireName()
          + " - planets " + planets[i] + "/" + maxPlanets[i]);
    }
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    System.out.println(newsData[newsData.length - 1].getNewsText());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGames() {
    Game game;
    int[] raceWins = new int[SpaceRace.values().length];
    int[] govWins = new int[GovernmentType.values().length];
    for (int i = 0; i < 2; i++) {
      GalaxyConfig config = new GalaxyConfig();
      config.setMaxPlayers(8);
      config.setScoringVictoryTurns(400);
      config.setSpacePiratesLevel(2);
      config.setChanceForPlanetaryEvent(40);
      config.setKarmaType(KarmaType.SECOND_FIRST_AND_LAST);
      config.setKarmaSpeed(2);
      config.setSize(75, 1);
      config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
//      System.out.println("Game number " + i);
      game = null;
      System.gc();
      game = new Game(false);
      game.setGalaxyConfig(config);
      game.makeNewGame(false);
      game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
      do {
        game.setAITurnView(new AITurnView(game));
        boolean singleTurnEnd = false;
        do {
          singleTurnEnd = game.getAITurnView().handleAiTurn();
        } while (!singleTurnEnd);
        assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
      } while (!game.getStarMap().isGameEnded());
      NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
      System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
      if (newsData.length > 0) {
        String victoryText = newsData[newsData.length - 1].getNewsText();
        for (int j = 0; j < 8; j++) {
          PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(j);
          if (victoryText.contains(info.getEmpireName())) {
            raceWins[info.getRace().getIndex()] = raceWins[info.getRace().getIndex()] + 1;
            govWins[info.getGovernment().getIndex()] = govWins[info.getGovernment().getIndex()] +1;
          }
        }
        System.out.println(newsData[newsData.length - 1].getNewsText());
      } else {
//        System.out.println("not sure who win!");
      }
    }
/*    System.out.println("Wins for races:");
    for (int i = 0; i < raceWins.length; i++) {
      System.out.println(SpaceRace.values()[i].getName() + ": " +raceWins[i]);
    }
    System.out.println("---");
    System.out.println("Wins for governments:");
    for (int i = 0; i < govWins.length; i++) {
      System.out.println(GovernmentType.values()[i].getName() + ": " +govWins[i]);
    }*/
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameWithAncient() throws IOException {
    Game game;
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(400);
    config.setSpacePiratesLevel(1);
    config.setChanceForPlanetaryEvent(40);
    config.setKarmaType(KarmaType.SECOND_FIRST_AND_LAST);
    config.setKarmaSpeed(2);
    config.setAncientHeadStart(40);
    config.setPlayerAncientRealm(1, true);
    config.setPlayerAncientRealm(2, true);
    config.setSize(75, 1);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game = null;
    System.gc();
    // Tutorial is required for saving the game
    //Game.readTutorial(null);
    game = new Game(false);
    
    game.setGalaxyConfig(config);
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    // Uncomment below if game is wanted to be saved.
    /*new GameRepository().saveGame(GameRepository.DEFAULT_SAVE_FOLDER,
        "testgame.save", game.getStarMap());*/
    System.out.println("Game ended at turn: " + game.getStarMap().getTurn());
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    if (newsData.length > 0) {
      System.out.println(newsData[newsData.length - 1].getNewsText());
      for (int i = 0; i < game.getStarMap().getPlayerList().getCurrentMaxRealms(); i++) {
        PlayerInfo info = game.getStarMap().getPlayerByIndex(i);
        System.out.println(i + ": " + info.getEmpireName() + " - " + info.isAncientRealm());
      }
    } else {
      System.out.println("not sure who win!");
    }
/*    for (int i = 0; i < game.getPlayers().getCurrentMaxRealms(); i++) {
      StringBuilder sb = new StringBuilder();
      PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(i);
      sb.append(info.getEmpireName());
      sb.append(": ");
      for (int j = 0; j < game.getPlayers().getCurrentMaxRealms(); j++) {
        if (i != j) {
          sb.append(j +": ");
          sb.append(info.getDiplomacy().getDiplomaticRelation(j));
          sb.append(", ");
        }
      }
      System.out.println(sb.toString());
    }*/
  }

}
