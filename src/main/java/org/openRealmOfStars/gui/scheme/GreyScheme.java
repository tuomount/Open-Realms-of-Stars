package org.openRealmOfStars.gui.scheme;

import java.awt.Color;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
* Grey Scheme for UI.
*
*/
public class GreyScheme extends BaseScheme {

  /**
   * Panel background
   */
  private static final Color COLOR_SPACE_GREY = new Color(81, 87, 109,
      255);

  /**
   * Cool space color
   */
  public static final Color COLOR_COOL_SPACE_BLUE = new Color(210, 210, 255);
  /**
   * Cool space color dark
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARK = new Color(160, 160,
      190);
  /**
   * Cool space color darker
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARKER = new Color(120, 120,
      150);
  /**
   * Cool space color, opacity 128
   */
  public static final Color COLOR_COOL_SPACE_BLUE_TRANS = new Color(210, 210,
      255, 128);
  /**
   * Green text dark one
   */
  public static final Color COLOR_GREEN_TEXT_DARK = new Color(2, 102, 0, 255);

  /**
   * Deep space gray
   */
  public static final Color COLOR_DEEP_SPACE_GRAY = new Color(36, 36, 64);

  /**
   * Deep space gray dark
   */
  public static final Color COLOR_DEEP_SPACE_GRAY_DARK = new Color(15, 15, 41);

  @Override
  public Color getPanelBackground() {
    return COLOR_SPACE_GREY;
  }

  @Override
  public SchemeType getType() {
    return SchemeType.SPACE_GREY;
  }

  @Override
  public Color getCoolSpaceColor() {
    return COLOR_COOL_SPACE_BLUE;
  }

  @Override
  public Color getCoolSpaceColorTransparent() {
    return COLOR_COOL_SPACE_BLUE_TRANS;
  }

  @Override
  public Color getInfoTextColor() {
    return COLOR_GREEN_TEXT_DARK;
  }

  @Override
  public Color getCoolSpaceColorDark() {
    return COLOR_COOL_SPACE_BLUE_DARK;
  }

  @Override
  public Color getCoolSpaceColorDarker() {
    return COLOR_COOL_SPACE_BLUE_DARKER;
  }

  @Override
  public Color getDeepSpaceColor() {
    return COLOR_DEEP_SPACE_GRAY;
  }
  @Override
  public Color getDeepSpaceDarkerColor() {
    return COLOR_DEEP_SPACE_GRAY_DARK;
  }
}
