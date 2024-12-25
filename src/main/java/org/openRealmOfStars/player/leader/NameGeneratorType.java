package org.openRealmOfStars.player.leader;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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


/** Leader Name Generator type enumeration. */
public enum NameGeneratorType {

  /** Namegenerator that creates names for deep ancient monsters */
  DEEP_ANCIENT_MONSTER,
  /** Namegenerator that creates names for evil creatures */
  EVIL_CREATURE,
  /** Namegenerator that creates names for space orcs. */
  SPACE_ORC,
  /** Namegenerator that creates names for insect like creatures. */
  INSECT,
  /** Namegenerator that creates names for kind of ancient roman names. */
  ANCIENT_ROMAN,
  /** Namegenerator that creates names for where gender is
   *  visible in surname and names are quite long. */
  LONG_NAMES,
  /** Namegenerator that creates names for kind of deep creatures */
  DEEP_CREATURE,
  /** Namegenerator that creates names for kind of ancient nordic names */
  ANCIENT_NORDIC,
  /** Namegenerator that creates names for female robots */
  FEMALE_ROBOT,
  /** Namegenerator that creates names for robots */
  ROBOT,
  /** Namegenerator that creates names for scifi human names */
  SCIFI_HUMAN,
  /** Namegenerator that creates names for cyborg names */
  CYBORG,
  /** Namegenerator that creates names for stone/crystal people */
  STONE_PEOPLE,
  /** Namegenerator that creates names for light or gaseous creatures */
  GASEOUS_CREATURE,
  /** Namegenerator that creates names for pirate name */
  PIRATE,
  /** Namegenerator that create names for big planet like creatures */
  BIG_PLANT,
  /** Namegenerator that create names for dwarven like creatures */
  DWARF,
  /** Namegenerator that creates names for mix of all generators */
  ALL;

  /**
   * Get Name generator name as a string.
   * @return String
   */
  public String getName() {
    switch (this) {
    case DEEP_ANCIENT_MONSTER: return "deep_ancient_monster";
    case EVIL_CREATURE: return "evil_creature";
    case SPACE_ORC: return "space_orc";
    case INSECT: return "insect";
    case ANCIENT_ROMAN: return "ancient_roman";
    case LONG_NAMES: return "long_names";
    case DEEP_CREATURE: return "deep_creature";
    case ANCIENT_NORDIC: return "ancient_nordic";
    case FEMALE_ROBOT: return "female_robot";
    case ROBOT: return "robot";
    case SCIFI_HUMAN: return "scifi_human";
    case CYBORG: return "cyborg";
    case STONE_PEOPLE: return "stone_people";
    case GASEOUS_CREATURE: return "gaseous_creature";
    case PIRATE: return "pirate";
    case BIG_PLANT: return "big_plant";
    case DWARF: return "dwarf";
    default:
    case ALL: return "all";
    }
  }
  /**
   * Get NameGeneratorType by String.
   * @param value String
   * @return NameGeneratorType
   */
  public static NameGeneratorType getByString(final String value) {
    for (NameGeneratorType type : NameGeneratorType.values()) {
      if (type.getName().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unexpected name generator type, "
        + value + ".");
  }
}
