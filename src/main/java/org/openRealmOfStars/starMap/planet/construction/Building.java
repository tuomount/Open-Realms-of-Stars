package org.openRealmOfStars.starMap.planet.construction;

import org.openRealmOfStars.gui.icons.Icon16x16;
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
    this.prodCost = 1;
    this.metalCost = 1;
    this.maintenanceCost = 0;
    this.singleAllowed = false;
    this.battleBonus = 0;
    this.recycleBonus = 0;
    this.defenseDamage = 0;
    this.scanRange = 0;
    this.scanCloakingDetection = 0;
  }

  public BuildingType getType() {
    return type;
  }

  public void setType(final BuildingType type) {
    this.type = type;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(final int index) {
    this.index = index;
  }

  public int getFarmBonus() {
    return farmBonus;
  }

  public void setFarmBonus(final int farmBonus) {
    this.farmBonus = farmBonus;
  }

  public int getMineBonus() {
    return mineBonus;
  }

  public void setMineBonus(final int mineBonus) {
    this.mineBonus = mineBonus;
  }

  public int getFactBonus() {
    return factBonus;
  }

  public void setFactBonus(final int factBonus) {
    this.factBonus = factBonus;
  }

  public int getCultBonus() {
    return cultBonus;
  }

  public void setCultBonus(final int cultBonus) {
    this.cultBonus = cultBonus;
  }

  public int getReseBonus() {
    return reseBonus;
  }

  public void setReseBonus(final int reseBonus) {
    this.reseBonus = reseBonus;
  }

  public int getCredBonus() {
    return credBonus;
  }

  public void setCredBonus(final int credBonus) {
    this.credBonus = credBonus;
  }

  @Override
  public String getFullDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    if (isSingleAllowed()) {
      sb.append(" - one per planet");
    }
    sb.append("\n");
    sb.append(IOUtilities.stringWrapper(getDescription(), 39));
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
      sb.append(getCredBonus());
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

  public boolean isSingleAllowed() {
    return singleAllowed;
  }

  public void setSingleAllowed(final boolean singleAllowed) {
    this.singleAllowed = singleAllowed;
  }

  public int getBattleBonus() {
    return battleBonus;
  }

  public void setBattleBonus(final int battleBonus) {
    this.battleBonus = battleBonus;
  }

  public int getRecycleBonus() {
    return recycleBonus;
  }

  public void setRecycleBonus(final int recycleBonus) {
    this.recycleBonus = recycleBonus;
  }

  public int getDefenseDamage() {
    return defenseDamage;
  }

  public void setDefenseDamage(final int defenseDamage) {
    this.defenseDamage = defenseDamage;
  }

  public int getScanRange() {
    return scanRange;
  }

  public void setScanRange(final int scanRange) {
    this.scanRange = scanRange;
  }

  public int getScanCloakingDetection() {
    return scanCloakingDetection;
  }

  public void setScanCloakingDetection(final int scanCloakingDetection) {
    this.scanCloakingDetection = scanCloakingDetection;
  }

}
