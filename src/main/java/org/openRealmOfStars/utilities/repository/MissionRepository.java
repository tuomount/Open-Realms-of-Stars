package org.openRealmOfStars.utilities.repository;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * Mission repository class
 *
 */
public class MissionRepository {

  /**
   * Write mission data to DataOutputStream
   * @param dos DataOutputStream
   * @param mission Mission to save
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveMission(final DataOutputStream dos, final Mission mission)
      throws IOException {
    dos.writeInt(mission.getType().getIndex());
    dos.writeInt(mission.getPhase().getIndex());
    dos.writeInt(mission.getX());
    dos.writeInt(mission.getY());
    dos.writeInt(mission.getMissionTime());
    IOUtilities.writeString(dos, mission.getFleetName());
    IOUtilities.writeString(dos, mission.getPlanetBuilding());
    IOUtilities.writeString(dos, mission.getSunName());
    IOUtilities.writeString(dos, mission.getTargetPlanet());
    // Mission specific data
    if (mission.getType() == MissionType.GATHER) {
      IOUtilities.writeString(dos, mission.getPlanetGathering());
      IOUtilities.writeString(dos, mission.getShipType());
    }
    if (mission.getType() == MissionType.ESPIONAGE_MISSION) {
      IOUtilities.writeString(dos, mission.getEspionageType().getName());
    }
    if (mission.getType() == MissionType.DIPLOMATIC_DELEGACY) {
      IOUtilities.writeString(dos, mission.getTargetRealmName());
    }
  }

  /**
   * Get mission type by index
   * @param index The mission type index
   * @return Mission Type, never null
   */
  protected static MissionType getTypeIndex(final int index) {
    switch (index) {
    case 0:
      return MissionType.EXPLORE;
    case 1:
      return MissionType.COLONIZE;
    case 2:
      return MissionType.DEFEND;
    case 3:
      return MissionType.ATTACK;
    case 4:
      return MissionType.MOVE;
    case 5:
      return MissionType.GATHER;
    case 6:
      return MissionType.DEPLOY_STARBASE;
    case 7:
      return MissionType.DESTROY_STARBASE;
    case 8:
      return MissionType.TRADE_FLEET;
    case 9:
      return MissionType.PRIVATEER;
    case 10:
      return MissionType.COLONY_EXPLORE;
    case 11:
      return MissionType.SPY_MISSION;
    case 12:
      return MissionType.ESPIONAGE_MISSION;
    case 13:
      return MissionType.DIPLOMATIC_DELEGACY;
    case 14:
      return MissionType.INTERCEPT;
    case 15:
      return MissionType.DESTROY_FLEET;
    case 16:
      return MissionType.ROAM;
    default:
      ErrorLogger.log("Warning: Unknown mission type: " + index
          + ". Defaulting to explore.");
      return MissionType.EXPLORE;
    }
  }

  /**
   * Get mission phase by index
   * @param index The mission phase index
   * @return Mission phase, never null
   */
  protected static MissionPhase getPhaseIndex(final int index) {
    switch (index) {
    case 0:
      return MissionPhase.BUILDING;
    case 1:
      return MissionPhase.TREKKING;
    case 2:
      return MissionPhase.EXECUTING;
    case 3:
      return MissionPhase.PLANNING;
    case 4:
      return MissionPhase.LOADING;
    default:
      ErrorLogger.log("Warning: Unknown mission phase: " + index
          + ". Defaulting to building.");
      return MissionPhase.BUILDING;
    }
  }

  /**
   * Read mission from DataInputStream
   * @param dis DataInputStream
   * @return mission from Data Input Stream
   * @throws IOException if there is any problem with DataInputStream
   */
  public Mission restoreMission(final DataInputStream dis) throws IOException {
    Mission mission = new Mission(getTypeIndex(dis.readInt()),
        getPhaseIndex(dis.readInt()), new Coordinate(dis.readInt(),
            dis.readInt()));
    mission.setMissionTime(dis.readInt());
    String str = IOUtilities.readString(dis);
    if (!str.isEmpty()) {
      mission.setFleetName(str);
    }
    str = IOUtilities.readString(dis);
    if (!str.isEmpty()) {
      mission.setPlanetBuilding(str);
    }
    str = IOUtilities.readString(dis);
    if (!str.isEmpty()) {
      mission.setSunName(str);
    }
    str = IOUtilities.readString(dis);
    if (!str.isEmpty()) {
      mission.setTargetPlanet(str);
    }
    // Mission specific data
    if (mission.getType() == MissionType.GATHER) {
      str = IOUtilities.readString(dis);
      if (!str.isEmpty()) {
        mission.setPlanetGathering(str);
      }
      str = IOUtilities.readString(dis);
      if (!str.isEmpty()) {
        mission.setShipType(str);
      }
    }
    if (mission.getType() == MissionType.ESPIONAGE_MISSION) {
      str = IOUtilities.readString(dis);
      if (!str.isEmpty()) {
        mission.setEspionageType(EspionageMission.getMission(str));
      }
    }
    if (mission.getType() == MissionType.DIPLOMATIC_DELEGACY) {
      str = IOUtilities.readString(dis);
      mission.setTargetRealmName(str);
    }

    return mission;
  }

}
