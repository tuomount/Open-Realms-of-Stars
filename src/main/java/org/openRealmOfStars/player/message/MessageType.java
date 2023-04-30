package org.openRealmOfStars.player.message;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2020,2023 Tuomo Untinen
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
 * Message type
 *
 */

public enum MessageType {
  /**
   * Player has researched new technology
   */
  RESEARCH,
  /**
   * Player's planet has built new construction
   */
  CONSTRUCTION,
  /**
   * Information for player, no focus
   */
  INFORMATION,
  /**
   * Population has decreased or increased on planet
   */
  POPULATION,
  /**
   * Planetary information, focus to planet
   */
  PLANETARY,
  /**
   * Fleet information, focus to fleet
   */
  FLEET,
  /**
   * GBNC has news message
   */
  NEWS,
  /**
   * Leader information, focus to leader
   */
  LEADER,
  /**
   * Story event
   */
  STORY;

  /**
   * Get Message type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case RESEARCH:
      return 0;
    case CONSTRUCTION:
      return 1;
    case INFORMATION:
      return 2;
    case POPULATION:
      return 3;
    case PLANETARY:
      return 4;
    case FLEET:
      return 5;
    case NEWS:
      return 6;
    case LEADER:
      return 7;
    case STORY:
      return 8;
    default:
      return 0;
    }
  }

  /**
   * Return Message Type by index
   * @param index The message type index
   * @return Tech Type
   */
  public static MessageType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return MessageType.RESEARCH;
    case 1:
      return MessageType.CONSTRUCTION;
    case 2:
      return MessageType.INFORMATION;
    case 3:
      return MessageType.POPULATION;
    case 4:
      return MessageType.PLANETARY;
    case 5:
      return MessageType.FLEET;
    case 6:
      return MessageType.NEWS;
    case 7:
      return MessageType.LEADER;
    case 8:
      return MessageType.STORY;
    default:
      return MessageType.RESEARCH;
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case RESEARCH:
      return "Research";
    case CONSTRUCTION:
      return "Construction";
    case INFORMATION:
      return "Information";
    case POPULATION:
      return "Population";
    case PLANETARY:
      return "Planetary";
    case FLEET:
      return "Fleet";
    case NEWS:
      return "News";
    case LEADER:
      return "Leader";
    case STORY:
      return "Story";
    default:
      return "Error - Unknown";
    }

  }

}
