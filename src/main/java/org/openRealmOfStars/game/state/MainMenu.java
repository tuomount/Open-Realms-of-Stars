package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.FileIo.Folders;
import org.openRealmOfStars.utilities.FileIo.GenericFileFilter;

/**
 *
 * Main menu for Open Realm of Stars
 *
 */
public class MainMenu extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for main menu
   * @param listener ActionListener
   * @param text Optional Title text, for showing planet with text
   */
  public MainMenu(final ActionListener listener, final String text) {
    Planet planet = new Planet(new Coordinate(1, 1), "Main Menu Planet", 1,
        false);
    planet.setPlanetType(PlanetTypes.getRandomPlanetType(true, true, true));
    if (planet.getPlanetType().isGasGiant()) {
      planet.setGasGiant(true);
    }
    BufferedImage orbital = null;
    if (DiceGenerator.getRandom(99) < 33) {
      if (DiceGenerator.getRandom(99) < 70) {
        planet.setOrbital(ShipGenerator.generateRandomOrbital());
      } else {
        orbital = PlanetTypes.getRandomPlanetType(false, true, true).getImage();
      }
    }
    // Background image
    String title = "Open Realm of Stars";
    if (text != null) {
      title = text;
    }
    BigImagePanel imgBase = new BigImagePanel(planet, true, title);
    if (orbital != null) {
      imgBase.setCustomOrbital(orbital);
    }
    if (text != null) {
      imgBase.setTextInMiddle(true);
    }
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 250)));

    SpaceButton btn = new SpaceButton("Continue game",
        GameCommands.COMMAND_CONTINUE_GAME);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    File folder = new File(Folders.getSavegamePath());
    if (folder.exists()) {
      File[] files = folder.listFiles(new GenericFileFilter(".save"));
      if (files == null) {
        files = new File[0];
      }
      if (files.length > 0) {
        invisible.add(btn);
      }
    }
    btn = new SpaceButton("New game", GameCommands.COMMAND_NEW_GAME);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    btn = new SpaceButton("Options", GameCommands.COMMAND_OPTIONS_VIEW);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    btn = new SpaceButton("Edit government",
        GameCommands.COMMAND_EDIT_GOVERNMENT);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    btn = new SpaceButton("Credits", GameCommands.COMMAND_CREDITS);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    btn = new SpaceButton("View change log", GameCommands.COMMAND_CHANGE_LOG);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    btn = new SpaceButton("Quit", GameCommands.COMMAND_QUIT_GAME);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    if (text == null) {
      imgBase.add(invisible);
    }
    this.add(imgBase, BorderLayout.CENTER);

  }

}
