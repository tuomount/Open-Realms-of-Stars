package org.openRealmOfStars.gui.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
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
  
  public MapInfoPanel(ActionListener listener) {
    BufferedImage img = new BufferedImage(Tile.MAX_WIDTH*2, Tile.MAX_HEIGHT*2,
        BufferedImage.TYPE_4BYTE_ABGR);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    imageLabel = new ImageLabel(img, true);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    Graphics2D g2d = img.createGraphics();
    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    this.add(imageLabel);
    this.add(Box.createRigidArea(new Dimension(10,10)));
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    SpaceButton btn = new SpaceButton("View planet", 
        GameCommands.COMMAND_VIEW_PLANET);
    btn.addActionListener(listener);
    this.add(textArea);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(Box.createRigidArea(new Dimension(10,10)));
    this.add(btn);
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
      if (!planet.isGasGiant()) {
        tile.draw(g2d, Tile.MAX_WIDTH/2, Tile.MAX_HEIGHT/2);
      } else {
        switch (planet.getPlanetImageIndex()) {
        default:
        case 0: {
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW);
          tile.draw(g2d, 0, 0);
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE);
          tile.draw(g2d, Tile.MAX_WIDTH, 0);
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW);
          tile.draw(g2d, 0,  Tile.MAX_HEIGHT);
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE);
          tile.draw(g2d, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);

          break;
        }
        case 1: {
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_2_NW);
          tile.draw(g2d, 0, 0);
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_2_NE);
          tile.draw(g2d, Tile.MAX_WIDTH, 0);
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_2_SW);
          tile.draw(g2d, 0,  Tile.MAX_HEIGHT);
          tile = Tiles.getTileByName(TileNames.GAS_GIANT_2_SE);
          tile.draw(g2d, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
          break;
        }
        }
      }
      imageLabel.setImage(img);
      setTitle(planet.getName());
      textArea.setText(planet.generateInfoText());
      this.repaint();
    } else {
      setTitle("Galactic info");
      BufferedImage img = imageLabel.getImage();
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      imageLabel.setImage(img);
      textArea.setText("");
      this.repaint();
    }
  }
}
