package org.openRealmOfStars.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.repository.GameRepository;

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
 * Saved game information
 *
 */
public class SavedGame {

  /**
   * Player's race
   */
  private SpaceRace playerRace;

  /**
   * Which turn
   */
  private int turnNumber;

  /**
   * Galaxy size as string
   */
  private String galaxySize;

  /**
   * Filename of saved game file
   */
  private String filename;

  /**
   * First player's empire name
   */
  private String empireName;

  /**
   * File's creation time
   */
  private String creationTime;

  /**
   * Load game from certain file name and get all information from saved
   * game
   * @param filename File name
   * @throws IOException if reading fails
   */
  public SavedGame(final String filename) throws IOException {
    String folderName = "saves";
    File file = new File(folderName + "/" + filename);
    BasicFileAttributes attr = Files.readAttributes(file.toPath(),
        BasicFileAttributes.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    creationTime = dateFormat.format(attr.creationTime().toMillis());
    initializeSavedGame(filename, new GameRepository());
  }

  /**
   * Load game from certain file name and get all information from saved
   * game. Use this method only from JUnits.
   * @param filename File name
   * @param repository Game repository for loading
   * @throws IOException if reading fails
   */
  public SavedGame(final String filename, final GameRepository repository)
      throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    creationTime = dateFormat.format(System.currentTimeMillis());
    initializeSavedGame(filename, repository);
  }

  /**
   * Do the actual save game loading
   * @param file Filename to load
   * @param repository GameRepository for loading the game
   * @throws IOException if reading fails
   */
  private void initializeSavedGame(final String file,
      final GameRepository repository) throws IOException {
    StarMap starMap = repository.loadGame(file);
    this.filename = file;
    turnNumber = starMap.getTurn();
    galaxySize = starMap.getMaxX() + " X " + starMap.getMaxY();
    playerRace = starMap.getPlayerList().getPlayerInfoByIndex(0).getRace();
    empireName = starMap.getPlayerList().getPlayerInfoByIndex(0)
        .getEmpireName();
  }

  /**
   * Get first player's space race.
   * @return Space race
   */
  public SpaceRace getPlayerRace() {
    return playerRace;
  }

  /**
   * Get which turn number it was on saved game.
   * @return Turn number
   */
  public int getTurnNumber() {
    return turnNumber;
  }

  /**
   * Get galaxy size as a textual information.
   * @return Galaxy size as a text
   */
  public String getGalaxySize() {
    return galaxySize;
  }

  /**
   * Get save game file name
   * @return File name as a String
   */
  public String getFilename() {
    return filename;
  }

  /**
   * Get first player's empire name
   * @return Empire name as a String.
   */
  public String getEmpireName() {
    return empireName;
  }

  /**
   * Get save file's creation time
   *
   * @return save file's creation time
   */
  public String getTime() {
    return creationTime;
  }

}
