package org.openRealmOfStars.player.ship;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2019-2021 Tuomo Untinen
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
 * Ship hull for handling the very base of ship
 *
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
    // Centaur ships have extra hull point per slot
    // but hulls are more expensive.
    if (race == SpaceRace.CENTAURS) {
      this.slotHull = this.slotHull + 1;
      this.metalCost = this.metalCost * 2;
      this.cost = this.cost * 3 / 2;
    }
    if (race == SpaceRace.ALTEIRIANS && this.hullType == ShipHullType.ORBITAL) {
      this.metalCost = this.metalCost / 2;
      this.cost = this.cost / 2;
    }
  }

  /**
   * Get ship's 64x64 pixel image
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return ShipImages.getByRace(originalBuilder).getShipImage(imageIndex);
  }

  /**
   * Get ship's 32x32 pixel image
   * @return BufferedImage
   */
  public BufferedImage getSmallImage() {
    return ShipImages.getByRace(originalBuilder).getSmallShipImage(imageIndex);
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
    String hullDescription = IOUtilities.stringWrapper(
        getHullType().getDescription(), LINE_LENGTH);
    if (originalBuilder == SpaceRace.SMAUGIRIANS
        && getHullType() == ShipHullType.FREIGHTER) {
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
