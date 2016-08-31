package org.openRealmOfStars.AI.Mission;

import java.util.ArrayList;

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
 * Mission  list for AI
 * 
 */

public class MissionList {

  /**
   * AI missions only for AI player
   */
  private ArrayList<Mission> missions;

  public MissionList() {
    missions = new ArrayList<>();
  }
  
  /**
   * Get mission for fleet name
   * @param fleetName
   * @param MissionType
   * @return Mission or null if not found
   */
  public Mission getMissionForFleet(String fleetName, MissionType type) {
    for (Mission mission : missions) {
      if (mission.getFleetName().equals(fleetName) &&
          mission.getType() == type) {
        return mission;
      }
    }
    return null;
  }

  
  /**
   * Get mission for planet
   * @param planetName
   * @param MissionType
   * @return Mission or null if not found
   */
  public Mission getMissionForPlanet(String planetName, MissionType type) {
    for (Mission mission : missions) {
      if (mission.getPlanetBuilding() != null && 
          mission.getPlanetBuilding().equals(planetName) &&
          mission.getType() == type) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Get mission for planet
   * @param planetName
   * @param MissionPhase
   * @return Mission or null if not found
   */
  public Mission getMissionForPlanet(String planetName, MissionPhase phase) {
    for (Mission mission : missions) {
      if (mission.getPlanetBuilding() != null &&
          mission.getPlanetBuilding().equals(planetName) &&
          mission.getPhase() == phase) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Adde new mission into mission list;
   * @param mission
   */
  public void add(Mission mission) {
    missions.add(mission);
  }
}
