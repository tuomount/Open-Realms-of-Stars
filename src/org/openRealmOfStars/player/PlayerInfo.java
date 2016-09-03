package org.openRealmOfStars.player;

import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;

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
 * Player info contains which race player has, planet etc
 * Player here means both Human and AI players
 * 
 */

public class PlayerInfo {

  /**
   * Player's space race
   */
  private SpaceRace race;
  
  /**
   * Player's empire name
   */
  private String empireName;
  
  /**
   * Total credits for player, these should not go negative
   */
  private int totalCredits;

  /**
   * Technology list that player has studied or gained
   */
  private TechList techList;
  
  /**
   * Message for player for one turn
   */
  private MessageList msgList;
  
  /**
   * Space ship stat and design list
   */
  private ArrayList<ShipStat> shipStatList;
  
  /**
   * Player fleets
   */
  private FleetList fleets;
  
  /**
   * Map Data
   * 0: Uncharted only suns are drawn
   * 1: Fog of war, no fleets are drawn
   * 2: Visible everything is drawn
   */
  private byte[][] mapData;

  /**
   * Cloaking detection per sector
   */
  private int[][] mapCloakDetection;
  
  /**
   * Map X size
   */
  private int maxX;
  /**
   * Map Y size
   */
  private int maxY;
  
  /**
   * Human player if true
   */
  private boolean human;
  
  /**
   * Missions list
   */
  private MissionList missions;
  

  /**
   * Uncharted map sector, only suns are visible
   */
  public static final byte UNCHARTED = 0;
  /**
   * Fog of war, no fleets are drawn
   */
  public static final byte FOG_OF_WAR = 1;
  /**
   * Every thing are drawn
   */
  public static final byte VISIBLE = 2;
  
