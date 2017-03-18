package org.openRealmOfStars.game;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.States.AITurnView;
import org.openRealmOfStars.game.States.BattleView;
import org.openRealmOfStars.game.States.CreditsView;
import org.openRealmOfStars.game.States.FleetView;
import org.openRealmOfStars.game.States.GalaxyCreationView;
import org.openRealmOfStars.game.States.LoadGameView;
import org.openRealmOfStars.game.States.MainMenu;
import org.openRealmOfStars.game.States.PlanetBombingView;
import org.openRealmOfStars.game.States.PlanetView;
import org.openRealmOfStars.game.States.PlayerSetupView;
import org.openRealmOfStars.game.States.ResearchView;
import org.openRealmOfStars.game.States.ShipDesignView;
import org.openRealmOfStars.game.States.ShipView;
import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.game.States.StatView;
import org.openRealmOfStars.gui.scrollPanel.SpaceScrollBarUI;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.GalaxyConfig;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017  Tuomo Untinen
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

public class Game extends JFrame implements ActionListener {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Game Title show in various places
   */
  public static final String GAME_TITLE = "Open Realm of Stars";

  /**
   * Game version number
   */
  public static final String GAME_VERSION = "0.0.5Alpha";

  /**
   * Animation timer used for animation
   */
  private Timer animationTimer;

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
   * Animation timer delay in milli seconds.
   */

  private static final int ANIMATION_TIMER_DELAY = 75;

  /**
   * Constructor of Game class
   */
  public Game() {
    // Set look and feel match on CrossPlatform Look and feel
    try {
      UIManager
          .setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    UIManager.put("ScrollBarUI", SpaceScrollBarUI.class.getName());
    setTitle(GAME_TITLE + " " + GAME_VERSION);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new GameWindowListener());
    setSize(WINDOW_X_SIZE, WINDOW_Y_SIZE);
    setLocationRelativeTo(null);
    animationTimer = new Timer(ANIMATION_TIMER_DELAY, this);
    animationTimer.setActionCommand(GameCommands.COMMAND_ANIMATION_TIMER);
    animationTimer.start();

    changeGameState(GameState.MAIN_MENU);

    // Add new KeyEventDispatcher
    KeyboardFocusManager kfm = KeyboardFocusManager
        .getCurrentKeyboardFocusManager();
    kfm.addKeyEventDispatcher(new GameKeyAdapter(this));
    setResizable(false);

    this.setVisible(true);

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
    int index = players.getIndex(info);
    // And making sure that fleet owner is actually make the move
    if (index == fleetTile.getPlayerIndex()
        && getStarMap().isValidCoordinate(nx, ny) && fleet.getMovesLeft() > 0
        && !getStarMap().isBlocked(nx, ny)) {
      Combat combat = getStarMap().fightWithFleet(nx, ny, fleet, info);
      if (combat != null) {
        fleet.decMovesLeft();
        starMapView.setReadyToMove(false);
        changeGameState(GameState.COMBAT, combat);
      } else {
        fleet.setPos(new Coordinate(nx, ny));
        starMap.clearFleetTiles();
        fleet.decMovesLeft();
        getStarMap().doFleetScanUpdate(info, fleet, null);
        starMapView.updatePanels();
        if (info.isHuman()) {
          getStarMap().setDrawPos(fleet.getX(), fleet.getY());
        }
        starMapView.setReadyToMove(false);
      }
    }
  }

  /**
   * Show planet view panel
   * @param planet Planet to show
   * @param interactive Show planet view as interactive if true
   */
  public void showPlanetView(final Planet planet, final boolean interactive) {
    planetView = new PlanetView(planet, interactive, this);
    this.getContentPane().removeAll();
    this.add(planetView);
    this.validate();
  }

