package org.openRealmOfStars.gui.labels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

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
 * Class for handling different images, for example planets, gas giants.
 *
 */
public class ImageLabel extends JLabel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Image to draw
   */
  private BufferedImage image;

  /**
   * Is there a border
   */
  private boolean border;

  /**
   * Fill color behind the image
   */
  private Color fillColor;

  /**
   * Center image of component
   */
  private boolean center;

  /**
   * Construct an ImageLabel.
   * @param image Image to show
   * @param border Boolean is there a border or not
   */
  public ImageLabel(final BufferedImage image, final boolean border) {
    super();
    ImageIcon icon = new ImageIcon(image, "");
    this.setIcon(icon);
    this.setImage(image);
    this.setBorder(border);
    this.setFillColor(null);
    if (isBorder()) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    } else {
      this.setBorder(BorderFactory.createEmptyBorder());

    }

  }

  /**
   * Set Image for label
   * @param image BufferedImage
   */
  public void setImage(final BufferedImage image) {
    this.image = image;
  }

  /**
   * Get image from label
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return image;
  }

  /**
   * Set if border is visible for label.
   * @param border True to show border
   */
  public void setBorder(final boolean border) {
    this.border = border;
  }

  /**
   * Is border visible
   * @return True if border is visible
   */
  public boolean isBorder() {
    return border;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    if (getFillColor() != null) {
      g.setColor(getFillColor());
      g.fillRect(0, 0, getImage().getWidth(), getImage().getHeight());
    }
    if (isBorder()) {
      this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
      if (getImage() != null) {
        int x = 0;
        int y = 0;
        if (isCenter()) {
          x = this.getWidth() / 2 - getImage().getWidth() / 2;
          y = this.getHeight() / 2 - getImage().getHeight() / 2;
        }
        g.drawImage(getImage(), x + 2, y + 2, null);
      }
    } else if (getImage() != null) {
      int x = 0;
      int y = 0;
      if (isCenter()) {
        x = this.getWidth() / 2 - getImage().getWidth() / 2;
        y = this.getHeight() / 2 - getImage().getHeight() / 2;
      }
      g.drawImage(getImage(), x, y, null);
    }
  }

  /**
   * Get Image label background color
   * @return Background fill color
   */
  public Color getFillColor() {
    return fillColor;
  }

  /**
   * Set background color for filling the label
   * @param fillColor Background color
   */
  public void setFillColor(final Color fillColor) {
    this.fillColor = fillColor;
  }

  /**
   * Is image centered
   * @return true if image is centered
   */
  public boolean isCenter() {
    return center;
  }

  /**
   * Set centered value
   * @param center the center to set
   */
  public void setCenter(final boolean center) {
    this.center = center;
  }

}
