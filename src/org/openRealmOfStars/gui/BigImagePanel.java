package org.openRealmOfStars.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.openRealmOfStars.starMap.planet.Planet;

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
  
  /**
   * Planet to draw
   */
  private Planet planet;
  
  /**
   * Title text
   */
  private String title;
  
  /**
   * Create BigImagePanel
   * @param planet Planet to draw on background
   * @param starField Use starfield or not
   * @param title if this is not null then planet info is not shown.
   */
  public BigImagePanel(Planet planet, boolean starField,String title) {
    super();
    this.setBackground(Color.black);
    this.planet = planet;
    backgroundImg = Planet.PLANET_BIG_IMAGES[this.planet.getPlanetType()];
    drawStarField = starField;
    this.title = title;
  }
  
  /**
   * Draw bold text with current font
   * @param g Graphics to draw
   * @param border Text border color
   * @param center Text center color
   * @param x X-coordinate
   * @param y Y-coordinate
   * @param text Text to draw
   */
  private void drawBoldText(Graphics g,Color border, Color center, int x, 
      int y, String text) {
    g.setColor(border);
    g.drawString(text, x+1, y);
    g.drawString(text, x-1, y);
    g.drawString(text, x, y+1);
    g.drawString(text, x, y-1);
    g.setColor(center);
    g.drawString(text, x, y);

  }

  private void drawTextArea(Graphics g) {
    g.setFont(GuiStatics.getFontCubellan());
    if (title == null) {
      StringBuilder sb = new StringBuilder(planet.generateInfoText());
      int lastSpace = -1;
      int rowLen = 0;
      int maxRowLen = this.getWidth()/12;
      for (int i=0;i<sb.length();i++) {
        if (sb.charAt(i)==' ') {
          lastSpace = i;
        }
        if (sb.charAt(i)=='\n') {
          lastSpace = -1;
          rowLen=0;
        } else {
          rowLen++;
        }
        if (rowLen > maxRowLen) {
          if (lastSpace != -1) {
            sb.setCharAt(lastSpace, '\n');
            rowLen=i-lastSpace;
            lastSpace = -1;
          } else {
            sb.setCharAt(i, '\n');
            lastSpace = -1;
            rowLen=0;
          }
        }
      }
      String[] texts = sb.toString().split("\n");
      int offsetX = (575-backgroundImg.getWidth())/2-
          GuiStatics.getTextWidth(GuiStatics.getFontCubellanBold(), texts[0])/2+
          backgroundImg.getWidth()/2;
      int offsetY = (575-backgroundImg.getHeight())/2;
      g.setFont(GuiStatics.getFontCubellanBold());
      drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
          GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, offsetX, offsetY, texts[0]);
      if (planet.getPlanetPlayerInfo() != null) {
        offsetX = (575-backgroundImg.getWidth())/2-
            GuiStatics.getTextWidth(GuiStatics.getFontCubellanBold(), 
                planet.getPlanetPlayerInfo().getEmpireName())/2+
            backgroundImg.getWidth()/2;
        drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
          GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, offsetX, offsetY+25, planet.getPlanetPlayerInfo().getEmpireName());
      }
      g.setFont(GuiStatics.getFontCubellan());
      for (int i=1;i<texts.length;i++) {
        drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
          GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, 25, this.getHeight()/2+i*15, texts[i]);
      }
    } else {
      g.setFont(GuiStatics.getFontCubellanBoldBig());
      int offsetX = (this.getWidth()-backgroundImg.getWidth())/2-
          GuiStatics.getTextWidth(GuiStatics.getFontCubellanBoldBig(), title)/2+
          backgroundImg.getWidth()/2;
      int offsetY = (575-backgroundImg.getHeight())/2;
      drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK,
          GuiStatics.COLOR_COOL_SPACE_BLUE, offsetX, offsetY, title);
      
    }

  }
  
  @Override
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int sx = planet.getName().length()*(planet.getRadiationLevel()-1)*3;
    int sy = planet.getName().length()*(planet.getGroundSize()-7)*3;
    if (drawStarField) {
      g2d.drawImage(GuiStatics.starFieldImage,-sx,-sy,null);
    } else {
      this.setBackground(Color.black);
      g2d.fillRect(0,0, this.getWidth(), this.getHeight());
    }
    if (title == null) {
      int offsetX = (575-backgroundImg.getWidth())/2;
      int offsetY = (575-backgroundImg.getHeight())/2;
      g2d.drawImage(backgroundImg,offsetX,offsetY,null);
    } else {
      g2d.drawImage(GuiStatics.nebulaeImage,-sx,-sy,null);
      int offsetX = (this.getWidth()-backgroundImg.getWidth())/2;
      int offsetY = (575-backgroundImg.getHeight())/2;
      g2d.drawImage(backgroundImg,offsetX,offsetY,null);      
    }
    
    drawTextArea(g);
    this.paintChildren(g2d);
  }
  
  

}
