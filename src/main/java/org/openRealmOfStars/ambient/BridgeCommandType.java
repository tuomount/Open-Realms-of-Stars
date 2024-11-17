package org.openRealmOfStars.ambient;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2021 Tuomo Untinen
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
* Bridge command type, these are given for thread which makes all
* the connections to bridge.
*
*/
public enum BridgeCommandType {

  /**
   * Register bridge.
   */
  REGISTER,
  /**
   * Test connection
   */
  TEST,
  /**
   * Ends command processing
   */
  EXIT,
  /**
   * Fetch lights into list.
   */
  FETCH_LIGHTS,
  /**
   * Warm white light.
   */
  WARM_WHITE,
  /**
   * Lowest possible light
   */
  DARKEST,
  /**
   * Red alert aka Battle alert.
   */
  RED_ALERT,
  /**
   * Yellow alert aka Bombing alert.
   */
  YELLOW_ALERT,
  /**
   * Nuke start, sets all colors to bright white.
   */
  NUKE_START,
  /**
   * Nuclear blast fades away.
   */
  NUKE_FADE,
  /**
   * Float in space
   */
  FLOAT_IN_SPACE,
  /**
   * Green console
   */
  GREEN_CONSOLE,
  /**
   * Space console effect
   */
  SPACE_CONSOLE,
  /**
   * Space console effect 2
   */
  SPACE_CONSOLE2,
  /**
   * Space console effect 3
   */
  SPACE_CONSOLE3,
  /**
   * Bright cyan
   */
  BRIGHT_CYAN,
  /**
   * Blueish white
   */
  BLUEISH_WHITE,
  /**
   * Dark orange
   */
  DARK_ORANGE,
  /**
   * Dark red
   */
  DARK_RED,
  /**
   * Purple dream
   */
  PURPLE_DREAM,
  /**
   * Fade in start
   */
  FADE_IN_START,
  /**
   * Fade in
   */
  FADE_IN,
  /**
   * Blinking orange light
   */
  ORANGE_BLINK,
  /**
   * Orange Blue
   */
  ORANGE_BLUE,
  /**
   * GreyBlue
   */
  GREYBLUE,
  /**
   * Jungle
   */
  JUNGLE;

  /**
   * Get BridgeCommandType as a string.
   * @return String.
   */
  public String getName() {
    switch (this) {
    case REGISTER: return "register";
    case TEST: return "test";
    case EXIT: return "exit";
    case FETCH_LIGHTS: return "fetch_lights";
    case WARM_WHITE: return "warm_white";
    case DARKEST: return "darkest";
    case RED_ALERT: return "red_alert";
    case YELLOW_ALERT: return "yellow_alert";
    case NUKE_START: return "nuke_start";
    case NUKE_FADE: return "nuke_fade";
    case FLOAT_IN_SPACE: return "float_in_space";
    case GREEN_CONSOLE: return "green_console";
    case SPACE_CONSOLE: return "space_console";
    case SPACE_CONSOLE2: return "space_console2";
    case SPACE_CONSOLE3: return "space_console3";
    case BRIGHT_CYAN: return "bright_cyan";
    case BLUEISH_WHITE: return "blueish_white";
    case DARK_ORANGE: return "dark_orange";
    case DARK_RED: return "dark_red";
    case PURPLE_DREAM: return "purple_dream";
    case FADE_IN_START: return "fade_in_start";
    case FADE_IN: return "fade_in";
    case ORANGE_BLINK: return "orange_blink";
    case ORANGE_BLUE: return "orange_blue";
    case GREYBLUE: return "greyblue";
    case JUNGLE: return "jungle";
    default:
      throw new IllegalArgumentException("Missing bridge command name.");
    }
  }

  /**
   * Get BridgeCommandType by String.
   * @param value String
   * @return BridgeCommandType
   */
  public static BridgeCommandType getByString(final String value) {
    for (BridgeCommandType type : BridgeCommandType.values()) {
      if (type.getName().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unexpected bridge command type, "
        + value + ".");
  }
}
