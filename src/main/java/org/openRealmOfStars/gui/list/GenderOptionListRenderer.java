package org.openRealmOfStars.gui.list;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.game.state.options.GenderOption;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;

/**
 *
 * Gender option list renderer
 *
 */
public class GenderOptionListRenderer
             implements ListCellRenderer<GenderOption> {

  /**
   * Default list cell renderer
   */
  private DefaultListCellRenderer defaultRenderer
      = new DefaultListCellRenderer();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends GenderOption> list, final GenderOption value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    defaultRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
    renderer.setFont(GuiFonts.getFontCubellan());
    renderer.setText(value.getName());
    if (isSelected) {
      renderer.setForeground(GuiStatics.getCoolSpaceColor());
      renderer.setBackground(GuiStatics.getDeepSpaceColor());
    } else {
      renderer.setForeground(GuiStatics.getCoolSpaceColorDark());
      renderer.setBackground(GuiStatics.getDeepSpaceDarkColor());
    }
    return renderer;
  }
}