package org.openRealmOfStars.starMap.planet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;


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
 * Class planet
 * 
 */

public class Planet {

  /**
   * List of Planet tile names for regular planets
   */
  public static final int[] PLANET_IMAGE_INDEX = 
      {Tiles.getTileByName(TileNames.ROCK1).getIndex(),
       Tiles.getTileByName(TileNames.WATERWORLD1).getIndex()};

  /**
   * List of big planet images
   */
  public static final BufferedImage[] PLANET_BIG_IMAGES = 
    {GuiStatics.BIG_PLANET_ROCK1,GuiStatics.BIG_PLANET_WATERWORLD1};

  /**
   * Planet name
   */
  private String name;
  
  /**
   * Planet order number in system
   */
  private int OrderNumber;
  
  /**
   * Planet's radioation level between 1-10.
   */
  private int radiationLevel;
  
  /**
   * Planet's ground size aka how many improvements can be fitted there.
   * This is between 7-16.
   */
  private int groundSize;
    
  /**
   * How much metal there is still in the ground.
   * This is between 2000-10000.
   */
  private int amountMetalInGround;

  /**
   * How much metal has been mined and available to use
   */
  private int metal;
  
  /**
   * Amount of production resource available
   */
  private int prodResource;
  
  /**
   * How much production resources available for building stuff
   */
  private int productionResource;
  
  /**
   * Is planet inhabitable gas giant. Gas giants just block the radar.
   */
  private boolean gasGiant;
  
  /**
   * Planet's x coordinate. On gas giant this left upper corner.
   */
  private int x;
  /**
   * Planet's y coordinate. On gas giant this left upper corner.
   */
  private int y;
  
  /**
   * Planet Image Index for planet tile
   */
  private int planetImageIndex;
  
  /**
   * Planet type: 0 Rock, 1 Waterworld
   */
  private int planetType;
  
  /**
   * Planet Owner info, this is index to player index, -1 means
   * that planet is not colonized yet.
   */
  private int planetOwner;
  
  /**
   * Planet playerInfo, null means not colonized yet.
   */
  private PlayerInfo planetOwnerInfo;
  
  /**
   * Extra food, each +10 increases people by one and each -10 decreseases
   * people by one.
   */
  private int extraFood;
  
  /**
   * How many productions are converted to credits
   */
  private int tax;
  
  /**
   * Maximum number of different works
   */
  public static final int MAX_WORKER_TYPE = 5;
  
  /**
   * Food production from farmers
   */
  public static final int FOOD_FARMERS = 0;

  /**
   * Metal production from miners
   */
  public static final int METAL_MINERS = 1;

  /**
   * production from workers
   */
  public static final int PRODUCTION_WORKERS = 2;

  /**
   * Research from scientist
   */
  public static final int RESEARCH_SCIENTIST = 3;

  /**
   * Culture from artist
   */
  public static final int CULTURE_ARTIST = 4;

  /**
   * Amount of different workers
   */
  private int[] workers;
  
  
  /**
   * Buildings / Planetary improvements
   */
  private ArrayList<Building> buildings;
  
  /**
   * What building / Planetary improvement is currently under construction
   */
  private Construction underConstruction;
  /**
   * Maximum number of different production
   */
  public static final int MAX_PRODUCTION_TYPE = 6;
  
  /**
   * Food production
   */
  public static final int PRODUCTION_FOOD = 0;

  /**
   * Metal production
   */
  public static final int PRODUCTION_METAL = 1;

  /**
   * Production production
   */
  public static final int PRODUCTION_PRODUCTION = 2;

  /**
   * Research production
   */
  public static final int PRODUCTION_RESEARCH = 3;

  /**
   * Culture production
   */
  public static final int PRODUCTION_CULTURE = 4;
  
  /**
   * Credit production
   */
  public static final int PRODUCTION_CREDITS = 5;

  /**
   * Population growth
   */
  public static final int PRODUCTION_POPULATION = 6;


