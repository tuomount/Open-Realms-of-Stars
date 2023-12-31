package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.list.FleetListRenderer;
import org.openRealmOfStars.gui.list.ShipListRenderer;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 *
 * Fleet view for handling single fleet orbiting on planet or deep space.
 *
 */
public class FleetView extends BlackPanel implements ListSelectionListener {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

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
   * Planet owner's empire name if fleet is orbiting planet.
   */
  private SpaceLabel ownerLabel;

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
   * Ships in fleet
   */
  private JList<Ship> shipsInFleet;

  /**
   * Other fleets in same space
   */
  private JList<Fleet> fleetsInSpace;

  /**
   * Starbase fleet
   */
  private Fleet starbaseFleet;

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
   * Conquer button for staring planet conquer
   */
  private SpaceButton conquerBtn;

  /**
   * Hail button for calling other fleet
   */
  private SpaceButton hailBtn;
  /**
   * Is view interactive or not
   */
  private boolean interactiveView;

  /**
   * Label for current commander.
   */
  private IconLabel commanderLabel;
  /**
   * Button to access leader view.
   */
  private SpaceButton leaderViewBtn;
  /**
   * Button to access espionage mission view.
   */
  private SpaceButton espionageMissonBtn;
  /**
   * Button to enable auto exploring.
   */
  private SpaceButton exploreBtn;

  /**
   * Button to view upgrade fleet;
   */
  private SpaceButton upgradeBtn;

