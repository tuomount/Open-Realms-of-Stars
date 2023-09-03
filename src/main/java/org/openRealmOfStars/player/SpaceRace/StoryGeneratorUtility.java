package org.openRealmOfStars.player.SpaceRace;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
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
 *
 *
 * Utility for story generator.
 *
 */
public class StoryGeneratorUtility {

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
}
