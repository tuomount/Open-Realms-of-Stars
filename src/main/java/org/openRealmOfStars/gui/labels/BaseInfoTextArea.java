package org.openRealmOfStars.gui.labels;

import java.awt.Color;

import javax.swing.JTextArea;

import org.openRealmOfStars.gui.borders.SimpleBorder;
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
 * Class for handling text without repainting content
 *
 */
public class BaseInfoTextArea extends JTextArea {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Construct BaseInfoTextArea with default but set size on rows and
   * columns.
   * @param rows Number of rows
   * @param columns Number of columns
   */
  public BaseInfoTextArea(final int rows, final int columns) {
    super(rows, columns);
    this.setFont(GuiStatics.getFontCubellanSmaller());
    this.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    this.setBackground(Color.BLACK);
    this.setBorder(new SimpleBorder());
    this.setSelectedTextColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setSelectionColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
  }

  /**
   * Construct BaseInfoTextArea with defaults.
   */
  public BaseInfoTextArea() {
    super(17, 10);
    this.setFont(GuiStatics.getFontCubellanSmaller());
    this.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    this.setBackground(Color.BLACK);
    this.setBorder(new SimpleBorder());
    this.setSelectedTextColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setSelectionColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
  }

  /**
   * Construct BaseInfoTextArea with certain text.
   * @param text The text to be displayed, or null
   */
  public BaseInfoTextArea(final String text) {
    super(text);
    this.setFont(GuiStatics.getFontCubellanSmaller());
    this.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    this.setBackground(Color.BLACK);
    this.setBorder(new SimpleBorder());
    this.setSelectedTextColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setSelectionColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
  }

}
