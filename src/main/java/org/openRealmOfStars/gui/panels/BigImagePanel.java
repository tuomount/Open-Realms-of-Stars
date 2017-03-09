package org.openRealmOfStars.gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.mapPanel.ParticleEffect;
import org.openRealmOfStars.gui.mapPanel.PlanetAnimation;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

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
 * Big image panel for drawing planet view/star dock view
 *
 */
public class BigImagePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Background image to draw
   */
  private BufferedImage backgroundImg;

  /**
   * Ship images to draw
   */
  private BufferedImage[] shipImages;

  /**
   * Draw star field
   */
  private boolean drawStarField;

  /**
   * Planet to draw
   */
  private Planet planet;

  /**
   * Title text
   */
  private String title;

  /**
   * Planet animation
   */
  private PlanetAnimation animation;

  /**
   * Create BigImagePanel
   * @param planet Planet to draw on background
   * @param starField Use star field or not
   * @param title if this is not null then planet info is not shown.
   */
  public BigImagePanel(final Planet planet, final boolean starField,
      final String title) {
    super();
    this.setBackground(Color.black);
    this.planet = planet;
    if (this.planet != null && !this.planet.isGasGiant()) {
      backgroundImg = Planet.PLANET_BIG_IMAGES[this.planet.getPlanetType()];
    } else if (this.planet != null && this.planet.isGasGiant()) {
      int imageIndex = this.planet.getPlanetImageIndex();
      if (imageIndex > Planet.GASWORLD_BIG_IMAGES.length - 1) {
        backgroundImg = Planet.GASWORLD_BIG_IMAGES[0];
      } else {
        backgroundImg = Planet.GASWORLD_BIG_IMAGES[this.planet
          .getPlanetImageIndex()];
      }
    } else {
      backgroundImg = null;
    }
    drawStarField = starField;
    this.title = title;
    this.shipImages = null;
    this.setAnimation(null);
  }

  /**
   * Draw bold text with current font
   * @param g Graphics to draw
   * @param border Text border color
   * @param center Text center color
   * @param x X-coordinate
   * @param y Y-coordinate
   * @param text Text to draw
   */
  private static void drawBoldText(final Graphics g, final Color border,
      final Color center, final int x, final int y, final String text) {
    g.setColor(Color.black);
    g.drawString(text, x + 2, y);
    g.drawString(text, x - 2, y);
    g.drawString(text, x, y + 2);
    g.drawString(text, x, y - 2);
    g.drawString(text, x - 1, y - 1);
    g.drawString(text, x + 1, y - 1);
    g.drawString(text, x + 1, y + 1);
    g.drawString(text, x - 1, y + 1);
    g.setColor(border);
    g.drawString(text, x + 1, y);
    g.drawString(text, x - 1, y);
    g.drawString(text, x, y + 1);
    g.drawString(text, x, y - 1);
    g.setColor(center);
    g.drawString(text, x, y);

  }

  /**
   * BufferedImage array of ship image to draw
   * @param images Images to draw
   */
  public void setShipImage(final BufferedImage[] images) {
    this.shipImages = images;
    this.repaint();
  }

  /**
   * Background planet offset X coordinate
   */
  private static final int PLANET_X_OFFSET = 575;
  /**
   * Background planet offset Y coordinate
   */
  private static final int PLANET_Y_OFFSET = 575;

  /**
   * Planet animation offset X coordinate
   */
  private static final int ANIM_X_OFFSET = 280;
  /**
   * Planet animation offset Y coordinate
   */
  private static final int ANIM_Y_OFFSET = 220;
  /**
   * Planet bomb animation offset X coordinate
   */
  private static final int BOMB_X_OFFSET = 235;
  /**
   * Planet bomb animation offset Y coordinate
   */
  private static final int BOMB_Y_OFFSET = 235;

  /**
   * Draw planet text are about planet information
   * @param g Graphics to use for drawing
   */
  private void drawTextArea(final Graphics g) {
    g.setFont(GuiStatics.getFontCubellan());
    if (title == null && planet != null) {
      StringBuilder sb = new StringBuilder(planet.generateInfoText());
      int lastSpace = -1;
      int rowLen = 0;
      int maxRowLen = this.getWidth() / 12;
      for (int i = 0; i < sb.length(); i++) {
        if (sb.charAt(i) == ' ') {
          lastSpace = i;
        }
        if (sb.charAt(i) == '\n') {
          lastSpace = -1;
          rowLen = 0;
        } else {
          rowLen++;
        }
        if (rowLen > maxRowLen) {
          if (lastSpace != -1) {
            sb.setCharAt(lastSpace, '\n');
            rowLen = i - lastSpace;
            lastSpace = -1;
          } else {
            sb.setCharAt(i, '\n');
            lastSpace = -1;
            rowLen = 0;
          }
        }
      }
      String[] texts = sb.toString().split("\n");
      int offsetX = (PLANET_X_OFFSET - backgroundImg.getWidth()) / 2
          - GuiStatics.getTextWidth(GuiStatics.getFontCubellanBold(), texts[0])
              / 2
          + backgroundImg.getWidth() / 2;
      int offsetY = (PLANET_Y_OFFSET - backgroundImg.getHeight()) / 2;
      if (planet.isGasGiant()) {
        offsetY = offsetY + 200;
      }
      g.setFont(GuiStatics.getFontCubellanBold());
      drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
          GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, offsetX, offsetY, texts[0]);
      if (planet.getPlanetPlayerInfo() != null) {
        offsetX = (PLANET_X_OFFSET - backgroundImg.getWidth()) / 2
            - GuiStatics.getTextWidth(GuiStatics.getFontCubellanBold(),
                planet.getPlanetPlayerInfo().getEmpireName()) / 2
            + backgroundImg.getWidth() / 2;
        drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
            GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, offsetX, offsetY + 25,
            planet.getPlanetPlayerInfo().getEmpireName());
      }
      g.setFont(GuiStatics.getFontCubellan());
      for (int i = 1; i < texts.length; i++) {
        drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
            GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, 25,
            this.getHeight() / 2 + i * 15, texts[i]);
      }
    } else {
      if (title == null) {
        title = "In Deep Space...";
      }
      g.setFont(GuiStatics.getFontCubellanBoldBig());
      int offsetX = 50;
      int offsetY = 75;
      if (backgroundImg != null) {
        offsetX = (this.getWidth() - backgroundImg.getWidth()) / 2 - GuiStatics
            .getTextWidth(GuiStatics.getFontCubellanBoldBig(), title) / 2
            + backgroundImg.getWidth() / 2;
        offsetY = (PLANET_Y_OFFSET - backgroundImg.getHeight()) / 2;
        if (planet.isGasGiant()) {
          offsetY = offsetY + 100;
        }
      }
      drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK,
          GuiStatics.COLOR_COOL_SPACE_BLUE, offsetX, offsetY, title);

    }

  }

  /**
   * Ship's offset for x coordinate
   */
  private static final int SHIP_OFFSET_X = 332;
  /**
   * Ship's offset for y coordinate
   */
  private static final int SHIP_OFFSET_Y = 332;

  @Override
  public void paint(final Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int sx = 0;
    int sy = 0;
    if (planet != null) {

      sx = planet.getName().length() * (planet.getOrderNumber() - 1) * 3
          + planet.getX();
      sy = planet.getName().length() * (planet.getGroundSize() - 7) * 3
          + planet.getY();
    }
    if (drawStarField) {
      g2d.drawImage(GuiStatics.STAR_FIELD_IMAGE, -sx, -sy, null);
    } else {
      this.setBackground(Color.black);
      g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    if (backgroundImg != null) {
      if (title == null) {
        int offsetX = (PLANET_X_OFFSET - backgroundImg.getWidth()) / 2;
        int offsetY = (PLANET_Y_OFFSET - backgroundImg.getHeight()) / 2;
        if (planet.isGasGiant()) {
          offsetY = offsetY + 200;
          offsetX = offsetX + 200;
        }
        g2d.drawImage(backgroundImg, offsetX, offsetY, null);
      } else {
        g2d.drawImage(GuiStatics.NEBULAE_IMAGE, -sx, -sy, null);
        int offsetX = (this.getWidth() - backgroundImg.getWidth()) / 2;
        int offsetY = (PLANET_Y_OFFSET - backgroundImg.getHeight()) / 2;
        if (planet.isGasGiant()) {
          offsetY = offsetY + 100;
          offsetX = offsetX + 100;
        }
        g2d.drawImage(backgroundImg, offsetX, offsetY, null);
      }
    } else {
      g2d.drawImage(GuiStatics.NEBULAE_IMAGE, -sx, -sy, null);
    }
    if (animation != null) {
      if (animation
          .getAnimationType() == PlanetAnimation.ANIMATION_TYPE_TURRET) {
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        g2d.setStroke(full);
        g2d.setColor(animation.getBeamColor());
        g2d.drawLine(animation.getSx(), animation.getSy(), animation.getEx(),
            animation.getEy());
      }
      List<ParticleEffect> particles = animation.getParticles();
      Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
      g2d.setStroke(full);
      for (ParticleEffect part : particles) {
        g2d.setColor(part.getColor());
        g2d.drawLine(part.getX(), part.getY(), part.getX() + 1,
            part.getY() + 1);
      }
      animation.doAnimation();
      if (animation.isAnimationFinished()) {
        animation = null;
      }
    }
    if (shipImages != null) {
      for (int i = 0; i < shipImages.length; i++) {

        int offsetX = SHIP_OFFSET_X;
        int offsetY = SHIP_OFFSET_Y;
        switch (i) {
        case 0:
          break;
        case 1: {
          offsetY = offsetY - ShipImage.MAX_HEIGHT - 14;
          offsetX = offsetX + ShipImage.MAX_WIDTH + 14;
          break;
        }
        case 2: {
          offsetY = offsetY + ShipImage.MAX_HEIGHT / 2;
          offsetX = offsetX - ShipImage.MAX_WIDTH - 6;
          break;
        }
        case 3: {
          offsetY = offsetY - ShipImage.MAX_HEIGHT;
          offsetX = offsetX + (ShipImage.MAX_WIDTH + 6) * 2;
          break;
        }
        case 4: {
          offsetY = offsetY + 10;
          offsetX = offsetX + ShipImage.MAX_WIDTH + 6;
          break;
        }
        case 5: {
          offsetY = offsetY + ShipImage.MAX_HEIGHT + 6;
          offsetX = offsetX + 10;
          break;
        }
        case 6: {
          offsetY = offsetY + 5;
          offsetX = offsetX + (ShipImage.MAX_WIDTH + 6) * 2;
          break;
        }
        case 7: {
          offsetY = offsetY + ShipImage.MAX_HEIGHT + 10;
          offsetX = offsetX + ShipImage.MAX_WIDTH + 11;
          break;
        }
        case 8: {
          offsetY = offsetY + ShipImage.MAX_HEIGHT;
          offsetX = offsetX + (ShipImage.MAX_WIDTH + 6) * 2;
          break;
        }
        default: {
          offsetY = offsetY - ShipImage.MAX_HEIGHT - 14;
          offsetX = offsetX + ShipImage.MAX_WIDTH + 14;
          break;
        }
        }
        if (animation != null) {
          if (animation.getAnimationType() == PlanetAnimation
              .ANIMATION_TYPE_AIM
              && backgroundImg != null && (animation.getShipIndex() == i
                  || animation.getShipIndex() > 8)) {
            int px = ANIM_X_OFFSET;
            int py = ANIM_Y_OFFSET;
            int nx = DiceGenerator.getRandom(25);
            int ny = DiceGenerator.getRandom(25);
            if (DiceGenerator.getRandom(1) == 0) {
              nx = nx * -1;
            }
            if (DiceGenerator.getRandom(1) == 0) {
              ny = ny * -1;
            }
            px = px + nx;
            py = py + ny;

            animation.setCoords(px, py, offsetX + ShipImage.MAX_WIDTH / 2,
                offsetY + ShipImage.MAX_HEIGHT / 2);
            animation.setAnimationType(PlanetAnimation.ANIMATION_TYPE_TURRET);
          }
          if ((animation.getAnimationType()
              == PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM
              || animation.getAnimationType()
              == PlanetAnimation.ANIMATION_TYPE_NUKE_AIM)
              && backgroundImg != null) {
            int px = BOMB_X_OFFSET;
            int py = BOMB_Y_OFFSET;
            int nx = DiceGenerator.getRandom(backgroundImg.getWidth() / 4);
            int ny = DiceGenerator.getRandom(backgroundImg.getHeight() / 4);
            px = px + nx;
            py = py + ny;

            animation.setCoords(px, py, px, py);
            if (animation.getAnimationType()
                == PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM) {
              animation.setAnimationType(
                  PlanetAnimation.ANIMATION_TYPE_BOMBING);
            }
            if (animation.getAnimationType()
                == PlanetAnimation.ANIMATION_TYPE_NUKE_AIM) {
              animation.setAnimationType(
                  PlanetAnimation.ANIMATION_TYPE_NUKING);
            }
          }
        }
        if (i < 9) {
          g2d.drawImage(shipImages[i], offsetX, offsetY, null);
        }
      } // End of ship loop
    }
    if (animation != null && animation.getAnimFrame() != null) {
      BufferedImage img = animation.getAnimFrame();
      g2d.drawImage(img, animation.getEx() - img.getWidth() / 2,
          animation.getEy() - img.getHeight() / 2, null);
      if (animation
          .getAnimationType() == PlanetAnimation.ANIMATION_TYPE_NUKING) {
        g2d.setColor(animation.getNukeColor());
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
      }
    }

    drawTextArea(g);
    this.paintChildren(g2d);
  }

  /**
   * Get animation for planet. Usually bombing or nuking the planet
   * @return Planet Animation
   */
  public PlanetAnimation getAnimation() {
    return animation;
  }

  /**
   * Set new animation for planet
   * @param animation Planet animation
   */
  public void setAnimation(final PlanetAnimation animation) {
    this.animation = animation;
  }

}
