package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;

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
* Test for ShipComponentListRenderer.
*/
public class ShipComponentListRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    JList<? extends ShipComponent> list = new JList<>();
    ShipComponent value = Mockito.mock(ShipComponent.class);
    Mockito.when(value.getName()).thenReturn("Component");
    Mockito.when(value.getType()).thenReturn(ShipComponentType.ENGINE);
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;

    ShipComponentListRenderer renderer = new ShipComponentListRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Component - Engine",label.getText());
    assertEquals(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK, label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK, label.getBackground());
    
    value = null;
    selected = true;
    renderer = new ShipComponentListRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("None",label.getText());
    assertEquals(GuiStatics.COLOR_COOL_SPACE_BLUE, label.getForeground());
    assertEquals(GuiStatics.COLOR_DEEP_SPACE_PURPLE, label.getBackground());
  }

}
