package org.openRealmOfStars.player.SpaceRace;

import org.openRealmOfStars.utilities.DiceGenerator;

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
* Factory for creating space races and space race utilities
*
*/
public final class SpaceRaceUtility {

  /**
   * Hide the constructor
   */
  private SpaceRaceUtility() {
    // Nothing to do
  }

  /**
   * Get SpaceRace with indexed number
   * @param index Space Race index
   * @return SpaceRace, if index is out of bounds human is given
   */
  public static SpaceRace getRaceByIndex(final int index) {
    if (index > -1 && index < SpaceRace.values().length) {
      return SpaceRace.values()[index];
    }
    return SpaceRace.HUMAN;
  }

  /**
   * Get Space race by single name
   * @param name Race name in single format
   * @return SpaceRace, if does not match then return null
   */
  public static SpaceRace getRaceByName(final String name) {
    if (name == null) {
      return null;
    }
    if (name.equals(SpaceRace.HUMAN.getNameSingle())) {
      return SpaceRace.HUMAN;
    }
    if (name.equals(SpaceRace.MECHIONS.getNameSingle())) {
      return SpaceRace.MECHIONS;
    }
    if (name.equals(SpaceRace.SPORKS.getNameSingle())) {
      return SpaceRace.SPORKS;
    }
    if (name.equals(SpaceRace.GREYANS.getNameSingle())) {
      return SpaceRace.GREYANS;
    }
    if (name.equals(SpaceRace.CENTAURS.getNameSingle())) {
      return SpaceRace.CENTAURS;
    }
    return null;
  }

  /**
   * Get Random race
   * @return SpaceRace
   */
  public static SpaceRace getRandomRace() {
    int index = DiceGenerator.getRandom(SpaceRace.values().length - 1);
    return getRaceByIndex(index);
  }

  /**
   * Get random empire name for player
   * @param race SpaceRace for which random name is going to be generated
   * @return player name
   */
  public static String getRandomName(final SpaceRace race) {
    StringBuilder sb = new StringBuilder();
    if (DiceGenerator.getRandom(1) == 0) {
      if (race == SpaceRace.HUMAN) {
        if (DiceGenerator.getRandom(1) == 0) {
          sb.append("Terran");
        } else {
          sb.append(race.getNameSingle());
        }
      } else {
        sb.append(race.getNameSingle());
      }
      sb.append(" ");
      switch (DiceGenerator.getRandom(7)) {
      case 0:
        sb.append("Empire");
        break;
      case 1:
        sb.append("Federation");
        break;
      case 2:
        sb.append("Republic");
        break;
      case 3:
        sb.append("Alliance");
        break;
      case 4: {
        if (race == SpaceRace.SPORKS || race == SpaceRace.MECHIONS) {
          sb.append("Horde");
        } else {
          sb.append("Kingdom");
        }
        break;
      }
      case 5: {
        if (race == SpaceRace.SPORKS) {
          sb.append("Clan");
        } else {
          sb.append("Hegemony");
        }
        break;
      }
      case 6: {
        if (race == SpaceRace.MECHIONS) {
          sb.append("AI");
        } else {
          sb.append("Democracy");
        }
        break;
      }
      case 7:
        sb.append("Hiearchy");
        break;
      default:
        sb.append("Empire");
      }

    } else {
      switch (DiceGenerator.getRandom(7)) {
      case 0:
        sb.append("Empire of ");
        break;
      case 1:
        sb.append("Federation of ");
        break;
      case 2:
        sb.append("Republic of ");
        break;
      case 3:
        sb.append("Alliance of ");
        break;
      case 4: {
        if (race == SpaceRace.SPORKS || race == SpaceRace.MECHIONS) {
          sb.append("Horde of ");
        } else {
          sb.append("Kingdom of ");
        }
        break;
      }
      case 5: {
        if (race == SpaceRace.SPORKS) {
          sb.append("Clan of ");
        } else {
          sb.append("Hegemony of ");
        }
        break;
      }
      case 6: {
        if (race == SpaceRace.MECHIONS) {
          sb.append("AI of ");
        } else {
          sb.append("Democracy of ");
        }
        break;
      }
      case 7:
        sb.append("Hiearchy of ");
        break;
      default:
        sb.append("Empire of ");
      }
      sb.append(race.getName());
    }
    return sb.toString();
  }


}
