package org.openRealmOfStars.game;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.openRealmOfStars.game.States.CreditsView;
import org.openRealmOfStars.game.States.FleetView;
import org.openRealmOfStars.game.States.MainMenu;
import org.openRealmOfStars.game.States.PlanetView;
import org.openRealmOfStars.game.States.ResearchView;
import org.openRealmOfStars.game.States.ShipDesignView;
import org.openRealmOfStars.game.States.ShipView;
import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.gui.scrollPanel.SpaceScrollBarUI;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

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
  public static final String GAME_VERSION = "0.0.1Alpha";

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
  public PlayerList players;
  
  
  
  /**
   * Current Game state
   */
  public GameState gameState;
  
  
  /**
   * Planet view Panel and handling planet
   */
  public PlanetView planetView;

  /**
   * Fleet view Panel and handling the fleet
   */
  public FleetView fleetView;

  /**
   * Main menu for the game
   */
  public MainMenu mainMenu;

  /**
   * Credits for the game
   */
  public CreditsView creditsView;

  /**
   * StarMap view for the game
   */
  public StarMapView starMapView;
  
  /**
   * Research view for the game
   */
  public ResearchView researchView;

  /**
   * Ship view for the game
   */
  public ShipView shipView;

  /**
   * Ship design view for the game
   */
  public ShipDesignView shipDesignView;
  
  /**
   * Get Star map
   * @return StarMap
   */
  public StarMap getStarMap() {
    return starMap;
  }

  /**
   * Contructor of Game class
   */
  public Game() {
    // Set look and feel match on CrossPlatform Look and feel
    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
              e.printStackTrace();
    }
    UIManager.put("ScrollBarUI", SpaceScrollBarUI.class.getName());
    setTitle(GAME_TITLE+" "+GAME_VERSION);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);         
    addWindowListener(new GameWindowListener());
    setSize(1024, 768);
    setLocationRelativeTo(null)    ;
    animationTimer = new Timer(75,this);
    animationTimer.setActionCommand(GameCommands.COMMAND_ANIMATION_TIMER);
    animationTimer.start();

    changeGameState(GameState.MAIN_MENU);

    // Add new KeyEventDispatcher
    KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    kfm.addKeyEventDispatcher(new GameKeyAdapter(this));
    setResizable(false);

    this.setVisible(true);
    

  }
  
  /**
   * Show planet view panel
   * @param planet Planet to show
   */
  public void showPlanetView(Planet planet) {
    planetView = new PlanetView(planet, this);
    this.getContentPane().removeAll();
    this.add(planetView);
    this.validate();
  }

  /**
   * Show fleet view panel
   * @param planet Planet to show
   */
  public void showFleetView(Planet planet,Fleet fleet) {
    fleetView = new FleetView(planet,fleet,
        players.getCurrentPlayerInfo().Fleets(),players.getCurrentPlayerInfo(), this);
    this.getContentPane().removeAll();
    this.add(fleetView);
    this.validate();
  }

  /**
   * Show Star Map panels
   */
  public void showStarMap() {
    starMapView = new StarMapView(starMap, players, this);
    this.getContentPane().removeAll();
    this.add(starMapView);
    this.validate();
  }

  /**
   * Show Research panels
   * @param focusMsg which tech should have focus on show up. Can be null.
   */
  public void showResearch(Message focusMsg) {
    String focusTech = null;
    if (focusMsg != null && focusMsg.getType() == MessageType.RESEARCH &&
        focusMsg.getMatchByString() != null) {
      focusTech = focusMsg.getMatchByString();
    }
    researchView = new ResearchView(players.getCurrentPlayerInfo(),
        starMap.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH,
            players.getCurrentPlayer()),focusTech, this);
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
   * Show Ship design panels
   * @parm old design to copy to new one. Can be null.
   */
  public void showShipDesignView(ShipDesign oldDesign) {
    shipDesignView = new ShipDesignView(players.getCurrentPlayerInfo(), oldDesign, this);
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
   * Show credits panel
   */
  public void showCredits() {
    try {
      creditsView = new CreditsView(this, GAME_TITLE, GAME_VERSION);
    } catch (IOException e) {
      System.out.println("Could not show credits: "+e.getMessage());
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
  public void changeGameState(GameState newState, Message focusMessage) {
    gameState = newState;
    switch (gameState) {
    case MAIN_MENU: showMainMenu(); break;
    case NEW_GAME: { 
      players = new PlayerList();
      for (int i=0;i<PlayerList.MAX_PLAYERS;i++) {
        PlayerInfo info = new PlayerInfo(SpaceRace.getRandomRace());
        info.setEmpireName("Empire of "+info.getRace().getName());
        players.addPlayer(info);
      }
      starMap = new StarMap(75, 75,players);
      starMap.updateStarMapOnStartGame();
      players.setCurrentPlayer(0);
      changeGameState(GameState.STARMAP);
      break;
    }
    case CREDITS: showCredits(); break;
    case STARMAP: showStarMap(); break;
    case RESEARCHVIEW: showResearch(focusMessage); break;
    case VIEWSHIPS: showShipView(); break;
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
        Planet planet = starMap.getPlanetByCoordinate(focusMessage.getX(), focusMessage.getY());
        if (planet != null) {
          starMap.setCursorPos(focusMessage.getX(), focusMessage.getY());
          starMap.setDrawPos(focusMessage.getX(), focusMessage.getY());
          showPlanetView(planet);
        }
      } else if (starMapView.getStarMapMouseListener().getLastClickedPlanet()!=null) {
       showPlanetView(starMapView.getStarMapMouseListener().getLastClickedPlanet());
      }
      break;
    }
    case FLEETVIEW: {
      if (starMapView.getStarMapMouseListener().getLastClickedFleet()!=null) {
        Fleet fleet = starMapView.getStarMapMouseListener().getLastClickedFleet();
        Planet planet = starMap.getPlanetByCoordinate(fleet.getX(), fleet.getY());
        showFleetView(planet,fleet);
      }
      break;
    }
    }    
  }
  
  /**
   * Change game state and show new panel/screen
   * @param newState Game State where to change
   */
  public void changeGameState(GameState newState) {
    changeGameState(newState,null);
  }
  
  /**
   * Main method to run the game
   * @param args from Command line
   */
  public static void main(String[] args) {
    new Game();

  }

  /**
   * Focus on active message
   * @param mapOnly focus only message which move map
   */
  private void focusOnMessage(boolean mapOnly) {
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
      Fleet fleet = players.getCurrentPlayerInfo().Fleets().
          getByName(msg.getMatchByString());
      if (fleet != null) {
        starMapView.setShowFleet(fleet);
        starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
        starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
      }
    }
    if ((msg.getType() == MessageType.CONSTRUCTION ||
        msg.getType() == MessageType.POPULATION) && (!mapOnly)) {
      changeGameState(GameState.PLANETVIEW, msg);
    }
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (gameState == GameState.STARMAP && starMapView != null) {
      if (arg0.getActionCommand().equals(GameCommands.COMMAND_FOCUS_MSG)) {
        focusOnMessage(false);
      } else if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_TARGET)) {
        if (starMapView.getStarMapMouseListener().getLastClickedFleet() != null) {
          Fleet fleet = players.getCurrentPlayerInfo().Fleets().getPrev();
          if (fleet != null) {
            starMap.setCursorPos(fleet.getX(), fleet.getY());
            starMap.setDrawPos(fleet.getX(), fleet.getY());
            starMapView.setShowFleet(fleet);
            starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
            starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
          }          
        }
      } else if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_TARGET)) {
        if (starMapView.getStarMapMouseListener().getLastClickedFleet() != null) {
          Fleet fleet = players.getCurrentPlayerInfo().Fleets().getNext();
          if (fleet != null) {
            starMap.setCursorPos(fleet.getX(), fleet.getY());
            starMap.setDrawPos(fleet.getX(), fleet.getY());
            starMapView.setShowFleet(fleet);
            starMapView.getStarMapMouseListener().setLastClickedFleet(fleet);
            starMapView.getStarMapMouseListener().setLastClickedPlanet(null);
          }          
        }
      } else {
        starMapView.handleActions(arg0);
        if (starMapView.isAutoFocus() && arg0.getActionCommand().equals(GameCommands.COMMAND_END_TURN)) {
          starMapView.setAutoFocus(false);
          focusOnMessage(true);
        }
      }
    }
    if (gameState == GameState.CREDITS) {
      if (arg0.getActionCommand().equalsIgnoreCase(
          GameCommands.COMMAND_ANIMATION_TIMER)) {
        creditsView.updateTextArea();
      }
      if (arg0.getActionCommand().equalsIgnoreCase(
          GameCommands.COMMAND_OK)) {
        changeGameState(GameState.MAIN_MENU);
      }
      return;
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_PLANET) &&
        starMapView.getStarMapMouseListener().getLastClickedPlanet() != null) {
      changeGameState(GameState.PLANETVIEW);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_FLEET) &&
        starMapView.getStarMapMouseListener().getLastClickedFleet() != null) {
      changeGameState(GameState.FLEETVIEW);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_STARMAP)) {
      changeGameState(GameState.STARMAP);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_RESEARCH)) {
      changeGameState(GameState.RESEARCHVIEW);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_SHIPS)) {
      changeGameState(GameState.VIEWSHIPS);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_SHIPDESIGN)) {
      shipView.setCopyClicked(false);
      changeGameState(GameState.SHIPDESIGN);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_SHIPDESIGN_DONE)) {
      if (shipDesignView != null && shipDesignView.isDesignOK()) {
        shipDesignView.keepDesign();
        changeGameState(GameState.VIEWSHIPS);
      }
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_COPY_SHIP)) {
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
    if (gameState == GameState.SHIPDESIGN && shipDesignView != null) {
      // Ship Design View
      shipDesignView.handleAction(arg0);
    }
    if (gameState == GameState.PLANETVIEW && planetView != null) {
      // Planet view
      planetView.handleAction(arg0);
    }
    if (gameState == GameState.FLEETVIEW && fleetView != null) {
      // Fleet view
      if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIZE)) {
        Ship ship = fleetView.getFleet().getColonyShip();
        if (ship != null && fleetView.getPlanet() != null &&
            fleetView.getPlanet().getPlanetPlayerInfo() == null) {
          // Make sure that ship is really colony and there is planet to colonize
          fleetView.getPlanet().setPlanetOwner(players.getCurrentPlayer(), 
              players.getCurrentPlayerInfo());
          if (players.getCurrentPlayerInfo().getRace() == SpaceRace.MECHIONS) {
            fleetView.getPlanet().setWorkers(Planet.PRODUCTION_WORKERS, ship.getColonist());
          } else {
            fleetView.getPlanet().setWorkers(Planet.PRODUCTION_FOOD, ship.getColonist());
          }
          // Remove the ship and show the planet view you just colonized
          fleetView.getFleet().removeShip(ship);
          ShipStat stat = starMap.getCurrentPlayerInfo()
              .getShipStatByName(ship.getName());
          stat.setNumberOfInUse(stat.getNumberOfInUse()-1);
          fleetView.getFleetList().recalculateList();
          starMapView.getStarMapMouseListener().setLastClickedFleet(null);
          starMapView.getStarMapMouseListener().setLastClickedPlanet(fleetView.getPlanet());
          changeGameState(GameState.PLANETVIEW);
        }
      }
      fleetView.handleAction(arg0);
    }
    if (gameState == GameState.MAIN_MENU) {
      // Main menu
      if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_NEW_GAME)) {
        changeGameState(GameState.NEW_GAME);
      }
      if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_CREDITS)) {
        changeGameState(GameState.CREDITS);
      }
      if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_QUIT_GAME)) {
        System.exit(0);
      }
    }
  }

}
