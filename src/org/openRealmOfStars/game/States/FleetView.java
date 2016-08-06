package org.openRealmOfStars.game.States;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BigImagePanel;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
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
 * Fleet view for handling single fleet orbiting on planet or deep space.
 * 
 */
public class FleetView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  
  private IconLabel totalPeople;
  private IconLabel metal;
  private TransparentLabel ownerLabel;
  
  /**
   * How much colonist has moved to fleet
   */
  private WorkerProductionPanel colonistSelection;
  /**
   * How much metal has moved to fleet
   */
  private WorkerProductionPanel metalSelection;
  /**
   * Planet to show, if any
   */
  private Planet planet;
  /**
   * Fleet to show
   */
  private Fleet fleet;
  
  /**
   * List of players other fleets
   */
  private FleetList fleetList;
  
  /**
   * Fleet's name Text
   */
  private JTextField fleetNameText;

  /**
   * PlayerInfo
   */
  private PlayerInfo info;
  
  public FleetView(Planet planet, Fleet fleet, FleetList fleetList,
      PlayerInfo playerInfo, ActionListener listener) {
    this.setPlanet(planet);
    this.setFleet(fleet);
    this.setFleetList(fleetList);
    this.setInfo(playerInfo);
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,null);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = null;
    if (this.planet != null) {
      topPanel = new InfoPanel();
      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    
      topPanel.add(Box.createRigidArea(new Dimension(15,25)));
      InvisiblePanel invis = new InvisiblePanel(topPanel);
      invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
      SpaceButton conquerBtn = null;
      SpaceButton colonizeBtn = null;
      colonistSelection = null;
      metalSelection = null;
      
      if (planet.getPlanetPlayerInfo() != null) {
        ownerLabel = new TransparentLabel(invis, planet.getPlanetPlayerInfo().getEmpireName());
        if (info != planet.getPlanetPlayerInfo()) {
          conquerBtn = new SpaceButton("Conquer", GameCommands.COMMAND_CONQUER);
          conquerBtn.addActionListener(listener);
        }
      } else {
        ownerLabel = new TransparentLabel(invis, "Uncolonized planet");
        colonizeBtn = new SpaceButton("Colonize", GameCommands.COMMAND_COLONIZE);
        colonizeBtn.addActionListener(listener);
      }
      invis.add(ownerLabel);
      totalPeople = new IconLabel(invis,Icons.getIconByName(Icons.ICON_PEOPLE), 
        ": "+planet.getTotalPopulation());
      totalPeople.setToolTipText("Total number of people on planet.");
      totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
      invis.add(totalPeople);
      topPanel.add(invis);
      topPanel.add(Box.createRigidArea(new Dimension(25,25)));

      invis = new InvisiblePanel(topPanel);
      invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
      metal = new IconLabel(invis,Icons.getIconByName(Icons.ICON_METAL), 
        ": "+planet.getMetal());
      metal.setToolTipText("Total metal on surface");
      metal.setAlignmentX(Component.LEFT_ALIGNMENT);
      invis.add(metal);
      topPanel.add(invis);
      topPanel.add(Box.createRigidArea(new Dimension(25,25)));
      topPanel.setTitle(planet.getName());

      invis = new InvisiblePanel(topPanel);
      invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
      if (colonizeBtn != null) {
        invis.add(colonizeBtn);
      }
      if (conquerBtn != null) {
        invis.add(conquerBtn);
      }
      if (planet.getPlanetPlayerInfo() != null &&
          info == planet.getPlanetPlayerInfo()) {
        colonistSelection = new WorkerProductionPanel(invis, 
            GameCommands.COMMAND_COLONIST_MINUS, 
            GameCommands.COMMAND_COLONIST_PLUS, Icons.ICON_PEOPLE, 
            "Colonist: 10000", "How many colonist/troops is on board of fleet.", listener);
        invis.add(colonistSelection);
        metalSelection = new WorkerProductionPanel(invis, 
            GameCommands.COMMAND_METAL_CARGO_MINUS, 
            GameCommands.COMMAND_METAL_CARGO_PLUS, Icons.ICON_METAL, 
            "Metal: 1000000", "How many metal cargo is on board of fleet.", listener);
        invis.add(metalSelection);
      }
      topPanel.add(invis);
      topPanel.add(Box.createRigidArea(new Dimension(50,25)));
      topPanel.setTitle(planet.getName());
}
    // East panel
    InfoPanel eastPanel = new InfoPanel();
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.setTitle("Fleet info");
    eastPanel.add(Box.createRigidArea(new Dimension(150,5)));
    fleetNameText = new JTextField();
    fleetNameText.setFont(GuiStatics.getFontCubellan());
    fleetNameText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    fleetNameText.setBackground(Color.BLACK);
    fleetNameText.setText(getFleet().getName());
    fleetNameText.addKeyListener(new KeyListener() {
      
      @Override
      public void keyTyped(KeyEvent e) {
        // Nothing to  do here
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        getFleet().setName(fleetNameText.getText());
      }
      
      @Override
      public void keyPressed(KeyEvent e) {
        // Nothing to  do here
      }
    });
    eastPanel.add(fleetNameText);
    eastPanel.add(Box.createRigidArea(new Dimension(5,500)));

    
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map", 
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn,BorderLayout.CENTER);
    
    this.updatePanel();
    
    // Add panels to base
    this.add(bottomPanel,BorderLayout.SOUTH);
    this.add(eastPanel,BorderLayout.EAST);
    this.add(imgBase,BorderLayout.CENTER);
    if (topPanel != null) {
      this.add(topPanel,BorderLayout.NORTH);
    }

  }
  
  /**
   * Update Planet view panels
   */
  public void updatePanel() {
    if (planet != null) {
      totalPeople.setText(": "+planet.getTotalPopulation());
      metal.setText(": "+planet.getMetal());
    }
    if (colonistSelection != null) {
      colonistSelection.setText("Colonist: "+fleet.getTotalCargoColonist()+"/"+
        fleet.getFreeSpaceForColonist());
    }
    if (metalSelection != null) {
      metalSelection.setText("Metal: "+fleet.getTotalCargoMetal()+"/"+
        fleet.getFreeSpaceForMetal());
    }

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
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIST_PLUS) &&
        fleet.getFreeSpaceForColonist() > 0 && planet != null) {
      if (planet.takeColonist()) {
        fleet.addColonist();
        updatePanel();
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIST_MINUS) &&
      fleet.getTotalCargoColonist() > 0 && planet != null) {
      if (planet.getPlanetPlayerInfo().getRace() != SpaceRace.MECHIONS) {
        planet.setWorkers(Planet.PRODUCTION_FOOD, planet.getWorkers(Planet.PRODUCTION_FOOD)+1);
        fleet.removeColonist();
      } else {
        planet.setWorkers(Planet.PRODUCTION_WORKERS, planet.getWorkers(Planet.PRODUCTION_WORKERS)+1);
        fleet.removeColonist();
      }
      updatePanel();
    }
  }

  public Fleet getFleet() {
    return fleet;
  }

  public void setFleet(Fleet fleet) {
    this.fleet = fleet;
  }

  public FleetList getFleetList() {
    return fleetList;
  }

  public void setFleetList(FleetList fleetList) {
    this.fleetList = fleetList;
  }

  public PlayerInfo getInfo() {
    return info;
  }

  public void setInfo(PlayerInfo info) {
    this.info = info;
  }
}
