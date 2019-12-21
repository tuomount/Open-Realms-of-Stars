package org.openRealmOfStars.starMap;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.government.GovernmentUtility;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2018,2019  Tuomo Untinen
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
 * Galaxy config for creating new star map with player
 *
 */
public class GalaxyConfig {

  /**
   * Galaxy size X coordinates
   */
  private int sizeX;
  /**
   * Galaxy size Y coordinates
   */
  private int sizeY;

  /**
   * Galaxy size index in UI
   */
  private int galaxySizeIndex;

  /**
   * How many players there are
   */
  private int maxPlayers;

  /**
   * How far away solar systems are from each others
   */
  private int solarSystemDistance;

  /**
   * Sun density index in UI
   */
  private int sunDensityIndex;

  /**
   * Players start from the map border
   */
  public static final int START_POSITION_BORDER = 0;

  /**
   * Number of rogue planet none
   */
  public static final int ROGUE_PLANETS_NONE = 0;
  /**
   * Number of rogue planet very few
   */
  public static final int ROGUE_PLANETS_VERY_FEW = 2;
  /**
   * Number of rogue planet few
   */
  public static final int ROGUE_PLANETS_FEW = 4;
  /**
   * Number of rogue planet some
   */
  public static final int ROGUE_PLANETS_SOME = 6;
  /**
   * Number of rogue planet many
   */
  public static final int ROGUE_PLANETS_MANY = 8;

  /**
   * Players start from random position
   */
  public static final int START_POSITION_RANDOM = 1;

  /**
   * Where players start
   */
  private int startingPosition;

  /**
   * Player races
   */
  private SpaceRace[] playerRaces;

  /**
   * Player name
   */
  private String[] playerName;

  /**
   * Player government
   */
  private GovernmentType[] playerGovernment;
  /**
   * Player ancient realm.
   */
  private boolean[] playerAncientRealm;

  /**
   * Chance for planetary event
   */
  private int chanceForPlanetaryEvent;

  /**
   * Number of Rogue planets
   */
  private int numberOfRoguePlanets;

  /**
   * How many turns to get scoring victory
   */
  private int scoringVictoryTurns;
  /**
   * Scoring by culture
   * -1 Disabled victory by culture
   * 0 75% of regular culture score limit
   * 1 100% of regular culture score limit
   * 2 150% of regular culture score limit
   * 3 200% of regular culture score limit
   */
  private int scoringCulture;

  /**
   * Scoring by Conquer
   * 0 Disabled victory by conquer
   * 1 Enabled victory by conquer
   */
  private int scoringConquer;

  /**
   * Scoring by Research
   * 0 Disabled victory by research
   * NOTE THIS IS NOT IMPLEMENTED YET;
   * Just reserving 4 bytes to save games
   */
  private int scoringResearch;

  /**
   * Scoring by Diplomacy
   * 0 Disabled victory by diplomacy
   * NOTE THIS IS NOT IMPLEMENTED YET;
   * Just reserving 4 bytes to save games
   */
  private int scoringDiplomacy;

  /**
   * Space pirates level
   * 0 Disabled
   * 1 Very rare 10%
   * 2 Rare 20%
   * 3 Few 40%
   * 4 Common 60%
   * 5 Very common 80%
   * 6 All over 100%
   */
  private int spacePiratesLevel;

  /**
   * Space pirates difficulty level.
   */
  private PirateDifficultLevel spacePiratesDifficulty;

  /**
   * Karma type for galaxy.
   */
  private KarmaType karmaType;

  /**
   * Karma speed for galaxy.
   */
  private int karmaSpeed;

  /**
   * Space anomaly level
   * 0 Disabled
   * 1 Non-harmful
   * 2 All
   */
  private int spaceAnomaliesLevel;

  /**
   * How many turns ancient realms player before others start.
   */
  private int ancientHeadStart;

