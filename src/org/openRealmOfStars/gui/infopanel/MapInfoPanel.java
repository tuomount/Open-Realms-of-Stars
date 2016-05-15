package org.openRealmOfStars.gui.infopanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.Planet;

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
 * Handling info on next to the star map
 * 
 */

public class MapInfoPanel extends InfoPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Image Label for image
   */
  private ImageLabel imageLabel;
  
  /**
   * Text area containing info
   */
  private InfoTextArea textArea;
  
  /**
   * Show info about the planet
   */
  private Planet planet;
  
  public MapInfoPanel() {
    BufferedImage img = new BufferedImage(Tile.MAX_WIDTH*2, Tile.MAX_HEIGHT*2,
        BufferedImage.TYPE_4BYTE_ABGR);
    imageLabel = new ImageLabel(img, true);
    Graphics2D g2d = img.createGraphics();
    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    this.add(imageLabel);
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    this.add(textArea);
  }
  
  /**
   * Show planet on info panel
   * @param planet
   */
  public void showPlanet(Planet planet) {
    this.planet = planet;
    updatePanel();
  }
  
  /**
   * Show empty planet
   */
  public void showEmpty() {
    this.planet = null;
    updatePanel();
  }
  
  /**
   * Update panels according set data
   */
  public void updatePanel() {
    if (planet != null) {
      BufferedImage img = imageLabel.getImage();
      Tile tile = Tiles.getTileByIndex(planet.getPlanetImageIndex());
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      tile.draw(g2d, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
      imageLabel.setImage(img);
      setTitle(planet.getName());
      this.add(imageLabel);
      this.repaint();
    } else {
      setTitle("Galactic info");
      BufferedImage img = imageLabel.getImage();
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(GuiStatics.COLOR_SPACE_GREY_BLUE);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      imageLabel.setImage(img);
      this.remove(imageLabel);
      this.repaint();
    }
  }
}
