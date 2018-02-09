package org.openRealmOfStars.gui.scrollPanel;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.icons.Icons;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2016-2018  Tuomo Untinen
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
* Test for Space Scrollbar UI
*
*/
public class SpaceScrollBarUiTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testButtons() {
    ComponentUI componentUi = SpaceScrollBarUI.createUI(null);
    SpaceScrollBarUI ui = (SpaceScrollBarUI) componentUi;
    JButton btn = ui.createDecreaseButton(SwingConstants.NORTH);
    if (btn instanceof IconButton) {
      IconButton iBtn = (IconButton) btn;
      assertEquals(Icons.getIconByName(Icons.ICON_SCROLL_UP_PRESSED).getIcon(),
          iBtn.getPressedImage());
    }
    btn = ui.createDecreaseButton(SwingConstants.WEST);
    if (btn instanceof IconButton) {
      IconButton iBtn = (IconButton) btn;
      assertEquals(Icons.getIconByName(Icons.ICON_SCROLL_LEFT).getIcon(),
          iBtn.getNotPressedImage());
    }
    btn = ui.createIncreaseButton(SwingConstants.EAST);
    if (btn instanceof IconButton) {
      IconButton iBtn = (IconButton) btn;
      assertEquals(Icons.getIconByName(Icons.ICON_SCROLL_RIGHT).getIcon(),
          iBtn.getNotPressedImage());
    }
    btn = ui.createIncreaseButton(SwingConstants.SOUTH);
    if (btn instanceof IconButton) {
      IconButton iBtn = (IconButton) btn;
      assertEquals(Icons.getIconByName(Icons.ICON_SCROLL_DOWN_PRESSED).getIcon(),
          iBtn.getPressedImage());
    }
  }

}
