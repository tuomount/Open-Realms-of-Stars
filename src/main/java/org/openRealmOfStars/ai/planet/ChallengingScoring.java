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
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;

/** Scoring methods for Challenging AI */
public final class ChallengingScoring {
  /**
   * Calculate scores for each construction. Each score is between -1 and 1000.
   * This will try to give one the constructions the highest score.
   * @param constructions The constructions
   * @param planet The planet
   * @param info The planet info
   * @param map The star map
   * @param attitude AI's attitude
   * @param nearFleetLimit Is fleet capacity near the limit or even over it.
   * @return The calculate scores
   */
  public static int[] scoreConstructions(
      final Construction[] constructions, final Planet planet,
      final PlayerInfo info, final StarMap map, final Attitude attitude,
      final boolean nearFleetLimit) {
    int[] scores = new int[constructions.length];
    int metalProd = planet.getTotalProduction(Planet.PRODUCTION_METAL);
    int prod = planet.getTotalProduction(Planet.PRODUCTION_PRODUCTION);
    int research = planet.getTotalProduction(Planet.PRODUCTION_RESEARCH);
    int food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
    int credit = planet.getTotalProduction(Planet.PRODUCTION_CREDITS);
    int culture = planet.getTotalProduction(Planet.PRODUCTION_CULTURE);

    final int playerIndex = map.getPlayerList().getIndex(info);
    final var realmResearch = map.getTotalProductionByPlayerPerTurn(
        Planet.PRODUCTION_RESEARCH, playerIndex);
    for (int i = 0; i < constructions.length; i++) {
      scores[i] = -1;
      // Try scoring population building projects
      scores[i] = scorePopBuilding(constructions[i], planet, info, prod,
          metalProd, realmResearch);

      if (constructions[i] instanceof Building) {
        Building building = (Building) constructions[i];
        scores[i] = DefaultScoring.scoreBuilding(building, planet, info,
            attitude,
            nearFleetLimit);

        if (building.getName().equals("Basic factory") && prod < 5) {
          scores[i] = scores[i] + (50 - prod * 10);
          if (!planet.hasCertainBuilding("Basic factory")) {
            scores[i] = scores[i] + 10;
          }
        }
        if (building.getName().equals("Basic mine") && metalProd < 5
            && planet.getAmountMetalInGround() > 0) {
          scores[i] = scores[i] + (40 - metalProd * 10);
          if (!planet.hasCertainBuilding("Basic factory")) {
            scores[i] = scores[i] - 15;
          }
          if (!planet.hasCertainBuilding("Basic mine")) {
            scores[i] = scores[i] + 5;
          }
        }
        if (building.getName().equals("Basic lab")) {
          if (prod > 2 && metalProd > 2 && research < 2) {
            scores[i] = scores[i] + (20 - research * 10);
          }
          int index = map.getPlayerList().getIndex(info);
          int totalResearch = map.getTotalProductionByPlayerPerTurn(
              Planet.PRODUCTION_RESEARCH, index);
          if (totalResearch == 0) {
            scores[i] = scores[i] + 40;
          }
          if (totalResearch <= 1 && prod >= 3 && metalProd >= 2) {
            scores[i] = scores[i] + 40;
          }
        }
        if (building.getName().equals("Basic farm")) {
          if (info.getRace().getFoodRequire() == 100
              && planet.getTotalPopulation() < 4
              && prod > 2 && metalProd > 2 && food < 4) {
            scores[i] = scores[i] + (40 - planet.getTotalPopulation() * 10);
          }
          if (info.getRace().getFoodRequire() == 100
              && planet.getTotalPopulation() < 5
              && info.areLeadersDead()) {
            scores[i] = scores[i] + 10;
          }
        }
        if (credit < 0 && building.getCredBonus() > 0
            && prod > 4 && metalProd > 4) {
          scores[i] = scores[i] + building.getCredBonus() * 5;
        }
        if (culture < 1 && info.getStrategy() == WinningStrategy.CULTURAL
            && prod > 4 && metalProd > 4) {
          scores[i] = scores[i] + building.getCultBonus() * 5;
        }
        if (info.getStrategy() == WinningStrategy.DIPLOMATIC
            && prod > 4 && metalProd > 4
            && building.getName().equals("United Galaxy Tower")) {
          scores[i] = scores[i] + 20;
        }
      }
      if (constructions[i] instanceof Ship) {
        Ship ship = (Ship) constructions[i];
        int score = DefaultScoring.scoreShip(ship, map.getGameLengthState(),
            planet);
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
              && attitude == Attitude.BACKSTABBING) {
            score += ship.getTotalMilitaryPower();
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
          score = DefaultScoring.scoreColonyShip(score, ship, info, map,
              attitude, planet);
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
          score = DefaultScoring.scoreTradeShip(score, ship, planet, info, map,
              attitude);
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
          score = DefaultScoring.scoreSpyShip(score, ship, info, map,
              attitude);
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
   * Score population building project
   * @param construction Construction
   * @param planet Planet
   * @param info PlayerInfo
   * @param prod int
   * @param metalProd int
   * @param realmResearch int
   * @return score
   */
  private static int scorePopBuilding(final Construction construction,
      final Planet planet, final PlayerInfo info, final int prod,
      final int metalProd, final int realmResearch) {
    var result = -1;
    // TODO: Better identification of pop-building projects
    if (!construction.getName().endsWith(" citizen")) {
      return result;
    }

    result = planet.getPopulationLimit() * 4 - 2 * planet.getTotalPopulation();

    // There is not much population, build more
    if (planet.getTotalPopulation() < 4) {
      result += 40;
    }
    // There are not many building on the planet, build less population
    if (planet.getBuildingList().length < 4) {
      result -= 15;
    }

    // No research, focus on building more population
    if (realmResearch == 0 && !info.getTechList().hasTech("Basic lab")) {
      result += 50;
    }

    // Not enough production to build pop efficiently
    if (prod < construction.getProdCost() / 4) {
      result /= 3;
    }
    // Not enough metal production to build pop efficiently
    if (metalProd < construction.getMetalCost() / 4) {
      result /= 3;
    }

    if (planet.getEffectiveGovernorGuide() == Planet.POPULATION_PLANET) {
      result += 40;
    }

    return result;
  }

  /** Hidden constructor */
  private ChallengingScoring() {
  }
}
