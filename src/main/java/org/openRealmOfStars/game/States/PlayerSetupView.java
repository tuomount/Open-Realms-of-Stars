package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
   * List of selectable races
   */
  private static final String[] RACE_SELECTION = {"Human", "Mechion", "Spork",
      "Greyan", "Centaur" };

  /**
   * ComboBox on galaxy size
   */
  private JComboBox<String>[] comboRaceSelect;

  /**
   * Player name
   */
  private JTextField[] playerName;

  /**
   * Race Images
   */
  private RaceImagePanel[] raceImgs;

  /**
   * Galaxy config
   */
  private GalaxyConfig config;

  /**
   * Constructor for Player Setup view
   * @param config Galaxy Configuration
   * @param listener ActionListener
   */
  @SuppressWarnings("unchecked")
  public PlayerSetupView(final GalaxyConfig config,
      final ActionListener listener) {
    this.config = config;
    if (this.config == null) {
      this.config = new GalaxyConfig();
    }
    Planet planet = new Planet(new Coordinate(1, 1), "Galaxy Creation Planet",
        2, false);
    if (DiceGenerator.getRandom(100) < 10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(
          DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length - 1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Player Setup");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 100)));

    comboRaceSelect = new JComboBox[StarMap.MAX_PLAYERS];
    raceImgs = new RaceImagePanel[StarMap.MAX_PLAYERS];
    playerName = new JTextField[StarMap.MAX_PLAYERS];

    InvisiblePanel xinvis = new InvisiblePanel(invisible);
    xinvis.setLayout(new GridLayout(2, 4));
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
      xinvis.add(createPlayerRaceSelection(xinvis, i, listener));
    }
    invisible.add(xinvis);
    invisible.add(Box.createRigidArea(new Dimension(200, 150)));

    imgBase.add(invisible, BorderLayout.CENTER);

    invisible = new InvisiblePanel(imgBase);
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
      for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
        if (comboRaceSelect[i].isEnabled()) {
          String raceStr = (String) comboRaceSelect[i].getSelectedItem();
          SpaceRace race = SpaceRaceUtility.getRaceByName(raceStr);
          config.setRace(i, race);
          raceImgs[i].setRaceToShow(raceStr);
        }
      }
      String tmp = arg0.getActionCommand().substring(
          GameCommands.COMMAND_GALAXY_SETUP.length(),
          arg0.getActionCommand().length());
      int index = Integer.parseInt(tmp);
      playerName[index].setText(SpaceRaceUtility.getRandomName(
          config.getRace(index)));
    }
  }

  /**
   * Get player name to game config
   */
  public void getNamesToConfig() {
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
      config.setPlayerName(i, playerName[i].getText());
    }

  }

  /**
   * Create Player config for one player
   * @param base The panel
   * @param index The player index
   * @param listener The action listener
   * @return InvisiblePanel with configuration components
   */
  private InvisiblePanel createPlayerRaceSelection(final InvisiblePanel base,
      final int index, final ActionListener listener) {
    InvisiblePanel xinvis = new InvisiblePanel(base);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(25, 25)));

    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    if (index == 0) {
      info.setTitle("Player " + (index + 1));
    } else {
      info.setTitle("Player " + (index + 1) + " (AI)");
    }
    raceImgs[index] = new RaceImagePanel();
    raceImgs[index].setRaceToShow(config.getRace(index).getNameSingle());
    info.add(raceImgs[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    comboRaceSelect[index] = new JComboBox<>(RACE_SELECTION);
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
    info.add(comboRaceSelect[index]);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    playerName[index] = new JTextField(
        "Empire of " + config.getRace(index).getName());
    playerName[index].setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    playerName[index].setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    playerName[index].setFont(GuiStatics.getFontCubellanSmaller());
    if (config.getMaxPlayers() < (index + 1)) {
      playerName[index].setEnabled(false);
      raceImgs[index].setRaceToShow(null);
    } else {
      playerName[index].setText(config.getPlayerName(index));
    }
    info.add(playerName[index]);
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
