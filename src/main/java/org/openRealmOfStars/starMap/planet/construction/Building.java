package org.openRealmOfStars.starMap.planet.construction;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2018  Tuomo Untinen
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
 * Class for planetary building or improvement
 *
 */
public class Building extends Construction {

  /**
   * Building type
   */
  private BuildingType type;
  /**
   * Unique index for factories and saving the game
   */
  private int index;

  /**
   * Bonus for farming
   */
  private int farmBonus;
  /**
   * Bonus for mining
   */
  private int mineBonus;
  /**
   * Bonus for production
   */
  private int factBonus;
  /**
   * Bonus for culture
   */
  private int cultBonus;
  /**
   * Bonus for research
   */
  private int reseBonus;

  /**
   * Bonus for credits
   */
  private int credBonus;

  /**
   * Maintenance Cost
   */
  private double maintenanceCost;

  /**
   * Only single building allowed per planet
   */
  private boolean singleAllowed;

  /**
   * Building's battle bonus
   */
  private int battleBonus;

  /**
   * Building's recycle bonus
   */
  private int recycleBonus;

  /**
   * Planetary defense damage
   */
  private int defenseDamage;

  /**
   * Planetary scan range
   */
  private int scanRange;

  /**
   * Planetary scanner cloaking detection
   */
  private int scanCloakingDetection;

  /**
   * Happiness bonus
   */
  private int happinessBonus;

  /**
   * Building is scientific achievement
   */
  private boolean scientificAchievement;
  /**
   * Building material bonus
   */
  private int materialBonus;

  /**
   * Fleet capacity bonus
   */
  private int fleetCapacityBonus;
  /**
   * Construct building for planet
   * @param index Unique number for building
   * @param name Building name
   * @param icon Icon to use next to the building
   * @param type BuildingType
   */
  public Building(final int index, final String name, final Icon16x16 icon,
      final BuildingType type) {
    super(name, icon);
    this.index = index;
    this.type = type;
    this.farmBonus = 0;
    this.mineBonus = 0;
    this.factBonus = 0;
    this.cultBonus = 0;
    this.credBonus = 0;
    this.reseBonus = 0;
    this.materialBonus = 0;
    this.setProdCost(1);
    this.setMetalCost(1);
    this.maintenanceCost = 0;
    this.singleAllowed = false;
    this.battleBonus = 0;
    this.recycleBonus = 0;
    this.defenseDamage = 0;
    this.scanRange = 0;
    this.scanCloakingDetection = 0;
    this.happinessBonus = 0;
    this.fleetCapacityBonus = 0;
  }

  /**
   * Get Building type enumeration
   * @return BuildingType
   */
  public BuildingType getType() {
    return type;
  }

  /**
   * Get building unique index
   * @return unique index for building
   */
  public int getIndex() {
    return index;
  }

  /**
   * Get building food produce bonus
   * @return food production bonus
   */
  public int getFarmBonus() {
    return farmBonus;
  }

  /**
   * Set food production bonus for building
   * @param farmBonus Food production bonus
   */
  public void setFarmBonus(final int farmBonus) {
    this.farmBonus = farmBonus;
  }

  /**
   * Get building mine production bonus
   * @return Mine production bonus
   */
  public int getMineBonus() {
    return mineBonus;
  }

  /**
   * Set building mine bonus
   * @param mineBonus Mine production bonus
   */
  public void setMineBonus(final int mineBonus) {
    this.mineBonus = mineBonus;
  }

  /**
   * Get building factory aka production bonus
   * @return factory bonus
   */
  public int getFactBonus() {
    return factBonus;
  }

  /**
   * Set building factory bonus
   * @param factBonus factory aka production bonus
   */
  public void setFactBonus(final int factBonus) {
    this.factBonus = factBonus;
  }

  /**
   * Get building culture bonus
   * @return Culture bonus
   */
  public int getCultBonus() {
    return cultBonus;
  }

