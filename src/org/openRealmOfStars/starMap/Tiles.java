package org.openRealmOfStars.starMap;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.Utilities.IOUtilities;


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
  
  /**
   * Get tile with index. Initializes tiles if they are uninitialized
   * @param index for tile
   * @return Tile Always returns a tile, If not found tile at first index is 
   * returned
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
  
  private static void initTiles() {
    BufferedImage tilesImage = IOUtilities.loadImage(Tiles.class.getResource(
        "/resources/images/maptiles.png"));
    listOfTiles = new ArrayList<>();
    // Reading the sun 3x3
    Tile tile = new Tile(tilesImage, 0, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 1, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 2, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 0, 1);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 1, 1);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 2, 1);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 0, 2);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 1, 2);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 2, 2);
    listOfTiles.add(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 3, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 4, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 3, 1);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 4, 1);
    listOfTiles.add(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 5, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 6, 0);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 5, 1);
    listOfTiles.add(tile);
    tile = new Tile(tilesImage, 6, 1);
    listOfTiles.add(tile);
    // Reading the rock planet 
    tile = new Tile(tilesImage, 8, 0);
    listOfTiles.add(tile);
    // Reading the water planet 
    tile = new Tile(tilesImage, 9, 0);
    listOfTiles.add(tile);
  }
  

}
