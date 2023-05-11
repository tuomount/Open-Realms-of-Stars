package org.openRealmOfStars.game.config;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.ErrorLogger;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018, 2020-2023 Tuomo Untinen
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
   * Config option RemoveBorders
   */
  public static final String CONFIG_BORDERLESS = "Borderless";
  /**
   * Config option larger fonts
   */
  public static final String CONFIG_LARGER_FONTS = "LargerFonts";
  /**
   * Config option for ambient light bridge
   */
  public static final String CONFIG_BRIDGE_HOST = "BridgeHost";
  /**
   * Config option for ambient light bridge username
   */
  public static final String CONFIG_BRIDGE_USERNAME = "BridgeUsername";
  /**
   * Config option for left ambient light
   */
  public static final String CONFIG_LEFT_LIGHT = "LeftLightName";
  /**
   * Config option for center ambient light
   */
  public static final String CONFIG_CENTER_LIGHT = "CenterLightName";
  /**
   * Config option for center right light
   */
  public static final String CONFIG_RIGHT_LIGHT = "RightLightName";
  /**
   * Config option for light intense
   */
  public static final String CONFIG_LIGHT_INTENSE = "LightIntense";
  /**
   * Config option for enabled ambient light
   */
  public static final String CONFIG_ENABLE_LIGHTS = "EnableLights";
  /**
   * Config option for bridge Id.
   */
  public static final String CONFIG_BRIDGE_ID = "BridgeId";
  /**
   * Config option for fullscreen
   */
  public static final String CONFIG_FULLSCREEN = "FullScreen";
  /**
   * Config option for hardware acceleration
   */
  public static final String CONFIG_HARDWARE_ACCELERATION =
      "HardwareAcceleration";
  /**
   * Config file default comment
   */
  public static final String CONFIG_COMMENT = "# Config file for "
      + "Open Realm of Stars";

  /**
   * Config option for improved parallax.
   */
  public static final String CONFIG_IMPROVED_PARALLAX = "ImprovedParallax";
  /**
   * Config option for border scrolling.
   */
  public static final String CONFIG_BORDER_SCROLLING = "BorderScrolling";
  /**
   * Config option for show minimap.
   */
  public static final String CONFIG_SHOW_MINIMAP = "ShowMinimap";
  /**
   * Config option for UI Scheme
   */
  public static final String CONFIG_UI_SCHEME = "UIScheme";
  /**
   * String true
   */
  public static final String TRUE = "true";
  /**
   * String false
   */
  public static final String FALSE = "false";
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
   * Get boolean key value
   * @param key Key to read
   * @return boolean
   */
  private boolean getBoolean(final String key) {
    ConfigLine line = getLineByKey(key);
    if (line != null && line.getValue().equalsIgnoreCase(TRUE)) {
        return true;
    }
    return false;
  }

  /**
   * Set boolean key value
   * @param key Key to read
   * @param value boolean value to set.
   */
  private void setBoolean(final String key, final boolean value) {
    ConfigLine line = getLineByKey(key);
    if (line == null) {
      line = new ConfigLine(key + "=" + FALSE);
      add(line);
    }
    if (value) {
      line.setValue(TRUE);
    } else {
      line.setValue(FALSE);
    }
  }

  /**
   * Set resolution for config file. Minimum resolution is
   * 1024x768.
   * @param width Resolution width.
   * @param height Resolution height.
   */
  public void setResolution(final int width, final int height) {
    ConfigLine line = getLineByKey(CONFIG_RESOLUTION);
    if (line == null) {
      line = new ConfigLine(CONFIG_RESOLUTION + "=1024x768");
      add(line);
    }
    if (width >= 1024 && height >= 768) {
      line.setValue(width + "x" + height);
    }
  }
  /**
   * Set Bridge id
   * @param id ID for ambient light bridge.
   */
  public void setBridgeId(final String id) {
    ConfigLine line = getLineByKey(CONFIG_BRIDGE_ID);
    if (id != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_BRIDGE_ID + "=" + id);
        add(line);
      } else {
        line.setValue(id);
      }
    }
  }

  /**
   * Get Bridge id or null
   * @return Bridge id or null
   */
  public String getBridgeId() {
    ConfigLine line = getLineByKey(CONFIG_BRIDGE_ID);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }

  /**
   * Set Bridge hostname
   * @param hostname Hostname for ambient light bridge.
   */
  public void setBridgeHost(final String hostname) {
    ConfigLine line = getLineByKey(CONFIG_BRIDGE_HOST);
    if (hostname != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_BRIDGE_HOST + "=" + hostname);
        add(line);
      } else {
        line.setValue(hostname);
      }
    }
  }

  /**
   * Get Bridge hostname or null
   * @return hostname or null
   */
  public String getBridgeHost() {
    ConfigLine line = getLineByKey(CONFIG_BRIDGE_HOST);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }
  /**
   * Set Bridge hostname
   * @param username Username for ambient light bridge.
   */
  public void setBridgeUsername(final String username) {
    ConfigLine line = getLineByKey(CONFIG_BRIDGE_USERNAME);
    if (username != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_BRIDGE_USERNAME + "=" + username);
        add(line);
      } else {
        line.setValue(username);
      }
    }
  }

  /**
   * Get Bridge username or null
   * @return username or null
   */
  public String getBridgeUsername() {
    ConfigLine line = getLineByKey(CONFIG_BRIDGE_USERNAME);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }

  /**
   * Set Left ambient light name
   * @param lightName Light name to set
   */
  public void setLeftLight(final String lightName) {
    ConfigLine line = getLineByKey(CONFIG_LEFT_LIGHT);
    if (lightName != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_LEFT_LIGHT + "=" + lightName);
        add(line);
      } else {
        line.setValue(lightName);
      }
    }
  }

  /**
   * Get left ambient light name
   * @return lightname or null
   */
  public String getLeftLight() {
    ConfigLine line = getLineByKey(CONFIG_LEFT_LIGHT);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }

  /**
   * Set right ambient light name
   * @param lightName Light name to set
   */
  public void setRightLight(final String lightName) {
    ConfigLine line = getLineByKey(CONFIG_RIGHT_LIGHT);
    if (lightName != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_RIGHT_LIGHT + "=" + lightName);
        add(line);
      } else {
        line.setValue(lightName);
      }
    }
  }

  /**
   * Get right ambient light name
   * @return lightname or null
   */
  public String getRightLight() {
    ConfigLine line = getLineByKey(CONFIG_RIGHT_LIGHT);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }

  /**
   * Set center ambient light name
   * @param lightName Light name to set
   */
  public void setCenterLight(final String lightName) {
    ConfigLine line = getLineByKey(CONFIG_CENTER_LIGHT);
    if (lightName != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_CENTER_LIGHT + "=" + lightName);
        add(line);
      } else {
        line.setValue(lightName);
      }
    }
  }

  /**
   * Get right ambient light name
   * @return lightname or null
   */
  public String getCenterLight() {
    ConfigLine line = getLineByKey(CONFIG_CENTER_LIGHT);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }

  /**
   * Get Resolution width from config file
   * @return Resolution width
   */
  public int getResolutionWidth() {
    return getResolutionPart(0);
  }

  /**
   * Is FullScreen mode enabled?
   * @return True if enabled
   */
  public boolean isFullscreen() {
    return getBoolean(CONFIG_FULLSCREEN);
  }
  /**
   * Set fullscreen setting.
   * @param fullscreen Fullscreen to set.
   */
  public void setFullscreen(final boolean fullscreen) {
    setBoolean(CONFIG_FULLSCREEN, fullscreen);
  }

  /**
   * Is Improved parallax enabled?
   * @return True if enabled
   */
  public boolean isImprovedParallax() {
    return getBoolean(CONFIG_IMPROVED_PARALLAX);
  }
  /**
   * Set Improved parallax setting.
   * @param improvedParallax Improved parallax to set.
   */
  public void setImprovedParallax(final boolean improvedParallax) {
    setBoolean(CONFIG_IMPROVED_PARALLAX, improvedParallax);
  }
  /**
   * Is Hardware acceleration enabled or not.
   * @return True if enabled.
   */
  public boolean isHardwareAcceleration() {
    return getBoolean(CONFIG_HARDWARE_ACCELERATION);
  }
  /**
   * Set Hardware acceleration setting.
   * @param hardwareAcceleration Hardware acceleration boolean setting.
   */
  public void setHardwareAcceleration(final boolean hardwareAcceleration) {
    setBoolean(CONFIG_HARDWARE_ACCELERATION, hardwareAcceleration);
  }
  /**
   * Is borders enabled or disabled?
   * @return true if disabled
   */
  public boolean getBorderless() {
    return getBoolean(CONFIG_BORDERLESS);
  }

  /**
   * Is larger fonts enabled or disabled?
   * @return true if larger fonts
   */
  public boolean getLargerFonts() {
    return getBoolean(CONFIG_LARGER_FONTS);
  }

  /**
   * Set or disable larger fonts.
   * @param largerFonts true to set larger fonts
   */
  public void setLargerFonts(final boolean largerFonts) {
    setBoolean(CONFIG_LARGER_FONTS, largerFonts);
  }

  /**
   * Set or disable borders.
   * @param borderless true to disable borders
   */
  public void setBorderless(final boolean borderless) {
    setBoolean(CONFIG_BORDERLESS, borderless);
  }

  /**
   * Is ambient lights enabled or disabled?
   * @return true if disabled
   */
  public boolean getAmbientLights() {
    return getBoolean(CONFIG_ENABLE_LIGHTS);
  }

  /**
   * Set or disable ambient lights
   * @param lights true to set larger fonts
   */
  public void setAmbientLights(final boolean lights) {
    setBoolean(CONFIG_ENABLE_LIGHTS, lights);
  }

  /**
   * Is Border scrolling enabled?
   * @return True if enabled
   */
  public boolean isBorderScrolling() {
    return getBoolean(CONFIG_BORDER_SCROLLING);
  }
  /**
   * Set border scrolling setting.
   * @param borderScrolling borderScrolling to be set.
   */
  public void setBorderScrolling(final boolean borderScrolling) {
    setBoolean(CONFIG_BORDER_SCROLLING, borderScrolling);
  }

  /**
   * Is Minimap enabled?
   * @return True if enabled
   */
  public boolean isShowMinimap() {
    return getBoolean(CONFIG_SHOW_MINIMAP);
  }
  /**
   * Set show minimap setting.
   * @param showMinimap Show minimap to be set.
   */
  public void setShowMinimap(final boolean showMinimap) {
    setBoolean(CONFIG_SHOW_MINIMAP, showMinimap);
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
    if (line == null) {
      line = new ConfigLine(configVolume + "=75");
      add(line);
    }
    if (volume >= 0 && volume <= 100) {
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

  /**
   * Light Intense for ambient effects.
   * @param intense Light intense
   */
  public void setLightIntense(final int intense) {
    ConfigLine line = getLineByKey(CONFIG_LIGHT_INTENSE);
    if (line == null) {
      line = new ConfigLine(CONFIG_LIGHT_INTENSE + "=3");
      add(line);
    }
    if (intense >= 1 && intense <= 5) {
      String str = Integer.toString(intense);
      line.setValue(str);
    }
  }

  /**
   * Get light intense for ambient lights effect
   * @return Light intense value
   */
  public int getLightIntense() {
    int result = 3;
    ConfigLine line = getLineByKey(CONFIG_LIGHT_INTENSE);
    if (line != null) {
      String value = line.getValue();
      if (value != null) {
        try {
          result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
          ErrorLogger.log("Invalid " + CONFIG_LIGHT_INTENSE
              + " value: " + value);
        }
      }
    }
    return result;
  }

  /**
   * Set UI Scheme name
   * @param scheme Scheme name to set
   */
  public void setUiScheme(final String scheme) {
    ConfigLine line = getLineByKey(CONFIG_UI_SCHEME);
    if (scheme != null) {
      if (line == null) {
        line = new ConfigLine(CONFIG_UI_SCHEME + "=" + scheme);
        add(line);
      } else {
        line.setValue(scheme);
      }
    }
  }

  /**
   * Get UI Scheme name
   * @return UI Scheme name or null
   */
  public String getUiScheme() {
    ConfigLine line = getLineByKey(CONFIG_UI_SCHEME);
    if (line != null) {
      return line.getValue();
    }
    return null;
  }

}
