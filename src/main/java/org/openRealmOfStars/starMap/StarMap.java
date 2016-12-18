package org.openRealmOfStars.starMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.AI.PlanetHandling.PlanetHandling;
import org.openRealmOfStars.AI.Research.Research;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;
import org.openRealmOfStars.utilities.repository.PlanetRepository;
import org.openRealmOfStars.utilities.repository.SunRepository;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Class planet
 *
 */
public class StarMap {

  /**
   * Maximum number of player
   */
  public static final int MAX_PLAYERS = 8;

  /**
   * Maximum map size x
   */
  public static final int MAX_MAP_SIZE_X = 256;

  /**
   * Maximum map size y
   */
  public static final int MAX_MAP_SIZE_Y = 256;

  /**
   * Solar system width
   */
  public static final int SOLAR_SYSTEM_WIDTH = 7;

  /**
   * Star map's maximum X coordinate
   */
  private int maxX;
  /**
   * Star map's maximum Y coordinate
   */
  private int maxY;

  /**
   * Tiles to show. Contains only planets, suns, asteroids etc.
   */
  private int[][] tiles;

  /**
   * Sector information if square contains planet, sun and is it visible or not
   */
  private SquareInfo[][] tileInfo;

  /**
   * Culture level for each sector
   */
  private CulturePower[][] culture;

  /**
   * Cursor X coordinate
   */
  private int cursorX;
  /**
   * Cursor Y coordinate
   */
  private int cursorY;

  /**
   * Draw X coordinate
   */
  private int drawX;
  /**
   * Draw Y Coordinate
   */
  private int drawY;

  /**
   * List of suns in star map
   */
  private ArrayList<Sun> sunList;

  /**
   * List of planets in star map
   */
  private ArrayList<Planet> planetList;

  /**
   * List of players
   */
  private PlayerList players;

  /**
   * Game turn
   */
  private int turn;

  /**
   * Fleet tiles on map
   */
  private FleetTileInfo[][] fleetTiles;

  /**
   * AI turn number
   */
  private int aiTurnNumber;

  /**
   * AI fleet for current AI number
   */
  private Fleet aiFleet;

  /**
   * Magic string to save game files
   */
  public static final String MAGIC_STRING = "OROS-SAVE-GAME-0.2";

