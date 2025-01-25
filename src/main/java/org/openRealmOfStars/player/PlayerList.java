package org.openRealmOfStars.player;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.player.diplomacy.DiplomacyBonus;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.government.Government;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.government.GovernmentUtility;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.scenario.ScenarioIds;
import org.openRealmOfStars.player.scenario.StartingScenario;
import org.openRealmOfStars.player.scenario.StartingScenarioFactory;
import org.openRealmOfStars.player.scenario.StartingScenarioType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.PirateDifficultLevel;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * List of PLayer info
 */
public class PlayerList {

  /**
   * List of players
   */
  private ArrayList<PlayerInfo> list;

  /**
   * Current player
   */
  private int currentPlayer;

  /**
   * Create a new PlayerList
   */
  public PlayerList() {
    list = new ArrayList<>();
    currentPlayer = 0;
  }

  /**
   * Call reInit for all players
   */
  public void reInit() {
    for (PlayerInfo info : list) {
      info.reInit();
    }
  }

  /**
   * Read PlayerList from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public PlayerList(final DataInputStream dis) throws IOException {
    currentPlayer = dis.readInt();
    int count = dis.readInt();
    list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      list.add(new PlayerInfo(dis));
    }
  }

  /**
   * Get possible starting scenarios when using fixed scenario list.
   * @param race Space Race
   * @param scenarioList Scenarios available.
   * @return Array of starting scenarios
   */
  public static StartingScenario[] getPossibleStartingScenarios(
      final SpaceRace race, final StartingScenario[] scenarioList) {
    ArrayList<StartingScenario> scenarios = new ArrayList<>();
    PlayerInfo info = new PlayerInfo(race);
    for (StartingScenario scenario : scenarioList) {
      info.setStartingScenario(scenario);
      if (scenario.getType() == StartingScenarioType.NO_HOME) {
        // No home scenarios are always suitable
        scenarios.add(scenario);
      } else {
        Planet planet = new Planet(new Coordinate(5, 5), "TempPlanet",
            0, false);
        planet.setRadiationLevel(RadiationType.NO_RADIATION);
        planet.setTemperatureType(scenario.getTemperature());
        planet.setWaterLevel(scenario.getWaterLevel());
        planet.setGroundSize(scenario.getPlanetSize());
        planet.generateGravityBasedOnSize();
        if (scenario.getId().equals(ScenarioIds.METAL_PLANET)) {
          planet.setPlanetType(PlanetTypes.ARTIFICIALWORLD1);
        } else {
          planet.generateWorldType();
        }
        int value = info.getPlanetSuitabilityValue(planet);
        if (race.hasTrait(TraitIds.PHOTOSYNTHESIS)
            && scenario.getWaterLevel() == WaterLevelType.DESERT
            || scenario.getWaterLevel() == WaterLevelType.BARREN) {
          value = 0;
        }
        if (race.hasTrait(TraitIds.ZERO_GRAVITY_BEING) && value > 0) {
          // Zero gravity being lives on orbitals.
          value = 100;
        }
        if (value >= 75) {
          scenarios.add(scenario);
        }
      }
    }
    return scenarios.toArray(new StartingScenario[0]);
  }
  /**
   * Get possible starting scenarios when using random scenario.
   * @param race Space Race
   * @return Array of starting scenarios
   */
  public static StartingScenario[] getPossibleStartingScenarios(
      final SpaceRace race) {
    return getPossibleStartingScenarios(race,
        StartingScenarioFactory.getValues());
  }
  /**
   * Creates PlayerList based on galaxy config
   * @param galaxyConfig Galaxy Config
   * @return PlayerList
   */
  public static PlayerList createPlayerList(final GalaxyConfig galaxyConfig) {
    PlayerList players = new PlayerList();
    int boardIndex = -1;
    int maxPlayers = galaxyConfig.getMaxPlayers();
    if (galaxyConfig.getSpacePiratesLevel() > 0) {
      maxPlayers++;
      boardIndex = galaxyConfig.getMaxPlayers();
    }
    if (galaxyConfig.getSpaceAnomaliesLevel() == 2) {
      maxPlayers++;
      if (boardIndex == -1) {
        boardIndex = galaxyConfig.getMaxPlayers();
      }
    }
    ArrayList<PlayerColor> randomListOfColors = new ArrayList<>();
    for (PlayerColor color : PlayerColor.values()) {
      randomListOfColors.add(color);
    }
    for (int i = 0; i < galaxyConfig.getMaxPlayers(); i++) {
      StartingScenario scenario = galaxyConfig.getStartingScenario(i);
      if (scenario.getId().equals(StartingScenarioFactory.RANDOM_ID)) {
        scenario = StartingScenarioFactory.pickRandomScenario();
      }
      PlayerInfo info = new PlayerInfo(galaxyConfig.getRace(i),
          maxPlayers, i, boardIndex, scenario);
      info.setGovernment(galaxyConfig.getPlayerGovernment(i));
      info.setEmpireName(galaxyConfig.getPlayerName(i));
      info.setElderRealm(galaxyConfig.getPlayerElderRealm(i));
      info.setAiDifficulty(galaxyConfig.getDifficulty(i));
      info.setColor(galaxyConfig.getPlayerColor(i));
      randomListOfColors.remove(galaxyConfig.getPlayerColor(i));
      if (i == 0 && !galaxyConfig.isAiOnly()) {
        info.setHuman(true);
      }
      if (info.isHuman()) {
        info.setAiDifficulty(AiDifficulty.CHALLENGING);
      }
      players.addPlayer(info);
    }
    if (galaxyConfig.getSpacePiratesLevel() > 0) {
      int index = galaxyConfig.getMaxPlayers();
      PlayerInfo info = new PlayerInfo(
          SpaceRaceFactory.createOne(SpaceRaceFactory.SPACE_PIRATE), maxPlayers,
          index, boardIndex, StartingScenarioFactory.create(
              ScenarioIds.TEMPERATE_HUMID_SIZE12));
      info.setBoard(true);
      info.setGovernment(GovernmentFactory.createOne("PIRATES"));
      info.setEmpireName("Space pirates");
      PirateDifficultLevel difficultyLevel = galaxyConfig
          .getSpacePiratesDifficulty();
      if (difficultyLevel == PirateDifficultLevel.EASY
          || difficultyLevel == PirateDifficultLevel.VERY_EASY) {
        info.setAiDifficulty(AiDifficulty.WEAK);
      }
      if (difficultyLevel == PirateDifficultLevel.NORMAL) {
        info.setAiDifficulty(AiDifficulty.NORMAL);
      }
      if (difficultyLevel == PirateDifficultLevel.HARD
          || difficultyLevel == PirateDifficultLevel.VERY_HARD) {
        info.setAiDifficulty(AiDifficulty.CHALLENGING);
      }
      var color = DiceGenerator.pickRandom(randomListOfColors);
      info.setColor(color);
      players.addPlayer(info);
    }
    if (galaxyConfig.getSpaceAnomaliesLevel() == 2) {
      int index = galaxyConfig.getMaxPlayers();
      if (galaxyConfig.getSpacePiratesLevel() > 0) {
        index++;
      }
      PlayerInfo info = new PlayerInfo(
          SpaceRaceFactory.createOne(SpaceRaceFactory.SPACE_MONSTER),
          maxPlayers, index, boardIndex, StartingScenarioFactory.create(
              ScenarioIds.TEMPERATE_HUMID_SIZE12));
      info.setBoard(true);
      info.setGovernment(GovernmentFactory.createOne("PIRATES"));
      info.setEmpireName("Space monsters");
      info.setAiDifficulty(AiDifficulty.WEAK);
      var color = DiceGenerator.pickRandom(randomListOfColors);
      info.setColor(color);
      players.addPlayer(info);
    }
    players.calculateInitialDiplomacyBonuses();
    return players;
  }
  /**
   * Save Player List to DataOutputStream
   * @param dos The data output stream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void savePlayerList(final DataOutputStream dos) throws IOException {
    dos.writeInt(currentPlayer);
    dos.writeInt(list.size());
    for (int i = 0; i < list.size(); i++) {
      list.get(i).savePlayerInfo(dos);
    }
  }

  /**
   * Add new player to list
   * @param info PlayerInfo
   */
  public void addPlayer(final PlayerInfo info) {
    list.add(info);
  }

