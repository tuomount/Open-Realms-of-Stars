package org.openRealmOfStars.gui.scheme;
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
* Scheme types.
*
*/
public enum SchemeType {
  /**
   * Classic scheme
   */
  CLASSIC_SPACE_GREY_BLUE,
  /**
   * Grey scheme
   */
  SPACE_GREY;

  @Override
  public String toString() {
    switch (this) {
      default:
      case CLASSIC_SPACE_GREY_BLUE: return "ClassicSpaceGreyBlue";
      case SPACE_GREY: return "SpaceGrey";
    }
  }

  /**
   * Get UI Scheme type by name.
   * @param name Scheme name
   * @return UI Scheme type
   */
  public static SchemeType getByName(final String name) {
    if (name.equals(CLASSIC_SPACE_GREY_BLUE.toString())) {
      return CLASSIC_SPACE_GREY_BLUE;
    }
    if (name.equals(SPACE_GREY.toString())) {
      return SPACE_GREY;
    }
    return CLASSIC_SPACE_GREY_BLUE;
  }

}
