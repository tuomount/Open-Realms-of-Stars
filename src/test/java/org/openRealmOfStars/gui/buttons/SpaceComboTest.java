package org.openRealmOfStars.gui.buttons;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openRealmOfStars.gui.utilies.GuiStatics;

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
 * Tests for SpaceComboTest
 */
public class SpaceComboTest {

  @Test
  public void testBasic() {
    String[] tmpStr = new String[3];
    tmpStr[0] = "Test A";
    tmpStr[1] = "Test B";
    tmpStr[2] = "Test C";
    SpaceCombo<String> combo = new SpaceCombo<>(tmpStr);
    assertEquals(GuiStatics.getFontCubellan(), combo.getFont());
    assertEquals(GuiStatics.getDeepSpaceDarkerColor(), combo.getBackground());
    assertEquals(GuiStatics.getCoolSpaceColor(), combo.getForeground());
  }

}
