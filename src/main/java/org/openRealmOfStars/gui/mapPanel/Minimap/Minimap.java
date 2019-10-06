package org.openRealmOfStars.gui.mapPanel.Minimap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.SquareInfo;
import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019  Tuomo Untinen
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
* Minimap for drawing big portion of map or even all
*
*/
public class Minimap {

  /**
   * Maximum minimap size in pixels. Minimap is always square shaped.
   */
  private static final int MAX_SIZE_PX = 256;

  /**
   * Minimap size in pixels.
   */
  private int size;

  /**
   * Number of sectors to show;
   */
  private int sectorsToShow;
  /**
   * Sector size in pixels;
   */
  private int sectorSize;

  /**
   * Image buffers.
   */
  private BufferedImage[] images;

  /**
   * Index for image which to show.
   */
  private int showImage;
  /**
   * Index for image which to draw.
   */
  private int drawImage;

  /**
   * Starmap which to draw to minimap.
   */
  private StarMap map;

  /**
   * Top X coordinate of minimap
   */
  private int topX;
  /**
   * Top Y Coordinate of minimap
   */
  private int topY;
  /**
   * X-Coordinate where drawn image points
   */
  private int drawX;
  /**
   * Y-Coordinate where drawn image points
   */
  private int drawY;

  /**
   * Flag marking if map needs to be updated. Means basically that
   * full map is not shown at once.
   */
  private boolean needsUpdate;

  /**
   * Minimap constructor.
   * @param starMap StarMap
   */
  public Minimap(final StarMap starMap) {
    this.map = starMap;
    if (map.getMaxX() <= 50) {
      sectorSize = 4;
      size = map.getMaxX() * sectorSize;
      sectorsToShow = map.getMaxX();
      needsUpdate = false;
    } else     if (map.getMaxX() <= 75) {
      sectorSize = 3;
      size = map.getMaxX() * sectorSize;
      sectorsToShow = map.getMaxX();
      needsUpdate = false;
    } else     if (map.getMaxX() <= 128) {
      sectorSize = 2;
      size = map.getMaxX() * sectorSize;
      sectorsToShow = map.getMaxX();
      needsUpdate = false;
    } else {
      sectorSize = 2;
      size = MAX_SIZE_PX;
      sectorsToShow = 128;
      needsUpdate = true;
    }
    images = new BufferedImage[2];
    images[0] = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
    images[1] = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
    showImage = 0;
    drawImage = 1;
    topX = 0;
    topY = 0;
    drawX = 0;
    drawY = 0;
  }

  /**
   * Set point where minimap should be drawn.
   * @param x Top X coordinate
   * @param y Top Y coordinate
   */
  public void setDrawPoint(final int x, final int y) {
    topX = x;
    topY = y;
  }

  /**
   * Set point where minimap should be drawn.
   * @param x Center X coordinate
   * @param y Center Y coordinate
   */
  public void setCenterPoint(final int x, final int y) {
    topX = x - sectorsToShow / 2;
    topY = y - sectorsToShow / 2;
    if (topX < 0) {
      topX = 0;
    }
    if (topY < 0) {
      topY = 0;
    }
  }

  /**
   * Get center coordinate of minimap
   * @return X coordinate
   */
  public int getCenterX() {
    return drawX + size / 2;
  }
  /**
   * Get center coordinate of minimap
   * @return Y coordinate
   */
  public int getCenterY() {
    return drawY + size / 2;
  }
  /**
   * Get draw point to minimap X Coordinate
   * @return X coordinate
   */
  public int getDrawPointX() {
    return drawX;
  }

  /**
   * Get draw point to minimap Y Coordinate
   * @return Y coordinate
   */
  public int getDrawPointY() {
    return drawY;
  }

  /**
   * Get double buffered image which is ready.
   * @return Buffered Image
   */
  public BufferedImage getDrawnImage() {
    return images[showImage];
  }

  /**
   * Get single sector size in pixel in minimap.
   * @return Sector size
   */
  public int getSectorSize() {
    return sectorSize;
  }

  /**
   * Draw sector with single color
   * @param img Image where to draw
   * @param x X coordinate
   * @param y Y coordinate
   * @param color Color which to draw
   */
  public void drawSector(final BufferedImage img, final int x, final int y,
      final Color color) {
    for (int my = 0; my < sectorSize; my++) {
      for (int mx = 0; mx < sectorSize; mx++) {
        img.setRGB(x + mx, y + my, color.getRGB());
      }
    }
  }

