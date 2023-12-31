package org.openRealmOfStars.ai.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2022 Tuomo Untinen
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

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.WinningStrategy;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.trait.TraitIds;
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

/** Scoring methods for Normal AI */
public final class DefaultScoring {

  /** Turret defense value score */
  private static final int TURRET_DEFENSE_VALUE_SCORE = 15;

  /**
  * Calculate scores for each construction. Each score is between -1 and 1000
  * @param constructions The constructions
  * @param planet The planet
  * @param info The planet info
  * @param map The star map
  * @param attitude AI's attitude
  * @param nearFleetLimit Is fleet capacity near the limit or even over it.
  * @return The calculate scores
  */
  public static int[] scoreConstructions(final Construction[] constructions,
      final Planet planet, final PlayerInfo info, final StarMap map,
      final Attitude attitude, final boolean nearFleetLimit) {
    int[] scores = new int[constructions.length];
    for (int i = 0; i < constructions.length; i++) {
      scores[i] = -1;
      // Scoring for population building projects
      // TODO: Better identification of pop-building projects
      if (constructions[i].getName().endsWith(" citizen")) {
        var prod = planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION);
        var metalProd = planet.getTotalProduction(Planet.PRODUCTION_METAL);
        final int index = map.getPlayerList().getIndex(info);
        final var realmResearch = map.getTotalProductionByPlayerPerTurn(
            Planet.PRODUCTION_RESEARCH, index);

        scores[i] = planet.getPopulationLimit() * 4
            - 2 * planet.getTotalPopulation() + 15;
        // There are not many building on the planet, build more population
        if (planet.getBuildingList().length < 4) {
          scores[i] += 5;
        }

        // No research, focus on building more population
        if (realmResearch == 0 && !info.getTechList().hasTech("Basic lab")) {
          scores[i] += 50;
        }

        // Not enough production to build pop efficiently
        if (prod < constructions[i].getProdCost() / 4) {
          scores[i] /= 2;
        }
        // Not enough metal production to build pop efficiently
        if (metalProd < constructions[i].getMetalCost() / 4) {
          scores[i] /= 2;
        }

        if (planet.getEffectiveGovernorGuide() == Planet.POPULATION_PLANET) {
          scores[i] += 40;
        }
      }

      if (constructions[i] instanceof Building) {
        Building building = (Building) constructions[i];
        scores[i] = scoreBuilding(building, planet, info, attitude,
            nearFleetLimit);
      }
      if (constructions[i] instanceof Ship) {
        Ship ship = (Ship) constructions[i];
        int score = scoreShip(ship, map.getGameLengthState(), planet);
        int time = planet.getProductionTime(constructions[i]);
        if (ship.getTotalMilitaryPower() > 0) {
          if (info.getDiplomacy().getNumberOfWar() > 0) {
            score = score + info.getDiplomacy().getNumberOfWar() * 3;
          }
          Mission defendMission = info.getMissions()
              .getMissionForPlanet(planet.getName(), MissionType.DEFEND);
          Mission attackMission = info.getMissions().getMission(
              MissionType.GATHER, MissionPhase.PLANNING);
          if (defendMission != null) {
            if (defendMission.getPhase() == MissionPhase.PLANNING) {
              score = score + ship.getTotalMilitaryPower() * 2;
              if (time < 10) {
                score = score + ship.getTotalMilitaryPower();
              }
              if (attitude == Attitude.AGGRESSIVE
                  || attitude == Attitude.MILITARISTIC) {
                score = score + 20;
              } else if (attitude == Attitude.PEACEFUL) {
                score = score - 10;
              }
            } else if (defendMission.getPhase() == MissionPhase.BUILDING) {
              score = score + ship.getTotalMilitaryPower();
              if (attitude == Attitude.AGGRESSIVE
                  || attitude == Attitude.MILITARISTIC) {
                score = score + 20;
              } else if (attitude == Attitude.PEACEFUL) {
                score = score - 10;
              }
            }
          }
          if (attackMission != null) {
            score = score + ship.getTotalMilitaryPower() * 3;
            if (time < 10) {
              score = score + ship.getTotalMilitaryPower();
            }
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
          if (ship.isPrivateeringShip()
              && (info.getGovernment() == GovernmentType.SPACE_PIRATES
                  || info.getGovernment() == GovernmentType.SYNDICATE)) {
            score = score + ship.getTotalMilitaryPower() * 2;
          }
          if (ship.isPrivateeringShip()
              && (info.getGovernment() == GovernmentType.FEUDALISM
                  || info.getGovernment() == GovernmentType.KINGDOM
                  || info.getGovernment() == GovernmentType.EMPIRE
                  || info.getGovernment() == GovernmentType.REGIME)) {
            score = score + ship.getTotalMilitaryPower();
          }
        }
        if (ship.isScoutShip()) {
          if (info.getFleets().getNumberOfFleets() == 0) {
            score = score + 30;
          }
          if (info.getMissions().getMission(MissionType.EXPLORE,
              MissionPhase.PLANNING) != null) {
            score = score + 30;
          }
          if (info.getMissions().getMission(MissionType.DIPLOMATIC_DELEGACY,
              MissionPhase.PLANNING) != null) {
            score = score + 50;
          }
          if (attitude == Attitude.EXPANSIONIST) {
            score = score + 10;
          }
        }
        if (ship.isColonyModule()) {
          // Colony ship should be built only on request
          if (planet.getTotalPopulation() > 4) {
            score = score + 30;
          }
          if (planet.getTotalPopulation() > 8) {
            score = score + 30;
          }
          if (planet.getTotalPopulation() > 12) {
            score = score + 30;
          }
          score = scoreColonyShip(score, ship, info, map, attitude, planet);
          if (planet.getTotalPopulation() == 1) {
            score = -1;
          }
        }
        if (ship.hasBombs() && info.getMissions().getGatherMission(
            Mission.BOMBER_TYPE) != null) {
          score = score + 50;
          if (time < 10) {
            score = score + 200;
          }
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
            if (time < 10) {
              score = score + 200;
            }
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
            if (info.getDiplomacy().getNumberOfWar() == 0) {
              // During peace time build more starbases.
              score = score + 20;
            }
            if (attitude == Attitude.SCIENTIFIC) {
              score = score + ship.getTotalResearchBonus() * 5;
            } else {
              score = score + ship.getTotalResearchBonus() * 3;
            }
            if (ship.getTotalResearchBonus() > 0
                && planet
                    .getEffectiveGovernorGuide() == Planet.RESEARCH_PLANET) {
              score = score + 10;
            }
            if (attitude == Attitude.DIPLOMATIC
                || attitude == Attitude.PEACEFUL) {
              score = score + ship.getTotalCultureBonus() * 5;
            } else {
              score = score + ship.getTotalCultureBonus() * 3;
            }
            if (ship.getTotalCultureBonus() > 0
                && planet
                    .getEffectiveGovernorGuide() == Planet.CULTURE_PLANET) {
              score = score + 10;
            }
            if (attitude == Attitude.MERCHANTICAL) {
              score = score + ship.getTotalCreditBonus() * 5;
            } else {
              score = score + ship.getTotalCreditBonus() * 3;
            }
            if (ship.getTotalCreditBonus() > 0
                && planet.getEffectiveGovernorGuide() == Planet.CREDIT_PLANET) {
              score = score + 10;
            }
          } else {
            score = -1;
          }
        }
        if (ship.isTradeShip()) {
          // Trade ship should be built only on request
          score = scoreTradeShip(score, ship, planet, info, map, attitude);
          if (time < 10) {
            score = score + 10;
          }
          if (info.getDiplomacy().getNumberOfWar() == 0) {
            score = score + 20;
          }
          if (info.getStrategy() == WinningStrategy.DIPLOMATIC
              || info.getStrategy() == WinningStrategy.CULTURAL) {
            score = score + 20;
          }
        }
        if (ship.isSpyShip()) {
          score = scoreSpyShip(score, ship, info, map, attitude);
          if (time < 10) {
            score = score + 10;
          }
          if (info.getDiplomacy().getNumberOfWar() == 0) {
            score = score + 20;
          }
        }
        scores[i] = score;
      }
      int time = planet.getProductionTime(constructions[i]);
      if (time == -1) {
        scores[i] = -1;
      }
      if (time > 15 && scores[i] > 0) {
        scores[i] = scores[i] / 2;
        if (constructions[i].getName()
            .equals(ConstructionFactory.MECHION_CITIZEN)) {
          scores[i] = scores[i] / 2;
        }
      }
      if (time > 25) {
        scores[i] = -1;
      }

      // Sanitize score
      if (scores[i] > PlanetHandling.MAX_SLOT_SCORE) {
        scores[i] = PlanetHandling.MAX_SLOT_SCORE;
      }
      if (scores[i] < -1) {
        scores[i] = -1;
      }

    }
    return scores;
  }

  /**
   * Score Building on certain planet and playerinfo
   * @param building Building to score
   * @param planet Planet where building is placed or about to place
   * @param info PlayerInfo who's building is about to score
   * @param attitude AI's attitude
   * @param nearFleetLimit Is Fleet capacity near the limit or even over.
   * @return Score of building, bigger is better
   */
  public static int scoreBuilding(final Building building, final Planet planet,
      final PlayerInfo info, final Attitude attitude,
      final boolean nearFleetLimit) {
    // Military score
    int score = building.getBattleBonus()
        + building.getDefenseDamage() * TURRET_DEFENSE_VALUE_SCORE;
    score = score + building.getScanRange() * 10
        + building.getScanCloakingDetection() / 2;

    // Production score
    score = score + building.getFactBonus() * 60;
    if (building.getFactBonus() > 0
        && planet.getEffectiveGovernorGuide() == Planet.MILITARY_PLANET) {
      score = score + 40;
    }

    if (planet.getAmountMetalInGround() > 30) {
      score = score + building.getMineBonus() * 40;
      if (info.getRace().getMiningSpeed(planet.getGravityType()) < 100) {
        score = score + building.getMineBonus() * 30;
      }
      if (building.getMineBonus() > 0
          && planet.getEffectiveGovernorGuide() == Planet.MILITARY_PLANET) {
        score = score + 40;
      }
    }
    int metalProd = planet.getTotalProduction(Planet.PRODUCTION_METAL);
    int prodProd = planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION);
    if (metalProd < 4 && building.getName().equals("Basic mine")) {
      score = score + 40;
    }
    if (prodProd < 4 && building.getName().equals("Basic factory")) {
      score = score + 40;
    }
    if (metalProd > prodProd) {
      score = score + building.getFactBonus() * 20;
    }
    if (metalProd < prodProd) {
      score = score + building.getMineBonus() * 20;
    }
    if (building.getName().equals("United Galaxy Tower")
        && metalProd > 5 && prodProd > 5
        && info.getStrategy() == WinningStrategy.DIPLOMATIC) {
      score += 400;
    }
    if (building.getMineBonus() > 0
        && planet.getEffectiveGovernorGuide() == Planet.POPULATION_PLANET
        && info.getRace().isLithovorian()) {
      score += 20;
    }
    score += building.getMaterialBonus() * 60;
    boolean extraFarmBonus = false;
    if (!info.getRace().isEatingFood()) {
      score += building.getFarmBonus() * -40;
    } else if (info.getRace().hasTrait(TraitIds.SLOW_GROWTH)) {
      score += building.getFarmBonus() * 50;
      extraFarmBonus = true;
    } else if (info.getRace().hasTrait(TraitIds.FIXED_GROWTH)) {
      score += building.getFarmBonus() * 20;
      extraFarmBonus = true;
    } else {
      score += building.getFarmBonus() * 40;
      extraFarmBonus = true;
    }

    if (extraFarmBonus && building.getFarmBonus() > 0) {
      if (planet.getEffectiveGovernorGuide() == Planet.POPULATION_PLANET) {
        score += 40;
      }
      if (info.getStrategy() == WinningStrategy.POPULATION) {
        score += building.getFarmBonus() * 10;
      }
    }
    if (!info.getRace().isEatingFood()
        && building.getFarmBonus() > 0
        && planet.getTotalPopulation() == planet.getPopulationLimit()
        && planet.calculateSurPlusFood() >= 0) {
      // Planet population is already max out and food is enough.
      // No need for building more food production.
      // ... Or the race just does not eat normal food.
      // XXX: Possible logic error, farms are discarded from scoring lower
      score = 0;
    }
    if (info.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
      score += building.getReseBonus() * 80;
      score += building.getCultBonus() * 60;
    } else {
      score += building.getReseBonus() * 60;
      score += building.getCultBonus() * 40;
    }
    if (building.getReseBonus() > 0
        && planet.getEffectiveGovernorGuide() == Planet.RESEARCH_PLANET) {
      score = score + 50;
    }
    if (building.getCultBonus() > 0
        && planet.getEffectiveGovernorGuide() == Planet.CULTURE_PLANET) {
      score = score + 50;
    }
    if (building.getCultBonus() > 0
        && info.getStrategy() == WinningStrategy.CULTURAL) {
      score = score + building.getCultBonus() * 20;
    }
    if (planet.getMaintenanceCost() >= planet
        .getTotalProduction(Planet.PRODUCTION_CREDITS)) {
      // Planet has much expenses so build credit production is important
      score = score + building.getCredBonus() * 80;
    } else {
      score = score + building.getCredBonus() * 50;
    }
    if (building.getCredBonus() > 0
        && planet.getEffectiveGovernorGuide() == Planet.CREDIT_PLANET) {
      score = score + 50;
    }
    score = score + building.getRecycleBonus();
    if (attitude == Attitude.DIPLOMATIC) {
      score = score + building.getCultBonus() * 15;
    }
    if (attitude == Attitude.SCIENTIFIC) {
      score = score + building.getReseBonus() * 15;
      if (building.getScientificAchievement()) {
        score = score + 50;
      }
    }
    if (building.getScientificAchievement()
        && info.getStrategy() == WinningStrategy.SCIENCE
        && info.getBestPlanetForTechWorld() == planet) {
      score = score + 500;
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

    GovernmentType government = info.getGovernment();
    if (!government.isImmuneToHappiness() && building.getHappiness() > 0) {
      int happyness = planet.calculateHappiness();
      int mult = 10 - (happyness - 1) * 5;
      if (mult < 10) {
        mult = 10;
      }
      if (mult > 40) {
        mult = 40;
      }
      score = score + building.getHappiness() * mult;

    }
    if (building.getFleetCapacityBonus() > 0) {
      score = score + 30 * building.getFleetCapacityBonus();
      if (nearFleetLimit) {
        score = score + 50;
      }
    }

    score = score - (int) Math.round(building.getMaintenanceCost() * 10);
    // High cost drops the value
    score = score - building.getMetalCost() / 10;
    score = score - building.getProdCost() / 10;

    int production = planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION);
    int metal = planet.getTotalProduction(Planet.PRODUCTION_METAL);
    if (building.getFactBonus() > 0 && production >= metal * 3
        && production > 0) {
      // Planet has enough production production
      score = -1;
    }
    if (building.getMineBonus() > 0 && metal >= production * 3
        && metal > 0) {
      // Planet has enough metal production
      score = -1;
    }
    if (!info.getRace().isEatingFood()
        && building.getType() == BuildingType.FARM) {
      // Races that do not eat normal food do not build farms
      score = -1;
    }
    int time = planet.getProductionTime(building);
    if (time == -1) {
      score = -1;
    }
    if (time > 15) {
      score = score / 2;
    }
    if (time > 25) {
      score = -1;
    }
    if (building.getName().equals("United Galaxy Tower")) {
      if (attitude == Attitude.DIPLOMATIC || attitude == Attitude.PEACEFUL) {
        score = score + 20;
      }
      if (attitude == Attitude.LOGICAL) {
        score = score + 15;
      }
      if (info.getStrategy() == WinningStrategy.DIPLOMATIC) {
        score = score + 80;
      }
    }
    if (info.getStrategy() == WinningStrategy.CULTURAL) {
      if (building.isBroadcaster() && !planet.broadcaster()) {
        score = score + 120;
      }
      score = score + building.getCultBonus() * 40;
    }
    if (building.getName().equals("Basic factory")
        && info.getTechList().hasTech(TechType.Improvements,
            "Advanced factory")
        && production > 4) {
      score = -1;
    }
    if (building.getName().equals("Basic mine")
        && info.getTechList().hasTech(TechType.Improvements,
            "Advanced mine")
        && metal > 4) {
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
        score = PlanetHandling.MAX_SLOT_SCORE;
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
   * Score ship, scoring also depends on missions under planning
   * @param ship Ship to score
   * @param state Current gameLengthState
   * @param planet Planet which is about to build something
   * @return Ship score
   */
  public static int scoreShip(final Ship ship, final GameLengthState state,
      final Planet planet) {
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
    if (planet.getEffectiveGovernorGuide() == Planet.MILITARY_PLANET) {
      militaryFocus++;
    }
    score = score + ship.getTotalMilitaryPower() * militaryFocus;
    // High cost drops the value
    score = score - ship.getMetalCost() / 10;
    score = score - ship.getProdCost() / 10;
    if (ship.getHull().getHullType() == ShipHullType.ORBITAL) {
      if (planet.getPlanetPlayerInfo().getRace() == SpaceRace.ALTEIRIANS) {
        // Alteirians should built orbitals more frequently
        score += 50;
      }
      score = score + ship.getHull().getSize().getMass() * 5;
      if (ship.getHull().getName().startsWith("Minor orbital")) {
        // Minor orbital should not be built.
        score = 0;
      }
      if (planet.getOrbital() != null
          && planet.getOrbital().getHull().getSize().getMass() > ship.getHull()
              .getSize().getMass()) {
        // Planet has orbital which is already bigger.
        score = 0;
      }
      if (planet.getOrbital() != null
          && planet.getOrbital().getTotalMilitaryPower() > ship
              .getTotalMilitaryPower()) {
        // Planet has orbital is better
        score = 0;
      }
      if (planet.getOrbital() != null
          && planet.getOrbital().getName().equals(ship.getName())) {
        // Don't rebuild same orbital over and over.
        score = 0;
      }
    }
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
        if (planet.getEffectiveGovernorGuide() == Planet.CULTURE_PLANET) {
          score = score + 10;
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
      int maxPlayers = map.getPlayerList().getCurrentMaxRealms();
      for (int i = 0; i < maxPlayers; i++) {
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
   * @param planet Player building colonyship
   * @return Score of the colony ship.
   */
  protected static int scoreColonyShip(final int preScore, final Ship ship,
      final PlayerInfo info, final StarMap map, final Attitude attitude,
      final Planet planet) {
    int score = preScore;
    if (ship.isColonyModule()) {
      // Colony ship should be built only on request
      Mission mission = info.getMissions().getBestColonizeMissionPlanning(map,
          info, planet.getCoordinate());
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
        score = score + info.getMissions().getNumberOfMissionTypes(
            MissionType.COLONIZE, MissionPhase.PLANNING) * 5;
        if (planet.getEffectiveGovernorGuide() == Planet.POPULATION_PLANET) {
          score = score + 40;
        }
      } else {
        score = -1;
      }
    }
    return score;
  }

  /** Hidden constructor */
  private DefaultScoring() {
  }
}