package org.openRealmOfStars.player.ship;

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
 * Ship component for handling shields, armors, weapons, engines etc.
 * 
 */
public class ShipComponent {

  /**
   * Index for saving information
   */
  private int index;
  
  /**
   * Component type
   */
  private ShipComponentType type;

  /**
   * Component cost in production
   */
  private int cost;

  /**
   * Component cost in metal
   */
  private int metalCost;

  /**
   * Component name must match one on techs
   */
  private String name;
  
  /**
   * Component energy requirement
   */
  private int energyRequirement;
  /**
   * Component energy resource
   */
  private int energyResource;
  
  /**
   * Faster than light speed aka route speed
   */
  private int ftlSpeed;

  /**
   * Regular speed
   */
  private int speed;

  /**
   * Tactic speed in combat
   */
  private int tacticSpeed;
  
  /**
   * Scanner range if component is scanner
   */
  private int scannerRange;
  
  /**
   * Cloak detection in scanner
   */
  private int cloakDetection;

  /**
   * Cloaking device value
   */
  private int cloaking;

  /**
   * defense value, could be either shield or armor
   */
  private int defenseValue;
  
  /**
   * weapon damage
   */
  private int damage;

  /**
   * Constructor for ship component
   * @param index Index for saving component
   * @param name Component name 
   * @param cost Production cost for single component
   * @param metalCost Metal cost for single component
   * @param type Component type
   */
  public ShipComponent(int index, String name, int cost, int metalCost, 
      ShipComponentType type) {
    this.index = index;
    this.name = name;
    this.cost = cost;
    this.metalCost = metalCost;
    this.type = type;
    this.energyRequirement = 0;
    this.energyResource = 0;
    this.ftlSpeed = 0;
    this.speed = 0;
    this.tacticSpeed = 0;
    this.scannerRange = 0;
    this.cloakDetection = 0;
    this.cloaking = 0;
    this.defenseValue = 0;
    this.damage = 0;
  }

  public int getIndex() {
    return index;
  }

  public ShipComponentType getType() {
    return type;
  }

  public int getCost() {
    return cost;
  }

  public int getMetalCost() {
    return metalCost;
  }

  public String getName() {
    return name;
  }

  public int getEnergyRequirement() {
    return energyRequirement;
  }

  public void setEnergyRequirement(int energyRequirement) {
    this.energyRequirement = energyRequirement;
  }

  public int getEnergyResource() {
    return energyResource;
  }

  public void setEnergyResource(int energyResource) {
    this.energyResource = energyResource;
  }

  public int getFtlSpeed() {
    return ftlSpeed;
  }

  public void setFtlSpeed(int ftlSpeed) {
    this.ftlSpeed = ftlSpeed;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getTacticSpeed() {
    return tacticSpeed;
  }

  public void setTacticSpeed(int tacticSpeed) {
    this.tacticSpeed = tacticSpeed;
  }

  public int getScannerRange() {
    return scannerRange;
  }

  public void setScannerRange(int scannerRange) {
    this.scannerRange = scannerRange;
  }

  public int getCloakDetection() {
    return cloakDetection;
  }

  public void setCloakDetection(int cloakDetection) {
    this.cloakDetection = cloakDetection;
  }

  public int getCloaking() {
    return cloaking;
  }

  public void setCloaking(int cloaking) {
    this.cloaking = cloaking;
  }

  public int getDefenseValue() {
    return defenseValue;
  }

  public void setDefenseValue(int defenseValue) {
    this.defenseValue = defenseValue;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  
}
