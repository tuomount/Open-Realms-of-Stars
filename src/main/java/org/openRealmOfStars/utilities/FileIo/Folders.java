package org.openRealmOfStars.utilities.FileIo;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
  /** Custom government folder */
  private static final String CUSTOM_GOV_FOLDER = "/custom/government";
  /** Custom space race folder */
  private static final String CUSTOM_RACE_FOLDER = "/custom/spacerace";
  /** Custom space race image folder */
  private static final String CUSTOM_RACE_IMAGE_FOLDER =
      "/custom/images/spacerace";
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
    return userFolder + CUSTOM_GOV_FOLDER;
  }

  /**
   * Get Path for Custom government path
   * @return Path for custom governments.
   */
  public static String getCustomSpaceRacePath() {
    if (userFolder == null) {
      initFolders();
    }
    return userFolder + CUSTOM_RACE_FOLDER;
  }

  /**
   * Get Path for Custom space race image path
   * @return Path for custom space race image.
   */
  public static String getCustomSpaceRaceImage() {
    if (userFolder == null) {
      initFolders();
    }
    return userFolder + CUSTOM_RACE_IMAGE_FOLDER;
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
    return userFolder + SCREENSHOT_FOLDER;
  }

  /**
   * Get Path for savegame
   * @return Path for savegames.
   */
  public static String getSavegamePath() {
    if (userFolder == null) {
      initFolders();
    }
    return userFolder + SAVEGAME_FOLDER;
  }

}
