package org.openRealmOfStars.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.openRealmOfStars.gui.BigImagePanel;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.MapPanel;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.infopanel.MapInfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.starMap.Planet;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapMouseListener;

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
   * MapPanel for drawing the star map
   */
  private MapPanel mapPanel;

  /**
   * Infopanel next to starMap
   */
  private MapInfoPanel infoPanel;
  
  /**
   * Star map for the game
   */
  private StarMap starMap = null;
  
  /**
   * Mouse listener aka mouse handler for star map.
   */
  private StarMapMouseListener starMapMouseListener;
  
  public GameState gameState;

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
    setTitle(GAME_TITLE+" "+GAME_VERSION);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);         
    addWindowListener(new GameWindowListener());
    setSize(1024, 768);
    setLocationRelativeTo(null);
    starMap = new StarMap(75, 75);
    changeGameState(GameState.STARMAP);
    animationTimer = new Timer(75,this);
    animationTimer.setActionCommand(GameCommands.COMMAND_ANIMATION_TIMER);
    animationTimer.start();

    
    setResizable(false);

    this.setVisible(true);

  }
  
  /**
   * Show Star Map panels
   */
  public void showStarMap() {
    BlackPanel base = new BlackPanel();
    mapPanel = new MapPanel(this);
    infoPanel = new MapInfoPanel(this);
    mapPanel.drawMap(starMap);
    starMapMouseListener = new StarMapMouseListener(starMap,mapPanel,infoPanel);
    mapPanel.addMouseListener(starMapMouseListener);
    mapPanel.addMouseMotionListener(starMapMouseListener);
    
    InfoPanel bottomPanel = new EmptyInfoPanel();
    base.setLayout(new BorderLayout());
    base.add(mapPanel,BorderLayout.CENTER);
    base.add(infoPanel, BorderLayout.EAST);
    base.add(bottomPanel, BorderLayout.SOUTH);
    
    this.getContentPane().removeAll();
    this.add(base);
    this.validate();
  }

  /**
   * Show planet view panel
   * @param planet Planet to show
   */
  public void showPlanetView(Planet planet) {
    BlackPanel base = new BlackPanel();
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true);
    base.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = new InfoPanel();
    topPanel.setLayout(new GridLayout(1, 0));
    
    InvisiblePanel invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    IconLabel totalPeople = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_PEOPLE), ": 5");
    totalPeople.setToolTipText("Total number of people on planet.");
    invis.add(totalPeople);
    WorkerProductionPanel farmPanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_FARM, GameCommands.COMMAND_PLUS_FARM, 
        Icons.ICON_FARM, ": 1", "Number of people working as a farmers.",
        this);
    invis.add(farmPanel);
    WorkerProductionPanel minePanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_MINE, GameCommands.COMMAND_PLUS_MINE, 
        Icons.ICON_MINE, ": 1", "Number of people working as a miners.",
        this);
    invis.add(minePanel);
    WorkerProductionPanel prodPanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_PRODUCTION, GameCommands.COMMAND_PLUS_PRODUCTION, 
        Icons.ICON_FACTORY, ": 1", "Number of people working as a factory worker.",
        this);
    invis.add(prodPanel);
    WorkerProductionPanel resePanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_RESEARCH, GameCommands.COMMAND_PLUS_RESEARCH, 
        Icons.ICON_RESEARCH, ": 1", "Number of people working as a scientist.",
        this);
    invis.add(resePanel);
    IconLabel culture = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_CULTURE), ": 0");
    culture.setToolTipText("Number of people producing culture.");
    invis.add(culture);
    topPanel.add(invis);

    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    IconLabel peopleGrowth = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_PEOPLE), "10 turns");
    peopleGrowth.setToolTipText("How many turns to population growth.");
    invis.add(peopleGrowth);
    IconLabel farmProd = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_FARM), ": 3");
    farmProd.setToolTipText("Total production of food");
    invis.add(farmProd);
    IconLabel mineProd = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_MINE), ": 3");
    mineProd.setToolTipText("Total production of metal");
    invis.add(mineProd);
    IconLabel prodProd = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_FACTORY), ": 3");
    prodProd.setToolTipText("Total production of production");
    invis.add(prodProd);
    IconLabel reseProd = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_RESEARCH), ": 3");
    reseProd.setToolTipText("Total production of research");
    invis.add(reseProd);
    IconLabel cultProd = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_CULTURE), ": 3");
    cultProd.setToolTipText("Total production of culture");
    invis.add(cultProd);
    topPanel.add(invis);

    
    topPanel.setTitle(planet.getName());
    
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map", 
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(this);
    bottomPanel.add(btn,BorderLayout.CENTER);
    
    // Add panels to base
    base.add(bottomPanel,BorderLayout.SOUTH);
    base.add(imgBase,BorderLayout.CENTER);
    base.add(topPanel,BorderLayout.NORTH);

    this.getContentPane().removeAll();
    this.add(base);
    this.validate();
  }

  /**
   * Change game state and show new panel/screen
   * @param newState Game State where to change
   */
  public void changeGameState(GameState newState) {
    gameState = newState;
    switch (gameState) {
    case STARMAP: showStarMap(); break;
    case PLANETVIEW: { 
      if (starMapMouseListener.getLastClickedPlanet()!=null) {
       showPlanetView(starMapMouseListener.getLastClickedPlanet());
       break;
      }
    }
    }
  }
  
  /**
   * Main method to run the game
   * @param args from Command line
   */
  public static void main(String[] args) {
    new Game();

  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (starMapMouseListener != null) {
        starMapMouseListener.updateScrollingIfOnBorder();
      }
      mapPanel.drawMap(starMap);
      mapPanel.repaint();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_PLANET) &&
        starMapMouseListener.getLastClickedPlanet() != null) {
      changeGameState(GameState.PLANETVIEW);
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_VIEW_STARMAP)) {
      changeGameState(GameState.STARMAP);
    }
  }

}
