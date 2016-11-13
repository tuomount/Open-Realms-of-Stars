package org.openRealmOfStars.starMap;

import org.openRealmOfStars.player.SpaceRace;

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
 * Galaxy config for creating new star map with player
 *
 */
public class GalaxyConfig {

  /**
   * Galaxy size X coordinates
   */
  private int sizeX;
  /**
   * Galaxy size Y coordinates
   */
  private int sizeY;

  /**
   * Galaxy size index in UI
   */
  private int galaxySizeIndex;

  /**
   * How many players there are
   */
  private int maxPlayers;

  /**
   * How far away solar systems are from each others
   */
  private int solarSystemDistance;

  /**
   * Sun density index in UI
   */
  private int sunDensityIndex;

  /**
   * Players start from the map border
   */
  public final static int START_POSITION_BORDER = 0;

  /**
   * Players start from random position
   */
  public final static int START_POSITION_RANDOM = 1;

  /**
   * Where players start
   */
  private int startingPosition;

  /**
   * Player races
   */
  private SpaceRace[] playerRaces;

  /**
   * Player name
   */
  private String[] playerName;

  /**
   * Constructor for galaxy config
   */
  public GalaxyConfig() {
    sizeX = 75;
    sizeY = 75;
    this.galaxySizeIndex = 1;
    setMaxPlayers(4);
    setSolarSystemDistance(12, 0);
    playerRaces = new SpaceRace[StarMap.MAX_PLAYERS];
    playerName = new String[StarMap.MAX_PLAYERS];
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {

      setRace(i, SpaceRace.getRandomRace());
      while (true) {
        String tmp = getRace(i).getRandomName();
        if (isUniqueName(tmp)) {
          setPlayerName(i, getRace(i).getRandomName());
          break;
        }
      }
    }
  }

  /**
   * Is player name unique
   * @param name Unique name
   * @return True if unique
   */
  public boolean isUniqueName(final String name) {
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
      if (playerName[i] != null && name.equals(playerName[i])) { return false; }
    }
    return true;
  }

  public void setRace(final int index, final SpaceRace race) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      playerRaces[index] = race;
    }
  }

  public SpaceRace getRace(final int index) {
    if (index >= 0
        && index < StarMap.MAX_PLAYERS) { return playerRaces[index]; }
    return null;
  }

  public void setPlayerName(final int index, final String name) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      playerName[index] = name;
    }
  }

  public String getPlayerName(final int index) {
    if (index >= 0
        && index < StarMap.MAX_PLAYERS) { return playerName[index]; }
    return null;
  }

  /**
   * Set galaxy size. Galaxy is set for square size
   * @param size side length
   * @param galaxySize Galaxy size index in UI
   */
  public void setSize(final int size, final int galaxySize) {
    this.sizeX = size;
    this.sizeY = size;
    this.galaxySizeIndex = galaxySize;
  }

  public int getSizeX() {
    return sizeX;
  }

  public int getSizeY() {
    return sizeY;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(final int maxPlayers) {
    if (maxPlayers >= 2 && maxPlayers <= 8) {
      this.maxPlayers = maxPlayers;
    }
  }

  public int getSolarSystemDistance() {
    return solarSystemDistance;
  }

  /**
   * Set Solar system distance
   * @param solarSystemDistance Actual distance between solar system
   * @param sunDensity Sun density setting in ui index
   */
  public void setSolarSystemDistance(final int solarSystemDistance,
      final int sunDensity) {
    this.solarSystemDistance = solarSystemDistance;
    this.sunDensityIndex = sunDensity;
  }

  public int getStartingPosition() {
    return startingPosition;
  }

  public void setStartingPosition(final int startingPosition) {
    this.startingPosition = startingPosition;
  }

  public int getSunDensityIndex() {
    return sunDensityIndex;
  }

  public int getGalaxySizeIndex() {
    return galaxySizeIndex;
  }

}
