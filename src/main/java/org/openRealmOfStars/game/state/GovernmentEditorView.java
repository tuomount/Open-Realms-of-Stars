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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
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
import org.openRealmOfStars.player.government.Government;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.government.GovernmentLoader;
import org.openRealmOfStars.player.government.RulerSelection;
import org.openRealmOfStars.player.government.trait.GovTrait;
import org.openRealmOfStars.player.government.trait.GovTraitFactory;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.Folders;

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
    governmentNameField.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        String tmp = getEditedGovernmentId();
        conflictWithId = false;
        for (Government gov : GovernmentFactory.getValues()) {
          if (gov.getId().equals(tmp)) {
            conflictWithId = true;
          }
        }
        if (conflictWithId) {
          governmentNameField.setForeground(GuiStatics.COLOR_RED_TEXT);
        } else {
          governmentNameField.setForeground(GuiStatics.getCoolSpaceColor());
        }
        governmentNameField.repaint();
      }

      @Override
      public void keyPressed(final KeyEvent e) {
        // Nothing to do here
      }
    });
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
    int screenWidth = 1024;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      screenWidth = game.getWidth();
    }
    traitPanel = new TraitPanel(screenWidth,
        GovTraitFactory.getAll(), listener);
    mainPanel.add(traitPanel);
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
   * Get Government ID
   * @return GovernmentId.
   */
  private String getEditedGovernmentId() {
    String tmp = governmentNameField.getText().toUpperCase().trim();
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
    sb.append(getEditedGovernmentId());
    sb.append("\",\n");
    sb.append("    \"Name\": \"");
    sb.append(governmentNameField.getText());
    sb.append("\",\n");
    sb.append("    \"RulerSelection\": \"");
    RulerSelection rulerSelect = (RulerSelection) rulerSelectionCombo
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
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SAVE_GOVERNMENT)) {
      JFileChooser saveFileChooser = new JFileChooser(
          new File(Folders.getCustomGovPath()));
      saveFileChooser.setFileFilter(new FileNameExtensionFilter(
          "JSON Data file", "json"));
      String fileName = getEditedGovernmentId().toLowerCase() + ".json";
      saveFileChooser.setSelectedFile(
          new File(Folders.getCustomGovPath() + "/" + fileName));
      saveFileChooser.setApproveButtonText("Save");
      saveFileChooser.setDialogTitle("Save government file");
      int returnValue = saveFileChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = saveFileChooser.getSelectedFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
          fos.write(buildJson().getBytes(StandardCharsets.UTF_8));
          fos.flush();
        } catch (FileNotFoundException e) {
          ErrorLogger.log(e);
        } catch (IOException e) {
          ErrorLogger.log(e);
        }
        SoundPlayer.playMenuSound();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_LOAD_GOVERNMENT)) {
      JFileChooser loadFileChooser = new JFileChooser(
          new File(Folders.getCustomGovPath()));
      loadFileChooser.setFileFilter(new FileNameExtensionFilter(
          "JSON Data file", "json"));
      String fileName = getEditedGovernmentId().toLowerCase() + ".json";
      loadFileChooser.setSelectedFile(
          new File(Folders.getCustomGovPath() + "/" + fileName));
      loadFileChooser.setApproveButtonText("Load");
      loadFileChooser.setDialogTitle("Load government file");
      int returnValue = loadFileChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        GovernmentLoader loader = new GovernmentLoader();
        File file = loadFileChooser.getSelectedFile();
        try (FileInputStream fis = new FileInputStream(file)) {
          JSONArray jsonArray = new JSONArray(new JSONTokener(fis));

          for (var obj : jsonArray) {
            if (!(obj instanceof JSONObject)) {
              ErrorLogger.log("Malformed JSON file: " + file.getAbsolutePath());
            }
            var governmentOpt = loader.parseFromJson((JSONObject) obj);
            if (governmentOpt.isEmpty()) {
              ErrorLogger.log("Malformed JSON file: " + file.getAbsolutePath());
            } else {
              Government gov = governmentOpt.get();
              governmentNameField.setText(gov.getName());
              rulerSelectionCombo.setSelectedItem(gov.getRulerSelection());
              rulerTitleMaleField.setText(gov.getRulerTitleMale());
              rulerTitleFemaleField.setText(gov.getRulerTitleFemale());
              traitPanel.uncheckAllTraits();
              for (GovTrait trait : gov.getTraits()) {
                traitPanel.checkTrait(trait.getId());
                traitPanel.handleTraitSelection(trait.getId());
              }
            }
          }
        } catch (IOException e) {
          ErrorLogger.log("Failed reading Government: " + e);
        }
        SoundPlayer.playMenuSound();
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
  }
}
