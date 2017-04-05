package org.openRealmOfStars.utilities.repository;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.starMap.StarMap;
import static org.junit.Assert.*;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2016  Tuomo Untinen
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
* Test for GameRepository
*
*/
public class GameRepositoryTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLoadingGame() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    assertEquals("Terran Alliance",starMap.getPlayerByIndex(0).getEmpireName());
    assertEquals(128,starMap.getTurn());
    assertEquals(50,starMap.getMaxX());
    assertEquals(50,starMap.getMaxY());
    assertEquals("Alpha Libcochus I",starMap.getPlanetList().get(0).getName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLoadingGame2() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testStats.save");
    assertEquals("Terran Empire",starMap.getPlayerByIndex(0).getEmpireName());
    assertEquals(20,starMap.getTurn());
    assertEquals(75,starMap.getMaxX());
    assertEquals(75,starMap.getMaxY());
    int[][] data = starMap.getNewsCorpData().getCredit().getGalaxyData();
    assertEquals(2,data[0][1]);
    assertEquals(0,data[1][1]);
    data = starMap.getNewsCorpData().getMilitary().getGalaxyData();
    assertEquals(6,data[3][1]);
    assertEquals(10,data[4][0]);
    assertEquals(10,data[4][1]);
  }

}
