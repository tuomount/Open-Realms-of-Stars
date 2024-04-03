package org.openRealmOfStars.starMap.planet.status;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
 * Copyright (C) 2024 Tuomo Untinen
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Game-time definition of planetary status.
 *
 * <p>
 * Objects of this type are <b>definitions</b> of planetary statuses.
 * It can be considered "template" or "description"
 * what the status is and how it works.
 * </p>
 * <p>
 * Application of status on a planet is done with {@link AppliedStatus}
 * objects, that contain a reference to object of this type.
 * </p>
 * <p>
 * Objects of this class are immutable.
 * </p>
 * @see StatusFactory
 */
public class PlanetaryStatus {
  /**
   * Check if status is in conflict with provided statuses.
   * If provided statuses contain status with same ID as checked
   * status, this is also considered a conflict.
   * @param status Status to check if conflicting
   * @param otherStatuses Statuses to check against
   * @return True if status is in conflict with specified statuses
   */
  public static boolean isConflictingWith(final PlanetaryStatus status,
      final PlanetaryStatus... otherStatuses) {
    for (var other : otherStatuses) {
      if (status.getConflictingIds().contains(other.getId())) {
        return true;
      }
      if (status.getId().equals(other.getId())) {
        return true;
      }
    }
    return false;
  }

  /** ID of the PlanetaryStatus, must be unique */
  private String statusId;
  /** Name of the status */
  private String name;
  /** Description of the status */
  private String description;
  /** Array of IDs of PlanetaryStatuses this conflicts with */
  private String[] conflictingIds;

  // Planet attribute changes applied by the status
  // TODO: Turn those attributes into object?

  /** Bonus/malus to food production */
  private int foodBonus;
  /** Bonus/malus to planet happiness */
  private int happinessBonus;
  /** Bonus/malus to metal production */
  private int mineBonus;
  /** Bonus/malus to planetary production */
  private int prodBonus;
  /** Bonus/malus to credits production */
  private int credBonus;
  /** Status is not visible, until it is applied. This cannot be revealed even
   * by sending away team. */
  private boolean hidden;
  /** Status can be found with away team */
  private boolean awayTeam;
  /** Text to show when discovering planetary status. */
  private String discoveryText;
  /**
   * Create new planetary status definition
   * @param id ID of planetary status definition
   * @param name name of the status
   * @param description description of the status
   * @param conflictingIds IDs of statuses this status conflicts with
   */
  PlanetaryStatus(final String id, final String name, final String description,
      final String... conflictingIds) {
    this.statusId = Objects.requireNonNull(id);
    this.name = Objects.requireNonNull(name);
    this.description = Objects.requireNonNull(description);
    this.conflictingIds = Objects.requireNonNull(conflictingIds);
    this.hidden = false;
    this.setAwayTeam(false);
    setDiscoveryText("");
  }

  /**
   * @return ID of the PlanetaryStatus
   */
  public String getId() {
    return statusId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the conflictingIds
   */
  public List<String> getConflictingIds() {
    return Collections.unmodifiableList(Arrays.asList(conflictingIds));
  }

  /**
   * @return the foodBonus
   */
  public int getFoodBonus() {
    return foodBonus;
  }

  /**
   * @return the happinessBonus
   */
  public int getHappinessBonus() {
    return happinessBonus;
  }

  /**
   * @return the mineBonus
   */
  public int getMineBonus() {
    return mineBonus;
  }

  /**
   * @return the prodBonus
   */
  public int getProdBonus() {
    return prodBonus;
  }

  /**
   * @return the credBonus
   */
  public int getCredBonus() {
    return credBonus;
  }

  //
  // Setters visibility is intentionally limited to this package.
  // This is to preserve immutability guarantees to rest of the code.
  //

  /**
   * @param foodBonus the foodBonus to set
   */
  void setFoodBonus(final int foodBonus) {
    this.foodBonus = foodBonus;
  }

  /**
   * @param happinessBonus the happinessBonus to set
   */
  void setHappinessBonus(final int happinessBonus) {
    this.happinessBonus = happinessBonus;
  }

  /**
   * @param mineBonus the mineBonus to set
   */
  void setMineBonus(final int mineBonus) {
    this.mineBonus = mineBonus;
  }

  /**
   * @param prodBonus the prodBonus to set
   */
  void setProdBonus(final int prodBonus) {
    this.prodBonus = prodBonus;
  }

  /**
   * @param credBonus the credBonus to set
   */
  void setCredBonus(final int credBonus) {
    this.credBonus = credBonus;
  }

  /**
   * @return the hidden
   */
  public boolean isHidden() {
    return hidden;
  }

  /**
   * @param hidden the hidden to set
   */
  public void setHidden(final boolean hidden) {
    this.hidden = hidden;
  }

  /**
   * @return the awayTeam
   */
  public boolean isAwayTeam() {
    return awayTeam;
  }

  /**
   * @param awayTeam the awayTeam to set
   */
  public void setAwayTeam(final boolean awayTeam) {
    this.awayTeam = awayTeam;
  }

  /**
   * Get Planetary status discovery text.
   * @return the discoveryText
   */
  public String getDiscoveryText() {
    return discoveryText;
  }

  /**
   * Set discovery text for planetary status.
   * @param discoveryText the discoveryText to set
   */
  public void setDiscoveryText(final String discoveryText) {
    this.discoveryText = discoveryText;
  }

}