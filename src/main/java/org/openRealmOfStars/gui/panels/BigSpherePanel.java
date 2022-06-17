package org.openRealmOfStars.gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.mapPanel.ParticleEffect;
import org.openRealmOfStars.gui.mapPanel.PlanetAnimation;
import org.openRealmOfStars.gui.utilies.GraphRoutines;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
 * Big sphere panel for drawing planet view/star dock view
 *
 */
public class BigSpherePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Background image to draw
   */
  private BufferedImage backgroundImg;

  /**
   * Image for north planet
   */
  private BufferedImage northPlanetImg;
  /**
   * Image for south planet
   */
  private BufferedImage southPlanetImg;
  /**
   * Image for west planet
   */
  private BufferedImage westPlanetImg;
  /**
   * Image for east planet
   */
  private BufferedImage eastPlanetImg;
  /**
   * Ship images to draw
   */
  private BufferedImage[] shipImages;

  /**
   * Draw star field
   */
  private boolean drawStarField;


  /**
   * Title text
   */
  private String title;

  /**
   * Planet animation
   */
  private PlanetAnimation animation;

  /**
   * Player who is currently playing the game.
   * This attribute can be null. If this is not null
   * then player is shown text if this planet can colonize this planet.
   */
  private PlayerInfo player;

  /**
   * Textual information shown in screen.
   */
  private String textInformation;

  /**
   * Orbital X coordinate around the planet
   */
  private double orbitalX;
  /**
   * Orbital Z coordinate around the planet
   */
  private double orbitalZ;
  /**
   * Orbital pixel offset Y coordinate
   */
  private int orbitalY;
  /**
   * Orbital angle around the planet.
   */
  private double orbitalAngle;
  /**
   * Custom image for orbital.
   */
  private BufferedImage customOrbital;

  /**
   * Flag to reposition title text.
   */
  private boolean textInMiddle;
  /**
   * Planet's texture offset for rotation
   */
  private int planetTextureOffset = 0;
  /**
   * Create BigSpherePanel
   * @param starField Use star field or not
   * @param title if this is not null then planet info is not shown.
   */
  public BigSpherePanel(final boolean starField,
      final String title) {
    super();
    this.setBackground(Color.black);
    backgroundImg = new BufferedImage(360 * 2, 360,
        BufferedImage.TYPE_4BYTE_ABGR);
    BufferedImage temp = new BufferedImage(360, 360,
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics graphics = temp.getGraphics();
    if (graphics instanceof Graphics2D) {
      Graphics2D g2d = (Graphics2D) graphics;
      g2d.setColor(Color.BLUE);
      g2d.fillRect(0, 0, temp.getWidth(), temp.getHeight());
      int w = temp.getWidth();
      int h = temp.getHeight();
      int ground = DiceGenerator.getRandom(500, 900);
      for (int j = 0; j < 7; j++) {
        int mx = DiceGenerator.getRandom(w);
        int my = DiceGenerator.getRandom(h);
        for (int i = 0; i < ground; i++) {
          Color c = Color.GREEN;
          g2d.setColor(c);
          g2d.fillOval(mx, my, DiceGenerator.getRandom(4, 20),
              DiceGenerator.getRandom(4, 20));
          int choice = DiceGenerator.getRandom(3);
          if (choice == 0) {
            my = my - DiceGenerator.getRandom(1, 6);
            if (my < 0) {
              my = my + h;
            }
          }
          if (choice == 1) {
            mx = mx - DiceGenerator.getRandom(1, 6);
            if (mx < 0) {
              mx = mx + w;
            }
          }
          if (choice == 2) {
            mx = mx + DiceGenerator.getRandom(1, 6);
            if (mx > w) {
              mx = mx - w;
            }
          }
          if (choice == 3) {
            my = my + DiceGenerator.getRandom(1, 6);
            if (my > h) {
              my = my - h;
            }
          }
        }
      }
      int mx = DiceGenerator.getRandom(w);
      int my = 10;
      for (int i = 0; i < 500; i++) {
        Color c = Color.WHITE;
        g2d.setColor(c);
        g2d.fillOval(mx, my, DiceGenerator.getRandom(4, 20),
            DiceGenerator.getRandom(4, 20));
        int choice = DiceGenerator.getRandom(3);
        if (choice == 0) {
          my = my - DiceGenerator.getRandom(1, 6);
          if (my < 0) {
            my = 0;
          }
        }
        if (choice == 1) {
          mx = mx - DiceGenerator.getRandom(1, 6);
          if (mx < 0) {
            mx = mx + w;
          }
        }
        if (choice == 2) {
          mx = mx + DiceGenerator.getRandom(1, 6);
          if (mx > w) {
            mx = mx - w;
          }
        }
        if (choice == 3) {
          my = my + DiceGenerator.getRandom(1, 6);
          if (my > 80) {
            my = 80;
          }
        }
      }
      mx = DiceGenerator.getRandom(w);
      my = temp.getHeight() - 10;
      for (int i = 0; i < 500; i++) {
        Color c = Color.WHITE;
        g2d.setColor(c);
        g2d.fillOval(mx, my, DiceGenerator.getRandom(4, 20),
            DiceGenerator.getRandom(4, 20));
        int choice = DiceGenerator.getRandom(3);
        if (choice == 0) {
          my = my - DiceGenerator.getRandom(1, 6);
          if (my < temp.getHeight() - 80) {
            my = temp.getHeight() - 80;
          }
        }
        if (choice == 1) {
          mx = mx - DiceGenerator.getRandom(1, 6);
          if (mx < 0) {
            mx = mx + w;
          }
        }
        if (choice == 2) {
          mx = mx + DiceGenerator.getRandom(1, 6);
          if (mx > w) {
            mx = mx - w;
          }
        }
        if (choice == 3) {
          my = my + DiceGenerator.getRandom(1, 6);
          if (my > h) {
            my = h;
          }
        }
      }
    }
    graphics = backgroundImg.getGraphics();
    if (graphics instanceof Graphics2D) {
      Graphics2D g2d = (Graphics2D) graphics;
      g2d.drawImage(temp, 0, 0, null);
      g2d.drawImage(temp, backgroundImg.getWidth() / 2, 0, null);
    }
    drawStarField = starField;
    this.title = title;
    this.shipImages = null;
    this.setAnimation(null);
    orbitalX = 0;
    orbitalY = 0;
    orbitalZ = 0;
    orbitalAngle = DiceGenerator.getRandom(359);
    customOrbital = null;
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
  private static final int PLANET_Y_OFFSET = 600;

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
      if (offsetY < 75) {
        offsetY = 75;
      }
      if (textInMiddle) {
        offsetY = (this.getHeight() - backgroundImg.getHeight()) / 2
            - GuiStatics.getTextHeight(GuiStatics.getFontCubellanBoldBig(),
                title) / 2;
      }
    }
    drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK,
        GuiStatics.COLOR_COOL_SPACE_BLUE, offsetX, offsetY, title);

    if (textInformation != null) {
      String[] texts = textInformation.split("\n");
      g.setFont(GuiStatics.getFontCubellan());
      offsetX = 0;
      for (int i = 0; i < texts.length; i++) {
        int value = GuiStatics.getTextWidth(GuiStatics.getFontCubellan(),
            texts[i]);
        if (value > offsetX) {
          offsetX = value;
        }
      }
      for (int i = 0; i < texts.length; i++) {
        drawBoldText(g, GuiStatics.COLOR_COOL_SPACE_BLUE_DARK_TRANS,
            GuiStatics.COLOR_COOL_SPACE_BLUE_TRANS, this.getWidth() - offsetX
            - 20, this.getHeight() - (texts.length + 2) * 15 + i * 15,
            texts[i]);
      }
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

  /**
   * Paint sphere
   * @param g2d Graphics 2D
   * @param offsetX Offset X coordinate where to draw
   * @param offsetY Offset Y coordinate where to draw
   */
  private void paintSphere(final Graphics2D g2d,
       final int offsetX, final int offsetY) {
    double westAngle = 0;
    double northAngle = 0;
    final int maxPixelSize = 8;
    for (int i = 0; i < 360; i++) {
      westAngle = 0;
      for (int j = 0; j < 180; j++) {
        double y = Math.sin(Math.toRadians(northAngle / 2.0));
        double x = -Math.cos(Math.toRadians(westAngle)) * y
            * backgroundImg.getWidth() / 4.0 + backgroundImg.getWidth() / 8.0;
        double z = Math.sin(Math.toRadians(westAngle)) * y * maxPixelSize;
        y = -Math.cos(Math.toRadians(northAngle / 2.0))
            * backgroundImg.getHeight() / 2.0 + backgroundImg.getHeight() / 2.0;
        int size = (int) z;
        if (size < 0) {
          size = 1;
        }
        if (size > maxPixelSize) {
          size = maxPixelSize;
        }
        int color = backgroundImg.getRGB(planetTextureOffset + j, i);
        g2d.setColor(new Color(color));
        if (size > 0) {
          g2d.fillOval(offsetX + (int) x, offsetY + (int) y, size, size);
        }
        westAngle++;
      }
      northAngle++;
    }
    planetTextureOffset++;
    if (planetTextureOffset >= backgroundImg.getWidth() / 2) {
      planetTextureOffset = 0;
    }
  }
  /**
   * Paint orbital.
   * @param g2d Graphics
   * @param orbitalImage Orbital image
   * @param offsetX Offset in pixel
   * @param offsetY Offset in pixels
   */
  private void paintOrbital(final Graphics2D g2d,
      final BufferedImage orbitalImage, final int offsetX, final int offsetY) {
    if (orbitalImage != null) {
      orbitalX = Math.cos(Math.toRadians(orbitalAngle));
      orbitalZ = Math.sin(Math.toRadians(orbitalAngle));
      int newSize = (int) (5 + (orbitalZ + 1) * 42.5);
      BufferedImage scaled = GraphRoutines.scaleImage(orbitalImage,
          newSize, newSize);
      int width = backgroundImg.getWidth() / 2;
      g2d.drawImage(scaled,
          offsetX + width + (int) (orbitalX * (width + 64)),
          offsetY + backgroundImg.getHeight() / 2 + orbitalY, null);
    }

  }
  @Override
  public void paint(final Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    int sx = 0;
    int sy = 0;
    int nebulaeJitter = 16;
    if (drawStarField) {
      //g2d.drawImage(GuiStatics.STAR_FIELD_IMAGE, -sx, -sy, null);
/*      GraphRoutines.drawTiling(g2d, GuiStatics.STAR_FIELD_IMAGE, -sx, -sy,
          this.getWidth(), this.getHeight());*/
      GraphRoutines.drawTiling(g2d, GuiStatics.getStarField(), -sx, -sy,
          this.getWidth(), this.getHeight());
    } else {
      this.setBackground(Color.black);
      g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    orbitalAngle = orbitalAngle + 1;
    if (orbitalAngle > 359) {
      orbitalAngle = orbitalAngle - 360;
    }
    //g2d.drawImage(GuiStatics.NEBULAE_IMAGE, -sx, -sy, null);
    int dx = (int) (Math.cos(Math.toRadians(orbitalAngle)) * nebulaeJitter);
    int dy = (int) (Math.sin(Math.toRadians(orbitalAngle)) * nebulaeJitter);
    GraphRoutines.drawTiling(g2d, GuiStatics.NEBULAE_IMAGE,
        -sx - dx - nebulaeJitter,
        -sy - dy - nebulaeJitter, this.getWidth(), this.getHeight());
    if (northPlanetImg != null) {
      int offsetX = 0;
      int offsetY = 0;
      g2d.drawImage(northPlanetImg, offsetX, offsetY, null);
    }
    if (eastPlanetImg != null) {
      int offsetX = eastPlanetImg.getWidth();
      int offsetY = 0;
      g2d.drawImage(eastPlanetImg, this.getWidth() - offsetX, offsetY, null);
    }
    if (southPlanetImg != null) {
      int offsetX = southPlanetImg.getWidth();
      int offsetY = southPlanetImg.getHeight();
      g2d.drawImage(southPlanetImg, this.getWidth() - offsetX,
          this.getHeight() - offsetY, null);
    }
    if (westPlanetImg != null) {
      int offsetX = 0;
      int offsetY = westPlanetImg.getHeight();
      g2d.drawImage(westPlanetImg, offsetX, this.getHeight() - offsetY, null);
    }
    if (backgroundImg != null) {
      if (title == null) {
        int offsetX = (PLANET_X_OFFSET - backgroundImg.getWidth()) / 2;
        int offsetY = (PLANET_Y_OFFSET - backgroundImg.getHeight()) / 2;
        BufferedImage orbital = null;
        if (customOrbital != null) {
          orbital = customOrbital;
        }
        if (orbitalZ < 0) {
          paintOrbital(g2d, orbital,
              offsetX, offsetY);
        }
        paintSphere(g2d, offsetX, offsetY);
        if (orbitalZ >= 0) {
          paintOrbital(g2d, orbital,
              offsetX, offsetY);
        }
      } else {
        int offsetX = (this.getWidth() - backgroundImg.getWidth()) / 2;
        int offsetY = (PLANET_Y_OFFSET - backgroundImg.getHeight()) / 2;
        BufferedImage orbital = null;
        if (customOrbital != null) {
          orbital = customOrbital;
        }
        if (orbitalZ < 0) {
          paintOrbital(g2d, orbital,
              offsetX, offsetY);
        }
        paintSphere(g2d, offsetX, offsetY);
        if (orbitalZ >= 0) {
          paintOrbital(g2d, orbital,
              offsetX, offsetY);
        }
      }
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

  /**
   * Get the current player for BigImagePanel. This can be null.
   * This is used for telling if planet can be colonized by certain
   * race.
   * @return the player
   */
  public PlayerInfo getPlayer() {
    return player;
  }

  /**
   * Set the current player for BigImagePanel. This can be null.
   * This is used for telling if planet can be colonized by certain
   * race.
   * @param player the player to set
   */
  public void setPlayer(final PlayerInfo player) {
    this.player = player;
  }

  /**
   * Set Title text
   * @param text Title as a String
   */
  public void setTitle(final String text) {
    title = text;
  }

  /**
   * Set Textual information to scren. Set to null to clear it.
   * @param text String or null
   */
  public void setText(final String text) {
    textInformation = text;
  }

  /**
   * Set distant planet image
   * @param distantPlanet Planet to be in distant. Can be null.
   * @return Distant planet image
   */
  private BufferedImage setDistantPlanet(final Planet distantPlanet) {
    BufferedImage img = null;
    if (distantPlanet != null) {
      img = distantPlanet.getBigImage();
      int imgWid = img.getWidth() / 2;
      int screenWid = this.getWidth() / 3;
      if (imgWid < screenWid) {
        img = GraphRoutines.scaleImage(img, img.getWidth() / 2,
            img.getHeight() / 2);
      } else {
        img = GraphRoutines.scaleImage(img, img.getWidth() / 3,
            img.getHeight() / 3);
      }
    }
    return img;
  }

  /**
   * Set distant north planet.
   * @param northPlanet Planet
   */
  public void setNorthPlanet(final Planet northPlanet) {
    northPlanetImg = setDistantPlanet(northPlanet);
  }
  /**
   * Set distant east planet.
   * @param eastPlanet Planet
   */
  public void setEastPlanet(final Planet eastPlanet) {
    eastPlanetImg = setDistantPlanet(eastPlanet);
  }
  /**
   * Set distant south planet.
   * @param southPlanet Planet
   */
  public void setSouthPlanet(final Planet southPlanet) {
    southPlanetImg = setDistantPlanet(southPlanet);
  }
  /**
   * Set distant west planet.
   * @param westPlanet Planet
   */
  public void setWestPlanet(final Planet westPlanet) {
    westPlanetImg = setDistantPlanet(westPlanet);
  }

  /**
   * Get Custom orbital image
   * @return Orbital image.
   */
  public BufferedImage getCustomOrbital() {
    return customOrbital;
  }

  /**
   * Set custom orbital image
   * @param customOrbital BufferedImage
   */
  public void setCustomOrbital(final BufferedImage customOrbital) {
    this.customOrbital = customOrbital;
  }

  /**
   * Is title text in middle of Y axis.
   * @return True if in middle
   */
  public boolean isTextInMiddle() {
    return textInMiddle;
  }

  /**
   * Set title text in middle
   * @param textInMiddle True to center title text
   */
  public void setTextInMiddle(final boolean textInMiddle) {
    this.textInMiddle = textInMiddle;
  }

}
