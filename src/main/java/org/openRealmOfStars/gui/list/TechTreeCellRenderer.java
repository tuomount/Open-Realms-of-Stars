package org.openRealmOfStars.gui.list;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.tech.Tech;

/**
*
* Tech tree cell renderer.
*
*/
public class TechTreeCellRenderer implements TreeCellRenderer {

  /**
   * Default Tree cell renderer
   */
  private DefaultTreeCellRenderer defaultRenderer
      = new DefaultTreeCellRenderer();
  @Override
  public Component getTreeCellRendererComponent(final JTree tree,
      final Object value, final boolean selected, final boolean expanded,
      final boolean leaf, final int row, final boolean hasFocus) {
    JLabel renderer = (JLabel) defaultRenderer.getTreeCellRendererComponent(
        tree, value, selected, expanded, leaf, row, hasFocus);
    if (value != null && value instanceof DefaultMutableTreeNode) {
      Object object = ((DefaultMutableTreeNode) value)
          .getUserObject();
      if (object instanceof String) {
        String str = (String) object;
        renderer.setText(str);
        if (str.startsWith("Combat")) {
          renderer.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH)
              .getAsIcon());
        }
        if (str.startsWith("Defense")) {
          renderer.setIcon(Icons.getIconByName(Icons.ICON_DEFENSE_TECH)
              .getAsIcon());
        }
        if (str.startsWith("Hull")) {
          renderer.setIcon(Icons.getIconByName(Icons.ICON_HULL_TECH)
              .getAsIcon());
        }
        if (str.startsWith("Planetary")) {
          renderer.setIcon(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH)
              .getAsIcon());
        }
        if (str.startsWith("Propulsion")) {
          renderer.setIcon(Icons.getIconByName(Icons.ICON_PROPULSION_TECH)
              .getAsIcon());
        }
        if (str.startsWith("Electronic")) {
          renderer.setIcon(Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH)
              .getAsIcon());
        }
      }
      if (object instanceof Tech) {
        Tech tech = (Tech) object;
        renderer.setText(tech.getName());
        renderer.setFont(GuiFonts.getFontCubellan());
        renderer.setIcon(tech.getIcon().getAsIcon());
      }
      if (selected) {
        renderer.setForeground(GuiStatics.getInfoTextColor());
        renderer.setBackground(Color.BLACK);
      } else {
        renderer.setForeground(GuiStatics.getInfoTextColorDark());
        renderer.setBackground(Color.BLACK);
      }
    }
    return renderer;
  }

}
