package org.openRealmOfStars.gui.ListRenderers;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.starMap.planet.construction.Construction;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Production list renderer
 *
 */
public class ProductionListRenderer implements ListCellRenderer<Construction> {

  /**
   * Default list cell renderer
   */
  private DefaultListCellRenderer defaultRenderer
      = new DefaultListCellRenderer();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends Construction> list, final Construction value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.getFontCubellan());
    renderer.setIcon(value.getIcon().getAsIcon());
    if (isSelected) {
      renderer.setForeground(GuiStatics.getCoolSpaceColor());
      renderer.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE);
    } else {
      renderer.setForeground(GuiStatics.getCoolSpaceColorDark());
      renderer.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    }
    return renderer;
  }
}