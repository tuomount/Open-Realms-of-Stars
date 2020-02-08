package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ProductionListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.BaseInfoTextArea;
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
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2020  Tuomo Untinen
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
 * Planet view for handling single planet production and space dock.
 *
 */
public class PlanetView extends BlackPanel {

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
   * JCombobox for selected construction for planet
   */
  private JComboBox<Construction> constructionSelect;
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
   * Button to access leader view.
   */
  private SpaceButton leaderViewBtn;
  /**
   * New construction information text.
   */
  private BaseInfoTextArea productionInfo;
  /**
   * Building information text.
   */
  private InfoTextArea buildingInfo;
  /**
   * Buildings on planet in a list
   */
  private JList<Building> buildingList;
  /**
   * Button for demolish buildings from planet. This same button
   * can be used recycle buildings.
   */
  private SpaceButton demolishBuildingBtn;
  /**
   * Button for rushing buildings with credits.
   */
  private SpaceButton rushWithCreditsBtn;
  /**
   * Button for rushing buildings with population.
   */
  private SpaceButton rushWithPopulationBtn;

  /**
   * Planet to show
   */
  private Planet planet;

  /**
   * Allow player to handle planet via UI
   */
  private boolean allowHandling;

  /**
   * Player trying to interact with planet
   */
  private PlayerInfo info;
  /**
   * Planet view constructor. Planet view for viewing planet.
   * @param planet Planet to view
   * @param interactive If true view contains full planet controls.
   * @param player Which player is currently playing
   * @param listener Action listener for commands
   */
  public PlanetView(final Planet planet, final boolean interactive,
      final PlayerInfo player, final ActionListener listener) {
    this.setPlanet(planet);
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true, null);
    imgBase.setPlayer(player);
    allowHandling = interactive;
    info = player;
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
    farmPanel.setInteractive(interactive);
    panel.add(farmPanel);
    minePanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_MINE, GameCommands.COMMAND_PLUS_MINE,
        Icons.ICON_MINE, ": 0",
        "Number of people working as a miners.", listener);
    minePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    minePanel.setInteractive(interactive);
    panel.add(minePanel);
    factoryPanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_PRODUCTION,
        GameCommands.COMMAND_PLUS_PRODUCTION, Icons.ICON_FACTORY,
        ": 0", "Number of people working as a factory worker.", listener);
    factoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    factoryPanel.setInteractive(interactive);
    panel.add(factoryPanel);
    resePanel = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_RESEARCH, GameCommands.COMMAND_PLUS_RESEARCH,
        Icons.ICON_RESEARCH,
        ": 0", "Number of people working as a scientist.", listener);
    resePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    resePanel.setInteractive(interactive);
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
    taxPanel.setInteractive(interactive);
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
        Icons.getIconByName(Icons.ICON_FACTORY), "Next project:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(label);
    constructionSelect = new JComboBox<>(this.planet.getProductionList());
    constructionSelect.addActionListener(listener);
    constructionSelect.setActionCommand(GameCommands.COMMAND_PRODUCTION_LIST);
    constructionSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    constructionSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    constructionSelect.setBorder(new SimpleBorder());
    constructionSelect.setFont(GuiStatics.getFontCubellan());
    constructionSelect.setRenderer(new ProductionListRenderer());
    if (planet.getUnderConstruction() != null) {
      Construction[] list = this.planet.getProductionList();
      for (int i = 0; i < list.length; i++) {
        if (list[i].getName().equals(planet.getUnderConstruction().getName())) {
          constructionSelect.setSelectedIndex(i);
          break;
        }
      }
    }
    constructionSelect.setEditable(false);
    constructionSelect.setEnabled(interactive);
    constructionSelect.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(constructionSelect);
    panel.add(Box.createRigidArea(new Dimension(60, 5)));
    buildingEstimate = new SpaceLabel("1000 turns");
    buildingEstimate.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(buildingEstimate);
    SpaceGreyPanel panelX = new SpaceGreyPanel();
    panelX.setLayout(new BoxLayout(panelX, BoxLayout.X_AXIS));
    panelX.setAlignmentX(Component.LEFT_ALIGNMENT);
    label = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_CREDIT), "Rushing options:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(label);
    rushWithCreditsBtn = new SpaceButton("Purchase",
        GameCommands.COMMAND_RUSH_WITH_CREDITS);
    rushWithCreditsBtn.addActionListener(listener);
    rushWithCreditsBtn.setEnabled(false);
    panelX.add(rushWithCreditsBtn);
    panelX.add(Box.createRigidArea(new Dimension(5, 5)));
    rushWithPopulationBtn = new SpaceButton("Enslave",
        GameCommands.COMMAND_RUSH_WITH_POPULATION);
    rushWithPopulationBtn.addActionListener(listener);
    rushWithPopulationBtn.setEnabled(false);
    panelX.add(rushWithPopulationBtn);
    panel.add(panelX);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    topPanel.add(panel);

    topPanel.add(Box.createRigidArea(new Dimension(10, 25)));
    productionInfo = new BaseInfoTextArea(5, 35);
    productionInfo.setFont(GuiStatics.getFontCubellanSmaller());
    productionInfo.setEditable(false);
    JScrollPane scroll = new JScrollPane(productionInfo);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setBackground(Color.BLACK);
    topPanel.add(scroll);
    topPanel.add(Box.createRigidArea(new Dimension(15, 25)));

    northPanel.add(topPanel);
    northPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceGreyPanel governorPanel = new SpaceGreyPanel();
    governorPanel.setLayout(new BoxLayout(governorPanel, BoxLayout.X_AXIS));
    governorPanel.add(Box.createRigidArea(new Dimension(16, 5)));
    governorLabel = new IconLabel(null,
        Icons.getIconByName(Icons.ICON_GOVERNOR),
        ": Planet's governor with very extra extra long name with surname");
    if (getPlanet().getGovernor() == null) {
      governorLabel.setText(": No governor");
    } else {
      governorLabel.setText(": " + getPlanet().getGovernor().getName());
    }
    governorLabel.setAlignmentX(RIGHT_ALIGNMENT);
    governorPanel.add(governorLabel);
    leaderViewBtn = new SpaceButton("Assign leader  ",
        GameCommands.COMMAND_VIEW_LEADERS);
    leaderViewBtn.addActionListener(listener);
    leaderViewBtn.setAlignmentX(RIGHT_ALIGNMENT);
    governorPanel.add(leaderViewBtn);
    governorPanel.setAlignmentX(RIGHT_ALIGNMENT);
    governorPanel.add(Box.createRigidArea(new Dimension(20, 5)));
    northPanel.add(governorPanel);
    northPanel.setTitle(planet.getName());

    InvisiblePanel eastPanel = new InvisiblePanel(imgBase);
    buildingLabel = new SpaceLabel("Buildings(00/00):");
    buildingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    eastPanel.add(buildingLabel);
    buildingList = new JList<>(planet.getBuildingList());
    buildingList.setCellRenderer(new ProductionListRenderer());
    buildingList.setAlignmentX(Component.LEFT_ALIGNMENT);
    buildingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    buildingList.setBackground(Color.BLACK);
    scroll = new JScrollPane(buildingList);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setBackground(Color.BLACK);
    scroll.setPreferredSize(new Dimension(200, 200));
    eastPanel.add(scroll);
    buildingInfo = new InfoTextArea(5, 35);
    buildingInfo.setFont(GuiStatics.getFontCubellanSmaller());
    buildingInfo.setEditable(false);
    eastPanel.add(buildingInfo);
    String demoBtnText = "Demolish";
    if (planet.getRecycleBonus() > 0) {
      demoBtnText = "Recycle";
    }
    demolishBuildingBtn = new SpaceButton(demoBtnText,
        GameCommands.COMMAND_DEMOLISH_BUILDING);
    demolishBuildingBtn
        .setSpaceIcon(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
    demolishBuildingBtn.addActionListener(listener);
    demolishBuildingBtn.setEnabled(interactive);
    eastPanel.add(demolishBuildingBtn);
    if (!interactive) {
      SpaceButton btn = new SpaceButton("Hail",
          GameCommands.COMMAND_HAIL_FLEET_PLANET);
      btn.addActionListener(listener);
      btn.setEnabled(!interactive);
      eastPanel.add(btn);
    }

    imgBase.setLayout(new BorderLayout());
    if (planet.getPlanetOwnerIndex() != -1) {
      imgBase.add(eastPanel, BorderLayout.EAST);
    }

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
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
   * Update Planet view panels
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
        + planet.getGroundSize() + "):");

    Construction building = (Construction) constructionSelect.getSelectedItem();
    buildingEstimate.setText(planet.getProductionTimeAsString(building));
    int productionTime = planet.getProductionTime(building);
    int rushCost = planet.getRushingCost(building);
    if (rushCost > 0 && allowHandling && productionTime > 1) {
      if ((info.getRace().hasCreditRush()
          || info.getGovernment().hasCreditRush())
          && rushCost <= info.getTotalCredits()
          && planet.getUnderConstruction() != null) {
        rushWithCreditsBtn.setEnabled(true);
        rushWithCreditsBtn.setToolTipText("Rush construction with " + rushCost
            + " credits!");
      }
      if ((info.getRace().hasPopulationRush()
          || info.getGovernment().hasPopulationRush())
          && planet.getUnderConstruction() != null) {
        int populationCost = rushCost / Planet.POPULATION_RUSH_COST + 1;
        if (planet.getTotalPopulation() > populationCost) {
          rushWithPopulationBtn.setEnabled(true);
          rushWithPopulationBtn.setToolTipText("Rush construction with "
              + populationCost + " population!");
        }
      }
    } else {
      rushWithCreditsBtn.setEnabled(false);
      rushWithPopulationBtn.setEnabled(false);
      rushWithCreditsBtn.setToolTipText(null);
      rushWithPopulationBtn.setToolTipText(null);
    }

    SpaceRace race = null;
    if (planet.getPlanetPlayerInfo() != null) {
      race = planet.getPlanetPlayerInfo().getRace();
    }
    if (building instanceof Building) {
      Building newBuilding = (Building) building;
      productionInfo.setText(newBuilding.getFullDescription(race));
    } else {
      productionInfo.setText(building.getFullDescription());
    }
    buildingList.setListData(planet.getBuildingList());
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
   * Handle actions for Planet view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      Building building = buildingList.getSelectedValue();
      if (building != null) {
        SpaceRace race = null;
        if (planet.getPlanetPlayerInfo() != null) {
          race = planet.getPlanetPlayerInfo().getRace();
        }
        String tmp = building.getFullDescription(race);
        if (!tmp.equals(buildingInfo.getText())) {
          buildingInfo.setText(tmp);
          this.repaint();
        }
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_DEMOLISH_BUILDING)) {
      Building building = buildingList.getSelectedValue();
      if (building != null) {
        planet.removeBuilding(building);
        SoundPlayer.playMenuSound();
        SoundPlayer.playSound(SoundPlayer.EXPLOSION_SMALL);
        SoundPlayer.playSound(SoundPlayer.DESTROY_BUILDING);
        updatePanel();
      }
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_RUSH_WITH_CREDITS)) {
      planet.doRush(true, info);
      SoundPlayer.playSound(SoundPlayer.COINS);
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_RUSH_WITH_POPULATION)) {
      planet.doRush(false, info);
      SoundPlayer.playSound(SoundPlayer.WHIP);
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_MINUS_FARM)
        && planet.getWorkers(Planet.FOOD_FARMERS) > 0) {
      planet.setWorkers(Planet.FOOD_FARMERS,
          planet.getWorkers(Planet.FOOD_FARMERS) - 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) + 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_PLUS_FARM)
        && planet.getWorkers(Planet.CULTURE_ARTIST) > 0) {
      planet.setWorkers(Planet.FOOD_FARMERS,
          planet.getWorkers(Planet.FOOD_FARMERS) + 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) - 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_MINUS_MINE)
        && planet.getWorkers(Planet.METAL_MINERS) > 0) {
      planet.setWorkers(Planet.METAL_MINERS,
          planet.getWorkers(Planet.METAL_MINERS) - 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) + 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_PLUS_MINE)
        && planet.getWorkers(Planet.CULTURE_ARTIST) > 0) {
      planet.setWorkers(Planet.METAL_MINERS,
          planet.getWorkers(Planet.METAL_MINERS) + 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) - 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_MINUS_PRODUCTION)
        && planet.getWorkers(Planet.PRODUCTION_WORKERS) > 0) {
      int originalProd = planet.getTotalProduction(
          Planet.PRODUCTION_PRODUCTION);
      planet.setWorkers(Planet.PRODUCTION_WORKERS,
          planet.getWorkers(Planet.PRODUCTION_WORKERS) - 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) + 1);
      if (planet.getTax()
          > planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)) {
        int newProd = planet.getTotalProduction(
            Planet.PRODUCTION_PRODUCTION);
        int decrease = originalProd - newProd;
        planet.setTax(planet.getTax() - decrease, false);
      }
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_PLUS_PRODUCTION)
        && planet.getWorkers(Planet.CULTURE_ARTIST) > 0) {
      planet.setWorkers(Planet.PRODUCTION_WORKERS,
          planet.getWorkers(Planet.PRODUCTION_WORKERS) + 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) - 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_MINUS_RESEARCH)
        && planet.getWorkers(Planet.RESEARCH_SCIENTIST) > 0) {
      planet.setWorkers(Planet.RESEARCH_SCIENTIST,
          planet.getWorkers(Planet.RESEARCH_SCIENTIST) - 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) + 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_PLUS_RESEARCH)
        && planet.getWorkers(Planet.CULTURE_ARTIST) > 0) {
      planet.setWorkers(Planet.RESEARCH_SCIENTIST,
          planet.getWorkers(Planet.RESEARCH_SCIENTIST) + 1);
      planet.setWorkers(Planet.CULTURE_ARTIST,
          planet.getWorkers(Planet.CULTURE_ARTIST) - 1);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_MINUS_TAX)) {
      planet.setTax(planet.getTax() - 1, false);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_PLUS_TAX)) {
      planet.setTax(planet.getTax() + 1, false);
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_PRODUCTION_LIST)) {
      planet.setUnderConstruction(
          (Construction) constructionSelect.getSelectedItem());
      SoundPlayer.playMenuSound();
      updatePanel();
    }
  }
}
