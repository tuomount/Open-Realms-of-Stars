package org.openRealmOfStars.gui.ListRenderers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2022 Tuomo Untinen
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
* Tutorial tree cell renderer.
*
*/
public class LeaderTreeCellRenderer implements TreeCellRenderer {

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
      }
      if (object instanceof Leader) {
        Leader leader = (Leader) object;
        renderer.setText(leader.getCallName());
        renderer.setFont(GuiStatics.getFontCubellan());
        renderer.setIcon(
            LeaderUtility.getIconBasedOnLeaderJob(leader).getAsIcon());
      }
    }
    return renderer;
  }

}
