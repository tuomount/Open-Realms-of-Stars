package org.openRealmOfStars.gui.labels;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.utilies.GuiStatics;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018,2020 Tuomo Untinen
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
* Space Combo box
*
* @param <E> Element type for SpaceComboBox
*/
public class SpaceComboBox<E> extends JComboBox<E> {

  /**
   * Default serial
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for SpaceComboBox. This will
   * automatically add items when creating the combobox.
   * @param items Items to add
   */
  public SpaceComboBox(final E[] items) {
    super(items);
    this.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
  }

  @Override
  public JToolTip createToolTip() {
    JToolTip toolTip = super.createToolTip();
    toolTip.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    toolTip.setBorder(BorderFactory
        .createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER));
    return toolTip;
  }
}
