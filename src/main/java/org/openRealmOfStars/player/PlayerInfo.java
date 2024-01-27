package org.openRealmOfStars.player;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
 * Copyright (C) 2024 BottledByte
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
import java.util.Collections;

import org.openRealmOfStars.ai.mission.MissionList;
import org.openRealmOfStars.ai.pathfinding.PathPoint;
import org.openRealmOfStars.player.artifact.ArtifactLists;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.espionage.Espionage;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.government.GovernmentUtility;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceUtility;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.event.RandomEvent;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.WorldType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.repository.DiplomacyRepository;

/**
 * Player info contains which race player has, planet etc
 * Player here means both Human and AI players
 */
public class PlayerInfo {

  /**
   * Player's space race
   */
  private SpaceRace race;

  /**
   * Player's Government
   */
  private GovernmentType government;
  /**
   * Realm's war fatigue value
   */
  private int warFatigue;

  /**
   * Player's empire name
   */
  private String empireName;

  /**
   * Total credits for player, these should not go negative
   */
  private int totalCredits;

  /**
   * Technology list that player has studied or gained
   */
  private TechList techList;

  /**
   * Message for player for one turn
   */
  private MessageList msgList;

  /**
   * Space ship stat and design list
   */
  private ArrayList<ShipStat> shipStatList;

  /**
   * Player fleets
   */
  private FleetList fleets;

  /**
   * Map Data
   * 0: Uncharted only suns are drawn
   * 1: Fog of war, no fleets are drawn
   * 2: Visible everything is drawn
   */
  private byte[][] mapData;

  /**
   * Cloaking detection per sector
   */
  private int[][] mapCloakDetection;

  /**
   * Map size
   */
  private Coordinate maxCoordinate;

  /**
   * Human player if true
   */
  private boolean human;

  /**
   * Board player if true
   */
  private boolean board;
  /**
   * Missions list
   */
  private MissionList missions;

  /**
   * Player's diplomacy relations to other players
   */
  private Diplomacy diplomacy;

  /**
   * Player's espionage to other players. This has no need
   * to save it can always calculated.
   */
  private Espionage espionage;

  /**
   * Fake military size for GBNC
   */
  private int fakeMilitarySize;

  /**
   * Attitude for AI player to be randomized.
   */
  private Attitude attitude;

  /**
   * Random event occured during end turn phase.
   *  This information won't be saved.
   */
  private RandomEvent randomEventOccured;

  /**
   * Winning strategy for AI.
   */
  private WinningStrategy strategy;

  /**
   * Is player elder Realm or not
   */
  private boolean elderRealm;

  /**
   * All the leaders realm has or have had.
   */
  private ArrayList<Leader> leaderPool;

  /**
   * All the leaders realm has to recruit.
   */
  private ArrayList<Leader> leaderRecruitPool;

  /**
   * Ruler of the realm.
   */
  private Leader ruler;

  /**
   * Realm color
   */
  private PlayerColor color;

  /**
   * Enemy fleet which should intercepted. These will handled only on AI turn.
   * These will not be saved into save game data.
   */
  private ArrayList<Fleet> interceptableFleets;
  /**
   * Center of realm based on culture in galaxy.
   * This is cached for realm, since calculation
   * can be rather expensive.
   */
  private Coordinate centerRealm;

  /**
   * Best evaluation of player to build tech world.
   * This information is not saved to games, since it can be
   * and will be calculated. This information will be used only by AI.
   */
  private Planet bestPlanetForTechWorld;

  /**
   * AI Difficulty for realm. Assuming that realm is being played by AI.
   */
  private AiDifficulty aiDifficulty;

  /**
   * Artifact lists realm has found and discovered.
   */
  private ArtifactLists artifactLists;

  /**
   * Flag if realm has been lost from the game.
   * No more planets or colony ships with colonists left.
   */
  private boolean realmLost;

  /**
   * Realm background story.
   */
  private String backgroundStory;

  /**
   * Uncharted map sector, only suns are visible
   */
  public static final byte UNCHARTED = 0;
  /**
   * Fog of war, no fleets are drawn
   */
  public static final byte FOG_OF_WAR = 1;
  /**
   * Every thing are drawn expect ascension veins
   */
  public static final byte VISIBLE = 2;
  /**
   * Every thing are drawn including ascension veins
   */
  public static final byte VISIBLE_VEINS = 3;

  /**
   * Normal AI controlled player
   */
  private static final int AI_CONTROLLED = 0;

  /**
   * Human controlled player
   */
  private static final int HUMAN_CONTROLLED = 1;

  /**
   * AI controlled board player. Board player controls
   * space pirates and space monsters
   */
  private static final int BOARD_CONTROLLED = 2;

  /**
   * Constructor player info. Use this only for JUnits.
   * @param race Space Race for player
   * @param maxPlayers Maximum number of players when game is created
   * @param index Player's index in list when creating the player
   */
  public PlayerInfo(final SpaceRace race, final int maxPlayers,
      final int index) {
    this(race, maxPlayers, index, -1);
  }
  /**
   * Constructor player info. This can be used for game playing.
   * @param race Space Race for player
   * @param maxPlayers Maximum number of players when game is created
   * @param index Player's index in list when creating the player
   * @param boardPlayerIndex Board player index
   */
  public PlayerInfo(final SpaceRace race, final int maxPlayers,
      final int index, final int boardPlayerIndex) {
    setBackgroundStory("");
    setTechList(new TechList(race));
    strategy = WinningStrategy.GENERIC;
    this.msgList = new MessageList();
    shipStatList = new ArrayList<>();
    fleets = new FleetList();
    elderRealm = false;
    leaderPool = new ArrayList<>();
    leaderRecruitPool = new ArrayList<>();
    artifactLists = new ArtifactLists();
    ruler = null;
    setRealmLost(false);
    color = PlayerColor.getByIndex(index);
    aiDifficulty = AiDifficulty.NORMAL;
    setRandomEventOccured(null);
    setHuman(false);
    setBoard(false);
    missions = new MissionList();
    setRace(race);
    diplomacy = new Diplomacy(maxPlayers, index, boardPlayerIndex);
    espionage = new Espionage(maxPlayers);
    attitude = Attitude.getRandom();
    setFakeMilitarySize(100);
    // This is the old way of government
    setGovernment(GovernmentType.AI);
    setWarFatigue(0);
    if (getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      addZeroGravityTechs();
    } else {
      if (getRace() == SpaceRace.SPACE_MONSTERS) {
        addSpaceMonsterTechs();
      } else if (getRace() == SpaceRace.SPACE_PIRATE) {
        addSpacePirateTechs();
      } else {
        addDefaultTechs();
      }
    }
  }

  /**
   * Add techs by their names
   * @param techNames Tech names
   */
  private void addTechs(final String... techNames) {
    for (var techName : techNames) {
      var tech = TechFactory.findTech(techName);
      techList.addTech(tech);
    }
  }

  /**
   * Try to add number of random Tech of specified level.
   * If count is more than there are possible Techs for given type/level,
   * more Techs will not be added.
   * @param count How many Techs to add
   * @param type Type of Techs
   * @param level Level of Techs
   */
  private void addRandomTechs(final int count, final TechType type,
      final int level) {
    for (int i = 0; i < count; i++) {
      var tech = techList.findRandomNewTech(type, level);
      techList.addTech(tech);
    }
  }

  /**
   * Design initial Ship/Orbital/Starbase designs based on available Tech.
   */
  private void designInitialShips() {
    ShipDesign design = ShipGenerator.createScout(this);
    if (design != null) {
      addShipStat(new ShipStat(design));
    }

    design = ShipGenerator.createColony(this, false);
    if (design != null) {
      addShipStat(new ShipStat(design));
    }

    design = ShipGenerator.createMinorOrbital(this);
    if (design != null) {
      addShipStat(new ShipStat(design));
    }

    design = ShipGenerator.createStarbase(this, ShipSize.SMALL);
    if (design != null) {
      addShipStat(new ShipStat(design));
    }
  }

