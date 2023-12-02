package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 Tuomo Untinen
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

import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Utility for story generator.
 */
public final class StoryGeneratorUtility {

  /**
   * Hide constructor.
   */
  private StoryGeneratorUtility() {
    // Hide constructor
  }

  /**
   * Get random word Inaugural.
   * @return first or inaugural.
   */
  public static String randomInaugural() {
    if (DiceGenerator.getRandom(1) == 0) {
      return "first";
    }
    return "inaugural";
  }

  /**
   * Get random explanation for space exploration.
   * @return explanation.
   */
  public static String startSpaceExploration() {
    switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        return " embarked on its momentous journey of space exploration, "
            + "departing from ";
      }
      case 1: {
        return " embarked on an ambitious journey of space exploration, "
            + "setting forth from ";
      }
      case 2: {
        return " embarked on its grand odyssey of space exploration,"
            + " launching from ";
      }
      case 3: {
        return " embarked on its grand voyage of space exploration from ";
      }
    }
  }

  /**
   * Get random explanation for space exploration.
   * @return explanation.
   */
  public static String intoStars() {
    switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        return " into the uncharted realms of the stars";
      }
      case 1: {
        return " toward the boundless reaches of the stars";
      }
      case 2: {
        return " into the boundless realm of the stars";
      }
      case 3: {
        return " into the vast expanse of the cosmos";
      }
    }
  }

}
