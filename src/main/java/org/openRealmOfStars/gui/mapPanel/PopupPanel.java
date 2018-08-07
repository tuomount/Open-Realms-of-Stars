package org.openRealmOfStars.gui.mapPanel;

import java.awt.image.BufferedImage;

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

}
