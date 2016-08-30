package org.openRealmOfStars.starMap;

import java.util.ArrayList;

import org.openRealmOfStars.AI.PlanetHandling.PlanetHandling;
import org.openRealmOfStars.AI.Research.Research;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.Planet;
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
 * Class planet
 * 
 */
public class StarMap {

  
  /**
   * Star map's maximum X coordinate
   */
  private int maxX;
  /**
   * Star map's maximum Y coordinate
   */
  private int maxY;
  
  /**
   * Tiles to show. Contains only planets, suns, asteroids etc.
   */
  private int tiles[][];
  
  private SquareInfo tileInfo[][];
  
  /**
   * Culture level for each sector
   */
  private CulturePower culture[][];
  
  /**
   * Solar system width
   */
  private final static int SOLARSYSTEMWIDTH = 7;
  
  /**
   * Cursor X coordinate
   */
  private int cursorX;
  /**
   * Cursor Y coordinate
   */
  private int cursorY;
  
  /**
   * Draw X coordinate
   */
  private int drawX;
  /**
   * Draw Y Coordinate
   */
  private int drawY;
  
  /**
   * List of suns in starmap
   */
  private ArrayList<Sun> sunList;

  /**
   * List of planets in starmap
   */
  private ArrayList<Planet> planetList;

  /**
   * List of players
   */
  private PlayerList players;
  
  /**
   * Game turn
   */
  private int turn;
  
