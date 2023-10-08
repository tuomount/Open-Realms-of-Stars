package org.openRealmOfStars.mapTiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.openRealmOfStars.starMap.SunType;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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
   * List of tiles size 32x32
   */
  private static ArrayList<Tile> listOfTiles32;
  /**
   * Hash map for tile size 32x32
   */
  private static HashMap<String, Tile> hashOfTiles32;

  /**
   * List of tiles size 64x64
   */
  private static ArrayList<Tile> listOfTiles64;
  /**
   * Hash map for tile size 64x64
   */
  private static HashMap<String, Tile> hashOfTiles64;
  /**
   * List of tiles size 16x16
   */
  private static ArrayList<Tile> listOfTiles16;
  /**
   * Hash map for tile size 16x16
   */
  private static HashMap<String, Tile> hashOfTiles16;

  /**
   * Get tile with index. Initializes tiles if they are uninitialized
   * @param index for tile
   * @return Tile Always returns a tile, If not found tile then first index is
   * returned.
   */
  public static Tile getTileByIndex(final int index) {
    if (listOfTiles32 == null) {
      initTiles();
    }
    if (index > 0 && index < listOfTiles32.size()) {
      return listOfTiles32.get(index);
    }
    return listOfTiles32.get(0);
  }
  /**
   * Get tile with index. Initializes tiles if they are uninitialized
   * @param index for tile
   * @param zoomLevel Zoomlevel for getting tile
   * @return Tile Always returns a tile, If not found tile then first index is
   * returned.
   */
  public static Tile getTileByIndex(final int index, final int zoomLevel) {
    if (listOfTiles32 == null) {
      initTiles();
    }
    if (index > 0 && index < listOfTiles32.size()
        && zoomLevel == Tile.ZOOM_NORMAL) {
      return listOfTiles32.get(index);
    }
    if (index > 0 && index < listOfTiles64.size()
        && zoomLevel == Tile.ZOOM_IN) {
      return listOfTiles64.get(index);
    }
    if (index > 0 && index < listOfTiles16.size()
        && zoomLevel == Tile.ZOOM_OUT1) {
      return listOfTiles16.get(index);
    }
    return listOfTiles32.get(0);
  }

  /**
   * Get tile by name. Initializes tiles if they are uninitialized.
   * @param name For search the tile
   * @return Always returns a tile if not found tile then first index is
   * returned.
   */
  public static Tile getTileByName(final String name) {
    if (hashOfTiles32 == null) {
      initTiles();
    }
    Tile tile = hashOfTiles32.get(name);
    if (tile == null) {
      return getTileByIndex(0);
    }
    return tile;
  }

  /**
   * Get tile by name. Initializes tiles if they are uninitialized.
   * @param name For search the tile
   * @param zoomLevel Zoom level
   * @return Always returns a tile if not found tile then first index is
   * returned.
   */
  public static Tile getTileByName(final String name, final int zoomLevel) {
    if (hashOfTiles32 == null) {
      initTiles();
    }
    Tile tile = null;
    if (zoomLevel == Tile.ZOOM_NORMAL) {
      tile = hashOfTiles32.get(name);
    }
    if (zoomLevel == Tile.ZOOM_IN) {
      tile = hashOfTiles64.get(name);
    }
    if (zoomLevel == Tile.ZOOM_OUT1) {
      tile = hashOfTiles16.get(name);
    }
    if (tile == null) {
      return getTileByIndex(0);
    }
    return tile;
  }

  /**
   * Get sun tiles by name. Initializes tiles if they are uninitialized.
   * @param name For search the tile
   * @param type There are three types of sun Red, blue and yellow
   * @return Always returns a tile if not found tile then first index is
   * returned.
   */
  public static Tile getSunTile(final String name, final SunType type) {
    if (hashOfTiles32 == null) {
      initTiles();
    }
    Tile tile = null;
    if (type == SunType.BLUE_STAR) {
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
    } else if (type == SunType.YELLOW_STAR) {
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
    if (listOfTiles32 == null) {
      initTiles();
    }
    return listOfTiles32.size();
  }

 /**
  * Add new tile all tile zoom levels
  * @param tilesImage32 Image for 32x32 tiles
  * @param tilesImage64 Image for 64x64 tiles
  * @param tilesImage16 Image for 16x16 tiles
  * @param x X position
  * @param y Y Position
  * @param name Tile name
  * @param description Tile description, can be null
  */
  public static void addTile(final BufferedImage tilesImage32,
      final BufferedImage tilesImage64,
      final BufferedImage tilesImage16,
      final int x, final int y, final String name,
      final String description) {
    addTile(tilesImage16, Tile.ZOOM_OUT1, x, y, name, description, -1);
    addTile(tilesImage32, Tile.ZOOM_NORMAL, x, y, name, description, -1);
    addTile(tilesImage64, Tile.ZOOM_IN, x, y, name, description, -1);
  }

  /**
   * Add new tile all tile zoom levels
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   * @param x X position
   * @param y Y Position
   * @param name Tile name
   * @param description Tile description, can be null
   * @param animationIndex AnimationIndex for tile. -1 Not set,
   *                       -2 for next index in list
   */
   public static void addTile(final BufferedImage tilesImage32,
       final BufferedImage tilesImage64,
       final BufferedImage tilesImage16,
       final int x, final int y, final String name,
       final String description, final int animationIndex) {
     addTile(tilesImage16, Tile.ZOOM_OUT1, x, y, name, description,
         animationIndex);
     addTile(tilesImage32, Tile.ZOOM_NORMAL, x, y, name, description,
         animationIndex);
     addTile(tilesImage64, Tile.ZOOM_IN, x, y, name, description,
         animationIndex);
   }

  /**
   * Add new tile to Tiles list and map
   * @param image Image for Tile
   * @param zoomLevel Zoomlevel for tile
   * @param x X position
   * @param y Y Position
   * @param name Tile name
   * @param description Tile description, can be null
   * @param animationIndex AnimationIndex for tile. -1 Not set,
   *                       -2 for next index in list
   */
  private static void addTile(final BufferedImage image, final int zoomLevel,
      final int x, final int y, final String name,
      final String description, final int animationIndex) {
    Tile tile = new Tile(image, zoomLevel, x, y, name);
    tile.setAnimationIndex(animationIndex);
    if (description != null) {
      tile.setDescription(description);
    }
    if (zoomLevel == Tile.ZOOM_NORMAL) {
      listOfTiles32.add(tile);
      tile.setIndex(listOfTiles32.size() - 1);
      hashOfTiles32.put(tile.getName(), tile);
      if (animationIndex == -2) {
        tile.setAnimationIndex(listOfTiles32.size());
      } else if (animationIndex != -1) {
        tile.setAnimationIndex(animationIndex);
      }
    }
    if (zoomLevel == Tile.ZOOM_IN) {
      listOfTiles64.add(tile);
      tile.setIndex(listOfTiles64.size() - 1);
      hashOfTiles64.put(tile.getName(), tile);
      if (animationIndex == -2) {
        tile.setAnimationIndex(listOfTiles64.size());
      } else if (animationIndex != -1) {
        tile.setAnimationIndex(animationIndex);
      }
    }
    if (zoomLevel == Tile.ZOOM_OUT1) {
      listOfTiles16.add(tile);
      tile.setIndex(listOfTiles16.size() - 1);
      hashOfTiles16.put(tile.getName(), tile);
      if (animationIndex == -2) {
        tile.setAnimationIndex(listOfTiles16.size());
      } else if (animationIndex != -1) {
        tile.setAnimationIndex(animationIndex);
      }
    }
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
    BufferedImage tilesImage32 = IOUtilities
        .loadImage(Tiles.class.getResource("/resources/images/maptiles.png"));
    BufferedImage tilesImage16 = IOUtilities
        .loadImage(Tiles.class.getResource(
            "/resources/images/maptiles_16.png"));
    BufferedImage tilesImage64 = IOUtilities
        .loadImage(Tiles.class.getResource(
            "/resources/images/maptiles_64.png"));
    listOfTiles32 = new ArrayList<>();
    hashOfTiles32 = new HashMap<>();
    listOfTiles16 = new ArrayList<>();
    hashOfTiles16 = new HashMap<>();
    listOfTiles64 = new ArrayList<>();
    hashOfTiles64 = new HashMap<>();
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 0, TileNames.EMPTY,
        null);
    readSunGasPlanets(tilesImage32, tilesImage64, tilesImage16);
    readWaterPlanetAndCulture(tilesImage32, tilesImage64, tilesImage16);
    readMorePlanets(tilesImage32, tilesImage64, tilesImage16);
    readMorePlanetsAndAnomalies(tilesImage32, tilesImage64, tilesImage16);
    readPlayerMarkings(tilesImage32, tilesImage64, tilesImage16);
    readNewStarsAndPlanets(tilesImage32, tilesImage64, tilesImage16);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_ANCIENT_ARTIFACT,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_NEWS_STATION,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 6,
        TileNames.NEWSTATION1,
        TileNames.NEWS_STATION_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 6,
        TileNames.NEWSTATION2,
        TileNames.NEWS_STATION_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_LEADER_IN_STASIS,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_DESTROYED_PLANET,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 6,
        TileNames.DESTROYED_PLANET,
        TileNames.DESTROYED_PLANET_DESCRIPTION);
  }

  /**
   * Read Sun and Gas Planets images.
   *
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   */
  private static void readSunGasPlanets(final BufferedImage tilesImage32,
      final BufferedImage tilesImage64, final BufferedImage tilesImage16) {
    // Reading the sun 3x3
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 0,
        TileNames.SUN_NW,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 0,
        TileNames.SUN_N,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 0,
        TileNames.SUN_NE,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 1,
        TileNames.SUN_W,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 1,
        TileNames.SUN_C,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 1,
        TileNames.SUN_E,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 2,
        TileNames.SUN_SW,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 2,
        TileNames.SUN_S,
        TileNames.RED_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 2,
        TileNames.SUN_SE,
        TileNames.RED_STAR_DESCRIPTION);
    // Reading the gas planet 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 0,
        TileNames.GAS_GIANT_1_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 0,
        TileNames.GAS_GIANT_1_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 1,
        TileNames.GAS_GIANT_1_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 1,
        TileNames.GAS_GIANT_1_SE,
        null);
    // Reading the gas planet 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 0,
        TileNames.GAS_GIANT_2_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 0,
        TileNames.GAS_GIANT_2_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 1,
        TileNames.GAS_GIANT_2_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 1,
        TileNames.GAS_GIANT_2_SE,
        null);
    // Reading the rock planet
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 0,
        TileNames.ROCK1,
        null);
  }

  /**
   * Read Water planets and culture tiles.
   *
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   */
  private static void readWaterPlanetAndCulture(
      final BufferedImage tilesImage32,
      final BufferedImage tilesImage64, final BufferedImage tilesImage16) {
    // Reading the water planet
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 0,
        TileNames.WATERWORLD1,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 11, 0,
        TileNames.WATERWORLD2,
        null);
    // Reading the fog of war
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 0,
        TileNames.FOG_OF_WAR,
        null);
    // Reading the uncharted
    addTile(tilesImage32, tilesImage64, tilesImage16, 10, 0,
        TileNames.UNCHARTED,
        null);

    // Reading the culture tiles
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 2,
        TileNames.PLAYER_BLUE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 2,
        TileNames.PLAYER_GREEN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 2,
        TileNames.PLAYER_WHITE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 2,
        TileNames.PLAYER_ORANGE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 3,
        TileNames.PLAYER_CYAN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 3,
        TileNames.PLAYER_PINK,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 3,
        TileNames.PLAYER_RED,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 3,
        TileNames.PLAYER_YELLOW,
        null);
  }

  /**
   * Read more planets and ship markers.
   *
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   */
  private static void readMorePlanets(
      final BufferedImage tilesImage32,
      final BufferedImage tilesImage64, final BufferedImage tilesImage16) {
    // Reading the iron planet
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 0,
        TileNames.IRONPLANET1,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 0,
        TileNames.IRONPLANET2,
        null);
    // Reading the deep space anchors
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 1,
        TileNames.DEEP_SPACE_ANCHOR1,
        TileNames.DEEP_SPACE_ANCHOR_DESCRIPTION, -2);
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 1,
        TileNames.DEEP_SPACE_ANCHOR2,
        TileNames.DEEP_SPACE_ANCHOR_DESCRIPTION, getTileByName(
            TileNames.DEEP_SPACE_ANCHOR1).getIndex());
    // Readon more planets
    addTile(tilesImage32, tilesImage64, tilesImage16, 14, 0,
        TileNames.WATERWORLD3,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 15, 0,
        TileNames.WATERWORLD4,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 16, 0,
        TileNames.ICEWORLD1,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 17, 0,
        TileNames.ICEWORLD2,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 18, 0,
        TileNames.IRONPLANET3,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 19, 0,
        TileNames.CARBONWORLD1,
        null);
    // Reading the ship color tiles
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 2,
        TileNames.PLAYER_SHIP_BLUE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 2,
        TileNames.PLAYER_SHIP_GREEN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 2,
        TileNames.PLAYER_SHIP_WHITE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 10, 2,
        TileNames.PLAYER_SHIP_ORANGE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 3,
        TileNames.PLAYER_SHIP_CYAN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 3,
        TileNames.PLAYER_SHIP_PINK,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 3,
        TileNames.PLAYER_SHIP_RED,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 10, 3,
        TileNames.PLAYER_SHIP_YELLOW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 11, 3,
        TileNames.PLAYER_SHIP_BLACK,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 11, 2,
        TileNames.PLAYER_BLACK,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
  }

  /**
   * Read more planets and anomalies.
   *
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   */
  private static void readMorePlanetsAndAnomalies(
      final BufferedImage tilesImage32,
      final BufferedImage tilesImage64, final BufferedImage tilesImage16) {
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_CREDITS,
        TileNames.SPACE_ANOMALY_DESCRIPTION);

    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_TECH,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_DSA,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_MAP,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_MONSTER,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_PIRATE,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_LAIR,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 10, 1,
        TileNames.WORM_HOLE1,
        TileNames.WORM_HOLE_DESCRIPTION, -2);
    addTile(tilesImage32, tilesImage64, tilesImage16, 11, 1,
        TileNames.WORM_HOLE2,
        TileNames.WORM_HOLE_DESCRIPTION, getTileByName(
            TileNames.WORM_HOLE1).getIndex());
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_SHIP,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    // Reading the gas planet 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 1,
        TileNames.GAS_GIANT_3_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 1,
        TileNames.GAS_GIANT_3_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 2,
        TileNames.GAS_GIANT_3_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 2,
        TileNames.GAS_GIANT_3_SE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 14, 1,
        TileNames.ICEWORLD3,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 15, 1,
        TileNames.IRONPLANET4,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 16, 1,
        TileNames.CARBONWORLD2,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 17, 1,
        TileNames.DESERTWORLD1,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 18, 1,
        TileNames.WATERWORLD5,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 19, 1,
        TileNames.WATERWORLD6,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 14, 2,
        TileNames.WATERWORLD7,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 15, 2,
        TileNames.ICEWORLD4,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 16, 2,
        TileNames.WATERWORLD8,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 17, 2,
        TileNames.DESERTWORLD2,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 18, 2,
        TileNames.WATERWORLD9,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 19, 2,
        TileNames.IRONPLANET5,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 14, 3,
        TileNames.IRONPLANET6,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 15, 3,
        TileNames.DESERTWORLD3,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 16, 3,
        TileNames.CARBONWORLD3,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 17, 3,
        TileNames.ARTIFICIALWORLD1,
        null);
    // Reading the blackhole 3x3
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 3,
        TileNames.BLACKHOLE_NW,
        TileNames.BLACKHOLE_DESCRIPTION);
    bhFirstStart = getTileByName(TileNames.BLACKHOLE_NW).getIndex();
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 3,
        TileNames.BLACKHOLE_N,
        TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 3,
        TileNames.BLACKHOLE_NE,
        TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 4,
        TileNames.BLACKHOLE_W,
        TileNames.BLACKHOLE_DESCRIPTION);
    bhFirstEnd = getTileByName(TileNames.BLACKHOLE_W).getIndex();
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 4,
        TileNames.BLACKHOLE_C,
        TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 4,
        TileNames.BLACKHOLE_E,
        TileNames.BLACKHOLE_DESCRIPTION);
    bhSecondStart = getTileByName(TileNames.BLACKHOLE_E).getIndex();
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 5,
        TileNames.BLACKHOLE_SE,
        TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 5,
        TileNames.BLACKHOLE_S,
        TileNames.BLACKHOLE_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 5,
        TileNames.BLACKHOLE_S,
        TileNames.BLACKHOLE_DESCRIPTION);
    bhSecondEnd = getTileByName(TileNames.BLACKHOLE_SE).getIndex();
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_MECHION,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_TIME_WARP,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 1,
        TileNames.SPACE_ANOMALY_RARE_TECH,
        TileNames.SPACE_ANOMALY_DESCRIPTION);
  }

  /**
   * Read player markings
   *
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   */
  private static void readPlayerMarkings(
      final BufferedImage tilesImage32,
      final BufferedImage tilesImage64, final BufferedImage tilesImage16) {
    // New Player tiles
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 4,
        TileNames.PLAYER_PURPLE,
        null);

    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 5,
        TileNames.PLAYER_SHIP_PURPLE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 4,
        TileNames.PLAYER_BROWN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 5,
        TileNames.PLAYER_SHIP_BROWN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 4,
        TileNames.PLAYER_LIME,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 5,
        TileNames.PLAYER_SHIP_LIME,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 4,
        TileNames.PLAYER_CHESTNUT,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 5,
        TileNames.PLAYER_SHIP_CHESTNUT,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 4,
        TileNames.PLAYER_ROSE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 7, 5,
        TileNames.PLAYER_SHIP_ROSE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 4,
        TileNames.PLAYER_BANANA,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 8, 5,
        TileNames.PLAYER_SHIP_BANANA,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 4,
        TileNames.PLAYER_GRAY,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 9, 5,
        TileNames.PLAYER_SHIP_GRAY,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 10, 4,
        TileNames.PLAYER_TAN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 10, 5,
        TileNames.PLAYER_SHIP_TAN,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 11, 4,
        TileNames.PLAYER_CORAL,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 11, 5,
        TileNames.PLAYER_SHIP_CORAL,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 6,
        TileNames.PLAYER_OLIVE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 6,
        TileNames.PLAYER_SHIP_OLIVE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 6,
        TileNames.PLAYER_SKY,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 6, 6,
        TileNames.PLAYER_SHIP_SKY,
        null);
  }
  /**
   * Read new stars and planets
   *
   * @param tilesImage32 Image for 32x32 tiles
   * @param tilesImage64 Image for 64x64 tiles
   * @param tilesImage16 Image for 16x16 tiles
   */
  private static void readNewStarsAndPlanets(
      final BufferedImage tilesImage32,
      final BufferedImage tilesImage64, final BufferedImage tilesImage16) {
    // Reading the Blue star 3x3
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 7,
        TileNames.BLUE_STAR_NW,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 7,
        TileNames.BLUE_STAR_N,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 7,
        TileNames.BLUE_STAR_NE,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 8,
        TileNames.BLUE_STAR_W,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 8,
        TileNames.BLUE_STAR_C,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 8,
        TileNames.BLUE_STAR_E,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 0, 9,
        TileNames.BLUE_STAR_SW,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 1, 9,
        TileNames.BLUE_STAR_S,
        TileNames.BLUE_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 2, 9,
        TileNames.BLUE_STAR_SE,
        TileNames.BLUE_STAR_DESCRIPTION);
    // Reading the Star 3x3
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 7,
        TileNames.STAR_NW,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 7,
        TileNames.STAR_N,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 7,
        TileNames.STAR_NE,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 8,
        TileNames.STAR_W,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 8,
        TileNames.STAR_C,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 8,
        TileNames.STAR_E,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 3, 9,
        TileNames.STAR_SW,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 4, 9,
        TileNames.STAR_S,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 5, 9,
        TileNames.STAR_SE,
        TileNames.YELLOW_STAR_DESCRIPTION);
    addTile(tilesImage32, tilesImage64, tilesImage16, 18, 3,
        TileNames.PLANET_EARTH,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 19, 3,
        TileNames.PLANET_MARS,
        null);
    // Reading the gas planet 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 3,
        TileNames.JUPITER_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 3,
        TileNames.JUPITER_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 4,
        TileNames.JUPITER_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 4,
        TileNames.JUPITER_SE,
        null);
    // Reading the gas planet 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 5,
        TileNames.SATURN_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 5,
        TileNames.SATURN_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 6,
        TileNames.SATURN_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 6,
        TileNames.SATURN_SE,
        null);
    // Reading the ice giant 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 7,
        TileNames.ICEGIANT1_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 7,
        TileNames.ICEGIANT1_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 8,
        TileNames.ICEGIANT1_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 8,
        TileNames.ICEGIANT1_SE,
        null);
    // Reading the ice giant 2x2
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 9,
        TileNames.ICEGIANT2_NW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 9,
        TileNames.ICEGIANT2_NE,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 12, 10,
        TileNames.ICEGIANT2_SW,
        null);
    addTile(tilesImage32, tilesImage64, tilesImage16, 13, 10,
        TileNames.ICEGIANT2_SE,
        null);

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