  /**
   * Draw the minimap to buffered Image.
   */
  public void drawMinimap() {
    images[drawImage] = new BufferedImage(size, size,
        BufferedImage.TYPE_4BYTE_ABGR);
    BufferedImage img = images[drawImage];
    Graphics2D g2d = (Graphics2D) img.getGraphics();
    g2d.setColor(GuiStatics.COLOR_TRANSPARENT_GREY);
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    PlayerInfo info = map.getCurrentPlayerInfo();
    for (int y = 0; y < sectorsToShow; y++) {
      for (int x = 0; x < sectorsToShow; x++) {
        if (info != null && info.getSectorVisibility(new Coordinate(x + topX,
            y + topY)) != PlayerInfo.UNCHARTED) {
          CulturePower culture = map.getSectorCulture(x + topX, y + topY);
          if (culture != null) {
            int index = culture.getHighestCulture();
            if (index != -1) {
              Tile tile = Tiles.getTileByName("Player_" + index);
              if (tile != null) {
                tile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                    sectorSize);
              }
            }
          }
          Tile tile = map.getTile(x + topX, y + topY);
          if (map.getTileInfo(x + topX, y + topY)
              .getType() == SquareInfo.TYPE_GAS_PLANET && sectorSize == 4) {
            tile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                sectorSize);
          } else if (map.getTileInfo(x + topX, y + topY)
              .getType() == SquareInfo.TYPE_GAS_PLANET && sectorSize < 4) {
            if (tile.getName().contains("NE")) {
              Tile tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE);
              tmpTile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                  sectorSize);
            }
            if (tile.getName().contains("NW")) {
              Tile tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW);
              tmpTile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                  sectorSize);
            }
            if (tile.getName().contains("SW")) {
              Tile tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW);
              tmpTile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                  sectorSize);
            }
            if (tile.getName().contains("SE")) {
              Tile tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE);
              tmpTile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                  sectorSize);
            }
          }
          if (map.getTileInfo(x + topX, y + topY)
              .getType() == SquareInfo.TYPE_PLANET && sectorSize > 2) {
            Tile tmpTile = Tiles.getTileByName(TileNames.ICEWORLD2);
            tmpTile.drawMiniSector(img, x * sectorSize, y * sectorSize,
                sectorSize);
          } else if (map.getTileInfo(x + topX, y + topY)
              .getType() == SquareInfo.TYPE_PLANET && sectorSize <= 2) {
            drawSector(img, x * sectorSize, y * sectorSize,
                GuiStatics.COLOR_BRIGHT_WHITE);
          }
        } else {
          drawSector(img, x * sectorSize, y * sectorSize,
              GuiStatics.COLOR_TRANSPARENT_BLACK);
        }
        Tile tile = map.getTile(x + topX, y + topY);
        if (tile == null) {
          continue;
        }
        // Draw only non empty tiles
        if (map.getTileInfo(x + topX, y + topY)
                .getType() == SquareInfo.TYPE_SUN) {
          tile.drawMiniSector(img, x * sectorSize, y * sectorSize,
              sectorSize);
        }
      }
    }
    if (drawImage == 1) {
      showImage = 1;
      drawImage = 0;
    } else {
      showImage = 0;
      drawImage = 1;
    }
    drawX = topX;
    drawY = topY;
    needsUpdate = false;
  }

  /**
   * Force update map flag.
   * @param x Update x coordinate
   */
  public void updateMapX(final int x) {
    needsUpdate = true;
    topX = topX + x;
    if (topX < 0) {
      topX = 0;
    }
    if (topX > map.getMaxX() - 1) {
      topX = map.getMaxX() - 1;
    }
  }
  /**
   * Force update map flag.
   * @param y Update y coordinate
   */
  public void updateMapY(final int y) {
    needsUpdate = true;
    topY = topY + y;
    if (topY < 0) {
      topY = 0;
    }
    if (topY > map.getMaxY() - 1) {
      topY = map.getMaxY() - 1;
    }
  }
  /**
   * Return true if minimap needs to be update once and while.
   * @return True if update is needed.
   */
  public boolean needUpdate() {
    return needsUpdate;
  }
}
