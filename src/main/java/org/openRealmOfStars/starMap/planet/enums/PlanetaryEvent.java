package org.openRealmOfStars.starMap.planet.enums;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2023 Tuomo Untinen
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

import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Planetary event when Colonization happens
*
*/
public enum PlanetaryEvent {

  /** Nothing happens */
  NONE,

  /** Building ancient lab */
  ANCIENT_LAB,
  /** Building ancient factory */
  ANCIENT_FACTORY,
  /** Building ancient temple */
  ANCIENT_TEMPLE,
  /** Building ancient palace */
  ANCIENT_PALACE,
  /** Building black monolith */
  BLACK_MONOLITH,
  /** Ancient artifact */
  ANCIENT_ARTIFACT;

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
    if (this == PlanetaryEvent.ANCIENT_PALACE) {
      return true;
    }
    if (this == PlanetaryEvent.BLACK_MONOLITH) {
      return true;
    }
    if (this == PlanetaryEvent.ANCIENT_ARTIFACT) {
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
    if (this == ANCIENT_PALACE) {
      return BuildingFactory.createByName("Ancient palace");
    }
    if (this == BLACK_MONOLITH) {
      return BuildingFactory.createByName("Black monolith");
    }
    return null;
  }

  /**
   * Get planetary event as index;
   * @return Index
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Get planetary event name
   * @return String never null
   */
  public String getName() {
    switch (this) {
      case NONE: return "None";
      case ANCIENT_LAB: return "Ancient lab";
      case ANCIENT_FACTORY: return "Ancient factory";
      case ANCIENT_TEMPLE: return "Ancient temple";
      case ANCIENT_PALACE: return "Ancient palace";
      case BLACK_MONOLITH: return "Black monolith";
      case ANCIENT_ARTIFACT: return "Ancient artefact";
      default:
        throw new IllegalArgumentException("Unknown planetary event!!");
    }
  }

  /**
   * Get planetary event explanation as String
   * @return String never null
   */
  public String getExplanation() {
    switch (this) {
      case NONE: return "";
      case ANCIENT_LAB: return "Ancient lab building";
      case ANCIENT_FACTORY: return "Ancient factory building";
      case ANCIENT_TEMPLE: return "Ancient temple building";
      case ANCIENT_PALACE: return "Ancient palace building";
      case BLACK_MONOLITH: return "Black monolith building";
      case ANCIENT_ARTIFACT: return "Ancient artifact found on planet";
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
    if (index >= 0 && index < values().length) {
      return values()[index];
    }
    throw new IllegalArgumentException("Unknown planetary event!!");
  }

  /**
   * Get Random event for planet type
   * FIXME: Correct event weights
   * @param type Planet type
   * @param chance Chance for random event.
   * @return PlanetaryEvent
   */
  public static PlanetaryEvent getRandomEvent(final PlanetTypes type,
      final int chance) {
    int value = DiceGenerator.getRandom(99);
    if (value < chance) {
      switch (type) {
        case SWAMPWORLD1:
        case SWAMPWORLD2:
        case SWAMPWORLD3:
          value = DiceGenerator.getRandom(99);
          if (value < 50) {
            return PlanetaryEvent.ANCIENT_ARTIFACT;
          } else if (value < 61) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 72) {
            return PlanetaryEvent.ANCIENT_FACTORY;
          } else if (value < 83) {
            return PlanetaryEvent.ANCIENT_TEMPLE;
          } else if (value < 94) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;
        case BARRENWORLD1:
          value = DiceGenerator.getRandom(99);
          if (value < 40) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 50) {
            return PlanetaryEvent.ANCIENT_ARTIFACT;
          } else if (value < 65) {
            return PlanetaryEvent.ANCIENT_FACTORY;
          } else if (value < 80) {
            return PlanetaryEvent.ANCIENT_TEMPLE;
          } else if (value < 95) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;
        case ICEWORLD1:
        case ICEWORLD2:
        case ICEWORLD3:
        case ICEWORLD4:
          value = DiceGenerator.getRandom(99);
          if (value < 30) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 40) {
            return PlanetaryEvent.ANCIENT_ARTIFACT;
          } else if (value < 60) {
            return PlanetaryEvent.ANCIENT_FACTORY;
          } else if (value < 80) {
            return PlanetaryEvent.ANCIENT_TEMPLE;
          } else if (value < 95) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;
        case DESERTWORLD1:
        case DESERTWORLD2:
        case DESERTWORLD3:
          value = DiceGenerator.getRandom(99);
          if (value < 30) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 40) {
            return PlanetaryEvent.ANCIENT_ARTIFACT;
          } else if (value < 80) {
            return PlanetaryEvent.ANCIENT_TEMPLE;
          } else if (value < 95) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;
        case VOLCANICWORLD1:
        case VOLCANICWORLD2:
        case VOLCANICWORLD3:
        case VOLCANICWORLD4:
        case VOLCANICWORLD5:
        case VOLCANICWORLD6:
          value = DiceGenerator.getRandom(99);
          if (value < 30) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 37) {
            return PlanetaryEvent.ANCIENT_ARTIFACT;
          } else if (value < 50) {
            return PlanetaryEvent.ANCIENT_FACTORY;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;
        case WATERWORLD1:
        case WATERWORLD2:
        case WATERWORLD3:
        case WATERWORLD4:
        case WATERWORLD5:
        case WATERWORLD6:
        case WATERWORLD7:
        case WATERWORLD8:
        case WATERWORLD9:
          value = DiceGenerator.getRandom(99);
          if (value < 44) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 57) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 80) {
            return PlanetaryEvent.ANCIENT_FACTORY;
          } else if (value < 87) {
            return PlanetaryEvent.ANCIENT_ARTIFACT;
          } else if (value < 95) {
            return PlanetaryEvent.ANCIENT_TEMPLE;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;

        default:
          break;
      }
    }
    return PlanetaryEvent.NONE;
  }

}
