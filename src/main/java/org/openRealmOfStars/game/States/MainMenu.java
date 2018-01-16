package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.GenericFileFilter;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
   */
  public MainMenu(final ActionListener listener) {
    Planet planet = new Planet(new Coordinate(1, 1), "Main Menu Planet", 1,
        false);
    if (DiceGenerator.getRandom(100) < 10) {
      planet.setPlanetType(PlanetTypes.getRandomPlanetType(true));
      planet.setGasGiant(true);
    } else {
      planet.setPlanetType(PlanetTypes.getRandomPlanetType(false));
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,
        "Open Realm of Stars");
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 250)));

    SpaceButton btn = new SpaceButton("Continue game",
        GameCommands.COMMAND_CONTINUE_GAME);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    File folder = new File("saves");
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
    btn = new SpaceButton("Credits", GameCommands.COMMAND_CREDITS);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    btn = new SpaceButton("Quit", GameCommands.COMMAND_QUIT_GAME);
    btn.addActionListener(listener);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    invisible.add(btn);
    imgBase.add(invisible);
    this.add(imgBase, BorderLayout.CENTER);

  }

}
