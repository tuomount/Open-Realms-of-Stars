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

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceLabel;

/**
 * Trait panel for both government and space race editor.
 *
 */
public class TraitPanel extends InfoPanel {

  /**
   * Static string for traitValue label.
   */
  private static final String TRAIT_VALUE_LABEL = "Selected traits: ";
  /**
   * Label containing total trait value
   */
  private SpaceLabel traitValue;

  /**
   * Trait groups in array list.
   */
  private ArrayList<TraitGroupPanel> groups;
  /**
   * Checkboxes in array list.
   */
  private ArrayList<TraitCheckBox> checkBoxes;

  /**
   * TraitPanel constructor.
   * @param screenWidth Screen width
   */
  public TraitPanel(final int screenWidth) {
    super();
    groups = new ArrayList<>();
    checkBoxes = new ArrayList<>();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    traitValue = new SpaceLabel(TRAIT_VALUE_LABEL + "+44");
    this.add(traitValue);
    EmptyInfoPanel traitsPane = new EmptyInfoPanel();
    if (screenWidth < 1280) {
      traitsPane.setLayout(new GridLayout(0, 2));
    } else {
      traitsPane.setLayout(new GridLayout(0, 3));
    }
    this.add(traitsPane);
  }

  /**
   * Get Group by group name or create if it does not exits.
   * @param groupName GroupName to search.
   * @return TraitGroupPanel
   */
  public TraitGroupPanel getOrCreateGroup(final String groupName) {
    for (TraitGroupPanel panel : groups) {
      if (panel.getTitle().equals(groupName)) {
        return panel;
      }
    }
    TraitGroupPanel panel = new TraitGroupPanel(groupName);
    return panel;
  }
}
