package org.openRealmOfStars.starMap;
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

import java.util.ArrayList;

import org.openRealmOfStars.ai.pathfinding.AStarSearch;
import org.openRealmOfStars.ai.pathfinding.PathPoint;
import org.openRealmOfStars.ai.planet.PlanetHandling;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.StartingScenario;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.BackgroundStoryGenerator;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.history.event.PlayerStartEvent;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.starMap.planet.enums.PlanetaryEvent;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.WeightedList;
import org.openRealmOfStars.utilities.namegenerators.RandomSystemNameGenerator;
import org.openRealmOfStars.utilities.namegenerators.RoguePlanetNameGenerator;



/**
 * Generates star map for whole game.
 *
 */
public class StarMapGenerator {

  /** Name generator for all systems aka stars. */
  private RandomSystemNameGenerator nameGenerator;
  /**
   * Just flag for galaxy generation. When this is true Sol is no longer added
   * to starmap. This will not be saved on file.
   */
  private boolean solHasAdded = false;
  /**
   * Just flag for galaxy generation. When this is true, destroyed planet start
   * has been added to starmap.
   */
  private boolean destroyedPlanetStartAdded = false;
  /** Solar System map for generation. */
  private int[][] solarSystem;
  /** Maximum amount of looping when finding free solar system spot. */
  private static final int MAX_LOOPS = 10000;

  /** Default amount of metal in home worlds. */
  private static final int HOMEWORLD_METAL = 8000;

  /** StarMap about to be generated. */
  private StarMap starMap;

  /**
   * StarMap Generator constructor
   */
  public StarMapGenerator() {
    solHasAdded = false;
    destroyedPlanetStartAdded = false;
    nameGenerator = new RandomSystemNameGenerator();
  }

