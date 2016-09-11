package org.openRealmOfStars.game.States;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ShipListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
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
 * Planet bombing view for handling attack on planet
 * 
 */
public class PlanetBombingView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  
  
  /**
   * Total number of population
   */
  private IconLabel totalPeople;
  /**
   * Trooper's power
   */
  private IconLabel troopsPower;
  /**
   * Defense turret value
   */
  private IconLabel defenseTurret;
  /**
   * Total number of building
   */
  private IconLabel totalBuildings;

  /**
   * Planet owner's name
   */
  private TransparentLabel ownerLabel;

  /**
   * Planet to show
   */
  private Planet planet;

  /**
   * Bombing fleet
   */
  private Fleet fleet;


  /**
   * Ships in fleet
   */
  private JList<Ship> shipsInFleet;

  /**
   * Background picture
   */
  private BigImagePanel imgBase;
  
  /**
   * Infopanel on east side
   */
  private BattleInfoPanel infoPanel;
  
  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  
  /**
   * Text log
   */
  private String[] textLog;

  
  public PlanetBombingView(Planet planet, Fleet fleet, ActionListener listener) {
    this.setPlanet(planet);
    this.fleet = fleet;
    // Background image
    imgBase = new BigImagePanel(planet, true,null);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = new InfoPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    
    topPanel.add(Box.createRigidArea(new Dimension(15,25)));
    InvisiblePanel invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    if (planet.getPlanetPlayerInfo() != null) {
      ownerLabel = new TransparentLabel(invis, planet.getPlanetPlayerInfo().getEmpireName());
    } else {
      ownerLabel = new TransparentLabel(invis, "Uncolonized planet");
    }
    invis.add(ownerLabel);
    topPanel.add(invis);
    topPanel.add(Box.createRigidArea(new Dimension(15,25)));

    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    totalPeople = new IconLabel(invis,Icons.getIconByName(Icons.ICON_PEOPLE), 
        "Population: "+planet.getTotalPopulation());
    totalPeople.setToolTipText("Total number of people on planet.");
    totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(totalPeople);
    defenseTurret = new IconLabel(invis,Icons.getIconByName(Icons.ICON_PLANETARY_TURRET), 
        "Turrets: "+planet.getTurretLvl());
    defenseTurret.setToolTipText("Total defense value of turrets.");
    defenseTurret.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(defenseTurret);
    topPanel.add(invis);
    topPanel.add(Box.createRigidArea(new Dimension(15,25)));

    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    troopsPower = new IconLabel(invis,Icons.getIconByName(Icons.ICON_TROOPS), 
        "Troops power: "+planet.getTroopPower());
    troopsPower.setToolTipText("Total power of defending troops.");
    troopsPower.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(troopsPower);
    totalBuildings = new IconLabel(invis,Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH), 
        "Buildings: "+planet.getNumberOfBuildings());
    totalBuildings.setToolTipText("Total number of buildings on planet.");
    totalBuildings.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(totalBuildings);
    topPanel.add(invis);
    topPanel.add(Box.createRigidArea(new Dimension(15,25)));

    topPanel.setTitle(planet.getName());
    
    // East panel
    InfoPanel eastPanel = new InfoPanel();
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.setTitle(fleet.getName());
    eastPanel.add(Box.createRigidArea(new Dimension(150,5)));
    shipsInFleet = new JList<>();
    shipsInFleet.setListData(fleet.getShips());
    shipsInFleet.setCellRenderer(new ShipListRenderer());
    shipsInFleet.setBackground(Color.BLACK);
    shipsInFleet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scroll = new JScrollPane(shipsInFleet);
    eastPanel.add(scroll);

    infoPanel = new BattleInfoPanel(fleet.getFirstShip(),listener);
    infoPanel.showShip(fleet.getFirstShip());
    infoPanel.setBorder(null);
    eastPanel.add(infoPanel);
    eastPanel.add(Box.createRigidArea(new Dimension(5,5)));
    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.X_AXIS));
    SpaceButton btn = new SpaceButton("Next ship", 
        GameCommands.COMMAND_NEXT_TARGET);
    btn.addActionListener(listener);
    invis.add(btn);
    invis.add(Box.createRigidArea(new Dimension(5,5)));
    btn = new SpaceButton("Abort conquest", 
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    invis.add(btn);
    eastPanel.add(invis);

    
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    textArea = new InfoTextArea(10,30);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    bottomPanel.add(textArea,BorderLayout.CENTER);
    textLog = new String[11];
    textLog[0] = "1st Line";
    textLog[1] = "2st Line";
    textLog[2] = "3st Line";
    textLog[3] = "4st Line";
    textLog[4] = "5st Line";
    textLog[5] = "6st Line";
    textLog[6] = "7st Line";
    textLog[7] = "8st Line";
    textLog[8] = "9st Line";
    textLog[9] = "10st Line";
    textLog[10] = "11st Line";

    
    this.updatePanel();
    
    // Add panels to base
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());
    centerPanel.add(imgBase, BorderLayout.CENTER);
    centerPanel.add(bottomPanel, BorderLayout.SOUTH);
    if (planet.getPlanetOwnerIndex() != -1) {
      centerPanel.add(topPanel,BorderLayout.NORTH);
    }
    this.add(centerPanel,BorderLayout.CENTER);
    this.add(eastPanel,BorderLayout.EAST);

  }
  
  public Fleet getFleet() {
    return fleet;
  }

  public void setFleet(Fleet fleet) {
    this.fleet = fleet;
  }

  
  /**
   * Update Planet view panels
   */
  public void updatePanel() {
    totalPeople.setText("Population: "+planet.getTotalPopulation());
    defenseTurret.setText("Turrets: "+planet.getTurretLvl());
    totalBuildings.setText("Buildings: "+planet.getNumberOfBuildings());
    troopsPower.setText("Troops power: "+planet.getTroopPower());

    StringBuilder sb = new StringBuilder();
    for (int i=textLog.length-1;i>=0;i--) {
      sb.append(textLog[i]);
      sb.append("\n");
    }
    textArea.setText(sb.toString());
    
    /*
     * Set the orbting ships
     */
    Ship[] ships = fleet.getShips();
    BufferedImage[] imgs = new BufferedImage[ships.length];
    for (int i = 0;i<ships.length;i++) {
      imgs[i] = ships[i].getHull().getImage();
    }
    imgBase.setShipImage(imgs);

  }

  /**
   * @return the planet
   */
  public Planet getPlanet() {
    return planet;
  }

  /**
   * @param planet the planet to set
   */
  public void setPlanet(Planet planet) {
    this.planet = planet;
  }
  
  
  /**
   * Handle actions for Planet view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      // FIXME
    }
  }
}
