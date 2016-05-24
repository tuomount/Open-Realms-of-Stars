package org.openRealmOfStars.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

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
 * Big image panel for drawing planet view/star dock view
 * 
 */
public class BigImagePanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  
  /**
   * Background image to draw
   */
  private BufferedImage backgroundImg;
  
  /**
   * Draw star field
   */
  private boolean drawStarField;
  
  public BigImagePanel(BufferedImage background, boolean starField) {
    super();
    this.setBackground(Color.black);
    backgroundImg = background;
    drawStarField = starField;
    
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    if (drawStarField) {
      g2d.drawImage(GuiStatics.starFieldImage,0,0,null);
    } else {
      this.setBackground(Color.black);
      g2d.fillRect(0,0, this.getWidth(), this.getHeight());
    }
    int offsetX = (575-backgroundImg.getWidth())/2;
    int offsetY = (575-backgroundImg.getHeight())/2;
    g2d.drawImage(backgroundImg,offsetX,offsetY,null);
  }
  
  

}
