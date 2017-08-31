package org.openRealmOfStars.game;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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

}
