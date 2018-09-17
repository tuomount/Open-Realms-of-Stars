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
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018 Tuomo Untinen
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
* Race image panel of player setup
*
*/
public class ShipInteriorPanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Space race inside the ship
   */
  private SpaceRace race;

  /**
   * Optional planet at background
   */
  private BufferedImage planetImage;

  /**
   * Ship Interior panel. Draws space ship interior with optional
   * planet in background and space race in front.
   * @param race Space to draw
   * @param planetBackground Optional planet at background
   */
  public ShipInteriorPanel(final SpaceRace race,
      final BufferedImage planetBackground) {
    super();
    this.race = race;
    this.planetImage = planetBackground;
    Dimension size = new Dimension(300, 400);
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setBorder(new SimpleBorder());
  }

  /**
   * Get the space race at image
   * @return SpaceRace
   */
  public SpaceRace getRace() {
    return race;
  }

  /**
   * Get the planet background image
   * @return Image of planet or null
   */
  public BufferedImage getPlanetImage() {
    return planetImage;
  }

  @Override
  protected void paintComponent(final Graphics g) {
    GradientPaint gradient = new GradientPaint(this.getWidth() / 2, 0,
        Color.BLACK, this.getWidth() / 2, this.getHeight(),
        GuiStatics.COLOR_GREY_40, true);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(gradient);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.drawImage(GuiStatics.STAR_FIELD_IMAGE, 0,0, null);
    if (planetImage != null) {
      g.drawImage(planetImage,
          this.getWidth() / 2 - planetImage.getWidth() / 2,
          0, null);
    }
    g.drawImage(GuiStatics.IMAGE_INTERIOR1,
        this.getWidth() / 2 - GuiStatics.IMAGE_INTERIOR1.getWidth() / 2,
        this.getHeight() / 2 - GuiStatics.IMAGE_INTERIOR1.getHeight() / 2,
        null);
    if (race == SpaceRace.HUMAN) {
      g.drawImage(race.getRaceImage(),
          this.getWidth() / 2 - race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - GuiStatics.IMAGE_INTERIOR1_CONSOLE.getHeight() / 2, null);
      g.drawImage(GuiStatics.IMAGE_INTERIOR1_CONSOLE,
          this.getWidth() / 2 - GuiStatics.IMAGE_INTERIOR1_CONSOLE.getWidth()
          / 2,
          this.getHeight() - GuiStatics.IMAGE_INTERIOR1_CONSOLE.getHeight() - 5,
          null);
    } else {
      g.drawImage(race.getRaceImage(),
          this.getWidth() / 2 - race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - GuiStatics.IMAGE_INTERIOR1_CONSOLE.getHeight(), null);
    }
  }
}
