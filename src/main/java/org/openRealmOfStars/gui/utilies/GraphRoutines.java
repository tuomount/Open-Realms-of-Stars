package org.openRealmOfStars.gui.utilies;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016-2018  Tuomo Untinen
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
* Collection of graphical routines.
*
*/
public final class GraphRoutines {

  /**
   * Hiding the constructor for utility class
   */
  private GraphRoutines() {
    // Nothing to do here
  }

  /**
   * Draw tiling for graphics with give image.
   * @param gr Graphics used for drawing
   * @param img Buffered Image for tiling
   * @param offsetX Offset for X coordinate
   * @param offsetY Offset for Y coordinate
   * @param width Maximum width for drawing
   * @param height Maximum heigth for drawing
   */
  public static void drawTiling(final Graphics2D gr, final BufferedImage img,
      final int offsetX, final int offsetY, final int width, final int height) {
    int singleOffsetX = 0;
    int singleOffsetY = 0;
    if (offsetX > 0) {
      int countX = offsetX / img.getWidth();
      singleOffsetX = -1 * (countX * img.getWidth() - offsetX);
    }
    if (offsetX < 0) {
      int countX = offsetX / img.getWidth();
      singleOffsetX = countX * img.getWidth() + offsetX;
    }
    if (offsetY > 0) {
      int countY = offsetY / img.getHeight();
      singleOffsetY = -1 * (countY * img.getHeight() - offsetY);
    }
    if (offsetY < 0) {
      int countY = offsetY / img.getHeight();
      singleOffsetY = countY * img.getHeight() + offsetY;
    }
    int countX = 1 + (width - singleOffsetX) / img.getWidth();
    int countY = 1 + (height - singleOffsetY) / img.getHeight();
    for (int y = 0; y < countY; y++) {
      for (int x = 0; x < countX; x++) {
        gr.drawImage(img, singleOffsetX + x * img.getWidth(),
            singleOffsetY + y * img.getHeight(), null);
      }
    }
  }

  /**
   * Scale image and maintaing the aspect ration
   * @param image Image to scale
   * @param maxWidth New maximum width
   * @param maxHeight New maximum height
   * @return Scaled buffered image
   */
  public static BufferedImage scaleImage(final BufferedImage image,
      final int maxWidth, final int maxHeight) {
    if (image == null) {
      return null;
    }
    int origWidth = image.getWidth();
    int origHeight = image.getHeight();
    int width = origHeight;
    int height = origHeight;
    if (origWidth > maxWidth) {
      width = maxWidth;
      height = width * origHeight / origWidth;
    }
    if (height > maxHeight) {
      height = maxHeight;
      width = height * origWidth / origHeight;
    }
    BufferedImage resizedImage = new BufferedImage(width, height,
        image.getType());
    Graphics2D gr2D = resizedImage.createGraphics();
    gr2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    gr2D.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
    gr2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    gr2D.drawImage(image, 0, 0, width, height, null);
    gr2D.dispose();
    return resizedImage;
  }
}
