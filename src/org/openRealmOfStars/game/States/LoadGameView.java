package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.SavedGame;
import org.openRealmOfStars.gui.ListRenderers.SaveGameListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.GenericFileFilter;

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
 * Load Game View
 * 
 */
public class LoadGameView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  /**
   * Saved Game list
   * 
   */
  private JList<SavedGame> saveGamesList;

  /**
   * Constructor for Load Game View
   * @param listener ActionListener
   */
  public LoadGameView( ActionListener listener) {
    Planet planet = new Planet(1, 1, "Load Game Planet",1, false);
    if (DiceGenerator.getRandom(100)<10) {
      planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length-1));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,"Load game");
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
    info.setTitle("Saved games");
    info.add(Box.createRigidArea(new Dimension(5,5)));
    
    File folder = new File("saves");
    File[] files = folder.listFiles(new GenericFileFilter(".save"));
    
    SavedGame[] games = new SavedGame[files.length];
    for (int i=0;i<files.length;i++) {
      try {
        games[i] = new SavedGame(files[i].getName());
      } catch (IOException e) {
        games[i] = null;
      }
    }
    
    saveGamesList = new JList<>(games);
    
    saveGamesList.setCellRenderer(new SaveGameListRenderer());
    JScrollPane scroll = new JScrollPane(saveGamesList);
    saveGamesList.setBackground(Color.BLACK);
    saveGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    info.add(scroll);
    info.add(Box.createRigidArea(new Dimension(10,10)));
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(200,5)));
    invis.add(xinvis);

    imgBase.add(invis,BorderLayout.CENTER);

    invis = new InvisiblePanel(imgBase);
    invis.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("Cancel", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(btn,BorderLayout.WEST);
    btn = new SpaceButton("Load", GameCommands.COMMAND_NEXT);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invis.add(btn,BorderLayout.EAST);
    imgBase.add(invis,BorderLayout.SOUTH);
    this.add(imgBase,BorderLayout.CENTER);

  }

  /**
   * Get Selected Save file name
   * @return String as saved game file name or null if not selected.
   */
  public String getSelectedSaveFile() {
    if (saveGamesList.getSelectedValue() != null) {
      return saveGamesList.getSelectedValue().getFilename();
    }
    return null;
  }



}
