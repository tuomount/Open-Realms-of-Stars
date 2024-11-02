package org.openRealmOfStars.gui.infopanel.traitpanel;
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.openRealmOfStars.gui.util.GuiStatics;

/**
 * Group traits into panel with group name at top.
 *
 */
public class TraitGroupPanel extends JPanel {

  /**
   * Array List of check boxes.
   */
  private ArrayList<TraitCheckBox> checkBoxes;

  /**
   * Titled Border.
   */
  private TitledBorder border;

  /**
   * TraitGroupPanel Constructor.
   * @param groupName Group name
   */
  public TraitGroupPanel(final String groupName) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    border = new TitledBorder(groupName);
    checkBoxes = new ArrayList<>();
  }

  /**
   * Add check box into group.
   * @param checkBox TraitCheckBox
   */
  public void addCheckBox(final TraitCheckBox checkBox) {
    checkBoxes.add(checkBox);
    if (checkBoxes.size() > 1) {
      this.add(Box.createRigidArea(new Dimension(5, 5)));
    }
    this.add(checkBox);
  }

  /**
   * Set panel title. Set null to remove the title
   * @param title String or null
   */
  public void setTitle(final String title) {
    border.setTitle(title);
  }

  @Override
  protected void paintComponent(final Graphics arg0) {
    Graphics2D g2d = (Graphics2D) arg0;
    g2d.setColor(GuiStatics.getPanelBackground());
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
  }
}
