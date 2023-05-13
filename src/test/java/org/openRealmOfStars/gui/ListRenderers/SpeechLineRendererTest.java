package org.openRealmOfStars.gui.ListRenderers;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechLine;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;

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
* Test for SpeechLineRenderer.
*/
public class SpeechLineRendererTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpeechLineRenderer() {
    JList<? extends SpeechLine> list = new JList<>();
    SpeechLine line = Mockito.mock(SpeechLine.class);
    Mockito.when(line.getLine()).thenReturn("My line");
    Mockito.when(line.getType()).thenReturn(SpeechType.AGREE);
    int index = 0;
    boolean selected = false;
    boolean cellHasFocus = false;
    
    SpeechLineRenderer renderer = new SpeechLineRenderer();
    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, line, index,
        selected, cellHasFocus);
    assertEquals("My line",label.getText());
    assertEquals(GuiStatics.getInfoTextColor(), label.getForeground());
    assertEquals(Color.black, label.getBackground());
    
    selected = true;
    renderer = new SpeechLineRenderer();
    label = (JLabel) renderer.getListCellRendererComponent(list, line, index,
        selected, cellHasFocus);
    assertEquals("My line",label.getText());
    assertEquals(GuiStatics.COLOR_GREEN_TEXT, label.getForeground());
    assertEquals(Color.black, label.getBackground());
  }

}
