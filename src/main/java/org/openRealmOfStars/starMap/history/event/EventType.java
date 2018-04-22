package org.openRealmOfStars.starMap.history.event;

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
* Event type for history information
*
*/
public enum EventType {
  /**
   * Culture has changed in game map for single sector
   */
  CULTURE_CHANGE,
  /**
   * Realm colonized planet
   */
  PLANET_COLONIZED,
  /**
   * Planer conquered
   */
  PLANET_CONQUERED,
  /**
   * Space combat
   */
  SPACE_COMBAT,
  /**
   * Diplomatic relation changed between two realms
   */
  DIPLOMATIC_RELATION_CHANGE,
  /**
   * Galatic news like biggest military, research etc.
   */
  GALATIC_NEWS,
  /**
   * Player start event
   */
  PLAYER_START;

  /**
   * Get Event type index
   * @return Index
   */
  public int getIndex() {
    switch (this) {
      case CULTURE_CHANGE: return 0;
      case PLANET_COLONIZED: return 1;
      case PLANET_CONQUERED: return 2;
      case SPACE_COMBAT: return 3;
      case DIPLOMATIC_RELATION_CHANGE: return 4;
      case GALATIC_NEWS: return 5;
      case PLAYER_START: return 6;
      default: return 0;
    }
  }

  /**
   * Get Event type for index
   * @param index Which index is used for fetching event type
   * @return Event Type
   */
  public static EventType getTypeByIndex(final int index) {
    switch (index) {
      case 0: return EventType.CULTURE_CHANGE;
      case 1: return EventType.PLANET_COLONIZED;
      case 2: return EventType.PLANET_CONQUERED;
      case 3: return EventType.SPACE_COMBAT;
      case 4: return EventType.DIPLOMATIC_RELATION_CHANGE;
      case 5: return EventType.GALATIC_NEWS;
      case 6: return EventType.PLAYER_START;
      default: throw new IllegalArgumentException(
          "No event type found for index " + index);
    }
  }

  @Override
  public String toString() {
    switch (this) {
      case CULTURE_CHANGE: return "Culture change";
      case PLANET_COLONIZED: return "Planet colonized";
      case PLANET_CONQUERED: return "Planet conquered";
      case SPACE_COMBAT: return "Space combat";
      case DIPLOMATIC_RELATION_CHANGE: return "Diplomatic relation change";
      case GALATIC_NEWS: return "Galactic news";
      case PLAYER_START: return "Player start";
      default: return "Default";
    }
  }


}
