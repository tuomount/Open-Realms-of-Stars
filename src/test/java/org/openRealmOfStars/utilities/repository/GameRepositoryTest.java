package org.openRealmOfStars.utilities.repository;

import org.junit.Test;
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
  public void testLoadingGame() {
    GameRepository repository = new GameRepository();
    repository.enableJUnit();
    StarMap starMap = repository.loadGame("testGame.save");
    assertEquals("Terran Alliance",starMap.getPlayerByIndex(0).getEmpireName());
    assertEquals(128,starMap.getTurn());
    assertEquals(50,starMap.getMaxX());
    assertEquals(50,starMap.getMaxY());
    assertEquals("Alpha Libcochus I",starMap.getPlanetList().get(0).getName());
  }

}
