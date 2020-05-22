package org.openRealmOfStars.AI.Mission;

import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.starMap.Coordinate;

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
public class Mission {

  /**
   * Mission type
   */
  private MissionType type;

  /**
   * Mission phase
   */
  private MissionPhase phase;

  /**
   * Target coordinate
   */
  private Coordinate targetCoordinate;

  /**
   * Fleet name which is on the mission
   */
  private String fleetName;

  /**
   * Planet name which is building the fleet
   */
  private String planetBuilding;

  /**
   * Planet where to gather attacking shops
   */
  private String planetGathering;

  /**
   * Planet name which is going to be conquered;
   */
  private String targetPlanet;

  /**
   * Mission textual parameter. In Explore mission this should
   * solar system name. Gathering mission this will tell what kind of
   * ship mission is searching.
   */
  private String parameter;

  /**
   * How many turns mission has been on some fleet.
   * Usually this calculates execution time. It does not tell how many turns
   * ago mission was created. Mission time can be used to decide when
   * to upgrade defending fleet, when explorer should move to next solar system.
   * Each mission will have their own way of calculating this one.
   */
  private int missionTime;

  /**
   * Ship type trooper
   */
  public static final String TROOPER_TYPE = "Trooper";

  /**
   * Ship type bomber
   */
  public static final String BOMBER_TYPE = "Bomber";

  /**
   * Ship type assault
   */
  public static final String ASSAULT_TYPE = "Assault";

  /**
   * Ship type assault for Starbase destroying
   */
  public static final String ASSAULT_SB_TYPE = "Assault_SB";

  /**
   * Create new mission for AI
   * @param missionType MissionType
   * @param missionPhase Mission Phase
   * @param coordinate Target coordinate
   */
  public Mission(final MissionType missionType, final MissionPhase missionPhase,
                 final Coordinate coordinate) {
    this.type = missionType;
    this.phase = missionPhase;
    setMissionTime(0);
    setTarget(coordinate);
    targetPlanet = null;

  }

  /**
   * Set the target's coordinate.
   *
   * @param coordinate Target coordinate
   */
  public void setTarget(final Coordinate coordinate) {
    if (coordinate != null) {
      targetCoordinate = new Coordinate(coordinate);
    } else {
      targetCoordinate = null;
    }
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
    if (targetCoordinate == null) {
      return -1;
    }
    return targetCoordinate.getX();
  }

  /**
   * Get mission's target Y coordinate
   * @return Y coordinate
   */
  public int getY() {
    if (targetCoordinate == null) {
      return -1;
    }
    return targetCoordinate.getY();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getType().toString());
    sb.append(" - ");
    sb.append(getPhase().toString());
    sb.append("\nCoordinate:");
    sb.append(getX());
    sb.append(",");
    sb.append(getY());
    sb.append("\nBuilding Planet:");
    sb.append(getPlanetBuilding());
    sb.append("\nFleet:");
    sb.append(getFleetName());
    sb.append("\nTime:");
    sb.append(getMissionTime());
    if (type == MissionType.EXPLORE
        || type == MissionType.COLONY_EXPLORE
        || type == MissionType.PRIVATEER) {
      sb.append("\nSolar:");
      sb.append(getSunName());
    }
    if (type == MissionType.ATTACK) {
      sb.append("\nTarget planet:");
      sb.append(getTargetPlanet());
    }
    if (type == MissionType.TRADE_FLEET
        || type == MissionType.SPY_MISSION) {
      sb.append("\nTarget planet:");
      sb.append(getTargetPlanet());
    }
    if (type == MissionType.GATHER) {
      sb.append("\nGather planet:");
      sb.append(getPlanetGathering());
      sb.append("\nShiptype:");
      sb.append(getShipType());
      sb.append("\nTarget planet:");
      sb.append(getTargetPlanet());
    }
    return sb.toString();
  }

  /**
   * Get sun name involved for the explore mission including
   * colony explore and privateering.
   * @return Sun name as a String
   */
  public String getSunName() {
    if (type == MissionType.EXPLORE
        || type == MissionType.COLONY_EXPLORE
        || type == MissionType.PRIVATEER) {
      return parameter;
    }
    return "";
  }

  /**
   * Set sun name for the explore mission including
   * colony explore and privateering.
   * @param name Sun Name
   */
  public void setSunName(final String name) {
    if (type == MissionType.EXPLORE
        || type == MissionType.COLONY_EXPLORE
        || type == MissionType.PRIVATEER) {
      this.parameter = name;
    }
  }

  /**
   * Get Espionage Type.
   * @return Espionage mission
   */
  public EspionageMission getEspionageType() {
    if (type == MissionType.ESPIONAGE_MISSION) {
      return EspionageMission.getMission(parameter);
    }
    return null;
  }

  /**
   * Set espionage type for espionage mission.
   * @param espionageMission Espionage type
   */
  public void setEspionageType(final EspionageMission espionageMission) {
    if (type == MissionType.ESPIONAGE_MISSION) {
      parameter = espionageMission.getName();
    }
  }
  /**
   * Set ship type for the gather mission.
   * @param shipType Sun Name
   */
  public void setShipType(final String shipType) {
    if (type == MissionType.GATHER) {
      this.parameter = shipType;
    }
  }

  /**
   * Get ship type involved for the explore mission
   * @return Sun ShipType
   */
  public String getShipType() {
    if (type == MissionType.GATHER) {
      return parameter;
    }
    return "";
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

  /**
   * Planet where attacking ships gather
   * @return the planetGathering
   */
  public String getPlanetGathering() {
    if (type == MissionType.GATHER) {
      return planetGathering;
    }
    return null;
  }

  /**
   * Set gathering planet
   * @param planetGathering the planetGathering to set
   */
  public void setPlanetGathering(final String planetGathering) {
    this.planetGathering = planetGathering;
  }

}
