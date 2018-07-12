package org.openRealmOfStars.AI.PlanetHandling;

import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
 * AI handling for planets
 *
 */

public final class PlanetHandling {

  /**
   * Just hiding the planetHandling constructor.
   */
  private PlanetHandling() {
    // Nothing to do here
  }

  /**
   * Maximum score for slots
   */
  private static final int MAX_SLOT_SCORE = 1000;

  /**
   * Score for zero available slot
   */
  private static final int ZERO_SLOT_SCORE = 180;

  /**
   * Score for one available slot
   */
  private static final int ONE_SLOT_SCORE = 150;

  /**
   * Score for two available slot
   */
  private static final int TWO_SLOT_SCORE = 120;

  /**
   * Score for three available slot
   */
  private static final int THREE_SLOT_SCORE = 80;

  /**
   * Score for four available slot
   */
  private static final int FOUR_SLOT_SCORE = 40;

  /**
   * Score for five available slot
   */
  private static final int FIVE_SLOT_SCORE = 20;

  /**
   * Score for six available slot
   */
  private static final int SIX_SLOT_SCORE = 10;

  /**
   * High value score for construction
   */
  private static final int HIGH_VALUE_SCORE = 400;

  /**
   * Turret defense value score
   */
  private static final int TURRET_DEFENSE_VALUE_SCORE = 15;

  /**
   * Spork military value score
   */
  private static final int SPORK_MILITARY_VALUE_SCORE = 15;

  /**
   * Greyan research value score
   */
  private static final int GREYAN_RESEARCH_VALUE_SCORE = 15;

  /**
   * Metal amount divider
   */
  private static final int METAL_AMOUNT_DIVIDER = 120;

