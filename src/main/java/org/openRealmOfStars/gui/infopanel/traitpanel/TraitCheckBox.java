package org.openRealmOfStars.gui.infopanel.traitpanel;
/*
 * Open Realm of Stars game project
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

import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.player.government.trait.GovTrait;

/**
 * Trait Check box based on SpaceCheckBox.
 *
 */
public class TraitCheckBox extends SpaceCheckBox {

  /** Trait ID. */
  private String id;
  /** Trait name. */
  private String name;
  /** Trait description, basically tool tip. */
  private String description;
  /** Which group trait belongs. */
  private String group;
  /** Array of IDs which this cannot be selected same time. */
  private String[] conflictsWithId;
  /** Trait value in points. */
  private byte traitPoints;

  /**
   * Constructor for building check box for government trait.
   * @param trait Government Trait.
   */
  public TraitCheckBox(final GovTrait trait) {
    super(trait.getName());
    id = trait.getId();
    name = trait.getName();
    description = trait.getDescription();
    group = trait.getGroup();
    conflictsWithId = trait.getConflictsWithIds().toArray(new String[0]);
    traitPoints = trait.getPoints();
  }

  /**
   * Get Trait ID.
   * @return ID as a String.
   */
  public String getTraitId() {
    return id;
  }

  /**
   * Get Trait name.
   * @return Trait name as a String.
   */
  public String getTraitName() {
    return name;
  }

  /**
   * Get Trait description.
   * @return Trait description as a String.
   */
  public String getTraitDescription() {
    return description;
  }

  /**
   * Get Trait group
   * @return Trait group as a String.
   */
  public String getTraitGroup() {
    return group;
  }

  /**
   * Get array of other Trait IDs which conflict with current one.
   * @return Array of Strings
   */
  public String[] getTraitConflictsWithId() {
    return conflictsWithId;
  }

  /**
   * Get point value of trait.
   * @return Point value of trait as a byte.
   */
  public byte getTraitPoints() {
    return traitPoints;
  }

}
