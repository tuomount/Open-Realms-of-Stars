package org.openRealmOfStars.starMap;

import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.starMap.event.KarmaEvents;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.GravityType;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.starMap.planet.enums.PlanetaryEvent;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.namegenerators.RandomSystemNameGenerator;

/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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

/**
 * Generates star map for whole game.
 *
 */
public class StarMapGenerator {

  /**
   * Name generator for all systems aka stars.
   */
  private RandomSystemNameGenerator nameGenerator;
  /**
   * Just flag for galaxy generation. When this is true Sol is no longer added
   * to starmap. This will not be saved on file.
   */
  private boolean solHasAdded = false;
  /**
   * Solar System map for generation.
   */
  private int[][] solarSystem;
  /**
   * Maximum amount of looping when finding free solar system spot.
   */
  private static final int MAX_LOOPS = 10000;

  /**
   * StarMap about to be generated.
   */
  private StarMap starMap;
  /**
   * StarMap Generator constructor
   */
  public StarMapGenerator() {
    solHasAdded = false;
    nameGenerator = new RandomSystemNameGenerator();
  }


  public StarMap generateStarMap(final GalaxyConfig config,
      final PlayerList players) {
    starMap = new StarMap(config.getSizeX(), config.getSizeY(),
        players.getCurrentMaxPlayers());
    solarSystem = new int[starMap.getMaxX()][starMap.getMaxY()];
    for (int i = 0; i < starMap.getMaxX(); i++) {
      for (int j = 0; j < starMap.getMaxY(); j++) {
        solarSystem[i][j] = 0;
      }
    }
    starMap.setScoreVictoryTurn(config.getScoringVictoryTurns());
    starMap.setScoreCulture(config.getScoreLimitCulture());
    starMap.setScoreConquer(config.getScoreLimitConquer());
    starMap.setScoreResearch(config.getScoreLimitResearch());
    starMap.setScoreDiplomacy(config.getScoreLimitDiplomacy());
    starMap.setScorePopulation(config.getScoreLimitPopulation());
    starMap.setPirateDifficulty(PirateDifficultLevel.NORMAL);
    starMap.defineKarmaEvents(config.getKarmaType(), config.getKarmaSpeed());
    starMap.setAllNewsEnabled(config.isAllNews());
    starMap.setTutorialEnabled(config.isEnableTutorial());
    boolean elderRealmStart = false;
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      if (config.getPlayerElderRealm(i)) {
        elderRealmStart = true;
      }
    }
    if (elderRealmStart) {
      starMap.getHistory().addTurn(-config.getElderHeadStart());
      starMap.setTurn(-config.getElderHeadStart());
    } else {
      starMap.setTurn(0);
      starMap.getHistory().addTurn(0);
    }
    starMap.setChanceForPlanetaryEvent(config.getChanceForPlanetaryEvent());
    starMap.setPirateDifficulty(config.getSpacePiratesDifficulty());
    starMap.setPlayers(players);;
    starMap.getPlayerList().initVisibilityMaps(starMap.getMaxX(),
        starMap.getMaxY());

    buildBlackHole(starMap);
    int loop = 0;
    // Create starting systems
    try {
      if (config.getStartingPosition() == GalaxyConfig.START_POSITION_RANDOM) {
        createRandomStartSystems(config);
      }
      if (config.getStartingPosition() == GalaxyConfig.ELDERS_IN_MIDDLE) {
        createEldersInMiddleStart(config);
      }
      if (config.getStartingPosition() == GalaxyConfig.TWO_RINGS) {
        createTwoCirclesStartingSystems(config);
      }
    } catch (IllegalStateException illegalState) {
      ErrorLogger.log(illegalState);
      loop = MAX_LOOPS;
    }

