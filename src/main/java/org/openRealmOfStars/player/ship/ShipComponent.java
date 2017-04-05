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
   * Weapon range
   */
  private int weaponRange;

  /**
   * Initiative boost for combat
   */
  private int initiativeBoost;

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
    case ARMOR: {
      sb.append("Armor value:");
      sb.append(getDefenseValue());
      sb.append("\n");
      break;
    }
    case SHIELD: {
      sb.append("Shield value:");
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
    case COLONY_MODULE:
    case POWERSOURCE:
    case PRIVATEERING_MODULE:
    case ORBITAL_NUKE: {
      // Do nothing
      break;
    }
    case ENGINE: {
      sb.append("Speed: ");
      sb.append(getSpeed());
      sb.append(" FTL:");
      sb.append(getFtlSpeed());
      sb.append(" Combat Speed:");
      sb.append(getTacticSpeed());
      sb.append("\n");
      break;
    }
    case WEAPON_BEAM: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range:");
      sb.append(getWeaponRange());
      sb.append("\nHit: 100%, 50% penetrates armor");
      sb.append("\n");
      break;
    }
    case WEAPON_ECM_TORPEDO: {
      sb.append("Shield damage: ");
      sb.append(getDamage());
      sb.append(" Range:");
      sb.append(getWeaponRange());
      sb.append("\nHit: 50%, damages only shields");
      sb.append("\n");
      break;
    }
    case WEAPON_HE_MISSILE: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range:");
      sb.append(getWeaponRange());
      sb.append("\nHit: 50%, 50% penetrates shields");
      sb.append("\n");
      break;
    }
    case WEAPON_PHOTON_TORPEDO: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range:");
      sb.append(getWeaponRange());
      sb.append("\nHit: 75%, 50% penetrates armor");
      sb.append("\n");
      break;
    }
    case WEAPON_RAILGUN: {
      sb.append("Damage: ");
      sb.append(getDamage());
      sb.append(" Range:");
      sb.append(getWeaponRange());
      sb.append("\nHit: 50%, 50% penetrates shields");
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
      sb.append("\n");
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
      sb.append(" Cloak detection:");
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
    case TARGETING_COMPUTER: {
      sb.append("Targeting computer: +");
      sb.append(getDamage());
      sb.append("% to hit");
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

}