  /**
   * Default techs and ship design.
   */
  private void addDefaultTechs() {
    final String[] techs1 = {
      "Colony", "Scout Mk1", "Ion drive Mk1", "Fission source Mk1",
    };
    final String[] techs2 = {
        "Colony", "Scout Mk1", "Nuclear drive Mk1", "Fission source Mk1",
      };
    if (DiceGenerator.getBoolean()) {
      addTechs(techs1);
    } else {
      addTechs(techs2);
    }

    addRandomTechs(1, TechType.Combat, 1);
    addRandomTechs(1, TechType.Defense, 1);

    designInitialShips();
  }

  /** Add Zero Gravity techs and ship designs. */
  private void addZeroGravityTechs() {
    final String[] techs = {
      "Railgun Mk1", "Shield Mk1", "Colony", "Scout Mk1", "Minor orbital",
      "Nuclear drive Mk1", "Fission source Mk1",
    };
    addTechs(techs);

    designInitialShips();
  }

  /** Add space pirate techs and ship designs. */
  private void addSpacePirateTechs() {
    // Space pirate is always board player
    setBoard(true);

    final String[] techs = {
      "Small starbase Mk1", "Scout Mk1", "Ion drive Mk1", "Nuclear drive Mk1",
      "Fission source Mk1", "Fission source Mk2",
    };
    addTechs(techs);

    addRandomTechs(2, TechType.Combat, 1);
    addRandomTechs(1, TechType.Combat, 2);
    addRandomTechs(2, TechType.Defense, 1);
    addRandomTechs(1, TechType.Defense, 2);

    designInitialShips();
  }
  /** Add Space monster techs and ship designs. */
  private void addSpaceMonsterTechs() {
    /*
     * Space monsters get space monster tech.
     * Space monsters get all their tech.
     */
    Tech tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 1,
        "Massive mouth with teeth Mk1");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 2,
        "Massive mouth with teeth Mk2");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 3,
        "Massive mouth with teeth Mk3");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Defense, 1,
        "Organic armor Mk1");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Defense, 2,
        "Organic armor Mk2");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Defense, 3,
        "Organic armor Mk3");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Propulsion, 1,
        "Space fin");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Propulsion, 1,
        "Heart");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Propulsion, 2,
        "Large heart");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 1,
        "Tentacle Mk1");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 2,
        "Tentacle Mk2");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 1, "Space worm");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 2, "Kraken");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 3,
        "Large kraken");
    techList.addTech(tech);

    ShipDesign design = ShipGenerator.createSpaceWorm(this);
    ShipStat stat = new ShipStat(design);
    addShipStat(stat);
    design = ShipGenerator.createSpaceKraken(this);
    stat = new ShipStat(design);
    addShipStat(stat);
    design = ShipGenerator.createLargeKraken(this, false);
    stat = new ShipStat(design);
    addShipStat(stat);
    design = ShipGenerator.createLargeKraken(this, true);
    stat = new ShipStat(design);
    addShipStat(stat);
  }

  /**
   * Constructor player info.
   * This constructor should be used only when
   * creating temporary playerInfo without real
   * diplomacy information. For example in JUnits.
   * @param race Space Race for player
   */
  public PlayerInfo(final SpaceRace race) {
    this(race, 4, 0);
  }

  /**
   * Read PlayerInfo from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public PlayerInfo(final DataInputStream dis) throws IOException {
    setRandomEventOccured(null);
    setRealmLost(false);
    strategy = WinningStrategy.GENERIC;
    empireName = IOUtilities.readString(dis);
    race = SpaceRaceUtility.getRaceByIndex(dis.readInt());
    int elder = dis.read();
    if (elder == 1) {
      elderRealm = true;
    } else {
      elderRealm = false;
    }
    artifactLists = new ArtifactLists(dis);
    color = PlayerColor.getByIndex(dis.read());
    aiDifficulty = AiDifficulty.getByIndex(dis.read());
    government = GovernmentUtility.getGovernmentByIndex(dis.readInt());
    warFatigue = dis.readInt();
    totalCredits = dis.readInt();
    attitude = Attitude.getTypeByIndex(dis.read());
    // Read background story
    backgroundStory = IOUtilities.readString(dis);
    leaderPool = new ArrayList<>();
    leaderRecruitPool = new ArrayList<>();
    // Reads leader recruit pool
    int poolSize = dis.readInt();
    for (int i = 0; i < poolSize; i++) {
      Leader leader = new Leader(dis);
      leaderRecruitPool.add(leader);
    }
    // Read leader pool
    poolSize = dis.readInt();
    for (int i = 0; i < poolSize; i++) {
      Leader leader = new Leader(dis);
      leaderPool.add(leader);
    }
    for (int i = 0; i < leaderPool.size(); i++) {
      Leader leader = leaderPool.get(i);
      if (leader.getParentIndex() != -1) {
        leader.setParent(leaderPool.get(leader.getParentIndex()));
      }
    }
    int rulerIndex = dis.readInt();
    if (rulerIndex != -1) {
      setRuler(leaderPool.get(rulerIndex));
    } else {
      setRuler(null);
    }
    techList = new TechList(dis, race);
    elderRealm = false;
    msgList = new MessageList(dis);
    int count = dis.readInt();
    shipStatList = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      ShipStat ship = new ShipStat(dis);
      shipStatList.add(ship);
    }
    fleets = new FleetList(dis, this);
    int xSize = dis.readInt();
    int ySize = dis.readInt();
    initMapData(xSize, ySize);
    int mapOffset = 0;
    try {
      for (int y = 0; y < maxCoordinate.getY(); y++) {
        for (int x = 0; x < maxCoordinate.getX(); x++) {
          mapData[x][y] = dis.readByte();
          mapOffset++;
        }
      }
    } catch (IOException e) {
      throw new IOException("Reading failed at player mapdata! MapOffset:"
          + mapOffset + " " + e.getMessage());
    }
    diplomacy = DiplomacyRepository.loadDiplomacy(dis);
    espionage = new Espionage(diplomacy.getDiplomacySize());

    int size = dis.read();
    for (int i = 0; i < size; i++) {
      EspionageList list = espionage.getByIndex(i);
      boolean nonNull = dis.readBoolean();
      int level1 = 0;
      int level3 = 0;
      int level5 = 0;
      int level7 = 0;
      if (nonNull) {
        level1 = dis.readInt();
        level3 = dis.readInt();
        level5 = dis.readInt();
        level7 = dis.readInt();
      }
      if (list != null && nonNull) {
        list.setEspionageLevel1Estimate(level1);
        list.setEspionageLevel3Estimate(level3);
        list.setEspionageLevel5Estimate(level5);
        list.setEspionageLevel7Estimate(level7);
      }
    }
    setFakeMilitarySize(dis.readInt());
    int value = dis.read();
    switch (value) {
      case AI_CONTROLLED: {
        setHuman(false);
        setBoard(false);
        break;
      }
      case HUMAN_CONTROLLED: {
        setHuman(true);
        setBoard(false);
        break;
      }
      case BOARD_CONTROLLED: {
        setHuman(false);
        setBoard(true);
        break;
      }
      default: {
        setHuman(false);
        setBoard(false);
        break;
      }
    }
    missions = new MissionList(dis);
  }

  /**
   * Save Player Info to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void savePlayerInfo(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, empireName);
    dos.writeInt(race.getIndex());
    if (elderRealm) {
      dos.writeByte(1);
    } else {
      dos.writeByte(0);
    }
    artifactLists.saveArtifactLists(dos);
    dos.writeByte(color.getIndex());
    dos.writeByte(aiDifficulty.getIndex());
    dos.writeInt(government.getIndex());
    dos.writeInt(warFatigue);
    dos.writeInt(totalCredits);
    dos.writeByte(attitude.getIndex());
    IOUtilities.writeString(dos, backgroundStory);
    dos.writeInt(leaderRecruitPool.size());
    for (int i = 0; i < leaderRecruitPool.size(); i++) {
      Leader leader = leaderRecruitPool.get(i);
      leader.saveLeader(dos, this);
    }
    dos.writeInt(leaderPool.size());
    for (int i = 0; i < leaderPool.size(); i++) {
      Leader leader = leaderPool.get(i);
      leader.saveLeader(dos, this);
    }
    dos.writeInt(getLeaderIndex(ruler));
    techList.saveTechList(dos);
    msgList.saveMessageList(dos);
    dos.writeInt(shipStatList.size());
    for (int i = 0; i < shipStatList.size(); i++) {
      shipStatList.get(i).saveShipStat(dos);
    }
    fleets.saveFleetList(dos, this);
    dos.writeInt(maxCoordinate.getX());
    dos.writeInt(maxCoordinate.getY());
    if (mapData == null) {
      throw new IOException("Map data is not initialized yet!");
    }
    for (int y = 0; y < maxCoordinate.getY(); y++) {
      for (int x = 0; x < maxCoordinate.getX(); x++) {
        dos.writeByte(mapData[x][y]);
      }
    }
    DiplomacyRepository.saveDiplomacy(dos, diplomacy);
    dos.writeByte(espionage.getSize());
    for (int i = 0; i < espionage.getSize(); i++) {
      EspionageList list = espionage.getByIndex(i);
      if (list != null) {
        dos.writeBoolean(true);
        dos.writeInt(list.getEspionageLevel1Estimate());
        dos.writeInt(list.getEspionageLevel3Estimate());
        dos.writeInt(list.getEspionageLevel5Estimate());
        dos.writeInt(list.getEspionageLevel7Estimate());
      } else {
        dos.writeBoolean(false);
      }
    }
    dos.writeInt(fakeMilitarySize);
    int value = 0;
    if (isHuman()) {
      value = 1;
    } else if (isBoard()) {
      value = 2;
    } else {
      value = 0;
    }
    dos.writeByte(value);
    missions.saveMissionList(dos);
  }

  /**
   * Get Player diplomatic relations to other players
   * @return Diplomacy
   */
  public Diplomacy getDiplomacy() {
    return diplomacy;
  }

  /**
   * Get player espionage information to other players
   * @return Espionage
   */
  public Espionage getEspionage() {
    return espionage;
  }
  /**
   * Get AI Attitude which is get from the ruler or space race attitude.
   * @return Attitude
   */
  public Attitude getAiAttitude() {
    if (ruler != null) {
      return LeaderUtility.getRulerAttitude(ruler, race.getAttitude());
    }
    return race.getAttitude();
  }

  /**
   * Check AI's attitude towards spy ships
   * if it wants to build them. If attitudes
   * are in threshold this decision varies if spy ships
   * are wanted or not.
   * @return True if wants to build spy ships.
   */
  public boolean researchSpyShips() {
    int value = 0;
    switch (race.getAttitude()) {
      case BACKSTABBING: value = value + 9; break;
      case AGGRESSIVE: value = value + 8; break;
      case MILITARISTIC: value = value + 7; break;
      case EXPANSIONIST: value = value + 6; break;
      case DIPLOMATIC: value = value + 5; break;
      case LOGICAL: value = value + 5; break;
      case MERCHANTICAL: value = value + 4; break;
      case SCIENTIFIC: value = value + 3; break;
      case PEACEFUL: value = value + 2; break;
      default: value = value + 5; break;
    }
    Attitude secondaryAttitude = attitude;
    if (ruler != null) {
      secondaryAttitude = LeaderUtility.getRulerAttitude(ruler, attitude);
    }
    switch (secondaryAttitude) {
      case BACKSTABBING: value = value + 9; break;
      case AGGRESSIVE: value = value + 8; break;
      case MILITARISTIC: value = value + 7; break;
      case EXPANSIONIST: value = value + 6; break;
      case DIPLOMATIC: value = value + 5; break;
      case LOGICAL: value = value + 5; break;
      case MERCHANTICAL: value = value + 4; break;
      case SCIENTIFIC: value = value + 3; break;
      case PEACEFUL: value = value + 2; break;
      default: value = value + 5; break;
    }
    if (value > 10) {
      return true;
    }
    if (value == 10 && DiceGenerator.getRandom(100) >= 50) {
      return true;
    }
    return false;
  }
  /**
   * Get fake military setting for AI.
   * This is not very good. It is just static values. This
   * is used tuneFakeMilitarySetting which gives more dynamic
   * values for AI players.
   * @param state Which game length state is on
   * @return Fakemilitary value between 50-200
   */
  public int getFakeMilitarySetting(final GameLengthState state) {
    if (state == GameLengthState.START_GAME
        || state == GameLengthState.ELDER_HEAD_START) {
      switch (getAiAttitude()) {
        case AGGRESSIVE: return 120;
        case MILITARISTIC: return 120;
        case BACKSTABBING: return 100;
        case DIPLOMATIC: return 100;
        case EXPANSIONIST: return 100;
        case LOGICAL: return 100;
        case MERCHANTICAL: return 110;
        case PEACEFUL: return 100;
        case SCIENTIFIC: return 100;
        default: return 100;
      }
    } else if (state == GameLengthState.EARLY_GAME) {
      switch (getAiAttitude()) {
        case AGGRESSIVE: return 130;
        case MILITARISTIC: return 150;
        case BACKSTABBING: return 120;
        case DIPLOMATIC: return 100;
        case EXPANSIONIST: return 130;
        case LOGICAL: return 100;
        case MERCHANTICAL: return 130;
        case PEACEFUL: return 140;
        case SCIENTIFIC: return 100;
        default: return 100;
      }
    } else if (state == GameLengthState.MIDDLE_GAME) {
      switch (getAiAttitude()) {
        case AGGRESSIVE: return 80; //Luring someone to attack
        case MILITARISTIC: return 170;
        case BACKSTABBING: return 110;
        case DIPLOMATIC: return 100;
        case EXPANSIONIST: return 140;
        case LOGICAL: return 90;
        case MERCHANTICAL: return 130;
        case PEACEFUL: return 140;
        case SCIENTIFIC: return 100;
        default: return 100;
      }
    } else if (state == GameLengthState.LATE_GAME) {
      switch (getAiAttitude()) {
        case AGGRESSIVE: return 70; //Luring someone to attack
        case MILITARISTIC: return 200;
        case BACKSTABBING: return 70; //Luring someone to attack
        case DIPLOMATIC: return 100;
        case EXPANSIONIST: return 150;
        case LOGICAL: return 80;
        case MERCHANTICAL: return 140;
        case PEACEFUL: return 150;
        case SCIENTIFIC: return 100;
        default: return 100;
      }
    } else if (state == GameLengthState.END_GAME) {
      switch (getAiAttitude()) {
        case AGGRESSIVE: return 100;
        case MILITARISTIC: return 200;
        case BACKSTABBING: return 50; //Luring someone to attack
        case DIPLOMATIC: return 100;
        case EXPANSIONIST: return 150;
        case LOGICAL: return 50;
        case MERCHANTICAL: return 140;
        case PEACEFUL: return 150;
        case SCIENTIFIC: return 100;
        default: return 100;
      }
    }
    // Default
    return 100;
  }

  /**
   * Tune fake military size setting towards target.
   * This should be called once at turn.
   * @param state Game Length State.
   */
  public void tuneFakeMilitarySetting(final GameLengthState state) {
    int targetValue = getFakeMilitarySetting(state);
    if (targetValue > getFakeMilitarySize()) {
      setFakeMilitarySize(getFakeMilitarySize() + 10);
    } else if (targetValue < getFakeMilitarySize()) {
      setFakeMilitarySize(getFakeMilitarySize() - 10);
    }
  }
  /**
   * Get AI player's attitude
   * @return the attitude
   */
  public Attitude getAttitude() {
    return attitude;
  }

  /**
   * Get Realm's artifact lists.
   * @return Artifact lists.
   */
  public ArtifactLists getArtifactLists() {
    return artifactLists;
  }
  /**
   * Set Attitude for AI
   * @param attitude the attitude to set
   */
  public void setAttitude(final Attitude attitude) {
    this.attitude = attitude;
  }

  /**
   * Calculate how many uncharted sectors is between start and end
   * @param sx Start X coordinate
   * @param sy Start Y coordinate
   * @param ex End X coordinate
   * @param ey End Y coordinate
   * @return Number of uncharted sector
   */
  private int calculateUnchartedLine(final int sx, final int sy, final int ex,
      final int ey) {
    double startX = sx;
    double startY = sy;
    double dx = Math.abs(startX - ex);
    double dy = Math.abs(startY - ey);
    // Calculate distance to end
    int distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
    int result = 0;
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
    // Moving loop
    for (int i = 0; i < distance; i++) {
      startX = startX + mx;
      startY = startY + my;
      int nx = (int) Math.round(startX);
      int ny = (int) Math.round(startY);
      if (new Coordinate(nx, ny).isValidCoordinate(maxCoordinate)
          && mapData[nx][ny] == UNCHARTED) {
        result++;
      }
    }
    return result;
  }

  /**
  * How many percentage of system is uncharted.
  * @param sun Solar System
  * @return How many percentage is uncharted
  */
  public int getUnchartedValueSystem(final Sun sun) {
    int unCharted = 0;
    int charted = 0;
    for (int x = -StarMap.SOLAR_SYSTEM_WIDTH - 2;
        x < StarMap.SOLAR_SYSTEM_WIDTH + 3; x++) {
      for (int y = -StarMap.SOLAR_SYSTEM_WIDTH - 2;
          y < StarMap.SOLAR_SYSTEM_WIDTH + 3; y++) {
        Coordinate coordinate = new Coordinate(sun.getCenterX() + x,
            sun.getCenterY() + y);
        if (coordinate.isValidCoordinate(maxCoordinate)
            && (x > 1 || x < -1 || y > 1 || y < -1)) {
          if (mapData[sun.getCenterX() + x][sun.getCenterY()
              + y] == UNCHARTED) {
            unCharted++;
          } else {
            charted++;
          }
        }
      }
    }
    unCharted = 100 * unCharted / (charted + unCharted);
    return unCharted;
  }

  /**
   * Calculate amount of uncharted sectors.
   * @param coord Center coordinate
   * @param scannerRange Scanner Range.
   * @param sun Sun
   * @return Amount uncharted sectors.
   */
  private int calculateAmountOfUncharted(final Coordinate coord,
      final int scannerRange, final Sun sun) {
    int amount = 0;
    int cx = coord.getX();
    int cy = coord.getY();
    int minRange = scannerRange - 1;
    if (minRange < 1) {
      Coordinate newCoord = new Coordinate(cx - 1, cy);
      if (newCoord.isValidCoordinate(maxCoordinate)
          && getSectorVisibility(newCoord) == UNCHARTED
          && sun.getCenterCoordinate().calculateDistance(newCoord) >= 2) {
        amount++;
      }
      newCoord = new Coordinate(cx, cy - 1);
      if (newCoord.isValidCoordinate(maxCoordinate)
          && getSectorVisibility(newCoord) == UNCHARTED
          && sun.getCenterCoordinate().calculateDistance(newCoord) >= 2) {
        amount++;
      }
      newCoord = new Coordinate(cx, cy + 1);
      if (newCoord.isValidCoordinate(maxCoordinate)
          && getSectorVisibility(newCoord) == UNCHARTED
          && sun.getCenterCoordinate().calculateDistance(newCoord) >= 2) {
        amount++;
      }
      newCoord = new Coordinate(cx + 1, cy);
      if (newCoord.isValidCoordinate(maxCoordinate)
          && getSectorVisibility(newCoord) == UNCHARTED
          && sun.getCenterCoordinate().calculateDistance(newCoord) >= 2) {
        amount++;
      }
      newCoord = new Coordinate(cx, cy);
      if (newCoord.isValidCoordinate(maxCoordinate)
          && getSectorVisibility(newCoord) == UNCHARTED
          && sun.getCenterCoordinate().calculateDistance(newCoord) >= 2) {
        amount++;
      }
    } else {
      for (int x = -minRange; x < minRange + 1; x++) {
        for (int y = -minRange; y < minRange + 1; y++) {
          Coordinate newCoord = new Coordinate(cx + x, cy + y);
          if (newCoord.isValidCoordinate(maxCoordinate)
              && getSectorVisibility(newCoord) == UNCHARTED
              && sun.getCenterCoordinate().calculateDistance(newCoord) >= 2
              && coord.calculateDistance(newCoord) <= scannerRange) {
            amount++;
          }
        }
      }
    }
    return amount;
  }

  /**
   * Get Best uncharted sector around certain solar system for weak AI.
   * @param sun Solar system
   * @param fleet Fleet doing the exploring.
   * @return PathPoint where to go next or null if no more exploring
   */
  private PathPoint getBestUnchartedSectorWeak(final Sun sun,
      final Fleet fleet) {
    double bestDistance = 999;
    int bestScore = 0;
    PathPoint bestPoint = null;
    int scanner = fleet.getFleetScannerLvl();
    int sunScanRad = scanner + 2;
    for (int x = -sunScanRad; x < sunScanRad + 1; x++) {
      for (int y = -sunScanRad; y < sunScanRad + 1; y++) {
        Coordinate coordinate = new Coordinate(sun.getCenterX() + x,
            sun.getCenterY() + y);
        if (coordinate.isValidCoordinate(maxCoordinate)
            && (x > 1 || x < -1 || y > 1 || y < -1)
            && mapData[sun.getCenterX() + x][sun.getCenterY()
              + y] == UNCHARTED) {
          double dist = coordinate.calculateDistance(fleet.getCoordinate());
          if (dist < bestDistance) {
            double sundist = coordinate.calculateDistance(
                sun.getCenterCoordinate());
            int score = 5 - Math.abs((sunScanRad - (int) sundist));
            if (score > bestScore) {
              bestPoint = new PathPoint(coordinate.getX(), coordinate.getY(),
                  dist);
              bestScore = score;
              bestDistance = dist;
            }
          }
        }
      }
    }
    if (bestPoint == null) {
      if (DiceGenerator.getBoolean()) {
        bestPoint = getClosestUnchartedSector(sun, fleet);
      } else {
        bestPoint = getUnchartedSector(sun, fleet);
      }
    }
    return bestPoint;
  }

  /**
   * Get Best uncharted sector around certain solar system for normal AI.
   * @param sun Solar system
   * @param fleet Fleet doing the exploring.
   * @return PathPoint where to go next or null if no more exploring
   */
  private PathPoint getBestUnchartedSectorNormal(final Sun sun,
      final Fleet fleet) {
    double bestDistance = 999;
    int bestScore = 0;
    PathPoint bestPoint = null;
    int scanner = fleet.getFleetScannerLvl();
    int sunScanRad = scanner + 2;
    for (int x = -sunScanRad; x < sunScanRad + 1; x++) {
      for (int y = -sunScanRad; y < sunScanRad + 1; y++) {
        Coordinate coordinate = new Coordinate(sun.getCenterX() + x,
            sun.getCenterY() + y);
        if (coordinate.isValidCoordinate(maxCoordinate)
            && (x > 1 || x < -1 || y > 1 || y < -1)
            && mapData[sun.getCenterX() + x][sun.getCenterY()
              + y] == UNCHARTED) {
          double dist = coordinate.calculateDistance(fleet.getCoordinate());
          if (dist <= bestDistance) {
            double sundist = coordinate.calculateDistance(
                sun.getCenterCoordinate());
            int score = 5 - Math.abs((sunScanRad - (int) sundist))
                + calculateAmountOfUncharted(coordinate, scanner, sun);
            if (score > bestScore) {
              bestPoint = new PathPoint(coordinate.getX(), coordinate.getY(),
                  dist);
              bestScore = score;
              bestDistance = dist;
            }
          }
        }
      }
    }
    if (bestPoint == null) {
      if (DiceGenerator.getBoolean()) {
        bestPoint = getClosestUnchartedSector(sun, fleet);
      } else {
        bestPoint = getUnchartedSector(sun, fleet);
      }
    }
    return bestPoint;
  }

  /**
   * Get Best uncharted sector around certain solar system
   * @param sun Solar system
   * @param fleet Fleet doing the exploring.
   * @return PathPoint where to go next or null if no more exploring
   */
  public PathPoint getBestUnchartedSector(final Sun sun,
      final Fleet fleet) {
    if (aiDifficulty == AiDifficulty.WEAK) {
      return getBestUnchartedSectorWeak(sun, fleet);
    }
    if (aiDifficulty == AiDifficulty.NORMAL) {
      return getBestUnchartedSectorNormal(sun, fleet);
    }
    double bestDistance = 999;
    int bestScore = 0;
    PathPoint bestPoint = null;
    int uncharted = 0;
    int sectorAmount = 0;
    int scanner = fleet.getFleetScannerLvl();
    int sunScanRad = scanner + 2;
    for (int x = -sunScanRad; x < sunScanRad + 1; x++) {
      for (int y = -sunScanRad; y < sunScanRad + 1; y++) {
        Coordinate coordinate = new Coordinate(sun.getCenterX() + x,
            sun.getCenterY() + y);
        if (coordinate.isValidCoordinate(maxCoordinate)
            && (x > 1 || x < -1 || y > 1 || y < -1)) {
          sectorAmount++;
          if (getSectorVisibility(coordinate) == UNCHARTED) {
            uncharted++;
          }
          double dist = coordinate.calculateDistance(fleet.getCoordinate());
          int score = calculateAmountOfUncharted(coordinate, scanner, sun);
          if (score > bestScore && dist <= bestDistance) {
            bestPoint = new PathPoint(coordinate.getX(), coordinate.getY(),
                dist);
            bestScore = score;
            bestDistance = dist;
          }
        }
      }
    }
    if (uncharted * 100 / sectorAmount > 95
        || bestDistance > 5) {
      bestPoint = null;
    }
    if (bestPoint == null) {
      bestPoint = getClosestUnchartedSector(sun, fleet);
    }
    return bestPoint;
  }
  /**
   * Get closest sector to explore in this Solar system.
   * @param sun Solar System
   * @param fleet Fleet doing the exploring
   * @return PathPoint where to go next or null if no more exploring
   */
  public PathPoint getClosestUnchartedSector(final Sun sun,
      final Fleet fleet) {
    double distance = 999;
    PathPoint bestPoint = null;
    for (int x = -StarMap.SOLAR_SYSTEM_WIDTH;
        x < StarMap.SOLAR_SYSTEM_WIDTH + 1; x++) {
      for (int y = -StarMap.SOLAR_SYSTEM_WIDTH;
          y < StarMap.SOLAR_SYSTEM_WIDTH + 1; y++) {
        Coordinate coordinate = new Coordinate(sun.getCenterX() + x,
            sun.getCenterY() + y);
        if (coordinate.isValidCoordinate(maxCoordinate)
            && (x > 1 || x < -1 || y > 1 || y < -1)
            && mapData[sun.getCenterX() + x][sun.getCenterY()
              + y] == UNCHARTED) {
          double dist = coordinate.calculateDistance(fleet.getCoordinate());
          if (dist < distance) {
            distance = dist;
            bestPoint = new PathPoint(coordinate.getX(), coordinate.getY(),
                distance);
          }
        }
      }
    }
    return bestPoint;
  }
  /**
   * Get best sector to explore in this Solar system.
   * @param sun Solar System
   * @param fleet Fleet doing the exploring
   * @return PathPoint where to go next or null if no more exploring
   */
  public PathPoint getUnchartedSector(final Sun sun, final Fleet fleet) {
    PathPoint result = null;
    int scan = fleet.getFleetScannerLvl();
    int[] unCharted = new int[4];
    int[] charted = new int[4];
    int[] sectors = new int[4];
    PathPoint[] points = new PathPoint[4];
    int[] bestPoint = new int[4];
    for (int i = 0; i < points.length; i++) {
      points[i] = null;
    }
    int sector = 0;
    for (int x = -StarMap.SOLAR_SYSTEM_WIDTH
        - 2; x < StarMap.SOLAR_SYSTEM_WIDTH + 3; x++) {
      for (int y = -StarMap.SOLAR_SYSTEM_WIDTH
          - 2; y < StarMap.SOLAR_SYSTEM_WIDTH + 3; y++) {
        if (x <= 0 && y <= 0) {
          sector = 0;
        } else if (x > 0 && y <= 0) {
          sector = 1;
        } else if (x <= 0 && y > 0) {
          sector = 2;
        } else if (x > 0 && y > 0) {
          sector = 3;
        }
        Coordinate sectorCoordinate =
            new Coordinate(sun.getCenterX() + x, sun.getCenterY() + y);
        if (sectorCoordinate.isValidCoordinate(maxCoordinate)
            && (x > 1 || x < -1 || y > 1 || y < -1)) {
          if (mapData[sun.getCenterX() + x][sun.getCenterY()
              + y] == UNCHARTED) {
            unCharted[sector]++;
            Coordinate fleetCoordinate =
                new Coordinate(fleet.getX(), fleet.getY());
            PathPoint tempPoint = new PathPoint(sun.getCenterX() + x,
                sun.getCenterY() + y,
                fleetCoordinate.calculateDistance(sectorCoordinate));
            int value = calculateUnchartedLine(fleet.getX(), fleet.getY(),
                sun.getCenterX() + x, sun.getCenterY() + y);
            if (points[sector] == null) {
              points[sector] = tempPoint;
              bestPoint[sector] = value;
            } else if (value > bestPoint[sector]) {
              points[sector] = tempPoint;
              bestPoint[sector] = value;
            }
          } else {
            charted[sector]++;
          }
        }
      }
    }
    for (int i = 0; i < sectors.length; i++) {
      sectors[i] = 100 * unCharted[i] / (charted[i] + unCharted[i]);
    }
    int unChartedValue = (sectors[0] + sectors[1] + sectors[2] + sectors[3])
        / 4;
    if (unChartedValue < 20) {
      return null;
    }
    int pathValue = 0;
    int resultValue = 0;
    for (sector = 0; sector < 4; sector++) {
      int mx = 0;
      int my = 0;
      switch (sector) {
      case 0: {
        mx = -1;
        my = -1;
        break;
      }
      case 1: {
        mx = 1;
        my = -1;
        break;
      }
      case 2: {
        mx = -1;
        my = 1;
        break;
      }
      case 3: {
        mx = 1;
        my = 1;
        break;
      }
      default: {
        mx = -1;
        my = -1;
      }
      }
      PathPoint temp = null;
      if (sectors[sector] > 60) {
        int nx = sun.getCenterX();
        int ny = sun.getCenterY();
        nx = nx + mx;
        ny = ny + my;
        for (int i = 0; i < StarMap.SOLAR_SYSTEM_WIDTH + 2; i++) {
          nx = nx + mx;
          ny = ny + my;
          Coordinate fleetCoordinate = fleet.getCoordinate();
          Coordinate coordinate = new Coordinate(nx, ny);
          double distance = fleetCoordinate.calculateDistance(coordinate);
          if (coordinate.isValidCoordinate(maxCoordinate) && i >= scan
              && distance > 1 && mapData[nx][ny] == UNCHARTED) {
            temp = new PathPoint(nx, ny, distance);
            pathValue = calculateUnchartedLine(fleet.getX(), fleet.getY(), nx,
                ny);
            break;
          }
          coordinate = new Coordinate(sun.getCenterX(), ny);
          distance = fleetCoordinate.calculateDistance(coordinate);
          if (temp == null && coordinate.isValidCoordinate(maxCoordinate)
              && i >= scan && distance > 1
              && mapData[sun.getCenterX()][ny] == UNCHARTED) {
            temp = new PathPoint(sun.getCenterX(), ny, distance);
            pathValue = calculateUnchartedLine(fleet.getX(), fleet.getY(),
                sun.getCenterX(), ny);
            break;
          }
          coordinate = new Coordinate(nx, sun.getCenterY());
          distance = fleetCoordinate.calculateDistance(coordinate);
          if (temp == null && coordinate.isValidCoordinate(maxCoordinate)
              && i >= scan && distance > 1
              && mapData[nx][sun.getCenterY()] == UNCHARTED) {
            temp = new PathPoint(nx, sun.getCenterY(), distance);
            pathValue = calculateUnchartedLine(fleet.getX(), fleet.getY(), nx,
                sun.getCenterY());
            break;
          }
        }
      }
      if (temp == null && points[sector] != null
          && points[sector].getDistance() > 1) {
        temp = points[sector];
        pathValue = bestPoint[sector];
      }
      if (result == null && temp != null) {
        result = temp;
        resultValue = pathValue;
      }
      if (temp != null && result != null && pathValue > resultValue) {
        result = temp;
        resultValue = pathValue;
      }
    }
    return result;
  }

  /**
   * Init map visibility and cloaking detection maps
   * @param maximumX Map size in X axel
   * @param maximumY Map size in Y axel
   */
  public void initMapData(final int maximumX, final int maximumY) {
    maxCoordinate = new Coordinate(maximumX, maximumY);
    mapData = new byte[maximumX][maximumY];
    mapCloakDetection = new int[maximumX][maximumY];
  }

  /**
   * Get sector visibility
   * @param coordinate coordinate
   * @return UNCHARTED, FOG_OF_WAR or VISIBLE
   */
  public byte getSectorVisibility(final Coordinate coordinate) {
    byte result = UNCHARTED;
    try {
      result = mapData[coordinate.getX()][coordinate.getY()];
    } catch (ArrayIndexOutOfBoundsException e) {
      ErrorLogger.log(e);
    }
    return result;
  }

  /**
   * Set sector visibility
   * @param x X coordinate
   * @param y Y coordinate
   * @param visibility UNCHARTED, FOG_OF_WAR or VISIBLE
   */
  public void setSectorVisibility(final int x, final int y,
      final byte visibility) {
    if (visibility >= 0 && visibility <= VISIBLE_VEINS) {
      try {
        mapData[x][y] = visibility;
      } catch (ArrayIndexOutOfBoundsException e) {
        ErrorLogger.log(e);
      }
    }
  }

  /**
   * Get sector cloaking detection
   * @param x X coordinate
   * @param y Y coordinate
   * @return cloaking detection value
   */
  public int getSectorCloakDetection(final int x, final int y) {
    int result = 0;
    try {
      result = mapCloakDetection[x][y];

    } catch (ArrayIndexOutOfBoundsException e) {
      ErrorLogger.log(e);
    }
    return result;
  }

  /**
   * Set sector cloaking detection value
   * @param x X coordinate
   * @param y Y coordinate
   * @param value cloaking detection value
   */
  public void setSectorCloakingDetection(final int x, final int y,
      final int value) {
    try {
      mapCloakDetection[x][y] = value;
    } catch (ArrayIndexOutOfBoundsException e) {
      ErrorLogger.log(e);
    }
  }

  /**
   * Clear visibility data after turn. These needs to be recalculated for
   * each turn.
   */
  public void resetVisibilityDataAfterTurn() {
    for (int y = 0; y < maxCoordinate.getY(); y++) {
      for (int x = 0; x < maxCoordinate.getX(); x++) {
        mapCloakDetection[x][y] = 0;
        if (mapData[x][y] >= VISIBLE) {
          mapData[x][y] = FOG_OF_WAR;
        }
      }
    }
  }

  /**
   * Number of Ship stats player has
   * @return Number of ship stats in list
   */
  public int getNumberOfShipStats() {
    return shipStatList.size();
  }

  /**
   * Get ship stat by index. May return null if index invalid
   * @param index ShipStat index
   * @return ShipStat or null
   */
  public ShipStat getShipStat(final int index) {
    if (shipStatList.size() > 0 && index >= 0 && index < shipStatList.size()) {
      return shipStatList.get(index);
    }
    return null;
  }

  /**
   * Does Player has starbase bigger than small. Returns true if has.
   * @return True if player has starbase bigger than small.
   */
  public boolean isBiggerStarbases() {
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getHull().getHullType() == ShipHullType.STARBASE
          && stat.getDesign().getHull().getSize() != ShipSize.SMALL) {
        return true;
      }
    }
    return false;
  }
  /**
   * Get Ship stat by name.
   * @param name for search
   * @return ShipStat if found otherwise null
   */
  public ShipStat getShipStatByName(final String name) {
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().equals(name)) {
        return stat;
      }
    }
    return null;
  }

  /**
   * Does player have one bomber ship available?
   * @return True if player has bomber ships
   */
  public boolean bombersAvailable() {
    for (ShipStat stat : shipStatList) {
      if (!stat.isObsolete() && stat.getDesign().isBomberShip()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Does player have one bomber/trooper ship available?
   * @return True if player has bomber ship with trooper functionality.
   */
  public boolean bomberTrooperAvailable() {
    for (ShipStat stat : shipStatList) {
      if (!stat.isObsolete() && stat.getDesign().isBomberShip()
          && stat.getDesign().isTrooperShip()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get count of Ship stats name starts with
   * @param name for search
   * @return Number of ship stats which start with that
   */
  public int getShipStatStartsWith(final String name) {
    int result = 0;
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().startsWith(name)) {
        result++;
      }
    }
    return result;
  }

  /**
   * Get highest number after ships starting with name.
   * @param name for search
   * @return Highest number after " Mk".
   */
  public int getShipStatHighestNumber(final String name) {
    int result = 0;
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().startsWith(name)) {
        String[] parts = stat.getDesign().getName().split(" Mk");
        if (parts.length == 2) {
          try {
            result = Integer.valueOf(parts[1]);
          } catch (NumberFormatException e) {
            // Just ignore this one
            ErrorLogger.log("Not an number after Mk in design name:"
               + stat.getDesign().getName());
          }
        }
      }
    }
    return result;
  }

  /**
  * Get Ship Stat list as a fixed array
  * @return Ship Stat array
  */
  public ShipStat[] getShipStatList() {
    return shipStatList.toArray(new ShipStat[shipStatList.size()]);
  }

  /**
  * Get Ship Stat list as a fixed array but in alphabetical order
  * @return Ship Stat array
  */
  public ShipStat[] getShipStatListInOrder() {
    @SuppressWarnings("unchecked")
    ArrayList<ShipStat> orderList = (ArrayList<ShipStat>) shipStatList.clone();
    Collections.sort(orderList);
    return orderList.toArray(new ShipStat[orderList.size()]);
  }

  /**
   * Add Ship Stat to list
   * @param stat ShipStat to add
   */
  public void addShipStat(final ShipStat stat) {
    if (stat != null) {
      shipStatList.add(stat);
    }
  }

  /**
   * remove Ship Stat from list
   * @param index Index to remove
   */
  public void removeShipStat(final int index) {
    if (shipStatList.size() > 0 && index >= 0 && index < shipStatList.size()) {
      shipStatList.remove(index);
    }
  }

  /**
   * Get Best upgrade for military ship
   * @param ship Ship which to upgrade
   * @return Upgraded ship stat or null
   */
  public ShipStat getBestUpgrade(final Ship ship) {
    ShipStat bestStat = null;
    int bestValue = 0;
    if (ship != null && ship.getTheoreticalMilitaryPower() > 0) {
      for (ShipStat stat : getShipStatList()) {
        if (stat.getDesign().getHull().getName().equals(
            ship.getHull().getName())
            && !ship.getName().equals(stat.getDesign().getName())
            && stat.getDesign().getHull().getMaxSlot()
            == ship.getHull().getMaxSlot()) {
          int value = stat.getDesign().getTotalMilitaryPower();
          if (value > bestValue) {
            bestValue = value;
            bestStat = stat;
          }
        }
      }
    }
    return bestStat;
  }

  /**
   * Remove ship stat from list.
   * @param toDelete ShipStat to delete
   */
  public void removeShipStat(final ShipStat toDelete) {
    int i = 0;
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().equals(toDelete.getDesign().getName())) {
        break;
      }
      i++;
    }
    if (shipStatList.size() > 0) {
      removeShipStat(i);
    }
  }

  /**
   * Get space race for player
   * @return Space Race
   */
  public SpaceRace getRace() {
    return race;
  }

  /**
   * Set space race for player
   * @param race Space Race
   */
  public void setRace(final SpaceRace race) {
    this.race = race;
  }

  /**
   * Get government type for player
   * @return Government type
   */
  public GovernmentType getGovernment() {
    return government;
  }

  /**
   * Set government type for player
   * @param government Government type
   */
  public void setGovernment(final GovernmentType government) {
    this.government = government;
  }
  /**
   * Set Realm's war fatigue.
   * War fatigue is negative number.
   * @param fatigue to set
   */
  public void setWarFatigue(final int fatigue) {
    warFatigue = fatigue;
  }
  /**
   * Get Realm's war fatigue.
   * War fatigue is negative number.
   * @return war fatigue
   */
  public int getWarFatigue() {
    return warFatigue;
  }

  /**
   * Get total war fatigue. This is amount negative happiness
   * is being given to planets. This is between -6 and 0.
   * -6 is really bad, 0 is not war fatigue at all.
   * @return Total war fatigue.
   */
  public int getTotalWarFatigue() {
    int fatigue = getWarFatigue()
        / getRace().getWarFatigueResistance();
    if (fatigue < -6) {
      // Maximum war fatigue
      fatigue = -6;
    }
    return fatigue;
  }

  /**
   * Get empire name for player
   * @return Empire name as a String
   */
  public String getEmpireName() {
    return empireName;
  }

  /**
   * Set empire name for player
   * @param empireName as a String
   */
  public void setEmpireName(final String empireName) {
    this.empireName = empireName;
  }

  /**
   * Get total amount of credits player has
   * @return Number of credits
   */
  public int getTotalCredits() {
    return totalCredits;
  }

  /**
   * Set total amount of credits for player
   * @param totalCredits Number of credits
   */
  public void setTotalCredits(final int totalCredits) {
    this.totalCredits = totalCredits;
  }

  /**
   * Get tech list for player
   * @return Tech list
   */
  public TechList getTechList() {
    return techList;
  }

  /**
   * Set tech list for player
   * @param techList Tech List
   */
  public void setTechList(final TechList techList) {
    this.techList = techList;
  }

  /**
   * Get the player fleets
   * @return fleetList which is never null
   */
  public FleetList getFleets() {
    return fleets;
  }

  /**
   * Get message list for one turn
   *
   * @return Message list for one turn
   */
  public MessageList getMsgList() {
    return msgList;
  }

  /**
   * Is player human controlled or AI
   * @return True for human controlled
   */
  public boolean isHuman() {
    return human;
  }

  /**
   * Set if player is human controlled or AI
   * @param human True for human controlled
   */
  public void setHuman(final boolean human) {
    this.human = human;
  }

  /**
   * Is player board controlled or not
   * @return True for board controlled
   */
  public boolean isBoard() {
    return board;
  }

  /**
   * Set if player is board controlled AI
   * @param board True for board controlled
   */
  public void setBoard(final boolean board) {
    this.board = board;
  }

  /**
   * Get missions list. This is non null only for AI players
   * @return Mission list
   */
  public MissionList getMissions() {
    return missions;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(empireName);
    sb.append(" - ");
    if (isHuman()) {
      sb.append("Human");
    } else {
      sb.append("AI");
    }
    sb.append("\n");
    sb.append(race.toString());
    return sb.toString();
  }

  /**
   * Reinit playerinfo when creating starmap and random positions do not fit.
   */
  public void reInit() {
    msgList.clearMessages();
    fleets = new FleetList();
  }

  /**
   * Check if Ship design has duplicate name.
   * @param name to check
   * @return True if duplicate found, otherwise false
   */
  public boolean duplicateShipDesignName(final String name) {
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the fake military size. This value is in percents.
   * 50 means half of the real military size.
   * 200 mean double the military size.
   * @return the fakeMilitarySize
   */
  public int getFakeMilitarySize() {
    return fakeMilitarySize;
  }

  /**
   * Set the fake military size. This value is in percents.
   * 50 means half of the real military size.
   * 200 mean double the military size.
   * @param fakeMilitarySize the fakeMilitarySize to set
   */
  public void setFakeMilitarySize(final int fakeMilitarySize) {
    if (fakeMilitarySize >= 50 && fakeMilitarySize <= 200) {
      this.fakeMilitarySize = fakeMilitarySize;
    }
  }

  /**
   * Handle player's fake military cost.
   * This method can tune down fake military
   * if player does not have enough credits.
   */
  public void handleFakeMilitarySizeCost() {
    int value = getFakeMilitarySize();
    int cost = Espionage.calculateEspionageCost(value);
    if (cost > getTotalCredits()) {
      int direction = 0;
      if (value > 100) {
        direction = -10;
      } else if (value < 100) {
        direction = 10;
      }
      while (cost > getTotalCredits()) {
        value = value + direction;
        cost = Espionage.calculateEspionageCost(value);
        if (cost == 0) {
          break;
        }
      }
      setFakeMilitarySize(value);
    }
    setTotalCredits(getTotalCredits() - cost);
  }

  /**
   * Get list of obsoleted ship design
   * @return Obsolete ship designs.
   */
  public ShipStat[] getObsoleteShips() {
    ArrayList<ShipStat> stats = new ArrayList<>();
    for (ShipStat stat : shipStatList) {
      if (stat.isObsolete()) {
        stats.add(stat);
      }
    }
    return stats.toArray(new ShipStat[stats.size()]);
  }
  /**
   * Get random event which has occured.
   * @return the randomEventOccured
   */
  public RandomEvent getRandomEventOccured() {
    return randomEventOccured;
  }
  /**
   * Set random event that occured.
   * @param randomEventOccured the randomEventOccured to set
   */
  public void setRandomEventOccured(final RandomEvent randomEventOccured) {
    this.randomEventOccured = randomEventOccured;
  }
  /**
   * @return the strategy
   */
  public WinningStrategy getStrategy() {
    return strategy;
  }
  /**
   * @param strategy the strategy to set
   */
  public void setStrategy(final WinningStrategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Is realm elder or not elder realm.
   * @return True for elder realm.
   */
  public boolean isElderRealm() {
    return elderRealm;
  }

  /**
   * Set Elder realm flag.
   * @param elderRealm True for elder realm.
   */
  public void setElderRealm(final boolean elderRealm) {
    this.elderRealm = elderRealm;
  }
  /**
   * Get current ruler of the realm
   * @return the ruler
   */
  public Leader getRuler() {
    return ruler;
  }
  /**
   * Set the current leader of the realm
   * @param ruler the ruler to set
   */
  public void setRuler(final Leader ruler) {
    this.ruler = ruler;
    if (this.ruler != null) {
      this.ruler.assignJob(Job.RULER, this);
    }
  }

  /**
   * Get leader pool.
   * @return Array list of all leader for single realm.
   */
  public ArrayList<Leader> getLeaderPool() {
    return leaderPool;
  }

  /**
   * Get leader recruit pool.
   * @return Array list of all leader for recruit
   */
  public ArrayList<Leader> getLeaderRecruitPool() {
    return leaderRecruitPool;
  }

  /**
   * Add new recruit for recruit leader pool.
   * @param newRecruit Leader
   */
  public void addRecruitLeader(final Leader newRecruit) {
    leaderRecruitPool.add(newRecruit);
  }
  /**
   * Remove recruit from recruit leader pool.
   * @param newRecruit Leader
   */
  public void removeRecruitLeader(final Leader newRecruit) {
    leaderRecruitPool.remove(newRecruit);
  }
  /**
   * Get Leader index.
   * @param leader Leader which index is searched for
   * @return Leader index or -1 if not found or leader is null.
   */
  public int getLeaderIndex(final Leader leader) {
    if (leader == null) {
      return -1;
    }
    for (int i = 0; i < leaderPool.size(); i++) {
      if (leader == leaderPool.get(i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Are all leaders dead or not.
   * @return True if all are dead.
   */
  public boolean areLeadersDead() {
    for (Leader leader : leaderPool) {
      if (leader.getJob() != Job.DEAD) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get knowledge bonus against another realm.
   * This bonus is between 0-10. Where 0 is that no information is basically
   * available and 10 is everything is known.
   * @param realmIndex Realm which knowledge bonus is calculated.
   * @return Knowledge bonus.
   */
  public int getKnowledgeBonus(final int realmIndex) {
    int result = 0;
    EspionageList espionageList = getEspionage().getByIndex(realmIndex);
    if (espionageList != null) {
      result = espionageList.getTotalBonus();
    }
    DiplomacyBonusList diplomacyList = getDiplomacy().getDiplomacyList(
        realmIndex);
    if (diplomacyList != null) {
      if (diplomacyList.getNumberOfMeetings() > 2) {
        result++;
      }
      if (diplomacyList.getNumberOfMeetings() > 5) {
        result++;
      }
      if (diplomacyList.getNumberOfMeetings() > 8) {
        result++;
      }
      if (diplomacyList.getNumberOfMeetings() > 11) {
        result++;
      }
    }
    if (result > 10) {
      result = 10;
    }
    return result;
  }
  /**
   * Get intercetable fleets.
   * @return the interceptableFleets
   */
  public ArrayList<Fleet> getInterceptableFleets() {
    if (interceptableFleets == null) {
      interceptableFleets = new ArrayList<>();
    }
    return interceptableFleets;
  }

  /**
   * Add interceptable fleet into list.
   * @param fleet New fleet to add.
   */
  public void addInterceptableFleet(final Fleet fleet) {
    if (interceptableFleets == null) {
      interceptableFleets = new ArrayList<>();
    }
    interceptableFleets.add(fleet);
  }

  /**
   * Initialized interceptable fleet list.
   */
  public void cleanInterceptableFleetList() {
    interceptableFleets = new ArrayList<>();
  }
  /**
   * Get Center of realm. This will return
   * cached value.
   * @return the centerRealm
   */
  public Coordinate getCenterRealm() {
    return centerRealm;
  }
  /**
   * Set center of realm.
   * @param centerRealm the centerRealm to set
   */
  public void setCenterRealm(final Coordinate centerRealm) {
    this.centerRealm = centerRealm;
  }
  /**
   * Best planet for tech world.
   * @return the bestPlanetForTechWorld
   */
  public Planet getBestPlanetForTechWorld() {
    return bestPlanetForTechWorld;
  }

  /**
   * Estimate which planet is best for making the tech world.
   * @param planets Array list of planets.
   */
  public void estimateBestTechWorld(final ArrayList<Planet> planets) {
    int bestValue = -99;
    bestPlanetForTechWorld = null;
    for (Planet planet : planets) {
      if (planet.getPlanetPlayerInfo() == this) {
        int value = planet.getEvaluationForTechWorld();
        if (value > bestValue) {
          bestPlanetForTechWorld = planet;
          bestValue = value;
        }
      }
    }
  }

  /**
   * Get planet base value for space race.
   * This will tell how much of population world type can
   * hold.
   * @param planet Planet
   * @return Base value between 0 - 125 %.
   */
  public int getPlanetSuitabilityValue(final Planet planet) {
    int result = this.getRace().getTemperatureBaseValue(
        planet.getTemperatureType());
    WorldType worldType = planet.getPlanetType().getWorldType();
    if (worldType == WorldType.BARRENWORLD
        && techList.hasTech("Advanced colonization")) {
      result = result + 25;
    }
    if (worldType == WorldType.DESERTWORLD
        && techList.hasTech("Desert colonization")) {
      result = result + 25;
    }
    if (worldType == WorldType.SWAMPWORLD
        && techList.hasTech("Swamp colonization")) {
      result = result + 25;
    }
    if (worldType == WorldType.ICEWORLD
        && techList.hasTech("Ice colonization")) {
      result = result + 25;
    }
    if (worldType == WorldType.VOLCANICWORLD
        && techList.hasTech("Volcanic colonization")) {
      result = result + 25;
    }
    if (worldType == WorldType.WATERWORLD
        && techList.hasTech("Aquatic colonization")) {
      result = result + 25;
    }
    return result;
  }

  /**
   * Set realm color.
   * @param color PlayerColor
   */
  public void setColor(final PlayerColor color) {
    this.color = color;
  }

  /**
   * Get Realm color.
   * @return PlayerColor
   */
  public PlayerColor getColor() {
    return color;
  }
  /**
   * Get AI Difficulty
   * @return AiDifficulty
   */
  public AiDifficulty getAiDifficulty() {
    return aiDifficulty;
  }
  /**
   * Set AI Difficulty
   * @param aiDifficulty AI Difficulty level to set.
   */
  public void setAiDifficulty(final AiDifficulty aiDifficulty) {
    this.aiDifficulty = aiDifficulty;
  }

  /**
   * Find Minor orbital design.
   * @return Minor orbital design or null
   */
  public ShipStat findMinorOrbitalDesign() {
    for (ShipStat ite : shipStatList) {
      if (ite.getDesign().getHull().getName().equals("Minor orbital")
          && !ite.isObsolete()) {
        return ite;
      }
    }
    return null;
  }

  /**
   * Count Minor orbital designs.
   * @return number of minor orbital design
   */
  public int countMinorOrbitalDesign() {
    int result = 0;
    for (ShipStat ite : shipStatList) {
      if (ite.getDesign().getHull().getName().equals("Minor orbital")
          && !ite.isObsolete()) {
        result++;
      }
    }
    return result;
  }
  /**
   * Is Realm lost from the game?
   * @return the realmLost
   */
  public boolean isRealmLost() {
    return realmLost;
  }
  /**
   * Set if realm has lost from the game.
   * @param realmLost the realmLost to set
   */
  public void setRealmLost(final boolean realmLost) {
    this.realmLost = realmLost;
  }
  /**
   * Get Realm's background story
   * @return the backgroundStory
   */
  public String getBackgroundStory() {
    return backgroundStory;
  }
  /**
   * Set realm's background story
   * @param backgroundStory the backgroundStory to set
   */
  public void setBackgroundStory(final String backgroundStory) {
    this.backgroundStory = backgroundStory;
  }

  /**
   * Add new story part for background story.
   * @param newStory New story part.
   */
  public void appendStory(final String newStory) {
    appendStory(newStory, -1);
  }

  /**
   * Add new story part for background story.
   * @param newStory New story part.
   * @param starYear Star year when story happens
   */
  public void appendStory(final String newStory, final int starYear) {
    if (newStory != null && !newStory.isBlank()) {
      this.backgroundStory = this.backgroundStory + "\n\n";
      if (starYear > 0) {
        this.backgroundStory = this.backgroundStory + "At " + starYear + ": ";
      }
      this.backgroundStory = this.backgroundStory + newStory;
    } else {
      ErrorLogger.log("Null or empty story encountered!");
      Thread.dumpStack();
    }
  }

}
