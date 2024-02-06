package org.openRealmOfStars.player.government;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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
*
* Government Utility
*
*/

public final class GovernmentUtility {

  /**
   * Hiding the constructor
   */
  private GovernmentUtility() {
    // Hiding the constructor
  }

  /**
   * Get Random government type
   * @return GovernmentType
   */
  public static GovernmentType getRandomGovernment() {
    GovernmentType[] govs = GovernmentType.values();
    return DiceGenerator.pickRandom(govs);
  }
  /**
   * Get government type by index
   * @param index Index to fetch
   * @return GovernmentType
   */
  public static GovernmentType getGovernmentByIndex(final int index) {
    GovernmentType[] govs = GovernmentType.values();
    if (index >= 0 && index < govs.length) {
      return govs[index];
    }
    throw new IllegalArgumentException("Unknown government type index:"
      + index);
  }

  /**
   * Get Government Group
   * @param government Government which to get group id
   * @return Government group id.
   */
  public static int getGovernmentGroup(final GovernmentType government) {
    switch (government) {
      default:
      case DEMOCRACY:
      case UNION:
      case FEDERATION:
      case REPUBLIC:
        return 0;
      case GUILD:
      case ENTERPRISE:
      case SYNDICATE:
        return 1;
      case COLLECTIVE:
      case UTOPIA:
      case TECHNOCRACY:
        return 2;
      case HEGEMONY:
      case EMPIRE:
      case KINGDOM:
      case HIERARCHY:
      case HORDE:
      case CLAN:
      case REGIME:
      case FEUDALISM:
        return 3;
      case NEST:
      case AI:
      case HIVEMIND:
      case SPACE_PIRATES:
        return 4;
    }
  }
}
