package org.openRealmOfStars.starMap;
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

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionHandling;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.ai.planet.PlanetHandling;
import org.openRealmOfStars.ai.research.Research;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.WinningStrategy;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.espionage.IntelligenceBonusType;
import org.openRealmOfStars.player.espionage.IntelligenceList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.event.KarmaEvents;
import org.openRealmOfStars.starMap.event.KarmaType;
import org.openRealmOfStars.starMap.history.History;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.starMap.planet.enums.GravityType;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.Votes;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.namegenerators.UnrealPlanetNameGenerator;
import org.openRealmOfStars.utilities.repository.NewsCorpRepository;
import org.openRealmOfStars.utilities.repository.PlanetRepository;
import org.openRealmOfStars.utilities.repository.SunRepository;

/**
 *
 * Class planet
 *
 */
public class StarMap {

  /**
   * Maximum number of player
   */
  public static final int MAX_PLAYERS = 16;

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
   * Enemy type for pirate colony ship.
   */
  public static final int ENEMY_PIRATE_COLONY = 3;

  /**
   * Star year when game starts. Elder realms start earlier.
   * This is for turn 0.
   */
  private static final int START_STARYEAR = 2400;
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
   * Population scoring setting
   */
  private int scorePopulation;


  /**
   * Game history containing important events.
   * History information is not saved in to same save game as
   * starmap. It is saved into separate file.
   */
  private History history;

  /**
   * Votes made in the galaxy.
   */
  private Votes votes;
  /**
   * Game has ended
   */
  private boolean gameEnd;

  /**
   * Human player has lost the game.
   */
  private boolean humanLost;
  /**
   * Pirate difficulty level.
   */
  private PirateDifficultLevel pirateDifficulty;

  /** Karma event system */
  private KarmaEvents karmaEvents;

  /**
   * Flag for tutorial enabled
   */
  private boolean tutorialEnabled;

  /**
   * List of shown tutorial index.
   * This is place where they are loaded from when reading the save file.
   */
  private ArrayList<Integer> shownTutorialIndexes;
  /**
   * All news from galaxy enabled.
   */
  private boolean allNewsEnabled;
  /**
   * AI or Automate is taking fleet moves. This does not to need to save into
   * save file. This is just a flag which will be set on when AI Turn is
   * progressing.
   */
  private boolean aiOrAutomateTakingMoves = false;

  /**
   * Flag to force redraw for map.
   */
  private boolean forceRedraw = false;
  /**
   * Starmap zoom level. This will not be saved on file.
   */
  private int zoomLevel;
  /**
   * Magic string to save game files
   */
  public static final String MAGIC_STRING = "OROS-SAVE-GAME-0.26";


