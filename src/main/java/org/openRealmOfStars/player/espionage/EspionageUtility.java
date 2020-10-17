package org.openRealmOfStars.player.espionage;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
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

  /**
   * Get Stealable tech based on planet value.
   * @param planet Planet where to steal.
   * @param info Realm who is stealing.
   * @return Array of stealable techs.
   */
  public static Tech[] getStealableTech(final Planet planet,
      final PlayerInfo info) {
    int value = planet.getFullLevel() / 10;
    ArrayList<Tech> stealableTech = new ArrayList<>();
    for (int type = 0; type < 6; type++) {
      Tech[] tradeTechs = planet.getPlanetPlayerInfo()
          .getTechList().getListForType(TechType
              .getTypeByIndex(type));
      Tech[] ownTechs = info.getTechList().getListForType(TechType
              .getTypeByIndex(type));
      Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
      for (Tech tech : techs) {
        if (tech.getLevel() <= value) {
          stealableTech.add(tech);
        }
      }
    }
    return stealableTech.toArray(new Tech[stealableTech.size()]);
  }
  /**
   * Get available mission types based on planet.
   * @param planet where to do espionage mission
   * @param info Realm who is doing espionage mission.
   * @return Available mission types.
   */
  public static EspionageMission[] getAvailableMissionTypes(
      final Planet planet, final PlayerInfo info) {
    ArrayList<EspionageMission> list = new ArrayList<>();
    list.add(EspionageMission.GAIN_TRUST);
    list.add(EspionageMission.SABOTAGE);
    if (planet.getPlanetPlayerInfo() != null) {
      if (planet.getPlanetPlayerInfo().getTotalCredits() > 0) {
        list.add(EspionageMission.STEAL_CREDIT);
      }
      if (getStealableTech(planet, info).length > 0) {
        list.add(EspionageMission.STEAL_TECH);
      }
      if (planet.getGovernor() != null) {
        list.add(EspionageMission.ASSASSIN_GOVERNOR);
      }
      if (planet.getBuildingList().length > 0) {
        list.add(EspionageMission.DEMOLISH_BUILDING);
        list.add(EspionageMission.TERRORIST_ATTACK);
      }
      if (!info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
        list.add(EspionageMission.FALSE_FLAG);
      }
    }
    return list.toArray(new EspionageMission[list.size()]);
  }
}
