package org.openRealmOfStars.gui.mapPanel;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.utilies.GuiStatics;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Class for handling popups on map panel
*
*/
public class PopupPanel {

  /**
   * Text to display on panel
   */
  private String text;

  /**
   * Image to shop on panel
   */
  private BufferedImage image;

  /**
   * Constructor for PopupPanel
   * @param text Text to show
   */
  public PopupPanel(final String text) {
    this.text = text;
    image = null;
  }

  /**
   * Change popup panel text.
   * @param text where to change.
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Change popup panel text.
   * @return Text of panel
   */
  public String getText() {
    return this.text;
  }

  /**
   * Chnage popup panel image
   * @param image Where to change
   */
  public void setImage(final BufferedImage image) {
    this.image = image;
  }

  /**
   * Get popup panel image. This can be null.
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return this.image;
  }

  /**
   * Draw popup with text and possible image
   * @param screen Screen where to draw pop up
   */
  public void drawPopup(final BufferedImage screen) {
    Graphics2D gr = screen.createGraphics();
    int x = screen.getWidth() / 10;
    int y = screen.getHeight() / 10;
    int width = screen.getWidth() - (screen.getWidth() / 5);
    int height = screen.getHeight() - (screen.getHeight() / 5);
    gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS);
    gr.fillRect(x, y, width, height);
    GradientPaint gradient = new GradientPaint(x, y,
        GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER, x + width, y + width,
        GuiStatics.COLOR_COOL_SPACE_BLUE, false);
    gr.setPaint(gradient);
    int borderSize = 3;
    gr.fillRect(x, y, width, borderSize);
    gr.fillRect(x, y + borderSize, borderSize, height - borderSize);
    gr.fillRect(x + borderSize, y + height - borderSize, width - borderSize,
        borderSize);
    gr.fillRect(x + width - borderSize, y + borderSize, borderSize,
        height - borderSize);

  }

}
