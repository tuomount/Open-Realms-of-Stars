package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2018,2019 Tuomo Untinen
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
 * Test for GalaxyConfig
 *
 */
public class GalaxyConfigTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyConfig() {
    GalaxyConfig config = new GalaxyConfig();
    assertEquals(1, config.getSpacePiratesLevel());
    config.setSpacePiratesLevel(4);
    assertEquals(4, config.getSpacePiratesLevel());
    assertEquals(10, config.getChanceForPlanetaryEvent());
    config.setChanceForPlanetaryEvent(-5);
    assertEquals(0, config.getChanceForPlanetaryEvent());
    config.setChanceForPlanetaryEvent(120);
    assertEquals(99, config.getChanceForPlanetaryEvent());
    config.setChanceForPlanetaryEvent(20);
    assertEquals(20, config.getChanceForPlanetaryEvent());
    assertEquals(75, config.getSizeX());
    assertEquals(75, config.getSizeY());
    assertEquals(6, config.getMaxPlayers());
    assertEquals(12, config.getSolarSystemDistance());
    assertEquals(300, config.getScoringVictoryTurns());
    config.setScoringVictoryTurns(0);
    assertEquals(200, config.getScoringVictoryTurns());
    config.setScoringVictoryTurns(2000);
    assertEquals(1000, config.getScoringVictoryTurns());
    config.setScoringVictoryTurns(800);
    assertEquals(800, config.getScoringVictoryTurns());
    config.setRace(0, SpaceRace.HUMAN);
    assertEquals(SpaceRace.HUMAN, config.getRace(0));
    assertEquals(true,config.isUniqueName("Test of Human"));
    config.setPlayerName(0, "Test of Human");
    assertEquals("Test of Human",config.getPlayerName(0));
    assertEquals(false,config.isUniqueName("Test of Human"));
    assertEquals(null,config.getPlayerName(9));
    config.setMaxPlayers(3);
    assertEquals(3, config.getMaxPlayers());
    config.setSolarSystemDistance(13, 0);
    assertEquals(13, config.getSolarSystemDistance());
    assertEquals(0, config.getSunDensityIndex());
    config.setSize(50, 0);
    assertEquals(50, config.getSizeX());
    assertEquals(50, config.getSizeY());
    assertEquals(0, config.getGalaxySizeIndex());
    assertEquals(GalaxyConfig.ROGUE_PLANETS_FEW,
        config.getNumberOfRoguePlanets());
    config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_NONE);
    assertEquals(GalaxyConfig.ROGUE_PLANETS_NONE,
        config.getNumberOfRoguePlanets());
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    assertEquals(GalaxyConfig.START_POSITION_RANDOM,
        config.getStartingPosition());
    assertEquals(1, config.getScoreLimitCulture());
    config.setScoreLimitCulture(-1);
    assertEquals(-1, config.getScoreLimitCulture());
    assertEquals(1, config.getScoreLimitConquer());
    assertEquals(2, config.getScoreLimitDiplomacy());
    assertEquals(2, config.getScoreLimitResearch());
    config.setScoreLimitConquer(0);
    assertEquals(0, config.getScoreLimitConquer());
    config.setScoreLimitDiplomacy(1);
    config.setScoreLimitResearch(1);
    assertEquals(1, config.getScoreLimitDiplomacy());
    assertEquals(1, config.getScoreLimitResearch());
    assertEquals(PirateDifficultLevel.EASY, config.getSpacePiratesDifficulty());
    config.setSpacePiratesDifficulty(PirateDifficultLevel.VERY_HARD);
    assertEquals(PirateDifficultLevel.VERY_HARD, config.getSpacePiratesDifficulty());
    assertEquals(60, config.getAncientHeadStart());
    config.setAncientHeadStart(40);
    assertEquals(40, config.getAncientHeadStart());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateDifficultyLevels() {
    assertEquals("Easy", PirateDifficultLevel.EASY.toString());
    assertEquals("Very easy", PirateDifficultLevel.VERY_EASY.toString());
    assertEquals("Normal", PirateDifficultLevel.NORMAL.toString());
    assertEquals("Hard", PirateDifficultLevel.HARD.toString());
    assertEquals("Very hard", PirateDifficultLevel.VERY_HARD.toString());
    assertEquals(PirateDifficultLevel.VERY_EASY, PirateDifficultLevel.getLevelByInt(0));
    assertEquals(PirateDifficultLevel.EASY, PirateDifficultLevel.getLevelByInt(1));
    assertEquals(PirateDifficultLevel.NORMAL, PirateDifficultLevel.getLevelByInt(2));
    assertEquals(PirateDifficultLevel.HARD, PirateDifficultLevel.getLevelByInt(3));
    assertEquals(PirateDifficultLevel.VERY_HARD, PirateDifficultLevel.getLevelByInt(4));
  }

}
