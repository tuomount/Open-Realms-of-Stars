package org.openRealmOfStars.game.States;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.BigImagePanel;
import org.openRealmOfStars.gui.BlackPanel;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.starMap.Planet;

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
  
  private IconLabel peopleGrowth;
  private IconLabel farmProd;
  private IconLabel mineProd;
  private IconLabel prodProd;
  private IconLabel reseProd;
  private IconLabel cultProd;
  private IconLabel credProd;
  private IconLabel metal;
  private IconLabel metalOre;
  
  public PlanetView(Planet planet,ActionListener listener) {
    // Background image
    BigImagePanel imgBase = new BigImagePanel(planet, true);
    this.setLayout(new BorderLayout());

    // Top Panel
    InfoPanel topPanel = new InfoPanel();
    topPanel.setLayout(new GridLayout(1, 0));
    
    InvisiblePanel invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    IconLabel totalPeople = new IconLabel(invis,
        Icons.getIconByName(Icons.ICON_PEOPLE), ": "+planet.getTotalPopulation());
    totalPeople.setToolTipText("Total number of people on planet.");
    invis.add(totalPeople);
    farmPanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_FARM, GameCommands.COMMAND_PLUS_FARM, 
        Icons.ICON_FARM, ": "+planet.getWorkers(Planet.FOOD_FARMERS),
        "Number of people working as a farmers.",listener);
    invis.add(farmPanel);
    minePanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_MINE, GameCommands.COMMAND_PLUS_MINE, 
        Icons.ICON_MINE, ": "+planet.getWorkers(Planet.METAL_MINERS),
        "Number of people working as a miners.",listener);
    invis.add(minePanel);
    factoryPanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_PRODUCTION, GameCommands.COMMAND_PLUS_PRODUCTION, 
        Icons.ICON_FACTORY, ": "+planet.getWorkers(Planet.PRODUCTION_WORKERS),
        "Number of people working as a factory worker.",listener);
    invis.add(factoryPanel);
    resePanel = new WorkerProductionPanel(invis, 
        GameCommands.COMMAND_MINUS_RESEARCH, GameCommands.COMMAND_PLUS_RESEARCH, 
        Icons.ICON_RESEARCH, ": "+planet.getWorkers(Planet.RESEARCH_SCIENTIST),
        "Number of people working as a scientist.",listener);
    invis.add(resePanel);
    cultureLabel = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CULTURE),
        ": "+planet.getWorkers(Planet.CULTURE_ARTIST));
    cultureLabel.setToolTipText("Number of people producing culture.");
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
    invis.add(peopleGrowth);
    farmProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_FARM), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_FOOD));
    farmProd.setToolTipText("Total production of food");
    invis.add(farmProd);
    mineProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_MINE), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_METAL));
    mineProd.setToolTipText("Total production of metal");
    invis.add(mineProd);
    prodProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_FACTORY), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION));
    prodProd.setToolTipText("Total production of production");
    invis.add(prodProd);
    reseProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_RESEARCH), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_RESEARCH));
    reseProd.setToolTipText("Total production of research");
    invis.add(reseProd);
    cultProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CULTURE), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_CULTURE));
    cultProd.setToolTipText("Total production of culture");
    invis.add(cultProd);
    topPanel.add(invis);


    invis = new InvisiblePanel(topPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.Y_AXIS));
    credProd = new IconLabel(invis,Icons.getIconByName(Icons.ICON_CREDIT), 
        ": "+planet.getTotalProduction(Planet.PRODUCTION_CREDITS));
    credProd.setToolTipText("Total production of credits");
    invis.add(credProd);
    metal = new IconLabel(invis,Icons.getIconByName(Icons.ICON_METAL), 
        ": "+planet.getMetal());
    metal.setToolTipText("Total metal on surface");
    invis.add(metal);
    metalOre = new IconLabel(invis,Icons.getIconByName(Icons.ICON_METAL_ORE), 
        ": "+planet.getAmountMetalInGround());
    metalOre.setToolTipText("Total metal ore to mine.");
    invis.add(metalOre);
    topPanel.add(invis);

    topPanel.setTitle(planet.getName());
    
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map", 
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn,BorderLayout.CENTER);
    
    // Add panels to base
    this.add(bottomPanel,BorderLayout.SOUTH);
    this.add(imgBase,BorderLayout.CENTER);
    this.add(topPanel,BorderLayout.NORTH);

  }
  
}
