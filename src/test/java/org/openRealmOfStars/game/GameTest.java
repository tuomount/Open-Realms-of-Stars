package org.openRealmOfStars.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.game.States.AITurnView;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.newsCorp.NewsData;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017, 2018 Tuomo Untinen
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
  public void testRunFullGameVeryShort() {
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(100);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame();
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameShort() {
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(200);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame();
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunFullGameMedium() {
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(4);
    config.setScoringVictoryTurns(400);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame();
    game.getPlayers().getPlayerInfoByIndex(0).setHuman(false);
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
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
      config.setScoringVictoryTurns(200);
      config.setSpacePiratesLevel(2);
      config.setChanceForPlanetaryEvent(40);
      config.setSize(128, 1);
      config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
//      System.out.println("Game number " + i);
      System.gc();
      game = null;
      game = new Game(false);
      game.setGalaxyConfig(config);
      game.makeNewGame();
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
//      System.out.print("Done, ");
      if (newsData.length > 0) {
        String victoryText = newsData[newsData.length - 1].getNewsText();
        for (int j = 0; j < 8; j++) {
          PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(j);
          if (victoryText.contains(info.getEmpireName())) {
            raceWins[info.getRace().getIndex()] = raceWins[info.getRace().getIndex()] + 1;
            govWins[info.getGovernment().getIndex()] = govWins[info.getGovernment().getIndex()] +1;
          }
        }
        //System.out.println(newsData[newsData.length - 1].getNewsText());
      } else {
        //System.out.println("not sure who win!");
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

}
