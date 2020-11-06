package org.openRealmOfStars.starMap.planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Nuked planet information.
*
*/
public class PlanetNuked {

  /**
   * How many population is killed.
   */
  private int populationKilled;
  /**
   * How many buildings are destroyed.
   */
  private int buildingsDestroyed;
  /**
   * Textual information of planet nuking.
   */
  private String text;

  /**
   * Constructor for planet nuked.
   */
  public PlanetNuked() {
    populationKilled = 0;
    buildingsDestroyed = 0;
    text = null;
  }

  /**
   * Get number killed population.
   * @return the populationKilled
   */
  public int getPopulationKilled() {
    return populationKilled;
  }

  /**
   * Set number of killed population.
   * @param populationKilled the populationKilled to set
   */
  public void setPopulationKilled(final int populationKilled) {
    this.populationKilled = populationKilled;
  }

  /**
   * Get number destroyed buildings.
   * @return the buildingsDestroyed
   */
  public int getBuildingsDestroyed() {
    return buildingsDestroyed;
  }

  /**
   * Set number destroyed buildings.
   * @param buildingsDestroyed the buildingsDestroyed to set
   */
  public void setBuildingsDestroyed(final int buildingsDestroyed) {
    this.buildingsDestroyed = buildingsDestroyed;
  }

  /**
   * Get text about planet nuking.
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Set text about planet nuking.
   * @param text the text to set
   */
  public void setText(final String text) {
    this.text = text;
  }

}
