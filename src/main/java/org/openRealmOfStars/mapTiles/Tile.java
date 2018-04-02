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
   * Normal scaling
   */
  public static final int SCALED_NORMAL = 1;

  /**
   * Scaled down half size
   */
  public static final int SCALED_HALF = 2;

  /**
   * Scaled down quarter size
   */
  public static final int SCALED_QUARTER = 4;

  /**
   * Scaled down fifth size
   */
  public static final int SCALED_FIFTH = 5;

  /**
   * Get max width for tile for certain scale
   * @param scale SCALED_NORMAL, SCALED_HALF, SCALED_QUARTER, SCALED_FIFTH
   * @return max width
   */
  public static int maxWidth(final int scale) {
    return MAX_WIDTH / scale;
  }

  /**
   * Get max height for tile for certain scale
   * @param scale SCALED_NORMAL, SCALED_HALF, SCALED_QUARTER, SCALED_FIFTH
   * @return max height
   */
  public static int maxHeight(final int scale) {
    return MAX_HEIGHT / scale;
  }

  /**
   * Graphical data for tile
   */
  private BufferedImage img;

  /**
   * Graphical data for tile, scaled down half size
   */
  private BufferedImage scaledImgHalf;

  /**
   * Graphical data for tile, scaled down quarter size
   */
  private BufferedImage scaledImgQuarter;

  /**
   * Graphical data for tile, scaled down fifth size
   */
  private BufferedImage scaledImgFifth;

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
      scaledImgHalf = scaleDown(SCALED_HALF);
      scaledImgQuarter = scaleDown(SCALED_QUARTER);
      scaledImgFifth = scaleDown(SCALED_FIFTH);
      this.name = name;
      this.tileDescription = "";
    } else {
      throw new RasterFormatException("Tile is outside of tileset.");
    }
  }

  /**
   * Scale down image.
   * @param scaleDown Scale down to size
   * @return Scaled down image
   * @throws IllegalArgumentException if scaledown is not one of the constants.
   */
  private BufferedImage scaleDown(final int scaleDown)
      throws IllegalArgumentException {
    if (scaleDown != SCALED_FIFTH && scaleDown != SCALED_HALF
        && scaleDown != SCALED_QUARTER) {
      throw new IllegalArgumentException("Illegal scaling argument!");
    }
    int type = img.getType();
    BufferedImage scaledImg = new BufferedImage(MAX_WIDTH / scaleDown,
        MAX_HEIGHT / scaleDown, type);
    int steps = 16;
    int mult = 2;
    if (scaleDown == SCALED_QUARTER) {
      steps = 8;
      mult = 4;
    }
    if (scaleDown == SCALED_FIFTH) {
      steps = 6;
      mult = 6;
    }
    for (int y = 0; y < steps; y++) {
      for (int x = 0; x < steps; x++) {
        int value = img.getRGB(x * mult, y * mult);
        scaledImg.setRGB(x, y, value);
      }
    }
    return scaledImg;
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
   * Draw tile to coordinates
   * @param g Graphics2D using for drawing
   * @param x Coordinates on x axel
   * @param y Coordinates on y axel
   * @param scale SCALED_HALF, SCALED_QUARTER or SCALED_FIFTH
   */
  public void drawScaled(final Graphics2D g, final int x, final int y,
      final int scale) {
    if (scale == SCALED_HALF) {
      g.drawImage(scaledImgHalf, x, y, null);
    } else if (scale == SCALED_QUARTER) {
      g.drawImage(scaledImgQuarter, x, y, null);
    } else if (scale == SCALED_FIFTH) {
      g.drawImage(scaledImgFifth, x, y, null);
    } else {
      g.drawImage(img, x, y, null);
    }
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
  @Override
  public String toString() {
    return name + " (" + tileIndex + ")";
  }

}
