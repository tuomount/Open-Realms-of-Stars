package org.openRealmOfStars.starMap.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.ai.planet.PlanetHandling;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.artifact.ArtifactFactory;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;
import org.openRealmOfStars.starMap.planet.enums.GravityType;
import org.openRealmOfStars.starMap.planet.enums.HappinessBonus;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.starMap.planet.enums.PlanetaryEvent;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.starMap.planet.enums.WorldType;
import org.openRealmOfStars.starMap.planet.status.AppliedStatus;
import org.openRealmOfStars.starMap.planet.status.PlanetaryStatus;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.WeightedList;
import org.openRealmOfStars.utilities.namegenerators.RandomSystemNameGenerator;

/**
 *
 * Class planet
 *
 */
public class Planet {

  /** Maximum number of different worker types */
  public static final int MAX_WORKER_TYPE = 5;
  /** Farmer worker type, produces food */
  public static final int FOOD_FARMERS = 0;
  /** Miner worker type, produces metal*/
  public static final int METAL_MINERS = 1;
  /** Worker worker type, produces production points */
  public static final int PRODUCTION_WORKERS = 2;
  /** Scientist worker type, produces research */
  public static final int RESEARCH_SCIENTIST = 3;
  /** Artist worker type, produces culture and happiness */
  public static final int CULTURE_ARTIST = 4;

  /** Governor guide for general guide. This is the regular handling. */
  public static final int GENERALIST_PLANET = 0;
  /** Governor guide for getting more credits. */
  public static final int CREDIT_PLANET = 1;
  /** Governor guide for getting more research. */
  public static final int RESEARCH_PLANET = 2;
  /** Governor guide for getting more culture. */
  public static final int CULTURE_PLANET = 3;
  /** Governor guide for getting more military ships from the planet. */
  public static final int MILITARY_PLANET = 4;
  /** Governor guide for getting more population quickly for the planet. */
  public static final int POPULATION_PLANET = 5;
  /** Governor guide is passive. */
  public static final int PASSIVE_GOVERNOR = 6;

  // TODO: Enumerize production type fields?
  // It could remove some safety checks, but would be slightly less extensible
  /** Food production */
  public static final int PRODUCTION_FOOD = 0;
  /** Metal production */
  public static final int PRODUCTION_METAL = 1;
  /** Production production */
  public static final int PRODUCTION_PRODUCTION = 2;
  /** Research production */
  public static final int PRODUCTION_RESEARCH = 3;
  /** Culture production */
  public static final int PRODUCTION_CULTURE = 4;
  /** Credit production */
  public static final int PRODUCTION_CREDITS = 5;
  /** Population growth */
  public static final int PRODUCTION_POPULATION = 6;
  /** Material production from empty */
  public static final int PRODUCTION_MATERIAL = 7;
  /** Artifact research production */
  public static final int PRODUCTION_ARTIFACT_RESEARCH = 8;

  /** Number of credits one population is worth when rushing. */
  public static final int POPULATION_RUSH_COST = 80;

  /** Minimum amount of ore on planets */
  private static final int MINIMUM_ORE = 2000;
  /** Maximum amount of ore on planets */
  private static final int MAXIMUM_ORE = 10000;

  /** Planet name */
  private String name;
  /** Planet order number in system */
  private int orderNumber;

  /** Planet's radiation type. */
  private RadiationType radiationType;
  /**
   * Planet's gravity type.
   * between low, normal and high gravity.
   */
  private GravityType gravityType;
  /**
   * Planet's temperature type.
   * From Frozen to volcanic.
   */
  private TemperatureType temperatureType;
  /**
   * Planet's water level type.
   */
  private WaterLevelType waterLevel;
  /** Is planet inhabitable gas giant. Gas giants just block the radar. */
  private boolean gasGiant;
  /** Planet's coordinate. On gas giant this left upper corner. */
  private Coordinate coordinate;
  /** Planet type. Constain world type, images and image instruction. */
  private PlanetTypes planetType;
  /** Planet's size aka how many improvements can be there. Between 7-16. */
  private int groundSize;

  /** How much metal there is still in the ground. Default is 2000-10000. */
  private int amountMetalInGround;
  /** How much metal has been mined and available to use */
  private int metal;
  /** Amount of production resource available */
  private int prodResource;
  /** Extra food, each +10 increases people by 1, each -10 decreases by one. */
  private int extraFood;
  /** Planet's culture value */
  private int culture;

  /** Index of planet's owner PlayerInfo, -1 if planet is not colonized. */
  private int planetOwner;
  /** Planet playerInfo, null means not colonized. */
  private PlayerInfo planetOwnerInfo;
  /**
   * Space race id which space race home world planet is.
   * Empty means that is is not an home world.
   * Home world needs generate 1 culture per turn.
   */
  private String homeWorldId;
  /**
   * Starting realm index for elder realm start.
   * This value is only used when creating galaxy.
   * This will no be saved on file.
   */
  private int startRealmIndex;

  /** How many productions are converted to credits */
  private int tax;

  /** Statuses applied on the Planet */
  private ArrayList<AppliedStatus> statuses;

  /** Planetary event when colonizing the planet */
  private PlanetaryEvent event;
  /** Event found AKA planet has been colonized */
  private boolean eventFound;

  /**
   * Happiness effect for planet. This can be also sadness effect.
   * This is only used when planet is being update to next turn
   * so no need to save this to file.
   */
  private HappinessEffect happinessEffect;

  /** String containg how happiness is calculated. */
  private String happinessExplanation;
  /** String containing how farm production is calculated. */
  private String farmProdExplain;
  /** String containing how metal production is calculated. */
  private String metaProdExplain;
  /** String containing how production production is calculated. */
  private String prodProdExplain;
  /** String containing how research production is calculated. */
  private String reseProdExplain;
  /** String containing how culture production is calculated. */
  private String cultProdExplain;
  /** String containing how credit production is calculated. */
  private String credProdExplain;

  /** Current governor of the planet. */
  private Leader governor;
  /** Guide for governor how to rule the planet. */
  private int governorGuide;

  /** Orbital for planet. */
  private Ship orbital;

  /** Amount of different workers */
  private int[] workers;

  /** Buildings / Planetary improvements */
  private ArrayList<Building> buildings;
  /** What building / Planetary improvement is currently under construction */
  private Construction underConstruction;

  /**
   * Create random planet with name + orderNumber with Roman numbers.
   * Other planet attributes are randomized.
   * @param coordinate Planet's coordinate
   * @param name Planet name
   * @param orderNumber as integer
   * @param gasGiant Is planet inhabitable gas giant
   */
  public Planet(final Coordinate coordinate, final String name,
      final int orderNumber, final boolean gasGiant) {
    this.setCoordinate(coordinate);
    governor = null;
    orbital = null;
    setGovernorGuide(GENERALIST_PLANET);
    if (orderNumber == 0) {
      // Rogue planet
      this.name = name;
    } else {
      this.name = name + " "
          + RandomSystemNameGenerator.numberToRoman(orderNumber);
    }
    happinessEffect = new HappinessEffect(HappinessBonus.NONE, 0);
    this.setOrderNumber(orderNumber);
    this.setRadiationLevel(RadiationType.values()[DiceGenerator.getRandom(
        RadiationType.values().length - 1)]);
    if (gasGiant) {
      this.setGravityType(GravityType.HIGH_GRAVITY);
      this.setTemperatureType(TemperatureType.ARCTIC);
      this.setWaterLevel(WaterLevelType.MARINE);
    } else {
      this.setGravityType(GravityType.NORMAL_GRAVITY);
      this.setTemperatureType(TemperatureType.TEMPERATE);
      this.setWaterLevel(WaterLevelType.HUMID);
    }
    if (orderNumber == 0) {
      // Rogue planet have more metal
      this.setAmountMetalInGround(DiceGenerator.getRandom(MINIMUM_ORE + 2000,
          MAXIMUM_ORE));
      if (DiceGenerator.getBoolean()) {
        this.setRadiationLevel(RadiationType.NO_RADIATION);
      } else {
        this.setRadiationLevel(RadiationType.LOW_RADIATION);
      }
    } else {
      this.setAmountMetalInGround(DiceGenerator.getRandom(MINIMUM_ORE,
          MAXIMUM_ORE));
    }
    if (orderNumber == 0) {
      // Rogue planets are slightly bigger
      this.setGroundSize(DiceGenerator.getRandom(9, 16));
    } else {
      this.setGroundSize(DiceGenerator.getRandom(7, 16));
    }
    this.setMetal(0);
    this.gasGiant = gasGiant;
    this.planetOwner = -1;
    this.workers = new int[MAX_WORKER_TYPE];
    this.extraFood = 0;
    this.buildings = new ArrayList<>();
    this.prodResource = 0;
    this.underConstruction = null;
    this.tax = 0;
    this.culture = 0;
    this.homeWorldId = "";
    this.startRealmIndex = -1;
    if (this.gasGiant) {
      this.planetType = PlanetTypes.GASGIANT1;
    } else {
      this.planetType = PlanetTypes.SWAMPWORLD1;
    }
    this.event = PlanetaryEvent.NONE;
    this.eventFound = true;
    this.statuses = new ArrayList<>();
  }

  /**
   * Add building to planet
   * @param building to add
   */
  public void addBuilding(final Building building) {
    if (building != null) {
      this.buildings.add(building);
    }
  }

  /**
   * Remove building from planet and apply recycle bonus if available
   * @param building to remove
   */
  public void removeBuilding(final Building building) {
    if (building != null) {
      int recycleBonus = getRecycleBonus();
      for (int i = 0; i < buildings.size(); i++) {
        Building temp = buildings.get(i);
        if (temp.getName().equals(building.getName())) {
          buildings.remove(i);
          if (recycleBonus > 0) {
            metal = metal + building.getMetalCost() * recycleBonus / 100;
          }
          break;
        }
      }
    }
  }

  /**
   * Remove one colonist where are the most workers
   * @return true if colonist is taken, otherwise false
   */
  public boolean takeColonist() {
    boolean result = false;
    int index = -1;
    int value = 0;
    int total = 0;
    for (int i = 0; i < MAX_WORKER_TYPE; i++) {
      total = total + workers[i];
      if (workers[i] > value) {
        value = workers[i];
        index = i;
      }
    }
    if (index != -1 && value > 0 && total > 1) {
      workers[index]--;
      result = true;
    }
    return result;
  }

  /**
   * Move one worker from one position to another one.
   * Method makes all the required checks
   * @param from Worker position where to take a worker
   * @param to Worker position where to move the worker
   */
  public void moveWorker(final int from, final int to) {
    if (from > -1 && from < MAX_WORKER_TYPE && to > -1 && to < MAX_WORKER_TYPE
        && from != to && workers[from] > 0) {
      workers[from] = workers[from] - 1;
      workers[to] = workers[to] + 1;
    }
  }

  /**
   * Planet fights against wildlife.
   * @param building Wildlife building
   * @return True if fight succeed and false if not
   */
  public boolean fightAgainstWildLife(final Building building) {
    if (building.getWildLifePower() > 0) {
      if (getTroopPower() <= building.getWildLifePower()) {
        return false;
      }
      fightAgainstAttacker(building.getWildLifePower(), null, "wild life",
          "fighting against wildlife", "fighting against wildlife");
    }
    return true;
  }

  /**
   * Get production time as in star years
   * @param build The construction
   * @return The production time in star years.
   *  -1 mean construction will never complete.
   */
  public int getProductionTime(final Construction build) {
    int requiredMetalCost = getActualCost(build, PRODUCTION_METAL);
    int requiredProdCost = getActualCost(build, PRODUCTION_PRODUCTION);
    int metalReq = requiredMetalCost - getMetal();
    int prodReq = requiredProdCost - getProdResource();
    if (metalReq <= 0 && prodReq <= 0) {
      return 1;
    }
    int metalTurn = getProductionTimeByProductionType(metalReq,
        PRODUCTION_METAL);
    int prodTurn = getProductionTimeByProductionType(prodReq,
        PRODUCTION_PRODUCTION);
    if (prodTurn == -1 || metalTurn == -1) {
      return -1;
    }
    if (prodTurn > metalTurn) {
      return prodTurn;
    }
    return metalTurn;
  }

  /**
   * Get production time as String
   * @param build The construction
   * @return The production time
   */
  public String getProductionTimeAsString(final Construction build) {
    int time = getProductionTime(build);
    if (time == -1) {
      return "Never";
    } else if (time == 1) {
      return "1 star year";
    }
    return time + " star years";
  }

  /**
   * Get production rushing cost in credits
   * @param build The construction
   * @return The rushing cost
   */
  public int getRushingCost(final Construction build) {
    int metalReq = build.getMetalCost() - getMetal();
    int prodReq = build.getProdCost() - getProdResource();
    if (metalReq <= 0 && prodReq <= 0) {
      return 0;
    }
    if (getAmountMetalInGround() < metalReq) {
      return 0;
    }
    if (metalReq < 0) {
      metalReq = 0;
    }
    if (prodReq < 0) {
      prodReq = 0;
    }
    return metalReq + prodReq * 2;
  }

  /**
   * Do construction rushing. This adds missing metal and production to planet.
   * Construction is then ready on next turn.
   * @param creditRush Do rushing with credit. Otherwise with population
   * @param info PlayerInfo who is doing the rushing.
   */
  public void doRush(final boolean creditRush, final PlayerInfo info) {
    int rushCost = getRushingCost(underConstruction);
    int metalReq = underConstruction.getMetalCost() - getMetal();
    int prodReq = underConstruction.getProdCost() - getProdResource();
    if (metalReq < 0) {
      metalReq = 0;
    }
    if (prodReq < 0) {
      prodReq = 0;
    }
    boolean hasCreditRush = info.getGovernment().hasCreditRush();
    boolean hasPopulationRush = info.getGovernment().hasPopulationRush();
    int populationCost = rushCost / Planet.POPULATION_RUSH_COST + 1;
    if (rushCost > 0 && creditRush && hasCreditRush
        && rushCost <= info.getTotalCredits()) {
      info.setTotalCredits(info.getTotalCredits() - rushCost);
      prodResource = prodResource + prodReq;
      metal = metal + metalReq;
    }
    if (rushCost > 0 && !creditRush && hasPopulationRush
        && populationCost < getTotalPopulation()) {
      for (int i = 0; i < populationCost; i++) {
        killOneWorker();
      }
      prodResource = prodResource + prodReq;
      metal = metal + metalReq;
    }
  }

  /**
  * Get production time in turn depending on production type.
  * This method estimates how long construction production takes in turn.
  * This can be used for both metal and production estimations.
  * @param productionReq How much production is required
  * @param productionType Either PRODUCTION_PRODUCTION or PRODUCTION_METAL
  * @return Production time in star years
  */
  private int getProductionTimeByProductionType(final int productionReq,
      final int productionType) {
    int productionTime = 0;
    if (getTotalProduction(productionType) > 0 && productionReq > 0) {
      productionTime = (int) Math.ceil(
          (double) productionReq / (double) getTotalProduction(productionType));
    } else if (getTotalProduction(productionType) == 0 && productionReq > 0) {
      productionTime = -1;
    } else if (productionReq <= 0) {
      productionTime = 1;
    }
    return productionTime;
  }

  /**
   * Get amount of workers in certain type
   * @param workerType The worker type
   * @return Amount of workers
   */
  public int getWorkers(final int workerType) {
    if (workerType >= 0 && workerType < MAX_WORKER_TYPE) {
      return workers[workerType];
    }
    return 0;
  }

  /**
   * Set amount of workers in certain type
   * @param workerType The worker type
   * @param value how many workers in this production
   */
  public void setWorkers(final int workerType, final int value) {
    if (workerType >= 0 && workerType < MAX_WORKER_TYPE) {
      workers[workerType] = value;
    }
  }

  /**
   * Get total population number
   * @return Total Population
   */
  public int getTotalPopulation() {
    int result = 0;
    for (int i = 0; i < workers.length; i++) {
      result = result + workers[i];
    }
    return result;
  }

  /**
   * Get Troop power
   * @return Get Total troop power where improvements are taken to count
   */
  public int getTroopPower() {
    if (planetOwnerInfo == null) {
      return 0;
    }

    int troopCount = getTotalPopulation();
    return troopCount * getTroopPowerBonus();
  }

