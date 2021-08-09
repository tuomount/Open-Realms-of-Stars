package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ShipListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.BaseInfoTextArea;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextPane;
import org.openRealmOfStars.gui.labels.SpaceComboBox;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* Ship Upgrade view.
*
*/
public class ShipUpgradeView extends BlackPanel
  implements ListSelectionListener {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Player Info
   */
  private PlayerInfo player;

  /**
   * Planet where to upgrade.
   */
  private Planet planet;

  /**
   * Fleet to upgrade.
   */
  private Fleet fleet;
  /**
   * Ship image label
   */
  private ImageLabel shipImage;

  /**
   * Text is containing information about the ship
   */
  private InfoTextPane infoText;

  /**
   * List of ships in fleet
   */
  private JList<Ship> shipListInFleet;

  /**
   * Upgradeable ship designs.
   */
  private SpaceComboBox<ShipDesign> upgradeList;

  /**
   * Metal on planet available
   */
  private SpaceLabel metalOnPlanet;
  /**
   * Credits for playerinfo
   */
  private SpaceLabel credits;
  /**
   * Create new ship upgrade view
   * @param player Player Info
   * @param fleet Fleet which to upgrade.
   * @param planet Planet where to upgrade
   * @param listener Action Listener
   */
  public ShipUpgradeView(final PlayerInfo player, final Fleet fleet,
     final Planet planet, final ActionListener listener) {
    this.player = player;
    this.planet = planet;
    this.fleet = fleet;
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Ship upgrade on " + planet.getName());
    shipListInFleet = new JList<>(this.fleet.getShips());
    shipListInFleet.setCellRenderer(new ShipListRenderer());
    shipListInFleet.setBackground(Color.BLACK);
    shipListInFleet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shipListInFleet.addListSelectionListener(this);
    JScrollPane scroll = new JScrollPane(shipListInFleet);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(scroll);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    metalOnPlanet = new SpaceLabel("Metal available: 9999");
    panel.add(metalOnPlanet);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    credits = new SpaceLabel("Credits: 99999");
    panel.add(credits);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    this.add(base, BorderLayout.WEST);
    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    infoText = new InfoTextPane();
    panel.add(infoText);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceLabel label = new SpaceLabel("Upgrade ship to:");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to fleet view",
        GameCommands.COMMAND_VIEW_FLEET);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    // updatePanel();
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
  }
  @Override
  public void valueChanged(ListSelectionEvent e) {
    // TODO Auto-generated method stub
    
  }

}
