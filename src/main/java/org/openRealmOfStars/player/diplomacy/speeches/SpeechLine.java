package org.openRealmOfStars.player.diplomacy.speeches;

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
* Speech lines
*
*/
public class SpeechLine {

  /**
   * Speech type
   */
  private SpeechType type;

  /**
   * Actual line in text
   */
  private String line;

  /**
   * Constructor for Speech line.
   * @param speechType Line type
   * @param text Line as a text
   */
  public SpeechLine(final SpeechType speechType, final String text) {
    type = speechType;
    line = text;
  }
  /**
   * Get line type
   * @return Speech type
   */
  public SpeechType getType() {
    return type;
  }

  /**
   * Get line
   * @return Line as a String
   */
  public String getLine() {
    return line;
  }

  @Override
  public String toString() {
    return type.toString() + "/" + line;
  }
}
