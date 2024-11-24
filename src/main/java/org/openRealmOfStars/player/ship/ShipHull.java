package org.openRealmOfStars.player.ship;
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

import java.awt.image.BufferedImage;

import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
 * Ship hull for handling the very base of ship
 */
public class ShipHull {

  /**
   * Minimum upgrade value for small hull.
   */
  public static final int MIN_UPGRADE_SMALL = 2;
  /**
   * Minimum upgrade value for medium hull.
   */
  public static final int MIN_UPGRADE_MEDIUM = 4;
  /**
   * Minimum upgrade value for large hull.
   */
  public static final int MIN_UPGRADE_LARGE = 8;
  /**
   * Minimum upgrade value for huge hull.
   */
  public static final int MIN_UPGRADE_HUGE = 12;
  /**
   * Unique index for factories and saving the game
   */
  private int index;

  /**
   * Hull name needs to match one in techs
   */
  private String name;

  /**
   * How many components can be fitted
   */
  private int maxSlot;

  /**
   * How much hull point per slot
   */
  private int slotHull;

  /**
   * Ship's Hull type
   */
  private ShipHullType hullType;

  /**
   * Ship's size
   */
  private ShipSize size;

  /**
   * Hull cost in production
   */
  private int cost;

  /**
   * Hull cost in metal
   */
  private int metalCost;

  /**
   * Ship's image index
   */
  private int imageIndex;

  /**
   * Ship's required fleet capacity.
   */
  private double fleetCapacity;

  /**
   * Get Image Index
   * @return Image index
   */
  public int getImageIndex() {
    return imageIndex;
  }

  /**
   * Set image index for ship hull
   * @param imageIndex for ship hull
   */
  public void setImageIndex(final int imageIndex) {
    this.imageIndex = imageIndex;
  }

  /**
   * Save original builder information
   */
  private SpaceRace originalBuilder;

  /**
   * Constructor for Ship hull
   * @param index Index for factory
   * @param name Hull name, must match one in techs
   * @param maxSlots How many slots in hull
   * @param hull How many hull points single slot has
   * @param type ShipHullType
   * @param size ShipSize
   * @param cost production cost
   * @param metal metal cost
   * @param race whom builds the ship hull
   */
  public ShipHull(final int index, final String name, final int maxSlots,
      final int hull, final ShipHullType type, final ShipSize size,
      final int cost, final int metal, final SpaceRace race) {
    this.index = index;
    this.name = name;
    this.maxSlot = maxSlots;
    this.slotHull = hull;
    this.hullType = type;
    this.size = size;
    this.cost = cost;
    this.metalCost = metal;
    this.originalBuilder = race;
    // Default to Scout image
    this.imageIndex = ShipImage.SCOUT;
    // Default for fleet capacity.
    this.fleetCapacity = 0.1;
    // Ships of physically large races have extra hull point per slot
    // but hulls are more expensive.
    if (race.hasTrait(TraitIds.MASSIVE_SIZE)) {
      this.slotHull = this.slotHull + 1;
      this.metalCost = this.metalCost * 2;
      this.cost = this.cost * 3 / 2;
    }
    // Zero-G races have greatly lowered price of orbitals
    if (race.hasTrait(TraitIds.ZERO_GRAVITY_BEING)
        && this.hullType == ShipHullType.ORBITAL) {
      this.metalCost = this.metalCost / 2;
      this.cost = this.cost / 2;
    }
  }

  /**
   * Get ship's 64x64 pixel image
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return ShipImageFactor.create(originalBuilder.getSpaceShipId())
        .getShipImage(imageIndex);
  }

  /**
   * Get ship's 32x32 pixel image
   * @return BufferedImage
   */
  public BufferedImage getSmallImage() {
    return ShipImageFactor.create(originalBuilder.getSpaceShipId())
        .getNormalShipImage(imageIndex);
  }

  /**
   * Get Ship Hull index for factory. This needs to be unique.
   * @return Ship hull index for factory
   */
  public int getIndex() {
    return index;
  }

  /**
   * Set ship hull index for factory. This needs to be unique.
   * @return Ship hull index
   */
  public String getName() {
    return name;
  }

  /**
   * Maximum number of component fit to hull
   * @return Maximum number of slots
   */
  public int getMaxSlot() {
    return maxSlot;
  }

  /**
   * Hull point per slot
   * @return Hull points per slot aka "hit points"
   */
  public int getSlotHull() {
    return slotHull;
  }

  /**
   * Get Hull Type
   * @return Hull Tyoe
   */
  public ShipHullType getHullType() {
    return hullType;
  }

  /**
   * Get Ship size
   * @return Ship Size enum
   */
  public ShipSize getSize() {
    return size;
  }

  /**
   * Ship hull production cost
   * @return Production cost
   */
  public int getCost() {
    return cost;
  }

  /**
   * Ship hull metal cost
   * @return metal cost
   */
  public int getMetalCost() {
    return metalCost;
  }

  /**
   * Get the race whom built this ship
   * @return SpaceRace
   */
  public SpaceRace getRace() {
    return originalBuilder;
  }

  /**
   * @return the fleetCapacity
   */
  public double getFleetCapacity() {
    return fleetCapacity;
  }

  /**
   * Set ship hull's fleet capacity.
   * @param fleetCapacity the fleetCapacity to set
   */
  public void setFleetCapacity(final double fleetCapacity) {
    this.fleetCapacity = fleetCapacity;
  }

  /**
   * Line length for ship hull type description
   */
  private static final int LINE_LENGTH = 39;

  @Override
  public String toString() {
    return getDescription(false);
  }

  /**
   * @return defenseValue by ship hull size
   */
  public int getDefenseValueByShipHullSize() {
    int defenseValue;
    switch (size) {
    case SMALL:
        defenseValue = 10;
      break;
    case MEDIUM:
        defenseValue = 5;
      break;
    case LARGE:
        defenseValue = 0;
      break;
    case HUGE:
        defenseValue = -5;
      break;
    default:
        defenseValue = 0;
    }
    return defenseValue;
  }

  /**
   * Get hull description.
   * @param allowArmedFreighter True if arms are allowed in freighters
   * @return description as a string.
   */
  public String getDescription(final boolean allowArmedFreighter) {
    String hullDescription = IOUtilities.stringWrapper(
        getHullType().getDescription(), LINE_LENGTH);
    if (getHullType() == ShipHullType.PROBE
        && getName().startsWith("Probe Mk")) {
      hullDescription = IOUtilities.stringWrapper(
          "Probe, no weapons allowed. Faster regular and FTL speed.",
          LINE_LENGTH);
    }
    if (allowArmedFreighter && getHullType() == ShipHullType.FREIGHTER) {
      hullDescription = IOUtilities.stringWrapper(
          "Freighter, single weapon and privateer module allowed. Cargo ship",
          LINE_LENGTH);
    }
    return getName() + "\n" + "Cost: " + getCost() + " Metal: " + getMetalCost()
        + "\n" + "Slots:" + getMaxSlot() + " Hull:"
        + getMaxSlot() * getSlotHull() + "\n" + "Size:" + getSize().toString()
        + " Fleet capacity: " + getFleetCapacity()
        + "\n" + hullDescription;
  }

}