  /**
   * Create random planet with name + orderNumber with Roman numbers.
   * Other planet attributes are randomized.
   * @param x Planet's X coordinate
   * @param y Planet's Y coordinate
   * @param name Planet name
   * @param orderNumber as integer
   * @param gasGiant Is planet inhabitable gas giant
   */
  public Planet(int x, int y,String name,int orderNumber,boolean gasGiant) {
    this.setX(x);
    this.setY(y);
    this.name = name+" "+RandomSystemNameGenerator.numberToRoman(orderNumber);
    this.setOrderNumber(orderNumber);
    this.setRadiationLevel(DiceGenerator.getRandom(1, 10));
    this.setAmountMetalInGround(DiceGenerator.getRandom(2000,10000));
    this.setGroundSize(DiceGenerator.getRandom(7,16));
    this.setMetal(0);
    this.gasGiant = gasGiant;
    this.planetImageIndex = 0;
    this.planetOwner = -1;
    this.workers = new int[MAX_WORKER_TYPE];
    this.extraFood = 0;
    this.productionResource = 0;
    this.buildings = new ArrayList<>();
    this.prodResource = 0;
    this.underConstruction = null;
    this.tax = 0;
  }

  /**
   * Add building to planet
   * @param building to add
   */
  public void addBuilding(Building building) {
    if (building != null) {
      this.buildings.add(building);
    }
  }
  
  /**
   * Remove building from planet and apply recycle bonus if available
   * @param building to remove
   */
  public void removeBuilding(Building building) {
    if (building != null) {
      int recycleBonus = getRecycleBonus();
      for (int i=0;i<buildings.size();i++) {
        Building temp = buildings.get(i);
        if (temp.getName().equals(building.getName())) {
          buildings.remove(i);
          if (recycleBonus > 0) {
            metal = metal +building.getMetalCost()*recycleBonus/100;
          }
        }
      }
    }
    
  }

  /**
   * Get production time as String
   * @param build
   * @return
   */
  public String getProductionTime(Building build) {
    int metalReq = build.getMetalCost()-getMetal();
    int prodReq = build.getProdCost()-getProdResource();
    int metalTurn = 0;
    int prodTurn = 0;
    if (metalReq <= 0 &&prodReq <= 0) {
      return "1 turn";
    }
    if (getTotalProduction(PRODUCTION_METAL) > 0 && metalReq > 0) {
      metalTurn = (int) Math.ceil((double) metalReq / (double) getTotalProduction(PRODUCTION_METAL));
    } else if (getTotalProduction(PRODUCTION_METAL) == 0 && metalReq > 0) {
      metalTurn = -1;
    } else if (metalReq <= 0) {
      metalTurn = 1;
    }
    if (getTotalProduction(PRODUCTION_PRODUCTION) > 0 && prodReq > 0) {
      prodTurn = (int) Math.ceil((double) prodReq / (double) getTotalProduction(PRODUCTION_PRODUCTION));
    } else if (getTotalProduction(PRODUCTION_PRODUCTION) == 0 && prodReq > 0) {
      prodTurn = -1;
    } else if (prodReq <= 0) {
      prodTurn = 1;
    }
    if (prodTurn == -1 || metalTurn== -1) {
      return "Never";
    } else {
      if (prodTurn > metalTurn) {
        return prodTurn+" turns";
      }
      return metalTurn+" turns";
    }
  }
  
  /**
   * Get amount of workers in certain type
   * @param workerType
   * @return Amount of workers
   */
  public int getWorkers(int workerType) {
    if (workerType >= 0 && workerType < MAX_WORKER_TYPE) {
      return workers[workerType];
    }
    return 0;
  }

  /**
   * Set amount of workers in certain type
   * @param workerType
   * @param value how many workers in this production
   */
  public void setWorkers(int workerType,int value) {
    if (workerType >= 0 && workerType < MAX_WORKER_TYPE) {
      workers[workerType] = value;
    }
  }
  
  /**
   * Get total population number
   * @return Total Population
   */
  public int getTotalPopulation() {
    int result=0;
    for (int i=0;i<workers.length;i++) {
      result = result +workers[i];
    }
    return result;
  }

