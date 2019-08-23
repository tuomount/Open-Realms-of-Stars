package org.openRealmOfStars.starMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Pirate difficulty level
*
*/
public enum PirateDifficultLevel {
  /**
   * Easy pirate difficulty level.
   * Pirates wont gain any new techs.
   */
  VERY_EASY,
  /**
   * Easy pirate difficulty level
   * Pirates slowly gain new tech
   */
  EASY,
  /**
   * Normal pirate difficulty level
   */
  NORMAL,
  /**
   * Hard pirate difficulty level.
   * Pirates start higher tech than normally.
   */
  HARD,
  /**
   * Hard pirate difficulty level.
   * Pirate start higher tech than normally and
   * gain tech faster.
   */
  VERY_HARD;

  /**
   * Get Difficulty level as integer
   * @return integer
   */
  public int getIndex() {
    switch (this) {
      case VERY_EASY: return 0;
      case EASY: return 1;
      default:
      case NORMAL: return 2;
      case HARD: return 3;
      case VERY_HARD: return 4;
    }
  }

  /**
   * Get Difficulty level by integer
   * @param value Integer value
   * @return Pirate Difficult level
   */
  public static PirateDifficultLevel getLevelByInt(final int value) {
    switch (value) {
      case 0: return PirateDifficultLevel.VERY_EASY;
      case 1: return PirateDifficultLevel.EASY;
      default:
      case 2: return PirateDifficultLevel.NORMAL;
      case 3: return PirateDifficultLevel.HARD;
      case 4: return PirateDifficultLevel.VERY_HARD;
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case VERY_EASY: return "Very easy";
      case EASY: return "Easy";
      default:
      case NORMAL: return "Normal";
      case HARD: return "Hard";
      case VERY_HARD: return "Very hard";
    }
  }
}
