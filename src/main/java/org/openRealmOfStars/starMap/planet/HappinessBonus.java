package org.openRealmOfStars.starMap.planet;

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
* Happiness and sadness bonus enumeration
*
*/
public enum HappinessBonus {
  /**
   * No bonus at all
   */
  NONE,
  /**
   * Bonus for production, negative value for sad bonus
   */
  PRODUCTION,
  /**
   * Bonus for credit, negative value for sad bonus
   */
  CREDIT,
  /**
   * Bonus for food, negative value for sad bonus
   */
  FOOD,
  /**
   * Bonus for Mined metal, negative value for sad bonus
   */
  METAL,
  /**
   * Culture bonus, negative value for sad bonus
   */
  CULTURE,
  /**
   * Kill population, sad bonus
   */
  KILL_POPULATION,
  /**
   * Destroy building, sad bonus
   */
  DESTROY_BUILDING;
}
