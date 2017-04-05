package org.openRealmOfStars.utilities.repository;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.utilities.IOUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
  }

  /**
   * Get mission type by index
   * @param index The mission type index
   * @return Mission Type, never null
   */
  private static MissionType getTypeIndex(final int index) {
    switch (index) {
    case 0:
      return MissionType.EXPLORE;
    case 1:
      return MissionType.COLONIZE;
    case 2:
      return MissionType.DEFEND;
    case 3:
      return MissionType.ATTACK;
    default:
      return MissionType.EXPLORE;
    }
  }

  /**
   * Get mission phase by index
   * @param index The mission phase index
   * @return Mission phase, never null
   */
  private static MissionPhase getPhaseIndex(final int index) {
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
    return mission;
  }

}
