package org.openRealmOfStars.starMap;

import org.openRealmOfStars.player.PlayerList;

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
 * Culture power for single sector
 * 
 */

public class CulturePower {

  /**
   * Culture value for each player
   */
  private int[] culture;
  
  /**
   * Constructor for culture power
   */
  public CulturePower() {
    culture = new int[PlayerList.MAX_PLAYERS];
  }
  
  /**
   * Reset culture for sector
   */
  public void reset() {
    for (int i=0;i<PlayerList.MAX_PLAYERS;i++) {
      culture[i] = 0;
    }
  }
  
  /**
   * Add culture for certain player
   * @param playerIndex PlayerIndex
   * @param value Culture value
   */
  public void addCulture(int playerIndex, int value) {
    if(playerIndex>0 && playerIndex < PlayerList.MAX_PLAYERS) {
      culture[playerIndex] = culture[playerIndex]+value;
    }
  }
  
  /**
   * Get player index for highest culture
   * @return Player index or -1 if no culture at all
   */
  public int getHighestCulture() {
    int value = 0;
    int index = -1;
    for (int i=0;i<PlayerList.MAX_PLAYERS;i++) {
      if (culture[i]>value) {
        index = i;
        value = culture[i];
      }
    }
    return index;
  }
  
}
