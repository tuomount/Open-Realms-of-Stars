package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
* News Corporation Data Test
*
*/
public class NewsCorpDataTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatValid() {
    NewsCorpData data = new NewsCorpData(8);
    if (data.getCredit() == null) {
      fail("Credit is null!");
    }
    if (data.getCultural() == null) {
      fail("Cultural is null!");
    }
    if (data.getMilitary() == null) {
      fail("Military is null!");
    }
    if (data.getResearch() == null) {
      fail("Research is null!");
    }
    if (data.getPlanets() == null) {
      fail("Planets are null!");
    }
    if (data.getPopulation() == null) {
      fail("Population is null!");
    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatInValid() {
    NewsCorpData data = new NewsCorpData(-1);
    // Should not never reach here!
    data.getCredit();
  }

}
