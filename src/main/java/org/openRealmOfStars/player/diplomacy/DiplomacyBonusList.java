package org.openRealmOfStars.player.diplomacy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
* Diplomacy Bonus List and handling of it. This is list of diplomacy
* bonus for certain player.
*
*/
public class DiplomacyBonusList {

  /**
   * This is for saving PlayerInfo index for saved game
   */
  private int playerIndex;

  /**
   * Diplomacy Bonus list
   */
  private List<DiplomacyBonus> list;

  /**
   * Number of diplomatic meetings
   */
  private int numberOfMeetings;

  /**
   * Constructor for Diplomacy Bonus List. List contains
   * diplomacy bonuses for certain player by some player.
   * DiplomacyBonusList class does not define that player who
   * creates the list only the player who list affects.
   * @param index Index for player info
   */
  public DiplomacyBonusList(final int index) {
    this.playerIndex = index;
    list = new ArrayList<>();
    setNumberOfMeetings(0);
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

  /**
   * Make war so that Alliance, trade alliance and long peace
   * are removed from the list
   */
  private void makeWar() {
    Iterator<DiplomacyBonus> iterator = list.iterator();
    while (iterator.hasNext()) {
      DiplomacyBonus diplomacyBonus = iterator.next();
      if (diplomacyBonus.getType() == DiplomacyBonusType.IN_ALLIANCE
          || diplomacyBonus.getType() == DiplomacyBonusType.IN_TRADE_ALLIANCE
          || diplomacyBonus.getType() == DiplomacyBonusType.LONG_PEACE
          || diplomacyBonus.getType() == DiplomacyBonusType.IN_DEFENSIVE_PACT) {
        iterator.remove();
      }
    }
  }

  /**
   * Make peace so that IN_WAR is removed from the list.
   */
  private void makePeace() {
    Iterator<DiplomacyBonus> iterator = list.iterator();
    while (iterator.hasNext()) {
      DiplomacyBonus diplomacyBonus = iterator.next();
      if (diplomacyBonus.getType() == DiplomacyBonusType.IN_WAR) {
        iterator.remove();
      }
    }
  }

  /**
   * Checks if diplomacy bonus list contains certain bonus type.
   * Returns true if bonus type is found
   * @param type DiplomacyBonusType to find
   * @return True if type is found.
   */
  public boolean isBonusType(final DiplomacyBonusType type) {
    for (DiplomacyBonus tmp : list) {
      if (tmp.getType() == type) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the number of diplomacy bonuses on the list
   * @return Number of diplomacy bonuses on the list
   */
  public int getListSize() {
    return list.size();
  }

  /**
   * Get the single diplomacy bonus from the list
   * @param index Diplomacy bonus index
   * @return Diplomacy Bonus
   */
  public DiplomacyBonus get(final int index) {
    if (index > -1 && index < getListSize()) {
      return list.get(index);
    }
    return null;
  }
  /**
   * Add Diplomacy bonus to list. If diplomacy is type which can
   * be only one and there is already one in list, then nothing
   * is done.
   * @param type DiplomacyBonusType to add
   * @param race Race who is adding the bonus
   * @return True if bonus was really added, otherwise false
   */
  public boolean addBonus(final DiplomacyBonusType type, final SpaceRace race) {
    DiplomacyBonus bonus = new DiplomacyBonus(type, race);
    boolean found = false;
    boolean added = false;
    if (bonus.isOnlyOne()) {
      for (DiplomacyBonus tmp : list) {
        if (tmp.getType() == type) {
          found = true;
          break;
        }
      }
    }
    if (!found) {
      list.add(bonus);
      added = true;
      if (bonus.getType() == DiplomacyBonusType.IN_WAR) {
        makeWar();
      }
      if (bonus.getType() == DiplomacyBonusType.LONG_PEACE) {
        makePeace();
      }
    }
    return added;
  }

  /**
   * Adds diplomacy into bonus list
   * @param bonus to add into list
   */
  public void addBonus(final DiplomacyBonus bonus) {
    list.add(bonus);
  }

  /**
   * Handle diplomacy bonus list for one turn.
   * This will calculate bonus lasting and remove
   * bonuses which lasting is zero.
   */
  public void handleForTurn() {
    Iterator<DiplomacyBonus> iterator = list.iterator();
    while (iterator.hasNext()) {
      DiplomacyBonus bonus = iterator.next();
      bonus.handleBonusForTurn();
      if (bonus.getBonusLasting() == 0) {
        iterator.remove();
      }
    }
  }

  /**
   * Get the number of meetings two players have had.
   * @return the numberOfMeetings
   */
  public int getNumberOfMeetings() {
    return numberOfMeetings;
  }

  /**
   * Set the number of meetings two players have had.
   * @param numberOfMeetings the numberOfMeetings to set
   */
  public void setNumberOfMeetings(final int numberOfMeetings) {
    this.numberOfMeetings = numberOfMeetings;
  }
}