  /**
   * Get total production from planetary improvements.
   * @param prod, Production to get: See all PRODUCTION_*
   * @return amount of production in one turn
   */
  private int getTotalProductionFromBuildings(int prod) {
    int result = 0;
    if (gasGiant || planetOwnerInfo == null ) {
      return 0;
    }
    switch (prod) {
    case PRODUCTION_FOOD: { 
      for (Building build : getBuildingList()) {
        result = result +build.getFarmBonus();
      }
      break;}
    case PRODUCTION_METAL: { 
      for (Building build : getBuildingList()) {
        result = result +build.getMineBonus();
      }
      break;}
    case PRODUCTION_PRODUCTION: { 
      for (Building build : getBuildingList()) {
        result = result +build.getFactBonus();
      }
      break;}
    case PRODUCTION_RESEARCH: { 
      for (Building build : getBuildingList()) {
        result = result +build.getReseBonus();
      }
      break;}
    case PRODUCTION_CULTURE: { 
      for (Building build : getBuildingList()) {
        result = result +build.getCultBonus();
      }
      break;}
    case PRODUCTION_CREDITS: { 
      for (Building build : getBuildingList()) {
        result = result +build.getCredBonus();
      }
      break;}
    case PRODUCTION_POPULATION: {
      result = 0;
     break;}
    }
    return result;
    
  }
  