  /**
   * Fleet tiles on map
   */
  private FleetTileInfo[][] fleetTiles;
  /**
   * Constructor for StarMap. Generates universum with default settings.
   * @param maxXSize
   * @param maxYSize
   */
  public StarMap(int maxXSize, int maxYSize,PlayerList players) {
    maxX = maxXSize;
    maxY = maxYSize;
    this.players = players;
    this.players.initVisibilityMaps(maxX, maxY);
    drawX = 0;
    drawY = 0;
    tiles = new int[maxX][maxY];
    tileInfo = new SquareInfo[maxX][maxY];
    fleetTiles = new FleetTileInfo[maxX][maxY];
    culture = new CulturePower[maxX][maxY];
    sunList = new ArrayList<>();
    planetList = new ArrayList<>();
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        tiles[i][j] = empty.getIndex();
        tileInfo[i][j] = SquareInfo.EMPTY_TILE;
        culture[i][j] = new CulturePower();
      }
    }
    turn = 0;
    // First starting Systems
    int sx = SOLARSYSTEMWIDTH;
    int sy = SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,5,2,0);
    sx = maxX/2;
    sy = SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0);
    sx = maxX-SOLARSYSTEMWIDTH;
    sy = SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,1,1);
    sx = SOLARSYSTEMWIDTH;
    sy = maxY/2;
    createSolarSystem(sx,sy,3,0);
    sx = maxX-SOLARSYSTEMWIDTH;
    sy = maxY/2;
    createSolarSystem(sx,sy,3,0);
    sx = SOLARSYSTEMWIDTH;
    sy = maxY-SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0,2);
    sx = maxX/2;
    sy = maxY-SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0);
    sx = maxX-SOLARSYSTEMWIDTH;
    sy = maxY-SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0,3);
  }

  /**
   * Check if coordinates are valid for this StarMap
   * @param x X coordinate
   * @param y y coordinate
   * @return true if valid and false if invalid
   */
  public boolean isValidCoordinate(int x, int y) {
    if (x >= 0 && y >= 0 && x < maxX && y<maxY ) {
      return true;
    }
    return false;
  }
  
  /**
   * Checks if 3x3 area is empty around the coordinate
   * @param x X coordinate
   * @param y Y coordinate
   * @return true if all are empty false otherwise
   */
  private boolean is9NeighboursEmpty(int x, int y) {
    boolean result = true;
    for (int i=-1;i<2;i++) {
      for (int j=-1;j<2;j++) {
        if (isValidCoordinate(x+i, y+j) && tiles[x+i][y+j]==0) {
          result = true;
        } else {
          return false;
        }
      }
    }
    return result;
  }

  /**
   * Checks if 4x4 area is empty around the coordinate.
   * Note this is not centered. This is more closer
   * on leftupper corner but not in the corner.
   * @param x X coordinate
   * @param y Y coordinate
   * @return true if all are empty false otherwise
   */
  private boolean is16NeighboursEmpty(int x, int y) {
    boolean result = true;
    for (int i=-1;i<4;i++) {
      for (int j=-1;j<4;j++) {
        if (isValidCoordinate(x+i, y+j) && tiles[x+i][y+j]==0) {
          result = true;
        } else {
          return false;
        }
      }
    }
    return result;
  }


  /**
   * Create Solar System
   * @param sx Sun's about coordinates
   * @param sy Sun's about coordinates
   * @param numberOfPlanets Number of planets to Solar System
   * @param numberOfGasGiants Number of Gas Giants to Solar System
   */
  private void createSolarSystem(int sx,int sy, int numberOfPlanets, 
      int numberOfGasGiants) {
    createSolarSystem(sx, sy, numberOfPlanets, numberOfGasGiants,-1);
  }

  
  /**
   * Create Solar System
   * @param sx Sun's about coordinates
   * @param sy Sun's about coordinates
   * @param numberOfPlanets Number of planets to Solar System
   * @param numberOfGasGiants Number of Gas Giants to Solar System
   * @param playerIndex if Player index is else than -1 then SolarSystem
   * is created as home system for that player index.
   */
  private void createSolarSystem(int sx,int sy, int numberOfPlanets, 
      int numberOfGasGiants,int playerIndex) {
    if (numberOfPlanets > 5) {
      numberOfPlanets = 5;
    }
    if (numberOfGasGiants > 2) {
      numberOfGasGiants = 2;
    }
    // The Sun
    sx = sx +DiceGenerator.getRandom(-1, 1);
    sy = sy +DiceGenerator.getRandom(-1, 1);
    Sun sun = new Sun(sx, sy, null);
    sunList.add(sun);
    int sunNumber = sunList.size()-1;
    SquareInfo info = new SquareInfo(SquareInfo.TYPE_SUN, sunNumber);
    tileInfo[sx-1][sy-1] = info;
    tileInfo[sx][sy-1] = info;
    tileInfo[sx+1][sy-1] = info;
    tileInfo[sx-1][sy] = info;
    tileInfo[sx][sy] = info;
    tileInfo[sx+1][sy] = info;
    tileInfo[sx-1][sy+1] = info;
    tileInfo[sx][sy+1] = info;
    tileInfo[sx+1][sy+1] = info;
    tiles[sx][sy] = Tiles.getTileByName(TileNames.SUN_C).getIndex();
    tiles[sx-1][sy-1] = Tiles.getTileByName(TileNames.SUN_NW).getIndex();
    tiles[sx][sy-1] = Tiles.getTileByName(TileNames.SUN_N).getIndex();
    tiles[sx+1][sy-1] = Tiles.getTileByName(TileNames.SUN_NE).getIndex();
    tiles[sx-1][sy] = Tiles.getTileByName(TileNames.SUN_W).getIndex();
    tiles[sx+1][sy] = Tiles.getTileByName(TileNames.SUN_E).getIndex();
    tiles[sx-1][sy+1] = Tiles.getTileByName(TileNames.SUN_SW).getIndex();
    tiles[sx][sy+1] = Tiles.getTileByName(TileNames.SUN_S).getIndex();
    tiles[sx+1][sy+1] = Tiles.getTileByName(TileNames.SUN_SE).getIndex();
    int planets = 0;
    while (planets < numberOfPlanets) {
      int px = sx +DiceGenerator.getRandom(-SOLARSYSTEMWIDTH, SOLARSYSTEMWIDTH);
      int py = sy +DiceGenerator.getRandom(-SOLARSYSTEMWIDTH, SOLARSYSTEMWIDTH);
      if (is9NeighboursEmpty(px, py)) {
        planets++;
        Planet planet = new Planet(px,py,sun.getName(),planets,false);
        planet.setPlanetType(DiceGenerator.
            getRandom(Planet.PLANET_IMAGE_INDEX.length-1));
        if (planets == 1) {
          if (playerIndex != -1) {
            PlayerInfo playerInfo = players.getPlayerInfoByIndex(playerIndex);
            Message msg = new Message(MessageType.PLANETARY,
                playerInfo.getEmpireName()+" starts at "+planet.getName()+".",
                Icons.getIconByName(Icons.ICON_CULTURE));
            msg.setCoordinate(planet.getX(),planet.getY());
            msg.setMatchByString(planet.getName());
            playerInfo.getMsgList().addNewMessage(msg);
            planet.setPlanetOwner(playerIndex,playerInfo);
            planet.setRadiationLevel(1);
            planet.setGroundSize(12);
            planet.setAmountMetalInGround(8000);
            planet.addBuilding(BuildingFactory.createByName("Space port"));
            if (playerInfo.getRace() == SpaceRace.MECHIONS) {
              planet.setWorkers(Planet.FOOD_FARMERS, 0);
              planet.setWorkers(Planet.METAL_MINERS, 0);
              planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
              planet.setWorkers(Planet.RESEARCH_SCIENTIST, 2);
              planet.setWorkers(Planet.CULTURE_ARTIST, 0);
            } else {
              planet.setWorkers(Planet.FOOD_FARMERS, 1);
              planet.setWorkers(Planet.METAL_MINERS, 0);
              planet.setWorkers(Planet.PRODUCTION_WORKERS, 1);
              planet.setWorkers(Planet.RESEARCH_SCIENTIST, 1);
              planet.setWorkers(Planet.CULTURE_ARTIST, 0);
            }
            ShipStat[] stats = playerInfo.getShipStatList();
            for (ShipStat stat : stats) {
              Ship ship = new Ship(stat.getDesign());
              stat.setNumberOfBuilt(stat.getNumberOfBuilt()+1);
              stat.setNumberOfInUse(stat.getNumberOfInUse()+1);
              Fleet fleet = new Fleet(ship, planet.getX(), planet.getY());
              playerInfo.Fleets().add(fleet);
              msg = new Message(MessageType.FLEET,
                  fleet.getName()+" is waiting for orders.",
                  Icons.getIconByName(Icons.ICON_HULL_TECH));
              msg.setCoordinate(planet.getX(),planet.getY());
              msg.setMatchByString(fleet.getName());
              playerInfo.getMsgList().addNewMessage(msg);
            }
            
          }
        }
        planetList.add(planet);
        int planetNumber = planetList.size()-1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        tileInfo[px][py] = info;
        tiles[px][py] = planet.getPlanetImageIndex();
      }
    }
    int gasGiants = 0;
    while (gasGiants < numberOfGasGiants) {
      int px = sx +DiceGenerator.getRandom(-SOLARSYSTEMWIDTH, SOLARSYSTEMWIDTH);
      int py = sy +DiceGenerator.getRandom(-SOLARSYSTEMWIDTH, SOLARSYSTEMWIDTH);
      if (is16NeighboursEmpty(px, py)) {
        gasGiants++;
        Planet planet = new Planet(px,py,sun.getName(),planets+gasGiants,true);
        planet.setPlanetImageIndex(DiceGenerator.getRandom(1));
        planetList.add(planet);        
        int planetNumber = planetList.size()-1;
        info = new SquareInfo(SquareInfo.TYPE_GAS_PLANET, planetNumber);
        switch (planet.getPlanetImageIndex()) {
        case 0: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW).getIndex();
          tiles[px+1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE).getIndex();
          tiles[px][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW).getIndex();
          tiles[px+1][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE).getIndex();
          tileInfo[px][py] = info;
          tileInfo[px+1][py] = info;
          tileInfo[px][py+1] = info;
          tileInfo[px+1][py+1] = info;
          break; } 
        case 1: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NW).getIndex();
          tiles[px+1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NE).getIndex();
          tiles[px][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SW).getIndex();
          tiles[px+1][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SE).getIndex();
          tileInfo[px][py] = info;
          tileInfo[px+1][py] = info;
          tileInfo[px][py+1] = info;
          tileInfo[px+1][py+1] = info;
          break; }
        }
      }
    }
  }
  
  public int getMaxX() {
    return maxX;
  }

  public void setMaxX(int maxX) {
    this.maxX = maxX;
  }

  public int getMaxY() {
    return maxY;
  }

  public void setMaxY(int maxY) {
    this.maxY = maxY;
  }

  public int[][] getTiles() {
    return tiles;
  }

  /**
   * Get the fleet tiles from the map.
   * These fleet positions are always calculated
   * @return FleetTiles
   */
  public FleetTileInfo[][] getFleetTiles() {
    for (int x=0;x<maxX;x++) {
      for (int y=0;y<maxY;y++) {
        fleetTiles[x][y] = null;
      }
    }
    for (int i=0;i<players.getCurrentMaxPlayers();i++) {
      PlayerInfo player = players.getPlayerInfoByIndex(i);
      for (int j=0;j<player.Fleets().getNumberOfFleets();j++) {
        Fleet fleet = player.Fleets().getByIndex(j);
        FleetTileInfo info = new FleetTileInfo(
            fleet.getFirstShip().getHull().getRace(), 
            fleet.getFirstShip().getHull().getImageIndex(),i,j);
        fleetTiles[fleet.getX()][fleet.getY()] = info;
      }
    }
    return fleetTiles;
  }
  
  /**
   * Get cursor X coordinate
   * @return X coordinate
   */
  public int getCursorX() {
    return cursorX;
  }
  
  /**
   * Get cursor Y coordinate
   * @return Y coordinate
   */
  public int getCursorY() {
    return cursorY;
  }
  
  /**
   * Set cursor coordinates. Validates coordinate before sets it.
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setCursorPos(int x, int y) {
    if (isValidCoordinate(x, y)) {
      cursorX = x;
      cursorY = y;
    }
  }

  /**
   * Get Draw X coordinate
   * @return x coordinate
   */
  public int getDrawX() {
    return drawX;
  }

  /**
   * Get Draw Y coordinate
   * @return y coordinate
   */
  public int getDrawY() {
    return drawY;
  }
  
  /**
   * Set draw coordinates. Validates coordinate before sets it.
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setDrawPos(int x, int y) {
    if (isValidCoordinate(x, y)) {
      drawX = x;
      drawY = y;
    }    
  }
  
  /**
   * Get Sun by coordinates. If not found then null is returned.
   * @param x X coordinate
   * @param y Y coordinate
   * @return Sun or null
   */
  public Sun getSunByCoordinate(int x, int y) {
    if (isValidCoordinate(x, y)) {
      SquareInfo info = tileInfo[x][y];
      if (info.getType() == SquareInfo.TYPE_SUN) {
        return sunList.get(info.getValue());
      }
    }
    return null;
  }
  
  /**
   * Get Planet by coordinates. If not found then null is returned.
   * @param x X coordinate
   * @param y Y coordinate
   * @return Planet or null
   */
  public Planet getPlanetByCoordinate(int x, int y) {
    if (isValidCoordinate(x, y)) {
      SquareInfo info = tileInfo[x][y];
      if (info.getType() == SquareInfo.TYPE_PLANET || 
          info.getType() == SquareInfo.TYPE_GAS_PLANET) {
        return planetList.get(info.getValue());
      }
    }
    return null;
  }

  /**
   * Get Fleet by coordinates. If not found then null is returned.
   * @param x X coordinate
   * @param y Y coordinate
   * @return Fleet or null
   */
  public Fleet getFleetByCoordinate(int x, int y) {
    if (isValidCoordinate(x, y) && fleetTiles != null) {
      if (fleetTiles[x][y] != null) {
        int playerIndex = fleetTiles[x][y].getPlayerIndex();
        int fleetIndex = fleetTiles[x][y].getFleetIndex();
        return players.getPlayerInfoByIndex(playerIndex).Fleets().getByIndex(fleetIndex);
      }
    }
    return null;
  }

  /**
   * Fight with two fleets
   * @param x X coordinate
   * @param y Y coordinate
   * @return Combat or null
   */
  public Combat fightWithFleet(int x, int y,Fleet fleet1, PlayerInfo info1) {
    if (isValidCoordinate(x, y) && fleetTiles != null) {
      if (fleetTiles[x][y] != null) {
        int playerIndex = fleetTiles[x][y].getPlayerIndex();
        int fleetIndex = fleetTiles[x][y].getFleetIndex();
        PlayerInfo info2 = players.getPlayerInfoByIndex(playerIndex);
        if (info1 != info2) {
          Fleet fleet2 = info2.Fleets().getByIndex(fleetIndex);
          return new Combat(fleet1, fleet2, info1, info2);
        }
        
      }
    }
    return null;
  }

  /**
   * @return the turn
   */
  public int getTurn() {
    return turn;
  }

  /**
   * @param turn the turn to set
   */
  public void setTurn(int turn) {
    this.turn = turn;
  }
  
  /**
   * Make AI Turns
   */
  public void makeAITurns() {
    for (int i=0;i<players.getCurrentMaxPlayers();i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null && !info.isHuman()) {
        Research.handle(info);
        for (int j=0;j<planetList.size();j++) {
          Planet planet = planetList.get(j);
          if (planet.getPlanetPlayerInfo() == info) {
            PlanetHandling.handlePlanet(this, planet, i);
          }
        }
      }
    }
  }
  
  /**
   * Update whole star map to next turn
   */
  public void updateStarMapToNextTurn() {
    resetCulture();
    for (int i=0;i<players.getCurrentMaxPlayers();i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        info.resetVisibilityDataAfterTurn();
        info.getMsgList().clearMessages();
        for (int j = 0;j<info.Fleets().getNumberOfFleets();j++) {
          Fleet fleet = info.Fleets().getByIndex(j);
          if (fleet.getRoute() != null) {
            fleet.getRoute().makeNextMove();
            if (!isBlocked(fleet.getRoute().getX(), fleet.getRoute().getY())) {
              // Not blocked so fleet is moving
              fleet.setPos(fleet.getRoute().getX(), fleet.getRoute().getY());
              if (fleet.getRoute().isEndReached()) {
                // End is reached giving a message
                fleet.setRoute(null);
                Message msg = new Message(MessageType.FLEET,
                  fleet.getName()+" has reached it's target and waiting for orders.",
                  Icons.getIconByName(Icons.ICON_HULL_TECH));
                msg.setMatchByString(fleet.getName());
                msg.setCoordinate(fleet.getX(), fleet.getY());
                info.getMsgList().addNewMessage(msg);
              }
            } else {
              // Movement was blocked, giving a message
              fleet.setRoute(null);
              Message msg = new Message(MessageType.FLEET,
                fleet.getName()+" has encouter obstacle and waiting for more orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
              msg.setMatchByString(fleet.getName());
              msg.setCoordinate(fleet.getX(), fleet.getY());
              info.getMsgList().addNewMessage(msg);
            }
          } else {
            Message msg = new Message(MessageType.FLEET,
                fleet.getName()+" is waiting for orders.",
                Icons.getIconByName(Icons.ICON_HULL_TECH));
            msg.setMatchByString(fleet.getName());
            msg.setCoordinate(fleet.getX(), fleet.getY());
            info.getMsgList().addNewMessage(msg);
          }
          fleet.movesLeft = fleet.getFleetSpeed();
          doFleetScanUpdate(info, fleet,null);
        }
      }
    }
    for (int i=0;i<planetList.size();i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null) {
        PlayerInfo info = planet.getPlanetPlayerInfo();
        planet.updateOneTurn();
        int index =players.getIndex(info);
        if (index > -1) {
          calculateCulture(planet.getX(), planet.getY(), planet.getCulture(), index);
        }
        doFleetScanUpdate(info, null,planet);
      }
    }
    turn=turn+1;
  }
  
  /**
   * Do Fleet/Planet scan Update for starmap. Fleet or planet can be null
   * but only one should be null.
   * @param info Player who controls the fleet
   * @param fleet Fleet which is doing the rescan
   * @param planet Planet which is doing the rescan
   */
  public void doFleetScanUpdate(PlayerInfo info, Fleet fleet,Planet planet) {
    int scanRad=-1; 
    int cloakDetection=0; 
    int cx=0;
    int cy=0;
    if (fleet != null) {
      scanRad = fleet.getFleetScannerLvl();
      cloakDetection = fleet.getFleetCloakDetection();
      cx = fleet.getX();
      cy = fleet.getY();
    } else if (planet != null) {
      scanRad = planet.getScannerLvl();
      cloakDetection = planet.getCloakingDetectionLvl();
      cx = planet.getX();
      cy = planet.getY();
    }
    if (scanRad != -1) {
      for (int y=-scanRad;y<scanRad+1;y++) {
        for (int x=-scanRad;x<scanRad+1;x++) {
          drawVisibilityLine(info, cx, cy,cx+x, cy+y, cloakDetection, scanRad);
        }
      }
    }
  }
  
  /**
   * Update starmap when game starts
   */
  public void updateStarMapOnStartGame() {
    for (int i=0;i<players.getCurrentMaxPlayers();i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      if (info != null) {
        for (int j = 0;j<info.Fleets().getNumberOfFleets();j++) {
          Fleet fleet = info.Fleets().getByIndex(j);
          fleet.movesLeft = fleet.getFleetSpeed();
          doFleetScanUpdate(info, fleet,null);
        }
      }
    }
    for (int i=0;i<planetList.size();i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null) {        
        PlayerInfo info = planet.getPlanetPlayerInfo();
        int index =players.getIndex(info);
        if (index > -1) {
          calculateCulture(planet.getX(), planet.getY(), planet.getCulture(), index);
        }
        doFleetScanUpdate(info, null,planet);
      }
    }
  }
  
  
  /**
   * Calculate culture on map
   * @param cx Center of culture X coordinate
   * @param cy Center of culture Y coordinate
   * @param value Culture value
   * @param index Player index
   */
  private void calculateCulture(int cx, int cy, int value, int index) {
    String mask = null;
    if (value < 5) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"...............\n"+
             /*4*/"...............\n"+
             /*3*/"...............\n"+
             /*2*/"...............\n"+
             /*1*/".......X.......\n"+
             /*0*/"......XXX......\n"+
             /*1*/".......X.......\n"+
             /*2*/"...............\n"+
             /*3*/"...............\n"+
             /*4*/"...............\n"+
             /*5*/"...............\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 10) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"...............\n"+
             /*4*/"...............\n"+
             /*3*/"...............\n"+
             /*2*/"...............\n"+
             /*1*/"......XXX......\n"+
             /*0*/"......XXX......\n"+
             /*1*/"......XXX......\n"+
             /*2*/"...............\n"+
             /*3*/"...............\n"+
             /*4*/"...............\n"+
             /*5*/"...............\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 20) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"...............\n"+
             /*4*/"...............\n"+
             /*3*/"...............\n"+
             /*2*/".......X.......\n"+
             /*1*/"......XXX......\n"+
             /*0*/".....XXXXX.....\n"+
             /*1*/"......XXX......\n"+
             /*2*/".......X.......\n"+
             /*3*/"...............\n"+
             /*4*/"...............\n"+
             /*5*/"...............\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 40) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"...............\n"+
             /*4*/"...............\n"+
             /*3*/".......X.......\n"+
             /*2*/"......XXX......\n"+
             /*1*/".....XXXXX.....\n"+
             /*0*/"....XXXXXXX....\n"+
             /*1*/".....XXXXX.....\n"+
             /*2*/"......XXX......\n"+
             /*3*/".......X.......\n"+
             /*4*/"...............\n"+
             /*5*/"...............\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 80) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"...............\n"+
             /*4*/".......X.......\n"+
             /*3*/"......XXX......\n"+
             /*2*/".....XXXXX.....\n"+
             /*1*/"....XXXXXXX....\n"+
             /*0*/"...XXXXXXXXX...\n"+
             /*1*/"....XXXXXXX....\n"+
             /*2*/".....XXXXX.....\n"+
             /*3*/"......XXX......\n"+
             /*4*/".......X.......\n"+
             /*5*/"...............\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 160) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"...............\n"+
             /*4*/"......XXX......\n"+
             /*3*/".....XXXXX.....\n"+
             /*2*/"....XXXXXXX....\n"+
             /*1*/"...XXXXXXXXX...\n"+
             /*0*/"...XXXXXXXXX...\n"+
             /*1*/"...XXXXXXXXX...\n"+
             /*2*/"....XXXXXXX....\n"+
             /*3*/".....XXXXX.....\n"+
             /*4*/"......XXX......\n"+
             /*5*/"...............\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 320) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"...............\n"+
             /*5*/"......XXX......\n"+
             /*4*/".....XXXXX.....\n"+
             /*3*/"....XXXXXXX....\n"+
             /*2*/"...XXXXXXXXX...\n"+
             /*1*/"..XXXXXXXXXXX..\n"+
             /*0*/"..XXXXXXXXXXX..\n"+
             /*1*/"..XXXXXXXXXXX..\n"+
             /*2*/"...XXXXXXXXX...\n"+
             /*3*/"....XXXXXXX....\n"+
             /*4*/".....XXXXX.....\n"+
             /*5*/"......XXX......\n"+
             /*6*/"...............\n"+
             /*7*/"...............\n";
    } else if (value < 640) {
      //           765432101234567
      mask = /*7*/"...............\n"+
             /*6*/"......XXX......\n"+
             /*5*/".....XXXXX.....\n"+
             /*4*/"....XXXXXXX....\n"+
             /*3*/"...XXXXXXXXX...\n"+
             /*2*/"..XXXXXXXXXXX..\n"+
             /*1*/".XXXXXXXXXXXXX.\n"+
             /*0*/".XXXXXXXXXXXXX.\n"+
             /*1*/".XXXXXXXXXXXXX.\n"+
             /*2*/"..XXXXXXXXXXX..\n"+
             /*3*/"...XXXXXXXXX...\n"+
             /*4*/"....XXXXXXX....\n"+
             /*5*/".....XXXXX.....\n"+
             /*6*/"......XXX......\n"+
             /*7*/"...............\n";
    } else if (value < 1280) {
      //           765432101234567
      mask = /*7*/".......X.......\n"+
             /*6*/"......XXX......\n"+
             /*5*/"....XXXXXXX....\n"+
             /*4*/"....XXXXXXX....\n"+
             /*3*/"..XXXXXXXXXXX..\n"+
             /*2*/"..XXXXXXXXXXX..\n"+
             /*1*/".XXXXXXXXXXXXX.\n"+
             /*0*/"XXXXXXXXXXXXXXX\n"+
             /*1*/".XXXXXXXXXXXXX.\n"+
             /*2*/"..XXXXXXXXXXX..\n"+
             /*3*/"..XXXXXXXXXXX..\n"+
             /*4*/"....XXXXXXX....\n"+
             /*5*/"....XXXXXXX....\n"+
             /*6*/"......XXX......\n"+
             /*7*/".......X.......\n";
    }

    
    String[] lines = mask.split("\n");
    int x = -7;
    int y = -7;
    for (int line = 0;line <lines.length;line++) {
      for (int col=0;col<lines[line].length();col++) {
        if (lines[line].charAt(col)=='X') {
          addSectorCulture(cx+x, cy+y, index, value);
        }
        x++;
      }
      x = -7;
      y++;
    }
  }
  
  /**
   * Reset culture information for whole map
   */
  private void resetCulture() {
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        culture[i][j].reset();
      }
    }
  }
  /**
   * Draw visibility line and set visibility info for one player
   * @param info PlayerInfo
   * @param sx Start X
   * @param sy Stary Y
   * @param ex End X
   * @param ey End Y
   * @param cloakDetection Cloaking Detection level
   * @param maxRad maximum radius 
   */
  private void drawVisibilityLine(PlayerInfo info, int sx, int sy, int ex, 
      int ey, int cloakDetection, int maxRad) {
    double startX = sx;
    double startY = sy;
    double dx = Math.abs(startX-ex);
    double dy = Math.abs(startY-ey);
    // Calculate distance to end
    int distance = (int) dy;
    if (dx > dy) {
      distance = (int) dx;
    }
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
    int detectValue = cloakDetection;
    info.setSectorVisibility(sx, sy, PlayerInfo.VISIBLE);
    if (detectValue > 0){
      info.setSectorCloakingDetection(sx, sy, detectValue);
    }
    // Moving loop
    for (int i=0;i<distance;i++) {
      startX = startX +mx;
      startY = startY +my;
      int nx = (int) Math.round(startX);
      int ny = (int) Math.round(startY);
      if (getDistance(sx, sy, nx, ny) > maxRad) {
        // We have moved to maximum radius
        break;
      }
      if (isValidCoordinate(nx, ny)) {
        info.setSectorVisibility(nx, ny, PlayerInfo.VISIBLE);
        if (detectValue > 0){
          info.setSectorCloakingDetection(nx, ny, detectValue);
        }
        if (tileInfo[nx][ny].isVisibilityBlocked()) {
          // There is something that blocks the vision
          break;
        }
        if (detectValue > 0) {
          detectValue=detectValue-10;
        }
      }
    }
  }
  
  /**
   * Get total production for one player per turn for certain production
   * @param production See Planet.PRODUCTION_*
   * @param playerIndex Player index to match
   * @return total production per turn
   */
  public int getTotalProductionByPlayerPerTurn(int production, int playerIndex) {
    int result =0;
    for (int i=0;i<planetList.size();i++) {
      Planet planet = planetList.get(i);
      if (planet.getPlanetPlayerInfo() != null &&
          planet.getPlanetOwnerIndex()==playerIndex) {
        result = result +planet.getTotalProduction(production);
      }
    }
    return result;
  }
  
  /**
   * Get current player info
   * @return PlayerInfo or null
   */
  public PlayerInfo getCurrentPlayerInfo() {
    if (players != null) {
      return players.getCurrentPlayerInfo();
    } else {
      return null;
    }
  }
  
  /**
   * Change to next player
   */
  public void nextPlayer() {
    if (players != null) {
      if (players.getCurrentPlayer()+1 == players.getCurrentMaxPlayers()) {
        players.setCurrentPlayer(0);
      } else {
        players.setCurrentPlayer(players.getCurrentPlayer()+1);
      }
    }
  }

  /**
   * Get tile info from coordinate
   * @param x
   * @param y
   * @return TileInfo
   */
  public SquareInfo getTileInfo(int x, int y) {
    if (isValidCoordinate(x, y)) {
      return tileInfo[x][y];
    } else {
      return null;
    }
  }

  /**
   * Get culture power
   * @param x
   * @param y
   * @return Culture power or null
   */
  public CulturePower getSectorCulture(int x, int y) {
    if (isValidCoordinate(x, y)) {
      return culture[x][y];
    } else {
      return null;
    }
  }

  /**
   * Add culture power
   * @param x X coordinate
   * @param y Y Coordinate
   * @param index Player index from player list
   * @param value Culture value to add
   */
  public void addSectorCulture(int x, int y,int index,int value) {
    if (isValidCoordinate(x, y)) {
      culture[x][y].addCulture(index, value);
    }
  }

  /**
   * Is tile blocked or not
   * @param x X coordinate
   * @param y Y Coordinate
   * @return true if tile is blocked otherwise false. Also if
   * coordinate is out of map then true is returned.
   */
  public boolean isBlocked(int x, int y) {
    SquareInfo sector = getTileInfo(x, y);
    if (sector != null) {
      return sector.isBlocked();
    }
    return true;
  }
  /**
   * Calculate distance between two coordinates
   * @param x1 first coordinate's X
   * @param y1 first coordinate's Y
   * @param x2 second coordinate's X
   * @param y2 second coordinate's Y
   * @return distance as double
   */
  public static double getDistance(int x1,int y1, int x2, int y2) {
    double result = 0;
    int mx = Math.abs(x2-x1);
    int my = Math.abs(y2-y1);
    result = Math.sqrt(mx*mx+my*my);
    return result;
  }
  
  /**
   * Get Player info by index
   * @param index Player index
   * @return PlayerInfo or null
   */
  public PlayerInfo getPlayerByIndex(int index) {
    return players.getPlayerInfoByIndex(index);
  }

}
