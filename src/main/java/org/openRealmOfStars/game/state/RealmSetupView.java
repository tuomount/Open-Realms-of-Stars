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
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JTextField;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.list.PlayerColorListRenderer;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.race.SpaceRaceUtility;
import org.openRealmOfStars.player.scenario.StartingScenario;
import org.openRealmOfStars.player.scenario.StartingScenarioFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;


/**
 * Realm Setup view for single realm. Optionally view has arrow keys to change
 * to different realm.
 *
 */
public class RealmSetupView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * ComboBox on race
   */
  private SpaceComboBox<String> comboRaceSelect;

  /**
   * ComboBox on government
   */
  private SpaceComboBox<GovernmentType> comboGovernmentSelect;

  /**
   * Checkbox for elder realm
   */
  private SpaceCheckBox checkElderRealm;

  /**
   * Realm name
   */
  private JTextField realmName;

  /**
   * Race Images
   */
  private RaceImagePanel raceImgs;

  /**
   * Combobox for selecting realm color
   */
  private SpaceComboBox<PlayerColor> comboRealmColor;
  /**
   * Combobox for selecting difficulty for each realm.
   */
  private SpaceComboBox<AiDifficulty> comboDifficult;

  /**
   * Combobox for selecting starting scenario for each realm.
   */
  private SpaceComboBox<StartingScenario> comboScenario;

  /**
   * Galaxy config
   */
  private GalaxyConfig config;

  /**
   * Action Listener for combobox
   */
  private ActionListener actionListener;

  /**
   * Random list for colors.
   */
  private ArrayList<PlayerColor> randomListOfColors;

  /** Flag for allow changing realm */
  private boolean allowChangingRealm;

  /** Realm Index. */
  private int realmIndex;

  /** Info text for space race. */
  private InfoTextArea spaceRaceInfo;
  /** Info text for government. */
  private InfoTextArea governmentInfo;

  public RealmSetupView(final GalaxyConfig config,
      final ActionListener listener, final boolean allowChangeRealm) {
    this.config = config;
    this.actionListener = listener;
    this.allowChangingRealm = allowChangeRealm;
    realmIndex = 0;
    if (allowChangingRealm) {
      realmIndex = 1;
    }
    if (this.config == null) {
      this.config = new GalaxyConfig();
    }
    randomListOfColors = new ArrayList<>();
    for (PlayerColor color : PlayerColor.values()) {
      randomListOfColors.add(color);
    }
    Planet planet = new Planet(new Coordinate(1, 1), "Galaxy Creation Planet",
        2, false);
    planet.setPlanetType(PlanetTypes.getRandomPlanetType(true, true, true));
    if (planet.getPlanetType().isGasGiant()) {
      planet.setGasGiant(true);
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Realm Setup");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());
    imgBase.add(Box.createRigidArea(new Dimension(500, 100)),
        BorderLayout.NORTH);

    InfoPanel mainPanel = new InfoPanel();
    mainPanel.setTitle("Realm Setup");
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(Box.createRigidArea(new Dimension(500, 10)));
    mainPanel.add(createRealmSetup(realmIndex, actionListener));
    imgBase.add(mainPanel, BorderLayout.CENTER);

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("Cancel", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn, BorderLayout.WEST);
    btn = new SpaceButton("Next", GameCommands.COMMAND_NEXT);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn, BorderLayout.EAST);
    imgBase.add(invisible, BorderLayout.SOUTH);
    this.add(imgBase, BorderLayout.CENTER);
  }

  /**
   * Create Realm config for one realm
   * @param index The player index
   * @param listener The action listener
   * @return panel with configuration components
   */
  private InfoPanel createRealmSetup(final int index,
      final ActionListener listener) {
    InfoPanel fullPanel = new InfoPanel();
    fullPanel.setLayout(new BorderLayout());
    if (index == 0 && !config.isAiOnly()) {
      fullPanel.setTitle("Player " + (index + 1));
    } else {
      fullPanel.setTitle("Player " + (index + 1) + " (AI)");
    }
    InvisiblePanel xinvis = new InvisiblePanel(fullPanel);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(10, 10)));

    InvisiblePanel westPanel = new InvisiblePanel(fullPanel);
    westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
    westPanel.add(Box.createRigidArea(new Dimension(10, 10)));

    raceImgs = new RaceImagePanel();
    raceImgs.setRaceToShow(config.getRace(index).getNameSingle());
    westPanel.add(raceImgs);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    comboRaceSelect = new SpaceComboBox<>(
        SpaceRaceUtility.RACE_SELECTION);
    comboRaceSelect.setBackground(GuiStatics.getDeepSpaceDarkColor());
    comboRaceSelect.setForeground(GuiStatics.getCoolSpaceColor());
    comboRaceSelect.setBorder(new SimpleBorder());
    comboRaceSelect.setFont(GuiFonts.getFontCubellan());
    comboRaceSelect.getModel()
        .setSelectedItem(config.getRace(index).getNameSingle());
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboRaceSelect.setRenderer(dlcr);
    comboRaceSelect.addActionListener(listener);
    comboRaceSelect.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP + index);
    if (config.getMaxPlayers() < (index + 1)) {
      comboRaceSelect.setEnabled(false);
    }
    comboRaceSelect.setToolTipText(config.getRace(index)
        .getFullDescription(false, false));
    westPanel.add(comboRaceSelect);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    EmptyInfoPanel info2 = new EmptyInfoPanel();
    info2.setLayout(new BoxLayout(info2, BoxLayout.X_AXIS));
    comboGovernmentSelect = new SpaceComboBox<>(GovernmentType.values());
    comboGovernmentSelect.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    comboGovernmentSelect.setForeground(
        GuiStatics.getCoolSpaceColor());
    comboGovernmentSelect.setBorder(new SimpleBorder());
    comboGovernmentSelect.setFont(GuiFonts.getFontCubellan());
    comboGovernmentSelect.getModel()
        .setSelectedItem(config.getRace(index).getNameSingle());
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboGovernmentSelect.setRenderer(dlcr);
    comboGovernmentSelect.addActionListener(listener);
    comboGovernmentSelect.setActionCommand(
        GameCommands.COMMAND_GOVERNMENT_SETUP + index);
    if (config.getMaxPlayers() < (index + 1)) {
      comboGovernmentSelect.setEnabled(false);
    }
    comboGovernmentSelect.setSelectedItem(
        config.getPlayerGovernment(index));
    int i = comboGovernmentSelect.getSelectedIndex();
    GovernmentType[] governments = GovernmentType.values();
    comboGovernmentSelect.setToolTipText(
        governments[i].getDescription(false));
    info2.add(comboGovernmentSelect);
    info2.add(Box.createRigidArea(new Dimension(5, 5)));
    checkElderRealm = new SpaceCheckBox("");
    checkElderRealm.setType(SpaceCheckBox.CHECKBOX_TYPE_ELDER);
    checkElderRealm.setToolTipText("<html>Select rune to mark Realm"
        + " as an elder realm.<br> This will allow realm head"
        + " start and will make realm more stronger than others.<br>"
        + "Elder realms are played by AI for amount of head start.</html>");
    info2.add(checkElderRealm);
    westPanel.add(info2);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    realmName = new JTextField(
        "Empire of " + config.getRace(index).getName());
    realmName.setBackground(GuiStatics.getDeepSpaceDarkColor());
    realmName.setForeground(GuiStatics.getCoolSpaceColor());
    realmName.setFont(GuiFonts.getFontCubellanSmaller());
    realmName.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    if (config.getMaxPlayers() < (index + 1)) {
      realmName.setEnabled(false);
      raceImgs.setRaceToShow(null);
    } else {
      realmName.setText(config.getPlayerName(index));
      comboRaceSelect.setToolTipText(config.getRace(index)
          .getFullDescription(false, false));
    }
    westPanel.add(realmName);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    comboDifficult = new SpaceComboBox<>(AiDifficulty.values());
    comboDifficult.setSelectedIndex(
        config.getDifficultyLevel().getIndex());
    comboDifficult.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    comboDifficult.setForeground(
        GuiStatics.getCoolSpaceColor());
    comboDifficult.setFont(GuiFonts.getFontCubellanSmaller());
    comboDifficult.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    comboDifficult.setActionCommand(
        GameCommands.COMMAND_DIFFICULT_SETUP);
    comboDifficult.addActionListener(listener);
    if (index == 0 && !config.isAiOnly()) {
      comboDifficult.setEnabled(false);
      comboDifficult.setToolTipText("");
    }
    westPanel.add(comboDifficult);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    comboRealmColor = new SpaceComboBox<>(
        PlayerColor.values());
    comboRealmColor
        .setBackground(GuiStatics.getDeepSpaceDarkColor());
    comboRealmColor.setForeground(GuiStatics.getCoolSpaceColor());
    comboRealmColor.setBorder(new SimpleBorder());
    comboRealmColor.setFont(GuiFonts.getFontCubellan());

    PlayerColor color = DiceGenerator.pickRandom(randomListOfColors);
    randomListOfColors.remove(color);
    comboRealmColor.getModel()
        .setSelectedItem(color);
    PlayerColorListRenderer pclr = new PlayerColorListRenderer();
    comboRealmColor.setForeground(color.getColor());
    comboRealmColor.setRenderer(pclr);
    comboRealmColor.addActionListener(listener);
    comboRealmColor.setActionCommand(GameCommands.COMMAND_COLOR_SETUP + index);
    comboRealmColor.setToolTipText("<html>Realm color in map and"
        + " statistics.</html>");
    westPanel.add(comboRealmColor);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    StartingScenario[] scenarioList = new StartingScenario[
        StartingScenarioFactory.getValues().length + 1];
    scenarioList[0] = StartingScenarioFactory.createRandom();
    int j = 0;
    for (StartingScenario scenario : StartingScenarioFactory.getValues()) {
      j++;
      scenarioList[j] = scenario;
    }
    comboScenario = new SpaceComboBox<>(scenarioList);
    comboScenario.setBackground(GuiStatics.getDeepSpaceDarkColor());
    comboScenario.setForeground(GuiStatics.getCoolSpaceColor());
    comboScenario.setBorder(new SimpleBorder());
    comboScenario.setFont(GuiFonts.getFontCubellan());
    comboScenario.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    comboScenario.setActionCommand(
        GameCommands.COMMAND_SCENARIO_SETUP);
    comboScenario.addActionListener(listener);
    westPanel.add(comboScenario);
    westPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    xinvis.add(westPanel);
    xinvis.add(Box.createRigidArea(new Dimension(10, 10)));
    fullPanel.add(xinvis, BorderLayout.WEST);
    InvisiblePanel centerPanel = new InvisiblePanel(fullPanel);
    centerPanel.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    SpaceLabel label = new SpaceLabel("Space race info:");
    centerPanel.add(label);
    centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    spaceRaceInfo = new InfoTextArea();
    centerPanel.add(spaceRaceInfo);
    centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    label = new SpaceLabel("Government info:");
    centerPanel.add(label);
    centerPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    governmentInfo = new InfoTextArea();
    centerPanel.add(governmentInfo);
    fullPanel.add(centerPanel, BorderLayout.CENTER);
    return fullPanel;
  }
}
