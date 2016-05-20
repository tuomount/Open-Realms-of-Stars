package org.openRealmOfStars.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.MapPanel;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.infopanel.MapInfoPanel;
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
    starMap = new StarMap(75, 75);
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
    base.setLayout(new BorderLayout());
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map", 
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(this);
    bottomPanel.add(btn);
    base.add(bottomPanel,BorderLayout.SOUTH);

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
