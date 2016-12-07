package org.openRealmOfStars.AI.PlanetHandling;

import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;
import org.openRealmOfStars.utilities.DiceGenerator;

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
   * Score for one available slot
   */
  private static final int ONE_SLOT_SCORE = 200;

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
      handlePlanetPopulation(planet, info);
      if (credit < 0) {
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
        Building[] buildings = planet.getBuildingList();
        Construction[] constructions = planet.getProductionList();
        boolean constructionSelected = false;
        int gotFactory = gotBuildings(
            new String[] {"Basic factory", "Advanced factory"}, buildings);
        int gotLabs = gotBuildings(
            new String[] {"Basic lab", "Advanced laboratory"}, buildings);
        int gotFarms = gotBuildings(
            new String[] {"Basic farm", "Advanced farm"}, buildings);
        int gotMines = gotBuildings(
            new String[] {"Basic mine", "Advanced mine"}, buildings);
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
            && planet.getTotalPopulation() < 5) {
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
              info, map);
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
      }
    }
  }

  /**
   * Score Building on certain planet and playerinfo
   * @param building Building to score
   * @param planet Planet where building is placed or about to place
   * @param info PlayerInfo who's building is about to score
   * @return Score of building, bigger is better
   */
  public static int scoreBuilding(final Building building, final Planet planet,
      final PlayerInfo info) {
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
    if (info.getRace() == SpaceRace.CENTAURS) {
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

    score = score / (planet.howManyBuildings(building.getName()) + 1);

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
   * Handle construction by using scoring
   * @param constructions The constructions
   * @param planet The planet
   * @param info The planet info
   * @param map The star map
   * @return boolean true if construction has been selected
   */
  private static boolean handleConstructions(final Construction[] constructions,
      final Planet planet, final PlayerInfo info, final StarMap map) {
    boolean constructionSelected = false;
    int[] scores = scoreConstructions(constructions, planet, info, map);
    int highest = -1;
    int value = -1;
    boolean over400 = false;
    int minimum = 0;
    int freeSlot = planet.getGroundSize() - planet.getUsedPlanetSize();
    switch (freeSlot) {
    case 0:
      minimum = MAX_SLOT_SCORE;
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
              && planet.getTotalPopulation() < 20) {
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
          }
          break;
        }
        total = total + listScore.get(i).intValue();
      }
    }
    return constructionSelected;
  }

  /**
   * Calculate scores for each construction. Each score is between -1 and 1000
   * @param constructions The constructions
   * @param planet The planet
   * @param info The planet info
   * @param map The star map
   * @return The calculate scores
   */
  private static int[] scoreConstructions(final Construction[] constructions,
      final Planet planet, final PlayerInfo info, final StarMap map) {
    int[] scores = new int[constructions.length];
    for (int i = 0; i < constructions.length; i++) {
      scores[i] = -1;
      if (constructions[i].getName()
          .equals(ConstructionFactory.MECHION_CITIZEN)) {
        scores[i] = planet.getAmountMetalInGround() / 100
            - 10 * planet.getTotalPopulation();
        // Does not take a planet space
        scores[i] = scores[i] + 20;

      }
      if (constructions[i] instanceof Building) {
        Building building = (Building) constructions[i];
        scores[i] = scoreBuilding(building, planet, info);
      }
      if (constructions[i] instanceof Ship) {
        Ship ship = (Ship) constructions[i];
        // Does not take a planet space
        scores[i] = 20;
        scores[i] = scores[i] + ship.getTotalMilitaryPower() * 2;
        // High cost drops the value
        scores[i] = scores[i] - ship.getMetalCost() / 10;
        scores[i] = scores[i] - ship.getProdCost() / 10;
        if (ship.getTotalMilitaryPower() > 0) {
          Mission mission = info.getMissions()
              .getMissionForPlanet(planet.getName(), MissionType.DEFEND);
          if (mission != null) {
            if (mission.getPhase() == MissionPhase.PLANNING) {
              scores[i] = scores[i] + ship.getTotalMilitaryPower() * 2;
            } else if (mission.getPhase() == MissionPhase.BUILDING) {
              scores[i] = scores[i] + ship.getTotalMilitaryPower();
            }
            mission = info.getMissions().getMission(MissionType.ATTACK,
                MissionPhase.PLANNING);
            if (mission != null && ship.hasBombs()) {
              scores[i] = scores[i] + 30;
            }

          } else {
            mission = info.getMissions().getMission(MissionType.ATTACK,
                MissionPhase.PLANNING);
            if (mission != null) {
              scores[i] = scores[i] + ship.getTotalMilitaryPower() * 2;
              if (ship.hasBombs()) {
                scores[i] = scores[i] + 30;
              }
            }
          }

        }
        if (ship.isColonyModule()) {
          // Colony ship should be built only on request
          Mission mission = info.getMissions().getMission(MissionType.COLONIZE,
              MissionPhase.PLANNING);
          if (mission != null) {
            Planet colonPlanet = map.getPlanetByCoordinate(mission.getX(),
                mission.getY());
            int score = (colonPlanet.getGroundSize() - 7) * 3
                + colonPlanet.getAmountMetalInGround() / 400;
            score = score + info.getRace().getMaxRad()
                - colonPlanet.getRadiationLevel();
            scores[i] = scores[i] + score;
          } else {
            scores[i] = -1;
          }
        }
        if (ship.isTrooperModule()) {
          // Trooper ship should be built only on request
          Mission mission = info.getMissions().getMission(MissionType.ATTACK,
              MissionPhase.PLANNING);
          if (mission != null) {
            Planet colonPlanet = map.getPlanetByCoordinate(mission.getX(),
                mission.getY());
            int score = (colonPlanet.getGroundSize() - 7) * 3
                + colonPlanet.getAmountMetalInGround() / 400;
            score = score + info.getRace().getMaxRad()
                - colonPlanet.getRadiationLevel();
            scores[i] = scores[i] + score;
          } else {
            scores[i] = -1;
          }
        }
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
  private static void handlePlanetPopulation(final Planet planet,
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
          prodAdd = 1;
          metalAdd = 3;
        }
        planet.setWorkers(Planet.METAL_MINERS, quarter + metalAdd);
        planet.setWorkers(Planet.PRODUCTION_WORKERS, quarter + prodAdd);
        if (quarter % 2 == 1) {
          reseAdd = 1;
          cultAdd = -1;
        }
        planet.setWorkers(Planet.RESEARCH_SCIENTIST, quarter + reseAdd);
        planet.setWorkers(Planet.CULTURE_ARTIST, quarter + cultAdd);
      } else {
        switch (total) {
        case 1: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          break;
        }
        case 2: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 1);
          break;
        }
        case 3: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          break;
        }
        case 4: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 1);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          break;
        }
        case 5: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
          planet.setWorkers(Planet.METAL_MINERS, 2);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          break;
        }
        case 6: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 2);
          planet.setWorkers(Planet.METAL_MINERS, 2);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
          break;
        }
        case 7: {
          planet.setWorkers(Planet.PRODUCTION_WORKERS, 3);
          planet.setWorkers(Planet.METAL_MINERS, 2);
          planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
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
      // Handle races whom need something to eat
      int food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
      int total = planet.getTotalPopulation();
      int foodReq = total * info.getRace().getFoodRequire() / 100;
      food = food - foodReq;
      if (food > 2) {
        if (info.getRace() == SpaceRace.GREYANS) {
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
