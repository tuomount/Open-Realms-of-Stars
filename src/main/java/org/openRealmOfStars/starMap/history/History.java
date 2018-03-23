package org.openRealmOfStars.starMap.history;

import java.util.ArrayList;

import org.openRealmOfStars.starMap.history.event.Event;

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
* Historical information about game being played
*
*/
public class History {

  /**
   * List of turns in history
   */
  private ArrayList<HistoryTurn> listOfTurns;

  /**
   * Constructor for creating history for single game.
   */
  public History() {
    listOfTurns = new ArrayList<>();
  }

  /**
   * Get the number of turns in history. NOTE this does not
   * mean actual number of turn, just turns which contain historical
   * information.
   * @return Number of historically important turns
   */
  public int numberOfTurns() {
    return listOfTurns.size();
  }

  /**
   * Get the latest turn from history
   * @return Laster history turn or null if no turns at all.
   */
  public HistoryTurn getLatestTurn() {
    if (listOfTurns.size() > 0) {
      return listOfTurns.get(listOfTurns.size() - 1);
    }
    return null;
  }

  /**
   * Add Event to latest turn. This throws IllegalArgumentException
   * if no turns in history
   * @param event Event to add.
   */
  public void addEvent(final Event event) {
    HistoryTurn latest = getLatestTurn();
    if (latest != null) {
      latest.addEvent(event);
    } else {
      throw new IllegalArgumentException("No turns at all in history!");
    }
  }

  /**
   * Add new turn into history.
   * @param turnNumber Turn number to add
   */
  public void addTurn(final int turnNumber) {
    HistoryTurn  turn = new HistoryTurn(turnNumber);
    listOfTurns.add(turn);
  }

  /**
   * Get historical turn by index. Can return null if no turn
   * found by index.
   * @param index Index to search
   * @return HistoryTurn or null.
   */
  public HistoryTurn getByIndex(final int index) {
    if (index >= 0 && index < numberOfTurns()) {
      return listOfTurns.get(index);
    }
    return null;
  }
}
