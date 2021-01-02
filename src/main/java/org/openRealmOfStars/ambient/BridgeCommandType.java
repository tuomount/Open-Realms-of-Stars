package org.openRealmOfStars.ambient;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020,2021 Tuomo Untinen
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
  FADE_IN;


}
