package org.openRealmOfStars.starMap.newsCorp;

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
  public static final String PLANET = "planet";
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
          && parameter.charAt(i) != INSTRUCTION_DELIM) {
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
}
