package org.openRealmOfStars.AI.PlanetHandling;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;

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
    Building[] buildings = planet.getBuildingList();
    Construction[] constructions = planet.getProductionList();
    int gotFactory = gotBuildings(
        new String[] {"Basic factory","Advanced factory"}, buildings);
    int gotLabs = gotBuildings(
        new String[] {"Basic lab","Advanced laboratory"}, buildings);
    int gotFarms = gotBuildings(
        new String[] {"Basic farm","Advanced farm"}, buildings);
    int gotMines = gotBuildings(
        new String[] {"Basic mine","Advanced mine"}, buildings);
    if (gotFactory == -1) {
      // No factories at all
      int i = getConstruction("Advanced factory", constructions);
      if (i != -1) {
        planet.setUnderConstruction(constructions[i]);
      } else {
        i = getConstruction("Basic factory", constructions);
        if (i != -1) {
          planet.setUnderConstruction(constructions[i]);
        }
      }
    }
    if (gotLabs == -1) {
      // No labs at all
      int i = getConstruction("Advanced laboratory", constructions);
      if (i != -1) {
        planet.setUnderConstruction(constructions[i]);
      } else {
        i = getConstruction("Basic lab", constructions);
        if (i != -1) {
          planet.setUnderConstruction(constructions[i]);
        }
      }
    }
    if (gotFarms == -1) {
      // No farms at all
      int i = getConstruction("Advanced farm", constructions);
      if (i != -1) {
        planet.setUnderConstruction(constructions[i]);
      } else {
        i = getConstruction("Basic farm", constructions);
        if (i != -1) {
          planet.setUnderConstruction(constructions[i]);
        }
      }
    }
    if (gotMines == -1) {
      // No mines at all
      int i = getConstruction("Advanced mine", constructions);
      if (i != -1) {
        planet.setUnderConstruction(constructions[i]);
      } else {
        i = getConstruction("Basic mine", constructions);
        if (i != -1) {
          planet.setUnderConstruction(constructions[i]);
        }
      }
    }
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
