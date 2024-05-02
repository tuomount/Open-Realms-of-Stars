package org.openRealmOfStars.player.scenario;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023-2024 Tuomo Untinen
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

import java.util.ArrayList;

import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;

/**
 * Starting Scenario for Open Realm of Stars
 */
public class StartingScenario {


  /** Starting Scenario unique ID */
  private String id;

  /** Starting scenario type */
  private StartingScenarioType type;

  /** Starting scenario name aka human readable */
  private String name;

  /** Starting scenario description */
  private String description;

  /** Number of scouts in start. */
  private int numberOfScouts;

  /** Number of colony ships in start. */
  private int numberOfColonyShips;

  /** Starting credit. */
  private int startingCredit;

  /** Planet's water level */
  private WaterLevelType waterLevel;
  /** Planet's temperature */
  private TemperatureType temperature;
  /** Planet ground size */
  private int planetSize;
  /** Population on planet */
  private int population;
  /** Number of colony pops in colony ships. */
  private int colonyPop;
  /** Extra technologies at start. */
  private ArrayList<String> extraTech;
  /** Starting buildings. */
  private ArrayList<String> buildings;
  /**
   * Constructor for starting scenario.
   * @param id Unique id
   * @param type Starting Scenario type.
   * @param name Starting scenario human readable name.
   */
  public StartingScenario(final String id,
      final StartingScenarioType type, final String name) {
    this.id = id;
    this.type = type;
    this.name = name;
    extraTech = new ArrayList<>();
    buildings = new ArrayList<>();
  }

  /**
   * Add Extra starting technology.
   * @param tech Techname. These can direct technology name or randomTechType.
   * Example: randomCombat, randomDefense and so on.
   */
  public void addTech(final String tech) {
    extraTech.add(tech);
  }

  /**
   * Add building on start planet.
   * @param building BuildingName to add. FarmBuilding is special it
   * depends on space race dietary what is being added.
   */
  public void addBuilding(final String building) {
    buildings.add(building);
  }
  /**
   * Get array of extra technology.
   * @return Array of string of extra technology.
   */
  public String[] getTechs() {
    return extraTech.toArray(new String[extraTech.size()]);
  }

  /**
   * Get array of buildings.
   * @return Array of string of starting planet.
   */
  public String[] getBuildings() {
    return buildings.toArray(new String[buildings.size()]);
  }
  /**
   * Get Starting Scenario description.
   * @return Description as string.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get Starting scenario unique id.
   * @return ID
   */
  public String getId() {
    return id;
  }

  /**
   * Get starting scenario type.
   * @return StartingScenarioType
   */
  public StartingScenarioType getType() {
    return type;
  }

  /**
   * @return the name human readable name.
   */
  public String getName() {
    return name;
  }

  /**
   * Get number of scouts at start.
   * @return Number of scouts
   */
  public int getNumberOfScouts() {
    return numberOfScouts;
  }

  /**
   * Get number of colony ships at start.
   * @return Number of colony ships
   */
  public int getNumberOfColonyShips() {
    return numberOfColonyShips;
  }

  /**
   * Get planet's water level
   * @return WaterLevelType
   */
  public WaterLevelType getWaterLevel() {
    return waterLevel;
  }

  /**
   * Get planet's temperature
   * @return TemperatureType
   */
  public TemperatureType getTemperature() {
    return temperature;
  }

  /**
   * Get Planet size.
   * @return Ground size
   */
  public int getPlanetSize() {
    return planetSize;
  }

  /**
   * Get starting population
   * @return Population
   */
  public int getPopulation() {
    return population;
  }

  /**
   * How many populations are inside colony ship.
   * @return Colony pop per colony ship.
   */
  public int getColonyPop() {
    return colonyPop;
  }

  /**
   * Set Scenario description.
   * @param description String
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Set number of scouts.
   * @param numberOfScouts Number of scouts at start.
   */
  public void setNumberOfScouts(final int numberOfScouts) {
    this.numberOfScouts = numberOfScouts;
  }

  /**
   * Set number of colony ships.
   * @param numberOfColonyShips Number of colony ships at start.
   */
  public void setNumberOfColonyShips(final int numberOfColonyShips) {
    this.numberOfColonyShips = numberOfColonyShips;
  }

  /**
   * Set Water level for start up planet.
   * @param waterLevel Water Level
   */
  public void setWaterLevel(final WaterLevelType waterLevel) {
    this.waterLevel = waterLevel;
  }

  /**
   * Set Temperature for start up planet.
   * @param temperature TemperatureType
   */
  public void setTemperature(final TemperatureType temperature) {
    this.temperature = temperature;
  }

  /**
   * Set startup planet's ground size. Needs to be between 7-16.
   * @param planetSize Ground size
   */
  public void setPlanetSize(final int planetSize) {
    if (planetSize > 6 && planetSize < 17) {
      this.planetSize = planetSize;
    }
  }

  /**
   * Set startup planet starting population.
   * @param population Starting population
   */
  public void setPopulation(final int population) {
    this.population = population;
  }

  /**
   * Set amount colony pops in each colony ship.
   * @param colonyPop Colony population.
   */
  public void setColonyPop(final int colonyPop) {
    this.colonyPop = colonyPop;
  }

  /**
   * Get staring credits.
   * @return the startingCredit
   */
  public int getStartingCredit() {
    return startingCredit;
  }

  /**
   * Set amount of starting credits.
   * @param startingCredit the startingCredit to set
   */
  public void setStartingCredit(final int startingCredit) {
    this.startingCredit = startingCredit;
  }

  @Override
  public String toString() {
    return getName();
  }
}
