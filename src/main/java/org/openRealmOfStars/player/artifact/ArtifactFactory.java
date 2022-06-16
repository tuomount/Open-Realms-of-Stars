package org.openRealmOfStars.player.artifact;

import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.utilities.DiceGenerator;

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
  private static final int MAX_ARTIFACT = 12;

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
   * Artifact Hull fragment
   */
  public static final int ARTIFACT_HULL_FRAGMENT = 4;
  /**
   * Artifact Shattered hull
   */
  public static final int ARTIFACT_SHATTERED_HULL = 5;
  /**
   * Artifact Facility fragment
   */
  public static final int ARTIFACT_FACILITY_FRAGMENT = 6;
  /**
   * Artifact Facility fragment
   */
  public static final int ARTIFACT_SHATTERED_FACILITY = 7;
  /**
   * Artifact Powersource fragment
   */
  public static final int ARTIFACT_POWERSOURCE_FRAGMENT = 8;
  /**
   * Artifact Shattered Powersource
   */
  public static final int ARTIFACT_SHATTERED_POWERSOURCE = 9;
  /**
   * Artifact Electronic fragment
   */
  public static final int ARTIFACT_ELECTRONIC_FRAGMENT = 10;
  /**
   * Artifact Shattered Electronic
   */
  public static final int ARTIFACT_SHATTERED_ELECTRONIC = 11;

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
      tmp.setDescription("This artifact is small piece of ancient space"
          + " weapon. It is very difficult to say what weapons was"
          + " originally but you can get material ideas for it.");
    }
    if (index == ARTIFACT_SHATTERED_WEAPON) {
      tmp = new Artifact(index, "Shattered weapon", ArtifactType.MILITARY);
      tmp.setOneTimeTechBonus(30);
      tmp.setDescription("This artifact is ancient space weapon"
          + " which has been broken into pieces. Main idea how it works"
          + " and how it was used is possible to understand with"
          + " extensive research.");
      tmp.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_MILITARY));
    }
    if (index == ARTIFACT_ARMOR_FRAGMENT) {
      tmp = new Artifact(index, "Armor fragment", ArtifactType.DEFENSE);
      tmp.setOneTimeTechBonus(10);
      tmp.setDescription("This artifact is small piece of ancient space"
          + " armor. This piece give idea what was the material base "
          + "for armor and possible design elements.");
    }
    if (index == ARTIFACT_SHATTERED_DEFLECTOR) {
      tmp = new Artifact(index, "Shattered deflector", ArtifactType.DEFENSE);
      tmp.setOneTimeTechBonus(30);
      tmp.setDescription("This artifact is ancient space deflektor."
          + " Main function how it works and also smaller details"
          + "is able to solve by through studies.");
      tmp.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_DEFENSE));
    }
    if (index == ARTIFACT_HULL_FRAGMENT) {
      tmp = new Artifact(index, "Hull fragment", ArtifactType.SHIPHULL);
      tmp.setOneTimeTechBonus(10);
      tmp.setDescription("This artifact is small piece of ancient space"
          + " hull. This piece give idea what was the material for hull"
          + " and possible design elements and layouts.");
    }
    if (index == ARTIFACT_SHATTERED_HULL) {
      tmp = new Artifact(index, "Shattered hull", ArtifactType.SHIPHULL);
      tmp.setOneTimeTechBonus(30);
      tmp.setDescription("This artifact is ancient space hull."
          + " Main purpose and design choices made can be discovered"
          + " by major research work.");
      tmp.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_HULL));
    }
    if (index == ARTIFACT_FACILITY_FRAGMENT) {
      tmp = new Artifact(index, "Facility fragment", ArtifactType.FACILITY);
      tmp.setOneTimeTechBonus(10);
      tmp.setDescription("This artifact is small piece of ancient"
          + " facility. This piece give idea what was the material for"
          + " facility and based on that scientists can guess what was"
          + " the purpose of the facility.");
    }
    if (index == ARTIFACT_SHATTERED_FACILITY) {
      tmp = new Artifact(index, "Shattered facility", ArtifactType.FACILITY);
      tmp.setOneTimeTechBonus(30);
      tmp.setDescription("This artifact is ancient facility."
          + " These pieces give idea what was the material for"
          + " facility and based on that scientists can guess what was"
          + " the purpose of the facility.");
      tmp.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_FACILITY));
    }
    if (index == ARTIFACT_POWERSOURCE_FRAGMENT) {
      tmp = new Artifact(index, "Powersource fragment", ArtifactType.ENERGY);
      tmp.setOneTimeTechBonus(10);
      tmp.setDescription("This artifact is small piece of ancient space"
          + " powersource. This piece give idea what was the material for "
          + "powersource and theoretical power output capabilities.");
    }
    if (index == ARTIFACT_SHATTERED_POWERSOURCE) {
      tmp = new Artifact(index, "Shattered powersource", ArtifactType.ENERGY);
      tmp.setOneTimeTechBonus(30);
      tmp.setDescription("This artifact is ancient powersource."
          + " This broken powersource give good insight how"
          + " power output worked and how much energy could be"
          + " drawn from it.");
      tmp.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_ENERGY));
    }
    if (index == ARTIFACT_ELECTRONIC_FRAGMENT) {
      tmp = new Artifact(index, "Electronic fragment",
          ArtifactType.ELECTRONIC);
      tmp.setOneTimeTechBonus(10);
      tmp.setDescription("This artifact is small piece of ancient space"
          + " electronic. This piece give idea what was the material for "
          + "electronic and theoretical functions of it.");
    }
    if (index == ARTIFACT_SHATTERED_ELECTRONIC) {
      tmp = new Artifact(index, "Shattered electronic",
          ArtifactType.ELECTRONIC);
      tmp.setOneTimeTechBonus(30);
      tmp.setDescription("This artifact is ancient space electronic."
          + " This broken electronic tells much about it's designers"
          + " and how this thing could have worked and where it has been"
          + " used for.");
      tmp.setIcon(Icons.getIconByName(Icons.ICON_ANCIENT_ELECTRONIC));
    }
    return tmp;
  }

  /**
   * Get random non facility artifact.
   * @return Random artifact
   */
  public static Artifact getRandomNonFacility() {
    ArrayList<Artifact> choices = new ArrayList<>();
    for (int i = 0; i < MAX_ARTIFACT; i++) {
      Artifact artifact = createArtifact(i);
      if (artifact.getArtifactType() != ArtifactType.FACILITY) {
        choices.add(artifact);
      }
    }
    int index = DiceGenerator.getRandom(choices.size() - 1);
    return choices.get(index);
  }

  /**
   * Get random artifact.
   * @return Random artifact
   */
  public static Artifact getRandomArtifact() {
    int index = DiceGenerator.getRandom(MAX_ARTIFACT - 1);
    return createArtifact(index);
  }

  /**
   * Get artifact level cost as research points
   * @param level Level to research
   * @param gameLength Maximum game length in turns
   * @return Amount of research points required
   */
  public static int getResearchCost(final int level, final int gameLength) {
    int veryLowBonus = 0;
    int lowBonus = 0;
    int mediumBonus = 0;
    int highBonus = 0;
    if (gameLength <= 200) {
      veryLowBonus = -2;
      lowBonus = -4;
      mediumBonus = -8;
      highBonus = -12;
    } else if (gameLength <= 300) {
      veryLowBonus = 0;
      lowBonus = 0;
      mediumBonus = 0;
      highBonus = 0;
    } else if (gameLength <= 400) {
      veryLowBonus = 0;
      lowBonus = 2;
      mediumBonus = 4;
      highBonus = 8;
    } else if (gameLength <= 600) {
      veryLowBonus = 2;
      lowBonus = 4;
      mediumBonus = 8;
      highBonus = 16;
    } else if (gameLength <= 800) {
      veryLowBonus = 3;
      lowBonus = 6;
      mediumBonus = 9;
      highBonus = 18;
    } else if (gameLength <= 1000) {
      veryLowBonus = 4;
      lowBonus = 8;
      mediumBonus = 12;
      highBonus = 24;
    }
    switch (level) {
      case 0:
        return 20;
      case 1:
        return 22;
      case 2:
        return 26 + veryLowBonus;
      case 3:
        return 28 + veryLowBonus;
      case 4:
        return 32 + lowBonus;
      case 5:
        return 34 + lowBonus;
      case 6:
        return 38 + mediumBonus;
      case 7:
        return 40 + mediumBonus;
      case 8:
        return 44 + highBonus;
      case 9:
        return 46 + highBonus;
      default:
        return 50 + highBonus;
    }
  }
}