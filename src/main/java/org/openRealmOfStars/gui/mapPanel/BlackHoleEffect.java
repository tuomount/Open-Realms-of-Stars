package org.openRealmOfStars.gui.mapPanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.mapTiles.Tile;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Class for drawing black hole in to starmap panels.
*
*/
public class BlackHoleEffect {

  /**
   * Background tile used for drawing the distrotion.
   */
  private BufferedImage backgroundTile;

  /**
   * Constructor for blackhole effect.
   * @param img Background image for blackhole
   */
  public BlackHoleEffect(final BufferedImage img) {
    updateBackground(img);
  }

  /**
   * Calculate average value for each channel.
   * Alpha channel is taken from color1.
   * @param color1 Color value 1
   * @param color2 Color value 2
   * @return Average color
   */
  private int mixColors(final int color1, final int color2) {
    int red1 = color1 & 0xff;
    int green1 = (color1 & 0xff00) >> 8;
    int blue1 = (color1 & 0xff0000) >> 16;
    int alpha1 = (color1 & 0xff000000) >> 24;
    int red2 = color2 & 0xff;
    int green2 = (color2 & 0xff00) >> 8;
    int blue2 = (color2 & 0xff0000) >> 16;
    red1 = (red1 + red2) / 2;
    green1 = (green1 + green2) / 2;
    blue1 = (blue1 + blue2) / 2;
    int color = red1 + green1 << 8 + blue1 << 16 + alpha1 << 24;
    return color;
  }
  /**
   * Update background image for black hole.
   * @param img Background image to update.
   */
  public void updateBackground(final BufferedImage img) {
    if (img.getWidth() == Tile.MAX_WIDTH
        && img.getHeight() == Tile.MAX_HEIGHT) {
      backgroundTile = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
          BufferedImage.TYPE_INT_ARGB);
      backgroundTile.getGraphics().drawImage(img, 0, 0, null);
      backgroundTile.setRGB(0, 0,
          backgroundTile.getRGB(Tile.MAX_WIDTH / 2, Tile.MAX_HEIGHT / 2));
      backgroundTile.setRGB(Tile.MAX_WIDTH - 1, 0,
          backgroundTile.getRGB(Tile.MAX_WIDTH / 2 + 1, Tile.MAX_HEIGHT / 2));
      backgroundTile.setRGB(0, Tile.MAX_HEIGHT - 1,
          backgroundTile.getRGB(Tile.MAX_WIDTH / 2, Tile.MAX_HEIGHT / 2 + 1));
      backgroundTile.setRGB(Tile.MAX_WIDTH - 1, Tile.MAX_HEIGHT - 1,
          backgroundTile.getRGB(Tile.MAX_WIDTH / 2 + 1,
              Tile.MAX_HEIGHT / 2 + 1));
      for (int i = 0; i < Tile.MAX_WIDTH; i++) {
        int color1 = img.getRGB(Tile.MAX_WIDTH / 2, Tile.MAX_HEIGHT / 2);
        int color2 = backgroundTile.getRGB(i, 1);
        backgroundTile.setRGB(i, 0, mixColors(color1, color2));
        color1 = img.getRGB(Tile.MAX_WIDTH / 2, Tile.MAX_HEIGHT / 2 + 1);
        color2 = backgroundTile.getRGB(i, Tile.MAX_HEIGHT - 2);
        backgroundTile.setRGB(i, Tile.MAX_HEIGHT - 1, mixColors(color1,
            color2));
      }
      for (int i = 0; i < Tile.MAX_HEIGHT; i++) {
        int color1 = img.getRGB(Tile.MAX_WIDTH / 2 + 1, Tile.MAX_HEIGHT / 2);
        int color2 = backgroundTile.getRGB(1, i);
        backgroundTile.setRGB(0, i, mixColors(color1, color2));
        color1 = img.getRGB(Tile.MAX_WIDTH / 2 + 1, Tile.MAX_HEIGHT / 2 + 1);
        color2 = backgroundTile.getRGB(Tile.MAX_WIDTH - 2, i);
        backgroundTile.setRGB(Tile.MAX_HEIGHT - 1, i, mixColors(color1,
            color2));
      }
    } else {
      backgroundTile = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
          BufferedImage.TYPE_INT_ARGB);
    }
  }

  /**
   * Raw pixel value for black
   */
  private static final int BLACK = -16777216;
  /**
   * Raw pixel value for red where distrotion is created.
   */
  private static final int DISTROTION = -65536;
  /**
   * Raw pixel value for empty.
   */
  private static final int EMPTY = 0;

  /**
   * Draw blackhole tile.
   * @param g Graphics2d which is used for drawing
   * @param x X coordinate
   * @param y Y Coordinate
   * @param tile Blackhole tile
   */
  public void drawBlackholeTile(final Graphics2D g, final int x, final int y,
      final Tile tile) {
    BufferedImage temp = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
        BufferedImage.TYPE_INT_ARGB);
    for (int i = 0; i < Tile.MAX_HEIGHT; i++) {
      for (int j = 0; j < Tile.MAX_WIDTH; j++) {
        int color = tile.getRawTile().getRGB(j, i);
        if (color == DISTROTION) {
          temp.setRGB(j, i, backgroundTile.getRGB(j, i));
        } else if (color == BLACK) {
          temp.setRGB(j, i, BLACK);
        } else if (color == EMPTY) {
          // Do nothing
          color = EMPTY;
        } else {
          temp.setRGB(j, i, color);
        }
      }
    }
    g.drawImage(temp, x, y, null);
  }
}
