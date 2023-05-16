package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.fleet.TradeRoute;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Ship trade route list renderer test class
*
*/
public class TradeRouteListRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeRouteListRenderer() {
    JList<? extends TradeRoute> list = new JList<>();
    TradeRoute value = Mockito.mock(TradeRoute.class);
    Mockito.when(value.toString()).thenReturn("ToString");
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;
    TradeRouteListRenderer renderer = new TradeRouteListRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("ToString", label.getText());
    assertEquals(GuiStatics.getCoolSpaceColorDark(), label.getForeground());
    assertEquals(GuiStatics.getDeepSpaceDarkerColor(), label.getBackground());
    selected = true;
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("ToString", label.getText());
    assertEquals(GuiStatics.getCoolSpaceColor(), label.getForeground());
    assertEquals(GuiStatics.getDeepSpaceColor(), label.getBackground());

  }

}
