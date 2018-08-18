package org.openRealmOfStars.mapTiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
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

public class Tile {

  /**
   * Tile Maximum width
   */
  public static final int MAX_WIDTH = 32;
  /**
   * Tile Maximum height
   */
  public static final int MAX_HEIGHT = 32;

  /**
   * Graphical data for tile
   */
  private BufferedImage img;

  /**
   * Tile name
   */
  private String name;

  /**
   * Tile index in list of tiles
   */
  private int tileIndex = -1;
  /**
   * Next anim index
   */
  private int nextAnimIndex = -1;

  /**
   * Special tile descriptio
   */
  private String tileDescription;

  /**
   * Get tile from tileset image, where x is number of tiles in X axel and
   * y is number of tiles in y axel.
   * @param tilesetImage BufferedImage
   * @param x X-axel coordinate
   * @param y Y-axel coordinate
   * @param name Name for the tile
   * @throws RasterFormatException if tile is outside of tileset image.
   */
  public Tile(final BufferedImage tilesetImage, final int x, final int y,
      final String name) throws RasterFormatException {
    if (x >= 0 && y >= 0 && x * MAX_WIDTH < tilesetImage.getHeight()
        && y * MAX_HEIGHT < tilesetImage.getHeight()) {
      img = tilesetImage.getSubimage(x * MAX_WIDTH, y * MAX_HEIGHT, MAX_WIDTH,
          MAX_HEIGHT);
      this.name = name;
      this.tileDescription = "";
    } else {
      throw new RasterFormatException("Tile is outside of tileset.");
    }
  }


  /**
   * Draw tile to coordinates
   * @param g Graphics2D using for drawing
   * @param x Coordinates on x axel
   * @param y Coordinates on y axel
   */
  public void draw(final Graphics2D g, final int x, final int y) {
    g.drawImage(img, x, y, null);
  }

  /**
   * Get the Tile name
   * @return Name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the tile index
   * @return tile index
   */
  public int getIndex() {
    return tileIndex;
  }

  /**
   * Set the tile index, disables the animation when set.
   * @param index tile index
   */
  public void setIndex(final int index) {
    tileIndex = index;
    nextAnimIndex = index;
  }

  /**
   * Get the tile animation index
   * @return tile animation index
   */
  public int getAnimationIndex() {
    return nextAnimIndex;
  }

  /**
   * Set the tile animation index
   * @param index tile animation index
   */
  public void setAnimationIndex(final int index) {
    nextAnimIndex = index;
  }

  /**
   * Get get tile description
   * @return Tile Description
   */
  public String getDescription() {
    return tileDescription;
  }

  /**
   * Set the tile description
   * @param description Tile description as string
   */
  public void setDescription(final String description) {
    tileDescription = description;
  }
  /**
   * Is tile a star tile
   * @return True if tile is star tile
   */
  public boolean isStarTile() {
    if (name.equals(TileNames.SUN_C)
        || name.equals(TileNames.SUN_E)
        || name.equals(TileNames.SUN_W)
        || name.equals(TileNames.SUN_N)
        || name.equals(TileNames.SUN_S)
        || name.equals(TileNames.SUN_NE)
        || name.equals(TileNames.SUN_SE)
        || name.equals(TileNames.SUN_NW)
        || name.equals(TileNames.SUN_SW)) {
      return true;
    }
    return false;
  }

  /**
   * Is tile potentially dangerous
   * @return True if dangerous
   */
  public boolean isDangerous() {
    if (name.equals(TileNames.SPACE_ANOMALY)
        || name.equals(TileNames.SPACE_ANOMALY_CREDITS)
        || name.equals(TileNames.SPACE_ANOMALY_DSA)
        || name.equals(TileNames.SPACE_ANOMALY_LAIR)
        || name.equals(TileNames.SPACE_ANOMALY_MAP)
        || name.equals(TileNames.SPACE_ANOMALY_MONSTER)
        || name.equals(TileNames.SPACE_ANOMALY_PIRATE)
        || name.equals(TileNames.SPACE_ANOMALY_SHIP)
        || name.equals(TileNames.SPACE_ANOMALY_TECH)
        || name.equals(TileNames.WORM_HOLE1)
        || name.equals(TileNames.WORM_HOLE2)) {
      return true;
    }
    return false;
  }
  /**
   * Is tile space anomaly
   * @return True if space anomaly
   */
  public boolean isSpaceAnomaly() {
    if (name.equals(TileNames.SPACE_ANOMALY)
        || name.equals(TileNames.SPACE_ANOMALY_CREDITS)
        || name.equals(TileNames.SPACE_ANOMALY_DSA)
        || name.equals(TileNames.SPACE_ANOMALY_LAIR)
        || name.equals(TileNames.SPACE_ANOMALY_MAP)
        || name.equals(TileNames.SPACE_ANOMALY_MONSTER)
        || name.equals(TileNames.SPACE_ANOMALY_PIRATE)
        || name.equals(TileNames.SPACE_ANOMALY_SHIP)
        || name.equals(TileNames.SPACE_ANOMALY_TECH)) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return name + " (" + tileIndex + ")";
  }

}
