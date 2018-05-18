package org.openRealmOfStars.game.config;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Config Line for reading configuration information
*
*/

public class ConfigLine {

  /**
   * Config Line  type
   */
  private ConfigLineType type;

  /**
   * Config Line comment
   */
  private String comment;

  /**
   * Config line Key
   */
  private String key;

  /**
   * Config line value
   */
  private String value;

  /**
   * Get Config line type
   * @return ConfigLineType
   */
  public ConfigLineType getType() {
    return type;
  }

  /**
   * Parse config line from single line
   * @param line Config line to parse
   */
  public ConfigLine(final String line) {
    if (line == null) {
      type = ConfigLineType.EMPTY;
    } else if (line.startsWith("#")) {
      type = ConfigLineType.COMMENT;
      comment = line;
    } else {
      type = ConfigLineType.KEY_VALUE;
      String[] parts = line.split("=");
      if (parts.length == 2) {
        key = parts[0];
        value = parts[1];
      } else if (parts.length == 1) {
        key = parts[0];
        value = "";
      } else {
        throw new IllegalArgumentException("Invalid key value pair: " + line);
      }
    }
  }

  /**
   * Get the key for key value pair.
   * @return Key as string, null for non key value pair.
   */
  public String getKey() {
    if (type == ConfigLineType.KEY_VALUE) {
      return key;
    }
    return null;
  }
  /**
   * Get the key for key value pair.
   * @return Key as string, null for non key value pair.
   */
  public String getValue() {
    if (type == ConfigLineType.KEY_VALUE) {
      return value;
    }
    return null;
  }

  /**
   * Set Value for Key value pair
   * @param newValue New value for key value pair
   */
  public void setValue(final String newValue) {
    if (type == ConfigLineType.KEY_VALUE) {
      value = newValue;
    }
  }
  /**
   * Get Comment for config line
   * @return Comment as String, null for non comment line
   */
  public String getComment() {
    if (type == ConfigLineType.COMMENT) {
      return comment;
    }
    return null;
  }

  @Override
  public String toString() {
    switch (type) {
      case COMMENT: return comment;
      case EMPTY: return "";
      case KEY_VALUE: return key + "=" + value;
      default: return "Unknown line";
    }
  }

}
