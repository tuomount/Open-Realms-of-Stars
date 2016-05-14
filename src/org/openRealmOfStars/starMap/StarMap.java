package org.openRealmOfStars.starMap;

import java.util.ArrayList;

import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
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
   * Constructor for StarMap. Generates universum with default settings.
   * @param maxXSize
   * @param maxYSize
   */
  public StarMap(int maxXSize, int maxYSize) {
    maxX = maxXSize;
    maxY = maxYSize;
    drawX = 0;
    drawY = 0;
    tiles = new int[maxX][maxY];
    tileInfo = new SquareInfo[maxX][maxY];
    sunList = new ArrayList<>();
    planetList = new ArrayList<>();
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        tiles[i][j] = empty.getIndex();
        tileInfo[i][j] = SquareInfo.EMPTY_TILE;
      }
    }
    // First starting Systems
    int sx = SOLARSYSTEMWIDTH;
    int sy = SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,5,2);
    sx = maxX/2;
    sy = SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0);
    sx = maxX-SOLARSYSTEMWIDTH;
    sy = SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,1);
    sx = SOLARSYSTEMWIDTH;
    sy = maxY/2;
    createSolarSystem(sx,sy,3,0);
    sx = maxX-SOLARSYSTEMWIDTH;
    sy = maxY/2;
    createSolarSystem(sx,sy,3,0);
    sx = SOLARSYSTEMWIDTH;
    sy = maxY-SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0);
    sx = maxX/2;
    sy = maxY-SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0);
    sx = maxX-SOLARSYSTEMWIDTH;
    sy = maxY-SOLARSYSTEMWIDTH;
    createSolarSystem(sx,sy,3,0);
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
        planet.setPlanetImageIndex(Planet.PLANET_IMAGE_INDEX[DiceGenerator.
                                getRandom(Planet.PLANET_IMAGE_INDEX.length-1)]);
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
        planetList.add(planet);
        int planetNumber = planetList.size()-1;
        info = new SquareInfo(SquareInfo.TYPE_PLANET, planetNumber);
        switch (DiceGenerator.getRandom(1)) {
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
      if (info.getType() == SquareInfo.TYPE_PLANET) {
        return planetList.get(info.getValue());
      }
    }
    return null;
  }
  
}
