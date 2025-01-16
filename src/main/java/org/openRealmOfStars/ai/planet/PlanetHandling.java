package org.openRealmOfStars.ai.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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

import java.util.ArrayList;

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.WinningStrategy;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 *
 * AI handling for planets
 *
 */

public final class PlanetHandling {

  /** Maximum score for slots */
  static final int MAX_SLOT_SCORE = 275;

  /** Score for zero available slot */
  private static final int ZERO_SLOT_SCORE = 20;
  /** Score for one available slot */
  private static final int ONE_SLOT_SCORE = 15;
  /** Score for two available slot */
  private static final int TWO_SLOT_SCORE = 10;
  /** Score for three available slot */
  private static final int THREE_SLOT_SCORE = 8;

  /** High value score for construction */
  private static final int HIGH_VALUE_SCORE = 250;

  /**
   * Remove worst building from the planet.
   * @param map StarMap
   * @param planet Planet where to remove building
   * @param index PlayerInfo index
   * @param attitude Attitude for scoring the buildings.
   * @return true if worst was removed.
   */
  public static boolean removeWorstBuilding(final StarMap map,
      final Planet planet, final int index, final Attitude attitude) {
    PlayerInfo info = map.getPlayerByIndex(index);
    if (info == null) {
      return false;
    }
    int fleetCap = map.getTotalFleetCapacity(info);
    double fleetSize = info.getFleets().getTotalFleetCapacity();
    boolean nearFleetLimit = false;
    if (fleetSize + 1 > fleetCap) {
      nearFleetLimit = true;
    }
    Building newBuild = (Building) planet.getUnderConstruction();
    Building worst = getWorstBuilding(planet, info, attitude, newBuild,
        nearFleetLimit);
    if (worst != null && planet.fightAgainstWildLife(worst, map)) {
      // Removing the worst building
      planet.removeBuilding(worst);
      return true;
    }
    return false;
  }

