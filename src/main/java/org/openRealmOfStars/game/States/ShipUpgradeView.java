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

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
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
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.starMap.StarMapUtilities;
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
    scroll.setBackground(GuiStatics.getDeepSpaceDarkerColor());
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
    scroll.setBackground(GuiStatics.getDeepSpaceDarkerColor());
    panel.add(scroll);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceLabel label = new SpaceLabel("Upgrade ship to:");
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    String[] emptyList = {"None"};
    upgradeList = new SpaceComboBox<>(emptyList);
    upgradeList.setBackground(GuiStatics.getDeepSpaceDarkerColor());
    upgradeList.setForeground(GuiStatics.getCoolSpaceColor());
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
   * Get Value color based on two value.
   * @param greenValue Green color if this is bigger
   * @param redValue Red color if this is bigger
   * @return Color
   */
  private static Color getValueColor(final int greenValue, final int redValue) {
    if (greenValue > redValue) {
      return GuiStatics.COLOR_GREEN_TEXT;
    } else if (greenValue < redValue) {
      return GuiStatics.COLOR_RED_TEXT;
    } else {
      return GuiStatics.COLOR_GREY_160;
    }
  }
  /**
   * Show difference in info text panel
   * @param design New ship design
   * @param ship Original ship
   */
  private void showDifference(final ShipDesign design, final Ship ship) {
    infoText.setText("");
    infoText.addText(ship.getName(), GuiStatics.COLOR_RED_TEXT);
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(design.getName(), GuiStatics.COLOR_GREEN_TEXT);
    infoText.addText(" - ", GuiStatics.getCoolSpaceColor());
    infoText.addText(ship.getHull().getHullType().toString(),
        GuiStatics.getCoolSpaceColor());
    infoText.addText("\nCapacity: ", GuiStatics.getCoolSpaceColor());
    infoText.addText(String.format("%.1f", ship.getFleetCapacity()),
        GuiStatics.getCoolSpaceColor());
    infoText.addText(" Energy: ", GuiStatics.getCoolSpaceColor());
    int oldValue = ship.getTotalEnergy() - ship.getEnergyConsumption();
    int newValue = design.getFreeEnergy();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText(" Init.: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getInitiative();
    newValue = design.getInitiative();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText("\nSpeed: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getSpeed();
    newValue = design.getSpeed();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText(" FTL: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getFtlSpeed();
    newValue = design.getFtlSpeed();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText(" Tactic: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getTacticSpeed();
    newValue = design.getTacticSpeed();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText("\nShield: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getTotalShield();
    newValue = design.getTotalShield();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText(" Armor: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getTotalArmor();
    newValue = design.getTotalArmor();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText(" Hull Points: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getHull().getSlotHull() * ship.getNumberOfComponents();
    newValue = design.getHull().getSlotHull() * design.getNumberOfComponents();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText("\nMilitary power: ", GuiStatics.getCoolSpaceColor());
    oldValue = ship.getTheoreticalMilitaryPower();
    newValue = design.getTotalMilitaryPower();
    infoText.addText(oldValue, getValueColor(oldValue, newValue));
    infoText.addText("->", GuiStatics.getCoolSpaceColor());
    infoText.addText(newValue, getValueColor(newValue, oldValue));
    infoText.addText("\n\nComponents:\n", GuiStatics.getCoolSpaceColor());
    for (int i = 0; i < ship.getNumberOfComponents(); i++) {
      ShipComponent origComp = ship.getComponent(i);
      ShipComponent newComp = design.getComponent(i);
      if (origComp == null && newComp == null) {
        infoText.addText(i + 1, GuiStatics.getCoolSpaceColor());
        infoText.addText(": Empty\n\n", GuiStatics.getCoolSpaceColor());
        continue;
      }
      if (origComp == null && newComp != null) {
        infoText.addText(i + 1, GuiStatics.getCoolSpaceColor());
        infoText.addText(": Empty -> ", GuiStatics.getCoolSpaceColor());
        infoText.addText(newComp.getName(), GuiStatics.COLOR_GREEN_TEXT);
        infoText.addText("\n", GuiStatics.getCoolSpaceColor());
        infoText.addText(newComp.toString(), GuiStatics.COLOR_GREEN_TEXT);
        infoText.addText("\n\n", GuiStatics.getCoolSpaceColor());
        continue;
      }
      if (origComp != null && newComp == null) {
        infoText.addText(i + 1, GuiStatics.getCoolSpaceColor());
        infoText.addText(":", GuiStatics.getCoolSpaceColor());
        infoText.addText(origComp.getName(), GuiStatics.COLOR_GREEN_TEXT);
        infoText.addText(" -> ", GuiStatics.getCoolSpaceColor());
        infoText.addText("Empty", GuiStatics.COLOR_RED_TEXT);
        infoText.addText("\n\n", GuiStatics.getCoolSpaceColor());
        continue;
      }
      if (origComp.getName().equals(newComp.getName())) {
        infoText.addText(i + 1, GuiStatics.getCoolSpaceColor());
        infoText.addText(":", GuiStatics.getCoolSpaceColor());
        infoText.addText(origComp.getName(), GuiStatics.getCoolSpaceColor());
        infoText.addText("\n", GuiStatics.getCoolSpaceColor());
        infoText.addText(origComp.toString(), GuiStatics.getCoolSpaceColor());
        infoText.addText("\n\n", GuiStatics.getCoolSpaceColor());
      } else {
        infoText.addText(i + 1, GuiStatics.getCoolSpaceColor());
        infoText.addText(":", GuiStatics.getCoolSpaceColor());
        infoText.addText(origComp.getName(), GuiStatics.COLOR_RED_TEXT);
        infoText.addText(" -> ", GuiStatics.getCoolSpaceColor());
        infoText.addText(newComp.getName(), GuiStatics.COLOR_GREEN_TEXT);
        infoText.addText("\n", GuiStatics.getCoolSpaceColor());
        infoText.addText(newComp.toString(), GuiStatics.COLOR_GREEN_TEXT);
        infoText.addText("\n\n", GuiStatics.getCoolSpaceColor());
      }
    }
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
      if (stat != null && ship.getHull().getName().equals(
          stat.getDesign().getHull().getName())) {
        metalUpgradeCost = ship.getUpgradeMetalCost(stat.getDesign());
        prodUpgradeCost = ship.getUpgradeCost(stat.getDesign());
        if (metalUpgradeCost > planet.getMetal()
            + planet.getAmountMetalInGround()) {
          metalUpgradeCost = 0;
          prodUpgradeCost = 0;
        }
        if (metalUpgradeCost > planet.getMetal()) {
          int left = metalUpgradeCost - planet.getMetal();
          metalUpgradeCost = planet.getMetal();
          prodUpgradeCost = prodUpgradeCost + left * 2;
        }
        showDifference(stat.getDesign(), ship);
        if (prodUpgradeCost > 0) {
          upgradeButton.setEnabled(true);
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
      SoundPlayer.playMenuSound();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_UPGRADE)) {
      ShipStat stat = player.getShipStatByName(
          (String) upgradeList.getSelectedItem());
      if (prodUpgradeCost > 0 && stat != null) {
        StarMapUtilities.upgradeShip(getSelectedShip(), stat, player, planet);
        shipListInFleet.setListData(fleet.getShips());
        updatePanels();
        SoundPlayer.playSound(SoundPlayer.REPAIR);
        fleet.setMovesLeft(0);
        SoundPlayer.playMenuSound();
      } else {
        SoundPlayer.playMenuDisabled();
      }
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
        if (stat.getDesign().getHull().getName().equals(
            ship.getHull().getName())
            && !ship.getName().equals(stat.getDesign().getName())
            && !stat.isObsolete()) {
          stats.add(stat);
        }
      }
      ShipStat stat = player.getBestUpgrade(ship);
      if (stat != null && !stat.isObsolete()) {
        stats.remove(stat);
        stats.add(0, stat);
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
