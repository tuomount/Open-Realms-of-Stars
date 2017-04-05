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
 * Race Image Panel Test
 * 
 */
public class RaceImagePanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatisticPanel() {
    RaceImagePanel panel = new RaceImagePanel();
    panel.setRaceToShow("Test");
    assertEquals("Test", panel.getRaceToShow());
    panel.setRaceToShow(null);
    assertEquals("Not in use", panel.getRaceToShow());
  }

}
