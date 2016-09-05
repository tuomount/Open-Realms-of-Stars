package org.openRealmOfStars.AI.PlanetHandling;

import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
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

public class PlanetHandling {

  /**
   * AI player handling for a single planet, what to build
   * and how to set population work
   * @param StarMap Star Map
   * @param planet Planet to handle
   * @param index Player Index;
   */
  public static void handlePlanet(StarMap map, Planet planet, int index) {
    int credit = map.getTotalProductionByPlayerPerTurn(Planet.PRODUCTION_CREDITS,
        index);
    PlayerInfo info = map.getPlayerByIndex(index);
    if (info != null) {
      handlePlanetPopulation(planet, info);
      if (credit < 0) {
        planet.setTax(planet.getTax()+1);
      }
    }
    ArrayList<Message> msgs = info.getMsgList().getFullList();
    boolean changeConstruction = false;
    for (Message msg : msgs) {
      if (msg.getType() == MessageType.CONSTRUCTION &&
          msg.getMatchByString().equals(planet.getName())) {
        changeConstruction = true;        
      }
    }
    if (changeConstruction || planet.getUnderConstruction() == null) {
      Building[] buildings = planet.getBuildingList();
      Construction[] constructions = planet.getProductionList();
      boolean constructionSelected = false;
      int gotFactory = gotBuildings(
          new String[] {"Basic factory","Advanced factory"}, buildings);
      int gotLabs = gotBuildings(
          new String[] {"Basic lab","Advanced laboratory"}, buildings);
      int gotFarms = gotBuildings(
          new String[] {"Basic farm","Advanced farm"}, buildings);
      int gotMines = gotBuildings(
          new String[] {"Basic mine","Advanced mine"}, buildings);
      int gotSpacePort = gotBuildings(
          new String[] {"Space port"}, buildings);
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
      if (gotFarms == -1 && !constructionSelected &&
          info.getRace() != SpaceRace.MECHIONS) {
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
      if (!constructionSelected && info.getRace() == SpaceRace.MECHIONS &&
          planet.getTotalPopulation() < 5) {
        int i = getConstruction(ConstructionFactory.MECHION_CITIZEN, constructions);
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
        Mission mission = info.getMissions().getMissionForPlanet(planet.getName(), MissionType.DEFEND);
        if (mission == null) {
          mission = new Mission(MissionType.DEFEND, MissionPhase.PLANNING, planet.getX(), planet.getY());
          mission.setFleetName("Defender");
          mission.setPlanetBuilding(planet.getName());
          info.getMissions().add(mission);
        }
      }
      if (!constructionSelected) {
        int[] scores = scoreConstructions(constructions,planet, info,map);
        int highest = -1;
        int value = -1;
        boolean over400=false;
        int minimum = 0;
        int freeSlot = planet.getGroundSize()-planet.getUsedPlanetSize();
        switch (freeSlot) {
        case 0: minimum = 1000; break;
        case 1: minimum = 200; break;
        case 2: minimum = 120; break;
        case 3: minimum = 80; break;
        case 4: minimum = 40; break;
        case 5: minimum = 20; break;
        case 6: minimum = 10; break;
        default: minimum = 0; break;
        }
        for (int i=0;i<scores.length;i++) {
          if (scores[i] > value) {
            value = scores[i];
            highest = i;
          }
          if (scores[i] > 399) {
            over400 =true;
          }
        }
        if (highest == value) {
          if (highest != -1) {
            planet.setUnderConstruction(constructions[highest]);
            constructionSelected = true;
          }
        } else if (over400) {
          ArrayList<Construction> list = new ArrayList<>();
          for (int i=0;i<scores.length;i++) {
            if (scores[i] > 399) {
              list.add(constructions[i]);
            }
          }
          int rand = DiceGenerator.getRandom(list.size()-1);
          planet.setUnderConstruction(list.get(rand));
          constructionSelected = true;
        } else {
          ArrayList<Construction> list = new ArrayList<>();
          ArrayList<Integer> listScore = new ArrayList<>();
          int sum = 0;
          for (int i=0;i<scores.length;i++) {
            if (scores[i] >= minimum) {
              list.add(constructions[i]);
              listScore.add(Integer.valueOf(scores[i]));
              sum = sum+scores[i];
            } else {
              if (constructions[i] instanceof Ship && freeSlot < 4) {
                list.add(constructions[i]);
                listScore.add(Integer.valueOf(scores[i]));
                sum = sum+scores[i];
              } else if (constructions[i].getName().equals(ConstructionFactory.MECHION_CITIZEN)
                  && freeSlot < 3 && planet.getTotalPopulation() <20) {
                list.add(constructions[i]);
                listScore.add(Integer.valueOf(scores[i]));
                sum = sum+scores[i];
              }
            }
          }
          int rand = DiceGenerator.getRandom(sum);
          int total = 0;
          for (int i=0;i<listScore.size();i++) {
            if (rand < total+listScore.get(i).intValue()) {
              Construction cons = list.get(i);
              planet.setUnderConstruction(cons);
              constructionSelected = true;
              if (cons instanceof Ship) {
                Ship ship = (Ship) cons;
                Mission mission = info.getMissions()
                    .getMissionForPlanet(planet.getName(), MissionType.DEFEND);
                if (mission != null && ship.getTotalMilitaryPower()>0) {
                  if (mission.getPhase() == MissionPhase.PLANNING) {
                    mission.setPhase(MissionPhase.BUILDING);
                  }
                }
                mission = info.getMissions().getMission(MissionType.COLONIZE, MissionPhase.PLANNING);
                if (mission != null && ship.isColonyModule()) {
                  mission.setPhase(MissionPhase.BUILDING);
                  mission.setPlanetBuilding(planet.getName());
                }
              }
              break;
            }
            total = total+listScore.get(i).intValue();
          }

        }
      }
      if (!constructionSelected) {
        // Nothing to select to let's select culture or credit
        int i = getConstruction(ConstructionFactory.EXTRA_CREDIT, constructions);
        int j = getConstruction(ConstructionFactory.EXTRA_CULTURE, constructions);
        if (i != -1 && j != -1) {
          if (DiceGenerator.getRandom(1)==0) {
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
  
  /**
   * Calculate scores for each construction. Each score is between -1 and 1000
   * @param constructions
   * @param planet
   * @param info
   * @return
   */
  private static int[] scoreConstructions(Construction[] constructions, 
      Planet planet, PlayerInfo info, StarMap map) {
    int[] scores = new int[constructions.length];
    for (int i=0;i<constructions.length;i++) {
      scores[i] = -1;
      if (constructions[i].getName().equals(ConstructionFactory.MECHION_CITIZEN)) {
        scores[i] = planet.getAmountMetalInGround()/100 -10*planet.getTotalPopulation();
        // Does not take a planet space
        scores[i] = scores[i]+20;

      }
      if (constructions[i] instanceof Building) {
        Building building = (Building) constructions[i];
        // Military score
        scores[i] = building.getBattleBonus()+building.getDefenseDamage()*15;
        scores[i] = scores[i]+building.getScanRange()*10+building.getScanCloakingDetection()/4;
        if (info.getRace() == SpaceRace.SPORKS && building.getType() == BuildingType.MILITARY) {
          scores[i] = scores[i] +15;
        }

        // Production score
        scores[i] = scores[i]+building.getFactBonus()*60;
        scores[i] = scores[i]+building.getMineBonus()*planet.getAmountMetalInGround()/120;
        if (info.getRace() == SpaceRace.CENTAURS) {
          scores[i] = scores[i]+building.getFarmBonus()*50;
        } else if (info.getRace() != SpaceRace.MECHIONS) {
          scores[i] = scores[i]+building.getFarmBonus()*40;
        } else {
          scores[i] = scores[i]-building.getFarmBonus()*40;
        }
        if (info.getRace() == SpaceRace.MECHIONS) {
          scores[i] = scores[i]+building.getReseBonus()*80;
          scores[i] = scores[i]+building.getCultBonus()*60;
        } else {
          scores[i] = scores[i]+building.getReseBonus()*60;
          scores[i] = scores[i]+building.getCultBonus()*40;
        }
        if (planet.getMaintenanceCost() >= planet.getTotalProduction(Planet.PRODUCTION_CREDITS)) {
          // Planet has much expenses so build credit production is important
          scores[i] = scores[i]+building.getCredBonus()*80;
        } else {
          scores[i] = scores[i]+building.getCredBonus()*50;
        }
        scores[i] = scores[i]+building.getRecycleBonus();
        
        scores[i] = scores[i]-(int) Math.round(building.getMaintenanceCost()*10);
        // High cost drops the value
        scores[i] = scores[i]-building.getMetalCost()/10;
        scores[i] = scores[i]-building.getProdCost()/10;

        if (info.getRace() == SpaceRace.GREYANS && building.getType() == BuildingType.RESEARCH) {
          scores[i] = scores[i] +15;
        }

        if (info.getRace() == SpaceRace.MECHIONS && building.getType() == BuildingType.FARM) {
          // Mechions do not build farms
          scores[i] = -1;
        }
        
        scores[i] = scores[i] / (planet.howManyBuildings(building.getName())+1);

        if (planet.exceedRadiation()) {
          if (building.getName().equals("Radiation dampener") ||
              building.getName().equals("Radiation well")) {
            // Radiation level is high so these are in high priority
            scores[i] = 1000;
          }
        } else {
          if (building.getName().equals("Radiation dampener") ||
              building.getName().equals("Radiation well")) {
            // Radiation level is not high so never building these
            scores[i] = -1;
          }

        }
        
      }
      if (constructions[i] instanceof Ship) {
        Ship ship = (Ship) constructions[i];
        // Does not take a planet space
        scores[i] = 20;
        scores[i] = scores[i]+ship.getTotalMilitaryPower()*2;
        // High cost drops the value
        scores[i] = scores[i]-ship.getMetalCost()/10;
        scores[i] = scores[i]-ship.getProdCost()/10;
        if (ship.getTotalMilitaryPower() > 0) {
          Mission mission = info.getMissions().
              getMissionForPlanet(planet.getName(), MissionType.DEFEND);
          if (mission != null) {
            if (mission.getPhase() == MissionPhase.PLANNING) {
              scores[i] = scores[i]+ship.getTotalMilitaryPower()*2;
            } else if (mission.getPhase() == MissionPhase.BUILDING) {
              scores[i] = scores[i]+ship.getTotalMilitaryPower();
            }
          }
        }
        if (ship.isColonyModule()) {
          // Colony ship should be built only on request
          Mission mission = info.getMissions().
              getMission(MissionType.COLONIZE, MissionPhase.PLANNING);
          if (mission != null) {
            Planet colonPlanet = map.getPlanetByCoordinate(mission.getX(), mission.getY());
            int score = (colonPlanet.getGroundSize()-7)*3+colonPlanet.getAmountMetalInGround()/400;
            score = score +info.getRace().getMaxRad()-colonPlanet.getRadiationLevel();
            scores[i] = scores[i] +score;
          } else {
            scores[i] = -1;
          }
        }
      }

      
      // Sanitize score
      if (scores[i] > 1000) {
        scores[i] = 1000;
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
  private static void handlePlanetPopulation(Planet planet, PlayerInfo info) {
    if (info.getRace()==SpaceRace.MECHIONS) {
      int total = planet.getTotalPopulation();
      int quarter = total /4;
      int modulo = total %4;
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
        planet.setWorkers(Planet.METAL_MINERS, quarter+metalAdd);
        planet.setWorkers(Planet.PRODUCTION_WORKERS, quarter+prodAdd);
        if (quarter % 2==1) {
          reseAdd = 1;
          cultAdd = -1;
        }
        planet.setWorkers(Planet.RESEARCH_SCIENTIST, quarter+reseAdd);
        planet.setWorkers(Planet.CULTURE_ARTIST, quarter+cultAdd);
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
        }
      }
    } else {
      // Handle races whom need something to eat
      int food = planet.getTotalProduction(Planet.PRODUCTION_FOOD);
      int total = planet.getTotalPopulation();
      int foodReq = total *info.getRace().getFoodRequire()/100;
      food = food-foodReq;
      if (food > 2) {
        if (info.getRace() == SpaceRace.GREYANS) {
          if (planet.getWorkers(Planet.RESEARCH_SCIENTIST) <= 
              planet.getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.RESEARCH_SCIENTIST);
          } else if (planet.getWorkers(Planet.METAL_MINERS)*2 >= 
              planet.getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.METAL_MINERS);
          } else if (planet.getWorkers(Planet.CULTURE_ARTIST) == 0) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.CULTURE_ARTIST);
          } else {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.PRODUCTION_WORKERS);
          }
        } else {
          if (planet.getWorkers(Planet.RESEARCH_SCIENTIST)*2 >= 
              planet.getWorkers(Planet.PRODUCTION_WORKERS)) {
            planet.moveWorker(Planet.FOOD_FARMERS, Planet.RESEARCH_SCIENTIST);
          } else if (planet.getWorkers(Planet.METAL_MINERS)*2 >= 
              planet.getWorkers(Planet.PRODUCTION_WORKERS)) {
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
  private static int getConstruction(String name, Construction[] list) {
    for (int i=0;i<list.length;i++) {
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
  private static int gotBuildings(String[] names, Building[] buildings) {
    int result = -1;
    for (int i=0;i<buildings.length;i++) {
      for (int j=0;j<names.length;j++) {
        if (buildings[i].getName().equals(names[j]) && result < j) {
          result = j;
        }
      }
    }
    return result;
  }
}
