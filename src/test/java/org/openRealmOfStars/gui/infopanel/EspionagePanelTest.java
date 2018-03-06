package org.openRealmOfStars.gui.infopanel;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.gui.GuiStatics;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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
 * Espionage panel test
 * 
 */
public class EspionagePanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    String realm = "Test realm";
    String desc = "Test description";
    int value = 5;
    String relationText = "Neutral Peace";
    Color color = GuiStatics.COLOR_GREEN_TEXT;
    EspionagePanel panel = new EspionagePanel(realm, desc, value,
        relationText, color);
    assertEquals(realm, panel.getRealmName());
    assertEquals(desc, panel.getDescription());
    assertEquals(value, panel.getValue());
  }

}
