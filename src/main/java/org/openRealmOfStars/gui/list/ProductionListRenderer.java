package org.openRealmOfStars.gui.list;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
 * Copyright (C) 2016 Tuomo Untinen
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

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.RenderLabel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.starMap.planet.construction.Construction;

/**
 *
 * Production list renderer
 *
 */
public class ProductionListRenderer implements ListCellRenderer<Construction> {


  /**
   * Reusable label for rendering
   */
  private RenderLabel label = new RenderLabel();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends Construction> list, final Construction value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    label.setFont(GuiFonts.getFontCubellan());
    label.setText(value.getName());
    label.setIcon(Icons.getIconByName(value.getIconId()).getAsIcon());
    label.setOpaque(true);
    if (isSelected) {
      label.setForeground(GuiStatics.getCoolSpaceColor());
      label.setBackground(GuiStatics.getDeepSpaceColor());
    } else {
      label.setForeground(GuiStatics.getCoolSpaceColorDark());
      label.setBackground(GuiStatics.getDeepSpaceDarkColor());
    }
    return label;
  }
}