    return starMap;
  }

  /**
   * Create random start systems
   * @param config GalaxyConfig
   * @param starMap StarMap
   * @param solarSystemIn Array of solar systems.
   * @return array of solar systems.
   * @throws IllegalStateException if systems cannot be created.
   */
  private int[][] createRandomStartSystems(final GalaxyConfig config)
      throws IllegalStateException {
    int loop = 0;
    int realms = 0;
    int maxX = starMap.getMaxX();
    int maxY = starMap.getMaxY();
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      while (loop < MAX_LOOPS) {
        int sx = DiceGenerator.getRandom(StarMap.SOLAR_SYSTEM_WIDTH,
            maxX - StarMap.SOLAR_SYSTEM_WIDTH);
        int sy = DiceGenerator.getRandom(StarMap.SOLAR_SYSTEM_WIDTH,
            maxY - StarMap.SOLAR_SYSTEM_WIDTH);
        int planets = DiceGenerator.getRandom(3, 5);
        int gasGiants = DiceGenerator.getRandom(2);
        if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, maxX, maxY,
            config.getSolarSystemDistance()) == 0) {
          createSolarSystem(sx, sy, planets, gasGiants, i, config);
          break;
        }
        loop++;
      }
      if (loop < MAX_LOOPS) {
        realms++;
      }
    }
    if (loop >= MAX_LOOPS) {
      throw new IllegalStateException("Random space was too crowded. "
          + realms + " / " + config.getMaxPlayers() + " were fit on space.");
    }
    return solarSystem;
  }

  /**
   * Create Solar System
   * @param sunx Sun's about coordinates
   * @param suny Sun's about coordinates
   * @param planetsToCreate Number of planets to Solar System
   * @param gasGiantsToCreate Number of Gas Giants to Solar System
   * @param playerIndex if Player index is else than -1 then SolarSystem
   * is created as home system for that player index.
   * @param config GalaxyConfig
   */
  private void createSolarSystem(final int sunx, final int suny,
      final int planetsToCreate, final int gasGiantsToCreate,
      final int playerIndex, final GalaxyConfig config) {
    boolean elderRealmStart = false;
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      if (config.getPlayerElderRealm(i)) {
        elderRealmStart = true;
      }
    }
    if (playerIndex != -1) {
      PlayerInfo playerInfo = starMap.getPlayerList().getPlayerInfoByIndex(
          playerIndex);
      if (playerInfo.getRace() == SpaceRace.HUMAN && !solHasAdded) {
        solHasAdded = true;
        createSolSystem(sunx, suny, playerIndex, config);
        return;
      }
    }
    if (playerIndex == -1 && !solHasAdded
        && DiceGenerator.getRandom(99) < 10) {
      solHasAdded = true;
      createSolSystem(sunx, suny, -1, config);
      return;
    }
    int[][] mapOfSolar = solarSystem;
    int numberOfPlanets = planetsToCreate;
    int numberOfGasGiants = gasGiantsToCreate;
    if (numberOfPlanets > 5) {
      numberOfPlanets = 5;
    }
    if (numberOfGasGiants > 2) {
      numberOfGasGiants = 2;
    }
    // The Sun
    int sx = sunx + DiceGenerator.getRandom(-1, 1);
    int sy = suny + DiceGenerator.getRandom(-1, 1);
    mapOfSolar = StarMapUtilities.setSolarSystem(solarSystem, sx, sy, getMaxX(),
        getMaxY());
    Sun sun = new Sun(new Coordinate(sx, sy), nameGenerator);
    starMap.getSunList().add(sun);
    int sunNumber = starMap.getSunList().size() - 1;
    SquareInfo info = new SquareInfo(SquareInfo.TYPE_SUN, sunNumber);
    tileInfo[sx - 1][sy - 1] = info;
    tileInfo[sx][sy - 1] = info;
    tileInfo[sx + 1][sy - 1] = info;
    tileInfo[sx - 1][sy] = info;
    tileInfo[sx][sy] = info;
    tileInfo[sx + 1][sy] = info;
    tileInfo[sx - 1][sy + 1] = info;
    tileInfo[sx][sy + 1] = info;
    tileInfo[sx + 1][sy + 1] = info;
    SunType sunType = SunType.getRandomType();
    if (playerIndex != -1) {
      // Realms start from red star aka sun.
      sunType = SunType.RED_STAR;
    }
    tiles[sx][sy] = Tiles.getSunTile(TileNames.SUN_C, sunType).getIndex();
    tiles[sx - 1][sy - 1] = Tiles.getSunTile(TileNames.SUN_NW,
        sunType).getIndex();
    tiles[sx][sy - 1] = Tiles.getSunTile(TileNames.SUN_N, sunType).getIndex();
    tiles[sx + 1][sy - 1] = Tiles.getSunTile(TileNames.SUN_NE,
        sunType).getIndex();
    tiles[sx - 1][sy] = Tiles.getSunTile(TileNames.SUN_W, sunType).getIndex();
    tiles[sx + 1][sy] = Tiles.getSunTile(TileNames.SUN_E, sunType).getIndex();
    tiles[sx - 1][sy + 1] = Tiles.getSunTile(TileNames.SUN_SW,
        sunType).getIndex();
    tiles[sx][sy + 1] = Tiles.getSunTile(TileNames.SUN_S, sunType).getIndex();
    tiles[sx + 1][sy + 1] = Tiles.getSunTile(TileNames.SUN_SE,
        sunType).getIndex();
    int planets = 0;
    int startingPlanet = DiceGenerator.getRandom(1, numberOfPlanets);
    while (planets < numberOfPlanets) {
      int px = sx + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      if (is9NeighboursEmpty(px, py)) {
        planets++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets, false);
        planet.setPlanetType(PlanetTypes.getRandomPlanetType(false));
        if (planets == startingPlanet && playerIndex != -1) {
          PlayerInfo playerInfo = players.getPlayerInfoByIndex(playerIndex);
          playerInfo.setElderRealm(config.getPlayerElderRealm(playerIndex));
          planet.setRadiationLevel(RadiationType.NO_RADIATION);
          planet.setGravityType(GravityType.NORMAL_GRAVITY);
          planet.setTemperatureType(TemperatureType.TEMPERATE);
          planet.setWaterLevel(WaterLevelType.HUMID);
          planet.setGroundSize(12);
          planet.generateWorldType();
          planet.setAmountMetalInGround(HOMEWORLD_METAL);
          planet.setHomeWorldIndex(playerInfo.getRace().getIndex());
          planet.setStartRealmIndex(playerIndex);
          if (!elderRealmStart) {
            createRealmToPlanet(planet, playerInfo, playerIndex);
          } else if (playerInfo.isElderRealm()) {
            createRealmToPlanet(planet, playerInfo, playerIndex);
          }
        } else {
          planet.setPlanetaryEvent(PlanetaryEvent.getRandomEvent(
              planet.getPlanetType(), chanceForPlanetaryEvent));
          planet.setRadiationLevel(SunType.getRadiation(sunType));
          planet.setTemperatureType(SunType.getTemperature(sunType));
          planet.generateGravityBasedOnSize();
          planet.generateWaterLevelBasedOnTemperature();
          planet.generateWorldType();
          planet.setEventActivation(false);
        }
        planetList.add(planet);
        int planetNumber = planetList.size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        tileInfo[px][py] = info;
        tiles[px][py] = planet.getPlanetType().getTileIndex();
      }
    }
    int gasGiants = 0;
    int loops = 0;
    while (gasGiants < numberOfGasGiants) {
      loops++;
      if (loops > 100) {
        break;
      }
      int px = sx + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      if (is16NeighboursEmpty(px, py)) {
        gasGiants++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets + gasGiants, true);
        planet.setPlanetType(PlanetTypes.getRandomPlanetType(true));
        planetList.add(planet);
        int planetNumber = planetList.size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, planetNumber);
        switch (planet.getPlanetTypeIndex()) {
        case 0: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 1: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 2: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_3_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_3_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_3_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_3_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 31: {
          tiles[px][py] = Tiles.getTileByName(TileNames.ICEGIANT1_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.ICEGIANT1_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT1_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT1_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 32: {
          tiles[px][py] = Tiles.getTileByName(TileNames.ICEGIANT2_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.ICEGIANT2_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT2_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT2_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        default:
          throw new IllegalArgumentException("Unexpected gas giant type:"
             + planet.getPlanetTypeIndex());
        }
      }
    }
    return mapOfSolar;
  }

  /**
   * Create Sol System
   * @param sunx Sun's about coordinates
   * @param suny Sun's about coordinates
   * @param playerIndex if Player index is else than -1 then SolarSystem
   * is created as home system for that player index.
   * @param config GalaxyConfig
   */
  private void createSolSystem(final int sunx,
      final int suny, final int playerIndex, final GalaxyConfig config) {
    boolean elderRealmStart = false;
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      if (config.getPlayerElderRealm(i)) {
        elderRealmStart = true;
      }
    }
    int numberOfPlanets = 4;
    int numberOfGasGiants = 4;
    // The Sun
    int sx = sunx + DiceGenerator.getRandom(-1, 1);
    int sy = suny + DiceGenerator.getRandom(-1, 1);
    solarSystem = StarMapUtilities.setSolarSystem(solarSystem, sx, sy, getMaxX(),
        getMaxY());
    Sun sun = new Sun(new Coordinate(sx, sy), nameGenerator);
    sunList.add(sun);
    int sunNumber = sunList.size() - 1;
    SquareInfo info = new SquareInfo(SquareInfo.TYPE_SUN, sunNumber);
    tileInfo[sx - 1][sy - 1] = info;
    tileInfo[sx][sy - 1] = info;
    tileInfo[sx + 1][sy - 1] = info;
    tileInfo[sx - 1][sy] = info;
    tileInfo[sx][sy] = info;
    tileInfo[sx + 1][sy] = info;
    tileInfo[sx - 1][sy + 1] = info;
    tileInfo[sx][sy + 1] = info;
    tileInfo[sx + 1][sy + 1] = info;
    // Sol has sun type 0.
    SunType sunType = SunType.RED_STAR;
    tiles[sx][sy] = Tiles.getSunTile(TileNames.SUN_C, sunType).getIndex();
    tiles[sx - 1][sy - 1] = Tiles.getSunTile(TileNames.SUN_NW,
        sunType).getIndex();
    tiles[sx][sy - 1] = Tiles.getSunTile(TileNames.SUN_N, sunType).getIndex();
    tiles[sx + 1][sy - 1] = Tiles.getSunTile(TileNames.SUN_NE,
        sunType).getIndex();
    tiles[sx - 1][sy] = Tiles.getSunTile(TileNames.SUN_W, sunType).getIndex();
    tiles[sx + 1][sy] = Tiles.getSunTile(TileNames.SUN_E, sunType).getIndex();
    tiles[sx - 1][sy + 1] = Tiles.getSunTile(TileNames.SUN_SW,
        sunType).getIndex();
    tiles[sx][sy + 1] = Tiles.getSunTile(TileNames.SUN_S, sunType).getIndex();
    tiles[sx + 1][sy + 1] = Tiles.getSunTile(TileNames.SUN_SE,
        sunType).getIndex();
    int planets = 0;
    while (planets < numberOfPlanets) {
      int px = sx + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      if (is9NeighboursEmpty(px, py)) {
        planets++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets, false);
        planet.setPlanetType(PlanetTypes.getRandomPlanetType(false));
        if (planets == 1) {
          planet.setPlanetType(PlanetTypes.BARRENWORLD1);
          planet.setRadiationLevel(RadiationType.HIGH_RADIATION);
          planet.setGroundSize(7);
          planet.setTemperatureType(TemperatureType.VOLCANIC);
          planet.generateWaterLevelBasedOnTemperature();
          planet.generateGravityBasedOnSize();
          planet.generateWorldType();
          planet.setName("Mercury I");
        }
        if (planets == 2) {
          planet.setPlanetType(PlanetTypes.SWAMPWORLD2);
          planet.setRadiationLevel(RadiationType.LOW_RADIATION);
          planet.setGroundSize(11);
          planet.setTemperatureType(TemperatureType.VOLCANIC);
          planet.generateWaterLevelBasedOnTemperature();
          planet.generateGravityBasedOnSize();
          planet.generateWorldType();
          planet.setName("Venus II");
        }
        if (planets == 3) {
          planet.setPlanetType(PlanetTypes.PLANET_EARTH);
          planet.setRadiationLevel(RadiationType.NO_RADIATION);
          planet.setGroundSize(12);
          planet.setTemperatureType(TemperatureType.TEMPERATE);
          planet.setWaterLevel(WaterLevelType.OCEAN);
          planet.generateGravityBasedOnSize();
          planet.setName("Earth III");
          if (playerIndex != -1) {
            PlayerInfo playerInfo = players.getPlayerInfoByIndex(playerIndex);
            playerInfo.setElderRealm(config.getPlayerElderRealm(playerIndex));
            planet.setAmountMetalInGround(HOMEWORLD_METAL);
            planet.setHomeWorldIndex(playerInfo.getRace().getIndex());
            planet.setStartRealmIndex(playerIndex);
            if (!elderRealmStart) {
              createRealmToPlanet(planet, playerInfo, playerIndex);
            } else if (playerInfo.isElderRealm()) {
              createRealmToPlanet(planet, playerInfo, playerIndex);
            }
          }
          if (playerIndex == -1) {
            int index = DiceGenerator.getRandom(5);
            switch (index) {
              case 0: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_FACTORY);
                break;
              }
              case 1: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_LAB);
                break;
              }
              case 2: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_PALACE);
                break;
              }
              case 3: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_TEMPLE);
                break;
              }
              default:
              case 4: {
                planet.setPlanetaryEvent(PlanetaryEvent.BLACK_MONOLITH);
                break;
              }
              case 5: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_ARTIFACT);
                break;
              }
            }
          }
        }
        if (planets == 4) {
          planet.setPlanetType(PlanetTypes.PLANET_MARS);
          planet.setRadiationLevel(RadiationType.NO_RADIATION);
          planet.setGroundSize(8);
          planet.setTemperatureType(TemperatureType.COLD);
          planet.setWaterLevel(WaterLevelType.ARID);
          planet.generateGravityBasedOnSize();
          planet.setName("Mars IV");
          if (playerIndex == -1 && DiceGenerator.getRandom(99) <= 25) {
            int index = DiceGenerator.getRandom(3);
            switch (index) {
              case 0: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_FACTORY);
                break;
              }
              case 1: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_LAB);
                break;
              }
              default:
              case 2: {
                planet.setPlanetaryEvent(PlanetaryEvent.BLACK_MONOLITH);
                break;
              }
              case 3: {
                planet.setPlanetaryEvent(PlanetaryEvent.ANCIENT_ARTIFACT);
                break;
              }
            }
          }
        }
        planetList.add(planet);
        int planetNumber = planetList.size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        tileInfo[px][py] = info;
        tiles[px][py] = planet.getPlanetType().getTileIndex();
      }
    }
    int gasGiants = 0;
    int loops = 0;
    while (gasGiants < numberOfGasGiants) {
      loops++;
      if (loops > 100) {
        break;
      }
      int px = sx + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      if (is16NeighboursEmpty(px, py)) {
        gasGiants++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets + gasGiants, true);
        if (gasGiants == 1) {
          planet.setPlanetType(PlanetTypes.PLANET_JUPITER);
          planet.setName("Jupiter V");
        }
        if (gasGiants == 2) {
          planet.setPlanetType(PlanetTypes.PLANET_SATURN);
          planet.setName("Saturn VI");
        }
        if (gasGiants == 3) {
          planet.setPlanetType(PlanetTypes.ICEGIANT1);
          planet.setName("Uranus VII");
        }
        if (gasGiants == 4) {
          planet.setPlanetType(PlanetTypes.ICEGIANT2);
          planet.setName("Neptune VIII");
        }
        planetList.add(planet);
        int planetNumber = planetList.size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, planetNumber);
        switch (planet.getPlanetTypeIndex()) {
        case 0: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 1: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 2: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_3_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_3_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_3_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.GAS_GIANT_3_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 29: {
          tiles[px][py] = Tiles.getTileByName(TileNames.JUPITER_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.JUPITER_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.JUPITER_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.JUPITER_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 30: {
          tiles[px][py] = Tiles.getTileByName(TileNames.SATURN_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.SATURN_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.SATURN_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.SATURN_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 31: {
          tiles[px][py] = Tiles.getTileByName(TileNames.ICEGIANT1_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.ICEGIANT1_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT1_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT1_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        case 32: {
          tiles[px][py] = Tiles.getTileByName(TileNames.ICEGIANT2_NW)
              .getIndex();
          tiles[px + 1][py] = Tiles.getTileByName(TileNames.ICEGIANT2_NE)
              .getIndex();
          tiles[px][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT2_SW)
              .getIndex();
          tiles[px + 1][py + 1] = Tiles.getTileByName(TileNames.ICEGIANT2_SE)
              .getIndex();
          tileInfo[px][py] = info;
          tileInfo[px + 1][py] = info;
          tileInfo[px][py + 1] = info;
          tileInfo[px + 1][py + 1] = info;
          break;
        }
        default:
          throw new IllegalArgumentException("Unexpected gas giant type:"
             + planet.getPlanetTypeIndex());
        }
      }
    }
    return mapOfSolar;
  }

  /**
   * Build Black hole. This will build black hole in the galaxy center.
   * @param starMap StarMap
   */
  private void buildBlackHole(final StarMap starMap) {
    int cx = starMap.getMaxX() / 2;
    int cy = starMap.getMaxY() / 2;
    starMap.setTile(cx, cy, Tiles.getTileByName(TileNames.BLACKHOLE_C));
    starMap.setSquareInfo(cx, cy, SquareInfo.TYPE_BLACKHOLE_CENTER, 0);
    starMap.setTile(cx - 1, cy - 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_NW));
    starMap.setSquareInfo(cx - 1, cy - 1, SquareInfo.TYPE_BLACKHOLE, 0);
    starMap.setTile(cx, cy - 1, Tiles.getTileByName(TileNames.BLACKHOLE_N));
    starMap.setSquareInfo(cx, cy - 1, SquareInfo.TYPE_BLACKHOLE, 0);
    starMap.setTile(cx + 1, cy - 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_NE));
    starMap.setSquareInfo(cx + 1, cy - 1, SquareInfo.TYPE_BLACKHOLE, 0);

    starMap.setTile(cx - 1, cy, Tiles.getTileByName(TileNames.BLACKHOLE_W));
    starMap.setSquareInfo(cx - 1, cy, SquareInfo.TYPE_BLACKHOLE, 0);
    starMap.setTile(cx + 1, cy, Tiles.getTileByName(TileNames.BLACKHOLE_E));
    starMap.setSquareInfo(cx + 1, cy, SquareInfo.TYPE_BLACKHOLE, 0);

    starMap.setTile(cx - 1, cy + 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_SW));
    starMap.setSquareInfo(cx - 1, cy + 1, SquareInfo.TYPE_BLACKHOLE, 0);
    starMap.setTile(cx, cy + 1, Tiles.getTileByName(TileNames.BLACKHOLE_S));
    starMap.setSquareInfo(cx, cy + 1, SquareInfo.TYPE_BLACKHOLE, 0);
    starMap.setTile(cx + 1, cy + 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_SE));
    starMap.setSquareInfo(cx + 1, cy + 1, SquareInfo.TYPE_BLACKHOLE, 0);

    /* Mark blackhole as solar system, so no star will replace it. */
    for (int i = -2; i < 3; i++) {
      for (int j = -2; j < 2; j++) {
        solarSystem[cx + i][cy + j] = 1;
      }
    }
  }

  /**
   * Helper getter for getting maximum galaxy X size.
   * @return Max galaxy size
   */
  private int getMaxX() {
    return starMap.getMaxX();
  }
  /**
   * Helper getter for getting maximum galaxy Y size.
   * @return Max galaxy size
   */
  private int getMaxY() {
    return starMap.getMaxY();
  }

}
