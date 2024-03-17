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
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;


/**
 * AI Realm Setup view. This should be easy view to setup AI realms
 * quickly.
 *
 */
public class AiRealmSetupView extends BlackPanel {

  /**
   * Galaxy config
   */
  private GalaxyConfig config;

  /**
   * Checkbox for Unique race
   */
  private SpaceCheckBox uniqueRace;
  /**
   * Constructor for AI Realm setup View
   * @param config Galaxy Configuration
   * @param listener ActionListener
   */
  public AiRealmSetupView(final GalaxyConfig config,
      final ActionListener listener) {
    if (config == null) {
      this.config = new GalaxyConfig();
    } else {
      this.config = config;
    }
    Planet planet = new Planet(new Coordinate(1, 1), "AI Realm Setup Planet",
        1, false);
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
    BigImagePanel imgBase = new BigImagePanel(planet, true, "AI Realm Setup");
    if (orbital != null) {
      imgBase.setCustomOrbital(orbital);
    }
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    int extraBoxHeight = 150;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      if (game.getHeight() < 960) {
        extraBoxHeight = 100;
      }
    }
    invisible.add(Box.createRigidArea(new Dimension(500, extraBoxHeight)));
    invisible.add(createRealmSetupPanel(listener));
    imgBase.add(invisible, BorderLayout.CENTER);

    invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("BACK", GameCommands.COMMAND_CANCEL);
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
   * Create Realm Setup panel.
   * @param listener ActionListener
   * @return InfoPanel with galaxy creation settings.
   */
  private InfoPanel createRealmSetupPanel(final ActionListener listener) {
    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Realm Setup");
    info.add(Box.createRigidArea(new Dimension(10, 10)));
    uniqueRace = new SpaceCheckBox("Unique space race");
    uniqueRace.setToolTipText("Each realm has unique space race.");
    uniqueRace.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    uniqueRace.setAlignmentX(CENTER_ALIGNMENT);
    info.add(uniqueRace);
    return info;
  }
}
