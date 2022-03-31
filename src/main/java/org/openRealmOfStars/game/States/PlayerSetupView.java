package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.PlayerColorListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.government.GovernmentUtility;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
 * Player setup view for Open Realm of Stars
 *
 */
public class PlayerSetupView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * ComboBox on race
   */
  private SpaceComboBox<String>[] comboRaceSelect;

  /**
   * ComboBox on government
   */
  private SpaceComboBox<GovernmentType>[] comboGovernmentSelect;

  /**
   * Checkbox for elder realm
   */
  private SpaceCheckBox[] checkElderRealm;

  /**
   * Player name
   */
  private JTextField[] playerName;

  /**
   * Race Images
   */
  private RaceImagePanel[] raceImgs;

  /**
   * Combobox for selecting realm color
   */
  private SpaceComboBox<PlayerColor>[] comboRealmColor;
  /**
   * Combobox for selecting difficulty for each realm.
   */
  private SpaceComboBox<AiDifficulty>[] comboDifficult;
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
  /**
   * Constructor for Player Setup view
   * @param config Galaxy Configuration
   * @param listener ActionListener
   */
  @SuppressWarnings("unchecked")
  public PlayerSetupView(final GalaxyConfig config,
      final ActionListener listener) {
    this.actionListener = listener;
    this.config = config;
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

    comboRaceSelect = new SpaceComboBox[StarMap.MAX_PLAYERS];
    comboGovernmentSelect = new SpaceComboBox[StarMap.MAX_PLAYERS];
    comboDifficult = new SpaceComboBox[StarMap.MAX_PLAYERS];
    checkElderRealm = new SpaceCheckBox[StarMap.MAX_PLAYERS];
    raceImgs = new RaceImagePanel[StarMap.MAX_PLAYERS];
    playerName = new JTextField[StarMap.MAX_PLAYERS];
    comboRealmColor = new SpaceComboBox[StarMap.MAX_PLAYERS];

    SpaceGreyPanel xgrey = new SpaceGreyPanel();
    xgrey.setLayout(new GridLayout(4, 4));
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
      xgrey.add(createPlayerRaceSelection(xgrey, i, listener));
    }
    mainPanel.add(xgrey);
    mainPanel.add(Box.createRigidArea(new Dimension(500, 10)));

    JScrollPane scroll = new JScrollPane(mainPanel);
    scroll.getVerticalScrollBar().setUnitIncrement(20);
    imgBase.add(scroll, BorderLayout.CENTER);

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
   * Handle actions for Player Setup view
   * @param arg0 The event
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().startsWith(GameCommands.COMMAND_GALAXY_SETUP)) {
      SoundPlayer.playMenuSound();
      for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
        if (comboRaceSelect[i].isEnabled()) {
          String raceStr = (String) comboRaceSelect[i].getSelectedItem();
          SpaceRace race = SpaceRaceUtility.getRaceByName(raceStr);
          comboRaceSelect[i].setToolTipText(race.getFullDescription(false,
              false));
          config.setRace(i, race);
          raceImgs[i].setRaceToShow(raceStr);
        }
      }
      String tmp = arg0.getActionCommand().substring(
          GameCommands.COMMAND_GALAXY_SETUP.length(),
          arg0.getActionCommand().length());
      int index = Integer.parseInt(tmp);
      comboGovernmentSelect[index].removeActionListener(actionListener);
      comboGovernmentSelect[index].removeAllItems();
      SpaceRace race = config.getRace(index);
      GovernmentType[] govs = GovernmentUtility.getGovernmentsForRace(race);
      for (GovernmentType gov : govs) {
        comboGovernmentSelect[index].addItem(gov);
      }
      config.setPlayerGovernment(index,
          GovernmentUtility.getRandomGovernment(race));
      comboGovernmentSelect[index].setSelectedItem(
          config.getPlayerGovernment(index));
      comboGovernmentSelect[index].setToolTipText(
          config.getPlayerGovernment(index).getDescription(false));
      playerName[index].setText(SpaceRaceUtility.getRandomName(race,
          config.getPlayerGovernment(index)));
      comboGovernmentSelect[index].addActionListener(actionListener);
    }
    if (arg0.getActionCommand().startsWith(
        GameCommands.COMMAND_GOVERNMENT_SETUP)) {
      SoundPlayer.playMenuSound();
      for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
        if (comboRaceSelect[i].isEnabled()) {
          GovernmentType gov = (GovernmentType) comboGovernmentSelect[i]
              .getSelectedItem();
          if (gov != null) {
            comboGovernmentSelect[i].setToolTipText(
                gov.getDescription(false));
            config.setPlayerGovernment(i, gov);
          }
        }
      }
      String tmp = arg0.getActionCommand().substring(
          GameCommands.COMMAND_GOVERNMENT_SETUP.length(),
          arg0.getActionCommand().length());
      int index = Integer.parseInt(tmp);
      String raceStr = (String) comboRaceSelect[index].getSelectedItem();
      SpaceRace race = SpaceRaceUtility.getRaceByName(raceStr);
      GovernmentType gov = config.getPlayerGovernment(index);
      playerName[index].setText(SpaceRaceUtility.getRandomName(race, gov));
    }
    if (arg0.getActionCommand().startsWith(
        GameCommands.COMMAND_DIFFICULT_SETUP)) {
      SoundPlayer.playMenuSound();
      for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
        if (comboRaceSelect[i].isEnabled()) {
          AiDifficulty diff = (AiDifficulty) comboDifficult[i]
              .getSelectedItem();
          if (diff != null) {
            config.setPlayerDifficult(i, diff);
            comboDifficult[i].setToolTipText(diff.getDescription());
          }
        }
      }
    }
    if (arg0.getActionCommand().startsWith(
        GameCommands.COMMAND_COLOR_SETUP)) {
      SoundPlayer.playMenuSound();
      for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
        if (comboRaceSelect[i].isEnabled()) {
          PlayerColor color = (PlayerColor) comboRealmColor[i]
              .getSelectedItem();
          if (color != null) {
            config.setPlayerColor(i, color);
          }
        }
      }
      String tmp = arg0.getActionCommand().substring(
          GameCommands.COMMAND_COLOR_SETUP.length(),
          arg0.getActionCommand().length());
      int index = Integer.parseInt(tmp);
      PlayerColor color = (PlayerColor) comboRealmColor[index]
          .getSelectedItem();
      comboRealmColor[index].setForeground(color.getColor());
    }
  }

  /**
   * Get player name to game config
   */
  public void getNamesToConfig() {
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
      config.setPlayerName(i, playerName[i].getText());
      config.setPlayerElderRealm(i, checkElderRealm[i].isSelected());
    }

  }

  /**
   * Create Player config for one player
   * @param base The panel
   * @param index The player index
   * @param listener The action listener
   * @return panel with configuration components
   */
  private InvisiblePanel createPlayerRaceSelection(final SpaceGreyPanel base,
      final int index, final ActionListener listener) {
    InvisiblePanel xinvis = new InvisiblePanel(base);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(25, 25)));

    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    if (index == 0 && !config.isAiOnly()) {
      info.setTitle("Player " + (index + 1));
    } else {
      info.setTitle("Player " + (index + 1) + " (AI)");
    }
    raceImgs[index] = new RaceImagePanel();
    raceImgs[index].setRaceToShow(config.getRace(index).getNameSingle());
    info.add(raceImgs[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    comboRaceSelect[index] = new SpaceComboBox<>(
        SpaceRaceUtility.RACE_SELECTION);
    comboRaceSelect[index]
        .setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboRaceSelect[index].setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboRaceSelect[index].setBorder(new SimpleBorder());
    comboRaceSelect[index].setFont(GuiStatics.getFontCubellan());
    comboRaceSelect[index].getModel()
        .setSelectedItem(config.getRace(index).getNameSingle());
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboRaceSelect[index].setRenderer(dlcr);
    comboRaceSelect[index].addActionListener(listener);
    comboRaceSelect[index]
        .setActionCommand(GameCommands.COMMAND_GALAXY_SETUP + index);
    if (config.getMaxPlayers() < (index + 1)) {
      comboRaceSelect[index].setEnabled(false);
    }
    comboRaceSelect[index].setToolTipText(config.getRace(index)
        .getFullDescription(false, false));
    info.add(comboRaceSelect[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    EmptyInfoPanel info2 = new EmptyInfoPanel();
    info2.setLayout(new BoxLayout(info2, BoxLayout.X_AXIS));
    comboGovernmentSelect[index] = new SpaceComboBox<>(
        GovernmentUtility.getGovernmentsForRace(config.getRace(index)));
    comboGovernmentSelect[index].setBackground(
        GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboGovernmentSelect[index].setForeground(
        GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboGovernmentSelect[index].setBorder(new SimpleBorder());
    comboGovernmentSelect[index].setFont(GuiStatics.getFontCubellan());
    comboGovernmentSelect[index].getModel()
        .setSelectedItem(config.getRace(index).getNameSingle());
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboGovernmentSelect[index].setRenderer(dlcr);
    comboGovernmentSelect[index].addActionListener(listener);
    comboGovernmentSelect[index]
        .setActionCommand(GameCommands.COMMAND_GOVERNMENT_SETUP + index);
    if (config.getMaxPlayers() < (index + 1)) {
      comboGovernmentSelect[index].setEnabled(false);
    }
    comboGovernmentSelect[index].setSelectedItem(
        config.getPlayerGovernment(index));
    int i = comboGovernmentSelect[index].getSelectedIndex();
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        config.getRace(index));
    comboGovernmentSelect[index].setToolTipText(
        governments[i].getDescription(false));
    info2.add(comboGovernmentSelect[index]);
    info2.add(Box.createRigidArea(new Dimension(5, 5)));
    checkElderRealm[index] = new SpaceCheckBox("");
    checkElderRealm[index].setType(SpaceCheckBox.CHECKBOX_TYPE_ELDER);
    checkElderRealm[index].setToolTipText("<html>Select rune to mark Realm"
        + " as an elder realm.<br> This will allow realm head"
        + " start and will make realm more stronger than others.<br>"
        + "Elder realms are played by AI for amount of head start.</html>");
    info2.add(checkElderRealm[index]);
    info.add(info2);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    playerName[index] = new JTextField(
        "Empire of " + config.getRace(index).getName());
    playerName[index].setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    playerName[index].setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    playerName[index].setFont(GuiStatics.getFontCubellanSmaller());
    playerName[index].setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    if (config.getMaxPlayers() < (index + 1)) {
      playerName[index].setEnabled(false);
      raceImgs[index].setRaceToShow(null);
    } else {
      playerName[index].setText(config.getPlayerName(index));
      comboRaceSelect[index].setToolTipText(config.getRace(index)
          .getFullDescription(false, false));
    }
    info.add(playerName[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    comboDifficult[index] = new SpaceComboBox<>(AiDifficulty.values());
    comboDifficult[index].setSelectedIndex(
        config.getDifficultyLevel().getIndex());
    comboDifficult[index].setBackground(
        GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboDifficult[index].setForeground(
        GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboDifficult[index].setFont(GuiStatics.getFontCubellanSmaller());
    comboDifficult[index].setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    comboDifficult[index].setActionCommand(
        GameCommands.COMMAND_DIFFICULT_SETUP);
    comboDifficult[index].addActionListener(listener);
    if (index == 0 && !config.isAiOnly()) {
      comboDifficult[index].setEnabled(false);
      comboDifficult[index].setToolTipText("");
    }
    info.add(comboDifficult[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    comboRealmColor[index] = new SpaceComboBox<>(
        PlayerColor.values());
    comboRealmColor[index]
        .setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboRealmColor[index].setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboRealmColor[index].setBorder(new SimpleBorder());
    comboRealmColor[index].setFont(GuiStatics.getFontCubellan());
    PlayerColor color = config.getRace(index).getPrimaryColor();
    if (!randomListOfColors.contains(color)) {
      color = config.getRace(index).getSecondaryColor();
      if (!randomListOfColors.contains(color)) {
        int colorIndex = DiceGenerator.getRandom(randomListOfColors.size() - 1);
        color = randomListOfColors.get(colorIndex);
      }
    }
    randomListOfColors.remove(color);
    comboRealmColor[index].getModel()
        .setSelectedItem(color);
    PlayerColorListRenderer pclr = new PlayerColorListRenderer();
    comboRealmColor[index].setForeground(color.getColor());
    comboRealmColor[index].setRenderer(pclr);
    comboRealmColor[index].addActionListener(listener);
    comboRealmColor[index]
        .setActionCommand(GameCommands.COMMAND_COLOR_SETUP + index);
    comboRealmColor[index].setToolTipText("<html>Realm color in map and"
        + " statistics.</html>");
    info.add(comboRealmColor[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(25, 25)));
    return xinvis;
  }

  /**
   * Get galaxy and player for of player setup.
   * @return Galaxy and player configuration
   */
  public GalaxyConfig getConfig() {
    return config;
  }

}