  /**
   * Planet at north from fleet.
   */
  private Planet northPlanet;
  /**
   * Planet at south from fleet.
   */
  private Planet southPlanet;
  /**
   * Planet at west from fleet.
   */
  private Planet westPlanet;
  /**
   * Planet at east from fleet.
   */
  private Planet eastPlanet;
  /**
   * Fleet view constructor. Fleet view is used when view fleet in deep space
   * or fleet is orbiting a planet.
   * @param planet Planet where fleet is orbiting. If planet is null
   * then fleet is in deep space.
   * @param fleet Fleet orbiting
   * @param fleetList Player Fleet list
   * @param playerInfo Player info who is viewing the fleet
   * @param interactive Is Fleet view interactive for current player
   * @param listener Action listener for commands
   */
  public FleetView(final Planet planet, final Fleet fleet,
      final FleetList fleetList, final PlayerInfo playerInfo,
      final boolean interactive, final ActionListener listener) {
    interactiveView = interactive;
    this.setPlanet(planet);
    this.setFleet(fleet);
    this.setFleetList(fleetList);
    this.setInfo(playerInfo);
    // Background image
    imgBase = new BigImagePanel(planet, true, null);
    imgBase.setPlayer(info);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = null;
    if (this.planet != null) {
      topPanel = new InfoPanel();
      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

      topPanel.add(Box.createRigidArea(new Dimension(15, 25)));
      SpaceGreyPanel panel = new SpaceGreyPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      conquerBtn = null;
      SpaceButton colonizeBtn = null;
      colonistSelection = null;
      metalSelection = null;

      if (planet.getPlanetPlayerInfo() != null) {
        ownerLabel = new SpaceLabel(planet.getPlanetPlayerInfo()
            .getEmpireName());
        if (info != planet.getPlanetPlayerInfo()) {
          conquerBtn = new SpaceButton("Conquer",
              GameCommands.COMMAND_CONQUEST);
          conquerBtn.addActionListener(listener);
          conquerBtn.setEnabled(interactive);
        }
      } else {
        ownerLabel = new SpaceLabel("Uncolonized planet");
        colonizeBtn = new SpaceButton("Colonize",
            GameCommands.COMMAND_COLONIZE);
        colonizeBtn.addActionListener(listener);
        colonizeBtn.setEnabled(interactive);
      }
      panel.add(ownerLabel);
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
      if (colonizeBtn != null) {
        if (planet.isColonizeablePlanet(playerInfo.getRace())) {
          SpaceLabel radWarning = new SpaceLabel("Warning! High radiation!");
          radWarning.setForeground(GuiStatics.COLOR_RED_TEXT);
          panel.add(radWarning);
          panel.add(Box.createRigidArea(new Dimension(5, 5)));
        }
        panel.add(colonizeBtn);
      }
      if (conquerBtn != null) {
        panel.add(conquerBtn);
      }
      if (planet.getPlanetPlayerInfo() != null
          && info == planet.getPlanetPlayerInfo()) {
        colonistSelection = new WorkerProductionPanel(
            GameCommands.COMMAND_COLONIST_MINUS,
            GameCommands.COMMAND_COLONIST_PLUS, Icons.ICON_PEOPLE,
            "Colonist: 10000", "How many colonist/troops is on board of fleet.",
            listener);
        panel.add(colonistSelection);
        metalSelection = new WorkerProductionPanel(
            GameCommands.COMMAND_METAL_CARGO_MINUS,
            GameCommands.COMMAND_METAL_CARGO_PLUS, Icons.ICON_METAL,
            "Metal: 1000000", "How many metal cargo is on board of fleet.",
            listener);
        panel.add(metalSelection);
      }
      topPanel.add(panel);
      topPanel.add(Box.createRigidArea(new Dimension(50, 25)));
      topPanel.setTitle(planet.getName());
    }
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
    fleetNameText.setForeground(GuiStatics.getInfoTextColor());
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
    fleetNameText.setEnabled(interactive);
    eastPanel.add(fleetNameText);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    commanderLabel = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_COMMANDER),
        ": Commander Firstname Surname");
    if (fleet.getCommander() == null) {
      commanderLabel.setText(": No commander");
    } else {
      commanderLabel.setText(": "
          + fleet.getCommander().getMilitaryRank().toString()
          + " " + fleet.getCommander().getName());
    }
    commanderLabel.setAlignmentX(CENTER_ALIGNMENT);
    eastPanel.add(commanderLabel);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    leaderViewBtn = new SpaceButton("Assign leader  ",
        GameCommands.COMMAND_VIEW_LEADERS);
    leaderViewBtn.addActionListener(listener);
    leaderViewBtn.setAlignmentX(CENTER_ALIGNMENT);
    leaderViewBtn.setEnabled(interactive);
    eastPanel.add(leaderViewBtn);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    espionageMissonBtn = new SpaceButton("Espionage mission",
        GameCommands.COMMAND_ESPIONAGE_MISSIONS);
    espionageMissonBtn.addActionListener(listener);
    espionageMissonBtn.setAlignmentX(CENTER_ALIGNMENT);
    espionageMissonBtn.setEnabled(interactive);
    eastPanel.add(espionageMissonBtn);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    exploreBtn = new SpaceButton(" Auto explore ",
        GameCommands.COMMAND_AUTO_EXPLORE);
    exploreBtn.addActionListener(listener);
    exploreBtn.setAlignmentX(CENTER_ALIGNMENT);
    exploreBtn.setEnabled(interactive);
    eastPanel.add(exploreBtn);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    upgradeBtn = new SpaceButton(" Upgrade ships ",
        GameCommands.COMMAND_VIEW_UPGRADE);
    upgradeBtn.addActionListener(listener);
    upgradeBtn.setAlignmentX(CENTER_ALIGNMENT);
    upgradeBtn.setEnabled(interactive);
    eastPanel.add(upgradeBtn);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));

    label = new SpaceLabel("Ships in fleet");
    label.setAlignmentX(CENTER_ALIGNMENT);
    eastPanel.add(label);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    shipsInFleet = new JList<>();
    shipsInFleet.setListData(fleet.getShips());
    shipsInFleet.setCellRenderer(new ShipListRenderer());
    shipsInFleet.setBackground(Color.BLACK);
    shipsInFleet
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    shipsInFleet.addListSelectionListener(this);
    JScrollPane scroll = new JScrollPane(shipsInFleet);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    eastPanel.add(scroll);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceGreyPanel fleetBtns = new SpaceGreyPanel();
    fleetBtns.setLayout(new BoxLayout(fleetBtns, BoxLayout.X_AXIS));
    SpaceButton btn = new SpaceButton("Split",
        GameCommands.COMMAND_SPLIT_THE_FLEET);
    btn.addActionListener(listener);
    btn.setEnabled(interactive);
    fleetBtns.add(btn);
    fleetBtns.add(Box.createRigidArea(new Dimension(5, 5)));
    if (planet != null && planet.getRecycleBonus() > 0) {
      btn = new SpaceButton("Recycle", GameCommands.COMMAND_DESTROY_THE_SHIP);
      btn.setToolTipText("Destroy the ship, recycle some of the metal on ship");
      btn.addActionListener(listener);
    } else {
      btn = new SpaceButton("Scrap", GameCommands.COMMAND_DESTROY_THE_SHIP);
      btn.setToolTipText("Destroy the ship, lose the all metal on ship");
      btn.addActionListener(listener);
    }
    btn.setEnabled(interactive);
    fleetBtns.add(btn);
    fleetBtns.add(Box.createRigidArea(new Dimension(5, 5)));
    hailBtn = new SpaceButton("Hail",
        GameCommands.COMMAND_HAIL_FLEET_PLANET);
    hailBtn.addActionListener(listener);
    hailBtn.setEnabled(!interactive);
    if (!interactiveView && this.fleet != null) {
      if (this.fleet.isPrivateerFleet()) {
        hailBtn.setEnabled(false);
      } else {
        hailBtn.setEnabled(true);
      }
    }
    fleetBtns.add(hailBtn);
    fleetBtns.add(Box.createRigidArea(new Dimension(5, 5)));
    btn = new SpaceButton("Deploy", GameCommands.COMMAND_DEPLOY_STARBASE);
    btn.addActionListener(listener);
    btn.setToolTipText("Deploy starbase");
    btn.setEnabled(interactive);
    fleetBtns.add(btn);

    eastPanel.add(fleetBtns);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    label = new SpaceLabel("Other fleets");
    label.setAlignmentX(CENTER_ALIGNMENT);
    eastPanel.add(label);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    fleetsInSpace = new JList<>();
    updateOtherFleet();
    fleetsInSpace.setCellRenderer(new FleetListRenderer());
    fleetsInSpace.setBackground(Color.BLACK);
    fleetsInSpace
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    scroll = new JScrollPane(fleetsInSpace);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    eastPanel.add(scroll);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    fleetBtns = new SpaceGreyPanel();
    fleetBtns.setLayout(new BoxLayout(fleetBtns, BoxLayout.X_AXIS));

    btn = new SpaceButton("Merge", GameCommands.COMMAND_MERGE_FLEETS);
    btn.addActionListener(listener);
    btn.setEnabled(interactive);
    fleetBtns.add(btn);
    fleetBtns.add(Box.createRigidArea(new Dimension(5, 5)));
    btn = new SpaceButton("Switch", GameCommands.COMMAND_SWITCH_FLEETS);
    btn.addActionListener(listener);
    fleetBtns.add(btn);
    eastPanel.add(fleetBtns);
    eastPanel.add(Box.createRigidArea(new Dimension(5, 175)));

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    this.updatePanel();

    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(eastPanel, BorderLayout.EAST);
    this.add(imgBase, BorderLayout.CENTER);
    if (topPanel != null) {
      this.add(topPanel, BorderLayout.NORTH);
    }

  }

  /**
   * Update other fleet list
   */
  private void updateOtherFleet() {
    ArrayList<Fleet> othFleets = new ArrayList<>();
    for (int i = 0; i < fleetList.getNumberOfFleets(); i++) {
      Fleet ite = fleetList.getByIndex(i);
      if (ite.isStarBaseDeployed()
          && ite.getX() == fleet.getX() && ite.getY() == fleet.getY()) {
        starbaseFleet = ite;
      }
      if (ite.getX() == fleet.getX() && ite.getY() == fleet.getY()
          && !ite.getName().equals(fleet.getName())) {
        othFleets.add(ite);
      }
    }
    Fleet[] otherFleets = othFleets.toArray(new Fleet[othFleets.size()]);
    fleetsInSpace.setListData(otherFleets);
  }

  /**
   * Calculate total amount of metal space
   * @return Metal space in fleet
   */
  private int calculateTotalMetalSpace() {
    int result = fleet.getTotalCargoMetal() + fleet.getFreeSpaceForMetal();
    return result;
  }
  /**
   * Calculate total amount of trooper space
   * @return Trooper space in fleet
   */
  private int calculateTotalTroopSpace() {
    int result = fleet.getTotalCargoColonist();
    if (result % 2 != 0) {
      result = result - 1;
      if (result < 0) {
        result = 0;
      }
    }
    result = result + fleet.getFreeSpaceForColonist();
    return result;
  }
  /**
   * Update Planet view panels
   */
  public void updatePanel() {
    if (planet != null) {
      totalPeople.setText(": " + planet.getTotalPopulation());
      metal.setText(": " + planet.getMetal());
      if (conquerBtn != null) {
        if (fleet.getTrooperShip() != null
          || fleet.getBomberShip() != null) {
          conquerBtn.setEnabled(true);
        } else {
          conquerBtn.setEnabled(false);
        }
      }
    }
    if (!interactiveView && fleet != null) {
      if (fleet.isPrivateerFleet()) {
        hailBtn.setEnabled(false);
      } else {
        hailBtn.setEnabled(true);
      }
      if (starMap != null) {
        PlayerInfo fleetOwner = starMap.getPlayerInfoByFleet(fleet);
        if (fleetOwner.getRace() == SpaceRace.SPACE_MONSTERS) {
          hailBtn.setEnabled(false);
        }
      }
    }
    if (colonistSelection != null) {
      colonistSelection.setText("Colonist: " + fleet.getTotalCargoColonist()
          + "/" + calculateTotalTroopSpace());
    }
    if (metalSelection != null) {
      metalSelection.setText("Metal: " + fleet.getTotalCargoMetal() + "/"
          + calculateTotalMetalSpace());
    }
    if (fleet.getCommander() == null) {
      commanderLabel.setText(": No commander");
      espionageMissonBtn.setEnabled(false);
    } else {
      commanderLabel.setText(": "
          + fleet.getCommander().getMilitaryRank().toString()
          + " " + fleet.getCommander().getName());
      if (!interactiveView) {
        espionageMissonBtn.setEnabled(false);
      } else {
        if (getEspionagePlanet() != null) {
          espionageMissonBtn.setEnabled(true);
        }
      }
    }
    if (interactiveView) {
      if (planet != null && planet.getPlanetPlayerInfo() == info
          && planet.hasCertainBuilding("Space port")) {
        upgradeBtn.setEnabled(true);
      } else {
        upgradeBtn.setEnabled(false);
      }
      exploreBtn.setEnabled(true);
      Mission mission = info.getMissions().getMissionForFleet(fleet.getName());
      if (mission == null) {
        exploreBtn.setText("Auto explore");
      } else {
        exploreBtn.setText("Stop exploring");
      }
    } else {
      exploreBtn.setEnabled(false);
      upgradeBtn.setEnabled(false);
    }
    shipsInFleet.setListData(fleet.getShips());
    updateOtherFleet();

    /*
     * Set the orbiting ships
     */
    Ship[] ships = fleet.getShips();
    BufferedImage[] images = new BufferedImage[ships.length];
    for (int i = 0; i < ships.length; i++) {
      images[i] = ships[i].getHull().getImage();
    }
    imgBase.setShipImage(images);

  }

  /**
   * Check if target planet is suitable for espionage.
   * @param target Target planet
   * @return Target planet if suiteable otherwise null
   */
  private Planet getTargetEspionagePlanet(final Planet target) {
    if (interactiveView && target != null
        && target.getPlanetPlayerInfo() != null
        && target.getPlanetPlayerInfo() != info
        && fleet.getCommander() != null) {
      return target;
    }
    return null;
  }

  /**
   * Get suiteable espionage planet
   * @return Suiteable espionage planet
   */
  public Planet getEspionagePlanet() {
    Planet result = null;
    result = getTargetEspionagePlanet(planet);
    if (result == null) {
      result = getTargetEspionagePlanet(northPlanet);
    }
    if (result == null) {
      result = getTargetEspionagePlanet(eastPlanet);
    }
    if (result == null) {
      result = getTargetEspionagePlanet(southPlanet);
    }
    if (result == null) {
      result = getTargetEspionagePlanet(westPlanet);
    }
    return result;
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
  public void setPlanet(final Planet planet) {
    this.planet = planet;
  }

  /**
   * Handle actions for Fleet view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      this.repaint();
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_AUTO_EXPLORE)
        && interactiveView) {
      Mission mission = info.getMissions().getMissionForFleet(fleet.getName());
      if (mission == null) {
        mission = new Mission(MissionType.EXPLORE, MissionPhase.LOADING,
            fleet.getCoordinate());
        mission.setFleetName(fleet.getName());
        info.getMissions().add(mission);
        SoundPlayer.playMenuSound();
      } else {
        info.getMissions().remove(mission);
        SoundPlayer.playMenuDisabled();
      }
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIST_PLUS)
        && fleet.getFreeSpaceForColonist() > 0 && planet != null
        && planet.takeColonist()) {
      fleet.addColonist();
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_COLONIST_MINUS)
        && fleet.getTotalCargoColonist() > 0 && planet != null) {
      if (planet.getPlanetPlayerInfo().getRace().isEatingFood()) {
        planet.setWorkers(Planet.PRODUCTION_FOOD,
            planet.getWorkers(Planet.PRODUCTION_FOOD) + 1);
        fleet.removeColonist();
      } else {
        planet.setWorkers(Planet.PRODUCTION_WORKERS,
            planet.getWorkers(Planet.PRODUCTION_WORKERS) + 1);
        fleet.removeColonist();
      }
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_METAL_CARGO_PLUS)
        && fleet.getFreeSpaceForMetal() > 0 && planet != null
        && planet.getMetal() > 9) {
      fleet.addMetal();
      planet.setMetal(planet.getMetal() - 10);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_METAL_CARGO_MINUS)
        && fleet.getTotalCargoMetal() > 9 && planet != null) {
      fleet.removeMetal();
      planet.setMetal(planet.getMetal() + 10);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SPLIT_THE_FLEET)
        && shipsInFleet.getSelectedIndices().length > 0
        && fleet != starbaseFleet) {

      Fleet newFleet = fleet.splitFromFleet(false,
          shipsInFleet.getSelectedValuesList());
      if (newFleet != null) {
        fleetList.add(newFleet);
      }

      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MERGE_FLEETS)
        && fleetsInSpace.getSelectedIndices().length > 0
        && fleet != starbaseFleet) {
      for (int i = 0; i < fleetsInSpace.getSelectedIndices().length; i++) {
        Fleet mergeFleet = fleetsInSpace.getSelectedValuesList().get(i);
        if (mergeFleet != starbaseFleet
            && fleet.getNumberOfShip() + mergeFleet.getNumberOfShip()
            <= Fleet.MAX_FLEET_SIZE) {
          int speed1 = mergeFleet.getMovesLeft();
          int speed2 = fleet.getMovesLeft();
          if (speed1 < speed2) {
            fleet.setMovesLeft(speed1);
          }
          if (mergeFleet.getCommander() != null) {
            mergeFleet.getCommander().setJob(Job.UNASSIGNED);
            mergeFleet.setCommander(null);
          }
          for (int j = 0; j < mergeFleet.getNumberOfShip(); j++) {
            Ship ship = mergeFleet.getShipByIndex(j);
            if (ship != null) {
              fleet.addShip(ship);
            }
          }
          int index = fleetList.getIndexByName(mergeFleet.getName());
          if (index > -1) {
            fleetList.remove(index);
            info.getMissions().deleteMissionForFleet(mergeFleet.getName());
          }
        }
      }
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SWITCH_FLEETS)
        && fleetsInSpace.getSelectedIndices().length == 1) {
      Fleet switchFleet = fleetsInSpace.getSelectedValuesList().get(0);
      shipsInFleet.setListData(switchFleet.getShips());
      fleet = switchFleet;
      ArrayList<Fleet> othFleets = new ArrayList<>();
      for (int i = 0; i < fleetList.getNumberOfFleets(); i++) {
        Fleet ite = fleetList.getByIndex(i);
        if (ite.getX() == fleet.getX() && ite.getY() == fleet.getY()
            && !ite.getName().equals(fleet.getName())) {
          othFleets.add(ite);
        }
      }
      Fleet[] otherFleets = othFleets.toArray(new Fleet[othFleets.size()]);
      fleetsInSpace.setListData(otherFleets);
      fleetNameText.setText(fleet.getName());
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_DESTROY_THE_SHIP)
        && shipsInFleet.getSelectedIndices().length > 0) {
      for (int i = 0; i < shipsInFleet.getSelectedIndices().length; i++) {
        Ship ship = shipsInFleet.getSelectedValuesList().get(i);
        if (ship != null) {
          fleet.removeShip(ship);
          if (fleet.getNumberOfShip() == 0
              && fleet.getCommander() != null) {
            fleet.getCommander().assignJob(Job.UNASSIGNED, info);
            fleet.setCommander(null);
          }
          fleetList.recalculateList();
          if (planet != null && planet.getRecycleBonus() > 0) {
            int recycledMetal = ship.getMetalCost() * planet.getRecycleBonus()
                / 100 + ship.getMetal();
            planet.setMetal(planet.getMetal() + recycledMetal);
          }
        }
      }
      SoundPlayer.playSound(SoundPlayer.DISASSEMBLE);
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_DEPLOY_STARBASE)
        && starMap != null) {
      Tile tile = starMap.getTile(fleet.getX(), fleet.getY());
      if (tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
        || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2)) {
        boolean startBuilding = false;
        for (Ship ship : fleet.getShips()) {
          if (ship.getHull().getHullType() == ShipHullType.STARBASE
              && !ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
            if (ship.getHull().getName().equals("Artificial planet")) {
              startBuilding = true;
            }
            if (starbaseFleet == null) {
               starbaseFleet = new Fleet(ship, fleet.getX(), fleet.getY());
               starbaseFleet.setName(fleetList.generateUniqueName(
                   "Deep Space"));
               fleetList.add(starbaseFleet);
               starbaseFleet.setRoute(new Route(starbaseFleet.getX(),
                   starbaseFleet.getY(), starbaseFleet.getX(),
                   starbaseFleet.getY(), Route.ROUTE_DEFEND));
               fleet.removeShip(ship);
               ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
               if (fleet.getNumberOfShip() == 0
                   && fleet.getCommander() != null) {
                 starbaseFleet.setCommander(fleet.getCommander());
                 fleet.setCommander(null);
               }
               fleetList.recalculateList();
            } else if (starbaseFleet.getNumberOfShip()
                < Fleet.MAX_STARBASE_SIZE) {
              starbaseFleet.addShip(ship);
              fleet.removeShip(ship);
              ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
              if (fleet.getNumberOfShip() == 0
                  && fleet.getCommander() != null) {
                fleet.getCommander().assignJob(Job.UNASSIGNED, info);
                fleet.setCommander(null);
              }
              fleetList.recalculateList();
            }
            SoundPlayer.playSound(SoundPlayer.STARBASE);
            updatePanel();
          }
        }
        if (startBuilding) {
          starMap.createArtificialPlanet(starbaseFleet, info);
        }
      }
    }
  }

  /**
   * Get fleet which orbiting
   * @return Fleet
   */
  public Fleet getFleet() {
    return fleet;
  }

  /**
   * Change fleet to view
   * @param fleet New fleet to view
   */
  public void setFleet(final Fleet fleet) {
    this.fleet = fleet;
  }

  /**
   * Get Fleet list for player
   * @return FleetList
   */
  public FleetList getFleetList() {
    return fleetList;
  }

  /**
   * Set Fleet list for view
   * @param fleetList FleetList to change
   */
  public void setFleetList(final FleetList fleetList) {
    this.fleetList = fleetList;
  }

  /**
   * Get PlayerInfo who is viewing the fleet
   * @return PlayerInfo
   */
  public PlayerInfo getInfo() {
    return info;
  }

  /**
   * Set PlayerInfo for viewing the fleet
   * @param info PlayerInfo
   */
  public void setInfo(final PlayerInfo info) {
    this.info = info;
    if (imgBase != null) {
      imgBase.setPlayer(info);
    }
  }

  /**
   * Set starmap for view. Needed for deploying the starbase.
   * @param map StarMap
   */
  public void setStarmap(final StarMap map) {
    starMap = map;
    if (starMap !=  null) {
      Tile tile = starMap.getTile(fleet.getX(), fleet.getY());
      if (tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
        || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2)) {
        imgBase.setTitle("In Deep Space Anchor...");
      }
      PlayerInfo fleetInfo = starMap.getPlayerInfoByFleet(fleet);
      if (fleetInfo != info && conquerBtn != null) {
        conquerBtn.setEnabled(false);
      }
      PlayerInfo fleetOwner = starMap.getPlayerInfoByFleet(fleet);
      if (fleetOwner != null
          && fleetOwner.getRace() == SpaceRace.SPACE_MONSTERS) {
        hailBtn.setEnabled(false);
      }
      int x = fleet.getCoordinate().getX();
      int y = fleet.getCoordinate().getY();
      northPlanet = starMap.getPlanetByCoordinate(x, y - 1);
      southPlanet = starMap.getPlanetByCoordinate(x, y + 1);
      westPlanet = starMap.getPlanetByCoordinate(x - 1, y);
      eastPlanet = starMap.getPlanetByCoordinate(x + 1, y);
      imgBase.setNorthPlanet(northPlanet);
      imgBase.setSouthPlanet(southPlanet);
      imgBase.setWestPlanet(westPlanet);
      imgBase.setEastPlanet(eastPlanet);
      if (getEspionagePlanet() == null) {
        espionageMissonBtn.setEnabled(false);
      }
    }
  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (shipsInFleet.getSelectedIndex() !=  -1) {
      Ship ship = shipsInFleet.getSelectedValue();
      imgBase.setText(ship.getTacticalInfo());
      imgBase.repaint();
    } else {
      imgBase.setText(null);
      imgBase.repaint();
    }
  }
}
