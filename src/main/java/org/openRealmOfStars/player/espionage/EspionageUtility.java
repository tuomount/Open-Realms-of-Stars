package org.openRealmOfStars.player.espionage;

import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Espionage Utility
*
*/
public final class EspionageUtility {

  /**
   * Hidden constructor.
   */
  private EspionageUtility() {
    // Hiding the constructor.
  }

  /**
   * Calculate detection success for the planet. This is always between 5%-95%
   * @param planet Planet trying to detect
   * @param fleet Fleet trying to do espionage
   * @param mission Espionage mission type
   * @return Chance
   */
  public static int calculateDetectionSuccess(final Planet planet,
      final Fleet fleet, final EspionageMission mission) {
    int result = mission.getBaseDetection();
    result = result - fleet.getFleetCloackingValue();
    result = result + planet.getCloakingDetectionLvl();
    if (result > 95) {
      result = 95;
    }
    if (result < 5) {
      result = 5;
    }
    return result;
  }

  /**
   * Calculate mission success for the fleet. This is always between 5%-95%
   * @param planet Planet trying to detect
   * @param fleet Fleet trying to do espionage
   * @param mission Espionage mission type
   * @return Chance
   */
  public static int calculateSuccess(final Planet planet,
      final Fleet fleet, final EspionageMission mission) {
    int result = mission.getBaseChance();
    result = result + fleet.getEspionageBonus() * 10;
    if (planet.hasCertainBuilding("Barracks")) {
      result = result - 10;
    }
    if (planet.hasCertainBuilding("Space academy")) {
      result = result - 10;
    }
    if (planet.hasCertainBuilding("United Galaxy Tower")) {
      result = result - 10;
    }
    if (result > 95) {
      result = 95;
    }
    if (result < 5) {
      result = 5;
    }
    return result;
  }

}
