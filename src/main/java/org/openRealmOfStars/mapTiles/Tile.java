package org.openRealmOfStars.mapTiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;


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
  private int Index=-1;
  
  /**
   * Get tile from tileset image, where x is number of tiles in X axel and
   * y is number of tiles in y axel.
   * @param tilesetImage BufferedImage
   * @param x X-axel coordinate
   * @param y Y-axel coordinate
   * @param name Name for the tile
   * @throws RasterFormatException if tile is outside of tileset image.
   */
  public Tile(BufferedImage tilesetImage,int x, int y,String name) throws 
  RasterFormatException {
    if (x >= 0 && y >= 0 && x*MAX_WIDTH < tilesetImage.getHeight() &&
        y*MAX_HEIGHT < tilesetImage.getHeight()) {
      img = tilesetImage.getSubimage(x*MAX_WIDTH, y*MAX_HEIGHT, MAX_WIDTH, MAX_HEIGHT);
      this.name =name;
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
  public void draw(Graphics2D g,int x, int y) {
    g.drawImage(img, x, y, null);
  }

  /**
   * Get the Tile name
   * @return Name
   */
  public String getName() {
    return name;
  }

  public int getIndex() {
    return Index;
  }

  public void setIndex(int index) {
    Index = index;
  }

}
