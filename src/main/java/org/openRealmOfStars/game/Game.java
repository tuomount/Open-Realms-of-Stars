package org.openRealmOfStars.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.audio.music.MusicFileInfo;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.States.AITurnView;
import org.openRealmOfStars.game.States.BattleView;
import org.openRealmOfStars.game.States.CreditsView;
import org.openRealmOfStars.game.States.DiplomacyView;
import org.openRealmOfStars.game.States.EspionageView;
import org.openRealmOfStars.game.States.FleetTradeView;
import org.openRealmOfStars.game.States.FleetView;
import org.openRealmOfStars.game.States.GalaxyCreationView;
import org.openRealmOfStars.game.States.HistoryView;
import org.openRealmOfStars.game.States.LoadGameView;
import org.openRealmOfStars.game.States.MainMenu;
import org.openRealmOfStars.game.States.NewsCorpView;
import org.openRealmOfStars.game.States.OptionsView;
import org.openRealmOfStars.game.States.PlanetBombingView;
import org.openRealmOfStars.game.States.PlanetView;
import org.openRealmOfStars.game.States.PlayerSetupView;
import org.openRealmOfStars.game.States.ResearchView;
import org.openRealmOfStars.game.States.ShipDesignView;
import org.openRealmOfStars.game.States.ShipView;
import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.game.States.StatView;
import org.openRealmOfStars.game.config.ConfigFile;
import org.openRealmOfStars.game.config.ConfigLine;
import org.openRealmOfStars.gui.mapPanel.PopupPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.scrollPanel.SpaceScrollBarUI;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.anomaly.SpaceAnomaly;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.TradeRoute;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.message.ChangeMessage;
import org.openRealmOfStars.player.message.ChangeMessageFleet;
import org.openRealmOfStars.player.message.ChangeMessagePlanet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.repository.ConfigFileRepository;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
 * Copyright (C) 2017 God Beom
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
 * Contains runnable main method which runs the Game class.
 *
 */

public class Game implements ActionListener {

  /**
   * Game Title show in various places
   */
  public static final String GAME_TITLE = "Open Realm of Stars";

  /**
   * Game version number
   */
  public static final String GAME_VERSION = "0.7.45Alpha";

  /**
   * Animation timer used for animation
   */
  private Timer animationTimer;

  /**
   * Animation timer used for animation
   */
  private Timer musicTimer;

  /**
   * Star map for the game
   */
  private StarMap starMap = null;

  /**
   * List of players
   */
  private PlayerList players;

  /**
   * Current Game state
   */
  private GameState gameState;

  /**
   * Previous Game state
   */
  private GameState previousState;

  /**
   * Planet view Panel and handling planet
   */
  private PlanetView planetView;

  /**
   * Planet bombing view Panel
   */
  private PlanetBombingView planetBombingView;

  /**
   * Fleet view Panel and handling the fleet
   */
  private FleetView fleetView;

  /**
   * Fleet trade view Panel and handling the trade
   */
  private FleetTradeView fleetTradeView;

  /**
   * Main menu for the game
   */
  private MainMenu mainMenu;

  /**
   * Galaxy Creation view
   */
  private GalaxyCreationView galaxyCreationView;

  /**
   * Player Setup view
   */
  private PlayerSetupView playerSetupView;

  /**
   * Load Game View
   */
  private LoadGameView loadGameView;

  /**
   * AI Turn view
   */
  private AITurnView aiTurnView;

  /**
   * Credits for the game
   */
  private CreditsView creditsView;

  /**
   * StarMap view for the game
   */
  private StarMapView starMapView;

  /**
   * Combat view for the game
   */
  private BattleView combatView;

  /**
   * Research view for the game
   */
  private ResearchView researchView;

  /**
   * Diplomacy view for the game
   */
  private DiplomacyView diplomacyView;

  /**
   * Ship view for the game
   */
  private ShipView shipView;

  /**
   * Stat view for the game
   */
  private StatView statView;

  /**
   * Ship design view for the game
   */
  private ShipDesignView shipDesignView;

  /**
   * Galaxy config for new game
   */
  private GalaxyConfig galaxyConfig;

  /**
   * News corporation view for the game
   */
  private NewsCorpView newsCorpView;

  /**
   * Espionage view for the game
   */
  private EspionageView espionageView;

  /**
   * History view for the game
   */
  private HistoryView historyView;

  /**
   * Options view for the game
   */
  private OptionsView optionsView;

  /**
   * Change Message Fleet or Planet
   */
  private ChangeMessage changeMessage;

  /**
   * Separate game frame to support headless running of the game class.
   */
  private JFrame gameFrame;

  /**
   * Layered Panel for view
   */
  private JLayeredPane layeredPane;

  /**
   * Song information to draw
   */
  private JLabel songText;

  /**
   * Game ConfigFile
   */
  private ConfigFile configFile;

  /**
   * Get Star map
   * @return StarMap
   */
  public StarMap getStarMap() {
    return starMap;
  }

  /**
   * Game window X size aka width
   */
  private static final int WINDOW_X_SIZE = 1024;

  /**
   * Game window Y size aka height
   */
  private static final int WINDOW_Y_SIZE = 768;

  /**
   * Y coordinate when showing music text at top of screen
   */
  private static final int MUSIC_TEXT_TOP = 25;

  /**
   * Y coordinate when showing music text at bottom of screen
   */
  private static final int MUSIC_TEXT_BOTTOM = 268;

  /**
   * Animation timer delay in milli seconds.
   */
  private static final int ANIMATION_TIMER_DELAY = 75;

  /**
   * Music timer delay in milli seconds. How often music playing is about
   * to check.
   */
  private static final int MUSIC_TIMER_DELAY = 500;

  /**
   * Animation timer delay in milli seconds for credits
   */
  private static final int ANIMATION_DELAY_CREDITS = 30;

  /**
   * Animation timer delay in milli seconds for combat
   */
  private static final int ANIMATION_DELAY_COMBAT = 60;

