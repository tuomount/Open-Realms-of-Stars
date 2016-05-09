package org.openRealmOfStars.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.StarMap;

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
 * Class for handling the star map drawing to JPanel
 * 
 */

public class MapPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private final static int WIDTH = 864;
  private final static int HEIGHT = 672;
  
  /**
   * How many tiles can be fitted on half of the panel
   * This is for X axel.
   */
  private int viewPointX;
  /**
   * How many tiles can be fitted on half of the panel.
   * This is for Y axel.
   */
  private int viewPointY;

  /**
   * View point offset for x axel
   */
  private int viewPointOffsetX;

  /**
   * View point offset for Y axel
   */
  private int viewPointOffsetY;

  /**
   * Where the map is actually drawn
   */
  private BufferedImage screen;
  
  public MapPanel() {
    screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Dimension size = new Dimension(WIDTH, HEIGHT);
    this.setSize(size);
    this.setPreferredSize(size);
    this.setMinimumSize(size);
    calculateViewPoints();
    this.setBackground(Color.blue);
    Graphics2D gr = screen.createGraphics();
    gr.setColor(new Color(0,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
  }
  

  /**
   * Calculate view according the actual panel size;
   */
  private void calculateViewPoints() {
    viewPointX = ((this.getWidth()/Tile.MAX_WIDTH)-1)/2;
    viewPointY = ((this.getHeight()/Tile.MAX_HEIGHT)-1)/2;
    if (viewPointX < 1) {
      viewPointX = 1;
    }
    if (viewPointY < 1) {
      viewPointY = 1;
    }
    viewPointOffsetX = this.getWidth()-(2*viewPointX*Tile.MAX_WIDTH+
        Tile.MAX_WIDTH);
    viewPointOffsetX = viewPointOffsetX/2;
    viewPointOffsetY = this.getHeight()-(2*viewPointY*Tile.MAX_HEIGHT+
        Tile.MAX_HEIGHT);
    viewPointOffsetY = viewPointOffsetY/2;
  }
  @Override
  public void setMaximumSize(Dimension maximumSize) {
    super.setMaximumSize(maximumSize);
    screen = new BufferedImage(this.getWidth(), this.getHeight(),
                       BufferedImage.TYPE_INT_ARGB);
    calculateViewPoints();
  }


  @Override
  public void setMinimumSize(Dimension minimumSize) {
    super.setMinimumSize(minimumSize);
    calculateViewPoints();
  }


  @Override
  public void setPreferredSize(Dimension preferredSize) {    
    super.setPreferredSize(preferredSize);
    calculateViewPoints();
  }


  @Override
  public void setSize(Dimension d) {
    super.setSize(d);
    calculateViewPoints();
  }


  @Override
  public void setSize(int width, int height) {
    super.setSize(width, height);
    calculateViewPoints();
  }


  @Override
  public void paint(Graphics arg0) {
    super.paint(arg0);
    if (screen != null) {
      arg0.drawImage(screen, 0, 0, null);
    }
  }
  
  public void drawMap(StarMap starMap, int x, int y) {
    if (screen == null) {
      calculateViewPoints();
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
      } else {
        return;
      }
    }
    Graphics2D gr = screen.createGraphics();
    gr.setColor(new Color(0,0,0));
    gr.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    // Center coordinates
    int cx = x;
    int cy = y;
    if (cx < viewPointX) {
      cx = viewPointX;
    }
    if (cx > starMap.getMaxX()-viewPointX) {
      cx = starMap.getMaxX()-viewPointX;
    }
    if (cy < viewPointY) {
      cy = viewPointY;
    }
    if (cy > starMap.getMaxY()-viewPointY) {
      cy = starMap.getMaxY()-viewPointY;
    }
    int[][] map = starMap.getTiles();
    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    for (int j=-viewPointY;j<viewPointY;j++) {
      for (int i=-viewPointX;i<viewPointX;i++) {
        gr.setColor(Color.blue);
        gr.drawLine(pixelX, pixelY, pixelX+Tile.MAX_WIDTH, pixelY);
        gr.drawLine(pixelX, pixelY, pixelX, pixelY+Tile.MAX_HEIGHT);
        gr.drawLine(pixelX+Tile.MAX_WIDTH, pixelY, pixelX+Tile.MAX_WIDTH, pixelY+Tile.MAX_HEIGHT);
        gr.drawLine(pixelX, pixelY+Tile.MAX_HEIGHT, pixelX+Tile.MAX_WIDTH, pixelY+Tile.MAX_HEIGHT);
        Tile tile = Tiles.getTileByIndex(map[i+cx][j+cy]);
        if (!tile.getName().equals(TileNames.EMPTY)) {
          // Draw only non empty tiles
          tile.draw(gr, pixelX, pixelY);
        }
        pixelX=pixelX+Tile.MAX_WIDTH;
      }
      pixelX = viewPointOffsetX;
      pixelY=pixelY+Tile.MAX_HEIGHT;
    }
  }
  
}
