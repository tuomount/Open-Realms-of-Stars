package org.openRealmOfStars.gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.border.EtchedBorder;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.utilies.GuiStatics;

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
 * Class for creating cool looking space button with a text
 *
 */
public class SpaceButton extends JButton {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Space button icon
   */
  private Icon16x16 icon;

  /**
   * Space Button with blue background and golden text
   * @param text Text shown in button
   * @param actionCommand Action command as String
   */
  public SpaceButton(final String text, final String actionCommand) {
    super(text);
    this.setActionCommand(actionCommand);
    this.setBackground(GuiStatics.getCoolSpaceColor());
    this.setForeground(GuiStatics.COLOR_GOLD);
    this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    String[] texts = text.split("\n");
    int longest = 0;
    for (int i = 0; i < texts.length; i++) {
      if (texts[i].length() > texts[longest].length()) {
        longest = i;
      }
    }
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
        texts[longest]) + 20;
    size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
        texts[longest]) * texts.length + 10;
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
    icon = null;
  }

  @Override
  public JToolTip createToolTip() {
    JToolTip toolTip = super.createToolTip();
    toolTip.setForeground(GuiStatics.getCoolSpaceColor());
    toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    toolTip.setBorder(BorderFactory
        .createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER));
    return toolTip;
  }

  /**
   * Set space Icon, Null to disable
   * @param spaceButtonIcon Icon16x16 icon
   */
  public void setSpaceIcon(final Icon16x16 spaceButtonIcon) {
    this.icon = spaceButtonIcon;
    Dimension size = this.getPreferredSize();
    if (this.icon != null) {
      size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          getText()) + 20 + 16;
      size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
          getText()) + 10;
    } else {
      size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          getText()) + 20;
      size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
          getText()) + 10;
    }
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }

  /**
   * Get space icon, can be null
   * @return Icon16x16 or null
   */
  public Icon16x16 getSpaceIcon() {
    return icon;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    GradientPaint gradient = new GradientPaint(0, 0,
        GuiStatics.getCoolSpaceColor(), this.getWidth(), this.getHeight(),
        GuiStatics.COLOR_COOL_SPACE_BLUE_DARK, true);

    if (this.getModel().isPressed()) {
      gradient = new GradientPaint(0, 0, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK,
          this.getWidth(), this.getHeight(), GuiStatics.COLOR_DEEP_SPACE_BLUE,
          true);
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

    } else {
      if (!this.isEnabled()) {
        gradient = new GradientPaint(0, 0, GuiStatics.COLOR_GREY_160,
            this.getWidth(), this.getHeight(), GuiStatics.COLOR_GREY_80, true);
      }
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }
    Insets inset = this.getBorder().getBorderInsets(this);
    int sx = inset.left;
    int sy = inset.top;
    int width = getWidth() - inset.left - inset.right;
    int height = getHeight() - inset.top - inset.bottom;
    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(gradient);
    g2d.fillRect(sx, sy, width, height);

    if (this.isEnabled()) {
      if (this.getModel().isRollover()) {
        g2d.setColor(GuiStatics.COLOR_DEEP_SPACE_BLUE);
      } else {
        g2d.setColor(Color.black);
      }
    } else {
      g2d.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER);
    }
    String[] texts = getText().split("\n");
    g.setFont(GuiStatics.getFontCubellan());
    int longest = 0;
    for (int i = 0; i < texts.length; i++) {
      if (texts[i].length() > texts[longest].length()) {
        longest = i;
      }
    }
    int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
        texts[longest]);
    int textHeight = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(),
        texts[longest]);
    int offsetX = sx;
    if (icon != null) {
      if (textWidth + icon.getIcon().getWidth() + sx + 6 < getWidth()) {
        sx = sx + 6;
        offsetX = offsetX + 4;
      }
      int offsetY = getHeight() / 2 - icon.getIcon().getHeight() / 2;
      icon.draw(g2d, offsetX, offsetY);
    }
    for (int i = 0; i < texts.length; i++) {
      textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          texts[i]);
      offsetX = width / 2 - textWidth / 2 + sx;
      int offsetY = textHeight * (i + 1) + sy;
      if (texts.length == 1) {
        int baseline = (int) g2d.getFont().getLineMetrics(texts[i],
            g2d.getFontRenderContext()).getBaselineOffsets()
            [Font.CENTER_BASELINE];
        baseline = baseline - 5;
        offsetY = getHeight() / 2 - textHeight / 2 - baseline;
      }
      g2d.drawString(texts[i], offsetX, offsetY);
    }
  }

}
