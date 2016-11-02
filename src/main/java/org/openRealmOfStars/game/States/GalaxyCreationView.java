package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.starMap.GalaxyConfig;
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
 * Galaxy creation view for Open Realm of Stars
 *
 */
public class GalaxyCreationView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * ComboBox on galaxy size
   */
  private JComboBox<String> comboGalaxySize;

  /**
   * ComboBox on number of player
   */
  private JComboBox<String> comboPlayers;

  /**
   * ComboBox on player start positions
   */
  private JComboBox<String> comboPlayerPos;

  /**
   * ComboBox on sun density
   */
  private JComboBox<String> comboSunDensity;

  /**
   * Galaxy config
   */
  private GalaxyConfig config;

  /**
   * Constructor for Galaxy Creation View
   * @param config Galaxy Configuration
   * @param listener ActionListener
   */
  public GalaxyCreationView(final GalaxyConfig config,
      final ActionListener listener) {
    if (config == null) {
      this.config = new GalaxyConfig();
    } else {
      this.config = config;
    }
    Planet planet = new Planet(1, 1, "Galaxy Creation Planet", 1, false);
    if (DiceGenerator.getRandom(100) < 10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(
          DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length - 1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Galaxy Creation");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 250)));

    InvisiblePanel xinvis = new InvisiblePanel(invisible);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(200, 5)));
    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Galaxy Creation");
    TransparentLabel label = new TransparentLabel(info, "Galaxy size:");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] galaxySizes = new String[6];
    galaxySizes[0] = "Tiny 50x50";
    galaxySizes[1] = "Small 75x75";
    galaxySizes[2] = "Medium 128x128";
    galaxySizes[3] = "Large 160x160";
    galaxySizes[4] = "Very large 200x200";
    galaxySizes[5] = "Huge 256x256";
    comboGalaxySize = new JComboBox<>(galaxySizes);
    comboGalaxySize.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboGalaxySize.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboGalaxySize.setBorder(new SimpleBorder());
    comboGalaxySize.setFont(GuiStatics.getFontCubellan());
    comboGalaxySize.setSelectedIndex(this.config.getGalaxySizeIndex());
    comboGalaxySize.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboGalaxySize.setRenderer(dlcr);
    comboGalaxySize.addActionListener(listener);
    info.add(comboGalaxySize);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new TransparentLabel(info, "Sun density:");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] sunDensity = new String[4];
    sunDensity[0] = "Sparse";
    sunDensity[1] = "Medium";
    sunDensity[2] = "Dense";
    sunDensity[3] = "Overlap";
    comboSunDensity = new JComboBox<>(sunDensity);
    comboSunDensity.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboSunDensity.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboSunDensity.setBorder(new SimpleBorder());
    comboSunDensity.setFont(GuiStatics.getFontCubellan());
    comboSunDensity.setSelectedIndex(this.config.getSunDensityIndex());
    comboSunDensity.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboSunDensity.setRenderer(dlcr);
    comboSunDensity.addActionListener(listener);
    info.add(comboSunDensity);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(200, 5)));

    info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Player Setting");
    label = new TransparentLabel(info, "Number of players:");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] players = new String[7];
    players[0] = "Two players";
    players[1] = "Three players";
    players[2] = "Four players";
    players[3] = "Five players";
    players[4] = "Six players";
    players[5] = "Seven players";
    players[6] = "Eight players";
    comboPlayers = new JComboBox<>(players);
    comboPlayers.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboPlayers.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboPlayers.setBorder(new SimpleBorder());
    comboPlayers.setFont(GuiStatics.getFontCubellan());
    comboPlayers.setSelectedIndex(this.config.getMaxPlayers() - 2);
    comboPlayers.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboPlayers.setRenderer(dlcr);
    comboPlayers.addActionListener(listener);
    info.add(comboPlayers);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new TransparentLabel(info, "Starting position:");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] startPos = new String[2];
    startPos[0] = "Border";
    startPos[1] = "Random";
    comboPlayerPos = new JComboBox<>(startPos);
    comboPlayerPos.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboPlayerPos.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboPlayerPos.setBorder(new SimpleBorder());
    comboPlayerPos.setFont(GuiStatics.getFontCubellan());
    comboPlayerPos.setSelectedIndex(this.config.getStartingPosition());
    comboPlayerPos.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    comboPlayerPos.setRenderer(dlcr);
    comboPlayerPos.addActionListener(listener);
    info.add(comboPlayerPos);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(200, 5)));
    invisible.add(xinvis);
    invisible.add(Box.createRigidArea(new Dimension(200, 300)));

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
   * Handle actions for Galaxy Creation view
   * @param arg0 The event to handle
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_GALAXY_SETUP)) {
      config.setMaxPlayers(comboPlayers.getSelectedIndex() + 2);
      config.setStartingPosition(comboPlayerPos.getSelectedIndex());
      switch (comboGalaxySize.getSelectedIndex()) {
      case 0: {
        // Very small
        config.setSize(50, 50, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 1: {
        // Small
        config.setSize(75, 75, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 2: {
        // Medium
        config.setSize(128, 128, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 3: {
        // Large
        config.setSize(160, 160, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 4: {
        // Very Large
        config.setSize(200, 200, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 5: {
        // Huge
        config.setSize(256, 256, comboGalaxySize.getSelectedIndex());
        break;
      }
      default: {
        // SMALL
        config.setSize(75, 75, comboGalaxySize.getSelectedIndex());
        break;
      }
      }
      switch (comboSunDensity.getSelectedIndex()) {
      case 0: {
        // SPARSE
        config.setSolarSystemDistance(12, comboSunDensity.getSelectedIndex());
        break;
      }
      case 1: {
        // Medium
        config.setSolarSystemDistance(10, comboSunDensity.getSelectedIndex());
        break;
      }
      case 2: {
        // Dense
        config.setSolarSystemDistance(7, comboSunDensity.getSelectedIndex());
        break;
      }
      case 3: {
        // Overlap
        config.setSolarSystemDistance(6, comboSunDensity.getSelectedIndex());
        break;
      }
      default: {
        // SPARSE
        config.setSolarSystemDistance(10, comboSunDensity.getSelectedIndex());
        break;
      }
      }
    }
  }

  public GalaxyConfig getConfig() {
    return config;
  }

}
