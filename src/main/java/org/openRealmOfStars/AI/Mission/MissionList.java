package org.openRealmOfStars.AI.Mission;

import org.openRealmOfStars.utilities.repository.MissionRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

  /**
   * Constructor for MissionList.
   */
  public MissionList() {
    missions = new ArrayList<>();
  }

  /**
   * Read MissionList from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public MissionList(final DataInputStream dis) throws IOException {
    missions = new ArrayList<>();
    int count = dis.readInt();
    for (int i = 0; i < count; i++) {
      Mission mission = new MissionRepository().restoreMission(dis);
      missions.add(mission);
    }
  }

  /**
   * Save Mission list to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveMissionList(final DataOutputStream dos) throws IOException {
    dos.writeInt(missions.size());
    for (int i = 0; i < missions.size(); i++) {
      new MissionRepository().saveMission(dos, missions.get(i));
    }
  }

  /**
   * Get mission for fleet name
   * @param fleetName The fleet name
   * @param type MissionType
   * @return Mission or null if not found
   */
  public Mission getMissionForFleet(final String fleetName,
      final MissionType type) {
    for (Mission mission : missions) {
      if (mission.getFleetName().equals(fleetName)
          && mission.getType() == type) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Get mission for fleet name
   * @param fleetName The fleet name
   * @return Mission or null if not found
   */
  public Mission getMissionForFleet(final String fleetName) {
    for (Mission mission : missions) {
      if (mission.getFleetName() != null
          && mission.getFleetName().equals(fleetName)) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Find a colonize mission for certain planet
   * @param x Planet X coordinate
   * @param y Planet Y coordinate
   * @return Mission or null if not found
   */
  public Mission getColonizeMission(final int x, final int y) {
    for (Mission mission : missions) {
      if (mission.getX() == x && mission.getY() == y
          && mission.getType() == MissionType.COLONIZE) {
        return mission;
      }
    }
    return null;

  }

  /**
   * Find a deploy starbase mission for coordinate
   * @param x Planet X coordinate
   * @param y Planet Y coordinate
   * @return Mission or null if not found
   */
  public Mission getDeployStarbaseMission(final int x, final int y) {
    for (Mission mission : missions) {
      if (mission.getX() == x && mission.getY() == y
          && mission.getType() == MissionType.DEPLOY_STARBASE) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Find a Attack mission for certain planet
   * @param name Planet where to attack
   * @return Mission or null if not found
   */
  public Mission getAttackMission(final String name) {
    for (Mission mission : missions) {
      if (mission.getTargetPlanet() != null
          && mission.getTargetPlanet().equals(name)
          && mission.getType() == MissionType.ATTACK) {
        return mission;
      }
    }
    return null;

  }

  /**
   * Find a Destroy starbase mission for starbase.
   * Destory Starbase mission are recognized by coordinates
   * @param name Destroy Starbase coordinates
   * @return Mission or null if not found
   */
  public Mission getDestroyStarbaseMission(final String name) {
    for (Mission mission : missions) {
      if (mission.getTargetPlanet() != null
          && mission.getTargetPlanet().equals(name)
          && mission.getType() == MissionType.DESTROY_STARBASE) {
        return mission;
      }
    }
    return null;

  }

  /**
   * Find a Gather missions for certain planet and only one attack
   * @param planetName Planet where to attack
   * @return true if only one attack mission found
   */
  public boolean isAttackMissionLast(final String planetName) {
    int count = 0;
    for (Mission mission : missions) {
      if (mission.getType() == MissionType.GATHER
          && mission.getTargetPlanet().equals(planetName)) {
        count++;
      }
    }
    if (count == 0) {
      return true;
    }
    return false;
  }

  /**
   * Get mission where type is certain and phase is certain
   * @param type Mission type
   * @param phase Mission phase
   * @return Mission or null if not found
   */
  public Mission getMission(final MissionType type, final MissionPhase phase) {
    for (Mission mission : missions) {
      if (mission.getType() == type
          && mission.getPhase() == phase) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Get Gather mission for certain ship type and which is under planning
   * @param shipType Ship type
   * @return Gather mission or null
   */
  public Mission getGatherMission(final String shipType) {
    for (Mission mission : missions) {
      if (mission.getType() == MissionType.GATHER
          && mission.getPhase() == MissionPhase.PLANNING
          && mission.getShipType().equals(shipType)) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Get mission for planet
   * @param planetName The planet name
   * @param type MissionType
   * @return Mission or null if not found
   */
  public Mission getMissionForPlanet(final String planetName,
      final MissionType type) {
    for (Mission mission : missions) {
      if (mission.getPlanetBuilding() != null
          && mission.getPlanetBuilding().equals(planetName)
          && mission.getType() == type) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Get mission for planet
   * @param planetName The planet name
   * @param phase MissionPhase
   * @return Mission or null if not found
   */
  public Mission getMissionForPlanet(final String planetName,
      final MissionPhase phase) {
    for (Mission mission : missions) {
      if (mission.getPlanetBuilding() != null
          && mission.getPlanetBuilding().equals(planetName)
          && mission.getPhase() == phase) {
        return mission;
      }
    }
    return null;
  }

  /**
   * Remove mission from the mission list
   * @param mission Mission to remove
   */
  public void remove(final Mission mission) {
    missions.remove(mission);
  }

  /**
   * Add new mission into mission list;
   * @param mission The mission to add to the list
   */
  public void add(final Mission mission) {
    missions.add(mission);
  }

  /**
   * Get number of missions
   * @return Number of missions
   */
  public int getSize() {
    return missions.size();
  }

  /**
   * Get mission by index
   * @param index Index to get mission
   * @return Mission or null if bad index
   */
  public Mission getMissionByIndex(final int index) {
    if (index >= 0 && index < missions.size()) {
      return missions.get(index);
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (Mission mission : missions) {
      sb.append("Mission ");
      sb.append(i);
      sb.append(":\n");
      sb.append(mission.toString());
      sb.append("\n\n");
      i++;
    }
    return sb.toString();
  }
}
