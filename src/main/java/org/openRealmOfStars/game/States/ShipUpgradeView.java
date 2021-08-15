package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ShipListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
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
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.player.ship.ShipStat;
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
   * Upgradeable ship designs names.
   */
  private SpaceComboBox<String> upgradeList;

  /**
   * Metal on planet available
   */
  private SpaceLabel metalOnPlanet;
  /**
   * Credits for playerinfo
   */
  private SpaceLabel credits;
  /**
   * Cost for upgrade
   */
  private SpaceLabel totalCost;

  /**
   * Upgrade button.
   */
  private SpaceButton upgradeButton;
  /**
   * Metal upgrade cost
   */
  private int metalUpgradeCost;
  /**
   * Production upgrade cost
   */
  private int prodUpgradeCost;
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
    metalUpgradeCost = 0;
    prodUpgradeCost = 0;
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
    totalCost = new SpaceLabel("Metal cost: 999 Credit cost: 999");
    panel.add(totalCost);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    base.add(panel, BorderLayout.WEST);
    // Center panel
    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    shipImage = new ImageLabel(
        ShipImages.humans().getShipImage(ShipImage.SCOUT), true);
    shipImage.setFillColor(Color.BLACK);
    panel.add(shipImage);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    infoText = new InfoTextPane();
    scroll = new JScrollPane(infoText);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    panel.add(scroll);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceLabel label = new SpaceLabel("Upgrade ship to:");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] emptyList = {"None"};
    upgradeList = new SpaceComboBox<>(emptyList);
    upgradeList.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    upgradeList.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    upgradeList.setBorder(new SimpleBorder());
    upgradeList.setFont(GuiStatics.getFontCubellan());
    upgradeList.getModel().setSelectedItem("None");
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
    upgradeList.setRenderer(dlcr);
    upgradeList.addActionListener(listener);
    upgradeList.setActionCommand(GameCommands.COMMAND_UPGRADE_SELECTED);
    panel.add(upgradeList);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    upgradeButton = new SpaceButton("Upgrade", GameCommands.COMMAND_UPGRADE);
    upgradeButton.addActionListener(listener);
    panel.add(upgradeButton);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    base.add(panel, BorderLayout.CENTER);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to fleet view",
        GameCommands.COMMAND_VIEW_FLEET);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    this.add(base, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);

    updatePanels();
  }

  /**
   * Update panels.
   */
  public void updatePanels() {
    metalOnPlanet.setText("Metal available: " + planet.getMetal());
    credits.setText("Credits: " + player.getTotalCredits());
    Ship ship = getSelectedShip();
    if (ship != null) {
      shipImage.setImage(ship.getHull().getImage());
      ShipStat stat = player.getShipStatByName(
          (String) upgradeList.getSelectedItem());
      if (stat != null && ship.getHull() == stat.getDesign().getHull()) {
        metalUpgradeCost = ship.getUpgradeMetalCost(stat.getDesign());
        prodUpgradeCost = ship.getUpgradeCost(stat.getDesign());
        if (metalUpgradeCost > planet.getMetal()
            + planet.getAmountMetalInGround()) {
          metalUpgradeCost = 0;
          prodUpgradeCost = 0;
        }
        if (metalUpgradeCost > planet.getMetal()) {
          int left = planet.getMetal() - metalUpgradeCost;
          metalUpgradeCost = planet.getMetal();
          prodUpgradeCost = prodUpgradeCost + left * 2;
        }
      } else {
        infoText.setText(ship.getTacticalInfo());
        metalUpgradeCost = 0;
        prodUpgradeCost = 0;
        upgradeButton.setEnabled(false);
      }
    }
    totalCost.setText("Metal cost: " + metalUpgradeCost + " Credits Cost: "
        + prodUpgradeCost);
    this.repaint();
  }
  /**
   * Handle actions for view.
   * @param arg0 ActionEvent.
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE_SELECTED)) {
      updatePanels();
    }
  }

  /**
   * Get Seletected ship
   * @return Ship.
   */
  public Ship getSelectedShip() {
    Ship ship = shipListInFleet.getSelectedValue();
    return ship;
  }

  /**
   * Upgrade possible ship design updates.
   */
  public void updateUpgradePossibilities() {
    Ship ship = getSelectedShip();
    if (ship != null) {
      ArrayList<ShipStat> stats = new ArrayList<>();
      for (ShipStat stat : player.getShipStatList()) {
        if (stat.getDesign().getHull() == ship.getHull()
            && ship.getName() != stat.getDesign().getName()) {
          stats.add(stat);
        }
      }
      String[] designNames = new String[stats.size() + 1];
      designNames[0] = "None";
      for (int i = 1; i < designNames.length; i++) {
        designNames[i] = stats.get(i - 1).getDesign().getName();
      }
      upgradeList.setModel(new DefaultComboBoxModel<>(designNames));
    }
  }
  @Override
  public void valueChanged(final ListSelectionEvent e) {
    updateUpgradePossibilities();
    updatePanels();
  }

}
