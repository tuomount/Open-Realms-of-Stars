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
* Good Random event type
*
*/
public enum GoodRandomType {
  /**
   * Mysterious signal received from solar system.
   * This signal is from another realm which player hasn't met yet.
   */
  MYSTERIOUS_SIGNAL,
  /**
   * Climate changes on planet to provide more food.
   * DESERT->ARID->None->Lush Vegatation->Paradise
   */
  CLIMATE_CHANGE,
  /**
   * Double the research points in one tech level
   */
  TECHNICAL_BREAKTHROUGH,
  /**
   * Lost treasure found, +50 credits.
   */
  LOST_TREASURE_FOUND,
  /**
   * All planets in system decrease radiation level.
   */
  SOLAR_ACTIVITY_DIMISHED,
  /**
   * Alien ship appears near the planet, but all crew is gone.
   * Realm gain access a new ship.
   */
  DESERTED_SHIP,
  /**
   * Meteoroid just misses the planet, but scientist are able mine
   * metal from it.
   */
  MISSED_METEOROID,
  /**
   * Leader gains level by getting his/hers achievements done earlier.
   */
  LEADER_LEVEL;
}
