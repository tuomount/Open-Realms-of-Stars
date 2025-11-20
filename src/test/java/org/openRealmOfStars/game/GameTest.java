package org.openRealmOfStars.game;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2024 Tuomo Untinen
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
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.game.state.AITurnView;
import org.openRealmOfStars.game.tutorial.TutorialList;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.PirateDifficultLevel;
import org.openRealmOfStars.starMap.event.karmaEvents.KarmaType;
import org.openRealmOfStars.starMap.history.HistoryTurn;
import org.openRealmOfStars.starMap.history.event.CombatEvent;
import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.planet.Planet;

/**
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

  private static String addEmptySpace(String s, int n) {
    return String.format("%-" + n + "s", s);  
}
  /**
   * Print end game results for containing debug information.
   * @param testGameName Test game name
   * @param game Actually played game
   */
  private static void printEndGameResults(final String testGameName,
      final Game game) {
    System.out.println("---- " + testGameName + " ----");
    int planets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int maxPlanets[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int charted[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int combats[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int conquest[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int maxProd[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int militaryPower[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int tradeShips[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    int spies[] = new int[game.getPlayers().getCurrentMaxPlayers()];
    for (Planet planet : game.getStarMap().getPlanetList()) {
      if (planet.getPlanetOwnerIndex() != -1) {
        planets[planet.getPlanetOwnerIndex()]++;
        int prod = 0;
        prod = prod + planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION);
        prod = prod + planet.getTotalProduction(Planet.PRODUCTION_METAL);
        prod = prod + planet.getTotalProduction(Planet.PRODUCTION_CREDITS);
        prod = prod + planet.getTotalProduction(Planet.PRODUCTION_RESEARCH);
        if (prod > maxProd[planet.getPlanetOwnerIndex()]) {
          maxProd[planet.getPlanetOwnerIndex()] = prod;
        }
      }
      for (int j = 0; j < game.getPlayers().getCurrentMaxPlayers(); j++) {
        if (planet.isColonizeablePlanet(game.getPlayers()
            .getPlayerInfoByIndex(j))
            && planet.getPlanetOwnerIndex() == -1) {
          maxPlanets[j]++;
        }
      }
    }
    int maxSectors = game.getStarMap().getMaxX() * game.getStarMap().getMaxY();
    for (int j = 0; j < game.getPlayers().getCurrentMaxPlayers(); j++) {
      int charting = 0;
      for (int y = 0; y < game.getStarMap().getMaxY(); y++) {
        for (int x = 0; x < game.getStarMap().getMaxX(); x++) {
          if (game.getPlayers().getPlayerInfoByIndex(j).getSectorVisibility(
              new Coordinate(x, y)) > PlayerInfo.UNCHARTED) {
            charting++;
          }
        }
      }
      charted[j] = charting * 100 / maxSectors;
      PlayerInfo info = game.getPlayers().getPlayerInfoByIndex(j);
      for (int k = 0; k < info.getFleets().getNumberOfFleets(); k++) {
        Fleet fleet = info.getFleets().getByIndex(k);
        militaryPower[j] = militaryPower[j] + fleet.getMilitaryValue();
        if (fleet.isTradeFleet()) {
          tradeShips[j]++;
        }
        if (fleet.isSpyFleet()) {
          spies[j]++;
        }
      }
    }
    for (int i = 0; i < game.getStarMap().getHistory().numberOfTurns(); i++) {
      HistoryTurn turn = game.getStarMap().getHistory().getByIndex(i);
      for (int j = 0; j < turn.getNumberOfEvents(); j++) {
        Event event = turn.getEvent(j);
        if (event.getType() == EventType.SPACE_COMBAT) {
          for (int k = 0; k < game.getPlayers().getCurrentMaxPlayers(); k++) {
            CombatEvent combatEvent = (CombatEvent) event;
            if (combatEvent.getText().contains(
                game.getPlayers().getPlayerInfoByIndex(k).getEmpireName())) {
              combats[k]++;
            }
          }
        }
        if (event.getType() == EventType.PLANET_CONQUERED) {
          EventOnPlanet eventOnPlanet = (EventOnPlanet) event;
          //System.out.println("Index: " + eventOnPlanet.getPlayerIndex());
          //System.out.println("Text: " + eventOnPlanet.getText());
          if (eventOnPlanet.getPlayerIndex() != -1) {
            conquest[eventOnPlanet.getPlayerIndex()]++;
          }
        }
      }
    }
    for (int i = 0; i < game.getPlayers().getCurrentMaxPlayers(); i++) {
      int tech = 0;
      for (TechType techtype : TechType.values()) {
        tech = tech + game.getPlayers().getPlayerInfoByIndex(i)
            .getTechList().getTechLevel(techtype);
      }
      tech = tech * 10 / 6;
      String scenario = game.getPlayers().getPlayerInfoByIndex(i)
          .getStartingScenario().getName();
      StringBuilder sb = new StringBuilder();
      sb.append(i);
      sb.append(": ");
      sb.append(game.getPlayers().getPlayerInfoByIndex(i).getEmpireName());
      sb.append(" (");
      sb.append(game.getPlayers().getPlayerInfoByIndex(i).getAiDifficulty()
          .toString());
      sb.append(")\n");
      sb.append(addEmptySpace(" planets " + planets[i] + "/" + maxPlanets[i],
          16));
      sb.append(addEmptySpace("Charted: " + charted[i] + "%", 14));
      sb.append(addEmptySpace("Military: " + militaryPower[i], 22));
      sb.append(addEmptySpace("Combats: " + combats[i], 15));
      sb.append(addEmptySpace("Conquest: " + conquest[i], 15));
      sb.append(addEmptySpace("Tech: " + tech, 10));
      sb.append(addEmptySpace("Max Prod: " + maxProd[i], 15));
      sb.append(addEmptySpace("Trade: " + tradeShips[i], 15));
      sb.append(addEmptySpace("Spies: " + spies[i], 15));
      sb.append("\n");
      sb.append("Scenario: ");
      sb.append(scenario);
      if (game.getPlayers().getPlayerInfoByIndex(i).isElderRealm()) {
        sb.append(" - Elder");
      }
      System.out.println(sb.toString());
    }
    NewsData[] newsData = game.getStarMap().getNewsCorpData().getNewsList();
    System.out.print("Done, turn " + game.getStarMap().getTurn()+ ": ");
    if (newsData != null && newsData.length > 0) {
      System.out.println(newsData[newsData.length - 1].getNewsText());
    }
    System.out.println("---- End of " + testGameName + " ----");

  }
  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameLong() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(800);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setAiOnly(true);
    config.setPlayerDifficult(0, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(1, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(2, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(3, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(4, AiDifficulty.NORMAL);
    config.setPlayerDifficult(5, AiDifficulty.NORMAL);
    config.setPlayerDifficult(6, AiDifficulty.WEAK);
    config.setPlayerDifficult(7, AiDifficulty.WEAK);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    int turn = 0;
    int maxTurns = 800;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("Mixed AI, 800 turns", game);
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameShort() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(200);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setPlayerDifficult(0, AiDifficulty.NORMAL);
    config.setPlayerDifficult(1, AiDifficulty.NORMAL);
    config.setPlayerDifficult(2, AiDifficulty.NORMAL);
    config.setPlayerDifficult(3, AiDifficulty.NORMAL);
    config.setPlayerDifficult(4, AiDifficulty.NORMAL);
    config.setPlayerDifficult(5, AiDifficulty.NORMAL);
    config.setPlayerDifficult(6, AiDifficulty.NORMAL);
    config.setPlayerDifficult(7, AiDifficulty.NORMAL);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    int turn = 0;
    int maxTurns = 200;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("All normal AI, short 200 turns", game);
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameMedium() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(4);
    config.setScoringVictoryTurns(400);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setPlayerDifficult(0, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(1, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(2, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(3, AiDifficulty.CHALLENGING);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    int turn = 0;
    int maxTurns = 400;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("All challening AI, medium 400 turns", game);
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameMediumWith8Realms() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setRace(0,  SpaceRaceFactory.createOne("MYCOLIANS"));
    config.setPlayerGovernment(0, GovernmentFactory.createOne("HIVEMIND"));
    config.setPlayerName(0, "Hive-mind of Mycolians");
    config.setPlayerDifficult(0, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(1, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(2, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(3, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(4, AiDifficulty.NORMAL);
    config.setPlayerDifficult(5, AiDifficulty.NORMAL);
    config.setPlayerDifficult(6, AiDifficulty.WEAK);
    config.setPlayerDifficult(7, AiDifficulty.WEAK);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    int turn = 0;
    int maxTurns = 400;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("Full game with 8 realms, medium 400 turns", game);
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameMediumWith8RealmsAndDifficulty() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setSpacePiratesDifficulty(PirateDifficultLevel.HARD);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setAiDifficulty(
        AiDifficulty.CHALLENGING);
    game.getPlayers().getPlayerInfoByIndex(1).setAiDifficulty(
        AiDifficulty.CHALLENGING);
    game.getPlayers().getPlayerInfoByIndex(2).setAiDifficulty(
        AiDifficulty.NORMAL);
    game.getPlayers().getPlayerInfoByIndex(3).setAiDifficulty(
        AiDifficulty.NORMAL);
    game.getPlayers().getPlayerInfoByIndex(4).setAiDifficulty(
        AiDifficulty.WEAK);
    game.getPlayers().getPlayerInfoByIndex(5).setAiDifficulty(
        AiDifficulty.WEAK);
    game.getPlayers().getPlayerInfoByIndex(6).setAiDifficulty(
        AiDifficulty.WEAK);
    game.getPlayers().getPlayerInfoByIndex(7).setAiDifficulty(
        AiDifficulty.WEAK);
    int turn = 0;
    int maxTurns = 400;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("Full game with 8 realms + pirate, medium 400 turns",
        game);
  }

/*  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunPartialAndSaveItGameMediumWith8RealmsAndDifficulty() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setSpacePiratesDifficulty(PirateDifficultLevel.HARD);
    config.setPlayerElderRealm(0, true);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setAiDifficulty(
        AiDifficulty.CHALLENGING);
    game.getPlayers().getPlayerInfoByIndex(1).setAiDifficulty(
        AiDifficulty.CHALLENGING);
    game.getPlayers().getPlayerInfoByIndex(2).setAiDifficulty(
        AiDifficulty.NORMAL);
    game.getPlayers().getPlayerInfoByIndex(3).setAiDifficulty(
        AiDifficulty.NORMAL);
    game.getPlayers().getPlayerInfoByIndex(4).setAiDifficulty(
        AiDifficulty.WEAK);
    game.getPlayers().getPlayerInfoByIndex(5).setAiDifficulty(
        AiDifficulty.WEAK);
    game.getPlayers().getPlayerInfoByIndex(6).setAiDifficulty(
        AiDifficulty.WEAK);
    game.getPlayers().getPlayerInfoByIndex(7).setAiDifficulty(
        AiDifficulty.WEAK);
    int turn = 0;
    int maxTurns = 400;
    int saveCount = 1;
    int maxCount = 15;
    boolean keepRunning = true;
    do {
      do {
        game.setAITurnView(new AITurnView(game));
        boolean singleTurnEnd = false;
        do {
         singleTurnEnd = game.getAITurnView().handleAiTurn();
        } while (!singleTurnEnd);
        if (turn != game.getStarMap().getTurn()) {
          turn = game.getStarMap().getTurn();
          if (turn % 50 == 0) {
            System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
          }
        }
        assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
      } while (turn <= saveCount * 20 && !game.getStarMap().isGameEnded());
      if (saveCount == maxCount || game.getStarMap().isGameEnded()) {
        keepRunning = false;
        game.getStarMap().getPlayerByIndex(0).setHuman(true);
        printEndGameResults("Not finished game", game);
      }
      try {
        Game.readTutorial(null);
        new GameRepository().saveGame(Folders.getSavegamePath(),
            "ai-played" + saveCount +".save", game.getStarMap());
        saveCount++;
      } catch (IOException e) {
        ErrorLogger.log(e);
      }
    } while (keepRunning);
  }*/

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameMediumWith3RealmsAndDifficulty() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(3);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(400);
    config.setAiOnly(true);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setRace(0, SpaceRaceFactory.createOne("HUMANS"));
    config.setPlayerName(0, "Challenging Terran");
    config.setPlayerGovernment(0, GovernmentFactory
        .createOne("FEDERATION"));
    config.setRace(1, SpaceRaceFactory.createOne("HUMANS"));
    config.setPlayerGovernment(1, GovernmentFactory
        .createOne("UNION"));
    config.setPlayerName(1, "Normal Human");
    config.setRace(2, SpaceRaceFactory.createOne("HUMANS"));
    config.setPlayerName(2, "Weak Human");
    config.setPlayerGovernment(2, GovernmentFactory
        .createOne("DEMOCRACY"));
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    game.getPlayers().getPlayerInfoByIndex(0).setAiDifficulty(
        AiDifficulty.CHALLENGING);
    game.getPlayers().getPlayerInfoByIndex(1).setAiDifficulty(
        AiDifficulty.NORMAL);
    game.getPlayers().getPlayerInfoByIndex(2).setAiDifficulty(
        AiDifficulty.WEAK);
    int turn = 0;
    int maxTurns = 400;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("Three terrans, medium 400 turns", game);
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameLongWith12Realms() {
    System.gc();
    Game game = new Game(false);
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(12);
    config.setSize(128, 2);
    config.setScoringVictoryTurns(600);
    config.setAiOnly(true);
    config.setSolarSystemDistance(7, 2);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    config.setSpacePiratesDifficulty(PirateDifficultLevel.EASY);
    config.setSpacePiratesLevel(2);
    config.setPlayerDifficult(0, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(1, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(2, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(3, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(4, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(5, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(6, AiDifficulty.NORMAL);
    config.setPlayerDifficult(7, AiDifficulty.NORMAL);
    config.setPlayerDifficult(8, AiDifficulty.NORMAL);
    config.setPlayerDifficult(9, AiDifficulty.NORMAL);
    config.setPlayerDifficult(10, AiDifficulty.WEAK);
    config.setPlayerDifficult(11, AiDifficulty.WEAK);
    game.setGalaxyConfig(config);
    game.setPlayerInfo();
    game.makeNewGame(false);
    int turn = 0;
    int maxTurns = 600;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
       singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("12 Realms, 600 turns", game);
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGames() {
    Game game;
    for (int i = 0; i < 2; i++) {
      GalaxyConfig config = new GalaxyConfig();
      config.setMaxPlayers(8);
      config.setScoringVictoryTurns(400);
      config.setAiOnly(true);
      config.setSpacePiratesLevel(2);
      config.setChanceForPlanetaryEvent(40);
      config.setKarmaType(KarmaType.SECOND_FIRST_AND_LAST);
      config.setKarmaSpeed(2);
      config.setSize(75, 1);
      config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
      game = null;
      System.gc();
      game = new Game(false);
      game.setGalaxyConfig(config);
      game.makeNewGame(false);
      int turn = 0;
      int maxTurns = 400;
      do {
        game.setAITurnView(new AITurnView(game));
        boolean singleTurnEnd = false;
        do {
          singleTurnEnd = game.getAITurnView().handleAiTurn();
        } while (!singleTurnEnd);
        if (turn != game.getStarMap().getTurn()) {
          turn = game.getStarMap().getTurn();
          if (turn % 50 == 0) {
            System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
          }
        }
        assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
      } while (!game.getStarMap().isGameEnded());
      String name = "Full game(" + i + "), 8 realms, 400 turns";
      printEndGameResults(name, game);
    }
  }

  @Test
  @Category(org.openRealmOfStars.FullGameAcceptanceTest.class)
  public void testRunFullGameWithElder() {
    Game game;
    GalaxyConfig config = new GalaxyConfig();
    config.setMaxPlayers(8);
    config.setScoringVictoryTurns(400);
    config.setAiOnly(true);
    config.setSpacePiratesLevel(1);
    config.setChanceForPlanetaryEvent(40);
    config.setKarmaType(KarmaType.SECOND_FIRST_AND_LAST);
    config.setKarmaSpeed(2);
    config.setPlayerDifficult(0, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(1, AiDifficulty.WEAK);
    config.setPlayerDifficult(2, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(3, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(4, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(5, AiDifficulty.CHALLENGING);
    config.setPlayerDifficult(6, AiDifficulty.NORMAL);
    config.setPlayerDifficult(7, AiDifficulty.NORMAL);
    config.setElderHeadStart(40);
    config.setPlayerElderRealm(1, true);
    config.setPlayerElderRealm(2, true);
    config.setSize(75, 1);
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    game = null;
    System.gc();
    // Tutorial is required for saving the game
    //Game.readTutorial(null);
    game = new Game(false);

    game.setGalaxyConfig(config);
    game.makeNewGame(false);
    int turn = 0;
    int maxTurns = 400;
    do {
      game.setAITurnView(new AITurnView(game));
      boolean singleTurnEnd = false;
      do {
        singleTurnEnd = game.getAITurnView().handleAiTurn();
      } while (!singleTurnEnd);
      if (turn != game.getStarMap().getTurn()) {
        turn = game.getStarMap().getTurn();
        if (turn % 50 == 0) {
          System.err.println("Game progressing, turn: " + turn + "/" + maxTurns);
        }
      }
      assertFalse(game.getStarMap().getTurn() > config.getScoringVictoryTurns());
    } while (!game.getStarMap().isGameEnded());
    printEndGameResults("8 Realms, two elders, medium 400 turns", game);
  }

}
