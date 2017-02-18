package org.openRealmOfStars.gui.panels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017 Tuomo Untinen
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
 * Test for StatisticPanel class
 * 
 */
public class StatisticPanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanel() {
    StatisticPanel panel = new StatisticPanel();
    int[][] data = new int[3][5];
    data[0][0] = 1; 
    data[0][1] = 2; 
    data[0][2] = 3; 
    data[0][3] = 4; 
    data[0][4] = 5; 
    data[1][0] = 1; 
    data[1][1] = 1; 
    data[1][2] = 3; 
    data[1][3] = 3; 
    data[1][4] = 6; 
    data[2][0] = 1; 
    data[2][1] = 1; 
    data[2][2] = 2; 
    data[2][3] = 2; 
    data[2][4] = 3;
    panel.setData(data);
    assertEquals(6, panel.getLargestY());
    assertEquals(5, panel.getLargestX());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanelWithNullData() {
    StatisticPanel panel = new StatisticPanel();
    int[][] data = null;
    panel.setData(data);
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanelWithInvalidData() {
    StatisticPanel panel = new StatisticPanel();
    int[][] data = new int[3][];
    data[0] = new int[5];
    data[1] = new int[3];
    data[2] = new int[6];
    panel.setData(data);
  }


}
