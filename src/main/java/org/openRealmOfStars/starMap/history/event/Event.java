package org.openRealmOfStars.starMap.history.event;

import org.openRealmOfStars.starMap.StarMap;

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
* Abstract Event
*
*/
public abstract class Event {

  /**
   * Event Type
   */
  private EventType type;

  /**
   * Player index, -1 is for no player
   */
  private byte playerIndex;

  /**
   * Get Event Type
   * @return EventType
   */
  public EventType getType() {
    return type;
  }

  /**
   * Set Event type for event.
   * This should be called only in constructors.
   * @param eventType Event Type
   */
  public void setType(final EventType eventType) {
    type = eventType;
  }

  /**
   * Set Player Index. -1 is for no player index.
   * @param index Player Index
   */
  public void setPlayerIndex(final int index) {
    if (index >= StarMap.MAX_PLAYERS) {
      playerIndex = 7;
    } else if (index < -1) {
      playerIndex = -1;
    } else {
      playerIndex = (byte) index;
    }
  }

  /**
   * Get Player Index. Index -1 means generic event or no player.
   * @return Player index
   */
  public int getPlayerIndex() {
    return playerIndex;
  }
}
