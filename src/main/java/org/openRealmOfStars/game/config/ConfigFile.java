package org.openRealmOfStars.game.config;

import java.util.ArrayList;

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
* Config File containing config line
*
*/
public class ConfigFile {

  /**
   * Config option SoundVolume
   */
  public static final String CONFIG_SOUND_VOLUME = "SoundVolume";
  /**
   * Config option MusicVolume
   */
  public static final String CONFIG_MUSIC_VOLUME = "MusicVolume";
  /**
   * Config option Resolution
   */
  public static final String CONFIG_RESOLUTION = "Resolution";
  /**
   * Config file default comment
   */
  public static final String CONFIG_COMMENT = "# Config file for "
      + "Open Realm of Stars";
  /**
   * ConfigFile lines
   */
  private ArrayList<ConfigLine> lines;

  /**
   * Create empty config file
   */
  public ConfigFile() {
    lines = new ArrayList<>();
  }

  /**
   * Number of config lines
   * @return Number of lines
   */
  public int getNumberOfLines() {
    return lines.size();
  }

  /**
   * Add new config line into end of file
   * @param line to add
   */
  public void add(final ConfigLine line) {
    lines.add(line);
  }

  /**
   * Get Config line
   * @param index Line index
   * @return Config line or null if not found
   */
  public ConfigLine getLine(final int index) {
    if (index >= 0 && index < lines.size()) {
      return lines.get(index);
    }
    return null;
  }

  /**
   * Find config line with certain key
   * @param key as a String
   * @return Config Line or null if not found.
   */
  public ConfigLine getLineByKey(final String key) {
    for (ConfigLine line : lines) {
      if (line.getType() == ConfigLineType.KEY_VALUE
          && line.getKey().equals(key)) {
        return line;
      }
    }
    return null;
  }
}
