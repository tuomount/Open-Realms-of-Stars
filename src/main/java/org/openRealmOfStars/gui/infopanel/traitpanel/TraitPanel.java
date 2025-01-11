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
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.player.government.trait.GovTrait;
import org.openRealmOfStars.player.race.trait.RaceTrait;

/**
 * Trait panel for both government and space race editor.
 *
 */
public class TraitPanel extends InfoPanel {

  /**
   * Static string for traitValue label.
   */
  private static final String TRAIT_VALUE_LABEL = "Trait points left: ";
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
   * Coloumns;
   */
  private ArrayList<EmptyInfoPanel> columns;
  /**
   * Max Number of columns;
   */
  private int maxColumns = 2;
  /**
   * Current column index.
   */
  private int currentColumn = 0;
  /**
   * TraitPanel constructor.
   * @param screenWidth Screen width
   * @param traits Government Traits in array
   * @param listener ActionListener
   */
  public TraitPanel(final int screenWidth, final GovTrait[] traits,
      final ActionListener listener) {
    super();
    this.setTitle("Government Traits");
    groups = new ArrayList<>();
    checkBoxes = new ArrayList<>();
    columns = new ArrayList<>();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    traitValue = new SpaceLabel(TRAIT_VALUE_LABEL + "4");
    this.add(traitValue);
    SpaceLabel hint = new SpaceLabel("Balanced trait value is 4."
        + " Below government is overpowered. Above government is weak.");
    this.add(hint);
    EmptyInfoPanel traitsPane = new EmptyInfoPanel();
    if (screenWidth < 1280) {
      traitsPane.setLayout(new GridLayout(0, 2));
      maxColumns = 2;
      for (int i = 0; i < maxColumns; i++) {
        EmptyInfoPanel column = new EmptyInfoPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        columns.add(column);
      }
    } else if (screenWidth < 1600) {
      traitsPane.setLayout(new GridLayout(0, 3));
      maxColumns = 3;
      for (int i = 0; i < maxColumns; i++) {
        EmptyInfoPanel column = new EmptyInfoPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        columns.add(column);
      }
    } else {
      traitsPane.setLayout(new GridLayout(0, 4));
      maxColumns = 4;
      for (int i = 0; i < maxColumns; i++) {
        EmptyInfoPanel column = new EmptyInfoPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        columns.add(column);
      }
    }
    for (GovTrait trait : traits) {
      TraitCheckBox checkBox = new TraitCheckBox(trait);
      checkBox.addActionListener(listener);
      checkBox.setActionCommand(GameCommands.COMMAND_GOV_TRAIT_SELECTED
          + "+" + checkBox.getTraitId());
      checkBoxes.add(checkBox);
      TraitGroupPanel groupPanel = getOrCreateGroup(trait.getGroup());
      groupPanel.addCheckBox(checkBox);
    }
    orderGroupsIntoColumn();
    for (EmptyInfoPanel columnPanel : columns) {
      traitsPane.add(columnPanel);
    }
    JScrollPane scroll = new JScrollPane(traitsPane);
    this.add(scroll);
  }

  /**
   * TraitPanel constructor.
   * @param screenWidth Screen width
   * @param traits Race Traits in array
   * @param listener ActionListener
   */
  public TraitPanel(final int screenWidth, final RaceTrait[] traits,
      final ActionListener listener) {
    super();
    this.setTitle("Race Traits");
    groups = new ArrayList<>();
    checkBoxes = new ArrayList<>();
    columns = new ArrayList<>();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    traitValue = new SpaceLabel(TRAIT_VALUE_LABEL + "4");
    this.add(traitValue);
    SpaceLabel hint = new SpaceLabel("Balanced trait value is 4."
        + " Below space race is overpowered. Above space race is weak.");
    this.add(hint);
    EmptyInfoPanel traitsPane = new EmptyInfoPanel();
    if (screenWidth < 1280) {
      traitsPane.setLayout(new GridLayout(0, 2));
      maxColumns = 2;
      for (int i = 0; i < maxColumns; i++) {
        EmptyInfoPanel column = new EmptyInfoPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        columns.add(column);
      }
    } else if (screenWidth < 1600) {
      traitsPane.setLayout(new GridLayout(0, 3));
      maxColumns = 3;
      for (int i = 0; i < maxColumns; i++) {
        EmptyInfoPanel column = new EmptyInfoPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        columns.add(column);
      }
    } else {
      traitsPane.setLayout(new GridLayout(0, 4));
      maxColumns = 4;
      for (int i = 0; i < maxColumns; i++) {
        EmptyInfoPanel column = new EmptyInfoPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        columns.add(column);
      }
    }
    for (RaceTrait trait : traits) {
      TraitCheckBox checkBox = new TraitCheckBox(trait);
      checkBox.addActionListener(listener);
      checkBox.setActionCommand(GameCommands.COMMAND_RACE_TRAIT_SELECTED
          + "+" + checkBox.getTraitId());
      checkBoxes.add(checkBox);
      TraitGroupPanel groupPanel = getOrCreateGroup(trait.getGroup());
      groupPanel.addCheckBox(checkBox);
    }
    orderGroupsIntoColumn();
    for (EmptyInfoPanel columnPanel : columns) {
      traitsPane.add(columnPanel);
    }
    JScrollPane scroll = new JScrollPane(traitsPane);
    this.add(scroll);
  }