  /**
   * Flag for enabled tutorial
   */
  private boolean enableTutorial;
/**
   * Constructor for galaxy config
   */
  public GalaxyConfig() {
    sizeX = 75;
    sizeY = 75;
    this.galaxySizeIndex = 1;
    this.spacePiratesLevel = 1;
    this.setSpacePiratesDifficulty(PirateDifficultLevel.NORMAL);
    this.spaceAnomaliesLevel = 1;
    this.setKarmaType(KarmaType.SECOND_FIRST_AND_LAST);
    this.setKarmaSpeed(2);
    setChanceForPlanetaryEvent(10);
    setNumberOfRoguePlanets(ROGUE_PLANETS_FEW);
    setScoringVictoryTurns(400);
    setScoreLimitCulture(1);
    setScoreLimitConquer(1);
    setScoreLimitResearch(2);
    setScoreLimitDiplomacy(2);
    setMaxPlayers(4);
    setAncientHeadStart(80);
    setSolarSystemDistance(12, 0);
    playerRaces = new SpaceRace[StarMap.MAX_PLAYERS];
    playerName = new String[StarMap.MAX_PLAYERS];
    playerGovernment = new GovernmentType[StarMap.MAX_PLAYERS];
    playerAncientRealm = new boolean[StarMap.MAX_PLAYERS];
    setEnableTutorial(true);
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {

      setRace(i, SpaceRaceUtility.getRandomRace());
      while (true) {
        GovernmentType gov = GovernmentUtility.getRandomGovernment(getRace(i));
        setPlayerGovernment(i, gov);
        String tmp = SpaceRaceUtility.getRandomName(getRace(i),
            getPlayerGovernment(i));
        if (isUniqueName(tmp)) {
          setPlayerName(i, tmp);
          break;
        }
      }
    }
  }

