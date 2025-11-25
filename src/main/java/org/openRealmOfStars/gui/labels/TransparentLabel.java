package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
 * Copyright (C) 2016-2021 Tuomo Untinen
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.EtchedBorder;

import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.gui.util.UIScale;

/**
 *
 * Handling label which draws text which is on transparent label.
 *
 */
public class TransparentLabel extends JLabel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Wrap text from white spaces
   */
  private boolean wrap;

  /**
   * Create label with transparency without borders or auto wrap.
   * @param parent Parent component (kept for API compatibility)
   * @param text Text to show
   */
  @SuppressWarnings("PMD.UnusedFormalParameter")
  public TransparentLabel(final Component parent, final String text) {
    super(text);
    // Mark as non-opaque so Swing handles parent repainting automatically
    setOpaque(false);
    this.setForeground(GuiStatics.getCoolSpaceColor());
    this.setFont(GuiFonts.getFontCubellan());
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), text)
        + 10;
    size.height = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(), text);
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }

  /**
   * Create label with transparency
   * @param parent Parent component (kept for API compatibility)
   * @param text Text to show
   * @param border Add border
   * @param autoWrap Wrap words from space
   */
  @SuppressWarnings("PMD.UnusedFormalParameter")
  public TransparentLabel(final Component parent, final String text,
      final boolean border, final boolean autoWrap) {
    super(text);
    // Mark as non-opaque so Swing handles parent repainting automatically
    setOpaque(false);
    this.setForeground(GuiStatics.getCoolSpaceColor());
    this.setFont(GuiFonts.getFontCubellan());
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), text)
        + 10;
    size.height = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(), text);
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
    this.wrap = autoWrap;
    if (border) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
          GuiStatics.COLOR_GREY_80, GuiStatics.COLOR_GREY_40));
    }
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
    // Non-opaque component - Swing handles parent repainting automatically

    // Early exit if no text to draw
    String text = this.getText();
    if (text == null || text.isEmpty()) {
      return;
    }

    int x = 0;
    int y = 0;
    // Border is set in constructor, not here (setBorder triggers repaint loop)
    g.setFont(this.getFont());
    g.setColor(this.getForeground());
    StringBuilder sb = new StringBuilder(text.length() + 10);
    if (wrap) {
      String[] texts = text.split(" ");
      int totalHeight = 0;
      int totalLineLen = 0;
      int spaceLen = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(),
          " ");
      for (int i = 0; i < texts.length; i++) {
        int textHeight = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(),
            texts[i]);
        int textLen = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(),
            texts[i]);
        if (totalLineLen + textLen > this.getWidth() - 10) {
          sb.append("\n");
          sb.append(texts[i]);
          totalLineLen = textLen;
        } else {
          totalLineLen = totalLineLen + textLen + spaceLen;
          if (sb.toString().isEmpty()) {
            sb.append(texts[i]);
          } else {
            sb.append(" ");
            sb.append(texts[i]);
          }
        }
        totalHeight = totalHeight + textHeight;
      }
      if (texts.length > 0) {
        totalHeight = totalHeight / texts.length;
      }
      texts = sb.toString().split("\n");
      totalHeight = totalHeight * texts.length;
      for (int i = 0; i < texts.length; i++) {
        int textWidth = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(),
            texts[i]);
        int textHeight = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(),
            texts[i]);
        x = this.getWidth() / 2 - textWidth / 2;
        y = this.getHeight() / 2 - totalHeight / 2 + textHeight;
        g.drawString(texts[i], x, y + i * textHeight);
      }
    } else {
      g.drawString(text, x + UIScale.scale(5),
          y + UIScale.scale(10));
    }
  }

}
