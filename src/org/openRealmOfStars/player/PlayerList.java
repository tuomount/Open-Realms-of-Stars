package org.openRealmOfStars.player;

import java.util.ArrayList;

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
 * List of PLayer infos
 * 
 */
public class PlayerList {

  
  /**
   * Maximum players
   */
  public static final int MAX_PLAYERS = 4;

  /**
   * List of players
   */
  private ArrayList<PlayerInfo> list;
  
  /**
   * Current player
   */
  private int currentPlayer;
  
  public PlayerList() {
    list = new ArrayList<>();
    currentPlayer = 0;
  }
  
  /**
   * Add new player to list
   * @param info PlayerInfo
   */
  public void addPlayer(PlayerInfo info) {
    list.add(info);
  }
  
  /**
   * Get PlayerInfo by index. If index is out of bounds then null is returned.
   * @param index 
   * @return PlayerInfo or null
   */
  public PlayerInfo getPlayerInfoByIndex(int index) {
    if (index > -1 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }
  
  /**
   * Get maximum players
   * @return
   */
  public int getCurrentMaxPlayers() {
    return list.size();
  }
  
  /**
   * Set currently playing player by index
   * @param index 0 - MAX_PLAYERS
   */
  public void setCurrentPlayer(int index) {
    if (index > -1 && index < list.size()) {
      currentPlayer = index;
    }
  }
  
  /**
   * Get current player index
   * @return
   */
  public int getCurrentPlayer() {
    return currentPlayer;
  }
  
  /**
   * Get Current player info
   * @return PlayerInfo
   */
  public PlayerInfo getCurrentPlayerInfo() {
    return getPlayerInfoByIndex(currentPlayer);
  }
  
  /**
   * Get player index for player info
   * @param toMatch PlayerInfo to match
   * @return Index or -1 if no match
   */
  public int getIndex(PlayerInfo toMatch) {
    for (int i=0;i<list.size();i++) {
      PlayerInfo info = list.get(i);
      if (info.hashCode() == toMatch.hashCode() &&
          info.getEmpireName().equals(toMatch.getEmpireName())) {
        return i;
      }
    }
    return -1;
  }
  /**
   * Init players visibility maps
   * @param maxX Map x size
   * @param maxY Map y size
   */
  public void initVisibilityMaps(int maxX, int maxY) {
    for (PlayerInfo info : list) {
      info.initMapData(maxX, maxY);
    }
  }
  
}
