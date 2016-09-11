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
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.ListRenderers.ShipListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
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

  
  
  private IconLabel totalPeople;
  /**
   * Planet to show
   */
  private Planet planet;

  /**
   * Bombing fleet
   */
  private Fleet fleet;

  /**
   * Fleet's name Text
   */
  private JTextField fleetNameText;

  /**
   * Ships in fleet
   */
  private JList<Ship> shipsInFleet;

  
  public PlanetBombingView(Planet planet, Fleet fleet, ActionListener listener) {
    this.setPlanet(planet);
    this.fleet = fleet;
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true,null);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = new InfoPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    
    topPanel.add(Box.createRigidArea(new Dimension(15,25)));
    InvisiblePanel invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    totalPeople = new IconLabel(invis,Icons.getIconByName(Icons.ICON_PEOPLE), 
        ": "+planet.getTotalPopulation());
    totalPeople.setToolTipText("Total number of people on planet.");
    totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(totalPeople);
    topPanel.add(invis);
    topPanel.add(Box.createRigidArea(new Dimension(15,25)));

    topPanel.setTitle(planet.getName());
    
    // East panel
    InfoPanel eastPanel = new InfoPanel();
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.setTitle("Fleet info");
    eastPanel.add(Box.createRigidArea(new Dimension(150,5)));
    TransparentLabel label = new TransparentLabel(eastPanel, "Fleet name");
    eastPanel.add(label);
    eastPanel.add(Box.createRigidArea(new Dimension(5,5)));
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
    eastPanel.add(Box.createRigidArea(new Dimension(5,5)));
    label = new TransparentLabel(eastPanel, "Ships in fleet");
    eastPanel.add(label);
    eastPanel.add(Box.createRigidArea(new Dimension(5,5)));
    shipsInFleet = new JList<>();
    shipsInFleet.setListData(fleet.getShips());
    shipsInFleet.setCellRenderer(new ShipListRenderer());
    shipsInFleet.setBackground(Color.BLACK);
    shipsInFleet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane scroll = new JScrollPane(shipsInFleet);
    eastPanel.add(scroll);
    eastPanel.add(Box.createRigidArea(new Dimension(5,5)));

    
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
    this.add(imgBase,BorderLayout.CENTER);
    this.add(eastPanel,BorderLayout.EAST);
    if (planet.getPlanetOwnerIndex() != -1) {
      this.add(topPanel,BorderLayout.NORTH);
    }

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
    totalPeople.setText(": "+planet.getTotalPopulation());

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
