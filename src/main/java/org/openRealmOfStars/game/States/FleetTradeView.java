package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.TradeRouteListRenderer;
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
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.fleet.TradeRoute;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018-2021  Tuomo Untinen
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
public class FleetTradeView extends BlackPanel
    implements ListSelectionListener {

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
    fleetNameText.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    fleetNameText.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        if (fleetList.isUniqueName(fleetNameText.getText(), fleet)) {
          String oldName = getFleet().getName();
          getFleet().setName(fleetNameText.getText());
          info.getMissions().changeFleetName(oldName, getFleet().getName());
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
    tradeRoutes.setListData(getPossibleTradeRoutes());
    tradeRoutes.setCellRenderer(new TradeRouteListRenderer());
    tradeRoutes.setBackground(Color.BLACK);
    tradeRoutes
        .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    tradeRoutes.addListSelectionListener(this);
    JScrollPane scroll = new JScrollPane(tradeRoutes);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    eastPanel.add(scroll);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceButton acceptBtn = new SpaceButton("Accept trade",
        GameCommands.COMMAND_START_TRADE_MISSION);
    acceptBtn.addActionListener(listener);
    eastPanel.add(acceptBtn);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceButton stopBtn = new SpaceButton("Stop trading",
        GameCommands.COMMAND_STOP_TRADE_MISSION);
    stopBtn.addActionListener(listener);
    eastPanel.add(stopBtn);
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
   * Get all the possible trade routes for planet and realm
   * @return Array of tradeRoutes
   */
  protected TradeRoute[] getPossibleTradeRoutes() {
    ArrayList<TradeRoute> list = new ArrayList<>();
    if (planet.getPlanetPlayerInfo() == info) {
      for (Planet target : starMap.getPlanetList()) {
        if (info.getSectorVisibility(target.getCoordinate())
            > PlayerInfo.UNCHARTED) {
          PlayerInfo targetOwner = target.getPlanetPlayerInfo();
          if (targetOwner != null && targetOwner != info) {
            int targetIndex = starMap.getPlayerList().getIndex(targetOwner);
            DiplomacyBonusList diplomacy = info.getDiplomacy()
                .getDiplomacyList(targetIndex);
            if (diplomacy != null
                && (diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
                || diplomacy.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
                || diplomacy.isBonusType(
                    DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
              TradeRoute route = new TradeRoute(planet, target, info, fleet);
              list.add(route);
            }
          }
        }
      }
    } else if (planet.getPlanetPlayerInfo() != null) {
      // Route is starting from non home planet
      for (Planet target : starMap.getPlanetList()) {
        if (info.getSectorVisibility(target.getCoordinate())
            > PlayerInfo.UNCHARTED) {
          PlayerInfo targetOwner = target.getPlanetPlayerInfo();
          if (targetOwner != null && targetOwner == info) {
            int targetIndex = starMap.getPlayerList().getIndex(
                planet.getPlanetPlayerInfo());
            DiplomacyBonusList diplomacy = info.getDiplomacy()
                .getDiplomacyList(targetIndex);
            if (diplomacy != null
                && (diplomacy.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
                || diplomacy.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
                || diplomacy.isBonusType(
                    DiplomacyBonusType.IN_DEFENSIVE_PACT))) {
              TradeRoute route = new TradeRoute(target, planet, info, fleet);
              list.add(route);
            }
          }
        }
      }
    }
    return list.toArray(new TradeRoute[list.size()]);
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

  /**
   * Get Selected trade route.
   * @return TradeRoute or null
   */
  public TradeRoute getTradeRoute() {
    return tradeRoutes.getSelectedValue();
  }

  @Override
  public void valueChanged(final ListSelectionEvent arg0) {
   TradeRoute route = tradeRoutes.getSelectedValue();
   if (route != null) {
     int distance = (int) route.getOriginWorld().getCoordinate()
         .calculateDistance(route.getTradeWorld().getCoordinate());
     StringBuilder sb = new StringBuilder();
     sb.append(route.toString());
     sb.append("\n");
     sb.append(route.getMoreInfo());
     sb.append("\n");
     if (fleet.getFleetFtlSpeed() > 0) {
       int time = distance / fleet.getFleetFtlSpeed();
       sb.append("Estimated trip time: ");
       sb.append(time);
       sb.append(" turn");
       if (time > 1) {
         sb.append("s");
       }
     } else {
       sb.append("Estimated trip time: Never");
     }
     imgBase.setText(sb.toString());
   } else {
     imgBase.setText(null);
   }
   this.repaint();
  }
}
