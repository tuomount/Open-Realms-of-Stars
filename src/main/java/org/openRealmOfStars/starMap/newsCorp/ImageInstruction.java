package org.openRealmOfStars.starMap.newsCorp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.utilies.GraphRoutines;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.starMap.planet.PlanetTypes;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017-2021 Tuomo Untinen
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
   * Instructions for texts
   */
  private static final String TEXT = "text";
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
   * Planet type ironworld1
   */
  public static final String PLANET_IRONWORLD1 = "ironworld1";
  /**
   * Planet type ironworld2
   */
  public static final String PLANET_IRONWORLD2 = "ironworld2";
  /**
   * Planet type ironworld3
   */
  public static final String PLANET_IRONWORLD3 = "ironworld3";
  /**
   * Planet type ironworld4
   */
  public static final String PLANET_IRONWORLD4 = "ironworld4";
  /**
   * Planet type ironworld5
   */
  public static final String PLANET_IRONWORLD5 = "ironworld5";
  /**
   * Planet type ironworld6
   */
  public static final String PLANET_IRONWORLD6 = "ironworld6";
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
   * Planet type carbon world 1
   */
  public static final String PLANET_CARBONWORLD1 = "carbonworld1";
  /**
   * Planet type carbon world 2
   */
  public static final String PLANET_CARBONWORLD2 = "carbonworld2";
  /**
   * Planet type carbon world 3
   */
  public static final String PLANET_CARBONWORLD3 = "carbonworld3";
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
   * Pirate raiders
   */
  public static final String PIRATE_RAIDERS = "pirate raiders";
  /**
   * Mutiny
   */
  public static final String MUTINY = "mutiny";
  /**
   * Dataloss
   */
  public static final String DATALOSS = "dataloss";
  /**
   * Old ship
   */
  public static final String OLD_SHIP = "old ship";
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
   * Instructions for containers
   */
  public static final String CONTAINERS = "containers";

  /**
   * Instruction to draw image
   */
  private static final String IMAGE = "image";
  /**
   * Instruction to draw siluete
   */
  private static final String SILUETE = "siluete";

  /**
   * String builder used to collect all the instructions
   */
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
   * Character for starting parameters
   */
  private static final char PARAM_START = '(';

  /**
   * Character for ending parameters
   */
  private static final char PARAM_END = ')';

  /**
   * Instruction delimiter
   */
  private static final char INSTRUCTION_DELIM = '+';
  /**
   * Parameter delimiter
   */
  private static final char PARAMETER_DELIM = ',';

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
  private String sanitizeParameters(final String parameter) {
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
   * Add another image to image instructions.
   * Image is added into center of image
   * @param image image name to place into image
   * @return ImageInstruction with text
   */
  public ImageInstruction addImage(final String image) {
    checkDelim();
    if (!SpaceRace.CENTAURS.getNameSingle().equals(image)
        && !SpaceRace.HUMAN.getNameSingle().equals(image)
        && !SpaceRace.SPORKS.getNameSingle().equals(image)
        && !SpaceRace.GREYANS.getNameSingle().equals(image)
        && !SpaceRace.MOTHOIDS.getNameSingle().equals(image)
        && !SpaceRace.TEUTHIDAES.getNameSingle().equals(image)
        && !SpaceRace.MECHIONS.getNameSingle().equals(image)
        && !SpaceRace.SCAURIANS.getNameSingle().equals(image)
        && !SpaceRace.HOMARIANS.getNameSingle().equals(image)
        && !SpaceRace.SPACE_PIRATE.getNameSingle().equals(image)
        && !SpaceRace.CHIRALOIDS.getNameSingle().equals(image)
        && !SpaceRace.REBORGIANS.getNameSingle().equals(image)
        && !LOGO.equals(image)
        && !BIG_BAN.equals(image)
        && !BIG_PEACE.equals(image)
        && !BIG_NUKE.equals(image)
        && !BIG_PRIVATEER.equals(image)
        && !GALAXY.equals(image)
        && !SOLAR_FLARES.equals(image)
        && !SOLAR_NO_FLARES.equals(image)
        && !PIRATE_PILOT.equals(image)
        && !PIRATE_RAIDERS.equals(image)
        && !MUTINY.equals(image)
        && !DATALOSS.equals(image)
        && !OLD_SHIP.equals(image)
        && !DESERT.equals(image)
        && !PARADISE.equals(image)
        && !VIRUSES.equals(image)
        && !METEOR.equals(image)
        && !SIGNAL.equals(image)
        && !TECHNICAL_BREAKTHROUGH.equals(image)
        && !SHIP_DESTROYED.equals(image)
        && !SHUTTLE.equals(image)
        && !SPINOSAURUS.equals(image)
        && !CONTAINERS.equals(image)) {
      throw new IllegalArgumentException("Illegal image: "
        + image);
    }
    sb.append(IMAGE);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(image));
    sb.append(PARAM_END);
    return this;
  }

  /**
   * Add another image siluete to image instructions.
   * Image is added into center of image
   * @param image image name to place into image
   * @param position Where siluete is going go be drawn.
   *        POSITION_CENTER, POSITION_LEFT, POSITION_RIGHT
   * @return ImageInstruction with text
   */
  public ImageInstruction addSiluete(final String image,
      final String position) {
    checkDelim();
    if (!SpaceRace.CENTAURS.getNameSingle().equals(image)
        && !SpaceRace.HUMAN.getNameSingle().equals(image)
        && !SpaceRace.SPORKS.getNameSingle().equals(image)
        && !SpaceRace.GREYANS.getNameSingle().equals(image)
        && !SpaceRace.MOTHOIDS.getNameSingle().equals(image)
        && !SpaceRace.TEUTHIDAES.getNameSingle().equals(image)
        && !SpaceRace.MECHIONS.getNameSingle().equals(image)
        && !SpaceRace.SCAURIANS.getNameSingle().equals(image)
        && !SpaceRace.HOMARIANS.getNameSingle().equals(image)
        && !SpaceRace.SPACE_PIRATE.getNameSingle().equals(image)
        && !SpaceRace.CHIRALOIDS.getNameSingle().equals(image)
        && !SpaceRace.REBORGIANS.getNameSingle().equals(image)) {
      throw new IllegalArgumentException("Illegal image: "
        + image);
    }
    if (!POSITION_CENTER.equals(position)
        && !POSITION_LEFT.equals(position)
        && !POSITION_RIGHT.equals(position)) {
      throw new IllegalArgumentException("Illegal logo position: "
        + position);
    }
    sb.append(SILUETE);
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
    if (!PLANET_SPORTS.equals(logoType)
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
        && !SpaceRace.CENTAURS.getNameSingle().equals(logoType)
        && !SpaceRace.HUMAN.getNameSingle().equals(logoType)
        && !SpaceRace.SPORKS.getNameSingle().equals(logoType)
        && !SpaceRace.GREYANS.getNameSingle().equals(logoType)
        && !SpaceRace.MOTHOIDS.getNameSingle().equals(logoType)
        && !SpaceRace.TEUTHIDAES.getNameSingle().equals(logoType)
        && !SpaceRace.MECHIONS.getNameSingle().equals(logoType)
        && !SpaceRace.SCAURIANS.getNameSingle().equals(logoType)
        && !SpaceRace.HOMARIANS.getNameSingle().equals(logoType)
        && !SpaceRace.SPACE_PIRATE.getNameSingle().equals(logoType)
        && !SpaceRace.CHIRALOIDS.getNameSingle().equals(logoType)
        && !SpaceRace.REBORGIANS.getNameSingle().equals(logoType)) {
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
    if (!TRADER1.equals(trader) && !TRADER2.equals(trader)) {
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
      g.drawImage(GuiStatics.STAR_FIELD_IMAGE, 0, 0, null);
    }
    if (BACKGROUND_NEBULAE.equals(backgroundType)) {
      Graphics2D g = (Graphics2D) workImage.getGraphics();
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
      g.drawImage(GuiStatics.STAR_FIELD_IMAGE, -50, -50, null);
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
    if (PLANET_CARBONWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_CARBONWORLD1;
    }
    if (PLANET_CARBONWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_CARBONWORLD2;
    }
    if (PLANET_CARBONWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_CARBONWORLD3;
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
    if (PLANET_IRONWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_IRONPLANET1;
    }
    if (PLANET_IRONWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_IRONPLANET2;
    }
    if (PLANET_IRONWORLD3.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_IRONPLANET3;
    }
    if (PLANET_IRONWORLD4.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_IRONPLANET4;
    }
    if (PLANET_IRONWORLD5.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_IRONPLANET5;
    }
    if (PLANET_IRONWORLD6.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_IRONPLANET6;
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
      planetImg = GuiStatics.BIG_SPORT_LOGO;
    }
    if (BIG_BAN.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_BIG_BAN_ICON;
    }
    if (BIG_PEACE.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_BIG_PEACE_ICON;
    }
    if (BIG_NUKE.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_BIG_NUKE;
    }
    if (GALAXY.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_GALAXY;
    }
    if (BIG_PRIVATEER.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_PRIVATEER;
    }
    if (UNITED_GALAXY_TOWER.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_UNITED_GALAXY_TOWER;
    }
    if (BIG_MISSILE.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_BIG_MISSILE;
    }
    if (BIG_MONEY.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_BIG_MONEY;
    }
    if (METEOR.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_METEOR;
    }
    if (METEOR_HIT.equals(planetType)) {
      planetImg = GuiStatics.IMAGE_METEOR_HIT;
    }
    SpaceRace race = SpaceRaceUtility.getRaceByName(planetType);
    if (race != null) {
      planetImg = race.getRaceImage();
    }
    if (SpaceRace.SPACE_PIRATE.getNameSingle().equals(planetType)) {
      planetImg = SpaceRace.SPACE_PIRATE.getRaceImage();
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
    BufferedImage traderImg = GuiStatics.IMAGE_TRADE_SHIP;
    if (TRADER1.equals(traderType)) {
      traderImg = GuiStatics.IMAGE_TRADE_SHIP;
    }
    if (TRADER2.equals(traderType)) {
      traderImg = GuiStatics.IMAGE_TRADE_SHIP2;
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
   * Draw siluete on image
   * @param workImage Image where to draw
   * @param raceName Race whom's siluete is drawn
   * @param position LEFT, CENTER or RIGHT
   */
  private static void paintSiluete(final BufferedImage workImage,
      final String raceName, final String position) {
    BufferedImage silueteImg = GuiStatics.IMAGE_HUMAN_RACE;
    SpaceRace race = SpaceRaceUtility.getRaceByName(raceName);
    if (race != null) {
      silueteImg = race.getRaceImage();
    }
    silueteImg = GraphRoutines.blackSiluete(silueteImg);

    Graphics2D g = (Graphics2D) workImage.getGraphics();
    if (POSITION_CENTER.equals(position)) {
      g.drawImage(silueteImg,
          workImage.getWidth() / 2 - silueteImg.getWidth() / 2,
          workImage.getHeight() / 2 - silueteImg.getHeight() / 2, null);
    }
    if (POSITION_LEFT.equals(position)) {
      g.drawImage(silueteImg,
          workImage.getWidth() / 5 - silueteImg.getWidth() / 2,
          workImage.getHeight() / 2 - silueteImg.getHeight() / 2, null);
    }
    if (POSITION_RIGHT.equals(position)) {
      g.drawImage(silueteImg,
          (workImage.getWidth() - workImage.getWidth() / 5)
          - silueteImg.getWidth() / 2,
          workImage.getHeight() / 2 - silueteImg.getHeight() / 2, null);
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
      drawImg = race.getRaceImage();
    }
    if (SpaceRace.SPACE_PIRATE.getNameSingle().equals(image)) {
      drawImg = SpaceRace.SPACE_PIRATE.getRaceImage();
    }
    if (LOGO.equals(image)) {
      drawImg = GuiStatics.IMAGE_GBNC;
    }
    if (BIG_BAN.equals(image)) {
      drawImg = GuiStatics.IMAGE_BIG_BAN_ICON;
    }
    if (METEOR.equals(image)) {
      drawImg = GuiStatics.IMAGE_METEOR;
    }
    if (BIG_PEACE.equals(image)) {
      drawImg = GuiStatics.IMAGE_BIG_PEACE_ICON;
    }
    if (BIG_NUKE.equals(image)) {
      drawImg = GuiStatics.IMAGE_BIG_NUKE;
    }
    if (GALAXY.equals(image)) {
      drawImg = GuiStatics.IMAGE_GALAXY;
    }
    if (BIG_PRIVATEER.equals(image)) {
      drawImg = GuiStatics.IMAGE_PRIVATEER;
    }
    if (SOLAR_FLARES.equals(image)) {
      drawImg = GuiStatics.IMAGE_SOLAR_FLARES;
    }
    if (SOLAR_NO_FLARES.equals(image)) {
      drawImg = GuiStatics.IMAGE_SOLAR_NO_FLARES;
    }
    if (PIRATE_PILOT.equals(image)) {
      drawImg = GuiStatics.IMAGE_PIRATE_PILOT;
    }
    if (PIRATE_RAIDERS.equals(image)) {
      drawImg = GuiStatics.IMAGE_PIRATE_RAIDERS;
    }
    if (MUTINY.equals(image)) {
      drawImg = GuiStatics.IMAGE_MUTINY;
    }
    if (DATALOSS.equals(image)) {
      drawImg = GuiStatics.IMAGE_DATALOSS;
    }
    if (OLD_SHIP.equals(image)) {
      drawImg = GuiStatics.IMAGE_OLD_SHIP;
    }
    if (DESERT.equals(image)) {
      drawImg = GuiStatics.IMAGE_DESERT;
    }
    if (PARADISE.equals(image)) {
      drawImg = GuiStatics.IMAGE_PARADISE;
    }
    if (VIRUSES.equals(image)) {
      drawImg = GuiStatics.IMAGE_VIRUSES;
    }
    if (SIGNAL.equals(image)) {
      drawImg = GuiStatics.IMAGE_SIGNAL;
    }
    if (TECHNICAL_BREAKTHROUGH.equals(image)) {
      drawImg = GuiStatics.IMAGE_TECHNICAL_BREAKTHROUGH;
    }
    if (SHIP_DESTROYED.equals(image)) {
      drawImg = GuiStatics.IMAGE_SHIP_DESTROYED;
    }
    if (SHUTTLE.equals(image)) {
      drawImg = GuiStatics.IMAGE_SHUTTLE;
    }
    if (SPINOSAURUS.equals(image)) {
      drawImg = GuiStatics.IMAGE_SPINOSAURUS;
    }
    if (CONTAINERS.equals(image)) {
      drawImg = GuiStatics.IMAGE_CONTAINERS;
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
   * Parse Image instruction string and draw image
   * to given image.
   * @param image Image where to draw
   * @param instructions Instruction as a String
   * @return BufferedImage with generated content
   */
  public static BufferedImage parseImageInstructions(final BufferedImage image,
      final String instructions) {
    int textY = 30;
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
      if (TEXT.equals(command)) {
        Graphics2D g = (Graphics2D) workImage.getGraphics();
        g.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
        g.setFont(GuiStatics.getFontCubellanBoldBig());
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
      if (SILUETE.equals(command)) {
        paintSiluete(workImage, parameters[0], parameters[1]);
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
