package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.fleet.TradeRoute;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Fleet trade view for handling trading for human player
*
*/
public class FleetTradeView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Planet to show, if any
   */
  private Planet planet;
  /**
   * Fleet to show
   */
  private Fleet fleet;

  /**
   * PlayerInfo
   */
  private PlayerInfo info;

  /**
   * Background image
   */
  private BigImagePanel imgBase;

  /**
   * Starmap, needed for deploying the starbase
   */
  private StarMap starMap;

  /**
   * Planet owner's empire name if fleet is orbiting planet.
   */
  private SpaceLabel ownerLabel;

  /**
   * Icon label showing for total amount of people on planet where
   * fleet is orbiting.
   */
  private IconLabel totalPeople;
  /**
   * Amount of metal on planet where fleet is orbiting.
   */
  private IconLabel metal;

  /**
   * Fleet's name Text
   */
  private JTextField fleetNameText;

  /**
   * List of players other fleets
   */
  private FleetList fleetList;

  /**
   * TradeRoute
   */
  private JList<TradeRoute> tradeRoutes;


  /**
   * Fleet Trade view constructor
   * @param map StarMap
   * @param info PlayerInfo who is about to trade
   * @param planet Origin planet of trade route
   * @param fleet Trade Fleet
   * @param listener ActionListener
   */
  public FleetTradeView(final StarMap map, final PlayerInfo info,
      final Planet planet, final Fleet fleet,
      final ActionListener listener) {
    this.starMap = map;
    this.planet = planet;
    this.info = info;
    this.fleet = fleet;
    this.fleetList = info.getFleets();

    // Top Panel
    InfoPanel topPanel = null;
    if (this.planet != null) {
      topPanel = new InfoPanel();
      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

      topPanel.add(Box.createRigidArea(new Dimension(15, 25)));
      SpaceGreyPanel panel = new SpaceGreyPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

      if (planet.getPlanetPlayerInfo() != null) {
        ownerLabel = new SpaceLabel(planet.getPlanetPlayerInfo()
            .getEmpireName());
        panel.add(ownerLabel);
      }
      totalPeople = new IconLabel(null,
          Icons.getIconByName(Icons.ICON_PEOPLE),
          ": " + planet.getTotalPopulation());
      totalPeople.setToolTipText("Total number of people on planet.");
      totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
      panel.add(totalPeople);
      topPanel.add(panel);
      topPanel.add(Box.createRigidArea(new Dimension(25, 25)));

      panel = new SpaceGreyPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      metal = new IconLabel(null, Icons.getIconByName(Icons.ICON_METAL),
          ": " + planet.getMetal());
      metal.setToolTipText("Total metal on surface");
      metal.setAlignmentX(Component.LEFT_ALIGNMENT);
      panel.add(metal);
      topPanel.add(panel);
      topPanel.add(Box.createRigidArea(new Dimension(25, 25)));
      topPanel.setTitle(planet.getName());

      panel = new SpaceGreyPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      topPanel.add(panel);
      topPanel.add(Box.createRigidArea(new Dimension(50, 25)));
      topPanel.setTitle(planet.getName());
    }

    // Background image
    imgBase = new BigImagePanel(planet, true, null);
    this.setLayout(new BorderLayout());

    // East panel
    InfoPanel eastPanel = new InfoPanel();
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.setTitle("Fleet info");
    eastPanel.add(Box.createRigidArea(new Dimension(150, 5)));
    SpaceLabel label = new SpaceLabel("Fleet name");
    label.setAlignmentX(CENTER_ALIGNMENT);
    eastPanel.add(label);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    fleetNameText = new JTextField();
    fleetNameText.setFont(GuiStatics.getFontCubellan());
    fleetNameText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    fleetNameText.setBackground(Color.BLACK);
    fleetNameText.setText(getFleet().getName());
    fleetNameText.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        if (fleetList.isUniqueName(fleetNameText.getText(), fleet)) {
          getFleet().setName(fleetNameText.getText());
          fleetNameText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
        } else {
          fleetNameText.setForeground(GuiStatics.COLOR_RED_TEXT);
        }
      }

      @Override
      public void keyPressed(final KeyEvent e) {
        // Nothing to do here
      }
    });
    eastPanel.add(fleetNameText);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Trade routes");
    label.setAlignmentX(CENTER_ALIGNMENT);
    eastPanel.add(label);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    tradeRoutes = new JList<>();
    //tradeRoutes.setListData(fleet.getShips());
    //tradeRoutes.setCellRenderer(new ShipListRenderer());
    tradeRoutes.setBackground(Color.BLACK);
    tradeRoutes
        .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    JScrollPane scroll = new JScrollPane(tradeRoutes);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    eastPanel.add(scroll);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceButton acceptBtn = new SpaceButton("Accept trade",
        GameCommands.COMMAND_START_TRADE_MISSION);
    acceptBtn.addActionListener(listener);
    eastPanel.add(acceptBtn);

    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    // Add panels to base
    this.add(topPanel, BorderLayout.NORTH);
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(eastPanel, BorderLayout.EAST);
    this.add(imgBase, BorderLayout.CENTER);
  }

  /**
   * Get StarMap
   * @return StarMap
   */
  public StarMap getMap() {
    return starMap;
  }
  /**
   * Get Planet
   * @return Planet
   */
  public Planet getPlanet() {
    return planet;
  }

  /**
   * Get Fleet
   * @return Fleet
   */
  public Fleet getFleet() {
    return fleet;
  }

  /**
   * Get Player info
   * @return PlayerInfo
   */
  public PlayerInfo getPlayerInfo() {
    return info;
  }
}