  /**
   * AI player handling for a single planet, what to build
   * and how to set population work
   * @param map Star Map
   * @param planet Planet to handle
   * @param index Player Index;
   */
  public static void handlePlanet(final StarMap map, final Planet planet,
      final int index) {
    int credit = map
        .getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_CREDITS, index);
    PlayerInfo info = map.getPlayerByIndex(index);
    if (info != null) {
      Attitude attitude = info.getAiAttitude();
      handlePlanetPopulation(planet, info);
      if (credit < 0
          && planet.getTax() < planet.getTotalProduction(
              Planet.PRODUCTION_PRODUCTION) + 2) {
        planet.setTax(planet.getTax() + 1, false);
      } else if (credit > 0 && planet.getTax() > 0) {
        planet.setTax(planet.getTax() - 1, false);
      }
      ArrayList<Message> messages = info.getMsgList().getFullList();
      boolean changeConstruction = false;
      for (Message msg : messages) {
        if (msg.getType() == MessageType.CONSTRUCTION
            && msg.getMatchByString().equals(planet.getName())) {
          changeConstruction = true;
        }
      }
      if (changeConstruction || planet.getUnderConstruction() == null) {
        Building[] buildings = planet.getBuildingList();
        Construction[] constructions = planet.getProductionList();
        boolean constructionSelected = false;
        int gotFactory = gotBuildings(
            new String[] {"Basic factory", "Advanced factory",
                "Manufacturing center", "Nanobot manufacturing center",
                "Ancient factory"}, buildings);
        int gotLabs = gotBuildings(
            new String[] {"Basic lab", "Advanced laboratory",
                "Research center", "Neural research center",
                "Ancient lab"}, buildings);
        int gotFarms = gotBuildings(
            new String[] {"Basic farm", "Advanced farm",
                "Farming center", "Hydropodic farming center"}, buildings);
        int gotMines = gotBuildings(
            new String[] {"Basic mine", "Advanced mine",
                "Mining center", "Nanobot mining center"}, buildings);
        int gotSpacePort = gotBuildings(new String[] {"Space port"},
            buildings);
        if (gotFactory == -1) {
          // No factories at all
          int i = getConstruction("Advanced factory", constructions);
          if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
            constructionSelected = true;
          } else {
            i = getConstruction("Basic factory", constructions);
            if (i != -1) {
              planet.setUnderConstruction(constructions[i]);
              constructionSelected = true;
            }
          }
        }
        if (gotLabs == -1 && !constructionSelected) {
          // No labs at all
          int i = getConstruction("Advanced laboratory", constructions);
          if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
            constructionSelected = true;
          } else {
            i = getConstruction("Basic lab", constructions);
            if (i != -1) {
              planet.setUnderConstruction(constructions[i]);
              constructionSelected = true;
            }
          }
        }
        if (gotFarms == -1 && !constructionSelected
            && info.getRace() != SpaceRace.MECHIONS) {
          // No farms at all
          int i = getConstruction("Advanced farm", constructions);
          if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
            constructionSelected = true;
          } else {
            i = getConstruction("Basic farm", constructions);
            if (i != -1) {
              planet.setUnderConstruction(constructions[i]);
              constructionSelected = true;
            }
          }
        }
        if (!constructionSelected && info.getRace() == SpaceRace.MECHIONS
            && planet.getTotalPopulation() < 6) {
          int i = getConstruction(ConstructionFactory.MECHION_CITIZEN,
              constructions);
          if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
            constructionSelected = true;
          }
        }
        if (gotMines == -1 && !constructionSelected) {
          // No mines at all
          int i = getConstruction("Advanced mine", constructions);
          if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
            constructionSelected = true;
          } else {
            i = getConstruction("Basic mine", constructions);
            if (i != -1) {
              planet.setUnderConstruction(constructions[i]);
              constructionSelected = true;
            }
          }
        }
        if (gotSpacePort == -1 && !constructionSelected) {
          // No space port
          int i = getConstruction("Space port", constructions);
          if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
            constructionSelected = true;
          }
        }
        if (map.getTurn() > 20 && !constructionSelected) {
          Mission mission = info.getMissions()
              .getMissionForPlanet(planet.getName(), MissionType.DEFEND);
          if (mission == null) {
            mission = new Mission(MissionType.DEFEND, MissionPhase.PLANNING,
                planet.getCoordinate());
            mission.setFleetName("Defender");
            mission.setPlanetBuilding(planet.getName());
            info.getMissions().add(mission);
          }
        }
        if (!constructionSelected) {
          constructionSelected = handleConstructions(constructions, planet,
              info, map, attitude);
        }
        if (!constructionSelected) {
          // Nothing to select to let's select culture or credit
          int i = getConstruction(ConstructionFactory.EXTRA_CREDIT,
              constructions);
          int j = getConstruction(ConstructionFactory.EXTRA_CULTURE,
              constructions);
          if (i != -1 && j != -1) {
            if (DiceGenerator.getRandom(1) == 0) {
              planet.setUnderConstruction(constructions[i]);
            } else {
              planet.setUnderConstruction(constructions[j]);
            }
          } else if (i != -1) {
            planet.setUnderConstruction(constructions[i]);
          } else if (j != -1) {
            planet.setUnderConstruction(constructions[j]);
          }
        }
      } else {
        // Let's check rushing
        int rushCost = planet.getRushingCost(planet.getUnderConstruction());
        int rushPopulation = rushCost / Planet.POPULATION_RUSH_COST + 1;
        int buildingTime = planet.getProductionTime(
            planet.getUnderConstruction());
        int rushChange = 0;
        boolean creditRush = true;
        if (info.getRace().hasCreditRush()
            && rushCost < info.getTotalCredits() && buildingTime > 1
            && rushCost > 0) {
          Mission mission = info.getMissions().getMissionForPlanet(
              planet.getName(), MissionPhase.BUILDING);
          if (mission != null) {
            // Planet on mission for building something
            rushChange = rushChange + 10;
          }
          int rushPerCredit =  info.getTotalCredits() / rushCost;
          if (rushPerCredit == 2) {
            rushChange = rushChange + 5;
          } else if (rushPerCredit == 3) {
            rushChange = rushChange + 7;
          } else if (rushPerCredit == 4) {
            rushChange = rushChange + 10;
          }  else if (rushPerCredit == 5) {
            rushChange = rushChange + 12;
          }  else if (rushPerCredit == 6) {
            rushChange = rushChange + 14;
          }  else if (rushPerCredit == 7) {
            rushChange = rushChange + 15;
          }  else if (rushPerCredit == 8) {
            rushChange = rushChange + 17;
          }  else if (rushPerCredit == 9) {
            rushChange = rushChange + 18;
          }  else if (rushPerCredit >= 10) {
            rushChange = rushChange + 20;
          }
          if (planet.getUnderConstruction() instanceof Ship) {
            rushChange = rushChange + 5;
          }
        }
        if (info.getRace().hasPopulationRush()
            && rushPopulation < planet.getTotalPopulation() && buildingTime > 1
            && rushChange == 0 && rushCost > 0) {
          creditRush = false;
          Mission mission = info.getMissions().getMissionForPlanet(
              planet.getName(), MissionPhase.BUILDING);
          if (mission != null) {
            // Planet on mission for building something
            rushChange = rushChange + 5;
          }
          if (planet.isFullOfPopulation()) {
            rushChange = rushChange + 5;
          }
          if (planet.getUnderConstruction() instanceof Ship) {
            rushChange = rushChange + 3;
          }
        }
        if (rushChange > 0) {
          int random = DiceGenerator.getRandom(99) + 1;
          if (random < rushChange) {
            planet.doRush(creditRush, info);
          }
        }
      }
    }
  }

  /**
   * Score Building on certain planet and playerinfo
   * @param building Building to score
   * @param planet Planet where building is placed or about to place
   * @param info PlayerInfo who's building is about to score
   * @param attitude AI's attitude
   * @return Score of building, bigger is better
   */
  public static int scoreBuilding(final Building building, final Planet planet,
      final PlayerInfo info, final Attitude attitude) {
    // Military score
    int score = building.getBattleBonus()
        + building.getDefenseDamage() * TURRET_DEFENSE_VALUE_SCORE;
    score = score + building.getScanRange() * 10
        + building.getScanCloakingDetection() / 4;
    if (info.getRace() == SpaceRace.SPORKS
        && building.getType() == BuildingType.MILITARY) {
      score = score + SPORK_MILITARY_VALUE_SCORE;
    }

    // Production score
    score = score + building.getFactBonus() * 60;
    score = score + building.getMineBonus() * planet.getAmountMetalInGround()
        / METAL_AMOUNT_DIVIDER;
    if (info.getRace() == SpaceRace.MOTHOIDS) {
      score = score + building.getMineBonus() * 30;
    }
    if (info.getRace() == SpaceRace.CENTAURS
        || info.getRace() == SpaceRace.TEUTHIDAES) {
      score = score + building.getFarmBonus() * 50;
    } else if (info.getRace() != SpaceRace.MECHIONS) {
      score = score + building.getFarmBonus() * 40;
    } else {
      score = score - building.getFarmBonus() * 40;
    }
    if (info.getRace() == SpaceRace.MECHIONS) {
      score = score + building.getReseBonus() * 80;
      score = score + building.getCultBonus() * 60;
    } else {
      score = score + building.getReseBonus() * 60;
      score = score + building.getCultBonus() * 40;
    }
    if (planet.getMaintenanceCost() >= planet
        .getTotalProduction(Planet.PRODUCTION_CREDITS)) {
      // Planet has much expenses so build credit production is important
      score = score + building.getCredBonus() * 80;
    } else {
      score = score + building.getCredBonus() * 50;
    }
    score = score + building.getRecycleBonus();
    if (attitude == Attitude.DIPLOMATIC) {
      score = score + building.getCultBonus() * 15;
    }
    if (attitude == Attitude.SCIENTIFIC) {
      score = score + building.getReseBonus() * 15;
    }
    if (attitude == Attitude.MERCHANTICAL) {
      score = score + building.getCredBonus() * 15;
    }
    if (attitude == Attitude.MILITARISTIC
        && building.getType() == BuildingType.MILITARY) {
      score = score + 15;
    }
    if (attitude == Attitude.BACKSTABBING
        && building.getScanCloakingDetection() > 0) {
      score = score + 15;
    }
    if (attitude == Attitude.AGGRESSIVE
        && building.getType() == BuildingType.MILITARY) {
      score = score + 10;
    }
    if (attitude == Attitude.EXPANSIONIST
        && building.getType() == BuildingType.FARM) {
      score = score + 10;
    }

    score = score - (int) Math.round(building.getMaintenanceCost() * 10);
    // High cost drops the value
    score = score - building.getMetalCost() / 10;
    score = score - building.getProdCost() / 10;

    if (info.getRace() == SpaceRace.GREYANS
        && building.getType() == BuildingType.RESEARCH) {
      score = score + GREYAN_RESEARCH_VALUE_SCORE;
    }

    if (info.getRace() == SpaceRace.MECHIONS
        && building.getType() == BuildingType.FARM) {
      // Mechions do not build farms
      score = -1;
    }

    if (building.getName().equals("Basic factory")
        && info.getTechList().hasTech(TechType.Improvements,
            "Advanced factory")) {
      score = -1;
    }
    if (building.getName().equals("Basic mine")
        && info.getTechList().hasTech(TechType.Improvements,
            "Advanced mine")) {
      score = -1;
    }
    if (building.getName().equals("Basic farm")
        && info.getTechList().hasTech(TechType.Improvements,
            "Advanced farm")) {
      score = -1;
    }
    if (building.getName().equals("Basic lab")
        && info.getTechList().hasTech(TechType.Improvements,
            "Advanced laboratory")) {
      score = -1;
    }
    if (planet.exceedRadiation()) {
      if (building.getName().equals("Radiation dampener")
          || building.getName().equals("Radiation well")) {
        // Radiation level is high so these are in high priority
        score = MAX_SLOT_SCORE;
      }
    } else {
      if (building.getName().equals("Radiation dampener")
          || building.getName().equals("Radiation well")) {
        // Radiation level is not high so never building these
        score = -1;
      }

    }
    return score;

  }

  /**
   * Get the planet's worst building
   * @param planet Planet which to check
   * @param info PlayerInfo who owns the planet
   * @param attitude AI's attitude
   * @param newBuild new Building about to replace the old one.
   *        Can be also null.
   * @return worst building or null if no buildings
   */
  public static Building getWorstBuilding(final Planet planet,
      final PlayerInfo info, final Attitude attitude, final Building newBuild) {
    int lowestScore = MAX_SLOT_SCORE;
    Building lowBuilding = null;
    Building[] buildings = planet.getBuildingList();
    for (Building building : buildings) {
      int score = scoreBuilding(building, planet, info, attitude);
      if (newBuild != null && building.getType() == newBuild.getType()) {
        // This should increase the chance for upgrading the building.
        int newBonus = newBuild.getBattleBonus() + newBuild.getCredBonus()
        + newBuild.getCultBonus() + newBuild.getDefenseDamage()
        + newBuild.getFactBonus() + newBuild.getFarmBonus()
        + newBuild.getMineBonus() + newBuild.getRecycleBonus()
        + newBuild.getReseBonus() + newBuild.getScanRange();
        int oldBonus = building.getBattleBonus() + building.getCredBonus()
        + building.getCultBonus() + building.getDefenseDamage()
        + building.getFactBonus() + building.getFarmBonus()
        + building.getMineBonus() + building.getRecycleBonus()
        + building.getReseBonus() + building.getScanRange();
        int bonus = newBonus - oldBonus;
        score = score - 80 * bonus;
      }
      if (score < lowestScore) {
        lowestScore = score;
        lowBuilding = building;
      }
    }
    return lowBuilding;
  }
  /**
   * Handle construction by using scoring
   * @param constructions The constructions
   * @param planet The planet
   * @param info The planet info
   * @param map The star map
   * @param attitude AI's attitude
   * @return boolean true if construction has been selected
   */
  private static boolean handleConstructions(final Construction[] constructions,
      final Planet planet, final PlayerInfo info, final StarMap map,
      final Attitude attitude) {
    boolean constructionSelected = false;
    int[] scores = scoreConstructions(constructions, planet, info, map,
        attitude);
    int highest = -1;
    int value = -1;
    boolean over400 = false;
    boolean needToRemoveWorst = false;
    int minimum = 0;
    int freeSlot = planet.getGroundSize() - planet.getUsedPlanetSize();
    switch (freeSlot) {
    case 0:
      minimum = ZERO_SLOT_SCORE;
      needToRemoveWorst = true;
      break;
    case 1:
      minimum = ONE_SLOT_SCORE;
      break;
    case 2:
      minimum = TWO_SLOT_SCORE;
      break;
    case 3:
      minimum = THREE_SLOT_SCORE;
      break;
    case 4:
      minimum = FOUR_SLOT_SCORE;
      break;
    case 5:
      minimum = FIVE_SLOT_SCORE;
      break;
    case 6:
      minimum = SIX_SLOT_SCORE;
      break;
    default:
      minimum = 0;
      break;
    }
    for (int i = 0; i < scores.length; i++) {
      if (scores[i] > value) {
        value = scores[i];
        highest = i;
      }
      if (scores[i] >= HIGH_VALUE_SCORE) {
        over400 = true;
      }
    }
    if (highest == value) {
      if (highest != -1) {
        planet.setUnderConstruction(constructions[highest]);
        constructionSelected = true;
      }
    } else if (over400) {
      ArrayList<Construction> list = new ArrayList<>();
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] >= HIGH_VALUE_SCORE) {
          list.add(constructions[i]);
        }
      }
      int rand = DiceGenerator.getRandom(list.size() - 1);
      planet.setUnderConstruction(list.get(rand));
      constructionSelected = true;
    } else {
      ArrayList<Construction> list = new ArrayList<>();
      ArrayList<Integer> listScore = new ArrayList<>();
      int sum = 0;
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] >= minimum) {
          list.add(constructions[i]);
          listScore.add(Integer.valueOf(scores[i]));
          sum = sum + scores[i];
        } else {
          if (constructions[i] instanceof Ship && freeSlot < 4) {
            list.add(constructions[i]);
            listScore.add(Integer.valueOf(scores[i]));
            sum = sum + scores[i];
          } else if (constructions[i].getName()
              .equals(ConstructionFactory.MECHION_CITIZEN) && freeSlot < 3
              && planet.getTotalPopulation() < planet.getGroundSize()) {
            list.add(constructions[i]);
            listScore.add(Integer.valueOf(scores[i]));
            sum = sum + scores[i];
          }
        }
      }
      int rand = DiceGenerator.getRandom(sum);
      int total = 0;
      for (int i = 0; i < listScore.size(); i++) {
        if (rand < total + listScore.get(i).intValue()) {
          Construction cons = list.get(i);
          planet.setUnderConstruction(cons);
          constructionSelected = true;
          if (cons instanceof Building && needToRemoveWorst) {
            Building newBuild = (Building) cons;
            Building worst = getWorstBuilding(planet, info, attitude, newBuild);
            if  (worst != null) {
              // Removing the worst building
              planet.removeBuilding(worst);
            } else {
              // Could not remove the worst building so no selection can be
              // made
              planet.setUnderConstruction(null);
              constructionSelected = false;
            }
          }
          if (cons instanceof Ship) {
            Ship ship = (Ship) cons;
            if (ship.isPrivateeringShip()) {
              // Privateering ship show not assign any planned missions
              break;
            }
            if (ship.isSpyShip()) {
              Mission mission = createSpyShipMission(info, map);
              if (mission != null) {
                mission.setPlanetBuilding(planet.getName());
                info.getMissions().add(mission);
              }
              break;
            }
            Mission mission = info.getMissions().getMissionForPlanet(
                planet.getName(), MissionType.DEFEND);
            if (mission != null && ship.getTotalMilitaryPower() > 0
                && mission.getPhase() == MissionPhase.PLANNING) {
              mission.setPhase(MissionPhase.BUILDING);
            }
            mission = info.getMissions().getMission(MissionType.COLONIZE,
                MissionPhase.PLANNING);
            if (mission != null && ship.isColonyModule()) {
              mission.setPhase(MissionPhase.BUILDING);
              mission.setPlanetBuilding(planet.getName());
            }
            mission = info.getMissions().getMission(MissionType.TRADE_FLEET,
                MissionPhase.PLANNING);
            if (mission != null && ship.isTradeShip()) {
              mission.setPhase(MissionPhase.BUILDING);
              mission.setPlanetBuilding(planet.getName());
            }
            mission = info.getMissions().getGatherMission(Mission.TROOPER_TYPE);
            if (ship.isTrooperModule() && mission != null) {
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
            } else if (ship.hasBombs()
                && info.getMissions().getGatherMission(Mission.BOMBER_TYPE)
                != null) {
              mission = info.getMissions().getGatherMission(
                  Mission.BOMBER_TYPE);
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
            } else if (ship.getTotalMilitaryPower() > 0
                && info.getMissions().getGatherMission(Mission.ASSAULT_TYPE)
                != null) {
              mission = info.getMissions().getGatherMission(
                  Mission.ASSAULT_TYPE);
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
            }  else if (ship.getTotalMilitaryPower() > 0
                && info.getMissions().getGatherMission(Mission.ASSAULT_SB_TYPE)
                != null) {
              mission = info.getMissions().getGatherMission(
                  Mission.ASSAULT_SB_TYPE);
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
            }
            mission = info.getMissions().getMission(MissionType.DEPLOY_STARBASE,
                MissionPhase.PLANNING);
            if (mission != null
                && ship.getHull().getHullType() == ShipHullType.STARBASE) {
              mission.setPhase(MissionPhase.BUILDING);
              mission.setPlanetBuilding(planet.getName());
            }
          }
          break;
        }
        total = total + listScore.get(i).intValue();
      }
    }
    return constructionSelected;
  }

  /**
   * Create Spy Ship mission
   * @param info Player building spy ship
   * @param map StarMap
   * @return Mission if player knows some other realm otherwise null
   */
  public static Mission createSpyShipMission(final PlayerInfo info,
      final StarMap map) {
    Mission result = null;
    int targetRealm = info.getDiplomacy()
        .getLeastLikingWithLowEspionage(info.getEspionage());
    Planet[] planets = map.getPlanetListSeenByOther(targetRealm, info);
    if (planets.length > 0) {
      Planet target = planets[DiceGenerator.getRandom(
          planets.length - 1)];
      Mission mission = new Mission(MissionType.SPY_MISSION,
          MissionPhase.BUILDING, target.getCoordinate());
      mission.setTargetPlanet(target.getName());
      result = mission;
    } else {
      for (int j = 0; j < map.getPlayerList().getCurrentMaxPlayers();
          j++) {
        planets = map.getPlanetListSeenByOther(j, info);
        if (planets.length > 0) {
          Planet target = planets[DiceGenerator.getRandom(
              planets.length - 1)];
          Mission mission = new Mission(MissionType.SPY_MISSION,
              MissionPhase.BUILDING, target.getCoordinate());
          mission.setTargetPlanet(target.getName());
          result = mission;
          break;
        }
      }
    }
    return result;
  }
  /**
   * Score ship, scoring also depends on missions under planning
   * @param ship Ship to score
   * @param state Current gameLengthState
   * @return Ship score
   */
  public static int scoreShip(final Ship ship, final GameLengthState state) {
    int score;
    // Does not take a planet space
    score = 20;
    int militaryFocus = 2;
    if (state == GameLengthState.MIDDLE_GAME) {
      militaryFocus = 3;
    }
    if (state == GameLengthState.LATE_GAME) {
      militaryFocus = 4;
    }
    if (state == GameLengthState.END_GAME) {
      militaryFocus = 5;
    }
    score = score + ship.getTotalMilitaryPower() * militaryFocus;
    // High cost drops the value
    score = score - ship.getMetalCost() / 10;
    score = score - ship.getProdCost() / 10;
    return score;
  }

  /**
   * Score trade ship. Separating this makes easier to do JUnits.
   * @param preScore Pre score from generic ship scoring.
   * @param ship Trade ship to score
   * @param planet Planet about to build
   * @param info Player who is building
   * @param map StarMap
   * @param attitude Attitude for AI
   * @return score for the ship
   */
  protected static int scoreTradeShip(final int preScore, final Ship ship,
      final Planet planet, final PlayerInfo info, final StarMap map,
      final Attitude attitude) {
    int score = preScore;
    if (ship.isTradeShip()) {
      // Trade ship should be built only on request
      Mission mission = info.getMissions().getMission(
          MissionType.TRADE_FLEET, MissionPhase.PLANNING);
      if (mission != null) {
        Planet tradePlanet = map.getPlanetByName(mission.getTargetPlanet());
        if (tradePlanet != null) {
          double distance = tradePlanet.getCoordinate().calculateDistance(
            planet.getCoordinate());
          score = score + (int) Math.round(distance / 25);
        }
        if (attitude == Attitude.MERCHANTICAL) {
          score = score + 20;
        } else if (attitude == Attitude.DIPLOMATIC) {
          score = score + 15;
        } else if (attitude == Attitude.PEACEFUL) {
          score = score + 10;
        } else if (attitude == Attitude.SCIENTIFIC) {
          score = score + 5;
        } else if (attitude == Attitude.MILITARISTIC
            || attitude == Attitude.AGGRESSIVE) {
          score = score - 10;
        }
        if (ship.getEspionageBonus() > 0 && info.researchSpyShips()) {
          // Spy trade is plus if AI likes spy ships
          score = score + 10;
        }
      } else {
        score = -1;
      }
    }
    return score;
  }

  /**
   * Score spy ship. Separating this makes easier to do JUnits.
   * @param preScore Pre score from generic ship scoring.
   * @param ship Trade ship to score
   * @param info Player who is building
   * @param map StarMap
   * @param attitude Attitude for AI
   * @return score for the ship
   */
  protected static int scoreSpyShip(final int preScore, final Ship ship,
      final PlayerInfo info, final StarMap map, final Attitude attitude) {
    int score = preScore;
    if (ship.isSpyShip()) {
      boolean moreIsRequired = false;
      for (int i = 0; i < map.getPlayerList().getCurrentMaxPlayers(); i++) {
        EspionageList espionage = info.getEspionage().getByIndex(i);
        DiplomacyBonusList diplomacy = info.getDiplomacy().getDiplomacyList(i);
        if (espionage != null && diplomacy != null
            && diplomacy.getNumberOfMeetings() > 0
            && espionage.getTotalBonus() < 8) {
            moreIsRequired = true;
            break;
          }
      }
      if (moreIsRequired) {
        score = score + ship.getEspionageBonus() * 10;
        if (attitude == Attitude.BACKSTABBING) {
          score = score + 10;
        }
        if (attitude == Attitude.AGGRESSIVE
            || attitude == Attitude.MILITARISTIC) {
          score = score + 5;
        }
      } else {
        score = -1;
      }
    }
    return score;
  }

  /**
   * Score colony ship.
   * @param preScore Prescore from regular ship scoring
   * @param ship Ship to score
   * @param info Player who is about to build ship.
   * @param map Starmp
   * @param attitude Player attitude
   * @return Score of the colony ship.
   */
  protected static int scoreColonyShip(final int preScore, final Ship ship,
      final PlayerInfo info, final StarMap map, final Attitude attitude) {
    int score = preScore;
    if (ship.isColonyModule()) {
      // Colony ship should be built only on request
      Mission mission = info.getMissions().getMission(MissionType.COLONIZE,
          MissionPhase.PLANNING);
      if (mission != null) {
        Planet colonPlanet = map.getPlanetByCoordinate(mission.getX(),
            mission.getY());
        int colonyScore = (colonPlanet.getGroundSize() - 7) * 3
            + colonPlanet.getAmountMetalInGround() / 400;
        score = score + info.getRace().getMaxRad()
            - colonPlanet.getTotalRadiationLevel();
        score = score + colonyScore;
        if (attitude == Attitude.EXPANSIONIST) {
          score = score + 20;
        }
        if (map.getGameLengthState() == GameLengthState.START_GAME) {
          score = score + 30;
        }
        if (map.getGameLengthState() == GameLengthState.EARLY_GAME) {
          score = score + 20;
        }
        if (map.getGameLengthState() == GameLengthState.MIDDLE_GAME) {
          score = score + 10;
        }
      } else {
        score = -1;
      }
    }
    return score;
  }
  /**
   * Calculate scores for each construction. Each score is between -1 and 1000
   * @param constructions The constructions
   * @param planet The planet
   * @param info The planet info
   * @param map The star map
   * @param attitude AI's attitude
   * @return The calculate scores
   */
  private static int[] scoreConstructions(final Construction[] constructions,
      final Planet planet, final PlayerInfo info, final StarMap map,
      final Attitude attitude) {
    int[] scores = new int[constructions.length];
    for (int i = 0; i < constructions.length; i++) {
      scores[i] = -1;
      if (constructions[i].getName()
          .equals(ConstructionFactory.MECHION_CITIZEN)) {
        scores[i] = planet.getAmountMetalInGround() / 100
            - 2 * planet.getTotalPopulation();
        // Does not take a planet space
        scores[i] = scores[i] + 20;
        int index = map.getPlayerList().getIndex(info);
        if (map.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_RESEARCH,
            index) == 0) {
          // No research so focusing on building more population
          scores[i] = scores[i] + 50;
        }

      }
      if (constructions[i] instanceof Building) {
        Building building = (Building) constructions[i];
        scores[i] = scoreBuilding(building, planet, info, attitude);
      }
      if (constructions[i] instanceof Ship) {
        Ship ship = (Ship) constructions[i];
        int score = scoreShip(ship, map.getGameLengthState());
        if (ship.getTotalMilitaryPower() > 0) {
          Mission mission = info.getMissions()
              .getMissionForPlanet(planet.getName(), MissionType.DEFEND);
          if (mission != null) {
            if (mission.getPhase() == MissionPhase.PLANNING) {
              score = score + ship.getTotalMilitaryPower() * 2;
              if (attitude == Attitude.AGGRESSIVE
                  || attitude == Attitude.MILITARISTIC) {
                score = score + 20;
              } else if (attitude == Attitude.PEACEFUL) {
                score = score - 10;
              }
            } else if (mission.getPhase() == MissionPhase.BUILDING) {
              score = score + ship.getTotalMilitaryPower();
              if (attitude == Attitude.AGGRESSIVE
                  || attitude == Attitude.MILITARISTIC) {
                score = score + 20;
              } else if (attitude == Attitude.PEACEFUL) {
                score = score - 10;
              }
            }
          } else {
            mission = info.getMissions().getMission(MissionType.GATHER,
                MissionPhase.PLANNING);
            if (mission != null) {
              score = score + ship.getTotalMilitaryPower() * 3;
              if (attitude == Attitude.AGGRESSIVE
                  || attitude == Attitude.MILITARISTIC) {
                score = score + 30;
              } else if (attitude == Attitude.BACKSTABBING) {
                score = score + 20;
              } else if (attitude == Attitude.LOGICAL) {
                score = score + 10;
              } else if (attitude == Attitude.PEACEFUL) {
                score = score - 10;
              }
              if (ship.getTotalMilitaryPower() > 0
                  && info.getMissions().getGatherMission(
                      Mission.ASSAULT_TYPE) != null) {
                score = score + 30;
              }
              if (ship.getTotalMilitaryPower() > 0
                  && info.getMissions().getGatherMission(
                      Mission.ASSAULT_SB_TYPE) != null) {
                score = score + 30;
              }
            }
          }

        }
        if (ship.isColonyModule()) {
          // Colony ship should be built only on request
          score = scoreColonyShip(score, ship, info, map, attitude);
        }
        if (ship.hasBombs() && info.getMissions().getGatherMission(
            Mission.BOMBER_TYPE) != null) {
          score = score + 50;
          if (attitude == Attitude.AGGRESSIVE
              || attitude == Attitude.MILITARISTIC) {
            score = score + 30;
          } else if (attitude == Attitude.BACKSTABBING) {
            score = score + 20;
          } else if (attitude == Attitude.LOGICAL) {
            score = score + 10;
          } else if (attitude == Attitude.PEACEFUL) {
            score = score - 10;
          }
        }
        if (ship.isTrooperModule()) {
          // Trooper ship should be built only on request
          Mission mission = info.getMissions().getGatherMission(
              Mission.TROOPER_TYPE);
          if (mission == null) {
            score = -1;
          } else {
            score = score + 50;
            if (attitude == Attitude.AGGRESSIVE
                || attitude == Attitude.MILITARISTIC) {
              score = score + 30;
            } else if (attitude == Attitude.BACKSTABBING) {
              score = score + 20;
            } else if (attitude == Attitude.LOGICAL) {
              score = score + 10;
            } else if (attitude == Attitude.PEACEFUL) {
              score = score - 10;
            }
          }
        }
        if (ship.isStarBase()) {
          Mission mission = info.getMissions().getMission(
              MissionType.DEPLOY_STARBASE, MissionPhase.PLANNING);
          if (mission != null) {
            if (attitude == Attitude.SCIENTIFIC) {
              score = score + ship.getTotalResearchBonus() * 5;
            } else {
              score = score + ship.getTotalResearchBonus() * 3;
            }
            if (attitude == Attitude.DIPLOMATIC
                || attitude == Attitude.PEACEFUL) {
              score = score + ship.getTotalCultureBonus() * 5;
            } else {
              score = score + ship.getTotalCultureBonus() * 3;
            }
            if (attitude == Attitude.MERCHANTICAL) {
              score = score + ship.getTotalCreditBonus() * 5;
            } else {
              score = score + ship.getTotalCreditBonus() * 3;
            }
          } else {
            score = -1;
          }
        }
        if (ship.isTradeShip()) {
          // Trade ship should be built only on request
          score = scoreTradeShip(score, ship, planet, info, map, attitude);
        }
        if (ship.isSpyShip()) {
          score = scoreSpyShip(score, ship, info, map, attitude);
        }
        scores[i] = score;
      }

      // Sanitize score
      if (scores[i] > MAX_SLOT_SCORE) {
        scores[i] = MAX_SLOT_SCORE;
      }
      if (scores[i] < -1) {
        scores[i] = -1;
      }

    }
    return scores;
  }

  /**
   * Handle planet population positions
   * @param planet Planet to handle
   * @param info Player who owns the planet
   */
  protected static void handlePlanetPopulation(final Planet planet,
      final PlayerInfo info) {
    if (info.getRace() == SpaceRace.MECHIONS) {
      int total = planet.getTotalPopulation();
      int quarter = total / 4;
      int modulo = total % 4;
      if (quarter > 1) {
        int metalAdd = 0;
        int prodAdd = 0;
        int reseAdd = 0;
        int cultAdd = 0;
        if (modulo == 1) {
          prodAdd = 1;
        }
        if (modulo == 2) {
          prodAdd = 1;
          metalAdd = 1;
        }
        if (modulo == 3) {
          prodAdd = 2;
          metalAdd = 1;
        }
        planet.setWorkers(Planet.METAL_MINERS, quarter + metalAdd);
        planet.setWorkers(Planet.PRODUCTION_WORKERS, quarter + prodAdd);
        if (quarter % 2 != 0) {
          reseAdd = 1;
          cultAdd = -1;
        }
        planet.setWorkers(Planet.RESEARCH_SCIENTIST, quarter + reseAdd);
        planet.setWorkers(Planet.CULTURE_ARTIST, quarter + cultAdd);
      } else {
        switch (total) {
        case 1: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 0);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        case 2: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 1);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        case 3: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 0);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        case 4: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 1);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        case 5: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 2);
          planet.setWorkers(Planet.METAL_MINERS, 1);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        case 6: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 2);
          planet.setWorkers(Planet.METAL_MINERS, 2);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        case 7: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 3);
          planet.setWorkers(Planet.METAL_MINERS, 2);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          planet.setWorkers(Planet.CULTURE_ARTIST, 0);
          break;
        }
        default: {
          // This happens only if there are no population or it is negative.
          // There no works can be set.
          throw new IllegalArgumentException("Planet(" + planet.getName()
              + ") has no population but is still colonized!");
        }
        }
      }
    } else if (info.getRace() == SpaceRace.HOMARIANS) {
      int total = planet.getTotalPopulation();
      if (total > 5) {
        int food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
        int foodReq = total * info.getRace().getFoodRequire() / 100;
        food = food - foodReq;
        if (food > 2) {
          planet.moveWorker(Planet.FOOD_FARMERS, Planet.PRODUCTION_WORKERS);
        } else if (food <= 0) {
          planet.moveWorker(Planet.METAL_MINERS, Planet.FOOD_FARMERS);
          food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
          foodReq = total * info.getRace().getFoodRequire() / 100;
          food = food - foodReq;
          if (food < 0) {
            planet.moveWorker(Planet.PRODUCTION_WORKERS, Planet.FOOD_FARMERS);
            food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
            foodReq = total * info.getRace().getFoodRequire() / 100;
            food = food - foodReq;
            if (food < 0) {
              planet.moveWorker(Planet.RESEARCH_SCIENTIST, Planet.FOOD_FARMERS);
            }
          }
        }
        if (planet.getTotalProductionFromBuildings(
            Planet.PRODUCTION_RESEARCH) == 0
            && planet.getWorkers(Planet.RESEARCH_SCIENTIST) == 0) {
          planet.moveWorker(Planet.PRODUCTION_WORKERS,
              Planet.RESEARCH_SCIENTIST);
          planet.moveWorker(Planet.METAL_MINERS, Planet.RESEARCH_SCIENTIST);
        }
        if (planet.getTotalProduction(Planet.PRODUCTION_METAL)
            < planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION)) {
          planet.moveWorker(Planet.PRODUCTION_WORKERS, Planet.METAL_MINERS);
        }
        if (planet.calculateHappiness() < -1) {
          planet.moveWorker(Planet.PRODUCTION_WORKERS, Planet.CULTURE_ARTIST);
        }
      } else {
        // Fixed amount of population 5 or less
        planet.setWorkers(Planet.FOOD_FARMERS, 0);
        planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
        planet.setWorkers(Planet.METAL_MINERS, 0);
        planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
        planet.setWorkers(Planet.CULTURE_ARTIST, 0);
        switch (total) {
        case 1: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          break;
        }
        case 2: {
          planet.setWorkers(Planet.FOOD_FARMERS, 1);
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          break;
        }
        case 3: {
          if (planet.getTotalProductionFromBuildings(
              Planet.PRODUCTION_RESEARCH) > 0) {
            planet.setWorkers(Planet.FOOD_FARMERS, 1);
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
            planet.setWorkers(Planet.METAL_MINERS, 1);
          } else {
            planet.setWorkers(Planet.FOOD_FARMERS, 1);
            planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          }
          break;
        }
        case 4: {
          planet.setWorkers(Planet.FOOD_FARMERS, 2);
          if (planet.getTotalProductionFromBuildings(
              Planet.PRODUCTION_RESEARCH) > 0) {
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
            planet.setWorkers(Planet.METAL_MINERS, 1);
          } else {
            planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          }
          break;
        }
        case 5: {
          planet.setWorkers(Planet.PRODUCTION_FOOD, 2);
          if (planet.getTotalProductionFromBuildings(
              Planet.PRODUCTION_RESEARCH) > 0) {
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 2);
            planet.setWorkers(Planet.METAL_MINERS, 1);
          } else {
            planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
            planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          }
          if (planet.calculateHappiness() < -1) {
            GovernmentType government = planet.getPlanetPlayerInfo()
                .getGovernment();
            if (government.getResearchBonus() > 0) {
              if (planet.getTotalProductionFromBuildings(
                  Planet.PRODUCTION_RESEARCH) == 0) {
                planet.moveWorker(Planet.RESEARCH_SCIENTIST,
                    Planet.CULTURE_ARTIST);
                planet.moveWorker(Planet.RESEARCH_SCIENTIST,
                    Planet.PRODUCTION_PRODUCTION);
              } else {
                planet.moveWorker(Planet.PRODUCTION_PRODUCTION,
                    Planet.CULTURE_ARTIST);
              }
            } else {
              planet.moveWorker(Planet.PRODUCTION_PRODUCTION,
                  Planet.CULTURE_ARTIST);
            }
          }
          break;
        }
        default: {
          // This happens only if there are no population or it is negative.
          // There no works can be set.
          throw new IllegalArgumentException("Planet(" + planet.getName()
              + ") has no population but is still colonized!");
        }
        }
      }
    } else {
      // Handle races whom need something to eat and have regular research
      int food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
      int total = planet.getTotalPopulation();
      int foodReq = total * info.getRace().getFoodRequire() / 100;
      food = food - foodReq;
      if (food > 2) {
        if (info.getRace() == SpaceRace.GREYANS
            || info.getRace() == SpaceRace.TEUTHIDAES) {
          if (planet.getWorkers(Planet.RESEARCH_SCIENTIST) <= planet
              .getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.RESEARCH_SCIENTIST);
          } else if (planet.getWorkers(Planet.METAL_MINERS) * 2 >= planet
              .getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.METAL_MINERS);
          } else if (planet.getWorkers(Planet.CULTURE_ARTIST) == 0) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.CULTURE_ARTIST);
          } else {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.PRODUCTION_WORKERS);
          }
        } else {
          if (planet.getWorkers(Planet.RESEARCH_SCIENTIST) * 2 >= planet
              .getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.RESEARCH_SCIENTIST);
          } else if (planet.getWorkers(Planet.METAL_MINERS) * 2 >= planet
              .getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.METAL_MINERS);
          } else if (planet.getWorkers(Planet.CULTURE_ARTIST) == 0) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.CULTURE_ARTIST);
          } else {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.PRODUCTION_WORKERS);
          }
        }
      }
      if (food < 0) {
        if (planet.getWorkers(Planet.CULTURE_ARTIST) > 0) {
          planet.moveWorker(Planet.CULTURE_ARTIST, Planet.FOOD_FARMERS);
        } else if (planet.getWorkers(Planet.METAL_MINERS) > 0) {
          planet.moveWorker(Planet.METAL_MINERS, Planet.FOOD_FARMERS);
        } else if (planet.getWorkers(Planet.PRODUCTION_WORKERS) > 0) {
          planet.moveWorker(Planet.PRODUCTION_WORKERS, Planet.FOOD_FARMERS);
        } else if (planet.getWorkers(Planet.RESEARCH_SCIENTIST) > 0) {
          planet.moveWorker(Planet.RESEARCH_SCIENTIST, Planet.FOOD_FARMERS);
        }
      }
      if (food > 0 && planet.getTotalPopulation() == planet.getGroundSize()) {
        if (planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION) == 0) {
          planet.moveWorker(Planet.FOOD_FARMERS, Planet.PRODUCTION_WORKERS);
        } else if (planet.getWorkers(Planet.RESEARCH_SCIENTIST) * 2 >= planet
            .getWorkers(Planet.PRODUCTION_WORKERS)) {
          planet.moveWorker(Planet.FOOD_FARMERS, Planet.RESEARCH_SCIENTIST);
        } else if (planet.getWorkers(Planet.METAL_MINERS) * 2 >= planet
            .getWorkers(Planet.PRODUCTION_WORKERS)) {
          planet.moveWorker(Planet.FOOD_FARMERS, Planet.METAL_MINERS);
        } else if (planet.getWorkers(Planet.CULTURE_ARTIST) == 0) {
          planet.moveWorker(Planet.FOOD_FARMERS, Planet.CULTURE_ARTIST);
        } else {
          planet.moveWorker(Planet.FOOD_FARMERS, Planet.PRODUCTION_WORKERS);
        }
      }
      int happiness = planet.calculateHappiness();
      if (happiness < -1) {
        if (planet.getTotalProductionFromBuildings(
            Planet.PRODUCTION_RESEARCH) > 0
            && planet.getWorkers(Planet.RESEARCH_SCIENTIST) > 0) {
          planet.moveWorker(Planet.RESEARCH_SCIENTIST, Planet.CULTURE_ARTIST);
        } else if (planet.getPlanetPlayerInfo().getGovernment()
            .getResearchBonus() > 0
            && planet.getWorkers(Planet.RESEARCH_SCIENTIST) > 0) {
          planet.moveWorker(Planet.RESEARCH_SCIENTIST, Planet.CULTURE_ARTIST);
        } else if (planet.getWorkers(Planet.METAL_MINERS) > 0) {
          planet.moveWorker(Planet.METAL_MINERS, Planet.CULTURE_ARTIST);
        } else if (planet.getWorkers(Planet.PRODUCTION_WORKERS) > 0) {
          planet.moveWorker(Planet.PRODUCTION_WORKERS, Planet.CULTURE_ARTIST);
        }
      }
    }
  }

  /**
   * Look for certain construction from the list
   * @param name Name for search
   * @param list Construction list
   * @return index or -1 if not found
   */
  private static int getConstruction(final String name,
      final Construction[] list) {
    for (int i = 0; i < list.length; i++) {
      if (list[i].getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Check if planet buildings contain following buildings
   * @param names List of building to check. First must be least significant
   * building and so on. For example first could be "Basic lab", second one
   * could be "Advanced lab"
   * @param buildings Buildings array to check
   * @return Which was the biggest index found from the name array. -1 if none
   * was found
   */
  private static int gotBuildings(final String[] names,
      final Building[] buildings) {
    int result = -1;
    for (int i = 0; i < buildings.length; i++) {
      for (int j = 0; j < names.length; j++) {
        if (buildings[i].getName().equals(names[j]) && result < j) {
          result = j;
        }
      }
    }
    return result;
  }
}
