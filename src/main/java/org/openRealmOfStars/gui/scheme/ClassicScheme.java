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
* Classic Scheme for UI.
*
*/
public class ClassicScheme extends BaseScheme {

  /**
   * Panel background
   */
  private static final Color COLOR_SPACE_GREY_BLUE = new Color(81, 87, 133,
      255);

  /**
   * Cool space color
   */
  public static final Color COLOR_COOL_SPACE_BLUE = new Color(88, 210, 255);
  /**
   * Cool space blue, opacity 128
   */
  public static final Color COLOR_COOL_SPACE_BLUE_TRANS = new Color(88, 210,
      255, 128);


  @Override
  public Color getPanelBackground() {
    return COLOR_SPACE_GREY_BLUE;
  }

  @Override
  public SchemeType getType() {
    return SchemeType.CLASSIC_SPACE_GREY_BLUE;
  }

  @Override
  public Color getCoolSpaceColor() {
    return COLOR_COOL_SPACE_BLUE;
  }

  @Override
  public Color getCoolSpaceColorTransparent() {
    return COLOR_COOL_SPACE_BLUE_TRANS;
  }

}
