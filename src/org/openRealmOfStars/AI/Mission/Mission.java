package org.openRealmOfStars.AI.Mission;

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
   * Target X coordinate
   */
  private int x;
  /**
   * Target Y coordinate
   */
  private int y;
    
  /**
   * Fleet name which is on the mission
   */
  private String fleetName;
  
  /**
   * Planet name which is building the ship
   */
  private String planetBuilding;

  /**
   * Create new mission for AI
   * @param type MissionType
   * @param phase Mission Phase
   * @param x Target X
   * @param y Target Y
   */
  public Mission(MissionType type, MissionPhase phase, int x, int y) {
    this.type = type;
    this.phase = phase;
    setTarget(x,y);
  }
  
  public void setTarget(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public MissionType getType() {
    return type;
  }

  public void setType(MissionType type) {
    this.type = type;
  }

  public MissionPhase getPhase() {
    return phase;
  }

  public void setPhase(MissionPhase phase) {
    this.phase = phase;
  }

  public String getFleetName() {
    return fleetName;
  }

  public void setFleetName(String fleetName) {
    this.fleetName = fleetName;
  }

  public String getPlanetBuilding() {
    return planetBuilding;
  }

  public void setPlanetBuilding(String planetBuilding) {
    this.planetBuilding = planetBuilding;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getType().toString());
    sb.append(" - ");
    sb.append(getPhase().toString());
    sb.append("\nPlanet:");
    sb.append(getPlanetBuilding());
    sb.append("\nFleet:");
    sb.append(getFleetName());
    return sb.toString();
  }
  
  
  
}
