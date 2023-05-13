package org.openRealmOfStars.gui.ListRenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.ambient.BridgeDevice;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* BridgeDevice renderer
*
*/
public class BridgeDeviceRenderer implements ListCellRenderer<BridgeDevice> {

  /**
   * Default list cell renderer
   */
  private DefaultListCellRenderer defaultRenderer
      = new DefaultListCellRenderer();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends BridgeDevice> list, final BridgeDevice value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.getFontCubellan());
    renderer.setText(value.toString());
    if (isSelected) {
      renderer.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      renderer.setBackground(Color.BLACK);
    } else {
      renderer.setForeground(GuiStatics.getInfoTextColor());
      renderer.setBackground(Color.BLACK);
    }
    return renderer;
  }
}
