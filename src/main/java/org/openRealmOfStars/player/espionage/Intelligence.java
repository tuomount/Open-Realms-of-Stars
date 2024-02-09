package org.openRealmOfStars.player.espionage;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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
 */

/**
* Intelligence class for handling espionage between realms.
* This contains espionage information for one realm.
*
*/
public class Intelligence {

  /**
   * Intelligence information for each realm
   */
  private IntelligenceList[] intelligenceLists;

  /**
   * Constructor for Intelligence
   * @param maxPlayers Maximum number of player
   */
  public Intelligence(final int maxPlayers) {
    intelligenceLists = new IntelligenceList[maxPlayers];
    for (int i = 0; i < maxPlayers; i++) {
      intelligenceLists[i] = new IntelligenceList(i);
    }
  }

  /**
   * Get Intelligence size
   * @return Intelligence size
   */
  public int getSize() {
    return intelligenceLists.length;
  }

  /**
   * Get Intelligence list for certain realm
   * @param index Realm/Player index
   * @return Intelligence list or null if not found
   */
  public IntelligenceList getByIndex(final int index) {
    if (index > -1 && index < intelligenceLists.length) {
      return intelligenceLists[index];
    }
    return null;
  }

  /**
   * Clear all Intelligence list bonus.
   * This should be called after each turn.
   */
  public void clearAllIntelligenceBonuses() {
    for (IntelligenceList list : intelligenceLists) {
      if (list != null) {
        list.clearList();
      }
    }
  }

  /**
   * At least one players Intelligence must be 2 or more to be able to
   * Intelligence trade.
   * @return True if spy trade is possible.
   */
  public boolean isSpyTradePossible() {
    boolean result = false;
    for (IntelligenceList list : intelligenceLists) {
      if (list != null && list.getOwnBonus() >= 2) {
        result = true;
      }
    }
    return result;
  }
  /**
   *  Calculate Intelligence cost from fakeMilitarySize;
   *  FakeMilitarySize should be from 50 to 200.
   * @param fakeMilitarySize Fake military size between 50 to 200 percent.
   * @return Cost of Intelligence
   */
  public static int calculateIntelligenceCost(final int fakeMilitarySize) {
    int value = fakeMilitarySize;
    int cost = 0;
    if (value >= 80 && value <= 120) {
      cost = 0;
    }
    if (value >= 70 && value < 80) {
      cost = 1;
    }
    if (value >= 60 && value < 70) {
      cost = 2;
    }
    if (value >= 50 && value < 60) {
      cost = 3;
    }
    if (value > 120) {
      cost = (value - 120) / 10;
    }
    return cost;
  }
}
