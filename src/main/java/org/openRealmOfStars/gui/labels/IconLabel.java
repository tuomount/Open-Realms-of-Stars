package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
 * Copyright (C) 2016 Tuomo Untinen
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FontMetrics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.gui.util.UIScale;

/**
 *
 * Handling Icon label which draws Icon and text to next it.
 * Icon and text are transparent so parent is required.
 *
 */
public class IconLabel extends JLabel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Icon to draw
   */
  private Icon16x16 icon;

  /**
   * Get left side icon
   *
   * @return Icon
   */
  public Icon16x16 getLeftIcon() {
    return icon;
  }

  /**
   * Set icon on left side of Label
   *
   * @param leftIcon Icon to draw
   */
  public void setLeftIcon(final Icon16x16 leftIcon) {
    this.icon = leftIcon;
  }

  /**
   * Create Icon label with transparency
   *
   * @param parent Parent component can be null
   * @param icon   Icon to draw, if null then not draw
   * @param text   Text to show
   */
  @SuppressWarnings("PMD.UnusedFormalParameter")
  public IconLabel(final Component parent, final Icon16x16 icon,
      final String text) {
    super(text);
    this.icon = icon;
    this.setForeground(GuiStatics.getCoolSpaceColor());
    this.setFont(GuiFonts.getFontCubellan());
    Dimension size = this.getPreferredSize();
    if (icon != null) {
      size.width = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), text)
          + this.icon.getIcon().getWidth() + UIScale.scale(10);
      size.height = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(),
          text);
      if (size.height < this.icon.getIcon().getHeight() + UIScale.scale(2)) {
        size.height = this.icon.getIcon().getHeight() + UIScale.scale(2);
      }
    } else {
      size.width = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), text)
          + UIScale.scale(10);
      size.height = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(),
          text);
    }
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }

  @Override
  public JToolTip createToolTip() {
    JToolTip toolTip = super.createToolTip();
    toolTip.setForeground(GuiStatics.getCoolSpaceColor());
    toolTip.setBackground(GuiStatics.getCoolSpaceColorDark());
    toolTip.setBorder(BorderFactory
        .createLineBorder(GuiStatics.getCoolSpaceColorDarker()));
    return toolTip;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    // Don't call parent.repaint() - causes infinite repaint loop
    // Draw icon if present
    if (icon != null) {
      int iconHeight = icon.getIcon().getHeight();
      int iconY = (this.getHeight() - iconHeight) / 2;
      g.drawImage(icon.getIcon(), 0, iconY, null);
    }
    // Early exit if no text to draw
    String text = this.getText();
    if (text == null || text.isEmpty()) {
      return;
    }
    // Calculate text position and draw
    g.setFont(this.getFont());
    g.setColor(this.getForeground());
    FontMetrics metrics = g.getFontMetrics(this.getFont());
    int textY = (this.getHeight() - metrics.getHeight()) / 2
        + metrics.getAscent();
    int textX;
    if (icon != null) {
      textX = icon.getIcon().getWidth() + UIScale.scale(5);
    } else {
      textX = UIScale.scale(5);
    }
    g.drawString(text, textX, textY);
  }
}
