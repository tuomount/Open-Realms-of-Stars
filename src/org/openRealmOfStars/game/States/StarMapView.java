package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.MapPanel;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.infopanel.MapInfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapMouseListener;
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
 * Star Map view for handling star map
 * 
 */
public class StarMapView extends BlackPanel {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Star map to show
   */
  private StarMap map;
  /**
   * Players list
   */
  private PlayerList players;
  
  /**
   * MapPanel for drawing the star map
   */
  private MapPanel mapPanel;

  /**
   * Infopanel next to starMap
   */
  private MapInfoPanel infoPanel;

  /**
   * Mouse listener aka mouse handler for star map.
   */
  private StarMapMouseListener starMapMouseListener;

  /**
   * End Turn Button
   */
  private SpaceButton endTurnButton;

  /**
   * View research Button
   */
  private SpaceButton viewResearchButton;

  /**
   * Credit production
   */
  private IconLabel credProd;
  
  /**
   * Research production
   */
  private IconLabel reseProd;
  
  public StarMapView(StarMap map, PlayerList players, ActionListener listener) {
    this.map = map;
    this.players = players;

    BlackPanel base = new BlackPanel();
    mapPanel = new MapPanel();
    infoPanel = new MapInfoPanel(listener);
    mapPanel.drawMap(this.map);
    starMapMouseListener = new StarMapMouseListener(this.map,mapPanel,infoPanel);
    mapPanel.addMouseListener(starMapMouseListener);
    mapPanel.addMouseMotionListener(starMapMouseListener);
    
    InfoPanel bottomPanel = new EmptyInfoPanel();    
    bottomPanel.setTitle(players.getCurrentPlayerInfo().getEmpireName());
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
    bottomPanel.add(Box.createRigidArea(new Dimension(15,25)));
    InvisiblePanel invis = new InvisiblePanel(bottomPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    credProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CREDIT), 
        ": "+this.players.getCurrentPlayerInfo().getTotalCredits()+
        "("+this.map.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_CREDITS,
            this.players.getCurrentPlayer())+")");
    invis.add(credProd);
    reseProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_RESEARCH), 
        ": "+this.map.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH,
            this.players.getCurrentPlayer()));
    invis.add(reseProd);
    bottomPanel.add(invis);
    bottomPanel.add(Box.createRigidArea(new Dimension(10,5)));

    
    endTurnButton = new SpaceButton("End Turn "+this.map.getTurn(), 
        GameCommands.COMMAND_END_TURN);
    endTurnButton.addActionListener(listener);
    bottomPanel.add(endTurnButton);
    bottomPanel.add(Box.createRigidArea(new Dimension(10,5)));
    
    viewResearchButton = new SpaceButton("Research",
        GameCommands.COMMAND_VIEW_RESEARCH);
    viewResearchButton.addActionListener(listener);
    bottomPanel.add(viewResearchButton);
    
    base.setLayout(new BorderLayout());
    base.add(mapPanel,BorderLayout.CENTER);
    base.add(infoPanel, BorderLayout.EAST);
    base.add(bottomPanel, BorderLayout.SOUTH);
    this.add(base);

  }

  /**
   * Get Star Map mouse listener
   * @return
   */
  public StarMapMouseListener getStarMapMouseListener() {
    return starMapMouseListener;
  }
  
  public void handleActions(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_ANIMATION_TIMER) ) {
      if (starMapMouseListener != null) {
        starMapMouseListener.updateScrollingIfOnBorder();
      }
      mapPanel.drawMap(this.map);
      mapPanel.repaint();
    }
    // Starmap
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_END_TURN)) {
      map.updateStarMapToNextTurn();
      endTurnButton.setText("End turn "+map.getTurn());
      credProd.setText(": "+players.getCurrentPlayerInfo().getTotalCredits()+
        "("+this.map.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_CREDITS,
            this.players.getCurrentPlayer())+")");
      reseProd.setText(": "+this.map.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH,
            this.players.getCurrentPlayer()));
           
     for (int i=0;i<players.getCurrentMaxPlayers();i++) {
       PlayerInfo info = players.getPlayerInfoByIndex(i);
       info.getTechList().updateResearchPointByTurn(map.
           getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH, i));
     }

    }

  }
}
