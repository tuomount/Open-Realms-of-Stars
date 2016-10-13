package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.player.SpaceRace;
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
  private static final String[] RACE_SELECTION = {"Human","Mechion","Spork","Greyan","Centaur"};
  
  /**
   * ComboBox on galaxy size
   */
  private JComboBox<String>[] comboRaceSelect;
  
  /**
   * Race Images
   */
  private RaceImagePanel[] raceImgs;

  /**
   * Galaxy config
   */
  private GalaxyConfig config;

  
  /**
   * Maximum number of players
   */
  private static final int MAX_PLAYERS = 8;

  /**
   * Constructor for main menu
   * @param listener ActionListener
   */
  @SuppressWarnings("unchecked")
  public PlayerSetupView(GalaxyConfig config, ActionListener listener) {
    this.config = config;
    if (this.config == null) {
      this.config = new GalaxyConfig();
    }
    Planet planet = new Planet(1, 1, "Galaxy Creation Planet",2, false);
    if (DiceGenerator.getRandom(100)<10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length-1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,"Player Setup");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());
    
    InvisiblePanel invis = new InvisiblePanel(imgBase);    
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    invis.add(Box.createRigidArea(new Dimension(500, 150)));

    comboRaceSelect = (JComboBox[]) new JComboBox[MAX_PLAYERS];
    raceImgs = new RaceImagePanel[MAX_PLAYERS];
    
    InvisiblePanel xinvis = new InvisiblePanel(invis);    
    xinvis.setLayout(new GridLayout(2, 4));
    for (int i=0;i<MAX_PLAYERS;i++) {
      xinvis.add(createPlayerRaceSelection(xinvis,i,listener));
    }
    invis.add(xinvis);
    invis.add(Box.createRigidArea(new Dimension(200,150)));

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
  
  /**
   * Handle actions for Player Setuo view
   * @param arg0
   */
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_GALAXY_SETUP)) {
      for (int i=0;i<MAX_PLAYERS;i++) {
        String raceStr = (String) comboRaceSelect[i].getSelectedItem();
        SpaceRace race = SpaceRace.getRaceByName(raceStr);
        config.setRace(i, race);
        raceImgs[i].setRaceToShow(raceStr);
      }      
    }
  }
  
  /**
   * Create Player config for one player
   * @param base
   * @param index
   * @return InvisiblePanel with configuration components
   */
  private InvisiblePanel createPlayerRaceSelection(InvisiblePanel base, 
      int index, ActionListener listener) {
    InvisiblePanel xinvis = new InvisiblePanel(base);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    xinvis.add(Box.createRigidArea(new Dimension(25,25)));
    
    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    if (index == 0) {
      info.setTitle("Player "+(index+1));
    } else {
      info.setTitle("Player "+(index+1)+" (AI)");
    }
    raceImgs[index] = new RaceImagePanel();
    raceImgs[index].setRaceToShow(config.getRace(index).getNameSingle());
    info.add(raceImgs[index]);
    info.add(Box.createRigidArea(new Dimension(5,5)));
    comboRaceSelect[index] = new JComboBox<>(RACE_SELECTION);
    comboRaceSelect[index].setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    comboRaceSelect[index].setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    comboRaceSelect[index].setBorder(new SimpleBorder());
    comboRaceSelect[index].setFont(GuiStatics.getFontCubellan());
    comboRaceSelect[index].addActionListener(listener);
    comboRaceSelect[index].setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    if (config.getMaxPlayers()<(index+1)) {
      comboRaceSelect[index].setEnabled(false);
    }
    info.add(comboRaceSelect[index]);
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(25,25)));
    return xinvis;
  }
  
  public GalaxyConfig getConfig() {
    return config;
  }




}
