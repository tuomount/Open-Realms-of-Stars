package org.openRealmOfStars.player.tech;
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
 * Enum for tech types
 * 
 */

public enum TechType {
   Combat,
   Defense,
   Hulls,
   Improvements,
   Propulsion,
   Electrics;
  
  /**
   * Get Tech type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case Combat: return 0;
    case Defense: return 1;
    case Hulls: return 2;
    case Improvements: return 3;
    case Propulsion: return 4;
    case Electrics: return 5;
    }
    return 0;
  }

   
   
}
