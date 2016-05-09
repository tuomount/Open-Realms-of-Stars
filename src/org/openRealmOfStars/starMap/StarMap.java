package org.openRealmOfStars.starMap;

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
  
  /**
   * Solar system width
   */
  private final static int SOLARSYSTEMWIDTH = 7;
  
  /**
   * Constructor for StarMap. Generates universum with default settings.
   * @param maxXSize
   * @param maxYSize
   */
  public StarMap(int maxXSize, int maxYSize) {
    maxX = maxXSize;
    maxY = maxYSize;
    tiles = new int[maxX][maxY];
    Tile empty = Tiles.getTileByName(TileNames.EMPTY);
    for (int i=0;i<maxX;i++) {
      for (int j=0;j<maxY;j++) {
        tiles[i][j] = empty.getIndex();
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
  
  private void createSolarSystem(int sx,int sy, int numberOfPlanets, 
      int numberOfGasGiants) {
    if (numberOfPlanets > 5) {
      numberOfPlanets = 5;
    }
    if (numberOfGasGiants > 2) {
      numberOfPlanets = 2;
    }
    // The Sun
    sx = sx +DiceGenerator.getRandom(-1, 1);
    sy = sy +DiceGenerator.getRandom(-1, 1);
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
      if (isValidCoordinate(px, py) && Tiles.getTileByIndex(tiles[px][py]).
          getName().equalsIgnoreCase(TileNames.EMPTY)) {
        switch (DiceGenerator.getRandom(1)) {
        case 0: {
          tiles[px][py] = Tiles.getTileByName(TileNames.ROCK1).getIndex();
          break; } 
        case 1: {
          tiles[px][py] = Tiles.getTileByName(TileNames.WATERWORLD1).getIndex();
          break; }
        }
        planets++;
      }
    }
    int gasGiants = 0;
    while (gasGiants < numberOfGasGiants) {
      int px = sx +DiceGenerator.getRandom(-SOLARSYSTEMWIDTH, SOLARSYSTEMWIDTH);
      int py = sy +DiceGenerator.getRandom(-SOLARSYSTEMWIDTH, SOLARSYSTEMWIDTH);
      if (isValidCoordinate(px, py) && Tiles.getTileByIndex(tiles[px][py]).
          getName().equalsIgnoreCase(TileNames.EMPTY) &&
          isValidCoordinate(px+1, py) && Tiles.getTileByIndex(tiles[px+1][py]).
          getName().equalsIgnoreCase(TileNames.EMPTY) &&
          isValidCoordinate(px, py+1) && Tiles.getTileByIndex(tiles[px][py+1]).
          getName().equalsIgnoreCase(TileNames.EMPTY) &&
          isValidCoordinate(px+1, py+1) && Tiles.getTileByIndex(tiles[px+1][py+1]).
          getName().equalsIgnoreCase(TileNames.EMPTY)) {
        switch (DiceGenerator.getRandom(1)) {
        case 0: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW).getIndex();
          tiles[px+1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE).getIndex();
          tiles[px][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW).getIndex();
          tiles[px+1][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE).getIndex();
          break; } 
        case 1: {
          tiles[px][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NW).getIndex();
          tiles[px+1][py] = Tiles.getTileByName(TileNames.GAS_GIANT_2_NE).getIndex();
          tiles[px][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SW).getIndex();
          tiles[px+1][py+1] = Tiles.getTileByName(TileNames.GAS_GIANT_2_SE).getIndex();
          break; }
        }
        gasGiants++;
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

  
  
}
