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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.HyperLabel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.list.PlayerColorListRenderer;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.government.Government;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.race.SpaceRaceUtility;
import org.openRealmOfStars.player.scenario.ScenarioIds;
import org.openRealmOfStars.player.scenario.StartingScenario;
import org.openRealmOfStars.player.scenario.StartingScenarioFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;


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
  private SpaceComboBox<Government> comboGovernmentSelect;

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

  /** Flag for allow changing realm */
  private boolean allowChangingRealm;

  /** Realm Index. */
  private int realmIndex;

  /** Info text for space race. */
  private HyperLabel spaceRaceInfo;
  /**
   * Info panel for Space race.
   */
  private InfoPanel infoPanelForSpaceRace;
  /** Info text for government. */
  private HyperLabel governmentInfo;
  /**
   * Size has been adjusted.
   */
  private boolean sizeAdjusted;

  /**
   * Full panel with title
   */
  private InfoPanel fullPanel;
  /**
   * Rigid box size
   */
  private int rigidSize = 10;

  /**
   * Constructor for realm setup view.
   * @param config GalaxyConfig
   * @param listener ActionListener, should be the game.
   * @param allowChangeRealm Allow changing realms in view.
   */
  public RealmSetupView(final GalaxyConfig config,
      final ActionListener listener, final boolean allowChangeRealm) {
    sizeAdjusted = false;
    this.config = config;
    this.actionListener = listener;
    rigidSize = 15;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      if (game.getHeight() < 960) {
        rigidSize = 5;
      }
    }
    this.allowChangingRealm = allowChangeRealm;
    realmIndex = 0;
    if (allowChangingRealm) {
      realmIndex = 1;
    }
    if (this.config == null) {
      this.config = new GalaxyConfig();
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
    changeRealmIndex(realmIndex);
  }

  /**
   * Setup realm info to config.
   */
  public void setupRealmConfig() {
    String raceStr = (String) comboRaceSelect.getSelectedItem();
    SpaceRace race = SpaceRaceUtility.getRaceByName(raceStr);
    if (race != null) {
      config.setRace(realmIndex, race);
    }
    Government gov = (Government) comboGovernmentSelect
        .getSelectedItem();
    config.setPlayerGovernment(realmIndex, gov);
    config.setPlayerName(realmIndex, realmName.getText());
    PlayerColor color = (PlayerColor) comboRealmColor.getSelectedItem();
    config.setPlayerColor(realmIndex, color);
    config.setPlayerElderRealm(realmIndex, checkElderRealm.isSelected());
    AiDifficulty difficulty = (AiDifficulty) comboDifficult.getSelectedItem();
    if (difficulty != null) {
      config.setPlayerDifficult(realmIndex, difficulty);
    }
    StartingScenario sce = (StartingScenario) comboScenario.getSelectedItem();
    config.setStartingScenario(realmIndex, sce);
  }
  /**
   * Is Allow change state? Allow change is after AI Realm, it is substate.
   * @return True if allow change.
   */
  public boolean isAllowChange() {
    return allowChangingRealm;
  }
  /**
   * Handle actions for Player Setup view
   * @param arg0 The event
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)
        && !sizeAdjusted) {
      adjustInfoTextSizes();
    }

    if (arg0.getActionCommand().equals(GameCommands.COMMAND_GALAXY_SETUP)) {
      SoundPlayer.playMenuSound();
      String raceStr = (String) comboRaceSelect.getSelectedItem();
      SpaceRace race = SpaceRaceUtility.getRaceByName(raceStr);
      config.setRace(realmIndex, race);
      raceImgs.setRaceToShow(raceStr);
      spaceRaceInfo.setText(race.getFullDescription(false, false));
      config.setPlayerGovernment(realmIndex,
          GovernmentFactory.getRandomGovernment());
      comboGovernmentSelect.setSelectedItem(
          config.getPlayerGovernment(realmIndex));
      governmentInfo.setText(
          config.getPlayerGovernment(realmIndex).getDescription(false));
      realmName.setText(SpaceRaceUtility.getRealmName(race,
          config.getPlayerGovernment(realmIndex)));
      adjustInfoTextSizes();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_GOVERNMENT_SETUP)) {
      SoundPlayer.playMenuSound();
      Government gov = (Government) comboGovernmentSelect
          .getSelectedItem();
      if (gov != null) {
        governmentInfo.setText(gov.getDescription(false));
        config.setPlayerGovernment(realmIndex, gov);
      }
      String raceStr = (String) comboRaceSelect.getSelectedItem();
      SpaceRace race = SpaceRaceUtility.getRaceByName(raceStr);
      realmName.setText(SpaceRaceUtility.getRealmName(race, gov));
      adjustInfoTextSizes();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_DIFFICULT_SETUP)) {
      SoundPlayer.playMenuSound();
      AiDifficulty difficulty = (AiDifficulty) comboDifficult.getSelectedItem();
      if (difficulty != null) {
        config.setPlayerDifficult(realmIndex, difficulty);
      }
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_COLOR_SETUP)) {
      SoundPlayer.playMenuSound();
      PlayerColor color = (PlayerColor) comboRealmColor.getSelectedItem();
      if (color != null) {
        config.setPlayerColor(realmIndex, color);
      }
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_GALAXY_PREV_REALM)) {
      SoundPlayer.playMenuSound();
      setupRealmConfig();
      realmIndex--;
      if (realmIndex <= 0) {
        realmIndex = config.getMaxPlayers() - 1;
      }
      changeRealmIndex(realmIndex);
      fullPanel.repaint();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_GALAXY_NEXT_REALM)) {
      SoundPlayer.playMenuSound();
      setupRealmConfig();
      realmIndex++;
      if (realmIndex >= config.getMaxPlayers()) {
        realmIndex = 1;
      }
      changeRealmIndex(realmIndex);
      fullPanel.repaint();
    }
  }
  /**
   * Create Realm config for one realm
   * @param index The player index
   * @param listener The action listener
   * @return panel with configuration components
   */
  private InfoPanel createRealmSetup(final int index,
      final ActionListener listener) {
    fullPanel = new InfoPanel();
    fullPanel.setLayout(new BorderLayout());
    if (index == 0 && !config.isAiOnly()) {
      fullPanel.setTitle("Player " + (index + 1));
    } else {
      fullPanel.setTitle("Player " + (index + 1) + " (AI)");
    }
    SpaceGreyPanel xinvis = new SpaceGreyPanel();
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(10, 10)));

    SpaceGreyPanel westPanel = new SpaceGreyPanel();
    westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
    westPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    SpaceGreyPanel topPanel = new SpaceGreyPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

    if (allowChangingRealm) {
      IconButton btn = new IconButton(GuiStatics.getArrowLeft(),
          GuiStatics.getArrowLeftPressed(), false,
          GameCommands.COMMAND_GALAXY_PREV_REALM, topPanel);
      btn.addActionListener(listener);
      topPanel.add(btn);
    }
    raceImgs = new RaceImagePanel();
    raceImgs.setRaceToShow(config.getRace(index).getNameSingle());
    topPanel.add(raceImgs);
    if (allowChangingRealm) {
      IconButton btn = new IconButton(GuiStatics.getArrowRight(),
          GuiStatics.getArrowRightPressed(), false,
          GameCommands.COMMAND_GALAXY_NEXT_REALM, topPanel);
      btn.addActionListener(listener);
      topPanel.add(btn);
    }
    SpaceLabel label = new SpaceLabel("Space Race:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
    comboRaceSelect = new SpaceComboBox<>(
        SpaceRaceFactory.getNames());
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
    comboRaceSelect.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    if (config.getMaxPlayers() < (index + 1)) {
      comboRaceSelect.setEnabled(false);
    }
    westPanel.add(comboRaceSelect);
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    label = new SpaceLabel("Realm Government:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
    comboGovernmentSelect = new SpaceComboBox<>(GovernmentFactory.getValues());
    comboGovernmentSelect.setBackground(
        GuiStatics.getDeepSpaceDarkColor());
    comboGovernmentSelect.setForeground(
        GuiStatics.getCoolSpaceColor());
    comboGovernmentSelect.setBorder(new SimpleBorder());
    comboGovernmentSelect.setFont(GuiFonts.getFontCubellan());
    comboGovernmentSelect.getModel()
        .setSelectedItem(config.getPlayerGovernment(index));
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboGovernmentSelect.setRenderer(dlcr);
    comboGovernmentSelect.addActionListener(listener);
    comboGovernmentSelect.setActionCommand(
        GameCommands.COMMAND_GOVERNMENT_SETUP);
    westPanel.add(comboGovernmentSelect);
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    label = new SpaceLabel("Ancient Realm:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
    checkElderRealm = new SpaceCheckBox("");
    checkElderRealm.setType(SpaceCheckBox.CHECKBOX_TYPE_ELDER);
    checkElderRealm.setToolTipText("<html>Select rune to mark Realm"
        + " as an elder realm.<br> This will allow realm head"
        + " start and will make realm more stronger than others.<br>"
        + "Elder realms are played by AI for amount of head start.</html>");
    westPanel.add(checkElderRealm);
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    label = new SpaceLabel("Realm name:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
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
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    label = new SpaceLabel("Realm color:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
    comboRealmColor = new SpaceComboBox<>(
        PlayerColor.values());
    comboRealmColor
        .setBackground(GuiStatics.getDeepSpaceDarkColor());
    comboRealmColor.setForeground(GuiStatics.getCoolSpaceColor());
    comboRealmColor.setBorder(new SimpleBorder());
    comboRealmColor.setFont(GuiFonts.getFontCubellan());

    PlayerColor color = config.getPlayerColor(index);
    comboRealmColor.getModel()
        .setSelectedItem(color);
    PlayerColorListRenderer pclr = new PlayerColorListRenderer();
    comboRealmColor.setForeground(color.getColor());
    comboRealmColor.setRenderer(pclr);
    comboRealmColor.addActionListener(listener);
    comboRealmColor.setActionCommand(GameCommands.COMMAND_COLOR_SETUP);
    comboRealmColor.setToolTipText("<html>Realm color in map and"
        + " statistics.</html>");
    westPanel.add(comboRealmColor);
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    label = new SpaceLabel("Realm's starting scenario:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
    int numberOfScenarios = StartingScenarioFactory.getValues().length + 1;
    boolean metalPlanetNotAllowed = false;
    if (config.getScoreLimitResearch() == 1) {
      numberOfScenarios--;
      metalPlanetNotAllowed = true;
    }
    StartingScenario[] scenarioList = new StartingScenario[numberOfScenarios];
    scenarioList[0] = StartingScenarioFactory.createRandom();
    int j = 0;
    for (StartingScenario scenario : StartingScenarioFactory.getSorted()) {
      if (!metalPlanetNotAllowed
          || !scenario.getId().equals(ScenarioIds.METAL_PLANET)
          && metalPlanetNotAllowed) {
        j++;
        scenarioList[j] = scenario;
      }
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
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    label = new SpaceLabel("AI Difficulty:");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    westPanel.add(label);
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
    westPanel.add(Box.createRigidArea(new Dimension(rigidSize, rigidSize)));
    xinvis.add(westPanel);
    xinvis.add(Box.createRigidArea(new Dimension(10, 10)));
    fullPanel.add(xinvis, BorderLayout.WEST);
    fullPanel.add(topPanel, BorderLayout.NORTH);
    EmptyInfoPanel panelX = new EmptyInfoPanel();
    panelX.setLayout(new GridLayout(0, 1));
    infoPanelForSpaceRace = new InfoPanel();
    infoPanelForSpaceRace.setTitle("Racial information");
    infoPanelForSpaceRace.setLayout(new BorderLayout());
    infoPanelForSpaceRace.setPreferredSize(new Dimension(900, 30));
    infoPanelForSpaceRace.setMaximumSize(new Dimension(900, 30));
    JScrollPane scroll = new JScrollPane(infoPanelForSpaceRace);
    spaceRaceInfo = new HyperLabel(
        SpaceRaceFactory.getRandomRace().getFullDescription(false, false));
    spaceRaceInfo.setFont(GuiFonts.getFontCubellanSmaller());
    Dimension dim = new Dimension(infoPanelForSpaceRace.getWidth(),
        spaceRaceInfo.getAdjustHeight());
    infoPanelForSpaceRace.setPreferredSize(dim);
    infoPanelForSpaceRace.setMaximumSize(dim);
    infoPanelForSpaceRace.add(spaceRaceInfo, BorderLayout.CENTER);
    panelX.add(scroll);
    InfoPanel info = new InfoPanel();
    info.setTitle("Government information");
    info.setLayout(new BorderLayout());
    governmentInfo = new HyperLabel(
        GovernmentFactory.getRandomGovernment().getDescription(false));
    governmentInfo.setFont(GuiFonts.getFontCubellanSmaller());
    info.add(governmentInfo, BorderLayout.CENTER);
    panelX.add(info);
    fullPanel.add(panelX, BorderLayout.CENTER);
    return fullPanel;
  }

  /**
   * Adjust info text sizes.
   */
  private void adjustInfoTextSizes() {
    Dimension dim = new Dimension(infoPanelForSpaceRace.getWidth(),
        spaceRaceInfo.getAdjustHeight());
    infoPanelForSpaceRace.setPreferredSize(dim);
    infoPanelForSpaceRace.setMaximumSize(dim);
    sizeAdjusted = true;
  }

  /**
   * Change realm index
   * @param index new index.
   */
  private void changeRealmIndex(final int index) {
    if (index == 0 && !config.isAiOnly()) {
      fullPanel.setTitle("Player " + (index + 1));
    } else {
      fullPanel.setTitle("Player " + (index + 1) + " (AI)");
    }
    raceImgs.setRaceToShow(config.getRace(index).getNameSingle());
    comboRaceSelect.getModel()
    .setSelectedItem(config.getRace(index).getNameSingle());
    comboGovernmentSelect.getModel()
    .setSelectedItem(config.getPlayerGovernment(index));
    checkElderRealm.setSelected(config.getPlayerElderRealm(index));
    realmName.setText(config.getPlayerName(index));
    if (index > 0 || config.isAiOnly()) {
      comboDifficult.setEnabled(true);
      comboDifficult.setSelectedIndex(
          config.getDifficultyLevel().getIndex());
    } else {
      comboDifficult.setEnabled(false);
    }
    PlayerColor color = config.getPlayerColor(index);
    comboRealmColor.getModel().setSelectedItem(color);
    comboRealmColor.setForeground(color.getColor());
    comboScenario.getModel().setSelectedItem(config.getStartingScenario(index));
    spaceRaceInfo.setText(config.getRace(index).getFullDescription(false,
        false));
    Dimension dim = new Dimension(infoPanelForSpaceRace.getWidth(),
        spaceRaceInfo.getAdjustHeight());
    infoPanelForSpaceRace.setPreferredSize(dim);
    infoPanelForSpaceRace.setMaximumSize(dim);
    governmentInfo.setText(config.getPlayerGovernment(index)
        .getDescription(false));
  }
}
