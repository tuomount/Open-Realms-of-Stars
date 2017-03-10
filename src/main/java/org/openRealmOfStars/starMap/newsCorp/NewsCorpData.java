package org.openRealmOfStars.starMap.newsCorp;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.TechType;
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
   * Calculate how much culture players have
   * @param planetList from StarMap
   * @param playerList from StarMap
   */
  public void calculateCulture(final ArrayList<Planet> planetList,
      final PlayerList playerList) {
    int[] data = new int[cultural.getMaxPlayers()];
    for (Planet planet : planetList) {
      int i = planet.getPlanetOwnerIndex();
      if (i != -1 && i < cultural.getMaxPlayers()) {
        data[i] = data[i] + planet.getCulture();
      }
    }
    for (int i = 0; i < playerList.getCurrentMaxPlayers(); i++) {
      PlayerInfo player = playerList.getPlayerInfoByIndex(i);
      for (int j = 0; j < player.getFleets().getNumberOfFleets(); j++) {
        Fleet fleet = player.getFleets().getByIndex(j);
        data[i] = data[i] + fleet.getCulturalValue();
      }
    }
    for (int i = 0; i < cultural.getMaxPlayers(); i++) {
      cultural.addStat(i, data[i]);
    }
  }

  /**
   * Calculate how much population players have
   * @param planetList from StarMap
   */
  public void calculatePopulation(final ArrayList<Planet> planetList) {
    int[] data = new int[population.getMaxPlayers()];
    for (Planet planet : planetList) {
      int i = planet.getPlanetOwnerIndex();
      if (i != -1 && i < population.getMaxPlayers()) {
        data[i] = data[i] + planet.getTotalPopulation();
      }
    }
    for (int i = 0; i < population.getMaxPlayers(); i++) {
      population.addStat(i, data[i]);
    }
  }

  /**
   * Calculate how much total military power players have
   * @param playerList from StarMap
   */
  public void calculateMilitary(final PlayerList playerList) {
    int[] data = new int[military.getMaxPlayers()];
    for (int i = 0; i < playerList.getCurrentMaxPlayers(); i++) {
      PlayerInfo player = playerList.getPlayerInfoByIndex(i);
      for (int j = 0; j < player.getFleets().getNumberOfFleets(); j++) {
        Fleet fleet = player.getFleets().getByIndex(j);
        data[i] = data[i] + fleet.getMilitaryValue();
      }
    }
    for (int i = 0; i < military.getMaxPlayers(); i++) {
      military.addStat(i, data[i]);
    }
  }

  /**
   * Calculate how much credits players have
   * @param playerList from StarMap
   */
  public void calculateCredit(final PlayerList playerList) {
    int[] data = new int[credit.getMaxPlayers()];
    for (int i = 0; i < playerList.getCurrentMaxPlayers(); i++) {
      PlayerInfo player = playerList.getPlayerInfoByIndex(i);
      data[i] = player.getTotalCredits();
    }
    for (int i = 0; i < credit.getMaxPlayers(); i++) {
      credit.addStat(i, data[i]);
    }
  }

  /**
   * Calculate how much research players have
   * @param playerList from StarMap
   */
  public void calculateResearch(final PlayerList playerList) {
    int[] data = new int[research.getMaxPlayers()];
    for (int i = 0; i < playerList.getCurrentMaxPlayers(); i++) {
      PlayerInfo player = playerList.getPlayerInfoByIndex(i);
      int sum = 0;
      sum = sum + player.getTechList().getTechLevel(TechType.Combat);
      sum = sum + player.getTechList().getTechLevel(TechType.Defense);
      sum = sum + player.getTechList().getTechLevel(TechType.Hulls);
      sum = sum + player.getTechList().getTechLevel(TechType.Improvements);
      sum = sum + player.getTechList().getTechLevel(TechType.Propulsion);
      sum = sum + player.getTechList().getTechLevel(TechType.Electrics);
      sum = sum * 10;
      data[i] = Math.round(sum / 6);
    }
    for (int i = 0; i < research.getMaxPlayers(); i++) {
      research.addStat(i, data[i]);
    }
  }

}
