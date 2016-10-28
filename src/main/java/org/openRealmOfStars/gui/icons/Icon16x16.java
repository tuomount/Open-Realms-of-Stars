package org.openRealmOfStars.gui.icons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import javax.swing.Icon;
import javax.swing.ImageIcon;


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
 * Class for handling icons used in GUI components
 *
 */

public class Icon16x16 {

  /**
   * Icon Maximum width
   */
  public static final int MAX_WIDTH = 16;
  /**
   * Icon Maximum height
   */
  public static final int MAX_HEIGHT = 16;

  /**
   * Graphical data for Icon
   */
  private BufferedImage img;
  
  /**
   * Icon name
   */
  private String name;
  
  /**
   * Icon index in list of icons
   */
  private int Index=-1;
  
  /**
   * Get Icon from image, where x is number of icons in X axel and
   * y is number of icons in y axel.
   * @param image BufferedImage
   * @param x X-axel coordinate
   * @param y Y-axel coordinate
   * @param name Name for the icon
   * @throws RasterFormatException if icon is outside of image.
   */
  public Icon16x16(BufferedImage image,int x, int y,String name) throws 
  RasterFormatException {
    if (x >= 0 && y >= 0 && x*MAX_WIDTH < image.getHeight() &&
        y*MAX_HEIGHT < image.getHeight()) {
      img = image.getSubimage(x*MAX_WIDTH, y*MAX_HEIGHT, MAX_WIDTH, MAX_HEIGHT);
      this.name =name;
    } else {
      throw new RasterFormatException("Icon is outside of image.");
    }
  }

  /**
   * Draw icon to coordinates
   * @param g Graphics2D using for drawing
   * @param x Coordinates on x axel
   * @param y Coordinates on y axel
   */
  public void draw(Graphics2D g,int x, int y) {
    g.drawImage(img, x, y, null);
  }

  /**
   * Get the Icon name
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
  
  public BufferedImage getIcon() {
    return img;
  }
  
  /**
   * Get as Icon
   * @return Icon
   */
  public Icon getAsIcon() {
    return new ImageIcon(img);
  }

}
