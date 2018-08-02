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
* Copyright (C) 2017, 2018  Tuomo Untinen
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
   * News data to show
   */
  private ArrayList<NewsData> newsData;

  /**
   * News data to show on next turn
   */
  private ArrayList<NewsData> upComingNewsData;

  /**
   * How many turns when new news is published
   */
  public static final int NEWS_PUBLISH_RATE = 20;
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
   * String for scores stat name
   */
  public static final String STAT_SCORE = "Score";

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
    newsData = new ArrayList<>();
    upComingNewsData = new ArrayList<>();
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
   * Generate player scores as a table.
   * @return Player scores as GalaxyStat table.
   */
  public GalaxyStat generateScores() {
    GalaxyStat result = new GalaxyStat(planets.getMaxPlayers(), "Score");
    for (int player = 0; player < planets.getMaxPlayers(); player++) {
      for (int i = 0; i < planets.getMaxIndex(); i++) {
        int score = 0;
        score = score + military.getValue(player, i);
        score = score + planets.getValue(player, i) * 10;
        score = score + population.getValue(player, i) * 4;
        score = score + cultural.getValue(player, i);
        score = score + credit.getValue(player, i);
        score = score + research.getValue(player, i) * 2;
        result.addStat(player, score);
      }
    }
    return result;
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
   * Calculate how many home planet players have.
   * @param planetList from StarMap
   */
  public void calculateHomePlanets(final ArrayList<Planet> planetList) {
    int[] data = new int[planets.getMaxPlayers()];
    for (Planet planet : planetList) {
      int i = planet.getPlanetOwnerIndex();
      if (planet.getHomeWorldIndex() != -1 && i != -1
          && i < planets.getMaxPlayers()) {
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
    int maxPlayer = playerList.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
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
   * Calculate current military value for player
   * @param player Player whos military to calculate
   * @return Military power
   */
  public static int calculateMilitaryValue(final PlayerInfo player) {
    int militaryValue = 0;
    for (int j = 0; j < player.getFleets().getNumberOfFleets(); j++) {
      Fleet fleet = player.getFleets().getByIndex(j);
      militaryValue = militaryValue + fleet.getMilitaryValue();
    }
    return militaryValue;
  }
  /**
   * Calculate how much total military power players have
   * @param playerList from StarMap
   * @param lastTurn if true then fake military is not calculated
   */
  public void calculateMilitary(final PlayerList playerList,
      final boolean lastTurn) {
    int[] data = new int[military.getMaxPlayers()];
    int maxPlayer = playerList.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
      PlayerInfo player = playerList.getPlayerInfoByIndex(i);
      int militaryValue = calculateMilitaryValue(player);
      if (!lastTurn) {
        data[i] = militaryValue * player.getFakeMilitarySize() / 100;
      } else {
        data[i] = militaryValue;
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
    int maxPlayer = playerList.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
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
    int maxPlayer = playerList.getCurrentMaxRealms();
    int[] data = new int[maxPlayer];
    for (int i = 0; i < maxPlayer; i++) {
      PlayerInfo player = playerList.getPlayerInfoByIndex(i);
      int sum = 0;
      sum = sum + player.getTechList().getTechLevel(TechType.Combat);
      sum = sum + player.getTechList().getTechLevel(TechType.Defense);
      sum = sum + player.getTechList().getTechLevel(TechType.Hulls);
      sum = sum + player.getTechList().getTechLevel(TechType.Improvements);
      sum = sum + player.getTechList().getTechLevel(TechType.Propulsion);
      sum = sum + player.getTechList().getTechLevel(TechType.Electrics);
      sum = sum * 10;
      data[i] = sum / 6;
    }
    for (int i = 0; i < research.getMaxPlayers(); i++) {
      research.addStat(i, data[i]);
    }
  }

  /**
   * Get latest military difference between two players
   * @param index1 First player index. This is where second one is compared.
   * @param index2 Second player index.
   * @return Positive number if first player has bigger military according
   *         the news corp.
   */
  public int getMilitaryDifference(final int index1, final int index2) {
    int first = military.getLatest(index1);
    int second = military.getLatest(index2);
    int difference = first - second;
    return difference;
  }

  /**
   * Is First stats only done. This means that there are two
   * stats in list. First one is always created when game is
   * started.
   * @return True if first stats is done
   */
  public boolean isFirstStats() {
    if (military.getNumberStats() == 2) {
      return true;
    }
    return false;
  }
  /**
   * How many stats has been done.
   * @return Number of stats has been done
   */
  public int getStatNumbers() {
    // Substract one, since one is create at beginning of game.
    return military.getNumberStats() - 1;
  }

  /**
   * Add news into array up coming news array
   * @param news to add
   */
  public void addNews(final NewsData news) {
    upComingNewsData.add(news);
  }

  /**
   * Get the actual news array
   * @return News array
   */
  public NewsData[] getNewsList() {
    return newsData.toArray(new NewsData[newsData.size()]);
  }

  /**
   * Puts upcoming news into current news list and clears the
   * upcoming news list. This should be called just before new turn starts.
   */
  public void clearNewsList() {
    newsData = upComingNewsData;
    upComingNewsData = new ArrayList<>();
  }

  /**
   * Are there news to show?
   * @return True if are, false otherwise
   */
  public boolean isNewsToShow() {
    if (newsData.size() > 0) {
      return true;
    }
    return false;
  }

  /**
   * Get the upcoming news array
   * @return News array
   */
  public NewsData[] getUpcomingNews() {
    return upComingNewsData.toArray(new NewsData[upComingNewsData.size()]);
  }
  /**
   * Is news information available. Either upcoming or current
   * @return Boolean if news information is available.
   */
  public boolean isNewsInformation() {
    if (newsData.size() > 0 || upComingNewsData.size() > 0) {
      return true;
    }
    return false;
  }
}
