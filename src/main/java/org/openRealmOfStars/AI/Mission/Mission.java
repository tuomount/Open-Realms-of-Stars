package org.openRealmOfStars.AI.Mission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.utilities.IOUtilities;

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
 * Mission for AI
 *
 */
public final class Mission {

  /**
   * Mission type
   */
  private MissionType type;

  /**
   * Mission phase
   */
  private MissionPhase phase;

  /**
   * Target X coordinate
   */
  private int targetXCoordinate;
  /**
   * Target Y coordinate
   */
  private int targetYCoordinate;

  /**
   * Fleet name which is on the mission
   */
  private String fleetName;

  /**
   * Planet name which is building the fleet
   */
  private String planetBuilding;

  /**
   * Planet name which is going to be conquered;
   */
  private String targetPlanet;

  /**
   * Solar system name where to go to explore
   */
  private String sunName;

  /**
   * How many turns mission has been on some fleet.
   * Usually this calculates execution time. It does not tell how many turns
   * ago mission was created. Mission time can be used to decide when
   * to upgrade defending fleet, when explorer should move to next solar system.
   * Each mission will have their own way of calculating this one.
   */
  private int missionTime;

  /**
   * Create new mission for AI
   * @param missionType MissionType
   * @param missionPhase Mission Phase
   * @param tx Target X
   * @param ty Target Y
   */
  public Mission(final MissionType missionType, final MissionPhase missionPhase,
      final int tx, final int ty) {
    this.type = missionType;
    this.phase = missionPhase;
    setMissionTime(0);
    setTarget(tx, ty);
    targetPlanet = null;
  }

  /**
   * Read mission from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public Mission(final DataInputStream dis) throws IOException {
    type = MissionType.getType(dis.readInt());
    phase = MissionPhase.getType(dis.readInt());
    targetXCoordinate = dis.readInt();
    targetYCoordinate = dis.readInt();
    missionTime = dis.readInt();
    String str = IOUtilities.readString(dis);
    if (str.isEmpty()) {
      fleetName = null;
    } else {
      fleetName = str;
    }
    str = IOUtilities.readString(dis);
    if (str.isEmpty()) {
      planetBuilding = null;
    } else {
      planetBuilding = str;
    }
    str = IOUtilities.readString(dis);
    if (str.isEmpty()) {
      sunName = null;
    } else {
      sunName = str;
    }
    str = IOUtilities.readString(dis);
    if (str.isEmpty()) {
      targetPlanet = null;
    } else {
      targetPlanet = str;
    }
  }

  /**
   * Write mission data to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveMission(final DataOutputStream dos) throws IOException {
    dos.writeInt(type.getIndex());
    dos.writeInt(phase.getIndex());
    dos.writeInt(targetXCoordinate);
    dos.writeInt(targetYCoordinate);
    dos.writeInt(missionTime);
    IOUtilities.writeString(dos, fleetName);
    IOUtilities.writeString(dos, planetBuilding);
    IOUtilities.writeString(dos, sunName);
    IOUtilities.writeString(dos, targetPlanet);
  }

  /**
   * Set the target's coordinate.
   *
   * @param x Target X coordinate
   * @param y Target Y coordinate
   */
  public void setTarget(final int x, final int y) {
    this.targetXCoordinate = x;
    this.targetYCoordinate = y;
  }

  /**
   * Return the mission type.
   *
   * @return the mission type
   */
  public MissionType getType() {
    return type;
  }

  /**
   * Set the mission type.
   *
   * @param missionType the mission type to set
   */
  public void setType(final MissionType missionType) {
    this.type = missionType;
  }

  /**
   * Get the mission phase
   * @return MissionPhase get mission phase
   */
  public MissionPhase getPhase() {
    return phase;
  }

  /**
   * Set the mission phase
   * @param missionPhase Set phase for mission
   */
  public void setPhase(final MissionPhase missionPhase) {
    this.phase = missionPhase;
  }

  /**
   * Get fleet involved in this mission
   * @return Fleet name as a string
   */
  public String getFleetName() {
    return fleetName;
  }

  /**
   * Set fleet name which is involved and executing the mission.
   * @param name Fleet name
   */
  public void setFleetName(final String name) {
    this.fleetName = name;
  }

  /**
   * Get planet name which is building a fleet for this missing.
   * @return  Planet name which is building the fleet for mission.
   */
  public String getPlanetBuilding() {
    return planetBuilding;
  }

  /**
   * Set planet name which building fleet for the mission.
   * @param building Planet name which is building the fleet
   */
  public void setPlanetBuilding(final String building) {
    this.planetBuilding = building;
  }

  /**
   * Get mission's target X coordinate
   * @return X coordinate
   */
  public int getX() {
    return targetXCoordinate;
  }

  /**
   * Get mission's target Y coordinate
   * @return Y coordinate
   */
  public int getY() {
    return targetYCoordinate;
  }

  @Override
  public String toString() {
    return getType().toString() + " - " + getPhase().toString() + "\nPlanet:"
        + getPlanetBuilding() + "\nFleet:" + getFleetName() + "\nSolar:"
        + getSunName();
  }

  /**
   * Get sun name involved for the mission
   * @return Sun name as a String
   */
  public String getSunName() {
    return sunName;
  }

  /**
   * Set sun name for the mission.
   * @param name Sun Name
   */
  public void setSunName(final String name) {
    this.sunName = name;
  }

  /**
   * How many turns mission has been on
   * @return amount of turns mission has been active
   */
  public int getMissionTime() {
    return missionTime;
  }

  /**
   * Set mission time in turns
   * @param time in turns
   */
  public void setMissionTime(final int time) {
    this.missionTime = time;
  }

  /**
   * Get mission's target planet name.
   * @return Planet name as a string
   */
  public String getTargetPlanet() {
    return targetPlanet;
  }

  /**
   * Set Mission's target planet name.
   * @param target Planet name as a string.
   */
  public void setTargetPlanet(final String target) {
    this.targetPlanet = target;
  }

}
