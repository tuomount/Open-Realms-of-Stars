package org.openRealmOfStars.starMap.randomEvent;

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
* Random Event
*
*/
public class RandomEvent {

  /**
   * Bad Random event type. Event should have either bad or good type.
   * Not both.
   */
  private BadRandomType badType;

  /**
   * Good Random event type. Event should have either bad or good type.
   * Not both.
   */
  private GoodRandomType goodType;

  /**
   * Constructor for Bad random event type.
   * @param badType Bad random event type
   */
  public RandomEvent(final BadRandomType badType) {
    this.badType = badType;
    this.goodType = null;
  }

  /**
   * Constructor for Good random event type.
   * @param goodType Good random event type
   */
  public RandomEvent(final GoodRandomType goodType) {
    this.badType = null;
    this.goodType = goodType;
  }

  /**
   * Get random event's bad type.
   * @return Bad Event type or null.
   */
  public BadRandomType getBadType() {
    return badType;
  }

  /**
   * Get random event's good type.
   * @return Good event type or null.
   */
  public GoodRandomType getGoodType() {
    return goodType;
  }
}
