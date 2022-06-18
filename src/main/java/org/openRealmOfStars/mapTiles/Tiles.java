package org.openRealmOfStars.mapTiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022  Tuomo Untinen
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

public final class Tiles {

  /**
   * Hiding the constructor
   */
  private Tiles() {
    // Nothing to do
  }

  /**
   * List of tiles
   */
  private static ArrayList<Tile> listOfTiles;
  /**
   * Hash map for tile
   */
  private static HashMap<String, Tile> hashOfTiles;

  /**
   * Get tile with index. Initializes tiles if they are uninitialized
   * @param index for tile
   * @return Tile Always returns a tile, If not found tile then first index is
   * returned.
   */
  public static Tile getTileByIndex(final int index) {
    if (listOfTiles == null) {
      initTiles();
    }
    if (index > 0 && index < listOfTiles.size()) {
      return listOfTiles.get(index);
    }
    return listOfTiles.get(0);
  }

  /**
   * Get tile by name. Initializes tiles if they are uninitialized.
   * @param name For search the tile
   * @return Always returns a tile if not found tile then first index is
   * returned.
   */
  public static Tile getTileByName(final String name) {
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
   * Get sun tiles by name. Initializes tiles if they are uninitialized.
   * @param name For search the tile
   * @param type There are three types of sun 0,1,2
   * @return Always returns a tile if not found tile then first index is
   * returned.
   */
  public static Tile getSunTile(final String name, final int type) {
    if (hashOfTiles == null) {
      initTiles();
    }
    Tile tile = null;
    if (type == 1) {
      if (name.equals(TileNames.SUN_C)) {
        tile = getTileByName(TileNames.BLUE_STAR_C);
      }
      if (name.equals(TileNames.SUN_NW)) {
        tile = getTileByName(TileNames.BLUE_STAR_NW);
      }
      if (name.equals(TileNames.SUN_N)) {
        tile = getTileByName(TileNames.BLUE_STAR_N);
      }
      if (name.equals(TileNames.SUN_NE)) {
        tile = getTileByName(TileNames.BLUE_STAR_NE);
      }
      if (name.equals(TileNames.SUN_W)) {
        tile = getTileByName(TileNames.BLUE_STAR_W);
      }
      if (name.equals(TileNames.SUN_E)) {
        tile = getTileByName(TileNames.BLUE_STAR_E);
      }
      if (name.equals(TileNames.SUN_SW)) {
        tile = getTileByName(TileNames.BLUE_STAR_SW);
      }
      if (name.equals(TileNames.SUN_S)) {
        tile = getTileByName(TileNames.BLUE_STAR_S);
      }
      if (name.equals(TileNames.SUN_SE)) {
        tile = getTileByName(TileNames.BLUE_STAR_SE);
      }
    } else if (type == 2) {
      if (name.equals(TileNames.SUN_C)) {
        tile = getTileByName(TileNames.STAR_C);
      }
      if (name.equals(TileNames.SUN_NW)) {
        tile = getTileByName(TileNames.STAR_NW);
      }
      if (name.equals(TileNames.SUN_N)) {
        tile = getTileByName(TileNames.STAR_N);
      }
      if (name.equals(TileNames.SUN_NE)) {
        tile = getTileByName(TileNames.STAR_NE);
      }
      if (name.equals(TileNames.SUN_W)) {
        tile = getTileByName(TileNames.STAR_W);
      }
      if (name.equals(TileNames.SUN_E)) {
        tile = getTileByName(TileNames.STAR_E);
      }
      if (name.equals(TileNames.SUN_SW)) {
        tile = getTileByName(TileNames.STAR_SW);
      }
      if (name.equals(TileNames.SUN_S)) {
        tile = getTileByName(TileNames.STAR_S);
      }
      if (name.equals(TileNames.SUN_SE)) {
        tile = getTileByName(TileNames.STAR_SE);
      }
    } else {
      tile = getTileByName(name);
    }
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
  public static void addTile(final Tile tile) {
    listOfTiles.add(tile);
    tile.setIndex(listOfTiles.size() - 1);
    hashOfTiles.put(tile.getName(), tile);
  }

  /**
   * Black hole indexing 1st part start
   */
  private static int bhFirstStart;
  /**
   * Black hole indexing 1st part END
   */
  private static int bhFirstEnd;
  /**
   * Black hole indexing 2nd part start
   */
  private static int bhSecondStart;
  /**
   * Black hole indexing 2nd part END
   */
  private static int bhSecondEnd;
  /**
   * Initialize tiles
   */
  private static void initTiles() {
    BufferedImage tilesImage = IOUtilities
        .loadImage(Tiles.class.getResource("/resources/images/maptiles.png"));
    listOfTiles = new ArrayList<>();
    hashOfTiles = new HashMap<>();
    Tile tile = new Tile(tilesImage, 0, 0, TileNames.EMPTY);
    addTile(tile);
    // Reading the sun 3x3
    tile = new Tile(tilesImage, 0, 0, TileNames.SUN_NW);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 0, TileNames.SUN_N);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 0, TileNames.SUN_NE);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 1, TileNames.SUN_W);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 1, TileNames.SUN_C);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 1, TileNames.SUN_E);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 2, TileNames.SUN_SW);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 2, TileNames.SUN_S);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 2, TileNames.SUN_SE);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 3, 0, TileNames.GAS_GIANT_1_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 0, TileNames.GAS_GIANT_1_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 1, TileNames.GAS_GIANT_1_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 1, TileNames.GAS_GIANT_1_SE);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 5, 0, TileNames.GAS_GIANT_2_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 0, TileNames.GAS_GIANT_2_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 1, TileNames.GAS_GIANT_2_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 1, TileNames.GAS_GIANT_2_SE);
    addTile(tile);
    // Reading the rock planet
    tile = new Tile(tilesImage, 7, 0, TileNames.ROCK1);
    addTile(tile);
    // Reading the water planet
    tile = new Tile(tilesImage, 8, 0, TileNames.WATERWORLD1);
    addTile(tile);
    tile = new Tile(tilesImage, 11, 0, TileNames.WATERWORLD2);
    addTile(tile);
    // Reading the fog of war
    tile = new Tile(tilesImage, 9, 0, TileNames.FOG_OF_WAR);
    addTile(tile);
    // Reading the uncharted
    tile = new Tile(tilesImage, 10, 0, TileNames.UNCHARTED);
    addTile(tile);
    // Reading the culture tiles
    tile = new Tile(tilesImage, 3, 2, TileNames.PLAYER_BLUE);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 2, TileNames.PLAYER_GREEN);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 2, TileNames.PLAYER_WHITE);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 2, TileNames.PLAYER_ORANGE);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 3, TileNames.PLAYER_CYAN);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 3, TileNames.PLAYER_PINK);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 3, TileNames.PLAYER_RED);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 3, TileNames.PLAYER_YELLOW);
    addTile(tile);
    // Reading the iron planet
    tile = new Tile(tilesImage, 12, 0, TileNames.IRONPLANET1);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 0, TileNames.IRONPLANET2);
    addTile(tile);
    // Reading the deep space anchors
    tile = new Tile(tilesImage, 7, 1, TileNames.DEEP_SPACE_ANCHOR1);
    addTile(tile);
    tile.setDescription(TileNames.DEEP_SPACE_ANCHOR_DESCRIPTION);
    // next index
    tile.setAnimationIndex(listOfTiles.size());
    tile = new Tile(tilesImage, 8, 1, TileNames.DEEP_SPACE_ANCHOR2);
    addTile(tile);
    tile.setDescription(TileNames.DEEP_SPACE_ANCHOR_DESCRIPTION);
    // Previous tile index as animation index
    tile.setAnimationIndex(getTileByName(
        TileNames.DEEP_SPACE_ANCHOR1).getIndex());
    // Readon more planets
    tile = new Tile(tilesImage, 14, 0, TileNames.WATERWORLD3);
    addTile(tile);
    tile = new Tile(tilesImage, 15, 0, TileNames.WATERWORLD4);
    addTile(tile);
    tile = new Tile(tilesImage, 16, 0, TileNames.ICEWORLD1);
    addTile(tile);
    tile = new Tile(tilesImage, 17, 0, TileNames.ICEWORLD2);
    addTile(tile);
    tile = new Tile(tilesImage, 18, 0, TileNames.IRONPLANET3);
    addTile(tile);
    tile = new Tile(tilesImage, 19, 0, TileNames.CARBONWORLD1);
    addTile(tile);
    // Reading the ship color tiles
    tile = new Tile(tilesImage, 7, 2, TileNames.PLAYER_SHIP_BLUE);
    addTile(tile);
    tile = new Tile(tilesImage, 8, 2, TileNames.PLAYER_SHIP_GREEN);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 2, TileNames.PLAYER_SHIP_WHITE);
    addTile(tile);
    tile = new Tile(tilesImage, 10, 2, TileNames.PLAYER_SHIP_ORANGE);
    addTile(tile);
    tile = new Tile(tilesImage, 7, 3, TileNames.PLAYER_SHIP_CYAN);
    addTile(tile);
    tile = new Tile(tilesImage, 8, 3, TileNames.PLAYER_SHIP_PINK);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 3, TileNames.PLAYER_SHIP_RED);
    addTile(tile);
    tile = new Tile(tilesImage, 10, 3, TileNames.PLAYER_SHIP_YELLOW);
    addTile(tile);
    tile = new Tile(tilesImage, 11, 3, TileNames.PLAYER_SHIP_BLACK);
    addTile(tile);
    tile = new Tile(tilesImage, 11, 2, TileNames.PLAYER_BLACK);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_CREDITS);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_TECH);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_DSA);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_MAP);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_MONSTER);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_PIRATE);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_LAIR);
    addTile(tile);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    tile = new Tile(tilesImage, 10, 1, TileNames.WORM_HOLE1);
    addTile(tile);
    tile.setDescription(TileNames.WORM_HOLE_DESCRIPTION);
    tile.setAnimationIndex(listOfTiles.size());
    tile = new Tile(tilesImage, 11, 1, TileNames.WORM_HOLE2);
    addTile(tile);
    tile.setDescription(TileNames.WORM_HOLE_DESCRIPTION);
    tile.setAnimationIndex(getTileByName(
        TileNames.WORM_HOLE1).getIndex());
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_SHIP);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 12, 1, TileNames.GAS_GIANT_3_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 1, TileNames.GAS_GIANT_3_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 12, 2, TileNames.GAS_GIANT_3_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 2, TileNames.GAS_GIANT_3_SE);
    addTile(tile);
    tile = new Tile(tilesImage, 14, 1, TileNames.ICEWORLD3);
    addTile(tile);
    tile = new Tile(tilesImage, 15, 1, TileNames.IRONPLANET4);
    addTile(tile);
    tile = new Tile(tilesImage, 16, 1, TileNames.CARBONWORLD2);
    addTile(tile);
    tile = new Tile(tilesImage, 17, 1, TileNames.DESERTWORLD1);
    addTile(tile);
    tile = new Tile(tilesImage, 18, 1, TileNames.WATERWORLD5);
    addTile(tile);
    tile = new Tile(tilesImage, 19, 1, TileNames.WATERWORLD6);
    addTile(tile);
    tile = new Tile(tilesImage, 14, 2, TileNames.WATERWORLD7);
    addTile(tile);
    tile = new Tile(tilesImage, 15, 2, TileNames.ICEWORLD4);
    addTile(tile);
    tile = new Tile(tilesImage, 16, 2, TileNames.WATERWORLD8);
    addTile(tile);
    tile = new Tile(tilesImage, 17, 2, TileNames.DESERTWORLD2);
    addTile(tile);
    tile = new Tile(tilesImage, 18, 2, TileNames.WATERWORLD9);
    addTile(tile);
    tile = new Tile(tilesImage, 19, 2, TileNames.IRONPLANET5);
    addTile(tile);
    tile = new Tile(tilesImage, 14, 3, TileNames.IRONPLANET6);
    addTile(tile);
    tile = new Tile(tilesImage, 15, 3, TileNames.DESERTWORLD3);
    addTile(tile);
    tile = new Tile(tilesImage, 16, 3, TileNames.CARBONWORLD3);
    addTile(tile);
    tile = new Tile(tilesImage, 17, 3, TileNames.ARTIFICIALWORLD1);
    addTile(tile);
    // Reading the blackhole 3x3
    tile = new Tile(tilesImage, 0, 3, TileNames.BLACKHOLE_NW);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    bhFirstStart = tile.getIndex();
    tile = new Tile(tilesImage, 1, 3, TileNames.BLACKHOLE_N);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 3, TileNames.BLACKHOLE_NE);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 4, TileNames.BLACKHOLE_W);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    bhFirstEnd = tile.getIndex();
    tile = new Tile(tilesImage, 1, 4, TileNames.BLACKHOLE_C);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 4, TileNames.BLACKHOLE_E);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    bhSecondStart = tile.getIndex();
    tile = new Tile(tilesImage, 0, 5, TileNames.BLACKHOLE_SW);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 5, TileNames.BLACKHOLE_S);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 5, TileNames.BLACKHOLE_SE);
    tile.setDescription(TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tile);
    bhSecondEnd = tile.getIndex();
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_MECHION);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_TIME_WARP);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_RARE_TECH);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tile);
    // New Player tiles
    tile = new Tile(tilesImage, 3, 4, TileNames.PLAYER_PURPLE);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 5, TileNames.PLAYER_SHIP_PURPLE);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 4, TileNames.PLAYER_BROWN);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 5, TileNames.PLAYER_SHIP_BROWN);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 4, TileNames.PLAYER_LIME);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 5, TileNames.PLAYER_SHIP_LIME);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 4, TileNames.PLAYER_CHESTNUT);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 5, TileNames.PLAYER_SHIP_CHESTNUT);
    addTile(tile);
    tile = new Tile(tilesImage, 7, 4, TileNames.PLAYER_ROSE);
    addTile(tile);
    tile = new Tile(tilesImage, 7, 5, TileNames.PLAYER_SHIP_ROSE);
    addTile(tile);
    tile = new Tile(tilesImage, 8, 4, TileNames.PLAYER_BANANA);
    addTile(tile);
    tile = new Tile(tilesImage, 8, 5, TileNames.PLAYER_SHIP_BANANA);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 4, TileNames.PLAYER_GRAY);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 5, TileNames.PLAYER_SHIP_GRAY);
    addTile(tile);
    tile = new Tile(tilesImage, 10, 4, TileNames.PLAYER_TAN);
    addTile(tile);
    tile = new Tile(tilesImage, 10, 5, TileNames.PLAYER_SHIP_TAN);
    addTile(tile);
    tile = new Tile(tilesImage, 11, 4, TileNames.PLAYER_CORAL);
    addTile(tile);
    tile = new Tile(tilesImage, 11, 5, TileNames.PLAYER_SHIP_CORAL);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 6, TileNames.PLAYER_OLIVE);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 6, TileNames.PLAYER_SHIP_OLIVE);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 6, TileNames.PLAYER_SKY);
    addTile(tile);
    tile = new Tile(tilesImage, 6, 6, TileNames.PLAYER_SHIP_SKY);
    addTile(tile);
    // Reading the Blue star 3x3
    tile = new Tile(tilesImage, 0, 7, TileNames.BLUE_STAR_NW);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 7, TileNames.BLUE_STAR_N);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 7, TileNames.BLUE_STAR_NE);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 8, TileNames.BLUE_STAR_W);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 8, TileNames.BLUE_STAR_C);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 8, TileNames.BLUE_STAR_E);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 0, 9, TileNames.BLUE_STAR_SW);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 1, 9, TileNames.BLUE_STAR_S);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 2, 9, TileNames.BLUE_STAR_SE);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    // Reading the Star 3x3
    tile = new Tile(tilesImage, 3, 7, TileNames.STAR_NW);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 7, TileNames.STAR_N);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 7, TileNames.STAR_NE);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 8, TileNames.STAR_W);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 8, TileNames.STAR_C);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 8, TileNames.STAR_E);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 3, 9, TileNames.STAR_SW);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 4, 9, TileNames.STAR_S);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 5, 9, TileNames.STAR_SE);
    tile.setDescription(TileNames.STAR_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 18, 3, TileNames.PLANET_EARTH);
    addTile(tile);
    tile = new Tile(tilesImage, 19, 3, TileNames.PLANET_MARS);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 12, 3, TileNames.JUPITER_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 3, TileNames.JUPITER_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 12, 4, TileNames.JUPITER_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 4, TileNames.JUPITER_SE);
    addTile(tile);
    // Reading the gas planet 2x2
    tile = new Tile(tilesImage, 12, 5, TileNames.SATURN_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 5, TileNames.SATURN_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 12, 6, TileNames.SATURN_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 6, TileNames.SATURN_SE);
    addTile(tile);
    // Reading the ice giant 2x2
    tile = new Tile(tilesImage, 12, 7, TileNames.ICEGIANT1_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 7, TileNames.ICEGIANT1_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 12, 8, TileNames.ICEGIANT1_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 8, TileNames.ICEGIANT1_SE);
    addTile(tile);
    // Reading the ice giant 2x2
    tile = new Tile(tilesImage, 12, 9, TileNames.ICEGIANT2_NW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 9, TileNames.ICEGIANT2_NE);
    addTile(tile);
    tile = new Tile(tilesImage, 12, 10, TileNames.ICEGIANT2_SW);
    addTile(tile);
    tile = new Tile(tilesImage, 13, 10, TileNames.ICEGIANT2_SE);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_ANCIENT_ARTIFACT);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 9, 1, TileNames.SPACE_ANOMALY_NEWS_STATION);
    // Read space anomaly
    tile.setDescription(TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 7, 6, TileNames.NEWSTATION1);
    tile.setDescription(TileNames.NEWS_STATION_DESCRIPTION);
    addTile(tile);
    tile = new Tile(tilesImage, 8, 6, TileNames.NEWSTATION2);
    tile.setDescription(TileNames.NEWS_STATION_DESCRIPTION);
    addTile(tile);

  }

  /**
   * Get Blackhole first index start
   * @return Index
   */
  public static int getBhFirstStart() {
    return bhFirstStart;
  }

  /**
   * Get Blackhole first index end
   * @return Index
   */
  public static int getBhFirstEnd() {
    return bhFirstEnd;
  }

  /**
   * Get Blackhole second index start
   * @return Index
   */
  public static int getBhSecondStart() {
    return bhSecondStart;
  }

  /**
   * Get Blackhole second index end
   * @return Index
   */
  public static int getBhSecondEnd() {
    return bhSecondEnd;
  }

}
