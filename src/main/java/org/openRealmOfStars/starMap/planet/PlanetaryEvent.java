package org.openRealmOfStars.starMap.planet;

import org.openRealmOfStars.starMap.planet.construction.Building;

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
* Planetary event when Colonization happens
*
*/
public enum PlanetaryEvent {

  /**
   * Nothing happens
   */
  NONE,
  /**
   * One extra food production
   */
  LUSH_VEGETATION,
  /**
   * Two extra food production
   */
  PARADISE,
  /**
   * One extra mining production
   */
  METAL_RICH_SURFACE,
  /**
   * Building ancient lab
   */
  ANCIENT_LAB,
  /**
   * Building ancient factory
   */
  ANCIENT_FACTORY,
  /**
   * Building ancient temple
   */
  ANCIENT_TEMPLE;

  /**
   * Extra food production due the planetary event
   * @return Extra food production
   */
  public int getExtraFoodProduction() {
    if (this == LUSH_VEGETATION) {
      return 1;
    }
    if (this == PlanetaryEvent.PARADISE) {
      return 2;
    }
    return 0;
  }

  /**
   * Event is only one time event. If true event should be removed
   * from planet after triggering.
   * @return True for one time event
   */
  public boolean oneTimeOnly() {
    if (this == PlanetaryEvent.ANCIENT_LAB) {
      return true;
    }
    if (this == PlanetaryEvent.ANCIENT_TEMPLE) {
      return true;
    }
    if (this == PlanetaryEvent.ANCIENT_FACTORY) {
      return true;
    }
    return false;
  }

  /**
   * Get possible one time building from event
   * @return Building or null
   */
  public Building getBuilding() {
    if (this == ANCIENT_TEMPLE) {
      return BuildingFactory.createByName("Ancient temple");
    }
    if (this == ANCIENT_LAB) {
      return BuildingFactory.createByName("Ancient lab");
    }
    if (this == ANCIENT_FACTORY) {
      return BuildingFactory.createByName("Ancient factory");
    }
    return null;
  }

  /**
   * Extra metal production due the planetary event
   * @return Extra metal production
   */
  public int getExtraMetalProduction() {
    if (this == PlanetaryEvent.METAL_RICH_SURFACE) {
      return 1;
    }
    return 0;
  }

  /**
   * Get planetary event as index;
   * @return Index
   */
  public int getIndex() {
    switch (this) {
      case NONE: return 0;
      case LUSH_VEGETATION: return 1;
      case PARADISE: return 2;
      case METAL_RICH_SURFACE: return 3;
      case ANCIENT_LAB: return 4;
      case ANCIENT_FACTORY: return 5;
      case ANCIENT_TEMPLE: return 6;
      default:
        throw new IllegalArgumentException("Unknown planetary event!!");
    }
  }

  /**
   * Get planetary event by index
   * @param index Index to fetch planetary event
   * @return PlanetaryEvent
   */
  public static PlanetaryEvent getByIndex(final int index) {
    switch (index) {
      case 0: return NONE;
      case 1: return LUSH_VEGETATION;
      case 2: return PARADISE;
      case 3: return METAL_RICH_SURFACE;
      case 4: return ANCIENT_LAB;
      case 5: return ANCIENT_FACTORY;
      case 6: return ANCIENT_TEMPLE;
      default:
        throw new IllegalArgumentException("Unknown planetary event!!");
    }
  }

}
