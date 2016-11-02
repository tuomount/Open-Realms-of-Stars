package org.openRealmOfStars.mapTiles;

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
 * All information to draw fleet as a tile
 *
 */

public class FleetTileInfo {

  /**
   * Which race space ship it is.
   */
  private SpaceRace race;

  /**
   * Image index
   */
  private int imageIndex;

  /**
   * Player which has fleet on that position
   */
  private int playerIndex;

  /**
   * fleet index
   */
  private int fleetIndex;

  /**
   * Constructor for Fleet Tile info
   * @param race Space Race information
   * @param index Ship's image index
   * @param playerIndex player index
   * @param fleetIndex player's fleet index
   */
  public FleetTileInfo(final SpaceRace race, final int index,
      final int playerIndex, final int fleetIndex) {
    this.race = race;
    this.imageIndex = index;
    this.playerIndex = playerIndex;
    this.setFleetIndex(fleetIndex);
  }

  public SpaceRace getRace() {
    return race;
  }

  public void setRace(final SpaceRace race) {
    this.race = race;
  }

  public int getImageIndex() {
    return imageIndex;
  }

  public void setImageIndex(final int imageIndex) {
    this.imageIndex = imageIndex;
  }

  public int getPlayerIndex() {
    return playerIndex;
  }

  public void setPlayerIndex(final int playerIndex) {
    this.playerIndex = playerIndex;
  }

  public int getFleetIndex() {
    return fleetIndex;
  }

  public void setFleetIndex(final int fleetIndex) {
    this.fleetIndex = fleetIndex;
  }

}
