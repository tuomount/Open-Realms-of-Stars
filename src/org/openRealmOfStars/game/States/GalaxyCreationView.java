package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
   * Constructor for main menu
   * @param listener ActionListener
   */
  public GalaxyCreationView(GalaxyConfig config, ActionListener listener) {
    if (config == null) {
      this.config = new GalaxyConfig();
    } else {
      this.config =  config;
    }
    Planet planet = new Planet(1, 1, "Galaxy Creation Planet",1, false);
    if (DiceGenerator.getRandom(100)<10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length-1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,"Galaxy Creation");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());
    
    InvisiblePanel invis = new InvisiblePanel(imgBase);    
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    invis.add(Box.createRigidArea(new Dimension(500, 250)));

    InvisiblePanel xinvis = new InvisiblePanel(invis);    
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(200,5)));
    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Galaxy Creation");
    TransparentLabel label = new TransparentLabel(info, "Galaxy size:");
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    String[] galaxySizes = new String[1];
    galaxySizes[0] = "Small";
    comboGalaxySize = new JComboBox<>(galaxySizes);
    comboGalaxySize.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboGalaxySize.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboGalaxySize.setBorder(new SimpleBorder());
    comboGalaxySize.setFont(GuiStatics.getFontCubellan());
    comboGalaxySize.setSelectedIndex(this.config.getGalaxySizeIndex());
    comboGalaxySize.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboGalaxySize.addActionListener(listener);
    info.add(comboGalaxySize);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    label = new TransparentLabel(info, "Sun density:");
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    String[] sunDensity = new String[1];
    sunDensity[0] = "Sparse";
    comboSunDensity = new JComboBox<>(sunDensity);
    comboSunDensity.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboSunDensity.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboSunDensity.setBorder(new SimpleBorder());
    comboSunDensity.setFont(GuiStatics.getFontCubellan());
    comboSunDensity.setSelectedIndex(this.config.getSunDensityIndex());
    comboSunDensity.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboSunDensity.addActionListener(listener);
    info.add(comboSunDensity);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(200,5)));
    
    info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Player Setting");
    label = new TransparentLabel(info, "Number of players:");
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5,5)));
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
    comboPlayers.setSelectedIndex(this.config.getMaxPlayers()-2);
    comboPlayers.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboPlayers.addActionListener(listener);
    info.add(comboPlayers);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    label = new TransparentLabel(info, "Starting position:");
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    String[] startPos = new String[1];
    startPos[0] = "Border";
    comboPlayerPos = new JComboBox<>(startPos);
    comboPlayerPos.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboPlayerPos.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboPlayerPos.setBorder(new SimpleBorder());
    comboPlayerPos.setFont(GuiStatics.getFontCubellan());
    comboPlayerPos.setSelectedIndex(this.config.getStartingPosition());
    comboPlayerPos.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboPlayerPos.addActionListener(listener);
    info.add(comboPlayerPos);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(200,5)));
    invis.add(xinvis);
    invis.add(Box.createRigidArea(new Dimension(200,300)));

    imgBase.add(invis,BorderLayout.CENTER);

    invis = new InvisiblePanel(imgBase);
    invis.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("Cancel", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(btn,BorderLayout.WEST);
    btn = new SpaceButton("Next", GameCommands.COMMAND_NEXT);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(btn,BorderLayout.EAST);
    imgBase.add(invis,BorderLayout.SOUTH);
    this.add(imgBase,BorderLayout.CENTER);

  }

  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_GALAXY_SETUP)) {
      config.setMaxPlayers(comboPlayers.getSelectedIndex()+2);
      config.setStartingPosition(comboPlayerPos.getSelectedIndex());
      switch (comboGalaxySize.getSelectedIndex()) {
      case 0: {
        //SMALL
        config.setSize(75, 75, comboGalaxySize.getSelectedIndex());
        break;
      }
      default: {
        //SMALL
        config.setSize(75, 75, comboGalaxySize.getSelectedIndex());
        break;
      }
      }
      switch (comboSunDensity.getSelectedIndex()) {
      case 0: {
        //SPARSE
        config.setSolarSystemDistance(20, comboSunDensity.getSelectedIndex());
        break;
      }
      default: {
        //SPARSE
        config.setSolarSystemDistance(20, comboSunDensity.getSelectedIndex());
        break;
      }
      }
    }
  }
  public GalaxyConfig getConfig() {
    return config;
  }



}
