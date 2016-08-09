package org.openRealmOfStars.mapTiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

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
 * Class for handling star map tiles
 *
 */

public class Tiles {

  /**
   * List of tiles
   */
  private static ArrayList<Tile> listOfTiles;
  private static HashMap<String, Tile> hashOfTiles;
  
  /**
   * Get tile with index. Initializes tiles if they are uninitialized
   * @param index for tile
   * @return Tile Always returns a tile, If not found tile then first index is 
   * returned.
   */
  public static Tile getTileByIndex(int index) {
    if (listOfTiles == null) {
      initTiles();
    }
    if (index > 0 && index < listOfTiles.size()) {
      return listOfTiles.get(index);
    }
    return listOfTiles.get(0);
  }

  /**
   * Get tile by name. Initializes tiles if they are unitialized.
   * @param name For search the tile
   * @return Always returns a tile if not found tile then first index is
   * returned.
   */
  public static Tile getTileByName(String name) {
    if (hashOfTiles == null) {
      initTiles();
    }
    Tile tile = hashOfTiles.get(name);
    if (tile == null) {
      return getTileByIndex(0);
    } 
    return tile;
  }
  
  /**
   * Get Maximum count of tiles
   * @return Maximum count of tiles
   */
  public static int getMaxTiles() {
    if (listOfTiles == null) {
      initTiles();
    }
    return listOfTiles.size();
  }
  
  /**
   * Add new tile to Tiles list and map
   * @param tile Tile to add
   */
  public static void addTile(Tile tile) {
    listOfTiles.add(tile);
    tile.setIndex(listOfTiles.size()-1);
    hashOfTiles.put(tile.getName(), tile);
  }
  
  private static void initTiles() {
    BufferedImage tilesImage = IOUtilities.loadImage(Tiles.class.getResource(
        "/resources/images/maptiles.png"));
    listOfTiles = new ArrayList<>();
    hashOfTiles = new HashMap<>();
    Tile tile = new Tile(tilesImage, 0, 0,TileNames.EMPTY);
    addTile(tile);
    // Reading the sun 3x3
    tile = new Tile(tilesImage, 0, 0,TileNames.SUN_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 0,TileNames.SUN_N);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 0,TileNames.SUN_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 1,TileNames.SUN_W);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 1,TileNames.SUN_C);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 1,TileNames.SUN_E);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 2,TileNames.SUN_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 2,TileNames.SUN_S);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 2,TileNames.SUN_SE);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 3, 0,TileNames.GAS_GIANT_1_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 0,TileNames.GAS_GIANT_1_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 1,TileNames.GAS_GIANT_1_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 1,TileNames.GAS_GIANT_1_SE);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 5, 0,TileNames.GAS_GIANT_2_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 0,TileNames.GAS_GIANT_2_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 1,TileNames.GAS_GIANT_2_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 1,TileNames.GAS_GIANT_2_SE);
    addTile(tile);
    // Reading the rock planet 
    tile = new Tile(tilesImage, 7, 0,TileNames.ROCK1);
    addTile(tile);
    // Reading the water planet 
    tile = new Tile(tilesImage, 8, 0,TileNames.WATERWORLD1);
    addTile(tile);
    // Reading the fog of war 
    tile = new Tile(tilesImage, 9, 0,TileNames.FOG_OF_WAR);
    addTile(tile);
    // Reading the uncharted 
    tile = new Tile(tilesImage, 10, 0,TileNames.UNCHARTED);
    addTile(tile);
    // Reading the culture tiles
    tile = new Tile(tilesImage, 3, 2,TileNames.MECHIONS);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 2,TileNames.SPORKS);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 2,TileNames.HUMANS);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 2,TileNames.CENTAURS);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 3,TileNames.GREYANS);
    addTile(tile);
  }
  

}
