package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
 * Copyright (C) 2026 Tuomo Untinen
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
 */

import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * New cell renderer label.
 */
public class RenderLabel extends JLabel {

  private static final long serialVersionUID = 1L;

  @Override
  protected void paintComponent(final Graphics g) {
    // Fill background first (since we're opaque)
    g.setColor(getBackground());
    g.fillRect(0, 0, getWidth(), getHeight());

    // Draw icon if present
    int textX = 5;
    Icon icon = getIcon();
    if (icon != null) {
      int iconY = (getHeight() - icon.getIconHeight()) / 2;
      icon.paintIcon(this, g, 5, iconY);
      textX = 5 + icon.getIconWidth() + 5;
    }

    // Early exit if no text to draw
    String text = getText();
    if (text == null || text.isEmpty()) {
      return;
    }

    // Draw text
    g.setFont(getFont());
    g.setColor(getForeground());
    FontMetrics metrics = g.getFontMetrics(getFont());
    int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
    g.drawString(text, textX, y);
  }

}
