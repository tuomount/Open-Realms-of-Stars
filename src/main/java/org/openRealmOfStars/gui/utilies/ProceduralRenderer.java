package org.openRealmOfStars.gui.utilies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* Thread for procedural rendering of background stars.
*
*/
public class ProceduralRenderer extends Thread {

  /**
   * Has thread started?
   */
  private boolean started;

  /**
   * Is thread running
   */
  private boolean running;

  /**
   * Background stars image
   */
  private BufferedImage stars;

  /**
   * Constructor for Procedural Renderer.
   */
  public ProceduralRenderer() {
    started = false;
    running = false;
    stars = null;
  }
  /**
   * Has thread started yet
   * @return True if started
   */
  public synchronized boolean isStarted() {
    return started;
  }

  /**
   * Is thread still running
   * @return True if running
   */
  public synchronized boolean isRunning() {
    return running;
  }

  @Override
  public synchronized void start() {
    started = true;
    super.start();
  }

  /**
   * Put single pixel in image with coordinate check
   * @param image Image where to draw.
   * @param x X coordinate
   * @param y Y coordinate
   * @param color Pixel color
   */
  private static void putPixel(final BufferedImage image, final int x,
      final int y, final int color) {
    if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
      image.setRGB(x, y, color);
    }
  }

  @Override
  public void run() {
    synchronized (this) {
      running = true;
    }
    stars = new BufferedImage(2100, 1600,
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics graphics = stars.getGraphics();
    if (graphics instanceof Graphics2D) {
      Graphics2D g2d = (Graphics2D) graphics;
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, stars.getWidth(),
          stars.getHeight());
      int w = stars.getWidth();
      int h = stars.getHeight();
      int darkNebulae = DiceGenerator.getRandom(100, 400);
      int mx = DiceGenerator.getRandom(w);
      int my = DiceGenerator.getRandom(h);
      for (int i = 0; i < darkNebulae; i++) {
        for (int j = 0; j < 900; j++) {
          int shade = DiceGenerator.getRandom(5, 24);
          int transparent = DiceGenerator.getRandom(30, 128);
          Color c = new Color(shade, shade, shade, transparent);
          g2d.setColor(c);
          g2d.fillOval(mx, my, DiceGenerator.getRandom(4, 20),
              DiceGenerator.getRandom(4, 20));
          int choice = DiceGenerator.getRandom(3);
          if (choice == 0) {
            my = my - DiceGenerator.getRandom(1, 6);
            if (my < 0) {
              my = my + h;
            }
          }
          if (choice == 1) {
            mx = mx - DiceGenerator.getRandom(1, 6);
            if (mx < 0) {
              mx = mx + w;
            }
          }
          if (choice == 2) {
            mx = mx + DiceGenerator.getRandom(1, 6);
            if (mx > w) {
              mx = mx - w;
            }
          }
          if (choice == 3) {
            my = my + DiceGenerator.getRandom(1, 6);
            if (my > h) {
              my = my - h;
            }
          }
        }
      }
      int smallStars = DiceGenerator.getRandom(3200, 4800);
      for (int i = 0; i < smallStars; i++) {
        int shade = DiceGenerator.getRandom(108, 215);
        Color c = new Color(shade + DiceGenerator.getRandom(40),
            shade + DiceGenerator.getRandom(40),
            shade + DiceGenerator.getRandom(40));
        putPixel(stars, DiceGenerator.getRandom(w),
            DiceGenerator.getRandom(h), c.getRGB());
      }
      int mediumStars = DiceGenerator.getRandom(900, 1200);
      for (int i = 0; i < mediumStars; i++) {
        int shade = DiceGenerator.getRandom(168, 205);
        Color c = new Color(shade + DiceGenerator.getRandom(40),
            shade + DiceGenerator.getRandom(40),
            shade + DiceGenerator.getRandom(40));
        int x = DiceGenerator.getRandom(w);
        int y = DiceGenerator.getRandom(h);
        putPixel(stars, x, y, c.getRGB());
        putPixel(stars, x + 1, y, c.getRGB());
        putPixel(stars, x, y + 1, c.getRGB());
        putPixel(stars, x + 1, y + 1, c.getRGB());

      }
      int bigStars = DiceGenerator.getRandom(200, 600);
      for (int i = 0; i < bigStars; i++) {
        int shade = DiceGenerator.getRandom(200, 215);
        int r = DiceGenerator.getRandom(40);
        int g = DiceGenerator.getRandom(40);
        int b = DiceGenerator.getRandom(40);
        Color c = new Color(shade + r, shade + g, shade + b);
        Color c2 = new Color((shade + r) / 2, (shade + g) / 2,
            (shade + b) / 2);
        Color c3 = new Color((shade + r) / 3, (shade + g) / 3,
            (shade + b) / 3);
        int x = DiceGenerator.getRandom(w);
        int y = DiceGenerator.getRandom(h);
        putPixel(stars, x, y, c.getRGB());
        putPixel(stars, x + 1, y, c2.getRGB());
        putPixel(stars, x, y + 1, c2.getRGB());
        putPixel(stars, x - 1, y, c2.getRGB());
        putPixel(stars, x, y - 1, c2.getRGB());
        putPixel(stars, x - 1, y - 1, c3.getRGB());
        putPixel(stars, x + 1, y - 1, c3.getRGB());
        putPixel(stars, x - 1, y + 1, c3.getRGB());
        putPixel(stars, x + 1, y + 1, c3.getRGB());
      }
      int massiveStars = DiceGenerator.getRandom(100, 400);
      for (int i = 0; i < massiveStars; i++) {
        int shade = DiceGenerator.getRandom(200, 215);
        int r = DiceGenerator.getRandom(40);
        int g = DiceGenerator.getRandom(40);
        int b = DiceGenerator.getRandom(40);
        Color c = new Color(shade + r, shade + g, shade + b);
        Color c2 = new Color((shade + r) / 2, (shade + g) / 2,
            (shade + b) / 2);
        Color c3 = new Color((shade + r) / 3, (shade + g) / 3,
            (shade + b) / 3);
        int x = DiceGenerator.getRandom(w);
        int y = DiceGenerator.getRandom(h);
        putPixel(stars, x, y, c.getRGB());
        putPixel(stars, x + 1, y, c.getRGB());
        putPixel(stars, x, y + 1, c.getRGB());
        putPixel(stars, x - 1, y, c.getRGB());
        putPixel(stars, x, y - 1, c.getRGB());
        putPixel(stars, x - 1, y - 1, c2.getRGB());
        putPixel(stars, x + 1, y - 1, c2.getRGB());
        putPixel(stars, x - 1, y + 1, c2.getRGB());
        putPixel(stars, x + 1, y + 1, c2.getRGB());
        putPixel(stars, x - 2, y, c3.getRGB());
        putPixel(stars, x + 2, y, c3.getRGB());
        putPixel(stars, x, y - 2, c3.getRGB());
        putPixel(stars, x, y + 2, c3.getRGB());
      }
    }
    synchronized (this) {
      running = false;
    }
  }

  /**
   * Get background stars image
   * @return BufferedImage.
   */
  public BufferedImage getStars() {
    if (isStarted() && !isRunning()) {
      return stars;
    }
    return null;
  }
}
