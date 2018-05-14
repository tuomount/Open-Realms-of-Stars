package org.openRealmOfStars.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017  Tuomo Untinen
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
 * Image panel of any generic image
 *
 */

public class ImagePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * picture to draw
   */
  private BufferedImage image;

  /**
   * Text to show in picture
   */
  private String text;

  /**
   * Create picture frame from image
   * @param picture to show
   */
  public ImagePanel(final BufferedImage picture) {
    super();
    image = picture;
    text = null;
    Dimension size = new Dimension(image.getWidth(), image.getHeight());
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setBorder(new SimpleBorder());
  }

  /**
   * Create picture frame from frame size
   * @param width frame width
   * @param height frame height
   */
  public ImagePanel(final int width, final int height) {
    super();
    image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    text = null;
    Dimension size = new Dimension(image.getWidth(), image.getHeight());
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setBorder(new SimpleBorder());
  }

  /**
   * Change image in frame
   * @param picture to show
   */
  public void setImage(final BufferedImage picture) {
    image = picture;
  }

  /**
   * Text to show instead of picture. Set to null to show picture instead.
   * @param textToShow In picture frame
   */
  public void setText(final String textToShow) {
    text = textToShow;
  }

  /**
   * Get Text to set to show. If null then image is shown.
   * @return Text or null
   */
  public String getText() {
    return text;
  }
  /**
   * Get Image from frame
   * @return Image from frame
   */
  public BufferedImage getImage() {
    return image;
  }
  @Override
  protected void paintComponent(final Graphics g) {
    GradientPaint gradient = new GradientPaint(this.getWidth() / 2, 0,
        Color.BLACK, this.getWidth() / 2, this.getHeight(),
        GuiStatics.COLOR_GREY_40, true);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(gradient);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    if (text != null) {
      g2d.setFont(GuiStatics.getFontCubellan());
      g2d.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
      int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          text);
      int offsetX = this.getWidth() / 2 - textWidth / 2;
      g2d.drawString(text, offsetX, this.getHeight() / 2);

    } else {
      g.drawImage(image, this.getWidth() / 2 - image.getWidth() / 2,
          this.getHeight() - image.getHeight(), null);
    }
  }
}
