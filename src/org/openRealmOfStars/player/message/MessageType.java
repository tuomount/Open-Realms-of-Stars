package org.openRealmOfStars.player.message;


/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
  INFORMATION;

  
  /**
   * Get Message type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case RESEARCH: return 0;
    case CONSTRUCTION: return 1;
    case INFORMATION: return 2;
    }
    return 0;
  }
  
  /**
   * Return Message Type by index
   * @param index This must be between 0-5
   * @return Tech Type
   */
  public static MessageType getTypeByIndex(int index) {
    switch (index) {
    case 0: return MessageType.RESEARCH;
    case 1: return MessageType.CONSTRUCTION;
    case 2: return MessageType.INFORMATION;
    }
    return MessageType.RESEARCH;
  }

  
  @Override
  public String toString() {
    switch (this) {
    case RESEARCH: return "Research";
    case CONSTRUCTION: return "Construction";
    case INFORMATION: return "Information";
    }
    return "Error - Unknown";

  }

}