  /**
   * Uncheck all trait boxes.
   */
  public void uncheckAllTraits() {
    int points = 4;
    traitValue.setText(TRAIT_VALUE_LABEL + points);
    for (TraitCheckBox box : checkBoxes) {
      box.setSelected(false);
    }
  }

  /**
   * Check trait box.
   * @param traitId TraitID to check
   */
  public void checkTrait(final String traitId) {
    for (TraitCheckBox box : checkBoxes) {
      if (box.getTraitId().equals(traitId)) {
        box.setSelected(true);
      }
    }
  }
  /**
   * Handle Trait selection.
   * @param traitId Trait ID which was selected.
   */
  public void handleTraitSelection(final String traitId) {
    int points = 4;
    TraitCheckBox selectedBox = null;
    for (TraitCheckBox box : checkBoxes) {
      if (box.getTraitId().equals(traitId)) {
        selectedBox = box;
      }
      if (box.isSelected()) {
        points = points - box.getTraitPoints();
      }
    }
    traitValue.setText(TRAIT_VALUE_LABEL + points);
    if (selectedBox != null
        && selectedBox.getTraitConflictsWithId().length > 0) {
      boolean conflictEnable = true;
      if (selectedBox.isSelected()) {
        conflictEnable = false;
      }
      for (String conflict : selectedBox.getTraitConflictsWithId()) {
        for (TraitCheckBox box : checkBoxes) {
          if (box.getTraitId().equals(conflict)) {
            box.setEnabled(conflictEnable);
            if (conflictEnable) {
              TraitCheckBox[] possibleConflicts = getBoxesById(
                  box.getTraitConflictsWithId());
              for (TraitCheckBox boxConflict : possibleConflicts) {
                if (boxConflict.isSelected()) {
                  box.setEnabled(false);
                  break;
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * Get Boxes by ID.
   * @param ids Array of IDs
   * @return array of check boxes.
   */
  private TraitCheckBox[] getBoxesById(final String[] ids) {
    ArrayList<TraitCheckBox> list = new ArrayList<>();
    for (String id : ids) {
      for (TraitCheckBox box : checkBoxes) {
        if (box.getTraitId().equals(id)) {
          list.add(box);
        }
      }
    }
    return list.toArray(new TraitCheckBox[0]);
  }
  /**
   * Order groups into columns.
   */
  private void orderGroupsIntoColumn() {
    int[] boxes = new int[maxColumns];
    for (int i = 0; i < maxColumns; i++) {
      boxes[i] = 0;
    }
    for (TraitGroupPanel panel : groups) {
      getCurrentColumn().add(panel);
      boxes[currentColumn] = boxes[currentColumn] + panel.countCheckBoxes();
      if (currentColumn > 0) {
        if (boxes[currentColumn - 1] < boxes[currentColumn] + 2) {
          moveToNextColumn();
        }
      } else {
        moveToNextColumn();
      }
    }
  }
  /**
   * Get current column from array.
   * @return EmptyInfoPanel
   */
  private EmptyInfoPanel getCurrentColumn() {
    return columns.get(currentColumn);
  }

  /**
   * Move column index to next column. Jumps back to zero if goes past
   * the upper limit.
   */
  private void moveToNextColumn() {
    currentColumn++;
    if (currentColumn >= maxColumns) {
      currentColumn = 0;
    }
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
    groups.add(panel);
    return panel;
  }

  /**
   * Get all checkboxes in array.
   * @return TraitCheckBox array
   */
  public TraitCheckBox[] getAllBoxes() {
    return checkBoxes.toArray(new TraitCheckBox[0]);
  }
}