  /**
   * Get Troop power bonus
   * @return Get Total troop power where improvements are taken to count
   */
  public int getTroopPowerBonus() {
    if (planetOwnerInfo == null) {
      return 0;
    }

    int multiply = 100;
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getBattleBonus() > 0) {
        multiply += building.getBattleBonus();
      }
    }

    if (governor != null && governor.hasPerk(Perk.DISCIPLINE)) {
      multiply += 25;
    }

    int basePower = planetOwnerInfo.getRace().getTrooperPower();
    return basePower * multiply / 100;
  }

  /**
   * Attack troops try to occupy planet by troops.
   * This will cause planet colonist to be decreased according by attacking
   * troops. If attacking troops win then left over must be calculated
   * somewhere else.
   * @param attackTroops Attacking troop power
   * @param starMap StarMap for history writing
   * @param attackType Attack type, used for creating explanation why governor
   *                   or worker died.
   * @param reasonGovernor Reason for governor if dies
   * @param reasonWorker Reason for worker if dies
   */
  public void fightAgainstAttacker(final int attackTroops,
      final StarMap starMap, final String attackType,
      final String reasonGovernor, final String reasonWorker) {
    if (planetOwnerInfo == null) {
      return;
    }

    int troop = getTroopPowerBonus();
    int total = getTroopPower();
    // Defenders defeated
    if (attackTroops >= total) {
      for (int i = 0; i < workers.length; i++) {
        workers[i] = 0;
      }
      killGovernor(attackType, " " + reasonGovernor, starMap);
      planetOwnerInfo = null;
      planetOwner = -1;
      // Fighting on planet drops the culture
      setCulture(getCulture() - getCulture() / 10);

      return;
    }

    // Fighting on planet drops the culture
    setCulture(getCulture() - getCulture() / 10);
    int left = total - attackTroops;
    left = left / troop;
    if (left <= 0) {
      left = 1;
    }
    int dies = getTotalPopulation() - left;
    for (int i = 0; i < dies; i++) {
      killOneWorker(attackType, " " + reasonWorker, starMap);
    }
  }

  /**
   * Attack troops try to occupy planet by troops.
   * This will cause planet colonist to be decreased according by attacking
   * troops. If attacking troops win then left over must be calculated
   * somewhere else.
   * @param attackTroops Attacking troop power
   * @param starMap StarMap for history writing
   */
  public void fightAgainstAttacker(final int attackTroops,
      final StarMap starMap) {
    fightAgainstAttacker(attackTroops, starMap, "conquest", "attacking troops",
        "conquest of planet");
  }

  /**
   * Get total food production from planet and buildings
   * @return Total food production from planet and buildings
   */
  public int getFoodProdByPlanetAndBuildings() {
    int total = getTotalProductionFromBuildings(PRODUCTION_FOOD);
    int statusesBonus = statuses.stream()
        .map(status -> status.getStatus().getFoodBonus())
        .reduce(0, Integer::sum);
    total = total + statusesBonus + getWaterLevel().getPlanetFoodProd();
    return total;
  }

  /**
   * Get total production from planetary improvements.
   * @param prod Production to get: See all PRODUCTION_*
   * @return amount of production in one turn
   */
  public int getTotalProductionFromBuildings(final int prod) {
    int result = 0;
    if (gasGiant || planetOwnerInfo == null) {
      return 0;
    }
    for (final var build : getBuildingList()) {
      var value = 0;
      switch (prod) {
        default: {
          throw new IllegalArgumentException("Illegal production type!");
        }
        case PRODUCTION_FOOD: {
          value = build.getFarmBonus();
          break;
        }
        case PRODUCTION_METAL: {
          value = build.getMineBonus();
          break;
        }
        case PRODUCTION_PRODUCTION: {
          value = build.getFactBonus();
          break;
        }
        case PRODUCTION_RESEARCH: {
          value = build.getReseBonus();
          break;
        }
        case PRODUCTION_CULTURE: {
          value = build.getCultBonus();
          break;
        }
        case PRODUCTION_CREDITS: {
          value = build.getCredBonus();
          // Special ability for mercantile races to get
          // one extra credit per trade building
          if (planetOwnerInfo.getRace().hasTrait(TraitIds.MERCANTILE)
              && build.getCredBonus() > 0) {
            value += 1;
          }
          break;
        }
        case PRODUCTION_POPULATION: {
          value = 0;
          break;
        }
        case PRODUCTION_MATERIAL: {
          value = build.getMaterialBonus();
          break;
        }
        case PRODUCTION_ARTIFACT_RESEARCH: {
          value = build.getAncientArtifactResearch();
          break;
        }
      }

      result += value;
    }

    return result;
  }

  /**
   * Get planet's maintenance cost for full credits.
   * @return int as maintenanceCost
   */
  public int getMaintenanceCost() {
    double result = 0;
    for (Building build : getBuildingList()) {
      result = result + build.getMaintenanceCost();
    }

    // Energy-powered races have maintenance for every 4th of population
    var ownerInfo = planetOwnerInfo;
    if (ownerInfo != null
        && ownerInfo.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
      // If race can sustain itself from radiation,
      // adjust the amount of maintained population accordingly
      var popToMaintain = getTotalPopulation();
      if (ownerInfo.getRace().hasTrait(TraitIds.RADIOSYNTHESIS)) {
        int sustFromRad = Math.min(getRadiationLevel().getRadiosynthesisFood(),
            popToMaintain);
        popToMaintain -= sustFromRad;
      }

      result += Math.floor(popToMaintain / 4);
    }

    return (int) Math.floor(result);
  }

  /**
   * Get total production from planet. This includes racial, worker, planetary
   * improvement bonus
   * @param prod Production to get: See all PRODUCTION_*
   * @return amount of production in one turn
   */
  public int getTotalProduction(final int prod) {
    if (happinessEffect == null) {
      happinessEffect = new HappinessEffect(HappinessBonus.NONE, 0);
    }
    int result = 0;
    if (gasGiant || planetOwnerInfo == null) {
      return 0;
    }
    switch (prod) {
      case PRODUCTION_FOOD: {
        result = getTotalFoodProduction();
        break;
      }
      case PRODUCTION_METAL: {
        result = getTotalMetalProduction();
        break;
      }
      case PRODUCTION_PRODUCTION: {
        result = getTotalProdProduction();
        break;
      }
      case PRODUCTION_RESEARCH: {
        result = getTotalResearchProduction();
        break;
      }
      case PRODUCTION_CULTURE: {
        result = getTotalCultureProduction();
        break;
      }
      case PRODUCTION_CREDITS: {
        result = getTotalCreditProduction();
        break;
      }
      case PRODUCTION_POPULATION: {
        result = getTotalPopulationProduction();
        break;
      }
      case PRODUCTION_ARTIFACT_RESEARCH: {
        result = getTotalArtifactResearchProduction();
        break;
      }
      default: {
        throw new IllegalArgumentException("Illegal production type!");
      }
    }
    return result;
  }

  /**
   * Add description entry to StringBuilder if value not 0;
   * @param sb StringBuilder
   * @param entryName Entry name
   * @param val Entry value
   */
  private static void addEntryIfWorthy(final StringBuilder sb,
      final String entryName, final int val) {
    if (val == 0) {
      return;
    }
    final var tplDescEntry = "<li> %1$s %2$+d </li>";
    sb.append(String.format(tplDescEntry, entryName, val));
  }

  /**
   * Get total credit production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */
  private int getTotalCreditProduction() {
    StringBuilder sb = new StringBuilder();
    int result = 0;
    GovernmentType government = planetOwnerInfo.getGovernment();
    int totalPopulation = getTotalPopulation();
    sb.append("<html>");
    sb.append("Total credits production.<br>");

    // Planet does not have innate credit bonus

    var value = getTotalProductionFromBuildings(PRODUCTION_CREDITS);
    result += value;
    addEntryIfWorthy(sb, "buildings", value);

    value = getTax();
    result += value;
    addEntryIfWorthy(sb, "tax", value);

    for (var appliedStatus : statuses) {
      final var status = appliedStatus.getStatus();
      final var tmpVal = status.getCredBonus();
      result += tmpVal;
      addEntryIfWorthy(sb, status.getName(), tmpVal);
    }

    value = getMaintenanceCost();
    result -= value;
    addEntryIfWorthy(sb, "maintenance", -value);

    if (getOrbital() != null) {
      value = getOrbital().getTotalCreditBonus();
      result += value;
      addEntryIfWorthy(sb, "orbital", value);
    }

    if (totalPopulation >= 4) {
      value = government.getCreditBonus();
      result += value;
      addEntryIfWorthy(sb, "government", value);
    }

    if (happinessEffect.getType() == HappinessBonus.CREDIT) {
      value = happinessEffect.getValue();
      result += value;
      addEntryIfWorthy(sb, "happiness", value);
    }

    value = 0;
    if (governor != null) {
      if (governor.hasPerk(Perk.MERCHANT)) {
        value += 1;
      }
      if (governor.hasPerk(Perk.SKILLFUL)) {
        value += 1;
      }
      if (governor.hasPerk(Perk.CORRUPTED)) {
        value -= 1;
      }
      if (governor.hasPerk(Perk.INCOMPETENT)) {
        value -= 1;
      }
      result += value;
      addEntryIfWorthy(sb, "governor", value);
    }

    sb.append("</html>");
    credProdExplain = sb.toString();
    return result;
  }

  /**
   * Get total artifact research production value.
   * @return Total artifact research production.
   */
  private int getTotalArtifactResearchProduction() {
    int result = 0;
    if (planetOwnerInfo.getArtifactLists().hasDiscoveredArtifacts()) {
      result = result + getTotalProductionFromBuildings(
          PRODUCTION_ARTIFACT_RESEARCH);
      if (governor != null) {
        if (governor.hasPerk(Perk.ACADEMIC)) {
          result++;
        }
        if (governor.hasPerk(Perk.SCIENTIST)) {
          result++;
        }
        if (governor.hasPerk(Perk.STUPID)) {
          result--;
        }
        if (governor.hasPerk(Perk.EXPLORER)) {
          result++;
        }
        if (governor.hasPerk(Perk.ARCHAEOLOGIST)) {
          result = result + 2;
        }
      }
    } else {
      result = 0;
    }
    return result;
  }

  /**
   * Get total population production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */
  private int getTotalPopulationProduction() {
    int result = 0;
    if (planetOwnerInfo.getRace().getFoodRequire() == 0
        && planetOwnerInfo.getRace().getGrowthSpeed() == 0) {
      // Races that do not require food and do not grow naturally
      // never starve nor populate
      return 0;
    }
    int require = 10;
    if (planetOwnerInfo.getRace().isLithovorian()) {
      // Lithovorians eat metal instead of food.
      require = getTotalPopulation() / 2;
      int available = getMetal() + getTotalProduction(PRODUCTION_METAL);
      if (available >= require * 16) {
        result = 4;
      } else if (available >= require * 8) {
        result = 3;
      } else if (available >= require * 4) {
        result = 2;
      } else if (available > require) {
        result = 1;
      } else if (available == require) {
        result = 0;
      } else {
        result = -1;
      }
      if (planetOwnerInfo.getRace().hasTrait(TraitIds.LIMITED_GROWTH)
          && result > 2) {
        result = 2;
      }
      require = 10 * 100 / planetOwnerInfo.getRace().getGrowthSpeed();
    } else {
      // Planet does not have population bonus
      result = getTotalProduction(PRODUCTION_FOOD) - getTotalPopulation()
          * planetOwnerInfo.getRace().getFoodRequire() / 100;
      if (planetOwnerInfo.getRace().getGrowthSpeed() == 0) {
        // Race never grows naturally
        require = 10;
        if (result > 0) {
          result = 0;
        }
      } else {
        require = 10 * 100 / planetOwnerInfo.getRace().getGrowthSpeed();
      }
      if (planetOwnerInfo.getRace().hasTrait(TraitIds.FIXED_GROWTH)
          && result > 0) {
        // Fixed grow rate
        result = 1;
      }
      if (planetOwnerInfo.getRace().hasTrait(TraitIds.LIMITED_GROWTH)
          && result > 2) {
        // Limited grow rate
        result = 2;
      }
    }
    if (result > 0) {
      result = (require - extraFood) / result;
      if (result < 1) {
        result = 1;
      }
    } else if (result < 0) {
      result = (-1 * require - extraFood) / result;
      if (result < 1) {
        result = 1;
      }
      result = result * -1;
    } else {
      result = 0;
    }

    return result;
  }

  /**
   * Get total culture production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */
  private int getTotalCultureProduction() {
    StringBuilder sb = new StringBuilder();
    int result = 0;
    int div = 100;
    GovernmentType government = planetOwnerInfo.getGovernment();
    int totalPopulation = getTotalPopulation();
    sb.append("<html>");
    sb.append("Total culture production.<br>");

    // Planet does not have innate culture bonus

    var mult = planetOwnerInfo.getRace().getCultureSpeed();
    var value = workers[PRODUCTION_CULTURE] * mult / div;
    result += value;
    addEntryIfWorthy(sb, "artists", value);

    value = getTotalProductionFromBuildings(PRODUCTION_CULTURE);
    result += value;
    addEntryIfWorthy(sb, "buildings", value);

    if (getOrbital() != null) {
      value = getOrbital().getTotalCultureBonus();
      result += value;
      addEntryIfWorthy(sb, "orbital", value);
    }

    if (totalPopulation >= 4) {
      value = government.getCultureBonus();
      result += value;
      addEntryIfWorthy(sb, "government", value);
    }

    // Home worlds produce one extra culture
    if (isHomeWorld()) {
      value = 1;
      result += value;
      addEntryIfWorthy(sb, "home world", value);
    }

    if (happinessEffect.getType() == HappinessBonus.CULTURE) {
      value = happinessEffect.getValue();
      result += value;
      addEntryIfWorthy(sb, "happiness", value);
    }

    if (governor != null && governor.hasPerk(Perk.ARTISTIC)) {
      value = 1;
      result += value;
      addEntryIfWorthy(sb, "governor", value);
    }

    sb.append("</html>");
    cultProdExplain = sb.toString();
    return result;
  }

  /**
   * Get total research production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */
  private int getTotalResearchProduction() {
    StringBuilder sb = new StringBuilder();
    int result = 0;
    int div = 100;
    GovernmentType government = planetOwnerInfo.getGovernment();
    int totalPopulation = getTotalPopulation();
    sb.append("<html>");
    sb.append("Total research production.<br>");

    // Planet does not have innate research bonus

    var mult = planetOwnerInfo.getRace().getResearchSpeed();
    var value = workers[PRODUCTION_RESEARCH] * mult / div;
    result += value;
    addEntryIfWorthy(sb, "scientists", value);

    value = getTotalProductionFromBuildings(PRODUCTION_RESEARCH);
    result += value;
    addEntryIfWorthy(sb, "buildings", value);

    if (getOrbital() != null) {
      value = getOrbital().getTotalResearchBonus();
      result += value;
      addEntryIfWorthy(sb, "orbital", value);
    }

    if (totalPopulation >= 4) {
      value = government.getResearchBonus();
      result += value;
      addEntryIfWorthy(sb, "government", value);
    }

    value = 0;
    if (governor != null) {
      if (governor.hasPerk(Perk.SCIENTIST)) {
        value += 1;
      }
      if (governor.hasPerk(Perk.STUPID)) {
        value -= 1;
      }
      result += value;
      addEntryIfWorthy(sb, "governor", value);
    }

    // Ensure it never goes negative
    result = Math.max(0, result);

    sb.append("</html>");
    reseProdExplain = sb.toString();
    return result;
  }

  /**
   * Get total prod production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */

  private int getTotalProdProduction() {
    StringBuilder sb = new StringBuilder();
    int result = 0;
    int div = 100;
    GovernmentType government = planetOwnerInfo.getGovernment();
    int totalPopulation = getTotalPopulation();
    sb.append("<html>");
    sb.append("Total production.<br>");

    // Planet always produces +1 by itself
    var value = 1;
    result += value;
    addEntryIfWorthy(sb, "planet", value);

    var mult = planetOwnerInfo.getRace().getProductionSpeed(getGravityType());
    value = workers[PRODUCTION_PRODUCTION] * mult / div;
    result += value;
    addEntryIfWorthy(sb, "workers", value);

    value = getTotalProductionFromBuildings(PRODUCTION_PRODUCTION);
    result += value;
    addEntryIfWorthy(sb, "buildings", value);

    for (var appliedStatus : statuses) {
      final var status = appliedStatus.getStatus();
      final var tmpVal = status.getProdBonus();
      result += tmpVal;
      addEntryIfWorthy(sb, status.getName(), tmpVal);
    }

    if (totalPopulation >= 4) {
      value = government.getProductionBonus();
      if (planetOwnerInfo.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
        value = value + government.getFoodBonus();
      }
      result += value;
      addEntryIfWorthy(sb, "government", value);
    }

    // Subtract taxes
    value = getTax();
    result -= value;
    addEntryIfWorthy(sb, "tax", -value);

    if (happinessEffect.getType() == HappinessBonus.PRODUCTION) {
      value = happinessEffect.getValue();
      result += value;
      addEntryIfWorthy(sb, "happiness", value);
    }

    value = 0;
    if (governor != null) {
      if (governor.hasPerk(Perk.INDUSTRIAL)) {
        value += 1;
      }
      if (governor.hasPerk(Perk.MICRO_MANAGER)) {
        value -= 1;
      }
      result += value;
      addEntryIfWorthy(sb, "governor", value);
    }

    // Ensure it never goes negative
    result = Math.max(0, result);

    sb.append("</html>");
    prodProdExplain = sb.toString();
    return result;
  }

  /**
   * Get total metal production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */

  private int getTotalMetalProduction() {
    StringBuilder sb = new StringBuilder();
    int result = 0;
    int div = 100;
    GovernmentType government = planetOwnerInfo.getGovernment();
    int totalPopulation = getTotalPopulation();
    sb.append("<html>");
    sb.append("Total metal production.<br>");

    // Planet always produces +1 by itself
    var value = 1;
    result += value;
    addEntryIfWorthy(sb, "planet", value);

    var mult = planetOwnerInfo.getRace().getMiningSpeed(getGravityType());
    value = workers[METAL_MINERS] * mult / div;
    result += value;
    addEntryIfWorthy(sb, "miners", value);

    value = getTotalProductionFromBuildings(PRODUCTION_METAL);
    result += value;
    addEntryIfWorthy(sb, "buildings", value);

    for (var appliedStatus : statuses) {
      final var status = appliedStatus.getStatus();
      final var tmpVal = status.getMineBonus();
      result += tmpVal;
      addEntryIfWorthy(sb, status.getName(), tmpVal);
    }

    if (totalPopulation >= 4) {
      value = government.getMiningBonus();
      if (planetOwnerInfo.getRace().isLithovorian()) {
        value = value + government.getFoodBonus();
      }
      result += value;
      addEntryIfWorthy(sb, "government", value);
    }

    if (happinessEffect.getType() == HappinessBonus.METAL) {
      value = happinessEffect.getValue();
      result += value;
      addEntryIfWorthy(sb, "happiness", value);
    }

    if (governor != null && governor.hasPerk(Perk.MINER)) {
      value = 1;
      result += value;
      addEntryIfWorthy(sb, "governor", value);
    }

    // Ensure it never goes negative
    result = Math.max(0, result);

    if (result > getAmountMetalInGround()) {
      result = getAmountMetalInGround();
      sb.append("Limited by amount of metal available on planet!");
    }

    // Add info about lithovoric race's metal consumption
    int pop = totalPopulation / 2;
    if (planetOwnerInfo.getRace().isLithovorian() && pop > 0) {
      addEntryIfWorthy(sb, "lithovorian", -pop);
    }

    sb.append("</html>");
    metaProdExplain = sb.toString();
    return result;
  }

  /**
   * Get total food production from planet. This includes racial, worker,
   * planetary improvement bonus
   * @return amount of production in one turn
   */

  private int getTotalFoodProduction() {
    StringBuilder sb = new StringBuilder();
    int result = 0;
    int div = 100;
    final var planetRace = planetOwnerInfo.getRace();
    GovernmentType government = planetOwnerInfo.getGovernment();
    int totalPopulation = getTotalPopulation();
    sb.append("<html>");
    sb.append("Food requirement: ");
    sb.append(calculateFoodRequirement());
    sb.append("<br><br>");
    sb.append("Total food production.<br>");

    // Planet always produces 0 - 5 food
    var value = getWaterLevel().getPlanetFoodProd();
    result += value;
    addEntryIfWorthy(sb, "planet", value);

    var mult = planetOwnerInfo.getRace().getFoodSpeed(getGravityType());
    value = workers[FOOD_FARMERS] * mult / div;
    result += value;
    addEntryIfWorthy(sb, "farmers", value);

    value = getTotalProductionFromBuildings(Planet.PRODUCTION_FOOD);
    result += value;
    addEntryIfWorthy(sb, "buildings", value);

    for (var appliedStatus : statuses) {
      final var status = appliedStatus.getStatus();
      final var tmpVal = status.getFoodBonus();
      result += tmpVal;
      addEntryIfWorthy(sb, status.getName(), tmpVal);
    }

    // TODO: "Self-sustenance" radiosynthesis should not "produce" food
    // It does in order to not break population growing
    if (planetRace.hasTrait(TraitIds.RADIOSYNTHESIS)) {
      final int rad = getRadiationLevel().getRadiosynthesisFood();
      final int currentPop = getTotalPopulation();
      final int sustFromRad = Math.min(rad, currentPop);
      if (planetRace.isEatingFood() && currentPop > 0) {
        result += sustFromRad;
        addEntryIfWorthy(sb, "radiosynthesis", sustFromRad);
      }
    }

    if (totalPopulation >= 4 && planetRace.isEatingFood()) {
      value = government.getFoodBonus();
      result += value;
      addEntryIfWorthy(sb, "government", value);
    }

    if (governor != null && governor.hasPerk(Perk.AGRICULTURAL)) {
      value = 1;
      result += value;
      addEntryIfWorthy(sb, "governor", value);
    }

    sb.append("</html>");
    farmProdExplain = sb.toString();
    return result;
  }

  /**
   * Get planet name
   * @return Planet name as a String
   */
  public String getName() {
    return name;
  }

  /**
   * Set planet name
   * @param name Planet name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get planet radiation level
   * @return radiation level
   */
  public RadiationType getRadiationLevel() {
    return radiationType;
  }

  /**
   * Get planet's effective radiation level. Level is adjusted by
   * buildings.
   * @return radiation level
   */
  public RadiationType getTotalRadiationLevel() {
    int value = getRadiationLevel().getIndex();
    for (Building building : buildings) {
      if (building.getName().equals("Radiation dampener")
          || building.getName().equals("Radiation well")) {
        value = value - 1;
      }
    }
    if (value < 0) {
      value = 0;
    }
    return RadiationType.getByIndex(value);
  }

  /**
   * Set planet's radiation type
   * @param radiationLevel RadiationType
   */
  public void setRadiationLevel(final RadiationType radiationLevel) {
    this.radiationType = radiationLevel;
  }

  /**
   * Get GravityType from planet.
   * @return the gravityType
   */
  public GravityType getGravityType() {
    return gravityType;
  }

  /**
   * Set gravity for planet.
   * @param gravityType the gravityType to set
   */
  public void setGravityType(final GravityType gravityType) {
    this.gravityType = gravityType;
  }

  /**
   * Get planet's temperature
   * @return the temperatureType
   */
  public TemperatureType getTemperatureType() {
    return temperatureType;
  }

  /**
   * Set planet's temperature
   * @param temperatureType the temperatureType to set
   */
  public void setTemperatureType(final TemperatureType temperatureType) {
    this.temperatureType = temperatureType;
  }

  /**
   * Get planet's water level
   * @return the waterLevel
   */
  public WaterLevelType getWaterLevel() {
    return waterLevel;
  }

  /**
   * Set planet's waterlevel
   * @param waterLevel the waterLevel to set
   */
  public void setWaterLevel(final WaterLevelType waterLevel) {
    this.waterLevel = waterLevel;
  }

  /**
   * Generate gravity based on planet ground size.
   * Bigger planet bigger gravity.
   */
  public void generateGravityBasedOnSize() {
    setGravityType(GravityType.NORMAL_GRAVITY);
    if (getGroundSize() <= 10) {
      setGravityType(GravityType.LOW_GRAVITY);
    }
    if (getGroundSize() > 13) {
      setGravityType(GravityType.HIGH_GRAVITY);
    }
  }

  /**
   * Generate water level based on temperature.
   * Only planet hotness limits how much water can be
   * on planet.
   */
  public void generateWaterLevelBasedOnTemperature() {
    int index = DiceGenerator.getRandom(WaterLevelType.values().length - 1);
    setWaterLevel(WaterLevelType.values()[index]);
    if (getTemperatureType() == TemperatureType.INFERNO) {
      setWaterLevel(WaterLevelType.BARREN);
    }
    if (getTemperatureType() == TemperatureType.VOLCANIC && index > 1) {
      setWaterLevel(WaterLevelType.DESERT);
    }
    if (getTemperatureType() == TemperatureType.HOT && index > 2) {
      setWaterLevel(WaterLevelType.ARID);
    }
  }

  /**
   * Generate world type based on planet attributes.
   */
  public void generateWorldType() {
    WeightedList<PlanetTypes> planetTypes = new WeightedList<>();
    if (getTemperatureType() == TemperatureType.INFERNO
        || getTemperatureType() == TemperatureType.VOLCANIC) {
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD1);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD2);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD3);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD4);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD5);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD6);
      setPlanetType(planetTypes.pickRandom());
      return;
    }
    if (getWaterLevel() == WaterLevelType.BARREN) {
      setPlanetType(PlanetTypes.BARRENWORLD1);
      return;
    }
    if (getTemperatureType() == TemperatureType.FROZEN
        || getTemperatureType() == TemperatureType.ARCTIC
        || getTemperatureType() == TemperatureType.COLD) {
      planetTypes.add(1, PlanetTypes.ICEWORLD1);
      planetTypes.add(1, PlanetTypes.ICEWORLD2);
      planetTypes.add(1, PlanetTypes.ICEWORLD3);
      planetTypes.add(1, PlanetTypes.ICEWORLD4);
      if (getWaterLevel() == WaterLevelType.DESERT
          || getWaterLevel() == WaterLevelType.ARID) {
        planetTypes.add(1, PlanetTypes.DESERTWORLD2);
        planetTypes.add(1, PlanetTypes.DESERTWORLD3);
        if (getTemperatureType() == TemperatureType.COLD) {
          planetTypes.add(1, PlanetTypes.DESERTWORLD1);
        }
      } else if (getTemperatureType() == TemperatureType.COLD) {
        planetTypes.add(1, PlanetTypes.SWAMPWORLD2);
      }
      setPlanetType(planetTypes.pickRandom());
      return;
    }
    if (getTemperatureType() == TemperatureType.HOT) {
      planetTypes.add(1, PlanetTypes.DESERTWORLD1);
      planetTypes.add(1, PlanetTypes.DESERTWORLD2);
      planetTypes.add(1, PlanetTypes.DESERTWORLD3);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD2);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD3);
      planetTypes.add(1, PlanetTypes.VOLCANICWORLD4);
    }
    if (getTemperatureType() == TemperatureType.TEMPERATE
        || getTemperatureType() == TemperatureType.TROPICAL) {
      if (getWaterLevel() == WaterLevelType.DESERT
          || getWaterLevel() == WaterLevelType.ARID) {
        planetTypes.add(1, PlanetTypes.DESERTWORLD1);
        planetTypes.add(1, PlanetTypes.DESERTWORLD2);
        planetTypes.add(1, PlanetTypes.DESERTWORLD3);
      }
      if (getWaterLevel() == WaterLevelType.HUMID) {
        planetTypes.add(1, PlanetTypes.SWAMPWORLD1);
        planetTypes.add(1, PlanetTypes.SWAMPWORLD2);
        planetTypes.add(1, PlanetTypes.SWAMPWORLD3);
        planetTypes.add(1, PlanetTypes.WATERWORLD2);
      }
      if (getWaterLevel() == WaterLevelType.MARINE) {
        planetTypes.add(1, PlanetTypes.WATERWORLD1);
        planetTypes.add(1, PlanetTypes.WATERWORLD2);
        planetTypes.add(1, PlanetTypes.WATERWORLD3);
        planetTypes.add(1, PlanetTypes.WATERWORLD5);
        planetTypes.add(1, PlanetTypes.WATERWORLD9);
      }
      if (getWaterLevel() == WaterLevelType.OCEAN) {
        planetTypes.add(1, PlanetTypes.WATERWORLD1);
        planetTypes.add(1, PlanetTypes.WATERWORLD3);
        planetTypes.add(1, PlanetTypes.WATERWORLD4);
        planetTypes.add(1, PlanetTypes.WATERWORLD5);
        planetTypes.add(1, PlanetTypes.WATERWORLD6);
        planetTypes.add(1, PlanetTypes.WATERWORLD7);
        planetTypes.add(1, PlanetTypes.WATERWORLD8);
        planetTypes.add(1, PlanetTypes.WATERWORLD9);
      }
      setPlanetType(planetTypes.pickRandom());
    }

  }
  /**
   * How many buildings can be fitted on planet
   * @return Maximum number of building can be fitted on planet
   */
  public int getGroundSize() {
    return groundSize;
  }

  /**
   * Set ground size for planet
   * @param freeGround Ground size is between 7 and 16
   */
  public void setGroundSize(final int freeGround) {
    if (freeGround > 6 && freeGround < 17) {
      this.groundSize = freeGround;
    } else {
      ErrorLogger.log("Invalid Ground size: " + freeGround);
    }
  }

  /**
   * How much ore is still available on planet
   * @return amount ore in ground
   */
  public int getAmountMetalInGround() {
    return amountMetalInGround;
  }

  /**
   * Change amount of ore.
   * Amount of must be between MINIMUM_ORE and MAXIMUM_ORE to
   * change actually the value.
   * @param amountMetalInGround New amount of metal on ground
   */
  public void setAmountMetalInGround(final int amountMetalInGround) {
    if (amountMetalInGround >= MINIMUM_ORE
        && amountMetalInGround <= MAXIMUM_ORE) {
      this.amountMetalInGround = amountMetalInGround;
    }
  }

  /**
   * How much of metal has been mined on planet and available for building.
   * @return amount of metal available
   */
  public int getMetal() {
    return metal;
  }

  /**
   * Set how much metal on planet
   * @param metal How much metal available on planet
   */
  public void setMetal(final int metal) {
    this.metal = metal;
  }

  /**
   * Planet order number in solar system
   * @return Order number
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * Set planet order number
   * @param order Order number of planet in solar system
   */
  public void setOrderNumber(final int order) {
    orderNumber = order;
  }

  /**
   * Is planet gas giant or not
   * @return True if gas giant
   */
  public boolean isGasGiant() {
    return gasGiant;
  }

  /**
   * Set gas giant flat
   * @param gasGiant True if planet is gas giant
   */
  public void setGasGiant(final boolean gasGiant) {
    this.gasGiant = gasGiant;
  }

  /**
   * Get planet's X coordinate
   * @return X coordinate
   */
  public int getX() {
    return coordinate.getX();
  }

  /**
   * Get planet's Y coordinate
   * @return Y coordinate
   */
  public int getY() {
    return coordinate.getY();
  }

  /**
   * Return planet's coordinates
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Set planet's coordinate
   * @param coordinate Planet's coordinate
   */
  public void setCoordinate(final Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * Check if planet radiation exceeds current owner's max radiation level.
   * @return True if planet radiation is higher and false if not
   */
  public boolean exceedRadiation() {
    boolean exceedRad = false;
    if (planetOwnerInfo != null
        && planetOwnerInfo.getRace().getMaxRad().getIndex()
        < getTotalRadiationLevel().getIndex()) {
      // Planet's radiation exceeds owner max rad level
      exceedRad = true;
    }
    return exceedRad;
  }

  /**
   * Get the construction list for planet
   * @return Building list of production
   */
  public Construction[] getProductionList() {
    Building[] alreadyBuilt = getBuildingList();
    ArrayList<Construction> result = new ArrayList<>();
    Construction tmp2 = null;
    tmp2 = ConstructionFactory.createByName(ConstructionFactory.EXTRA_CREDIT);
    if (tmp2 != null) {
      result.add(tmp2);
    }
    tmp2 = ConstructionFactory.createByName(ConstructionFactory.EXTRA_CULTURE);
    if (tmp2 != null && !exceedRadiation()) {
      result.add(tmp2);
    }

    if (planetOwnerInfo != null) {
      SpaceRace planetRace = planetOwnerInfo.getRace();
      // Population can construct itself
      // Lookup valid construction project for race based on singular name
      if (planetRace.hasTrait(TraitIds.CONSTRUCTED_POP)
          && getTotalPopulation() < getPopulationLimit()) {
        var popProjectId = planetRace.getNameSingle() + " citizen";
        var popProject = ConstructionFactory.createByName(popProjectId);
        if (popProject != null) {
          if (!exceedRadiation()) {
            result.add(popProject);
          }
        } else {
          ErrorLogger.log("Could not find population construction project"
              + " for race: " + planetRace.getName());
        }
      }
      // Population is eating food, add basic farms
      if (planetRace.isEatingFood()) {
        var farmBuilding = BuildingFactory.createByName("Basic farm");
        if (farmBuilding != null && !exceedRadiation()) {
          result.add(farmBuilding);
        }
      }
    }

    Building tmp = BuildingFactory.createByName("Basic mine");
    if (tmp != null) {
      result.add(tmp);
    }
    tmp = BuildingFactory.createByName("Basic factory");
    if (tmp != null) {
      result.add(tmp);
    }
    tmp = BuildingFactory.createByName("Space port");
    if (tmp != null && !exceedRadiation()) {
      if (tmp.isSingleAllowed()) {
        boolean built = false;
        for (int j = 0; j < alreadyBuilt.length; j++) {
          if (alreadyBuilt[j].getName().equals(tmp.getName())) {
            built = true;
            break;
          }
        }
        if (!built) {
          result.add(tmp);
        }
      } else {
        result.add(tmp);
      }
    }
    if (planetOwnerInfo != null) {
      String[] buildingsNames = planetOwnerInfo.getTechList()
          .getBuildingListFromTech();
      for (int i = 0; i < buildingsNames.length; i++) {
        tmp = BuildingFactory.createByName(buildingsNames[i]);
        if (tmp == null) {
          System.out.println("Null building: " + buildingsNames[i]);
        }
        if (getTotalRadiationLevel() == RadiationType.NO_RADIATION
            && tmp != null
            && (tmp.getName().equals("Radiation dampener")
                || tmp.getName().equals("Radiation well"))) {
          // No need for radiation well or dampener on small radiation planets
          tmp = null;
        }
        if (tmp != null && tmp.isOrbitalElevator() && getOrbital() == null) {
          // Orbital elevator requires orbital.
          tmp = null;
        }
        if (tmp != null) {
          if (tmp.getType() == BuildingType.FARM
              && !planetOwnerInfo.getRace().isEatingFood()) {
            // No farming buildings for races that don't eat organic food
            continue;
          }
          if (tmp.isSingleAllowed()) {
            boolean built = false;
            for (int j = 0; j < alreadyBuilt.length; j++) {
              if (alreadyBuilt[j].getName().equals(tmp.getName())) {
                built = true;
                break;
              }
              if (tmp.getName().startsWith("Planetary scanner Mk")) {
                int scannerLevel = Integer.valueOf(tmp.getName()
                    .substring(20));
                if (alreadyBuilt[j].getName().startsWith(
                    "Planetary scanner Mk")) {
                  int builtLvl = Integer.valueOf(alreadyBuilt[j].getName()
                      .substring(20));
                  if (builtLvl > scannerLevel) {
                    built = true;
                    break;
                  }
                }
              }
            }
            if (!built && !exceedRadiation()) {
              result.add(tmp);
            } else if (!built && (tmp.getName().equals("Radiation dampener")
                || tmp.getName().equals("Radiation well"))) {
              // Radiation well and dampener can be built even planet has
              // radiation.
              result.add(tmp);
            }
          } else {
            if (!exceedRadiation()) {
              result.add(tmp);
            }
          }
        }
      }
      if (this.hasSpacePort()) {
        ShipStat[] ships = planetOwnerInfo.getShipStatList();
        for (ShipStat stat : ships) {
          boolean addStat = !stat.isObsolete();
          if (orbital != null
              && stat.getDesign().getHull()
                  .getHullType() == ShipHullType.ORBITAL
              && orbital.getName().equals(stat.getDesign().getName())) {
            addStat = false;
          }
          if (addStat) {
            result.add(new Ship(stat.getDesign()));
          }
        }
      }
    }
    return result.toArray(new Construction[result.size()]);
  }

  /**
   * Get the Building list for planet
   * @return Building list of planet buildings
   */
  public Building[] getBuildingList() {
    return buildings.toArray(new Building[buildings.size()]);
  }

  /**
   * Get the total number of buildings on planet
   * @return number of buildings
   */
  public int getNumberOfBuildings() {
    return buildings.size();
  }

  /**
   * Return how many buildings there are on planet
   * @return int
   */
  public int getUsedPlanetSize() {
    return buildings.size();
  }

  /**
   * Planet size as string. Size varies from small to huge.
   * @return String
   */
  public String getSizeAsString() {
    switch (getGroundSize()) {
      case 7:
        return "tiny";
      case 8:
        return "tiny";
      case 9:
        return "small";
      case 10:
        return "small";
      case 11:
        return "medium";
      case 12:
        return "medium";
      case 13:
        return "large";
      case 14:
        return "large";
      case 15:
        return "huge";
      case 16:
        return "huge";
      default:
        return "small";
    }
  }

  /**
   * Planet size as int. Size varies from small to huge.
   * @return String
   */
  public int getSizeAsInt() {
    switch (getGroundSize()) {
      case 7:
        return 1;
      case 8:
        return 1;
      case 9:
        return 2;
      case 10:
        return 2;
      case 11:
        return 3;
      case 12:
        return 3;
      case 13:
        return 4;
      case 14:
        return 4;
      case 15:
        return 5;
      case 16:
        return 5;
      default:
        return 2;
    }
  }

  /**
   * Generate info text
   * @param activeScanned If planet is scanned in one turn
   * @param viewerInfo Realm who is view the planet
   * @return String
   */
  public String generateInfoText(final boolean activeScanned,
      final PlayerInfo viewerInfo) {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getName());
    sb.append("\n");
    if (isGasGiant()) {
      if (planetType.getWorldType() == WorldType.ICEGIANTWORLD) {
        sb.append("\n");
        sb.append("Ice Giant");
        sb.append("\n");
        sb.append("Planet is inhabitable, but planet can block scanners.");
        sb.append(" Ice giants are mostly oxygen, methane,"
            + " ammonia and hydrogen.");
      } else {
        sb.append("\n");
        sb.append("Gas Giant");
        sb.append("\n");
        sb.append("Planet is inhabitable, but planet can block scanners.");
        sb.append(" Gas giants are mostly hydrogen and helium.");
      }
    } else {
      sb.append(planetType.getTypeAsString());
      sb.append("\n");
      if (getPlanetPlayerInfo() != null) {
        sb.append("Suitable: ");
        sb.append(getPlanetPlayerInfo().getPlanetSuitabilityValue(this));
        sb.append("%\n");
      }
      if (getPlanetPlayerInfo() == null && viewerInfo != null) {
        sb.append("Suitable: ");
        sb.append(viewerInfo.getPlanetSuitabilityValue(this));
        sb.append("%\n");
      }
      if (activeScanned && event != PlanetaryEvent.NONE && eventFound) {
        sb.append(event.getExplanation());
        sb.append("\n");
      }
      sb.append("Radiation: ");
      sb.append(getTotalRadiationLevel());
      sb.append(" Temperature: ");
      sb.append(getTemperatureType().toString());
      sb.append("\n");
      sb.append("Water: ");
      sb.append(getWaterLevel().toString());
      sb.append(" Gravity: ");
      sb.append(getGravityType().toString());
      sb.append("\n");
      sb.append("Size: ");
      sb.append(getSizeAsString());
      sb.append("\n");
      if (activeScanned) {
        sb.append("Metal: ");
        sb.append(getAmountMetalInGround());
        sb.append("\n");
      }
      if (isHomeWorld()) {
        sb.append("Home world of\n");
        sb.append(SpaceRaceFactory.createOne(homeWorldId).getName());
        sb.append("\n");
      }
      if (planetOwnerInfo != null && activeScanned) {
        sb.append(planetOwnerInfo.getEmpireName());
        sb.append("\n");
        sb.append("Population: ");
        sb.append(getTotalPopulation());
        sb.append("\n");
        sb.append("Culture: ");
        sb.append(getCulture());
        sb.append("\n");
      }
      if (activeScanned && !isEventActivated()
          && event != PlanetaryEvent.NONE) {
        sb.append("\nAway team could be send down.");
        sb.append("\n");
      }

    }
    return sb.toString();
  }

  /**
   * Set planet owner index. -1 means that
   * planet is uncolonized.
   * @return the planetOwner
   */
  public int getPlanetOwnerIndex() {
    return planetOwner;
  }

  /**
   * Get planet player info
   * @return the planet player info
   */
  public PlayerInfo getPlanetPlayerInfo() {
    return planetOwnerInfo;
  }

  /**
   * Set Planet owner info and index. Use -1 and null for uncolonized.
   * @param ownerIndex the planetOwner to set
   * @param info PlayerInfo for planet owner
   */
  public void setPlanetOwner(final int ownerIndex, final PlayerInfo info) {
    this.planetOwner = ownerIndex;
    this.planetOwnerInfo = info;
    if (planetOwnerInfo == null) {
      setOrbital(null);
    }
  }

  /**
   * @return the prodResource
   */
  public int getProdResource() {
    return prodResource;
  }

  /**
   * @param prodResource the prodResource to set
   */
  public void setProdResource(final int prodResource) {
    this.prodResource = prodResource;
  }

  /**
   * Get the construction which is under construction
   * @return Construction
   */
  public Construction getUnderConstruction() {
    return underConstruction;
  }

  /**
   * Change construction to build
   * @param underConstruction New construction to build
   */
  public void setUnderConstruction(final Construction underConstruction) {
    this.underConstruction = underConstruction;
  }

  /**
   * Get actual cost of construction
   * @param build Construction
   * @param costType PRODUCTION_PRODUCTION returns production cost,
   *                 otherwise metal cost
   * @return prodcution cost
   */
  public int getActualCost(final Construction build, final int costType) {
    if (build == null) {
      return 0;
    }
    int requiredMetalCost = build.getMetalCost();
    int requiredProdCost = build.getProdCost();
    if (build instanceof Ship) {
      Ship ship = (Ship) build;
      if (ship.getHull().getHullType() == ShipHullType.ORBITAL
          && orbital != null) {
        requiredProdCost = requiredProdCost - orbital.getProdCost();
        requiredMetalCost = requiredMetalCost - orbital.getMetalCost();
        if (requiredMetalCost < 0) {
          requiredMetalCost = 0;
        }
        if (requiredProdCost < 0) {
          requiredProdCost = 0;
        }
        // Upgrade always cost a bit extra.
        requiredMetalCost = requiredMetalCost + 10;
        requiredProdCost = requiredProdCost + 10;
      }
    }
    if (costType == Planet.PRODUCTION_PRODUCTION) {
      return requiredProdCost;
    }
    return requiredMetalCost;
  }

  /**
   * Calculate sur plus food for planet. This does not take count possible
   * limitations which space race might have for surplus food. This will return
   * zero for races that do not eat normal food.
   * @return Sur plus food.
   */
  public int calculateSurPlusFood() {
    if (!planetOwnerInfo.getRace().isEatingFood()) {
      return 0;
    }
    int food = getTotalProduction(PRODUCTION_FOOD) - getTotalPopulation()
        * planetOwnerInfo.getRace().getFoodRequire() / 100;
    return food;
  }

  /**
   * Calculate food requirement for planet.
   * @return How many food is required for each turn.
   */
  public int calculateFoodRequirement() {
    int food = getTotalPopulation()
        * planetOwnerInfo.getRace().getFoodRequire() / 100;
    return food;
  }

  /**
   * Update planet food for one turn.
   * @param map StarMap
   * @return true if planet is still populated, otherwise false
   */
  private boolean updatePlanetFoodForOneTurn(final StarMap map) {
    Message msg;
    final var planetRace = planetOwnerInfo.getRace();
    // Non-eating, naturally non-growing races ignore food completely,
    // and start killing themselves if overpopulated
    if (!planetRace.isEatingFood() && planetRace.getGrowthSpeed() == 0
        && getTotalPopulation() > getPopulationLimit()) {
      var workerName = killWorkerPrioritzed();

      // TODO: Better resource reclamation calculation
      setMetal(getMetal() + 10);

      final var tplOverpopKill = "%1$s on %2$s died due to over-population."
          + " %3$s start killing others because of over-population."
          + " Remnants of killed population will be reused if possible."
          + " Population is now %4$s";
      msg = new Message(MessageType.POPULATION,
          String.format(tplOverpopKill, workerName, getName(),
              planetRace.getName(), getTotalPopulation()),
          Icons.getIconByName(Icons.ICON_DEATH));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);

      return true;
    }

    int food = 0;
    if (planetRace.isLithovorian()) {
      int require = getTotalPopulation() / 2;
      int available = getMetal();
      if (available >= require * 4) {
        food = 2;
        setMetal(available - require);
      } else if (available > require) {
        food = 1;
        setMetal(available - require);
      } else if (available == require) {
        food = 0;
        setMetal(available - require);
      } else {
        food = -1;
        setMetal(0);
      }
    } else {
      food = calculateSurPlusFood();
      if (planetRace.hasTrait(TraitIds.FIXED_GROWTH)
          && food > 0) {
        food = 1;
      }
    }

    int require = 10;
    if (planetRace.getGrowthSpeed() == 0) {
      if (food > 0) {
        food = 0;
      }
    } else {
      require = 10 * 100 / planetRace.getGrowthSpeed();
    }

    extraFood = extraFood + food;
    if (exceedRadiation() && extraFood > 0) {
      // Clear extra food if radiation is exceeded
      extraFood = 0;
    }
    if (extraFood > 0 && extraFood >= require && !isFullOfPopulation()) {
      extraFood = extraFood - require;
      if (planetRace.isLithovorian()) {
        int metalRequire = getTotalPopulation() / 2;
        int available = getTotalProduction(PRODUCTION_METAL);
        if (metalRequire >= available) {
          workers[METAL_MINERS] = workers[METAL_MINERS] + 1;
        } else {
          workers[PRODUCTION_WORKERS] = workers[PRODUCTION_WORKERS] + 1;
        }
      } else {
        workers[FOOD_FARMERS] = workers[FOOD_FARMERS] + 1;
      }
      if (governor != null) {
        governor.getStats().addOne(StatType.POPULATION_GROWTH);
      }
      msg = new Message(MessageType.POPULATION,
          getName() + " has population growth! Population is now "
              + getTotalPopulation(),
          Icons.getIconByName(Icons.ICON_PEOPLE));
      if (isFullOfPopulation()) {
        msg = new Message(MessageType.POPULATION,
            getName() + " has population growth! Population is now "
                + getTotalPopulation() + ". Population limit has reached!",
            Icons.getIconByName(Icons.ICON_PEOPLE));
      }
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);
      if (Game.getTutorial() != null && map != null
          && map.isTutorialEnabled()
          && getTotalPopulation() >= 5) {
        String tutorialText = Game.getTutorial().showTutorialText(128);
        if (tutorialText != null) {
          msg = new Message(MessageType.INFORMATION,
              tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
          planetOwnerInfo.getMsgList().addNewMessage(msg);
        }
      }
    }
    if (isFullOfPopulation()) {
      if (extraFood > require) {
        // Over populated no extra food more than maximum required.
        extraFood = require;
      }
      if (getTotalPopulation() > getPopulationLimit()) {
        msg = new Message(MessageType.POPULATION,
            getName() + " has population over the limit!",
            Icons.getIconByName(Icons.ICON_DEATH));
        // Over populated requires more food
        extraFood--;
      }
    }
    if (extraFood < 0 && extraFood <= require) {
      extraFood = 0;
      String workerName = killWorkerPrioritzed();
      msg = new Message(MessageType.POPULATION,
          getName() + " has " + workerName + " died! "
              + "Population is now " + getTotalPopulation(),
          Icons.getIconByName(Icons.ICON_DEATH));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);
      if (getTotalPopulation() < 1) {
        ErrorLogger.log("This probably should not happen but "
            + planetOwnerInfo.getEmpireName()
            + " has lost planet by starvation!!!");
        msg = new Message(MessageType.POPULATION,
            getName() + " has lost last population. " + getName()
                + " is now uncolonized!",
            Icons.getIconByName(Icons.ICON_DEATH));
        msg.setCoordinate(getCoordinate());
        msg.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(msg);
        if (getGovernor() != null) {
          getGovernor().setJob(Job.UNASSIGNED);
          setGovernor(null);
        }
        setPlanetOwner(-1, null);
        return false;
      }
    }
    return true;
  }

  /**
   * Try to kill one worker on the planet, but prioritize "extra" roles
   * when selecting which worker to kill.
   * TODO: Merge with killOneWorker()?
   * @return Profession of killed worker, or null if kill not possible
   */
  private String killWorkerPrioritzed() {
    String workerName = null;
    if (workers[CULTURE_ARTIST] > 0) {
      workers[CULTURE_ARTIST]--;
      workerName = "Artist";
    } else if (workers[METAL_MINERS] > 0) {
      workers[METAL_MINERS]--;
      workerName = "Miner";
    } else if (workers[RESEARCH_SCIENTIST] > 0) {
      workers[RESEARCH_SCIENTIST]--;
      workerName = "Scientist";
    } else if (workers[PRODUCTION_WORKERS] > 0) {
      workers[PRODUCTION_WORKERS]--;
      workerName = "Worker";
    } else if (workers[FOOD_FARMERS] > 0) {
      workers[FOOD_FARMERS]--;
      workerName = "Farmer";
    }
    return workerName;
  }

  /**
   * Colonize planet with minor orbital.
   */
  public void colonizeWithOrbital() {
    ShipStat stat = planetOwnerInfo.findMinorOrbitalDesign();
    // We need to create here a new instance
    Ship ship = new Ship(stat.getDesign());
    stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
    stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
    orbital = ship;
    if (!hasCertainBuilding("Space port")
        && buildings.size() < getGroundSize()) {
      addBuilding(BuildingFactory.createByName("Space port"));
    }
  }

  /**
   * Check if special projects are done in planet.
   * @param map Starmap
   * @param requiredMetalCost Required metal cost
   * @param requiredProdCost Required production cost
   */
  private void checkIfSpecialProjectsAreDone(final StarMap map,
      final int requiredMetalCost, final int requiredProdCost) {
    Message msg;
    if (underConstruction.getName()
        .equals(ConstructionFactory.MECHION_CITIZEN)) {
      if (governor != null) {
        governor.getStats().addOne(StatType.POPULATION_GROWTH);
      }
      metal = metal - requiredMetalCost;
      prodResource = prodResource - requiredProdCost;
      workers[PRODUCTION_WORKERS] = workers[PRODUCTION_WORKERS] + 1;
      String nextBuilding = "";
      String finishedBuilding = underConstruction.getName();
      if (governor != null
          && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
        int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
        Attitude attitude = LeaderUtility.getRulerAttitude(governor);
        PlanetHandling.chooseNextConstruction(map, this, index, attitude);
        nextBuilding = governor.getCallName() + " selected new "
            + "construction process where "
            + getUnderConstruction().getName()
            + " will be built. Estimated building time is "
            + getProductionTimeAsString(underConstruction) + ".";
      }
      msg = new Message(MessageType.CONSTRUCTION,
          getName() + " built " + finishedBuilding
              + ". " + nextBuilding,
          Icons.getIconByName(Icons.ICON_PEOPLE));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);
      return;
    }
    if (underConstruction.getName()
        .equals(ConstructionFactory.SYNTHDROID_CITIZEN)) {
      if (governor != null) {
        governor.getStats().addOne(StatType.POPULATION_GROWTH);
      }
      metal = metal - requiredMetalCost;
      prodResource = prodResource - requiredProdCost;
      if (calculateSurPlusFood() > 0) {
        workers[PRODUCTION_WORKERS] = workers[PRODUCTION_WORKERS] + 1;
      } else {
        workers[FOOD_FARMERS] = workers[FOOD_FARMERS] + 1;
      }
      String nextBuilding = "";
      String finishedBuilding = underConstruction.getName();
      if (governor != null
          && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
        int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
        Attitude attitude = LeaderUtility.getRulerAttitude(governor);
        PlanetHandling.chooseNextConstruction(map, this, index, attitude);
        nextBuilding = governor.getCallName() + " selected new "
            + "construction process where "
            + getUnderConstruction().getName()
            + " will be built. Estimated building time is "
            + getProductionTimeAsString(underConstruction) + ".";
      }
      msg = new Message(MessageType.CONSTRUCTION,
          getName() + " built " + finishedBuilding
              + ". " + nextBuilding,
          Icons.getIconByName(Icons.ICON_PEOPLE));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);
      return;
    }
    if (underConstruction.getName()
        .equals(ConstructionFactory.EXTRA_CULTURE)) {
      metal = metal - requiredMetalCost;
      prodResource = prodResource - requiredProdCost;
      culture = culture + 5;
      String nextBuilding = "";
      String finishedBuilding = underConstruction.getName();
      if (governor != null
          && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
        int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
        Attitude attitude = LeaderUtility.getRulerAttitude(governor);
        PlanetHandling.chooseNextConstruction(map, this, index, attitude);
        nextBuilding = governor.getCallName() + " selected new "
            + "construction process where "
            + getUnderConstruction().getName()
            + " will be built. Estimated building time is "
            + getProductionTimeAsString(underConstruction) + ".";
      }
      msg = new Message(MessageType.CONSTRUCTION,
          getName() + " built " + finishedBuilding
              + ". " + nextBuilding,
          Icons.getIconByName(Icons.ICON_CULTURE));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);
      return;
    }
    if (underConstruction.getName()
        .equals(ConstructionFactory.EXTRA_CREDIT)) {
      metal = metal - requiredMetalCost;
      prodResource = prodResource - requiredProdCost;
      planetOwnerInfo
          .setTotalCredits(planetOwnerInfo.getTotalCredits() + 12);
      String nextBuilding = "";
      String finishedBuilding = underConstruction.getName();
      if (governor != null
          && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
        int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
        Attitude attitude = LeaderUtility.getRulerAttitude(governor);
        PlanetHandling.chooseNextConstruction(map, this, index, attitude);
        nextBuilding = governor.getCallName() + " selected new "
            + "construction process where "
            + getUnderConstruction().getName()
            + " will be built. Estimated building time is "
            + getProductionTimeAsString(underConstruction) + ".";
      }
      msg = new Message(MessageType.CONSTRUCTION,
          getName() + " built " + finishedBuilding
              + ". " + nextBuilding,
          Icons.getIconByName(Icons.ICON_CREDIT));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      planetOwnerInfo.getMsgList().addNewMessage(msg);
    }
  }

  /**
   * Check if construction is done.
   * @param enemyOrbiting if true it means that other player,
   *        has fleet orbiting on planet.
   * @param map StarMap can be null in tests
   */
  private void checkIfConstructionIsDone(final boolean enemyOrbiting,
      final StarMap map) {
    Message msg;
    int requiredMetalCost = getActualCost(underConstruction,
        Planet.PRODUCTION_METAL);
    int requiredProdCost = getActualCost(underConstruction,
        Planet.PRODUCTION_PRODUCTION);
    // Making building happens at the end
    if (underConstruction != null
        && metal >= requiredMetalCost
        && prodResource >= requiredProdCost) {
      if (underConstruction instanceof Building
          && groundSize > buildings.size()) {
        boolean canBeBuilt = true;
        Building building = (Building) underConstruction;
        if (building.getScientificAchievement() && map != null) {
          NewsData news = NewsFactory.makeScientificAchivementNews(
              planetOwnerInfo, this, building, map.getStarYear());
          map.getNewsCorpData().addNews(news);
          EventOnPlanet eventOnPlanet = new EventOnPlanet(
              EventType.PLANET_BUILDING, getCoordinate(),
              getName(), getPlanetOwnerIndex());
          eventOnPlanet.setText(news.getNewsText());
          map.getHistory().addEvent(eventOnPlanet);
        }
        if (building.getName().equals("United Galaxy Tower") && map != null) {
          NewsData news = NewsFactory.makeUnitedGalaxyTowerNews(
              planetOwnerInfo, this, map.getStarYear());
          map.getNewsCorpData().addNews(news);
          EventOnPlanet eventOnPlanet = new EventOnPlanet(
              EventType.PLANET_BUILDING, getCoordinate(),
              getName(), getPlanetOwnerIndex());
          eventOnPlanet.setText(news.getNewsText());
          map.getHistory().addEvent(eventOnPlanet);
          if (Game.getTutorial() != null
              && map.isTutorialEnabled()
              && map.getPlayerList().getPlayerInfoByIndex(0).isHuman()) {
            String tutorialText = Game.getTutorial().showTutorialText(155);
            if (tutorialText != null) {
              Message msgTut = new Message(MessageType.INFORMATION,
                  tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
              map.getPlayerList().getPlayerInfoByIndex(0).getMsgList()
                  .addUpcomingMessage(msgTut);
            }
          }
        }
        if (building.isBroadcaster() && map != null) {
          NewsData news = NewsFactory.makeBroadcasterBuildingNews(
              planetOwnerInfo, this, building, map.getStarYear());
          map.getNewsCorpData().addNews(news);
          EventOnPlanet eventOnPlanet = new EventOnPlanet(
              EventType.PLANET_BUILDING, getCoordinate(),
              getName(), getPlanetOwnerIndex());
          eventOnPlanet.setText(news.getNewsText());
          map.getHistory().addEvent(eventOnPlanet);
        }
        if (building.getName().equals("Galactic sports center")
            && map != null) {
          NewsData news = NewsFactory.makeGalacticSportsNews(
              planetOwnerInfo, this);
          map.getNewsCorpData().addNews(news);
          EventOnPlanet eventOnPlanet = new EventOnPlanet(
              EventType.PLANET_BUILDING, getCoordinate(),
              getName(), getPlanetOwnerIndex());
          eventOnPlanet.setText(news.getNewsText());
          map.getHistory().addEvent(eventOnPlanet);
          Vote vote = new Vote(VotingType.GALACTIC_OLYMPIC_PARTICIPATE,
              map.getPlayerList().getCurrentMaxRealms(), 10);
          vote.setOrganizerIndex(this.getPlanetOwnerIndex());
          vote.setPlanetName(getName());
          map.getVotes().getVotes().add(vote);
          if (Game.getTutorial() != null && map.isTutorialEnabled()) {
            String tutorialText = Game.getTutorial().showTutorialText(98);
            if (tutorialText != null) {
              msg = new Message(MessageType.INFORMATION, tutorialText,
                  Icons.getIconByName(Icons.ICON_TUTORIAL));
              msg.setCoordinate(getCoordinate());
              map.getPlayerList().getPlayerInfoByIndex(0).getMsgList()
                  .addNewMessage(msg);
            }
          }
        }
        if (building.isOrbitalElevator()) {
          Building oldElevator = null;
          if (getOrbital() != null) {
            for (Building planetBuilding : getBuildingList()) {
              if (planetBuilding.isOrbitalElevator()) {
                oldElevator = planetBuilding;
                break;
              }
            }
            if (oldElevator != null) {
              removeBuilding(oldElevator);
            }
          } else {
            canBeBuilt = false;
            msg = new Message(MessageType.CONSTRUCTION,
                getName() + " cannot be built " + underConstruction.getName()
                    + " since there is no orbital on the planet.",
                Icons.getIconByName(Icons.ICON_STARBASE));
            msg.setCoordinate(getCoordinate());
            msg.setMatchByString(getName());
            planetOwnerInfo.getMsgList().addNewMessage(msg);
          }
        }
        if (canBeBuilt) {
          metal = metal - requiredMetalCost;
          prodResource = prodResource - requiredProdCost;
          if (governor != null) {
            governor.setExperience(governor.getExperience()
                + underConstruction.getProdCost() / 2);
            governor.getStats().addOne(StatType.NUMBER_OF_BUILDINGS_BUILT);
          }
          buildings.add((Building) underConstruction);
          String nextBuilding = "";
          String finishedBuilding = underConstruction.getName();
          if (building.isSingleAllowed()) {
            setUnderConstruction(getProductionList()[0]);
          }
          if (governor != null
              && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
            int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
            Attitude attitude = LeaderUtility.getRulerAttitude(governor);
            PlanetHandling.chooseNextConstruction(map, this, index, attitude);
            nextBuilding = governor.getCallName() + " selected new "
                + "construction process where "
                + getUnderConstruction().getName()
                + " will be built. Estimated building time is "
                + getProductionTimeAsString(underConstruction) + ".";
          }
          msg = new Message(MessageType.CONSTRUCTION,
              getName() + " built " + finishedBuilding
                  + ". " + nextBuilding,
              Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
          msg.setCoordinate(getCoordinate());
          msg.setMatchByString(getName());
          planetOwnerInfo.getMsgList().addNewMessage(msg);
        }
      } else if (underConstruction instanceof Ship && !enemyOrbiting) {
        metal = metal - requiredMetalCost;
        prodResource = prodResource - requiredProdCost;
        if (governor != null) {
          governor.setExperience(governor.getExperience()
              + underConstruction.getProdCost() / 2);
        }
        ShipStat stat = planetOwnerInfo.getShipStatByName(
            underConstruction.getName());
        if (stat == null) {
          msg = new Message(MessageType.CONSTRUCTION,
              getName() + " cannot built ship "
                  + underConstruction.getName()
                  + " due that blue prints are missing!",
              Icons.getIconByName(Icons.ICON_HULL_TECH));
          msg.setCoordinate(getCoordinate());
          msg.setMatchByString(getName());
          planetOwnerInfo.getMsgList().addNewMessage(msg);
        } else {
          // We need to create here a new instance
          Ship ship = new Ship(stat.getDesign());
          stat.setNumberOfBuilt(stat.getNumberOfBuilt() + 1);
          stat.setNumberOfInUse(stat.getNumberOfInUse() + 1);
          if (stat.getDesign().getTotalMilitaryPower() > 0) {
            culture = culture + stat.getDesign().getTotalMilitaryPower() / 4;
          }
          if (ship.getHull().getHullType() == ShipHullType.ORBITAL) {
            StringBuilder sb = new StringBuilder();
            if (orbital != null) {
              sb.append(orbital.getName());
              if (orbital.getTheoreticalMilitaryPower() > ship
                  .getTheoreticalMilitaryPower()) {
                sb.append(" downgraded to ");
              } else {
                sb.append(" upgraded to ");
              }
              sb.append(ship.getName());
              sb.append(" at ");
            } else {
              sb.append(ship.getName());
              sb.append(" built at ");
            }
            sb.append(getName());
            sb.append(".");
            orbital = ship;
            if (governor != null
                && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
              int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
              Attitude attitude = LeaderUtility.getRulerAttitude(governor);
              PlanetHandling.chooseNextConstruction(map, this, index, attitude);
              sb.append(" " + governor.getCallName() + " selected new "
                  + "construction process where "
                  + getUnderConstruction().getName()
                  + " will be built. Estimated building time is "
                  + getProductionTimeAsString(underConstruction) + ".");
            }
            msg = new Message(MessageType.CONSTRUCTION, sb.toString(),
                Icons.getIconByName(Icons.ICON_STARBASE));
            msg.setCoordinate(getCoordinate());
            msg.setMatchByString(getName());
            planetOwnerInfo.getMsgList().addNewMessage(msg);
          } else {
            Fleet fleet = new Fleet(ship, getX(), getY());
            if (governor != null) {
              governor.getStats().addOne(StatType.NUMBER_OF_SHIPS_BUILT);
            }
            planetOwnerInfo.getFleets().add(fleet);
            if (planetOwnerInfo.getMissions() != null) {
              Mission mission = planetOwnerInfo.getMissions()
                  .getMissionForPlanet(getName(), MissionPhase.BUILDING);
              if (mission != null) {
                if (mission.getFleetName() == null) {
                  if (mission.getType() == MissionType.COLONIZE) {
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName("Colony"));
                    mission.setFleetName(fleet.getName());
                  }
                  if (mission.getType() == MissionType.DEPLOY_STARBASE
                      && fleet.getStarbaseShip() != null) {
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName("Space Station"));
                    mission.setFleetName(fleet.getName());
                  }
                  if (mission.getType() == MissionType.TRADE_FLEET) {
                    String nameFleet = "Trader";
                    if (DiceGenerator.getBoolean()) {
                      nameFleet = "Merchant";
                    }
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName(nameFleet));
                    mission.setFleetName(fleet.getName());
                  }
                  if (mission.getType() == MissionType.SPY_MISSION) {
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName(ship.getName()));
                    mission.setFleetName(fleet.getName());
                  }
                  if (mission.getType() == MissionType.DIPLOMATIC_DELEGACY) {
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName(ship.getName()));
                    mission.setFleetName(fleet.getName());
                  }
                  if (mission.getType() == MissionType.EXPLORE) {
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName("Scout"));
                    mission.setFleetName(fleet.getName());
                  }
                } else {
                  fleet.setName(planetOwnerInfo.getFleets()
                      .generateUniqueName(mission.getFleetName()));
                }
                if (mission.getType() == MissionType.DEFEND) {
                  // For now one ship is enough for defend
                  mission.setPhase(MissionPhase.EXECUTING);
                } else if (mission.getType() == MissionType.COLONIZE) {
                  mission.setPhase(MissionPhase.LOADING);
                } else if (mission.getType() == MissionType.TRADE_FLEET) {
                  mission.setPhase(MissionPhase.LOADING);
                } else if (mission.getType() == MissionType.GATHER) {
                  if (ship.isTrooperModule()) {
                    // Loads trooper first
                    mission.setPhase(MissionPhase.LOADING);
                  } else {
                    mission.setPhase(MissionPhase.TREKKING);
                  }
                  fleet.setName(planetOwnerInfo.getFleets()
                      .generateUniqueName("Gather"));
                  mission.setFleetName(fleet.getName());
                } else if (mission.getType() == MissionType.EXPLORE) {
                  mission.setPhase(MissionPhase.TREKKING);
                  Sun sun = map.getAboutNearestSolarSystem(fleet.getX(),
                      fleet.getY(), getPlanetPlayerInfo(), null);
                  mission.setTarget(sun.getCenterCoordinate());
                  mission.setSunName(sun.getName());
                } else {
                  if (mission.getFleetName() != null) {
                    mission.setPhase(MissionPhase.TREKKING);
                  } else {
                    // Failed to build the correct fleet.
                    mission.setPhase(MissionPhase.PLANNING);
                  }
                }
              } else {
                if (ship.getTotalMilitaryPower() > 0) {
                  if (ship.isSpyShip()) {
                    mission = new Mission(MissionType.SPY_MISSION,
                        MissionPhase.LOADING, getCoordinate());
                    if (!planetOwnerInfo.isHuman()) {
                      planetOwnerInfo.getMissions().add(mission);
                    }
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName(ship.getName()));
                    mission.setFleetName(fleet.getName());
                  } else if (fleet.isScoutFleet()) {
                    if (DiceGenerator.getRandom(3) == 0) {
                      // Scout ship is for defending too
                      fleet.setName(planetOwnerInfo.getFleets()
                          .generateUniqueName("Defender"));
                    } else {
                      fleet.setName(planetOwnerInfo.getFleets()
                          .generateUniqueName("Scout"));
                    }
                  } else if (fleet.isPrivateerFleet()) {
                    fleet.setName(planetOwnerInfo.getFleets()
                        .generateUniqueName("Privateer"));
                  } else {
                    // No mission for planet, so just adding defender
                    // If not human player
                    if (!planetOwnerInfo.isHuman()) {
                      fleet.setName(planetOwnerInfo.getFleets()
                          .generateUniqueName("Defender"));
                    } else {
                      // For humans just take the ship name for fleet name
                      fleet.setName(planetOwnerInfo.getFleets()
                          .generateUniqueName(ship.getName()));
                    }
                  }
                }
              }
            }
            String nextBuilding = "";
            String finishedBuilding = underConstruction.getName();
            if (governor != null
                && getGovernorGuide() != Planet.PASSIVE_GOVERNOR) {
              int index = map.getPlayerList().getIndex(getPlanetPlayerInfo());
              Attitude attitude = LeaderUtility.getRulerAttitude(governor);
              PlanetHandling.chooseNextConstruction(map, this, index, attitude);
              nextBuilding = governor.getCallName() + " selected new "
                  + "construction process where "
                  + getUnderConstruction().getName()
                  + " will be built. Estimated building time is "
                  + getProductionTimeAsString(underConstruction) + ".";
            }
            msg = new Message(MessageType.CONSTRUCTION,
                getName() + " built " + finishedBuilding
                    + ". " + nextBuilding,
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setCoordinate(getCoordinate());
            msg.setMatchByString(getName());
            planetOwnerInfo.getMsgList().addNewMessage(msg);
          }
        }
      } else if (underConstruction instanceof Building
          && groundSize <= buildings.size()) {
        if (!planetOwnerInfo.isHuman()
            && planetOwnerInfo.getAiDifficulty() != AiDifficulty.WEAK) {
          Attitude attitude = null;
          if (governor != null) {
            attitude = LeaderUtility.getRulerAttitude(governor);
          }
          PlanetHandling.removeWorstBuilding(map, this, getPlanetOwnerIndex(),
              attitude);
        }
        String suggestion = "";
        if (governor != null) {
          Building newBuild = (Building) getUnderConstruction();
          Attitude attitude = LeaderUtility.getRulerAttitude(governor);
          int fleetCap = map.getTotalFleetCapacity(getPlanetPlayerInfo());
          double fleetSize = getPlanetPlayerInfo().getFleets()
              .getTotalFleetCapacity();
          boolean nearFleetLimit = false;
          if (fleetSize + 1 > fleetCap) {
            nearFleetLimit = true;
          }

          Building worst = PlanetHandling.getWorstBuilding(this,
              getPlanetPlayerInfo(), attitude, newBuild, nearFleetLimit);
          if (worst != null) {
            suggestion = " " + governor.getCallName() + " suggest destroying "
                + worst.getName() + " from the planet.";
          }
        }
        msg = new Message(MessageType.CONSTRUCTION, getName()
            + " is already full of buildings! "
            + underConstruction.getName() + " cannot be complete!" + suggestion,
            Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
        msg.setCoordinate(getCoordinate());
        msg.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(msg);
      } else if (underConstruction instanceof Ship && enemyOrbiting) {
        msg = new Message(MessageType.PLANETARY, getName()
            + " has another Realm's fleet orbiting so ship construction of "
            + underConstruction.getName() + " cannot be complete!",
            Icons.getIconByName(Icons.ICON_HULL_TECH));
        msg.setCoordinate(getCoordinate());
        msg.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(msg);
        if (!planetOwnerInfo.isHuman()
            && planetOwnerInfo.getAiDifficulty() == AiDifficulty.CHALLENGING
            && planetOwnerInfo.getMissions().getDestroyFleetMission(
                getCoordinate()) == null) {
          Mission destroy = new Mission(MissionType.DESTROY_FLEET,
              MissionPhase.PLANNING, getCoordinate());
          planetOwnerInfo.getMissions().add(destroy);
        }
      } else {
        checkIfSpecialProjectsAreDone(map, requiredMetalCost, requiredProdCost);
      }
    }
  }

  /**
   * Update planet for one turn
   * @param enemyOrbiting if true it means that other player,
   *        has fleet orbiting on planet.
   * @param map StarMap can be null in tests
   */
  public void updateOneTurn(final boolean enemyOrbiting, final StarMap map) {
    if (planetOwnerInfo == null) {
      return;
    }

    if (governor != null) {
      if (governor.getJob() == Job.DEAD) {
        governor = null;
      } else {
        governor.setExperience(governor.getExperience()
            + getTotalPopulation());
      }
    } else {
      if (Game.getTutorial() != null && map != null
          && map.isTutorialEnabled()
          && map.getTurn() > 10) {
        String tutorialText = Game.getTutorial().showTutorialText(122);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.INFORMATION,
              tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
          planetOwnerInfo.getMsgList().addNewMessage(msg);
        }
      }
    }

    happinessEffect = HappinessEffect.createHappinessEffect(
        calculateHappiness());
    final var happinessType = happinessEffect.getType();
    // Report planet is working more/less due to happiness
    if (happinessType != HappinessBonus.KILL_POPULATION
        && happinessType != HappinessBonus.DESTROY_BUILDING
        && happinessType != HappinessBonus.NONE) {
      if (happinessEffect.getValue() > 0) {
        final var tplProdBonus = "Population of %1$s is working harder due to"
            + "happiness. Planet's %2$s has temporarily increased by %3$d.";
        Message message = new Message(MessageType.PLANETARY,
            String.format(tplProdBonus, getName(), happinessType.getName(),
                happinessEffect.getValue()),
            Icons.getIconByName(Icons.ICON_VERY_HAPPY));
        message.setCoordinate(getCoordinate());
        message.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(message);
      }
      if (happinessEffect.getValue() < 0) {
        final var tplProdMalus = "Population of %1$s is working less due to"
            + "happiness. Planet's %2$s has temporarily decreased by %3$d.";
        Message message = new Message(MessageType.PLANETARY,
            String.format(tplProdMalus, getName(), happinessType.getName(),
                happinessEffect.getValue()),
            Icons.getIconByName(Icons.ICON_VERY_SAD));
        message.setCoordinate(getCoordinate());
        message.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(message);
      }
    }

    int minedMetal = getTotalProduction(PRODUCTION_METAL);
    if (minedMetal <= amountMetalInGround && minedMetal >= 0) {
      amountMetalInGround = amountMetalInGround - minedMetal;
      metal = metal + minedMetal;
    } else if (minedMetal > 0) {
      metal = metal + amountMetalInGround;
      amountMetalInGround = 0;
    }
    metal = metal + getTotalProductionFromBuildings(PRODUCTION_MATERIAL);
    prodResource = prodResource + getTotalProduction(PRODUCTION_PRODUCTION);
    planetOwnerInfo.setTotalCredits(planetOwnerInfo.getTotalCredits()
        + getTotalProduction(PRODUCTION_CREDITS));
    culture = culture + getTotalProduction(PRODUCTION_CULTURE);

    boolean alive = updatePlanetFoodForOneTurn(map);
    // Planet population has died.
    if (!alive) {
      return;
    }

    // Forcing planet to have something to construct if player is human.
    if (planetOwnerInfo.isHuman() && underConstruction == null) {
      setUnderConstruction(ConstructionFactory.createByName("Extra credit"));
    }

    checkIfConstructionIsDone(enemyOrbiting, map);

    Message msg;
    if (happinessType == HappinessBonus.KILL_POPULATION) {
      // Need to remember owner if last population is killed
      PlayerInfo oldOwner = planetOwnerInfo;
      killOneWorker("angry mob", "angry mob", map);
      final var tplAngryMob = "Population of %1$s has formed an angry mob."
          + " One population died in the riots!";
      msg = new Message(MessageType.PLANETARY,
          String.format(tplAngryMob, getName()),
          Icons.getIconByName(Icons.ICON_DEATH));
      msg.setCoordinate(getCoordinate());
      msg.setMatchByString(getName());
      oldOwner.getMsgList().addNewMessage(msg);
      // Planet got abandonned
      if (planetOwnerInfo == null) {
        return;
      }
    }
    if (happinessType == HappinessBonus.DESTROY_BUILDING) {
      Building destroyed = destroyOneBuilding();
      if (destroyed != null) {
        final var tplDestroyed = "%1$s population was so angry that"
            + " they destroyed %2$s in the riots!";
        msg = new Message(MessageType.PLANETARY,
            String.format(tplDestroyed, getName(), destroyed.getName()),
            Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
        msg.setCoordinate(getCoordinate());
        msg.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(msg);
      }
    }

    happinessEffect = new HappinessEffect(HappinessBonus.NONE, 0);

    if (planetOwnerInfo.getGovernment().isImmuneToHappiness()
        && planetOwnerInfo.getWarFatigue() < 0) {
      Building destroyed = destroyOneBuilding();
      if (destroyed != null) {
        final var tplCollapsed = "%1$s building %2$s collapsed"
            + " due to lack of funding!";
        planetOwnerInfo.setWarFatigue(planetOwnerInfo.getWarFatigue() + 1);
        msg = new Message(MessageType.PLANETARY,
            String.format(tplCollapsed, getName(), destroyed.getName()),
            Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
        msg.setCoordinate(getCoordinate());
        msg.setMatchByString(getName());
        planetOwnerInfo.getMsgList().addNewMessage(msg);
      }
    }

    if (getCulture() == 0) {
      // Planet is owned but no culture, setting it to 1
      setCulture(1);
    }

    if (orbital != null) {
      // Orbital is always fixed in one turn.
      orbital.fixShip(true);
    }
  }

  /**
   * Get the total production without taxes
   * @return int
   */
  private int getTotalProductionWithoutTax() {
    int mult;
    int result = 0;
    int div = 100;
    mult = planetOwnerInfo.getRace().getProductionSpeed(getGravityType());
    // Planet always produces +1 production
    result = workers[PRODUCTION_PRODUCTION] * mult / div + 1
        + getTotalProductionFromBuildings(PRODUCTION_PRODUCTION);
    return result;

  }

  /**
   * @return the tax
   */
  public int getTax() {
    return tax;
  }

  /**
   * Get Planet's population limit based on race on planet.
   * @return Planet's population limit
   */
  public int getPopulationLimit() {
    return getPopulationLimit(getPlanetPlayerInfo());
  }

  /**
   * Get Planet's population limit based on certain realm.
   * @param info PlayerInfo
   * @return Planet's population limit
   */
  public int getPopulationLimit(final PlayerInfo info) {
    int result = 0;
    if (info != null) {
      result = info.getRace().getExtraPopulation();
    }
    result = result + getGroundSize();
    if (info != null) {
      int percent = info.getPlanetSuitabilityValue(this);
      result = result * percent / 100;
      if (result < 2 && percent > 0) {
        result = 2;
      }
    }
    if (info != null
        && info.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      result = 0;
      if (getPlanetType() == PlanetTypes.ARTIFICIALWORLD1) {
        result = getGroundSize();
      }
      if (orbital == null) {
        return result;
      }
      if (orbital.getHull().getSize() == ShipSize.SMALL) {
        if (orbital.getHull().getName().equals("Minor orbital")) {
          result = result + 4;
        } else {
          result = result + 6;
        }
      }
      if (orbital.getHull().getSize() == ShipSize.MEDIUM) {
        result = result + 8;
      }
      if (orbital.getHull().getSize() == ShipSize.LARGE) {
        result = result + 10;
      }
      if (orbital.getHull().getSize() == ShipSize.HUGE) {
        result = result + 12;
      }
    }
    return result;
  }
  /**
   * Is planet full of population or not
   * @return True if planet is full of population
   */
  public boolean isFullOfPopulation() {
    if (getTotalPopulation() >= getPopulationLimit()) {
      return true;
    }
    return false;
  }

  /**
   * Set tax cannot be bigger than maximum production
   * @param taxLevel the tax to set
   * @param force if true taxLevel can be beyond the limits.
   */
  public void setTax(final int taxLevel, final boolean force) {
    if (force) {
      this.tax = taxLevel;
    } else {
      int max = getTotalProductionWithoutTax();
      this.tax = taxLevel;
      if (this.tax > max) {
        this.tax = max;
      }
      if (this.tax < 0) {
        this.tax = 0;
      }
    }
  }

  /**
   * Get planet recycle bonus
   * @return Recycle bonus
   */
  public int getRecycleBonus() {
    int result = 0;
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getRecycleBonus() > result) {
        result = building.getRecycleBonus();
      }
    }
    return result;
  }

  /**
   * Get planet Scanner level
   * @return Scanner level
   */
  public int getScannerLvl() {
    int result = 2;
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getScanRange() > 0 && result < building.getScanRange()) {
        result = building.getScanRange();
      }
    }
    if (orbital != null && orbital.getScannerLvl() > result) {
      result = orbital.getScannerLvl();
    }
    return result;
  }

  /**
   * Get planet's Turret level
   * @return Turret Level
   */
  public int getTurretLvl() {
    int result = 0;
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getDefenseDamage() > 0) {
        result = result + building.getDefenseDamage();
      }
    }
    return result;
  }

  /**
   * Does planet has certain building with specific name?
   * @param buildingName Building name to look for-
   * @return True if found, otherwise false.
   */
  public boolean hasCertainBuilding(final String buildingName) {
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getName().equals(buildingName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if planet has United Galaxy Tower?
   * @return True if planet has United Galaxy Tower?
   */
  public boolean hasTower() {
    return hasCertainBuilding("United Galaxy Tower");
  }

  /**
   * Check if planet has broadcaster building.
   * @return True if has, false if not.
   */
  public boolean broadcaster() {
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.isBroadcaster()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if planet has revealing building.
   * This could be broadcaster or Galactic sport center.
   * @return True if has, false if not.
   */
  public boolean revealing() {
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.isBroadcaster()) {
        return true;
      }
      if (building.getName().equalsIgnoreCase("Galactic sports center")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if planet has Space port for building ships
   * @return True if planet has space port, otherwise false.
   */
  public boolean hasSpacePort() {
    return hasCertainBuilding("Space port");
  }

  /**
   * Get planet Scanner Cloaking detection level
   * @return Cloaking detection level
   */
  public int getCloakingDetectionLvl() {
    int result = 0;
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getScanCloakingDetection() > 0
          && result < building.getScanCloakingDetection()) {
        result = building.getScanCloakingDetection();
      }
    }
    if (orbital != null && orbital.getScannerDetectionLvl() > result) {
      result = orbital.getScannerDetectionLvl();
    }
    return result;
  }

  /**
   * How many buildings with same name planet has
   * @param buildingName Building name to search
   * @return number of buildings with same name
   */
  public int howManyBuildings(final String buildingName) {
    int result = 0;
    Building[] buildingsArray = getBuildingList();
    for (Building building : buildingsArray) {
      if (building.getName().equals(buildingName)) {
        result = result + 1;
      }
    }
    return result;
  }

  /**
   * Get planet's culture points
   * @return culture points for planet
   */
  public int getCulture() {
    return culture;
  }

  /**
   * Kill randomly one worker. This could be used for example on bombing.
   * This will also kill governor if one exists on planet.
   * Kill governor requires attack type and reasons.
   * Also StarMap is required for news and history events.
   * @param attackType conquest, bombing, nuking
   * @param reason Explain why governor died like "conquest of planet",
   *               "nuclear blast"
   * @param starMap StarMap add news and history event. If null then history
   *                or news is not added.
   */
  public void killOneWorker(final String attackType, final String reason,
      final StarMap starMap) {
    if (getTotalPopulation() > 0) {
      ArrayList<Integer> list = new ArrayList<>();
      for (int i = 0; i < workers.length; i++) {
        if (workers[i] > 0) {
          list.add(Integer.valueOf(i));
        }
      }
      int index = DiceGenerator.getRandom(list.size() - 1);
      workers[list.get(index)]--;
    }
    if (getTotalPopulation() == 0) {
      killGovernor(attackType, reason, starMap);
      setPlanetOwner(-1, null);
    }
  }

  /**
   * Kill randomly one worker. This could be used for example on bombing.
   */
  public void killOneWorker() {
    killOneWorker("attack", "attack", null);
  }

  /**
   * Destroy randomly one building.
   * First try to destroy building with maintenance cost,
   * if there aren't any then just destroy building.
   * @return building which was destroyed or null
   */
  public Building destroyOneBuilding() {
    if (buildings.size() > 0) {
      Building buildingToDestory = null;
      ArrayList<Building> list = new ArrayList<>();
      for (Building building : getBuildingList()) {
        if (building.getMaintenanceCost() > 0) {
          list.add(building);
        }
      }
      if (list.isEmpty()) {
        int index = DiceGenerator.getRandom(buildings.size() - 1);
        buildingToDestory = buildings.get(index);
      } else {
        int index = DiceGenerator.getRandom(list.size() - 1);
        buildingToDestory = list.get(index);
      }
      // Destroying building affects on culture
      setCulture(getCulture() - buildingToDestory.getCultBonus() * 50
          - buildingToDestory.getProdCost());
      removeBuilding(buildingToDestory);
      return buildingToDestory;
    }
    return null;
  }

  /**
   * Destroy randomly one building. This should be used on bombing.
   * There is chance than bomb misses the building.
   * @return true if building was hit
   */
  public boolean bombOneBuilding() {
    if (buildings.size() > 0) {
      int index = DiceGenerator.getRandom(getGroundSize() - 1);
      if (index < buildings.size()) {
        // Bomb hit on building
        Building building = buildings.get(index);
        // Destroying building affects on culture
        setCulture(getCulture() - building.getCultBonus() * 50
            - building.getProdCost());
        removeBuilding(building);
        return true;
      }
      // Bomb missed building
      return false;
    }
    return false;
  }

  /**
   * Drop nuke on planet. Increased rad, kills all workers, makes planet
   * uncolonized. Culture drops to 1/10.
   * @param strength Nuke strength from 0-100
   * @param bombName Bomb name for different type of explosions
   * @param starMap StarMap for writing history
   * @param previousNuking Previous nuking information.
   * @return Description about bombs destruction power.
   */
  public PlanetNuked nukem(final int strength, final String bombName,
      final StarMap starMap, final PlanetNuked previousNuking) {
    PlanetNuked nuked = previousNuking;
    if (nuked == null) {
      nuked = new PlanetNuked();
    }
    StringBuilder sb = new StringBuilder();
    int dead = getGroundSize() * strength / 100;
    if (dead < 2) {
      dead = 2;
    }
    int buildingsToDestroy = 0;
    int buildingsDestroyed = 0;
    if (bombName.equals("Orbital nuke")
        || bombName.equals("Orbital fusion bomb")) {
      buildingsToDestroy = getGroundSize() * strength / 100;
      if (buildingsToDestroy < 2) {
        buildingsToDestroy = 2;
      }
      buildingsDestroyed = 0;
      for (int i = 0; i < buildingsToDestroy; i++) {
        if (bombOneBuilding()) {
          buildingsDestroyed++;
        }
      }
      nuked.setPopulationKilled(nuked.getPopulationKilled() + dead);
      sb.append(bombName);
      sb.append(" killed ");
      sb.append(nuked.getPopulationKilled());
      sb.append(" population");
      nuked.setBuildingsDestroyed(nuked.getBuildingsDestroyed()
          + buildingsDestroyed);
      if (nuked.getBuildingsDestroyed() > 0) {
        sb.append(" and destroyed ");
        sb.append(nuked.getBuildingsDestroyed());
        sb.append(" buildings!");
      } else {
        sb.append("!");
      }
      if (radiationType.getIndex()
          < RadiationType.VERY_HIGH_RAD.getIndex()
          && DiceGenerator.getRandom(100) < strength) {
        setRadiationLevel(
            RadiationType.getByIndex(radiationType.getIndex() + 1));
        sb.append(" Radiation level rised on planet surface to "
            + radiationType.toString() + ".");
      }
    }
    if (bombName.equals("Orbital antimatter bomb")) {
      dead = getGroundSize();
      buildingsToDestroy = getGroundSize() * strength / 100;
      if (buildingsToDestroy < 2) {
        buildingsToDestroy = 2;
      }
      // Dropping nukes on planet really drops the culture on planet
      setCulture(getCulture() / 10 - strength);
      buildingsDestroyed = 0;
      for (int i = 0; i < buildingsToDestroy; i++) {
        if (bombOneBuilding()) {
          buildingsDestroyed++;
        }
      }
      nuked.setPopulationKilled(nuked.getPopulationKilled() + dead);
      sb.append(bombName);
      sb.append(" killed ");
      sb.append(nuked.getPopulationKilled());
      sb.append(" population");
      nuked.setBuildingsDestroyed(nuked.getBuildingsDestroyed()
          + buildingsDestroyed);
      if (nuked.getBuildingsDestroyed() > 0) {
        sb.append(" and destroyed ");
        sb.append(nuked.getBuildingsDestroyed());
        sb.append(" buildings!");
      } else {
        sb.append("!");
      }
      if (radiationType != RadiationType.VERY_HIGH_RAD
          && DiceGenerator.getBoolean()) {
        setRadiationLevel(
            RadiationType.getByIndex(radiationType.getIndex() + 1));
        sb.append(" Radiation level rised on planet surface to "
            + radiationType + ".");
      }
    }
    if (bombName.equals("Orbital neutron bomb")) {
      dead = getGroundSize();
      buildingsToDestroy = getGroundSize() / 2;
      if (buildingsToDestroy < 2) {
        buildingsToDestroy = 2;
      }
      // Dropping nukes on planet really drops the culture on planet
      setCulture(getCulture() / 10 - strength / 2);
      buildingsDestroyed = 0;
      for (int i = 0; i < buildingsToDestroy; i++) {
        if (bombOneBuilding()) {
          buildingsDestroyed++;
        }
      }
      nuked.setPopulationKilled(nuked.getPopulationKilled() + dead);
      sb.append(bombName);
      sb.append(" killed ");
      sb.append(nuked.getPopulationKilled());
      sb.append(" population");
      nuked.setBuildingsDestroyed(nuked.getBuildingsDestroyed()
          + buildingsDestroyed);
      if (nuked.getBuildingsDestroyed() > 0) {
        sb.append(" and destroyed ");
        sb.append(nuked.getBuildingsDestroyed());
        sb.append(" buildings!");
      } else {
        sb.append("!");
      }
      if (radiationType != RadiationType.VERY_HIGH_RAD
          && DiceGenerator.getBoolean()) {
        setRadiationLevel(
            RadiationType.getByIndex(radiationType.getIndex() + 1));
        sb.append(" Radiation level rised on planet surface to "
            + radiationType + ".");
      }
    }
    for (int i = 0; i < dead; i++) {
      killOneWorker("nuking", "nuclear blast", starMap);
    }
    nuked.setText(sb.toString());
    return nuked;
  }

  /**
   * Evaluate planet value based on PlayerInfo, distance from certain point.
   * @param info PlayerInfo aka realm
   * @param coord Coordinate where distance is calculate
   * @param distanceDivider Is distance going to be divided with something.
   * @return planet value
   */
  public int evaluatePlanetValue(final PlayerInfo info,
      final Coordinate coord, final int distanceDivider) {
    int value = getSizeAsInt() * 10;
    int dist = (int) coord.calculateDistance(getCoordinate());
    final var race = info.getRace();
    if (race.isEatingFood()) {
      value = value + getWaterLevel().getIndex() * 5;
    }
    if (getGravityType() == GravityType.HIGH_GRAVITY) {
      if (race.hasTrait(TraitIds.LOW_GRAVITY_BEING)) {
        value = value - 10;
      } else if (race.hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
        value = value - 15;
      } else if (race.hasTrait(TraitIds.HIGH_GRAVITY_BEING)) {
        value = value - 1;
        value = value + 1;
      } else {
        value = value - 5;
      }
    } else if (getGravityType() == GravityType.LOW_GRAVITY) {
      if (race.hasTrait(TraitIds.LOW_GRAVITY_BEING)) {
        value = value - 1;
        value = value + 1;
      } else if (race.hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
        value = value - 5;
      } else if (race.hasTrait(TraitIds.HIGH_GRAVITY_BEING)) {
        value = value + 10;
      } else {
        value = value + 5;
      }
    } else if (getGravityType() == GravityType.NORMAL_GRAVITY) {
      if (race.hasTrait(TraitIds.LOW_GRAVITY_BEING)) {
        value = value - 5;
      } else if (race.hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
        value = value - 10;
      } else if (race.hasTrait(TraitIds.HIGH_GRAVITY_BEING)) {
        value = value + 5;
      }
    }
    int suitability = info.getPlanetSuitabilityValue(this);
    value = value * suitability / 100;
    value = value + 10 - dist / distanceDivider;
    final var raceMaxRad = race.getMaxRad();
    int raceMaxRadInt = raceMaxRad.getIndex();
    if (info.getTechList().isTech("Radiation dampener")) {
      raceMaxRadInt++;
    }
    if (info.getTechList().isTech("Radiation well")) {
      raceMaxRadInt++;
    }
    raceMaxRadInt = Math.min(RadiationType.values().length - 1, raceMaxRadInt);
    final var planetRad = getRadiationLevel();
    // Races with radiosythesis rate planets with radiation more favorably
    if (race.hasTrait(TraitIds.RADIOSYNTHESIS)) {
      if (planetRad.getIndex() > raceMaxRadInt) {
        value -= (planetRad.getIndex() - raceMaxRadInt) * 2;
      } else {
        value += planetRad.getRadiosynthesisFood();
      }
    } else {
      if (planetRad.getIndex() > raceMaxRad.getIndex()) {
        value -= (planetRad.getIndex() - raceMaxRadInt) * 10;
      } else {
        value += raceMaxRadInt - planetRad.getIndex();
      }
    }
    return value;
  }
  /**
   * Is planet colonizable for realm
   * @param realm PlayerINfo
   * @return True or false
   */
  public boolean isColonizeablePlanet(final PlayerInfo realm) {
    boolean result = true;
    int maxRad = realm.getRace().getMaxRad().getIndex();
    if (realm.getTechList().hasTech("Radiation dampener")) {
      maxRad++;
    }
    if (realm.getTechList().hasTech("Radiation well")) {
      maxRad++;
    }
    if (maxRad
        < getTotalRadiationLevel().getIndex()) {
      result = false;
    }
    int suitability = realm.getPlanetSuitabilityValue(this);
    if (suitability == 0) {
      result = false;
    }
    if (realm.getRace().isEatingFood()
        && getWaterLevel() == WaterLevelType.BARREN) {
      result = false;
    }
    return result;
  }
  /**
   * Set Planet's culture value
   * @param culture new culture value
   */
  public void setCulture(final int culture) {
    if (culture < 0) {
      this.culture = 0;
    } else {
      this.culture = culture;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(" X: ");
    sb.append(coordinate.getX());
    sb.append(" Y: ");
    sb.append(coordinate.getY());
    sb.append(" - ");
    if (planetOwnerInfo != null) {
      sb.append(planetOwnerInfo.getEmpireName());
    } else {
      sb.append("Uncolonized");
    }
    return sb.toString();
  }

  /**
   * Is home world or not.
   * @return True if homeworld.
   */
  public boolean isHomeWorld() {
    if (!homeWorldId.isEmpty()) {
      return true;
    }
    return false;
  }
  /**
   * Get home world id. Id is matching for space race  who's
   * home world planet is. Empty means that planet is not an home world.
   * @return Space race id
   */
  public String getHomeWorldId() {
    return homeWorldId;
  }

  /**
   * Set space race home world id.
   * @param raceId Space race id. Empty for non home world.
   */
  public void setHomeWorldId(final String raceId) {
    this.homeWorldId = raceId;
  }

  /**
   * Get start realm index. Index is matching for realm's index.
   * -1 means that planet is not a start world.
   * @return Realm index or -1
   */
  public int getstartRealmIndex() {
    return startRealmIndex;
  }

  /**
   * Set start realm index.
   * @param realmIndex realm index. -1 for non start world.
   */
  public void setStartRealmIndex(final int realmIndex) {
    this.startRealmIndex = realmIndex;
  }

  /**
   * Get extra food
   * @return extra food
   */
  public int getExtraFood() {
    return extraFood;
  }

  /**
   * Set extra food
   * @param extraFood extra food
   */
  public void setExtraFood(final int extraFood) {
    this.extraFood = extraFood;
  }

  /**
   * Get planet type
   * @return the type
   */
  public PlanetTypes getPlanetType() {
    return planetType;
  }

  /**
   * Get planet type index. Separate list for gas giant
   * and regular planets.
   * @return Index
   */
  public int getPlanetTypeIndex() {
    return planetType.getPlanetTypeIndex();
  }

  /**
   * Set planet type
   * @param newType the type to set
   */
  public void setPlanetType(final PlanetTypes newType) {
    this.planetType = newType;
    if (this.planetType == PlanetTypes.ARTIFICIALWORLD1) {
      amountMetalInGround = 0;
    }
  }

  /**
   * Get big planet image
   * @return BufferedImage
   */
  public BufferedImage getBigImage() {
    return planetType.getImage();
  }

  /**
   * Get image instructions
   * @return Image instructions as a string
   */
  public String getImageInstructions() {
    return planetType.getImageInstructions();
  }

  /**
   * Try to add provided status. Returns true if addition
   * was successful, false otherwise.
   * @param status AppliedStatus to add
   * @return True if status was added
   */
  public boolean addStatus(final AppliedStatus status) {
    var statusesArray = statuses.toArray(new PlanetaryStatus[statuses.size()]);
    var conflicting = PlanetaryStatus.isConflictingWith(status.getStatus(),
        statusesArray);

    if (conflicting) {
      return false;
    }

    return statuses.add(status);
  }

  /**
   * Return true if Planet has status with given ID
   * @param statusId ID of status
   * @return True if planet has status with given ID
   */
  public boolean hasStatus(final String statusId) {
    return statuses.stream()
        .anyMatch(status -> status.getStatusId().equals(statusId));
  }

  /**
   * Return unmodifiable list of planet's statuses
   * @return Unmodifiable List of statuses
   */
  public List<AppliedStatus> getStatuses() {
    return Collections.unmodifiableList(statuses);
  }

  /**
   * Remove status with specified ID from planet
   * @param statusId ID of status to remove
   * @return True if status was removed
   */
  public boolean removeStatus(final String statusId) {
    return statuses.removeIf(status -> status.getStatusId().equals(statusId));
  }

  /**
   * Set planetary event
   * @param planetaryEvent Event to set
   */
  public void setPlanetaryEvent(final PlanetaryEvent planetaryEvent) {
    event = planetaryEvent;
  }

  /**
   * Get planetary event
   * @return Planetary event
   */
  public PlanetaryEvent getPlanetaryEvent() {
    return event;
  }

  /**
   * Has planetary event been activated by colonization
   * @return True or false
   */
  public boolean isEventActivated() {
    return eventFound;
  }

  /**
   * Set planetary event activation
   * @param activation True for activate event.
   */
  public void setEventActivation(final boolean activation) {
    eventFound = activation;
  }

  /**
   * Event activation.
   * @param isTutorialEnabled Boolean if tutorial is enabled.
   * @param commander null or commander commanding away team
   * @param info PlayerInfo who controls the fleet or null
   */
  public void eventActivation(final boolean isTutorialEnabled,
      final Leader commander, final PlayerInfo info) {
    PlayerInfo realm = planetOwnerInfo;
    int exp = 0;
    if (info != null) {
      realm = info;
    }
    if (realm == null || eventFound || event == PlanetaryEvent.NONE) {
      return;
    }

    StringBuilder msgText = new StringBuilder();
    if (commander == null) {
      msgText.append("When colonizating ");
      msgText.append(getName());
      msgText.append(" colonist found ");
    } else {
      msgText.append("Away team on ");
      msgText.append(getName());
      msgText.append(" lead by ");
      msgText.append(commander.getCallName());
      msgText.append(" found ");
    }

    eventFound = true;

    if (event == PlanetaryEvent.ANCIENT_ARTIFACT) {
      exp = 15;
      event = PlanetaryEvent.NONE;
      msgText.append("that ");
      msgText.append(getName() + " has strange ancient artifact.");
      if (commander == null) {
        msgText.append(" Colonists send it immediately for research.");
      } else {
        msgText.append(" Away team takes it immediately for research.");
      }
      Message msg = new Message(MessageType.PLANETARY, msgText.toString(),
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
      msg.setCoordinate(getCoordinate());
      ImageInstruction imageInst = new ImageInstruction();
      imageInst.addBackground(ImageInstruction.BACKGROUND_BLACK);
      imageInst.addImage(ImageInstruction.ARTIFACT_ON_PLANET);
      msg.setImageInstructions(imageInst.build());
      realm.getMsgList().addUpcomingMessage(msg);
      realm.getArtifactLists().addDiscoveredArtifact(
          ArtifactFactory.getRandomArtifact());
      if (Game.getTutorial() != null && realm.isHuman()
          && isTutorialEnabled) {
        String tutorialText = Game.getTutorial().showTutorialText(15);
        if (tutorialText != null) {
          msg = new Message(MessageType.INFORMATION, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          realm.getMsgList().addNewMessage(msg);
        }
      }
    } else {
      exp = 8;
      Building building = event.getBuilding();
      addBuilding(building);
      ImageInstruction imageInst = null;
      if (event == PlanetaryEvent.ANCIENT_LAB) {
        imageInst = new ImageInstruction();
        imageInst.addBackground(ImageInstruction.BACKGROUND_BLACK);
        imageInst.addImage(ImageInstruction.ANCIENT_LABORATORY);
      }
      if (event == PlanetaryEvent.ANCIENT_FACTORY) {
        imageInst = new ImageInstruction();
        imageInst.addBackground(ImageInstruction.BACKGROUND_BLACK);
        imageInst.addImage(ImageInstruction.ANCIENT_FACTORY);
      }
      if (event == PlanetaryEvent.ANCIENT_TEMPLE) {
        imageInst = new ImageInstruction();
        imageInst.addBackground(ImageInstruction.BACKGROUND_BLACK);
        imageInst.addImage(ImageInstruction.ANCIENT_TEMPLE);
      }
      if (event == PlanetaryEvent.ANCIENT_PALACE) {
        imageInst = new ImageInstruction();
        imageInst.addBackground(ImageInstruction.BACKGROUND_BLACK);
        imageInst.addImage(ImageInstruction.ANCIENT_PALACE);
      }
      if (event == PlanetaryEvent.BLACK_MONOLITH) {
        imageInst = new ImageInstruction();
        imageInst.addBackground(ImageInstruction.BACKGROUND_BLACK);
        imageInst.addImage(ImageInstruction.BLACK_MONOLITH);
      }

      event = PlanetaryEvent.NONE;

      msgText.append(building.getName());
      if (commander == null) {
        msgText.append(". Colonists has taken it in use now.");
      } else {
        msgText.append(". Building must be left on planet waiting"
            + " for colonization.");
      }
      Message msg = new Message(MessageType.PLANETARY, msgText.toString(),
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
      msg.setCoordinate(getCoordinate());
      if (imageInst != null) {
        msg.setImageInstructions(imageInst.build());
      }
      realm.getMsgList().addUpcomingMessage(msg);
    }

    if (commander != null) {
      if (commander.hasPerk(Perk.TREKKER)) {
        exp = exp * 2;
      }
      commander.setExperience(commander.getExperience() + exp);
    }
  }

  /**
   * Calculate happiness of the planet. Also updates explanation how
   * happiness is calculated.
   * @return Happiness, positive number is happy and negative is unhappy.
   */
  public int calculateHappiness() {
    if (planetOwnerInfo == null) {
      happinessExplanation = "<html>Planet is not colonized!</html>";
      return 0;
    }
    if (getPlanetPlayerInfo().getGovernment().isImmuneToHappiness()) {
      happinessExplanation = "<html>Government is single minded,"
          + " no happiness or sadness.</html>";
      return 0;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    GovernmentType government = planetOwnerInfo.getGovernment();
    int base = government.getGenericHappiness();
    if (government.getGenericHappiness() != 0) {
      sb.append("<li>");
      sb.append("Government ");
      if (government.getGenericHappiness() > 0) {
        sb.append("+");
      }
      sb.append(government.getGenericHappiness());
      sb.append("<br>");
    }
    if (government.hasWarHappiness()
        && planetOwnerInfo.getDiplomacy().getNumberOfWar() > 0) {
      base = base + 1;
      sb.append("<li>");
      sb.append("War happiness +1");
      sb.append("<br>");
    }
    base = base - getTotalPopulation() / 5;
    if (getTotalPopulation() / 5 > 0) {
      sb.append("<li>");
      sb.append("Population -");
      sb.append(getTotalPopulation() / 5);
      sb.append("<br>");
    }
    int artists = getWorkers(Planet.CULTURE_ARTIST);
    base = base + artists;
    if (artists > 0) {
      sb.append("<li>");
      sb.append("Artists +");
      sb.append(artists);
      sb.append("<br>");
    }

    int bonusBuildings = 0;
    for (Building building : buildings) {
      if (building.getHappiness() != 0) {
        base = base + building.getHappiness();
        bonusBuildings = bonusBuildings + building.getHappiness();
      }
    }
    if (bonusBuildings != 0) {
      sb.append("<li>");
      sb.append("Buildings ");
      if (bonusBuildings > 0) {
        sb.append("+");
      }
      sb.append(bonusBuildings);
      sb.append("<br>");
    }
    int statusesBonus = statuses.stream()
        .map(status -> status.getStatus().getHappinessBonus())
        .reduce(0, (acc, b) -> acc + b);
    if (statusesBonus != 0) {
      sb.append("<li>");
      sb.append(event.getName());
      if (statusesBonus > 0) {
        sb.append("+");
      }
      base = base + statusesBonus;
      sb.append(statusesBonus);
      sb.append("<br>");
    }
    int totalWarFatigue = planetOwnerInfo.getTotalWarFatigue();
    base = base + totalWarFatigue;
    if (totalWarFatigue < 0) {
      sb.append("<li>");
      sb.append("War fatigue ");
      sb.append(totalWarFatigue);
      sb.append("<br>");
    }
    if (governor != null && governor.hasPerk(Perk.GOOD_LEADER)) {
      base = base + 1;
      sb.append("<li>");
      sb.append("Governor Good leader +1");
      sb.append("<br>");
    }
    if (governor != null && governor.hasPerk(Perk.CRUEL)) {
      base = base - 1;
      sb.append("<li>");
      sb.append("Governor Cruel -1");
      sb.append("<br>");
    }
    if (governor != null && government.getGovernorHappiness() != 0) {
      base = base + government.getGovernorHappiness();
      sb.append("<li>");
      sb.append("Happiness from governor ");
      if (government.getGovernorHappiness() > 0) {
        sb.append("+");
      }
      sb.append(government.getGovernorHappiness());
      sb.append("<br>");
    }
    if (getPlanetPlayerInfo().getRuler() != null
        && getPlanetPlayerInfo().getRuler().hasPerk(Perk.GOOD_LEADER)) {
      base = base + 1;
      sb.append("<li>");
      sb.append("Ruler Good leader +1");
      sb.append("<br>");
    }
    if (getPlanetPlayerInfo().getRuler() != null
        && getPlanetPlayerInfo().getRuler().hasPerk(Perk.CRUEL)) {
      base = base - 1;
      sb.append("<li>");
      sb.append("Ruler Cruel -1");
      sb.append("<br>");
    }
    sb.append("</html>");
    happinessExplanation = sb.toString();
    return base;
  }

  /**
   * Get happiness effect from planet
   * @return Happiness effect
   */
  public HappinessEffect getHappinessEffect() {
    return happinessEffect;
  }

  /**
   * Set Happiness effect for planet.
   * @param effect HappinessEffect to set for planet
   */
  public void setHappinessEffect(final HappinessEffect effect) {
    happinessEffect = effect;
  }

  /**
   * Get happiness explanation how it is calculated.
   * @return Happiness explanation
   */
  public String getHappinessExplanation() {
    if (happinessExplanation == null) {
      calculateHappiness();
    }
    return happinessExplanation;
  }

  /**
   * Get Food production explanation how it is calculated.
   * @return Food production explanation
   */
  public String getFarmProdExplanation() {
    if (farmProdExplain == null) {
      getTotalProduction(PRODUCTION_FOOD);
    }
    return farmProdExplain;
  }

  /**
   * Get Metal production explanation how it is calculated.
   * @return Metal production explanation
   */
  public String getMetalProdExplanation() {
    if (metaProdExplain == null) {
      getTotalProduction(PRODUCTION_METAL);
    }
    return metaProdExplain;
  }

  /**
   * Get production production explanation how it is calculated.
   * @return Production explanation
   */
  public String getProductionExplanation() {
    if (prodProdExplain == null) {
      getTotalProduction(PRODUCTION_PRODUCTION);
    }
    return prodProdExplain;
  }

  /**
   * Get research production explanation how it is calculated.
   * @return Research production explanation
   */
  public String getResearchProdExplanation() {
    if (reseProdExplain == null) {
      getTotalProduction(PRODUCTION_RESEARCH);
    }
    return reseProdExplain;
  }

  /**
   * Get culture production explanation how it is calculated.
   * @return Culture production explanation
   */
  public String getCultureProdExplanation() {
    if (cultProdExplain == null) {
      getTotalProduction(PRODUCTION_CULTURE);
    }
    return cultProdExplain;
  }

  /**
   * Get credits production explanation how it is calculated.
   * @return Credits production explanation
   */
  public String getCreditsProdExplanation() {
    if (credProdExplain == null) {
      getTotalProduction(PRODUCTION_CREDITS);
    }
    return credProdExplain;
  }

  /**
   * Does planet have Orbital shield and bombing is not possible.
   * Orbital shield does not protect against invasion
   * @return True if one of the building is orbital shield. Otherwise false.
   */
  public boolean isShieldForBombing() {
    for (Building building : buildings) {
      if (building.getName().equals("Orbital shield")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Fleet capacity bonus from planet
   * @return Fleet capacity bonus.
   */
  public int getFleetCapacityBonus() {
    int result = 0;
    for (Building building : buildings) {
      result = result + building.getFleetCapacityBonus();
    }
    return result;
  }

  /**
   * Get current governor of the planet
   * @return the governor
   */
  public Leader getGovernor() {
    return governor;
  }

  /**
   * Set the current governor of the planet
   * @param governor the governor to set
   */
  public void setGovernor(final Leader governor) {
    this.governor = governor;
    if (this.governor != null) {
      this.governor.assignJob(Job.GOVERNOR, this.getPlanetPlayerInfo());
    }
  }

  /**
   * Method to kill planet governor with reason explanation
   * @param attackType conquest, bombing, nuking
   * @param reason Explain why governor died
   * @param starMap StarMap add news and history event. If null then
   *        history isn't written.
   */
  public void killGovernor(final String attackType, final String reason,
      final StarMap starMap) {
    if (getTotalPopulation() == 0
        && getGovernor() != null) {
      if (getGovernor().hasPerk(Perk.WEALTHY)) {
        Message msg = new Message(MessageType.LEADER,
            getGovernor().getCallName()
                + " has paid massive amount of credits to save "
                + getGovernor().getGender().getHisHer() + " life."
                + " Private shuttle was used to save "
                + getGovernor().getName() + ".",
            Icons.getIconByName(Icons.ICON_DEATH));
        msg.setMatchByString("Index:" + getPlanetPlayerInfo().getLeaderIndex(
            getGovernor()));
        getPlanetPlayerInfo().getMsgList().addNewMessage(msg);
        getGovernor().useWealth();
        getGovernor().setJob(Job.UNASSIGNED);
        setGovernor(null);
      } else {
        getGovernor().setJob(Job.DEAD);
        Message msg = new Message(MessageType.LEADER,
            getGovernor().getCallName()
                + " has died at " + attackType + " of " + getName() + ". ",
            Icons.getIconByName(Icons.ICON_DEATH));
        msg.setMatchByString("Index:" + getPlanetPlayerInfo().getLeaderIndex(
            getGovernor()));
        getPlanetPlayerInfo().getMsgList().addNewMessage(msg);
        NewsData news = NewsFactory.makeLeaderDies(getGovernor(),
            getPlanetPlayerInfo(), reason, starMap.getStarYear());
        if (starMap != null) {
          if (starMap.hasHumanMet(getPlanetPlayerInfo())) {
            starMap.getNewsCorpData().addNews(news);
          }
          starMap.getHistory().addEvent(
              NewsFactory.makeLeaderEvent(getGovernor(),
                  getPlanetPlayerInfo(), starMap, news));
        }
        setGovernor(null);
      }
    }
  }

  /**
   * Calculate how full planet is.
   * This estimate can be then used how valueable planet is
   * in certain cituations.
   * @return Planet value in per cents.
   */
  public int getFullLevel() {
    int result = 0;
    result = getTotalPopulation() + getBuildingList().length;
    result = result * 100;
    result = result / (getGroundSize() * 2);
    return result;
  }

  /**
   * Get evaluation value for how good current planet is for building
   * scientific achievements.
   * @return Evaluation value from -3 to positive number.
   */
  public int getEvaluationForTechWorld() {
    int result = 0;
    result = getGroundSize() - 10;
    if (getPlanetType() == PlanetTypes.ARTIFICIALWORLD1) {
      result = result + 5;
    }
    for (Building building : buildings) {
      if (building.getScientificAchievement()) {
        result = result + 5;
      }
    }
    result = result + getTotalProduction(PRODUCTION_PRODUCTION);
    result = result + getTotalProduction(PRODUCTION_METAL);
    result = result + getMetal() / 25;
    return result;
  }

  /**
   * Get Orbital of the planet or null.
   * @return Ship or null
   */
  public Ship getOrbital() {
    return orbital;
  }

  /**
   * Set Orbital of the planet or null.
   * @param orbital Ship
   */
  public void setOrbital(final Ship orbital) {
    this.orbital = orbital;
  }

  /**
   * Get Governor guide information for planet.
   * @return Governor guide as int
   */
  public int getGovernorGuide() {
    return governorGuide;
  }

  /**
   * Set Governor guide information for planet.
   * @param governorGuide Guide as a int
   */
  public void setGovernorGuide(final int governorGuide) {
    this.governorGuide = governorGuide;
  }

  /**
   * Get Effective governor guide if governor is available.
   * @return Effective governor guide or general planet.
   */
  public int getEffectiveGovernorGuide() {
    if (getGovernor() != null) {
      return governorGuide;
    }
    return GENERALIST_PLANET;
  }

  /**
   * Get suggestion for governor guide.
   * This will be used by AI.
   * @return Planetary guide value.
   */
  public int getGuideSuggestion() {
    // Generalist planet is always good.
    int bestGuide = GENERALIST_PLANET;
    int bestScore = 0;

    var planetGroundSize = getGroundSize();
    // Get scoring for population planet
    int score = 0;
    int maxPop = getPopulationLimit();
    if (maxPop > 11) {
      score = score + 1;
    }
    if (getTotalPopulation() < maxPop) {
      score = score + 1;
    }
    if (planetOwnerInfo.getPlanetSuitabilityValue(this) > 100) {
      score = score + 1;
    }
    if (planetOwnerInfo.getPlanetSuitabilityValue(this) < 75) {
      score = score - 1;
    }

    int statusFoodBonus = statuses.stream()
        .map(status -> status.getStatus().getFoodBonus())
        .reduce(0, Integer::sum);
    score += statusFoodBonus;

    if (score > bestScore) {
      bestGuide = POPULATION_PLANET;
      score = bestScore;
    }

    // Get scoring for military planet
    score = 0;
    if (planetGroundSize > 14) {
      score = score + 1;
    }
    if (planetGroundSize > 11) {
      score = score + 1;
    }

    int statusMineBonus = statuses.stream()
        .map(status -> status.getStatus().getMineBonus())
        .reduce(0, Integer::sum);
    int statusProdBonus = statuses.stream()
        .map(status -> status.getStatus().getProdBonus())
        .reduce(0, Integer::sum);
    score += statusMineBonus + statusProdBonus;

    if (score > bestScore) {
      bestGuide = MILITARY_PLANET;
      score = bestScore;
    }

    // Get scoring for culture planet
    score = 0;
    if (planetGroundSize > 12) {
      score = score + 1;
    }
    if (planetGroundSize > 9) {
      score = score + 1;
    }
    if (getCulture() > 50) {
      score = score + 1;
    }
    if (getTotalCultureProduction() > 1) {
      score = score + 1;
    }
    if (score > bestScore) {
      bestGuide = CULTURE_PLANET;
      score = bestScore;
    }

    // Get scoring for research planet
    score = 0;
    if (planetGroundSize > 10) {
      score = score + 1;
    }
    if (planetGroundSize > 8) {
      score = score + 1;
    }
    if (getTotalResearchProduction() > 1) {
      score = score + 1;
    }
    if (getTotalResearchProduction() > 3) {
      score = score + 1;
    }
    if (score > bestScore) {
      bestGuide = RESEARCH_PLANET;
      score = bestScore;
    }

    // Get scoring for credit planet
    score = 0;
    if (planetGroundSize > 10) {
      score = score + 1;
    }
    if (planetGroundSize > 8) {
      score = score + 1;
    }

    int statusCredBonus = statuses.stream()
        .map(status -> status.getStatus().getCredBonus())
        .reduce(0, Integer::sum);
    score += statusCredBonus;

    if (getTotalCreditProduction() > 1) {
      score = score + 1;
    }
    if (getTotalCreditProduction() > 3) {
      score = score + 1;
    }
    if (score > bestScore) {
      bestGuide = CREDIT_PLANET;
      score = bestScore;
    }

    return bestGuide;
  }
}
