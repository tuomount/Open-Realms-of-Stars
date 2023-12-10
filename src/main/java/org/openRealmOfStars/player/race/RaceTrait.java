package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a trait that SpaceRace may have.
 *
 * <p>
 * Traits are not stackable and should be considered being "flags".
 * SpaceRace can have only one trait with given ID.
 * </p>
 * <p>
 * RaceTrait must have unique ID. It must have a name
 * and description, both may be non-unique.
 * Trait can have a list of trait IDs it conflicts with.
 * </p>
 * <p>
 * List of conflicting traits should be in sync with other traits.
 * Situations like trait A conflicting with trait B,
 * but B NOT conficting with A should be avoided.
 * </p>
 * TODO: Dehardcode actual traits from here
 * @see SpaceRace
 */
public final class RaceTrait {
  /** Robotic */
  public static final RaceTrait ROBOTIC = new RaceTrait("ROBOTIC", "Robotic",
      "Race is robotic.");
  /**
   * Eats minerals.
   * Cannot be combined with ENERGY_POWERED.
   */
  public static final RaceTrait LITHOVORIC = new RaceTrait("LITHOVORIC",
      "Lithovoric",
      "Eats minerals instead of food.", "ENERGY_POWERED");
  /**
   * Eats energy (credits).
   * Cannot be combined with LITHOVORIC.
   */
  public static final RaceTrait ENERGY_POWERED = new RaceTrait(
      "ENERGY_POWERED", "Powered",
      "Consumes energy credits instead of food"
          + " at a rate of 1 credit per 4 population.");
  /** Concept of parental heritage is not applicable for the race. */
  public static final RaceTrait NO_HEIRS = new RaceTrait(
      "NO_HEIRS", "No heirs",
      "Race has unusual birth process, to which it is"
          + " not possible to apply concept of parental heritage.");
  /** Race can breed by explictly constructing it's own population. */
  public static final RaceTrait CONSTRUCTED_POP = new RaceTrait(
      "CONSTRUCTED_POP", "Constructed",
      "Breeds by external process, where individuals "
          + " are \"constructed\" in some way.");
  /**
   * Race can gain sustenance from radiation.
   * This does NOT mean that it able to *produce* food,
   * but rather that it can *sustain itself* with radiation.
   */
  public static final RaceTrait RADIOSYNTHESIS = new RaceTrait(
      "RADIOSYNTHESIS", "Radiosynthesis",
      "Required sustenance (food) for population"
          + " is reduced by 1 per existing population,"
          + " up to planet's radiation value.");

  /** ID of the trait */
  private String traitId;
  /** Trait name */
  private String traitName;
  /** Description of the trait */
  private String description;
  /** IDs of traits this trait conflicts with */
  private ArrayList<String> conflictsWithIds;

  /**
   * Creates new RaceTrait
   * @param id ID of the trait, must be unique and non-null
   * @param name name of the trait, must be non-null
   * @param description description of the trait, must be non-null
   * @param conflictsWith Array of IDs of traits this trait conflicts with
   */
  private RaceTrait(final String id, final String name,
      final String description, final String... conflictsWith) {
    this.traitId = Objects.requireNonNull(id);
    this.traitName = Objects.requireNonNull(name);
    this.description = Objects.requireNonNull(description);
    this.conflictsWithIds = new ArrayList<>(Arrays.asList(conflictsWith));
  }

  /**
   * @return ID of the trait
   */
  public String getId() {
    return this.traitId;
  }

  /**
   * @return the traitName
   */
  public String getName() {
    return traitName;
  }

  /**
   * @return description of the trait
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @return List of IDs of traits this trait conflicts with
   */
  public List<String> getConflictsWithIds() {
    return Collections.unmodifiableList(conflictsWithIds);
  }

  /**
   * Check if RaceTrait conflicts with other RaceTraits.
   * If otherTraits already contains the trait, it is considered a conflict.
   * @param trait RaceTrait checked if being in conflict
   * @param otherTraits Set of traits checked against
   * @return True if trait is in conflict
   */
  public static boolean isTraitConflict(final RaceTrait trait,
      final RaceTrait... otherTraits) {
    for (var otherTrait : otherTraits) {
      if (trait.conflictsWithIds.contains(otherTrait.traitId)) {
        return true;
      }
      if (trait.traitId.equals(otherTrait.traitId)) {
        return true;
      }
    }
    return false;
  }
}
