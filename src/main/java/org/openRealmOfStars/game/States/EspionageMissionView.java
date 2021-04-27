package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ProductionListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCombo;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BigImagePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.espionage.EspionageUtility;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;


/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2020  Tuomo Untinen
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
 * Espionage mission view.
 *
 */
public class EspionageMissionView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Farm production panel for colonized planet.
   */
  private WorkerProductionPanel farmPanel;
  /**
   * Metal production panel for colonized planet.
   */
  private WorkerProductionPanel minePanel;
  /**
   * Production production panel for colonized planet.
   */
  private WorkerProductionPanel factoryPanel;
  /**
   * Research production panel for colonized planet.
   */
  private WorkerProductionPanel resePanel;
  /**
   * Tax production panel for colonized planet.
   */
  private WorkerProductionPanel taxPanel;

  /**
   * Culture production panel for colonized planet.
   */
  private IconLabel cultureLabel;

  /**
   * Label for total population for planet.
   */
  private IconLabel totalPeople;
  /**
   * Label for population growth for planet.
   */
  private IconLabel peopleGrowth;
  /**
   * Label for food production.
   */
  private IconLabel farmProd;
  /**
   * Label for metal production.
   */
  private IconLabel mineProd;
  /**
   * Label for production production.
   */
  private IconLabel prodProd;
  /**
   * Label for research production.
   */
  private IconLabel reseProd;
  /**
   * Label for culture production.
   */
  private IconLabel cultProd;
  /**
   * Label for credits production.
   */
  private IconLabel credProd;
  /**
   * Label for colonized planet maintenance costs
   */
  private IconLabel maintenance;
  /**
   * Label for metal which has been mined from the planet and
   * available for building.
   */
  private IconLabel metal;
  /**
   * Label for amount ore in planet which is available for mining.
   */
  private IconLabel metalOre;
  /**
   * Label for happiness
   */
  private IconLabel happiness;
  /**
   * Label for selected construction.
   */
  private IconLabel constructionLabel;
  /**
   * How many buildings are on planet and maximum number of buildings on
   * planet.
   */
  private SpaceLabel buildingLabel;
  /**
   * Estimate how long it takes to build construction.
   */
  private SpaceLabel buildingEstimate;
  /**
   * Label for current governor.
   */
  private IconLabel governorLabel;
  /**
   * Buildings on planet in a list
   */
  private JList<Building> buildingList;

  /**
   * Planet to show
   */
  private Planet planet;

  /**
   * Player trying to interact with planet
   */
  private PlayerInfo info;

  /**
   * Fleet near the planet
   */
  private Fleet fleet;
  /**
   * Mission type selection.
   */
  private SpaceCombo<EspionageMission> missionType;
  /**
   * Mission information text area.
   */
  private InfoTextArea missionInfo;
  /**
   * Planet view constructor. Planet view for viewing planet.
   * @param planet Planet to view
   * @param player Which player is currently playing
   * @param fleet Fleet where is commander as a leader
   * @param listener Action listener for commands
   */
  public EspionageMissionView(final Planet planet, final PlayerInfo player,
      final Fleet fleet, final ActionListener listener) {
    this.setPlanet(planet);
    this.setFleet(fleet);
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, null);
    info = player;
    imgBase.setPlayer(info);
    imgBase.setText(getEspionageStats());
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel northPanel = new InfoPanel();
    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    SpaceGreyPanel topPanel = new SpaceGreyPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    totalPeople = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_PEOPLE), ": 00");
    totalPeople.setToolTipText("Total number of people on planet.");
    totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(totalPeople);
    farmPanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_FARM, GameCommands.COMMAND_PLUS_FARM,
        Icons.ICON_FARM, ": 0",
        "Number of people working as a farmers.", listener);
    farmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    farmPanel.setInteractive(false);
    panel.add(farmPanel);
    minePanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_MINE, GameCommands.COMMAND_PLUS_MINE,
        Icons.ICON_MINE, ": 0",
        "Number of people working as a miners.", listener);
    minePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    minePanel.setInteractive(false);
    panel.add(minePanel);
    factoryPanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_PRODUCTION,
        GameCommands.COMMAND_PLUS_PRODUCTION, Icons.ICON_FACTORY,
        ": 0", "Number of people working as a factory worker.", listener);
    factoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    factoryPanel.setInteractive(false);
    panel.add(factoryPanel);
    resePanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_RESEARCH, GameCommands.COMMAND_PLUS_RESEARCH,
        Icons.ICON_RESEARCH,
        ": 0", "Number of people working as a scientist.", listener);
    resePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    resePanel.setInteractive(false);
    panel.add(resePanel);
    cultureLabel = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_CULTURE), ": 00");
    cultureLabel.setToolTipText(
        "Number of people working as a culture artist.");
    cultureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(cultureLabel);
    topPanel.add(panel);
    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    peopleGrowth = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_PEOPLE), "1000 turns");
    peopleGrowth.setToolTipText("How many turns to population growth.");
    peopleGrowth.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(peopleGrowth);
    farmProd = new IconLabel(null, Icons.getIconByName(Icons.ICON_FARM),
        ": 00");
    farmProd.setToolTipText("Total production of food");
    farmProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(farmProd);
    mineProd = new IconLabel(null, Icons.getIconByName(Icons.ICON_MINE),
        ": 00");
    mineProd.setToolTipText("Total production of metal");
    mineProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(mineProd);
    prodProd = new IconLabel(null, Icons.getIconByName(Icons.ICON_FACTORY),
        ": 00");
    prodProd.setToolTipText("Total production of production");
    prodProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(prodProd);
    reseProd = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_RESEARCH), ": 00");
    reseProd.setToolTipText("Total production of research");
    reseProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(reseProd);
    cultProd = new IconLabel(null, Icons.getIconByName(Icons.ICON_CULTURE),
        ": 00");
    cultProd.setToolTipText("Total production of culture");
    cultProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(cultProd);
    topPanel.add(panel);
    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    credProd = new IconLabel(null, Icons.getIconByName(Icons.ICON_CREDIT),
        ": 00");
    credProd.setToolTipText("Total production of credits");
    credProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(credProd);

    maintenance = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_MAINTENANCE), ": 00");
    maintenance.setToolTipText("Maintenance cost of planet");
    maintenance.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(maintenance);

    taxPanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_TAX, GameCommands.COMMAND_PLUS_TAX,
        Icons.ICON_TAX, ": 10",
        "How many productions are converted to credits", listener);
    taxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    taxPanel.setInteractive(false);
    panel.add(taxPanel);

    metal = new IconLabel(null, Icons.getIconByName(Icons.ICON_METAL),
        ": 0000");
    metal.setToolTipText("Total metal on surface");
    metal.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(metal);
    metalOre = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_METAL_ORE), ": 00000");
    metalOre.setToolTipText("Total metal ore to mine.");
    metalOre.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(metalOre);
    happiness = new IconLabel(null, Icons.getIconByName(Icons.ICON_OKAY),
        ": 00");
    happiness.setToolTipText(planet.getHappinessExplanation());
    happiness.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(happiness);
    topPanel.add(panel);
    topPanel.add(Box.createRigidArea(new Dimension(25, 25)));

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    IconLabel label = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_FACTORY), "Next project");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(label);
    constructionLabel = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_CLOSED),
        "No building project selected.");
    constructionLabel.setToolTipText("Production currently underconstruction");
    constructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    if (planet.getUnderConstruction() != null) {
      constructionLabel.setText(planet.getUnderConstruction().getName());
      constructionLabel.setLeftIcon(planet.getUnderConstruction().getIcon());
    }
    panel.add(constructionLabel);
    panel.add(Box.createRigidArea(new Dimension(60, 5)));
    buildingEstimate = new SpaceLabel("Construction time 1000 turns");
    buildingEstimate.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(buildingEstimate);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    topPanel.add(panel);

    topPanel.add(Box.createRigidArea(new Dimension(10, 25)));

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    buildingLabel = new SpaceLabel("Buildings(00/00)");
    buildingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(buildingLabel);
    buildingList = new JList<>(planet.getBuildingList());
    buildingList.setCellRenderer(new ProductionListRenderer());
    buildingList.setAlignmentX(Component.LEFT_ALIGNMENT);
    buildingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    buildingList.setBackground(Color.BLACK);
    JScrollPane scroll = new JScrollPane(buildingList);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setBackground(Color.BLACK);
    scroll.setPreferredSize(new Dimension(100, 100));
    panel.add(scroll);
    topPanel.add(panel);

    northPanel.add(topPanel);
    northPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceGreyPanel governorPanel = new SpaceGreyPanel();
    governorPanel.setLayout(new BorderLayout());
    governorLabel = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_GOVERNOR),
        ": Planet's governor with very long name");
    if (getPlanet().getGovernor() == null) {
      governorLabel.setText(": No governor");
    } else {
      governorLabel.setText(": " + getPlanet().getGovernor().getName());
    }
    SpaceGreyPanel governorLabelPanel = new SpaceGreyPanel();
    governorLabelPanel.setLayout(new BoxLayout(governorLabelPanel,
        BoxLayout.X_AXIS));
    governorLabelPanel.add(Box.createRigidArea(new Dimension(15, 5)));
    governorLabelPanel.add(governorLabel);
    governorPanel.add(governorLabelPanel, BorderLayout.WEST);
    northPanel.add(governorPanel);
    northPanel.setTitle(planet.getName());

    InvisiblePanel eastPanel = new InvisiblePanel(imgBase);
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

    missionType = new SpaceCombo<EspionageMission>(
        EspionageUtility.getAvailableMissionTypes(planet, info));
    missionType.setSelectedIndex(0);
    missionType.addActionListener(listener);
    missionType.setActionCommand(GameCommands.COMMAND_ESPIONAGE_MISSIONS);
    eastPanel.add(missionType);
    missionInfo = new InfoTextArea(5, 35);
    missionInfo.setFont(GuiStatics.getFontCubellanSmaller());
    missionInfo.setEditable(false);
    missionInfo.setWrapStyleWord(true);
    missionInfo.setLineWrap(true);
    missionInfo.setCharacterWidth(7);
    missionInfo.setMaximumSize(new Dimension(400, 200));
    eastPanel.add(missionInfo);
    SpaceButton btn = new SpaceButton("Execute mission",
        GameCommands.COMMAND_EXECUTE_MISSION);
    btn.addActionListener(listener);
    eastPanel.add(btn);
    btn = new SpaceButton("Hail Planet",
        GameCommands.COMMAND_HAIL_FLEET_PLANET);
    btn.addActionListener(listener);
    eastPanel.add(btn);

    imgBase.setLayout(new BorderLayout());
    if (planet.getPlanetOwnerIndex() != -1) {
      imgBase.add(eastPanel, BorderLayout.EAST);
    }

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    btn = new SpaceButton("Back to fleet view",
        GameCommands.COMMAND_VIEW_FLEET);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    this.updatePanel();

    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(imgBase, BorderLayout.CENTER);
    if (planet.getPlanetOwnerIndex() != -1) {
      this.add(northPanel, BorderLayout.NORTH);
    }

  }

  /**
   * Get Espionage stats.
   * @return Stats as a string
   */
  public String getEspionageStats() {
    StringBuilder sb = new StringBuilder();
    sb.append("Planet's espionage detection: ");
    sb.append(planet.getCloakingDetectionLvl());
    sb.append("\n");
    sb.append("Agent's covert value: ");
    sb.append(fleet.getFleetCloackingValue());
    sb.append("\n");
    sb.append("Agent's espionage: ");
    sb.append(fleet.getEspionageBonus());
    sb.append("\n");
    Leader agent = fleet.getCommander();
    sb.append(agent.getCallName());
    sb.append("\n");
    for (Perk perk : agent.getPerkList()) {
      sb.append(" * ");
      sb.append(perk.getName());
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Update espionage mission view panels
   */
  public void updatePanel() {
    totalPeople.setText(": " + planet.getTotalPopulation());
    farmPanel.setText(": " + planet.getWorkers(Planet.FOOD_FARMERS));
    minePanel.setText(": " + planet.getWorkers(Planet.METAL_MINERS));
    factoryPanel.setText(": " + planet.getWorkers(Planet.PRODUCTION_WORKERS));
    resePanel.setText(": " + planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    cultureLabel.setText(": " + planet.getWorkers(Planet.CULTURE_ARTIST));

    int peopleGrow = planet.getTotalProduction(Planet.PRODUCTION_POPULATION);
    if (peopleGrow > 0) {
      if (planet.exceedRadiation()) {
        peopleGrowth.setText("Never");
        peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_RADIATION));
      } else {
        peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
        peopleGrowth.setText(peopleGrow + " turns.");
      }
    } else if (peopleGrow < 0) {
      peopleGrow = peopleGrow * -1;
      peopleGrowth.setText(peopleGrow + " turns.");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_DEATH));
    } else {
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetPlayerInfo().getRace() == SpaceRace.MECHIONS) {
        peopleGrowth.setText("no growth");
        peopleGrowth.setToolTipText(
            "Mechions needs to be built to get more population.");
      } else {
        peopleGrowth.setText("stable ");
      }
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
    }
    if (planet.getTotalPopulation() >= planet.getGroundSize()) {
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_NO_MORE_PEOPLE));
      peopleGrowth.setToolTipText(
          "Planet population is at maximum!");
    }
    farmProd.setText(": " + planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    mineProd.setText(": " + planet.getTotalProduction(Planet.PRODUCTION_METAL));
    prodProd.setText(
        ": " + planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    reseProd
        .setText(": " + planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    cultProd
        .setText(": " + planet.getTotalProduction(Planet.PRODUCTION_CULTURE));

    credProd
        .setText(": " + planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    maintenance.setText(": " + planet.getMaintenanceCost());
    taxPanel.setText(": " + planet.getTax());
    metal.setText(": " + planet.getMetal());
    metalOre.setText(": " + planet.getAmountMetalInGround());
    int happyValue = planet.calculateHappiness();
    if (planet.getPlanetPlayerInfo() != null
        && planet.getPlanetPlayerInfo().getGovernment().isImmuneToHappiness()) {
      happiness.setText(": -");
      happiness.setToolTipText(
          "<html>Government is single minded, no happiness or sadness.</html>");
    } else {
      happiness.setText(": " + happyValue);
      happiness.setToolTipText(planet.getHappinessExplanation());
    }
    happiness.setLeftIcon(Icons.getHappyFace(happyValue));
    buildingLabel.setText("Buildings(" + planet.getUsedPlanetSize() + "/"
        + planet.getGroundSize() + ")");

    Construction building = planet.getUnderConstruction();
    if (building != null) {
      buildingEstimate.setText("Building time "
          + planet.getProductionTimeAsString(building));
    }

    buildingList.setListData(planet.getBuildingList());
    if (missionType.getSelectedItem() != null) {
      EspionageMission mission =
          (EspionageMission) missionType.getSelectedItem();
      missionInfo.setText(mission.getDescription() + "\nDetection chance: "
      + EspionageUtility.calculateDetectionSuccess(planet, fleet, mission)
      + "%\nSuccess chance: "
      + EspionageUtility.calculateSuccess(planet, fleet, mission)
      + "%");
    }
    Mission mission = info.getMissions().getMissionForFleet(
        fleet.getName(), MissionType.ESPIONAGE_MISSION);
    if (mission != null) {
      EspionageMission espionage = mission.getEspionageType();
      if (espionage != null) {
        missionInfo.setText(espionage.getDescription() + "\nDetection chance: "
            + EspionageUtility.calculateDetectionSuccess(planet, fleet,
                espionage) + "%\nSuccess chance: "
            + EspionageUtility.calculateSuccess(planet, fleet, espionage)
            + "%\nEspionage activated");
      }
    }
    this.repaint();
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
   * @return the fleet
   */
  public Fleet getFleet() {
    return fleet;
  }

  /**
   * @param fleet the fleet to set
   */
  public void setFleet(final Fleet fleet) {
    this.fleet = fleet;
  }

  /**
   * Handle actions for espionage mission view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_ESPIONAGE_MISSIONS)) {
      updatePanel();
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_EXECUTE_MISSION)) {
      if (missionType.getSelectedItem() != null) {
        SoundPlayer.playMenuSound();
        EspionageMission espionage =
            (EspionageMission) missionType.getSelectedItem();
        Mission mission = new Mission(MissionType.ESPIONAGE_MISSION,
            MissionPhase.EXECUTING, planet.getCoordinate());
        mission.setFleetName(fleet.getName());
        mission.setEspionageType(espionage);
        mission.setTargetPlanet(planet.getName());
        info.getMissions().add(mission);
        fleet.setMovesLeft(0);
      } else {
        SoundPlayer.playMenuDisabled();
      }
      updatePanel();
    }
  }

}