  /**
   * Get PlayerInfo by index. If index is out of bounds then null is returned.
   * @param index The player info index
   * @return PlayerInfo or null
   */
  public PlayerInfo getPlayerInfoByIndex(final int index) {
    if (index > -1 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }

  /**
   * Get maximum players, including humans and board players
   * @return The number of players
   */
  public int getCurrentMaxPlayers() {
    return list.size();
  }

  /**
   * Get number of player including only human and AI players.
   * @return The number of players
   */
  public int getCurrentMaxRealms() {
    int count = 0;
    for (PlayerInfo info : list) {
      if (!info.isBoard()) {
        count++;
      }
    }
    return count;
  }

  /**
   * Set currently playing player by index
   * @param index 0 - MAX_PLAYERS
   */
  public void setCurrentPlayer(final int index) {
    if (index > -1 && index < list.size()) {
      currentPlayer = index;
    }
  }

  /**
   * Get current player index
   * @return The current player index
   */
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Get board space monster player info
   * @return The board player info or null
   */
  public PlayerInfo getSpaceMonsterPlayer() {
    for (int i = list.size() - 1; i > -1; i--) {
      PlayerInfo info = list.get(i);
      if (info.isBoard() && info.getRace().isMonster()) {
        return info;
      }
    }
    return null;
  }

  /**
   * Get board space pirate player info
   * @return The board player info or null
   */
  public PlayerInfo getSpacePiratePlayer() {
    for (int i = list.size() - 1; i > -1; i--) {
      PlayerInfo info = list.get(i);
      if (info.isBoard() && info.getRace().isPirate()) {
        return info;
      }
    }
    return null;
  }

  /**
   * Get Current player info
   * @return PlayerInfo
   */
  public PlayerInfo getCurrentPlayerInfo() {
    return getPlayerInfoByIndex(currentPlayer);
  }

  /**
   * Get player index for player info
   * @param toMatch PlayerInfo to match
   * @return Index or -1 if no match
   */
  public int getIndex(final PlayerInfo toMatch) {
    if (toMatch != null) {
      for (int i = 0; i < list.size(); i++) {
        PlayerInfo info = list.get(i);
        if (info.hashCode() == toMatch.hashCode()
            && info.getEmpireName().equals(toMatch.getEmpireName())) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Get player/realm by name
   * @param toMatch Realm name to match
   * @return PlayerInfo or null if not found
   */
  public PlayerInfo findByName(final String toMatch) {
    if (toMatch != null) {
      for (int i = 0; i < list.size(); i++) {
        PlayerInfo info = list.get(i);
        if (info.getEmpireName().equals(toMatch)) {
          return info;
        }
      }
    }
    return null;
  }

  /**
   * Init players visibility maps
   * @param maxX Map x size
   * @param maxY Map y size
   */
  public void initVisibilityMaps(final int maxX, final int maxY) {
    for (PlayerInfo info : list) {
      info.initMapData(maxX, maxY);
    }
  }

  /**
   * Handle players fake military cost.
   * This method can tune down fake military
   * if player does not have enough credits.
   */
  public void handlePlayerFakeMilitaryCost() {
    for (int i = 0; i < list.size(); i++) {
      PlayerInfo info = list.get(i);
      info.handleFakeMilitarySizeCost();
    }
  }

  /**
   * Return player infos in order where their realm names are found
   * from the text.
   * @param text Containing player realm names
   * @return PlayerInfo array
   */
  public PlayerInfo[] findRealmNamesFromText(final String text) {
    ArrayList<PlayerInfo> result = new ArrayList<>();
    int[] offsets = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      PlayerInfo info = list.get(i);
      offsets[i] = text.indexOf(info.getEmpireName());
    }
    boolean done;
    do {
      done = true;
      int smallest = Integer.MAX_VALUE;
      int index = -1;
      for (int i = 0; i < offsets.length; i++) {
        if (offsets[i] > -1) {
          done = false;
          if (offsets[i] < smallest) {
            index = i;
            smallest = offsets[i];
          }
        }
      }
      if (index != -1) {
        result.add(list.get(index));
        offsets[index] = -1;
      }
    } while (!done);
    return result.toArray(new PlayerInfo[result.size()]);
  }

  /**
   * Calculate initial diplomacy Bonuses for all players.
   */
  public void calculateInitialDiplomacyBonuses() {
    int maxPlayers = getCurrentMaxPlayers();
    for (int i = 0; i < maxPlayers; i++) {
      PlayerInfo info = getPlayerInfoByIndex(i);
      for (int j = 0; j < maxPlayers; j++) {
        DiplomacyBonusList bonus = info.getDiplomacy().getDiplomacyList(j);
        if (bonus != null) {
          PlayerInfo info2 = getPlayerInfoByIndex(j);
          bonus.addBonus(DiplomacyBonusType.DIPLOMACY_BONUS, info2.getRace());
          if (info2.isBoard()) {
            // Board AI has war against every body
            bonus.addBonus(DiplomacyBonusType.IN_WAR, info.getRace());
          }
          if (info.isBoard()) {
            // Board AI has war against every body
            bonus.addBonus(DiplomacyBonusType.IN_WAR, info.getRace());
          }
          Government government = info2.getGovernment();
          DiplomacyBonus diplomacyBonus = new DiplomacyBonus(
              DiplomacyBonusType.DIPLOMACY_BONUS, info2.getRace());
          diplomacyBonus.setBonusValue(government.getDiplomaticBonus());
          if (diplomacyBonus.getBonusValue() != 0) {
            bonus.addBonus(diplomacyBonus);
          }
          if (info.getRace() == info2.getRace()) {
            bonus.addBonus(DiplomacyBonusType.SAME_RACE, info2.getRace());
          }
          if (info.getGovernment() == info2.getGovernment()) {
            bonus.addBonus(DiplomacyBonusType.SAME_GOVERNMENT, info2.getRace());
          } else {
            int value = GovernmentUtility.getGovernmentComparison(
                info.getGovernment(), info2.getGovernment());
            if (value > 8) {
              bonus.addBonus(DiplomacyBonusType.SIMILAR_GOVERNMENT,
                  info2.getRace());
            } else if (value > 1) {
                bonus.addBonus(
                    DiplomacyBonusType.SIMILAR_GOVERNMENT_DIFFERENT_GROUP,
                    info2.getRace());
            } else {
              bonus.addBonus(
                  DiplomacyBonusType.DIFFERENT_GOVERNMENT,
                  info2.getRace());
            }
          }
        }
      }
    }
  }

  /**
   * Get Realm Names in array
   * @return Realm names in array
   */
  public String[] getRealmNamesInArray() {
    String[] namesArray = new String[list.size()];
    for (int i = 0; i < namesArray.length; i++) {
      PlayerInfo info = list.get(i);
      namesArray[i] = info.getEmpireName();
    }
    return namesArray;
  }
}
