package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.assertEquals;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipSize;

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
* Test for ShipHullListRenderer.
*/
public class ShipHullListRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipHullListRenderer() {
    JList<? extends ShipHull> list = new JList<>();
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Hull 1");
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;
    
    ShipHull value = hull;
    ShipHullListRenderer renderer = new ShipHullListRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Hull 1 - Medium",label.getText());
    assertEquals(GuiStatics.getCoolSpaceColorDark(), label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK, label.getBackground());

    Mockito.when(hull.getSize()).thenReturn(ShipSize.SMALL);
    selected = true;
    renderer = new ShipHullListRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Hull 1 - Small",label.getText());
    assertEquals(GuiStatics.getCoolSpaceColor(), label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE, label.getBackground());
  }

}
