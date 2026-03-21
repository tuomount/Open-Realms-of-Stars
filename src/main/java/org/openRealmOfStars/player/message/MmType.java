package org.openRealmOfStars.player.message;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2026 Tuomo Untinen
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
 * Main Message type
 */
public enum MmType {
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
   * @return MainMessageType
   */
  public static MmType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return MmType.RESEARCH;
    case 1:
      return MmType.CONSTRUCTION;
    case 2:
      return MmType.INFORMATION;
    case 3:
      return MmType.POPULATION;
    case 4:
      return MmType.PLANETARY;
    case 5:
      return MmType.FLEET;
    case 6:
      return MmType.NEWS;
    case 7:
      return MmType.LEADER;
    case 8:
      return MmType.STORY;
    default:
      return MmType.RESEARCH;
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
