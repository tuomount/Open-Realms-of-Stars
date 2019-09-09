package org.openRealmOfStars.starMap.planet;

import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

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
  ANCIENT_TEMPLE,
  /**
   * Building ancient palace
   */
  ANCIENT_PALACE,
  /**
   * Building black monolith
   */
  BLACK_MONOLITH,
  /**
   * Molten lava
   */
  MOLTEN_LAVA,
  /**
   * Arid climate
   */
  ARID,
  /**
   * Desert climate
   */
  DESERT;




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
    if (this == PlanetaryEvent.ARID) {
      return -1;
    }
    if (this == PlanetaryEvent.DESERT) {
      return -2;
    }
    return 0;
  }

  /**
   * Extra happiness due the planetary event
   * @return Extra happiness
   */
  public int getExtraHappiness() {
    if (this == PlanetaryEvent.PARADISE) {
      return 1;
    }
    if (this == PlanetaryEvent.MOLTEN_LAVA) {
      return -1;
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
    if (this == PlanetaryEvent.ANCIENT_PALACE) {
      return true;
    }
    if (this == PlanetaryEvent.BLACK_MONOLITH) {
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
   * Extra metal production due the planetary event
   * @return Extra metal production
   */
  public int getExtraMetalProduction() {
    if (this == PlanetaryEvent.METAL_RICH_SURFACE) {
      return 1;
    }
    if (this == PlanetaryEvent.MOLTEN_LAVA) {
      return 1;
    }
    return 0;
  }

  /**
   * Extra production due the planetary event
   * @return Extra production
   */
  public int getExtraProduction() {
    if (this == PlanetaryEvent.MOLTEN_LAVA) {
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
      case ANCIENT_PALACE: return 7;
      case BLACK_MONOLITH: return 8;
      case MOLTEN_LAVA: return 9;
      case ARID: return 10;
      case DESERT: return 11;
      default:
        throw new IllegalArgumentException("Unknown planetary event!!");
    }
  }

  /**
   * Get planetary event name
   * @return String never null
   */
  public String getName() {
    switch (this) {
      case NONE: return "None";
      case LUSH_VEGETATION: return "Lush vegetation";
      case PARADISE: return "Paradise";
      case METAL_RICH_SURFACE: return "Metal rich surface";
      case ANCIENT_LAB: return "Ancient lab";
      case ANCIENT_FACTORY: return "Ancient factory";
      case ANCIENT_TEMPLE: return "Ancient temple";
      case ANCIENT_PALACE: return "Ancient palace";
      case BLACK_MONOLITH: return "Black monolith";
      case MOLTEN_LAVA: return "Molten lava";
      case ARID: return "Arid climate";
      case DESERT: return "Desert climate";
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
      case LUSH_VEGETATION: return "Lush vegetation +1 Food";
      case PARADISE: return "Paradise +2 Food, +1 happiness";
      case METAL_RICH_SURFACE: return "Metal rich surface +1 Metal";
      case ANCIENT_LAB: return "Ancient lab building";
      case ANCIENT_FACTORY: return "Ancient factory building";
      case ANCIENT_TEMPLE: return "Ancient temple building";
      case ANCIENT_PALACE: return "Ancient palace building";
      case BLACK_MONOLITH: return "Black monolith building";
      case MOLTEN_LAVA: return "Molten lava +1 metal, +1 production,"
          + " -1 happiness";
      case ARID: return "Arid climate -1 Food";
      case DESERT: return "Desert climate -2 Food";
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
      case 7: return ANCIENT_PALACE;
      case 8: return BLACK_MONOLITH;
      case 9: return MOLTEN_LAVA;
      case 10: return ARID;
      case 11: return DESERT;
      default:
        throw new IllegalArgumentException("Unknown planetary event!!");
    }
  }

  /**
   * Get Random event for planet type
   * @param type Planet type
   * @param chance Chance for random event.
   * @return PlanetaryEvent
   */
  public static PlanetaryEvent getRandomEvent(final PlanetTypes type,
      final int chance) {
    int value = DiceGenerator.getRandom(99);
    if (value < chance) {
      switch (type) {
        case CARBONWORLD1:
        case CARBONWORLD2:
        case CARBONWORLD3:
          value = DiceGenerator.getRandom(99);
          if (value < 25) {
            return LUSH_VEGETATION;
          } else if (value < 50) {
            return PlanetaryEvent.METAL_RICH_SURFACE;
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
        case SILICONWORLD1:
          value = DiceGenerator.getRandom(99);
          if (value < 25) {
            return PlanetaryEvent.METAL_RICH_SURFACE;
          } else if (value < 50) {
            return PlanetaryEvent.ANCIENT_LAB;
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
          if (value < 20) {
            return PlanetaryEvent.METAL_RICH_SURFACE;
          } else if (value < 40) {
            return PlanetaryEvent.ANCIENT_LAB;
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
          if (value < 20) {
            return PlanetaryEvent.METAL_RICH_SURFACE;
          } else if (value < 40) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 60) {
            return PlanetaryEvent.ARID;
          } else if (value < 80) {
            return PlanetaryEvent.ANCIENT_TEMPLE;
          } else if (value < 95) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 100) {
            return PlanetaryEvent.BLACK_MONOLITH;
          }
          break;
        case IRONWORLD1:
        case IRONWORLD2:
        case IRONWORLD3:
        case IRONWORLD4:
        case IRONWORLD5:
        case IRONWORLD6:
          value = DiceGenerator.getRandom(99);
          if (value < 20) {
            return PlanetaryEvent.METAL_RICH_SURFACE;
          } else if (value < 35) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 50) {
            return PlanetaryEvent.ANCIENT_FACTORY;
          } else if (value < 60) {
            return PlanetaryEvent.ARID;
          } else if (value < 80) {
            return PlanetaryEvent.MOLTEN_LAVA;
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
          if (value < 16) {
            return LUSH_VEGETATION;
          } else if (value < 33) {
            return PlanetaryEvent.PARADISE;
          } else if (value < 49) {
            return PlanetaryEvent.ANCIENT_PALACE;
          } else if (value < 66) {
            return PlanetaryEvent.ANCIENT_LAB;
          } else if (value < 82) {
            return PlanetaryEvent.ANCIENT_FACTORY;
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