  /**
   * Show planet bombing view panel
   * @param planet Planet to show
   * @param fleet Fleet to show
   */
  public void showPlanetBombingView(final Planet planet, final Fleet fleet) {
    planetBombingView = new PlanetBombingView(planet, fleet,
        starMap.getCurrentPlayerInfo(), players.getCurrentPlayer(), this);
    this.getContentPane().removeAll();
    this.add(planetBombingView);
    this.validate();
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
    this.getContentPane().removeAll();
    this.add(fleetView);
    this.validate();
  }

  /**
   * Show Star Map panels
   * @param object to show on map, Currently work only with fleet.
   */
  public void showStarMap(final Object object) {
    starMapView = new StarMapView(starMap, players, this);
    this.getContentPane().removeAll();
    this.add(starMapView);
    this.validate();
    starMapView.setAutoFocus(false);
    if (object == null) {
      focusOnMessage(true);
    } else if (object instanceof Fleet) {
      Fleet fleet = (Fleet) object;
      if (fleet != null) {
        starMapView.setShowFleet(fleet);
        starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
        starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
      }
    }
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
    this.getContentPane().removeAll();
    this.add(combatView);
    this.validate();
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
        focusTech, this);
    this.getContentPane().removeAll();
    this.add(researchView);
    this.validate();
  }

  /**
   * Show Ship panels
   */
  public void showShipView() {
    shipView = new ShipView(players.getCurrentPlayerInfo(), this);
    this.getContentPane().removeAll();
    this.add(shipView);
    this.validate();
  }

  /**
   * Show Stat panels
   */
  public void showStatView() {
    statView = new StatView(starMap, this);
    this.getContentPane().removeAll();
    this.add(statView);
    this.validate();
  }

  /**
   * Show Ship design panels
   * @param oldDesign to copy to new one. Can be null.
   */
  public void showShipDesignView(final ShipDesign oldDesign) {
    shipDesignView = new ShipDesignView(players.getCurrentPlayerInfo(),
        oldDesign, this);
    this.getContentPane().removeAll();
    this.add(shipDesignView);
    this.validate();
  }

  /**
   * Show main menu panel
   */
  public void showMainMenu() {
    mainMenu = new MainMenu(this);
    this.getContentPane().removeAll();
    this.add(mainMenu);
    this.validate();
  }

  /**
   * Show Galaxy creation panel
   */
  public void showGalaxyCreation() {
    galaxyCreationView = new GalaxyCreationView(galaxyConfig, this);
    galaxyConfig = galaxyCreationView.getConfig();
    this.getContentPane().removeAll();
    this.add(galaxyCreationView);
    this.validate();
  }

  /**
   * Show Player setup panel
   */
  public void showPlayerSetup() {
    playerSetupView = new PlayerSetupView(galaxyConfig, this);
    this.getContentPane().removeAll();
    this.add(playerSetupView);
    this.validate();
  }

  /**
   * Show Load Game view panel
   */
  public void showLoadGame() {
    loadGameView = new LoadGameView(this);
    this.getContentPane().removeAll();
    this.add(loadGameView);
    this.validate();
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
    if (starMap != null) {
      players = starMap.getPlayerList();
      starMap.updateStarMapOnLoadGame();
      return true;
    }
    return false;
  }

  /**
   * Show AI Turn view
   */
  public void showAITurnView() {
    aiTurnView = new AITurnView(this);
    this.getContentPane().removeAll();
    this.add(aiTurnView);
    this.validate();
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
    this.getContentPane().removeAll();
    this.add(creditsView);
    this.validate();
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
    gameState = newState;
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
    case NEW_GAME: {
      players = new PlayerList();
      for (int i = 0; i < galaxyConfig.getMaxPlayers(); i++) {
        PlayerInfo info = new PlayerInfo(galaxyConfig.getRace(i));
        info.setEmpireName(galaxyConfig.getPlayerName(i));
        if (i == 0) {
          info.setHuman(true);
        }
        players.addPlayer(info);
      }
      starMap = new StarMap(galaxyConfig, players);
      starMap.updateStarMapOnStartGame();
      players.setCurrentPlayer(0);
      starMapView = null;
      combatView = null;
      researchView = null;
      shipView = null;
      shipDesignView = null;
      starMap.getNewsCorpData().calculateCredit(players);
      starMap.getNewsCorpData().calculateCulture(starMap.getPlanetList(),
          players);
      starMap.getNewsCorpData().calculateMilitary(players);
      starMap.getNewsCorpData().calculatePlanets(starMap.getPlanetList());
      starMap.getNewsCorpData().calculatePopulation(starMap.getPlanetList());
      starMap.getNewsCorpData().calculateResearch(players);
      changeGameState(GameState.STARMAP);
      break;
    }
    case PLANETBOMBINGVIEW: {
      boolean changed = false;
      if (dataObject instanceof Planet) {
        Planet planet = (Planet) dataObject;
        Fleet fleet = starMap.getFleetByCoordinate(planet.getX(),
            planet.getY());
        if (fleet != null) {
          showPlanetBombingView(planet, fleet);
          changed = true;
        }
      }
      if (!changed) {
        changeGameState(GameState.STARMAP);
      }
      break;
    }
    case CREDITS:
      showCredits();
      break;
    case STARMAP:
      showStarMap(dataObject);
      break;
    case COMBAT: {
      if (dataObject instanceof Combat) {
        showCombat((Combat) dataObject);
      } else {
        showCombat(null);
      }
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
      if (shipView != null && shipView.isCopyClicked()) {
        showShipDesignView(shipView.getSelectedShip());
      } else {
        showShipDesignView(null);
      }
      break;
    }
    case PLANETVIEW: {
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
          showPlanetView(planet, interactive);
        }
      } else if (starMapView.getStarMapMouseListener()
          .getLastClickedPlanet() != null) {
        boolean interactive = false;
        Planet planet = starMapView.getStarMapMouseListener()
            .getLastClickedPlanet();
        if (starMap.getCurrentPlayerInfo() == planet.getPlanetPlayerInfo()) {
          interactive = true;
        }
        showPlanetView(planet, interactive);
      }
      break;
    }
    case FLEETVIEW: {
      if (starMapView.getStarMapMouseListener().getLastClickedFleet() != null) {
        Fleet fleet = starMapView.getStarMapMouseListener()
            .getLastClickedFleet();
        Planet planet = starMap.getPlanetByCoordinate(fleet.getX(),
            fleet.getY());
        boolean interactive = false;
        if (starMap.getCurrentPlayerInfo() == starMap
            .getPlayerInfoByFleet(fleet)) {
          interactive = true;
        }
        showFleetView(planet, fleet, interactive);
      }
      break;
    }
    default: {
        showMainMenu();
    }
    }
  }

  /**
   * Change game state and show new panel/screen
   * @param newState Game State where to change
   */
  public void changeGameState(final GameState newState) {
    changeGameState(newState, null);
  }

  /**
   * Main method to run the game
   * @param args from Command line
   */
  public static void main(final String[] args) {
    new Game();

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
      stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
      fleetView.getFleetList().recalculateList();
      starMapView.getStarMapMouseListener().setLastClickedFleet(null);
      starMapView.getStarMapMouseListener()
          .setLastClickedPlanet(fleetView.getPlanet());
      changeGameState(GameState.PLANETVIEW);
    }
  }

  /**
   * Change message focus for fleet
   * @param fleet Where to focus
   */
  private void changeMessageForFleets(final Fleet fleet) {
    if (fleet != null) {
      starMap.setCursorPos(fleet.getX(), fleet.getY());
      starMap.setDrawPos(fleet.getX(), fleet.getY());
      starMapView.setShowFleet(fleet);
      starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
      starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
    }
  }

  /**
   * Change message focus for Planet
   * @param planet Where to focus
   */
  private void changeMessageForPlanet(final Planet planet) {
    if (planet != null) {
      starMap.setCursorPos(planet.getX(), planet.getY());
      starMap.setDrawPos(planet.getX(), planet.getY());
      starMapView.setShowPlanet(planet);
      starMapView.getStarMapMouseListener().setLastClickedFleet(null);
      starMapView.getStarMapMouseListener().setLastClickedPlanet(planet);
    }
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
    if (gameState == GameState.STARMAP && starMapView != null) {
      if (arg0.getActionCommand()
          .equalsIgnoreCase(GameCommands.COMMAND_END_TURN)) {
        SoundPlayer.playMenuSound();
        new GameRepository().saveGame(GameRepository.DEFAULT_SAVE_FOLDER,
                                      "autosave.save", starMap);
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
        changeGameState(GameState.STARMAP);
      } else {
        combatView.handleActions(arg0);
      }
    }
    if (gameState == GameState.PLANETBOMBINGVIEW && planetBombingView != null) {
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
        .equalsIgnoreCase(GameCommands.COMMAND_VIEW_STARMAP)) {
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
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_VIEW_RESEARCH)) {
      SoundPlayer.playMenuSound();
      changeGameState(GameState.RESEARCHVIEW);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_SHIPS)) {
      SoundPlayer.playMenuSound();
      changeGameState(GameState.VIEWSHIPS);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_STATS)) {
      SoundPlayer.playMenuSound();
      changeGameState(GameState.VIEWSTATS);
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_SHIPDESIGN)) {
      shipView.setCopyClicked(false);
      SoundPlayer.playMenuSound();
      changeGameState(GameState.SHIPDESIGN);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_BATTLE)) {
      SoundPlayer.playMenuSound();
      // changeGameState(GameState.COMBAT);
      changeGameState(GameState.PLANETBOMBINGVIEW,
          starMap.getPlanetList().get(0));
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_SHIPDESIGN_DONE)
        && shipDesignView != null && shipDesignView.isDesignOK()) {
      SoundPlayer.playMenuSound();
      shipDesignView.keepDesign();
      changeGameState(GameState.VIEWSHIPS);
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_COPY_SHIP)) {
      SoundPlayer.playMenuSound();
      shipView.setCopyClicked(true);
      changeGameState(GameState.SHIPDESIGN);
    }
    if (gameState == GameState.RESEARCHVIEW && researchView != null) {
      // Research View
      researchView.handleAction(arg0);
    }
    if (gameState == GameState.VIEWSHIPS && shipView != null) {
      // View Ship
      shipView.handleAction(arg0);
    }
    if (gameState == GameState.VIEWSHIPS && shipView != null) {
      // View Ship
      shipView.handleAction(arg0);
    }
    if (gameState == GameState.SHIPDESIGN && shipDesignView != null) {
      // Ship Design View
      shipDesignView.handleAction(arg0);
    }
    if (gameState == GameState.VIEWSTATS && statView != null) {
      // Stat View
      statView.handleAction(arg0);
    }
    if (gameState == GameState.PLANETVIEW && planetView != null) {
      // Planet view
      planetView.handleAction(arg0);
    }
    if (gameState == GameState.FLEETVIEW && fleetView != null) {
      // Fleet view
      if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIZE)) {
        SoundPlayer.playMenuSound();
        colonizePlanetAction();
      }
      if (arg0.getActionCommand().equals(GameCommands.COMMAND_CONQUEST)) {
        changeGameState(GameState.PLANETBOMBINGVIEW, fleetView.getPlanet());
        SoundPlayer.playMenuSound();
      }
      fleetView.handleAction(arg0);
    }
    if (gameState == GameState.GALAXY_CREATION) {
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
    } else if (gameState == GameState.PLAYER_SETUP) {
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
          .equalsIgnoreCase(GameCommands.COMMAND_QUIT_GAME)) {
        SoundPlayer.playMenuSound();
        System.exit(0);
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
}