  /**
   * Get planet's maintenance cost for full credits.
   * @return int as maintenanceCost
   */
  public int getMaintenanceCost() {
    double result=0;
    for (Building build : getBuildingList()) {
      result = result +build.getMaintenanceCost();
    }
    if (planetOwnerInfo != null && planetOwnerInfo.getRace() == SpaceRace.MECHIONS) {
      // Mechions have maintenance cost for each 4th of population
      result = result +Math.floor(getTotalPopulation()/4);
    }
    return (int) Math.floor(result);
    
  }
  /**
   * Get total production from planet. This includes racial, worker, planetary
   * improvement bonus
   * @param prod, Production to get: See all PRODUCTION_*
   * @return amount of production in one turn
   */
  public int getTotalProduction(int prod) {
    int result = 0;
    int mult=100;
    int div=100;
    if (gasGiant || planetOwnerInfo == null ) {
      return 0;
    }
    switch (prod) {
    case PRODUCTION_FOOD: { 
      // Planet always produces +2 food
      mult = 100;
      result=workers[FOOD_FARMERS]*mult/div+2+getTotalProductionFromBuildings(prod);break;}
    case PRODUCTION_METAL: { 
      mult = planetOwnerInfo.getRace().getMiningSpeed();
    // Planet always produces +1 metal      
    result=workers[METAL_MINERS]*mult/div+1+getTotalProductionFromBuildings(prod);
    if (result > getAmountMetalInGround()) {
      result = getAmountMetalInGround();
    }
    break;}
    case PRODUCTION_PRODUCTION: { 
      mult = planetOwnerInfo.getRace().getProductionSpeed();
     //  Planet always produces +1 production
    result=workers[PRODUCTION_PRODUCTION]*mult/div+1+getTotalProductionFromBuildings(prod);
    result = result -getTax();
    break;}
    case PRODUCTION_RESEARCH: { 
      mult = planetOwnerInfo.getRace().getResearchSpeed();
      //  Planet does not have research bonus
     result=workers[PRODUCTION_RESEARCH]*mult/div+getTotalProductionFromBuildings(prod);break;}
    case PRODUCTION_CULTURE: { 
      mult = planetOwnerInfo.getRace().getCultureSpeed();
      //  Planet does not have culture bonus
     result=workers[PRODUCTION_CULTURE]*mult/div+getTotalProductionFromBuildings(prod);break;}
    case PRODUCTION_CREDITS: { 
      mult = 100;
      //  Planet does not have credit bonus
     result=getTotalProductionFromBuildings(prod)+getTax()-getMaintenanceCost();break;}
    case PRODUCTION_POPULATION: { 
      //  Planet does not have population bonus
     result=getTotalProduction(PRODUCTION_FOOD)-getTotalPopulation();
     int require = 10*planetOwnerInfo.getRace().getFoodRequire()/100;
     if (result > 0) {
       result = (require-extraFood)/result;
       if (result < 1) {
         result = 1;
       }
     } else if (result < 0) {
       result = (-1*require-extraFood)/result;
       if (result < 1) {
         result = 1;
       }
       result = result *-1;
     } else {
       result = 0;
     }
     if (planetOwnerInfo.getRace() == SpaceRace.MECHIONS) {
       // Mechions never starve or populate
       result = 0; 
     }
     break;}
    }
    return result;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRadiationLevel() {
    return radiationLevel;
  }

  public void setRadiationLevel(int radiationLevel) {
    if (radiationLevel > 0 && radiationLevel < 11) {
      this.radiationLevel = radiationLevel;
    }
  }

  public int getGroundSize() {
    return groundSize;
  }

  public void setGroundSize(int groundSize) {
    if (groundSize > 6 && groundSize < 17) {
      this.groundSize = groundSize;
    }
  }

  public int getAmountMetalInGround() {
    return amountMetalInGround;
  }

  public void setAmountMetalInGround(int amountMetalInGround) {
    if (amountMetalInGround > 1999 && amountMetalInGround < 10001) {
      this.amountMetalInGround = amountMetalInGround;
    }
  }

  public int getMetal() {
    return metal;
  }

  public void setMetal(int metal) {
    this.metal = metal;
  }

  public int getOrderNumber() {
    return OrderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    OrderNumber = orderNumber;
  }

  public boolean isGasGiant() {
    return gasGiant;
  }

  public void setGasGiant(boolean gasGiant) {
    this.gasGiant = gasGiant;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  /**
   * Get Planet tile index
   * @return
   */
  public int getPlanetImageIndex() {
    return planetImageIndex;
  }

  /**
   * Get the construction list for planet
   * @return Building list of production
   */
  public Construction[] getProductionList() {
    Building[] alreadyBuilt = getBuildingList();
    ArrayList<Construction> result = new ArrayList<>();
    Building tmp = BuildingFactory.createByName("Basic mine");
    if (tmp != null) {
      result.add(tmp);
    }
    tmp = BuildingFactory.createByName("Basic factory");
    if (tmp != null) {
      result.add(tmp);
    }
    tmp = BuildingFactory.createByName("Basic farm");
    if (tmp != null) {
      result.add(tmp);
    }
    tmp = BuildingFactory.createByName("Space port");
    if (tmp != null) {
      if (tmp.isSingleAllowed()) {
        boolean built = false;
        for (int j=0;j<alreadyBuilt.length;j++) {
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
      String[] buildings = planetOwnerInfo.getTechList().getBuildingListFromTech();
      for (int i=0;i<buildings.length;i++) {
        tmp = BuildingFactory.createByName(buildings[i]);
        if (tmp != null) {
          if (tmp.isSingleAllowed()) {
            boolean built = false;
            for (int j=0;j<alreadyBuilt.length;j++) {
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
   * Return how many buildings there are on planet
   * @return int
   */
  public int getUsedPlanetSize() {
    return buildings.size();
  }
  
  public void setPlanetImageIndex(int planetImageIndex) {
    this.planetImageIndex = planetImageIndex;
  }
  
  /**
   * Planet size as string. Size varies from small to huge.
   * @return String
   */
  public String getSizeAsString() {
    switch (getGroundSize()) {
    case 7: return "small";
    case 8: return "small";
    case 9: return "medium";
    case 10: return "below average";
    case 11: return "average";
    case 12: return "average";
    case 13: return "above average";
    case 14: return "large";
    case 15: return "huge";
    case 16: return "huge";
    default: return "small";
    }
  }
  
  /**
   * Generate info text
   * @return String
   */
  public String generateInfoText() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getName());
    sb.append("\n");
    if (isGasGiant()) {
      sb.append("\n");
      sb.append("Gas Giant");
      sb.append("\n");
      sb.append("Planet is inhabitable, but planet can block scanners.");
    } else {
      sb.append("Radiation:");
      sb.append(getRadiationLevel());
      sb.append("\n");
      sb.append("Size:");
      sb.append(getSizeAsString());
      sb.append("\n");
      sb.append("Metal:");
      sb.append(getAmountMetalInGround());
    }
    return sb.toString();
  }

  /**
   * @return the planetType
   */
  public int getPlanetType() {
    return planetType;
  }

  /**
   * @param planetType the planetType to set
   */
  public void setPlanetType(int planetType) {
    if (planetType >= 0 && planetType <  PLANET_IMAGE_INDEX.length) {
      this.planetType = planetType;
      setPlanetImageIndex(PLANET_IMAGE_INDEX[planetType]);
    }
  }

  /**
   * @return the planetOwner
   */
  public int getPlanetOwnerIndex() {
    return planetOwner;
  }

  /**
   * @return the planet player info
   */
  public PlayerInfo getPlanetPlayerInfo() {
    return planetOwnerInfo;
  }

  /**
   * Set Planet owner info and index. Use -1 and null for uncolonized.
   * @param planetOwner the planetOwner to set
   */
  public void setPlanetOwner(int planetOwner, PlayerInfo info) {
    this.planetOwner = planetOwner;
    this.planetOwnerInfo = info;
  }

  /**
   * @return the productionResource
   */
  public int getProductionResource() {
    return productionResource;
  }

  /**
   * @param productionResource the productionResource to set
   */
  public void setProductionResource(int productionResource) {
    this.productionResource = productionResource;
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
  public void setProdResource(int prodResource) {
    this.prodResource = prodResource;
  }

  public Construction getUnderConstruction() {
    return underConstruction;
  }

  public void setUnderConstruction(Construction underConstruction) {
    this.underConstruction = underConstruction;
  }
  
  public void updateOneTurn() {
    if (planetOwnerInfo != null) {
      int minedMetal = getTotalProduction(PRODUCTION_METAL);
      if (minedMetal <= amountMetalInGround) {
        amountMetalInGround = amountMetalInGround -minedMetal;
        metal = metal + minedMetal;
      } else {
        metal = metal + amountMetalInGround;
        amountMetalInGround = 0;
      }
      prodResource = prodResource + getTotalProduction(PRODUCTION_PRODUCTION);
      
      int food=getTotalProduction(PRODUCTION_FOOD)-getTotalPopulation();
      extraFood = extraFood +food;
      int require = 10*planetOwnerInfo.getRace().getFoodRequire()/100;
      if (extraFood > 0 && extraFood >= require) {
        extraFood = extraFood -require;
        workers[FOOD_FARMERS] = workers[FOOD_FARMERS]+1; 
      }
  
      
      // Making building happens at the end
      if (underConstruction != null) {
        if (metal >= underConstruction.getMetalCost() &&
            prodResource >= underConstruction.getProdCost()  && groundSize > buildings.size()) {
          metal = metal - underConstruction.getMetalCost();
          prodResource = prodResource - underConstruction.getProdCost();
          if (underConstruction instanceof Building) {
            buildings.add((Building) underConstruction);
          }
        }
      }
    }
  }

  /**
   * Get the total production without taxes
   * @return int
   */
  private int getTotalProductionWithoutTax() {
    int mult;
    int result =0;
    int div = 100;
    mult = planetOwnerInfo.getRace().getProductionSpeed();
    //  Planet always produces +1 production
    result=workers[PRODUCTION_PRODUCTION]*mult/div+1+
        getTotalProductionFromBuildings(PRODUCTION_PRODUCTION);
    return result;

  }
  
  /**
   * @return the tax
   */
  public int getTax() {
    return tax;
  }

  /**
   * Set tax cannot be bigger than maximum production
   * @param tax the tax to set
   */
  public void setTax(int tax) {
    int max = getTotalProductionWithoutTax();
    this.tax = tax;
    if (this.tax > max) {
      this.tax = max;
    }
    if (this.tax < 0) {
      this.tax = 0;
    }
  }
  
  /**
   * Get planet recycle bonus
   * @return Recycle bonus
   */
  public int getRecycleBonus() {
    int result = 0;
    Building[] buildings = getBuildingList();
    for (Building building : buildings) {
      if (building.getRecycleBonus() > result) {
        result = building.getRecycleBonus();
      }
    }
    return result;
  }
  

}
