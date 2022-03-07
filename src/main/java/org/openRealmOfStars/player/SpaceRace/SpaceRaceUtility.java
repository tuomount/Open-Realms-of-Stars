package org.openRealmOfStars.player.SpaceRace;

import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016-2018,2020-2022  Tuomo Untinen
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
      "Greyan", "Centaur", "Mothoid", "Teuthidae", "Scaurian", "Homarian",
      "Chiraloid", "Reborgian", "Lithorian", "Alteirian", "Smaugirian",
      "Synthdroid" };


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
    if (name.equals(SpaceRace.CHIRALOIDS.getNameSingle())) {
      return SpaceRace.CHIRALOIDS;
    }
    if (name.equals(SpaceRace.REBORGIANS.getNameSingle())) {
      return SpaceRace.REBORGIANS;
    }
    if (name.equals(SpaceRace.LITHORIANS.getNameSingle())) {
      return SpaceRace.LITHORIANS;
    }
    if (name.equals(SpaceRace.ALTEIRIANS.getNameSingle())) {
      return SpaceRace.ALTEIRIANS;
    }
    if (name.equals(SpaceRace.SMAUGIRIANS.getNameSingle())) {
      return SpaceRace.SMAUGIRIANS;
    }
    if (name.equals(SpaceRace.SYNTHDROIDS.getNameSingle())) {
      return SpaceRace.SYNTHDROIDS;
    }
    return null;
  }

  /**
   * Get Random race
   * @return SpaceRace
   */
  public static SpaceRace getRandomRace() {
    int index = DiceGenerator.getRandom(RACE_SELECTION.length - 1);
    return getRaceByName(RACE_SELECTION[index]);
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
          sb.append(buildSecondaryName(race, "Terran"));
      } else if (race == SpaceRace.MECHIONS) {
          sb.append(buildSecondaryName(race, "Steel"));
      } else if (race == SpaceRace.SPORKS) {
          sb.append(buildSecondaryName(race, "Taurus"));
      } else if (race == SpaceRace.GREYANS) {
          sb.append(buildSecondaryName(race, "Aesir"));
      } else if (race == SpaceRace.CENTAURS) {
          sb.append(buildSecondaryName(race, "Sagittarian"));
      } else if (race == SpaceRace.TEUTHIDAES) {
          sb.append(buildSecondaryName(race, "Squiddan"));
      } else if (race == SpaceRace.MOTHOIDS) {
          sb.append(buildSecondaryName(race, "Scorpio"));
      } else if (race == SpaceRace.SCAURIANS) {
          sb.append(buildSecondaryName(race, "Nemean"));
      } else if (race == SpaceRace.HOMARIANS) {
          sb.append(buildSecondaryName(race, "Cancerian"));
      } else if (race == SpaceRace.CHIRALOIDS) {
          sb.append(buildSecondaryName(race, "Capricorn"));
      } else if (race == SpaceRace.REBORGIANS) {
        sb.append(buildSecondaryName(race, "Bionian"));
      } else if (race == SpaceRace.LITHORIANS) {
        sb.append(buildSecondaryName(race, "Metavore"));
      } else if (race == SpaceRace.SMAUGIRIANS) {
        sb.append(buildSecondaryName(race, "Hareans"));
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

  /**
   * Build secondary name for Space race based on the dice number.<br>
   * If the dice number is 0, the secondary name will be defined based on
   * the input parameter.<br>
   * Otherwise, single name from the Space race will be used.
   * @param race SpaceRace for which random name is going to be generated
   * @param secondaryName the secondary name default based on type
   * @return secondary name for Space race
   */
  private static String buildSecondaryName(final SpaceRace race,
          final String secondaryName) {
      if (DiceGenerator.getRandom(1) == 0) {
          return secondaryName;
      } else {
          return race.getNameSingle();
      }
  }

}
