package org.openRealmOfStars.player.artefact;

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
* Artifact factory
*
*/
public final class ArtifactFactory {

  /**
   * Hiding the constructor
   */
  private ArtifactFactory() {
    // nothing to do here
  }

  /**
   * Current maximum Artifact for whole game.
   * Remember to increase this when new artifact is added to game.
   * It should be one bigger than last index.
   */
  //private static final int MAX_ARTIFACT = 2;

  /**
   * Artifact Weapon fragment
   */
  public static final int ARTIFACT_WEAPON_FRAGMENT = 0;
  /**
   * Artifact Shattered weapon
   */
  public static final int ARTIFACT_SHATTERED_WEAPON = 1;

  /**
   * Artifact Armor fragment
   */
  public static final int ARTIFACT_ARMOR_FRAGMENT = 2;
  /**
   * Artifact Shattered deflector
   */
  public static final int ARTIFACT_SHATTERED_DEFLECTOR = 3;

  /**
   * Create artifact with index
   * @param index Index to create
   * @return Artifact or null if index is not found
   */
  public static Artifact createArtifact(final int index) {
    Artifact tmp = null;
    if (index == ARTIFACT_WEAPON_FRAGMENT) {
      tmp = new Artifact(index, "Weapon fragment", ArtifactType.MILITARY);
      tmp.setOneTimeTechBonus(10);
    }
    if (index == ARTIFACT_SHATTERED_WEAPON) {
      tmp = new Artifact(index, "Shattered weapon", ArtifactType.MILITARY);
      tmp.setOneTimeTechBonus(30);
    }
    if (index == ARTIFACT_ARMOR_FRAGMENT) {
      tmp = new Artifact(index, "Armor fragment", ArtifactType.DEFENSE);
      tmp.setOneTimeTechBonus(10);
    }
    if (index == ARTIFACT_SHATTERED_DEFLECTOR) {
      tmp = new Artifact(index, "Shattered deflector", ArtifactType.DEFENSE);
      tmp.setOneTimeTechBonus(30);
    }
    return tmp;
  }

}
