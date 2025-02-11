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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.government.Government;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.scenario.ScenarioIds;
import org.openRealmOfStars.player.scenario.StartingScenario;
import org.openRealmOfStars.player.scenario.StartingScenarioFactory;
import org.openRealmOfStars.player.scenario.StartingScenarioType;
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
   * Checkbox for Unique government
   */
  private SpaceCheckBox uniqueGovernment;

  /**
   * Checkbox for Start from earth
   */
  private SpaceCheckBox startEarth;
  /**
   * Checkbox for no home starting scenario.
   */
  private SpaceCheckBox noHomeStart;
  /**
   * Checkbox for utopia starting scenario.
   */
  private SpaceCheckBox utopiaStart;
  /**
   * Checkbox for doomed starting scenario.
   */
  private SpaceCheckBox doomedStart;

  /**
   * ComboBox on minimum elder race
   */
  private SpaceComboBox<String> comboMinimumElderRace;
  /**
   * ComboBox on maximum elder race
   */
  private SpaceComboBox<String> comboMaximumElderRace;

  /**
   * Generated realms based on settings.
   */
  private boolean generated;
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
    generated = false;
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
    SpaceButton btn = new SpaceButton("Back", GameCommands.COMMAND_CANCEL);
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
   * Generate amount of elder based on number of realms.
   * @return Elder options as Strings
   */
  private String[] generateAmountElders() {
    double[] percentage = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.75, 1};
    ArrayList<String> elderList = new ArrayList<>();
    int oldValue = -1;
    int maxRealms = config.getMaxPlayers() - 1;
    for (int i = 0; i < percentage.length; i++) {
      int value = (int) Math.round(maxRealms * percentage[i]);
      if (value != oldValue) {
        oldValue = value;
        if (value == 0) {
          elderList.add("No elder realms");
        } else if (value == 1) {
          elderList.add("One elder realm");
        } else {
          elderList.add(String.valueOf(value) + " elder realms");
        }
      }
    }
    return elderList.toArray(new String[elderList.size()]);
  }

  /**
   * Parse amount of elder from string.
   * @param str String to parse
   * @return number of elder.
   */
  public static int parseAmountOfElder(final String str) {
    int index = str.indexOf("elder");
    if (index > -1) {
      String valueStr = str.substring(0, index);
      valueStr = valueStr.trim();
      if (valueStr.contains("No")) {
        return 0;
      }
      if (valueStr.contains("One")) {
        return 1;
      }
      return Integer.valueOf(valueStr);
    }
    return 0;
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
    uniqueRace.addActionListener(listener);
    if (config.getMaxPlayers() > SpaceRaceFactory.getValues().length - 2) {
      uniqueRace.setSelected(false);
      uniqueRace.setEnabled(false);
    } else {
      uniqueRace.setSelected(true);
    }
    info.add(uniqueRace);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    uniqueGovernment = new SpaceCheckBox("Unique government");
    uniqueGovernment.setToolTipText("Each realm has unique government.");
    uniqueGovernment.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    uniqueGovernment.setAlignmentX(CENTER_ALIGNMENT);
    uniqueGovernment.addActionListener(listener);
    info.add(uniqueGovernment);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceLabel label = new SpaceLabel(
        "Minimum number of elder realms in galaxy: ");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    comboMinimumElderRace = new SpaceComboBox<>(generateAmountElders());
    comboMinimumElderRace.setBackground(GuiStatics.getDeepSpaceDarkColor());
    comboMinimumElderRace.setForeground(GuiStatics.getCoolSpaceColor());
    comboMinimumElderRace.setBorder(new SimpleBorder());
    comboMinimumElderRace.setFont(GuiFonts.getFontCubellan());
    comboMinimumElderRace.setSelectedIndex(0);
    comboMinimumElderRace.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboMinimumElderRace.addActionListener(listener);
    comboMinimumElderRace.setToolTipText("<html>What is the minimum amount of"
        + " elder realms in the galaxy.</html>");
    info.add(comboMinimumElderRace);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Maximum number of elder realms in galaxy: ");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    comboMaximumElderRace = new SpaceComboBox<>(generateAmountElders());
    comboMaximumElderRace.setBackground(GuiStatics.getDeepSpaceDarkColor());
    comboMaximumElderRace.setForeground(GuiStatics.getCoolSpaceColor());
    comboMaximumElderRace.setBorder(new SimpleBorder());
    comboMaximumElderRace.setFont(GuiFonts.getFontCubellan());
    comboMaximumElderRace.setSelectedIndex(0);
    comboMaximumElderRace.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboMaximumElderRace.addActionListener(listener);
    comboMaximumElderRace.setToolTipText("<html>What is the maximum amount of"
        + " elder realms in the galaxy.</html>");
    info.add(comboMaximumElderRace);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Allowed starting scenarios: ");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    startEarth = new SpaceCheckBox("Allow starting from Earth");
    startEarth.setToolTipText("Allow realm start from Earth. Only single"
        + " realm may start from Earth.");
    startEarth.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    startEarth.setAlignmentX(CENTER_ALIGNMENT);
    startEarth.addActionListener(listener);
    info.add(startEarth);
    info.add(Box.createRigidArea(new Dimension(2, 2)));
    noHomeStart = new SpaceCheckBox("Allow starting without home planet");
    noHomeStart.setToolTipText("Realm starts without home planet. It has more "
        + "ships though.");
    noHomeStart.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    noHomeStart.setAlignmentX(CENTER_ALIGNMENT);
    noHomeStart.addActionListener(listener);
    noHomeStart.setSelected(true);
    info.add(noHomeStart);
    info.add(Box.createRigidArea(new Dimension(2, 2)));
    utopiaStart = new SpaceCheckBox("Allow utopia starting scenario");
    utopiaStart.setToolTipText("Realm starts without any ships, but starting "
        + "planet is better than normally would be.");
    utopiaStart.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    utopiaStart.setAlignmentX(CENTER_ALIGNMENT);
    utopiaStart.addActionListener(listener);
    utopiaStart.setSelected(true);
    info.add(utopiaStart);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    doomedStart = new SpaceCheckBox("Allow doomed starting scenario");
    doomedStart.setToolTipText("Realm starts in planet which fate is doomed");
    doomedStart.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    doomedStart.setAlignmentX(CENTER_ALIGNMENT);
    doomedStart.addActionListener(listener);
    doomedStart.setSelected(true);
    info.add(doomedStart);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceButton btn = new SpaceButton("Edit details",
        GameCommands.COMMAND_REALM_DETAILS);
    btn.setAlignmentX(CENTER_ALIGNMENT);
    btn.addActionListener(listener);
    info.add(btn);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    return info;
  }

  /**
   * Generate realms.
   */
  public void generateRealms() {
    String minElderStr = (String) comboMinimumElderRace.getSelectedItem();
    int minElder = parseAmountOfElder(minElderStr);
    String maxElderStr = (String) comboMaximumElderRace.getSelectedItem();
    int maxElder = parseAmountOfElder(maxElderStr);
    int elders = 0;
    if (minElder != maxElder && minElder < maxElder) {
      elders = DiceGenerator.getRandom(minElder, maxElder);
    } else if (minElder == maxElder && minElder > 0) {
      elders = minElder;
    }
    int countElder = 0;
    ArrayList<SpaceRace> availableRaces = new ArrayList<>();
    for (String name : SpaceRaceFactory.getIds()) {
      availableRaces.add(SpaceRaceFactory.createOne(name));
    }
    if (uniqueRace.isSelected()) {
      availableRaces.remove(config.getRace(0));
    }
    ArrayList<Government> availableGovs = new ArrayList<>();
    for (Government government : GovernmentFactory.getValues()) {
      availableGovs.add(government);
    }
    if (uniqueGovernment.isSelected()) {
      availableGovs.remove(config.getPlayerGovernment(0));
    }
    ArrayList<PlayerColor> availableColors = new ArrayList<>();
    for (PlayerColor color : PlayerColor.values()) {
      availableColors.add(color);
    }
    availableColors.remove(config.getPlayerColor(0));
    ArrayList<StartingScenario> availableScenario = new ArrayList<>();
    for (StartingScenario scenario : StartingScenarioFactory.getValues()) {
      if (scenario.getType() == StartingScenarioType.REGULAR) {
        if (scenario.getId().equals(ScenarioIds.EARTH)
            && startEarth.isSelected()) {
          availableScenario.add(scenario);
          continue;
        }
        if (!scenario.getId().equals(ScenarioIds.EARTH)) {
          availableScenario.add(scenario);
          continue;
        }
      }
      if (scenario.getType() == StartingScenarioType.NO_HOME
          && noHomeStart.isSelected()) {
        availableScenario.add(scenario);
      }
      if (scenario.getType() == StartingScenarioType.UTOPIA_WORLD
          && utopiaStart.isSelected()) {
        availableScenario.add(scenario);
      }
      if (scenario.getType() == StartingScenarioType.DOOMED
          && doomedStart.isSelected()) {
        availableScenario.add(scenario);
      }
    }
    for (int i = 1; i < config.getMaxPlayers(); i++) {
      if (countElder < elders) {
        countElder++;
        config.setPlayerElderRealm(i, true);
      } else {
        config.setPlayerElderRealm(i, false);
      }
      if (uniqueRace.isSelected()) {
        SpaceRace race = DiceGenerator.pickRandom(availableRaces);
        availableRaces.remove(race);
        config.setRace(i, race);
      } else {
        SpaceRace race = DiceGenerator.pickRandom(availableRaces);
        config.setRace(i, race);
      }
      if (uniqueGovernment.isSelected()) {
        Government gov = DiceGenerator.pickRandom(availableGovs);
        availableGovs.remove(gov);
        config.setPlayerGovernment(i, gov);
      } else {
        Government gov = DiceGenerator.pickRandom(availableGovs);
        config.setPlayerGovernment(i, gov);
      }
      StartingScenario[] possibleScenarios =
          PlayerList.getPossibleStartingScenarios(config.getRace(i),
              availableScenario.toArray(new StartingScenario[0]));
      StartingScenario scenario = DiceGenerator.pickRandom(possibleScenarios);
      config.setStartingScenario(i, scenario);
      config.generateUniqueName(i);
      PlayerColor color = DiceGenerator.pickRandom(availableColors);
      availableColors.remove(color);
      config.setPlayerColor(i, color);
    }
  }
  /**
   * Handle actions for Player Setup view
   * @param arg0 The event
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)
        && !generated) {
      generateRealms();
      generated = true;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_GALAXY_SETUP)) {
      SoundPlayer.playMenuSound();
      generateRealms();
    }
  }
}
