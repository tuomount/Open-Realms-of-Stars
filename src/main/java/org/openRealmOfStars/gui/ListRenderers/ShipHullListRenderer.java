package org.openRealmOfStars.gui.ListRenderers;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.player.ship.ShipHull;

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
 * Ship Hull list renderer
 *
 */
public class ShipHullListRenderer implements ListCellRenderer<ShipHull> {

  /**
   * Default list cell renderer
   */
  private DefaultListCellRenderer defaultRenderer
      = new DefaultListCellRenderer();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends ShipHull> list, final ShipHull value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.getFontCubellan());
    if (value != null) {
      renderer.setText(value.getName() + " - " + value.getSize().toString());
    } else {
      renderer.setText("No hull");
    }
    if (isSelected) {
      renderer.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
      renderer.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE);
    } else {
      renderer.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
      renderer.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    }
    return renderer;
  }
}