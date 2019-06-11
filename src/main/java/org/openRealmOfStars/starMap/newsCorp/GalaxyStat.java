package org.openRealmOfStars.starMap.newsCorp;

import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
* News Corporation Data Model
*
*/
public class GalaxyStat {

  /**
   * Galaxy Statistics name
   */
  private String galaxyStatisticsName;

  /**
   * Maximum number of players
   */
  private int maxPlayers;

  /**
   * Actual Galaxy statistics data
   */
  private DataModel[] dataStat;

  /**
   * Constructor for Galaxy Data model statistics.
   * @param numberOfPlayers Number of players in game
   * @param statName Statistics name
   * @throws IllegalArgumentException If numberOfPlayers less than one or
   * over StarMap.MAX_PLAYERS
   */
  public GalaxyStat(final int numberOfPlayers, final String statName)
    throws IllegalArgumentException {
    if (numberOfPlayers < 1 || numberOfPlayers > StarMap.MAX_PLAYERS) {
      throw new IllegalArgumentException("Invalid number of players: "
           + numberOfPlayers);
    }
    galaxyStatisticsName = statName;
    maxPlayers = numberOfPlayers;
    dataStat = new DataModel[maxPlayers];
    for (int i = 0; i < maxPlayers; i++) {
      dataStat[i] = new DataModel();
    }
  }

  /**
   * Get the actual Galaxy stat data as double integer array.
   * StatisticPanel can show this data directly
   * @return double int array
   */
  public int[][] getGalaxyData() {
    int[][] result = new int[maxPlayers][];
    for (int i = 0; i < maxPlayers; i++) {
      result[i] = dataStat[i].getData();
    }
    return result;
  }

  /**
   * Get maximum index for data
   * @return Size of the data
   */
  public int getMaxIndex() {
    return dataStat[0].getSize();
  }

  /**
   * Get Value for single data
   * @param player Player Index
   * @param index Data index
   * @return Data value or -1
   */
  public int getValue(final int player, final int index) {
    if (player >= 0 && player < maxPlayers && index >= 0
        && index < getMaxIndex()) {
      return dataStat[player].getData()[index];
    }
    return -1;
  }
  /**
   * Get number of stats
   * @return Number of stats in array
   */
  public int getNumberStats() {
    return dataStat[0].getSize();
  }
  /**
   * Add single data value for single player
   * @param playerIndex Player Index which to add data.
   * @param value Value to add
   */
  public void addStat(final int playerIndex, final int value) {
    if (playerIndex > -1 && playerIndex < maxPlayers) {
      dataStat[playerIndex].addData(value);
    }
  }

  /**
   * Get the latest value for Galaxy Stat for one player
   * @param playerIndex Player index
   * @return Latest value as int
   */
  public int getLatest(final int playerIndex) {
    if (playerIndex > -1 && playerIndex < maxPlayers) {
      int[] data = dataStat[playerIndex].getData();
      if (data.length > 0) {
        return data[data.length - 1];
      }
    }
    return 0;
  }
  /**
   * Get the biggest player index for stat
   * @return Biggest Player index for stat or -1 if all are zeros.
   */
  public int getBiggest() {
    int biggest = 0;
    int bigIndex = -1;
    for (int i = 0; i < maxPlayers; i++) {
      if (getLatest(i) > biggest) {
        bigIndex = i;
        biggest = getLatest(i);
      }
    }
    return bigIndex;
  }
  /**
   * Get the smallest player index for stat
   * @return Smallest Player index for stat or -1 if all are zeros.
   */
  public int getSmallest() {
    int smallest = Integer.MAX_VALUE;
    int smallIndex = -1;
    for (int i = 0; i < maxPlayers; i++) {
      if (getLatest(i) < smallest) {
        smallIndex = i;
        smallest = getLatest(i);
      }
    }
    return smallIndex;
  }
  /**
   * Get the second biggest player index for stat
   * @return second Biggest Player index for stat or -1 if all are zeros.
   */
  public int getSecond() {
    int biggest = 0;
    int second = 0;
    int bigIndex = -1;
    int secondIndex = -1;
    for (int i = 0; i < maxPlayers; i++) {
      if (getLatest(i) > biggest) {
        second = biggest;
        secondIndex = bigIndex;
        bigIndex = i;
        biggest = getLatest(i);
      } else if (getLatest(i) > second) {
        second = getLatest(i);
        secondIndex = i;
      }
    }
    return secondIndex;
  }
  /**
   * Get Galaxy Statistics Data name
   * @return  Galaxy Statistics data name
   */
  public String getGalaxyStatisticsName() {
    return galaxyStatisticsName;
  }

  /**
   * Get Max players for planet data
   * @return Max players
   */
  public int getMaxPlayers() {
    return maxPlayers;
  }


}
