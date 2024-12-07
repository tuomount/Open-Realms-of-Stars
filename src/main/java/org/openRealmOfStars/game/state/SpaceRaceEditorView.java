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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.infopanel.traitpanel.TraitCheckBox;
import org.openRealmOfStars.gui.infopanel.traitpanel.TraitPanel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.government.RulerSelection;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.race.trait.TraitFactory;

/**
 * Editor for government JSON files with UI.
 *
 */
public class SpaceRaceEditorView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * SpaceRace name.
   */
  private JTextField spaceRaceNameField;
  /**
   * SpaceRace name in single.
   */
  private JTextField spaceRaceNameSingleField;

  /**
   * Combobox for Ruler selection.
   */
  private SpaceComboBox<RulerSelection> bridgeIdCombo;
  /**
   * Ruler title for male.
   */
  private JTextField rulerTitleMaleField;
  /**
   * Ruler title for female.
   */
  private JTextField rulerTitleFemaleField;
  /**
   * Trait panel containing all the selectable traits.
   */
  private TraitPanel traitPanel;
  /**
   * Conflict with government ID.
   */
  private boolean conflictWithId = false;
  /**
   * Constructor for GovernmentEditorView.
   *
   * @param listener ActionListener
   */
  public SpaceRaceEditorView(final ActionListener listener) {
    this.setLayout(new BorderLayout());
    InfoPanel mainPanel = new InfoPanel();
    mainPanel.setTitle("Space Race Editor");
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(GuiFonts.getFontCubellanSmaller());
    tabs.setForeground(GuiStatics.getCoolSpaceColorDarker());
    tabs.setBackground(GuiStatics.getDeepSpaceDarkColor());
    tabs.add("Name and Traits", createSpaceRaceMainTab(listener));
    mainPanel.add(tabs);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to main menu",
        GameCommands.COMMAND_MAIN_MENU);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    btn = new SpaceButton("Load government",
        GameCommands.COMMAND_LOAD_GOVERNMENT);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.WEST);
    btn = new SpaceButton("Save government",
        GameCommands.COMMAND_SAVE_GOVERNMENT);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.EAST);
    this.add(mainPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Create Space Race main tab.
   * @param listener Action Listener
   * @return InfoPanel
   */
  private InfoPanel createSpaceRaceMainTab(final ActionListener listener) {
    InfoPanel mainPanel = new InfoPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    InfoPanel infoPanel = new InfoPanel();
    infoPanel.setTitle("New space race");
    infoPanel.setLayout(new BoxLayout(infoPanel,
        BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Space Race name:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    spaceRaceNameField = new JTextField("Custom Terrans");
    spaceRaceNameField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    spaceRaceNameField.setForeground(GuiStatics.getCoolSpaceColor());
    spaceRaceNameField.setFont(GuiFonts.getFontCubellanSmaller());
    spaceRaceNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    spaceRaceNameField.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        String tmp = getEditedSpaceRaceId();
        conflictWithId = false;
        for (SpaceRace race : SpaceRaceFactory.getValues()) {
          if (race.getId().equals(tmp)) {
            conflictWithId = true;
          }
        }
        if (conflictWithId) {
          spaceRaceNameField.setForeground(GuiStatics.COLOR_RED_TEXT);
        } else {
          spaceRaceNameField.setForeground(GuiStatics.getCoolSpaceColor());
        }
        spaceRaceNameField.repaint();
      }

      @Override
      public void keyPressed(final KeyEvent e) {
        // Nothing to do here
      }
    });
    infoPanel.add(spaceRaceNameField);
    infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    label = new SpaceLabel("Space Race name in single:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    spaceRaceNameSingleField = new JTextField("Custom Terran");
    spaceRaceNameSingleField.setBackground(GuiStatics.getDeepSpaceDarkColor());
    spaceRaceNameSingleField.setForeground(GuiStatics.getCoolSpaceColor());
    spaceRaceNameSingleField.setFont(GuiFonts.getFontCubellanSmaller());
    spaceRaceNameSingleField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));

    label = new SpaceLabel("Space ship bridge:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(label);
    infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
    bridgeIdCombo = new SpaceComboBox<>(RulerSelection.values());
    bridgeIdCombo.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    bridgeIdCombo.setForeground(
        GuiStatics.getCoolSpaceColor());
    bridgeIdCombo.setBorder(new SimpleBorder());
    bridgeIdCombo.setFont(GuiFonts.getFontCubellan());
    bridgeIdCombo.getModel()
        .setSelectedItem(RulerSelection.AI_RULER);
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    bridgeIdCombo.setRenderer(dlcr);
    bridgeIdCombo.addActionListener(listener);
    bridgeIdCombo.setActionCommand(
        GameCommands.COMMAND_GOVERNMENT_EDITOR_RULER_SELECT);
    infoPanel.add(bridgeIdCombo);
    infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));


    mainPanel.add(infoPanel);
    int screenWidth = 1024;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      screenWidth = game.getWidth();
    }
    traitPanel = new TraitPanel(screenWidth,
        TraitFactory.getAll(), listener);
    mainPanel.add(traitPanel);
    return mainPanel;
  }
  /**
   * Get Government ID
   * @return GovernmentId.
   */
  private String getEditedSpaceRaceId() {
    String tmp = spaceRaceNameField.getText().toUpperCase().trim();
    return tmp.replaceAll(" ", "_");
  }

  /**
   * Is conflict with government ID.
   * @return true or false
   */
  public boolean isConflictWithId() {
    return conflictWithId;
  }
  /**
   * Build Json file based on government editor
   * @return String as a JSON.
   */
  public String buildJson() {
    StringBuilder sb = new StringBuilder();
    sb.append("[\n");
    sb.append("  {\n");
    sb.append("    \"ID\": \"");
    sb.append(getEditedSpaceRaceId());
    sb.append("\",\n");
    sb.append("    \"Name\": \"");
    sb.append(spaceRaceNameField.getText());
    sb.append("\",\n");
    sb.append("    \"RulerSelection\": \"");
    RulerSelection rulerSelect = (RulerSelection) bridgeIdCombo
        .getSelectedItem();
    sb.append(rulerSelect.getAsString());
    sb.append("\",\n");
    sb.append("    \"RulerTitleMale\": \"");
    sb.append(rulerTitleMaleField.getText());
    sb.append("\",\n");
    sb.append("    \"RulerTitleFemale\": \"");
    sb.append(rulerTitleFemaleField.getText());
    sb.append("\",\n");
    sb.append("    \"Traits\": [\n");
    boolean notFirst = false;
    for (TraitCheckBox box : traitPanel.getAllBoxes()) {
      if (box.isSelected()) {
        if (notFirst) {
          sb.append(",\n");
        }
        sb.append("      \"");
        sb.append(box.getTraitId());
        sb.append("\"");
        notFirst = true;
      }
    }
    sb.append("\n");
    sb.append("     ]\n");
    sb.append("  }\n");
    sb.append("]\n");
    return sb.toString();
  }
  /**
   * Handle actions for Government editor
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().startsWith(
        GameCommands.COMMAND_GOV_TRAIT_SELECTED)) {
      String[] param = arg0.getActionCommand().split("\\+");
      if (param.length == 2) {
        traitPanel.handleTraitSelection(param[1]);
      }
      SoundPlayer.playMenuSound();
    }
  }
}
