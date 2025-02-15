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
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.openRealmOfStars.gui.util.GuiFonts;
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
   * Internal panel with empty border.
   */
  private JPanel internalPanel;
  /**
   * Internal border.
   */
  private EmptyBorder internalBorder;

  /**
   * TraitGroupPanel Constructor.
   * @param groupName Group name
   */
  public TraitGroupPanel(final String groupName) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    border = new TitledBorder(groupName);
    border.setTitleColor(GuiStatics.getInfoTextColor());
    border.setTitleFont(GuiFonts.getFontCubellanSmaller());
    this.setBorder(border);
    this.setBackground(GuiStatics.getPanelBackground());
    internalPanel = new JPanel();
    internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.Y_AXIS));
    internalPanel.setBackground(GuiStatics.getPanelBackground());
    internalBorder = null;
    this.add(internalPanel);
    checkBoxes = new ArrayList<>();
  }

  /**
   * Set clear border size by pixels.
   *
   * @param top How many pixels are added on top.
   * @param left How many pixels are added on left.
   * @param right How many pixels are added on right.
   * @param bottom How many pixels are added on bottom.
   */
  public void setBorderInsets(final int top, final int left, final int right,
      final int bottom) {
    if (internalBorder == null) {
      internalBorder = new EmptyBorder(top, left, bottom, right);
      internalPanel.setBorder(internalBorder);
    }
  }
  /**
   * How many check box in single group?
   * @return Number of check boxes.
   */
  public int countCheckBoxes() {
    return checkBoxes.size();
  }
  /**
   * Add check box into group.
   * @param checkBox TraitCheckBox
   */
  public void addCheckBox(final TraitCheckBox checkBox) {
    checkBoxes.add(checkBox);
    if (checkBoxes.size() > 1) {
      internalPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    }
    internalPanel.add(checkBox);
  }

  /**
   * Set panel title. Set null to remove the title
   * @param title String or null
   */
  public void setTitle(final String title) {
    border.setTitle(title);
  }

  /**
   * Get Group title aka group name
   * @return Group name
   */
  public String getTitle() {
    return border.getTitle();
  }
}
