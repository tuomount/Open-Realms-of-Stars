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
* Artefact
*
*/
public class Artefact {

  /**
   * Artefact index
   */
  private int index;
  /**
   * Artefact name.
   */
  private String name;
  /**
   * Artefact type.
   */
  private ArtefactType artefactType;
  /**
   * One time tech bonus;
   */
  private int oneTimeTechBonus;
  /**
   * Get Artefact index.
   * @return Artefact index.
   */
  public int getIndex() {
    return index;
  }
  /**
   * Constructor for Artefact
   * @param index Artefact index
   * @param name Artefact name
   * @param type Artefact type
   */
  public Artefact(final int index, final String name,
      final ArtefactType type) {
    this.index = index;
    this.name = name;
    this.artefactType = type;
  }
  /**
   * Get Artefact name.
   * @return String
   */
  public String getName() {
    return name;
  }
  /**
   * Get Artefact type
   * @return Artefact type
   */
  public ArtefactType getArtefactType() {
    return artefactType;
  }
  /**
   * Get Artefact one time bonus for research
   * @return Research bonus
   */
  public int getOneTimeTechBonus() {
    return oneTimeTechBonus;
  }
  /**
   * Set one time research bonus.
   * @param oneTimeTechBonus
   */
  public void setOneTimeTechBonus(final int oneTimeTechBonus) {
    this.oneTimeTechBonus = oneTimeTechBonus;
  }
}
