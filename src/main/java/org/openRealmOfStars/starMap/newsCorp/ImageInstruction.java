package org.openRealmOfStars.starMap.newsCorp;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2023 Tuomo Untinen
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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.graphs.BridgeGraphFactory;
import org.openRealmOfStars.gui.util.GraphRoutines;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceUtility;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Image instruction class.
*
*/
public class ImageInstruction {
  /**
   * Instructions for background
   */
  private static final String BACKGROUND = "background";
  /**
   * Background stars
   */
  public static final String BACKGROUND_STARS = "stars";
  /**
   * Background nebulae
   */
  public static final String BACKGROUND_NEBULAE = "nebulae";
  /**
   * Background black
   */
  public static final String BACKGROUND_BLACK = "black";
  /**
   * Background grey gradient
   */
  public static final String BACKGROUND_GREY_GRADIENT = "grey gradient";
  /**
   * Text color bright
   */
  public static final String TEXT_COLOR_BRIGHT = "bright";
  /**
   * Text color dark
   */
  public static final String TEXT_COLOR_DARK = "dark";
  /**
   * Instructions for texts
   */
  private static final String TEXT = "text";
  /**
   * Instructions for text color
   */
  private static final String TEXT_COLOR = "textColor";
  /**
   * Instructions for planet
   */
  private static final String PLANET = "planet";
  /**
   * Instructions for draw logo
   */
  private static final String DRAW_LOGO = "drawLogo";
  /**
   * Instructions for trader
   */
  private static final String TRADER = "trader";
  /**
   * Planet position in image: left
   */
  public static final String POSITION_LEFT = "position left";
  /**
   * Planet position in image: center
   */
  public static final String POSITION_CENTER = "position center";
  /**
   * Planet position in image: right
   */
  public static final String POSITION_RIGHT = "position right";
  /**
   * Size of the object, usually the planet. This is for full size.
   */
  public static final String SIZE_FULL = "full";
  /**
   * Size of the object, usually the planet. This is for half size.
   */
  public static final String SIZE_HALF = "half";
  /**
   * Planet type rock1
   */
  public static final String PLANET_ROCK1 = "rock1";
  /**
   * Planet type waterworld1
   */
  public static final String PLANET_WATERWORLD1 = "waterworld1";
  /**
   * Planet type waterworld2
   */
  public static final String PLANET_WATERWORLD2 = "waterworld2";
  /**
   * Planet type waterworld3
   */
  public static final String PLANET_WATERWORLD3 = "waterworld3";
  /**
   * Planet type waterworld4
   */
  public static final String PLANET_WATERWORLD4 = "waterworld4";
  /**
   * Planet type waterworld5
   */
  public static final String PLANET_WATERWORLD5 = "waterworld5";
  /**
   * Planet type waterworld6
   */
  public static final String PLANET_WATERWORLD6 = "waterworld6";
  /**
   * Planet type waterworld7
   */
  public static final String PLANET_WATERWORLD7 = "waterworld7";
  /**
   * Planet type waterworld8
   */
  public static final String PLANET_WATERWORLD8 = "waterworld8";
  /**
   * Planet type waterworld9
   */
  public static final String PLANET_WATERWORLD9 = "waterworld9";
  /**
   * Planet type volcanicworld1
   */
  public static final String PLANET_VOLCANICWORLD1 = "volcanicworld1";
  /**
   * Planet type volcanicworld2
   */
  public static final String PLANET_VOLCANICWORLD2 = "volcanicworld2";
  /**
   * Planet type volcanicworld3
   */
  public static final String PLANET_VOLCANICWORLD3 = "volcanicworld3";
  /**
   * Planet type volcanicworld4
   */
  public static final String PLANET_VOLCANICWORLD4 = "volcanicworld4";
  /**
   * Planet type volcanicworld5
   */
  public static final String PLANET_VOLCANICWORLD5 = "volcanicworld5";
  /**
   * Planet type volcanicworld6
   */
  public static final String PLANET_VOLCANICWORLD6 = "volcanicworld6";
  /**
   * Planet type iceworld1
   */
  public static final String PLANET_ICEWORLD1 = "iceworld1";
  /**
   * Planet type iceworld2
   */
  public static final String PLANET_ICEWORLD2 = "iceworld2";
  /**
   * Planet type iceworld3
   */
  public static final String PLANET_ICEWORLD3 = "iceworld3";
  /**
   * Planet type iceworld4
   */
  public static final String PLANET_ICEWORLD4 = "iceworld3";
  /**
   * Planet type swamp world 1
   */
  public static final String PLANET_SWAMPWORLD1 = "swampworld1";
  /**
   * Planet type swamp world 2
   */
  public static final String PLANET_SWAMPWORLD2 = "swampworld2";
  /**
   * Planet type swamp world 3
   */
  public static final String PLANET_SWAMPWORLD3 = "swampworld3";
  /**
   * Planet type gas giant 1
   */
  public static final String PLANET_GASGIANT1 = "gasgiant1";
  /**
   * Planet type gas giant 2
   */
  public static final String PLANET_GASGIANT2 = "gasgiant2";
  /**
   * Planet type gas giant 3
   */
  public static final String PLANET_GASGIANT3 = "gasgiant3";
  /**
   * Planet type desert world 1
   */
  public static final String PLANET_DESERTWORLD1 = "desertworld1";
  /**
   * Planet type desert world 2
   */
  public static final String PLANET_DESERTWORLD2 = "desertworld2";
  /**
   * Planet type desert world 3
   */
  public static final String PLANET_DESERTWORLD3 = "desertworld3";
  /**
   * Planet type artificial world 1
   */
  public static final String PLANET_ARTIFICIALWORLD1 = "artificialtworld1";
  /**
   * Planet type Earth
   */
  public static final String PLANET_EARTH = "earth";
  /**
   * Planet type Mars
   */
  public static final String PLANET_MARS = "mars";
  /**
   * Planet type Jupiter
   */
  public static final String PLANET_JUPITER = "jupiter";
  /**
   * Planet type Saturn
   */
  public static final String PLANET_SATURN = "saturn";
  /**
   * Planet type Icegiant1
   */
  public static final String PLANET_ICEGIANT1 = "icegiant1";
  /**
   * Planet type Icegiant2
   */
  public static final String PLANET_ICEGIANT2 = "icegiant2";
  /**
   * Sports logo around the planet image
   */
  public static final String PLANET_SPORTS = "sportslogo";
  /**
   * Trader ship 1
   */
  public static final String TRADER1 = "trader1";
  /**
   * Trader ship 2
   */
  public static final String TRADER2 = "trader2";
  /**
   * Cloaked ship
   */
  public static final String CLOAKED_SHIP = "cloaked-ship";
  /**
   * Factory
   */
  public static final String FACTORY = "factory";
  /**
   * Shuttle2
   */
  public static final String SHUTTLE2 = "shuttle2";
  /**
   * Instructions for Relation symbol between two text
   */
  private static final String RELATION_SYMBOL = "relation_symbol";
  /**
   * Relation symbol peace
   */
  public static final String PEACE = "peace";
  /**
   * Relation symbol peace
   */
  public static final String WAR = "war";
  /**
   * Relation symbol trade alliance
   */
  public static final String TRADE_ALLIANCE = "trade alliance";
  /**
   * Relation symbol trade embargo
   */
  public static final String TRADE_EMBARGO = "trade embargo";
  /**
   * Relation symbol alliance
   */
  public static final String ALLIANCE = "alliance";
  /**
   * Relation defensive pact
   */
  public static final String DEFENSIVE_PACT = "defensive pact";

