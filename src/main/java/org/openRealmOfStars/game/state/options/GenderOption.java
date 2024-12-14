/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
package org.openRealmOfStars.game.state.options;

import org.openRealmOfStars.player.leader.Gender;

/**
 * Gender Options for Space race
 *
 */
public enum GenderOption {
  /**
   * Male and Female leaders
   */
  MALE_AND_FEMALE,
  /**
   * Male leaders only
   */
  MALE_ONLY,
  /**
   * Female leaders only
   */
  FEMALE_ONLY,
  /**
   * Leaders have no gender
   */
  NONE;

  /**
   * Get option name
   * @return String
   */
  public String getName() {
    switch (this) {
      default:
      case MALE_AND_FEMALE: return "Male and female";
      case MALE_ONLY: return "Male only";
      case FEMALE_ONLY: return "Female only";
      case NONE: return "None";
    }
  }

  /**
   * Get option values
   * @return Gender array
   */
  public Gender[] getValue() {
    switch (this) {
      default:
      case MALE_AND_FEMALE: return new Gender[]{Gender.MALE, Gender.FEMALE};
      case MALE_ONLY: return new Gender[]{Gender.MALE};
      case FEMALE_ONLY: return new Gender[]{Gender.FEMALE};
      case NONE: return new Gender[]{Gender.NONE};
    }
  }

}
