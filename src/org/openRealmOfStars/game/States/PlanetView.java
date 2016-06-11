package org.openRealmOfStars.game.States;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JList;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BigImagePanel;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.ListRenderers.ProductionListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.starMap.planet.Building;
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
 * Planet view for handling single planet production and space dock.
 * 
 */
public class PlanetView extends BlackPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private WorkerProductionPanel farmPanel;
  private WorkerProductionPanel minePanel;
  private WorkerProductionPanel factoryPanel;
  private WorkerProductionPanel resePanel;
  
  private IconLabel cultureLabel;
  
  private IconLabel totalPeople;
  private IconLabel peopleGrowth;
  private IconLabel farmProd;
  private IconLabel mineProd;
  private IconLabel prodProd;
  private IconLabel reseProd;
  private IconLabel cultProd;
  private IconLabel credProd;
  private IconLabel metal;
  private IconLabel metalOre;
  private JComboBox<Building> productionSelect;
  private TransparentLabel buildingLabel;
  private TransparentLabel buildingEstimate;
  
  /**
   * Planet to show
   */
  private Planet planet;
  
  public PlanetView(Planet planet,ActionListener listener) {
    this.setPlanet(planet);
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = new InfoPanel();
    topPanel.setLayout(new GridLayout(1, 0));
    
    InvisiblePanel invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    totalPeople = new IconLabel(invis,Icons.getIconByName(Icons.ICON_PEOPLE), 
        ": "+planet.getTotalPopulation());
    totalPeople.setToolTipText("Total number of people on planet.");
    totalPeople.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(totalPeople);
    farmPanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_FARM, GameCommands.COMMAND_PLUS_FARM, 
        Icons.ICON_FARM, ": "+planet.getWorkers(Planet.FOOD_FARMERS),
        "Number of people working as a farmers.",listener);
    farmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(farmPanel);
    minePanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_MINE, GameCommands.COMMAND_PLUS_MINE, 
        Icons.ICON_MINE, ": "+planet.getWorkers(Planet.METAL_MINERS),
        "Number of people working as a miners.",listener);
    minePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(minePanel);
    factoryPanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_PRODUCTION, GameCommands.COMMAND_PLUS_PRODUCTION, 
        Icons.ICON_FACTORY, ": "+planet.getWorkers(Planet.PRODUCTION_WORKERS),
        "Number of people working as a factory worker.",listener);
    factoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(factoryPanel);
    resePanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_RESEARCH, GameCommands.COMMAND_PLUS_RESEARCH, 
        Icons.ICON_RESEARCH, ": "+planet.getWorkers(Planet.RESEARCH_SCIENTIST),
        "Number of people working as a scientist.",listener);
    resePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(resePanel);
    cultureLabel = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CULTURE),
        ": "+planet.getWorkers(Planet.CULTURE_ARTIST));
    cultureLabel.setToolTipText("Number of people producing culture.");
    cultureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(cultureLabel);
    topPanel.add(invis);

    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    peopleGrowth = new IconLabel(invis, Icons.getIconByName(Icons.ICON_PEOPLE),
        "10 turns");
    int peopleGrow = planet.getTotalProduction(Planet.PRODUCTION_POPULATION);
    if (peopleGrow > 0) {
      peopleGrowth.setText(peopleGrow+" turns.");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
    } else if (peopleGrow < 0) {
      peopleGrow = peopleGrow *-1;
      peopleGrowth.setText(peopleGrow+" turns.");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_DEATH));
    } else {
      peopleGrowth.setText("stable ");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
    }
    peopleGrowth.setToolTipText("How many turns to population growth.");
    peopleGrowth.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(peopleGrowth);
    farmProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_FARM), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    farmProd.setToolTipText("Total production of food");
    farmProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(farmProd);
    mineProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_MINE), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_METAL));
    mineProd.setToolTipText("Total production of metal");
    mineProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(mineProd);
    prodProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_FACTORY), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    prodProd.setToolTipText("Total production of production");
    prodProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(prodProd);
    reseProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_RESEARCH), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    reseProd.setToolTipText("Total production of research");
    reseProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(reseProd);
    cultProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CULTURE), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    cultProd.setToolTipText("Total production of culture");
    cultProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(cultProd);
    topPanel.add(invis);


    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    credProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CREDIT), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    credProd.setToolTipText("Total production of credits");
    credProd.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(credProd);
    metal = new IconLabel(invis,Icons.getIconByName(Icons.ICON_METAL), 
        ": "+planet.getMetal());
    metal.setToolTipText("Total metal on surface");
    metal.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(metal);
    metalOre = new IconLabel(invis,Icons.getIconByName(Icons.ICON_METAL_ORE), 
        ": "+planet.getAmountMetalInGround());
    metalOre.setToolTipText("Total metal ore to mine.");
    metalOre.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(metalOre);
    topPanel.add(invis);

    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    IconLabel label = new IconLabel(invis,Icons.getIconByName(Icons.ICON_FACTORY), 
        "Next project:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    invis.add(label);
    productionSelect = new JComboBox<>(this.planet.getProductionList());
    productionSelect.addActionListener(listener);
    productionSelect.setActionCommand(GameCommands.COMMAND_PRODUCTION_LIST);
    productionSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    productionSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    productionSelect.setBorder(new SimpleBorder());
    productionSelect.setFont(GuiStatics.getFontCubellan());
    productionSelect.setRenderer(new ProductionListRenderer());
    productionSelect.setEditable(false);
    invis.add(productionSelect);
    invis.add(Box.createRigidArea(new Dimension(60,5)));
    buildingEstimate = new TransparentLabel(topPanel,
        planet.getProductionTime((Building) productionSelect.getSelectedItem()));
    invis.add(buildingEstimate);
    invis.add(Box.createRigidArea(new Dimension(60,25)));
    
    topPanel.add(invis);
    
    

    topPanel.setTitle(planet.getName());
    
    InvisiblePanel eastPanel = new InvisiblePanel(imgBase);
    if (planet != null) {
      buildingLabel = new TransparentLabel(eastPanel,
          "Buildings("+planet.getUsedPlanetSize()+"/"+planet.getGroundSize()+"):");
      buildingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
      eastPanel.add(buildingLabel);
      JList<Building> buildingList = new JList<>(planet.getBuildingList());
      buildingList.setCellRenderer(new ProductionListRenderer());
      eastPanel.add(buildingList);
      imgBase.setLayout(new BorderLayout());
      if (planet.getPlanetOwnerIndex() != -1) {
        imgBase.add(eastPanel,BorderLayout.EAST);
      }
    }
    
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
    if (planet.getPlanetOwnerIndex() != -1) {
      this.add(topPanel,BorderLayout.NORTH);
    }

  }
  
  /**
   * Update Planet view panels
   */
  public void updatePanel() {
    totalPeople.setText(": "+planet.getTotalPopulation());
    farmPanel.setText(": "+planet.getWorkers(Planet.FOOD_FARMERS));
    minePanel.setText(": "+planet.getWorkers(Planet.METAL_MINERS));
    factoryPanel.setText(": "+planet.getWorkers(Planet.PRODUCTION_WORKERS));
    resePanel.setText(": "+planet.getWorkers(Planet.RESEARCH_SCIENTIST));
    cultureLabel.setText(": "+planet.getWorkers(Planet.CULTURE_ARTIST));
    
    int peopleGrow = planet.getTotalProduction(Planet.PRODUCTION_POPULATION);
    if (peopleGrow > 0) {
      peopleGrowth.setText(peopleGrow+" turns.");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
    } else if (peopleGrow < 0) {
      peopleGrow = peopleGrow *-1;
      peopleGrowth.setText(peopleGrow+" turns.");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_DEATH));
    } else {
      peopleGrowth.setText("stable ");
      peopleGrowth.setLeftIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
    }
    farmProd.setText(": "+planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    mineProd.setText(": "+planet.getTotalProduction(Planet.PRODUCTION_METAL));
    prodProd.setText(": "+planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    reseProd.setText(": "+planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    cultProd.setText(": "+planet.getTotalProduction(Planet.PRODUCTION_CULTURE));

    credProd.setText(": "+planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    metal.setText(": "+planet.getMetal());
    metalOre.setText(": "+planet.getAmountMetalInGround());
    buildingLabel.setText(
        "Buildings("+planet.getUsedPlanetSize()+"/"+planet.getGroundSize()+"):");
    
    buildingEstimate.setText(
      planet.getProductionTime((Building) productionSelect.getSelectedItem()));


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
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_MINUS_FARM) && planet.getWorkers(Planet.FOOD_FARMERS) >0) {
      planet.setWorkers(Planet.FOOD_FARMERS, planet.getWorkers(Planet.FOOD_FARMERS)-1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)+1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_PLUS_FARM) && planet.getWorkers(Planet.CULTURE_ARTIST) >0) {
      planet.setWorkers(Planet.FOOD_FARMERS, planet.getWorkers(Planet.FOOD_FARMERS)+1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)-1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_MINUS_MINE) && planet.getWorkers(Planet.METAL_MINERS) >0) {
      planet.setWorkers(Planet.METAL_MINERS, planet.getWorkers(Planet.METAL_MINERS)-1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)+1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_PLUS_MINE) && planet.getWorkers(Planet.CULTURE_ARTIST) >0) {
      planet.setWorkers(Planet.METAL_MINERS, planet.getWorkers(Planet.METAL_MINERS)+1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)-1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_MINUS_PRODUCTION) && planet.getWorkers(Planet.PRODUCTION_WORKERS) >0) {
      planet.setWorkers(Planet.PRODUCTION_WORKERS, planet.getWorkers(Planet.PRODUCTION_WORKERS)-1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)+1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_PLUS_PRODUCTION) && planet.getWorkers(Planet.CULTURE_ARTIST) >0) {
      planet.setWorkers(Planet.PRODUCTION_WORKERS, planet.getWorkers(Planet.PRODUCTION_WORKERS)+1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)-1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_MINUS_RESEARCH) && planet.getWorkers(Planet.RESEARCH_SCIENTIST) >0) {
      planet.setWorkers(Planet.RESEARCH_SCIENTIST, planet.getWorkers(Planet.RESEARCH_SCIENTIST)-1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)+1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(
        GameCommands.COMMAND_PLUS_RESEARCH) && planet.getWorkers(Planet.CULTURE_ARTIST) >0) {
      planet.setWorkers(Planet.RESEARCH_SCIENTIST, planet.getWorkers(Planet.RESEARCH_SCIENTIST)+1);
      planet.setWorkers(Planet.CULTURE_ARTIST, planet.getWorkers(Planet.CULTURE_ARTIST)-1);
      updatePanel();
    }
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_PRODUCTION_LIST)) {
      updatePanel();
    }
  }
}
