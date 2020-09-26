package org.openRealmOfStars.player.ship;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2019  Tuomo Untinen
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
   * Weapon range
   */
  private int weaponRange;

  /**
   * Initiative boost for combat
   */
  private int initiativeBoost;

  /**
   * Culture bonus from starbase component
   */
  private int cultureBonus;

  /**
   * Credit bonus from starbase component
   */
  private int creditBonus;

  /**
   * Research bonus from starbase component
   */
  private int researchBonus;

  /**
   * Espionage bonus from component
   */
  private int espionageBonus;

  /**
   * Fleet capacity bonus from component
   */
  private int fleetCapacityBonus;

  /**
   * Constructor for ship component
   * @param index Index for saving component
   * @param name Component name
   * @param cost Production cost for single component
   * @param metalCost Metal cost for single component
   * @param type Component type
   */
  public ShipComponent(final int index, final String name, final int cost,
      final int metalCost, final ShipComponentType type) {
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
    this.initiativeBoost = 0;
    this.setWeaponRange(0);
    this.researchBonus = 0;
    this.creditBonus = 0;
    this.cultureBonus = 0;
    this.setEspionageBonus(0);
  }

  /**
   * Is component weapon
   * @return True if weapon
   */
  public boolean isWeapon() {
    if (type == ShipComponentType.WEAPON_BEAM
        || type == ShipComponentType.WEAPON_ECM_TORPEDO
        || type == ShipComponentType.WEAPON_HE_MISSILE
        || type == ShipComponentType.WEAPON_PHOTON_TORPEDO
        || type == ShipComponentType.WEAPON_RAILGUN) {
      return true;
    }
    return false;
  }

  /**
   * Is privateer module
   * @return True if privateer module
   */
  public boolean isPrivateer() {
    if (type == ShipComponentType.PRIVATEERING_MODULE) {
      return true;
    }
    return false;
  }

  /**
   * Is tractor beam component
   * @return True if tractor beam
   */
  public boolean isTractor() {
    if (type == ShipComponentType.TRACTOR_BEAM) {
      return true;
    }
    return false;
  }

  /**
   * Get ship component Index
   * @return Component index
   */
  public int getIndex() {
    return index;
  }

  /**
   * Get component type
   * @return Ship component type
   */
  public ShipComponentType getType() {
    return type;
  }

  /**
   * Get component production cost
   * @return production cost
   */
  public int getCost() {
    return cost;
  }

  /**
   * Get component metal cost
   * @return metal cost
   */
  public int getMetalCost() {
    return metalCost;
  }

  /**
   * Get component name
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Get component energy requirement
   * @return Energy requirement
   */
  public int getEnergyRequirement() {
    return energyRequirement;
  }

  /**
   * Set component energy requirement
   * @param energyRequirement Energy required by component
   */
  public void setEnergyRequirement(final int energyRequirement) {
    this.energyRequirement = energyRequirement;
  }

  /**
   * Get component energy resource. This is positive if component
   * is producing energy.
   * @return Energy production or zero
   */
  public int getEnergyResource() {
    return energyResource;
  }

  /**
   * Set component energy source
   * @param energyResource Amount energy component is producing
   */
  public void setEnergyResource(final int energyResource) {
    this.energyResource = energyResource;
  }

  /**
   * Get engine's FTL speed
   * @return FTL speed in tiles
   */
  public int getFtlSpeed() {
    return ftlSpeed;
  }

  /**
   * Set engine's FTL speed
   * @param ftlSpeed FTL speed in tiles
   */
  public void setFtlSpeed(final int ftlSpeed) {
    this.ftlSpeed = ftlSpeed;
  }

  /**
   * Get Speed for Star map moving without route or FTL
   * @return Speed
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Set engine speed for normal moving
   * @param speed normal movement speed
   */
  public void setSpeed(final int speed) {
    this.speed = speed;
  }

  /**
   * Get Speed in combat
   * @return Combat speed
   */
  public int getTacticSpeed() {
    return tacticSpeed;
  }

  /**
   * Set speed in combat
   * @param tacticSpeed Combat speed
   */
  public void setTacticSpeed(final int tacticSpeed) {
    this.tacticSpeed = tacticSpeed;
  }

  /**
   * Get component scanner range
   * @return range in tiles.
   */
  public int getScannerRange() {
    return scannerRange;
  }

  /**
   * Set component scanner range.
   * @param scannerRange Range in tiles.
   */
  public void setScannerRange(final int scannerRange) {
    this.scannerRange = scannerRange;
  }

  /**
   * Get component cloak detection
   * @return Cloak detection level
   */
  public int getCloakDetection() {
    return cloakDetection;
  }

  /**
   * Set component cloak detection level.
   * @param cloakDetection Cloak detection level
   */
  public void setCloakDetection(final int cloakDetection) {
    this.cloakDetection = cloakDetection;
  }

  /**
   * Get component's cloaking value
   * @return Cloaking value
   */
  public int getCloaking() {
    return cloaking;
  }

  /**
   * Set cloaking value for component
   * @param cloaking Cloaking value for component
   */
  public void setCloaking(final int cloaking) {
    this.cloaking = cloaking;
  }

  /**
   * Get component defense value. This is used for both armor and shields.
   * @return Defense value
   */
  public int getDefenseValue() {
    return defenseValue;
  }

  /**
   * Get Bay size for fighter bays.
   * Small ship takes one, medium ship takes two size of bay.
   * @return Bay size
   */
  public int getBaySize() {
    return defenseValue;
  }
  /**
   * Set Bay size for fighter bays.
   * Small ship takes one, medium ship takes two size of bay.
   * @param size Bay size to set
   */
  public void setBaySize(final int size) {
    this.defenseValue = size;
  }
  /**
   * Set component defense value. This is used for both armor and shields.
   * @param defenseValue Defense value
   */
  public void setDefenseValue(final int defenseValue) {
    this.defenseValue = defenseValue;
  }

  /**
   * Get component damage. This is used for all weapons.
   * @return Damage component causes when it hits
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Set how much component causes damage on hit.
   * @param damage Damage on hit.
   */
  public void setDamage(final int damage) {
    this.damage = damage;
  }

  /**
   * Get maximum range for weapon
   * @return Range for weapon
   */
  public int getWeaponRange() {
    return weaponRange;
  }

  /**
   * Set maximum range for weapon
   * @param weaponRange Range in tiles.
   */
  public void setWeaponRange(final int weaponRange) {
    this.weaponRange = weaponRange;
  }

  /**
   * Get hitChance by type
   * @return hitChance
   */
  public int getHitChance() {
      return type.getHitChance();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append("\n");
    sb.append("Cost: ");
    sb.append(getCost());
    sb.append(" Metal: ");
    sb.append(getMetalCost());
    sb.append("\n");
    if (getEnergyRequirement() > 0 && getEnergyResource() == 0) {
      sb.append("Energy: ");
      sb.append(getEnergyRequirement());
      sb.append("\n");
    }
    if (getEnergyResource() > 0) {
      sb.append("Energy source: ");
      sb.append(getEnergyResource());
      sb.append("\n");
    }
    switch (getType()) {
    case SOLAR_ARMOR:
    case ARMOR: {
      sb.append("Armor value: ");
      sb.append(getDefenseValue());
      sb.append("\n");
      break;
    }
    case ORGANIC_ARMOR: {
      sb.append("Armor value: ");
      sb.append(getDefenseValue());
      sb.append(" ");
      sb.append("Armor regeneration");
      sb.append("\n");
      break;
    }
    case DISTORTION_SHIELD: {
      sb.append("Shield value: ");
      sb.append(getDefenseValue());
      sb.append(" Jammer: -");
      sb.append(getDamage());
      sb.append("%");
      sb.append("\n");
      break;
    }
    case SHIELD: {
      sb.append("Shield value: ");
      sb.append(getDefenseValue());
      sb.append("\n");
      break;
    }
    case CLOAKING_DEVICE: {
      sb.append("Cloaking:");
      sb.append(getCloaking());
      sb.append("\n");
      break;
    }
    case STARBASE_COMPONENT: {
      if (getCultureBonus() > 0) {
        sb.append("Culture bonus: ");
        sb.append(getCultureBonus());
        sb.append("\n");
      }
      if (getResearchBonus() > 0) {
        sb.append("Research bonus: ");
        sb.append(getResearchBonus());
        sb.append("\n");
      }
      if (getCreditBonus() > 0) {
        sb.append("Credit bonus: ");
        sb.append(getCreditBonus());
        sb.append("\n");
      }
      if (getFleetCapacityBonus() > 0) {
        sb.append("Fleet capacity bonus: ");
        sb.append(getFleetCapacityBonus());
        sb.append("\n");
      }
      break;
    }
    case TRACTOR_BEAM: {
      sb.append("Pulls smaller ships to closer");
      sb.append("\n");
      break;
    }
    case COLONY_MODULE:
    case POWERSOURCE:
    case PRIVATEERING_MODULE: {
      // Do nothing
      break;
    }
    case ENGINE: {
      sb.append("Speed: ");
      sb.append(getSpeed());
      sb.append(" FTL: ");
      sb.append(getFtlSpeed());
      sb.append(" Combat Speed: ");
      sb.append(getTacticSpeed());
      sb.append("\n");
      break;
    }
    case THRUSTERS: {
      sb.append("Initiative boost: +");
      sb.append(getInitiativeBoost());
      sb.append("\n");
      sb.append("Combat Speed boost: +");
      sb.append(getTacticSpeed());
      sb.append("\n");
      break;
    }
    case FIGHTER_BAY: {
      sb.append("Bay size: ");
      sb.append(getBaySize());
      sb.append("\n");
      break;
    }
    case WEAPON_BEAM: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range: ");
      sb.append(getWeaponRange());
      sb.append("\nHit: 100%, 50% penetrates armor");
      sb.append("\n");
      break;
    }
    case WEAPON_ECM_TORPEDO: {
      sb.append("Shield damage: ");
      sb.append(getDamage());
      sb.append(" Range: ");
      sb.append(getWeaponRange());
      sb.append("\nHit: 50%, damages only shields");
      sb.append("\n");
      break;
    }
    case WEAPON_HE_MISSILE: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range: ");
      sb.append(getWeaponRange());
      sb.append("\nHit: 50%, 50% penetrates shields");
      sb.append("\n");
      break;
    }
    case WEAPON_PHOTON_TORPEDO: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range: ");
      sb.append(getWeaponRange());
      sb.append("\nHit: 75%, 50% penetrates armor");
      sb.append("\n");
      break;
    }
    case WEAPON_RAILGUN: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range: ");
      sb.append(getWeaponRange());
      sb.append("\nHit: 75%, 50% penetrates shields");
      sb.append("\n");
      break;
    }
    case JAMMER: {
      sb.append("Jammer: -");
      sb.append(getDefenseValue());
      sb.append("%");
      sb.append("\n");
      break;
    }
    case ORBITAL_BOMBS: {
      sb.append("Orbital bombs: ");
      sb.append(getDamage());
      sb.append("% hit chance");
      sb.append("\n");
      break;
    }
    case ORBITAL_NUKE: {
      sb.append("Orbital Nuke\n");
      int dead = getDamage();
      int buildingsDestroyed = getDamage();
      if (getName().equals("Orbital antimatter bomb")) {
        dead = 100;
      }
      if (getName().equals("Orbital neutron bomb")) {
        dead = 100;
        buildingsDestroyed = 50;
      }
      sb.append("Kill ratio: ");
      sb.append(dead);
      sb.append("%\n");
      sb.append("Buildings destroyed: ");
      sb.append(buildingsDestroyed);
      sb.append("%\n");
      break;
    }
    case PLANETARY_INVASION_MODULE: {
      sb.append("Troop combat: ");
      sb.append(getDamage());
      sb.append("%");
      sb.append("\n");
      break;
    }
    case SCANNER: {
      sb.append("Range: ");
      sb.append(getScannerRange());
      sb.append(" Cloak detection: ");
      sb.append(getCloakDetection());
      sb.append(" %");
      sb.append("\n");
      break;
    }
    case SHIELD_GENERATOR: {
      sb.append("Shield generator: ");
      sb.append(getDefenseValue());
      sb.append("\n");
      break;
    }
    case ESPIONAGE_MODULE: {
      sb.append("Espionage: +");
      sb.append(getEspionageBonus());
      sb.append("\n");
      break;
    }
    case TARGETING_COMPUTER: {
      sb.append("Targeting computer: +");
      sb.append(getDamage());
      sb.append("% to hit");
      sb.append("\n");
      sb.append("Initiative boost: +");
      sb.append(getInitiativeBoost());
      sb.append("\n");
      break;
    }
    default:
      // Do nothing
    }
    return sb.toString();
  }

  /**
   * Get component initiative boost for combat
   * @return Initiative boost
   */
  public int getInitiativeBoost() {
    return initiativeBoost;
  }

  /**
   * Set component initiative boost for combat.
   * @param initiativeBoost for combat.
   */
  public void setInitiativeBoost(final int initiativeBoost) {
    this.initiativeBoost = initiativeBoost;
  }

  /**
   * Culture bonus from starbase component
   * @return Culture bonus
   */
  public int getCultureBonus() {
    return cultureBonus;
  }

  /**
   * Set culture bonus for starbase component
   * @param cultureBonus Culture bonus
   */
  public void setCultureBonus(final int cultureBonus) {
    this.cultureBonus = cultureBonus;
  }

  /**
   * Get credit bonus from starbase component
   * @return Credit bonus
   */
  public int getCreditBonus() {
    return creditBonus;
  }

  /**
   * Set credit bonus for starbase component
   * @param creditBonus Credit bonus
   */
  public void setCreditBonus(final int creditBonus) {
    this.creditBonus = creditBonus;
  }

  /**
   * Get research bonus
   * @return Research bonus
   */
  public int getResearchBonus() {
    return researchBonus;
  }

  /**
   * Set research bonus for starbase component
   * @param researchBonus Research bonus
   */
  public void setResearchBonus(final int researchBonus) {
    this.researchBonus = researchBonus;
  }

  /**
   * Get fleet capacity bonus
   * @return the fleetCapacityBonus
   */
  public int getFleetCapacityBonus() {
    return fleetCapacityBonus;
  }

  /**
   * Set Fleet capacity bonus for starbase component
   * @param fleetCapacityBonus the fleetCapacityBonus to set
   */
  public void setFleetCapacityBonus(final int fleetCapacityBonus) {
    this.fleetCapacityBonus = fleetCapacityBonus;
  }

  /**
   * Get Espionage bonus
   * @return the espionageBonus
   */
  public int getEspionageBonus() {
    return espionageBonus;
  }

  /**
   * Set Espionage bonus. Maximum bonus is 10.
   * @param espionageBonus the espionageBonus to set
   */
  public void setEspionageBonus(final int espionageBonus) {
    if (espionageBonus > 10) {
      this.espionageBonus = 10;
    } else if (espionageBonus < 0) {
      this.espionageBonus = 0;
    } else {
      this.espionageBonus = espionageBonus;
    }
  }

}
