package org.openRealmOfStars.utilities.FileIo;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024-2026 Tuomo Untinen
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

import java.io.File;

/**
 * Utility to get specific folder from user's home folder.
 *
 */
public final class Folders {

  /** User folder */
  private static String userFolder;
  /** Boolean for Windows OS. */
  private static boolean windowsOs;
  /** Custom government folder */
  private static final String CUSTOM_GOV_FOLDER = "/custom/government";
  /** Custom space race folder */
  private static final String CUSTOM_RACE_FOLDER = "/custom/spacerace";
  /** Custom space race image folder */
  private static final String CUSTOM_RACE_IMAGE_FOLDER =
      "/custom/images/spacerace";
  /** Custom music folder */
  private static final String CUSTOM_MUSIC_FOLDER =
      "/custom/music";
  /** Custom space ship image folder */
  private static final String CUSTOM_SHIP_IMAGE_FOLDER =
      "/custom/images/ships";
  /** Custom space ship graphset folder */
  private static final String CUSTOM_SHIP_GRAPHSET_FOLDER =
      "/custom/graphset/ships";
  /** Custom space ship bridge image folder */
  private static final String CUSTOM_BRIDGE_IMAGE_FOLDER =
      "/custom/images/bridges";
  /** Custom space ship bridge graphset folder */
  private static final String CUSTOM_BRIDGE_GRAPHSET_FOLDER =
      "/custom/graphset/bridges";
  /** Custom speeches folder */
  private static final String CUSTOM_SPEECHES_FOLDER =
      "/custom/speeches";
  /** Folder to screenshots */
  private static final String SCREENSHOT_FOLDER = "/screenshots";
  /** Save game folder */
  private static final String SAVEGAME_FOLDER = "/saves";
  /**
   * Hiding constructor.
   */
  private Folders() {
    /* Nothing to do. */
  }

  /**
   * Init folders. Return null string if everything went fine.
   * Otherwise returns error message.
   * @return Null or error message.
   */
  public static String initFolders() {
    userFolder = System.getProperty("user.home");
    userFolder = userFolder + "/.oros";
    String osName = System.getProperty("os.name").toLowerCase();
    windowsOs = false;
    if (osName.contains("windows")) {
        windowsOs = true;
    }
    try {
      File path = new File(userFolder);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create .oros folder.";
      }
      path = new File(userFolder + CUSTOM_GOV_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom government folder.";
      }
      path = new File(userFolder + CUSTOM_RACE_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom space race folder.";
      }
      path = new File(userFolder + CUSTOM_RACE_IMAGE_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom space race image folder.";
      }
      path = new File(userFolder + CUSTOM_MUSIC_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom music folder.";
      }
      path = new File(userFolder + CUSTOM_SHIP_IMAGE_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom space ship image folder.";
      }
      path = new File(userFolder + CUSTOM_SHIP_GRAPHSET_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom space ship graphset folder.";
      }
      path = new File(userFolder + CUSTOM_BRIDGE_IMAGE_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom space ship bridge image folder.";
      }
      path = new File(userFolder + CUSTOM_BRIDGE_GRAPHSET_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom space ship bridge graphset folder.";
      }
      path = new File(userFolder + CUSTOM_SPEECHES_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create custom speeches folder.";
      }
      path = new File(userFolder + SCREENSHOT_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create screenshot folder.";
      }
      path = new File(userFolder + SAVEGAME_FOLDER);
      if (!path.exists() && !path.mkdirs()) {
        return "Could not create savegame folder.";
      }
    } catch (SecurityException e) {
      return "Creating folder failed: " + e.getMessage();
    }
    return null;
  }

  /**
   * Get Path for Custom government path
   * @return Path for custom governments.
   */
  public static String getCustomGovPath() {
    if (userFolder == null) {
      initFolders();
    }
    return userFolder + handleSeparator(CUSTOM_GOV_FOLDER);
  }

  /**
   * Get Path for Custom space race path
   * @return Path for custom space race.
   */
  public static String getCustomSpaceRacePath() {
    if (userFolder == null) {
      initFolders();
    }
    return userFolder + handleSeparator(CUSTOM_RACE_FOLDER);
  }

  /**
   * Get Path for Custom space race image path
   * @return Path for custom space race image.
   */
  public static String getCustomSpaceRaceImage() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_RACE_IMAGE_FOLDER);
  }

  /**
   * Get Path for Custom music path
   * @return Path for custom music.
   */
  public static String getCustomMusic() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_MUSIC_FOLDER);
  }

  /**
   * Get Path for Custom space ship image path
   * @return Path for custom space ship image.
   */
  public static String getCustomSpaceShipImage() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_SHIP_IMAGE_FOLDER);
  }

  /**
   * Get Path for Custom space ship graphset path
   * @return Path for custom space ship graphset.
   */
  public static String getCustomSpaceShipGraphset() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_SHIP_GRAPHSET_FOLDER);
  }

  /**
   * Get Path for Custom space ship bridge image path
   * @return Path for custom space ship bridge image.
   */
  public static String getCustomSpaceShipBridgeImage() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_BRIDGE_IMAGE_FOLDER);
  }

  /**
   * Get Path for Custom space ship bridge graphset path
   * @return Path for custom space ship bridge graphset.
   */
  public static String getCustomSpaceShipBridgeGraphset() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_BRIDGE_GRAPHSET_FOLDER);
  }

  /**
   * Get Path for Custom speeches path
   * @return Path for custom speeches.
   */
  public static String getCustomSpeeches() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + CUSTOM_SPEECHES_FOLDER);
  }

  /**
   * Get Open Realm of Stars user folder
   * @return Path for user folder for Open Realm of Stars.
   */
  public static String getUserFolder() {
    if (userFolder == null) {
      initFolders();
    }
    return userFolder;
  }

  /**
   * Get Path for Screenshot folder
   * @return Path for screenshots.
   */
  public static String getScreenShotPath() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + SCREENSHOT_FOLDER);
  }

  /**
   * Get Path for savegame
   * @return Path for savegames.
   */
  public static String getSavegamePath() {
    if (userFolder == null) {
      initFolders();
    }
    return handleSeparator(userFolder + SAVEGAME_FOLDER);
  }

  /**
   * Handle file and folder separator.
   * @param input String input
   * @return String input with correct separator.
   */
  public static String handleSeparator(final String input) {
    if (windowsOs) {
      return input.replace('/', '\\');
    }
    return input;
  }
}