  public PlayerInfo(SpaceRace race) {
    setTechList(new TechList());
    this.msgList = new MessageList();
    shipStatList = new ArrayList<>();
    fleets = new FleetList();
    setHuman(false);
    setRace(race);
    switch (getRace()) {
    case HUMAN:
    case MECHIONS:
    case CENTAURS:{
      /*
       * Humans, Mechions and Centaurs get 1 Combat, 1 Defense, Scout and Colony
       */
      Tech tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Defense, 1, 
          techList.getListForTypeAndLevel(TechType.Defense, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Colony", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Scout Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createPropulsionTech("Ion drive Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createPropulsionTech("Fission source Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      ShipDesign design = ShipGenerator.createScout(this);
      ShipStat stat = new ShipStat(design);
      addShipStat(stat);
      design = ShipGenerator.createColony(this, false);
      stat = new ShipStat(design);
      addShipStat(stat);
      break;
    }
    case SPORKS:{
      /*
       * Sporks get 2 Combat, 1 Defense, Scout and Colony
       */
      Tech tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Defense, 1, 
          techList.getListForTypeAndLevel(TechType.Defense, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Colony", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Scout Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createPropulsionTech("Ion drive Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createPropulsionTech("Fission source Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      ShipDesign design = ShipGenerator.createScout(this);
      ShipStat stat = new ShipStat(design);
      addShipStat(stat);
      design = ShipGenerator.createColony(this, false);
      stat = new ShipStat(design);
      addShipStat(stat);
      break;
    }
    case GREYANS:{
      /*
       * Greyans get 1 Combat, 1 Defense, Scout and Colony, 1 propulsion, 1 electronics
       */
      Tech tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Defense, 1, 
          techList.getListForTypeAndLevel(TechType.Defense, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Colony", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Scout Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createPropulsionTech("Ion drive Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createPropulsionTech("Fission source Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Propulsion, 1, 
          techList.getListForTypeAndLevel(TechType.Propulsion, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Electrics, 1, 
          techList.getListForTypeAndLevel(TechType.Electrics, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      ShipDesign design = ShipGenerator.createScout(this);
      ShipStat stat = new ShipStat(design);
      addShipStat(stat);
      design = ShipGenerator.createColony(this, false);
      stat = new ShipStat(design);
      addShipStat(stat);
      break;
    }
    }
    
  }
  
  /**
   * Get best sector to explore in this Solar system
   * @param sun Solar System
   * @param fleet Fleet doing the exploring
   * @return PathPoint where to go next or null if no more exploring
   */
  public PathPoint getUnchartedSector(Sun sun, Fleet fleet) {
    PathPoint result = null;
    int scan = fleet.getFleetScannerLvl();
    int[] unCharted = new int[4];
    int[] charted = new int[4];
    int[] sectors = new int[4];
    int sector=0;
    for (int x=-StarMap.SOLARSYSTEMWIDTH; x <StarMap.SOLARSYSTEMWIDTH+1;x++) {
      for (int y=-StarMap.SOLARSYSTEMWIDTH; y <StarMap.SOLARSYSTEMWIDTH+1;y++) {
        if (x<=0 && y<= 0) {
          sector = 0;
        } else if (x>0 && y<= 0) {
          sector = 1;
        } else if (x<=0 && y> 0) {
          sector = 2;
        } else if (x>0 && y> 0) {
          sector = 3;
        }
        if (isValidCoordinate(sun.getCenterX()+x, sun.getCenterY()+y)) {
          if (mapData[sun.getCenterX()+x][sun.getCenterY()+y]==UNCHARTED) {
            unCharted[sector]++;
          } else {
            charted[sector]++;
          }
        }
      }
    }
    for (int i=0;i<sectors.length;i++) {
      sectors[i] = 100*unCharted[i] /(charted[i]+unCharted[i]);
    }
    sector = 0;
    int value = -1;
    for (int i=0;i<sectors.length;i++) {
      if (sectors[i] > value) {
        sector = i;
        value = sectors[i];
      }
    }
    int mx = 0;
    int my = 0;
    switch (sector) {
    case 0: { mx = -1; my = -1; break; }
    case 1: { mx = 1; my = -1; break; }
    case 2: { mx = -1; my = 1; break; }
    case 3: { mx = 1; my = 1; break; }
    }
    if (value > 25) {
      int range = scan*2;
      int amount = StarMap.SOLARSYSTEMWIDTH/range;
      int nx = sun.getCenterX();
      int ny = sun.getCenterY();
      for (int i=0;i<=amount;i++) {
        nx = nx+mx;
        ny = ny+my;
        if (isValidCoordinate(nx, ny)) {
          if (mapData[nx][ny]==UNCHARTED) {
            result = new PathPoint(nx, ny, StarMap.getDistance(fleet.getX(), fleet.getY(),
                nx, ny));
            break;
          }
        }
      }
    }
    return result;
  }
  
  /**
   * Check if coordinates are valid for this StarMap
   * @param x X coordinate
   * @param y y coordinate
   * @return true if valid and false if invalid
   */
  private boolean isValidCoordinate(int x, int y) {
    if (x >= 0 && y >= 0 && x < maxX && y<maxY ) {
      return true;
    }
    return false;
  }

  /**
   * Init map visibility and cloaking detection maps
   * @param maxX Map size in X axel
   * @param maxY Map size in Y axel
   */
  public void initMapData(int maxX, int maxY) {
    this.maxX = maxX;
    this.maxY = maxY;
    mapData = new byte[maxX][maxY];
    mapCloakDetection = new int[maxX][maxY];
  }
  
  /**
   * Get sector visibility
   * @param x X coordinate
   * @param y Y coordinate
   * @return UNCHARTED, FOG_OF_WAR or VISIBLE
   */
  public byte getSectorVisibility(int x, int y) {
    byte result = UNCHARTED;
    try {
      result = mapData[x][y];
      
    } catch (ArrayIndexOutOfBoundsException e) {
      // Do nothing
    }
    return result;
  }

  /**
   * Set sector visibility
   * @param x X coordinate
   * @param y Y coordinate
   * @param visibility UNCHARTED, FOG_OF_WAR or VISIBLE
   */
  public void setSectorVisibility(int x, int y,byte visibility) {
    if (visibility >= 0 && visibility <= VISIBLE) {
      try {
        mapData[x][y] = visibility;
      } catch (ArrayIndexOutOfBoundsException e) {
      // Do nothing
      }
    }
  }

  /**
   * Get sector cloaking detection
   * @param x X coordinate
   * @param y Y coordinate
   * @return cloaking detection value
   */
  public int getSectorCloakDetection(int x, int y) {
    int result = 0;
    try {
      result = mapCloakDetection[x][y];
      
    } catch (ArrayIndexOutOfBoundsException e) {
      // Do nothing
    }
    return result;
  }

  /**
   * Set sector cloaking detection value
   * @param x X coordinate
   * @param y Y coordinate
   * @param cloaking detection value
   */
  public void setSectorCloakingDetection(int x, int y,int value) {
    try {
      mapCloakDetection[x][y] = value;
    } catch (ArrayIndexOutOfBoundsException e) {
    // Do nothing
    }
  }
  
  public void resetVisibilityDataAfterTurn() {
    for (int y=0;y<maxY;y++) {
      for (int x=0;x<maxX;x++) {
        mapCloakDetection[x][y] = 0;
        if (mapData[x][y] == VISIBLE) {
          mapData[x][y] = FOG_OF_WAR;
        }
      }
    }
  }

  /**
   * Number of Ship stats player has
   * @return Number of ship stats in list
   */
  public int getNumberOfShipStats() {
    return shipStatList.size();
  }
  
  /**
   * Get ship stat by index. May return null if index invalid
   * @param index ShipStat index
   * @return ShipStat or null
   */
  public ShipStat getShipStat(int index) {
    if (shipStatList.size() > 0 && index >= 0 && index < shipStatList.size()) {
      return shipStatList.get(index);
    }
    return null;
  }
  
  /**
   * Get Ship stat by name.
   * @param name for search
   * @return ShipStat if found otherwise null
   */
  public ShipStat getShipStatByName(String name) {
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().equals(name)) {
        return stat;
      }
    }
    return null;
  }
  /**
   * Get Ship Stat list as a fixed array
   * @return Ship Stat array
   */
  public ShipStat[] getShipStatList() {
    return shipStatList.toArray(new ShipStat[shipStatList.size()]);
  }
  
  /**
   * Add Ship Stat to list
   * @param stat ShipStat to add
   */
  public void addShipStat(ShipStat stat) {
    if (stat != null)  {
      shipStatList.add(stat);
    }
  }

  /**
   * remove Ship Stat from list
   * @param index Index to remove
   */
  public void removeShipStat(int index) {
    if (shipStatList.size() > 0 && index >= 0 && index < shipStatList.size()) {
      shipStatList.remove(index);
    }
  }

  public SpaceRace getRace() {
    return race;
  }

  public void setRace(SpaceRace race) {
    this.race = race;
  }

  public String getEmpireName() {
    return empireName;
  }

  public void setEmpireName(String empireName) {
    this.empireName = empireName;
  }

  public int getTotalCredits() {
    return totalCredits;
  }

  public void setTotalCredits(int totalCredits) {
    this.totalCredits = totalCredits;
  }

  public TechList getTechList() {
    return techList;
  }

  public void setTechList(TechList techList) {
    this.techList = techList;
  }
  
  /**
   * Get the player Fleets
   * @return Fleets never null
   */
  public FleetList Fleets() {
    return fleets;
  }
  
  /**
   * Get message list for one turn
   */
  public MessageList getMsgList() {
    return msgList;
  }

  public boolean isHuman() {
    return human;
  }

  public void setHuman(boolean human) {
    this.human = human;
    if (this.human) {
      missions = null;
    } else {
      missions = new MissionList();
    }
  }

  
  /**
   * Get missions list. This is non null only for AI players
   * @return Mission list
   */
  public MissionList getMissions() {
    return missions;
  }

  
}