  /**
   * Set building culture bonus
   * @param cultBonus Culture bonus
   */
  public void setCultBonus(final int cultBonus) {
    this.cultBonus = cultBonus;
  }

  /**
   * Get building research bonus
   * @return Research bonus
   */
  public int getReseBonus() {
    return reseBonus;
  }

  /**
   * Set building research bonus
   * @param reseBonus Research bonus
   */
  public void setReseBonus(final int reseBonus) {
    this.reseBonus = reseBonus;
  }

  /**
   * Get building credit aka money bonus
   * @return credit bonus
   */
  public int getCredBonus() {
    return credBonus;
  }

  /**
   * Set building credit aka money bonus
   * @param credBonus Credit Bonus
   */
  public void setCredBonus(final int credBonus) {
    this.credBonus = credBonus;
  }

  /**
   * Maximum building description line length
   */
  private static final int LINE_LENGTH = 39;

  /**
   * Get full description of Building. This can be modified by space race.
   * Some buildings might have some variants according the race.
   * @param race SpaceRace can be null
   * @return Description as a String.
   */
  public String getFullDescription(final SpaceRace race) {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    if (isSingleAllowed()) {
      sb.append(" - one per planet");
    }
    sb.append("\n");
    sb.append(IOUtilities.stringWrapper(getDescription(), LINE_LENGTH));
    sb.append("\n");
    sb.append("Cost: Prod.:");
    sb.append(getProdCost());
    sb.append(" Metal:");
    sb.append(getMetalCost());
    if (getMaintenanceCost() > 0) {
      sb.append(" Mainte.: ");
      sb.append(getMaintenanceCost());
    }
    sb.append("\n");
    boolean space = false;
    if (getFarmBonus() > 0) {
      sb.append("Food: +");
      sb.append(getFarmBonus());
      space = true;
    }
    if (getMineBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Mine: +");
      sb.append(getMineBonus());
      space = true;
    }
    if (getMaterialBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Material: +");
      sb.append(getMaterialBonus());
      space = true;
    }
    if (getFactBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Prod.: +");
      sb.append(getFactBonus());
      space = true;
    }
    if (getCultBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Cult.: +");
      sb.append(getCultBonus());
      space = true;
    }
    if (getReseBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Resea.: +");
      sb.append(getReseBonus());
      space = true;
    }
    if (getCredBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Cred.: +");
      int value = getCredBonus();
      if (race != null && race == SpaceRace.SCAURIANS) {
        value = value + 1;
      }
      sb.append(value);
      space = true;
    }
    if (getHappiness() != 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Happiness: ");
      int value = getHappiness();
      if (value > 0) {
        sb.append("+");
      }
      sb.append(value);
      space = true;
    }
    if (getFleetCapacityBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Fleet capacity: +");
      int value = getFleetCapacityBonus();
      sb.append(value);
      space = true;
    }
    if (getBattleBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Battle: +");
      sb.append(getBattleBonus());
      sb.append("%");
      space = true;
    }
    if (getRecycleBonus() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Recycle: +");
      sb.append(getRecycleBonus());
      sb.append("%");
      space = true;
    }
    if (getDefenseDamage() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Damage: ");
      sb.append(getDefenseDamage());
      space = true;
    }
    if (getScanRange() > 0) {
      if (space) {
        sb.append("\n");
      }
      sb.append("Scan range: ");
      sb.append(getScanRange());
      space = true;
    }
    if (getScanCloakingDetection() > 0) {
      if (space) {
        sb.append(" ");
      }
      sb.append("Cloaking det.: ");
      sb.append(getScanCloakingDetection());
      sb.append("%");
      space = true;
    }
    return sb.toString();
  }

  @Override
  public String getFullDescription() {
    return getFullDescription(null);
  }

  /**
   * @return the maintenanceCost
   */
  public double getMaintenanceCost() {
    return maintenanceCost;
  }

