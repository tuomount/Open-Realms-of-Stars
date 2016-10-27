package org.openRealmOfStars.gui.ListRenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.player.ship.ShipStat;


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
 * ShipStat list renderer
 * 
 */
public class ShipStatRenderer implements ListCellRenderer<ShipStat> {

  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
  

  @Override
  public Component getListCellRendererComponent(JList<? extends ShipStat> list,
      ShipStat value, int index,boolean isSelected, boolean cellHasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
        isSelected, cellHasFocus);
    renderer.setFont(GuiStatics.getFontCubellan());
    StringBuilder sb = new StringBuilder();
    sb.append(value.getDesign().getName());
    sb.append(" - ");
    sb.append(value.getDesign().getHull().getSize());
    if (value.isObsolete()) {
      sb.append(" Obsoleted"); 
    } else {
      sb.append(" Cost/Metal: ");
      sb.append(value.getDesign().getCost());
      sb.append("/");
      sb.append(value.getDesign().getMetalCost());
    }
    renderer.setText(sb.toString());
    if (isSelected) {
      if (value.isObsolete()) {
        renderer.setForeground(GuiStatics.COLOR_GREY_TEXT);
      } else {
        renderer.setForeground(GuiStatics.COLOR_GREEN_TEXT);
      }
      renderer.setBackground(Color.BLACK);
    } else {
      if (value.isObsolete()) {
        renderer.setForeground(GuiStatics.COLOR_GREY_TEXT_DARK);
      } else {
        renderer.setForeground(GuiStatics.COLOR_GREEN_TEXT_DARK);
      }
      renderer.setBackground(Color.BLACK);
    }
    return renderer;
  }

}
