package org.openRealmOfStars.gui.labels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.icons.Icon;
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
 * Handling Icon label which draws Icon and text to next it.
 * Icon and text are opaque so parent is required.
 * 
 */
public class IconLabel extends JLabel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Parent component
   */
  private Component parent;
  
  /**
   * Icon to draw
   */
  private Icon icon;

  /**
   * Get left side icon
   * @return Icon
   */
  public Icon getLeftIcon() {
    return icon;
  }

  /**
   * Set icon on left side of Label
   * @param icon 
   */
  public void setLeftIcon(Icon icon) {
    this.icon = icon;
  }

  /**
   * Create Icon label with opaque
   * @param parent Parent component
   * @param icon Icon to draw
   * @param text Text to show
   */
  public IconLabel(Component parent, Icon icon, String text) {
    super(text);
    this.parent = parent;
    this.icon = icon;
    this.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    this.setFont(GuiStatics.getFontCubellan());
    Dimension size = this.getPreferredSize();
    size.width = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(), text)+this.icon.getIcon().getWidth()+10;
    size.height = GuiStatics.getTextHeight(GuiStatics.getFontCubellan(), text);
    if (size.height < this.icon.getIcon().getHeight()+2 ) {
      size.height = this.icon.getIcon().getHeight()+2;
    }
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setMaximumSize(size);
  }
  
  @Override
  public JToolTip createToolTip()
  {
      JToolTip toolTip = super.createToolTip();
      toolTip.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
      toolTip.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_OPAQUE);
      toolTip.setBorder(BorderFactory.createLineBorder(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_OPAQUE));
      return toolTip;
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    parent.repaint();
    int x = 0;
    int y = 0;
    g.drawImage(icon.getIcon(), x,y, null);
    g.setFont(this.getFont());
    g.setColor(this.getForeground());
    g.drawString(this.getText(),x+icon.getIcon().getWidth()+5, y+10);
  }

}
