package org.openRealmOfStars.gui.labels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

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
   * Create label with transparency
   * @param parent Parent component
   * @param text Text to show
   */
  public TransparentLabel(Component parent, String text) {
    super(text);
    this.parent = parent;
    this.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setFont(GuiStatics.getFontCubellan());
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), text)+10;
    size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(), text);
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }
  
  @Override
  public JToolTip createToolTip()
  {
      JToolTip toolTip = super.createToolTip();
      toolTip.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
      toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS);
      toolTip.setBorder(BorderFactory.createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS));
      return toolTip;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    parent.repaint();
    int x = 0;
    int y = 0;
    g.setFont(this.getFont());
    g.setColor(this.getForeground());
    g.drawString(this.getText(),x+5, y+10);
  }

}