  /**
   * GBNC Logo
   */
  public static final String LOGO = "logo";
  /**
   * Big ban icon
   */
  public static final String BIG_BAN = "big ban";
  /**
   * Big peace icon
   */
  public static final String BIG_PEACE = "big peace";
  /**
   * Big nuke
   */
  public static final String BIG_NUKE = "big nuke";
  /**
   * Big orbital
   */
  public static final String BIG_ORBITAL = "big orbital";
  /**
   * Big explosion
   */
  public static final String BIG_EXPLOSION = "big explosion";
  /**
   * Galaxy image
   */
  public static final String GALAXY = "galaxy";
  /**
   * Big privateer
   */
  public static final String BIG_PRIVATEER = "big privateer";
  /**
   * Pirate pilot
   */
  public static final String PIRATE_PILOT = "pirate pilot";
  /**
   * Kraken
   */
  public static final String KRAKEN = "kraken";
  /**
   * Pirate raiders
   */
  public static final String PIRATE_RAIDERS = "pirate raiders";
  /**
   * Big corvette image
   */
  public static final String CORVETTE = "corvette";
  /**
   * Big cruiser image
   */
  public static final String CRUISER = "cruiser";
  /**
   * Mutiny
   */
  public static final String MUTINY = "mutiny";
  /**
   * Dataloss
   */
  public static final String DATALOSS = "dataloss";
  /**
   * Newstation
   */
  public static final String NEWSTATION = "newstation";
  /**
   * Old ship
   */
  public static final String OLD_SHIP = "old ship";
  /**
   * Alien ship
   */
  public static final String ALIEN_SHIP = "alien ship";
  /**
   * United Galaxy Tower
   */
  public static final String UNITED_GALAXY_TOWER = "united galaxy tower";
  /**
   * Big missile
   */
  public static final String BIG_MISSILE = "big missile";
  /**
   * Big money
   */
  public static final String BIG_MONEY = "big money";
  /**
   * Solar with flares
   */
  public static final String SOLAR_FLARES = "solar flares";
  /**
   * Solar without flares
   */
  public static final String SOLAR_NO_FLARES = "solar no flares";
  /**
   * Desert image
   */
  public static final String DESERT = "desert";
  /**
   * Paradise image
   */
  public static final String PARADISE = "paradise";
  /**
   * Viruses image
   */
  public static final String VIRUSES = "viruses";
  /**
   * Signal image
   */
  public static final String SIGNAL = "signal";
  /**
   * Technical breakthrough image
   */
  public static final String TECHNICAL_BREAKTHROUGH = "technical breakthrough";
  /**
   * Meteor image
   */
  public static final String METEOR = "meteor";
  /**
   * Meteor image
   */
  public static final String METEOR_HIT = "meteor_hit";
  /**
   * Instructions for ship
   */
  public static final String SHIP = "ship";
  /**
   * Instructions for ship destroyed
   */
  public static final String SHIP_DESTROYED = "ship_destroyed";
  /**
   * Instructions for shuttle
   */
  public static final String SHUTTLE = "shuttle";
  /**
   * Instructions for spinosaurus
   */
  public static final String SPINOSAURUS = "spinosaurus";
  /**
   * Instructions for stasis
   */
  public static final String STASIS = "stasis";
  /**
   * Instructions for containers
   */
  public static final String CONTAINERS = "containers";
  /**
   * Instructions for terror
   */
  public static final String TERROR = "terror";
  /**
   * Instructions for old desk
   */
  public static final String OLD_DESK = "olddesk";
  /**
   * Instructions for metal rich surface
   */
  public static final String METAL_RICH_SURFACE = "metalRichSurface";
  /**
   * Instructions for precious gems
   */
  public static final String PRECIOUS_GEMS = "PreciousGems";
  /**
   * Instructions for ancient laboratory
   */
  public static final String ANCIENT_LABORATORY = "AncientLaboratory";
  /**
   * Instructions for ancient research
   */
  public static final String ANCIENT_RESEARCH = "AncientResearch";
  /**
   * Instructions for ancient factory
   */
  public static final String ANCIENT_FACTORY = "AncientFactory";
  /**
   * Instructions for ancient temple
   */
  public static final String ANCIENT_TEMPLE = "AncientTemple";
  /**
   * Instructions for ancient palace
   */
  public static final String ANCIENT_PALACE = "AncientPalace";
  /**
   * Instructions for black monolith
   */
  public static final String BLACK_MONOLITH = "BlackMonolith";
  /**
   * Instructions for molten lava
   */
  public static final String MOLTEN_LAVA = "MoltenLava";
  /**
   * Instructions for Arid
   */
  public static final String ARID = "Arid";
  /**
   * Instructions for Lush Vegetation
   */
  public static final String LUSH_VEGETATION = "LushVegetation";
  /**
   * Instructions for Artifact on planet
   */
  public static final String ARTIFACT_ON_PLANET = "ArtifactOnPlanet";

