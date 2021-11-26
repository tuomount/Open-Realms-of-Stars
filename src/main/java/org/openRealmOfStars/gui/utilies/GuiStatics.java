package org.openRealmOfStars.gui.utilies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.AnimatedImage;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

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
 * Static GUI component like fonts and colors.
 *
 */
public final class GuiStatics {

  /**
   * Hiding the constructor for utility class
   */
  private GuiStatics() {
    // Nothing to do
  }

  /**
   *  Monospace font size 10
   */
  public static final Font FONT_SMALL = new Font("monospaced", Font.PLAIN, 10);
  /**
   *  Monospace font size 12
   */
  public static final Font FONT_NORMAL = new Font("monospaced", Font.BOLD, 12);

  /**
   * Text field height in pixels.
   */
  public static final int TEXT_FIELD_HEIGHT = 30;
  /**
   * Regular cubellan font
   */
  private static Font fontCubellan;

  /**
   * Regular cubellan font but Larger
   */
  private static Font fontCubellanLarger;

  /**
   * Regular cubellan font but smaller
   */
  private static Font fontCubellanSmall;

  /**
   * Regular cubellan font but smaller and larger
   */
  private static Font fontCubellanSmallLarger;

  /**
   * Get Regular Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellan() {
    if (isLargerFonts()) {
      if (fontCubellanLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan.ttf")
            .openStream()) {
          fontCubellanLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontCubellanLarger = fontCubellanLarger.deriveFont(18F);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontCubellanLarger;
    } else if (fontCubellan == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan.ttf")
          .openStream()) {
        fontCubellan = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellan = fontCubellan.deriveFont(16F);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontCubellan;
  }

  /**
   * Get Regular Cubellan font but smaller
   * @return Cubellan font
   */
  public static Font getFontCubellanSmaller() {
    if (isLargerFonts()) {
      if (fontCubellanSmallLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan.ttf")
            .openStream()) {
          fontCubellanSmallLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontCubellanSmallLarger = fontCubellanSmallLarger.deriveFont(15F);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontCubellanSmallLarger;
    } else if (fontCubellanSmall == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan.ttf")
          .openStream()) {
        fontCubellanSmall = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanSmall = fontCubellanSmall.deriveFont(13F);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontCubellanSmall;
  }

  /**
   * Bold cubellan font
   */
  private static Font fontCubellanBold;

  /**
   * Bold cubellan font and larger
   */
  private static Font fontCubellanBoldLarger;

  /**
   * Get bold Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellanBold() {
    if (isLargerFonts()) {
      if (fontCubellanBoldLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf")
            .openStream()) {
          fontCubellanBoldLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontCubellanBoldLarger = fontCubellanBoldLarger.deriveFont(28F);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontCubellanBoldLarger;
    } else if (fontCubellanBold == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf")
          .openStream()) {
        fontCubellanBold = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanBold = fontCubellanBold.deriveFont(24F);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontCubellanBold;
  }

  /**
   * Bold cubellan font
   */
  private static Font fontCubellanBoldBig;

  /**
   * Bold cubellan font and larger
   */
  private static Font fontCubellanBoldBigLarger;

  /**
   * Get bold Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellanBoldBig() {
    if (isLargerFonts()) {
      if (fontCubellanBoldBigLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf")
            .openStream()) {
          fontCubellanBoldBigLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontCubellanBoldBigLarger = fontCubellanBoldBigLarger.deriveFont(40F);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontCubellanBoldBigLarger;
    } else if (fontCubellanBoldBig == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf")
          .openStream()) {
        fontCubellanBoldBig = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanBoldBig = fontCubellanBoldBig.deriveFont(35F);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontCubellanBoldBig;
  }

  /**
   * Small cubellan font for Small Caps
   */
  private static Font fontCubellanSC;

  /**
   * Small cubellan font for Small Caps and larger
   */
  private static Font fontCubellanSCLarger;

  /**
   * Get Cubellan font with Small Caps
   * @return Cubellan font
   */
  public static Font getFontCubellanSC() {
    if (isLargerFonts()) {
      if (fontCubellanSCLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan_SC.ttf")
            .openStream()) {
          fontCubellanSCLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontCubellanSCLarger = fontCubellanSCLarger.deriveFont(15F);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontCubellanSCLarger;
    } else if (fontCubellanSC == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Cubellan_v_0_7/Cubellan_SC.ttf")
          .openStream()) {
        fontCubellanSC = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanSC = fontCubellanSC.deriveFont(13F);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontCubellanSC;
  }

  /**
   * Regular Squarion font
   */
  private static Font fontSquarion;

  /**
   * Regular Squarion font but Larger
   */
  private static Font fontSquarionLarger;

  /**
   * Get Regular Generale Station font
   * @return Generale Station font
   */
  public static Font getFontSquarion() {
    if (isLargerFonts()) {
      if (fontSquarionLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Squarion/hinted-Squarion.ttf")
            .openStream()) {
          fontSquarionLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontSquarionLarger = fontSquarionLarger.deriveFont(
              16F);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontSquarionLarger;
    } else if (fontSquarion == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Squarion/hinted-Squarion.ttf")
          .openStream()) {
        fontSquarion = Font.createFont(Font.TRUETYPE_FONT, is);
        fontSquarion = fontSquarion.deriveFont(14F);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontSquarion;
  }

  /**
   * Regular Squarion bold font
   */
  private static Font fontSquarionBold;

  /**
   * Regular Squarion bold font but Larger
   */
  private static Font fontSquarionBoldLarger;

  /**
   * Get Regular Generale Station font
   * @return Generale Station font
   */
  public static Font getFontSquarionBold() {
    if (isLargerFonts()) {
      if (fontSquarionBoldLarger == null) {
        try (InputStream is = Tiles.class
            .getResource("/resources/fonts/Squarion/hinted-Squarion.ttf")
            .openStream()) {
          fontSquarionBoldLarger = Font.createFont(Font.TRUETYPE_FONT, is);
          fontSquarionBoldLarger = fontSquarionBoldLarger.deriveFont(
              22F);
          fontSquarionBoldLarger = fontSquarionBoldLarger.deriveFont(Font.BOLD);
        } catch (IOException | FontFormatException e) {
          ErrorLogger.log("Error:" + e.getMessage());
          return FONT_SMALL;
        }
      }
      return fontSquarionBoldLarger;
    } else if (fontSquarionBold == null) {
      try (InputStream is = Tiles.class
          .getResource("/resources/fonts/Squarion/hinted-Squarion.ttf")
          .openStream()) {
        fontSquarionBold = Font.createFont(Font.TRUETYPE_FONT, is);
        fontSquarionBold = fontSquarionBold.deriveFont(18F);
        fontSquarionBold = fontSquarionBold.deriveFont(Font.BOLD);
      } catch (IOException | FontFormatException e) {
        ErrorLogger.log("Error:" + e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontSquarionBold;
  }

  /**
   * Use larger fonts
   */
  private static boolean useLargerFonts;

  /**
   * Line type for text background
   */
  public static final Stroke TEXT_LINE = new BasicStroke(12,
      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] {1f }, 0);

  /**
   * Sun Line type for text background, opacity 230.
   */
  public static final Color COLOR_GOLD_TRANS = new Color(210, 181, 44, 230);

  /**
   * Gold color
   */
  public static final Color COLOR_GOLD = new Color(210, 181, 44);

  /**
   * Space yellow
   */
  public static final Color COLOR_SPACE_YELLOW = new Color(251, 255, 51);

  /**
   * Bright white
   */
  public static final Color COLOR_BRIGHT_WHITE = new Color(241, 255, 241, 128);
  /**
   * Transparent black
   */
  public static final Color COLOR_TRANSPARENT_GREY = new Color(40, 40, 40, 128);
  /**
   * Transparent black
   */
  public static final Color COLOR_TRANSPARENT_BLACK = new Color(5, 5, 5, 128);

  /**
   * Space grey
   */
  public static final Color COLOR_SPACE_GREY = new Color(180, 180, 221);

  /**
   * Dark space yellow
   */
  public static final Color COLOR_SPACE_YELLOW_DARK = new Color(134, 134, 33);

  /**
   * Dark Gold color
   */
  public static final Color COLOR_GOLD_DARK = new Color(155, 130, 13);

  /**
   * Planet Line type for text background, opacity 65.
   */
  public static final Color COLOR_GREYBLUE = new Color(180, 180, 200, 65);

  /**
   * Panel background
   */
  public static final Color COLOR_SPACE_GREY_BLUE = new Color(81, 87, 133, 255);

  /**
   * Green text dark one
   */
  public static final Color COLOR_GREEN_TEXT_DARK = new Color(2, 102, 0, 255);

  /**
   * Green text
   */
  public static final Color COLOR_GREEN_TEXT = new Color(4, 186, 0, 255);

  /**
   * Grey text dark one
   */
  public static final Color COLOR_GREY_TEXT_DARK = new Color(90, 95, 90);

  /**
   * Grey text
   */
  public static final Color COLOR_GREY_TEXT = new Color(160, 165, 160);

  /**
   * Red text
   */
  public static final Color COLOR_RED_TEXT = new Color(186, 4, 0, 255);
  /**
   * Red text Dark
   */
  public static final Color COLOR_RED_TEXT_DARK = new Color(93, 2, 0, 255);

  /**
   * Yellow text
   */
  public static final Color COLOR_YELLOW_TEXT = new Color(220, 220, 4, 255);

  /**
   * Yellow text Dark
   */
  public static final Color COLOR_YELLOW_TEXT_DARK = new Color(110, 110, 2,
      255);

  /**
   * Damage 3/4
   */
  public static final Color COLOR_DAMAGE_LITTLE = new Color(177, 255, 11, 255);

  /**
   * Damage half
   */
  public static final Color COLOR_DAMAGE_HALF = new Color(252, 255, 11, 255);

  /**
   * Damage MUCH
   */
  public static final Color COLOR_DAMAGE_MUCH = new Color(255, 189, 11, 255);

  /**
   * Damage almost destroyed
   */
  public static final Color COLOR_DAMAGE_ALMOST_DESTROYED = new Color(255, 143,
      11, 255);

  /**
   * Damage destroyed
   */
  public static final Color COLOR_DESTROYED = new Color(255, 28, 11, 255);

  /**
   * Cool space blue
   */
  public static final Color COLOR_COOL_SPACE_BLUE = new Color(88, 210, 255);

  /**
   * Cool space blue dark
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARK = new Color(25, 120,
      193);

  /**
   * Deep space purple dark
   */
  public static final Color COLOR_DEEP_SPACE_PURPLE_DARK = new Color(25, 9, 61);

  /**
   * Deep space purple
   */
  public static final Color COLOR_DEEP_SPACE_PURPLE = new Color(47, 27, 92);

  /**
   * Cool space blue darker
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARKER = new Color(20, 110,
      180);

  /**
   * Cool space blue, opacity 128
   */
  public static final Color COLOR_COOL_SPACE_BLUE_TRANS = new Color(88, 210,
      255, 128);

  /**
   * Dark grey, opacity 128
   */
  public static final Color COLOR_VERY_DARK_GREY_TRANS = new Color(20, 20,
      20, 128);

  /**
   * Cool space blue dark, opacity 128
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARK_TRANS = new Color(25,
      120, 193, 128);

  /**
   * Deep Space Blue
   */
  public static final Color COLOR_DEEP_SPACE_BLUE = new Color(33, 33, 208);

  /**
   * Grey 160
   */
  public static final Color COLOR_GREY_160 = new Color(160, 160, 160);

  /**
   * Grey 80
   */
  public static final Color COLOR_GREY_80 = new Color(80, 80, 80);

  /**
   * Grey 40
   */
  public static final Color COLOR_GREY_40 = new Color(40, 40, 40);

  /**
   * First color for explosion
   */
  private static final Color EXPLOSION_COLOR_1 = new Color(255, 196, 18);
  /**
   * Second color for explosion
   */
  private static final Color EXPLOSION_COLOR_2 = new Color(244, 101, 14);
  /**
   * Third color for explosion
   */
  private static final Color EXPLOSION_COLOR_3 = new Color(255, 218, 72);
  /**
   * Fourth color for explosion
   */
  private static final Color EXPLOSION_COLOR_4 = new Color(241, 223, 17);
  /**
   * Fifth color for explosion
   */
  private static final Color EXPLOSION_COLOR_5 = new Color(255, 133, 13);

  /**
   * Explosion colors
   */
  public static final Color[] EXPLOSION_COLORS = {EXPLOSION_COLOR_1,
      EXPLOSION_COLOR_2, EXPLOSION_COLOR_3, EXPLOSION_COLOR_4,
      EXPLOSION_COLOR_5 };

  /**
   * First color for red beam
   */
  private static final Color BEAM_COLOR_1 = new Color(255, 36, 0);
  /**
   * Second color for red beam
   */
  private static final Color BEAM_COLOR_2 = new Color(255, 96, 0);
  /**
   * Third color for red beam
   */
  private static final Color BEAM_COLOR_3 = new Color(255, 128, 0);
  /**
   * Fourth color for red beam
   */
  private static final Color BEAM_COLOR_4 = new Color(255, 18, 0);
  /**
   * Fifth color for red beam
   */
  private static final Color BEAM_COLOR_5 = new Color(255, 115, 77);

  /**
   * Red beam colors
   */
  public static final Color[] BEAM_COLORS = {BEAM_COLOR_1, BEAM_COLOR_2,
      BEAM_COLOR_3, BEAM_COLOR_4, BEAM_COLOR_5, };

  /**
   * Base color for tentacle.
   */
  public static final Color TENTACLE_COLOR_1 = new Color(65, 50, 44);
  /**
   * Second color for tentacle.
   */
  public static final Color TENTACLE_COLOR_2 = new Color(88, 73, 52);
  /**
   * Green beam colors
   */
  public static final Color[] GREEN_BEAM_COLORS = {
      new Color(36, 255,  0),
      new Color(96, 255, 0),
      new Color(128, 255, 0),
      new Color(18, 255, 0),
      new Color(155, 255, 77)};

  /**
   * Blue beam colors
   */
  public static final Color[] BLUE_BEAM_COLORS = {
      new Color(36, 0,  255),
      new Color(96, 0, 255),
      new Color(128, 0, 255),
      new Color(18, 18, 255),
      new Color(155, 77, 255)};

  /**
   * Player color red.
   */
  public static final Color PLAYER_RED = new Color(215, 30, 34);
  /**
   * Player color blue.
   */
  public static final Color PLAYER_BLUE = new Color(29, 60, 233);
  /**
   * Player color green.
   */
  public static final Color PLAYER_GREEN = new Color(27, 145, 62);
  /**
   * Player color pink.
   */
  public static final Color PLAYER_PINK = new Color(237, 84, 186);
  /**
   * Player color orange.
   */
  public static final Color PLAYER_ORANGE = new Color(255, 148, 28);
  /**
   * Player color yellow.
   */
  public static final Color PLAYER_YELLOW = new Color(255, 255, 103);
  /**
   * Player color black.
   */
  public static final Color PLAYER_BLACK = new Color(63, 71, 78);
  /**
   * Player color white.
   */
  public static final Color PLAYER_WHITE = new Color(214, 224, 240);
  /**
   * Player color purple.
   */
  public static final Color PLAYER_PURPLE = new Color(107, 49, 188);
  /**
   * Player color brown.
   */
  public static final Color PLAYER_BROWN = new Color(113, 73, 30);
  /**
   * Player color cyan.
   */
  public static final Color PLAYER_CYAN = new Color(68, 253, 245);
  /**
   * Player color lime.
   */
  public static final Color PLAYER_LIME = new Color(80, 239, 57);
  /**
   * Player color chestnut.
   */
  public static final Color PLAYER_CHESTNUT = new Color(115, 27, 19);
  /**
   * Player color rose.
   */
  public static final Color PLAYER_ROSE = new Color(236, 192, 211);
  /**
   * Player color banana.
   */
  public static final Color PLAYER_BANANA = new Color(255, 253, 190);
  /**
   * Player color gray.
   */
  public static final Color PLAYER_GRAY = new Color(112, 132, 151);
  /**
   * Player color tan.
   */
  public static final Color PLAYER_TAN = new Color(146, 135, 118);
  /**
   * Player color coral.
   */
  public static final Color PLAYER_CORAL = new Color(236, 117, 120);

  /**
   * Player color olive.
   */
  public static final Color PLAYER_OLIVE = new Color(97, 114, 24);

  /**
   * Player color sky.
   */
  public static final Color PLAYER_SKY = new Color(110, 127, 217);

  /**
   * Relation unknown icon
   */
  public static final BufferedImage RELATION_UNKNOWN = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 0, 0, 32, 32);
  /**
   * Relation peace icon
   */
  public static final BufferedImage RELATION_PEACE = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 32, 0, 32, 32);
  /**
   * Relation war icon
   */
  public static final BufferedImage RELATION_WAR = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 64, 0, 32, 32);
  /**
   * Relation trade alliance icon
   */
  public static final BufferedImage RELATION_TRADE_ALLIANCE = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 96, 0, 32, 32);
  /**
   * Relation trade embargo icon
   */
  public static final BufferedImage RELATION_TRADE_EMBARGO = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 96, 32, 32, 32);
  /**
   * Relation alliance icon
   */
  public static final BufferedImage RELATION_ALLIANCE = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 64, 32, 32, 32);
  /**
   * Defensive Pact icon
   */
  public static final BufferedImage DEFENSIVE_PACT = Icons.loadBigIcon(
      "/resources/images/bigicons.png", 32, 32, 32, 32);
  /**
   * Photon torpedo
   */
  public static final BufferedImage PHOTON_TORPEDO = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/photon_torp.png"));
  /**
   * Plasma bullet
   */
  public static final BufferedImage PLASMA_BULLET = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/plasma.png"));

  /**
   * Explosion animation
   */
  public static final AnimatedImage EXPLOSION1 = new AnimatedImage(64, 64,
      "/resources/images/explosion1.png");

  /**
   * Blue explosion animation
   */
  public static final AnimatedImage EXPLOSION2 = new AnimatedImage(64, 64,
      "/resources/images/explosion2.png");

  /**
   * Small explosion animation
   */
  public static final AnimatedImage EXPLOSION3 = new AnimatedImage(32, 32,
      "/resources/images/explosion3.png");

  /**
   * Nuke animation
   */
  public static final AnimatedImage EXPLOSION4 = new AnimatedImage(64, 64,
      "/resources/images/explosion4.png");

  /**
   * Lighting animation
   */
  public static final AnimatedImage LIGHTNING = new AnimatedImage(64, 64,
      "/resources/images/lightning.png");

  /**
   * Shield animation
   */
  public static final AnimatedImage SHIELD1 = new AnimatedImage(64, 64,
      "/resources/images/shield.png");

  /**
   * Scanner animation
   */
  public static final AnimatedImage SCANNER = new AnimatedImage(64, 64,
      "/resources/images/scanner.png");

  /**
   * Privateering animation
   */
  public static final AnimatedImage PRIVATEER = new AnimatedImage(64, 64,
      "/resources/images/privateer.png");

  /**
   * Bite animation
   */
  public static final AnimatedImage BITE = new AnimatedImage(64, 64,
      "/resources/images/bite.png");

  /**
   * Wormhole animation
   */
  public static final AnimatedImage WORMHOLE = new AnimatedImage(64, 64,
      "/resources/images/wormhole.png");
  /**
   * Left arrow
   */
  public static final BufferedImage LEFT_ARROW = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/left_arrow.png"));

  /**
   * Left arrow pressed
   */
  public static final BufferedImage LEFT_ARROW_PRESSED = IOUtilities.loadImage(
      Tiles.class.getResource("/resources/images/left_arrow_pressed.png"));

  /**
   * Right arrow
   */
  public static final BufferedImage RIGHT_ARROW = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/right_arrow.png"));

  /**
   * Right arrow pressed
   */
  public static final BufferedImage RIGHT_ARROW_PRESSED = IOUtilities.loadImage(
      Tiles.class.getResource("/resources/images/right_arrow_pressed.png"));

  /**
   * Crosshair for combat
   */
  public static final BufferedImage CROSSHAIR = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/crosshair.png"));

  /**
   * ORoS icon 32x32
   */
  public static final BufferedImage LOGO32 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/oros-logo32.png"));
  /**
   * ORoS icon 48x48
   */
  public static final BufferedImage LOGO48 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/oros-logo48.png"));
  /**
   * ORoS icon 64x64
   */
  public static final BufferedImage LOGO64 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/oros-logo64.png"));
  /**
   * ORoS icon 128x128
   */
  public static final BufferedImage LOGO128 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/oros-logo128.png"));

  /**
   * Red Crosshair for combat
   */
  public static final BufferedImage RED_CROSSHAIR = IOUtilities.loadImage(
      Tiles.class.getResource("/resources/images/red_crosshair.png"));

  /**
   * Big planet screen Rock 1
   */
  public static final BufferedImage BIG_PLANET_ROCK1 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/rock1.png"));

  /**
   * Big planet screen water world 1
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD1 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld1.png"));

  /**
   * Big planet screen water world 2
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD2 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld2.png"));

  /**
   * Big planet screen water world 3
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD3 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld3.png"));

  /**
   * Big planet screen water world 4
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD4 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld4.png"));
  /**
   * Big planet screen water world 5
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD5 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld5.png"));
  /**
   * Big planet screen water world 6
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD6 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld6.png"));
  /**
   * Big planet screen water world 7
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD7 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld7.png"));
  /**
   * Big planet screen water world 8
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD8 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld8.png"));
  /**
   * Big planet screen water world 9
   */
  public static final BufferedImage BIG_PLANET_WATERWORLD9 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/waterworld9.png"));

  /**
   * Big planet screen ice world 1
   */
  public static final BufferedImage BIG_PLANET_ICEWORLD1 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/iceworld1.png"));

  /**
   * Big planet screen ice world 2
   */
  public static final BufferedImage BIG_PLANET_ICEWORLD2 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/iceworld2.png"));
  /**
   * Big planet screen ice world 3
   */
  public static final BufferedImage BIG_PLANET_ICEWORLD3 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/iceworld3.png"));
  /**
   * Big planet screen ice world 4
   */
  public static final BufferedImage BIG_PLANET_ICEWORLD4 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/iceworld4.png"));

  /**
   * Big planet screen carbon world 1
   */
  public static final BufferedImage BIG_PLANET_CARBONWORLD1 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/carbonworld1.png"));
  /**
   * Big planet screen carbon world 2
   */
  public static final BufferedImage BIG_PLANET_CARBONWORLD2 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/carbonworld2.png"));
  /**
   * Big planet screen carbon world 3
   */
  public static final BufferedImage BIG_PLANET_CARBONWORLD3 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/carbonworld3.png"));

  /**
   * Big planet screen iron planet 1
   */
  public static final BufferedImage BIG_PLANET_IRONPLANET1 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/ironplanet1.png"));

  /**
   * Big planet screen iron planet 2
   */
  public static final BufferedImage BIG_PLANET_IRONPLANET2 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/ironplanet2.png"));

  /**
   * Big planet screen iron planet 3
   */
  public static final BufferedImage BIG_PLANET_IRONPLANET3 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/ironplanet3.png"));
  /**
   * Big planet screen iron planet 4
   */
  public static final BufferedImage BIG_PLANET_IRONPLANET4 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/ironplanet4.png"));
  /**
   * Big planet screen iron planet 5
   */
  public static final BufferedImage BIG_PLANET_IRONPLANET5 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/ironplanet5.png"));
  /**
   * Big planet screen iron planet 6
   */
  public static final BufferedImage BIG_PLANET_IRONPLANET6 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/ironplanet6.png"));

  /**
   * Big planet gas world
   */
  public static final BufferedImage BIG_GASWORLD1 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/gasworld1.png"));

  /**
   * Big planet screen gas world
   */
  public static final BufferedImage BIG_GASWORLD2 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/gasworld2.png"));
  /**
   * Big planet screen gas world
   */
  public static final BufferedImage BIG_GASWORLD3 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/gasworld3.png"));
  /**
   * Big planet screen desert planet 1
   */
  public static final BufferedImage BIG_PLANET_DESERTWORLD1 = IOUtilities
      .loadImage(Tiles.class.getResource(
          "/resources/images/desertplanet1.png"));
  /**
   * Big planet screen desert planet 2
   */
  public static final BufferedImage BIG_PLANET_DESERTWORLD2 = IOUtilities
      .loadImage(Tiles.class.getResource(
          "/resources/images/desertplanet2.png"));
  /**
   * Big planet screen desert planet 3
   */
  public static final BufferedImage BIG_PLANET_DESERTWORLD3 = IOUtilities
      .loadImage(Tiles.class.getResource(
          "/resources/images/desertplanet3.png"));
  /**
   * Big planet screen artificial planet 1
   */
  public static final BufferedImage BIG_PLANET_ARTIFICIALPLANET1 = IOUtilities
      .loadImage(Tiles.class.getResource(
          "/resources/images/artificialworld1.png"));

  /**
   * Big sports logo
   */
  public static final BufferedImage BIG_SPORT_LOGO = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/olympics.png"));

  /**
   * Star field image for parallax scrolling
   */
  private static final BufferedImage STAR_FIELD_IMAGE = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/starfield.png"));

  /**
   * Generated star field image.
   */
  private static BufferedImage starField = null;

  /**
   * Generated star nebula image.
   */
  private static BufferedImage starNebulae = null;
  /**
   * Separate thread to generate background stars.
   */
  private static ProceduralRenderer proceduralRenderer = null;
  /**
   * Nebula image for parallax scrolling
   */
  public static final BufferedImage NEBULAE_IMAGE = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/nebulae.png"));

  /**
   * Image used for calculation text width and heights.
   */
  private static final BufferedImage TEMP_IMAGE = new BufferedImage(100, 100,
      BufferedImage.TYPE_4BYTE_ABGR);

  /**
   * Horizontal thumb
   */
  public static final BufferedImage IMAGE_SCROLL_BAR_THUMB = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/thumb.png"));

  /**
   * Vertical thumb
   */
  public static final BufferedImage IMAGE_SCROLL_BAR_THUMB2 = IOUtilities
      .loadImage(Tiles.class.getResource("/resources/images/thumb2.png"));

  /**
   * Teuthidae race selection image
   */
  public static final BufferedImage IMAGE_TEUTHIDAE_RACE = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/teuthidae_race.png"));
  /**
   * Scaurian race selection image
   */
  public static final BufferedImage IMAGE_SCAURIAN_RACE = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/scaurian_race.png"));
  /**
   * Homarian race selection image
   */
  public static final BufferedImage IMAGE_HOMARIAN_RACE = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/homarian_race.png"));
  /**
   * Mothoid race selection image
   */
  public static final BufferedImage IMAGE_MOTHOID_RACE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/mothoid_race.png"));
  /**
   * Centaur race selection image
   */
  public static final BufferedImage IMAGE_CENTAUR_RACE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/centaur_race.png"));
  /**
   * Greyan race selection image
   */
  public static final BufferedImage IMAGE_GREYAN_RACE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/greyan_race.png"));
  /**
   * Mechion race selection image
   */
  public static final BufferedImage IMAGE_MECHION_RACE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/mechion_race.png"));
  /**
   * Spork race selection image
   */
  public static final BufferedImage IMAGE_SPORK_RACE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/spork_race.png"));

  /**
   * Human race selection image
   */
  public static final BufferedImage IMAGE_HUMAN_RACE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/human_race.png"));

  /**
   * Chiraloid race selection image
   */
  public static final BufferedImage IMAGE_CHIRALOID_RACE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/chiraloid_race.png"));

  /**
   * Reborgian race selection image
   */
  public static final BufferedImage IMAGE_REBORGIAN_RACE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/reborgian_race.png"));

  /**
   * Lithorian race selection image
   */
  public static final BufferedImage IMAGE_LITHORIAN_RACE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/lithorian_race.png"));

  /**
   * Alteirian race selection image
   */
  public static final BufferedImage IMAGE_ALTEIRIAN_RACE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/alteirian_race.png"));

  /**
   * Smaugirian race selection image
   */
  public static final BufferedImage IMAGE_SMAUGIRIAN_RACE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/smaugirian_race.png"));

  /**
   * Privateer race selection image
   */
  public static final BufferedImage IMAGE_PRIVATEER_RACE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/privateer_race.png"));

  /**
   * Newsreader image for GBNC
   */
  public static final BufferedImage IMAGE_NEWSREADER = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/newsreader.png"));

  /**
   * GBNC logo
   */
  public static final BufferedImage IMAGE_GBNC = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/gbnc-logo.png"));

  /**
   * ASTEROIDS
   */
  public static final BufferedImage IMAGE_ASTEROIDS = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/asteroids.png"));

  /**
   * Pirate pilot
   */
  public static final BufferedImage IMAGE_PIRATE_PILOT = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/pirate_pilot.png"));

  /**
   * Space kraken
   */
  public static final BufferedImage IMAGE_KRAKEN = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/kraken.png"));

  /**
   * Pirate raiders
   */
  public static final BufferedImage IMAGE_PIRATE_RAIDERS =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/pirate_raiders.png"));

  /**
   * Mutiny
   */
  public static final BufferedImage IMAGE_MUTINY = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/mutiny.png"));
  /**
   * Dataloss
   */
  public static final BufferedImage IMAGE_DATALOSS = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/dataloss.png"));

  /**
   * Shuttle
   */
  public static final BufferedImage IMAGE_SHUTTLE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/shuttle.png"));

  /**
   * Blackhole pilot
   */
  public static final BufferedImage IMAGE_BLACKHOLE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/blackhole.png"));

  /**
   * Old probe
   */
  public static final BufferedImage IMAGE_OLD_PROBE = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/oldprobe.png"));

  /**
   * Rare tech
   */
  public static final BufferedImage IMAGE_RARE_TECH = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/raretech.png"));

  /**
   * Old Ship
   */
  public static final BufferedImage IMAGE_OLD_SHIP = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/oldship.png"));
  /**
   * Time Warp
   */
  public static final BufferedImage IMAGE_TIME_WARP = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/timewarp.png"));
  /**
   * Factory
   */
  public static final BufferedImage IMAGE_FACTORY = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/factory.png"));
  /**
   * Pirate lair
   */
  public static final BufferedImage IMAGE_PIRATE_LAIR = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/piratelair.png"));
  /**
   * Electron nebula
   */
  public static final BufferedImage IMAGE_DSA = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/electron nebula.png"));
  /**
   * Space ship
   */
  public static final BufferedImage IMAGE_SPACE_SHIP = IOUtilities.loadImage(
      GuiStatics.class.getResource("/resources/images/spaceship_final.png"));
  /**
   * Trade Space ship 1
   */
  public static final BufferedImage IMAGE_TRADE_SHIP = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/tradeship-learmarch.png"));
  /**
   * Trade Space ship 2
   */
  public static final BufferedImage IMAGE_TRADE_SHIP2 = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/tradeship-learmarch1.png"));
  /**
   * Cloaked ship
   */
  public static final BufferedImage IMAGE_CLOAKED_SHIP = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/cloaked_ship.png"));
  /**
   * Shuttle 2
   */
  public static final BufferedImage IMAGE_SHUTTLE2 = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/shuttle2.png"));
  /**
   * Big nuke image
   */
  public static final BufferedImage IMAGE_BIG_NUKE = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/bignuke.png"));
  /**
   * Big ban icon
   */
  public static final BufferedImage IMAGE_BIG_BAN_ICON = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/bigban.png"));
  /**
   * Big ban peace
   */
  public static final BufferedImage IMAGE_BIG_PEACE_ICON = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/bigpeace.png"));
  /**
   * Big pirate ship.
   */
  public static final BufferedImage IMAGE_PRIVATEER = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/pirateship.png"));
  /**
   * Galaxy image
   */
  public static final BufferedImage IMAGE_GALAXY = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/galaxy.png"));
  /**
   * United Galaxy Tower image
   */
  public static final BufferedImage IMAGE_UNITED_GALAXY_TOWER = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/unitedgalaxytower.png"));
  /**
   * Big missile image
   */
  public static final BufferedImage IMAGE_BIG_MISSILE = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/bigmissile.png"));
  /**
   * Big money image
   */
  public static final BufferedImage IMAGE_BIG_MONEY = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/bigmoney.png"));
  /**
   * Solar flares images
   */
  public static final BufferedImage IMAGE_SOLAR_FLARES = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/sun-flares.png"));
  /**
   * Solar no flares images
   */
  public static final BufferedImage IMAGE_SOLAR_NO_FLARES = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/sun-noflares.png"));
  /**
   * Image desert planet image
   */
  public static final BufferedImage IMAGE_DESERT = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/desert.png"));
  /**
   * Image paradise planet
   */
  public static final BufferedImage IMAGE_PARADISE = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/landscape.png"));
  /**
   * Image viruses
   */
  public static final BufferedImage IMAGE_VIRUSES = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/viruses.png"));
  /**
   * Image mysterious signal
   */
  public static final BufferedImage IMAGE_SIGNAL = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/mysterious_signal.png"));
  /**
   * Image technical breakthrough
   */
  public static final BufferedImage IMAGE_TECHNICAL_BREAKTHROUGH = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/technical_breakthrough.png"));

  /**
   * Image meteor
   */
  public static final BufferedImage IMAGE_METEOR = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/meteor.png"));
  /**
   * Image meteor hit explosion
   */
  public static final BufferedImage IMAGE_METEOR_HIT = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/meteorhit.png"));
  /**
   * Image ship destroyed
   */
  public static final BufferedImage IMAGE_SHIP_DESTROYED = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/ship_destroyed.png"));
  /**
   * Image spinosaurus
   */
  public static final BufferedImage IMAGE_SPINOSAURUS = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/spinosaurus.png"));
  /**
   * Image containers
   */
  public static final BufferedImage IMAGE_CONTAINERS = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/containers.png"));
  /**
   * Image terror strike
   */
  public static final BufferedImage IMAGE_TERROR = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/terror.png"));
  /**
   * Big Orbital.
   */
  public static final BufferedImage IMAGE_BIG_ORBITAL = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/bigorbital.png"));
  /**
   * Big Explosion
   */
  public static final BufferedImage IMAGE_BIG_EXPLOSION = IOUtilities
      .loadImage(GuiStatics.class.getResource(
          "/resources/images/bigexplosion.png"));

  /**
   * Ship bridge interior 1
   */
  public static final BufferedImage IMAGE_INTERIOR1 = IOUtilities.loadImage(
      GuiStatics.class.getResource(
          "/resources/images/bridge1.png"));
  /**
   * Ship centaur bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_CENTAUR =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/centaur_bridge.png"));
  /**
   * Ship Scaurian bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_SCAURIAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/scaurian_bridge.png"));
  /**
   * Ship Mechion bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_MECHION =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/mechion_bridge.png"));

  /**
   * Ship Human bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_HUMAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/human_bridge.png"));

  /**
   * Ship Mothoid bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_MOTHOID =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/mothoid_bridge.png"));

  /**
   * Ship Greyan bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_GREYAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/greyan_bridge.png"));

  /**
   * Ship Homarian bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_HOMARIAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/homarian_bridge.png"));

  /**
   * Ship Teuthidae bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_TEUTHIDAE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/teuthidae_bridge.png"));

  /**
   * Ship Spork bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_SPORK =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/spork_bridge.png"));
  /**
   * Ship Chiraloid bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_CHIRALOID =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/chiraloid_bridge.png"));
  /**
   * Ship Pirate bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_PIRATE =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/pirate_bridge.png"));
  /**
   * Ship Lithorian bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_LITHORIAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/lithorian_bridge.png"));

  /**
   * Ship Reborgian bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_REBORGIAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/reborgian_bridge.png"));

  /**
   * Ship Smaugirian bridge interior
   */
  public static final BufferedImage IMAGE_INTERIOR_SMAUGIRIAN =
      IOUtilities.loadImage(GuiStatics.class.getResource(
          "/resources/images/smaugirian_bridge.png"));

  /**
   * Get text width for certain font
   * @param font Font to use
   * @param text String
   * @return Text width in pixels
   */
  public static int getTextWidth(final Font font, final String text) {
    if (font != null && text != null) {
      Graphics2D g2d = TEMP_IMAGE.createGraphics();
      int width = (int) font.getStringBounds(text, g2d.getFontRenderContext())
          .getWidth();
      g2d.dispose();
      return width;
    }
    return 0;
  }

  /**
   * Get text height for certain font
   * @param font Font to use
   * @param text String
   * @return Text height in pixels
   */
  public static int getTextHeight(final Font font, final String text) {
    if (font != null && text != null) {
      Graphics2D g2d = TEMP_IMAGE.createGraphics();
      int height = (int) font.getStringBounds(text, g2d.getFontRenderContext())
          .getHeight();
      g2d.dispose();
      return height;
    }
    return 0;
  }

  /**
   * Scale Image to half size of the original size
   * @param source Buffered image to scale
   * @return Scaled buffered image
   */
  public static BufferedImage scaleToHalf(final BufferedImage source) {
    int halfWidth = source.getWidth() / 2;
    int halfHeight = source.getHeight() / 2;
    int origWidth = source.getWidth();
    int origHeight = source.getHeight();
    BufferedImage target = new BufferedImage(halfWidth, halfHeight,
        BufferedImage.TYPE_4BYTE_ABGR);
    if (source.getHeight() == origHeight && source.getWidth() == origWidth) {
      int mx = origWidth / halfWidth;
      int my = origHeight / halfHeight;
      for (int y = 0; y < halfHeight; y++) {
        for (int x = 0; x < halfWidth; x++) {
          int color = source.getRGB(x * mx, y * my);
          target.setRGB(x, y, color);
        }
      }
    }
    return target;
  }

  /**
   * Is larger fonts in use?
   * @return True if larger fonts are in use
   */
  public static boolean isLargerFonts() {
    return useLargerFonts;
  }

  /**
   * Set use of larger fonts
   * @param largerFonts the useLargerFonts to set
   */
  public static void setLargerFonts(final boolean largerFonts) {
    GuiStatics.useLargerFonts = largerFonts;
  }

  /**
   * Get star field image and generate if not available.
   * @return BufferedImage
   */
  public static BufferedImage getStarField() {
    if (starField == null) {
      if (!Game.isMainMethodCalled()) {
        return STAR_FIELD_IMAGE;
      }
      if (proceduralRenderer == null) {
        proceduralRenderer = new ProceduralRenderer();
        proceduralRenderer.start();
      }
      starField = proceduralRenderer.getStars();
      if (starField == null) {
        return STAR_FIELD_IMAGE;
      }
    }
    return starField;
  }

  /**
   * Generate both stars and nebulae in same pictue.
   * @return Buffered Image.
   */
  public static BufferedImage getStarNebulae() {
    if (starNebulae == null) {
      starNebulae = new BufferedImage(2100, 1600,
          BufferedImage.TYPE_4BYTE_ABGR);
      Graphics graphics = starNebulae.getGraphics();
      graphics.drawImage(getStarField(), 0, 0, null);
      graphics.drawImage(NEBULAE_IMAGE, 0, 0, null);
    }
    return starNebulae;
  }
}
