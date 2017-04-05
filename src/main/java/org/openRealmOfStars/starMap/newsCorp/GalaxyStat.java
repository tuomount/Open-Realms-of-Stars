package org.openRealmOfStars.starMap.newsCorp;

import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
