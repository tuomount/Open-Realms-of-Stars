package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.*;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;

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
* Test for ShipStatRenderer.
*/
public class ShipStatRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    JList<? extends ShipStat> list = new JList<>();
    ShipStat stat = Mockito.mock(ShipStat.class);
    ShipDesign design = Mockito.mock(ShipDesign.class);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Hull 1");
    Mockito.when(hull.getSize()).thenReturn(ShipSize.MEDIUM);
    Mockito.when(hull.getCost()).thenReturn(10);
    Mockito.when(hull.getMetalCost()).thenReturn(15);
    Mockito.when(design.getName()).thenReturn("Ship 1");
    Mockito.when(design.getHull()).thenReturn(hull);
    Mockito.when(design.getCost()).thenReturn(10);
    Mockito.when(design.getMetalCost()).thenReturn(15);
    Mockito.when(stat.getDesign()).thenReturn(design);
    Mockito.when(stat.isObsolete()).thenReturn(false);
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;
    
    ShipStat value = stat;
    ShipStatRenderer renderer = new ShipStatRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Ship 1 - Medium Cost/Metal: 10/15",label.getText());
    assertEquals(GuiStatics.getInfoTextColor(), label.getForeground());
    assertEquals(Color.BLACK, label.getBackground());

    selected = true;
    renderer = new ShipStatRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Ship 1 - Medium Cost/Metal: 10/15",label.getText());
    assertEquals(GuiStatics.COLOR_GREEN_TEXT, label.getForeground());

    selected = false;
    Mockito.when(stat.isObsolete()).thenReturn(true);
    renderer = new ShipStatRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Ship 1 - Medium Obsoleted",label.getText());
    assertEquals(GuiStatics.COLOR_GREY_TEXT_DARK, label.getForeground());

    selected = true;
    Mockito.when(stat.isObsolete()).thenReturn(true);
    renderer = new ShipStatRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("Ship 1 - Medium Obsoleted",label.getText());
    assertEquals(GuiStatics.COLOR_GREY_TEXT, label.getForeground());

  }

}
