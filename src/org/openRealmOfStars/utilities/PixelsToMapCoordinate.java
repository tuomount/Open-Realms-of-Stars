package org.openRealmOfStars.utilities;

import org.openRealmOfStars.mapTiles.Tile;

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
 * 
 * Utility to convert pixels to star map coordinate
 * 
 */
public class PixelsToMapCoordinate {

  
  /**
   * Relative X coordinate on star map
   */
  private int relativeMapX;
  /**
   * Relative Y coordinate on star map
   */
  private int relativeMapY;
  /**
   * Center drawn map X
   */
  private int centerMapX;
  /**
   * Center drawn map Y
   */
  private int centerMapY;
  /**
   * Pixel coordinate X
   */
  private int pixelX;
  /**
   * Pixel coordinate Y
   */
  private int pixelY;
  
  /**
   * X Coordinate where to start drawing the map in panel AKA panel
   * offset X
   */
  private int startX;
  /**
   * Y Coordinate where to start drawing the map in panel AKA panel
   * offset Y
   */
  private int startY;
  /**
   * How many tiles in panel on X axle
   */
  private int numXTiles;
  /**
   * How many tiles in panel on Y axle
   */
  private int numYTiles;
  /**
   * X coordinate where panel drawing ends
   */
  private int endX;
  /**
   * Y coordinate where panel drawing ends
   */
  private int endY;

  /**
   * Is coordinates out of panel
   */
  private boolean outOfPanel;
  
  /**
   * Convert pixel to map coordinate
   * @param mx Center of map X
   * @param my Center of map Y
   * @param px Pixel coordinate X
   * @param py Pixel coordinate Y
   * @param sx Start X pixel coordinate in panel
   * @param sy Start Y pixel coordinate in panel
   * @param radX View Port Radius X axel in star map
   * @param radY View port radius Y axel in star map
   */
  public PixelsToMapCoordinate(int mx,int my, int px, int py, int sx,int sy,
      int radX,int radY) {
    startX = sx;
    startY = sy;
    numXTiles = radX*2+1;
    numYTiles = radY*2+1;
    endX = startX+numXTiles*Tile.MAX_WIDTH;
    endY = startY+numYTiles*Tile.MAX_HEIGHT;
    centerMapX = mx;
    centerMapY = my;
    pixelX = px;
    pixelY = py;
    if (pixelX >= startX && pixelY >= startY 
        && pixelX < endX && pixelY < endY) {
      pixelX = pixelX-startX;
      pixelY = pixelY-startY;
      relativeMapX = (pixelX / Tile.MAX_WIDTH)-radX;
      relativeMapY = (pixelY / Tile.MAX_HEIGHT)-radY;
    } else {
      outOfPanel = true;
      relativeMapX = -1;
      relativeMapY = -1;
    }
  }

  /**
   * get the absolute map X coordinate
   * @return x coordinate or -1 if cannot be calculated
   */
  public int getMapX() {
    if (!isOutOfPanel()) {
      return centerMapX+relativeMapX;
    } else {
      return -1;
    }
  }

  /**
   * get the absolute map Y coordinate
   * @return x coordinate or -1 if cannot be calculated
   */
  public int getMapY() {
    if (!isOutOfPanel()) {
      return centerMapY+relativeMapY;
    } else {
      return -1;
    }
  }
  
  /**
   * Get Relative X coordinate
   * @return x coordinate or -1 if cannot be calculated
   */
  public int getRelativeX() {
    if (!isOutOfPanel()) {
      return relativeMapX;
    } else {
      return -1;
    }
    
  }

  /**
   * Get Relative Y coordinate
   * @return y coordinate or -1 if cannot be calculated
   */
  public int getRelativeY() {
    if (!isOutOfPanel()) {
      return relativeMapY;
    } else {
      return -1;
    }
    
  }
  
  /**
   * Are given coordinates out of panel and not in the map.
   * If outside then no map coordinates are calculated
   * @return true if pixel coordinates are out of map
   */
  public boolean isOutOfPanel() {
    return outOfPanel;
  }
}
