package org.openRealmOfStars.player.tech;

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
 * Enum for tech types
 *
 */

public enum TechType {
  /**
   *  Combat tech including weapons, defense turret, bombs etc
   */
  Combat,
  /**
   * Defense tech including armor, shields, shield generators
   */
  Defense,
  /**
   * Hull tech including new ship hulls
   */
  Hulls,
  /**
   * Improvement tech including planetary improvements
   */
  Improvements,
  /**
   * Propulsion tech including engines and powersources
   */
  Propulsion,
  /**
   * Electronics tech including scanners, jammer, cloaking devices etc.
   */
  Electrics;

  /**
   * Get Tech type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case Combat:
      return 0;
    case Defense:
      return 1;
    case Hulls:
      return 2;
    case Improvements:
      return 3;
    case Propulsion:
      return 4;
    case Electrics:
      return 5;
    default:
      throw new IllegalArgumentException("Unexpected Tech type!");
    }
  }

  /**
   * Return Tech Type by index
   * @param index This must be between 0-5
   * @return Tech Type
   */
  public static TechType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return TechType.Combat;
    case 1:
      return TechType.Defense;
    case 2:
      return TechType.Hulls;
    case 3:
      return TechType.Improvements;
    case 4:
      return TechType.Propulsion;
    case 5:
      return TechType.Electrics;
    default:
      throw new IllegalArgumentException("Unexpected Tech type!");
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case Combat:
      return "Combat";
    case Defense:
      return "Defense";
    case Hulls:
      return "Hulls";
    case Improvements:
      return "Improvements";
    case Propulsion:
      return "Propulsion";
    case Electrics:
      return "Electronics";
    default:
      return "Error - Unknown";
    }
  }

  /**
   * Get Technology general description
   * @return String
   */
  public String getDescription() {
    switch (this) {
    case Combat:
      return "Combat Technology\n\n"
          + "Technology contains space weapons, orbital bombs and "
          + "invasion modules.\nHigher this technology is better weapons "
          + "space ships will have and better bombs bombers will have. "
          + "Beam weapons and photon torpedos are good against armor plated "
          + "ships. HE missile and rail guns are good against shielded "
          + "ships. ECM missile are very powerful against shield but they "
          + "do not cause hull damage. ";
    case Defense:
      return "Defense Technology\n\n"
          + "Technology contains means to protect space ships and planets. "
          + "There are two kinds of space ship defense systems: "
          + "Armor platings and Shields. Shields are good protecting "
          + "against beam weapons and photon torpedos. Armor platings "
          + "are good protecting against rail guns and HE missiles. "
          + "Shields are generated automatically one per turn. This can be "
          + "upgraded with shield generator which also belong to Defense "
          + "technology. For planetary defenses there are Planetary defense "
          + "turrets. These turrets shoot invading bombers and ships "
          + "containing invasion modules. ";
    case Hulls:
      return "Hull Technology\n\n"
          + "Technology allows build different kind of space ships and "
          + "starbases.There are four different hull sizes: small, medium, "
          + "large and huge. Smaller ships have better initiative in combat "
          + "and more difficult to hit, but they cannot carry that much "
          + "ship components as larger ones can. Improved version of hulls "
          + "have more hull points per each slot they contain. ";
    case Improvements:
      return "Improvements Technology\n\n"
          + "Technology allows building different kind of planetary "
          + "improvements, for example factories, laboriatories, mines. "
          + "These buildings can be generally built only planet which are "
          + "colonized and allows current space race to habit on that "
          + "planet. ";
    case Propulsion:
      return "Propulsion Technology\n\n"
          + "Technology is used to build space ship engines and power "
          + "sources. Better engines make space ship travel faster, expolore "
          + "faster get better movement in space combat. Power sources then "
          + "give energy for space ships. Energy is critical to space ships "
          + "since most of the ship components require energy to function. ";
    case Electrics:
      return "Electronics Technology\n\n"
          + "Technology allows to have special components "
          + "or buildings which require high electronics "
          + "know how. These components are scanners, cloaking devices, "
          + "jammer and targeting computers. Also planetary scanner are "
          + "acquired from Electronics technology. ";
    default:
      return "Error - Unknown";
    }
  }

}
