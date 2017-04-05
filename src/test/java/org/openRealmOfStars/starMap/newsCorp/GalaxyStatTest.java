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
* News Corporation Galaxy Stat Test
*
*/
public class GalaxyStatTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatValid() {
    GalaxyStat stat = new GalaxyStat(3, "Test stat");
    assertEquals("Test stat",stat.getGalaxyStatisticsName());
    assertEquals(3,stat.getMaxPlayers());
    stat.addStat(0, 1);
    stat.addStat(0, 2);
    stat.addStat(0, 3);
    stat.addStat(1, 2);
    stat.addStat(1, 4);
    stat.addStat(1, 8);
    stat.addStat(2, 9);
    stat.addStat(2, 3);
    stat.addStat(2, 0);
    int[][] data = stat.getGalaxyData();
    assertEquals(1,data[0][0]);
    assertEquals(2,data[0][1]);
    assertEquals(3,data[0][2]);
    assertEquals(2,data[1][0]);
    assertEquals(4,data[1][1]);
    assertEquals(8,data[1][2]);
    assertEquals(9,data[2][0]);
    assertEquals(3,data[2][1]);
    assertEquals(0,data[2][2]);
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyStatInValid() {
    GalaxyStat stat = new GalaxyStat(-1, "Test stat");
    // Should never reach here
    assertEquals("Test stat",stat.getGalaxyStatisticsName());
  }

}
