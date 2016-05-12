package org.openRealmOfStars.gui.infopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.ScifiBorder;


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
 * Abstract Class for handling info on next to the star map
 * 
 */
public abstract class InfoPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  
  public InfoPanel() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(Box.createRigidArea(new Dimension(130,50)));
    ScifiBorder border = new ScifiBorder("Galactic Info");
    this.setBorder(border);
  }


  @Override
  protected void paintComponent(Graphics arg0) {
    Graphics2D g2d = (Graphics2D)arg0;
    g2d.setColor(GuiStatics.COLOR_SPACE_GREY_BLUE);
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    
  }
  
  
}
