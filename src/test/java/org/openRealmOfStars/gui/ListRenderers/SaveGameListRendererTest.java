package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.*;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.SavedGame;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2023 Tuomo Untinen
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
* Test for SaveGameListRenderer.
*/
public class SaveGameListRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    JList<? extends SavedGame> list = new JList<>();
    SavedGame save = Mockito.mock(SavedGame.class);
    Mockito.when(save.getFilename()).thenReturn("autosave.sav");
    Mockito.when(save.getTime()).thenReturn("11-01-2017");
    Mockito.when(save.getStarYear()).thenReturn(2410);
    Mockito.when(save.getRealms()).thenReturn(6);
    Mockito.when(save.getEmpireName()).thenReturn("Empire of Saves");
    Mockito.when(save.getGalaxySize()).thenReturn("75 x 75");
    int index = 0;
    boolean selected = true;
    boolean cellHasFocus = false;

    SavedGame value = save;
    SaveGameListRenderer renderer = new SaveGameListRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("autosave.sav - 11-01-2017 Star year: 2410 - Empire of Saves Realms: 6 - 75 x 75",label.getText());
    assertEquals(GuiStatics.getCoolSpaceColor(), label.getForeground());

    selected = false;
    assertEquals(Color.BLACK, label.getBackground());
    renderer = new SaveGameListRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, value, index,
        selected, cellHasFocus);
    assertEquals("autosave.sav - 11-01-2017 Star year: 2410 - Empire of Saves Realms: 6 - 75 x 75",label.getText());
    assertEquals(GuiStatics.COLOR_GREY_TEXT, label.getForeground());
    assertEquals(Color.BLACK, label.getBackground());


  }

}
