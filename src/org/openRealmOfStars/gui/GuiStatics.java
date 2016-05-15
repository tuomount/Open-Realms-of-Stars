package org.openRealmOfStars.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

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
  public final static Color COLOR_GOLD = new Color(210, 181, 44, 230);


  /**
   * Planet Line type for text background, opacity 65.
   */
  public final static Color COLOR_GREYBLUE = new Color(180, 180, 200, 65);

  /**
   * Panel background
   */
  public final static Color COLOR_SPACE_GREY_BLUE = new Color(81, 87, 133,255);

}
