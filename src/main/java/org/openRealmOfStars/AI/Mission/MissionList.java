package org.openRealmOfStars.AI.Mission;

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
      Mission mission = new Mission(dis);
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
      missions.get(i).saveMission(dos);
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
   * Find a Attack mission for certain planet and only one attack
   * @param x coordinate where is rendevouz x coordinate
   * @param y coordinate where is rendevouz y coordinate
   * @return true if only one attack mission found
   */
  public Boolean isAttackMissionLast(final int x, final int y) {
    int count = 0;
    for (Mission mission : missions) {
      if (mission.getType() == MissionType.ATTACK && mission.getX() == x
          && mission.getY() == y) {
        count++;
      }
    }
    if (count == 1) {
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
}
