package org.openRealmOfStars.gui.list;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
 * Copyright (C) 2016-2023 Tuomo Untinen
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.openRealmOfStars.game.SavedGame;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;

/**
 *
 * Save game list renderer
 *
 */
public class SaveGameListRenderer implements ListCellRenderer<SavedGame> {

  /**
   * Custom JLabel for list cell rendering that properly handles painting
   */
  private static class SaveGameLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(final Graphics g) {
      // Fill background first (since we're opaque)
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());
      // Draw text
      int x = 5;
      g.setFont(getFont());
      g.setColor(getForeground());
      FontMetrics metrics = g.getFontMetrics(getFont());
      int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
      g.drawString(getText(), x, y);
    }
  }

  /**
   * Reusable label for rendering
   */
  private SaveGameLabel label = new SaveGameLabel();

  @Override
  public Component getListCellRendererComponent(
      final JList<? extends SavedGame> list, final SavedGame value,
      final int index, final boolean isSelected, final boolean cellHasFocus) {
    label.setFont(GuiFonts.getFontCubellan());
    if (value != null) {
      String text = value.getFilename() + " - " + value.getTime()
          + " Star year: " + value.getStarYear() + " - "
          + value.getEmpireName() + " Realms: "
          + value.getRealms() + " - "
          + value.getGalaxySize();
      label.setText(text);
    }
    label.setOpaque(true);
    if (isSelected) {
      label.setForeground(GuiStatics.getCoolSpaceColor());
      label.setBackground(Color.BLACK);
    } else {
      label.setForeground(GuiStatics.COLOR_GREY_TEXT);
      label.setBackground(Color.BLACK);
    }
    return label;
  }
}