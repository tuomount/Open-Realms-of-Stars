package org.openRealmOfStars.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

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
 * Static GUI component like fonts and colors.
 * 
 */
public class GuiStatics {
  /**
   *  Monospace font size 10
   */
  public final static Font FONT_SMALL = new Font("monospaced",Font.PLAIN,10);
  /**
   *  Monospace font size 12
   */
  public final static Font FONT_NORMAL = new Font("monospaced",Font.BOLD,12);

  /**
   * Line type for text background
   */
  public final static Stroke TEXT_LINE = new BasicStroke(12, 
      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[]{1f}, 0);

  /**
   * Sun Line type for text background, opacity 230.
   */
  public final static Color COLOR_GOLD_OPAQUE = new Color(210, 181, 44, 230);

  /**
   * Gold color non opaque
   */
  public final static Color COLOR_GOLD = new Color(210, 181, 44);

  /**
   * Dark Gold color non opaque
   */
  public final static Color COLOR_GOLD_DARK = new Color(155, 130, 13);

  /**
   * Planet Line type for text background, opacity 65.
   */
  public final static Color COLOR_GREYBLUE = new Color(180, 180, 200, 65);

  /**
   * Panel background
   */
  public final static Color COLOR_SPACE_GREY_BLUE = new Color(81, 87, 133,255);

  /**
   * Green text dark one
   */
  public final static Color COLOR_GREEN_TEXT_DARK = new Color(2, 102, 0,255);

  /**
   * Green text 
   */
  public final static Color COLOR_GREEN_TEXT = new Color(4, 186, 0,255);

  /**
   * Cool space blue
   */
  public final static Color COLOR_COOL_SPACE_BLUE = new Color(88,210,255);

  /**
   * Cool space blue dark
   */
  public final static Color COLOR_COOL_SPACE_BLUE_DARK = new Color(25,120,193);

  /**
   * Deep Space Blue
   */
  public final static Color COLOR_DEEP_SPACE_BLUE = new Color(33,33,208);

  /**
   * Grey 160
   */
  public final static Color COLOR_GREY_160 = new Color(160,160,160);

  /**
   * Grey 80
   */
  public final static Color COLOR_GREY_80 = new Color(80,80,80);

  /**
   * Image used for calculation text width and heights.
   */
  private static final BufferedImage tempImage = new BufferedImage(100,100,BufferedImage.TYPE_4BYTE_ABGR);

  /**
   * Get text width for certain font
   * @param font Font to use
   * @param text String
   * @return Text width in pixels
   */
  public static int getTextWidth(Font font, String text) {
    if (font != null && text != null) {
      Graphics2D g2d = tempImage.createGraphics();
      int textWidth = (int) font.getStringBounds(text,
        g2d.getFontRenderContext()).getWidth();
      return textWidth;
    }
    return 0;
  }

  /**
   * Get text height for certain font
   * @param font Font to use
   * @param text String
   * @return Text height in pixels
   */
  public static int getTextHeight(Font font, String text) {
    if (font != null && text != null) {
      Graphics2D g2d = tempImage.createGraphics();
      int textHeight = (int) font.getStringBounds(text,
        g2d.getFontRenderContext()).getHeight();
      return textHeight;
    }
    return 0;
  }

}