  /**
   * Constructor of Game class
   * @param visible Is game actually visible or not
   */
  public Game(final boolean visible) {
    readConfigFile();
    int musicVolume = configFile.getMusicVolume();
    int soundVolume = configFile.getSoundVolume();
    int resolutionWidth = configFile.getResolutionWidth();
    int resolutionHeight = configFile.getResolutionHeight();
    if (visible) {
      gameFrame = new JFrame();
      // Set look and feel match on CrossPlatform Look and feel
      try {
      UIManager
            .setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
      UIManager.put("ScrollBarUI", SpaceScrollBarUI.class.getName());
      gameFrame.setTitle(GAME_TITLE + " " + GAME_VERSION);
      gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      gameFrame.addWindowListener(new GameWindowListener());
      gameFrame.setMinimumSize(new Dimension(WINDOW_X_SIZE, WINDOW_Y_SIZE));
      gameFrame.setSize(resolutionWidth, resolutionHeight);
      gameFrame.setLocationRelativeTo(null);
      animationTimer = new Timer(ANIMATION_TIMER_DELAY, this);
      animationTimer.setActionCommand(GameCommands.COMMAND_ANIMATION_TIMER);
      animationTimer.start();
      musicTimer = new Timer(MUSIC_TIMER_DELAY, this);
      musicTimer.setActionCommand(GameCommands.COMMAND_MUSIC_TIMER);
      musicTimer.start();
      gameFrame.setUndecorated(configFile.getBorderless());
      GuiStatics.setLargerFonts(configFile.getLargerFonts());
      gameFrame.setResizable(false);
      gameFrame.setVisible(true);
      // Adjusting JFrame size. Some OS take UI component space
      // from JFrame. This happens at least with Windows 7/10 and Java8.
      int sizeX = gameFrame.getWidth() - gameFrame.getContentPane().getWidth();
      int sizeY = gameFrame.getHeight()
          - gameFrame.getContentPane().getHeight();
      if (sizeX > 0 || sizeY > 0) {
        ErrorLogger.log("Adjust frame, since OS's UI component require"
            + " their own space from JFrame.");
        ErrorLogger.log("Adjusting X: " + sizeX + " Adjusting Y: " + sizeY);
        gameFrame.setVisible(false);
        gameFrame.setSize(resolutionWidth + sizeX, resolutionHeight + sizeY);
        gameFrame.setMinimumSize(new Dimension(WINDOW_X_SIZE + sizeX,
            WINDOW_Y_SIZE + sizeY));
        gameFrame.setVisible(true);
      }
      // Add new KeyEventDispatcher
      KeyboardFocusManager kfm = KeyboardFocusManager
          .getCurrentKeyboardFocusManager();
      kfm.addKeyEventDispatcher(new GameKeyAdapter(this));
      MusicPlayer.play(MusicPlayer.MILLION_LIGHT_YEARS);
      MusicPlayer.setLoop(false);
      MusicPlayer.setVolume(musicVolume);
      SoundPlayer.setSoundVolume(soundVolume);
    }
    changeGameState(GameState.MAIN_MENU);
  }

  /**
   * Read config file from oros.cfg or generate
   * default config file.
   */
  private void readConfigFile() {
    File file = new File("oros.cfg");
    if (file.exists()) {
      try (FileInputStream fis = new FileInputStream(file)) {
        configFile = ConfigFileRepository.readConfigFile(fis);
      } catch (IOException e) {
        ErrorLogger.log("Failed reading the config file! \n"
           + e.getMessage());
      }
    }
    if (configFile == null) {
      configFile = new ConfigFile();
      configFile.add(new ConfigLine(ConfigFile.CONFIG_COMMENT));
      ConfigLine line = new ConfigLine(ConfigFile.CONFIG_MUSIC_VOLUME + "=75");
      configFile.add(line);
      line = new ConfigLine(ConfigFile.CONFIG_SOUND_VOLUME + "=75");
      configFile.add(line);
      line = new ConfigLine(ConfigFile.CONFIG_RESOLUTION + "="
          + WINDOW_X_SIZE + "x" + WINDOW_Y_SIZE);
      configFile.add(line);
    }
  }

  /**
   * Write config file to oros.cfg.
   */
  private void writeConfigFile() {
    File file = new File("oros.cfg");
    try (FileOutputStream fos = new FileOutputStream(file)) {
      ConfigFileRepository.writeConfigFile(fos, configFile);
    } catch (IOException e) {
      ErrorLogger.log("Failed reading the config file! \n"
         + e.getMessage());
    }
  }

  /**
   * Cause Fleet to make a move
   * @param info Player who owns the fleet
   * @param fleet Fleet to move
   * @param nx New coordinate x axel
   * @param ny new coordinate y axel
   */
  public void fleetMakeMove(final PlayerInfo info, final Fleet fleet,
      final int nx, final int ny) {
    // Getting fleet owner information
    FleetTileInfo[][] fleetTiles = starMap.getFleetTiles();
    FleetTileInfo fleetTile = fleetTiles[fleet.getX()][fleet.getY()];

    // And making sure that fleet owner is actually make the move
    boolean isSamePlayer = false;
    if (fleetTile != null) {
      isSamePlayer = players.getIndex(info) == fleetTile.getPlayerIndex();
    }
    final boolean isValidCoordinate = getStarMap().isValidCoordinate(nx, ny);
    final boolean isMovesLeft = fleet.getMovesLeft() > 0;
    final boolean isNotBlocked = !getStarMap().isBlocked(nx, ny);

    if (isSamePlayer && isValidCoordinate && isMovesLeft && isNotBlocked) {
      Combat combat = getStarMap().fightWithFleet(nx, ny, fleet, info);
      if (combat != null) {
        fleet.decMovesLeft();
        Coordinate combatCoord = combat.getCombatCoordinates();
        Planet planet = starMap.getPlanetByCoordinate(combatCoord.getX(),
            combatCoord.getY());
        combat.setPlanet(planet);
        int defenderIndex = starMap.getPlayerList().getIndex(
            combat.getPlayer2());
        int attackerIndex = starMap.getPlayerList().getIndex(
            combat.getPlayer1());
        if (!info.getDiplomacy().getDiplomaticRelation(defenderIndex).equals(
            Diplomacy.WAR) && !fleet.isPrivateerFleet()) {
          // Moving on towards another player's fleet is war declaration
          DiplomaticTrade trade = new DiplomaticTrade(starMap, attackerIndex,
              defenderIndex);
          trade.generateEqualTrade(NegotiationType.WAR);
          StarMapUtilities.addWarDeclatingRepuation(starMap, info);
          NewsData newsData = NewsFactory.makeWarNews(info,
              combat.getPlayer2(), planet, starMap);
          starMap.getNewsCorpData().addNews(newsData);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              planet, newsData));
          trade.doTrades();
          PlayerInfo defender = starMap.getPlayerByIndex(defenderIndex);
          String[] list = defender.getDiplomacy().activateDefensivePact(
              starMap, info);
          if (list != null) {
            starMap.getNewsCorpData().addNews(
                NewsFactory.makeDefensiveActivation(info, list));
          }
        }
        if (combat.isHumanPlayer()) {
          starMapView.setReadyToMove(false);
          changeGameState(GameState.COMBAT, combat);
        } else {
          combat.doFastCombat();
          getStarMap().getHistory().addEvent(combat.getCombatEvent());
        }
      } else {
        fleet.setPos(new Coordinate(nx, ny));
        SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(starMap,
            info, fleet);
        if (anomaly != null && starMapView != null && info.isHuman()) {
          PopupPanel popup = new PopupPanel(anomaly);
          starMapView.setPopup(popup);
        }
        if (anomaly != null && !info.isHuman() && anomaly.getCombat() != null) {
          anomaly.getCombat().doFastCombat();
          getStarMap().getHistory().addEvent(
              anomaly.getCombat().getCombatEvent());
        }
        starMap.clearFleetTiles();
        fleet.decMovesLeft();
        getStarMap().doFleetScanUpdate(info, fleet, null);
        Tile tile = getStarMap().getTile(fleet.getX(), fleet.getY());
        if (tile.getName().equals(TileNames.WORM_HOLE1)
            || tile.getName().equals(TileNames.WORM_HOLE2)) {
          Coordinate coord = getStarMap().getFreeRandomSpot();
          fleet.setPos(coord);
          getStarMap().doFleetScanUpdate(info, fleet, null);
        }
        if (starMapView != null) {
          starMapView.updatePanels();
          if (info.isHuman()) {
            getStarMap().setDrawPos(fleet.getX(), fleet.getY());
          }
          starMapView.setReadyToMove(false);
        }
      }
    }
  }

  /**
   * Update View
   * @param view about BlackPanel
   */
  private void updateDisplay(final BlackPanel view) {
    if (gameFrame != null) {
      gameFrame.getContentPane().removeAll();
      layeredPane = new JLayeredPane();
      view.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
      layeredPane.setLayer(view, JLayeredPane.DEFAULT_LAYER);
      layeredPane.add(view);
      layeredPane.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
      int y = MUSIC_TEXT_TOP;
      if (view instanceof MainMenu || view instanceof ResearchView
          || view instanceof ShipView || view instanceof GalaxyCreationView
          || view instanceof PlayerSetupView
          || view instanceof DiplomacyView) {
        y = getHeight() - MUSIC_TEXT_BOTTOM;
      }
      songText = new JLabel("Test");
      songText.setBounds(getWidth() - 374, y, 350, 150);
      songText.setFont(GuiStatics.getFontCubellanBold());
      songText.setForeground(Color.white);
      MusicFileInfo info = MusicPlayer.getNowPlaying();
      String text = "<html>Now playing: " + info.getName() + " by "
          + info.getAuthor() + "</html>";
      songText.setText(text);
      layeredPane.setLayer(songText, JLayeredPane.POPUP_LAYER);
      layeredPane.add(songText);
      if (MusicPlayer.isTextDisplayedEnough()) {
        songText.setVisible(false);
      }
      gameFrame.add(layeredPane);
      gameFrame.validate();
    }
  }

  /**
   * Set Game frame to be resizable.
   * @param resizable True for resizable frame
   */
  public void setResizable(final boolean resizable) {
    if (gameFrame != null) {
      gameFrame.setResizable(resizable);
    }
  }

  /**
   * Set new resolution for game frame
   * @param resolution Resolution as String NNNxMMM
   */
  public void setNewResolution(final String resolution) {
    String[] resolutionParts = resolution.split("x");
    int resolutionWidth = Integer.parseInt(resolutionParts[0]);
    int resolutionHeight = Integer.parseInt(resolutionParts[1]);
    if (resolutionWidth != gameFrame.getWidth()
        || resolutionHeight != gameFrame.getHeight()) {
      gameFrame.setVisible(false);
      gameFrame.setSize(resolutionWidth, resolutionHeight);
      gameFrame.setLocationRelativeTo(null);
      gameFrame.setVisible(true);
      // Adjusting JFrame size. Some OS take UI component space
      // from JFrame. This happens at least with Windows 7/10 and Java8.
      int sizeX = gameFrame.getWidth() - gameFrame.getContentPane().getWidth();
      int sizeY = gameFrame.getHeight()
          - gameFrame.getContentPane().getHeight();
      if (sizeX > 0 || sizeY > 0) {
        ErrorLogger.log("Adjust frame, since OS's UI component require"
            + " their own space from JFrame.");
        ErrorLogger.log("Adjusting X: " + sizeX + " Adjusting Y: " + sizeY);
        gameFrame.setVisible(false);
        gameFrame.setSize(resolutionWidth + sizeX, resolutionHeight + sizeY);
        gameFrame.setMinimumSize(new Dimension(WINDOW_X_SIZE + sizeX,
            WINDOW_Y_SIZE + sizeY));
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
      }
      gameFrame.repaint();
    }
  }

  /**
   * Get Current Resolution NNNxMMM
   * @return Current ConentPane resolution as string
   */
  public String getCurrentResolution() {
    if (gameFrame != null) {
      return getWidth() + "x" + getHeight();
    }
    return "1024x768";
  }
  /**
   * Get width of Game frame
   * @return Width of game frame
   */
  public int getWidth() {
    if (gameFrame != null) {
      return gameFrame.getContentPane().getWidth();
    }
    return WINDOW_X_SIZE;
  }

  /**
   * Get height of Game frame
   * @return Width of game frame
   */
  public int getHeight() {
    if (gameFrame != null) {
      return gameFrame.getContentPane().getHeight();
    }
    return WINDOW_Y_SIZE;
  }

  /**
   * Call paint for Game frame
   * @param graphics Graphics
   */
  public void paint(final Graphics graphics) {
    if (gameFrame != null) {
      gameFrame.getContentPane().paint(graphics);
    }
  }

  /**
   * Show planet view panel
   * @param planet Planet to show
   * @param interactive Show planet view as interactive if true
   * @param player player who is currently playing
   */
  public void showPlanetView(final Planet planet, final PlayerInfo player,
      final boolean interactive) {
    planetView = new PlanetView(planet, interactive, player, this);
    this.updateDisplay(planetView);
  }

  /**
   * Show news corp view
   */
  public void showNewsCorpView() {
    newsCorpView = new NewsCorpView(starMap.getNewsCorpData().getNewsList(),
        this);
    this.updateDisplay(newsCorpView);
  }

  /**
   * Show Options view
   */
  public void showOptionsView() {
    optionsView = new OptionsView(configFile, this, this);
    this.updateDisplay(optionsView);
  }

  /**
   * Show espionage view
   */
  public void showEspionageView() {
    espionageView = new EspionageView(starMap.getPlayerList(),
        starMap.getCurrentPlayerInfo(),
        starMap.getNewsCorpData().getMilitary(), this);
    this.updateDisplay(espionageView);
  }

  /**
   * Show planet bombing view panel. Also
   * declares war against player who's planet is being
   * conquered if there is now war between these two players.
   * @param planet Planet to show
   * @param fleet Fleet to show
   */
  public void showPlanetBombingView(final Planet planet, final Fleet fleet) {
    if (!starMap.getCurrentPlayerInfo().getDiplomacy().isWar(
        planet.getPlanetOwnerIndex())) {
      // No war between these two players, trying to conquer another player's
      // planet is act of war.
      DiplomaticTrade trade = new DiplomaticTrade(starMap,
          players.getCurrentPlayer(), planet.getPlanetOwnerIndex());
      trade.generateEqualTrade(NegotiationType.WAR);
      trade.doTrades();
      PlayerInfo defender = planet.getPlanetPlayerInfo();
      PlayerInfo attacker = starMap.getCurrentPlayerInfo();
      StarMapUtilities.addWarDeclatingRepuation(starMap, attacker);
      NewsData newsData = NewsFactory.makeWarNews(attacker, defender, fleet,
          starMap);
      starMap.getNewsCorpData().addNews(newsData);
      starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
          fleet, newsData));
      String[] defenseList = defender.getDiplomacy().activateDefensivePact(
          starMap, attacker);
      if (defenseList != null) {
        starMap.getNewsCorpData().addNews(
            NewsFactory.makeDefensiveActivation(attacker, defenseList));
      }
    }
    planetBombingView = new PlanetBombingView(planet, fleet,
        starMap.getCurrentPlayerInfo(), players.getCurrentPlayer(), this);
    planetBombingView.setStarMap(starMap);
    this.updateDisplay(planetBombingView);
  }

  /**
   * Show planet bombing view panel
   * @param view PlanetBombingView to show
   */
  public void showPlanetBombingView(final PlanetBombingView view) {
    planetBombingView = view;
    planetBombingView.setStarMap(starMap);
    this.updateDisplay(planetBombingView);
  }

  /**
   * Show fleet view panel
   * @param planet Planet to show
   * @param fleet Fleet to show
   * @param interactive If true then player can edit the fleet
   */
  public void showFleetView(final Planet planet, final Fleet fleet,
      final boolean interactive) {
    fleetView = new FleetView(planet, fleet,
        players.getCurrentPlayerInfo().getFleets(),
        players.getCurrentPlayerInfo(), interactive, this);
    fleetView.setStarmap(getStarMap());
    this.updateDisplay(fleetView);
  }

  /**
   * Show fleet trade view panel
   * @param planet Planet to show
   * @param fleet Fleet to show
   */
  public void showFleetTradeView(final Planet planet, final Fleet fleet) {
    fleetTradeView = new FleetTradeView(starMap,
        players.getCurrentPlayerInfo(), planet, fleet, this);
    this.updateDisplay(fleetTradeView);
  }

  /**
   * Show Star Map panels
   * @param object to show on map, Currently work only with fleet.
   */
  public void showStarMap(final Object object) {
    starMapView = new StarMapView(starMap, players, this);
    this.updateDisplay(starMapView);
    starMapView.setAutoFocus(false);
    if (object == null) {
      focusOnMessage(true);
    } else if (object instanceof Fleet) {
      Fleet fleet = (Fleet) object;
      starMapView.setShowFleet(fleet);
      starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
      starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
    }
  }

  /**
   * Show History view
   */
  public void showHistoryView() {
    historyView = new HistoryView(starMap, this);
    this.updateDisplay(historyView);
  }

  /**
   * Show Diplomacy view
   * @param dataObject Object where PlayerInfo with whom diplomacy
   * is going to be started is need to find
   */
  public void showDiplomacyView(final Object dataObject) {
    PlayerInfo info = starMap.getPlayerByIndex(1);
    Fleet fleet = null;
    Planet planet = null;
    int type = DiplomacyView.HUMAN_REGULAR;
    if (dataObject != null) {
      if (dataObject instanceof Fleet) {
        fleet = (Fleet) dataObject;
        info = starMap.getPlayerInfoByFleet(fleet);
        if (info.isHuman()) {
          type = DiplomacyView.AI_BORDER_CROSS;
          CulturePower culture = starMap.getSectorCulture(fleet.getX(),
              fleet.getY());
          if (culture.getHighestCulture() != -1) {
            info = starMap.getPlayerByIndex(culture.getHighestCulture());
            type = Diplomacy.getBorderCrossingType(info, fleet, 0);
          } else {
            // This should not happen, but it means
            // that AI started diplomacy not on his/her sector.
            type = DiplomacyView.AI_REGULAR;
          }
        } else {
          type = DiplomacyView.HUMAN_BORDER_CROSS;
          PlayerInfo human = starMap.getPlayerByIndex(0);
          int index = starMap.getPlayerList().getIndex(info);
          type = Diplomacy.getBorderCrossingType(human, fleet, index);
        }
      }
      if (dataObject instanceof FleetView) {
        FleetView view = (FleetView) dataObject;
        info = starMap.getPlayerInfoByFleet(view.getFleet());
      }
      if (dataObject instanceof PlanetView) {
        PlanetView view = (PlanetView) dataObject;
        if (view.getPlanet().getPlanetPlayerInfo() != null) {
          info = view.getPlanet().getPlanetPlayerInfo();
          planet = view.getPlanet();
        }
      }
      if (dataObject instanceof PlayerInfo) {
        info = (PlayerInfo) dataObject;
        type = DiplomacyView.AI_REGULAR;
      }
    }
    diplomacyView = new DiplomacyView(starMap.getPlayerByIndex(0), info,
        starMap, type, fleet, planet, this);
    this.updateDisplay(diplomacyView);
  }

  /**
   * Show Combat
   * @param combat Combat to show
   */
  public void showCombat(final Combat combat) {
    if (combat == null) {
      // This is fixed combat
      combatView = new BattleView(
          players.getCurrentPlayerInfo().getFleets().getByIndex(0),
          players.getCurrentPlayerInfo(),
          players.getPlayerInfoByIndex(1).getFleets().getByIndex(0),
          players.getPlayerInfoByIndex(1), starMap, this);
    } else {
      combatView = new BattleView(combat, starMap, this);
    }
    this.updateDisplay(combatView);
  }

  /**
   * Show Research panels
   * @param focusMsg which tech should have focus on show up. Can be null.
   */
  public void showResearch(final Message focusMsg) {
    String focusTech = null;
    if (focusMsg != null && focusMsg.getType() == MessageType.RESEARCH
        && focusMsg.getMatchByString() != null) {
      focusTech = focusMsg.getMatchByString();
    }
    researchView = new ResearchView(players.getCurrentPlayerInfo(),
        starMap.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH,
            players.getCurrentPlayer()),
        focusTech, starMap.getScoreVictoryTurn(), this);
    this.updateDisplay(researchView);
  }

  /**
   * Show Ship panels
   */
  public void showShipView() {
    shipView = new ShipView(players.getCurrentPlayerInfo(), this);
    this.updateDisplay(shipView);
  }

  /**
   * Show Stat panels
   */
  public void showStatView() {
    statView = new StatView(starMap, this);
    if (starMap.getTurn() == starMap.getScoreVictoryTurn()) {
      statView.setBackButtonText("Back to main menu");
    }
    this.updateDisplay(statView);
  }

  /**
   * Show Ship design panels
   * @param oldDesign to copy to new one. Can be null.
   */
  public void showShipDesignView(final ShipDesign oldDesign) {
    shipDesignView = new ShipDesignView(players.getCurrentPlayerInfo(),
        oldDesign, this);
    this.updateDisplay(shipDesignView);
  }

  /**
   * Show main menu panel
   */
  public void showMainMenu() {
    mainMenu = new MainMenu(this);
    this.updateDisplay(mainMenu);
  }

  /**
   * Show Galaxy creation panel
   */
  public void showGalaxyCreation() {
    galaxyCreationView = new GalaxyCreationView(galaxyConfig, this);
    galaxyConfig = galaxyCreationView.getConfig();
    this.updateDisplay(galaxyCreationView);
  }

  /**
   * Show Player setup panel
   */
  public void showPlayerSetup() {
    playerSetupView = new PlayerSetupView(galaxyConfig, this);
    this.updateDisplay(playerSetupView);
  }

  /**
   * Show Load Game view panel
   */
  public void showLoadGame() {
    loadGameView = new LoadGameView(this);
    this.updateDisplay(loadGameView);
  }


  /**
   * Load game from certain file name
   * @param filename File name
   * @return true if successful
   */
  public boolean loadSavedGame(final String filename) {
    GameRepository repository = new GameRepository();
    starMap = repository.loadGame(GameRepository.DEFAULT_SAVE_FOLDER,
                                  filename);
    return setLoadedGame(starMap);
  }

  /**
   * Show AI Turn view
   */
  public void showAITurnView() {
    aiTurnView = new AITurnView(this);
    this.updateDisplay(aiTurnView);
  }

  /**
   * Show credits panel
   */
  public void showCredits() {
    try {
      creditsView = new CreditsView(this, GAME_TITLE, GAME_VERSION);
    } catch (IOException e) {
      System.out.println("Could not show credits: " + e.getMessage());
      System.exit(0);
    }
    this.updateDisplay(creditsView);
  }

  /**
   * Change game state so that focus is also changed to target message
   * @param newState Game State where to change
   * @param focusMessage Focused message, can be also null
   */
  public void changeGameState(final GameState newState,
      final Message focusMessage) {
    changeGameState(newState, focusMessage, null);
  }

  /**
   * Change game state so that there is custom object given as a parameter
   * @param newState Game State where to change
   * @param dataObject Depends on which state is changed
   */
  public void changeGameState(final GameState newState,
      final Object dataObject) {
    changeGameState(newState, null, dataObject);
  }

  /**
   * Change game state so that focus is also changed to target message.
   * There is also possibility to give data object which depends on new game
   * state.
   * @param newState Game State where to change
   * @param focusMessage Focused message, can be also null
   * @param dataObject Depends on which state is changed
   */
  private void changeGameState(final GameState newState,
      final Message focusMessage, final Object dataObject) {
    if (newState != gameState) {
      previousState = gameState;
      gameState = newState;
    }
    if (animationTimer != null
        && animationTimer.getDelay() != ANIMATION_TIMER_DELAY) {
      animationTimer.setDelay(ANIMATION_TIMER_DELAY);
    }
    switch (gameState) {
    case AITURN:
      showAITurnView();
      break;
    case MAIN_MENU:
      showMainMenu();
      break;
    case GALAXY_CREATION:
      showGalaxyCreation();
      break;
    case PLAYER_SETUP:
      showPlayerSetup();
      break;
    case LOAD_GAME:
      showLoadGame();
      break;
    case OPTIONS_VIEW:
      showOptionsView();
      break;
    case NEW_GAME: {
      makeNewGame();
      break;
    }
    case NEWS_CORP_VIEW: {
      showNewsCorpView();
      break;
    }
    case ESPIONAGE_VIEW: {
      showEspionageView();
      break;
    }
    case HISTORY_VIEW: {
      showHistoryView();
      break;
    }
    case PLANETBOMBINGVIEW: {
      planetBombingView(dataObject);
      break;
    }
    case CREDITS:
      if (animationTimer != null) {
        animationTimer.setDelay(ANIMATION_DELAY_CREDITS);
      }
      showCredits();
      break;
    case STARMAP:
      showStarMap(dataObject);
      break;
    case COMBAT: {
      if (animationTimer != null) {
        animationTimer.setDelay(ANIMATION_DELAY_COMBAT);
      }
      combat(dataObject);
      break;
    }
    case RESEARCHVIEW:
      showResearch(focusMessage);
      break;
    case VIEWSHIPS:
      showShipView();
      break;
    case VIEWSTATS:
      showStatView();
      break;
    case SHIPDESIGN: {
      shipDesign();
      break;
    }
    case PLANETVIEW: {
      planetView(focusMessage);
      break;
    }
    case FLEETVIEW: {
      fleetView();
      break;
    }
    case FLEET_TRADE_VIEW: {
      fleetTradeView();
      break;
    }
    case DIPLOMACY_VIEW: {
      showDiplomacyView(dataObject);
      break;
    }
    default: {
        showMainMenu();
    }
    }
  }

  /**
   * Show fleet View
   */
  private void fleetView() {
    if (starMapView.getStarMapMouseListener()
             .getLastClickedFleet() != null) {
        Fleet fleet = starMapView.getStarMapMouseListener()
                .getLastClickedFleet();
        Planet planet = starMap.getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        boolean interactive = false;
        if (starMap.getCurrentPlayerInfo()
                == starMap.getPlayerInfoByFleet(fleet)) {
          interactive = true;
        }
        showFleetView(planet, fleet, interactive);
      }
  }

  /**
   * Show fleet Trade View
   */
  private void fleetTradeView() {
    if (starMapView.getStarMapMouseListener()
             .getLastClickedFleet() != null) {
        Fleet fleet = starMapView.getStarMapMouseListener()
                .getLastClickedFleet();
        Planet planet = starMap.getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        if (starMap.getCurrentPlayerInfo()
                == starMap.getPlayerInfoByFleet(fleet)) {
          showFleetTradeView(planet, fleet);
        }
      }
  }


  /**
   * Show planet View
   * @param focusMessage Focused message, can be also null
   */
  private void planetView(final Message focusMessage) {
   if (focusMessage != null) {
        Planet planet = starMap.getPlanetByCoordinate(focusMessage.getX(),
            focusMessage.getY());
        if (planet != null) {
          boolean interactive = false;
          if (starMap.getCurrentPlayerInfo() == planet.getPlanetPlayerInfo()) {
            interactive = true;
          }
          starMap.setCursorPos(focusMessage.getX(), focusMessage.getY());
          starMap.setDrawPos(focusMessage.getX(), focusMessage.getY());
          showPlanetView(planet, starMap.getCurrentPlayerInfo(), interactive);
        }
      } else if (starMapView.getStarMapMouseListener()
              .getLastClickedPlanet() != null) {
        boolean interactive = false;
        Planet planet = starMapView.getStarMapMouseListener()
            .getLastClickedPlanet();
        if (starMap.getCurrentPlayerInfo() == planet.getPlanetPlayerInfo()) {
          interactive = true;
        }
        showPlanetView(planet, starMap.getCurrentPlayerInfo(), interactive);
      }
  }


  /**
   * Show ship design
   */
  private void shipDesign() {
   if (shipView != null && shipView.isCopyClicked()) {
        showShipDesignView(shipView.getSelectedShip());
      } else {
        showShipDesignView(null);
      }

  }


  /**
   * Show combat
   * @param dataObject Depends on which state is changed
   */
  private void combat(final Object dataObject) {
    if (dataObject instanceof Combat) {
        showCombat((Combat) dataObject);
      } else {
        showCombat(null);
      }

  }


  /**
   * Show planetBombingView
   * @param dataObject Depends on which state is changed
   */
  private void planetBombingView(final Object dataObject) {
    boolean changed = false;
    if (dataObject instanceof FleetView) {
      FleetView view = (FleetView) dataObject;
      Planet planet = view.getPlanet();
      Fleet fleet = view.getFleet();
      if (fleet != null && planet != null
          && fleet.getX() == planet.getX()
          && fleet.getY() == planet.getY()) {
        showPlanetBombingView(planet, fleet);
        changed = true;
      }
    }
    if (dataObject instanceof PlanetBombingView) {
      showPlanetBombingView((PlanetBombingView) dataObject);
      changed = true;
    }
    if (!changed) {
      changeGameState(GameState.STARMAP);
    }

  }


  /**
   * Make new Game State
   */
  private void makeNewGame() {
    setPlayerInfo();
    starMap = new StarMap(galaxyConfig, players);
    starMap.updateStarMapOnStartGame();
    NewsCorpData corpData = starMap.getNewsCorpData();
    players.setCurrentPlayer(0);
    setNullView();
    calculateCorpData(corpData);
    changeGameState(GameState.STARMAP);

  }


  /**
   * Calculate Corporation Data
   * @param corpData various calculations
   */
  private void calculateCorpData(final NewsCorpData corpData) {
    corpData.calculateCredit(players);
    corpData.calculateCulture(starMap.getPlanetList(), players);
    corpData.calculateMilitary(players, false);
    corpData.calculatePlanets(starMap.getPlanetList());
    corpData.calculatePopulation(starMap.getPlanetList());
    corpData.calculateResearch(players);
  }


  /**
   * Set Player information when make new game
   */
  private void setPlayerInfo() {
    players = new PlayerList();
    int maxPlayers = galaxyConfig.getMaxPlayers();
    if (galaxyConfig.getSpacePiratesLevel() > 0) {
      maxPlayers++;
    }
    for (int i = 0; i < galaxyConfig.getMaxPlayers(); i++) {
      PlayerInfo info = new PlayerInfo(galaxyConfig.getRace(i),
          maxPlayers, i);
      info.setGovernment(galaxyConfig.getPlayerGovernment(i));
      info.setEmpireName(galaxyConfig.getPlayerName(i));
      if (i == 0) {
        info.setHuman(true);
      }
      players.addPlayer(info);
    }
    if (galaxyConfig.getSpacePiratesLevel() > 0) {
      PlayerInfo info = new PlayerInfo(SpaceRace.SPACE_PIRATE, maxPlayers, 8);
      info.setBoard(true);
      info.setGovernment(GovernmentType.AI);
      info.setEmpireName("Space pirates");
      players.addPlayer(info);
    }
    players.calculateInitialDiplomacyBonuses();
  }


  /**
   * Set null in view when make new game
   */
  private void setNullView() {
      starMapView = null;
      combatView = null;
      researchView = null;
      shipView = null;
      shipDesignView = null;

  }

  /**
   * Change game state and show new panel/screen
   * @param newState Game State where to change
   */
  public void changeGameState(final GameState newState) {
    changeGameState(newState, null);
  }

  /**
   * Print Tech into a markdown format
   * @param techNames Technames to print
   * @param type Tech type
   * @param level Which tech level
   * @return String of tech info in markdown format
   */
  public static String printTech(final String[] techNames,
      final TechType type, final int level) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n### " + type.toString() + " - " + level + "\n\n");
    for (int i = 0; i < techNames.length; i++) {
      Tech tech = TechFactory.createTech(type, level, techNames[i]);
      boolean noPrint = true;
      if (tech.getComponent() != null) {
        ShipComponent comp = ShipComponentFactory.createByName(
            tech.getComponent());
        sb.append(i + 1);
        sb.append(". ");
        sb.append(comp.toString());
        noPrint = false;
      }
      if (tech.getImprovement() != null) {
        Building build = BuildingFactory.createByName(tech.getImprovement());
        sb.append(i + 1);
        sb.append(". ");
        sb.append(build.getFullDescription());
        sb.append("\n");
        noPrint = false;
      }
      if (tech.getHull() != null) {
        ShipHull hull = ShipHullFactory.createByName(tech.getHull(),
            SpaceRace.HUMAN);
        sb.append(i + 1);
        sb.append(". ");
        sb.append(hull.toString());
        sb.append("\n");
        noPrint = false;
      }
      if (noPrint) {
        throw new IllegalArgumentException("Tech with not actually used: "
           + tech.getName());
      }
    }
    return sb.toString();
  }
  /**
   * Print whole research wiki page contain all information about tech
   * @return Research wiki page as a String
   */
  public static String printTechWiki() {
    StringBuilder sb = new StringBuilder();
    sb.append("## Combat technology\n\n");
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL1_NAMES,
        TechType.Combat, 1));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL2_NAMES,
        TechType.Combat, 2));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL3_NAMES,
        TechType.Combat, 3));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL4_NAMES,
        TechType.Combat, 4));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL5_NAMES,
        TechType.Combat, 5));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL6_NAMES,
        TechType.Combat, 6));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL7_NAMES,
        TechType.Combat, 7));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL8_NAMES,
        TechType.Combat, 8));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL9_NAMES,
        TechType.Combat, 9));
    sb.append(printTech(TechFactory.COMBAT_TECH_LEVEL10_NAMES,
        TechType.Combat, 10));
    sb.append("## Defense technology\n\n");
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL1_NAMES,
        TechType.Defense, 1));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL2_NAMES,
        TechType.Defense, 2));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL3_NAMES,
        TechType.Defense, 3));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL4_NAMES,
        TechType.Defense, 4));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL5_NAMES,
        TechType.Defense, 5));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL6_NAMES,
        TechType.Defense, 6));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL7_NAMES,
        TechType.Defense, 7));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL8_NAMES,
        TechType.Defense, 8));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL9_NAMES,
        TechType.Defense, 9));
    sb.append(printTech(TechFactory.DEFENSE_TECH_LEVEL10_NAMES,
        TechType.Defense, 10));
    sb.append("## Hull technology\n\n");
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL1_NAMES,
        TechType.Hulls, 1));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL2_NAMES,
        TechType.Hulls, 2));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL3_NAMES,
        TechType.Hulls, 3));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL4_NAMES,
        TechType.Hulls, 4));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL5_NAMES,
        TechType.Hulls, 5));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL6_NAMES,
        TechType.Hulls, 6));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL7_NAMES,
        TechType.Hulls, 7));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL8_NAMES,
        TechType.Hulls, 8));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL9_NAMES,
        TechType.Hulls, 9));
    sb.append(printTech(TechFactory.HULL_TECH_LEVEL10_NAMES,
        TechType.Hulls, 10));
    sb.append("## Planetary Improvement technology\n\n");
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL1_NAMES,
        TechType.Improvements, 1));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL2_NAMES,
        TechType.Improvements, 2));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL3_NAMES,
        TechType.Improvements, 3));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL4_NAMES,
        TechType.Improvements, 4));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL5_NAMES,
        TechType.Improvements, 5));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL6_NAMES,
        TechType.Improvements, 6));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL7_NAMES,
        TechType.Improvements, 7));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL8_NAMES,
        TechType.Improvements, 8));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL9_NAMES,
        TechType.Improvements, 9));
    sb.append(printTech(TechFactory.IMPROVEMENT_TECH_LEVEL10_NAMES,
        TechType.Improvements, 10));
    sb.append("## Propulsion technology\n\n");
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL1_NAMES,
        TechType.Propulsion, 1));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL2_NAMES,
        TechType.Propulsion, 2));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL3_NAMES,
        TechType.Propulsion, 3));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL4_NAMES,
        TechType.Propulsion, 4));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL5_NAMES,
        TechType.Propulsion, 5));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL6_NAMES,
        TechType.Propulsion, 6));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL7_NAMES,
        TechType.Propulsion, 7));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL8_NAMES,
        TechType.Propulsion, 8));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL9_NAMES,
        TechType.Propulsion, 9));
    sb.append(printTech(TechFactory.PROPULSION_TECH_LEVEL10_NAMES,
        TechType.Propulsion, 10));
    sb.append("## Electronics technology\n\n");
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL1_NAMES,
        TechType.Electrics, 1));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL2_NAMES,
        TechType.Electrics, 2));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL3_NAMES,
        TechType.Electrics, 3));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL4_NAMES,
        TechType.Electrics, 4));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL5_NAMES,
        TechType.Electrics, 5));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL6_NAMES,
        TechType.Electrics, 6));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL7_NAMES,
        TechType.Electrics, 7));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL8_NAMES,
        TechType.Electrics, 8));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL9_NAMES,
        TechType.Electrics, 9));
    sb.append(printTech(TechFactory.ELECTRONICS_TECH_LEVEL10_NAMES,
        TechType.Electrics, 10));
    return sb.toString();
  }

  /**
   * Main method to run the game
   * @param args from Command line
   */
  public static void main(final String[] args) {
    MusicPlayer.setMusicEnabled(true);
    SoundPlayer.setSoundEnabled(true);
    if (args.length > 0 && args[0].equals("--credits")) {
      System.out.println("# Authors of Open Reals Of Stars\n");
      System.out.println(CreditsView.MAIN_CREDITS);
    } else if (args.length > 0 && args[0].equals("--wiki-research")) {
      System.out.println(printTechWiki());
    } else {
      if (args.length > 0 && args[0].equals("--no-music")) {
        System.out.println("Disabling the music...");
        MusicPlayer.setMusicEnabled(false);
      }
      new Game(true);
    }

  }

  /**
   * Focus on active message
   * @param mapOnly focus only message which move map
   */
  public void focusOnMessage(final boolean mapOnly) {
    Message msg = players.getCurrentPlayerInfo().getMsgList().getMsg();
    if (msg.getType() == MessageType.RESEARCH && !mapOnly) {
      changeGameState(GameState.RESEARCHVIEW, msg);
    }
    if (msg.getType() == MessageType.PLANETARY) {
      starMap.setCursorPos(msg.getX(), msg.getY());
      starMap.setDrawPos(msg.getX(), msg.getY());
      Planet planet = starMap.getPlanetByCoordinate(msg.getX(), msg.getY());
      if (planet != null) {
        starMapView.setShowPlanet(planet);
        starMapView.getStarMapMouseListener().setLastClickedPlanet(planet);
      }
    }
    if (msg.getType() == MessageType.FLEET) {
      starMap.setCursorPos(msg.getX(), msg.getY());
      starMap.setDrawPos(msg.getX(), msg.getY());
      Fleet fleet = players.getCurrentPlayerInfo().getFleets()
          .getByName(msg.getMatchByString());
      if (fleet != null) {
        starMapView.setShowFleet(fleet);
        starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
        starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
      }
    }
    if ((msg.getType() == MessageType.CONSTRUCTION
        || msg.getType() == MessageType.POPULATION) && !mapOnly) {
      changeGameState(GameState.PLANETVIEW, msg);
    }
  }


  /**
   * Colonize Planet Action. This should be called when player colonized a
   * planet.
   */
  private void colonizePlanetAction() {
    Ship ship = fleetView.getFleet().getColonyShip();
    if (ship != null && fleetView.getPlanet() != null
        && fleetView.getPlanet().getPlanetPlayerInfo() == null) {
      // Make sure that ship is really colony and there is planet to
      // colonize
      fleetView.getPlanet().setPlanetOwner(players.getCurrentPlayer(),
          players.getCurrentPlayerInfo());
      if (players.getCurrentPlayerInfo().getRace() == SpaceRace.MECHIONS) {
        fleetView.getPlanet().setWorkers(Planet.PRODUCTION_WORKERS,
            ship.getColonist());
      } else {
        fleetView.getPlanet().setWorkers(Planet.PRODUCTION_FOOD,
            ship.getColonist());
      }
      // Remove the ship and show the planet view you just colonized
      fleetView.getFleet().removeShip(ship);
      ShipStat stat = starMap.getCurrentPlayerInfo()
          .getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
      }
      fleetView.getFleetList().recalculateList();
      starMapView.getStarMapMouseListener().setLastClickedFleet(null);
      starMapView.getStarMapMouseListener()
          .setLastClickedPlanet(fleetView.getPlanet());
      EventOnPlanet event = new EventOnPlanet(EventType.PLANET_COLONIZED,
          fleetView.getPlanet().getCoordinate(),
          fleetView.getPlanet().getName(), players.getCurrentPlayer());
      event.setText(players.getCurrentPlayerInfo().getEmpireName()
          + " colonized planet " + fleetView.getPlanet().getName()
          + ". ");
      starMap.getHistory().addEvent(event);
      fleetView.getPlanet().eventActivation();
      changeGameState(GameState.PLANETVIEW);
    }
  }

  /**
   * Change message focus for fleet
   * @param fleet Where to focus
   */
  private void changeMessageForFleets(final Fleet fleet) {
    changeMessage = new ChangeMessageFleet(starMap, starMapView);
    changeMessage.changeMessage(fleet);
  }

  /**
   * Change message focus for Planet
   * @param planet Where to focus
   */
  private void changeMessageForPlanet(final Planet planet) {
    changeMessage = new ChangeMessagePlanet(starMap, starMapView);
    changeMessage.changeMessage(planet);
  }

  /**
   * Handle state changes via double clicking on StarMap
   */
  private void handleDoubleClicksOnStarMap() {
    // Handle double click state changes
    if (starMapView.getStarMapMouseListener().isDoubleClicked()) {
      if (starMapView.getStarMapMouseListener()
          .getLastClickedPlanet() != null) {
        changeGameState(GameState.PLANETVIEW);
      } else if (starMapView.getStarMapMouseListener()
          .getLastClickedFleet() != null) {
        changeGameState(GameState.FLEETVIEW);
      }
    }
    if (!starMapView.getStarMapMouseListener().isDoubleClicked()
        && starMapView.getStarMapMouseListener().isMoveClicked()
        && starMapView.getStarMapMouseListener()
            .getLastClickedFleet() != null) {
      starMapView.getStarMapMouseListener().getLastClickedFleet()
          .setRoute(null);
      starMapView.getStarMapMouseListener().setMoveClicked(false);
      fleetMakeMove(players.getCurrentPlayerInfo(),
          starMapView.getStarMapMouseListener().getLastClickedFleet(),
          starMapView.getStarMapMouseListener().getMoveX(),
          starMapView.getStarMapMouseListener().getMoveY());
    }
  }

  @Override
  public void actionPerformed(final ActionEvent arg0) {
    if (arg0.getActionCommand() == GameCommands.COMMAND_MUSIC_TIMER) {
      if (songText != null) {
        if (MusicPlayer.isTextDisplayedEnough()) {
          songText.setVisible(false);
        } else {
          MusicFileInfo info = MusicPlayer.getNowPlaying();
          String text = "<html>Now playing:<br>" + info.getName() + "<br> by "
              + info.getAuthor() + "</html>";
          songText.setText(text);
          songText.setVisible(true);
        }
      }
      MusicPlayer.handleMusic(gameState);
      return;
    }
    if (gameState == GameState.FLEET_TRADE_VIEW && fleetTradeView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_START_TRADE_MISSION)
          && fleetTradeView.getTradeRoute() != null) {
        SoundPlayer.playMenuSound();
        TradeRoute route = fleetTradeView.getTradeRoute();
        Mission mission = new Mission(MissionType.TRADE_FLEET,
            MissionPhase.LOADING, route.getTradeWorld().getCoordinate());
        mission.setPlanetBuilding(route.getOriginWorld().getName());
        mission.setTargetPlanet(route.getTradeWorld().getName());
        mission.setFleetName(fleetTradeView.getFleet().getName());
        fleetTradeView.getPlayerInfo().getMissions().add(mission);
        fleetTradeView =  null;
        changeGameState(GameState.STARMAP);
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.STARMAP);
        return;
      }
    }
    if (gameState == GameState.STARMAP && starMapView != null
        && starMapView.getPopup() != null) {
      starMapView.handleActions(arg0);
      if (!arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
        SoundPlayer.playSound(SoundPlayer.MENU_DISABLED);
      }
      if (starMapView.getPopup().isDismissed()) {
        if (starMapView.getPopup().getCombat() != null) {
          starMapView.setReadyToMove(false);
          changeGameState(GameState.COMBAT,
              starMapView.getPopup().getCombat());
        }
        starMapView.setPopup(null);
        starMapView.updatePanels();
        return;
      }
    }
    if (gameState == GameState.STARMAP && starMapView != null
        && starMapView.getPopup() == null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_END_TURN)) {
        SoundPlayer.playMenuSound();
        new GameRepository()
        .saveGame(GameRepository.DEFAULT_SAVE_FOLDER, "autosave.save", starMap);
        changeGameState(GameState.AITURN);
      } else if (arg0.getActionCommand()
          .equals(GameCommands.COMMAND_FOCUS_MSG)) {
        SoundPlayer.playMenuSound();
        focusOnMessage(false);
      } else if (arg0.getActionCommand()
          .equals(GameCommands.COMMAND_PREV_TARGET)) {
        if (starMapView.getStarMapMouseListener()
            .getLastClickedFleet() != null) {
          Fleet fleet = players.getCurrentPlayerInfo().getFleets().getPrev();
          SoundPlayer.playMenuSound();
          changeMessageForFleets(fleet);
        } else if (starMapView.getStarMapMouseListener().getLastClickedPlanet()
            != null) {
          Planet planet = starMap.getNextPlanetForPlayer(starMap
              .getCurrentPlayerInfo(), starMapView.getStarMapMouseListener()
              .getLastClickedPlanet(), false);
          SoundPlayer.playMenuSound();
          changeMessageForPlanet(planet);
        }
      } else if (arg0.getActionCommand().equalsIgnoreCase(
          GameCommands.COMMAND_TRADE_FLEET)
          && starMapView.getStarMapMouseListener().getLastClickedFleet() != null
          && starMap.getPlayerInfoByFleet(
              starMapView.getStarMapMouseListener().getLastClickedFleet())
              == players.getCurrentPlayerInfo()) {
        changeGameState(GameState.FLEET_TRADE_VIEW);
        SoundPlayer.playMenuSound();
      } else if (arg0.getActionCommand()
          .equals(GameCommands.COMMAND_SPY)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.ESPIONAGE_VIEW);
      } else if (arg0.getActionCommand()
          .equals(GameCommands.COMMAND_NEXT_TARGET)) {
        if (starMapView.getStarMapMouseListener()
            .getLastClickedFleet() != null) {
          Fleet fleet = players.getCurrentPlayerInfo().getFleets().getNext();
          SoundPlayer.playMenuSound();
          changeMessageForFleets(fleet);
        } else if (starMapView.getStarMapMouseListener().getLastClickedPlanet()
            != null) {
          Planet planet = starMap.getNextPlanetForPlayer(starMap
              .getCurrentPlayerInfo(), starMapView.getStarMapMouseListener()
              .getLastClickedPlanet(), true);
          SoundPlayer.playMenuSound();
          changeMessageForPlanet(planet);
        }
      } else {
        if (arg0.getActionCommand()
            .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
          handleDoubleClicksOnStarMap();
        }
        if (arg0.getActionCommand()
            .equalsIgnoreCase(GameCommands.COMMAND_VIEW_PLANET)
            && starMapView.getStarMapMouseListener()
                .getLastClickedPlanet() != null) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.PLANETVIEW);
        }
        if (arg0.getActionCommand()
            .equalsIgnoreCase(GameCommands.COMMAND_VIEW_FLEET)
            && starMapView.getStarMapMouseListener()
                .getLastClickedFleet() != null) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.FLEETVIEW);
        }
        if (arg0.getActionCommand()
            .equalsIgnoreCase(GameCommands.COMMAND_VIEW_RESEARCH)) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.RESEARCHVIEW);
        }
        if (arg0.getActionCommand().equalsIgnoreCase(
            GameCommands.COMMAND_SHOW_HISTORY)) {
          // Debugging purposes
          SoundPlayer.playMenuSound();
          changeGameState(GameState.HISTORY_VIEW);
        }
        if (arg0.getActionCommand().equalsIgnoreCase(
            GameCommands.COMMAND_SHIPS)) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.VIEWSHIPS);
        }
        if (arg0.getActionCommand().equalsIgnoreCase(
            GameCommands.COMMAND_VIEW_STATS)) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.VIEWSTATS);
        }
        if (arg0.getActionCommand().equalsIgnoreCase(
            GameCommands.COMMAND_NEWS)) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.NEWS_CORP_VIEW);
        }
        if (arg0.getActionCommand().equalsIgnoreCase(
            GameCommands.COMMAND_BATTLE)) {
          // Debugging purposes
          SoundPlayer.playMenuSound();
          changeGameState(GameState.COMBAT);
        }
        starMapView.handleActions(arg0);
        if (starMapView.isAutoFocus()
            && arg0.getActionCommand().equals(GameCommands.COMMAND_END_TURN)) {
          SoundPlayer.playMenuSound();
          starMapView.setAutoFocus(false);
          focusOnMessage(true);
        }
      }
    }
    if (gameState == GameState.COMBAT && combatView != null) {
      if (combatView.isCombatEnded() && arg0.getActionCommand()
          .equals(GameCommands.COMMAND_END_BATTLE_ROUND)) {
        SoundPlayer.playMenuSound();
        MusicPlayer.playGameMusic();
        Combat combat = combatView.getCombat();
        getStarMap().getHistory().addEvent(combat.getCombatEvent());
        if (previousState == GameState.AITURN) {
          changeGameState(previousState);
          return;
        }
        changeGameState(GameState.STARMAP);
        if (combat.getWinnerFleet() != null) {
          getStarMap().doFleetScanUpdate(combat.getWinner(),
              combat.getWinnerFleet(), null);
        }
        return;
      }
      combatView.handleActions(arg0);
    }
    if (gameState == GameState.HISTORY_VIEW && historyView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_DONE_HISTORY)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.VIEWSTATS);
        return;
      }
      historyView.handleAction(arg0);
    }
    if (gameState == GameState.PLANETBOMBINGVIEW && planetBombingView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
        SoundPlayer.playMenuSound();
        if (previousState == GameState.AITURN) {
          changeGameState(previousState);
          return;
        }
        changeGameState(GameState.STARMAP);
        return;
      }
      planetBombingView.handleAction(arg0);
    }
    if (gameState == GameState.AITURN && aiTurnView != null) {
      aiTurnView.handleActions(arg0);
    }
    if (gameState == GameState.CREDITS) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
        creditsView.updateTextArea();
      }
      if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_OK)) {
        creditsView = null;
        SoundPlayer.playMenuSound();
        changeGameState(GameState.MAIN_MENU);
      }
      return;
    }
    if (gameState == GameState.RESEARCHVIEW && researchView != null) {
      // Handle Research View
      researchView.handleAction(arg0);
    }
    if (gameState == GameState.VIEWSHIPS && shipView != null) {
      if (arg0.getActionCommand().equalsIgnoreCase(
          GameCommands.COMMAND_DELETE_SHIP)) {
        ShipStat stat = shipView.getSelectedStat();
        PlayerInfo builder = shipView.getPlayerInfo();
        if (!starMap.isShipStatBeingBuilt(stat, builder)) {
          builder.removeShipStat(stat);
          SoundPlayer.playMenuSound();
          shipView.updateList();
        }
      } else if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_COPY_SHIP)) {
        SoundPlayer.playMenuSound();
        shipView.setCopyClicked(true);
        changeGameState(GameState.SHIPDESIGN);
      } else if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_SHIPDESIGN)) {
        shipView.setCopyClicked(false);
        SoundPlayer.playMenuSound();
        changeGameState(GameState.SHIPDESIGN);
      } else {
        // Handle View Ship
        shipView.handleAction(arg0);
      }
    }
    if (gameState == GameState.DIPLOMACY_VIEW && diplomacyView != null) {
      // Handle diplomacy view
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
        diplomacyView.updateMeetingNumbers();
        if (!diplomacyView.didTradeHappen()) {
          // Human and AI player had nothing to trade, so
          // adding nothing to trade bonus
          diplomacyView.addNothingToTrade();
        }
        if (previousState == GameState.AITURN) {
          changeGameState(previousState);
          return;
        }
        changeGameState(GameState.STARMAP);
        return;
      }
      diplomacyView.handleAction(arg0);
    }
    if (gameState == GameState.SHIPDESIGN && shipDesignView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_SHIPDESIGN_DONE)
          && shipDesignView != null && shipDesignView.isDesignOK()) {
        SoundPlayer.playMenuSound();
        shipDesignView.keepDesign();
        changeGameState(GameState.VIEWSHIPS);
      } else {
        // Ship Design View
        shipDesignView.handleAction(arg0);
      }
    }
    if (gameState == GameState.ESPIONAGE_VIEW && espionageView != null) {
      // Espionage  View
      espionageView.handleAction(arg0);
    }
    if (gameState == GameState.NEWS_CORP_VIEW && newsCorpView != null) {
      // News Corp view
      newsCorpView.handleAction(arg0);
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
        if (starMap.isGameEnded()) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.HISTORY_VIEW);
          return;
        }
        SoundPlayer.playMenuSound();
        changeGameState(GameState.STARMAP);
      }
    }
    if (gameState == GameState.VIEWSTATS && statView != null) {
      // Stat View
      statView.handleAction(arg0);
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
        if (starMap.isGameEnded()) {
          SoundPlayer.playMenuSound();
          changeGameState(GameState.MAIN_MENU);
          return;
        }
        SoundPlayer.playMenuSound();
        changeGameState(GameState.STARMAP);
      }
    }
    if (gameState == GameState.PLANETVIEW && planetView != null) {
      // Planet view
      if (arg0.getActionCommand().equals(
          GameCommands.COMMAND_HAIL_FLEET_PLANET)) {
        changeGameState(GameState.DIPLOMACY_VIEW, planetView);
        SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
      }
      planetView.handleAction(arg0);
    }
    if (gameState == GameState.FLEETVIEW && fleetView != null) {
      // Fleet view
      if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIZE)) {
        SoundPlayer.playMenuSound();
        colonizePlanetAction();
      }
      if (arg0.getActionCommand().equals(GameCommands.COMMAND_CONQUEST)) {
        changeGameState(GameState.PLANETBOMBINGVIEW, fleetView);
        SoundPlayer.playMenuSound();
      }
      if (arg0.getActionCommand().equals(
          GameCommands.COMMAND_HAIL_FLEET_PLANET)) {
        Fleet fleet = fleetView.getFleet();
        CulturePower power = starMap.getSectorCulture(fleet.getX(),
            fleet.getY());
        SoundPlayer.playSound(SoundPlayer.RADIO_CALL);
        if (power.getHighestCulture() == starMap.getPlayerList()
            .getCurrentPlayer()) {
          changeGameState(GameState.DIPLOMACY_VIEW, fleet);
        } else {
          changeGameState(GameState.DIPLOMACY_VIEW, fleetView);
        }
      }
      fleetView.handleAction(arg0);
    }
    if (gameState == GameState.GALAXY_CREATION && galaxyCreationView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_CANCEL)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.MAIN_MENU);
      } else if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_NEXT)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.PLAYER_SETUP);
      } else {
        galaxyCreationView.handleActions(arg0);
      }
    } else if (gameState == GameState.PLAYER_SETUP && playerSetupView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_CANCEL)) {
        SoundPlayer.playMenuSound();
        playerSetupView.getNamesToConfig();
        changeGameState(GameState.GALAXY_CREATION);
      } else if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_NEXT)) {
        SoundPlayer.playMenuSound();
        playerSetupView.getNamesToConfig();
        changeGameState(GameState.NEW_GAME);
      } else {
        playerSetupView.handleActions(arg0);
      }
    }
    if (gameState == GameState.LOAD_GAME && loadGameView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_CANCEL)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.MAIN_MENU);
      } else if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_NEXT)
          && loadGameView.getSelectedSaveFile() != null
          && loadSavedGame(loadGameView.getSelectedSaveFile())) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.STARMAP);
      }
    }
    if (gameState == GameState.OPTIONS_VIEW && optionsView != null) {
      // Options
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_OK)) {
        SoundPlayer.playMenuSound();
        if (!optionsView.getResolution().equals(getCurrentResolution())) {
          setNewResolution(optionsView.getResolution());
        }
        if (gameFrame.isResizable()) {
          setResizable(false);
          setNewResolution(gameFrame.getWidth() + "x" + gameFrame.getHeight());
        }
        configFile.setMusicVolume(optionsView.getMusicVolume());
        configFile.setSoundVolume(optionsView.getSoundVolume());
        configFile.setBorderless(optionsView.getBorderless());
        configFile.setLargerFonts(optionsView.getLargerFonts());
        configFile.setResolution(gameFrame.getWidth(), gameFrame.getHeight());
        GuiStatics.setLargerFonts(configFile.getLargerFonts());
        writeConfigFile();
        changeGameState(GameState.MAIN_MENU);
        return;
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_APPLY)) {
        SoundPlayer.playMenuSound();
        if (!optionsView.getResolution().equals("Custom")) {
          setNewResolution(optionsView.getResolution());
        }
        configFile.setMusicVolume(optionsView.getMusicVolume());
        configFile.setSoundVolume(optionsView.getSoundVolume());
        configFile.setBorderless(optionsView.getBorderless());
        configFile.setLargerFonts(optionsView.getLargerFonts());
        configFile.setResolution(gameFrame.getWidth(), gameFrame.getHeight());
        GuiStatics.setLargerFonts(configFile.getLargerFonts());
        return;
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_CANCEL)) {
        MusicPlayer.setVolume(configFile.getMusicVolume());
        SoundPlayer.setSoundVolume(configFile.getSoundVolume());
        setNewResolution(configFile.getResolutionWidth() + "x"
            + configFile.getResolutionHeight());
        SoundPlayer.playMenuSound();
        changeGameState(GameState.MAIN_MENU);
        if (gameFrame.isResizable()) {
          setResizable(false);
        }
        return;
      }
      optionsView.handleAction(arg0);
    }
    if (gameState == GameState.MAIN_MENU) {
      // Main menu
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_CONTINUE_GAME)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.LOAD_GAME);
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_NEW_GAME)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.GALAXY_CREATION);
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_CREDITS)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.CREDITS);
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_OPTIONS_VIEW)) {
        SoundPlayer.playMenuSound();
        changeGameState(GameState.OPTIONS_VIEW);
      }
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_QUIT_GAME)) {
        SoundPlayer.playMenuSound();
        configFile.setMusicVolume(MusicPlayer.getVolume());
        configFile.setSoundVolume(SoundPlayer.getSoundVolume());
        configFile.setResolution(gameFrame.getWidth(), gameFrame.getHeight());
        writeConfigFile();
        System.exit(0);
      }
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
      // This is default action in many view so there fore this
      // very last in commands
      SoundPlayer.playMenuSound();
      if (gameState == GameState.PLANETVIEW) {
        planetView = null;
      }
      if (gameState == GameState.RESEARCHVIEW) {
        researchView = null;
      }
      if (gameState == GameState.FLEETVIEW) {
        Fleet fleet = fleetView.getFleet();
        fleetView = null;
        changeGameState(GameState.STARMAP, fleet);
      } else {
        changeGameState(GameState.STARMAP);
      }
    }

  }

  /**
   * Get the list of players
   * @return the list of players
   */
  public PlayerList getPlayers() {
    return players;
  }

  /**
   * Get the current Game state
   * @return the current Game state
   */
  public GameState getGameState() {
    return gameState;
  }

  /**
   * Get the StarMap view for the game
   * @return the StarMap view for the game
   */
  public StarMapView getStarMapView() {
    return starMapView;
  }

  /**
   * Set the StarMap view for the game.
   * @param view StarMapView to set
   */
  public void setStarMapView(final StarMapView view) {
    starMapView = view;
  }

  /**
   * Set loaded starmap into game
   * @param map Starmap to set game
   * @return true if succesful false otherwise
   */
  public boolean setLoadedGame(final StarMap map) {
    if (map != null) {
      starMap = map;
      players = starMap.getPlayerList();
      starMap.updateStarMapOnLoadGame();
      return true;
    }
    return false;
  }
}
