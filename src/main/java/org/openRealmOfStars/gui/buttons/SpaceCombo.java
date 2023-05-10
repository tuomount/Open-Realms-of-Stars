package org.openRealmOfStars.gui.buttons;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2020 Tuomo Untinen
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
* Class for creating cool looking space combo box.
*
*
* @param <E> Space Combobox type parameter for example String.
*/
public class SpaceCombo<E> extends JComboBox<E> {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Init default space look and feel.
   * @param data Data model for combo box in array.
   */
  public SpaceCombo(final E[] data) {
    super(data);
    setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    setForeground(GuiStatics.getCoolSpaceColor());
    setBorder(new SimpleBorder());
    setFont(GuiStatics.getFontCubellan());
    setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    setRenderer(dlcr);
  }

  @Override
  public JToolTip createToolTip() {
    JToolTip toolTip = super.createToolTip();
    toolTip.setForeground(GuiStatics.getCoolSpaceColor());
    toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    toolTip.setBorder(BorderFactory
        .createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER));
    return toolTip;
  }
}
