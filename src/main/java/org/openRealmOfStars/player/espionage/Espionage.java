package org.openRealmOfStars.player.espionage;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Espionage class for handling espionage between realms.
* This contains espionage information for one realm.
*
*/
public class Espionage {

  /**
   * Espionage information for each realm
   */
  private EspionageList[] espionageLists;

  /**
   * Constructor for Espionage
   * @param maxPlayers Maximum number of player
   */
  public Espionage(final int maxPlayers) {
    espionageLists = new EspionageList[maxPlayers];
    for (int i = 0; i < maxPlayers; i++) {
      espionageLists[i] = new EspionageList(i);
    }
  }

  /**
   * Get espionage size
   * @return Espionage size
   */
  public int getSize() {
    return espionageLists.length;
  }

  /**
   * Get Espionage list for certain realm
   * @param index Realm/Player index
   * @return Espionage list or null if not found
   */
  public EspionageList getByIndex(final int index) {
    if (index > -1 && index < espionageLists.length) {
      return espionageLists[index];
    }
    return null;
  }

  /**
   * Clear all espionage list bonus.
   * This should be called after each turn.
   */
  public void clearAllEspionageBonuses() {
    for (EspionageList list : espionageLists) {
      if (list != null) {
        list.clearList();
      }
    }
  }

  /**
   *  Calculate espionage cost from fakeMilitarySize;
   *  FakeMilitarySize should be from 50 to 200.
   * @param fakeMilitarySize Fake military size between 50 to 200 percent.
   * @return Cost of espionage
   */
  public static int calculateEspionageCost(final int fakeMilitarySize) {
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
