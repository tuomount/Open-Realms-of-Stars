package org.openRealmOfStars.gui.panels;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.icons.Icons;

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
* Class for Space style slider test
*
*/

public class SpaceSliderPanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    SpaceSliderPanel panel = new SpaceSliderPanel("Minus", "Plus",
        Icons.ICON_BATTERY, "Test", 0, 40, 20, "Sliding", listener);
    assertEquals(20, panel.getSliderValue());
    assertEquals(0, panel.getMinimumValue());
    assertEquals(40, panel.getMaximumValue());
    assertEquals(false, panel.isSliderChanged());
    panel.setSliderValue(60);
    assertEquals(40, panel.getSliderValue());
    panel.setSliderValue(-60);
    assertEquals(0, panel.getSliderValue());
    panel.setSliderValue(30);
    assertEquals(30, panel.getSliderValue());
    panel.setSliderMinorTick(2);
    panel.setSliderMajorTick(20);
  }

}
