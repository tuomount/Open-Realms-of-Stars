package org.openRealmOfStars.starMap.history.event;

import org.openRealmOfStars.starMap.Coordinate;
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
* Culture change Event
*
*/
public class CultureEvent extends Event {

  /**
   * Coordinate where culture changed
   */
  private Coordinate coordinate;

  /**
   * Culture player index, -1 is for empty culture
   */
  private byte playerIndex;

  /**
   * Create culture change event
   * @param coord Coordinate where change culture
   * @param index Player index
   */
  public CultureEvent(final Coordinate coord, final int index) {
    setType(EventType.CULTURE_CHANGE);
    coordinate = coord;
    if (index >= StarMap.MAX_PLAYERS) {
      playerIndex = 7;
    } else if (index < -1) {
      playerIndex = -1;
    } else {
      playerIndex = (byte) index;
    }
  }

  /**
   * Get coordinate where culture has changed;
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get Player Index. Index -1 means that culture is cleared.
   * @return Player index for culture change.
   */
  public int getPlayerIndex() {
    return playerIndex;
  }
}
