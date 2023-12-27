package org.openRealmOfStars.player.race.trait;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.openRealmOfStars.player.race.SpaceRace;

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
 * @see SpaceRace
 */
public final class RaceTrait {
  /** ID of the trait */
  private String traitId;
  /** Trait name */
  private String traitName;
  /** Description of the trait */
  private String description;
  /** IDs of traits this trait conflicts with */
  private ArrayList<String> conflictsWithIds;
  /** How good (+) or bad (-) the trait is from gameplay perspective */
  private byte traitPoints;

  /**
   * Creates new RaceTrait
   * @param id ID of the trait, must be unique and non-null
   * @param name name of the trait, must be non-null
   * @param description description of the trait, must be non-null
   * @param points how good or bad the trait is from gameplay perspective
   * @param conflictsWith Array of IDs of traits this trait conflicts with
   */
  RaceTrait(final String id, final String name, final String description,
      final byte points, final String... conflictsWith) {
    this(id, name, description, points, Arrays.asList(conflictsWith));
  }

  /**
   * Creates new RaceTrait
   * @param id ID of the trait, must be unique and non-null
   * @param name name of the trait, must be non-null
   * @param description description of the trait, must be non-null
   * @param points how good or bad the trait is from gameplay perspective
   * @param conflictsWith Collection of IDs of traits this trait conflicts with
   */
  RaceTrait(final String id, final String name, final String description,
      final byte points, final Collection<String> conflictsWith) {
    this.traitId = Objects.requireNonNull(id);
    this.traitName = Objects.requireNonNull(name);
    this.description = Objects.requireNonNull(description);
    this.traitPoints = points;
    this.conflictsWithIds = new ArrayList<>(conflictsWith);
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
   * How good (+) or bad (-) the trait is from gameplay perspective
   * @return byte representing trait's benefit value
   */
  public byte getPoints() {
    return traitPoints;
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
