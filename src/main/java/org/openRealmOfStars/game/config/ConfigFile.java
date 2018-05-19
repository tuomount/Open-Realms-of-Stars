package org.openRealmOfStars.game.config;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.ErrorLogger;

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

  /**
   * Generic getter for sound and music volumes
   * @param configVolume SoundVolume or MusicVolume
   * @return Volume value
   */
  private int getVolume(final String configVolume) {
    int result = 75;
    ConfigLine line = getLineByKey(configVolume);
    if (line != null) {
      String value = line.getValue();
      if (value != null) {
        try {
          result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
          ErrorLogger.log("Invalid " + configVolume + " value: " + value);
        }
      }
    }
    return result;
  }

  /**
   * Get Resolution width or height.
   * @param index 0 for width, 1 for height
   * @return Resolution
   */
  private int getResolutionPart(final int index) {
    int result = 1024;
    int result2 = 0;
    if (index < 0 || index > 1) {
      throw new IllegalArgumentException("Index is not 0 or 1!");
    }
    if (index == 1) {
      result = 768;
    }
    ConfigLine line = getLineByKey(CONFIG_RESOLUTION);
    if (line != null) {
      String value = line.getValue();
      if (value != null) {
        String[] parts = value.split("x");
        if (parts.length == 2) {
          try {
            result2 = Integer.parseInt(parts[index]);
          } catch (NumberFormatException e) {
            ErrorLogger.log("Invalid " + CONFIG_RESOLUTION + " value: "
                + value);
          }
        } else {
          ErrorLogger.log("Invalid resolution: " + value);
        }
      }
    }
    if (result2 > result) {
      result = result2;
    }
    return result;
  }

  /**
   * Set resolution for config file. Minimum resolution is
   * 1024x768.
   * @param width Resolution width.
   * @param height Resolution height.
   */
  public void setResolution(final int width, final int height) {
    ConfigLine line = getLineByKey(CONFIG_RESOLUTION);
    if (line != null && width >= 1024 && height >= 768) {
      line.setValue(width + "x" + height);
    }
  }
  /**
   * Get Resolution width from config file
   * @return Resolution width
   */
  public int getResolutionWidth() {
    return getResolutionPart(0);
  }

  /**
   * Get Resolution height from config file
   * @return Resolution height
   */
  public int getResolutionHeight() {
    return getResolutionPart(1);
  }

  /**
   * Set the generic volume for config file.
   * @param configVolume SoundVolume or MusicVolume
   * @param volume to set
   */
  private void setVolume(final String configVolume, final int volume) {
    ConfigLine line = getLineByKey(configVolume);
    if (line != null && volume >= 0 && volume <= 100) {
      String str = Integer.toString(volume);
      line.setValue(str);
    }
  }

  /**
   * Get the music volume from config file.
   * @return Music volume
   */
  public int getMusicVolume() {
    return getVolume(CONFIG_MUSIC_VOLUME);
  }

  /**
   * Get the sound volume from config file.
   * @return Sound volume
   */
  public int getSoundVolume() {
    return getVolume(CONFIG_SOUND_VOLUME);
  }

  /**
   * Set the music volume for config file.
   * @param volume Music volume
   */
  public void setMusicVolume(final int volume) {
    setVolume(CONFIG_MUSIC_VOLUME, volume);
  }

  /**
   * Set the music volume for config file.
   * @param volume Music volume
   */
  public void setSoundVolume(final int volume) {
    setVolume(CONFIG_SOUND_VOLUME, volume);
  }

}
