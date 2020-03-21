package org.openRealmOfStars.AI.Mission;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.MissionRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2019 Tuomo Untinen
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
   * Clear all missions from the list.
   */
  public void clearMissions() {
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
   * Change Fleet name in mission
   * @param oldName Old name to change
   * @param newName New name where to change
   */
  public void changeFleetName(final String oldName, final String newName) {
    for (Mission mission : missions) {
      if (mission.getFleetName() != null
          && mission.getFleetName().equals(oldName)) {
        mission.setFleetName(newName);
      }
    }
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
   * Find a treade mission for certain planet
   * @param name Planet where to trade
   * @return Mission or null if not found
   */
  public Mission getTradeMission(final String name) {
    for (Mission mission : missions) {
      if (mission.getTargetPlanet() != null
          && mission.getTargetPlanet().equals(name)
          && mission.getType() == MissionType.TRADE_FLEET) {
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
   * Find a Gather missions for certain planet.
   * @param planetName Planet where to attack
   * @return true if no gather mission found
   */
  public boolean noMoreGatherMissions(final String planetName) {
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
   * Calculate number of certain mission types.
   * @param type Mission types to count
   * @return number of missions
   */
  public int getNumberOfMissionTypes(final MissionType type) {
    int count = 0;
    for (Mission mission : missions) {
      if (mission.getType() == type) {
        count++;
      }
    }
    return count;
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
   * Get mission for planet building ship
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
   * Delete all missions for certain fleet name
   * @param fleetName Fleet name to delete
   */
  public void deleteMissionForFleet(final String fleetName) {
    ArrayList<Mission> deletableMissions = new ArrayList<>();
    for (Mission mission : missions) {
      if (fleetName.equals(mission.getFleetName())) {
        deletableMissions.add(mission);
      }
    }
    for (Mission mission : deletableMissions) {
      missions.remove(mission);
    }
  }

  /**
   * Remove attacks mission against player
   * @param info PlayerInfo whom not to attack
   * @param map Starmap
   */
  public void removeAttackAgainstPlayer(final PlayerInfo info,
      final StarMap map) {
    Mission[] listMissions = missions.toArray(new Mission[missions.size()]);
    for (Mission mission : listMissions) {
      if (mission.getType() == MissionType.ATTACK
          || mission.getType() == MissionType.GATHER) {
        Planet planet = map.getPlanetByName(mission.getTargetPlanet());
        if (planet != null && planet.getPlanetPlayerInfo() == info) {
          remove(mission);
          continue;
        }
      }
      if (mission.getType() == MissionType.DESTROY_STARBASE) {
        Fleet fleet = map.getFleetByCoordinate(mission.getX(), mission.getY());
        if (fleet != null && map.getPlayerInfoByFleet(fleet) == info
            && fleet.isStarBaseDeployed()) {
          for (Mission gatherMission : listMissions) {
            if (gatherMission.getType() == MissionType.GATHER
                && gatherMission.getShipType() == Mission.ASSAULT_SB_TYPE
                && gatherMission.getTargetPlanet().equals(
                    mission.getTargetPlanet())) {
              remove(gatherMission);
              continue;
            }
          }
          remove(mission);
        }
      }
    }
  }

  /**
   * Add new mission into mission list;
   * @param mission The mission to add to the list
   */
  public void add(final Mission mission) {
    missions.add(mission);
  }

  /**
   * Add new mission into mission list with highest priority.
   * @param mission The mission to add to the list
   */
  public void addHighestPriority(final Mission mission) {
    missions.add(0, mission);
  }

  /**
   * Add new mission into mission list with priority.
   * @param mission The mission to add to the list
   * @param indexMission mission in list where new is added immediately
   *         afterwards.
   */
  public void addPriorityAfter(final Mission mission,
      final Mission indexMission) {
    int priority = missions.size();
    for (int i = 0; i < missions.size(); i++) {
      if (missions.get(i) == indexMission) {
        priority = i + 1;
        if (priority > missions.size()) {
          priority = missions.size();
        }
        break;
      }
    }
    missions.add(priority, mission);
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
