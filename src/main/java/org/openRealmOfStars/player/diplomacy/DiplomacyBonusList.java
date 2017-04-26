package org.openRealmOfStars.player.diplomacy;

import java.util.ArrayList;
import java.util.List;

import org.openRealmOfStars.player.PlayerInfo;

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
* Diplomacy Bonus List and handling of it
*
*/
public class DiplomacyBonusList {

  /**
   * PlayerInfo with whom diplomacy is affecting
   */
  private PlayerInfo info;

  /**
   * This is for saving PlayerInfo index for saved game
   */
  private int playerIndex;

  /**
   * Diplomacy Bonus list
   */
  private List<DiplomacyBonus> list;

  /**
   * Constructor for Diplomacy Bonus List
   * @param index Index for player info
   * @param player Player info with whom this diplomacy affects
   */
  public DiplomacyBonusList(final int index, final PlayerInfo player) {
    this.info = player;
    this.playerIndex = index;
    list = new ArrayList<>();
  }

  /**
   * Get Player Info with diplomacy affects
   * @return PlayerInfo
   */
  public PlayerInfo getInfo() {
    return info;
  }

  /**
   * Get Player's index number
   * @return Player's index number
   */
  public int getPlayerIndex() {
    return playerIndex;
  }

  /**
   * Get diplomacy Bonus
   * @return Total diplomacy bonus
   */
  public int getDiplomacyBonus() {
    int result = 0;
    for (DiplomacyBonus bonus : list) {
      result = result + bonus.getBonusValue();
    }
    return result;
  }
}
