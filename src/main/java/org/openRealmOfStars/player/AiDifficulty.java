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
  WEAK,
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
      case WEAK: {
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
    case WEAK: {
      return "Weak";
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
  /**
   * AI Difficulty description.
   * @return Description as String.
   */
  public String getDescription() {
    switch (this) {
    case WEAK: {
      return "<html>Weak AI is going to have bad AI algorithm which "
          + "causes AI to do clear mistakes. <br>"
          + "This should be good for first time or casual player.</html>";
    }
    default:
    case NORMAL: {
      return "<html>Normal AI is try to achieve challenging enough for"
          + " experience 4X player. There should not be obvious mistakes."
          + "</html>";
    }
    case CHALLENGING: {
      return "<html>Challening AI is try to achieve challenging enough for"
          + " very experienced 4X player. <br>There should not be obvious"
          + " mistakes and this should giving good fight.<br>"
          + "For now this is still work in progress.</html>";
    }
  }
  }

}