  /**
   * Maximum amount of looping when finding free solar system spot.
   */
  private static final int MAX_LOOPS = 10000;
  /**
   * Constructor for StarMap. Generates universe with galaxy config and
   * players
   * @param config Galaxy config
   * @param players Players
   */
  public StarMap(final GalaxyConfig config, final PlayerList players) {
    maxX = config.getSizeX();
    maxY = config.getSizeY();
    this.players = players;
    this.players.initVisibilityMaps(maxX, maxY);
    drawX = 0;
    drawY = 0;
    tiles = new int[maxX][maxY];
    int[][] solarSystem = new int[maxX][maxY];
    tileInfo = new SquareInfo[maxX][maxY];
    fleetTiles = new FleetTileInfo[maxX][maxY];
    culture = new CulturePower[maxX][maxY];
    sunList = new ArrayList<>();
    planetList = new ArrayList<>();
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i = 0; i < maxX; i++) {
      for (int j = 0; j < maxY; j++) {
        tiles[i][j] = empty.getIndex();
        tileInfo[i][j] = SquareInfo.EMPTY_TILE;
        culture[i][j] = new CulturePower(PlayerList.MAX_PLAYERS);
        solarSystem[i][j] = 0;
      }
    }
    turn = 0;
    aiTurnNumber = 0;
    aiFleet = null;
    if (config.getStartingPosition() == GalaxyConfig.START_POSITION_BORDER) {
      createBorderStartingSystems(config, solarSystem);
    }
    if (config.getStartingPosition() == GalaxyConfig.START_POSITION_RANDOM) {
      for (int i = 0; i < config.getMaxPlayers(); i++) {
        while (true) {
          int sx = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
              maxX - SOLAR_SYSTEM_WIDTH);
          int sy = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
              maxX - SOLAR_SYSTEM_WIDTH);
          int planets = DiceGenerator.getRandom(3, 5);
          int gasGiants = DiceGenerator.getRandom(2);
          if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, maxX, maxY,
              config.getSolarSystemDistance()) == 0) {
            createSolarSystem(solarSystem, sx, sy, planets, gasGiants, i);
            break;
          }
        }
      }
    }
    // Random system
    int loop = 0;
    while (loop < MAX_LOOPS) {
      int sx = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
          maxX - SOLAR_SYSTEM_WIDTH);
      int sy = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
          maxX - SOLAR_SYSTEM_WIDTH);
      int planets = DiceGenerator.getRandom(1, 6);
      int gasGiants = DiceGenerator.getRandom(3);
      if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, maxX, maxY,
          config.getSolarSystemDistance()) == 0) {
        createSolarSystem(solarSystem, sx, sy, planets, gasGiants);
        int full = StarMapUtilities.getSystemFullness(solarSystem, maxX, maxY);
        if (full > 60) {
          // Enough solar systems
          break;
        }
      }
      loop++;
    }
  }

  /**
   * Create border starting solar system
   * @param config Galaxy Config
   * @param solarSystem Solar system map
   */
  private void createBorderStartingSystems(final GalaxyConfig config,
      final int[][] solarSystem) {
    // First starting Systems
    int sx = SOLAR_SYSTEM_WIDTH;
    int sy = SOLAR_SYSTEM_WIDTH;
    int planets = DiceGenerator.getRandom(3, 5);
    int gasGiants = DiceGenerator.getRandom(2);
    if (config.getMaxPlayers() == 2) {
      // First player
      sx = maxX / 2;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 0);

      // Second player
      sx = maxX / 2;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 1);

    } else if (config.getMaxPlayers() == 3) {
      // First player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 0);

      // Second player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 1);

      // Third player
      sx = maxX / 2;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 2);

    } else if (config.getMaxPlayers() >= 4) {
      // First player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 0);

      // Second player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 1);

      // Third player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 2);

      // Fourth player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 3);
    }

    if (config.getMaxPlayers() >= 5) {
      // Fifth player
      sx = maxX / 2;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 4);
    }
    if (config.getMaxPlayers() >= 6) {
      // Sixth player
      sx = maxX / 2;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 5);
    }
    if (config.getMaxPlayers() >= 7) {
      // Seventh player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = maxY / 2;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 6);
    }
    if (config.getMaxPlayers() == 8) {
      // Eight player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = maxY / 2;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      createSolarSystem(solarSystem, sx, sy, planets, gasGiants, 7);
    }
  }

  /**
   * Initialize StarMap from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public StarMap(final DataInputStream dis) throws IOException {
    String str = IOUtilities.readString(dis);
    if (str.equals(MAGIC_STRING)) {
      turn = dis.readInt();
      maxX = dis.readInt();
      maxY = dis.readInt();
      fleetTiles = new FleetTileInfo[maxX][maxY];
      culture = new CulturePower[maxX][maxY];
      sunList = new ArrayList<>();
      planetList = new ArrayList<>();
      tiles = new int[maxX][maxY];
      tileInfo = new SquareInfo[maxX][maxY];

      // Map data itself
      for (int x = 0; x < maxX; x++) {
        for (int y = 0; y < maxY; y++) {
          tiles[x][y] = dis.readInt();
          tileInfo[x][y] = new SquareInfo(dis);
          culture[x][y] = new CulturePower(PlayerList.MAX_PLAYERS);
        }
      }
      // Read suns
      int count = dis.readInt();
      for (int i = 0; i < count; i++) {
        sunList.add(new SunRepository().restoreSun(dis));
      }
      // Players first
      players = new PlayerList(dis);
      count = dis.readInt();
      for (int i = 0; i < count; i++) {
        Planet planet = new PlanetRepository().restorePlanet(dis, players);
        planetList.add(planet);
      }
    } else {
      if (str.startsWith("OROS-SAVE-GAME-")) {
        throw new IOException(
          "Stream does not contain correct StarMap information.\n"
              + "Maybe saved game is for older version...");
      }
      throw new IOException("Stream does not contain StarMap information!");
    }
  }

  /**
   * Save Game to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveGame(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, MAGIC_STRING);
    // Turn number
    dos.writeInt(turn);
    // Map size
    dos.writeInt(maxX);
    dos.writeInt(maxY);
    // Map data itself
    for (int x = 0; x < maxX; x++) {
      for (int y = 0; y < maxY; y++) {
        dos.writeInt(tiles[x][y]);
        tileInfo[x][y].writeSquareInfo(dos);
      }
    }
    // Write suns
    dos.writeInt(sunList.size());
    for (int i = 0; i < sunList.size(); i++) {
      Sun sun = sunList.get(i);
      new SunRepository().saveSun(dos, sun);
    }
    // Players first
    players.savePlayerList(dos);
    dos.writeInt(planetList.size());
    for (int i = 0; i < planetList.size(); i++) {
      new PlanetRepository().savePlanet(dos, planetList.get(i));
    }
  }

  /**
   * Check if coordinates are valid for this StarMap
   * @param x X coordinate
   * @param y y coordinate
   * @return true if valid and false if invalid
   */
  public boolean isValidCoordinate(final int x, final int y) {
    if (x >= 0 && y >= 0 && x < maxX && y < maxY) {
      return true;
    }
    return false;
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
        boolean validCoordinate = isValidCoordinate(x + i, y + j);
        if (!validCoordinate
            || validCoordinate && tiles[x + i][y + j] != 0) {
          return false;
        }
      }
    }
    return true;
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
        if (isValidCoordinate(x + i, y + j) && tiles[x + i][y + j] == 0) {
          result = true;
        } else {
          return false;
        }
      }
    }
    return result;
  }

  /**
   * Create Solar System
   * @param solarSystem map of solar systems
   * @param sx Sun's about coordinates
   * @param sy Sun's about coordinates
   * @param numberOfPlanets Number of planets to Solar System
   * @param numberOfGasGiants Number of Gas Giants to Solar System
   */
  private void createSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int numberOfPlanets, final int numberOfGasGiants) {
    createSolarSystem(solarSystem, sx, sy, numberOfPlanets, numberOfGasGiants,
        -1);
  }

  /**
   * Default amount of metal in home worlds.
   */
  private static final int HOMEWORLD_METAL = 8000;

  /**
   * Create Solar System
   * @param solarSystem map of solar systems
   * @param sunx Sun's about coordinates
   * @param suny Sun's about coordinates
   * @param planetsToCreate Number of planets to Solar System
   * @param gasGiantsToCreate Number of Gas Giants to Solar System
   * @param playerIndex if Player index is else than -1 then SolarSystem
   * is created as home system for that player index.
   */
  private void createSolarSystem(final int[][] solarSystem, final int sunx,
      final int suny, final int planetsToCreate, final int gasGiantsToCreate,
      final int playerIndex) {
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
    StarMapUtilities.setSolarSystem(solarSystem, sx, sy, getMaxX(), getMaxY());
    Sun sun = new Sun(new Coordinate(sx, sy), new RandomSystemNameGenerator());
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
    tiles[sx][sy] = Tiles.getTileByName(TileNames.SUN_C).getIndex();
    tiles[sx - 1][sy - 1] = Tiles.getTileByName(TileNames.SUN_NW).getIndex();
    tiles[sx][sy - 1] = Tiles.getTileByName(TileNames.SUN_N).getIndex();
    tiles[sx + 1][sy - 1] = Tiles.getTileByName(TileNames.SUN_NE).getIndex();
    tiles[sx - 1][sy] = Tiles.getTileByName(TileNames.SUN_W).getIndex();
    tiles[sx + 1][sy] = Tiles.getTileByName(TileNames.SUN_E).getIndex();
    tiles[sx - 1][sy + 1] = Tiles.getTileByName(TileNames.SUN_SW).getIndex();
    tiles[sx][sy + 1] = Tiles.getTileByName(TileNames.SUN_S).getIndex();
    tiles[sx + 1][sy + 1] = Tiles.getTileByName(TileNames.SUN_SE).getIndex();
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
        planet.setPlanetType(
            DiceGenerator.getRandom(Planet.PLANET_IMAGE_INDEX.length - 1));
        if (planets == 1 && playerIndex != -1) {
          PlayerInfo playerInfo = players.getPlayerInfoByIndex(playerIndex);
          Message msg = new Message(
              MessageType.PLANETARY, playerInfo.getEmpireName() + " starts at "
                  + planet.getName() + ".",
              Icons.getIconByName(Icons.ICON_CULTURE));
          msg.setCoordinate(planet.getCoordinate());
          msg.setMatchByString(planet.getName());
          playerInfo.getMsgList().addNewMessage(msg);
          planet.setPlanetOwner(playerIndex, playerInfo);
          planet.setRadiationLevel(1);
          planet.setGroundSize(12);
          planet.setAmountMetalInGround(HOMEWORLD_METAL);
          planet.addBuilding(BuildingFactory.createByName("Space port"));
          planet.setHomeWorldIndex(playerInfo.getRace().getIndex());
          if (playerInfo.getRace() == SpaceRace.MECHIONS) {
            planet.setWorkers(Planet.FOOD_FARMERS, 0);
            planet.setWorkers(Planet.METAL_MINERS, 0);
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
            planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
            planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          } else {
            planet.setWorkers(Planet.FOOD_FARMERS, 1);
            planet.setWorkers(Planet.METAL_MINERS, 0);
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
            planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
            planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          }
          ShipStat[] stats = playerInfo.getShipStatList();
          int count = 0;
          for (ShipStat stat : stats) {
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
            msg = new Message(MessageType.FLEET,
                fleet.getName() + " is waiting for orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setCoordinate(planet.getCoordinate());
            msg.setMatchByString(fleet.getName());
            playerInfo.getMsgList().addNewMessage(msg);
            count++;
          }

        }
        planetList.add(planet);
        int planetNumber = planetList.size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        tileInfo[px][py] = info;
        tiles[px][py] = planet.getPlanetImageIndex();
      }
    }
    int gasGiants = 0;
    while (gasGiants < numberOfGasGiants) {
      int px = sx + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      int py = sy + DiceGenerator.getRandom(-SOLAR_SYSTEM_WIDTH,
              SOLAR_SYSTEM_WIDTH);
      if (is16NeighboursEmpty(px, py)) {
        gasGiants++;
        Planet planet = new Planet(new Coordinate(px, py), sun.getName(),
            planets + gasGiants, true);
        planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
        planetList.add(planet);
        int planetNumber = planetList.size() - 1;
        info = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, planetNumber);
        switch (planet.getPlanetImageIndex()) {
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
        default:
          throw new IllegalArgumentException("Unexpected gas giant type:"
             + planet.getPlanetImageIndex());
        }
      }
    }
  }

  /**
   * Locate in which solar system coordinate is
   * @param x coordinate
   * @param y coordinate
   * @return Sun or null if not in any solar system
   */
  public Sun locateSolarSystem(final int x, final int y) {
    for (Sun sun : sunList) {
      if (x >= sun.getCenterX() - SOLAR_SYSTEM_WIDTH
          && x <= sun.getCenterX() + SOLAR_SYSTEM_WIDTH
          && y >= sun.getCenterY() - SOLAR_SYSTEM_WIDTH
          && y <= sun.getCenterY() + SOLAR_SYSTEM_WIDTH) {
        return sun;
      }
    }
    return null;
  }

  /**
   * Longest distance that should never be possible to get in map.
   */
  private static final double LONGEST_DISTANCE = 999999;
  /**
   * Get nearest uncharted Solar system for coordinate. This should never
   * return null. Unless there are no suns in galaxy.
   * @param x coordinate
   * @param y coordinate
   * @param info Player who is doing the search
   * @param fleet doing the search
   * @param ignoreSun Sun to ignore
   * @return Nearest sun
   */
  public Sun getNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final Fleet fleet, final String ignoreSun) {
    double distance = LONGEST_DISTANCE;
    Sun result = null;
    for (Sun sun : sunList) {
      Coordinate coordinate = new Coordinate(x, y);
      double dist = coordinate.calculateDistance(sun.getCenterCoordinate());
      if (ignoreSun != null && ignoreSun.equals(sun.getName())) {
        dist = LONGEST_DISTANCE;
      }
      if (dist < distance && info.getUnchartedValueSystem(sun, fleet) > 50) {
        distance = dist;
        result = sun;
      }
    }
    return result;

  }

  /**
   * Get starmap maximum X coordinate
   * @return Maximum X coordinate, exclusive
   */
  public int getMaxX() {
    return maxX;
  }

  /**
   * Get starmap maximum Y coordinate
   * @return Maximum Y coordinate, exclusive
   */
  public int getMaxY() {
    return maxY;
  }

  /**
   * Get tiles which to show
   * @return Tiles array in array
   */
  public int[][] getTiles() {
    return tiles;
  }

  /**
   * Get the fleet tiles from the map.
   * These fleet positions are always calculated
   * @return FleetTiles
   */
  public FleetTileInfo[][] getFleetTiles() {
    for (int x = 0; x < maxX; x++) {
      for (int y = 0; y < maxY; y++) {
        fleetTiles[x][y] = null;
      }
    }
    for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
      PlayerInfo player = players.getPlayerInfoByIndex(i);
      for (int j = 0; j < player.getFleets().getNumberOfFleets(); j++) {
        Fleet fleet = player.getFleets().getByIndex(j);
        FleetTileInfo info = new FleetTileInfo(
            fleet.getFirstShip().getHull().getRace(),
            fleet.getFirstShip().getHull().getImageIndex(), i, j);
        fleetTiles[fleet.getX()][fleet.getY()] = info;
      }
    }
    return fleetTiles;
  }

  /**
   * Get cursor X coordinate
   * @return X coordinate
   */
  public int getCursorX() {
    return cursorX;
  }

  /**
   * Get cursor Y coordinate
   * @return Y coordinate
   */
  public int getCursorY() {
    return cursorY;
  }

  /**
   * Set cursor coordinates. Validates coordinate before sets it.
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setCursorPos(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      cursorX = x;
      cursorY = y;
    }
  }

  /**
   * Get Draw X coordinate
   * @return x coordinate
   */
  public int getDrawX() {
    return drawX;
  }

  /**
   * Get Draw Y coordinate
   * @return y coordinate
   */
  public int getDrawY() {
    return drawY;
  }

  /**
   * Set draw coordinates. Validates coordinate before sets it.
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setDrawPos(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      drawX = x;
      drawY = y;
    }
  }

  /**
   * Get Sun by coordinates. If not found then null is returned.
   * @param x X coordinate
   * @param y Y coordinate
   * @return Sun or null
   */
  public Sun getSunByCoordinate(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      SquareInfo info = tileInfo[x][y];
      if (info.getType() == SquareInfo.TYPE_SUN) {
        return sunList.get(info.getValue());
      }
    }
    return null;
  }

  /**
   * Get Planet by coordinates. If not found then null is returned.
   * @param x X coordinate
   * @param y Y coordinate
   * @return Planet or null
   */
  public Planet getPlanetByCoordinate(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      SquareInfo info = tileInfo[x][y];
      if (info.getType() == SquareInfo.TYPE_PLANET
          || info.getType() == SquareInfo.TYPE_GAS_PLANET) {
        return planetList.get(info.getValue());
      }
    }
    return null;
  }

  /**
   * Find planet by name
   * @param name Name to search
   * @return Planet or null if not found
   */
  public Planet getPlanetByName(final String name) {
    for (Planet planet : planetList) {
      if (planet.getName().equals(name)) {
        return planet;
      }
    }
    return null;
  }

  /**
   * Get Fleet by coordinates. If not found then null is returned.
   * @param x X coordinate
   * @param y Y coordinate
   * @return Fleet or null
   */
  public Fleet getFleetByCoordinate(final int x, final int y) {
    if (isValidCoordinate(x, y) && fleetTiles != null
        && fleetTiles[x][y] != null) {
      int playerIndex = fleetTiles[x][y].getPlayerIndex();
      int fleetIndex = fleetTiles[x][y].getFleetIndex();
      return players.getPlayerInfoByIndex(playerIndex).getFleets()
          .getByIndex(fleetIndex);
    }
    return null;
  }

  /**
   * Get Player info by fleet coordinates. If not found then null is returned.
   * @param fleet Fleet to search
   * @return PlayerInfo or null
   */
  public PlayerInfo getPlayerInfoByFleet(final Fleet fleet) {
    if (fleet != null) {
      int x = fleet.getX();
      int y = fleet.getY();
      if (isValidCoordinate(x, y) && fleetTiles != null
          && fleetTiles[x][y] != null) {
        int playerIndex = fleetTiles[x][y].getPlayerIndex();
        return players.getPlayerInfoByIndex(playerIndex);
      }
    }
    return null;
  }
  /**
   * Get Array of Planets
   * @return Planet list
   */
  public ArrayList<Planet> getPlanetList() {
    return planetList;
  }

  /**
   * Fight with two fleets
   * @param x X coordinate
   * @param y Y coordinate
   * @param fleet1 Fleet
   * @param info1 PlayerInfo
   * @return Combat or null
   */
  public Combat fightWithFleet(final int x, final int y, final Fleet fleet1,
      final PlayerInfo info1) {
    if (isValidCoordinate(x, y) && fleetTiles != null
        && fleetTiles[x][y] != null) {
      int playerIndex = fleetTiles[x][y].getPlayerIndex();
      int fleetIndex = fleetTiles[x][y].getFleetIndex();
      PlayerInfo info2 = players.getPlayerInfoByIndex(playerIndex);
      if (info1 != info2) {
        Fleet fleet2 = info2.getFleets().getByIndex(fleetIndex);
        return new Combat(fleet1, fleet2, info1, info2);
      }
    }
    return null;
  }

  /**
   * Which turn is now going on?
   * @return the turn
   */
  public int getTurn() {
    return turn;
  }

  /**
   * @param turn the turn to set
   */
  public void setTurn(final int turn) {
    this.turn = turn;
  }

  /**
   * Check if all AI players are handled
   * @return are the AIs handled
   */
  public boolean isAllAIsHandled() {
    if (aiTurnNumber == players.getCurrentMaxPlayers()) {
      return true;
    }
    return false;
  }

  /**
   * Reset AI Turn number and fleet
   */
  public void clearAITurn() {
    aiTurnNumber = 0;
    aiFleet = null;
  }

  /**
   * Handle research and planets for single AI player
   */
  public void handleAIResearchAndPlanets() {
    PlayerInfo info = players.getPlayerInfoByIndex(aiTurnNumber);
    if (info != null && !info.isHuman()) {
      // Handle research
      Research.handle(info);
      ArrayList<Message> messages = info.getMsgList().getFullList();
      for (Message msg : messages) {
        if (msg.getType() == MessageType.RESEARCH) {
          Research.handleShipDesigns(info);
          break;
        }
      }
      for (int j = 0; j < planetList.size(); j++) {
        // Handle planets
        Planet planet = planetList.get(j);
        if (planet.getPlanetPlayerInfo() == info) {
          PlanetHandling.handlePlanet(this, planet, aiTurnNumber);
        }
      }
      aiFleet = info.getFleets().getFirst();
      if (aiFleet == null) {
        aiTurnNumber++;
      }
    } else {
      aiTurnNumber++;
    }
  }

  /**
   * Get AI Fleet. If non null then handleAIFleet can be called
   * otherwise HandleAIResearchAndPlanet should be called
   * @return Fleet or null
   */
  public Fleet getAIFleet() {
    return aiFleet;
  }

  /**
   * Set AI Fleet
   * @param fleet The AI fleet
   */
  public void setAIFleet(final Fleet fleet) {
    aiFleet = fleet;
  }

  /**
   * Get current AI turn. This should be only used when handling AI
   * @return AI player's index
   */
  public int getAiTurnNumber() {
    return aiTurnNumber;
  }

  /**
   * Set new AI player's index.
   * @param aiTurnNumber AI player index
   */
  public void setAiTurnNumber(final int aiTurnNumber) {
    this.aiTurnNumber = aiTurnNumber;
  }

  /**
   * Do Fleet/Planet scan Update for star map. Fleet or planet can be null
   * but only one should be null.
   * @param info Player who controls the fleet
   * @param fleet Fleet which is doing the rescan
   * @param planet Planet which is doing the rescan
   */
  public void doFleetScanUpdate(final PlayerInfo info, final Fleet fleet,
      final Planet planet) {
    int scanRad = -1;
    int cloakDetection = 0;
    int cx = 0;
    int cy = 0;
    if (fleet != null) {
      scanRad = fleet.getFleetScannerLvl();
      cloakDetection = fleet.getFleetCloakDetection();
      cx = fleet.getX();
      cy = fleet.getY();
    } else if (planet != null) {
      scanRad = planet.getScannerLvl();
      cloakDetection = planet.getCloakingDetectionLvl();
      cx = planet.getX();
      cy = planet.getY();
    }
    if (scanRad != -1) {
      for (int y = -scanRad; y < scanRad + 1; y++) {
        for (int x = -scanRad; x < scanRad + 1; x++) {
          drawVisibilityLine(info, cx, cy, cx + x, cy + y, cloakDetection,
              scanRad);
        }
      }
    }
  }

  /**
   * Update star map when game starts
   */
  public void updateStarMapOnStartGame() {
    for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = info.getFleets().getByIndex(j);
          fleet.setMovesLeft(fleet.getFleetSpeed());
          doFleetScanUpdate(info, fleet, null);
        }
      }
    }
    for (int i = 0; i < planetList.size(); i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        int index = players.getIndex(info);
        if (index > -1) {
          calculateCulture(planet.getX(), planet.getY(), planet.getCulture(),
              index);
        }
        doFleetScanUpdate(info, null, planet);
      }
    }
  }

  /**
   * Update star map when game has loaded
   */
  public void updateStarMapOnLoadGame() {
    for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = info.getFleets().getByIndex(j);
          doFleetScanUpdate(info, fleet, null);
        }
      }
    }
    for (int i = 0; i < planetList.size(); i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        int index = players.getIndex(info);
        if (index > -1) {
          calculateCulture(planet.getX(), planet.getY(), planet.getCulture(),
              index);
        }
        doFleetScanUpdate(info, null, planet);
      }
    }
  }

  /**
   * Culture level 1
   */
  private static final int CULTURE_LEVEL_1 = 5;
  /**
   * Culture level 2
   */
  private static final int CULTURE_LEVEL_2 = 10;
  /**
   * Culture level 3
   */
  private static final int CULTURE_LEVEL_3 = 20;
  /**
   * Culture level 4
   */
  private static final int CULTURE_LEVEL_4 = 40;
  /**
   * Culture level 5
   */
  private static final int CULTURE_LEVEL_5 = 80;
  /**
   * Culture level 6
   */
  private static final int CULTURE_LEVEL_6 = 160;
  /**
   * Culture level 7
   */
  private static final int CULTURE_LEVEL_7 = 320;
  /**
   * Culture level 8
   */
  private static final int CULTURE_LEVEL_8 = 640;
  /**
   * Culture level 9
   */
  private static final int CULTURE_LEVEL_9 = 1280;

  /**
   * Maximum culture radius
   */
  private static final int MAX_CULTURE_RADIUS = 7;
  /**
   * Calculate culture on map
   * @param cx Center of culture X coordinate
   * @param cy Center of culture Y coordinate
   * @param value Culture value
   * @param index Player index
   */
  public void calculateCulture(final int cx, final int cy, final int value,
      final int index) {
    String mask = null;
    if (value < CULTURE_LEVEL_1) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */"...............\n"
      + /* 2 */"...............\n"
      + /* 1 */".......X.......\n"
      + /* 0 */"......XXX......\n"
      + /* 1 */".......X.......\n"
      + /* 2 */"...............\n"
      + /* 3 */"...............\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_2) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */"...............\n"
      + /* 2 */"...............\n"
      + /* 1 */"......XXX......\n"
      + /* 0 */"......XXX......\n"
      + /* 1 */"......XXX......\n"
      + /* 2 */"...............\n"
      + /* 3 */"...............\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_3) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */"...............\n"
      + /* 2 */".......X.......\n"
      + /* 1 */"......XXX......\n"
      + /* 0 */".....XXXXX.....\n"
      + /* 1 */"......XXX......\n"
      + /* 2 */".......X.......\n"
      + /* 3 */"...............\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_4) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */".......X.......\n"
      + /* 2 */"......XXX......\n"
      + /* 1 */".....XXXXX.....\n"
      + /* 0 */"....XXXXXXX....\n"
      + /* 1 */".....XXXXX.....\n"
      + /* 2 */"......XXX......\n"
      + /* 3 */".......X.......\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_5) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */".......X.......\n"
      + /* 3 */"......XXX......\n"
      + /* 2 */".....XXXXX.....\n"
      + /* 1 */"....XXXXXXX....\n"
      + /* 0 */"...XXXXXXXXX...\n"
      + /* 1 */"....XXXXXXX....\n"
      + /* 2 */".....XXXXX.....\n"
      + /* 3 */"......XXX......\n"
      + /* 4 */".......X.......\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_6) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"......XXX......\n"
      + /* 3 */".....XXXXX.....\n"
      + /* 2 */"....XXXXXXX....\n"
      + /* 1 */"...XXXXXXXXX...\n"
      + /* 0 */"...XXXXXXXXX...\n"
      + /* 1 */"...XXXXXXXXX...\n"
      + /* 2 */"....XXXXXXX....\n"
      + /* 3 */".....XXXXX.....\n"
      + /* 4 */"......XXX......\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_7) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"......XXX......\n"
      + /* 4 */".....XXXXX.....\n"
      + /* 3 */"....XXXXXXX....\n"
      + /* 2 */"...XXXXXXXXX...\n"
      + /* 1 */"..XXXXXXXXXXX..\n"
      + /* 0 */"..XXXXXXXXXXX..\n"
      + /* 1 */"..XXXXXXXXXXX..\n"
      + /* 2 */"...XXXXXXXXX...\n"
      + /* 3 */"....XXXXXXX....\n"
      + /* 4 */".....XXXXX.....\n"
      + /* 5 */"......XXX......\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_8) {
      // 765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"......XXX......\n"
      + /* 5 */".....XXXXX.....\n"
      + /* 4 */"....XXXXXXX....\n"
      + /* 3 */"...XXXXXXXXX...\n"
      + /* 2 */"..XXXXXXXXXXX..\n"
      + /* 1 */".XXXXXXXXXXXXX.\n"
      + /* 0 */".XXXXXXXXXXXXX.\n"
      + /* 1 */".XXXXXXXXXXXXX.\n"
      + /* 2 */"..XXXXXXXXXXX..\n"
      + /* 3 */"...XXXXXXXXX...\n"
      + /* 4 */"....XXXXXXX....\n"
      + /* 5 */".....XXXXX.....\n"
      + /* 6 */"......XXX......\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_9) {
      // 765432101234567
      mask = /* 7 */".......X.......\n"
      + /* 6 */"......XXX......\n"
      + /* 5 */"....XXXXXXX....\n"
      + /* 4 */"....XXXXXXX....\n"
      + /* 3 */"..XXXXXXXXXXX..\n"
      + /* 2 */"..XXXXXXXXXXX..\n"
      + /* 1 */".XXXXXXXXXXXXX.\n"
      + /* 0 */"XXXXXXXXXXXXXXX\n"
      + /* 1 */".XXXXXXXXXXXXX.\n"
      + /* 2 */"..XXXXXXXXXXX..\n"
      + /* 3 */"..XXXXXXXXXXX..\n"
      + /* 4 */"....XXXXXXX....\n"
      + /* 5 */"....XXXXXXX....\n"
      + /* 6 */"......XXX......\n"
      + /* 7 */".......X.......\n";
    } else {
      // 765432101234567
      mask = /* 7 */"......XXX......\n"
      + /* 6 */".....XXXXX.....\n"
      + /* 5 */"...XXXXXXXXX...\n"
      + /* 4 */"...XXXXXXXXX...\n"
      + /* 3 */".XXXXXXXXXXXXX.\n"
      + /* 2 */".XXXXXXXXXXXXX.\n"
      + /* 1 */"XXXXXXXXXXXXXXX\n"
      + /* 0 */"XXXXXXXXXXXXXXX\n"
      + /* 1 */"XXXXXXXXXXXXXXX\n"
      + /* 2 */".XXXXXXXXXXXXX.\n"
      + /* 3 */".XXXXXXXXXXXXX.\n"
      + /* 4 */"...XXXXXXXXX...\n"
      + /* 5 */"...XXXXXXXXX...\n"
      + /* 6 */".....XXXXX.....\n"
      + /* 7 */"......XXX......\n";
    }

    String[] lines = mask.split("\n");
    int x = -MAX_CULTURE_RADIUS;
    int y = -MAX_CULTURE_RADIUS;
    for (int line = 0; line < lines.length; line++) {
      for (int col = 0; col < lines[line].length(); col++) {
        if (lines[line].charAt(col) == 'X') {
          addSectorCulture(cx + x, cy + y, index, value);
        }
        x++;
      }
      x = -MAX_CULTURE_RADIUS;
      y++;
    }
  }

  /**
   * Reset culture information for whole map
   */
  public void resetCulture() {
    for (int i = 0; i < maxX; i++) {
      for (int j = 0; j < maxY; j++) {
        culture[i][j].reset();
      }
    }
  }

  /**
   * Draw visibility line and set visibility info for one player
   * @param info PlayerInfo
   * @param sx Start X
   * @param sy Start Y
   * @param ex End X
   * @param ey End Y
   * @param cloakDetection Cloaking Detection level
   * @param maxRad maximum radius
   */
  private void drawVisibilityLine(final PlayerInfo info, final int sx,
      final int sy, final int ex, final int ey, final int cloakDetection,
      final int maxRad) {
    double startX = sx;
    double startY = sy;
    double dx = Math.abs(startX - ex);
    double dy = Math.abs(startY - ey);
    // Calculate distance to end
    int distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    double mx;
    double my;
    // Calculate how much move each round
    if (distance > 0) {
      mx = (ex - startX) / distance;
      my = (ey - startY) / distance;
    } else {
      mx = 0;
      my = 0;
    }
    int detectValue = cloakDetection;
    info.setSectorVisibility(sx, sy, PlayerInfo.VISIBLE);
    if (detectValue > 0) {
      info.setSectorCloakingDetection(sx, sy, detectValue);
    }
    // Moving loop
    for (int i = 0; i < distance; i++) {
      startX = startX + mx;
      startY = startY + my;
      int nx = (int) Math.round(startX);
      int ny = (int) Math.round(startY);
      Coordinate startCoordinate = new Coordinate(sx, sy);
      Coordinate coordinate = new Coordinate(nx, ny);
      if (startCoordinate.calculateDistance(coordinate) > maxRad) {
        // We have moved to maximum radius
        break;
      }
      if (isValidCoordinate(nx, ny)) {
        info.setSectorVisibility(nx, ny, PlayerInfo.VISIBLE);
        if (detectValue > 0) {
          info.setSectorCloakingDetection(nx, ny, detectValue);
        }
        if (tileInfo[nx][ny].isVisibilityBlocked()) {
          // There is something that blocks the vision
          break;
        }
        if (detectValue > 0) {
          detectValue = detectValue - 10;
        }
      }
    }
  }

  /**
   * Get total production for one player per turn for certain production
   * @param production See Planet.PRODUCTION_*
   * @param playerIndex Player index to match
   * @return total production per turn
   */
  public int getTotalProductionByPlayerPerTurn(final int production,
      final int playerIndex) {
    int result = 0;
    for (int i = 0; i < planetList.size(); i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetOwnerIndex() == playerIndex) {
        result = result + planet.getTotalProduction(production);
      }
    }
    return result;
  }

  /**
   * Get current player info
   * @return PlayerInfo or null
   */
  public PlayerInfo getCurrentPlayerInfo() {
    if (players != null) {
      return players.getCurrentPlayerInfo();
    }
    return null;
  }

  /**
   * Change to next player
   */
  public void nextPlayer() {
    if (players != null) {
      if (players.getCurrentPlayer() + 1 == players.getCurrentMaxPlayers()) {
        players.setCurrentPlayer(0);
      } else {
        players.setCurrentPlayer(players.getCurrentPlayer() + 1);
      }
    }
  }

  /**
   * Get tile info from coordinate
   * @param x The X coordinate
   * @param y The Y coordinate
   * @return TileInfo
   */
  public SquareInfo getTileInfo(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      return tileInfo[x][y];
    }
    return null;
  }

  /**
   * Get culture power
   * @param x The X coordinate
   * @param y The Y coordinate
   * @return Culture power or null
   */
  public CulturePower getSectorCulture(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      return culture[x][y];
    }
    return null;
  }

  /**
   * Add culture power
   * @param x X coordinate
   * @param y Y Coordinate
   * @param index Player index from player list
   * @param value Culture value to add
   */
  public void addSectorCulture(final int x, final int y, final int index,
      final int value) {
    if (isValidCoordinate(x, y)) {
      culture[x][y].addCulture(index, value);
    }
  }

  /**
   * Is tile blocked or not
   * @param x X coordinate
   * @param y Y Coordinate
   * @return true if tile is blocked otherwise false. Also if
   * coordinate is out of map then true is returned.
   */
  public boolean isBlocked(final int x, final int y) {
    SquareInfo sector = getTileInfo(x, y);
    if (sector != null) {
      return sector.isBlocked();
    }
    return true;
  }

  /**
   * Get Player info by index
   * @param index Player index
   * @return PlayerInfo or null
   */
  public PlayerInfo getPlayerByIndex(final int index) {
    return players.getPlayerInfoByIndex(index);
  }

  /**
   * Get PlayerList
   * @return PlayerList
   */
  public PlayerList getPlayerList() {
    return players;
  }

}
