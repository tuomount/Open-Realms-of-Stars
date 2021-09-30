package org.openRealmOfStars.player;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* AI Difficulty level
*
*/
public enum AiDifficulty {
  /**
   * Lowest AI level.
   */
  STUPID,
  /**
   * Normal or standard AI level.
   */
  NORMAL,
  /**
   * A bit more challenging AI level.
   */
  CHALLENGING;

  /**
   * Get AI Difficulty by index.
   * @param index AI Difficulty
   * @return AiDifficulty
   */
  public static AiDifficulty getByIndex(final int index) {
    if (index >= 0 && index < AiDifficulty.values().length) {
      return AiDifficulty.values()[index];
    }
    return AiDifficulty.NORMAL;
  }

  /**
   * Get AI Difficulty as index
   * @return int
   */
  public int getIndex() {
    switch (this) {
      case STUPID: {
        return 0;
      }
      default:
      case NORMAL: {
        return 1;
      }
      case CHALLENGING: {
        return 2;
      }
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case STUPID: {
      return "Stupid";
    }
    default:
    case NORMAL: {
      return "Normal";
    }
    case CHALLENGING: {
      return "Challenging";
    }
  }
  }
}
