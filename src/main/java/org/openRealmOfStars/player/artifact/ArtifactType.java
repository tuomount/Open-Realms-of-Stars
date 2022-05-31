package org.openRealmOfStars.player.artifact;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2022 Tuomo Untinen
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
* Artifact Type
*
*/
public enum ArtifactType {
  /**
   * Military artifact.
   */
  MILITARY,
  /**
   * Defense artifact
   */
  DEFENSE,
  /**
   * Ship hull artifact
   */
  SHIPHULL,
  /**
   * Planetary facility artifact
   */
  FACILITY,
  /**
   * Energy Artifact
   */
  ENERGY,
  /**
   * Electronic Artifact
   */
  ELECTRONIC;

  /**
   * Get Tech/Artifact type index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case MILITARY:
      return 0;
    case DEFENSE:
      return 1;
    case SHIPHULL:
      return 2;
    case FACILITY:
      return 3;
    case ENERGY:
      return 4;
    case ELECTRONIC:
      return 5;
    default:
      throw new IllegalArgumentException("Unexpected Tech type!");
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case MILITARY:
      return "Military";
    case DEFENSE:
      return "Defense";
    case SHIPHULL:
      return "Shiphull";
    case FACILITY:
      return "Facility";
    case ENERGY:
      return "Energy";
    case ELECTRONIC:
      return "Electronic";
    default:
      throw new IllegalArgumentException("Unexpected Tech type!");
    }
  }
}
