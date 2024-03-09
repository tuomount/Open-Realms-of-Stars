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
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.scenario.StartingScenario;
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
  private SpaceComboBox<GovernmentType> comboGovernmentSelect;

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

  /**
   * Random list for colors.
   */
  private ArrayList<PlayerColor> randomListOfColors;

  /** Flag for allow changing realm */
  private boolean allowChangingRealm;

  /** Realm Index. */
  private int realmIndex;

  public RealmSetupView(final GalaxyConfig config,
      final ActionListener listener, final boolean allowChangeRealm) {
    this.config = config;
    this.actionListener = listener;
    this.allowChangingRealm = allowChangeRealm;
    realmIndex = 0;
    if (allowChangingRealm) {
      realmIndex = 1;
    }
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
}
