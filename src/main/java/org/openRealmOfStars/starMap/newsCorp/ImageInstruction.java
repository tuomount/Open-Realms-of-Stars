package org.openRealmOfStars.starMap.newsCorp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
   * Planet type ironworld1
   */
  public static final String PLANET_IRONWORLD1 = "ironworld1";
  /**
   * Planet type ironworld2
   */
  public static final String PLANET_IRONWORLD2 = "ironworld2";
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
  public static final String RELATION_SYMBOL = "relation_symbol";
  /**
   * Instructions for ship
   */
  public static final String SHIP = "ship";

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
   * Adds planet to the image.
   * @param position Three choices: left, center and right
   * @param planetType planet type. Choices are:
   *        rock1, waterworld1, waterworld2,
   *        ironworld1, ironworld2, gasgiant1,
   *        gasgiant2
   * @return ImageInstruction with text
   * @throws IllegalArgumentException If position or planet type are illegal
   */
  public ImageInstruction addPlanet(final String position,
      final String planetType) throws IllegalArgumentException {
    if (!POSITION_CENTER.equals(position)
        && !POSITION_LEFT.equals(position)
        && !POSITION_RIGHT.equals(position)) {
      throw new IllegalArgumentException("Illegal planet position: "
        + position);
    }
    if (!PLANET_ROCK1.equals(planetType)
        && !PLANET_WATERWORLD1.equals(planetType)
        && !PLANET_WATERWORLD2.equals(planetType)
        && !PLANET_IRONWORLD1.equals(planetType)
        && !PLANET_IRONWORLD2.equals(planetType)
        && !PLANET_GASGIANT1.equals(planetType)
        && !PLANET_GASGIANT1.equals(planetType)) {
      throw new IllegalArgumentException("Illegal planet type: " + planetType);
    }
    checkDelim();
    sb.append(PLANET);
    sb.append(PARAM_START);
    sb.append(sanitizeParameters(position));
    sb.append(PARAMETER_DELIM);
    sb.append(sanitizeParameters(planetType));
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
   * Parse Image instruction string and draw image
   * to given image.
   * @param image Image where to draw
   * @param instructions Instruction as a String
   * @return BufferedImage with generated content
   */
  public static BufferedImage parseImageInstructions(final BufferedImage image,
      final String instructions) {
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
        if (BACKGROUND_BLACK.equals(parameters[0])) {
          Graphics2D g = (Graphics2D) workImage.getGraphics();
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
        }
        if (BACKGROUND_GREY_GRADIENT.equals(parameters[0])) {
          Graphics2D g = (Graphics2D) workImage.getGraphics();
          GradientPaint gradient = new GradientPaint(workImage.getWidth() / 2,
              0, Color.BLACK, workImage.getWidth() / 2,
              workImage.getHeight() - 1, GuiStatics.COLOR_GREY_40, true);
          g.setPaint(gradient);
          g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
        }
        if (BACKGROUND_STARS.equals(parameters[0])) {
          Graphics2D g = (Graphics2D) workImage.getGraphics();
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
          g.drawImage(GuiStatics.STAR_FIELD_IMAGE, 0, 0, null);
        }
        if (BACKGROUND_NEBULAE.equals(parameters[0])) {
          Graphics2D g = (Graphics2D) workImage.getGraphics();
          g.setColor(Color.BLACK);
          g.fillRect(0, 0, workImage.getWidth(), workImage.getHeight());
          g.drawImage(GuiStatics.STAR_FIELD_IMAGE, -50, -50, null);
          g.drawImage(GuiStatics.NEBULAE_IMAGE, -25, -25, null);
        }
      }
    }
    return workImage;
  }
}
