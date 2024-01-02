package org.openRealmOfStars.gui.list;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 */

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechLine;

/**
*
* SpeechLine renderer
*
*/
public class SpeechLineRenderer implements ListCellRenderer<SpeechLine> {

  /**
   * Default list cell renderer
   */
  private DefaultListCellRenderer defaultRenderer
      = new DefaultListCellRenderer();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends SpeechLine> list, final SpeechLine value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
    renderer.setFont(GuiFonts.getFontCubellan());
    renderer.setText(value.getLine());
    if (isSelected) {
      renderer.setForeground(GuiStatics.getInfoTextColor());
      renderer.setBackground(Color.BLACK);
    } else {
      renderer.setForeground(GuiStatics.getInfoTextColorDark());
      renderer.setBackground(Color.BLACK);
    }
    return renderer;
  }
}
