package org.openRealmOfStars.mapTiles.anomaly;

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
* Space anomaly
*
*/
public class SpaceAnomaly {
  /**
   * Space anomaly type
   */
  private AnomalyType type;
  /**
   * Anomaly value for example credits
   */
  private int value;

  /**
   * Textual description what found or happened
   */
  private String text;

  /**
   * Image of Space anomaly
   */
  private BufferedImage image;

  /**
   * Constructor for Space anomaly
   * @param type AnomalyType
   * @param value Space anomaly value
   */
  public SpaceAnomaly(final AnomalyType type, final int value) {
    this.type = type;
    this.value = value;
    this.text = null;
    this.image = null;
  }

  /**
   * Get Space anomaly type
   * @return AnomalyType
   */
  public AnomalyType getType() {
    return type;
  }

  /**
   * Get Space anomaly value
   * @return value
   */
  public int getValue() {
    return value;
  }

  /**
   * Get textual description of space anomaly.
   * @return Textual description
   */
  public String getText() {
    return text;
  }

  /**
   * Set textual description of space anomaly
   * @param text To set
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Set image of space anomaly
   * @param image to set
   */
  public void setImage(final BufferedImage image) {
    this.image = image;
  }

  /**
   * Get image of space anomaly
   * @return BufferedImage
   */
  public BufferedImage getImage() {
    return image;
  }
}
