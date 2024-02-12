package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020 Tuomo Untinen
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

/**
*
* Social System for Kingdoms and Empires
*
*/
public enum SocialSystem {

  /**
   * Rulers should be females in Empire and Kingdom.
   */
  MATRIARCHY,
  /**
   * Rulers should be males in Empire and Kingdom.
   */
  PATRIARCHY,
  /**
   * Rulers can be both males or females in Empire and Kingdom.
   */
  EQUAL;

  /**
   * Get social system by string.
   * @param value String value
   * @return Social system.
   */
  public static SocialSystem getByString(final String value) {
    if (value.equalsIgnoreCase("matriarchy")) {
      return MATRIARCHY;
    }
    if (value.equalsIgnoreCase("patriarchy")) {
      return PATRIARCHY;
    }
    if (value.equalsIgnoreCase("equal")) {
      return EQUAL;
    }
    throw new IllegalArgumentException("Unexpected social system: " + value);
  }
}
