package org.openRealmOfStars.mapTiles;
/*
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
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import org.openRealmOfStars.gui.mapPanel.BlackHoleEffect;

/**
 *
 * Class for handling star map tiles
 *
 */

public class Tile {

  /**
   * Zoom Level normal.
   */
  public static final int ZOOM_NORMAL = 0;
  /**
   * Zoom Level Zoom in.
   */
  public static final int ZOOM_IN = 1;
  /**
   * Zoom Level Zoom out 1.
   */
  public static final int ZOOM_OUT1 = -1;

  /**
   * Tile normal size;
   */
  private static final int TILE_NORMAL_SIZE = 32;
  /**
   * Tile normal zoom in;
   */
  private static final int TILE_ZOOM_IN_SIZE = 64;
  /**
   * Tile normal zoom out 1;
   */
  private static final int TILE_ZOOM_OUT1_SIZE = 16;
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
   * Zoom Level for single tile
   */
  private int zoomLevel;

  /**
   * Static blackhole effect
   */
  private static BlackHoleEffect blackholeEffect;

  /**
   * Get tile from tileset image, where x is number of tiles in X axel and
   * y is number of tiles in y axel.
   * @param tilesetImage BufferedImage
   * @param zoomLevel Zoom Level for tile
   * @param x X-axel coordinate
   * @param y Y-axel coordinate
   * @param name Name for the tile
   * @throws RasterFormatException if tile is outside of tileset image.
   */
  public Tile(final BufferedImage tilesetImage, final int zoomLevel,
      final int x, final int y,
      final String name) throws RasterFormatException {
    this.zoomLevel = zoomLevel;
    if (x >= 0 && y >= 0
        && x * getMaxWidth(zoomLevel) < tilesetImage.getHeight()
        && y * getMaxHeight(zoomLevel) < tilesetImage.getHeight()) {
      img = tilesetImage.getSubimage(x * getMaxWidth(zoomLevel),
          y * getMaxHeight(zoomLevel), getMaxWidth(zoomLevel),
          getMaxHeight(zoomLevel));
      this.name = name;
      this.tileDescription = "";
    } else {
      throw new RasterFormatException("Tile is outside of tileset.");
    }
  }


  /**
   * Get Tile width on certain zoom level
   * @param zoomLevel Zoomlevel
   * @return width in pixels
   */
  public static int getMaxWidth(final int zoomLevel) {
    if (zoomLevel == ZOOM_NORMAL) {
      return TILE_NORMAL_SIZE;
    }
    if (zoomLevel == ZOOM_IN) {
      return TILE_ZOOM_IN_SIZE;
    }
    if (zoomLevel == ZOOM_OUT1) {
      return TILE_ZOOM_OUT1_SIZE;
    }
    return TILE_NORMAL_SIZE;
  }
  /**
   * Get Tile height on certain zoom level
   * @param zoomLevel Zoomlevel
   * @return height in pixels
   */
  public static int getMaxHeight(final int zoomLevel) {
    if (zoomLevel == ZOOM_NORMAL) {
      return TILE_NORMAL_SIZE;
    }
    if (zoomLevel == ZOOM_IN) {
      return TILE_ZOOM_IN_SIZE;
    }
    if (zoomLevel == ZOOM_OUT1) {
      return TILE_ZOOM_OUT1_SIZE;
    }
    return TILE_NORMAL_SIZE;
  }
  /**
   * Draw tile to coordinates
   * @param g Graphics2D using for drawing
   * @param x Coordinates on x axel
   * @param y Coordinates on y axel
   */
  public void draw(final Graphics2D g, final int x, final int y) {
    if (isBlackhole()) {
      if (blackholeEffect == null) {
        blackholeEffect = new BlackHoleEffect(new BufferedImage(
            getMaxWidth(zoomLevel),
            getMaxHeight(zoomLevel), BufferedImage.TYPE_INT_ARGB), zoomLevel);
      }
      blackholeEffect.drawBlackholeTile(g, x, y, this);
    } else {
      g.drawImage(img, x, y, null);
    }
  }

