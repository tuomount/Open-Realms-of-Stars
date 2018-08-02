package org.openRealmOfStars.player.SpaceRace;

import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016-2018  Tuomo Untinen
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
   * List of selectable races
   */
  public static final String[] RACE_SELECTION = {"Human", "Mechion", "Spork",
      "Greyan", "Centaur", "Mothoid", "Teuthidae", "Scaurian", "Homarian" };


  /**
   * Get SpaceRace with indexed number
   * @param index Space Race index
   * @return SpaceRace, if index is out of bounds human is given
   */
  public static SpaceRace getRaceByIndex(final int index) {
    if (index > -1 && index < RACE_SELECTION.length) {
      return getRaceByName(RACE_SELECTION[index]);
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
    if (name.equals(SpaceRace.MOTHOIDS.getNameSingle())) {
      return SpaceRace.MOTHOIDS;
    }
    if (name.equals(SpaceRace.TEUTHIDAES.getNameSingle())) {
      return SpaceRace.TEUTHIDAES;
    }
    if (name.equals(SpaceRace.SCAURIANS.getNameSingle())) {
      return SpaceRace.SCAURIANS;
    }
    if (name.equals(SpaceRace.HOMARIANS.getNameSingle())) {
      return SpaceRace.HOMARIANS;
    }
    return null;
  }

  /**
   * Get Random race
   * @return SpaceRace
   */
  public static SpaceRace getRandomRace() {
    int index = DiceGenerator.getRandom(RACE_SELECTION.length - 1);
    return getRaceByIndex(index);
  }

  /**
   * Get random empire name for realm
   * @param race SpaceRace for which random name is going to be generated
   * @param government Government type for realm
   * @return realm name
   */
  public static String getRandomName(final SpaceRace race,
      final GovernmentType government) {
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
      sb.append(government.getName());
    } else {
      sb.append(government.getName());
      sb.append(" of ");
      if (race == SpaceRace.HUMAN) {
        if (DiceGenerator.getRandom(1) == 0) {
          sb.append("Terran");
        } else {
          sb.append(race.getNameSingle());
        }
      } else {
        sb.append(race.getNameSingle());
      }
    }
    return sb.toString();
  }

}