  /**
   * Choose next construction for planet.
   * @param map StarMap
   * @param planet Planet which will choose next construction
   * @param index Realm index
   * @param attitude Attitude used for selecting next construction.
   */
  public static void chooseNextConstruction(final StarMap map,
      final Planet planet, final int index, final Attitude attitude) {
    PlayerInfo info = map.getPlayerByIndex(index);
    if (info == null) {
      return;
    }
    Building[] buildings = planet.getBuildingList();
    Construction[] constructions = planet.getProductionList();
    boolean constructionSelected = false;
    int gotFactory = gotBuildings(new String[] {
        "Basic factory", "Advanced factory", "Manufacturing center",
        "Nanobot manufacturing center", "Ancient factory" },
        buildings);
    int gotLabs = gotBuildings(new String[] {
        "Basic lab", "Advanced laboratory", "Research center",
        "Neural research center", "Ancient lab", "College of history" },
        buildings);
    int gotFarms = gotBuildings(new String[] {
        "Basic farm", "Advanced farm",
        "Farming center", "Hydropodic farming center" },
        buildings);
    int gotMines = gotBuildings(new String[] {
        "Basic mine", "Advanced mine",
        "Mining center", "Nanobot mining center" },
        buildings);
    int gotSpacePort = gotBuildings(new String[] {
        "Space port" },
        buildings);
    if (gotFactory == -1) {
      // No factories at all
     int i = getConstruction("Basic factory", constructions);
     if (i != -1) {
       planet.setUnderConstruction(constructions[i]);
        constructionSelected = true;
      }
    }
    if (gotLabs == -1 && !constructionSelected) {
      // No labs at all
      int i = getConstruction("College of history", constructions);
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

    boolean needFood = info.getRace().isEatingFood();
    if (gotFarms == -1 && !constructionSelected && !needFood) {
      // No farms at all
      int i = getConstruction("Basic farm", constructions);
      if (i != -1) {
        planet.setUnderConstruction(constructions[i]);
        constructionSelected = true;
      }
    }
    if (gotMines == -1 && !constructionSelected
        && planet.getAmountMetalInGround() > 30) {
      int i = getConstruction("Basic mine", constructions);
      if (i != -1) {
        planet.setUnderConstruction(constructions[i]);
        constructionSelected = true;
      }
    }

    if (!constructionSelected
        && info.getRace().hasTrait(TraitIds.CONSTRUCTED_POP)
        && planet.getTotalPopulation() < 3) {
      var prod = planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION);
      var metalProd = planet.getTotalProduction(Planet.PRODUCTION_METAL);
      // TODO: Better population build project lookup
      var popProjectId = info.getRace().getNameSingle() + " citizen";
      int i = getConstruction(popProjectId, constructions);
      if (i != -1
          && prod > constructions[i].getProdCost() / 7
          && metalProd > constructions[i].getMetalCost() / 7) {
        planet.setUnderConstruction(constructions[i]);
        constructionSelected = true;
      }
    }

    if (gotSpacePort == -1 && !constructionSelected
        && planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION) > 3
        && planet.getTotalProduction(Planet.PRODUCTION_METAL) > 3) {
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
    } else {
      int freeSlot = planet.getGroundSize() - planet.getUsedPlanetSize();
      if (planet.getUnderConstruction() instanceof Building
          && freeSlot == 0) {
        boolean isHuman = info.isHuman();
        boolean isWeakAi = false;
        if (info.getAiDifficulty() == AiDifficulty.WEAK) {
          isWeakAi = true;
        }
        if (!isHuman && isWeakAi
            && !removeWorstBuilding(map, planet, index, attitude)) {
          // Could not remove the worst building so no selection can be
          // made
          planet.setUnderConstruction(null);
          constructionSelected = false;
        }
      }
    }
    if (!constructionSelected) {
      // Nothing to select to let's select culture or credit
      int i = getConstruction(ConstructionFactory.EXTRA_CREDIT,
          constructions);
      int j = getConstruction(ConstructionFactory.EXTRA_CULTURE,
          constructions);
      if (i != -1 && j != -1) {
        if (DiceGenerator.getBoolean()) {
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
  }

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
      if (info.getAiDifficulty() == AiDifficulty.NORMAL
          || info.getAiDifficulty() == AiDifficulty.CHALLENGING) {
        for (Building building : planet.getBuildingList()) {
          if (building.getWildLifePower() > 0
              && planet.fightAgainstWildLife(building, map)) {
            // Removing the wild life
            planet.removeBuilding(building);
            break;
          }
        }
      }
      int totalResearch = map.getTotalProductionByPlayerPerTurn(
          Planet.PRODUCTION_RESEARCH, index);
      Attitude attitude = info.getAiAttitude();
      handlePlanetPopulation(planet, info, totalResearch,
          map.getGameLengthState());
      if (credit < 0
          && planet.getTax() < planet.getTotalProduction(
              Planet.PRODUCTION_PRODUCTION) + 2) {
        planet.setTax(planet.getTax() + 1, false);
      } else if (credit > 0 && planet.getTax() > 0) {
        planet.setTax(planet.getTax() - 1, false);
      }
      SpaceRace planetRace = planet.getPlanetPlayerInfo().getRace();
      if (planetRace.hasTrait(TraitIds.ENERGY_POWERED)
          && !planetRace.hasTrait(TraitIds.CONSTRUCTED_POP)) {
        int grow = planet.getTotalProduction(Planet.PRODUCTION_POPULATION);
        if (grow < 0) {
          // Tries to set that population do not die
          planet.setTax(planet.getTax() - grow, false);
        }
        if (planet.getTotalPopulation() < planet.getPopulationLimit()) {
          grow = planet.getTotalProduction(Planet.PRODUCTION_POPULATION);
          if (grow < 1) {
            planet.setTax(planet.getTax() + 1, false);
          }
        }
      }

      if (info.areLeadersDead() && credit == 0
          && info.getTotalCredits() < LeaderUtility.leaderRecruitCost(info)
          && planet.getTax() < planet.getTotalProduction(
              Planet.PRODUCTION_PRODUCTION) + 2) {
        // Set Planet text higher just for get new ruler
        planet.setTax(planet.getTax() + 1, false);
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
        chooseNextConstruction(map, planet, index, attitude);
      } else {
        // Let's check rushing
        int rushCost = planet.getRushingCost(planet.getUnderConstruction());
        int rushPopulation = rushCost / Planet.POPULATION_RUSH_COST + 1;
        int buildingTime = planet.getProductionTime(
            planet.getUnderConstruction());
        int rushChange = 0;
        boolean creditRush = true;
        boolean hasCreditRush = info.getGovernment().hasCreditRush();
        boolean hasPopulationRush = info.getGovernment().hasPopulationRush();
        if (hasCreditRush
            && rushCost < info.getTotalCredits() && buildingTime > 1
            && rushCost > 0) {
          Mission mission = info.getMissions().getMissionForPlanet(
              planet.getName(), MissionPhase.BUILDING);
          if (mission != null) {
            // Planet on mission for building something
            rushChange = rushChange + 10;
          }
          int rushPerCredit = info.getTotalCredits() / rushCost;
          if (rushPerCredit == 2) {
            rushChange = rushChange + 5;
          } else if (rushPerCredit == 3) {
            rushChange = rushChange + 7;
          } else if (rushPerCredit == 4) {
            rushChange = rushChange + 10;
          } else if (rushPerCredit == 5) {
            rushChange = rushChange + 12;
          } else if (rushPerCredit == 6) {
            rushChange = rushChange + 14;
          } else if (rushPerCredit == 7) {
            rushChange = rushChange + 15;
          } else if (rushPerCredit == 8) {
            rushChange = rushChange + 17;
          } else if (rushPerCredit == 9) {
            rushChange = rushChange + 18;
          } else if (rushPerCredit >= 10) {
            rushChange = rushChange + 20;
          }
          if (planet.getUnderConstruction() instanceof Ship) {
            rushChange = rushChange + 5;
          }
        }
        if (hasPopulationRush
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
   * Get the planet's worst building
   * @param planet Planet which to check
   * @param info PlayerInfo who owns the planet
   * @param attitude AI's attitude
   * @param newBuild new Building about to replace the old one.
   *        Can be also null.
   * @param nearFleetLimit Is fleet capacity over the limit or near it.
   * @return worst building or null if no buildings
   */
  public static Building getWorstBuilding(final Planet planet,
      final PlayerInfo info, final Attitude attitude, final Building newBuild,
      final boolean nearFleetLimit) {
    int lowestScore = MAX_SLOT_SCORE;
    Building lowBuilding = null;
    Building[] buildings = planet.getBuildingList();
    for (Building building : buildings) {
      int score = DefaultScoring.scoreBuilding(building, planet, info,
          attitude, nearFleetLimit);
      if (building.getWildLifePower() > 0) {
        score = score - building.getWildLifePower() * 2;
      }
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
      if (score < lowestScore && !building.getName().equals("Space port")) {
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
    int fleetCap = map.getTotalFleetCapacity(info);
    double fleetSize = info.getFleets().getTotalFleetCapacity();
    boolean nearFleetLimit = false;
    if (fleetSize + 1 > fleetCap) {
      nearFleetLimit = true;
    }
    int[] scores = null;
    if (info.getAiDifficulty() == AiDifficulty.CHALLENGING) {
      scores = ChallengingScoring.scoreConstructions(constructions,
          planet, info, map,
          attitude, nearFleetLimit);
    } else {
      scores = DefaultScoring.scoreConstructions(constructions, planet, info,
          map,
          attitude, nearFleetLimit);
    }
    int highest = -1;
    int value = -1;
    boolean over400 = false;
    boolean needToRemoveWorst = false;
    int minimum = 0;
    int freeSlot = planet.getGroundSize() - planet.getUsedPlanetSize();
    int chanceForHighest = 0;
    if (attitude == Attitude.LOGICAL) {
      chanceForHighest = chanceForHighest + 30;
    }
    if (attitude == Attitude.SCIENTIFIC) {
      chanceForHighest = chanceForHighest + 20;
    }
    switch (freeSlot) {
      case 0:
        minimum = ZERO_SLOT_SCORE;
        needToRemoveWorst = true;
        chanceForHighest = chanceForHighest + 30;
        break;
      case 1:
        minimum = ONE_SLOT_SCORE;
        chanceForHighest = chanceForHighest + 20;
        break;
      case 2:
        minimum = TWO_SLOT_SCORE;
        chanceForHighest = chanceForHighest + 10;
        break;
      case 3:
        minimum = THREE_SLOT_SCORE;
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
    if (info.getAiDifficulty() == AiDifficulty.CHALLENGING && highest != -1
        && DiceGenerator.getRandom(100) > 10) {
      planet.setUnderConstruction(constructions[highest]);
      constructionSelected = true;
    } else if (highest != -1
        && DiceGenerator.getRandom(100) < chanceForHighest) {
      planet.setUnderConstruction(constructions[highest]);
      constructionSelected = true;
    } else if (over400) {
      ArrayList<Construction> list = new ArrayList<>();
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] >= HIGH_VALUE_SCORE) {
          list.add(constructions[i]);
        }
      }
      var randConstruction = DiceGenerator.pickRandom(list);
      planet.setUnderConstruction(randConstruction);
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
          if (constructions[i] instanceof Ship && freeSlot < 4
              && scores[i] > 0) {
            list.add(constructions[i]);
            listScore.add(Integer.valueOf(scores[i]));
            sum = sum + scores[i];
          } else if (constructions[i].getName().endsWith(" citizen")
              && freeSlot < 3
              && planet.getTotalPopulation() < planet.getPopulationLimit()
              && scores[i] > 0) {
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
            Mission mission = info.getMissions().getMission(
                MissionType.COLONIZE, MissionPhase.PLANNING);
            if (mission != null && ship.isColonyModule()) {
              mission.setPhase(MissionPhase.BUILDING);
              mission.setPlanetBuilding(planet.getName());
              break;
            }
            if (info.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
              mission = info.getMissions().getMissionForPlanet(
                  planet.getName(), MissionType.DEFEND);
              if (mission != null && ship.getTotalMilitaryPower() > 0
                  && mission.getPhase() == MissionPhase.PLANNING) {
                mission.setPhase(MissionPhase.BUILDING);
                break;
              }
            }
            mission = info.getMissions().getGatherMission(Mission.TROOPER_TYPE);
            if (ship.isTrooperModule() && mission != null) {
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
              break;
            } else if (ship.hasBombs()
                && info.getMissions()
                    .getGatherMission(Mission.BOMBER_TYPE) != null) {
              mission = info.getMissions().getGatherMission(
                  Mission.BOMBER_TYPE);
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
              break;
            } else if (ship.getTotalMilitaryPower() > 0
                && info.getMissions()
                    .getGatherMission(Mission.ASSAULT_TYPE) != null
                && info.getMissions()
                    .getGatherMission(Mission.ASSAULT_SB_TYPE) != null) {
              int chance = 50;
              if (attitude == Attitude.MERCHANTICAL
                  || attitude == Attitude.DIPLOMATIC) {
                chance = 30;
              }
              if (attitude == Attitude.SCIENTIFIC
                  || attitude == Attitude.LOGICAL) {
                chance = 40;
              }
              if (attitude == Attitude.AGGRESSIVE
                  || attitude == Attitude.MILITARISTIC) {
                chance = 60;
              }
              if (DiceGenerator.getRandom(99) < chance) {
                mission = info.getMissions().getGatherMission(
                    Mission.ASSAULT_TYPE);
                mission.setPlanetBuilding(planet.getName());
                mission.setPhase(MissionPhase.BUILDING);
              } else {
                mission = info.getMissions().getGatherMission(
                    Mission.ASSAULT_SB_TYPE);
                mission.setPlanetBuilding(planet.getName());
                mission.setPhase(MissionPhase.BUILDING);
              }
              break;
            } else if (ship.getTotalMilitaryPower() > 0
                && info.getMissions()
                    .getGatherMission(Mission.ASSAULT_TYPE) != null) {
              mission = info.getMissions().getGatherMission(
                  Mission.ASSAULT_TYPE);
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
              break;
            } else if (ship.getTotalMilitaryPower() > 0
                && info.getMissions()
                    .getGatherMission(Mission.ASSAULT_SB_TYPE) != null) {
              mission = info.getMissions().getGatherMission(
                  Mission.ASSAULT_SB_TYPE);
              mission.setPlanetBuilding(planet.getName());
              mission.setPhase(MissionPhase.BUILDING);
              break;
            }
            mission = info.getMissions().getMission(MissionType.TRADE_FLEET,
                MissionPhase.PLANNING);
            if (mission != null && ship.isTradeShip()) {
              mission.setPhase(MissionPhase.BUILDING);
              mission.setPlanetBuilding(planet.getName());
              break;
            }
            mission = info.getMissions().getMission(
                MissionType.DIPLOMATIC_DELEGACY, MissionPhase.PLANNING);
            if (mission != null && ship.isScoutShip()) {
              Planet targetPlanet = map.getClosetPlanetFromCoordinate(
                  planet.getCoordinate(), mission.getTargetRealmName(), info);
              if (targetPlanet != null) {
                mission.setPhase(MissionPhase.BUILDING);
                mission.setPlanetBuilding(planet.getName());
                mission.setTarget(targetPlanet.getCoordinate());
                break;
              }
            }
            mission = info.getMissions().getMission(MissionType.DEPLOY_STARBASE,
                MissionPhase.PLANNING);
            if (mission != null
                && ship.getHull().getHullType() == ShipHullType.STARBASE) {
              mission.setPhase(MissionPhase.BUILDING);
              mission.setPlanetBuilding(planet.getName());
              break;
            }
            if (info.getAiAttitude() == Attitude.EXPANSIONIST
                || info.getAiAttitude() == Attitude.MERCHANTICAL
                || info.getAiAttitude() == Attitude.SCIENTIFIC) {
              mission = info.getMissions().getMission(MissionType.EXPLORE,
                  MissionPhase.PLANNING);
              if (mission != null && ship.isScoutShip()) {
                mission.setPhase(MissionPhase.BUILDING);
                mission.setPlanetBuilding(planet.getName());
                break;
              }
              mission = info.getMissions().getMissionForPlanet(
                  planet.getName(), MissionType.DEFEND);
              if (mission != null && ship.getTotalMilitaryPower() > 0
                  && mission.getPhase() == MissionPhase.PLANNING) {
                mission.setPhase(MissionPhase.BUILDING);
                break;
              }
            } else {
              mission = info.getMissions().getMissionForPlanet(
                  planet.getName(), MissionType.DEFEND);
              if (mission != null && ship.getTotalMilitaryPower() > 0
                  && mission.getPhase() == MissionPhase.PLANNING) {
                mission.setPhase(MissionPhase.BUILDING);
                break;
              }
              mission = info.getMissions().getMission(MissionType.EXPLORE,
                  MissionPhase.PLANNING);
              if (mission != null && ship.isScoutShip()) {
                mission.setPhase(MissionPhase.BUILDING);
                mission.setPlanetBuilding(planet.getName());
                break;
              }
            }
          }
          break;
        }
        total = total + listScore.get(i).intValue();
      }
    }
    if (constructionSelected
        && planet.getUnderConstruction() instanceof Building
        && needToRemoveWorst) {
      boolean isHuman = info.isHuman();
      boolean isWeakAi = false;
      if (info.getAiDifficulty() == AiDifficulty.WEAK) {
        isWeakAi = true;
      }
      if (!isHuman && isWeakAi
          && !removeWorstBuilding(map, planet, planet.getPlanetOwnerIndex(),
              attitude)) {
        // Could not remove the worst building so no selection can be
        // made
        planet.setUnderConstruction(null);
        constructionSelected = false;
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
        .getLeastLikingWithLowEspionage(info.getIntelligence());
    Planet[] planets = map.getPlanetListSeenByOther(targetRealm, info);
    if (planets.length > 0) {
      Planet target = DiceGenerator.pickRandom(planets);
      Mission mission = new Mission(MissionType.SPY_MISSION,
          MissionPhase.BUILDING, target.getCoordinate());
      mission.setTargetPlanet(target.getName());
      result = mission;
    } else {
      int maxPlayer = map.getPlayerList().getCurrentMaxRealms();
      for (int j = 0; j < maxPlayer; j++) {
        planets = map.getPlanetListSeenByOther(j, info);
        if (planets.length > 0) {
          Planet target = DiceGenerator.pickRandom(planets);
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
   * Reset all planet's worker positions 0
   * @param planet planet to reset workers on
   */
  private static void setPlanetNoWorkers(final Planet planet) {
    planet.setWorkers(Planet.FOOD_FARMERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, 0);
    planet.setWorkers(Planet.METAL_MINERS, 0);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, 0);
    planet.setWorkers(Planet.CULTURE_ARTIST, 0);
  }

  /**
   * Handle Lithorian population on planet
   * @param planet Which to handle
   * @param info Planet owner
   * @param totalResearch Total research value for whole realm
   */
  protected static void handleLithorianPopulation(final Planet planet,
      final PlayerInfo info, final int totalResearch) {
    int otherWorldResearch = totalResearch - planet.getTotalProduction(
        Planet.PRODUCTION_RESEARCH);
    int total = planet.getTotalPopulation();
    int mining = 1 + planet.getTotalProductionFromBuildings(
        Planet.PRODUCTION_METAL);
    int happiness = planet.calculateHappiness();
    if (!info.getGovernment().isImmuneToHappiness()) {
      happiness = happiness - planet.getWorkers(Planet.CULTURE_ARTIST);
    }
    int modulo = total % 2;
    int required = total / 2 - mining;
    int miners = 0;
    if (required > 0 && required < total) {
      miners = required + modulo;
      total = total - miners;
    } else if (required > 0 && required == total) {
      miners = total;
      total = 0;
    }
    int research = 0;
    int worker = 0;
    int artist = 0;
    if (total > 0 && otherWorldResearch < 1) {
      research++;
      total--;
    }
    if (total > 0) {
      worker++;
      total--;
    }
    if (total > 0 && mining == planet.getTotalPopulation() / 2) {
      miners++;
      total--;
    }
    if (!info.getGovernment().isImmuneToHappiness()
        && happiness < 0 && total > 0) {
      artist++;
      happiness++;
      total--;
    }
    do {
      if (total > 0) {
        worker++;
        total--;
      }
      if (total > 0) {
        research++;
        total--;
      }
      if (!info.getGovernment().isImmuneToHappiness()
          && happiness < 0 && total > 0) {
        artist++;
        happiness++;
        total--;
      }
    } while (total > 0);
    if (planet.getAmountMetalInGround() == 0) {
      if (info.getRace().getProductionSpeed(
          planet.getGravityType()) >= 100) {
        worker = worker + miners;
        miners = 0;
      } else {
        research = research + miners;
        miners = 0;
      }
    }
    planet.setWorkers(Planet.FOOD_FARMERS, 0);
    planet.setWorkers(Planet.PRODUCTION_WORKERS, worker);
    planet.setWorkers(Planet.METAL_MINERS, miners);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, research);
    planet.setWorkers(Planet.CULTURE_ARTIST, artist);
  }

  /**
   * Handle Non farmer workers.
   *
   * @param planet Planet which to handle
   * @param info Player owning the planet
   * @param otherWorldResearch Other world research
   * @param freePopulation Free population to assign
   * @param happiness Happiness of planet
   */
  private static void handleNonFarmers(final Planet planet,
      final PlayerInfo info, final int otherWorldResearch,
      final int freePopulation, final int happiness) {
    int happy = happiness;
    int freePop = freePopulation;
    int workers = 0;
    int miners = 0;
    int artists = 0;
    int scientist = 0;

    // Prioritize allocation of scientists if possible
    // -> when have enough people to get at least 1 research
    int popResearchSpeed = info.getRace().getResearchSpeed();
    int scientistsPerResearch = Math.max(1, 100 / popResearchSpeed);
    if (planet.getTotalProductionFromBuildings(
        Planet.PRODUCTION_RESEARCH) == 0 && freePop >= scientistsPerResearch
        && otherWorldResearch < 1) {
      scientist = scientistsPerResearch;
      freePop -= scientistsPerResearch;
    }

    int cultureSpeed = info.getRace().getCultureSpeed();
    int artistPerCulture = Math.max(1, 100 / cultureSpeed);
    int workerSpeed = info.getRace().getProductionSpeed(
        planet.getGravityType());
    int workerPerProd = 0;
    if (workerSpeed > 0) {
      workerPerProd = Math.max(1, 100 / workerSpeed);
    }
    int minerSpeed = info.getRace().getMiningSpeed(
        planet.getGravityType());
    int minerPerMetal = 0;
    if (minerSpeed > 0) {
      minerPerMetal = Math.max(1, 100 / minerSpeed);
    }

    if (freePop > 0
        && info.getRace().getProductionSpeed(planet.getGravityType()) >= 100) {
      workers++;
      freePop--;
    }

    if (freePop > 0 && happy < -1) {
      artists++;
      freePop--;
      happy++;
    }

    int part = 0;
    if (info.getRace().getMiningSpeed(planet.getGravityType()) <= 50
        && info.getRace().getProductionSpeed(planet.getGravityType()) <= 50) {
      part = freePop % 2;
      int div = freePop / 2;
      if (div == 1) {
        if (workerPerProd == 2) {
          workers = workers + div + div;
        } else if (minerPerMetal == 2 && planet.getAmountMetalInGround() > 30) {
          miners = miners + div + div;
        } else {
          artists = artists + div;
          scientist = scientist + div;
        }
      } else {
        artists = artists + div;
        scientist = scientist + div;
      }
    } else if (info.getRace().getMiningSpeed(planet.getGravityType()) <= 50) {
      part = freePop % 3;
      int div = freePop / 3;
      workers = workers + div;
      if (div == 1) {
        if (artistPerCulture == 1 && scientistsPerResearch == 1) {
          if (checkIfArtistIsAssigned(info)) {
            artists = artists + div;
          } else {
            workers = workers + div;
          }
          scientist = scientist + div;
        } else if (scientistsPerResearch == 1) {
          scientist = scientist + div + div;
        } else if (artistPerCulture == 1) {
          if (checkIfArtistIsAssigned(info)) {
            artists = artists + div + div;
          } else {
            workers = workers + div;
            artists = artists + div;
          }
        } else {
          workers = workers + div + div;
        }
      } else {
        if (checkIfArtistIsAssigned(info)) {
          artists = artists + div;
        } else {
          workers = workers + div;
        }
        scientist = scientist + div;
      }
    } else if (info.getRace().getProductionSpeed(
        planet.getGravityType()) <= 50) {
      part = freePop % 3;
      int div = freePop / 3;
      miners = miners + div;
      if (div == 1) {
        if (artistPerCulture == 1 && scientistsPerResearch == 1) {
          if (checkIfArtistIsAssigned(info)) {
            artists = artists + div;
          } else {
            miners = miners + div;
          }
          scientist = scientist + div;
        } else if (scientistsPerResearch == 1) {
          scientist = scientist + div + div;
        } else if (artistPerCulture == 1) {
          if (checkIfArtistIsAssigned(info)) {
            artists = artists + div;
            miners = miners + div;
          } else {
            miners = miners + div + div;
          }
        } else {
          miners = miners + div + div;
        }
      } else {
        if (checkIfArtistIsAssigned(info)) {
          artists = artists + div;
        } else {
          miners = miners + div;
        }
        scientist = scientist + div;
      }
    } else {
      part = freePop % 4;
      int div = freePop / 4;
      if (div == 1) {
        div = 0;
        if (scientistsPerResearch > 1) {
          part++;
        } else {
          scientist++;
        }
        if (artistPerCulture > 1) {
          part++;
        } else {
          if (checkIfArtistIsAssigned(info)) {
            artists++;
          } else {
            part++;
          }
        }
        if (minerPerMetal > 1) {
          part++;
        } else {
          miners++;
        }
        if (workerPerProd > 1) {
          part++;
        } else {
          workers++;
        }
        // Prioritize research
        if (part > 1 && scientistsPerResearch > 1) {
          scientist = scientist + 2;
          part = part - 2;
        }
        if (part > 1 && workerPerProd > 1) {
          workers = workers + 2;
          part = part - 2;
        }
        if (part > 1 && minerPerMetal > 1) {
          miners = miners + 2;
          part = part - 2;
        }
        if (part > 1 && artistPerCulture > 1) {
          artists = artists + 2;
          part = part - 2;
        }
      }
      workers = workers + div;
      artists = artists + div;
      scientist = scientist + div;
      miners = miners + div;
    }
    if (happy < -1 && part > 0) {
      artists++;
      part--;
    }
    if (part == 1) {
      if (info.getRace().getResearchSpeed() >= 100) {
        scientist++;
      } else if (info.getRace().getCultureSpeed() >= 100
          && checkIfArtistIsAssigned(info)) {
        artists++;
      } else if (info.getRace().getProductionSpeed(
          planet.getGravityType()) >= 100) {
        workers++;
      } else if (info.getRace().getMiningSpeed(
          planet.getGravityType()) >= 100) {
        miners++;
      } else {
        workers++;
      }
    } else if (part == 2) {
      if (info.getRace().getProductionSpeed(planet.getGravityType()) >= 100) {
        workers++;
      } else if (info.getRace().getResearchSpeed() >= 100) {
        scientist++;
      } else if (info.getRace().getMiningSpeed(
          planet.getGravityType()) >= 100) {
        miners++;
      } else if (info.getRace().getCultureSpeed() >= 100
          && checkIfArtistIsAssigned(info)) {
        artists++;
      } else {
        workers++;
      }
      if (info.getRace().getResearchSpeed() >= 100) {
        scientist++;
      } else if (info.getRace().getMiningSpeed(
          planet.getGravityType()) >= 100) {
        miners++;
      } else if (info.getRace().getProductionSpeed(
          planet.getGravityType()) >= 100) {
        workers++;
      } else if (info.getRace().getCultureSpeed() >= 100
          && checkIfArtistIsAssigned(info)) {
        artists++;
      } else {
        workers++;
      }
    } else {
      boolean workerAssigned = false;
      boolean minerAssigned = false;
      boolean scientistAssigned = false;
      while (part > 0) {
        if (info.getRace().getProductionSpeed(
            planet.getGravityType()) >= 100 && !workerAssigned) {
          workers++;
          workerAssigned = true;
          part--;
        } else  if (info.getRace().getMiningSpeed(
            planet.getGravityType()) >= 100 && !minerAssigned) {
          miners++;
          minerAssigned = true;
          part--;
        } else if (info.getRace().getResearchSpeed() >= 100
            && !scientistAssigned) {
          scientist++;
          scientistAssigned = true;
          part--;
        } else if (info.getRace().getCultureSpeed() >= 100
            && checkIfArtistIsAssigned(info)) {
          artists++;
          part--;
        } else {
          workerAssigned = false;
          minerAssigned = false;
          scientistAssigned = false;
        }
      }
    }
    if (planet.getAmountMetalInGround() == 0) {
      if (info.getRace().getProductionSpeed(
          planet.getGravityType()) >= 100) {
        workers = workers + miners;
        miners = 0;
      } else {
        scientist = scientist + miners;
        miners = 0;
      }
    }
    planet.setWorkers(Planet.PRODUCTION_WORKERS, workers);
    planet.setWorkers(Planet.METAL_MINERS, miners);
    planet.setWorkers(Planet.RESEARCH_SCIENTIST, scientist);
    planet.setWorkers(Planet.CULTURE_ARTIST, artists);
  }
  /**
   * Handle generic population
   * @param planet Planet to handle
   * @param info Owner of the planet
   * @param totalResearch Total research value for whole realm
   * @param state GameLengthState
   */
  protected static void handleGenericPopulation(final Planet planet,
      final PlayerInfo info, final int totalResearch,
      final GameLengthState state) {
    int otherWorldResearch = totalResearch - planet.getTotalProduction(
        Planet.PRODUCTION_RESEARCH);
    int happy = planet.calculateHappiness();
    int totalPop = planet.getTotalPopulation();
    int food = planet.getFoodProdByPlanetAndBuildings();

    if (info.getRace().hasTrait(TraitIds.RADIOSYNTHESIS)) {
      int rad = planet.getRadiationLevel().getRadiosynthesisFood();
      food += Math.min(rad, totalPop);
    }

    boolean gameStart = false;
    if (state == GameLengthState.START_GAME
        || state == GameLengthState.ELDER_HEAD_START) {
      gameStart = true;
    }
    int foodReq = totalPop * info.getRace().getFoodRequire() / 100;
    int farmersReq = foodReq - food;
    double foodMult = info.getRace().getFoodSpeed(
        planet.getGravityType()) / 100;
    boolean uselessFarmers = false;
    if (foodMult == 0) {
      // No matter what farmers aren't useful.
      farmersReq = 0;
      uselessFarmers = true;
    } else {
      farmersReq = (int) Math.round(farmersReq / foodMult);
      if (farmersReq > totalPop) {
        farmersReq = totalPop;
      }
    }
    int foodByFarmers = (int) Math.round(farmersReq * foodMult);
    boolean surplusFood = false;
    if (food + foodByFarmers > foodReq) {
      surplusFood = true;
    }
    // Races that grow and are not limited by insufficient space
    // will want to allocate one more population for farming
    if (info.getRace().getGrowthSpeed() > 0
        && totalPop < planet.getPopulationLimit()
        && farmersReq >= 0 && farmersReq < totalPop
        && !uselessFarmers
        && !surplusFood
        && !gameStart) {
      farmersReq++;
    }

    setPlanetNoWorkers(planet);

    int freePop = totalPop;
    if (farmersReq > 0) {
      planet.setWorkers(Planet.FOOD_FARMERS, farmersReq);
      freePop -= farmersReq;
    }
    handleNonFarmers(planet, info, otherWorldResearch, freePop, happy);
    if (totalPop != planet.getTotalPopulation()) {
      ErrorLogger.log("Original population: " + totalPop + " new pop: "
         + planet.getTotalPopulation());
    }
  }

  /**
   * Handle planet population positions for JUnits.
   * @param planet Planet to handle
   * @param info Player who owns the planet
   * @param totalResearch Total research value for whole realm
   */
  public static void handlePlanetPopulation(final Planet planet,
      final PlayerInfo info, final int totalResearch) {
    handlePlanetPopulation(planet, info, totalResearch,
        GameLengthState.START_GAME);
  }

  /**
   * Checks if artists should be assigned. This has different
   * behaviours for difficulty level.
   *
   * @param info Player who owns the planet
   * @return True if artist should be assigned
   */
  private static boolean checkIfArtistIsAssigned(final PlayerInfo info) {
    boolean result = false;
    boolean atWar = info.getDiplomacy().getNumberOfWar() > 0;
    boolean culturalVictory = false;
    boolean artisticRuler = false;
    boolean productiveRuler = false;
    if (info.getRuler() != null) {
      if (info.getRuler().hasPerk(Perk.ARTISTIC)) {
        artisticRuler = true;
      }
      if (info.getRuler().hasPerk(Perk.PACIFIST)) {
        artisticRuler = true;
      }
      if (info.getRuler().hasPerk(Perk.MINER)) {
        productiveRuler = true;
      }
      if (info.getRuler().hasPerk(Perk.INDUSTRIAL)) {
        productiveRuler = true;
      }
    }
    if (info.getStrategy() == WinningStrategy.CULTURAL) {
      culturalVictory = true;
    }
    if (info.getAiDifficulty() == AiDifficulty.WEAK) {
      // Always add artist, makes production lower
      result = true;
    }
    if (info.getAiDifficulty() == AiDifficulty.NORMAL && !atWar) {
      result = true;
    }
    if (info.getAiDifficulty() == AiDifficulty.CHALLENGING
        && !atWar && culturalVictory) {
      result = true;
    }
    if (artisticRuler) {
      result = true;
    }
    if (productiveRuler) {
      result = false;
    }
    return result;
  }
  /**
   * Handle generic population which do not eat food.
   * This does not have that WEAK AI feature that mechion handler has:
   * This will place 2 pops for research if that is only research available.
   *
   * @param planet Planet to handle
   * @param info Owner of the planet
   * @param totalResearch Total research value for whole realm
   */
  protected static void handleGenericPopulationNotEating(final Planet planet,
      final PlayerInfo info, final int totalResearch) {
    int otherWorldResearch = totalResearch - planet.getTotalProduction(
        Planet.PRODUCTION_RESEARCH);
    int happy = planet.calculateHappiness();
    int totalPop = planet.getTotalPopulation();

    setPlanetNoWorkers(planet);

    int freePop = totalPop;
    handleNonFarmers(planet, info, otherWorldResearch, freePop, happy);
    if (totalPop != planet.getTotalPopulation()) {
      ErrorLogger.log("Original population: " + totalPop + " new pop: "
         + planet.getTotalPopulation());
    }
  }

  /**
   * Handle planet population positions
   * @param planet Planet to handle
   * @param info Player who owns the planet
   * @param totalResearch Total research value for whole realm
   * @param state GameLengthState
   */
  public static void handlePlanetPopulation(final Planet planet,
      final PlayerInfo info, final int totalResearch,
      final GameLengthState state) {
    int population = planet.getTotalPopulation();
    if (population <= 0) {
      ErrorLogger.enabledDebugging();
      ErrorLogger.debug("Planet " + planet.getName() + " has no population."
          + " Planet is being owned by " + info.getEmpireName() + " ("
          + info.getRace().getName() + ").");
      throw new IllegalArgumentException("Population less than one!"
          + " Population: " + population);
    }
    int branch = -1;
    if (info.getRace().isLithovorian()) {
      handleLithorianPopulation(planet, info, totalResearch);
      branch = 3;
    } else if (!info.getRace().isEatingFood()) {
      handleGenericPopulationNotEating(planet, info, totalResearch);
      branch = 0;
    } else {
      // Handle races whom need something to eat and have regular research
      handleGenericPopulation(planet, info, totalResearch, state);
      branch = 5;
    }
    if (population != planet.getTotalPopulation()) {
      StringBuilder sb = new StringBuilder();
      for (Building building : planet.getBuildingList()) {
        sb.append(building.getName());
        sb.append(", ");
      }
      throw new IllegalArgumentException("Population changed original:"
          + population + " current: " + planet.getTotalPopulation()
          + " branch: " + branch + " Planet Owner: "
          + planet.getPlanetPlayerInfo().getRace().getName() + " Event: "
          + planet.getPlanetaryEvent().getName() + "Buildings: "
          + sb.toString());
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

  /** Hidden constructor */
  private PlanetHandling() {
  }
}
