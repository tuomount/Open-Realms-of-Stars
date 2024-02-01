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
   * Temperate humind planet with size 12.
   */
  TEMPERATE_HUMID_SIZE12,
  /**
   * Starting system is sol.
   */
  EARTH;

  /**
   * Get starting scenarion as an integer value.
   * @return int
   */
  public int getIndex() {
    switch (this) {
      case RANDOM: return 0;
      case TEMPERATE_HUMID_SIZE12: return 1;
      case EARTH: return 2;
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
    case EARTH: return "Realm starts at Earth and solar system is Sol.";
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
