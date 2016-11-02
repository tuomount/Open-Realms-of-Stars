package org.openRealmOfStars.gui.icons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.openRealmOfStars.mapTiles.Tiles;
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
 * Animated image where each animation frame is read from same image but
 * image just got all frames in width.
 *
 */

public class AnimatedImage {

  /**
   * Animation frames in list
   */
  private List<BufferedImage> animations;

  /**
   * Frame index
   */
  private int frame;

  /**
   * Image size in X axel in pixel
   */
  private int sizeX;
  /**
   * Image size in Y axel in pixel
   */
  private int sizeY;

  /**
   * Load animated image from file
   * @param sx Image size in X axel
   * @param sy Image size in Y axel
   * @param filename Filename to load. File must be as a resource file in jar.
   */
  public AnimatedImage(final int sx, final int sy, final String filename) {
    BufferedImage bigImage = IOUtilities
        .loadImage(Tiles.class.getResource(filename));
    sizeX = sx;
    sizeY = sy;
    animations = new ArrayList<>();
    int frames = bigImage.getWidth() / sizeX;
    for (int i = 0; i < frames; i++) {
      BufferedImage img = bigImage.getSubimage(i * sizeX, 0, sizeX, sizeY);
      animations.add(img);
    }
    frame = 0;
  }

  /**
   * Get next animation frame and update frame number
   * @return BufferedImage
   */
  public BufferedImage getNextFrame() {
    BufferedImage result = animations.get(frame);
    frame++;
    if (frame == animations.size()) {
      frame = 0;
    }
    return result;
  }

  /**
   * Get animation frame
   * @param index Frame number
   * @return Animation frame or if not found with index then frame 0 is returned
   */
  public BufferedImage getFrame(final int index) {
    if (index >= 0
        && index < animations.size()) { return animations.get(index); }
    return animations.get(0);
  }

  /**
   * Return maximum frame number
   * @return Maximum frame number
   */
  public int getMaxFrames() {
    return animations.size();
  }

  /**
   * Get animation width
   * @return width in pixels
   */
  public int getWidth() {
    return sizeX;
  }

  /**
   * Get animation height
   * @return height in pixels
   */
  public int getHeight() {
    return sizeY;
  }

}
