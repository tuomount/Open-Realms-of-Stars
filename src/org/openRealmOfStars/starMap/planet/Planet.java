package org.openRealmOfStars.starMap.planet;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.construction.ConstructionFactory;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.IOUtilities;
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
       Tiles.getTileByName(TileNames.WATERWORLD1).getIndex(),
       Tiles.getTileByName(TileNames.WATERWORLD2).getIndex(),
       Tiles.getTileByName(TileNames.IRONPLANET1).getIndex(),
       Tiles.getTileByName(TileNames.IRONPLANET2).getIndex()};

  /**
   * List of big planet images
   */
  public static final BufferedImage[] PLANET_BIG_IMAGES = 
    {GuiStatics.BIG_PLANET_ROCK1,GuiStatics.BIG_PLANET_WATERWORLD1,
        GuiStatics.BIG_PLANET_WATERWORLD2,GuiStatics.BIG_PLANET_IRONPLANET1,
        GuiStatics.BIG_PLANET_IRONPLANET2};

  /**
   * List of big planet images
   */
  public static final BufferedImage[] GASWORLD_BIG_IMAGES = 
    {GuiStatics.BIG_GASWORLD1,GuiStatics.BIG_GASWORLD2};

  /**
   * Planet name
   */
  private String name;
  
  /**
   * Planet order number in system
   */
  private int OrderNumber;
  
  /**
   * Planet's radiation level between 1-10.
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
   * Planet's culture value
   */
  private int culture;
  
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
   * Remove one colonist where are the most workers
   * @return true if colonist is taken, otherwise false
   */
  public boolean takeColonist() {
    boolean result = false;
    int index = -1;
    int value = 0;
    int total=0;
    for (int i = 0;i<MAX_WORKER_TYPE;i++) {
      total = total +workers[i];
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
  public void moveWorker(int from, int to) {
    if (from > -1 && from <MAX_WORKER_TYPE &&
        to > -1 && to <MAX_WORKER_TYPE &&
        from != to && workers[from]>0) {
      workers[from]=workers[from]-1;
      workers[to]=workers[to]+1;
    }
  }
  
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
    this.buildings = new ArrayList<>();
    this.prodResource = 0;
    this.underConstruction = null;
    this.tax = 0;
    this.culture = 0;
  }
  
  /**
   * Save Planet data to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException
   */
  public void savePlanet(DataOutputStream dos) throws IOException {
    // Coordinates
    dos.writeInt(x);
    dos.writeInt(y);
    IOUtilities.writeString(dos, name);
    dos.writeInt(OrderNumber);
    dos.writeInt(radiationLevel);
    dos.writeInt(groundSize);
    dos.writeInt(amountMetalInGround);
    dos.writeInt(metal);
    dos.writeInt(prodResource);
    dos.writeBoolean(gasGiant);
    dos.writeInt(planetImageIndex);
    dos.writeInt(planetType);
    dos.writeInt(planetOwner);
    dos.writeInt(extraFood);
    dos.writeInt(tax);
    dos.writeInt(culture);
    for (int i=0;i<MAX_WORKER_TYPE;i++) {
      dos.writeInt(workers[i]);
    }
    dos.writeInt(buildings.size());
    for (int i=0;i<buildings.size();i++) {
      IOUtilities.writeString(dos, buildings.get(i).getName());
    }
    if (underConstruction == null) {
      // Write empty string
      IOUtilities.writeString(dos, "");
    } else {
      IOUtilities.writeString(dos, underConstruction.getName());
    }
  }

  /**
   * Add building to planet
   * @param building to add
   */
  public void addBuilding(Building building) {
    if (building != null) {
      if (building.getName().equals("Radiation dampener") ||
          building.getName().equals("Radiation well")) {
        setRadiationLevel(getRadiationLevel()-1);
      }
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
          if (building.getName().equals("Radiation dampener") ||
              building.getName().equals("Radiation well")) {
            setRadiationLevel(getRadiationLevel()+1);
          }
          if (recycleBonus > 0) {
            metal = metal +building.getMetalCost()*recycleBonus/100;
          }
          break;
        }
      }
    }
    
  }

  /**
   * Get production time as String
   * @param build
   * @return
   */
  public String getProductionTime(Construction build) {
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
      if (planetOwnerInfo.getRace() == SpaceRace.MECHIONS) {
        // Mechions never starve or populate
        result = 0;
        break;
      }

      //  Planet does not have population bonus
     result=getTotalProduction(PRODUCTION_FOOD)-getTotalPopulation()*planetOwnerInfo.getRace().getFoodRequire()/100;
     int require = 10*100/planetOwnerInfo.getRace().getGrowthSpeed();
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
   * Check if planet radiation exceeds current owner's max radiation level.
   * @return True if planet radiation is higher and false if not
   */
  public boolean exceedRadiation() {
    boolean exceedRad = false;
    if (planetOwnerInfo != null && planetOwnerInfo.getRace().getMaxRad() < getRadiationLevel()) {
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
    Building tmp = BuildingFactory.createByName("Basic mine");
    if (tmp != null) {
      result.add(tmp);
    }
    if (planetOwnerInfo != null && planetOwnerInfo.getRace() == SpaceRace.MECHIONS) {
      tmp2 = ConstructionFactory.createByName(ConstructionFactory.MECHION_CITIZEN);
      if (tmp2 != null && !exceedRadiation()) {
        result.add(tmp2);
      }
    } else {
      tmp = BuildingFactory.createByName("Basic farm");
      if (tmp != null && !exceedRadiation()) {
        result.add(tmp);
      }
    }
    tmp = BuildingFactory.createByName("Basic factory");
    if (tmp != null) {
      result.add(tmp);
    }
    tmp = BuildingFactory.createByName("Space port");
    if (tmp != null && !exceedRadiation()) {
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
        if (getRadiationLevel() == 1 && (tmp.getName().equals("Radiation dampener") ||
            tmp.getName().equals("Radiation well"))) {
          // No need for radiation well or dampener on small radiation planets
          tmp = null;
        }
        if (tmp != null) {
          if (tmp.isSingleAllowed()) {
            boolean built = false;
            for (int j=0;j<alreadyBuilt.length;j++) {
              if (alreadyBuilt[j].getName().equals(tmp.getName())) {
               built = true;
               break;
              }
            }
            if (!built  && !exceedRadiation()) {
              result.add(tmp);
            }
            if (!built && (tmp.getName().equals("Radiation dampener") ||
                tmp.getName().equals("Radiation well"))) {
              // Radiation well and dampener can be built even planet has radiation.
              result.add(tmp);
            }
          } else {
            if  (!exceedRadiation()) {
              result.add(tmp);
            }
          }
        }
      }
      if (this.hasSpacePort()) {
        ShipStat[] ships = planetOwnerInfo.getShipStatList();
        for (ShipStat stat : ships) {
          if (!stat.isObsolete()) {
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
      sb.append("Radiation: ");
      sb.append(getRadiationLevel());
      sb.append("\n");
      sb.append("Size: ");
      sb.append(getSizeAsString());
      sb.append("\n");
      sb.append("Metal: ");
      sb.append(getAmountMetalInGround());
      if (planetOwnerInfo != null) {
        sb.append("\n");
        sb.append(planetOwnerInfo.getEmpireName());
        sb.append("\n");
        sb.append("Population: ");
        sb.append(getTotalPopulation());
        sb.append("\n");
        sb.append("Culture: ");
        sb.append(getCulture());
      }
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
      planetOwnerInfo.setTotalCredits(planetOwnerInfo.getTotalCredits()+getTotalProduction(PRODUCTION_CREDITS));
      culture = culture+getTotalProduction(PRODUCTION_CULTURE);
      
      Message msg;
      if (planetOwnerInfo.getRace() != SpaceRace.MECHIONS) { 
        int food=getTotalProduction(PRODUCTION_FOOD)-getTotalPopulation()*planetOwnerInfo.getRace().getFoodRequire()/100;
        extraFood = extraFood +food;
        int require = 10*100/planetOwnerInfo.getRace().getGrowthSpeed();
        if (exceedRadiation() && extraFood > 0) {
          // Clear extra food if radiation is exceeded
          extraFood = 0;
        }
        if (extraFood > 0 && extraFood >= require) {
          extraFood = extraFood -require;
          workers[FOOD_FARMERS] = workers[FOOD_FARMERS]+1;
          msg = new Message(MessageType.POPULATION, getName()+" has population growth!\n"
              + "Population is now "+getTotalPopulation(), 
              Icons.getIconByName(Icons.ICON_PEOPLE));
          msg.setCoordinate(getX(), getY());
          msg.setMatchByString(getName());
          planetOwnerInfo.getMsgList().addNewMessage(msg);
        }
        if (extraFood < 0 && extraFood <= require) {
          extraFood = 0;
          String workerName = "Culture artist";
          if (workers[CULTURE_ARTIST] > 0) {
            workers[CULTURE_ARTIST]--;
            workerName = "Artist";
          } else if (workers[METAL_MINERS] > 0) {
            workers[METAL_MINERS]--;
            workerName = "Miner";
          } else if (workers[RESEARCH_SCIENTIST] > 0) {
            workers[RESEARCH_SCIENTIST]--;
            workerName = "Scientist";
          }else if (workers[PRODUCTION_WORKERS] > 0) {
            workers[PRODUCTION_WORKERS]--;
            workerName = "Worker";
          } else {
            workers[FOOD_FARMERS]--;
            workerName = "Farmer";
          }
          msg = new Message(MessageType.POPULATION, getName()+" has "+workerName+" died!\n"
              + "Population is now "+getTotalPopulation(), 
              Icons.getIconByName(Icons.ICON_DEATH));
          msg.setCoordinate(getX(), getY());
          msg.setMatchByString(getName());
          planetOwnerInfo.getMsgList().addNewMessage(msg);
        }
      }
  
      
      // Making building happens at the end
      if (underConstruction != null) {
        if (metal >= underConstruction.getMetalCost() &&
            prodResource >= underConstruction.getProdCost()) {
          if (underConstruction instanceof Building && groundSize > buildings.size()) {
            metal = metal - underConstruction.getMetalCost();
            prodResource = prodResource - underConstruction.getProdCost();
            buildings.add((Building) underConstruction);
            msg = new Message(MessageType.CONSTRUCTION, getName()+" built "+underConstruction.getName(), 
                Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
            msg.setCoordinate(getX(), getY());
            msg.setMatchByString(getName());
            planetOwnerInfo.getMsgList().addNewMessage(msg);
          } else if (underConstruction instanceof Ship) {
            metal = metal - underConstruction.getMetalCost();
            prodResource = prodResource - underConstruction.getProdCost();
            Ship ship = (Ship) underConstruction;
            ShipStat stat = planetOwnerInfo.getShipStatByName(ship.getName());
            stat.setNumberOfBuilt(stat.getNumberOfBuilt()+1);
            stat.setNumberOfInUse(stat.getNumberOfInUse()+1);
            if (stat.getDesign().getTotalMilitaryPower()>0) {
              culture=culture+stat.getDesign().getTotalMilitaryPower()/4;
            }
            Fleet fleet = new Fleet(ship, getX(), getY());
            planetOwnerInfo.Fleets().add(fleet);
            if (planetOwnerInfo.getMissions() != null) {
              Mission mission = planetOwnerInfo.getMissions().getMissionForPlanet(getName(), MissionPhase.BUILDING);
              if (mission != null) {
                if (mission.getFleetName() == null) {
                  if (mission.getType() == MissionType.COLONIZE) {
                    fleet.setName("Colony #"+(planetOwnerInfo.Fleets().
                        howManyFleetWithStartingNames("Colony #")+1));
                    mission.setFleetName(fleet.getName());
                  }
                } else {
                  fleet.setName(mission.getFleetName()+" #"+(planetOwnerInfo.Fleets().
                    howManyFleetWithStartingNames(mission.getFleetName())+1));
                }                
                if (mission.getType() == MissionType.DEFEND) {
                  // For now one ship is enough for defend
                  mission.setPhase(MissionPhase.EXECUTING);
                } else if (mission.getType() == MissionType.COLONIZE) {
                  mission.setPhase(MissionPhase.LOADING);
                } else {
                  mission.setPhase(MissionPhase.TREKKING);
                }
              } else {
                // No mission for planet, so just adding defender
                if (ship.getTotalMilitaryPower() > 0) {
                  String fleetName = "Defender";
                  fleet.setName(fleetName+" #"+(planetOwnerInfo.Fleets().
                      howManyFleetWithStartingNames(fleetName)+1));
                }
              }
            } else {
              fleet.setName("Fleet #"+(planetOwnerInfo.Fleets().
                  howManyFleetWithStartingNames("Fleet #")+1));
            }
            msg = new Message(MessageType.CONSTRUCTION, getName()+" built "+underConstruction.getName(), 
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setCoordinate(getX(), getY());
            msg.setMatchByString(getName());
            planetOwnerInfo.getMsgList().addNewMessage(msg);
          } else {
            if (underConstruction.getName().equals(ConstructionFactory.MECHION_CITIZEN)) {
              metal = metal - underConstruction.getMetalCost();
              prodResource = prodResource - underConstruction.getProdCost();
              workers[PRODUCTION_WORKERS] = workers[PRODUCTION_WORKERS]+1;
              msg = new Message(MessageType.CONSTRUCTION, getName()+" built "+underConstruction.getName(), 
                  Icons.getIconByName(Icons.ICON_PEOPLE));
              msg.setCoordinate(getX(), getY());
              msg.setMatchByString(getName());
              planetOwnerInfo.getMsgList().addNewMessage(msg);
            }
            if (underConstruction.getName().equals(ConstructionFactory.EXTRA_CULTURE)) {
              metal = metal - underConstruction.getMetalCost();
              prodResource = prodResource - underConstruction.getProdCost();
              culture = culture +5;
              msg = new Message(MessageType.CONSTRUCTION, getName()+" built "+underConstruction.getName(), 
                  Icons.getIconByName(Icons.ICON_CULTURE));
              msg.setCoordinate(getX(), getY());
              msg.setMatchByString(getName());
              planetOwnerInfo.getMsgList().addNewMessage(msg);
            }
            if (underConstruction.getName().equals(ConstructionFactory.EXTRA_CREDIT)) {
              metal = metal - underConstruction.getMetalCost();
              prodResource = prodResource - underConstruction.getProdCost();
              planetOwnerInfo.setTotalCredits(planetOwnerInfo.getTotalCredits()+12);
              msg = new Message(MessageType.CONSTRUCTION, getName()+" built "+underConstruction.getName(), 
                  Icons.getIconByName(Icons.ICON_CREDIT));
              msg.setCoordinate(getX(), getY());
              msg.setMatchByString(getName());
              planetOwnerInfo.getMsgList().addNewMessage(msg);
            }
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

  /**
   * Get planet Scanner level
   * @return Scanner level
   */
  public int getScannerLvl() {
    int result = 2;
    Building[] buildings = getBuildingList();
    for (Building building : buildings) {
      if (building.getScanRange() > 0 && result < building.getScanRange()) {
        result = building.getScanRange();
      }
    }
    return result;
  }
  
  /**
   * Check if planet has Space port for building ships
   * @return True if planet has space port, otherwise false.
   */
  public boolean hasSpacePort() {
    Building[] buildings  = getBuildingList();
    for (Building building : buildings) {
      if (building.getName().equals("Space port")) {
        return true;
      }
    }
    return false;
  }
  /**
   * Get planet Scanner Cloaking detection level
   * @return Cloaking detection level
   */
  public int getCloakingDetectionLvl() {
    int result = 0;
    Building[] buildings = getBuildingList();
    for (Building building : buildings) {
      if (building.getScanCloakingDetection() > 0 && result < building.getScanCloakingDetection()) {
        result = building.getScanCloakingDetection();
      }
    }
    return result;
  }

  
  /**
   * How many buildings with same name planet has
   * @param name Building name to search
   * @return number of buildings with same name
   */
  public int howManyBuildings(String name) {
    int result = 0;
    Building[] buildings = getBuildingList();
    for (Building building : buildings) {
      if (building.getName().equals(name)) {
        result = result+1;
      }
    }
    return result;
  }
  
  public int getCulture() {
    return culture;
  }

  public void setCulture(int culture) {
    this.culture = culture;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append(" X: ");
    sb.append(getX());
    sb.append(" Y: ");
    sb.append(getY());
    sb.append(" - ");
    if (planetOwnerInfo != null) {
      sb.append(planetOwnerInfo.getEmpireName());
    } else {
      sb.append("Uncolonized");
    }
    return sb.toString();
  }


}
