package org.openRealmOfStars.utilities.repository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016  Tuomo Untinen
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
* Game repository class
*
*/
public class GameRepository {

  /**
   * Default save game folder name
   */
  public static final String DEFAULT_SAVE_FOLDER = "saves";

  /**
   * Save game for certain file name
   * @param folderName Folder name where to save game
   * @param filename File name
   * @param starMap StarMap to save to file
   */
  public void saveGame(final String folderName, final String filename,
                        final StarMap starMap) {
    if (starMap != null) {
      File folder = new File(folderName);
      if (!folder.exists()) {
        folder.mkdirs();
      }
      File file = new File(folderName + "/" + filename);
      try {
        FileOutputStream os = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        try (DataOutputStream dos = new DataOutputStream(bos)) {
          starMap.saveGame(dos);
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      } catch (FileNotFoundException e) {
        System.err.println("File could not be write: " + folderName + "/"
            + filename + "! " + e.getMessage());
      }
    }
  }

  /**
   * Load game from certain file name
   * @param folderName Folder name where to load saved games
   * @param filename File name
   * @return StarMap if successful, null if loading failed
   */
  public StarMap loadGame(final String folderName, final String filename) {
    File file = new File(folderName + "/" + filename);
    StarMap starMap = null;
    try (FileInputStream is = new FileInputStream(file)) {
      BufferedInputStream bis = new BufferedInputStream(is);
      DataInputStream dis = new DataInputStream(bis);
      starMap = new StarMap(dis);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return null;
    }
    return starMap;
  }

}
