package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.buttons.SpaceCombo;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.KarmaType;
import org.openRealmOfStars.starMap.PirateDifficultLevel;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2018-2021  Tuomo Untinen
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
  private SpaceCombo<String> comboGalaxySize;

  /**
   * ComboBox on number of player
   */
  private SpaceCombo<String> comboPlayers;

  /**
   * ComboBox on player start positions
   */
  private SpaceCombo<String> comboPlayerPos;

  /**
   * ComboBox for head start ancient realms.
   */
  private SpaceCombo<String> comboAncientTurns;
  /**
   * ComboBox on sun density
   */
  private SpaceCombo<String> comboSunDensity;

  /**
   * ComboBox on planetary event
   */
  private SpaceCombo<String> comboPlanetaryEvent;

  /**
   * ComboBox on scoring victory
   */
  private SpaceCombo<String> comboScoringVictory;

  /**
   * ComboBox on scoring culture
   */
  private SpaceCombo<String> comboScoringCulture;

  /**
   * ComboBox on scoring domination
   */
  private SpaceCombo<String> comboScoringDomination;
  /**
   * ComboBox on scoring scientific
   */
  private SpaceCombo<String> comboScoringScientific;
  /**
   * ComboBox on scoring diplomatic
   */
  private SpaceCombo<String> comboScoringDiplomatic;
  /**
   * ComboBox on scoring population
   */
  private SpaceCombo<String> comboScoringPopulation;

  /**
   * ComboBox for rogue planet
   */
  private SpaceCombo<String> comboRoguePlanets;

  /**
   * ComboBox for space pirates
   */
  private SpaceCombo<String> comboSpacePirates;

  /**
   * ComboBox for space pirate difficulty level
   */
  private SpaceCombo<String> comboSpacePirateDifficulty;

  /**
   * ComboBox for space anomalies
   */
  private SpaceCombo<String> comboSpaceAnomalies;

  /**
   * ComboBox for karma type
   */
  private SpaceCombo<String> comboKarmaType;

  /**
   * ComboBox for karma speed
   */
  private SpaceCombo<String> comboKarmaSpeed;

  /**
   * Checkbox for enabled tutorial.
   */
  private SpaceCheckBox tutorialEnabled;

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
    Planet planet = new Planet(new Coordinate(1, 1), "Galaxy Creation Planet",
        1, false);
    planet.setPlanetType(PlanetTypes.getRandomPlanetType(true, true, true));
    if (planet.getPlanetType().isGasGiant()) {
      planet.setGasGiant(true);
    }
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, "Galaxy Creation");
    imgBase.setLayout(new BorderLayout());
    this.setLayout(new BorderLayout());

    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 150)));

    InvisiblePanel xinvis = new InvisiblePanel(invisible);
    xinvis.setLayout(new BoxLayout(xinvis, BoxLayout.X_AXIS));
    int extraBoxWidth = 200;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      int freeSpace = game.getWidth() - 440 - 280;
      extraBoxWidth = freeSpace / 3;
      if (extraBoxWidth > 200) {
        extraBoxWidth = 200;
      }
    }
    xinvis.add(Box.createRigidArea(new Dimension(extraBoxWidth, 5)));
    InfoPanel info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Galaxy Creation");
    SpaceLabel label = new SpaceLabel("Galaxy size");
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
    comboGalaxySize = new SpaceCombo<>(galaxySizes);
    comboGalaxySize.setSelectedIndex(this.config.getGalaxySizeIndex());
    comboGalaxySize.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboGalaxySize.addActionListener(listener);
    comboGalaxySize.setToolTipText("<html>How many tiles galaxy has?"
        + " This can affect also victory conditions.<br>"
        + " Bigger galaxy needs more cultural score to win or more<br>"
        + " United Galaxy Towers to build to start diplomatic victory path."
        + "</html>");
    info.add(comboGalaxySize);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Sun density");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] sunDensity = new String[4];
    sunDensity[0] = "Sparse";
    sunDensity[1] = "Medium";
    sunDensity[2] = "Dense";
    sunDensity[3] = "Overlap";
    comboSunDensity = new SpaceCombo<>(sunDensity);
    comboSunDensity.setSelectedIndex(this.config.getSunDensityIndex());
    comboSunDensity.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboSunDensity.addActionListener(listener);
    comboSunDensity.setToolTipText("<html>How close solar system are to"
        + " each others? Sparse creates more space between them <br>"
        + "and overlap setting there can be solar system inside each others."
        + "</html>");
    info.add(comboSunDensity);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Planetary events");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] events = new String[5];
    events[0] = "No events (0%)";
    events[1] = "Very rare (5%)";
    events[2] = "Rare (10%)";
    events[3] = "Common (20%)";
    events[4] = "Very common (40%)";
    comboPlanetaryEvent = new SpaceCombo<>(events);
    switch (this.config.getChanceForPlanetaryEvent()) {
      case 0: comboPlanetaryEvent.setSelectedIndex(0); break;
      case 5: comboPlanetaryEvent.setSelectedIndex(1); break;
      case 10: comboPlanetaryEvent.setSelectedIndex(2); break;
      case 20: comboPlanetaryEvent.setSelectedIndex(3); break;
      case 40: comboPlanetaryEvent.setSelectedIndex(4); break;
      default: comboPlanetaryEvent.setSelectedIndex(2); break;
    }
    comboPlanetaryEvent.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboPlanetaryEvent.addActionListener(listener);
    comboPlanetaryEvent.setToolTipText("<html>How many of planets have"
        + " special event when colonizing the planet.</html>");
    info.add(comboPlanetaryEvent);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Rogue planets");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] roguePlanets = new String[5];
    roguePlanets[0] = "None";
    roguePlanets[1] = "Very few";
    roguePlanets[2] = "Few(Default)";
    roguePlanets[3] = "Some";
    roguePlanets[4] = "Many";
    comboRoguePlanets = new SpaceCombo<>(roguePlanets);
    switch (this.config.getNumberOfRoguePlanets()) {
      case GalaxyConfig.ROGUE_PLANETS_NONE:
        comboRoguePlanets.setSelectedIndex(0); break;
      case GalaxyConfig.ROGUE_PLANETS_VERY_FEW:
        comboRoguePlanets.setSelectedIndex(1); break;
      case GalaxyConfig.ROGUE_PLANETS_FEW:
        comboRoguePlanets.setSelectedIndex(2); break;
      case GalaxyConfig.ROGUE_PLANETS_SOME:
        comboRoguePlanets.setSelectedIndex(3); break;
      case GalaxyConfig.ROGUE_PLANETS_MANY:
        comboRoguePlanets.setSelectedIndex(4); break;
      default: comboRoguePlanets.setSelectedIndex(2); break;
    }
    comboRoguePlanets.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboRoguePlanets.addActionListener(listener);
    comboRoguePlanets.setToolTipText("Rogue planets are rare planet are"
        + " outside of solar systems.");
    info.add(comboRoguePlanets);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    label = new SpaceLabel("Space pirates");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] spacePirates = new String[7];
    spacePirates[0] = "Disabled pirates";
    spacePirates[1] = "Very rare 10%";
    spacePirates[2] = "Rare 20%";
    spacePirates[3] = "Few 40%";
    spacePirates[4] = "Common 60%";
    spacePirates[5] = "Very common 80%";
    spacePirates[6] = "All over 100%";
    comboSpacePirates = new SpaceCombo<>(spacePirates);
    comboSpacePirates.setSelectedIndex(this.config.getSpacePiratesLevel());
    comboSpacePirates.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboSpacePirates.addActionListener(listener);
    comboSpacePirates.setToolTipText("<html>How many percent of deep space"
        + " anchors contain space pirates lair.<br>"
        + " If disable there are space pirates in"
        + " space anomalies either.<html>");

    info.add(comboSpacePirates);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    xinvis.add(info);

    label = new SpaceLabel("Space pirate difficulty");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] spacePirateDifficulties = new String[5];
    spacePirateDifficulties[0] = PirateDifficultLevel.VERY_EASY.toString();
    spacePirateDifficulties[1] = PirateDifficultLevel.EASY.toString();
    spacePirateDifficulties[2] = PirateDifficultLevel.NORMAL.toString();
    spacePirateDifficulties[3] = PirateDifficultLevel.HARD.toString();
    spacePirateDifficulties[4] = PirateDifficultLevel.VERY_HARD.toString();
    comboSpacePirateDifficulty = new SpaceCombo<>(spacePirateDifficulties);
    comboSpacePirateDifficulty.setSelectedIndex(
        this.config.getSpacePiratesDifficulty().getIndex());
    comboSpacePirateDifficulty.setActionCommand(
        GameCommands.COMMAND_GALAXY_SETUP);
    comboSpacePirateDifficulty.addActionListener(listener);
    comboSpacePirateDifficulty.setToolTipText("<html>Difficulty level of"
        + " space pirates. Difficulty level affects how fast"
        + " space pirates gain new technology in game.<br>"
        + " <li> Very easy - No technological advance"
        + " <li> Easy      - Slow technological advance"
        + " <li> Normal    - Normal technological advance"
        + " <li> Hard      - Fast technological advance"
        + " <li> Very hard - Unfairly fast technological advance"
        + "<html>");
    info.add(comboSpacePirateDifficulty);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    label = new SpaceLabel("Space anomalies");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] spaceAnomalies = new String[3];
    spaceAnomalies[0] = "Disabled";
    spaceAnomalies[1] = "Non-harmful";
    spaceAnomalies[2] = "All";
    comboSpaceAnomalies = new SpaceCombo<>(spaceAnomalies);
    comboSpaceAnomalies.setSelectedIndex(this.config.getSpaceAnomaliesLevel());
    comboSpaceAnomalies.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboSpaceAnomalies.addActionListener(listener);
    comboSpaceAnomalies.setToolTipText("<html>Is there random space anomalies."
        + " If disable there are no space anomalies.<br> Space anomalies can"
        + " contain small bonus or even harmful events.<html>");
    info.add(comboSpaceAnomalies);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    label = new SpaceLabel("Random events");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] karmaTypes = new String[7];
    karmaTypes[0] = "Disabled";
    karmaTypes[1] = "First and last";
    karmaTypes[2] = "Two first and two last";
    karmaTypes[3] = "Half and half";
    karmaTypes[4] = "Random good and bad events";
    karmaTypes[5] = "Only good events for last half, bads are disabled";
    karmaTypes[6] = "Random only good events, bads are disabled";
    comboKarmaType = new SpaceCombo<>(karmaTypes);
    comboKarmaType.setSelectedIndex(this.config.getKarmaType().getIndex());
    comboKarmaType.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboKarmaType.addActionListener(listener);
    comboKarmaType.setToolTipText("<html>"
        + " Settings determines which realms get the random events."
        + "<br> Good events happen on realms which are last in scoring"
        + " and bad events happen on realms which are the first in"
        + " scoring.<html>");
    info.add(comboKarmaType);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Random event speed");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] karmaSpeed = new String[3];
    karmaSpeed[0] = "Slow";
    karmaSpeed[1] = "Normal";
    karmaSpeed[2] = "Fast";
    comboKarmaSpeed = new SpaceCombo<>(karmaSpeed);
    comboKarmaSpeed.setSelectedIndex(this.config.getKarmaSpeed() - 1);
    comboKarmaSpeed.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboKarmaSpeed.addActionListener(listener);
    comboKarmaSpeed.setToolTipText("<html>How often random events occur."
        + " If random events are disable this setting does not do"
        + " anything.<html>");
    info.add(comboKarmaSpeed);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(extraBoxWidth, 5)));

    info = new InfoPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.setTitle("Realm Setting");
    label = new SpaceLabel("Number of realms");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] players = new String[11];
    players[0] = "Two realms";
    players[1] = "Three realms";
    players[2] = "Four realms";
    players[3] = "Five realms";
    players[4] = "Six realms";
    players[5] = "Seven realms";
    players[6] = "Eight realms";
    players[7] = "Nine realms";
    players[8] = "Ten realms";
    players[9] = "Eleven realms";
    players[10] = "Twelve realms";
    comboPlayers = new SpaceCombo<>(players);
    comboPlayers.setSelectedIndex(this.config.getMaxPlayers() - 2);
    comboPlayers.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboPlayers.addActionListener(listener);
    info.add(comboPlayers);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Starting position");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 8)));
    String[] startPos = new String[3];
    startPos[0] = "Border";
    startPos[1] = "Random";
    startPos[2] = "Ancient middle";
    comboPlayerPos = new SpaceCombo<>(startPos);
    comboPlayerPos.setSelectedIndex(this.config.getStartingPosition());
    comboPlayerPos.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboPlayerPos.addActionListener(listener);
    info.add(comboPlayerPos);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Ancient realm headstart");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] headStarts = new String[5];
    headStarts[0] = "40 turns";
    headStarts[1] = "60 turns";
    headStarts[2] = "80 turns";
    headStarts[3] = "100 turns";
    headStarts[4] = "120 turns";
    comboAncientTurns = new SpaceCombo<>(headStarts);
    comboAncientTurns.setToolTipText("<html>How many turns Ancient Realms play"
        + " before actual game begins.<br>AI will play ancient realms this"
        + " amount of turns.<br>These ancient realms will be stronger than"
        + "other realms.<br>This will also create totally unique starts"
        + "for the game.</html>");
    switch (this.config.getAncientHeadStart()) {
      case 40: comboAncientTurns.setSelectedIndex(0); break;
      case 60: comboAncientTurns.setSelectedIndex(1); break;
      default:
      case 80: comboAncientTurns.setSelectedIndex(2); break;
      case 100: comboAncientTurns.setSelectedIndex(3); break;
      case 120: comboAncientTurns.setSelectedIndex(4); break;
    }
    comboAncientTurns.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboAncientTurns.addActionListener(listener);
    info.add(comboAncientTurns);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Victory by score");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] scoringVictory = new String[6];
    scoringVictory[0] = "Very short (200 turns)";
    scoringVictory[1] = "Short (300 turns)";
    scoringVictory[2] = "Medium (400 turns)";
    scoringVictory[3] = "Long (600 turns)";
    scoringVictory[4] = "Very long (800 turns)";
    scoringVictory[5] = "Massive (1000 turns)";
    comboScoringVictory = new SpaceCombo<>(scoringVictory);
    comboScoringVictory.setToolTipText("How many turns game is played before"
        + " winner is decided by game score");
    switch (this.config.getScoringVictoryTurns()) {
      case 200: comboScoringVictory.setSelectedIndex(0); break;
      case 300: comboScoringVictory.setSelectedIndex(1); break;
      case 400: comboScoringVictory.setSelectedIndex(2); break;
      case 600: comboScoringVictory.setSelectedIndex(3); break;
      case 800: comboScoringVictory.setSelectedIndex(4); break;
      case 1000: comboScoringVictory.setSelectedIndex(5); break;
      default: comboScoringVictory.setSelectedIndex(2); break;
    }
    comboScoringVictory.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboScoringVictory.addActionListener(listener);
    info.add(comboScoringVictory);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    label = new SpaceLabel("Victory by culture");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] scoringCulture = new String[5];
    scoringCulture[0] = "Disable cultural victory";
    scoringCulture[1] = "Faster (75% regular limit)";
    scoringCulture[2] = "Normal (100% regular limit)";
    scoringCulture[3] = "Slower (150% regular limit)";
    scoringCulture[4] = "Very slow (200% regular limit)";
    comboScoringCulture = new SpaceCombo<>(scoringCulture);
    comboScoringCulture.setToolTipText("<html>How much culture must gain"
        + " before winning by cultural domination.<br> Realm must "
        + " have one broadcasting building and highest culture.</html>");
    switch (this.config.getScoreLimitCulture()) {
      case -1: comboScoringCulture.setSelectedIndex(0); break;
      case 0: comboScoringCulture.setSelectedIndex(1); break;
      case 1: comboScoringCulture.setSelectedIndex(2); break;
      case 2: comboScoringCulture.setSelectedIndex(3); break;
      case 3: comboScoringCulture.setSelectedIndex(4); break;
      default: comboScoringCulture.setSelectedIndex(2); break;
    }
    comboScoringCulture.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboScoringCulture.addActionListener(listener);
    info.add(comboScoringCulture);
    info.add(Box.createRigidArea(new Dimension(5, 5)));

    label = new SpaceLabel("Victory by domination");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] scoringDomination = new String[2];
    scoringDomination[0] = "Disabled";
    scoringDomination[1] = "Enabled";
    comboScoringDomination = new SpaceCombo<>(scoringDomination);
    comboScoringDomination.setToolTipText("<html>Domination victory when all "
        + "home planets are controlled by one realm or alliance.<br> This is "
        + "enabled only if 4 or more realms in play.</html>");
    comboScoringDomination.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboScoringDomination.addActionListener(listener);
    info.add(comboScoringDomination);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    switch (this.config.getScoreLimitConquer()) {
      case 0: comboScoringDomination.setSelectedIndex(0); break;
      case 1: comboScoringDomination.setSelectedIndex(1); break;
      default: comboScoringDomination.setSelectedIndex(1); break;
    }

    label = new SpaceLabel("Victory by science");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] scoringScience = new String[6];
    scoringScience[0] = "Disabled";
    scoringScience[1] = "1 Scientic achievement";
    scoringScience[2] = "2 Scientic achievements";
    scoringScience[3] = "3 Scientic achievements";
    scoringScience[4] = "4 Scientic achievements";
    scoringScience[5] = "5 Scientic achievements";
    comboScoringScientific = new SpaceCombo<>(scoringScience);
    comboScoringScientific.setToolTipText("<html>Single planet must have this"
        + " amount of unique scientic achievements buildings to win.</html>");
    comboScoringScientific.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboScoringScientific.addActionListener(listener);
    info.add(comboScoringScientific);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    switch (this.config.getScoreLimitResearch()) {
      case 0: comboScoringScientific.setSelectedIndex(0); break;
      case 1: comboScoringScientific.setSelectedIndex(1); break;
      case 2: comboScoringScientific.setSelectedIndex(2); break;
      case 3: comboScoringScientific.setSelectedIndex(3); break;
      case 4: comboScoringScientific.setSelectedIndex(4); break;
      case 5: comboScoringScientific.setSelectedIndex(5); break;
      default: comboScoringScientific.setSelectedIndex(2); break;
    }

    label = new SpaceLabel("Victory by diplomacy");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] scoringDiplomacy = new String[6];
    scoringDiplomacy[0] = "Disabled";
    scoringDiplomacy[1] = "2 diplomatic votes";
    scoringDiplomacy[2] = "3 diplomatic votes";
    scoringDiplomacy[3] = "4 diplomatic votes";
    scoringDiplomacy[4] = "5 diplomatic votes";
    scoringDiplomacy[5] = "6 diplomatic votes";
    comboScoringDiplomatic = new SpaceCombo<>(scoringDiplomacy);
    comboScoringDiplomatic.setToolTipText("<html>Realm have must minimum number"
        + " of United Galaxy Towers. Minimum number varies of galaxy size."
        + "<br>"
        + " After minimum number of tower there are selected amount of"
        + " diplomatic votes.<br>"
        + " Two last votes are the most critical ones."
        + "</html>");
    comboScoringDiplomatic.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboScoringDiplomatic.addActionListener(listener);
    info.add(comboScoringDiplomatic);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    switch (this.config.getScoreLimitDiplomacy()) {
      case 0: comboScoringDiplomatic.setSelectedIndex(0); break;
      case 1: comboScoringDiplomatic.setSelectedIndex(1); break;
      case 2: comboScoringDiplomatic.setSelectedIndex(2); break;
      case 3: comboScoringDiplomatic.setSelectedIndex(3); break;
      case 4: comboScoringDiplomatic.setSelectedIndex(4); break;
      case 5: comboScoringDiplomatic.setSelectedIndex(5); break;
      default: comboScoringDiplomatic.setSelectedIndex(2); break;
    }
    label = new SpaceLabel("Victory by population");
    label.setAlignmentX(CENTER_ALIGNMENT);
    info.add(label);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] scoringPopulation = new String[6];
    scoringPopulation[0] = "Disabled";
    scoringPopulation[1] = "40% of whole galaxy";
    scoringPopulation[2] = "50% of whole galaxy";
    scoringPopulation[3] = "60% of whole galaxy";
    scoringPopulation[4] = "70% of whole galaxy";
    comboScoringPopulation = new SpaceCombo<>(scoringPopulation);
    comboScoringPopulation.setToolTipText("<html>Realm or alliance must have"
        + " certain percentage of whole galaxy population to win."
        + "<br>"
        + " Also at least 100 turns have to have passed before victory by"
        + " population is checked."
        + "</html>");
    comboScoringPopulation.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    comboScoringPopulation.addActionListener(listener);
    info.add(comboScoringPopulation);
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    switch (this.config.getScoreLimitPopulation()) {
      case 0: comboScoringPopulation.setSelectedIndex(0); break;
      case 1: comboScoringPopulation.setSelectedIndex(1); break;
      case 2: comboScoringPopulation.setSelectedIndex(2); break;
      case 3: comboScoringPopulation.setSelectedIndex(3); break;
      case 4: comboScoringPopulation.setSelectedIndex(4); break;
      default: comboScoringPopulation.setSelectedIndex(2); break;
    }
    info.add(Box.createRigidArea(new Dimension(5, 5)));
    tutorialEnabled = new SpaceCheckBox("Tutorial enabled");
    tutorialEnabled.setSelected(this.config.isEnableTutorial());
    tutorialEnabled.addActionListener(listener);
    tutorialEnabled.setActionCommand(GameCommands.COMMAND_GALAXY_SETUP);
    tutorialEnabled.setAlignmentX(CENTER_ALIGNMENT);
    tutorialEnabled.setToolTipText("<html>If enabled tutorial texts will be"
        + " shown as in-game messages.<br> Tutorial can be enabled or disabled"
        + " while playing and helps can accessed from Star Map view.</html>");
    info.add(tutorialEnabled);
    xinvis.add(info);
    xinvis.add(Box.createRigidArea(new Dimension(extraBoxWidth, 5)));
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
   * Galaxy size configuration for very small galaxy
   */
  private static final int GALAXY_SIZE_VERY_SMALL = 50;

  /**
   * Galaxy size configuration for small galaxy
   */
  private static final int GALAXY_SIZE_SMALL = 75;
  /**
   * Galaxy size configuration for medium galaxy
   */
  private static final int GALAXY_SIZE_MEDIUM = 128;
  /**
   * Galaxy size configuration for large galaxy
   */
  private static final int GALAXY_SIZE_LARGE = 160;
  /**
   * Galaxy size configuration for very large galaxy
   */
  private static final int GALAXY_SIZE_VERY_LARGE = 200;
  /**
   * Galaxy size configuration for huge galaxy
   */
  private static final int GALAXY_SIZE_HUGE = 256;
  /**
   * Handle actions for Galaxy Creation view
   * @param arg0 The event to handle
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_GALAXY_SETUP)) {
      SoundPlayer.playMenuSound();
      config.setEnableTutorial(tutorialEnabled.isSelected());
      config.setMaxPlayers(comboPlayers.getSelectedIndex() + 2);
      config.setStartingPosition(comboPlayerPos.getSelectedIndex());
      switch (comboGalaxySize.getSelectedIndex()) {
      case 0: {
        // Very small
        config.setSize(GALAXY_SIZE_VERY_SMALL,
            comboGalaxySize.getSelectedIndex());
        break;
      }
      case 1: {
        // Small
        config.setSize(GALAXY_SIZE_SMALL, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 2: {
        // Medium
        config.setSize(GALAXY_SIZE_MEDIUM, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 3: {
        // Large
        config.setSize(GALAXY_SIZE_LARGE, comboGalaxySize.getSelectedIndex());
        break;
      }
      case 4: {
        // Very Large
        config.setSize(GALAXY_SIZE_VERY_LARGE,
            comboGalaxySize.getSelectedIndex());
        break;
      }
      case 5: {
        // Huge
        config.setSize(GALAXY_SIZE_HUGE, comboGalaxySize.getSelectedIndex());
        break;
      }
      default: {
        // SMALL
        config.setSize(GALAXY_SIZE_SMALL, comboGalaxySize.getSelectedIndex());
        break;
      }
      }
      System.out.println("Players: " + config.getMaxPlayers());
      System.out.println("Size: " + config.getGalaxySizeIndex());
      if (config.getMaxPlayers() > 8 && config.getGalaxySizeIndex() < 2) {
        config.setSize(GALAXY_SIZE_MEDIUM, 2);
        comboGalaxySize.setSelectedIndex(2);
        this.repaint();
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
      switch (comboPlanetaryEvent.getSelectedIndex()) {
      case 0: {
        // None
        config.setChanceForPlanetaryEvent(0);
        break;
      }
      case 1: {
        // Very rare
        config.setChanceForPlanetaryEvent(5);
        break;
      }
      case 2: {
        // Rare
        config.setChanceForPlanetaryEvent(10);
        break;
      }
      case 3: {
        // Common
        config.setChanceForPlanetaryEvent(20);
        break;
      }
      case 4: {
        // Very Common
        config.setChanceForPlanetaryEvent(40);
        break;
      }
      default: {
        // Default
        config.setChanceForPlanetaryEvent(0);
        break;
      }
      }
      switch (comboRoguePlanets.getSelectedIndex()) {
      case 0: {
        // None
        config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_NONE);
        break;
      }
      case 1: {
        // Very few
        config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_VERY_FEW);
        break;
      }
      case 2: {
        // Few
        config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_FEW);
        break;
      }
      case 3: {
        // Some
        config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_SOME);
        break;
      }
      case 4: {
        // MANY
        config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_MANY);
        break;
      }
      default: {
        // Default
        config.setNumberOfRoguePlanets(GalaxyConfig.ROGUE_PLANETS_FEW);
        break;
      }
      }
      switch (comboAncientTurns.getSelectedIndex()) {
      case 0: {
        // Head start 40 turns
        config.setAncientHeadStart(40);
        break;
      }
      case 1: {
        // Head start 60 turns
        config.setAncientHeadStart(60);
        break;
      }
      default:
      case 2: {
        // Head start 80 turns
        config.setAncientHeadStart(80);
        break;
      }
      case 3: {
        // Head start 100 turns
        config.setAncientHeadStart(100);
        break;
      }
      case 4: {
        // Head start 120 turns
        config.setAncientHeadStart(120);
        break;
      }
      }
      config.setSpacePiratesLevel(comboSpacePirates.getSelectedIndex());
      config.setSpacePiratesDifficulty(PirateDifficultLevel.getLevelByInt(
          comboSpacePirateDifficulty.getSelectedIndex()));
      config.setSpaceAnomaliesLevel(comboSpaceAnomalies.getSelectedIndex());
      switch (comboScoringVictory.getSelectedIndex()) {
      case 0: {
        // 200 turns
        config.setScoringVictoryTurns(200);
        break;
      }
      case 1: {
        // 300 turns
        config.setScoringVictoryTurns(300);
        break;
      }
      case 2: {
        // 400 turns
        config.setScoringVictoryTurns(400);
        break;
      }
      case 3: {
        // 600 turns
        config.setScoringVictoryTurns(600);
        break;
      }
      case 4: {
        // 800 turns
        config.setScoringVictoryTurns(800);
        break;
      }
      case 5: {
        // 1000 turns
        config.setScoringVictoryTurns(1000);
        break;
      }
      default: {
        // Default
        config.setChanceForPlanetaryEvent(400);
        break;
      }
      }
      switch (comboScoringCulture.getSelectedIndex()) {
        case 0: {
          // Disabled
          config.setScoreLimitCulture(-1);
          break;
        }
        case 1: {
          // 75%
          config.setScoreLimitCulture(0);
          break;
        }
        case 2: {
          // 100%
          config.setScoreLimitCulture(1);
          break;
        }
        case 3: {
          // 150%
          config.setScoreLimitCulture(2);
          break;
        }
        case 4: {
          // 200%
          config.setScoreLimitCulture(3);
          break;
        }
        default: {
          // 100%
          config.setScoreLimitCulture(1);
          break;
        }
      }
      switch (comboScoringDomination.getSelectedIndex()) {
        case 0: {
          // Disabled
          config.setScoreLimitConquer(0);
          break;
        }
        case 1: {
          // enabled
          config.setScoreLimitConquer(1);
          break;
        }
        default: {
          // enabled
          config.setScoreLimitCulture(1);
          break;
        }
      }
      config.setScoreLimitResearch(comboScoringScientific.getSelectedIndex());
      config.setScoreLimitDiplomacy(comboScoringDiplomatic.getSelectedIndex());
      config.setScoreLimitPopulation(comboScoringPopulation.getSelectedIndex());
      config.setKarmaType(KarmaType.getTypeByInt(
          comboKarmaType.getSelectedIndex()));
      config.setKarmaSpeed(comboKarmaSpeed.getSelectedIndex() + 1);
    }
  }

  /**
   * Get Galaxy configuration
   * @return Galaxy configuration
   */
  public GalaxyConfig getConfig() {
    return config;
  }

}
