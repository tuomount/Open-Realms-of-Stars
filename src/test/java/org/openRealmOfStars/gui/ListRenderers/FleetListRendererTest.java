package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.assertEquals;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;

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
* Test for FleetListRenderer.
*/
public class FleetListRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFleetListRenderer() {
    JList<? extends Fleet> list = new JList<>();
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Ship 1");
    Fleet value = Mockito.mock(Fleet.class);
    Mockito.when(value.getNumberOfShip()).thenReturn(1);
    Mockito.when(value.getName()).thenReturn("Fleet 1");
    Mockito.when(value.getFirstShip()).thenReturn(ship);
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;
    
    FleetListRenderer renderer = new FleetListRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Fleet 1 - Ship 1",label.getText());
    assertEquals(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK, label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK, label.getBackground());
    
    Mockito.when(value.getNumberOfShip()).thenReturn(5);
    selected = true;
    renderer = new FleetListRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Fleet 1 - 5 ships",label.getText());
    assertEquals(GuiStatics.COLOR_COOL_SPACE_BLUE, label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE, label.getBackground());
  }

}
