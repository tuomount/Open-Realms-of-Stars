package org.openRealmOfStars.gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;

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
   * Count for star field
   */
  private int x;

  /**
   * Speed
   */
  private int speed;
  /**
   * Private planet/nebula offset X
   */
  private int offsetX = 0;
  /**
   * Private planet/nebula offset Y
   */
  private int offsetY = 0;

  /**
   * Line where video glitch goes
   */
  private int glitchLine = 0;

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
    x = -10;
    speed = -1;
    if (planetImage == null && DiceGenerator.getRandom(100) < 40) {
      planetImage = GuiStatics.NEBULAE_IMAGE;
    }
    if (planetImage == GuiStatics.NEBULAE_IMAGE) {
      offsetX = -DiceGenerator.getRandom(90);
      offsetY = -DiceGenerator.getRandom(90);
    }
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

  /**
   * Get captain adjustment for bridge image
   * @param spaceRace Captain's space race
   * @return Adjustment
   */
  public static int getAdjustment(final SpaceRace spaceRace) {
    int yOffset = 57;
    if (spaceRace == SpaceRace.CENTAURS) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.SCAURIANS) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.MECHIONS) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.HUMAN) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.MOTHOIDS) {
      yOffset = 35;
    }
    if (spaceRace == SpaceRace.GREYANS) {
      yOffset = 30;
    }
    if (spaceRace == SpaceRace.HOMARIANS) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.TEUTHIDAES) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.SPORKS) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.CHIRALOIDS) {
      yOffset = 15;
    }
    if (spaceRace == SpaceRace.SPACE_PIRATE) {
      yOffset = 15;
    }
    if (spaceRace == SpaceRace.LITHORIANS) {
      yOffset = 15;
    }
    if (spaceRace == SpaceRace.REBORGIANS) {
      yOffset = 25;
    }
    if (spaceRace == SpaceRace.SMAUGIRIANS) {
      yOffset = 20;
    }
    if (spaceRace == SpaceRace.ALTEIRIANS) {
      yOffset = 25;
    }
    if (spaceRace == SpaceRace.ALONIANS) {
      yOffset = 25;
    }
    return yOffset;
  }
  @Override
  protected void paintComponent(final Graphics g) {
    GradientPaint gradient = new GradientPaint(this.getWidth() / 2, 0,
        Color.BLACK, this.getWidth() / 2, this.getHeight(),
        GuiStatics.COLOR_GREY_40, true);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(gradient);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    x = x + speed;
    if (x <= -100) {
      speed = 1;
    }
    if (x >= -2) {
      speed = -1;
    }
    int starX = x;
    if (planetImage != null) {
      starX = x / 2;
    }
    g.drawImage(GuiStatics.getStarField(), starX, 0, null);
    if (planetImage != null) {
      g.drawImage(planetImage,
          this.getWidth() / 2 - planetImage.getWidth() / 2 + 50 + x
          + offsetX,
          offsetY, null);
    }
    BufferedImage interior = GuiStatics.IMAGE_INTERIOR1;
    int yOffset = getAdjustment(race);
    if (race == SpaceRace.CENTAURS) {
      interior = GuiStatics.IMAGE_INTERIOR_CENTAUR;
    }
    if (race == SpaceRace.SCAURIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_SCAURIAN;
    }
    if (race == SpaceRace.MECHIONS) {
      interior = GuiStatics.IMAGE_INTERIOR_MECHION;
    }
    if (race == SpaceRace.HUMAN) {
      interior = GuiStatics.IMAGE_INTERIOR_HUMAN;
    }
    if (race == SpaceRace.MOTHOIDS) {
      interior = GuiStatics.IMAGE_INTERIOR_MOTHOID;
    }
    if (race == SpaceRace.GREYANS) {
      interior = GuiStatics.IMAGE_INTERIOR_GREYAN;
    }
    if (race == SpaceRace.HOMARIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_HOMARIAN;
    }
    if (race == SpaceRace.TEUTHIDAES) {
      interior = GuiStatics.IMAGE_INTERIOR_TEUTHIDAE;
    }
    if (race == SpaceRace.SPORKS) {
      interior = GuiStatics.IMAGE_INTERIOR_SPORK;
    }
    if (race == SpaceRace.CHIRALOIDS) {
      interior = GuiStatics.IMAGE_INTERIOR_CHIRALOID;
    }
    if (race == SpaceRace.SPACE_PIRATE) {
      interior = GuiStatics.IMAGE_INTERIOR_PIRATE;
    }
    if (race == SpaceRace.LITHORIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_LITHORIAN;
    }
    if (race == SpaceRace.REBORGIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_REBORGIAN;
    }
    if (race == SpaceRace.SMAUGIRIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_SMAUGIRIAN;
    }
    if (race == SpaceRace.ALTEIRIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_ALTEIRIAN;
    }
    if (race == SpaceRace.ALONIANS) {
      interior = GuiStatics.IMAGE_INTERIOR_ALONIAN;
    }
    g.drawImage(interior,
        this.getWidth() / 2 - interior.getWidth() / 2,
        this.getHeight() / 2 - interior.getHeight() / 2,
        null);
    boolean drawImage = true;
    if (race == SpaceRace.SPACE_PIRATE && DiceGenerator.getRandom(40) == 0) {
      drawImage = false;
      SoundPlayer.playSound(SoundPlayer.GLITCH);
    }
    if (drawImage) {
      g.drawImage(race.getRaceImage(),
          this.getWidth() / 2 - race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - yOffset, null);
    }
    if (race == SpaceRace.SPACE_PIRATE) {
      float value1 = DiceGenerator.getRandom(1, 50) / 10f;
      float value2 = DiceGenerator.getRandom(1, 50) / 10f;
      Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {value1, value2 }, 0);
      g2d.setStroke(dashed);
      int grey = DiceGenerator.getRandom(40, 200);
      g2d.setColor(new Color(grey, grey, grey));
      g2d.drawLine(this.getWidth() / 2 - race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - yOffset + glitchLine,
          this.getWidth() / 2 + race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - yOffset + glitchLine);
      value1 = DiceGenerator.getRandom(1, 50) / 10f;
      value2 = DiceGenerator.getRandom(1, 50) / 10f;
      dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {value1, value2 }, 0);
      g2d.setStroke(dashed);
      grey = DiceGenerator.getRandom(40, 200);
      g2d.setColor(new Color(grey, grey, grey));
      g2d.drawLine(this.getWidth() / 2 - race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - yOffset + glitchLine + 1,
          this.getWidth() / 2 + race.getRaceImage().getWidth() / 2,
          this.getHeight() - race.getRaceImage().getHeight()
          - yOffset + glitchLine + 1);
      Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
      g2d.setStroke(full);
      glitchLine = glitchLine + 2;
      if (glitchLine > race.getRaceImage().getHeight()) {
        glitchLine = 0;
      }
    }
  }
}
