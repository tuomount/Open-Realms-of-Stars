package org.openRealmOfStars.player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
import org.openRealmOfStars.starMap.StarMapStatics;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.utilities.IOUtilities;

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
   * Read PlayerInfo from DataInputStream 
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public PlayerInfo(DataInputStream dis) throws IOException {
    empireName = IOUtilities.readString(dis);
    race = SpaceRace.getRaceByIndex(dis.readInt());
    totalCredits = dis.readInt();
    techList = new TechList(dis);
    msgList = new MessageList(dis);
    int count = dis.readInt();
    shipStatList = new ArrayList<>();
    for (int i=0;i<count;i++) {
      ShipStat ship = new ShipStat(dis);
      shipStatList.add(ship);
    }
    fleets = new FleetList(dis);
    maxX = dis.readInt();
    maxY = dis.readInt();
    mapData = new byte[maxX][maxY];
    mapCloakDetection = new int[maxX][maxY];
    for (int y=0;y<maxY;y++) {
      for (int x=0;x<maxX;x++) {
        mapData[x][y] = dis.readByte();
      }
    }
    human = dis.readBoolean();
    if (!human) {
      missions = new MissionList(dis);
    }
    
  }
  
  /**
   * Save Player Info to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void savePlayerInfo(DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, empireName);
    dos.writeInt(race.getIndex());
    dos.writeInt(totalCredits);
    techList.saveTechList(dos);
    msgList.saveMessageList(dos);
    dos.writeInt(shipStatList.size());
    for (int i=0;i<shipStatList.size();i++) {
      shipStatList.get(i).saveShipStat(dos);
    }
    fleets.saveFleetList(dos);
    dos.writeInt(maxX);
    dos.writeInt(maxY);
    if (mapData == null) {
      throw new IOException("Map data is not initialized yet!");
    }
    for (int y=0;y<maxY;y++) {
      for (int x=0;x<maxX;x++) {
        dos.writeByte(mapData[x][y]);
      }
    }
    dos.writeBoolean(human);
    if (!human) {
      missions.saveMissionList(dos);
    }
  }
  
  /**
   * Calculate how many uncharted sectors is between start and end
   * @param sx Start X coordinate
   * @param sy Start Y coordinate
   * @param ex End X coordinate
   * @param ey End Y coordinate
   * @return Number of uncharted sector
   */
  private int calculateUnchartedLine(int sx, int sy, int ex, int ey) {
   double startX = sx;
   double startY = sy;
   double dx = Math.abs(startX-ex);
   double dy = Math.abs(startY-ey);
   // Calculate distance to end
   int distance = (int) dy;
   if (dx > dy) {
     distance = (int) dx;
   }
   int result = 0;
   double mx;
   double my;
   // Calculate how much move each round
   if (distance > 0) {
     mx = (ex-startX)/distance;
     my = (ey-startY)/distance;
   } else {
     mx = 0;
     my = 0;
   }
   // Moving loop
   for (int i=0;i<distance;i++) {
     startX = startX +mx;
     startY = startY +my;
     int nx = (int) Math.round(startX);
     int ny = (int) Math.round(startY);
     if (isValidCoordinate(nx, ny)) {
       if (mapData[nx][ny] == UNCHARTED) {
         result++;
       }
     }
   }
   return result;
 }

 /**
  * Get best sector to explore in this Solar system
  * @param sun Solar System
  * @param fleet Fleet doing the exploring
  * @return How many percentage is uncharted
  */
 public int getUnchartedValueSystem(Sun sun, Fleet fleet) {
   int unCharted = 0;
   int charted = 0;
   for (int x=-StarMapStatics.SOLARSYSTEMWIDTH-2; x <StarMapStatics.SOLARSYSTEMWIDTH+3;x++) {
     for (int y=-StarMapStatics.SOLARSYSTEMWIDTH-2; y <StarMapStatics.SOLARSYSTEMWIDTH+3;y++) {
       if (isValidCoordinate(sun.getCenterX()+x, sun.getCenterY()+y) && (x > 1 || x < -1 || y> 1 || y <-1) ) {
         if (mapData[sun.getCenterX()+x][sun.getCenterY()+y]==UNCHARTED) {
           unCharted++;
         } else {
           charted++;
         }
       }
     }
   }
   unCharted = 100*unCharted /(charted+unCharted);
   return unCharted;
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
    PathPoint[] points = new PathPoint[4];
    int[] bestPoint = new int[4];
    for (int i=0;i<points.length;i++) {
      points[i] = null;
    }
    int sector=0;
    for (int x=-StarMapStatics.SOLARSYSTEMWIDTH-2; x <StarMapStatics.SOLARSYSTEMWIDTH+3;x++) {
      for (int y=-StarMapStatics.SOLARSYSTEMWIDTH-2; y <StarMapStatics.SOLARSYSTEMWIDTH+3;y++) {
        if (x<=0 && y<= 0) {
          sector = 0;
        } else if (x>0 && y<= 0) {
          sector = 1;
        } else if (x<=0 && y> 0) {
          sector = 2;
        } else if (x>0 && y> 0) {
          sector = 3;
        }
        if (isValidCoordinate(sun.getCenterX()+x, sun.getCenterY()+y) && (x > 1 || x < -1 || y> 1 || y <-1) ) {
          if (mapData[sun.getCenterX()+x][sun.getCenterY()+y]==UNCHARTED) {
            unCharted[sector]++;
            PathPoint tempPoint = new PathPoint(sun.getCenterX()+x, 
                sun.getCenterY()+y, 
                StarMapUtilities.getDistance(fleet.getX(), fleet.getY(), 
                    sun.getCenterX()+x, sun.getCenterY()+y));
            int value =calculateUnchartedLine(fleet.getX(), fleet.getY(), sun.getCenterX()+x, sun.getCenterY()+y);
            if(points[sector] == null) {
              points[sector] = tempPoint;
              bestPoint[sector] = value;
            } else if (value > bestPoint[sector]) {
              points[sector] = tempPoint;
              bestPoint[sector] = value;
            }
          } else {
            charted[sector]++;
          }
        }
      }
    }
    for (int i=0;i<sectors.length;i++) {
      sectors[i] = 100*unCharted[i] /(charted[i]+unCharted[i]);
    }
    int unChartedValue = (sectors[0]+sectors[1]+sectors[2]+sectors[3])/4;
    if (unChartedValue < 20) {
      return null;
    }
    int pathValue = 0;
    int resultValue = 0;
    for (sector = 0;sector < 4;sector++) {
      int mx = 0;
      int my = 0;
      switch (sector) {
      case 0: { mx = -1; my = -1; break; }
      case 1: { mx = 1; my = -1; break; }
      case 2: { mx = -1; my = 1; break; }
      case 3: { mx = 1; my = 1; break; }
      }
      PathPoint temp=null;
      if (sectors[sector] > 60) {
        int nx = sun.getCenterX();
        int ny = sun.getCenterY();
        nx = nx+mx;
        ny = ny+my;      
        for (int i=0;i<StarMapStatics.SOLARSYSTEMWIDTH+2;i++) {
          nx = nx+mx;
          ny = ny+my;
          double dist = StarMapUtilities.getDistance(fleet.getX(), fleet.getY(), nx, ny);
          if (isValidCoordinate(nx, ny) && i>=scan && dist > 1) {
            if (mapData[nx][ny]==UNCHARTED) {
              temp = new PathPoint(nx, ny, dist);
              pathValue =calculateUnchartedLine(fleet.getX(), fleet.getY(), nx, ny);
              break;
            }
          }
          dist = StarMapUtilities.getDistance(fleet.getX(), fleet.getY(), sun.getCenterX(), ny);
          if (temp == null  && isValidCoordinate(sun.getCenterX(), ny) && i>=scan && dist > 1) {
            if (mapData[sun.getCenterX()][ny]==UNCHARTED) {
              temp = new PathPoint(sun.getCenterX(), ny, dist);
              pathValue =calculateUnchartedLine(fleet.getX(), fleet.getY(), sun.getCenterX(), ny);
              break;
            }
          }
          dist = StarMapUtilities.getDistance(fleet.getX(), fleet.getY(), nx, sun.getCenterY());
          if (temp == null  && isValidCoordinate(nx, sun.getCenterY()) && i>=scan && dist > 1) {
            if (mapData[nx][sun.getCenterY()]==UNCHARTED) {
              temp = new PathPoint(nx, sun.getCenterY(), dist);
              pathValue =calculateUnchartedLine(fleet.getX(), fleet.getY(), nx, sun.getCenterY());
              break;
            }
          }
        }
      }
      if (temp == null && points[sector] != null && points[sector].getDistance()>1) {
        temp = points[sector];
        pathValue = bestPoint[sector];
      }
      if (result == null && temp != null) {
        result = temp;
        resultValue = pathValue;
      }
      if (temp != null &&result != null && pathValue > resultValue) {
        result = temp;
        resultValue = pathValue;
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
   * @param maximumX Map size in X axel
   * @param maximumY Map size in Y axel
   */
  public void initMapData(int maximumX, int maximumY) {
    this.maxX = maximumX;
    this.maxY = maximumY;
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
   * @param value cloaking detection value
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
   * Get count of Ship stats name starts with
   * @param name for search
   * @return Number of ship stats which start with that
   */
  public int getShipStatStartsWith(String name) {
    int result = 0;
    for (ShipStat stat : shipStatList) {
      if (stat.getDesign().getName().startsWith(name)) {
        result++;
      }
    }
    return result;
  }
  
  /**
  * Get Ship Stat list as a fixed array
  * @return Ship Stat array
  */
  public ShipStat[] getShipStatList() {
    return shipStatList.toArray(new ShipStat[shipStatList.size()]);
  }

  /**
  * Get Ship Stat list as a fixed array but in alphabetical order
  * @return Ship Stat array
  */
  public ShipStat[] getShipStatListInOrder() {
    @SuppressWarnings("unchecked")
    ArrayList<ShipStat> orderList = (ArrayList<ShipStat>) shipStatList.clone();
    Collections.sort(orderList);
    return orderList.toArray(new ShipStat[orderList.size()]);
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(empireName);
    sb.append(" - ");
    if (isHuman()) {
      sb.append("Human");
    } else {
      sb.append("AI");
    }
    sb.append("\n");
    sb.append(race.toString());
    return sb.toString();
  }

  
  
}
