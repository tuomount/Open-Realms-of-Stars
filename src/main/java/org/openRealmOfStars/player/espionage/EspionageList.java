package org.openRealmOfStars.player.espionage;

import java.util.ArrayList;
import java.util.List;

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
* Espionage List
*
*/
public class EspionageList {

  /**
   * This is for PlayerInfo index
   */
  private int playerIndex;

  /**
   * Espionage list
   */
  private List<EspionageBonus> list;

  /**
   * Get player index. This tells which realm is being
   * spied.
   * @return Player index
   */
  public int getPlayerIndex() {
    return playerIndex;
  }

  /**
   * Constructor Espionage list.
   * @param index Player index who is being spied.
   */
  public EspionageList(final int index) {
    playerIndex = index;
    list = new ArrayList<>();
  }

  /**
   * Get number of elements in list.
   * @return Number of elements in list
   */
  public int getSize() {
    return list.size();
  }

  /**
   * Adds new espionage bonus to list
   * @param type Espionage type
   * @param value Value
   * @param description Textual description about bonus
   */
  public void addEspionageBonus(final EspionageBonusType type,
      final int value, final String description) {
    EspionageBonus bonus = new EspionageBonus(type, value);
    bonus.setDescription(description);
    list.add(bonus);
  }

  /**
   * Get single espionage from the list by index
   * @param index Which espionage to fetch
   * @return Espionage bonus or null if not found.
   */
  public EspionageBonus getEspionage(final int index) {
    if (index >= 0 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }

  /**
   * Get total espionage bonus. This bonus is limited between
   * 0 and 10.
   * @return Total espionage bonus
   */
  public int getTotalBonus() {
    int result = 0;
    for (EspionageBonus bonus : list) {
      result = result + bonus.getValue();
    }
    if (result > 10) {
      result = 10;
    }
    // Zero is limited in EspionageBonus, there value cannot be negative.
    return result;
  }
}
