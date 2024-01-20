package org.openRealmOfStars.gui.graphs;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
 */

import java.awt.image.BufferedImage;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 * Bridge Graphics containing actual buffered image for bridge and
 * Y offset where to place space race image.
 *
 */
public class BridgeGraph {

  /** Bridge image attached to bridge graphics. */
  private BufferedImage bridgeImage;

  /** Y-offset for captain standing on the bridge */
  private int yOffset;

  /** Bridge name based on space race name. */
  private String name;

  /**
   * Constructor for Bridge Graph
   * @param name Bridge name
   * @param imagePath Path to image
   * @param yOffset Y Coordinate offset for captain.
   */
  public BridgeGraph(final String name, final String imagePath,
      final int yOffset) {
    this.name = name;
    bridgeImage = IOUtilities.loadImage(imagePath);
    this.yOffset = yOffset;
  }
  /**
   * Get Bridge Image as Buffered Image
   * @return BufferedImage
   */
  public BufferedImage getBridgeImage() {
    return bridgeImage;
  }

  /**
   * Get captain Y-offset on bridge. Captain is always in middle of bridge, but
   * Y coordinate varies a bit.
   * @return Y-Offset
   */
  public int getyOffset() {
    return yOffset;
  }
  /**
   * Get the name of the bridge.
   * @return the name
   */
  public String getName() {
    return name;
  }
}