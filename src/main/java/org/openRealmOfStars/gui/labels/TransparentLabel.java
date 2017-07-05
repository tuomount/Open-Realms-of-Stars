package org.openRealmOfStars.gui.labels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.EtchedBorder;

import org.openRealmOfStars.gui.GuiStatics;

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
 * Handling label which draws text which is on transparent label.
 *
 */
public class TransparentLabel extends JLabel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Parent component
   */
  private Component parent;

  /**
   * Add border
   */
  private boolean border;
  /**
   * Wrap text from white spaces
   */
  private boolean wrap;

  /**
   * Create label with transparency without borders or auto wrap.
   * @param parent Parent component
   * @param text Text to show
   */
  public TransparentLabel(final Component parent, final String text) {
    this.parent = parent;
    this.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setFont(GuiStatics.getFontCubellan());
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), text)
        + 10;
    size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(), text);
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }

  /**
   * Create label with transparency
   * @param parent Parent component
   * @param text Text to show
   * @param border Add border
   * @param autoWrap Wrap words from space
   */
  public TransparentLabel(final Component parent, final String text,
      final boolean border, final boolean autoWrap) {
    super(text);
    this.parent = parent;
    this.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setFont(GuiStatics.getFontCubellan());
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), text)
        + 10;
    size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(), text);
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
    this.border = border;
    this.wrap = autoWrap;
  }

  @Override
  public JToolTip createToolTip() {
    JToolTip toolTip = super.createToolTip();
    toolTip.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    toolTip.setBorder(BorderFactory
        .createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER));
    return toolTip;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    parent.repaint();
    int x = 0;
    int y = 0;
    if (border) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }
    g.setFont(this.getFont());
    g.setColor(this.getForeground());
    if (wrap) {
      String[] texts = this.getText().split(" ");
      for (int i = 0; i < texts.length; i++) {
        int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
            texts[i]);
        int textHeight = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
            texts[i]);
        g.drawString(texts[i], x + 5, y + 10 + i * textHeight);
      }
    } else {
      g.drawString(this.getText(), x + 5, y + 10);
    }
  }

}
