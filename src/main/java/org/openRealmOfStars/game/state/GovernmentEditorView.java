package org.openRealmOfStars.game.state;
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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JTextField;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.government.RulerSelection;

/**
 * Editor for government JSON files with UI.
 *
 */
public class GovernmentEditorView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Government name.
   */
  private JTextField governmentNameField;

  /**
   * Combobox for Ruler selection.
   */
  private SpaceComboBox<RulerSelection> rulerSelectionCombo;
  /**
   * Ruler title for male.
   */
  private JTextField rulerTitleMaleField;
  /**
   * Ruler title for female.
   */
  private JTextField rulerTitleFemaleField;
  /**
   * Constructor for GovernmentEditorView.
   *
   * @param listener ActionListener
   */
  public GovernmentEditorView(final ActionListener listener) {
    this.setLayout(new BorderLayout());
    InfoPanel mainPanel = new InfoPanel();
    mainPanel.setTitle("Government Editor");
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    InfoPanel governmentInfoPanel = new InfoPanel();
    governmentInfoPanel.setTitle("New government");
    governmentInfoPanel.setLayout(new BoxLayout(governmentInfoPanel,
        BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Government name:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    governmentInfoPanel.add(label);
    governmentInfoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    governmentNameField = new JTextField("Custom government");
    governmentNameField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    governmentNameField.setForeground(GuiStatics.getCoolSpaceColor());
    governmentNameField.setFont(GuiFonts.getFontCubellanSmaller());
    governmentNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    governmentInfoPanel.add(governmentNameField);
    governmentInfoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    label = new SpaceLabel("How ruler is selected:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    governmentInfoPanel.add(label);
    governmentInfoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    rulerSelectionCombo = new SpaceComboBox<>(RulerSelection.values());
    rulerSelectionCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    rulerSelectionCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    rulerSelectionCombo.setBorder(new SimpleBorder());
    rulerSelectionCombo.setFont(GuiFonts.getFontCubellan());
    rulerSelectionCombo.getModel()
        .setSelectedItem(RulerSelection.AI_RULER);
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    rulerSelectionCombo.setRenderer(dlcr);
    rulerSelectionCombo.addActionListener(listener);
    rulerSelectionCombo.setActionCommand(
        GameCommands.COMMAND_GOVERNMENT_EDITOR_RULER_SELECT);
    governmentInfoPanel.add(rulerSelectionCombo);
    governmentInfoPanel.add(Box.createRigidArea(new Dimension(10, 5)));

    label = new SpaceLabel("Ruler title for male:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    governmentInfoPanel.add(label);
    governmentInfoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    rulerTitleMaleField = new JTextField("President");
    rulerTitleMaleField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    rulerTitleMaleField.setForeground(GuiStatics.getCoolSpaceColor());
    rulerTitleMaleField.setFont(GuiFonts.getFontCubellanSmaller());
    rulerTitleMaleField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    governmentInfoPanel.add(rulerTitleMaleField);
    label = new SpaceLabel("Ruler title for female:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    governmentInfoPanel.add(label);
    governmentInfoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    rulerTitleFemaleField = new JTextField("President");
    rulerTitleFemaleField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    rulerTitleFemaleField.setForeground(GuiStatics.getCoolSpaceColor());
    rulerTitleFemaleField.setFont(GuiFonts.getFontCubellanSmaller());
    rulerTitleFemaleField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    governmentInfoPanel.add(rulerTitleFemaleField);

    mainPanel.add(governmentInfoPanel);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to main menu",
        GameCommands.COMMAND_MAIN_MENU);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    this.add(mainPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
  }
}