  /**
   * Is player name unique
   * @param name Unique name
   * @return True if unique
   */
  public boolean isUniqueName(final String name) {
    for (int i = 0; i < StarMap.MAX_PLAYERS; i++) {
      if (playerName[i] != null && name.equals(playerName[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Set race for certain player
   * @param index Player index
   * @param race Space race to set
   */
  public void setRace(final int index, final SpaceRace race) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      playerRaces[index] = race;
    }
  }

  /**
   * Get Space race for certain player
   * @param index Player index
   * @return SpaceRace for player
   */
  public SpaceRace getRace(final int index) {
    if (index >= 0
        && index < StarMap.MAX_PLAYERS) {
      return playerRaces[index];
    }
    return null;
  }

  /**
   * Set Player empire name
   * @param index Player Index
   * @param name Empire name
   */
  public void setPlayerName(final int index, final String name) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      playerName[index] = name;
    }
  }

  /**
   * Get player empire name
   * @param index Player index
   * @return Empire name
   */
  public String getPlayerName(final int index) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      return playerName[index];
    }
    return null;
  }

  /**
   * Set Realm Government type
   * @param index Player index
   * @param government Government type
   */
  public void setPlayerGovernment(final int index,
      final GovernmentType government) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      playerGovernment[index] = government;
    }
  }
  /**
   * Get Realm Government type
   * @param index Player index
   * @return GovernmentType
   */
  public GovernmentType getPlayerGovernment(final int index) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      return playerGovernment[index];
    }
    return null;
  }

  /**
   * Set Ancient realm for player
   * @param index Player Index
   * @param ancientRealm Ancient realm flag
   */
  public void setPlayerAncientRealm(final int index,
      final boolean ancientRealm) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      playerAncientRealm[index] = ancientRealm;
    }
  }

  /**
   * Get Ancient realm for player
   * @param index Player index
   * @return True for ancient realm
   */
  public boolean getPlayerAncientRealm(final int index) {
    if (index >= 0 && index < StarMap.MAX_PLAYERS) {
      return playerAncientRealm[index];
    }
    return false;
  }
  /**
   * Set galaxy size. Galaxy is set for square size
   * @param size side length
   * @param galaxySize Galaxy size index in UI
   */
  public void setSize(final int size, final int galaxySize) {
    this.sizeX = size;
    this.sizeY = size;
    this.galaxySizeIndex = galaxySize;
  }

  /**
   * Get galaxy X size
   * @return Get galaxy X size
   */
  public int getSizeX() {
    return sizeX;
  }

  /**
   * Get galaxy Y size
   * @return Get galaxy Y size
   */
  public int getSizeY() {
    return sizeY;
  }

  /**
   * Get maximum players
   * @return Maximum players
   */
  public int getMaxPlayers() {
    return maxPlayers;
  }

  /**
   * Set maximum amount of players for galaxy
   * @param maxPlayers for one game
   */
  public void setMaxPlayers(final int maxPlayers) {
    if (maxPlayers >= 2 && maxPlayers <= 8) {
      this.maxPlayers = maxPlayers;
    }
  }

  /**
   * Get solary system distance
   * @return Minimum distance between solar systems
   */
  public int getSolarSystemDistance() {
    return solarSystemDistance;
  }

  /**
   * Set Solar system distance
   * @param systemDistance Actual distance between solar system
   * @param sunDensity Sun density setting in ui index
   */
  public void setSolarSystemDistance(final int systemDistance,
      final int sunDensity) {
    this.solarSystemDistance = systemDistance;
    this.sunDensityIndex = sunDensity;
  }

  /**
   * get starting position information
   * @return Starting position information
   */
  public int getStartingPosition() {
    return startingPosition;
  }

  /**
   * Set starting position. There are two choices:
   * Border and Random
   * @param startingPosition see START_POSITION_BORDER and
   *  START_POSITION_RANDOM
   */
  public void setStartingPosition(final int startingPosition) {
    this.startingPosition = startingPosition;
  }

  /**
   * Which sun density is selected from UI
   * @return Sun density index
   */
  public int getSunDensityIndex() {
    return sunDensityIndex;
  }

  /**
   * Get Galaxy size inde from ui
   * @return Galaxy size index
   */
  public int getGalaxySizeIndex() {
    return galaxySizeIndex;
  }

  /**
   * Get the chance for planetary event. Default value is 10.
   * @return the chance For PlanetaryEvent
   */
  public int getChanceForPlanetaryEvent() {
    return chanceForPlanetaryEvent;
  }

  /**
   * Set chance for planetary event. If chance 0 then
   * there won't be any planetary events. If given value is not in
   * range then it its truncated to the range.
   * @param chance Chance for planetary event between 0-100%
   */
  public void setChanceForPlanetaryEvent(final int chance) {
    if (chance < 0) {
      chanceForPlanetaryEvent = 0;
    } else if (chance > 99) {
      chanceForPlanetaryEvent = 99;
    } else {
      chanceForPlanetaryEvent = chance;
    }
  }

  /**
   * Get game playing time before scoring is done
   * for victory
   * @return the scoringVictoryTurns
   */
  public int getScoringVictoryTurns() {
    return scoringVictoryTurns;
  }

  /**
   * Set game playing time in turns. After last turn
   * scoring is done. Maximum playing time is 1000 turns and
   * minimum is 200 turns.
   * @param scoringVictoryTurns the scoringVictoryTurns to set
   */
  public void setScoringVictoryTurns(final int scoringVictoryTurns) {
    if (scoringVictoryTurns > 1000) {
      this.scoringVictoryTurns = 1000;
    } else if (scoringVictoryTurns < 200) {
      this.scoringVictoryTurns = 200;
    } else {
      this.scoringVictoryTurns = scoringVictoryTurns;
    }
  }

  /**
   * Get Culture score limits.
   * -1 Disabled victory by culture
   * 0 75% of regular culture score limit
   * 1 100% of regular culture score limit
   * 2 150% of regular culture score limit
   * 3 200% of regular culture score limit
   * @return Culture score limit
   */
  public int getScoreLimitCulture() {
    return scoringCulture;
  }

  /**
   * Set culture score limit
   * @param limit Score limit
   *        -1 Disabled victory by culture
   *        0 75% of regular culture score limit
   *        1 100% of regular culture score limit
   *        2 150% of regular culture score limit
   *        3 200% of regular culture score limit
   */
  public void setScoreLimitCulture(final int limit) {
    if (limit >= -1 && limit <= 3) {
      scoringCulture = limit;
    }
  }

  /**
   * Get Score limit for conquer
   * @return 1 for enabled, 0 for disabled
   */
  public int getScoreLimitConquer() {
    return scoringConquer;
  }

  /**
   * Set score limit for conquer
   * @param limit 1 For enabling, 0 for disabling
   */
  public void setScoreLimitConquer(final int limit) {
    if (limit == 0 || limit == 1) {
      scoringConquer = limit;
    }
  }

  /**
   * Get Scoring limit for research
   * @return How many future techs must be research to win.
   */
  public int getScoreLimitResearch() {
    return scoringResearch;
  }

  /**
   * Set Scoring limit for research. Scientific achievements are
   * expensive building projects. To win with these certain number
   * must be built on same planet.
   * @param limit How many scientific achievements must be built to win
   */
  public void setScoreLimitResearch(final int limit) {
    if (limit >= 0 && limit <= 5) {
      scoringResearch = limit;
    }
  }

  /**
   * Get Scoring limit for diplomacy
   * @return Scoring limit for diplomacy
   */
  public int getScoreLimitDiplomacy() {
    return scoringDiplomacy;
  }

  /**
   * Set scoring limit for diplomacy.
   * @param limit Limit for diplomacy victory condition
   *              0 - Diplomacy victory disabled
   *              1 - 2 diplomatic votes
   *              2 - 3 diplomatic votes
   *              3 - 4 diplomatic votes
   *              4 - 5 diplomatic votes
   *              5 - 6 diplomatic votes
   */
  public void setScoreLimitDiplomacy(final int limit) {
    if (limit >= 0 && limit <= 5) {
     scoringDiplomacy = limit;
    }
  }
  /**
   * Get the number of rogue planets. This number is then
   * multiplied by galaxy size.
   * @return the numberOfRoguePlanets
   */
  public int getNumberOfRoguePlanets() {
    return numberOfRoguePlanets;
  }

  /**
   * Set the number of rogue planets.
   * @param numberOfRoguePlanets the numberOfRoguePlanets to set
   */
  public void setNumberOfRoguePlanets(final int numberOfRoguePlanets) {
    this.numberOfRoguePlanets = numberOfRoguePlanets;
  }

  /**
   * Set space pirates activity level
   * @param value 0 to disable Up to 6
   */
  public void setSpacePiratesLevel(final int value) {
    if (value >= 0 && value <= 6) {
      this.spacePiratesLevel = value;
    }
  }

  /**
   * Get space pirates activity level
   * @return Space pirates activity level
   */
  public int getSpacePiratesLevel() {
    return this.spacePiratesLevel;
  }

  /**
   * Get Space pirate difficulty level.
   * @return the spacePiratesDifficulty
   */
  public PirateDifficultLevel getSpacePiratesDifficulty() {
    return spacePiratesDifficulty;
  }

  /**
   * Set Space pirate difficulty level.
   * @param difficult the spacePiratesDifficulty to set
   */
  public void setSpacePiratesDifficulty(final PirateDifficultLevel difficult) {
    this.spacePiratesDifficulty = difficult;
  }

  /**
   * Set space anomalies level
   * @param value 0 to disable Up to 2
   */
  public void setSpaceAnomaliesLevel(final int value) {
    if (value >= 0 && value <= 2) {
      this.spaceAnomaliesLevel = value;
    }
  }

  /**
   * Get space anomalies level
   * @return Space anomalies level
   */
  public int getSpaceAnomaliesLevel() {
    return this.spaceAnomaliesLevel;
  }

  /**
   * Get the karma type for galaxy.
   * @return the karmaType
   */
  public KarmaType getKarmaType() {
    return karmaType;
  }

  /**
   * Set the karma type for galaxy.
   * @param karmaType the karmaType to set
   */
  public void setKarmaType(final KarmaType karmaType) {
    this.karmaType = karmaType;
  }

  /**
   * Get the karma speed for galaxy
   * @return the karmaSpeed
   */
  public int getKarmaSpeed() {
    return karmaSpeed;
  }

  /**
   * Set Karma speed for galaxy.
   * @param karmaSpeed the karmaSpeed to set
   */
  public void setKarmaSpeed(final int karmaSpeed) {
    this.karmaSpeed = karmaSpeed;
  }

  /**
   * Get Ancient realm head start
   * @return Ancient realm head start in turns.
   */
  public int getAncientHeadStart() {
    return ancientHeadStart;
  }

  /**
   * Set ancient realm head start
   * @param ancientHeadStart in turns
   */
  public void setAncientHeadStart(final int ancientHeadStart) {
    this.ancientHeadStart = ancientHeadStart;
  }

  /**
   * Is tutorial enabled
   * @return the enableTutorial
   */
  public boolean isEnableTutorial() {
    return enableTutorial;
  }

  /**
   * Set if tutorial is enabled.
   * @param enableTutorial the enableTutorial to set
   */
  public void setEnableTutorial(final boolean enableTutorial) {
    this.enableTutorial = enableTutorial;
  }

}
