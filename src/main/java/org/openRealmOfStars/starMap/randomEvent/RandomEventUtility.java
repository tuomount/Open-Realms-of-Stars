package org.openRealmOfStars.starMap.randomEvent;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Random Event Utility for creating random events and handling.
*
*/
public final class RandomEventUtility {

  /**
   * Hiding the constructor
   */
  private RandomEventUtility() {
    // Hiding the constructor
  }

  /**
   * Create good random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  public static RandomEvent createGoodRandomEvent(final PlayerInfo realm) {
    GoodRandomType[] values = GoodRandomType.values();
    int index = DiceGenerator.getRandom(values.length - 1);
    RandomEvent event = new RandomEvent(values[index], realm);
    return event;
  }

  /**
   * Create bad random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  public static RandomEvent createBadRandomEvent(final PlayerInfo realm) {
    BadRandomType[] values = BadRandomType.values();
    int index = DiceGenerator.getRandom(values.length - 1);
    RandomEvent event = new RandomEvent(values[index], realm);
    return event;
  }
}
