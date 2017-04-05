package org.openRealmOfStars.game;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
* Test for SavedGame class
*
*/
public class SavedGameTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLoadingSavedGame() throws IOException {
    SavedGame game = new SavedGame("src/test/resources/saves", 
                                   "testGame.save");
    assertEquals(128, game.getTurnNumber());
    assertEquals("Terran Alliance", game.getEmpireName());
    assertEquals("50 X 50", game.getGalaxySize());
    assertEquals(SpaceRace.HUMAN, game.getPlayerRace());
    assertEquals("testGame.save", game.getFilename());
  }

}
