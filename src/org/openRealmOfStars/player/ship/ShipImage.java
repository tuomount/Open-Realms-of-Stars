package org.openRealmOfStars.player.ship;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.utilities.IOUtilities;

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
 * Ship image for 64x64 images read from single file
 * 
 */

public class ShipImage {

  /**
   * Ship image width
   */
  private static final int MAX_WIDTH = 64;
  /**
   * Ship image height
   */
  private static final int MAX_HEIGHT = 64;

  /**
   * Smaller Ship image width
   */
  private static final int MIN_WIDTH = 32;
  /**
   * Smaller Ship image height
   */
  private static final int MIN_HEIGHT = 32;
  
  /**
   * MAX size ship images
   */
  private BufferedImage[] shipImages;
  /**
   * Min size ship images
   */
  private BufferedImage[] smallShipImages;

  public static final int SCOUT = 0;
  public static final int COLONY = 1;
  public static final int DESTROYER = 2;
  public static final int PROBE = 3;
  public static final int SMALL_FREIGHTER = 4;
  public static final int SMALL_STARBASE = 5;
  public static final int CORVETTE = 6;
  public static final int MEDIUM_STARBASE = 7;
  public static final int MEDIUM_FREIGHTER = 8;
  public static final int CRUISER = 9;
  
  /**
   * Must be one bigger than last ship
   */
  private static final int NUMBER_OF_IMAGES = 10;
  
  /**
   * Initialize ship images
   * @param fileToRead Needs to be inside JAR file
   */
  public ShipImage(String fileToRead) {
    BufferedImage image = IOUtilities.loadImage(Icons.class.getResource(
        "/resources/images/"+ fileToRead));
    shipImages = new BufferedImage[NUMBER_OF_IMAGES];
    smallShipImages = new BufferedImage[NUMBER_OF_IMAGES];
    shipImages[SCOUT] = image64x64(image,0,0);
    smallShipImages[SCOUT] = scaleTo32x32(shipImages[SCOUT]);
    shipImages[COLONY] = image64x64(image,1,0);
    smallShipImages[COLONY] = scaleTo32x32(shipImages[COLONY]);
    shipImages[DESTROYER] = image64x64(image,2,0);
    smallShipImages[DESTROYER] = scaleTo32x32(shipImages[DESTROYER]);
    shipImages[PROBE] = image64x64(image,3,0);
    smallShipImages[PROBE] = scaleTo32x32(shipImages[PROBE]);
    shipImages[SMALL_FREIGHTER] = image64x64(image,4,0);
    smallShipImages[SMALL_FREIGHTER] = scaleTo32x32(shipImages[SMALL_FREIGHTER]);
    shipImages[SMALL_STARBASE] = image64x64(image,0,1);
    smallShipImages[SMALL_STARBASE] = scaleTo32x32(shipImages[SMALL_STARBASE]);
    shipImages[CORVETTE] = image64x64(image,1,1);
    smallShipImages[CORVETTE] = scaleTo32x32(shipImages[CORVETTE]);
    shipImages[MEDIUM_STARBASE] = image64x64(image,2,1);
    smallShipImages[MEDIUM_STARBASE] = scaleTo32x32(shipImages[MEDIUM_STARBASE]);
    shipImages[MEDIUM_FREIGHTER] = image64x64(image,3,1);
    smallShipImages[MEDIUM_FREIGHTER] = scaleTo32x32(shipImages[MEDIUM_FREIGHTER]);
    shipImages[CRUISER] = image64x64(image,4,1);
    smallShipImages[CRUISER] = scaleTo32x32(shipImages[CRUISER]);
  }
  
  private BufferedImage image64x64(BufferedImage image, int x, int y) throws
    RasterFormatException {
    
    if (x >= 0 && y >= 0 && x*MAX_WIDTH < image.getHeight() &&
        y*MAX_HEIGHT < image.getHeight()) {
      return image.getSubimage(x*MAX_WIDTH, y*MAX_HEIGHT, MAX_WIDTH, MAX_HEIGHT);
    } else {
      throw new RasterFormatException("Icon is outside of image.");
    }
  }
  
  /**
   * Get ship image by index. If index is out of bounds scout image is returned.
   * @param index
   * @return BufferedImage
   */
  public BufferedImage getShipImage(int index) {
    if (index >=0 && index < NUMBER_OF_IMAGES) {
      return shipImages[index];
    }
    return shipImages[0];
  }

  /**
   * Get small ship image by index. If index is out of bounds scout image is
   * returned.
   * @param index
   * @return BufferedImage
   */
  public BufferedImage getSmallShipImage(int index) {
    if (index >=0 && index < NUMBER_OF_IMAGES) {
      return smallShipImages[index];
    }
    return smallShipImages[0];
  }

  public static BufferedImage scaleTo32x32(BufferedImage source) {
    BufferedImage target = new BufferedImage(MIN_WIDTH, MIN_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    if (source.getHeight() == MAX_HEIGHT && source.getWidth() == MAX_WIDTH) {
      int mx = MAX_WIDTH/MIN_WIDTH;
      int my = MAX_HEIGHT/MIN_HEIGHT;
      for (int y = 0;y<MIN_HEIGHT;y++) {
        for (int x = 0;x<MIN_WIDTH;x++) {
          int color = source.getRGB(x*mx, y*my);
          target.setRGB(x, y, color);
        }
      }
    }
    return target;
  }


}