  /**
   * Update black hole effect for all tiles.
   * @param img New background for blackhole.
   * @param zoomLevel Zoom level
   */
  public static void updateBlackHoleEffect(final BufferedImage img,
      final int zoomLevel) {
    if (blackholeEffect == null) {
      blackholeEffect = new BlackHoleEffect(img, zoomLevel);
    } else {
      blackholeEffect.updateBackground(img);
    }
  }
  /**
   * Draw mini sector to target image.
   * @param target Target image where to draw.
   * @param x X coordinate
   * @param y Y coordinate
   * @param sectorSize Sector size in pixel.
   *        Currently support sizes 2, 3 and 4.
   */
  public void drawMiniSector(final BufferedImage target, final int x,
      final int y, final int sectorSize) {
    int sx = 2;
    int sy = 2;
    int step = 6;
    if (zoomLevel == Tile.ZOOM_NORMAL) {
      if (sectorSize == 2) {
        sx = 8;
        sy = 8;
        step = 16;
      }
      if (sectorSize == 3) {
        sx = 5;
        sy = 5;
        step = 10;
      }
      if (sectorSize == 4) {
        sx = 4;
        sy = 4;
        step = 8;
      }
    }
    if (zoomLevel == Tile.ZOOM_IN) {
      if (sectorSize == 2) {
        sx = 1;
        sy = 16;
        step = 32;
      }
      if (sectorSize == 3) {
        sx = 10;
        sy = 10;
        step = 20;
      }
      if (sectorSize == 4) {
        sx = 8;
        sy = 8;
        step = 16;
      }
    }
    if (zoomLevel == Tile.ZOOM_OUT1) {
      if (sectorSize == 2) {
        sx = 4;
        sy = 4;
        step = 8;
      }
      if (sectorSize == 3) {
        sx = 2;
        sy = 2;
        step = 5;
      }
      if (sectorSize == 4) {
        sx = 2;
        sy = 2;
        step = 4;
      }
    }
    for (int my = 0; my < sectorSize; my++) {
      for (int mx = 0; mx < sectorSize; mx++) {
        int color = img.getRGB(sx + mx * step, sy + my * step);
        int alpha = (color >> 24) & 0xff;
        if (alpha > 30) {
          target.setRGB(x + mx, y + my, color);
        }
      }
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
   * Get the tile ZoomLevel
   * @return Zoomlevel
   */
  public int getZoomLevel() {
    return zoomLevel;
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
        || name.equals(TileNames.SUN_SW)
        || name.equals(TileNames.BLUE_STAR_C)
        || name.equals(TileNames.BLUE_STAR_E)
        || name.equals(TileNames.BLUE_STAR_W)
        || name.equals(TileNames.BLUE_STAR_N)
        || name.equals(TileNames.BLUE_STAR_S)
        || name.equals(TileNames.BLUE_STAR_NE)
        || name.equals(TileNames.BLUE_STAR_SE)
        || name.equals(TileNames.BLUE_STAR_NW)
        || name.equals(TileNames.BLUE_STAR_SW)
        || name.equals(TileNames.STAR_C)
        || name.equals(TileNames.STAR_E)
        || name.equals(TileNames.STAR_W)
        || name.equals(TileNames.STAR_N)
        || name.equals(TileNames.STAR_S)
        || name.equals(TileNames.STAR_NE)
        || name.equals(TileNames.STAR_SE)
        || name.equals(TileNames.STAR_NW)
        || name.equals(TileNames.STAR_SW)) {
      return true;
    }
    return false;
  }

  /**
   * Is tile a blue star tile
   * @return True if tile is blue star tile
   */
  public boolean isBlueStarTile() {
    if (name.equals(TileNames.BLUE_STAR_C)
        || name.equals(TileNames.BLUE_STAR_E)
        || name.equals(TileNames.BLUE_STAR_W)
        || name.equals(TileNames.BLUE_STAR_N)
        || name.equals(TileNames.BLUE_STAR_S)
        || name.equals(TileNames.BLUE_STAR_NE)
        || name.equals(TileNames.BLUE_STAR_SE)
        || name.equals(TileNames.BLUE_STAR_NW)
        || name.equals(TileNames.BLUE_STAR_SW)) {
      return true;
    }
    return false;
  }

  /**
   * Is tile a yellow star tile
   * @return True if tile is yellow star tile
   */
  public boolean isYellowStarTile() {
    if (name.equals(TileNames.STAR_C)
        || name.equals(TileNames.STAR_E)
        || name.equals(TileNames.STAR_W)
        || name.equals(TileNames.STAR_N)
        || name.equals(TileNames.STAR_S)
        || name.equals(TileNames.STAR_NE)
        || name.equals(TileNames.STAR_SE)
        || name.equals(TileNames.STAR_NW)
        || name.equals(TileNames.STAR_SW)) {
      return true;
    }
    return false;
  }

  /**
   * Is tile a sun tile
   * @return True if tile is sun tile
   */
  public boolean isSunTile() {
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
        || name.equals(TileNames.SPACE_ANOMALY_MECHION)
        || name.equals(TileNames.SPACE_ANOMALY_RARE_TECH)
        || name.equals(TileNames.SPACE_ANOMALY_TIME_WARP)
        || name.equals(TileNames.SPACE_ANOMALY_MONSTER)
        || name.equals(TileNames.SPACE_ANOMALY_ANCIENT_ARTIFACT)
        || name.equals(TileNames.SPACE_ANOMALY_NEWS_STATION)
        || name.equals(TileNames.SPACE_ANOMALY_LEADER_IN_STASIS)
        || name.equals(TileNames.SPACE_ANOMALY_DESTROYED_PLANET)
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
        || name.equals(TileNames.SPACE_ANOMALY_TECH)
        || name.equals(TileNames.SPACE_ANOMALY_MECHION)
        || name.equals(TileNames.SPACE_ANOMALY_RARE_TECH)
        || name.equals(TileNames.SPACE_ANOMALY_TIME_WARP)
        || name.equals(TileNames.SPACE_ANOMALY_MONSTER)
        || name.equals(TileNames.SPACE_ANOMALY_ANCIENT_ARTIFACT)
        || name.equals(TileNames.SPACE_ANOMALY_NEWS_STATION)
        || name.equals(TileNames.SPACE_ANOMALY_LEADER_IN_STASIS)
        || name.equals(TileNames.SPACE_ANOMALY_DESTROYED_PLANET)) {
      return true;
    }
    return false;
  }

  /**
   * Is tile black hole
   * @return True if black hole
   */
  public boolean isBlackhole() {
    if (tileIndex >= Tiles.getBhFirstStart()
        && tileIndex <= Tiles.getBhFirstEnd()
        || tileIndex >= Tiles.getBhSecondStart()
        && tileIndex <= Tiles.getBhSecondEnd()) {
      return true;
    }
    return false;
  }
  @Override
  public String toString() {
    return name + " (" + tileIndex + ")";
  }

  /**
   * Get raw image tile.
   * @return Get actual raw tile
   */
  public BufferedImage getRawTile() {
    return img;
  }

}
