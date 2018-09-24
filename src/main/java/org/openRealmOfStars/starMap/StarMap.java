package org.openRealmOfStars.starMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionHandling;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
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
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.espionage.EspionageBonusType;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.history.History;
import org.openRealmOfStars.starMap.history.event.PlayerStartEvent;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.planet.PlanetaryEvent;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.namegenerators.RandomSystemNameGenerator;
import org.openRealmOfStars.utilities.namegenerators.RoguePlanetNameGenerator;
import org.openRealmOfStars.utilities.repository.NewsCorpRepository;
import org.openRealmOfStars.utilities.repository.PlanetRepository;
import org.openRealmOfStars.utilities.repository.SunRepository;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
   * Enemy type for pirate ship
   */
  public static final int ENEMY_PIRATE = 0;
  /**
   * Enemy type for pirate station
   */
  public static final int ENEMY_PIRATE_LAIR = 1;
  /**
   * Enemy type for space monster
   */
  public static final int ENEMY_MONSTER = 2;

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
   * News corporation data
   */
  private NewsCorpData newsCorpData;

  /**
   * Special debug mode on
   */
  private boolean debug;

  /**
   * Chance for Planetary event
   */
  private int chanceForPlanetaryEvent;

  /**
   * Last turn for score victory
   */
  private int scoreVictoryTurn;

  /**
   * Conquer scoring setting
   */
  private int scoreConquer;

  /**
   * Culture scoring setting
   */
  private int scoreCulture;

  /**
   * Research scoring setting
   */
  private int scoreResearch;

  /**
   * Diplomacy scoring setting
   */
  private int scoreDiplomacy;

  /**
   * System name generator.
   */
  private RandomSystemNameGenerator nameGenerator;

  /**
   * Game history containing important events.
   * History information is not saved in to same save game as
   * starmap. It is saved into separate file.
   */
  private History history;

  /**
   * Game has ended
   */
  private boolean gameEnd;

  /**
   * Magic string to save game files
   */
  public static final String MAGIC_STRING = "OROS-SAVE-GAME-0.11";

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
    setDebug(false);
    nameGenerator = new RandomSystemNameGenerator();
    setScoreVictoryTurn(config.getScoringVictoryTurns());
    setScoreCulture(config.getScoreLimitCulture());
    setScoreConquer(config.getScoreLimitConquer());
    setScoreResearch(config.getScoreLimitResearch());
    setScoreDiplomacy(config.getScoreLimitDiplomacy());
    history = new History();
    history.addTurn(0);
    maxX = config.getSizeX();
    maxY = config.getSizeY();
    chanceForPlanetaryEvent = config.getChanceForPlanetaryEvent();
    this.players = players;
    this.players.initVisibilityMaps(maxX, maxY);
    drawX = 0;
    drawY = 0;
    tiles = new int[maxX][maxY];
    int[][] solarSystem = new int[maxX][maxY];
    tileInfo = new SquareInfo[maxX][maxY];
    culture = new CulturePower[maxX][maxY];
    sunList = new ArrayList<>();
    planetList = new ArrayList<>();
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i = 0; i < maxX; i++) {
      for (int j = 0; j < maxY; j++) {
        tiles[i][j] = empty.getIndex();
        tileInfo[i][j] = SquareInfo.EMPTY_TILE;
        culture[i][j] = new CulturePower(players.getCurrentMaxPlayers());
        solarSystem[i][j] = 0;
      }
    }
    newsCorpData = new NewsCorpData(players.getCurrentMaxRealms());
    turn = 0;
    aiTurnNumber = 0;
    aiFleet = null;
    int loop = 0;
    // Create starting systems
    if (config.getStartingPosition() == GalaxyConfig.START_POSITION_RANDOM) {
      for (int i = 0; i < config.getMaxPlayers(); i++) {
        while (loop < MAX_LOOPS) {
          int sx = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
              maxX - SOLAR_SYSTEM_WIDTH);
          int sy = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
              maxX - SOLAR_SYSTEM_WIDTH);
          int planets = DiceGenerator.getRandom(3, 5);
          int gasGiants = DiceGenerator.getRandom(2);
          if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, maxX, maxY,
              config.getSolarSystemDistance()) == 0) {
            solarSystem = createSolarSystem(solarSystem, sx, sy, planets,
                gasGiants, i);
            break;
          }
          loop++;
        }
      }
    }
    if (config.getStartingPosition() == GalaxyConfig.START_POSITION_BORDER
        || loop == MAX_LOOPS) {
      if (loop == MAX_LOOPS) {
        // Need to reinit whole map
        players.reInit();
        players.initVisibilityMaps(maxX, maxY);
        drawX = 0;
        drawY = 0;
        tiles = new int[maxX][maxY];
        tileInfo = new SquareInfo[maxX][maxY];
        culture = new CulturePower[maxX][maxY];
        sunList = new ArrayList<>();
        planetList = new ArrayList<>();
        for (int i = 0; i < maxX; i++) {
          for (int j = 0; j < maxY; j++) {
            tiles[i][j] = empty.getIndex();
            tileInfo[i][j] = SquareInfo.EMPTY_TILE;
            culture[i][j] = new CulturePower(players.getCurrentMaxPlayers());
            solarSystem[i][j] = 0;
          }
        }
      }
      solarSystem = createBorderStartingSystems(config, solarSystem);
    }
    // Random system
    loop = 0;
    while (loop < MAX_LOOPS) {
      int sx = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
          maxX - SOLAR_SYSTEM_WIDTH);
      int sy = DiceGenerator.getRandom(SOLAR_SYSTEM_WIDTH,
          maxX - SOLAR_SYSTEM_WIDTH);
      int planets = DiceGenerator.getRandom(1, 6);
      int gasGiants = DiceGenerator.getRandom(3);
      if (StarMapUtilities.isSolarSystem(solarSystem, sx, sy, maxX, maxY,
          config.getSolarSystemDistance()) == 0) {
        solarSystem = createSolarSystem(solarSystem, sx, sy, planets,
            gasGiants);
        int full = StarMapUtilities.getSystemFullness(solarSystem, maxX, maxY);
        if (full > 60) {
          // Enough solar systems
          break;
        }
      }
      loop++;
    }
    // Create rogue planets
    if (config.getNumberOfRoguePlanets() != GalaxyConfig.ROGUE_PLANETS_NONE) {
      loop = 0;
      int roguePlanets = config.getNumberOfRoguePlanets()
          * (config.getGalaxySizeIndex() + 1);
      RoguePlanetNameGenerator ng = new RoguePlanetNameGenerator();
      for (int i = 0; i < roguePlanets; i++) {
        while (loop < MAX_LOOPS) {
          int sx = DiceGenerator.getRandom(1,
              maxX - 2);
          int sy = DiceGenerator.getRandom(1,
              maxX - 2);
          if (Tiles.getTileByIndex(tiles[sx][sy]) == empty
              && getPlanetByCoordinate(sx, sy) == null
              && locateSolarSystem(sx, sy) == null) {
            String name = ng.generate();
            Planet planet = new Planet(new Coordinate(sx, sy), name, 0, false);
            planet.setPlanetaryEvent(PlanetaryEvent.getRandomEvent(
                planet.getPlanetType(), chanceForPlanetaryEvent));
            planet.setEventActivation(false);
            planetList.add(planet);
            int planetNumber = planetList.size() - 1;
            SquareInfo info = new SquareInfo(SquareInfo.TYPE_PLANET,
                planetNumber);
            tileInfo[sx][sy] = info;
            tiles[sx][sy] = planet.getPlanetType().getTileIndex();
            break;
          }
          loop++;
        }
      }
    }
    // Create random deep space anchors
    loop = 0;
    int numberOfAnchors = config.getMaxPlayers() * 3;
    int pirateLairs = 0;
    switch (config.getSpacePiratesLevel()) {
      case 0: {
        pirateLairs = 0;
        break;
      }
      case 1: {
        // 10%
        pirateLairs = numberOfAnchors * 10 / 100;
        if (pirateLairs < 1) {
          pirateLairs = 1;
        }
        break;
      }
      case 2: {
        // 20%
        pirateLairs = numberOfAnchors * 20 / 100;
        break;
      }
      case 3: {
        // 40%
        pirateLairs = numberOfAnchors * 40 / 100;
        break;
      }
      case 4: {
        // 60%
        pirateLairs = numberOfAnchors * 60 / 100;
        break;
      }
      case 5: {
        // 80%
        pirateLairs = numberOfAnchors * 80 / 100;
        break;
      }
      case 6: {
        // 100%
        pirateLairs = numberOfAnchors;
        break;
      }
      default: {
        pirateLairs = 0;
      }
    }
    for (int i = 0; i < numberOfAnchors; i++) {
      while (loop < MAX_LOOPS) {
        int sx = DiceGenerator.getRandom(1,
            maxX - 2);
        int sy = DiceGenerator.getRandom(1,
            maxX - 2);
        if (Tiles.getTileByIndex(tiles[sx][sy]) == empty
            && getPlanetByCoordinate(sx, sy) == null) {
          Tile anchor = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1);
          tiles[sx][sy] = anchor.getIndex();
          PlayerInfo board = players.getBoardPlayer();
          if (board != null && i < pirateLairs) {
            addSpacePirateLair(sx, sy, board);
          }
          break;
        }
        loop++;
      }
    }
 // Create random space anomalies
    loop = 0;
    int numberOfAnomalies = 0;
    boolean harmful = false;
    boolean board = players.getBoardPlayer() != null;
    if (config.getSpaceAnomaliesLevel() == 1) {
      numberOfAnomalies = config.getMaxPlayers() * 5;
    }
    if (config.getSpaceAnomaliesLevel() == 2) {
      numberOfAnomalies = config.getMaxPlayers() * 7;
      harmful = true;
    }
    for (int i = 0; i < numberOfAnomalies; i++) {
      while (loop < MAX_LOOPS) {
        int sx = DiceGenerator.getRandom(1,
            maxX - 2);
        int sy = DiceGenerator.getRandom(1,
            maxX - 2);
        if (Tiles.getTileByIndex(tiles[sx][sy]) == empty
            && getPlanetByCoordinate(sx, sy) == null) {
          String tileName = TileNames.getRandomSpaceAnomaly(harmful, board);
          Tile anomaly = Tiles.getTileByName(tileName);
          tiles[sx][sy] = anomaly.getIndex();
          break;
        }
        loop++;
      }
    }
    // No need to have generator after creation
    nameGenerator = null;
  }

  /**
   * Adds one pirate ship into coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @param playerInfo Board player info
   */
  public void addSpacePirate(final int x, final int y,
      final PlayerInfo playerInfo) {
    addSpaceAnomalyEnemy(x, y, playerInfo, ENEMY_PIRATE);
  }
  /**
   * Adds one pirate starbase into coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @param playerInfo Board player info
   */
  public void addSpacePirateLair(final int x, final int y,
      final PlayerInfo playerInfo) {
    addSpaceAnomalyEnemy(x, y, playerInfo, ENEMY_PIRATE_LAIR);
  }

  /**
   * Adds one enemy monster/pirate into coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @param playerInfo Board player info
   * @param type ENEMY_TYPE
   * @return created fleet or null if fleet was not created.
   */
  public Fleet addSpaceAnomalyEnemy(final int x, final int y,
      final PlayerInfo playerInfo, final int type) {
    ShipStat[] stats = playerInfo.getShipStatList();
    ArrayList<ShipStat> listStats = new ArrayList<>();
    for (ShipStat stat : stats) {
      if (!stat.isObsolete()) {
        Ship ship = new Ship(stat.getDesign());
        if (type == ENEMY_PIRATE_LAIR && ship.getTotalMilitaryPower() > 0
            && ship.isStarBase()) {
          listStats.add(stat);
        }
        if (type == ENEMY_PIRATE && ship.getTotalMilitaryPower() > 0
            && !ship.isStarBase()) {
          listStats.add(stat);
        }
      }
    }
    if (listStats.size() > 0) {
      ShipStat stat = listStats.get(DiceGenerator.getRandom(
          listStats.size() - 1));
      Ship ship = new Ship(stat.getDesign());
      stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
      stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
      Fleet fleet = null;
      if (type == ENEMY_PIRATE_LAIR) {
        Fleet starbaseFleet = null;
        for (int i = 0; i < playerInfo.getFleets().getNumberOfFleets(); i++) {
          Fleet ite = playerInfo.getFleets().getByIndex(i);
          if (ite.isStarBaseDeployed()
              && ite.getX() == x && ite.getY() == y) {
            starbaseFleet = ite;
            break;
          }
        }
        if (starbaseFleet != null) {
          if (starbaseFleet.getNumberOfShip() < Fleet.MAX_STARBASE_SIZE) {
            starbaseFleet.addShip(ship);
            ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
            fleet = starbaseFleet;
          }
        } else {
          fleet = new Fleet(ship, x, y);
          FleetList fleetList = playerInfo.getFleets();
          ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
          fleetList.add(fleet);
          fleet.setName(fleetList.generateUniqueName("Space lair"));
        }
      }
      if (type == ENEMY_PIRATE) {
        fleet = new Fleet(ship, x, y);
        playerInfo.getFleets().add(fleet);
        fleet.setName(playerInfo.getFleets().generateUniqueName("pirate"));
        Sun sun = getAboutNearestSolarSystem(x, y, playerInfo, fleet, null);
        Mission mission = new Mission(MissionType.PRIVATEER,
            MissionPhase.TREKKING, sun.getCenterCoordinate());
        mission.setFleetName(fleet.getName());
        mission.setSunName(sun.getName());
        playerInfo.getMissions().add(mission);
      }
      return fleet;
    }
    return null;
  }
  /**
   * Create border starting solar system
   * @param config Galaxy Config
   * @param solarSystem Solar system map
   * @return Map of updated solarsystems
   */
  private int[][] createBorderStartingSystems(final GalaxyConfig config,
      final int[][] solarSystem) {
    // First starting Systems
    int sx = SOLAR_SYSTEM_WIDTH;
    int sy = SOLAR_SYSTEM_WIDTH;
    int planets = DiceGenerator.getRandom(3, 5);
    int gasGiants = DiceGenerator.getRandom(2);
    int[][] mapOfSolars = solarSystem;
    if (config.getMaxPlayers() == 2) {
      // First player
      sx = maxX / 2;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          0);

      // Second player
      sx = maxX / 2;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          1);

    } else if (config.getMaxPlayers() == 3) {
      // First player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          0);

      // Second player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          1);

      // Third player
      sx = maxX / 2;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          2);

    } else if (config.getMaxPlayers() >= 4) {
      // First player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          0);

      // Second player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          1);

      // Third player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          2);

      // Fourth player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          3);
    }

    if (config.getMaxPlayers() >= 5) {
      // Fifth player
      sx = maxX / 2;
      sy = maxY - SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          4);
    }
    if (config.getMaxPlayers() >= 6) {
      // Sixth player
      sx = maxX / 2;
      sy = SOLAR_SYSTEM_WIDTH;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          5);
    }
    if (config.getMaxPlayers() >= 7) {
      // Seventh player
      sx = SOLAR_SYSTEM_WIDTH;
      sy = maxY / 2;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          6);
    }
    if (config.getMaxPlayers() == 8) {
      // Eight player
      sx = maxX - SOLAR_SYSTEM_WIDTH;
      sy = maxY / 2;
      planets = DiceGenerator.getRandom(3, 5);
      gasGiants = DiceGenerator.getRandom(2);
      mapOfSolars = createSolarSystem(mapOfSolars, sx, sy, planets, gasGiants,
          7);
    }
    return mapOfSolars;
  }

  /**
   * Initialize StarMap from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public StarMap(final DataInputStream dis) throws IOException {
    setDebug(false);
    history = new History();
    String str = IOUtilities.readString(dis);
    if (str.equals(MAGIC_STRING)) {
      turn = dis.readInt();
      // Just add single turn not to break not having turn
      history.addTurn(turn);
      // Victory conditions
      setScoreVictoryTurn(dis.readInt());
      setScoreCulture(dis.readInt());
      setScoreConquer(dis.readInt());
      setScoreResearch(dis.readInt());
      setScoreDiplomacy(dis.readInt());
      maxX = dis.readInt();
      maxY = dis.readInt();
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
        }
      }
      // Read suns
      int count = dis.readInt();
      for (int i = 0; i < count; i++) {
        sunList.add(new SunRepository().restoreSun(dis));
      }
      // Players first
      players = new PlayerList(dis);
      // Map data itself
      for (int x = 0; x < maxX; x++) {
        for (int y = 0; y < maxY; y++) {
          culture[x][y] = new CulturePower(players.getCurrentMaxPlayers());
        }
      }
      count = dis.readInt();
      for (int i = 0; i < count; i++) {
        Planet planet = new PlanetRepository().restorePlanet(dis, players);
        planetList.add(planet);
      }
      NewsCorpRepository newsCorpRepo = new NewsCorpRepository();
      newsCorpData = newsCorpRepo.restoreNewsCorp(dis,
          players.getCurrentMaxRealms());
      try {
        history = History.readFromStream(dis);
      } catch (IOException e) {
        ErrorLogger.log("Failed reading history data,"
            + " maybe save is missing it.");
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
    IOUtilities.writeString(dos, "OROS-SAVE-GAME-0.11");
    // Turn number
    dos.writeInt(turn);
    // Victory conditions
    dos.writeInt(getScoreVictoryTurn());
    dos.writeInt(getScoreCulture());
    dos.writeInt(getScoreConquer());
    dos.writeInt(getScoreResearch());
    dos.writeInt(getScoreDiplomacy());
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
    NewsCorpRepository newsCorpRepo = new NewsCorpRepository();
    newsCorpRepo.saveNewsCorp(dos, newsCorpData);
    history.writeToStream(dos);
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
   * @return Update map of solar systems
   */
  private int[][] createSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int numberOfPlanets, final int numberOfGasGiants) {
    return createSolarSystem(solarSystem, sx, sy, numberOfPlanets,
        numberOfGasGiants, -1);
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
   * @return updated solarsystem map
   */
  private int[][] createSolarSystem(final int[][] solarSystem, final int sunx,
      final int suny, final int planetsToCreate, final int gasGiantsToCreate,
      final int playerIndex) {
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
        planet.setPlanetType(PlanetTypes.getRandomPlanetType(false));
        if (planets == 1 && playerIndex != -1) {
          PlayerInfo playerInfo = players.getPlayerInfoByIndex(playerIndex);
          Message msg = new Message(
              MessageType.PLANETARY, playerInfo.getEmpireName() + " starts at "
                  + planet.getName() + ".",
              Icons.getIconByName(Icons.ICON_CULTURE));
          PlayerStartEvent event = new PlayerStartEvent(planet.getCoordinate(),
              planet.getName(), playerIndex);
          history.addEvent(event);
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
          } else if (playerInfo.getRace() == SpaceRace.HOMARIANS) {
            planet.setWorkers(Planet.FOOD_FARMERS, 2);
            planet.setWorkers(Planet.METAL_MINERS, 0);
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
            planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
            planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          } else if (playerInfo.getRace() == SpaceRace.CHIRALOIDS) {
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
            int numShip = 1;
            if (playerInfo.getRace() == SpaceRace.SPORKS
                && stat.getDesign().isMilitaryShip()) {
              numShip = 2;
            }
            for (int j = 0; j < numShip; j++) {
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

        } else {
            planet.setPlanetaryEvent(PlanetaryEvent.getRandomEvent(
                planet.getPlanetType(), chanceForPlanetaryEvent));
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
        default:
          throw new IllegalArgumentException("Unexpected gas giant type:"
             + planet.getPlanetTypeIndex());
        }
      }
    }
    return mapOfSolar;
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
    return getNearestSolarSystem(x, y, info, fleet, ignoreSun, false);
  }
  /**
   * Get nearest uncharted Solar system for coordinate. This should never
   * return null. Unless there are no suns in galaxy. This might not always
   * return same result. If there are two suns with more than 50% uncharted
   * then it is randomized which one is returned. This allows creating
   * more varying for AI's explore missions.
   * @param x coordinate
   * @param y coordinate
   * @param info Player who is doing the search
   * @param fleet doing the search
   * @param ignoreSun Sun to ignore
   * @return Nearest sun
   */
  public Sun getAboutNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final Fleet fleet, final String ignoreSun) {
    return getNearestSolarSystem(x, y, info, fleet, ignoreSun, true);
  }

  /**
   * Get nearest uncharted Solar system for coordinate. This should never
   * return null. Unless there are no suns in galaxy. This might not always
   * return same result. If there are two suns with more than 50% uncharted
   * then it is randomized which one is returned. This allows creating
   * more varying for AI's explore missions.
   * @param x coordinate
   * @param y coordinate
   * @param info Player who is doing the search
   * @param fleet doing the search
   * @param ignoreSun Sun to ignore
   * @param second true if possible to return second closest solar system
   * @return Nearest sun
   */
  private Sun getNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final Fleet fleet, final String ignoreSun,
      final boolean second) {
    double distance = LONGEST_DISTANCE;
    Sun result = null;
    Sun secondChoice = null;
    double secondDistance = LONGEST_DISTANCE;
    int leastChartedValue = 100;
    Sun leastCharted = null;
    for (Sun sun : sunList) {
      Coordinate coordinate = new Coordinate(x, y);
      double dist = coordinate.calculateDistance(sun.getCenterCoordinate());
      if (ignoreSun != null && ignoreSun.equals(sun.getName())) {
        dist = LONGEST_DISTANCE;
      }
      if (dist < distance && info.getUnchartedValueSystem(sun, fleet) > 50) {
        secondDistance = distance;
        distance = dist;
        secondChoice = result;
        result = sun;
      } else if (dist < secondDistance
          && info.getUnchartedValueSystem(sun, fleet) > 50) {
        secondDistance = dist;
        secondChoice = sun;
      }
      if (info.getUnchartedValueSystem(sun, fleet) < leastChartedValue) {
        leastCharted = sun;
        leastChartedValue = info.getUnchartedValueSystem(sun, fleet);
      }
    }
    if (result != null && secondChoice != null && second) {
      if (DiceGenerator.getRandom(1) == 0) {
        return result;
      }
      return secondChoice;
    }
    if (result != null) {
      return result;
    }
    return leastCharted;
  }

  /**
   * Get nearest Solar system for least liked player.
   * Searches nearest planet owned by least liked and
   * selects that solar system. If not found then
   * current solarsystem is returned or finally random
   * solar system if current location is outside of solar systems.
   * @param x X coordinate
   * @param y Y Coordinate
   * @param info Player doing the search
   * @param leastLiked Least liked player index
   * @return Sun
   */
  public Sun getNearestSolarSystemForLeastLiked(final int x, final int y,
      final PlayerInfo info, final int leastLiked) {
    double distance = LONGEST_DISTANCE;
    Planet targetPlanet = null;
    for (Planet planet : planetList) {
      Coordinate coordinate = new Coordinate(x, y);
      if (info.getSectorVisibility(planet.getCoordinate()) > 0
          && planet.getPlanetOwnerIndex() == leastLiked) {
        double dist = coordinate.calculateDistance(planet.getCoordinate());
        if (dist < distance) {
          distance = dist;
          targetPlanet = planet;
        }
      }
    }
    Sun result = null;
    if (targetPlanet != null) {
      result = locateSolarSystem(targetPlanet.getX(), targetPlanet.getY());
    }
    if (result == null) {
      result = locateSolarSystem(x, y);
      if (result == null) {
        int i = DiceGenerator.getRandom(sunList.size() - 1);
        result = sunList.get(i);
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
   * Get tile for coordinate
   * @param x Coordinate X
   * @param y Coordinate Y
   * @return Tile from coordinate or null if invalid coordinate
   */
  public Tile getTile(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      return Tiles.getTileByIndex(tiles[x][y]);
    }
    return null;
  }

  /**
   * Set tile for coordinate
   * @param x Coordinate X
   * @param y Coordinate Y
   * @param tile Tile to set
   */
  public void setTile(final int x, final int y, final Tile tile) {
    if (isValidCoordinate(x, y)) {
      tiles[x][y] = tile.getIndex();
    }
  }

  /**
   * Get the fleet tiles from the map.
   * These fleet positions are always calculated.
   * This method always refreshes fleet tiles
   * @return FleetTiles
   */
  public FleetTileInfo[][] getFleetTiles() {
    return getFleetTiles(true);
  }

  /**
   * Clear fleet tiles and when next time received fleet tiles will
   * be generated.
   */
  public void clearFleetTiles() {
    fleetTiles = null;
  }

  /**
   * Get the fleet tiles from the map.
   * These fleet positions are always calculated
   * @param refresh If true new fleet tiles are always generated
   * @return FleetTiles
   */
  public FleetTileInfo[][] getFleetTiles(final boolean refresh) {
    if (refresh || fleetTiles == null) {
      fleetTiles = new FleetTileInfo[maxX][maxY];
      for (int x = 0; x < maxX; x++) {
        for (int y = 0; y < maxY; y++) {
          fleetTiles[x][y] = null;
        }
      }
      for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
        PlayerInfo player = players.getPlayerInfoByIndex(i);
        for (int j = 0; j < player.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = player.getFleets().getByIndex(j);
          if (fleet != null && fleetTiles[fleet.getX()][fleet.getY()] == null
              && fleet.getFirstShip() != null) {
            FleetTileInfo info = new FleetTileInfo(
                fleet.getFirstShip().getHull().getRace(),
                fleet.getFirstShip().getHull().getImageIndex(), i, j);
            fleetTiles[fleet.getX()][fleet.getY()] = info;
          } else {
            for (int k = 0; k < player.getFleets().getNumberOfFleets(); k++) {
              if (j != k) {
                Fleet fleet2 = player.getFleets().getByIndex(k);
                if (fleet2 != null &&  fleet != null
                    && fleet2.getX() == fleet.getX()
                    && fleet2.getY() == fleet.getY()
                    && fleet2.getMilitaryValue() > fleet.getMilitaryValue()) {
                  FleetTileInfo info = new FleetTileInfo(
                      fleet2.getFirstShip().getHull().getRace(),
                      fleet2.getFirstShip().getHull().getImageIndex(), i, k);
                  fleetTiles[fleet2.getX()][fleet2.getY()] = info;
                }
                if (fleet2 != null &&  fleet != null
                    && fleet2.getX() == fleet.getX()
                    && fleet2.getY() == fleet.getY()
                    && fleet2.isStarBaseDeployed()) {
                  FleetTileInfo info = new FleetTileInfo(
                      fleet2.getFirstShip().getHull().getRace(),
                      fleet2.getFirstShip().getHull().getImageIndex(), i, k);
                  fleetTiles[fleet2.getX()][fleet2.getY()] = info;
                }
              }
            }
          }
        }
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
   * Get News corporation data about the players
   * @return the newsCorpData
   */
  public NewsCorpData getNewsCorpData() {
    return newsCorpData;
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
   * Get Sun by name. If not found then null is returned.
   * @param sunName Sun's name
   * @return Sun or null
   */
  public Sun getSunByName(final String sunName) {
    for (Sun sun : sunList) {
      if (sun.getName().equals(sunName)) {
        return sun;
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
    if (fleetTiles == null) {
      getFleetTiles();
    }
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
      if (fleetTiles == null) {
        getFleetTiles();
      }
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
   * Use only in JUnits. This changes whole planet list on starmap.
   * @param list New planet list
   */
  public void setPlanetList(final ArrayList<Planet> list) {
    planetList = list;
  }

  /**
   * Get the closest home port for player
   * @param info Player who is looking for closest home planet
   * @param currentPos Current position in starmap
   * @return Closest planet or null if no planet found
   */
  public Planet getClosestHomePort(final PlayerInfo info,
      final Coordinate currentPos) {
    double distance = 9999.0;
    Planet result = null;
    for (Planet planet : planetList) {
      if (planet.getPlanetPlayerInfo() == info) {
        double dist = currentPos.calculateDistance(planet.getCoordinate());
        if (dist < distance) {
          distance = dist;
          result = planet;
        }
      }
    }
    return result;
  }
  /**
   * Get Fleet by fleetTile
   * @param fleetTile to get the fleet
   * @return Fleet by FleetTile
   */
  public Fleet getFleetByFleetTileInfo(final FleetTileInfo fleetTile) {
    PlayerInfo info = getPlayerByIndex(fleetTile.getPlayerIndex());
    Fleet fleet = info.getFleets().getByIndex(fleetTile.getFleetIndex());
    return fleet;
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
        Coordinate escapePosition = StarMapUtilities.getEscapeCoordinates(
            fleet2.getCoordinate(), fleet1.getCoordinate());
        if (!isBlocked(escapePosition.getX(), escapePosition.getY())) {
          PlayerInfo info = isBlockedByFleet(escapePosition.getX(),
              escapePosition.getY());
          if (info != null && info != info2) {
            escapePosition = null;
          }
        } else {
          escapePosition = null;
        }
        return new Combat(fleet1, fleet2, info1, info2, escapePosition);
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
   * Handle faking the military size for single AI player
   */
  public void handleFakingMilitarySize() {
    GameLengthState gameLengthState = GameLengthState.getGameLengthState(
        getTurn(),  getScoreVictoryTurn());
    PlayerInfo info = players.getPlayerInfoByIndex(aiTurnNumber);
    if (info != null && !info.isHuman()) {
      info.tuneFakeMilitarySetting(gameLengthState);
    }

  }

  /**
   * Get List of planets owned by certain realm but which are
   * known to seen realm.
   * @param targetRealm Owner of the planet
   * @param seen Realm which sees them
   * @return Planets as an array
   */
  public Planet[] getPlanetListSeenByOther(final int targetRealm,
      final PlayerInfo seen) {
    ArrayList<Planet> list = new ArrayList<>();
    for (Planet planet : planetList) {
      if (planet.getPlanetOwnerIndex() == targetRealm
          && seen.getSectorVisibility(planet.getCoordinate()) >= 1) {
        list.add(planet);
      }
    }
    return list.toArray(new Planet[0]);
  }
  /**
   * Handle research and planets for single AI player
   */
  public void handleAIResearchAndPlanets() {
    PlayerInfo info = players.getPlayerInfoByIndex(aiTurnNumber);
    if (info != null && info.isHuman()) {
      aiFleet = info.getFleets().getFirst();
      if (aiFleet == null) {
        aiTurnNumber++;
      }
      return;
    }
    if (info != null && !info.isHuman()) {
      // Try to locate ships for gather missions
      for (int i = 0; i < info.getMissions().getSize(); i++) {
        Mission mission = info.getMissions().getMissionByIndex(i);
        if (mission.getType() == MissionType.GATHER
            && mission.getPhase() == MissionPhase.PLANNING) {
          MissionHandling.findGatheringShip(mission, info);
        }
      }
      // Handle research
      Research.handle(info);
      Research.removeUnusedAndObsoleteDesigns(info, this);
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
      if (info != null) {
        aiTurnNumber++;
      }
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
    updateEspionage();
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
          if (fleet.getCulturalValue() > 0 && fleet.isStarBaseDeployed()) {
            // Recalculate culture for the map for each player
            calculateCulture(fleet.getX(), fleet.getY(),
                fleet.getCulturalValue(), i);
          }

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
    updateEspionage();
  }

  /**
   * Update espionage bonuses. This should be called on load and
   * after each turn.
   */
  public void updateEspionage() {
    int maxPlayers = players.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayers; i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        info.getEspionage().clearAllEspionageBonuses();
        info.getEspionage().getByIndex(i).addEspionageBonus(
            EspionageBonusType.OWN_REALM, 10, "Own realm");
        for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = info.getFleets().getByIndex(j);
          int sectorIndex = getSectorCulture(fleet.getX(),
              fleet.getY()).getHighestCulture();
          if (sectorIndex != -1 && sectorIndex != i) {
            int espionageBonus = fleet.getEspionageBonus();
            PlayerInfo spiedInfo = players.getPlayerInfoByIndex(sectorIndex);
            if (spiedInfo != null && espionageBonus > 0) {
              String description = "Fleet " + fleet.getName() + " spying "
                  + spiedInfo.getEmpireName() + ".";
              info.getEspionage().getByIndex(sectorIndex).addEspionageBonus(
                  EspionageBonusType.SPY_FLEET, espionageBonus, description);
            }
          }
        }
      }
    }
    // Check for trade espionage aka spy trade
    for (int i = 0; i < maxPlayers; i++) {
      // Go through all players
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        for (int j = 0; j < maxPlayers; j++) {
          if (j != i && info.getDiplomacy().isAlliance(j)
              && !info.getDiplomacy().isSpyTrade(j)) {
            // Alliance so automatic SPY trade
            PlayerInfo info2 = players.getPlayerInfoByIndex(j);
            info.getDiplomacy().getDiplomacyList(j).addBonus(
                DiplomacyBonusType.SPY_TRADE, info.getRace());
            info2.getDiplomacy().getDiplomacyList(i).addBonus(
                DiplomacyBonusType.SPY_TRADE, info2.getRace());
          }
          // Check if they have spy trade with some one else
          if (j != i && info.getDiplomacy().isSpyTrade(j)) {
            PlayerInfo trader = players.getPlayerInfoByIndex(j);
            for (int k = 0; k < players.getCurrentMaxPlayers(); k++) {
              // Then go through all the trader's espionage
              info.getEspionage().getByIndex(k).addEspionageBonus(
                  EspionageBonusType.TRADE,
                  trader.getEspionage().getByIndex(k).getOwnBonus(),
                  "Spy trade with " + trader.getEmpireName());
            }
          }
          if (j != i && info.getDiplomacy().isAlliance(j)) {
            PlayerInfo trader = players.getPlayerInfoByIndex(j);
            info.getEspionage().getByIndex(j).addEspionageBonus(
                EspionageBonusType.TRADE,
                trader.getEspionage().getByIndex(j).getTotalBonus(),
                "Spy trade with " + trader.getEmpireName());
          }
        }
      }
    }

  }

  /**
   * First one is doing military estimation on espionage or if
   * not available then on newscorp.
   * @param first Player index who is estimating
   * @param second Second player who is being estimated
   * @return Military value
   */
  protected int getMilitaryEstimation(final int first, final int second) {
    PlayerInfo firstInfo = players.getPlayerInfoByIndex(first);
    PlayerInfo secondInfo = players.getPlayerInfoByIndex(second);
    int actualMilitarySecond = NewsCorpData.calculateMilitaryValue(secondInfo);
    EspionageList espionageList = firstInfo.getEspionage().getByIndex(second);
    int estimateSecond = espionageList.estimateMilitaryPower(
        actualMilitarySecond);
    if (estimateSecond == 0) {
      estimateSecond = newsCorpData.getMilitary().getLatest(second);
    }
    return estimateSecond;
  }
  /**
   * First one is doing military estimation on espionage or if
   * not available then on newscorp. This estimation is done
   * for whole defesive pact
   * @param first Player index who is estimating
   * @param second Second player who is being estimated
   *        and who belong to defensive pact
   * @return Military value
   */
  protected int getMilitaryEstimationForDefensivePact(final int first,
      final int second) {
    int result = getMilitaryEstimation(first, second);
    PlayerInfo secondInfo = players.getPlayerInfoByIndex(second);
    int maxPlayer = players.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
      if (i != first && i != second
          && secondInfo.getDiplomacy().isDefensivePact(i)) {
        result = result + getMilitaryEstimation(first, i);
      }
    }
    return result;
  }
  /**
   * Get latest military difference between two players, using espionage
   * information and news corp.
   * @param first First player index. This is where second one is compared.
   * @param second Second player index.
   * @return Positive number if first player has bigger military
   */
  public int getMilitaryDifference(final int first, final int second) {
    int result = 0;
    PlayerInfo firstInfo = players.getPlayerInfoByIndex(first);
    PlayerInfo secondInfo = players.getPlayerInfoByIndex(second);
    if (!secondInfo.getDiplomacy().hasDefensivePact()) {
      int newsCorpDiff = getNewsCorpData().getMilitaryDifference(first,
          second);
      result = newsCorpDiff;
      int actualMilitaryFirst = NewsCorpData.calculateMilitaryValue(firstInfo);
      int actualMilitarySecond = NewsCorpData.calculateMilitaryValue(
          secondInfo);
      EspionageList espionageList = firstInfo.getEspionage().getByIndex(
          second);
      int estimateSecond = espionageList.estimateMilitaryPower(
          actualMilitarySecond);
      int estimateDiff = actualMilitaryFirst - estimateSecond;
      if (espionageList.getTotalBonus() >= 7) {
        // 90-100% accuracy
        result = estimateDiff;
      } else if (espionageList.getTotalBonus() >= 5) {
        // 80% accuracy
        result = estimateDiff * 90 / 100 + newsCorpDiff * 10 / 100;
      } else if (espionageList.getTotalBonus() >= 3) {
        // 70% accuracy
        result = estimateDiff * 80 / 100 + newsCorpDiff * 20 / 100;
      } else if (espionageList.getTotalBonus() >= 1) {
        // 70% accuracy
        result = estimateDiff * 70 / 100 + newsCorpDiff * 30 / 100;
      }
    } else {
      int actualMilitaryFirst = NewsCorpData.calculateMilitaryValue(firstInfo);
      int estimate = getMilitaryEstimationForDefensivePact(first, second);
      result = actualMilitaryFirst - estimate;
    }
    return result;
  }
  /**
   * Culture level 1
   */
  private static final int CULTURE_LEVEL_0 = 1;
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
    if (value == CULTURE_LEVEL_0) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */"...............\n"
      + /* 2 */"...............\n"
      + /* 1 */"...............\n"
      + /* 0 */".......X.......\n"
      + /* 1 */"...............\n"
      + /* 2 */"...............\n"
      + /* 3 */"...............\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_1) {
      //        765432101234567
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
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */"...............\n"
      + /* 2 */"...............\n"
      + /* 1 */"......1X1......\n"
      + /* 0 */"......XXX......\n"
      + /* 1 */"......1X1......\n"
      + /* 2 */"...............\n"
      + /* 3 */"...............\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_3) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */"...............\n"
      + /* 2 */".......1.......\n"
      + /* 1 */"......1X1......\n"
      + /* 0 */".....1XXX1.....\n"
      + /* 1 */"......1X1......\n"
      + /* 2 */".......1.......\n"
      + /* 3 */"...............\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_4) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"...............\n"
      + /* 3 */".......1.......\n"
      + /* 2 */"......1X1......\n"
      + /* 1 */".....1XXX1.....\n"
      + /* 0 */"....1XXXXX1....\n"
      + /* 1 */".....1XXX1.....\n"
      + /* 2 */"......1X1......\n"
      + /* 3 */".......1.......\n"
      + /* 4 */"...............\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_5) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */".......1.......\n"
      + /* 3 */"......121......\n"
      + /* 2 */".....12X21.....\n"
      + /* 1 */"....12XXX21....\n"
      + /* 0 */"...12XXXXX21...\n"
      + /* 1 */"....12XXX21....\n"
      + /* 2 */".....12X21.....\n"
      + /* 3 */"......121......\n"
      + /* 4 */".......1.......\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_6) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"...............\n"
      + /* 4 */"......121......\n"
      + /* 3 */".....12X21.....\n"
      + /* 2 */"....12XXX21....\n"
      + /* 1 */"...12XXXXX21...\n"
      + /* 0 */"...2XXXXXXX2...\n"
      + /* 1 */"...12XXXXX21...\n"
      + /* 2 */"....12XXX21....\n"
      + /* 3 */".....12X21.....\n"
      + /* 4 */"......121......\n"
      + /* 5 */"...............\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_7) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"...............\n"
      + /* 5 */"......121......\n"
      + /* 4 */".....12X21.....\n"
      + /* 3 */"....12XXX21....\n"
      + /* 2 */"...12XXXXX21...\n"
      + /* 1 */"..12XXXXXXX21..\n"
      + /* 0 */"..2XXXXXXXXX2..\n"
      + /* 1 */"..12XXXXXXX21..\n"
      + /* 2 */"...12XXXXX21...\n"
      + /* 3 */"....12XXX21....\n"
      + /* 4 */".....12X21.....\n"
      + /* 5 */"......121......\n"
      + /* 6 */"...............\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_8) {
      //        765432101234567
      mask = /* 7 */"...............\n"
      + /* 6 */"......121......\n"
      + /* 5 */".....12321.....\n"
      + /* 4 */"....123X321....\n"
      + /* 3 */"...123XXX321...\n"
      + /* 2 */"..123XXXXX321..\n"
      + /* 1 */".123XXXXXXX321.\n"
      + /* 0 */".23XXXXXXXXX32.\n"
      + /* 1 */".123XXXXXXX321.\n"
      + /* 2 */"..123XXXXX321..\n"
      + /* 3 */"...123XXX321...\n"
      + /* 4 */"....123X321....\n"
      + /* 5 */".....12321.....\n"
      + /* 6 */"......121......\n"
      + /* 7 */"...............\n";
    } else if (value < CULTURE_LEVEL_9) {
      //        765432101234567
      mask = /* 7 */".......1.......\n"
      + /* 6 */"......121......\n"
      + /* 5 */"....123X321....\n"
      + /* 4 */"....2XXXXX2....\n"
      + /* 3 */"..123XXXXX321..\n"
      + /* 2 */"..23XXXXXXX32..\n"
      + /* 1 */".12XXXXXXXXX21.\n"
      + /* 0 */"123XXXXXXXXX321\n"
      + /* 1 */".12XXXXXXXXX21.\n"
      + /* 2 */"..23XXXXXXX32..\n"
      + /* 3 */"..123XXXXX321..\n"
      + /* 4 */"....2XXXXX2....\n"
      + /* 5 */"....123X321....\n"
      + /* 6 */"......121......\n"
      + /* 7 */".......1.......\n";
    } else {
      //        765432101234567
      mask = /* 7 */"......121......\n"
      + /* 6 */".....12321.....\n"
      + /* 5 */"...123XXX321...\n"
      + /* 4 */"...123XXX321...\n"
      + /* 3 */".123XXXXXXX321.\n"
      + /* 2 */".123XXXXXXX321.\n"
      + /* 1 */"123XXXXXXXXX321\n"
      + /* 0 */"23XXXXXXXXXXX32\n"
      + /* 1 */"123XXXXXXXXX321\n"
      + /* 2 */".123XXXXXXX321.\n"
      + /* 3 */".123XXXXXXX321.\n"
      + /* 4 */"...123XXX321...\n"
      + /* 5 */"...123XXX321...\n"
      + /* 6 */".....12321.....\n"
      + /* 7 */"......121......\n";
    }

    String[] lines = mask.split("\n");
    int x = -MAX_CULTURE_RADIUS;
    int y = -MAX_CULTURE_RADIUS;
    for (int line = 0; line < lines.length; line++) {
      for (int col = 0; col < lines[line].length(); col++) {
        if (lines[line].charAt(col) != '.') {
          int adjustValue = value;
          if (lines[line].charAt(col) == '1') {
            adjustValue = adjustValue / 2;
          }
          if (lines[line].charAt(col) == '2') {
            adjustValue = adjustValue * 2 / 3;
          }
          if (lines[line].charAt(col) == '3') {
            adjustValue = adjustValue * 3 / 4;
          }
          addSectorCulture(cx + x, cy + y, index, adjustValue);
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
        if (detectValue > 0
            && info.getSectorCloakDetection(nx, ny) < detectValue) {
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
   * Calculate average happiness for whole realm
   * @param playerIndex Realm or Player index
   * @return Average Happiness by population
   */
  public int calculateAverageHappiness(final int playerIndex) {
    int happiness = 0;
    int population = 0;
    for (int i = 0; i < planetList.size(); i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetOwnerIndex() == playerIndex) {
        int planetPopulation = planet.getTotalPopulation();
        happiness = happiness + planet.calculateHappiness() * planetPopulation;
        population = population + planetPopulation;
      }
    }
    int result = 0;
    if (population != 0) {
      result = happiness / population;
    }
    return result;
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
    PlayerInfo info = getPlayerByIndex(playerIndex);
    for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
      Fleet fleet = info.getFleets().getByIndex(i);
      if (production == Planet.PRODUCTION_RESEARCH) {
        result = result + fleet.getTotalReseachBonus();
      }
      if (production == Planet.PRODUCTION_CREDITS) {
        result = result + fleet.getTotalCreditsBonus();
      }
      if (production == Planet.PRODUCTION_CULTURE) {
        result = result + fleet.getTotalCultureBonus();
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
      if (players.getCurrentPlayer() + 1 == players.getCurrentMaxRealms()) {
        players.setCurrentPlayer(0);
        setDebug(false);
      } else {
        players.setCurrentPlayer(players.getCurrentPlayer() + 1);
        setDebug(true);
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
   * Is tile dangerous or not
   * @param x X coordinate
   * @param y Y Coordinate
   * @return true if tile is dangerous otherwise false. Also if
   * coordinate is out of map then true is returned.
   */
  public boolean isDangerous(final int x, final int y) {
    Tile tile = getTile(x, y);
    if (tile != null) {
      return tile.isDangerous();
    }
    return true;
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
   * Check if there is fleet in coordinates. Return fleet owner's
   * playerinfo
   * @param x X coordinate
   * @param y Y coordinate
   * @return PlayerInfo if found or null
   */
  public PlayerInfo isBlockedByFleet(final int x, final int y) {
    if (fleetTiles == null) {
      getFleetTiles();
    }
    if (isValidCoordinate(x, y) && fleetTiles[x][y] != null) {
      int index = fleetTiles[x][y].getPlayerIndex();
      return players.getPlayerInfoByIndex(index);
    }
    return null;
  }

  /**
   * Check if two players in war
   * @param first First playerinfo
   * @param second Second playerinfo
   * @return True if they are in war, otherwise false
   */
  public boolean isWarBetween(final PlayerInfo first,
      final PlayerInfo second) {
    int secondIndex = players.getIndex(second);
    DiplomacyBonusList list = first.getDiplomacy().getDiplomacyList(
        secondIndex);
    if (list != null && list.isBonusType(DiplomacyBonusType.IN_WAR)) {
      return true;
    }
    return false;
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

  /**
   * Get next planet for player which is owned by that player
   * @param info Player who owns the planet
   * @param currentPlanet Currently clicked planet, does not need to be
   * owned by the player
   * @param forward If true moves forward, on false moves backward
   * @return Next player planet or same planet
   */
  public Planet getNextPlanetForPlayer(final PlayerInfo info,
      final Planet currentPlanet, final boolean forward) {
    int startIndex = 0;
    for (int i = 0; i < planetList.size(); i++) {
      Planet planet = planetList.get(i);
      if (planet.getX() == currentPlanet.getX()
          && planet.getY() == currentPlanet.getY()) {
        startIndex = i;
        break;
      }
    }
    int i = startIndex;
    boolean exitLoop = false;
    while (!exitLoop) {
      if (forward) {
        i++;
      } else {
        i--;
      }
      if (i >= planetList.size()) {
        i = 0;
      }
      if (i <= -1) {
        i = planetList.size() - 1;
      }
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetPlayerInfo() == info) {
        exitLoop = true;
        return planet;
      }
      if (i == startIndex) {
        exitLoop = true;
      }
    }
    // Return same planet
    return planetList.get(startIndex);
  }

  /**
   * Is player stroger to compared to another player.
   * This method compares mostly military power
   * @param index Player whos is making the compare
   * @param compare Player index whom to compare
   * @return True if index player is stronger than compare
   */
  public boolean isPlayerStrongerThan(final int index, final int compare) {
    if (index >= 0 && index < getPlayerList().getCurrentMaxRealms()
        && compare >= 0 && compare < getPlayerList().getCurrentMaxRealms()
        && compare != index) {
      int power = newsCorpData.getMilitary().getLatest(index);
      int powerCompare = newsCorpData.getMilitary().getLatest(compare);
      power = power + newsCorpData.getPlanets().getLatest(index) * 2;
      powerCompare = powerCompare + newsCorpData.getPlanets()
        .getLatest(compare) * 2;
      if (power > powerCompare) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if in debug mode
   * @return the debug
   */
  public boolean isDebug() {
    return debug;
  }

  /**
   * Set debug mode
   * @param debug the debug to set
   */
  public void setDebug(final boolean debug) {
    this.debug = debug;
  }

  /**
   * Check is ShipStat is being built.
   * @param stat Ship stat
   * @param builder Player who is building
   * @return True if design is being built
   */
  public boolean isShipStatBeingBuilt(final ShipStat stat,
      final PlayerInfo builder) {
    for (Planet planet : planetList) {
      if (planet.getPlanetPlayerInfo() == builder
          && planet.getUnderConstruction() != null
          && planet.getUnderConstruction().getName().equals(stat.getDesign()
              .getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Planet next to a coordinate. Checks current coordinate
   * and orthogonal sectors.
   * @param coord Coordinate to check
   * @return Planet or null
   */
  public Planet getPlanetNextToCoordinate(final Coordinate coord) {
    Planet result = null;
    result = getPlanetByCoordinate(coord.getX(), coord.getY());
    if (result == null) {
      result = getPlanetByCoordinate(coord.getX(), coord.getY() - 1);
    }
    if (result == null) {
      result = getPlanetByCoordinate(coord.getX() + 1, coord.getY());
    }
    if (result == null) {
      result = getPlanetByCoordinate(coord.getX(), coord.getY() + 1);
    }
    if (result == null) {
      result = getPlanetByCoordinate(coord.getX() - 1, coord.getY());
    }
    return result;
  }

  /**
   * Get the turn number where scoring victory happens.
   * @return the scoreVictoryTurn
   */
  public int getScoreVictoryTurn() {
    return scoreVictoryTurn;
  }

  /**
   * Set the turn number where scoring victory happens.
   * Minimum is 200 and maximum is 1000 turns.
   * @param scoreVictoryTurn the scoreVictoryTurn to set
   */
  public void setScoreVictoryTurn(final int scoreVictoryTurn) {
    if (scoreVictoryTurn > 1000) {
      this.scoreVictoryTurn = 1000;
    } else if (scoreVictoryTurn < 200) {
      this.scoreVictoryTurn = 200;
    } else {
      this.scoreVictoryTurn = scoreVictoryTurn;
    }
  }

  /**
   * get Score culture. This return setting value for culture scoring.
   * -1 Means that it is disabled
   * 0 75% of regular limit
   * 1 100% of regular limit
   * 2 150% of regular limit
   * 3 200% of regular limit
   * @return Culture score setting limit
   */
  public int getScoreCulture() {
    return scoreCulture;
  }

  /**
   * Set culture score limit setting
   * @param limit Culture score setting
   */
  public void setScoreCulture(final int limit) {
    scoreCulture = limit;
  }

  /**
   * Get Conquer score limit.
   * 0 Disabled
   * 1 Enabled
   * @return Conquer score limit
   */
  public int getScoreConquer() {
    return scoreConquer;
  }

  /**
   * Set Conquer score limit
   * @param limit 1 for enabling, 0 for disabling
   */
  public void setScoreConquer(final int limit) {
    scoreConquer = limit;
  }

  /**
   * Get Score limit for research
   * THIS HAS NOT IMPLEMETED YET
   * @return Score limit for research
   */
  public int getScoreResearch() {
    return scoreResearch;
  }

  /**
   * Set Score limit for research
   * THIS HAS NOT IMPLEMETED YET
   * @param limit Limit for research
   */
  public void setScoreResearch(final int limit) {
    scoreResearch = limit;
  }

  /**
   * Get Score limit for diplomacy
   * THIS HAS NOT IMPLEMETED YET
   * @return Score limit for diplomacy
   */
  public int getScoreDiplomacy() {
    return scoreDiplomacy;
  }

  /**
   * Set Score limit for diplomacy
   * THIS HAS NOT IMPLEMETED YET
   * @param limit Limit for diplomacy
   */
  public void setScoreDiplomacy(final int limit) {
    scoreDiplomacy = limit;
  }
  /**
   * Get Game length state. This checks current turn and
   * compares it score victory turn. Game is divided into
   * five different states: START_GAME, EARLY_GAME, MIDDLE_GAME,
   * LATE_GAME and END_GAME
   * @return GameLengthState enumeration
   */
  public GameLengthState getGameLengthState() {
    return GameLengthState.getGameLengthState(getTurn(),
        getScoreVictoryTurn());
  }

  /**
   * Get history from starmap
   * @return History
   */
  public History getHistory() {
    return history;
  }

  /**
   * Set new history
   * @param newHistory History to set
   */
  public void setHistory(final History newHistory) {
    history = newHistory;
  }

  /**
   * Has game ended yet? This means has last news
   * given already? This information is not saved into save game.
   * @return True if game has ended
   */
  public boolean isGameEnded() {
    return gameEnd;
  }

  /**
   * Set game ended flag.
   * @param end True to end game.
   */
  public void setGameEnded(final boolean end) {
    gameEnd = end;
  }

  /**
   * Get free random spot from galaxy
   * @return Coordinate where is free or null
   */
  public Coordinate getFreeRandomSpot() {
    int loop = 1000;
    while (loop > 0) {
      loop--;
      int sx = DiceGenerator.getRandom(1,
          maxX - 2);
      int sy = DiceGenerator.getRandom(1,
          maxX - 2);
      if (!isBlocked(sx, sy) && isBlockedByFleet(sx, sy) == null) {
        return new Coordinate(sx, sy);
      }
    }
    return null;
  }
}