  /** Instruction to draw image */
  private static final String IMAGE = "image";
  /** Instruction to draw captain image */
  private static final String CAPTAIN = "captain";
  /** Instruction to draw space ship bridge */
  private static final String BRIDGE = "bridge";
  /** Instruction to draw silhouette */
  private static final String SILHOUETTE = "silhouette";

  /** Character for starting parameters */
  private static final char PARAM_START = '(';
  /** Character for ending parameters */
  private static final char PARAM_END = ')';
  /** Instruction delimiter */
  private static final char INSTRUCTION_DELIM = '+';
  /** Parameter delimiter */
  private static final char PARAMETER_DELIM = ',';

  /** String builder used to collect all the instructions */
  private StringBuilder sb;

  /**
   * Constructor for Image instruction
   */
  public ImageInstruction() {
    sb = new StringBuilder();
  }

  /**
   * Add instruction from another string.
   * This does not do sanitation.
   * @param instruction Instruction coomands
   */
  public void addInstruction(final String instruction) {
    checkDelim();
    sb.append(instruction);
  }

  /**
   * Check if there is instruction delimiter if it is missing
   * this will add it there too.
   */
  private void checkDelim() {
    if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '+') {
      sb.append(INSTRUCTION_DELIM);
    }
  }

  /**
   * Sanitize parameters. Removes all delimiter from parameters
   * @param parameter As a String
   * @return Sanitized parameter
   */
  private static String sanitizeParameters(final String parameter) {
    StringBuilder paramBuilder = new StringBuilder();
    for (int i = 0; i < parameter.length(); i++) {
      if (parameter.charAt(i) != PARAM_END
          && parameter.charAt(i) != PARAM_START
          && parameter.charAt(i) != INSTRUCTION_DELIM
          && parameter.charAt(i) != PARAMETER_DELIM) {
        paramBuilder.append(parameter.charAt(i));
      }
    }
    return paramBuilder.toString();
  }
  /**
   * Add background to image instructions.
   * If instruction is something unexpected then black is background
   * is being drawn.
   * @param backgroundName Background name:
   *        BACKGROUND_STARS,
   *        BACKGROUND_NEBULAE,
   *        BACKGROUND_BLACK,
   *        BACKGROUND_GREY_GRADIENT
   * @return ImageInstruction with background
   */
  public ImageInstruction addBackground(final String backgroundName) {
    checkDelim();
    sb.append(BACKGROUND);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(backgroundName));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add text to image instructions.
   * These are added as a centered rows.
   * @param paramText Text to show in image
   * @return ImageInstruction with text
   */
  public ImageInstruction addText(final String paramText) {
    checkDelim();
    sb.append(TEXT);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(paramText));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add text color to image instructions.
   * @param color Text color
   * @return ImageInstruction with text color
   */
  public ImageInstruction addTextColor(final String color) {
    checkDelim();
    if (!TEXT_COLOR_BRIGHT.equals(color)
        && !TEXT_COLOR_DARK.equals(color)) {
      throw new IllegalArgumentException("Illegal text color: "
        + color);
    }
    sb.append(TEXT_COLOR);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(color));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add relation symbol to image instructions.
   * These are added as a centered rows.
   * @param symbol Relation symbol to show
   * @return ImageInstruction with text
   */
  public ImageInstruction addRelationSymbol(final String symbol) {
    checkDelim();
    if (!PEACE.equals(symbol)
        && !WAR.equals(symbol)
        && !TRADE_ALLIANCE.equals(symbol)
        && !ALLIANCE.equals(symbol)
        && !DEFENSIVE_PACT.equals(symbol)
        && !TRADE_EMBARGO.equals(symbol)) {
      throw new IllegalArgumentException("Illegal relation symbol: "
        + symbol);
    }
    sb.append(RELATION_SYMBOL);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(symbol));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add another captain image to image instructions.
   * Image is added into center of image, but Y-axel is adjustable.
   * @param image image name to place into image
   * @param adjust Y-pixel adjustment
   * @return ImageInstruction with text
   */
  public ImageInstruction addCaptain(final String image, final int adjust) {
    checkDelim();
    if (SpaceRaceUtility.getRaceByName(image) == null) {
      throw new IllegalArgumentException("Illegal captain image: " + image);
    }
    sb.append(CAPTAIN);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(image));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(Integer.toString(adjust)));
    sb.append(PARAM_END);
    return this;

  }

  /**
   * Add another image to image instructions.
   * Image is added into center of image
   * @param image image name to place into image
   * @return ImageInstruction with text
   */
  public ImageInstruction addImage(final String image) {
    checkDelim();
    if (SpaceRaceUtility.getRaceByName(image) == null
        && !LOGO.equals(image)
        && !BIG_BAN.equals(image)
        && !BIG_PEACE.equals(image)
        && !BIG_NUKE.equals(image)
        && !BIG_PRIVATEER.equals(image)
        && !GALAXY.equals(image)
        && !SOLAR_FLARES.equals(image)
        && !SOLAR_NO_FLARES.equals(image)
        && !PIRATE_PILOT.equals(image)
        && !KRAKEN.equals(image)
        && !PIRATE_RAIDERS.equals(image)
        && !CORVETTE.equals(image)
        && !CRUISER.equals(image)
        && !MUTINY.equals(image)
        && !DATALOSS.equals(image)
        && !NEWSTATION.equals(image)
        && !OLD_SHIP.equals(image)
        && !ALIEN_SHIP.equals(image)
        && !DESERT.equals(image)
        && !PARADISE.equals(image)
        && !VIRUSES.equals(image)
        && !METEOR.equals(image)
        && !SIGNAL.equals(image)
        && !TECHNICAL_BREAKTHROUGH.equals(image)
        && !SHIP_DESTROYED.equals(image)
        && !SHUTTLE.equals(image)
        && !SPINOSAURUS.equals(image)
        && !CONTAINERS.equals(image)
        && !TERROR.equals(image)
        && !OLD_DESK.equals(image)
        && !METAL_RICH_SURFACE.equals(image)
        && !PRECIOUS_GEMS.equals(image)
        && !ANCIENT_LABORATORY.equals(image)
        && !ANCIENT_RESEARCH.equals(image)
        && !ANCIENT_FACTORY.equals(image)
        && !ANCIENT_TEMPLE.equals(image)
        && !ANCIENT_PALACE.equals(image)
        && !BLACK_MONOLITH.equals(image)
        && !MOLTEN_LAVA.equals(image)
        && !ARID.equals(image)
        && !LUSH_VEGETATION.equals(image)
        && !ARTIFACT_ON_PLANET.equals(image)
        && !FACTORY.equals(image)) {
      throw new IllegalArgumentException("Illegal image: " + image);
    }
    sb.append(IMAGE);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(image));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add space ship bridge to image instructions.
   * Use space race single names.
   * Image is added into center of image
   * @param image image name to place into image
   * @return ImageInstruction with text
   */
  public ImageInstruction addBridge(final String image) {
    checkDelim();
    if (SpaceRaceUtility.getRaceByName(image) == null) {
      throw new IllegalArgumentException("Illegal image: " + image);
    }
    sb.append(BRIDGE);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(image));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add another image silhouette to image instructions.
   * @param image image name to place into image
   * @param position Where silhouette is going go be drawn.
   *        POSITION_CENTER, POSITION_LEFT, POSITION_RIGHT
   * @return ImageInstruction with text
   */
  public ImageInstruction addSilhouette(final String image,
      final String position) {
    checkDelim();
    if (SpaceRaceUtility.getRaceByName(image) == null) {
      throw new IllegalArgumentException("Illegal image: " + image);
    }
    if (!POSITION_CENTER.equals(position)
        && !POSITION_LEFT.equals(position)
        && !POSITION_RIGHT.equals(position)) {
      throw new IllegalArgumentException("Illegal logo position: "
        + position);
    }
    sb.append(SILHOUETTE);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(image));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(position));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Adds planet to the image.
   * @param position Three choices: left, center and right
   * @param planetType planet type. Choices are:
   *        rock1, waterworld1, waterworld2,
   *        ironworld1, ironworld2, gasgiant1,
   *        gasgiant2
   * @param size Two choices half or full
   * @return ImageInstruction with text
   * @throws IllegalArgumentException If position or planet type are illegal
   */
  public ImageInstruction addPlanet(final String position,
      final String planetType, final String size)
      throws IllegalArgumentException {
    if (!POSITION_CENTER.equals(position)
        && !POSITION_LEFT.equals(position)
        && !POSITION_RIGHT.equals(position)) {
      throw new IllegalArgumentException("Illegal planet position: "
        + position);
    }
    if (!PlanetTypes.verifyInstruction(planetType)) {
      throw new IllegalArgumentException("Illegal planet type: " + planetType);
    }
    if (!SIZE_FULL.equals(size)
        && !SIZE_HALF.equals(size)) {
      throw new IllegalArgumentException("Illegal size: " + size);
    }
    checkDelim();
    sb.append(PLANET);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(position));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(planetType));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(size));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Adds logo to the image. Logo can be almost any image including
   * space race, planet, or big icon.
   * @param position Three choices: left, center and right
   * @param logoType logo type.
   * @param size Two choices half or full
   * @return ImageInstruction with text
   * @throws IllegalArgumentException If position or planet type are illegal
   */
  public ImageInstruction addLogo(final String position,
      final String logoType, final String size)
      throws IllegalArgumentException {
    if (!POSITION_CENTER.equals(position)
        && !POSITION_LEFT.equals(position)
        && !POSITION_RIGHT.equals(position)) {
      throw new IllegalArgumentException("Illegal logo position: "
        + position);
    }
    if (SpaceRaceUtility.getRaceByName(logoType) == null
        && !PLANET_SPORTS.equals(logoType)
        && !BIG_BAN.equals(logoType)
        && !BIG_PEACE.equals(logoType)
        && !BIG_NUKE.equals(logoType)
        && !BIG_PRIVATEER.equals(logoType)
        && !GALAXY.equals(logoType)
        && !UNITED_GALAXY_TOWER.equals(logoType)
        && !BIG_MISSILE.equals(logoType)
        && !BIG_MONEY.equals(logoType)
        && !METEOR.equals(logoType)
        && !METEOR_HIT.equals(logoType)
        && !BIG_EXPLOSION.equals(logoType)
        && !BIG_ORBITAL.equals(logoType)) {
      throw new IllegalArgumentException("Illegal logo type: " + logoType);
    }
    if (!SIZE_FULL.equals(size)
        && !SIZE_HALF.equals(size)) {
      throw new IllegalArgumentException("Illegal size: " + size);
    }
    checkDelim();
    sb.append(DRAW_LOGO);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(position));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(logoType));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(size));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Adds Trader to the image.
   * @param position Three choices: left, center and right
   * @param trader Trader type. Choices are:
   *        trader1, trader2
   * @param size Two choices half or full
   * @return ImageInstruction with text
   * @throws IllegalArgumentException If position or planet type are illegal
   */
  public ImageInstruction addTrader(final String position,
      final String trader, final String size)
      throws IllegalArgumentException {
    if (!POSITION_CENTER.equals(position)
        && !POSITION_LEFT.equals(position)
        && !POSITION_RIGHT.equals(position)) {
      throw new IllegalArgumentException("Illegal trader position: "
        + position);
    }
    if (!TRADER1.equals(trader) && !TRADER2.equals(trader)
        && !CLOAKED_SHIP.equals(trader) && !SHUTTLE2.equals(trader)) {
      throw new IllegalArgumentException("Illegal trader type: " + trader);
    }
    if (!SIZE_FULL.equals(size)
        && !SIZE_HALF.equals(size)) {
      throw new IllegalArgumentException("Illegal size: " + size);
    }
    checkDelim();
    sb.append(TRADER);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(position));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(trader));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(size));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Build actual instructions
   * @return instruction string
   */
  public String build() {
    return toString();
  }

  @Override
  public String toString() {
    return sb.toString();
  }

  /**
   * Paint background
   * @param workImage Image where to draw background
   * @param backgroundType Background type
   */
  private static void paintBackground(final BufferedImage workImage,
      final String backgroundType) {
    if (BACKGROUND_BLACK.equals(backgroundType)) {
      Graphics2D g = (Graphics2D) workImage.getGraphics();
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
    }
    if (BACKGROUND_GREY_GRADIENT.equals(backgroundType)) {
      Graphics2D g = (Graphics2D) workImage.getGraphics();
      GradientPaint gradient = new GradientPaint(workImage.getWidth() / 2,
          0, Color.BLACK, workImage.getWidth() / 2,
          workImage.getHeight() - 1, GuiStatics.COLOR_GREY_40, true);
      g.setPaint(gradient);
      g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
    }
    if (BACKGROUND_STARS.equals(backgroundType)) {
      Graphics2D g = (Graphics2D) workImage.getGraphics();
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
      g.drawImage(GuiStatics.getStarField(), 0, 0, null);
    }
    if (BACKGROUND_NEBULAE.equals(backgroundType)) {
      Graphics2D g = (Graphics2D) workImage.getGraphics();
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
      g.drawImage(GuiStatics.getStarField(), -50, -50, null);
      g.drawImage(GuiStatics.NEBULAE_IMAGE, -25, -25, null);
    }
  }

  /**
   * Draw planet on image
   * @param workImage Image where to draw
   * @param planetType Planet type to draw
   * @param position LEFT, CENTER or RIGHT
   * @param size HALF or FULL
   */
  private static void paintPlanet(final BufferedImage workImage,
      final String planetType, final String position, final String size) {
    BufferedImage planetImg = GuiStatics.BIG_PLANET_ROCK1;
    if (PLANET_ROCK1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ROCK1;
    }
    if (PLANET_WATERWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD1;
    }
    if (PLANET_WATERWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD2;
    }
    if (PLANET_WATERWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD3;
    }
    if (PLANET_WATERWORLD4.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD4;
    }
    if (PLANET_WATERWORLD5.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD5;
    }
    if (PLANET_WATERWORLD6.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD6;
    }
    if (PLANET_WATERWORLD7.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD7;
    }
    if (PLANET_WATERWORLD8.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD8;
    }
    if (PLANET_WATERWORLD9.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_WATERWORLD9;
    }
    if (PLANET_ICEWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ICEWORLD1;
    }
    if (PLANET_ICEWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ICEWORLD2;
    }
    if (PLANET_ICEWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ICEWORLD3;
    }
    if (PLANET_ICEWORLD4.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ICEWORLD4;
    }
    if (PLANET_SWAMPWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_SWAMPWORLD1;
    }
    if (PLANET_SWAMPWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_SWAMPWORLD2;
    }
    if (PLANET_SWAMPWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_SWAMPWORLD3;
    }
    if (PLANET_DESERTWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_DESERTWORLD1;
    }
    if (PLANET_DESERTWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_DESERTWORLD2;
    }
    if (PLANET_DESERTWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_DESERTWORLD3;
    }
    if (PLANET_ARTIFICIALWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ARTIFICIALPLANET1;
    }
    if (PLANET_EARTH.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_EARTH;
    }
    if (PLANET_MARS.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_MARS;
    }
    if (PLANET_JUPITER.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_JUPITER;
    }
    if (PLANET_SATURN.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_SATURN;
    }
    if (PLANET_VOLCANICWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_VOLCANICPLANET1;
    }
    if (PLANET_VOLCANICWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_VOLCANICPLANET2;
    }
    if (PLANET_VOLCANICWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_VOLCANICPLANET3;
    }
    if (PLANET_VOLCANICWORLD4.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_VOLCANICPLANET4;
    }
    if (PLANET_VOLCANICWORLD5.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_VOLCANICPLANET5;
    }
    if (PLANET_VOLCANICWORLD6.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_VOLCANICPLANET6;
    }
    if (PLANET_GASGIANT1.equals(planetType)) {
      planetImg = GuiStatics.BIG_GASWORLD1;
    }
    if (PLANET_GASGIANT2.equals(planetType)) {
      planetImg = GuiStatics.BIG_GASWORLD2;
    }
    if (PLANET_GASGIANT3.equals(planetType)) {
      planetImg = GuiStatics.BIG_GASWORLD3;
    }
    if (PLANET_GASGIANT3.equals(planetType)) {
      planetImg = GuiStatics.BIG_GASWORLD3;
    }
    if (PLANET_SPORTS.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.BIG_SPORT_LOGO);
    }
    if (BIG_BAN.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_BAN_ICON);
    }
    if (BIG_PEACE.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_PEACE_ICON);
    }
    if (BIG_NUKE.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_NUKE);
    }
    if (GALAXY.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_GALAXY);
    }
    if (BIG_PRIVATEER.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_PRIVATEER);
    }
    if (UNITED_GALAXY_TOWER.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_UNITED_GALAXY_TOWER);
    }
    if (BIG_MISSILE.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_MISSILE);
    }
    if (BIG_MONEY.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_MONEY);
    }
    if (METEOR.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_METEOR);
    }
    if (METEOR_HIT.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_METEOR_HIT);
    }
    if (BIG_EXPLOSION.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_EXPLOSION);
    }
    if (BIG_ORBITAL.equals(planetType)) {
      planetImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_ORBITAL);
    }
    SpaceRace race = SpaceRaceUtility.getRaceByName(planetType);
    if (race != null) {
      planetImg = GuiStatics.getRaceImg(race);
    }
    if (SpaceRace.SPACE_PIRATE.getNameSingle().equals(planetType)) {
      planetImg = GuiStatics.getRaceImg(SpaceRace.SPACE_PIRATE);
    }
    if (SIZE_HALF.equals(size)) {
      planetImg = GuiStatics.scaleToHalf(planetImg);
    }
    Graphics2D g = (Graphics2D) workImage.getGraphics();
    if (POSITION_CENTER.equals(position)) {
      g.drawImage(planetImg,
          workImage.getWidth() / 2 - planetImg.getWidth() / 2,
          workImage.getHeight() / 2 - planetImg.getHeight() / 2, null);
    }
    if (POSITION_LEFT.equals(position)) {
      g.drawImage(planetImg,
          workImage.getWidth() / 5 - planetImg.getWidth() / 2,
          workImage.getHeight() / 2 - planetImg.getHeight() / 2, null);
    }
    if (POSITION_RIGHT.equals(position)) {
      g.drawImage(planetImg,
          (workImage.getWidth() - workImage.getWidth() / 5)
          - planetImg.getWidth() / 2,
          workImage.getHeight() / 2 - planetImg.getHeight() / 2, null);
    }
  }
  /**
   * Draw trader on image
   * @param workImage Image where to draw
   * @param traderType Trader type to draw
   * @param position LEFT, CENTER or RIGHT
   * @param size HALF or FULL
   */
  private static void paintTrader(final BufferedImage workImage,
      final String traderType, final String position, final String size) {
    BufferedImage traderImg = IOUtilities.loadImage(
        GuiStatics.IMAGE_TRADE_SHIP);
    if (TRADER1.equals(traderType)) {
      traderImg = IOUtilities.loadImage(GuiStatics.IMAGE_TRADE_SHIP);
    }
    if (TRADER2.equals(traderType)) {
      traderImg = IOUtilities.loadImage(GuiStatics.IMAGE_TRADE_SHIP2);
    }
    if (CLOAKED_SHIP.equals(traderType)) {
      traderImg = IOUtilities.loadImage(GuiStatics.IMAGE_CLOAKED_SHIP);
    }
    if (SHUTTLE2.equals(traderType)) {
      traderImg = IOUtilities.loadImage(GuiStatics.IMAGE_SHUTTLE2);
    }
    if (SIZE_HALF.equals(size)) {
      traderImg = GuiStatics.scaleToHalf(traderImg);
    }
    Graphics2D g = (Graphics2D) workImage.getGraphics();
    if (POSITION_CENTER.equals(position)) {
      g.drawImage(traderImg,
          workImage.getWidth() / 2 - traderImg.getWidth() / 2,
          workImage.getHeight() / 2 - traderImg.getHeight() / 2, null);
    }
    if (POSITION_LEFT.equals(position)) {
      g.drawImage(traderImg,
          workImage.getWidth() / 5 - traderImg.getWidth() / 2,
          workImage.getHeight() / 2 - traderImg.getHeight() / 2, null);
    }
    if (POSITION_RIGHT.equals(position)) {
      g.drawImage(traderImg,
          (workImage.getWidth() - workImage.getWidth() / 5)
          - traderImg.getWidth() / 2,
          workImage.getHeight() / 2 - traderImg.getHeight() / 2, null);
    }
  }

  /**
   * Draw silhouette on image
   * @param workImage Image where to draw
   * @param raceName Race whom's silhouette is drawn
   * @param position LEFT, CENTER or RIGHT
   */
  private static void paintSilhouette(final BufferedImage workImage,
      final String raceName, final String position) {
    BufferedImage silhoutteImg = GuiStatics.IMAGE_HUMAN_RACE;
    SpaceRace race = SpaceRaceUtility.getRaceByName(raceName);
    if (race != null) {
      silhoutteImg = GuiStatics.getRaceImg(race);
    }
    silhoutteImg = GraphRoutines.blackSilhouette(silhoutteImg);

    Graphics2D g = (Graphics2D) workImage.getGraphics();
    if (POSITION_CENTER.equals(position)) {
      g.drawImage(silhoutteImg,
          workImage.getWidth() / 2 - silhoutteImg.getWidth() / 2,
          workImage.getHeight() / 2 - silhoutteImg.getHeight() / 2, null);
    }
    if (POSITION_LEFT.equals(position)) {
      g.drawImage(silhoutteImg,
          workImage.getWidth() / 5 - silhoutteImg.getWidth() / 2,
          workImage.getHeight() / 2 - silhoutteImg.getHeight() / 2, null);
    }
    if (POSITION_RIGHT.equals(position)) {
      g.drawImage(silhoutteImg,
          (workImage.getWidth() - workImage.getWidth() / 5)
          - silhoutteImg.getWidth() / 2,
          workImage.getHeight() / 2 - silhoutteImg.getHeight() / 2, null);
    }
  }

  /**
   * Draw image on image
   * @param workImage Image where to draw
   * @param image image type to draw
   * @return Drawn image
   */
  private static BufferedImage paintImage(final BufferedImage workImage,
      final String image) {
    BufferedImage drawImg = GuiStatics.BIG_PLANET_ROCK1;
    SpaceRace race = SpaceRaceUtility.getRaceByName(image);
    if (race != null) {
      drawImg = GuiStatics.getRaceImg(race);
    }
    if (SpaceRace.SPACE_MONSTERS.getNameSingle().equals(image)) {
      //FIXME: This might be needed for changing...
      drawImg = GuiStatics.getRaceImg(SpaceRace.SPACE_PIRATE);
    }
    if (SpaceRace.SPACE_PIRATE.getNameSingle().equals(image)) {
      drawImg = GuiStatics.getRaceImg(SpaceRace.SPACE_PIRATE);
    }
    if (LOGO.equals(image)) {
      drawImg = GuiStatics.IMAGE_GBNC;
    }
    if (BIG_BAN.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_BAN_ICON);
    }
    if (METEOR.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_METEOR);
    }
    if (BIG_PEACE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_PEACE_ICON);
    }
    if (BIG_NUKE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_NUKE);
    }
    if (GALAXY.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_GALAXY);
    }
    if (BIG_PRIVATEER.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_PRIVATEER);
    }
    if (SOLAR_FLARES.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_SOLAR_FLARES);
    }
    if (SOLAR_NO_FLARES.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_SOLAR_NO_FLARES);
    }
    if (PIRATE_PILOT.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_PIRATE_PILOT);
    }
    if (KRAKEN.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_KRAKEN);
    }
    if (PIRATE_RAIDERS.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_PIRATE_RAIDERS);
    }
    if (CORVETTE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_CORVETTE);
    }
    if (CRUISER.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_CRUISER);
    }
    if (MUTINY.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_MUTINY);
    }
    if (DATALOSS.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_DATALOSS);
    }
    if (NEWSTATION.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_NEWSTATION);
    }
    if (OLD_SHIP.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_OLD_SHIP);
    }
    if (ALIEN_SHIP.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ALIEN_SHIP);
    }
    if (DESERT.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_DESERT);
    }
    if (PARADISE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_PARADISE);
    }
    if (VIRUSES.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_VIRUSES);
    }
    if (SIGNAL.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_SIGNAL);
    }
    if (TECHNICAL_BREAKTHROUGH.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_TECHNICAL_BREAKTHROUGH);
    }
    if (SHIP_DESTROYED.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_SHIP_DESTROYED);
    }
    if (SHUTTLE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_SHUTTLE);
    }
    if (SPINOSAURUS.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_SPINOSAURUS);
    }
    if (STASIS.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_STASIS);
    }
    if (CONTAINERS.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_CONTAINERS);
    }
    if (TERROR.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_TERROR);
    }
    if (METAL_RICH_SURFACE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_METAL_RICH_SURFACE);
    }
    if (PRECIOUS_GEMS.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_PRECIOUS_GEMS);
    }
    if (ANCIENT_FACTORY.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ANCIENT_FACTORY);
    }
    if (ANCIENT_LABORATORY.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ANCIENT_LABORATORY);
    }
    if (ANCIENT_RESEARCH.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ANCIENT_RESEARCH);
    }
    if (ANCIENT_TEMPLE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ANCIENT_TEMPLE);
    }
    if (ANCIENT_PALACE.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ANCIENT_PALACE);
    }
    if (BLACK_MONOLITH.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_BLACK_MONOLITH);
    }
    if (BIG_ORBITAL.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_ORBITAL);
    }
    if (BIG_EXPLOSION.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_BIG_EXPLOSION);
    }
    if (MOLTEN_LAVA.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_MOLTEN_LAVA);
    }
    if (ARID.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ARID);
    }
    if (LUSH_VEGETATION.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_LUSH_VEGETATION);
    }
    if (ARTIFACT_ON_PLANET.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_ARTIFACT_ON_PLANET);
    }
    if (FACTORY.equals(image)) {
      drawImg = IOUtilities.loadImage(GuiStatics.IMAGE_FACTORY);
    }
    if (OLD_DESK.equals(image)) {
      drawImg = GuiStatics.IMAGE_DESKTOP;
    }
    BufferedImage img = workImage;
    if (img == null) {
      img = new BufferedImage(drawImg.getWidth(), drawImg.getHeight(),
          BufferedImage.TYPE_INT_ARGB);
    }
    Graphics2D g = (Graphics2D) img.getGraphics();
    g.drawImage(drawImg,
        img.getWidth() / 2 - drawImg.getWidth() / 2,
        img.getHeight() / 2 - drawImg.getHeight() / 2, null);
    return img;
  }

  /**
   * Draw captain image on image
   * @param workImage Image where to draw
   * @param image image type to draw
   * @param adjust Y-axel adjustment
   * @return Drawn image
   */
  private static BufferedImage paintCaptainImage(final BufferedImage workImage,
      final String image, final String adjust) {
    BufferedImage drawImg = GuiStatics.IMAGE_HUMAN_RACE;
    SpaceRace race = SpaceRaceUtility.getRaceByName(image);
    if (race != null) {
      drawImg = GuiStatics.getRaceImg(race);
    }
    BufferedImage img = workImage;
    if (img == null) {
      img = new BufferedImage(drawImg.getWidth(), drawImg.getHeight(),
          BufferedImage.TYPE_INT_ARGB);
    }
    int adjustment = Integer.parseInt(adjust);
    Graphics2D g = (Graphics2D) img.getGraphics();
    g.drawImage(drawImg,
        img.getWidth() / 2 - drawImg.getWidth() / 2,
        img.getHeight() - drawImg.getHeight() - adjustment, null);
    return img;
  }

  /**
   * Draw space ship bridge on image
   * @param workImage Image where to draw
   * @param image space ship image to draw.
   * @return Drawn image
   */
  private static BufferedImage paintBridge(final BufferedImage workImage,
      final String image) {
    BufferedImage drawImg = GuiStatics.BIG_PLANET_ROCK1;
    drawImg = BridgeGraphFactory.create(image).getBridgeImage();
    BufferedImage img = workImage;
    if (img == null) {
      img = new BufferedImage(drawImg.getWidth(), drawImg.getHeight(),
          BufferedImage.TYPE_INT_ARGB);
    }
    Graphics2D g = (Graphics2D) img.getGraphics();
    g.drawImage(drawImg,
        img.getWidth() / 2 - drawImg.getWidth() / 2,
        img.getHeight() / 2 - drawImg.getHeight() / 2, null);
    return img;
  }

  /**
   * Parse Image instruction string and draw image
   * to given image.
   * @param image Image where to draw
   * @param instructions Instruction as a String
   * @return BufferedImage with generated content
   */
  public static BufferedImage parseImageInstructions(final BufferedImage image,
      final String instructions) {
    int textY = 30;
    Color textColor = GuiStatics.getCoolSpaceColor();
    BufferedImage workImage = image;
    String[] lines = instructions.split("\\+");
    for (String line : lines) {
      String[] parts = line.split("\\(");
      if (parts.length != 2) {
        throw new IllegalArgumentException(
            "Command does not contain command and parameters: " + line);
      }
      String command = parts[0];
      String allParameters = parts[1];
      allParameters = allParameters.substring(0, allParameters.length() - 1);
      String[] parameters = allParameters.split(",");
      if (BACKGROUND.equals(command)) {
        // Background has only one parameter
        paintBackground(workImage, parameters[0]);
      }
      if (TEXT_COLOR.equals(command)) {
        if (parameters[0].equals(TEXT_COLOR_BRIGHT)) {
          textColor = GuiStatics.getCoolSpaceColor();
        }
        if (parameters[0].equals(TEXT_COLOR_DARK)) {
          textColor = GuiStatics.getCoolSpaceColorDarker();
        }
      }
      if (TEXT.equals(command)) {
        Graphics2D g = (Graphics2D) workImage.getGraphics();
        g.setColor(textColor);
        g.setFont(GuiFonts.getFontCubellanBoldBig());
        int textWidth = GuiStatics.getTextWidth(g.getFont(), parameters[0]);
        int height = GuiStatics.getTextHeight(g.getFont(), parameters[0]);
        g.drawString(parameters[0], workImage.getWidth() / 2 - textWidth / 2,
            textY);
        textY = textY + height * 2;
      }
      if (PLANET.equals(command) || DRAW_LOGO.equals(command)) {
        paintPlanet(workImage, parameters[1], parameters[0], parameters[2]);
      }
      if (TRADER.equals(command)) {
        paintTrader(workImage, parameters[1], parameters[0], parameters[2]);
      }
      if (IMAGE.equals(command)) {
        workImage = paintImage(workImage, parameters[0]);
      }
      if (CAPTAIN.equals(command)) {
        workImage = paintCaptainImage(workImage, parameters[0], parameters[1]);
      }
      if (IMAGE.equals(command)) {
        workImage = paintImage(workImage, parameters[0]);
      }
      if (BRIDGE.equals(command)) {
        workImage = paintBridge(workImage, parameters[0]);
      }
      if (SILHOUETTE.equals(command)) {
        paintSilhouette(workImage, parameters[0], parameters[1]);
      }
      if (RELATION_SYMBOL.equals(command)) {
        Graphics2D g = (Graphics2D) workImage.getGraphics();
        BufferedImage symbol = GuiStatics.RELATION_PEACE;
        if (PEACE.equals(parameters[0])) {
          symbol = GuiStatics.RELATION_PEACE;
        }
        if (WAR.equals(parameters[0])) {
          symbol = GuiStatics.RELATION_WAR;
        }
        if (TRADE_ALLIANCE.equals(parameters[0])) {
          symbol = GuiStatics.RELATION_TRADE_ALLIANCE;
        }
        if (TRADE_EMBARGO.equals(parameters[0])) {
          symbol = GuiStatics.RELATION_TRADE_EMBARGO;
        }
        if (ALLIANCE.equals(parameters[0])) {
          symbol = GuiStatics.RELATION_ALLIANCE;
        }
        if (DEFENSIVE_PACT.equals(parameters[0])) {
          symbol = GuiStatics.DEFENSIVE_PACT;
        }
        g.drawImage(symbol, workImage.getWidth() / 2 - symbol.getWidth() / 2,
            textY - symbol.getHeight() / 2, null);
        textY = textY + symbol.getHeight() * 2;
      }
    }
    return workImage;
  }
}
