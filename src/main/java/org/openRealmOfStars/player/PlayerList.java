package org.openRealmOfStars.player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017  Tuomo Untinen
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
 * List of PLayer info
 *
 */
public class PlayerList {

  /**
   * List of players
   */
  private ArrayList<PlayerInfo> list;

  /**
   * Current player
   */
  private int currentPlayer;

  /**
   * Create a new PlayerList
   */
  public PlayerList() {
    list = new ArrayList<>();
    currentPlayer = 0;
  }

  /**
   * Call reInit for all players
   */
  public void reInit() {
    for (PlayerInfo info : list) {
      info.reInit();
    }
  }

  /**
   * Read PlayerList from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public PlayerList(final DataInputStream dis) throws IOException {
    currentPlayer = dis.readInt();
    int count = dis.readInt();
    list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      list.add(new PlayerInfo(dis));
    }
  }

  /**
   * Save Player List to DataOutputStream
   * @param dos The data output stream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void savePlayerList(final DataOutputStream dos) throws IOException {
    dos.writeInt(currentPlayer);
    dos.writeInt(list.size());
    for (int i = 0; i < list.size(); i++) {
      list.get(i).savePlayerInfo(dos);
    }
  }

  /**
   * Add new player to list
   * @param info PlayerInfo
   */
  public void addPlayer(final PlayerInfo info) {
    list.add(info);
  }

  /**
   * Get PlayerInfo by index. If index is out of bounds then null is returned.
   * @param index The player info index
   * @return PlayerInfo or null
   */
  public PlayerInfo getPlayerInfoByIndex(final int index) {
    if (index > -1 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }

  /**
   * Get maximum players
   * @return The number of players
   */
  public int getCurrentMaxPlayers() {
    return list.size();
  }

  /**
   * Set currently playing player by index
   * @param index 0 - MAX_PLAYERS
   */
  public void setCurrentPlayer(final int index) {
    if (index > -1 && index < list.size()) {
      currentPlayer = index;
    }
  }

  /**
   * Get current player index
   * @return The current player index
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
  public int getIndex(final PlayerInfo toMatch) {
    if (toMatch != null) {
      for (int i = 0; i < list.size(); i++) {
        PlayerInfo info = list.get(i);
        if (info.hashCode() == toMatch.hashCode()
            && info.getEmpireName().equals(toMatch.getEmpireName())) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Init players visibility maps
   * @param maxX Map x size
   * @param maxY Map y size
   */
  public void initVisibilityMaps(final int maxX, final int maxY) {
    for (PlayerInfo info : list) {
      info.initMapData(maxX, maxY);
    }
  }

}
