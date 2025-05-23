package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import org.openRealmOfStars.player.government.Government;
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
   * Get all SpaceRace with certain trait.
   * @param traitId Trait Id
   * @return Array of Space Race with certain trait.
   */
  public static SpaceRace[] getRacesByTrait(final String traitId) {
    return getRacesByTraits(traitId);
  }

  /**
   * Get all SpaceRace with certain traits.
   * If any of the traits is found then space race is being selected.
   * @param traitIds Array of Trait Id
   * @return Array of Space Race with certain trait.
   */
  public static SpaceRace[] getRacesByTraits(final String... traitIds) {
    ArrayList<SpaceRace> list = new ArrayList<>();
    for (SpaceRace race : SpaceRaceFactory.getValues()) {
      for (String trait : traitIds) {
        if (race.hasTrait(trait)) {
          list.add(race);
          break;
        }
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

    for (var race : SpaceRaceFactory.getValues()) {
      if (name.equals(race.getNameSingle())) {
        return race;
      }
    }
    return null;
  }

  /**
   * Get random empire name for realm
   * @param race SpaceRace for which random name is going to be generated
   * @param government Government for realm
   * @return realm name
   */
  public static String getRealmName(final SpaceRace race,
      final Government government) {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(6)) {
      default:
      case 0: {
        sb.append(race.getNameSingle());
        sb.append(" ");
        if (DiceGenerator.getBoolean()) {
          sb.append("Space ");
        } else {
          sb.append("Astral ");
        }
        sb.append(government.getName());
        break;
      }
      case 1: {
        if (DiceGenerator.getBoolean()) {
          sb.append("Space ");
        } else {
          sb.append("Astral ");
        }
        sb.append(government.getName());
        sb.append(" of ");
        sb.append(race.getName());
        break;
      }
      case 2: {
        sb.append(race.getNameSingle());
        sb.append(" ");
        sb.append(government.getName());
        break;
      }
      case 3: {
        if (DiceGenerator.getBoolean()) {
          sb.append("Supreme ");
        } else {
          sb.append("Sovereign ");
        }
        sb.append(government.getName());
        sb.append(" of ");
        sb.append(race.getName());
        break;
      }
      case 4: {
        sb.append(government.getName());
        sb.append(" of ");
        sb.append(race.getNameSingle());
        break;
      }
      case 5: {
        sb.append(government.getName());
        sb.append(" of United ");
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
