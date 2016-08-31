package org.openRealmOfStars.AI.Mission;
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
 * Mission Phase for AI
 * 
 */

public enum MissionPhase {
  
  /**
   * Building the fleet for the mission
   */
  BUILDING,
  /**
   * Traveling to target with FTL
   */
  TREKKING,
  /**
   * Executing the mission
   */
  EXECUTING;
  
  
  /**
   * Get Mission phase with index
   * @return index
   */
  public int getIndex() {
    switch (this) {
    case BUILDING: return 0;
    case TREKKING: return 1;
    case EXECUTING: return 2;
    }
    return 0;
  }
  
  /**
   * Get mission phase by index
   * @param index
   * @return Mission phase, never null
   */
  public static MissionPhase getType(int index) {
    switch (index) {
    case 0: return MissionPhase.BUILDING;
    case 1: return MissionPhase.TREKKING;
    case 2: return MissionPhase.EXECUTING;
    }
    return MissionPhase.BUILDING;
  }

  @Override
  public String toString() {
    switch (this) {
    case BUILDING: return "Building";
    case TREKKING: return "Traveling";
    case EXECUTING: return "Executing";
    }
    return "Unknown";
  }
  
  
}
