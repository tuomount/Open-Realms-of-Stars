package org.openRealmOfStars.starMap.newsCorp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.starMap.planet.PlanetTypes;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
  public static final String PLANET_WATERWORLD4 = "waterworld3";
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
   * Planet type iceworld1
   */
  public static final String PLANET_ICEWORLD1 = "iceworld1";
  /**
   * Planet type iceworld2
   */
  public static final String PLANET_ICEWORLD2 = "iceworld2";
  /**
   * Planet type carbon world 1
   */
  public static final String PLANET_CARBONWORLD1 = "carbonworld1";
  /**
   * Planet type gas giant 1
   */
  public static final String PLANET_GASGIANT1 = "gasgiant1";
  /**
   * Planet type gas giant 2
   */
  public static final String PLANET_GASGIANT2 = "gasgiant2";
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
   * Relation symbol alliance
   */
  public static final String ALLIANCE = "alliance";
  /**
   * GBNC Logo
   */
  public static final String LOGO = "logo";
  /**
   * Instructions for ship
   */
  public static final String SHIP = "ship";

  /**
   * Instruction to draw image
   */
  private static final String IMAGE = "image";

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
        && !ALLIANCE.equals(symbol)) {
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
        && !LOGO.equals(image)) {
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
    if (!PlanetTypes.verifyInstruction(planetType)
        && !PLANET_GASGIANT1.equals(planetType)
        && !PLANET_GASGIANT2.equals(planetType)) {
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
    if (PLANET_ICEWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ICEWORLD1;
    }
    if (PLANET_ICEWORLD2.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_ICEWORLD2;
    }
    if (PLANET_CARBONWORLD1.equals(planetType)) {
      planetImg = GuiStatics.BIG_PLANET_CARBONWORLD1;
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
    if (PLANET_GASGIANT1.equals(planetType)) {
      planetImg = GuiStatics.BIG_GASWORLD1;
    }
    if (PLANET_GASGIANT2.equals(planetType)) {
      planetImg = GuiStatics.BIG_GASWORLD2;
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
   * Draw image on image
   * @param workImage Image where to draw
   * @param image image type to draw
   */
  private static void paintImage(final BufferedImage workImage,
      final String image) {
    BufferedImage drawImg = GuiStatics.BIG_PLANET_ROCK1;
    SpaceRace race = SpaceRaceUtility.getRaceByName(image);
    if (race != null) {
      drawImg = race.getRaceImage();
    }
    if (LOGO.equals(image)) {
      drawImg = GuiStatics.IMAGE_GBNC;
    }
    Graphics2D g = (Graphics2D) workImage.getGraphics();
    g.drawImage(drawImg,
        workImage.getWidth() / 2 - drawImg.getWidth() / 2,
        workImage.getHeight() / 2 - drawImg.getHeight() / 2, null);
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
      if (PLANET.equals(command)) {
        paintPlanet(workImage, parameters[1], parameters[0], parameters[2]);
      }
      if (IMAGE.equals(command)) {
        paintImage(workImage, parameters[0]);
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
        if (ALLIANCE.equals(parameters[0])) {
          symbol = GuiStatics.RELATION_ALLIANCE;
        }
        g.drawImage(symbol, workImage.getWidth() / 2 - symbol.getWidth() / 2,
            textY - symbol.getHeight() / 2, null);
        textY = textY + symbol.getHeight() * 2;
      }
    }
    return workImage;
  }
}
