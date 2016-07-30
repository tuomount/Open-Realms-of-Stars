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
   * Scout ship image
   */
  private BufferedImage scoutImage;

  /**
   * Colony ship image
   */
  private BufferedImage colonyImage;

  /**
   * Destroyer ship image
   */
  private BufferedImage destroyerImage;

  /**
   * Probe ship image
   */
  private BufferedImage probeImage;

  /**
   * Small freighter ship image
   */
  private BufferedImage smallFreighterImage;

  /**
   * Small starbase image
   */
  private BufferedImage smallStarbaseImage;

  /**
   * Initialize ship images
   * @param fileToRead Needs to be inside JAR file
   */
  public ShipImage(String fileToRead) {
    BufferedImage image = IOUtilities.loadImage(Icons.class.getResource(
        "/resources/images/"+ fileToRead));
    scoutImage = image64x64(image,0,0);
    colonyImage = image64x64(image,1,0);
    destroyerImage = image64x64(image,2,0);
    probeImage = image64x64(image,3,0);
    smallFreighterImage = image64x64(image,4,0);
    smallStarbaseImage = image64x64(image,0,1);
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
   * Get scout ship image
   * @return Scout image
   */
  public BufferedImage getScoutImage() {
    return scoutImage;
  }
  
  /**
   * Get colony ship image
   * @return Colony image
   */
  public BufferedImage getColonyImage() {
    return colonyImage;
  }

  /**
   * Get destroyer ship image
   * @return Destroyer image
   */
  public BufferedImage getDestroyerImage() {
    return destroyerImage;
  }

  /**
   * Get probe ship image
   * @return Probe image
   */
  public BufferedImage getProbeImage() {
    return probeImage;
  }

  /**
   * Get small freighter ship image
   * @return Small freighter image
   */
  public BufferedImage getSmallFreighterImage() {
    return smallFreighterImage;
  }

  /**
   * Get small star base image
   * @return Small star base image
   */
  public BufferedImage getSmallStarbaseImage() {
    return smallStarbaseImage;
  }

}
