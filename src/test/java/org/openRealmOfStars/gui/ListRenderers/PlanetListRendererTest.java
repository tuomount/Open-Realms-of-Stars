package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.assertEquals;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.starMap.planet.Planet;

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
* Test for PlanetListRenderer.
*/
public class PlanetListRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetListRenderer() {
    JList<? extends Planet> list = new JList<>();
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Planet 1");
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getName()).thenReturn("Planet 2");
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;
    
    PlanetListRenderer renderer = new PlanetListRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, planet, index,
        selected, cellHasFocus);
    assertEquals("Planet 1",label.getText());
    assertEquals(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK, label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK, label.getBackground());
    
    selected = true;
    renderer = new PlanetListRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, planet2, index,
        selected, cellHasFocus);
    assertEquals("Planet 2",label.getText());
    assertEquals(GuiStatics.COLOR_COOL_SPACE_BLUE, label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE, label.getBackground());
  }

}
