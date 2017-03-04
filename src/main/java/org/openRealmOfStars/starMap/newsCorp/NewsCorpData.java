package org.openRealmOfStars.starMap.newsCorp;

import java.util.ArrayList;

import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* News Corporation Data
*
*/
public class NewsCorpData {

  /**
   * How many planets players have
   */
  private GalaxyStat planets;

  /**
   * How much population players have
   */
  private GalaxyStat population;

  /**
   * How much research players have
   */
  private GalaxyStat research;

  /**
   * How much military power players have
   */
  private GalaxyStat military;

  /**
   * How much cultural power players have
   */
  private GalaxyStat cultural;

  /**
   * How much credit players have
   */
  private GalaxyStat credit;

  /**
   * String for planetary stat name
   */
  public static final String STAT_PLANETS = "Planets";
  /**
   * String for population stat name
   */
  public static final String STAT_POPULATION = "Population";
  /**
   * String for military stat name
   */
  public static final String STAT_MILITARY = "Military power";
  /**
   * String for cultural stat name
   */
  public static final String STAT_CULTURAL = "Cultural power";
  /**
   * String for credits stat name
   */
  public static final String STAT_CREDIT = "Credits";
  /**
   * String for research stat name
   */
  public static final String STAT_RESEARCH = "Research";

  /**
   * Create new NewsCorpData. This will initialize all
   * the data as empty
   * @param numberOfPlayers Number of players in game when game started.
   */
  public NewsCorpData(final int numberOfPlayers) {
    planets = new GalaxyStat(numberOfPlayers, STAT_PLANETS);
    population = new GalaxyStat(numberOfPlayers, STAT_POPULATION);
    military = new GalaxyStat(numberOfPlayers, STAT_MILITARY);
    cultural = new GalaxyStat(numberOfPlayers, STAT_CULTURAL);
    credit = new GalaxyStat(numberOfPlayers, STAT_CREDIT);
    research = new GalaxyStat(numberOfPlayers, STAT_RESEARCH);
  }

  /**
   * Get planets stats
   * @return Planet stats
   */
  public GalaxyStat getPlanets() {
    return planets;
  }

  /**
   * Get population stats
   * @return Population stats
   */
  public GalaxyStat getPopulation() {
    return population;
  }

  /**
   * Get research stats
   * @return research stats
   */
  public GalaxyStat getResearch() {
    return research;
  }

  /**
   * Get military power stats
   * @return military power stats
   */
  public GalaxyStat getMilitary() {
    return military;
  }

  /**
   * Get cultural power stats
   * @return Cultural power stats
   */
  public GalaxyStat getCultural() {
    return cultural;
  }

  /**
   * Get credits stats
   * @return credits stats
   */
  public GalaxyStat getCredit() {
    return credit;
  }

  /**
   * Calculate how many planets players have
   * @param planetList from StarMap
   */
  public void calculatePlanets(final ArrayList<Planet> planetList) {
    int[] data = new int[planets.getMaxPlayers()];
    for (Planet planet : planetList) {
      int i = planet.getPlanetOwnerIndex();
      if (i != -1 && i < planets.getMaxPlayers()) {
        data[i] = data[i] + 1;
      }
    }
    for (int i = 0; i < planets.getMaxPlayers(); i++) {
      planets.addStat(i, data[i]);
    }
  }

  /**
   * Calculate how much population players have
   * @param planetList from StarMap
   */
  public void calculatePopulation(final ArrayList<Planet> planetList) {
    int[] data = new int[planets.getMaxPlayers()];
    for (Planet planet : planetList) {
      int i = planet.getPlanetOwnerIndex();
      if (i != -1 && i < planets.getMaxPlayers()) {
        data[i] = data[i] + planet.getTotalPopulation();
      }
    }
    for (int i = 0; i < planets.getMaxPlayers(); i++) {
      planets.addStat(i, data[i]);
    }
  }

}
