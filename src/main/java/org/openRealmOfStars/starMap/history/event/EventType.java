package org.openRealmOfStars.starMap.history.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2025 Tuomo Untinen
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
 */

/**
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
   * Planet conquered
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
   * Galactic news like biggest military, research etc.
   */
  GALACTIC_NEWS,
  /**
   * Player start event
   */
  PLAYER_START,
  /**
   * Planet builds significant building
   */
  PLANET_BUILDING,
  /**
   * Artificial planet has been created
   */
  ARTIFICAL_PLANET_CREATED,
  /**
   * Event for leader
   */
  LEADER_EVENT,
  /**
   * Event for rift portal.
   */
  RIFT_PORTAL,
  /**
   * Event for ascension portal.
   */
  ASCENSION_PORTAL,
  /**
   * Event for travelling through ascension portal.
   */
  ASCENDED;

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
      case GALACTIC_NEWS: return 5;
      case PLAYER_START: return 6;
      case PLANET_BUILDING: return 7;
      case ARTIFICAL_PLANET_CREATED: return 8;
      case LEADER_EVENT: return 9;
      case RIFT_PORTAL: return 10;
      case ASCENSION_PORTAL: return 11;
      case ASCENDED: return 12;
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
      case 5: return EventType.GALACTIC_NEWS;
      case 6: return EventType.PLAYER_START;
      case 7: return EventType.PLANET_BUILDING;
      case 8: return EventType.ARTIFICAL_PLANET_CREATED;
      case 9: return EventType.LEADER_EVENT;
      case 10: return EventType.RIFT_PORTAL;
      case 11: return EventType.ASCENSION_PORTAL;
      case 12: return EventType.ASCENDED;
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
      case GALACTIC_NEWS: return "Galactic news";
      case PLAYER_START: return "Player start";
      case PLANET_BUILDING: return "Planet build significant building";
      case ARTIFICAL_PLANET_CREATED: return "Artificial planet created";
      case LEADER_EVENT: return "Leader event";
      case RIFT_PORTAL: return "Rift portal";
      case ASCENSION_PORTAL: return "Ascension portal";
      case ASCENDED: return "Ascended";
      default: return "Default";
    }
  }


}