  /**
   * Base constructor for StarMap.
   * @param maxX Maximum X coordinate
   * @param maxY maximum Y coordinate
   * @param maxPlayers Maximum number of players
   * @param maxRealms Maximum number of realms
   */
  public StarMap(final int maxX, final int maxY, final int maxPlayers,
      final int maxRealms) {
    zoomLevel = Tile.ZOOM_NORMAL;
    setDebug(false);
    setHumanLost(false);
    history = new History();
    votes = new Votes();
    shownTutorialIndexes = new ArrayList<>();
    this.maxX = maxX;
    this.maxY = maxY;
    drawX = 0;
    drawY = 0;
    tiles = new int[maxX][maxY];
    tileInfo = new SquareInfo[maxX][maxY];
    culture = new CulturePower[maxX][maxY];
    sunList = new ArrayList<>();
    planetList = new ArrayList<>();
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i = 0; i < maxX; i++) {
      for (int j = 0; j < maxY; j++) {
        tiles[i][j] = empty.getIndex();
        tileInfo[i][j] = SquareInfo.EMPTY_TILE;
        culture[i][j] = new CulturePower(maxPlayers);
      }
    }
    newsCorpData = new NewsCorpData(maxRealms);
    aiTurnNumber = 0;
    aiFleet = null;

  }
  /**
   * Define Karma events for starmap.
   * @param karmaType KarmaType
   * @param karmaSpeed How many karma points is added per star year.
   */
  public void defineKarmaEvents(final KarmaType karmaType,
      final int karmaSpeed) {
    karmaEvents = new KarmaEvents(karmaType, karmaSpeed);
  }

  /**
   * Set StarMap Players aka realms.
   * @param players Players list.
   */
  public void setPlayers(final PlayerList players) {
    this.players = players;
  }

  /**
   * Reveal whole map. This should be used only for debugging.
   * @param info PlayerInfo who sees everything.
   */
  public void revealWholeMap(final PlayerInfo info) {
    if (info != null) {
      for (int y = 0; y < maxY; y++) {
        for (int x = 0; x < maxX; x++) {
          info.setSectorVisibility(x, y, PlayerInfo.VISIBLE_VEINS);
        }
      }
    }
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
   * Adds one pirate colony ship into coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @param playerInfo Board player info
   */
  public void addSpacePirateColony(final int x, final int y,
      final PlayerInfo playerInfo) {
    addSpaceAnomalyEnemy(x, y, playerInfo, ENEMY_PIRATE_COLONY);
  }

  /**
   * Calculate how many artificial planets player has.
   * @param info PlayerInfo
   * @return Number of artificial planets.
   */
  public int countArtificialPlanets(final PlayerInfo info) {
    int result = 0;
    for (int i = 0; i < planetList.size(); i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() == info
          && planet.getPlanetType() == PlanetTypes.ARTIFICIALWORLD1) {
        result++;
      }
    }
    return result;
  }

  /**
   * Find Suitable planet for space pirates.
   * @param pirate PlayerInfo for pirates
   * @param x X coordinate where fleet is going to be created.
   * @param y Y coordinate where fleet is going to be created.
   * @return Found planet or null
   */
  public Planet findSuitablePlanetForPirates(final PlayerInfo pirate,
      final int x, final int y) {
    Planet result = null;
    double distance = 999;
    for (Planet planet : getPlanetList()) {
      if (planet.isColonizeablePlanet(pirate)
          && planet.getPlanetPlayerInfo() == null && !planet.isGasGiant()
          && pirate.getSectorVisibility(planet.getCoordinate())
          > PlayerInfo.UNCHARTED && planet.getPlanetOwnerIndex() == -1) {
        Coordinate from = new Coordinate(x, y);
        double dist = planet.getCoordinate().calculateDistance(from);
        if (dist < distance) {
          result = planet;
          distance = dist;
        }
      }
    }
    return result;
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
    int artificialPlanets = countArtificialPlanets(playerInfo);
    ShipStat[] stats = playerInfo.getShipStatList();
    ArrayList<ShipStat> listStats = new ArrayList<>();
    for (ShipStat stat : stats) {
      if (!stat.isObsolete()) {
        Ship ship = new Ship(stat.getDesign());
        if (type == ENEMY_PIRATE_LAIR && ship.isStarBase()) {
          if (ship.getHull().getName().equals("Artificial planet")
              && artificialPlanets < 2) {
            listStats.add(stat);
          }
          if (!ship.getHull().getName().equals("Artificial planet")) {
            listStats.add(stat);
          }
        }
        if (type == ENEMY_PIRATE && ship.getTotalMilitaryPower() > 0
            && !ship.isStarBase()
            && ship.getHull().getHullType() != ShipHullType.ORBITAL) {
          listStats.add(stat);
        }
        if (type == ENEMY_PIRATE_COLONY && ship.isColonyModule()) {
          listStats.add(stat);
        }
        if (type == ENEMY_MONSTER && ship.getTheoreticalMilitaryPower() > 0
            && !ship.isStarBase()
            && ship.getHull().getHullType() != ShipHullType.ORBITAL) {
          listStats.add(stat);
        }
      }
    }
    if (listStats.size() > 0) {
      ShipStat stat = DiceGenerator.pickRandom(listStats);
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
          if (ship.getHull().getName().equals("Artificial planet")) {
            createArtificialPlanet(starbaseFleet, playerInfo);
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
        Sun sun = getAboutNearestSolarSystem(x, y, playerInfo, null);
        Mission mission = new Mission(MissionType.PRIVATEER,
            MissionPhase.TREKKING, sun.getCenterCoordinate());
        mission.setFleetName(fleet.getName());
        mission.setSunName(sun.getName());
        playerInfo.getMissions().add(mission);
      }
      if (type == ENEMY_PIRATE_COLONY) {
        Planet planet = findSuitablePlanetForPirates(playerInfo, x, y);
        if (planet != null) {
          fleet = new Fleet(ship, x, y);
          ship.setColonist(1);
          playerInfo.getFleets().add(fleet);
          fleet.setName(playerInfo.getFleets().generateUniqueName(
              "pirate colony"));
          Mission mission = new Mission(MissionType.COLONIZE,
            MissionPhase.TREKKING, planet.getCoordinate());
          playerInfo.getMissions().add(mission);
        }
      }
      if (type == ENEMY_MONSTER) {
        fleet = new Fleet(ship, x, y);
        playerInfo.getFleets().add(fleet);
        fleet.setName(playerInfo.getFleets().generateUniqueName(
            ship.getName()));
        Mission mission = new Mission(MissionType.ROAM,
            MissionPhase.TREKKING, fleet.getCoordinate());
        mission.setFleetName(fleet.getName());
        playerInfo.getMissions().add(mission);
      }
      return fleet;
    }
    return null;
  }


  /**
   * Initialize StarMap from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public StarMap(final DataInputStream dis) throws IOException {
    zoomLevel = Tile.ZOOM_NORMAL;
    setDebug(false);
    setHumanLost(false);
    history = new History();
    votes = new Votes();
    shownTutorialIndexes = new ArrayList<>();
    tutorialEnabled = false;
    setPirateDifficulty(PirateDifficultLevel.NORMAL);
    karmaEvents = new KarmaEvents(KarmaType.DISABLED, 0);
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
      setScorePopulation(dis.readInt());
      setPirateDifficulty(PirateDifficultLevel.getLevelByInt(dis.read()));
      karmaEvents = new KarmaEvents(dis);
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
      // Handle realm lost.
      for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
        PlayerInfo info = players.getPlayerInfoByIndex(i);
        for (int j = 0; j < players.getCurrentMaxPlayers(); j++) {
          if (info.getDiplomacy().isLost(j)) {
            PlayerInfo realm = players.getPlayerInfoByIndex(j);
            if (!realm.isRealmLost()) {
              realm.setRealmLost(true);
              makeRealmLost(realm);
            }
          }
        }
      }
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
      setAllNewsEnabled(dis.readBoolean());
      NewsCorpRepository newsCorpRepo = new NewsCorpRepository();
      newsCorpData = newsCorpRepo.restoreNewsCorp(dis,
          players.getCurrentMaxRealms());
      votes = new Votes(dis, players.getCurrentMaxRealms());
      int value = dis.read();
      if (value == 1) {
        tutorialEnabled = true;
      } else {
        tutorialEnabled = false;
      }
      value = dis.readInt();
      for (int i = 0; i < value; i++) {
        int index = IOUtilities.read16BitsToInt(dis);
        Integer intValue = Integer.valueOf(index);
        shownTutorialIndexes.add(intValue);
      }
      try {
        history = History.readFromStream(dis);
      } catch (IOException e) {
        ErrorLogger.log("Failed reading history data,"
            + " maybe save is missing it.");
        throw e;
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
   * Before calling this remove to update shown tutorial indexes
   * from tutorial.
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveGame(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, "OROS-SAVE-GAME-0.26");
    // Turn number
    dos.writeInt(turn);
    // Victory conditions
    dos.writeInt(getScoreVictoryTurn());
    dos.writeInt(getScoreCulture());
    dos.writeInt(getScoreConquer());
    dos.writeInt(getScoreResearch());
    dos.writeInt(getScoreDiplomacy());
    dos.writeInt(getScorePopulation());
    dos.writeByte(getPirateDifficulty().getIndex());
    karmaEvents.save(dos);
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
    dos.writeBoolean(allNewsEnabled);
    NewsCorpRepository newsCorpRepo = new NewsCorpRepository();
    newsCorpRepo.saveNewsCorp(dos, newsCorpData);
    votes.saveVotes(dos);
    if (tutorialEnabled) {
      dos.writeByte(1);
    } else {
      dos.writeByte(0);
    }
    dos.writeInt(shownTutorialIndexes.size());
    for (int i = 0; i < shownTutorialIndexes.size(); i++) {
      Integer value = shownTutorialIndexes.get(i);
      dos.write(IOUtilities.convertIntTo16BitMsb(value.intValue()));
    }
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
   * @param ignoreSun Sun to ignore
   * @return Nearest sun
   */
  public Sun getNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final String ignoreSun) {
    return getNearestSolarSystem(x, y, info, ignoreSun, false);
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
   * @param ignoreSun Sun to ignore
   * @return Nearest sun
   */
  public Sun getAboutNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final String ignoreSun) {
    return getNearestSolarSystem(x, y, info, ignoreSun, true);
  }

  /**
   * Get list of suns which should be ignored when starting to explore.
   * @param info Realm which is exploring
   * @return List of ignorable suns.
   */
  public String[] getListOfSunsToIgnore(final PlayerInfo info) {
    ArrayList<String> listOfSuns = new ArrayList<>();
    ArrayList<String> exploredSuns = new ArrayList<>();
    for (Sun sun : sunList) {
      Mission mission = info.getMissions().getExploringForSun(sun.getName());
      if (mission != null) {
        listOfSuns.add(sun.getName());
        continue;
      }
      int uncharted = info.getUnchartedValueSystem(sun);
      if (uncharted < 30) {
        exploredSuns.add(sun.getName());
      }
    }
    if (exploredSuns.size() + listOfSuns.size() <= sunList.size()) {
      listOfSuns.addAll(exploredSuns);
    }
    return listOfSuns.toArray(new String[listOfSuns.size()]);
  }

  /**
   * Get sun type based on tile.
   * @param sun Sun which type is to be get.
   * @return SunType
   */
  public SunType getSunType(final Sun sun) {
    SunType type = SunType.RED_STAR;
    Tile tile = getTile(sun.getCenterX(), sun.getCenterY());
    if (tile.getName().equals(TileNames.SUN_C)) {
      type = SunType.RED_STAR;
    }
    if (tile.getName().equals(TileNames.BLUE_STAR_C)) {
      type = SunType.BLUE_STAR;
    }
    if (tile.getName().equals(TileNames.STAR_C)) {
      type = SunType.YELLOW_STAR;
    }
    return type;
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
   * @param ignoreSuns Suns to ignore
   * @param second true if possible to return second closest solar system
   * @return Nearest sun
   */
  public Sun getNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final Fleet fleet, final String[] ignoreSuns,
      final boolean second) {
    double distance = LONGEST_DISTANCE;
    double blueStarDistance = 10;
    double yellowStarDistance = 10;
    if (info.getRace().getMaxRad() == RadiationType.NO_RADIATION) {
      blueStarDistance = 40;
      yellowStarDistance = 15;
    } else if (info.getRace().getMaxRad() == RadiationType.LOW_RADIATION) {
      blueStarDistance = 20;
      yellowStarDistance = 10;
    } else if (info.getRace().getMaxRad() == RadiationType.HIGH_RADIATION) {
      blueStarDistance = 8;
      yellowStarDistance = 2;
    } else if (info.getRace().getMaxRad() == RadiationType.VERY_HIGH_RAD) {
      blueStarDistance = 2;
      yellowStarDistance = 0;
    }

    Sun result = null;
    Sun secondChoice = null;
    double secondDistance = LONGEST_DISTANCE;
    int leastChartedValue = 100;
    Sun leastCharted = null;
    double leastChartedDist = LONGEST_DISTANCE;
    for (Sun sun : sunList) {
      boolean ignore = false;
      for (String ignoresun : ignoreSuns) {
        if (sun.getName().equals(ignoresun)) {
          ignore = true;
          break;
        }
      }
      if (ignore) {
        continue;
      }
      Coordinate coordinate = new Coordinate(x, y);
      double dist = coordinate.calculateDistance(sun.getCenterCoordinate());
      SunType type = getSunType(sun);
      if (type == SunType.BLUE_STAR) {
        dist = dist + blueStarDistance;
      }
      if (type == SunType.YELLOW_STAR) {
        dist = dist + yellowStarDistance;
      }
      int uncharted = info.getUnchartedValueSystem(sun);
      if (dist < distance && uncharted > 50) {
        secondDistance = distance;
        distance = dist;
        secondChoice = result;
        result = sun;
      } else if (dist < secondDistance
          && uncharted > 50) {
        secondDistance = dist;
        secondChoice = sun;
      }
      if (uncharted < leastChartedValue && dist < leastChartedDist) {
        leastChartedDist = dist;
        leastCharted = sun;
        leastChartedValue = uncharted;
      }
    }
    if (result != null && secondChoice != null && second) {
      if (DiceGenerator.getBoolean()) {
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
   * Get nearest uncharted Solar system for coordinate. This should never
   * return null. Unless there are no suns in galaxy. This might not always
   * return same result. If there are two suns with more than 50% uncharted
   * then it is randomized which one is returned. This allows creating
   * more varying for AI's explore missions.
   * @param x coordinate
   * @param y coordinate
   * @param info Player who is doing the search
   * @param ignoreSun Sun to ignore
   * @param second true if possible to return second closest solar system
   * @return Nearest sun
   */
  private Sun getNearestSolarSystem(final int x, final int y,
      final PlayerInfo info, final String ignoreSun,
      final boolean second) {
    double distance = LONGEST_DISTANCE;
    Sun result = null;
    Sun secondChoice = null;
    double secondDistance = LONGEST_DISTANCE;
    int leastChartedValue = 100;
    Sun leastCharted = null;
    Coordinate coordinate = new Coordinate(x, y);
    for (Sun sun : sunList) {
      if (info.getAiDifficulty() == AiDifficulty.NORMAL
          || info.getAiDifficulty() == AiDifficulty.CHALLENGING) {
        Mission mission = info.getMissions().getExploringForSun(sun.getName());
        if (mission != null && leastCharted != null) {
          continue;
        }
      }
      double dist = coordinate.calculateDistance(sun.getCenterCoordinate());
      if (ignoreSun != null && ignoreSun.equals(sun.getName())) {
        dist = LONGEST_DISTANCE;
      }
      if (dist < distance && info.getUnchartedValueSystem(sun) > 50) {
        secondDistance = distance;
        distance = dist;
        secondChoice = result;
        result = sun;
      } else if (dist < secondDistance
          && info.getUnchartedValueSystem(sun) > 50) {
        secondDistance = dist;
        secondChoice = sun;
      }
      if (info.getUnchartedValueSystem(sun) < leastChartedValue) {
        leastCharted = sun;
        leastChartedValue = info.getUnchartedValueSystem(sun);
      }
    }
    if (result != null && secondChoice != null && second) {
      if (DiceGenerator.getBoolean()) {
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
        result = DiceGenerator.pickRandom(sunList);
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
      return Tiles.getTileByIndex(tiles[x][y], zoomLevel);
    }
    return null;
  }

  /**
   * Get tile index for coordinate
   * @param x Coordinate X
   * @param y Coordinate Y
   * @return Tile index from coordinate or 0.
   */
  public int getTileIndex(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      return tiles[x][y];
    }
    return 0;
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
   * Set tile for coordinate
   * @param x Coordinate X
   * @param y Coordinate Y
   * @param tileIndex TileIndex to set
   */
  public void setTile(final int x, final int y, final int tileIndex) {
    if (isValidCoordinate(x, y)) {
      tiles[x][y] = tileIndex;
    }
  }

  /**
   * Set Square info
   * @param x Coordinate X
   * @param y Coordinate Y
   * @param squareType Type
   * @param value SquareValue
   */
  public void setSquareInfo(final int x, final int y, final byte squareType,
      final int value) {
    if (isValidCoordinate(x, y)) {
      tileInfo[x][y] = new SquareInfo(squareType, value);
    }
  }
  /**
   * Set Square info
   * @param x Coordinate X
   * @param y Coordinate Y
   * @param info SquareInfo
   */
  public void setSquareInfo(final int x, final int y, final SquareInfo info) {
    if (isValidCoordinate(x, y)) {
      tileInfo[x][y] = info;
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
   * Set Fleet tile for certain position. If position already contains fleet
   * tile and realm is different then tile is being marked as conflict.
   * @param x X coordinate
   * @param y Y Coordinate
   * @param info FleetTileInfo
   */
  private void setFleetTile(final int x, final int y,
      final FleetTileInfo info) {
    if (fleetTiles[x][y] != null) {
      int oldIndex = fleetTiles[x][y].getConflictIndex();
      if (oldIndex == -1) {
        oldIndex = fleetTiles[x][y].getPlayerIndex();
      }
      fleetTiles[x][y] = info;
      if (fleetTiles[x][y].getPlayerIndex() != oldIndex) {
        fleetTiles[x][y].setConflict(oldIndex);
      }
    } else {
      fleetTiles[x][y] = info;
    }
  }

  /**
   * Get biggest military fleet in certain coordinates.
   * @param coord Coordinates where to look for ship
   * @param playerIndex Which players fleets are searched
   * @return Biggest fleet at coordinate or null
   */
  public Fleet getBiggestFleet(final Coordinate coord, final int playerIndex) {
    Fleet biggest = null;
    PlayerInfo player = players.getPlayerInfoByIndex(playerIndex);
    if (player == null) {
      return null;
    }
    for (int j = 0; j < player.getFleets().getNumberOfFleets(); j++) {
      Fleet fleet = player.getFleets().getByIndex(j);
      if (fleet.getCoordinate().sameAs(coord) && biggest == null) {
        biggest = fleet;
        continue;
      }
      if (fleet.getCoordinate().sameAs(coord) && biggest != null
          && fleet.getMilitaryValue() > biggest.getMilitaryValue()) {
        biggest = fleet;
        continue;
      }
    }
    return biggest;
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
            Ship ship = fleet.getBiggestShip();
            FleetTileInfo info = new FleetTileInfo(
                ship.getHull().getRace(),
                ship.getHull().getImageIndex(), i, j);
            setFleetTile(fleet.getX(), fleet.getY(), info);
          } else {
            for (int k = 0; k < player.getFleets().getNumberOfFleets(); k++) {
              if (j != k) {
                Fleet fleet2 = player.getFleets().getByIndex(k);
                if (fleet2 != null &&  fleet != null
                    && fleet2.getX() == fleet.getX()
                    && fleet2.getY() == fleet.getY()
                    && fleet2.getNumberOfShip() > 0) {
                  if (fleet2.getFleetCloackingValue()
                      < fleet.getFleetCloackingValue()) {
                    Ship ship = fleet2.getBiggestShip();
                    FleetTileInfo info = new FleetTileInfo(
                        ship.getHull().getRace(),
                        ship.getHull().getImageIndex(), i, k);
                    setFleetTile(fleet2.getX(), fleet2.getY(), info);
                  } else if (fleet2.getFleetCloackingValue()
                      > fleet.getFleetCloackingValue()) {
                    Ship ship = fleet.getBiggestShip();
                    if (ship != null) {
                      FleetTileInfo info = new FleetTileInfo(
                          ship.getHull().getRace(),
                          ship.getHull().getImageIndex(), i, j);
                      setFleetTile(fleet.getX(), fleet.getY(), info);
                    }
                  } else if (fleet2.getMilitaryValue()
                      > fleet.getMilitaryValue()) {
                    Ship ship = fleet2.getBiggestShip();
                    FleetTileInfo info = new FleetTileInfo(
                        ship.getHull().getRace(),
                        ship.getHull().getImageIndex(), i, k);
                    setFleetTile(fleet2.getX(), fleet2.getY(), info);
                  } else {
                    Ship ship = fleet.getBiggestShip();
                    if (ship != null) {
                      FleetTileInfo info = new FleetTileInfo(
                          ship.getHull().getRace(),
                          ship.getHull().getImageIndex(), i, j);
                      setFleetTile(fleet.getX(), fleet.getY(), info);
                    }
                  }
                  if (fleet2.isStarBaseDeployed()) {
                    Ship ship = fleet2.getBiggestShip();
                    FleetTileInfo info = new FleetTileInfo(
                        ship.getHull().getRace(),
                        ship.getHull().getImageIndex(), i, k);
                    setFleetTile(fleet2.getX(), fleet2.getY(), info);
                  } else if (fleet.isStarBaseDeployed()) {
                    Ship ship = fleet.getBiggestShip();
                    FleetTileInfo info = new FleetTileInfo(
                        ship.getHull().getRace(),
                        ship.getHull().getImageIndex(), i, j);
                    setFleetTile(fleet.getX(), fleet.getY(), info);
                  }
                }
              }
            }
          }
        }
      }
      for (int i = 0; i < planetList.size(); i++) {
        Planet planet = planetList.get(i);
        if (fleetTiles[planet.getX()][planet.getY()] == null
            && planet.getOrbital() != null) {
          FleetTileInfo info = new FleetTileInfo(
              planet.getOrbital().getHull().getRace(),
              planet.getOrbital().getHull().getImageIndex(), i);
          setFleetTile(planet.getX(), planet.getY(), info);
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
   * Clear news corp data.
   */
  public void clearNewsCorpData() {
    newsCorpData = new NewsCorpData(players.getCurrentMaxRealms());
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
      return getBiggestFleet(new Coordinate(x, y), playerIndex);
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
    if (info == null) {
      return null;
    }
    Fleet fleet = info.getFleets().getByIndex(fleetTile.getFleetIndex());
    return fleet;
  }

  /**
   * Get Planet by Fleet Tile info. Used for getting orbital.
   * @param fleetTile to get the oribtal
   * @return Planet or null
   */
  public Planet getPlanetByFleetTileInfo(final FleetTileInfo fleetTile) {
    int index = fleetTile.getPlanetIndex();
    Planet planet = null;
    if (index > -1 && index < planetList.size()) {
      planet = planetList.get(index);
    }
    return planet;
  }
  /**
   * Get pirate difficulty level.
   * @return Pirate difficulty level
   */
  public PirateDifficultLevel getPirateDifficulty() {
    return pirateDifficulty;
  }

  /**
   * Set pirate difficulty level.
   * @param pirateDifficulty Difficulty level to set.
   */
  public void setPirateDifficulty(
      final PirateDifficultLevel pirateDifficulty) {
    this.pirateDifficulty = pirateDifficulty;
  }

  /** Process and execute events. Should be called on turn start. */
  public void handleEvents() {
    karmaEvents.handleEvents(this);
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
      PlayerInfo info2 = players.getPlayerInfoByIndex(playerIndex);
      if (info1 != info2) {
        Fleet fleet2 = getBiggestFleet(new Coordinate(x, y), playerIndex);
        if (fleet2 != null) {
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
          Planet planet = getPlanetByCoordinate(x, y);
          if (planet != null && planet.getOrbital() != null) {
            return new Combat(fleet1, fleet2, info1, info2, escapePosition,
                planet, getStarYear());
          }
          return new Combat(fleet1, fleet2, info1, info2, escapePosition,
              getStarYear());
        }
      }
      if (info2 == null) {
        int planetIndex = fleetTiles[x][y].getPlanetIndex();
        Planet planet = getPlanetList().get(planetIndex);
        if (planet != null && planet.getOrbital() != null
            && info1 != planet.getPlanetPlayerInfo()
            && planet.getPlanetPlayerInfo() != null) {
          return new Combat(fleet1, null, info1, info2, null,
              planet, getStarYear());
        }
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
   * Get current star year
   * @return Star year
   */
  public int getStarYear() {
    return START_STARYEAR + turn;
  }

  /**
   * Get Start staryear
   * @return Start star year
   */
  public int getStartStarYear() {
    return START_STARYEAR;
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
    aiOrAutomateTakingMoves = false;
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
   * Handle AI planning for diplomatic delegacies.
   */
  public void handleDiplomaticDelegacies() {
    PlayerInfo info = players.getPlayerInfoByIndex(aiTurnNumber);
    if (info != null && !info.isHuman() && !info.isBoard()) {
      // War fatigue has exhausted realm so better try to make peace
      if (info.getTotalWarFatigue() < 0) {
        for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
          if (info.getDiplomacy().isWar(i)) {
            PlayerInfo target = players.getPlayerInfoByIndex(i);
            if (target != null) {
              Mission mission = info.getMissions().getDiplomaticMission(
                  target.getEmpireName());
              if (mission == null) {
                mission = new Mission(MissionType.DIPLOMATIC_DELEGACY,
                    MissionPhase.PLANNING, new Coordinate(maxX / 2, maxY / 2));
                mission.setTargetRealmName(target.getEmpireName());
                info.getMissions().add(mission);
              }
            }
          }
        }
      }
      if (info.getStrategy() == WinningStrategy.DIPLOMATIC
          || info.getStrategy() == WinningStrategy.CULTURAL) {
        for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
          if (i != aiTurnNumber
              && info.getDiplomacy().getDiplomaticRelations(i)
                 < Diplomacy.RELATION_TRADE_ALLIANCE
              && info.getDiplomacy().getDiplomacyList(i)
              .getNumberOfMeetings() > 0) {
            PlayerInfo target = players.getPlayerInfoByIndex(i);
            if (target != null) {
              Mission mission = info.getMissions().getDiplomaticMission(
                  target.getEmpireName());
              if (mission == null) {
                mission = new Mission(MissionType.DIPLOMATIC_DELEGACY,
                    MissionPhase.PLANNING, new Coordinate(maxX / 2, maxY / 2));
                mission.setTargetRealmName(target.getEmpireName());
                info.getMissions().add(mission);
              }
            }
          }
        }
      }
      Attitude attitude = info.getAiAttitude();
      // Diplomatic attitudes try to make more friends
      if (StarMapUtilities.getNumberOfAdmires(aiTurnNumber, players)
          < players.getCurrentMaxRealms() / 2
          && (attitude == Attitude.DIPLOMATIC
             || attitude == Attitude.MERCHANTICAL
             || attitude == Attitude.PEACEFUL
             || attitude == Attitude.LOGICAL)) {
        for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
          if (i != aiTurnNumber
              && info.getDiplomacy().getLiking(i) < Diplomacy.LIKE
              && info.getDiplomacy().getDiplomacyList(i)
              .getNumberOfMeetings() > 0) {
            PlayerInfo target = players.getPlayerInfoByIndex(i);
            if (target != null) {
              Mission mission = info.getMissions().getDiplomaticMission(
                  target.getEmpireName());
              if (mission == null) {
                mission = new Mission(MissionType.DIPLOMATIC_DELEGACY,
                    MissionPhase.PLANNING, new Coordinate(maxX / 2, maxY / 2));
                mission.setTargetRealmName(target.getEmpireName());
                info.getMissions().add(mission);
              }
            }
          }
        }
      }
      // If there are more than 6 realms, even aggressive ones should have
      // at least one friend.
      if (players.getCurrentMaxRealms() > 6
          && StarMapUtilities.getNumberOfAdmires(aiTurnNumber, players) == 0
          && (attitude == Attitude.AGGRESSIVE
             || attitude == Attitude.SCIENTIFIC
             || attitude == Attitude.MILITARISTIC
             || attitude == Attitude.EXPANSIONIST)) {
        for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
          if (i != aiTurnNumber
              && info.getDiplomacy().getLiking(i) < Diplomacy.LIKE
              && info.getDiplomacy().getDiplomacyList(i)
              .getNumberOfMeetings() > 0) {
            PlayerInfo target = players.getPlayerInfoByIndex(i);
            if (target != null) {
              Mission mission = info.getMissions().getDiplomaticMission(
                  target.getEmpireName());
              if (mission == null) {
                mission = new Mission(MissionType.DIPLOMATIC_DELEGACY,
                    MissionPhase.PLANNING, new Coordinate(maxX / 2, maxY / 2));
                mission.setTargetRealmName(target.getEmpireName());
                info.getMissions().add(mission);
              }
            }
          }
        }
      }
      if (info.getAiDifficulty() == AiDifficulty.CHALLENGING) {
        for (Planet planet : planetList) {
          if (planet.getPlanetPlayerInfo() != null
              && planet.getPlanetPlayerInfo() != info) {
            int index = planet.getPlanetOwnerIndex();
            if (info.getSectorVisibility(planet.getCoordinate())
                > PlayerInfo.UNCHARTED
                && info.getDiplomacy().getDiplomacyList(index)
                .getNumberOfMeetings() == 0) {
              PlayerInfo target = players.getPlayerInfoByIndex(index);
              if (target != null) {
                Mission mission = info.getMissions().getDiplomaticMission(
                    target.getEmpireName());
                if (mission == null) {
                  mission = new Mission(MissionType.DIPLOMATIC_DELEGACY,
                      MissionPhase.PLANNING,
                      new Coordinate(maxX / 2, maxY / 2));
                  mission.setTargetRealmName(target.getEmpireName());
                  info.getMissions().add(mission);
                }
              }
            }
          }
        }
      }
      // Search scout ship for diplomatic delagacy mission.
      Mission mission = info.getMissions().getMission(
          MissionType.DIPLOMATIC_DELEGACY, MissionPhase.PLANNING);
      if (mission != null) {
        MissionHandling.findScoutShipForMission(info, mission);
      }
    }
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
      Coordinate coord = calculateCenterOfRealm(aiTurnNumber);
      info.setCenterRealm(coord);
      int exploreMissions = 0;
      int extraExploring = 0;
      if (info.getStrategy() == WinningStrategy.POPULATION) {
        extraExploring = 1;
      }
      // Try to locate ships for gather missions
      for (int i = 0; i < info.getMissions().getSize(); i++) {
        Mission mission = info.getMissions().getMissionByIndex(i);
        if (mission.getType() == MissionType.GATHER
            && mission.getPhase() == MissionPhase.PLANNING) {
          MissionHandling.findGatheringShip(mission, info);
        }
        if (mission.getType() == MissionType.EXPLORE) {
          exploreMissions++;
        }
      }
      /*
       * Making sure that there are enough exploration ships
       */
      if ((getGameLengthState() == GameLengthState.START_GAME
          || getGameLengthState() == GameLengthState.ELDER_HEAD_START)
          && exploreMissions < 2 + extraExploring) {
        Mission mission = new Mission(MissionType.EXPLORE,
            MissionPhase.PLANNING, null);
        info.getMissions().add(mission);
      }
      if (getGameLengthState() == GameLengthState.EARLY_GAME
          && exploreMissions < 4 + extraExploring) {
        Mission mission = new Mission(MissionType.EXPLORE,
            MissionPhase.PLANNING, null);
        info.getMissions().add(mission);
      }
      if (getGameLengthState() == GameLengthState.MIDDLE_GAME
          && exploreMissions < 3 + extraExploring) {
        Mission mission = new Mission(MissionType.EXPLORE,
            MissionPhase.PLANNING, null);
        info.getMissions().add(mission);
      }
      if (getGameLengthState() == GameLengthState.LATE_GAME
          && exploreMissions < 2 + extraExploring) {
        Mission mission = new Mission(MissionType.EXPLORE,
            MissionPhase.PLANNING, null);
        info.getMissions().add(mission);
      }
      if (getGameLengthState() == GameLengthState.END_GAME
          && exploreMissions < 1 + extraExploring) {
        Mission mission = new Mission(MissionType.EXPLORE,
            MissionPhase.PLANNING, null);
        info.getMissions().add(mission);
      }
      Mission mission = info.getMissions().getMission(MissionType.EXPLORE,
          MissionPhase.PLANNING);
      if (mission != null) {
        MissionHandling.findScoutShipForMission(info, mission);
      }
      // Handle research
      Research.handle(info);
      Research.removeUnusedAndObsoleteDesigns(info, this);
      ArrayList<Message> messages = info.getMsgList().getFullList();
      for (Message msg : messages) {
        if (msg.getType() == MessageType.RESEARCH) {
          Research.handleShipDesigns(info, this.getVotes().areNukesBanned(),
              this.getVotes().arePrivateersBanned());
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
      // Handle Leaders
      if (info.getRuler() == null && info.areLeadersDead()) {
        // No ruler and no leaders in pool
        Leader ruler = LeaderUtility.recruiteLeader(this, info, Job.RULER);
        if (ruler != null) {
          LeaderUtility.assignLeaderAsRuler(ruler, info, this);
          if (info.getRuler() != null) {
            Message msg = new Message(MessageType.LEADER,
                ruler.getCallName()
                    + " has selected as ruler for " + info.getEmpireName(),
                Icons.getIconByName(Icons.ICON_RULER));
            msg.setMatchByString("Index:" + info.getLeaderIndex(ruler));
            NewsData news = NewsFactory.makeNewRulerNews(ruler, info,
                getStarYear());
            if (hasHumanMet(info)) {
              getNewsCorpData().addNews(news);
            }
            getHistory().addEvent(NewsFactory.makeLeaderEvent(info.getRuler(),
                info, this, news));
          }
        }
      }
      int openLeaderPositions = calculateMaxLeaders(info);
      if (openLeaderPositions > 0
          && DiceGenerator.getRandom(99) < openLeaderPositions * 5) {
        LeaderUtility.recruiteLeader(this, info, null);
      }
      for (Leader leader : info.getLeaderPool()) {
        if (leader.getJob() == Job.UNASSIGNED
            || leader.getTimeInJob() > 20 && leader.getJob() == Job.COMMANDER
            || leader.getTimeInJob() > 20
            && leader.getJob() == Job.GOVERNOR) {
          ArrayList<Object> targetJobPositions = new ArrayList<>();
          Job bestJob = leader.getMostSuitableJob();
          if (bestJob == Job.COMMANDER || bestJob == Job.UNASSIGNED) {
            for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
              Fleet fleet = info.getFleets().getByIndex(i);
              if (fleet.getCommander() != null
                  && fleet.getCommander().getTimeInJob() > 20) {
                targetJobPositions.add(fleet);
              } else if (fleet.getCommander() == null
                  && !fleet.isStarBaseDeployed()) {
                targetJobPositions.add(fleet);
              }
            }
          }
          if (bestJob == Job.GOVERNOR || bestJob == Job.UNASSIGNED) {
            for (Planet planet : planetList) {
              if (planet.getPlanetPlayerInfo() == info
                  && planet.getGovernor() != null
                  && planet.getGovernor().getTimeInJob() > 20) {
                targetJobPositions.add(planet);
              } else if (planet.getPlanetPlayerInfo() == info
                  && planet.getGovernor() == null) {
                targetJobPositions.add(planet);
              }
            }
          }
          if (targetJobPositions.size() > 0) {
            var target = DiceGenerator.pickRandom(targetJobPositions);
            LeaderUtility.assignLeader(leader, info, planetList, target);
          }
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
    forceRedraw = true;
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
      if (planet.howManyBuildings("Deep space scanner") > 0) {
        // Reveal all the planet
        for (Planet iterator : getPlanetList()) {
          if (info.getSectorVisibility(iterator.getCoordinate())
              == PlayerInfo.UNCHARTED) {
            info.setSectorVisibility(iterator.getX(), iterator.getY(),
                PlayerInfo.FOG_OF_WAR);
          }
          if (iterator.isGasGiant()) {
            Coordinate coord = new Coordinate(iterator.getX() + 1,
                iterator.getY());
            if (info.getSectorVisibility(coord)
                == PlayerInfo.UNCHARTED) {
              info.setSectorVisibility(coord.getX(), coord.getY(),
                  PlayerInfo.FOG_OF_WAR);
            }
            coord = new Coordinate(iterator.getX(), iterator.getY() + 1);
            if (info.getSectorVisibility(coord)
                == PlayerInfo.UNCHARTED) {
              info.setSectorVisibility(coord.getX(), coord.getY(),
                  PlayerInfo.FOG_OF_WAR);
            }
            coord = new Coordinate(iterator.getX() + 1, iterator.getY() + 1);
            if (info.getSectorVisibility(coord)
                == PlayerInfo.UNCHARTED) {
              info.setSectorVisibility(coord.getX(), coord.getY(),
                  PlayerInfo.FOG_OF_WAR);
            }
          }
        }
      }
    }
    if (scanRad != -1) {
      for (int y = -scanRad; y < scanRad + 1; y++) {
        for (int x = -scanRad; x < scanRad + 1; x++) {
          drawVisibilityLine(info, cx, cy, cx + x, cy + y, cloakDetection,
              scanRad, fleet);
        }
      }
    }
  }

  /**
   * Do fleet scan for blocked sector.
   *
   * @param info PlayerInfo making the scan
   * @param fleet Fleet scanning
   * @param nx New coordinate X axel.
   * @param ny New coordinate Y axel.
   */
  public void doFleetScanBlocked(final PlayerInfo info, final Fleet fleet,
      final int nx, final int ny) {
    int cx = fleet.getX();
    int cy = fleet.getY();
    int cloakDetection = fleet.getFleetCloakDetection();
    drawVisibilityLine(info, cx, cy, nx, ny, cloakDetection, 2, fleet);
  }
  /**
   * Update star map when game starts
   */
  public void updateStarMapOnStartGame() {
    for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        info.estimateBestTechWorld(planetList);
        for (int j = 0; j < info.getFleets().getNumberOfFleets(); j++) {
          Fleet fleet = info.getFleets().getByIndex(j);
          if (!fleet.isStarBaseDeployed()) {
            fleet.setMovesLeft(fleet.getFleetSpeed());
          }
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
        info.estimateBestTechWorld(planetList);
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
    updateWinningStrategies();
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
        info.getIntelligence().clearAllIntelligenceBonuses();
        info.getIntelligence().getByIndex(i).addIntelligenceBonus(
            IntelligenceBonusType.OWN_REALM, 10, "Own realm");
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
              info.getIntelligence().getByIndex(sectorIndex)
                  .addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET,
                      espionageBonus, description);
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
              info.getIntelligence().getByIndex(k).addIntelligenceBonus(
                  IntelligenceBonusType.TRADE,
                  trader.getIntelligence().getByIndex(k).getOwnBonus(),
                  "Spy trade with " + trader.getEmpireName());
            }
          }
          if (j != i && info.getDiplomacy().isAlliance(j)) {
            PlayerInfo trader = players.getPlayerInfoByIndex(j);
            info.getIntelligence().getByIndex(j).addIntelligenceBonus(
                IntelligenceBonusType.TRADE,
                trader.getIntelligence().getByIndex(j).getTotalBonus(),
                "Spy trade with " + trader.getEmpireName());
          }
          if (j != i) {
            PlayerInfo info2 = players.getPlayerInfoByIndex(j);
            if (info2.getRuler() != null
                && info2.getRuler().hasPerk(Perk.CHATTERBOX)) {
              info.getIntelligence().getByIndex(j).addIntelligenceBonus(
                  IntelligenceBonusType.CHATTERBOX, 1,
                  "Ruler has chatterbox perk");
            }
          }
          if (j != i && info.getRuler() != null
                && info.getRuler().hasPerk(Perk.NEGOTIATOR)) {
            info.getIntelligence().getByIndex(j).addIntelligenceBonus(
                IntelligenceBonusType.NEGOTIATOR, 1,
                "Learned by negotiator perk");
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
    IntelligenceList espionageList = firstInfo.getIntelligence().getByIndex(
        second);
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
   * @param isWar between first and second.
   * @return Military value
   */
  protected int getMilitaryEstimationForDefensivePact(final int first,
      final int second, final boolean isWar) {
    int result = getMilitaryEstimation(first, second);
    PlayerInfo secondInfo = players.getPlayerInfoByIndex(second);
    PlayerInfo firstInfo = players.getPlayerInfoByIndex(first);
    int maxPlayer = players.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
      if (!isWar) {
        if (i != first && i != second
            && secondInfo.getDiplomacy().isDefensivePact(i)) {
          result = result + getMilitaryEstimation(first, i);
        }
      } else {
        if (i != first && i != second
            && secondInfo.getDiplomacy().isDefensivePact(i)
            && firstInfo.getDiplomacy().isWar(i)) {
          result = result + getMilitaryEstimation(first, i);
        }
      }
    }
    return result;
  }
  /**
   * Get latest military difference between two players, using espionage
   * information and news corp. First one is making the comparison.
   * @param first First player index. This is where second one is compared.
   * @param second Second player index.
   * @return Positive number if first player has bigger military
   */
  public int getMilitaryDifference(final int first, final int second) {
    return getMilitaryDifference(first, second, false);
  }
  /**
   * Get latest military difference between two players, using espionage
   * information and news corp. First one is making the comparison.
   * @param first First player index. This is where second one is compared.
   * @param second Second player index.
   * @param isWar There is war between first and second realm.
   * @return Positive number if first player has bigger military
   */
  public int getMilitaryDifference(final int first, final int second,
      final boolean isWar) {
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
      IntelligenceList intelligenceList = firstInfo.getIntelligence()
          .getByIndex(second);
      int estimateSecond = intelligenceList.estimateMilitaryPower(
          actualMilitarySecond);
      int estimateDiff = actualMilitaryFirst - estimateSecond;
      if (intelligenceList.getTotalBonus() >= 7) {
        // 90-100% accuracy
        result = estimateDiff;
      } else if (intelligenceList.getTotalBonus() >= 5) {
        // 80% accuracy
        result = estimateDiff * 90 / 100 + newsCorpDiff * 10 / 100;
      } else if (intelligenceList.getTotalBonus() >= 3) {
        // 70% accuracy
        result = estimateDiff * 80 / 100 + newsCorpDiff * 20 / 100;
      } else if (intelligenceList.getTotalBonus() >= 1) {
        // 70% accuracy
        result = estimateDiff * 70 / 100 + newsCorpDiff * 30 / 100;
      }
    } else {
      int actualMilitaryFirst = NewsCorpData.calculateMilitaryValue(firstInfo);
      int estimate = getMilitaryEstimationForDefensivePact(first, second,
          isWar);
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
   * Show message based on tiles.
   * @param info PlayerInfo who is exploring
   * @param sx X coordinate
   * @param sy Y coordinate
   * @param fleet Fleet which is exploring.
   */
  private void messageBasedOnTiles(final PlayerInfo info, final int sx,
      final int sy, final Fleet fleet) {
    int exp = 0;
    Tile tile = Tiles.getTileByIndex(tiles[sx][sy]);
    SquareInfo square = tileInfo[sx][sy];
    if (tile.isSpaceAnomaly()) {
      Message msg = new Message(MessageType.FLEET,
          fleet.getName() + " found space anomaly.",
            Icons.getIconByName(Icons.ICON_HULL_TECH));
      msg.setCoordinate(fleet.getCoordinate());
      msg.setMatchByString(fleet.getName());
      if (aiOrAutomateTakingMoves) {
        info.getMsgList().addUpcomingMessage(msg);
      } else {
        info.getMsgList().addNewMessage(msg);
      }
      return;
    }
    if (square.getType() == SquareInfo.TYPE_PLANET) {
      Planet planet = planetList.get(square.getValue());
      if (planet != null) {
        exp = 8;
        StringBuilder sb = new StringBuilder();
        sb.append(fleet.getName());
        sb.append(" found planet called ");
        sb.append(planet.getName());
        sb.append(".");
        if (planet.getPlanetPlayerInfo() != null) {
          sb.append(" Unfortunately it has been already colonized by ");
          sb.append(planet.getPlanetPlayerInfo().getEmpireName());
          sb.append(".");
        } else if (!planet.isColonizeablePlanet(info)) {
          sb.append(" Unfortunately planet is not suitable");
          sb.append(" for your people to live there...");
        } else {
          int value = info.getPlanetSuitabilityValue(planet);
          if (value > 0) {
            sb.append(" Planet is ");
            sb.append(value);
            sb.append("% habitable for your people.");
            sb.append(" Planet size is ");
            sb.append(planet.getSizeAsString());
            sb.append(".");
          } else {
            sb.append(" Planet has conditions that your");
            sb.append(" people cannot tolarate.");
          }
        }
        if (fleet.getCommander() != null) {
          if (fleet.getCommander().hasPerk(Perk.CARTOGRAPHER)) {
            exp = exp * 2;
          }
          sb.append(" ");
          sb.append(fleet.getCommander().getCallName());
          sb.append(" gained ");
          sb.append(exp);
          sb.append(" amount of experience.");
        }
        Message msg = new Message(MessageType.FLEET,
            sb.toString(), Icons.getIconByName(Icons.ICON_PLANET));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(planet.getName());
        if (aiOrAutomateTakingMoves) {
          info.getMsgList().addUpcomingMessage(msg);
        } else {
          info.getMsgList().addNewMessage(msg);
        }
        return;
      }
    }
    if (square.getType() == SquareInfo.TYPE_GAS_PLANET) {
      Planet planet = planetList.get(square.getValue());
      if (planet != null) {
        exp = 2;
        StringBuilder sb = new StringBuilder();
        sb.append(fleet.getName());
        sb.append(" found gas giant called ");
        sb.append(planet.getName());
        sb.append(".");
        if (fleet.getCommander() != null) {
          if (fleet.getCommander().hasPerk(Perk.CARTOGRAPHER)) {
            exp = exp * 2;
          }
          sb.append(" ");
          sb.append(fleet.getCommander().getCallName());
          sb.append(" gained ");
          sb.append(exp);
          sb.append(" amount of experience.");
        }
        Message msg = new Message(MessageType.FLEET,
            sb.toString(), Icons.getIconByName(Icons.ICON_PLANET));
        msg.setCoordinate(planet.getCoordinate());
        msg.setMatchByString(planet.getName());
        if (aiOrAutomateTakingMoves) {
          info.getMsgList().addUpcomingMessage(msg);
        } else {
          info.getMsgList().addNewMessage(msg);
        }
        return;
      }
    }
    if (tile == Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1)
        || tile == Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR2)) {
      Message msg = new Message(MessageType.FLEET,
          fleet.getName() + " found deep space anchor.",
          Icons.getIconByName(Icons.ICON_STARBASE));
      msg.setCoordinate(new Coordinate(sx, sy));
      msg.setMatchByString(fleet.getName());
      if (aiOrAutomateTakingMoves) {
        info.getMsgList().addUpcomingMessage(msg);
      } else {
        info.getMsgList().addNewMessage(msg);
      }
      return;
    }
  }

  /**
   * Show tutorial based what has reveal from the map.
   * @param info PlayerInfo who is exploring
   * @param sx X coordinate
   * @param sy Y coordinate
   */
  private void tutorialBasedOnTiles(final PlayerInfo info, final int sx,
      final int sy) {
    if (Game.getTutorial() != null && isTutorialEnabled()
        && info.isHuman()) {
      Tile tile = Tiles.getTileByIndex(tiles[sx][sy]);
      SquareInfo square = tileInfo[sx][sy];
      if (tile.isSpaceAnomaly()) {
        String tutorialText = Game.getTutorial().showTutorialText(30);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.INFORMATION, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(new Coordinate(sx, sy));
          info.getMsgList().addNewMessage(msg);
          return;
        }
      }
      if (tile.isBlackhole()) {
        String tutorialText = Game.getTutorial().showTutorialText(31);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.INFORMATION, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(new Coordinate(sx, sy));
          info.getMsgList().addNewMessage(msg);
          return;
        }
      }
      if (square.getType() == SquareInfo.TYPE_PLANET) {
        Planet planet = planetList.get(square.getValue());
        if (planet != null && planet.getPlanetPlayerInfo() != info) {
          String tutorialText = Game.getTutorial().showTutorialText(20);
          if (tutorialText != null) {
            Message msg = new Message(MessageType.PLANETARY, tutorialText,
                Icons.getIconByName(Icons.ICON_TUTORIAL));
            msg.setCoordinate(new Coordinate(sx, sy));
            msg.setMatchByString(planet.getName());
            info.getMsgList().addNewMessage(msg);
            return;
          }
          if (planet.getOrderNumber() == 0) {
            // Rogue planet
            tutorialText = Game.getTutorial().showTutorialText(22);
            if (tutorialText != null) {
              Message msg = new Message(MessageType.PLANETARY, tutorialText,
                  Icons.getIconByName(Icons.ICON_TUTORIAL));
              msg.setCoordinate(new Coordinate(sx, sy));
              msg.setMatchByString(planet.getName());
              info.getMsgList().addNewMessage(msg);
              return;
            }
          }
          if (!planet.isColonizeablePlanet(info)) {
            // High radiation
            tutorialText = Game.getTutorial().showTutorialText(23);
            if (tutorialText != null) {
              Message msg = new Message(MessageType.PLANETARY, tutorialText,
                  Icons.getIconByName(Icons.ICON_TUTORIAL));
              msg.setCoordinate(new Coordinate(sx, sy));
              msg.setMatchByString(planet.getName());
              info.getMsgList().addNewMessage(msg);
              return;
            }
          }
        }
      }
      if (square.getType() == SquareInfo.TYPE_GAS_PLANET) {
        String tutorialText = Game.getTutorial().showTutorialText(21);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.PLANETARY, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(new Coordinate(sx, sy));
          msg.setMatchByString(planetList.get(square.getValue()).getName());
          info.getMsgList().addNewMessage(msg);
          return;
        }
      }
      if (tile == Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1)
          || tile == Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR2)) {
        String tutorialText = Game.getTutorial().showTutorialText(32);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.INFORMATION, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(new Coordinate(sx, sy));
          info.getMsgList().addNewMessage(msg);
          return;
        }
      }
      if (tile == Tiles.getTileByName(TileNames.WORM_HOLE1)
          || tile == Tiles.getTileByName(TileNames.WORM_HOLE2)) {
        String tutorialText = Game.getTutorial().showTutorialText(33);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.INFORMATION, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          msg.setCoordinate(new Coordinate(sx, sy));
          info.getMsgList().addNewMessage(msg);
          return;
        }
      }
    }
  }
  /**
   * Player can get rare tech based on tiles has seen.
   * @param info PlayerInfo
   * @param sx X coordinate
   * @param sy Y coordinate
   */
  private void rareTechBasedOnTiles(final PlayerInfo info, final int sx,
      final int sy) {
    Tile tile = Tiles.getTileByIndex(tiles[sx][sy]);
    if (tile.isBlackhole()
        && !info.getTechList().hasTech(TechType.Combat, "Tractor beam")) {
      info.getTechList().addTech(TechFactory.createCombatTech("Tractor beam",
          3));
      Message msg = new Message(MessageType.RESEARCH,
          "Research on Black hole reveals the tractor beam technology.",
          Icons.getIconByName(Icons.ICON_RESEARCH));
      msg.setCoordinate(new Coordinate(sx, sy));
      info.getMsgList().addNewMessage(msg);
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
   * @param fleet Fleet or null
   */
  private void drawVisibilityLine(final PlayerInfo info, final int sx,
      final int sy, final int ex, final int ey, final int cloakDetection,
      final int maxRad, final Fleet fleet) {
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
    tutorialBasedOnTiles(info, sx, sy);
    rareTechBasedOnTiles(info, sx, sy);
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
        if (info.getSectorVisibility(coordinate) == PlayerInfo.UNCHARTED
            && fleet != null) {
          messageBasedOnTiles(info, nx, ny, fleet);
        }
        info.setSectorVisibility(nx, ny, PlayerInfo.VISIBLE);
        tutorialBasedOnTiles(info, nx, ny);
        rareTechBasedOnTiles(info, nx, ny);
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
    if (production == Planet.PRODUCTION_CREDITS && info.getRuler() != null
        && info.getRuler().hasPerk(Perk.MERCHANT)) {
      result++;
    }
    boolean colonyInDeepSpace = false;
    for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
      Fleet fleet = info.getFleets().getByIndex(i);
      if (production == Planet.PRODUCTION_RESEARCH
          && fleet.hasColonyShip()) {
        Planet planet = getPlanetByCoordinate(fleet.getX(), fleet.getY());
        if (planet == null) {
          colonyInDeepSpace = true;
        }
      }
      if (production == Planet.PRODUCTION_RESEARCH) {
        result = result + fleet.getTotalReseachBonus();
      }
      if (production == Planet.PRODUCTION_CREDITS) {
        result = result + fleet.getTotalCreditsBonus();
        Leader leader = fleet.getCommander();
        if (leader != null
            && (leader.getJob() == Job.RULER
            || leader.getJob() == Job.COMMANDER
            || leader.getJob() == Job.GOVERNOR)
            && leader.hasPerk(Perk.CORRUPTED)) {
          result--;
        }
      }
      if (production == Planet.PRODUCTION_ARTIFACT_RESEARCH
          && info.getArtifactLists().hasDiscoveredArtifacts()) {
        Leader leader = fleet.getCommander();
        if (leader != null) {
          if (leader.hasPerk(Perk.ACADEMIC)) {
            result++;
          }
          if (leader.hasPerk(Perk.SCIENTIST)) {
            result++;
          }
          if (leader.hasPerk(Perk.STUPID)) {
            result--;
          }
          if (leader.hasPerk(Perk.EXPLORER)) {
            result++;
          }
          if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
            result = result + 2;
          }
        }
      }
      if (production == Planet.PRODUCTION_CULTURE) {
        result = result + fleet.getTotalCultureBonus();
      }
    }
    if (production == Planet.PRODUCTION_RESEARCH && colonyInDeepSpace) {
      // Realms which start from deep space get extra research on colony ships
      // in deep space.
      result = result + 1;
    }
    if (production == Planet.PRODUCTION_RESEARCH) {
      if (info.getRuler() != null
          && info.getRuler().hasPerk(Perk.SCIENTIST)) {
        result++;
      }
      if (info.getRuler() != null && info.getRuler().hasPerk(Perk.STUPID)
          && result > 0) {
        result--;
      }
    }
    if (production == Planet.PRODUCTION_CREDITS) {
      if (info.getRuler() != null && info.getRuler().hasPerk(Perk.CORRUPTED)) {
        result = result - 1;
      }
      int totalCapacity = getTotalFleetCapacity(info);
      double capacity = info.getFleets().getTotalFleetCapacity();
      totalCapacity = totalCapacity - (int) capacity;
      if (totalCapacity < 0) {
        result = result + totalCapacity;
      }
      int richest = getNewsCorpData().getCredit().getBiggest();
      int poorest = getNewsCorpData().getCredit().getSmallest();
      if (richest == playerIndex && getVotes().isTaxationOfRichestEnabled()) {
        result = result - 1;
      }
      if (poorest == playerIndex && getVotes().isTaxationOfRichestEnabled()) {
        result = result + 1;
      }
    }
    if (production == Planet.PRODUCTION_ARTIFACT_RESEARCH
        && info.getArtifactLists().hasDiscoveredArtifacts()) {
      int research = getTotalProductionByPlayerPerTurn(
          Planet.PRODUCTION_RESEARCH, playerIndex);
      research = research / 10;
      if (research < 1) {
        research = 1;
      }
      result = result + research;
      if (info.getRuler() != null) {
        Leader leader = info.getRuler();
        if (leader.hasPerk(Perk.ACADEMIC)) {
          result++;
        }
        if (leader.hasPerk(Perk.SCIENTIST)) {
          result++;
        }
        if (leader.hasPerk(Perk.STUPID)) {
          result--;
        }
        if (leader.hasPerk(Perk.EXPLORER)) {
          result++;
        }
        if (leader.hasPerk(Perk.ARCHAEOLOGIST)) {
          result = result + 2;
        }
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
   * Clear square info from certain point of space.
   * Checks that space is in valid coordinates.
   * @param x X coordinate
   * @param y Y Coordinate
   */
  public void clearTileInfo(final int x, final int y) {
    if (isValidCoordinate(x, y)) {
      tileInfo[x][y] = SquareInfo.EMPTY_TILE;
    }
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
   * Is tile good for ascension vein
   * @param x X coordinate
   * @param y Y Coordinate
   * @return true if tile is good for ascension. Also if
   * coordinate is out of map then false is returned.
   */
  public boolean isGoodForAscension(final int x, final int y) {
    if (!isValidCoordinate(x, y)) {
      return false;
    }
    SquareInfo info = getTileInfo(x, y);
    Tile tile = Tiles.getTileByIndex(tiles[x][y]);
    if (info.getType() == SquareInfo.TYPE_ASCENSION_VEIN
        || tile.getName().equals(TileNames.EMPTY)) {
      return true;
    }
    return false;
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
   * Minimum is 200 and maximum is 1000 star years.
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
   * Get Score limit for research. How many scientific achievements
   * must be on single planet.
   * @return Score limit for research
   */
  public int getScoreResearch() {
    return scoreResearch;
  }

  /**
   * Set Score limit for research. How many scientific achievements
   * must be on single planet.
   * @param limit Limit for research
   */
  public void setScoreResearch(final int limit) {
    scoreResearch = limit;
  }

  /**
   * Get Score limit for diplomacy.
   * @return Score limit for diplomacy
   *              0 - Diplomacy victory disabled
   *              1 - 2 diplomatic votes
   *              2 - 3 diplomatic votes
   *              3 - 4 diplomatic votes
   *              4 - 5 diplomatic votes
   *              5 - 6 diplomatic votes
   */
  public int getScoreDiplomacy() {
    return scoreDiplomacy;
  }

  /**
   * Set Score limit for diplomacy
   * @param limit Limit for diplomacy
   *              0 - Diplomacy victory disabled
   *              1 - 2 diplomatic votes
   *              2 - 3 diplomatic votes
   *              3 - 4 diplomatic votes
   *              4 - 5 diplomatic votes
   *              5 - 6 diplomatic votes
   */
  public void setScoreDiplomacy(final int limit) {
    scoreDiplomacy = limit;
  }
  /**
   * Get score limit for population.
   * @return the scorePopulation
   *             0 Disabled
   *             1 40% of whole galaxy
   *             2 50% of whole galaxy
   *             3 60% of whole galaxy
   *             4 70% of whole galaxy
   */
  public int getScorePopulation() {
    return scorePopulation;
  }

  /**
   * Set score limit for population.
   * @param scorePopulation the scorePopulation to set
   *                        0 Disabled
   *                        1 40% of whole galaxy
   *                        2 50% of whole galaxy
   *                        3 60% of whole galaxy
   *                        4 70% of whole galaxy
   */
  public void setScorePopulation(final int scorePopulation) {
    this.scorePopulation = scorePopulation;
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
          maxY - 2);
      if (!isBlocked(sx, sy) && isBlockedByFleet(sx, sy) == null) {
        return new Coordinate(sx, sy);
      }
    }
    return null;
  }

  /**
   * Get Free worm hole in map by random
   * @param notInclude Which coordinate's wormhole is not included
   * @return Random wormhole.
   */
  public Coordinate getFreeWormHole(final Coordinate notInclude) {
    ArrayList<Coordinate> wormholes = new ArrayList<>();
    Tile hole1 = Tiles.getTileByName(TileNames.WORM_HOLE1);
    Tile hole2 = Tiles.getTileByName(TileNames.WORM_HOLE2);
    for (int y = 1; y < maxY - 2; y++) {
      for (int x = 1; x < maxX - 2; x++) {
        if (x == notInclude.getX() && y == notInclude.getY()) {
          continue;
        }
        if (tiles[x][y] == hole1.getIndex()
            || tiles[x][y] == hole2.getIndex()) {
          if (!isBlocked(x, y) && isBlockedByFleet(x, y) == null) {
            wormholes.add(new Coordinate(x, y));
          } else {
            int sx = DiceGenerator.getRandom(-1, 1);
            int sy = DiceGenerator.getRandom(-1, 1);
            if (!isBlocked(x + sx, y + sy)
                && isBlockedByFleet(x + sx, y + sy) == null) {
                  wormholes.add(new Coordinate(x + sx, y + sy));
            }
          }
        }
      }
    }
    if (wormholes.size() > 0) {
      return wormholes.get(DiceGenerator.getRandom(wormholes.size() - 1));
    }
    return getFreeRandomSpot();
  }
  /**
   * Generate name for artificial planet
   * @return artificial planet name
   */
  public String generateNewArtificialPlanetName() {
    int count = 1;
    for (Planet planet : planetList) {
      if (planet.getPlanetType() == PlanetTypes.ARTIFICIALWORLD1) {
        count++;
      }
    }
    UnrealPlanetNameGenerator unrealPlanetGenerator =
        new UnrealPlanetNameGenerator();
    StringBuilder sb = new StringBuilder();
    sb.append(unrealPlanetGenerator.generate());
    sb.append(count);
    return sb.toString();
  }
  /**
   * Create Artificial Planet. This will add the actual planet,
   * update the history and add the news.
   * @param starbaseFleet Starbase fleet where to create
   * @param realm Player realm who is creating.
   */
  public void createArtificialPlanet(final Fleet starbaseFleet,
      final PlayerInfo realm) {
    boolean startBuilding = false;
    int labs = 0;
    int factory = 0;
    int metal = 0;
    int credits = 0;
    int cultureValue = 0;
    for (Ship ship : starbaseFleet.getShips()) {
      if (ship.getHull().getName().equals("Artificial planet")) {
        startBuilding = true;
      }
      // Recycle all the metal
      metal = metal + ship.getMetalCost();
      // Each ship should be calculated as factory
      factory++;
      labs = labs + ship.getTotalResearchBonus();
      credits = credits + ship.getTotalCreditBonus();
      cultureValue = cultureValue + ship.getTotalCultureBonus();
    }
    if (startBuilding) {
      String planetName = generateNewArtificialPlanetName();
      Planet planet = new Planet(starbaseFleet.getCoordinate(),
          planetName, 0, false);
      planet.setPlanetType(PlanetTypes.ARTIFICIALWORLD1);
      planet.setCulture(starbaseFleet.getCulturalValue());
      planet.setMetal(metal);
      planet.setRadiationLevel(RadiationType.NO_RADIATION);
      planet.setTemperatureType(TemperatureType.TEMPERATE);
      planet.setWaterLevel(WaterLevelType.HUMID);
      planet.setGravityType(GravityType.NORMAL_GRAVITY);
      String[] buildingList = realm.getTechList().getBuildingListFromTech();
      boolean lab = StarMapUtilities.listContains(buildingList, "Basic lab");
      boolean taxCenter = StarMapUtilities.listContains(buildingList,
          "Tax center");
      boolean marketCenter = StarMapUtilities.listContains(buildingList,
          "Market center");
      boolean cultureCenter = StarMapUtilities.listContains(buildingList,
          "Culture center");
      int freeSpace = 10;
      planet.setGroundSize(freeSpace);
      int ownerIndex = getPlayerList().getIndex(realm);
      planet.setPlanetOwner(ownerIndex, realm);
      planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
      if (cultureValue > 0 && cultureCenter) {
        planet.addBuilding(BuildingFactory.createByName("Culture center"));
        freeSpace--;
      }
      if (credits > 0 && taxCenter) {
        planet.addBuilding(BuildingFactory.createByName("Tax center"));
        freeSpace--;
        credits--;
      }
      if (credits > 0 && marketCenter) {
        planet.addBuilding(BuildingFactory.createByName("Market center"));
        freeSpace--;
        credits--;
      }
      boolean done = false;
      while (!done) {
        if (factory > 0 && freeSpace > 0) {
          planet.addBuilding(BuildingFactory.createByName("Basic factory"));
          factory--;
          freeSpace--;
        }
        if (labs > 0 && freeSpace > 0 && lab) {
          planet.addBuilding(BuildingFactory.createByName("Basic lab"));
          labs--;
          freeSpace--;
        }
        if (factory == 0 || freeSpace == 0) {
          done = true;
        }
      }
      realm.getFleets().removeFleet(starbaseFleet);
      if (starbaseFleet.getCommander() != null) {
        planet.setGovernor(starbaseFleet.getCommander());
        planet.getGovernor().assignJob(Job.GOVERNOR, realm);
      }
      NewsData newsData = NewsFactory.makeScientificAchivementNews(realm,
          planet, null, getStarYear());
      getNewsCorpData().addNews(newsData);
      EventOnPlanet event = new EventOnPlanet(
          EventType.ARTIFICAL_PLANET_CREATED, planet.getCoordinate(),
          planet.getName(), ownerIndex);
      event.setText(newsData.getNewsText());
      history.addEvent(event);
      planetList.add(planet);
      int planetNumber = planetList.size() - 1;
      SquareInfo info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
      int px = planet.getCoordinate().getX();
      int py = planet.getCoordinate().getY();
      tileInfo[px][py] = info;
      tiles[px][py] = planet.getPlanetType().getTileIndex();
    }
  }

  /**
   * Get Starmap votes.
   * @return Votes
   */
  public Votes getVotes() {
    return votes;
  }

  /**
   * Get Wealthy index. Which position realm has in order of credits.
   * This is done by calculating how many wealthier realms there are.
   * @param realm Realm to compare
   * @return Wealthy index or -1 if cannot calculate.
   */
  public int getWealthyIndex(final PlayerInfo realm) {
    int index = players.getIndex(realm);
    if (index != -1) {
      int biggerValue = 0;
      int value = newsCorpData.getCredit().getLatest(index);
      for (int i = 0; i < newsCorpData.getCredit().getMaxPlayers(); i++) {
        if (i != index && newsCorpData.getCredit().getLatest(i) > value) {
          biggerValue++;
        }
      }
      index = biggerValue + 1;
    }
    return index;
  }

  /**
   * Get highest military index.
   * @return Highest military index.
   */
  public int getMilitaryHighest() {
    int index = -1;
    int value = -1;
    for (int i = 0; i < newsCorpData.getMilitary().getMaxPlayers(); i++) {
      if (newsCorpData.getMilitary().getLatest(i) > value) {
        index = i;
        value = newsCorpData.getMilitary().getLatest(i);
      }
    }
    return index;
  }

  /**
   * Get second candidate for amount of United Galaxy Towers.
   * @return Second candidate for amount of tower.
   */
  public int getSecondCandidateForTower() {
    int[] towers = new int[getPlayerList().getCurrentMaxRealms()];
    for (int i = 0; i < getPlanetList().size(); i++) {
      Planet planet = getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null && planet.hasTower()) {
        towers[planet.getPlanetOwnerIndex()]++;
      }
    }
    int first = -1;
    int second = -1;
    for (int i = 0; i < towers.length; i++) {
      if (first == -1 && towers[i] > 0) {
        first = i;
      } else if (first != -1 && towers[i] > towers[first]) {
        second = first;
        first = i;
      }
    }
    for (int i = 0; i < towers.length; i++) {
      if (first != i && towers[i] > 0 && second == -1) {
        second = i;
      } else if (first != i && second != -1 && towers[i] > towers[second]) {
        second = i;
      }
    }
    if (second == -1) {
      // No other realm has tower choosing strongest military then
      second = getNewsCorpData().getMilitary().getBiggest();
      if (getVotes().getFirstCandidate() == second) {
        second = getNewsCorpData().getMilitary().getSecond();
      }
    }
    return second;
  }

  /**
   * Get wealthy index. This method can be used to look for richest realm
   * or poorest real.
   * @param highest True for richest and false for poorest
   * @return Wealthy index or -1 if not found.
   */
  public int getWealthyIndex(final boolean highest) {
    int index = -1;
    int value = -1;
    if (!highest) {
      value = Integer.MAX_VALUE;
    }
    for (int i = 0; i < newsCorpData.getCredit().getMaxPlayers(); i++) {
      if (highest && newsCorpData.getCredit().getLatest(i) > value) {
        index = i;
        value = newsCorpData.getCredit().getLatest(i);
      }
      if (!highest && newsCorpData.getCredit().getLatest(i) < value) {
        index = i;
        value = newsCorpData.getCredit().getLatest(i);
      }
    }
    return index;
  }

  /**
   * Get Total number of population for certain realm.
   * @param index Realm index
   * @return Total number of population
   */
  public int getTotalNumberOfPopulation(final int index) {
    int result = 0;
    for (Planet planet : planetList) {
      if (planet.getPlanetOwnerIndex() == index) {
        result = result + planet.getTotalPopulation();
      }
    }
    return result;
  }

  /**
   * Get total fleet capacity for certain realm
   * @param info Realm info
   * @return Fleet capacity
   */
  public int getTotalFleetCapacity(final PlayerInfo info) {
    int result = 0;
    result = result + info.getGovernment().getFleetCapacity();
    for (Planet planet : planetList) {
      if (planet.getPlanetPlayerInfo() == info) {
        result = result + planet.getFleetCapacityBonus();
      }
    }
    for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
      result = result + info.getFleets().getByIndex(i)
          .getTotalFleetCapacityBonus();
    }
    if (info.getRuler() != null
        && info.getRuler().hasPerk(Perk.MILITARISTIC)) {
      result = result + 1;
    }
    if (info.getRuler() != null
        && info.getRuler().hasPerk(Perk.PACIFIST)) {
      result = result - 1;
    }
    return result;
  }

  /**
   * Broadcast one message to each realms.
   * @param msg Message to broadcast
   * @param first Add message as first
   */
  public void broadcastMessage(final Message msg, final boolean first) {
    for (int i = 0; i < getPlayerList().getCurrentMaxRealms(); i++) {
      Message message = msg.copy();
      PlayerInfo info = getPlayerList().getPlayerInfoByIndex(i);
      if (info != null) {
        if (first) {
          info.getMsgList().addFirstMessage(message);
        } else {
          info.getMsgList().addNewMessage(message);
        }
      }
    }
  }

  /**
   * Update winning strategies for AI.
   */
  public void updateWinningStrategies() {
    if (getTurn() > 99) {
      for (int i = 0; i < getPlayerList().getCurrentMaxRealms(); i++) {
        if (getPlayerByIndex(i).isHuman()) {
          continue;
        }
        if (getPlayerByIndex(i).getTechList().hasTech(TechType.Improvements,
            "United Galaxy Tower") && getScoreDiplomacy() > 0
            && StarMapUtilities.getNumberOfAdmires(i, getPlayerList()) > 0) {
          getPlayerByIndex(i).setStrategy(WinningStrategy.DIPLOMATIC);
        } else if (getNewsCorpData().getResearch().getPosition(i) < 3
            && getScoreResearch() > 0
            && getPlayerByIndex(i).getTechList()
               .getNumberOfScientificAchievements() > 0) {
          getPlayerByIndex(i).setStrategy(WinningStrategy.SCIENCE);
        } else if (getNewsCorpData().getCultural().getPosition(i) < 3
            && getScoreCulture() > -1
            &&  StarMapUtilities.getNumberOfAdmires(i, getPlayerList()) > 0) {
          getPlayerByIndex(i).setStrategy(WinningStrategy.CULTURAL);
        } else if (getNewsCorpData().getResearch().getPosition(i) < 3
            && getScoreResearch() > 0) {
          getPlayerByIndex(i).setStrategy(WinningStrategy.SCIENCE);
        } else if (getNewsCorpData().getPopulation().getPosition(i) < 3
            && getScorePopulation() > 0) {
          getPlayerByIndex(i).setStrategy(WinningStrategy.POPULATION);
        } else if (getNewsCorpData().getMilitary().getPosition(i) < 3
            && getScoreConquer() == 1) {
          getPlayerByIndex(i).setStrategy(WinningStrategy.CONQUER);
        } else {
          getPlayerByIndex(i).setStrategy(WinningStrategy.GENERIC);
        }
      }
    }
  }

  /**
   * Is tutorial enabled or not
   * @return true if tutorial is enabled
   */
  public boolean isTutorialEnabled() {
    return tutorialEnabled;
  }

  /**
   * Set tutorial enabled flag
   * @param tutorialEnabled the tutorialEnabled to set
   */
  public void setTutorialEnabled(final boolean tutorialEnabled) {
    this.tutorialEnabled = tutorialEnabled;
  }

  /**
   * Get Shown tutorial indexes as list
   * @return the shownTutorialIndexes
   */
  public ArrayList<Integer> getShownTutorialIndexes() {
    return shownTutorialIndexes;
  }

  /**
   * Set Shown tutorial indexes in list
   * @param tutorialIndexes Array of indexes
   */
  public void setShownTutorial(final ArrayList<Integer> tutorialIndexes) {
    shownTutorialIndexes = tutorialIndexes;
  }

  /**
   * Calculate theoretical maximum for leaders by counting number of planets
   * and fleet which do not have leader. Deployed starbase fleets are not
   * count as fleet which should have leader.
   * @param realm Realm whose max leader count is calculate
   * @return Number of leaders still needed.
   */
  public int calculateMaxLeaders(final PlayerInfo realm) {
    int result = 0;
    for (Planet planet : planetList) {
      if (planet.getPlanetPlayerInfo() == realm
          && planet.getGovernor() == null) {
        result++;
      }
    }
    for (int i = 0; i < realm.getFleets().getNumberOfFleets(); i++) {
      Fleet fleet = realm.getFleets().getByIndex(i);
      if (fleet.getCommander() == null && !fleet.isStarBaseDeployed()) {
        result++;
      }
    }
    for (Leader leader : realm.getLeaderPool()) {
      if (leader.getJob() == Job.UNASSIGNED) {
        result--;
      }
    }
    return result;
  }

  /**
   * Find closest planet from certain coordinate for certain realm.
   * This planet must be visible for searcher.
   * @param coord Coordinate where to start looking.
   * @param realmName Target planet's realm's name
   * @param searcher Realm which is searching the planet
   * @return Target planet or null if not found.
   */
  public Planet getClosetPlanetFromCoordinate(final Coordinate coord,
      final String realmName, final PlayerInfo searcher) {
    PlayerInfo info = players.findByName(realmName);
    if (info != null) {
      DiplomaticTrade trade = new DiplomaticTrade(this,
          players.getIndex(searcher), players.getIndex(info));
      Planet[] planets = trade.getTradeablePlanetListForSecond();
      Planet targetPlanet = null;
      int distance = 9999;
      for (Planet planet : planets) {
        int dist = (int) coord.calculateDistance(planet.getCoordinate());
        if (dist < distance) {
          targetPlanet = planet;
          distance = dist;
        }
      }
      return targetPlanet;
    }
    return null;
  }

  /**
   * Find closest sector from certain coordinate for certain realm.
   * This sector must be visible for searcher.
   * @param coord Coordinate where to start looking.
   * @param realmName Target planet's realm's name
   * @param searcher Realm which is searching the planet
   * @return target coordinate or null
   */
  public Coordinate getClosetSectorFromCoordinate(final Coordinate coord,
      final String realmName, final PlayerInfo searcher) {
    PlayerInfo info = players.findByName(realmName);
    Coordinate target = null;
    if (info != null) {
      int index = players.getIndex(info);
      if (index != -1) {
        int distance = 9999;
        for (int x = 0; x < getMaxX(); x++) {
          for (int y = 0; y < getMaxY(); y++) {
            if (getSectorCulture(x, y).getHighestCulture() == index) {
              Coordinate possibleTarget = new Coordinate(x, y);
              if (searcher.getSectorVisibility(possibleTarget)
                  > PlayerInfo.UNCHARTED) {
                int dist = (int) coord.calculateDistance(possibleTarget);
                if (dist < distance) {
                  target = possibleTarget;
                  distance = dist;
                }
              }
            }
          }
        }
        return target;
      }
    }
    return null;
  }

  /**
   * Calculate center of realm.
   * @param index Realm index
   * @return Center of realm, or if not found then center of galaxy.
   */
  public Coordinate calculateCenterOfRealm(final int index) {
    Coordinate center = new Coordinate(maxX / 2, maxY / 2);
    if (index >= 0 && index < players.getCurrentMaxRealms()) {
      int divider = 0;
      int xSum = 0;
      int ySum = 0;
      for (Planet planet : planetList) {
        if (planet.getPlanetOwnerIndex() == index) {
          int mult = planet.getCulture() + 1;
          xSum = xSum + planet.getX() * mult;
          ySum = ySum + planet.getY() * mult;
          divider = divider + mult;
        }
      }
      PlayerInfo info = getPlayerByIndex(index);
      if (info != null) {
        for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
          Fleet fleet = info.getFleets().getByIndex(index);
          if (fleet != null && fleet.isStarBaseDeployed()
              && fleet.getCulturalValue() > 0) {
            int mult = fleet.getCulturalValue();
            xSum = xSum + fleet.getX() * mult;
            ySum = ySum + fleet.getY() * mult;
            divider = divider + mult;
          }
        }
      }
      if (divider > 0) {
        xSum = xSum / divider;
        ySum = ySum / divider;
        center = new Coordinate(xSum, ySum);
      }
    }
    return center;
  }

  /**
   * Is AI or automate taking fleet moves.
   * @return True if AI or Automate is moving.
   */
  public boolean isAiOrAutomateTakingMoves() {
    return aiOrAutomateTakingMoves;
  }

  /**
   * Set AI or automate moving the fleet flag.
   * @param flag boolean flag
   */
  public void setAiOrAutomateTakingMoves(final boolean flag) {
    this.aiOrAutomateTakingMoves = flag;
  }

  /**
   * Are all news enabled?
   * @return True if all news are enabled.
   */
  public boolean isAllNewsEnabled() {
    return allNewsEnabled;
  }

  /**
   * Set flag for all news.
   * @param allNewsEnabled Flag to set.
   */
  public void setAllNewsEnabled(final boolean allNewsEnabled) {
    this.allNewsEnabled = allNewsEnabled;
  }

  /**
   * Has human aka player met certain realm yet. This will also return true
   * if all news are subscribed. This method should be used when deciding if
   * certain news are going to show.
   * @param target Which realm is to be studied
   * @return True if has met or player realm and target are equal.
   */
  public boolean hasHumanMet(final PlayerInfo target) {
    if (isAllNewsEnabled()) {
      return true;
    }
    PlayerInfo human = players.getPlayerInfoByIndex(0);
    if (target == human) {
      return true;
    }
    if (target == null) {
      return false;
    }
    int i = players.getIndex(target);
    if (human.getDiplomacy().getDiplomacyList(i).getNumberOfMeetings() > 0) {
      return true;
    }
    return false;
  }

  /**
   * Has human realm lost the game or not?
   * @return True if has lost.
   */
  public boolean isHumanLost() {
    return humanLost;
  }

  /**
   * Set flag if human has lost the game.
   * @param humanLost Boolean flag
   */
  public void setHumanLost(final boolean humanLost) {
    this.humanLost = humanLost;
  }

  /**
   * Get Space monster realm index.
   * @return Space monster realm index or -1 if not enabled.
   */
  public int getSpaceMonsterIndex() {
    for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info.isBoard()
          && info.getRace().isMonster()) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Generate next best voting for secretary.
   * @param secretary Galactic Secretary
   * @param maxNumberOfVotes Maximum number of votes
   * @param numberOfRealms NUmber of realms in starmap
   * @param turns When voting needs to be done.
   * @return Vote if able to add new one, otherwise null
   */
  public Vote getBestNextVotingForRealm(final PlayerInfo secretary,
      final int maxNumberOfVotes, final int numberOfRealms, final int turns) {
    Vote[] availableVotes = getVotes().getListOfVoteables(maxNumberOfVotes,
        numberOfRealms, turns);
    Vote bestVote = null;
    int bestValue = -999;
    for (Vote vote : availableVotes) {
      int value = StarMapUtilities.getVotingSupport(secretary, vote, this);
      if (value > bestValue) {
        bestValue = value;
        bestVote = vote;
      }
    }
    if (bestVote == null) {
      bestVote = getVotes().generateNextVote(maxNumberOfVotes, numberOfRealms,
          turns);
    }
    return bestVote;
  }

  /**
   * Make realm lost from the starmap.
   * This will move all fleets to space pirate or remove them.
   * Also all leader are dead now.
   * @param realm Realm which has lost the game.
   */
  public void makeRealmLost(final PlayerInfo realm) {
    PlayerInfo pirates = players.getSpacePiratePlayer();
    if (pirates != null && realm.getFleets().getNumberOfFleets() > 0) {
      do {
        Fleet fleet = realm.getFleets().getByIndex(0);
        if (fleet != null) {
          pirates.getFleets().add(fleet);
          realm.getFleets().remove(0);
          realm.getMissions().deleteMissionForFleet(fleet.getName());
          String fleetName = realm.getRace().getNameSingle();
          if (fleet.isStarBaseDeployed()) {
            fleetName = fleetName + " lair";
          } else {
            fleetName = fleetName + " pirate";
          }
          fleet.setName(pirates.getFleets().generateUniqueName(fleetName));
        }
      } while (realm.getFleets().getNumberOfFleets() > 0);
    }
    if (pirates == null && realm.getFleets().getNumberOfFleets() > 0) {
      do {
        Fleet fleet = realm.getFleets().getByIndex(0);
        if (fleet != null) {
          realm.getFleets().remove(0);
          realm.getMissions().deleteMissionForFleet(fleet.getName());
        }
      } while (realm.getFleets().getNumberOfFleets() > 0);
    }
    int myIndex = players.getIndex(realm);
    for (int i = 0; i < players.getCurrentMaxRealms(); i++) {
      if (myIndex != i) {
        DiplomaticTrade trade = new DiplomaticTrade(this, myIndex, i);
        trade.generateEqualTrade(NegotiationType.PEACE);
        trade.doTrades();
      }
    }
    realm.setRuler(null);
    for (Leader leader : realm.getLeaderPool()) {
      leader.setJob(Job.DEAD);
    }
  }

  /**
   * Is redrawn force or not?
   * @return boolean
   */
  public boolean isForceRedraw() {
    return forceRedraw;
  }

  /**
   * Set force redraw flag.
   * @param forceRedraw boolean
   */
  public void setForceRedraw(final boolean forceRedraw) {
    this.forceRedraw = forceRedraw;
  }

  /**
   * Set starmap zoom level.
   * @param zoomLevel Zoom level
   */
  public void setZoomLevel(final int zoomLevel) {
    this.zoomLevel = zoomLevel;
  }
  /**
   * Get Starmap zoom level
   * @return Zoom level.
   */
  public int getZoomLevel() {
    return zoomLevel;
  }

  /**
   * Remove Obsolete ship design from human player.
   */
  public void removeObsoleteShipDesign() {
    for (Planet planet : planetList) {
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetPlayerInfo().isHuman()) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        if (planet.getUnderConstruction() instanceof Ship) {
          Ship ship = (Ship) planet.getUnderConstruction();
          for (ShipStat stat : info.getShipStatList()) {
            if (stat.isObsolete()
                && stat.getDesign().getName().equals(ship.getName())) {
              planet.setUnderConstruction(planet.getProductionList()[0]);
              break;
            }
          }
        }
      }
    }
  }

  /**
   * Get Star map's sun list.
   * @return Array list of suns.
   */
  public ArrayList<Sun> getSunList() {
    return sunList;
  }
}
