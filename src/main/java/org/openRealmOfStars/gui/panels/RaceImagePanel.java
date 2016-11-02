package org.openRealmOfStars.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.player.SpaceRace;

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
 * Race image panel of player setup
 *
 */

public class RaceImagePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Race picture to draw
   */
  private BufferedImage image;

  private static final int MAXX = 172;
  private static final int MAXY = 200;

  /**
   * Which race to show
   */
  private String raceToShow;

  /**
   * Create picture frame for player setup
   */
  public RaceImagePanel() {
    super();
    image = new BufferedImage(MAXX, MAXY, BufferedImage.TYPE_4BYTE_ABGR);
    Dimension size = new Dimension(image.getWidth(), image.getHeight());
    this.setMinimumSize(size);
    this.setPreferredSize(size);
    this.setBorder(new SimpleBorder());
    raceToShow = "Not in use";
  }

  public String getRaceToShow() {
    return raceToShow;
  }

  public void setRaceToShow(final String raceToShow) {
    if (raceToShow != null) {
      this.raceToShow = raceToShow;
      this.repaint();
    } else {
      this.raceToShow = "Not in use";
    }
  }

  @Override
  protected void paintComponent(final Graphics g) {
    GradientPaint gradient = new GradientPaint(this.getWidth() / 2, 0,
        Color.BLACK, this.getWidth() / 2, this.getHeight(),
        GuiStatics.COLOR_GREY_40, true);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setPaint(gradient);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    SpaceRace race = SpaceRace.getRaceByName(raceToShow);
    if (race == null) {
      g2d.setFont(GuiStatics.getFontCubellan());
      g2d.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
      int textWidth = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
          raceToShow);
      int offsetX = this.getWidth() / 2 - textWidth / 2;
      g2d.drawString(raceToShow, offsetX, this.getHeight() / 2);

    } else {
      image = race.getRaceImage();
      g.drawImage(image, this.getWidth() / 2 - image.getWidth() / 2,
          this.getHeight() - image.getHeight(), null);
    }
  }

}
