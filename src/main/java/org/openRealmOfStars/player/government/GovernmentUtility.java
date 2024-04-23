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

import org.openRealmOfStars.player.government.trait.GovTrait;

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
   * Get Government comparison
   * @param government1 Government 1
   * @param government2 Government 2
   * @return Value how close governments are.
   */
  public static int getGovernmentComparison(final Government government1,
      final Government government2) {
    int result = -2;
    if (government1 == government2) {
      return 100;
    }
    for (GovTrait trait : government1.getTraits()) {
      for (GovTrait trait2 : government2.getTraits()) {
        if (trait.getId().equals(trait2.getId())) {
          result++;
        }
      }
    }
    if (government1.getRulerSelection() == government2.getRulerSelection()) {
      result++;
    }
    if (government2.getRulerTitleMale().equals(
        government1.getRulerTitleMale())) {
      result++;
    }
    if (government2.getRulerTitleFemale().equals(
        government1.getRulerTitleFemale())) {
      result++;
    }
    return result;
  }
}