  /**
   * Generate StarMap based on galaxy config and player list.
   * @param config GalaxyConfig
   * @param players PlayerList
   * @return generated starmap
   */
  public StarMap generateStarMap(final GalaxyConfig config,
      final PlayerList players) {
    reinitStarMap(config, players);
    boolean tooFullSpace = false;
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
      tooFullSpace = true;
    }
    if (config.getStartingPosition() == GalaxyConfig.START_POSITION_BORDER
        || tooFullSpace) {
      if (tooFullSpace) {
        // Need to reinit whole map
        reinitStarMap(config, players);
        players.reInit();
        players.initVisibilityMaps(getMaxX(), getMaxY());
        for (int i = 0; i < getMaxX(); i++) {
          for (int j = 0; j < getMaxY(); j++) {
            solarSystem[i][j] = 0;
          }
        }
      }
      createBorderStartingSystems(config);
    }
    // Random system
    generateRandomSystems(config);
    // Rogue planets
    generateRoguePlanets(config);
    // Planetary Ascension portal
    // TODO: Implment later
    /*int ascensionPortalX = -1;
    int ascensionPortalY = -1;
    for (int i = 0; i < 100; i++) {
      Planet planet = DiceGenerator.pickRandom(starMap.getPlanetList());
      if (planet.isGasGiant()) {
        continue;
      }
      for (int j = 0; j < 4; j++) {
        int mx = 0;
        int my = 0;
        if (j == 0) {
          my = -1;
        }
        if (j == 1) {
          mx = 1;
        }
        if (j == 2) {
          my = 1;
        }
        if (j == 3) {
          mx = -1;
        }
        int x = planet.getX() + mx;
        int y = planet.getY() + my;
        if (starMap.isValidCoordinate(x, y)
            && starMap.getTileIndex(x, y) == 0) {
          ascensionPortalX = x;
          ascensionPortalY = y;
        }
        if (ascensionPortalX != -1 && DiceGenerator.getBoolean()) {
          break;
        }
      }
      if (ascensionPortalX != -1) {
        break;
      }
    }*/
    generateDeepSpaceAnchors(config);
    generateSpaceAnomalies(config);
    // TODO This is for ascension victory
    /*generateAscensionPortal(ascensionPortalX, ascensionPortalY);
    smoothAscensionVeins();
    revealWholeMap(getCurrentPlayerInfo());*/
    return starMap;
  }

  /**
   * Create realm to planet. This will add require buildings, workers
   * and ships. This will also add message about new realm starting.
   * Not this does not add home planet information.
   * @param planet Planet where player starts.
   * @param playerInfo Realm who is starting
   * @param playerIndex Index for player
   */
  public void createRealmToPlanet(final Planet planet,
      final PlayerInfo playerInfo, final int playerIndex) {
    if (planet.getPlanetPlayerInfo() != null && planet.getGovernor() != null) {
      planet.getGovernor().setJob(Job.UNASSIGNED);
      planet.setGovernor(null);
      //TODO: What to do when elder realm conquers other realm's starting
      //Planet? Set culture 0 and destroy all buildings? Elder realm knows
      //where is another realm's home planet.
    }
    planet.setPlanetOwner(playerIndex, playerInfo);
    if (playerInfo.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      planet.colonizeWithOrbital();
    }
    makeStartingTutorialTexts(playerInfo, planet);
    Message msg = new Message(
        MessageType.PLANETARY, playerInfo.getEmpireName() + " starts at "
            + planet.getName() + ".",
        Icons.getIconByName(Icons.ICON_CULTURE));
    PlayerStartEvent event = new PlayerStartEvent(planet.getCoordinate(),
        planet.getName(), playerIndex);
    starMap.getHistory().addEvent(event);
    msg.setCoordinate(planet.getCoordinate());
    msg.setMatchByString(planet.getName());
    if (playerInfo.getRuler() == null) {
      Leader ruler = LeaderUtility.createLeader(playerInfo, planet,
          LeaderUtility.LEVEL_START_RULER);
      ruler.setJob(Job.RULER);
      ruler.setTitle(LeaderUtility.createTitleForLeader(ruler, playerInfo));
      playerInfo.getLeaderPool().add(ruler);
      playerInfo.getMsgList().addNewMessage(msg);
      playerInfo.setRuler(ruler);
    }

    if (!playerInfo.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)
        && !planet.hasSpacePort()) {
       if (planet.getBuildingList().length >= planet.getGroundSize()) {
         // Planet is full and no space for space port.
         planet.destroyOneBuilding();
       }
      planet.addBuilding(BuildingFactory.createByName("Space port"));
    }
    int extraPop = 0;
    if (playerInfo.getStartingScenario() == StartingScenario.FARMING_PLANET) {
      extraPop = 2;
      if (playerInfo.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
        // No more space for zero gravity being.
        extraPop = 1;
      }
      for (int i = 0; i < 3; i++) {
        if (planet.getBuildingList().length >= planet.getGroundSize()) {
          // Planet is full and no space for "farms".
          planet.destroyOneBuilding();
        }
        if (playerInfo.getRace().isLithovorian()) {
          planet.addBuilding(BuildingFactory.createByName("Basic mine"));
        }
        if (playerInfo.getRace().isEatingFood()) {
          planet.addBuilding(BuildingFactory.createByName("Basic farm"));
        }
        if (playerInfo.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
          planet.addBuilding(BuildingFactory.createByName("Basic factory"));
        }
      }
    }
    if (playerInfo.isHuman()) {
      // Adding starting building for human.
      planet.setUnderConstruction(ConstructionFactory.createByName(
          "Extra credit"));
    }
    planet.setWorkers(Planet.FOOD_FARMERS, 0);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 1 + extraPop);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
    planet.setWorkers(Planet.CULTURE_ARTIST, 0);
    PlanetHandling.handlePlanetPopulation(planet, playerInfo, playerIndex,
        GameLengthState.START_GAME);
    ShipStat[] stats = playerInfo.getShipStatList();
    int count = 0;
    boolean noShips = false;
    if (playerInfo.getStartingScenario() == StartingScenario.FARMING_PLANET) {
      noShips = true;
    }
    if (!noShips) {
      for (ShipStat stat : stats) {
        int numShip = 1;
        for (int j = 0; j < numShip; j++) {
          if (stat.getDesign().getHull().getHullType()
              == ShipHullType.ORBITAL) {
            continue;
          }
          Ship ship = new Ship(stat.getDesign());
          stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
          stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
          Fleet fleet = new Fleet(ship, planet.getX(), planet.getY());
          playerInfo.getFleets().add(fleet);
          if (ship.isColonyModule()) {
            fleet.setName("Colony #" + count);
          } else {
            fleet.setName("Scout #" + count);
          }
          makeStartingFleetTutorialTexts(playerInfo, fleet);
          msg = new Message(MessageType.FLEET,
              fleet.getName() + " is waiting for orders.",
              Icons.getIconByName(Icons.ICON_HULL_TECH));
          msg.setCoordinate(planet.getCoordinate());
          msg.setMatchByString(fleet.getName());
          playerInfo.getMsgList().addNewMessage(msg);
          count++;
        }
      }
    }
    String backgroundStory = BackgroundStoryGenerator.generateBackgroundStory(
        playerInfo, planet, starMap.getStarYear());
    playerInfo.setBackgroundStory(backgroundStory);
    Message msgStart = new Message(MessageType.STORY, backgroundStory,
        Icons.getIconByName(Icons.ICON_CULTURE));
    msgStart.setCoordinate(planet.getCoordinate());
    msgStart.setMatchByString(planet.getName());
    playerInfo.getMsgList().addNewMessage(msgStart);
  }

  /**
   * Create realm to galaxy without planet. This will add required ships.
   * This will also add message about new realm starting.
   * @param x X coordinate where to start
   * @param y Y coordinate where to start
   * @param playerInfo Realm who is starting
   * @param playerIndex Index for player
   */
  public void createRealmToGalaxy(final int x, final int y,
      final PlayerInfo playerInfo, final int playerIndex) {
    Coordinate startCoord = new Coordinate(x, y);
    makeStartingTutorialTextsInDeepSpace(playerInfo, startCoord);
    Message msg = new Message(
        MessageType.FLEET, playerInfo.getEmpireName() + " starts at "
            + "deep space.",
        Icons.getIconByName(Icons.ICON_CULTURE));
    PlayerStartEvent event = new PlayerStartEvent(startCoord, "Deep space",
        playerIndex);
    starMap.getHistory().addEvent(event);
    msg.setCoordinate(startCoord);
    msg.setMatchByString("Colony #0");
    if (playerInfo.getRuler() == null) {
      Leader ruler = LeaderUtility.createLeader(playerInfo, null,
          LeaderUtility.LEVEL_START_RULER);
      ruler.setJob(Job.RULER);
      ruler.setTitle(LeaderUtility.createTitleForLeader(ruler, playerInfo));
      playerInfo.getLeaderPool().add(ruler);
      playerInfo.getMsgList().addNewMessage(msg);
      playerInfo.setRuler(ruler);
    }
    ShipStat[] stats = playerInfo.getShipStatList();
    int count = 0;
    for (ShipStat stat : stats) {
      int numShip = 1;
      if (playerInfo.getStartingScenario()
          == StartingScenario.DESTROYED_HOME_PLANET
          || playerInfo.getStartingScenario()
          == StartingScenario.FROM_ANOTHER_GALAXY) {
        if (stat.getDesign().isMilitaryShip()) {
          numShip = 4;
        }
        if (stat.getDesign().getName().startsWith("Colony")) {
          numShip = 2;
        }
      }
      for (int j = 0; j < numShip; j++) {
        if (stat.getDesign().getHull().getHullType() == ShipHullType.ORBITAL) {
          continue;
        }
        Ship ship = new Ship(stat.getDesign());
        stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
        stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
        Fleet fleet = new Fleet(ship, x, y);
        playerInfo.getFleets().add(fleet);
        if (ship.isColonyModule()) {
          fleet.setName("Colony #" + count);
          fleet.getColonyShip(false).setColonist(2);
          makeStartingFleetTutorialTexts(playerInfo, fleet);
        } else {
          fleet.setName("Scout #" + count);
          makeStartingFleetTutorialTexts(playerInfo, fleet);
        }
        msg = new Message(MessageType.FLEET,
            fleet.getName() + " is waiting for orders.",
            Icons.getIconByName(Icons.ICON_HULL_TECH));
        msg.setCoordinate(fleet.getCoordinate());
        msg.setMatchByString(fleet.getName());
        playerInfo.getMsgList().addNewMessage(msg);
        count++;
      }
    }
    Planet planet = new Planet(startCoord, "Alpha Aurora", 2, false);
    planet.setTemperatureType(TemperatureType.TEMPERATE);
    planet.setWaterLevel(WaterLevelType.MARINE);
    planet.setGroundSize(12);
    planet.generateGravityBasedOnSize();
    planet.generateWorldType();
    String backgroundStory = BackgroundStoryGenerator.generateBackgroundStory(
        playerInfo, planet, starMap.getStarYear());
    playerInfo.setBackgroundStory(backgroundStory);
    Message msgStart = new Message(MessageType.STORY, backgroundStory,
        Icons.getIconByName(Icons.ICON_CULTURE));
    msgStart.setCoordinate(startCoord);
    msgStart.setMatchByString("Colony #0");
    playerInfo.getMsgList().addNewMessage(msgStart);
  }

  /**
   * Smooth ascension veins.
   */
  public void smoothAscensionVeins() {
    for (int y = 0; y < getMaxY(); y++) {
      for (int x = 0; x < getMaxX(); x++) {
        boolean north = false;
        boolean west = false;
        boolean east = false;
        boolean south = false;
        Tile tile = Tiles.getTileByIndex(starMap.getTileIndex(x, y));
        if (tile.isAscensionVein()) {
          int mx = x;
          int my = y - 1;
          if (starMap.isValidCoordinate(mx, my)) {
            tile = Tiles.getTileByIndex(starMap.getTileIndex(mx, my));
            if (tile.isAscensionVein() || tile.isBlackhole()
                || tile.isAscensionPortal()) {
              north = true;
            }
          }
          mx = x;
          my = y + 1;
          if (starMap.isValidCoordinate(mx, my)) {
            tile = Tiles.getTileByIndex(starMap.getTileIndex(mx, my));
            if (tile.isAscensionVein() || tile.isBlackhole()
                || tile.isAscensionPortal()) {
              south = true;
            }
          }
          mx = x - 1;
          my = y;
          if (starMap.isValidCoordinate(mx, my)) {
            tile = Tiles.getTileByIndex(starMap.getTileIndex(mx, my));
            if (tile.isAscensionVein() || tile.isBlackhole()
                || tile.isAscensionPortal()) {
              west = true;
            }
          }
          mx = x + 1;
          my = y;
          if (starMap.isValidCoordinate(mx, my)) {
            tile = Tiles.getTileByIndex(starMap.getTileIndex(mx, my));
            if (tile.isAscensionVein() || tile.isBlackhole()
                || tile.isAscensionPortal()) {
              east = true;
            }
          }
          tile = Tiles.getTileByIndex(starMap.getTileIndex(x, y));
          if (north && south && west && east) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NSWE1);
          } else if (north && south && west) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NSW1);
          } else if (north && east && west) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NWE1);
          } else if (north && south && east) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NSE1);
          } else if (south && east && west) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_SWE1);
          } else if (north && east) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NE1);
          } else if (north && west) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NW1);
          } else if (south && west) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_SW1);
          } else if (south && east) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_SE1);
          } else if (west && east) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_WE1);
          } else if (north && south) {
            tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NS1);
          }
          starMap.setTile(x, y, tile);
        }
      }
    }
  }

  /**
   * Generate Ascension portal to map and ascension veins.
   *
   * @param x X coordinate for portal
   * @param y Y Coordinate for portal
   */
  public void generateAscensionPortal(final int x, final int y) {
    int cx = getMaxX() / 2;
    int cy = getMaxY() / 2;
    int[] sax = new int[4];
    int[] say = new int[4];
    sax[0] = cx;
    say[0] = cy - 2;
    sax[1] = cx + 2;
    say[1] = cy;
    sax[2] = cx;
    say[2] = cy + 2;
    sax[3] = cx - 2;
    say[3] = cy;
    int best = -1;
    double bestDist = 999;
    for (int i = 0; i < sax.length; i++) {
      Coordinate target = new Coordinate(x, y);
      Coordinate start = new Coordinate(sax[i], say[i]);
      double dist = target.calculateDistance(start);
      if (dist < bestDist) {
        best = i;
        bestDist = dist;
      }
    }
    int sx = sax[best];
    int sy = say[best];
    Tile tile = Tiles.getTileByName(TileNames.ASCENSION_VEIN_NSWE1);
    starMap.setTile(sx, sy, tile);
    starMap.setSquareInfo(sx, sy, new SquareInfo(
        SquareInfo.TYPE_ASCENSION_VEIN, 0));
    AStarSearch search = new AStarSearch(starMap, sx, sy, x, y);
    if (search.doSquareSearch()) {
      search.doSquareRoute();
      int count = 0;
      do {
        count++;
        PathPoint point = search.getMove();
        if (point != null) {
          starMap.setTile(point.getX(), point.getY(), tile.getIndex());
          starMap.setSquareInfo(point.getX(), point.getY(), new SquareInfo(
              SquareInfo.TYPE_ASCENSION_VEIN, count));
          search.nextMove();
          if (search.isLastMove()) {
            point = search.getMove();
            if (point != null) {
              starMap.setTile(point.getX(), point.getY(), tile.getIndex());
              starMap.setSquareInfo(point.getX(), point.getY(), new SquareInfo(
                  SquareInfo.TYPE_ASCENSION_VEIN, count));
            }
          }
        } else {
          break;
        }
      } while (!search.isLastMove());
      starMap.setTile(x, y, Tiles.getTileByName(
          TileNames.ASCENSION_PORTAL1).getIndex());
    }
  }

  /**
   * Reinit whole starmap.
   * @param config GalaxyConfig
   * @param players PlayerList
   */
  private void reinitStarMap(final GalaxyConfig config,
      final PlayerList players) {
    starMap = new StarMap(config.getSizeX(), config.getSizeY(),
        players.getCurrentMaxPlayers(), players.getCurrentMaxRealms());
    starMap.setPlayers(players);
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
    boolean elderRealmStart = config.isElderRealmStart();
    if (elderRealmStart) {
      starMap.getHistory().addTurn(-config.getElderHeadStart());
      starMap.setTurn(-config.getElderHeadStart());
    } else {
      starMap.setTurn(0);
      starMap.getHistory().addTurn(0);
    }
    starMap.setPirateDifficulty(config.getSpacePiratesDifficulty());
    starMap.setPlayers(players);
    starMap.getPlayerList().initVisibilityMaps(starMap.getMaxX(),
        starMap.getMaxY());

    buildBlackHole();

  }

  /**
   * Create random start systems
   * @param config GalaxyConfig
   * @throws IllegalStateException if systems cannot be created.
   */
  private void createRandomStartSystems(final GalaxyConfig config)
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
  }

  /**
   * Create random start systems so that elders are in middle.
   * @param config GalaxyConfig
   * @throws IllegalStateException of system cannot be created.
   */
  private void createEldersInMiddleStart(final GalaxyConfig config)
      throws IllegalStateException {
    int loop = 0;
    int oneThird = getMaxX() / 3;
    int realms = 0;
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      while (loop < MAX_LOOPS) {
        boolean elder = config.getPlayerElderRealm(i);
        int sx = DiceGenerator.getRandom(StarMap.SOLAR_SYSTEM_WIDTH,
            getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH);
        int sy = DiceGenerator.getRandom(StarMap.SOLAR_SYSTEM_WIDTH,
            getMaxY() - StarMap.SOLAR_SYSTEM_WIDTH);
        if (elder) {
          sx = DiceGenerator.getRandom(oneThird, 2 * oneThird);
          sy = DiceGenerator.getRandom(oneThird, 2 * oneThird);
        }
        boolean middle = false;
        if (sx >= oneThird && sx <= 2 * oneThird
            && sy >= oneThird && sy <= 2 * oneThird) {
          middle = true;
        }
        int planets = DiceGenerator.getRandom(3, 5);
        int gasGiants = DiceGenerator.getRandom(2);
        if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, getMaxX(),
            getMaxY(), config.getSolarSystemDistance()) == 0
            && middle == elder) {
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
      throw new IllegalStateException(
          "Elder in middle space was too crowded. "
          + realms + " / " + config.getMaxPlayers() + " were fit on space.");
    }
  }

  /**
   * Create two circles starting solar systems
   * @param config Galaxy Config
   */
  private void createTwoCirclesStartingSystems(final GalaxyConfig config) {
    int elders = 0;
    int regular = 0;
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      if (config.getPlayerElderRealm(i)) {
        elders++;
      } else {
        regular++;
      }
    }
    // First starting Systems
    int sx = StarMap.SOLAR_SYSTEM_WIDTH;
    int sy = StarMap.SOLAR_SYSTEM_WIDTH;
    int planets = DiceGenerator.getRandom(3, 5);
    int gasGiants = DiceGenerator.getRandom(2);
    int fineTune = 10;
    if (config.getSizeX() < 75) {
      fineTune = 5;
    }
    int length = getMaxX() / 2 - fineTune;
    int lengthElders = getMaxX() / 4;
    int angle = DiceGenerator.getRandom(359);
    int angleElders = DiceGenerator.getRandom(359);
    for (int i = 0; i < config.getMaxPlayers(); i++) {
      if (config.getPlayerElderRealm(i)) {
        double rad = Math.toRadians(angleElders);
        sx = (int) (getMaxX() / 2 + Math.cos(rad) * lengthElders);
        sy = (int) (getMaxX() / 2 + Math.sin(rad) * lengthElders);
        planets = DiceGenerator.getRandom(3, 5);
        gasGiants = DiceGenerator.getRandom(2);
        createSolarSystem(sx, sy, planets, gasGiants, i, config);
        angleElders = angleElders + 360 / elders;
        if (angleElders > 359) {
          angleElders = angleElders - 360;
        }
      } else {
        double rad = Math.toRadians(angle);
        sx = (int) (getMaxX() / 2 + Math.cos(rad) * length);
        sy = (int) (getMaxX() / 2 + Math.sin(rad) * length);
        planets = DiceGenerator.getRandom(3, 5);
        gasGiants = DiceGenerator.getRandom(2);
        createSolarSystem(sx, sy, planets, gasGiants, i, config);
        angle = angle + 360 / regular;
        if (angle > 359) {
          angle = angle - 360;
        }
      }
    }
  }

  /**
   * Create border starting solar system
   * @param config Galaxy Config
   */
  private void createBorderStartingSystems(final GalaxyConfig config) {
    // First starting Systems
    int sx = StarMap.SOLAR_SYSTEM_WIDTH;
    int sy = StarMap.SOLAR_SYSTEM_WIDTH;
    int planets = DiceGenerator.getRandom(3, 5);
    int gasGiants = DiceGenerator.getRandom(2);
    if (config.getSizeX() > 50) {
      int length = getMaxX() / 2 - 10;
      int angle = DiceGenerator.getRandom(359);
      for (int i = 0; i < config.getMaxPlayers(); i++) {
        double rad = Math.toRadians(angle);
        sx = (int) (getMaxX() / 2 + Math.cos(rad) * length);
        sy = (int) (getMaxX() / 2 + Math.sin(rad) * length);
        planets = DiceGenerator.getRandom(3, 5);
        gasGiants = DiceGenerator.getRandom(2);
        createSolarSystem(sx, sy, planets, gasGiants, i, config);
        angle = angle + 360 / config.getMaxPlayers();
        if (angle > 359) {
          angle = angle - 360;
        }
      }
      return;
    }
    // Old border star system is only used with Tiny map.
    if (config.getMaxPlayers() == 2) {
      // First player
      sx = getMaxX() / 2;
      sy = StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 0, config);

      // Second player
      sx = getMaxX() / 2;
      sy = getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 1, config);

    } else if (config.getMaxPlayers() == 3) {
      // First player
      sx = StarMap.SOLAR_SYSTEM_WIDTH;
      sy = getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 0, config);

      // Second player
      sx = getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH;
      sy = getMaxY() - StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 1, config);

      // Third player
      sx = getMaxX() / 2;
      sy = StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 2, config);

    } else if (config.getMaxPlayers() >= 4) {
      // First player
      sx = StarMap.SOLAR_SYSTEM_WIDTH;
      sy = StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 0, config);

      // Second player
      sx = getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH;
      sy = StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 1, config);

      // Third player
      sx = StarMap.SOLAR_SYSTEM_WIDTH;
      sy = getMaxY() - StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 2, config);

      // Fourth player
      sx = getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH;
      sy = getMaxY() - StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 3, config);
    }

    if (config.getMaxPlayers() >= 5) {
      // Fifth player
      sx = getMaxX() / 2;
      sy = getMaxY() - StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 4, config);
    }
    if (config.getMaxPlayers() >= 6) {
      // Sixth player
      sx = getMaxX() / 2;
      sy = StarMap.SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 5, config);
    }
    if (config.getMaxPlayers() >= 7) {
      // Seventh player
      sx = StarMap.SOLAR_SYSTEM_WIDTH;
      sy = getMaxY() / 2;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants, 6, config);
    }
    if (config.getMaxPlayers() == 8) {
      // Eight player
      sx = getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH;
      sy = getMaxY() / 2;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(sx, sy, planets, gasGiants,
          7, config);
    }
  }

  /**
   * Reserve start point from space
   * @param sunx Sun's about coordinates
   * @param suny Sun's about coordinates
   * @param playerIndex if Player index is else than -1 then SolarSystem
   * is created as home system for that player index.
   * @param config GalaxyConfig
   */
  private void reserverStartFromSpace(final int sunx, final int suny,
      final int playerIndex, final GalaxyConfig config) {
    boolean elderRealmStart = config.isElderRealmStart();
    int sx = sunx + DiceGenerator.getRandom(-1, 1);
    int sy = suny + DiceGenerator.getRandom(-1, 1);
    if (playerIndex != -1) {
      PlayerInfo playerInfo = starMap.getPlayerList().getPlayerInfoByIndex(
          playerIndex);
      if (!elderRealmStart) {
        createRealmToGalaxy(sx, sy, playerInfo, playerIndex);
      } else if (playerInfo.isElderRealm()) {
        createRealmToGalaxy(sx, sy, playerInfo, playerIndex);
      } else {
        SquareInfo info = new SquareInfo(SquareInfo.TYPE_DEEP_SPACE_START,
            playerIndex);
        starMap.setSquareInfo(sx, sy, info);
        starMap.setTile(sx, sy, Tiles.getTileByName(TileNames.EMPTY)
            .getIndex());
      }
      solarSystem = StarMapUtilities.setSolarSystem(solarSystem, sx,
          sy, getMaxX(), getMaxY());
    }
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
    boolean elderRealmStart = config.isElderRealmStart();
    if (playerIndex != -1) {
      PlayerInfo playerInfo = starMap.getPlayerList().getPlayerInfoByIndex(
          playerIndex);
      if (playerInfo.getStartingScenario() == StartingScenario.EARTH) {
        if (!solHasAdded) {
          solHasAdded = true;
          createSolSystem(sunx, suny, playerIndex, config);
          return;
        }
        playerInfo.setStartingScenario(StartingScenario.TEMPERATE_HUMID_SIZE12);
      }
      if (playerInfo.getStartingScenario()
          == StartingScenario.DESTROYED_HOME_PLANET) {
        if (!destroyedPlanetStartAdded) {
          destroyedPlanetStartAdded = true;
          reserverStartFromSpace(sunx, suny, playerIndex, config);
          return;
        }
        playerInfo.setStartingScenario(StartingScenario.TEMPERATE_HUMID_SIZE12);
      }
      if (playerInfo.getStartingScenario()
          == StartingScenario.FROM_ANOTHER_GALAXY) {
        reserverStartFromSpace(sunx, suny, playerIndex, config);
        return;
      }
    }
    if (playerIndex == -1 && !solHasAdded
        && DiceGenerator.getRandom(99) < 10) {
      solHasAdded = true;
      createSolSystem(sunx, suny, -1, config);
      return;
    }
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
    solarSystem = StarMapUtilities.setSolarSystem(solarSystem, sx, sy,
        getMaxX(), getMaxY());
    Sun sun = new Sun(new Coordinate(sx, sy), nameGenerator);
    starMap.getSunList().add(sun);
    int sunNumber = starMap.getSunList().size() - 1;
    SquareInfo info = new SquareInfo(SquareInfo.TYPE_SUN, sunNumber);
    place3x3SquareInfo(sx, sy, info);
    SunType sunType = SunType.getRandomType();
    if (playerIndex != -1) {
      // Realms start from red star aka sun.
      sunType = SunType.RED_STAR;
    }
    placeSunTiles(sx, sy, sunType);
    int planets = 0;
    int startingPlanet = DiceGenerator.getRandom(1, numberOfPlanets);
    while (planets < numberOfPlanets) {
      int px = sx + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
      if (is9NeighboursEmpty(px, py)) {
        planets++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets, false);
        planet.setPlanetType(PlanetTypes.getRandomPlanetType(false));
        if (planets == startingPlanet && playerIndex != -1) {
          PlayerInfo playerInfo = starMap.getPlayerList()
              .getPlayerInfoByIndex(playerIndex);
          playerInfo.setElderRealm(config.getPlayerElderRealm(playerIndex));
          planet.setRadiationLevel(RadiationType.NO_RADIATION);
          if (playerInfo.getStartingScenario()
              == StartingScenario.TEMPERATE_ARID_SIZE12) {
            planet.setTemperatureType(TemperatureType.TEMPERATE);
            planet.setWaterLevel(WaterLevelType.ARID);
            planet.setGroundSize(12);
          } else if (playerInfo.getStartingScenario()
              == StartingScenario.TEMPERATE_MARINE_SIZE9) {
            planet.setTemperatureType(TemperatureType.TEMPERATE);
            planet.setWaterLevel(WaterLevelType.MARINE);
            planet.setGroundSize(9);
          } else if (playerInfo.getStartingScenario()
              == StartingScenario.TEMPERATE_MARINE_SIZE14) {
            planet.setTemperatureType(TemperatureType.TEMPERATE);
            planet.setWaterLevel(WaterLevelType.MARINE);
            planet.setGroundSize(14);
          } else if (playerInfo.getStartingScenario()
              == StartingScenario.COLD_HUMID_SIZE12) {
            planet.setTemperatureType(TemperatureType.COLD);
            planet.setWaterLevel(WaterLevelType.HUMID);
            planet.setGroundSize(12);
          } else if (playerInfo.getStartingScenario()
              == StartingScenario.HOT_ARID_SIZE12) {
            planet.setTemperatureType(TemperatureType.HOT);
            planet.setWaterLevel(WaterLevelType.ARID);
            planet.setGroundSize(12);
          } else if (playerInfo.getStartingScenario()
              == StartingScenario.TROPICAL_HUMID_SIZE12) {
            planet.setTemperatureType(TemperatureType.TROPICAL);
            planet.setWaterLevel(WaterLevelType.HUMID);
            planet.setGroundSize(12);
          } else if (playerInfo.getStartingScenario()
              == StartingScenario.FARMING_PLANET) {
            planet.setTemperatureType(TemperatureType.TEMPERATE);
            planet.setWaterLevel(WaterLevelType.ARID);
            planet.setGroundSize(12);
          } else {
            planet.setTemperatureType(TemperatureType.TEMPERATE);
            planet.setWaterLevel(WaterLevelType.HUMID);
            planet.setGroundSize(12);
          }
          planet.generateGravityBasedOnSize();
          planet.generateWorldType();
          planet.setAmountMetalInGround(HOMEWORLD_METAL);
          planet.setHomeWorldId(playerInfo.getRace().getId());
          planet.setStartRealmIndex(playerIndex);
          if (!elderRealmStart || playerInfo.isElderRealm()) {
            createRealmToPlanet(planet, playerInfo, playerIndex);
          }
        } else {
          planet.setPlanetaryEvent(PlanetaryEvent.getRandomEvent(
              planet.getPlanetType(), config.getChanceForPlanetaryEvent()));
          planet.setRadiationLevel(SunType.getRadiation(sunType));
          planet.setTemperatureType(SunType.getTemperature(sunType));
          planet.generateGravityBasedOnSize();
          planet.generateWaterLevelBasedOnTemperature();
          planet.generateWorldType();
          planet.setEventActivation(false);
        }
        starMap.getPlanetList().add(planet);
        int planetNumber = starMap.getPlanetList().size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        starMap.setSquareInfo(px, py, info);
        starMap.setTile(px, py, planet.getPlanetType().getTileIndex());
      }
    }
    int gasGiants = 0;
    int loops = 0;
    while (gasGiants < numberOfGasGiants) {
      loops++;
      if (loops > 100) {
        break;
      }
      int px = sx + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
      if (is16NeighboursEmpty(px, py)) {
        gasGiants++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets + gasGiants, true);
        planet.setPlanetType(PlanetTypes.getRandomPlanetType(true));
        starMap.getPlanetList().add(planet);
        int planetNumber = starMap.getPlanetList().size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, planetNumber);
        place2x2SquareInfo(px, py, info);
        switch (planet.getPlanetTypeIndex()) {
        case 0: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.GAS_GIANT_1_NW),
              Tiles.getTileByName(TileNames.GAS_GIANT_1_NE),
              Tiles.getTileByName(TileNames.GAS_GIANT_1_SW),
              Tiles.getTileByName(TileNames.GAS_GIANT_1_SE));
          break;
        }
        case 1: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.GAS_GIANT_2_NW),
              Tiles.getTileByName(TileNames.GAS_GIANT_2_NE),
              Tiles.getTileByName(TileNames.GAS_GIANT_2_SW),
              Tiles.getTileByName(TileNames.GAS_GIANT_2_SE));
          break;
        }
        case 2: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.GAS_GIANT_3_NW),
              Tiles.getTileByName(TileNames.GAS_GIANT_3_NE),
              Tiles.getTileByName(TileNames.GAS_GIANT_3_SW),
              Tiles.getTileByName(TileNames.GAS_GIANT_3_SE));
          break;
        }
        case 31: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.ICEGIANT1_NW),
              Tiles.getTileByName(TileNames.ICEGIANT1_NE),
              Tiles.getTileByName(TileNames.ICEGIANT1_SW),
              Tiles.getTileByName(TileNames.ICEGIANT1_SE));
          break;
        }
        case 32: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.ICEGIANT2_NW),
              Tiles.getTileByName(TileNames.ICEGIANT2_NE),
              Tiles.getTileByName(TileNames.ICEGIANT2_SW),
              Tiles.getTileByName(TileNames.ICEGIANT2_SE));
          break;
        }
        default:
          throw new IllegalArgumentException("Unexpected gas giant type:"
             + planet.getPlanetTypeIndex());
        }
      }
    }
  }

  /**
   * Create Solar System
   * @param sx Sun's about coordinates
   * @param sy Sun's about coordinates
   * @param numberOfPlanets Number of planets to Solar System
   * @param numberOfGasGiants Number of Gas Giants to Solar System
   * @param config GalaxyConfig
   */
  private void createSolarSystem(final int sx, final int sy,
      final int numberOfPlanets, final int numberOfGasGiants,
      final GalaxyConfig config) {
    createSolarSystem(sx, sy, numberOfPlanets, numberOfGasGiants, -1, config);
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
    boolean elderRealmStart = config.isElderRealmStart();
    int numberOfPlanets = 4;
    int numberOfGasGiants = 4;
    // The Sun
    int sx = sunx + DiceGenerator.getRandom(-1, 1);
    int sy = suny + DiceGenerator.getRandom(-1, 1);
    solarSystem = StarMapUtilities.setSolarSystem(solarSystem, sx, sy,
        getMaxX(), getMaxY());
    Sun sun = new Sun(new Coordinate(sx, sy), nameGenerator);
    starMap.getSunList().add(sun);
    int sunNumber = starMap.getSunList().size() - 1;
    SquareInfo info = new SquareInfo(SquareInfo.TYPE_SUN, sunNumber);
    place3x3SquareInfo(sx, sy, info);
    // Sol has sun type 0.
    SunType sunType = SunType.RED_STAR;
    placeSunTiles(sx, sy, sunType);
    int planets = 0;
    while (planets < numberOfPlanets) {
      int px = sx + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
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
            PlayerInfo playerInfo = starMap.getPlayerList()
                .getPlayerInfoByIndex(playerIndex);
            playerInfo.setElderRealm(config.getPlayerElderRealm(playerIndex));
            planet.setAmountMetalInGround(HOMEWORLD_METAL);
            planet.setHomeWorldId(playerInfo.getRace().getId());
            planet.setStartRealmIndex(playerIndex);
            if (!elderRealmStart) {
              createRealmToPlanet(planet, playerInfo, playerIndex);
            } else if (playerInfo.isElderRealm()) {
              createRealmToPlanet(planet, playerInfo, playerIndex);
            }
          } else {
            WeightedList<PlanetaryEvent> list = new WeightedList<>();
            list.add(1, PlanetaryEvent.ANCIENT_FACTORY);
            list.add(1, PlanetaryEvent.ANCIENT_LAB);
            list.add(1, PlanetaryEvent.ANCIENT_PALACE);
            list.add(1, PlanetaryEvent.ANCIENT_TEMPLE);
            list.add(1, PlanetaryEvent.BLACK_MONOLITH);
            list.add(1, PlanetaryEvent.ANCIENT_ARTIFACT);
            planet.setPlanetaryEvent(list.pickRandom());
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
            WeightedList<PlanetaryEvent> list = new WeightedList<>();
            list.add(1, PlanetaryEvent.ANCIENT_FACTORY);
            list.add(1, PlanetaryEvent.ANCIENT_LAB);
            list.add(1, PlanetaryEvent.BLACK_MONOLITH);
            list.add(1, PlanetaryEvent.ANCIENT_ARTIFACT);
            planet.setPlanetaryEvent(list.pickRandom());
          }
        }
        starMap.getPlanetList().add(planet);
        int planetNumber = starMap.getPlanetList().size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        starMap.setSquareInfo(px, py, info);
        starMap.setTile(px, py, planet.getPlanetType().getTileIndex());
      }
    }
    int gasGiants = 0;
    int loops = 0;
    while (gasGiants < numberOfGasGiants) {
      loops++;
      if (loops > 100) {
        break;
      }
      int px = sx + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-StarMap.SOLAR_SYSTEM_WIDTH,
          StarMap.SOLAR_SYSTEM_WIDTH);
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
        starMap.getPlanetList().add(planet);
        int planetNumber = starMap.getPlanetList().size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, planetNumber);
        place2x2SquareInfo(px, py, info);
        switch (planet.getPlanetTypeIndex()) {
        case 0: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.GAS_GIANT_1_NW),
              Tiles.getTileByName(TileNames.GAS_GIANT_1_NE),
              Tiles.getTileByName(TileNames.GAS_GIANT_1_SW),
              Tiles.getTileByName(TileNames.GAS_GIANT_1_SE));
          break;
        }
        case 1: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.GAS_GIANT_2_NW),
              Tiles.getTileByName(TileNames.GAS_GIANT_2_NE),
              Tiles.getTileByName(TileNames.GAS_GIANT_2_SW),
              Tiles.getTileByName(TileNames.GAS_GIANT_2_SE));
          break;
        }
        case 2: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.GAS_GIANT_3_NW),
              Tiles.getTileByName(TileNames.GAS_GIANT_3_NE),
              Tiles.getTileByName(TileNames.GAS_GIANT_3_SW),
              Tiles.getTileByName(TileNames.GAS_GIANT_3_SE));
          break;
        }
        case 29: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.JUPITER_NW),
              Tiles.getTileByName(TileNames.JUPITER_NE),
              Tiles.getTileByName(TileNames.JUPITER_SW),
              Tiles.getTileByName(TileNames.JUPITER_SE));
          break;
        }
        case 30: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.SATURN_NW),
              Tiles.getTileByName(TileNames.SATURN_NE),
              Tiles.getTileByName(TileNames.SATURN_SW),
              Tiles.getTileByName(TileNames.SATURN_SE));
          break;
        }
        case 31: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.ICEGIANT1_NW),
              Tiles.getTileByName(TileNames.ICEGIANT1_NE),
              Tiles.getTileByName(TileNames.ICEGIANT1_SW),
              Tiles.getTileByName(TileNames.ICEGIANT1_SE));
          break;
        }
        case 32: {
          place2x2Tiles(px, py, Tiles.getTileByName(TileNames.ICEGIANT2_NW),
              Tiles.getTileByName(TileNames.ICEGIANT2_NE),
              Tiles.getTileByName(TileNames.ICEGIANT2_SW),
              Tiles.getTileByName(TileNames.ICEGIANT2_SE));
          break;
        }
        default:
          throw new IllegalArgumentException("Unexpected gas giant type:"
             + planet.getPlanetTypeIndex());
        }
      }
    }
  }

  /**
   * Build Black hole. This will build black hole in the galaxy center.
   */
  private void buildBlackHole() {
    int cx = starMap.getMaxX() / 2;
    int cy = starMap.getMaxY() / 2;
    starMap.setTile(cx, cy, Tiles.getTileByName(TileNames.BLACKHOLE_C));
    starMap.setTile(cx - 1, cy - 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_NW));
    starMap.setTile(cx, cy - 1, Tiles.getTileByName(TileNames.BLACKHOLE_N));
    starMap.setTile(cx + 1, cy - 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_NE));
    starMap.setTile(cx - 1, cy, Tiles.getTileByName(TileNames.BLACKHOLE_W));
    starMap.setTile(cx + 1, cy, Tiles.getTileByName(TileNames.BLACKHOLE_E));
    starMap.setTile(cx - 1, cy + 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_SW));
    starMap.setTile(cx, cy + 1, Tiles.getTileByName(TileNames.BLACKHOLE_S));
    starMap.setTile(cx + 1, cy + 1,
        Tiles.getTileByName(TileNames.BLACKHOLE_SE));
    place3x3SquareInfo(cx, cy,
        new SquareInfo(SquareInfo.TYPE_BLACKHOLE_CENTER, 0),
        new SquareInfo(SquareInfo.TYPE_BLACKHOLE, 0));
    /* Mark blackhole as solar system, so no star will replace it. */
    for (int i = -2; i < 3; i++) {
      for (int j = -2; j < 2; j++) {
        solarSystem[cx + i][cy + j] = 1;
      }
    }
  }

  /**
   * Place 3x3 square info
   * @param cx Center X coordinate
   * @param cy Center Y coordinate
   * @param info SquareInfo
   */
  private void place3x3SquareInfo(final int cx, final int cy,
      final SquareInfo info) {
    place3x3SquareInfo(cx, cy, info, info);
  }

  /**
   * Place 3x3 square infos
   * @param cx Center X coordinate
   * @param cy Center Y coordinate
   * @param centerInfo SquareInfo on center
   * @param info SquareInfo on borders
   */
  private void place3x3SquareInfo(final int cx, final int cy,
      final SquareInfo centerInfo, final SquareInfo info) {
    starMap.setSquareInfo(cx - 1, cy - 1, info);
    starMap.setSquareInfo(cx, cy - 1, info);
    starMap.setSquareInfo(cx + 1, cy - 1, info);
    starMap.setSquareInfo(cx - 1, cy, info);
    starMap.setSquareInfo(cx, cy, centerInfo);
    starMap.setSquareInfo(cx + 1, cy, info);
    starMap.setSquareInfo(cx - 1, cy + 1, info);
    starMap.setSquareInfo(cx, cy + 1, info);
    starMap.setSquareInfo(cx + 1, cy + 1, info);
  }

  /**
   * Place 2x2 square info
   * @param tx Top left X coordinate
   * @param ty Top left Y coordinate
   * @param info SquareInfo
   */
  private void place2x2SquareInfo(final int tx, final int ty,
      final SquareInfo info) {
    starMap.setSquareInfo(tx, ty, info);
    starMap.setSquareInfo(tx + 1, ty, info);
    starMap.setSquareInfo(tx, ty + 1, info);
    starMap.setSquareInfo(tx + 1, ty + 1, info);
  }

  /**
   * Place 2x2 tiles into starmap.
   * @param tx Top corner X coordinate AKA NorthWest tile
   * @param ty Top corner Y coordinate AKA NorthWest tile
   * @param northWest Tile in north west corner
   * @param northEast Tile in north east corner
   * @param southWest Tile in south west corner
   * @param southEast Tile in south east corner
   */
  private void place2x2Tiles(final int tx, final int ty, final Tile northWest,
      final Tile northEast, final Tile southWest, final Tile southEast) {
    starMap.setTile(tx, ty, northWest);
    starMap.setTile(tx + 1, ty, northEast);
    starMap.setTile(tx, ty + 1, southWest);
    starMap.setTile(tx + 1, ty + 1, southEast);
  }

  /**
   * Place Sun/Star tiles
   * @param cx Center of Star X coordinate
   * @param cy Center of Star Y coordinate
   * @param sunType SunType
   */
  private void placeSunTiles(final int cx, final int cy,
      final SunType sunType) {
    starMap.setTile(cx, cy, Tiles.getSunTile(TileNames.SUN_C, sunType));
    starMap.setTile(cx - 1, cy - 1, Tiles.getSunTile(TileNames.SUN_NW,
        sunType));
    starMap.setTile(cx, cy - 1, Tiles.getSunTile(TileNames.SUN_N, sunType));
    starMap.setTile(cx + 1, cy - 1, Tiles.getSunTile(TileNames.SUN_NE,
        sunType));
    starMap.setTile(cx - 1, cy, Tiles.getSunTile(TileNames.SUN_W, sunType));
    starMap.setTile(cx + 1, cy, Tiles.getSunTile(TileNames.SUN_E, sunType));
    starMap.setTile(cx - 1, cy + 1, Tiles.getSunTile(TileNames.SUN_SW,
        sunType));
    starMap.setTile(cx, cy + 1, Tiles.getSunTile(TileNames.SUN_S, sunType));
    starMap.setTile(cx + 1, cy + 1, Tiles.getSunTile(TileNames.SUN_SE,
        sunType));
  }

  /**
   * Generate random system.
   * @param config GalaxyConfig.
   */
  private void generateRandomSystems(final GalaxyConfig config) {
 // Random system
    int loop = 0;
    while (loop < MAX_LOOPS) {
      int sx = DiceGenerator.getRandom(StarMap.SOLAR_SYSTEM_WIDTH,
          getMaxX() - StarMap.SOLAR_SYSTEM_WIDTH);
      int sy = DiceGenerator.getRandom(StarMap.SOLAR_SYSTEM_WIDTH,
          getMaxY() - StarMap.SOLAR_SYSTEM_WIDTH);
      int planets = DiceGenerator.getRandom(1, 6);
      int gasGiants = DiceGenerator.getRandom(3);
      if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, getMaxX(),
          getMaxY(), config.getSolarSystemDistance()) == 0) {
        createSolarSystem(sx, sy, planets, gasGiants, config);
        int full = StarMapUtilities.getSystemFullness(solarSystem, getMaxX(),
            getMaxY());
        if (full > 60) {
          // Enough solar systems
          break;
        }
      }
      loop++;
    }
  }

  /**
   * Generate rogue planets.
   * @param config GalaxyConfig
   */
  private void generateRoguePlanets(final GalaxyConfig config) {
    // Create rogue planets
    if (config.getNumberOfRoguePlanets() != GalaxyConfig.ROGUE_PLANETS_NONE) {
      int loop = 0;
      int roguePlanets = config.getNumberOfRoguePlanets()
          * (config.getGalaxySizeIndex() + 1);
      RoguePlanetNameGenerator ng = new RoguePlanetNameGenerator();
      Tile empty = Tiles.getTileByName(TileNames.EMPTY);
      for (int i = 0; i < roguePlanets; i++) {
        while (loop < MAX_LOOPS) {
          int sx = DiceGenerator.getRandom(1,
              getMaxX() - 2);
          int sy = DiceGenerator.getRandom(1,
              getMaxY() - 2);
          if (starMap.getTile(sx, sy).getIndex() == empty.getIndex()
              && starMap.getPlanetByCoordinate(sx, sy) == null
              && starMap.locateSolarSystem(sx, sy) == null) {
            String name = ng.generate();
            Planet planet = new Planet(new Coordinate(sx, sy), name, 0, false);
            planet.setPlanetaryEvent(PlanetaryEvent.getRandomEvent(
                planet.getPlanetType(), config.getChanceForPlanetaryEvent()));
            planet.setEventActivation(false);
            starMap.getPlanetList().add(planet);
            int planetNumber = starMap.getPlanetList().size() - 1;
            SquareInfo info = new SquareInfo(SquareInfo.TYPE_PLANET,
                planetNumber);
            starMap.setSquareInfo(sx, sy, info);
            starMap.setTile(sx, sy, planet.getPlanetType().getTileIndex());
            break;
          }
          loop++;
        }
      }
    }
  }

  /**
   * Generate Deep Space Anchor.
   * @param config GalaxyConfig
   */
  private void generateDeepSpaceAnchors(final GalaxyConfig config) {
    // Create random deep space anchors
    int loop = 0;
    int numberOfAnchors = config.getMaxPlayers() * 3;
    if (config.getGalaxySizeIndex() >= 2) {
      numberOfAnchors = numberOfAnchors
          + 2 * (config.getGalaxySizeIndex() - 1);
    }
    int pirateLairs = 0;
    int value = config.getSpacePiratesLevel();
    value = value - 1;
    if (value > 0) {
      pirateLairs = numberOfAnchors * value * 20 / 100;
    } else if (value == 0) {
      pirateLairs = numberOfAnchors * 10 / 100;
      if (pirateLairs < 1) {
        pirateLairs = 1;
      }
    }
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i = 0; i < numberOfAnchors; i++) {
      while (loop < MAX_LOOPS) {
        int sx = DiceGenerator.getRandom(1,
            getMaxX() - 2);
        int sy = DiceGenerator.getRandom(1,
            getMaxY() - 2);
        if (starMap.getTileIndex(sx, sy) == empty.getIndex()
            && starMap.getPlanetByCoordinate(sx, sy) == null) {
          Tile anchor = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1);
          starMap.setTile(sx, sy, anchor.getIndex());
          PlayerInfo board = starMap.getPlayerList().getSpacePiratePlayer();
          if (board != null && i < pirateLairs) {
            starMap.addSpacePirateLair(sx, sy, board);
          }
          break;
        }
        loop++;
      }
    }
  }

  /**
   * Generate space anomalies.
   * @param config GalaxyConfig
   */
  private void generateSpaceAnomalies(final GalaxyConfig config) {
    // Create random space anomalies
    int loop = 0;
    int numberOfAnomalies = 0;
    boolean harmful = false;
    boolean pirate = starMap.getPlayerList().getSpacePiratePlayer() != null;
    boolean monsters = starMap.getPlayerList().getSpaceMonsterPlayer() != null;
    if (config.getSpaceAnomaliesLevel() == 1) {
      numberOfAnomalies = config.getMaxPlayers() * 5;
    }
    if (config.getSpaceAnomaliesLevel() == 2) {
      numberOfAnomalies = config.getMaxPlayers() * 7;
      harmful = true;
    }
    if (config.getGalaxySizeIndex() >= 2) {
      numberOfAnomalies = numberOfAnomalies
          + 10 * (config.getGalaxySizeIndex() - 1);
    }
    if (numberOfAnomalies < 20 && config.getSpaceAnomaliesLevel() > 0) {
      numberOfAnomalies = 20;
    }
    if (config.getSpaceAnomaliesLevel() > 0) {
      // Destroyed planet
      numberOfAnomalies++;
    }
    int numberOfArtifacts = config.getMaxPlayers() * 3
        + 3 * config.getGalaxySizeIndex();
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i = 0; i < numberOfAnomalies + numberOfArtifacts; i++) {
      while (loop < MAX_LOOPS) {
        int sx = DiceGenerator.getRandom(1, getMaxX() - 2);
        int sy = DiceGenerator.getRandom(1, getMaxY() - 2);
        if (starMap.getTileIndex(sx, sy) == empty.getIndex()
            && starMap.getPlanetByCoordinate(sx, sy) == null) {
          String tileName = TileNames.SPACE_ANOMALY_CREDITS;
          if (i == 0) {
            tileName = TileNames.SPACE_ANOMALY_DESTROYED_PLANET;
          } else if (i < numberOfAnomalies) {
            tileName = TileNames.getRandomSpaceAnomaly(harmful, pirate,
                monsters);
          } else {
            tileName = TileNames.SPACE_ANOMALY_ANCIENT_ARTIFACT;
          }
          Tile anomaly = Tiles.getTileByName(tileName);
          starMap.setTile(sx, sy, anomaly);
          break;
        }
        loop++;
      }
    }
    int bx = -1;
    int by = -1;
    double bestValue = -1;
    ArrayList<Coordinate> anchors = new ArrayList<>();
    for (int sy = 0; sy < getMaxY(); sy++) {
      for (int sx = 0; sx < getMaxX(); sx++) {
        if (starMap.getTileIndex(sx, sy) == empty.getIndex()
            && starMap.getPlanetByCoordinate(sx, sy) == null
            && starMap.locateSolarSystem(sx, sy) == null) {
          Coordinate coord = new Coordinate(sx, sy);
          Coordinate center = new Coordinate(getMaxX() / 2, getMaxY() / 2);
          double shortestDistance = getMaxX() * 10;
          double centerDist = center.calculateDistance(coord);
          for (Sun sun : starMap.getSunList()) {
            double value = coord.calculateDistance(
                new Coordinate(sun.getCenterX(), sun.getCenterY()));
            if (value < shortestDistance) {
              shortestDistance = value;
            }
          }
          if (shortestDistance > bestValue
              && centerDist > (getMaxX() / 2) * 0.75
              && centerDist < (getMaxX() / 2) * 0.95) {
            bx = sx;
            by = sy;
            bestValue = shortestDistance;
          }
        }
        Tile tile = Tiles.getTileByIndex(starMap.getTileIndex(sx, sy));
        if (tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
            || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2)
            || tile.getName().equals(TileNames.SPACE_ANOMALY_DSA)
            || tile.getName().equals(TileNames.SPACE_ANOMALY_LAIR)) {
          anchors.add(new Coordinate(sx, sy));
        }
      }
    }
    if (bestValue > 0) {
      Tile anomaly = Tiles.getTileByName(TileNames.SPACE_ANOMALY_NEWS_STATION);
      starMap.setTile(bx, by, anomaly);
    }
    // TODO This is for ascension victory
    //generateAscensionPortal(ascensionPortalX, ascensionPortalY);
