package org.openRealmOfStars.player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.player.diplomacy.DiplomacyBonus;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.government.GovernmentType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
   * Get maximum players, including humans and board players
   * @return The number of players
   */
  public int getCurrentMaxPlayers() {
    return list.size();
  }

  /**
   * Get number of player including only human and AI players.
   * @return The number of players
   */
  public int getCurrentMaxRealms() {
    int count = 0;
    for (PlayerInfo info : list) {
      if (!info.isBoard()) {
        count++;
      }
    }
    return count;
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
   * Get board player info
   * @return The board player info or null
   */
  public PlayerInfo getBoardPlayer() {
    for (int i = list.size() - 1; i > -1; i--) {
      PlayerInfo info = list.get(i);
      if (info.isBoard()) {
        return info;
      }
    }
    return null;
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

  /**
   * Handle players fake military cost.
   * This method can tune down fake military
   * if player does not have enough credits.
   */
  public void handlePlayerFakeMilitaryCost() {
    for (int i = 0; i < list.size(); i++) {
      PlayerInfo info = list.get(i);
      info.handleFakeMilitarySizeCost();
    }
  }

  /**
   * Return player infos in order where their realm names are found
   * from the text.
   * @param text Containing player realm names
   * @return PlayerInfo array
   */
  public PlayerInfo[] findRealmNamesFromText(final String text) {
    ArrayList<PlayerInfo> result = new ArrayList<>();
    int[] offsets = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      PlayerInfo info = list.get(i);
      offsets[i] = text.indexOf(info.getEmpireName());
    }
    boolean done;
    do {
      done = true;
      int smallest = Integer.MAX_VALUE;
      int index = -1;
      for (int i = 0; i < offsets.length; i++) {
        if (offsets[i] > -1) {
          done = false;
          if (offsets[i] < smallest) {
            index = i;
            smallest = offsets[i];
          }
        }
      }
      if (index != -1) {
        result.add(list.get(index));
        offsets[index] = -1;
      }
    } while (!done);
    return result.toArray(new PlayerInfo[result.size()]);
  }

  /**
   * Calculate initial diplomacy Bonuses for all players.
   */
  public void calculateInitialDiplomacyBonuses() {
    int maxPlayers = getCurrentMaxPlayers();
    for (int i = 0; i < maxPlayers; i++) {
      PlayerInfo info = getPlayerInfoByIndex(i);
      for (int j = 0; j < maxPlayers; j++) {
        DiplomacyBonusList bonus = info.getDiplomacy().getDiplomacyList(j);
        if (bonus != null) {
          PlayerInfo info2 = getPlayerInfoByIndex(j);
          bonus.addBonus(DiplomacyBonusType.DIPLOMACY_BONUS, info2.getRace());
          if (info2.isBoard()) {
            // Board AI has war against every body
            bonus.addBonus(DiplomacyBonusType.IN_WAR, info.getRace());
          }
          if (info.isBoard()) {
            // Board AI has war against every body
            bonus.addBonus(DiplomacyBonusType.IN_WAR, info.getRace());
          }
          GovernmentType government = info2.getGovernment();
          DiplomacyBonus diplomacyBonus = new DiplomacyBonus(
              DiplomacyBonusType.DIPLOMACY_BONUS, info2.getRace());
          diplomacyBonus.setBonusValue(government.getDiplomaticBonus());
          if (diplomacyBonus.getBonusValue() != 0) {
            bonus.addBonus(diplomacyBonus);
          }
          if (info.getRace() == info2.getRace()) {
            bonus.addBonus(DiplomacyBonusType.SAME_RACE, info2.getRace());
          }
        }
      }
    }

  }
}
