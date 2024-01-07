package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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

import java.util.ArrayList;

import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
* Factory for creating space races and space race utilities
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
   * Get all SpaceRace with certain trait.
   * @param traitId Trait Id
   * @return Array of Space Race with certain trait.
   */
  public static SpaceRace[] getRacesByTraits(final String traitId) {
    ArrayList<SpaceRace> list = new ArrayList<>();
    for (SpaceRace race : SpaceRace.values()) {
      if (race.hasTrait(traitId)) {
        list.add(race);
      }
    }
    return list.toArray(new SpaceRace[list.size()]);
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
    var raceName = DiceGenerator.pickRandom(RACE_SELECTION);
    return getRaceByName(raceName);
  }

  /**
   * Get random empire name for realm
   * TODO: Remove from here, realm name is only loosely coupled to race
   * @param race SpaceRace for which random name is going to be generated
   * @param government Government type for realm
   * @return realm name
   */
  public static String getRealmName(final SpaceRace race,
      final GovernmentType government) {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(6)) {
      default:
      case 0:
      case 1:
      case 2: {
        sb.append(race.getNameSingle());
        sb.append(" ");
        sb.append(government.getName());
        break;
      }
      case 3:
      case 4: {
        sb.append(government.getName());
        sb.append(" of ");
        sb.append(race.getNameSingle());
        break;
      }
      case 5: {
        sb.append(government.getName());
        sb.append(" of united ");
        sb.append(race.getName());
        break;
      }
      case 6: {
        if (DiceGenerator.getBoolean()) {
          sb.append("Sovereign ");
        } else {
          sb.append("Supreme ");
        }
        sb.append(race.getNameSingle());
        sb.append(" ");
        sb.append(government.getName());
        break;
      }
    }

    return sb.toString();
  }

}
