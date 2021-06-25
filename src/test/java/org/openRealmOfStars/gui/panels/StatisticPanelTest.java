package org.openRealmOfStars.gui.panels;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.gui.utilies.GuiStatics;

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
    Color[] colors = new Color[3];
    colors[0] = GuiStatics.PLAYER_BANANA;
    colors[1] = GuiStatics.PLAYER_BLUE;
    colors[2] = GuiStatics.PLAYER_CHESTNUT;
    StatisticPanel panel = new StatisticPanel(colors);
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
    assertEquals(4, panel.getLargestX());
    assertEquals(10,panel.getTurnDistance());
    panel.setTurnDistance(25);
    assertEquals(25,panel.getTurnDistance());
    panel.setTurnDistance(0);
    assertEquals(25,panel.getTurnDistance());
    panel.setTurnDistance(-1);
    assertEquals(25,panel.getTurnDistance());
    String[] names = new String[3];
    names[0] = "FooBar";
    names[1] = "Human Empire";
    names[2] = "Test";
    panel.setYDataNames(names);
    int width = panel.getWidestDataName();
    if (width < 80 || width > 90) {
      // Seems that font width might vary on different systems
      assertFalse("Width not between 80 and 90!", true);
    }
    names = new String[3];
    names[0] = "Bar";
    names[1] = "Bazbaz";
    names[2] = "Moi";
    panel.setYDataNames(names);
    width = panel.getWidestDataName();
    if (width < 45 || width > 55) {
      // Seems that font width might vary on different systems
      assertFalse("Width not between 45 and 55!", true);
    }
    panel.setYDataNames(null);
    assertEquals(0, panel.getWidestDataName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanelWithZeros() {
    Color[] colors = new Color[3];
    colors[0] = GuiStatics.PLAYER_BANANA;
    colors[1] = GuiStatics.PLAYER_BLUE;
    colors[2] = GuiStatics.PLAYER_CHESTNUT;
    StatisticPanel panel = new StatisticPanel(colors);
    int[][] data = new int[3][5];
    data[0][0] = 0; 
    data[0][1] = 0; 
    data[0][2] = 0; 
    data[0][3] = 0; 
    data[0][4] = 0; 
    data[1][0] = 0; 
    data[1][1] = 0; 
    data[1][2] = 0; 
    data[1][3] = 0; 
    data[1][4] = 0; 
    data[2][0] = 0; 
    data[2][1] = 0; 
    data[2][2] = 0; 
    data[2][3] = 0; 
    data[2][4] = 0;
    panel.setData(data);
    assertEquals(1, panel.getLargestY());
    assertEquals(4, panel.getLargestX());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanelWithNullData() {
    Color[] colors = new Color[3];
    colors[0] = GuiStatics.PLAYER_BANANA;
    colors[1] = GuiStatics.PLAYER_BLUE;
    colors[2] = GuiStatics.PLAYER_CHESTNUT;
    StatisticPanel panel = new StatisticPanel(colors);
    int[][] data = null;
    panel.setData(data);
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanelWithInvalidData() {
    Color[] colors = new Color[3];
    colors[0] = GuiStatics.PLAYER_BANANA;
    colors[1] = GuiStatics.PLAYER_BLUE;
    colors[2] = GuiStatics.PLAYER_CHESTNUT;
    StatisticPanel panel = new StatisticPanel(colors);
    int[][] data = new int[3][];
    data[0] = new int[5];
    data[1] = new int[3];
    data[2] = new int[6];
    panel.setData(data);
  }


}