/*    int ascensionPortalX = -1;
    int ascensionPortalY = -1;
    if (anchors.size() > 0) {
      for (int i = 0; i < 100; i++) {
        Coordinate coordinate = DiceGenerator.pickRandom(anchors);
        for (int j = 0; j < 4; j++) {
          int mx = 0;
          int my = 0;
          if (j == 0) {
            my = -1;
          }
          if (j == 1) {
            mx = 1;
          }
          if (j == 2) {
            my = 1;
          }
          if (j == 3) {
            mx = -1;
          }
          int x = coordinate.getX() + mx;
          int y = coordinate.getY() + my;
          if (starMap.isValidCoordinate(x, y)
              && starMap.getTileIndex(x, y) == 0) {
            ascensionPortalX = x;
            ascensionPortalY = y;
          }
          if (ascensionPortalY != -1 && DiceGenerator.getBoolean()) {
            break;
          }
        }
        if (ascensionPortalY != -1) {
          break;
        }
      }
    }*/

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

  /**
   * Checks if 3x3 area is empty around the coordinate
   * @param x X coordinate
   * @param y Y coordinate
   * @return true if all are empty false otherwise
   */
  private boolean is9NeighboursEmpty(final int x, final int y) {
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        boolean validCoordinate = starMap.isValidCoordinate(x + i, y + j);
        if (!validCoordinate
            || validCoordinate && starMap.getTiles()[x + i][y + j] != 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Make starting tutorial texts about starting planet.
   * @param playerInfo Realm must be human player.
   * @param planet Planet
   */
  private void makeStartingTutorialTexts(final PlayerInfo playerInfo,
      final Planet planet) {
    if (Game.getTutorial() == null || !playerInfo.isHuman()
        || !starMap.isTutorialEnabled()) {
      return;
    }

    String tutorialText = Game.getTutorial().showTutorialText(0);
    if (tutorialText != null) {
      Message msg = new Message(MessageType.INFORMATION, tutorialText,
          Icons.getIconByName(Icons.ICON_TUTORIAL));
      playerInfo.getMsgList().addNewMessage(msg);
    }

    tutorialText = Game.getTutorial().showTutorialText(1);
    if (tutorialText != null) {
      Message msg = new Message(MessageType.PLANETARY, tutorialText,
          Icons.getIconByName(Icons.ICON_TUTORIAL));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(planet.getName());
      playerInfo.getMsgList().addNewMessage(msg);
    }

    tutorialText = Game.getTutorial().showTutorialText(2);
    if (tutorialText != null) {
      Message msg = new Message(MessageType.PLANETARY, tutorialText,
          Icons.getIconByName(Icons.ICON_TUTORIAL));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(planet.getName());
      playerInfo.getMsgList().addNewMessage(msg);
    }
  }

  /**
   * Make starting tutorial texts about starting deep space.
   * @param playerInfo Realm must be human player.
   * @param coord Coordinate where to start
   */
  private void makeStartingTutorialTextsInDeepSpace(final PlayerInfo playerInfo,
      final Coordinate coord) {
    if (Game.getTutorial() == null || !playerInfo.isHuman()
        || !starMap.isTutorialEnabled()) {
      return;
    }

    String tutorialText = Game.getTutorial().showTutorialText(0);
    if (tutorialText != null) {
      Message msg = new Message(MessageType.INFORMATION, tutorialText,
          Icons.getIconByName(Icons.ICON_TUTORIAL));
      playerInfo.getMsgList().addNewMessage(msg);
    }

    tutorialText = Game.getTutorial().showTutorialText(3);
    if (tutorialText != null) {
      Message msg = new Message(MessageType.FLEET, tutorialText,
          Icons.getIconByName(Icons.ICON_TUTORIAL));
      msg.setCoordinate(coord);
      msg.setMatchByString("Colony #0");
      playerInfo.getMsgList().addNewMessage(msg);
    }
  }

  /**
   * Make Starting fleet tutorial texts. This will handle tutorial text for
   * both colony and scout ship.
   * @param playerInfo Realm, must be human player
   * @param fleet Fleet where tutorial text are placed.
   */
  private void makeStartingFleetTutorialTexts(final PlayerInfo playerInfo,
      final Fleet fleet) {
    Ship ship = fleet.getFirstShip();
    if (ship.isColonyModule()) {
      if (Game.getTutorial() != null && playerInfo.isHuman()
          && starMap.isTutorialEnabled()) {
        String tutorialText = Game.getTutorial().showTutorialText(7);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.FLEET, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(fleet.getCoordinate());
          msg.setMatchByString(fleet.getName());
          playerInfo.getMsgList().addNewMessage(msg);
        }
      }
    } else {
      if (Game.getTutorial() != null && playerInfo.isHuman()
          && starMap.isTutorialEnabled()) {
        String tutorialText = Game.getTutorial().showTutorialText(5);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.FLEET, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(fleet.getCoordinate());
          msg.setMatchByString(fleet.getName());
          playerInfo.getMsgList().addNewMessage(msg);
        }
        tutorialText = Game.getTutorial().showTutorialText(6);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.FLEET, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(fleet.getCoordinate());
          msg.setMatchByString(fleet.getName());
          playerInfo.getMsgList().addNewMessage(msg);
        }
      }
    }

  }

  /**
   * Checks if 4x4 area is empty around the coordinate.
   * Note this is not centered. This is more closer
   * on left upper corner but not in the corner.
   * @param x X coordinate
   * @param y Y coordinate
   * @return true if all are empty false otherwise
   */
  private boolean is16NeighboursEmpty(final int x, final int y) {
    boolean result = true;
    for (int i = -1; i < 4; i++) {
      for (int j = -1; j < 4; j++) {
        if (starMap.isValidCoordinate(x + i, y + j)
            && starMap.getTiles()[x + i][y + j] == 0) {
          result = true;
        } else {
          return false;
        }
      }
    }
    return result;
  }

}
