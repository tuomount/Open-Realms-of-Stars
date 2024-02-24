package org.openRealmOfStars.player;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

/*
 * Open Realm of Stars game project
 * Copyright (C) 2023-2024 Tuomo Untinen
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

/**
 * Starting Scenarion enumeration for Open Realm of Stars
 */
public enum StartingScenario {

  /**
   * Random starting scenario.
   */
  RANDOM,
  /**
   * So called standard starting scenario.
   * Temperate humid planet with size 12.
   */
  TEMPERATE_HUMID_SIZE12,
  /**
   * Starting system is sol.
   */
  EARTH,
  /**
   * Arid planet size 12, two extra technology for starters.
   */
  TEMPERATE_ARID_SIZE12,
  /**
   * Marine planet size 9.
   */
  TEMPERATE_MARINE_SIZE9,
  /**
   * Marine planet size 14.
   */
  TEMPERATE_MARINE_SIZE14,
  /**
   * Cold humid planet size 12, extra technology for starters.
   */
  COLD_HUMID_SIZE12,
  /**
   * Tropical humid planet size 12
   */
  TROPICAL_HUMID_SIZE12,
  /**
   * Hot arid planet size 12, extra technology for starters.
   */
  HOT_ARID_SIZE12,
  /**
   * Temperate Arid planet with 3 farms, 5 population but no starting ships.
   */
  FARMING_PLANET,
  /**
   * Start without home planet with 4 scouts and 2 colony ships.
   */
  DESTROYED_HOME_PLANET,
  /**
   * Start without home planet, since realm arrived from another galaxy.
   * Start with 4 scouts and 2 colony ships.
   */
  FROM_ANOTHER_GALAXY;


  /**
   * Get starting scenarion as an integer value.
   * @return int
   */
  public int getIndex() {
    switch (this) {
      case RANDOM: return 0;
      case TEMPERATE_HUMID_SIZE12: return 1;
      case EARTH: return 2;
      case TEMPERATE_ARID_SIZE12: return 3;
      case TEMPERATE_MARINE_SIZE9: return 4;
      case TEMPERATE_MARINE_SIZE14: return 5;
      case COLD_HUMID_SIZE12: return 6;
      case TROPICAL_HUMID_SIZE12: return 7;
      case HOT_ARID_SIZE12: return 8;
      case FARMING_PLANET: return 9;
      case DESTROYED_HOME_PLANET: return 10;
      case FROM_ANOTHER_GALAXY: return 11;
      default: throw new IllegalArgumentException("Unknown starting scenario.");
    }
  }

  /**
   * Get starting scenario based on index.
   * @param index Index
   * @return StartingScenario
   */
  public static StartingScenario getByIndex(final int index) {
    switch (index) {
      case 0: return RANDOM;
      case 1: return TEMPERATE_HUMID_SIZE12;
      case 2: return EARTH;
      case 3: return TEMPERATE_ARID_SIZE12;
      case 4: return TEMPERATE_MARINE_SIZE9;
      case 5: return TEMPERATE_MARINE_SIZE14;
      case 6: return COLD_HUMID_SIZE12;
      case 7: return TROPICAL_HUMID_SIZE12;
      case 8: return HOT_ARID_SIZE12;
      case 9: return FARMING_PLANET;
      case 10: return DESTROYED_HOME_PLANET;
      case 11: return FROM_ANOTHER_GALAXY;
      default: throw new IllegalArgumentException("Unknown starting scenario.");
    }
  }

  @Override
  public String toString() {
    switch (this) {
    default:
    case RANDOM: return "Random starting scenario";
    case TEMPERATE_HUMID_SIZE12: return "Temperate and humid planet";
    case EARTH: return "Start from Earth";
    case TEMPERATE_ARID_SIZE12: return "Temperate and arid planet,"
        + " with extra tech";
    case TEMPERATE_MARINE_SIZE9: return "Temperate, marine and low gravity"
        + " planet";
    case TEMPERATE_MARINE_SIZE14: return "Temperate, marine and high gravity"
        + " planet";
    case COLD_HUMID_SIZE12: return "Cold, humid planet with extra tech";
    case TROPICAL_HUMID_SIZE12: return "Tropical, humid planet";
    case HOT_ARID_SIZE12: return "Hot, arid planet with extra tech";
    case FARMING_PLANET: return "Temperate arid farming planet, but no ships";
    case DESTROYED_HOME_PLANET: return "No home, start from deep space";
    case FROM_ANOTHER_GALAXY: return "Arrived from another galaxy";
    }
  }

  /**
   * Get Starting Scenario description.
   * @return Description as string.
   */
  public String getDescription() {
    switch (this) {
    default:
    case RANDOM: return "Random starting scenario.";
    case TEMPERATE_HUMID_SIZE12: return "Temperate and humid planet with"
        + " ground size 12. World type is either Swamp or water planet.";
    case EARTH: return "Realm starts at Earth and solar system is Sol."
        + " Single start allowed.";
    case TEMPERATE_ARID_SIZE12: return "Temperate and arid planet with"
        + " ground size 12. World type is desert. Start with extra 2"
        + " technology.";
    case TEMPERATE_MARINE_SIZE9: return "Temperate, marine and low gravity"
        + " planet with ground size 9. World type is water world.";
    case TEMPERATE_MARINE_SIZE14: return "Temperate, marine and high gravity"
    + " planet with ground size 14. World type is water world.";
    case COLD_HUMID_SIZE12: return "Cold and humid planet with"
    + " ground size 12. World type is ice world. Start with extra technology.";
    case TROPICAL_HUMID_SIZE12: return "Tropical and humid planet with"
    + " ground size 12. World type is either Swamp or water planet.";
    case HOT_ARID_SIZE12: return "Hot and arid planet with"
    + " ground size 12. World type is desert world. Start with extra"
    + " 2 technology.";
    case FARMING_PLANET: return "Temperate arid planet with 3 farms,"
    + " ground size 12. World type is desert world. No staring ships. Extra"
    + " technology.";
    case DESTROYED_HOME_PLANET: return "Home planet has been destroyed. Start"
        + " from deep space with 4 scouts and 2 colony ships. Colony fleet in"
        + " deep space provides extra research point."
        + " Single start allowed.";
    case FROM_ANOTHER_GALAXY: return "No home planet. Start from deep space"
        + " with 4 scouts and 2 colony ships. Colony fleet in deep space"
        + " provides extra research point. Start with extra technology.";

    }
  }

  /**
   * Pick Random starting scenario.
   * @return Statring Scenario
   */
  public static StartingScenario pickRandom() {
    ArrayList<StartingScenario> list = new ArrayList<>();
    for (StartingScenario scenario : StartingScenario.values()) {
      if (scenario != RANDOM) {
        list.add(scenario);
      }
    }
    return DiceGenerator.pickRandom(list);
  }
}