  /**
   * @param maintenanceCost the maintenanceCost to set
   */
  public void setMaintenanceCost(final double maintenanceCost) {
    this.maintenanceCost = maintenanceCost;
  }

  /**
   * Is only single building allowed per planet
   * @return True if only one building per planet
   */
  public boolean isSingleAllowed() {
    return singleAllowed;
  }

  /**
   * Set if only single building allowed per planet
   * @param singleAllowed Set true if only one per planet allowed
   */
  public void setSingleAllowed(final boolean singleAllowed) {
    this.singleAllowed = singleAllowed;
  }

  /**
   * Get building battle bonus for defending troops
   * @return Battle bonus
   */
  public int getBattleBonus() {
    return battleBonus;
  }

  /**
   * Set building battle bonus for defending troops
   * @param battleBonus Battle bonus for defending troops.
   */
  public void setBattleBonus(final int battleBonus) {
    this.battleBonus = battleBonus;
  }

  /**
   * Get building recycle bonus. How much metal is recycled when
   * building or ship is recycled on planet.
   * @return Recycle bonus
   */
  public int getRecycleBonus() {
    return recycleBonus;
  }

  /**
   * Set building recycle bonus.
   * @param recycleBonus Recycle bonus
   */
  public void setRecycleBonus(final int recycleBonus) {
    this.recycleBonus = recycleBonus;
  }

  /**
   * Get building defense damage aka turret power
   * @return Defense Damage
   */
  public int getDefenseDamage() {
    return defenseDamage;
  }

  /**
   * Set building defense damage
   * @param defenseDamage Defense Damage
   */
  public void setDefenseDamage(final int defenseDamage) {
    this.defenseDamage = defenseDamage;
  }

  /**
   * Get building scanner range
   * @return Scanner ranger
   */
  public int getScanRange() {
    return scanRange;
  }

  /**
   * Set scanner range
   * @param scanRange Scanner Range in tiles
   */
  public void setScanRange(final int scanRange) {
    this.scanRange = scanRange;
  }

  /**
   * Set Building happiness bonus
   * @param bonus for happiness
   */
  public void setHappiness(final int bonus) {
    happinessBonus = bonus;
  }

  /**
   * Get Building happiness bonus
   * @return bonus for happiness
   */
  public int getHappiness() {
    return happinessBonus;
  }
  /**
   * Get cloaking detection power
   * @return cloaking detection power
   */
  public int getScanCloakingDetection() {
    return scanCloakingDetection;
  }

  /**
   * Set cloaking detection power
   * @param scanCloakingDetection Scanner cloaking detection power
   */
  public void setScanCloakingDetection(final int scanCloakingDetection) {
    this.scanCloakingDetection = scanCloakingDetection;
  }

  /**
   * Set scientific achievement
   * @param value True to set
   */
  public void setScientificAchievement(final boolean value) {
    scientificAchievement = value;
  }

  /**
   * Get scientic achievement
   * @return True if building is scientic achievement
   */
  public boolean getScientificAchievement() {
    return scientificAchievement;
  }

  /**
   * Get building's material bonus which means metal generate
   * from empty.
   * @return the materialBonus
   */
  public int getMaterialBonus() {
    return materialBonus;
  }

  /**
   * Set building's material bonus which means metal generate
   * from empty.
   * @param materialBonus the materialBonus to set
   */
  public void setMaterialBonus(final int materialBonus) {
    this.materialBonus = materialBonus;
  }

  /**
   * Get fleet capacity bonus.
   * @return the fleetCapacityBonus
   */
  public int getFleetCapacityBonus() {
    return fleetCapacityBonus;
  }

  /**
   * Set building's fleet capacity bonus
   * @param fleetCapacityBonus the fleetCapacityBonus to set
   */
  public void setFleetCapacityBonus(final int fleetCapacityBonus) {
    this.fleetCapacityBonus = fleetCapacityBonus;
  }
}
