package org.openRealmOfStars.gui.borders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

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
 * Very simple border made with two lines
 *
 */

public class SimpleBorder extends AbstractBorder {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private static final Color BRIGHT_BLUE = new Color(185, 190, 220);
  private static final Color TOP_LIGHT_BLUE = new Color(136, 140, 157);

  @Override
  public void paintBorder(final Component c, final Graphics g, final int x,
      final int y, final int width, final int height) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(TOP_LIGHT_BLUE);
    g2d.drawLine(0, 0, width, 0);
    g2d.drawLine(0, 0, 0, height);
    g2d.drawLine(0, height - 2, width, height - 2);
    g2d.drawLine(width - 2, 0, width - 2, height);
    g2d.setColor(BRIGHT_BLUE);
    g2d.drawLine(1, 1, width - 2, 1);
    g2d.drawLine(1, 1, 1, height - 2);
    g2d.drawLine(0, height - 1, width, height - 1);
    g2d.drawLine(width - 1, 0, width - 1, height);

  }

  @Override
  public Insets getBorderInsets(final Component c) {
    return getBorderInsets(c, new Insets(2, 2, 2, 2));
  }

  @Override
  public Insets getBorderInsets(final Component c, final Insets insets) {
    insets.left = 2;
    insets.top = 2;
    insets.right = 2;
    insets.bottom = 2;
    return insets;
  }

  @Override
  public boolean isBorderOpaque() {
    return false;
  }

}
