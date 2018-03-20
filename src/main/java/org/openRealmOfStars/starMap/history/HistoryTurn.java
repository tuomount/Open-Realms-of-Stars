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
* Historical turn containing list of events
*
*/
public class HistoryTurn {

  /**
   * Turn number
   */
  private int turnNumber;

  /**
   * List of Events
   */
  private ArrayList<Event> listOfEvents;

  /**
   * Constructor for History Turn
   * @param turn Turn number
   */
  public HistoryTurn(final int turn) {
    turnNumber = turn;
    listOfEvents = new ArrayList<>();
  }

  /**
   * Get Turn number.
   * @return turn number
   */
  public int getTurn() {
    return turnNumber;
  }

  /**
   * Get number of events in turn
   * @return Number of events
   */
  public int getNumberOfEvents() {
    return listOfEvents.size();
  }

  /**
   * Get Event by index.
   * @param index Index number for event
   * @return Event or null if not found by index
   */
  public Event getEvent(final int index) {
    if (index >= 0 && index < listOfEvents.size()) {
      return listOfEvents.get(index);
    }
    return null;
  }

  /**
   * Add Event to list.
   * @param event Event to add.
   */
  public void addEvent(final Event event) {
    listOfEvents.add(event);
  }
}
