package org.openRealmOfStars.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.gui.icons.AnimatedImage;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.utilities.IOUtilities;


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
 * Static GUI component like fonts and colors.
 * 
 */
public class GuiStatics {
  /**
   *  Monospace font size 10
   */
  public final static Font FONT_SMALL = new Font("monospaced",Font.PLAIN,10);
  /**
   *  Monospace font size 12
   */
  public final static Font FONT_NORMAL = new Font("monospaced",Font.BOLD,12);
  
  /**
   * Regular cubellan font
   */
  private static Font fontCubellan;

  /**
   * Regular cubellan font but smaller
   */
  private static Font fontCubellanSmall;

  /**
   * Get Regular Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellan() {
    if (fontCubellan == null) { 
      try (InputStream is = Tiles.class.getResource(
          "/resources/fonts/Cubellan_v_0_7/Cubellan.ttf").openStream()){        
        fontCubellan = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellan = fontCubellan.deriveFont(16F);
      } catch (IOException | FontFormatException e) {
        System.err.println("Error:"+e.getMessage());
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
    if (fontCubellanSmall == null) { 
      try (InputStream is = Tiles.class.getResource(
          "/resources/fonts/Cubellan_v_0_7/Cubellan.ttf").openStream()) {        
        fontCubellanSmall = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanSmall = fontCubellanSmall.deriveFont(13F);
      } catch (IOException | FontFormatException e) {
        System.err.println("Error:"+e.getMessage());
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
   * Get bold Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellanBold() {
    if (fontCubellanBold == null) { 
      try (InputStream is = Tiles.class.getResource(
          "/resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf").openStream()){
        fontCubellanBold = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanBold = fontCubellanBold.deriveFont(24F);
      } catch (IOException | FontFormatException e) {
        System.err.println("Error:"+e.getMessage());
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
   * Get bold Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellanBoldBig() {
    if (fontCubellanBoldBig == null) { 
      try (InputStream is = Tiles.class.getResource(
          "/resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf").openStream()) {
        fontCubellanBoldBig = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanBoldBig = fontCubellanBoldBig.deriveFont(35F);
      } catch (IOException | FontFormatException e) {
        System.err.println("Error:"+e.getMessage());
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
   * Get Cubellan font with Small Caps
   * @return Cubellan font
   */
  public static Font getFontCubellanSC() {
    if (fontCubellanSC == null) { 
      try (InputStream is = Tiles.class.getResource(
            "/resources/fonts/Cubellan_v_0_7/Cubellan_SC.ttf").openStream()) {
        fontCubellanSC = Font.createFont(Font.TRUETYPE_FONT, is);
        fontCubellanSC = fontCubellanSC.deriveFont(13F);
      } catch (IOException | FontFormatException e) {
        System.err.println("Error:"+e.getMessage());
        return FONT_SMALL;
      }
    }
    return fontCubellanSC;
  }

  /**
   * Line type for text background
   */
  public final static Stroke TEXT_LINE = new BasicStroke(12, 
      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[]{1f}, 0);

  /**
   * Sun Line type for text background, opacity 230.
   */
  public final static Color COLOR_GOLD_TRANS = new Color(210, 181, 44, 230);

  /**
   * Gold color 
   */
  public final static Color COLOR_GOLD = new Color(210, 181, 44);

  /**
   * Space yellow
   */
  public final static Color COLOR_SPACE_YELLOW = new Color(251, 255, 51);

  /**
   * Dark space yellow
   */
  public final static Color COLOR_SPACE_YELLOW_DARK = new Color(134, 134, 33);

  /**
   * Dark Gold color 
   */
  public final static Color COLOR_GOLD_DARK = new Color(155, 130, 13);

  /**
   * Planet Line type for text background, opacity 65.
   */
  public final static Color COLOR_GREYBLUE = new Color(180, 180, 200, 65);

  /**
   * Panel background
   */
  public final static Color COLOR_SPACE_GREY_BLUE = new Color(81, 87, 133,255);

  /**
   * Green text dark one
   */
  public final static Color COLOR_GREEN_TEXT_DARK = new Color(2, 102, 0,255);

  /**
   * Green text 
   */
  public final static Color COLOR_GREEN_TEXT = new Color(4, 186, 0,255);

  /**
   * Grey text dark one
   */
  public final static Color COLOR_GREY_TEXT_DARK = new Color(90, 95, 90);

  /**
   * Grey text 
   */
  public final static Color COLOR_GREY_TEXT = new Color(160, 165, 160);

  /**
   * Green text 
   */
  public final static Color COLOR_RED_TEXT = new Color(186, 4, 0,255);

  /**
   * Green text 
   */
  public final static Color COLOR_YELLOW_TEXT = new Color(220, 220, 4,255);

  /**
   * Damage 3/4 
   */
  public final static Color COLOR_DAMAGE_LITTLE = new Color(177, 255, 11,255);

  /**
   * Damage half 
   */
  public final static Color COLOR_DAMAGE_HALF = new Color(252, 255, 11,255);

  /**
   * Damage MUCH 
   */
  public final static Color COLOR_DAMAGE_MUCH = new Color(255, 189, 11,255);

  /**
   * Damage almost destroyed 
   */
  public final static Color COLOR_DAMAGE_ALMOST_DESTROYED = new Color(255, 143, 11,255);

  /**
   * Damage destroyed 
   */
  public final static Color COLOR_DESTROYED = new Color(255, 28, 11,255);

  /**
   * Cool space blue
   */
  public final static Color COLOR_COOL_SPACE_BLUE = new Color(88,210,255);

  /**
   * Cool space blue dark
   */
  public final static Color COLOR_COOL_SPACE_BLUE_DARK = new Color(25,120,193);

  /**
   * Deep space purple dark
   */
  public final static Color COLOR_DEEP_SPACE_PURPLE_DARK = new Color(25,9,61);

  /**
   * Deep space purple
   */
  public final static Color COLOR_DEEP_SPACE_PURPLE = new Color(47,27,92);

  /**
   * Cool space blue darker
   */
  public final static Color COLOR_COOL_SPACE_BLUE_DARKER = new Color(20,110,180);

  /**
   * Cool space blue, opacity 128
   */
  public final static Color COLOR_COOL_SPACE_BLUE_TRANS = new Color(88,210,255,128);

  /**
   * Cool space blue dark, opacity 128
   */
  public final static Color COLOR_COOL_SPACE_BLUE_DARK_TRANS = new Color(25,120,193,128);


  /**
   * Deep Space Blue
   */
  public final static Color COLOR_DEEP_SPACE_BLUE = new Color(33,33,208);

  /**
   * Grey 160
   */
  public final static Color COLOR_GREY_160 = new Color(160,160,160);

  /**
   * Grey 80
   */
  public final static Color COLOR_GREY_80 = new Color(80,80,80);

  /**
   * Grey 40
   */
  public final static Color COLOR_GREY_40 = new Color(40,40,40);

  
  private final static Color EXPLOSION_COLOR_1 = new Color(255,196,18);
  private final static Color EXPLOSION_COLOR_2 = new Color(244,101,14);
  private final static Color EXPLOSION_COLOR_3 = new Color(255,218,72);
  private final static Color EXPLOSION_COLOR_4 = new Color(241,223,17);
  private final static Color EXPLOSION_COLOR_5 = new Color(255,133,13);
  
  /**
   * Explosion color
   */
  public final static Color[] EXPLOSION_COLORS =  {EXPLOSION_COLOR_1,
      EXPLOSION_COLOR_2,EXPLOSION_COLOR_3,EXPLOSION_COLOR_4,EXPLOSION_COLOR_5};
  
  private final static Color BEAM_COLOR_1 = new Color(255,36,0);
  private final static Color BEAM_COLOR_2 = new Color(255,96,0);
  private final static Color BEAM_COLOR_3 = new Color(255,128,0);
  private final static Color BEAM_COLOR_4 = new Color(255,18,0);
  private final static Color BEAM_COLOR_5 = new Color(255,115,77);
  
  /**
   * Beam color
   */
  public final static Color[] BEAM_COLORS =  {BEAM_COLOR_1,BEAM_COLOR_2,
      BEAM_COLOR_3,BEAM_COLOR_4,BEAM_COLOR_5,};


  /**
   * Photon torpedo
   */
  public final static BufferedImage PHOTON_TORPEDO = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/photon_torp.png"));

  /**
   * Explosion animation
   */
  public final static AnimatedImage EXPLOSION1 = new AnimatedImage(64, 64, 
      "/resources/images/explosion1.png");

  /**
   * Blue explosion animation
   */
  public final static AnimatedImage EXPLOSION2 = new AnimatedImage(64, 64, 
      "/resources/images/explosion2.png");

  /**
   * Small explosion animation
   */
  public final static AnimatedImage EXPLOSION3 = new AnimatedImage(32, 32, 
      "/resources/images/explosion3.png");

  /**
   * Nuke animation
   */
  public final static AnimatedImage EXPLOSION4 = new AnimatedImage(64, 64, 
      "/resources/images/explosion4.png");

  /**
   * Left arrow
   */
  public final static BufferedImage LEFT_ARROW = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/left_arrow.png"));

  /**
   * Left arrow pressed
   */
  public final static BufferedImage LEFT_ARROW_PRESSED = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/left_arrow_pressed.png"));

  /**
   * Right arrow
   */
  public final static BufferedImage RIGHT_ARROW = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/right_arrow.png"));

  /**
   * Right arrow pressed
   */
  public final static BufferedImage RIGHT_ARROW_PRESSED = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/right_arrow_pressed.png"));

  /**
   * Crosshair for combat
   */
  public final static BufferedImage CROSSHAIR = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/crosshair.png"));

  /**
   * Red Crosshair for combat
   */
  public final static BufferedImage RED_CROSSHAIR = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/red_crosshair.png"));

  
  /**
   * Big planet screen Rock 1
   */
  public final static BufferedImage BIG_PLANET_ROCK1 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/rock1.png"));

  /**
   * Big planet screen water world 1
   */
  public final static BufferedImage BIG_PLANET_WATERWORLD1 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/waterworld1.png"));

  /**
   * Big planet screen water world 2
   */
  public final static BufferedImage BIG_PLANET_WATERWORLD2 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/waterworld2.png"));

  /**
   * Big planet screen iron planet 1
   */
  public final static BufferedImage BIG_PLANET_IRONPLANET1 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/ironplanet1.png"));

  /**
   * Big planet screen iron planet 2
   */
  public final static BufferedImage BIG_PLANET_IRONPLANET2 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/ironplanet2.png"));

  /**
   * Big planet gas world
   */
  public final static BufferedImage BIG_GASWORLD1 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/gasworld1.png"));

  /**
   * Big planet screen gas world
   */
  public final static BufferedImage BIG_GASWORLD2 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/gasworld2.png"));

  /**
   * Star field image for parallax scrolling
   */
  public final static BufferedImage starFieldImage = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/starfield.png"));

  /**
   * Nebula image for parallax scrolling
   */
  public final static BufferedImage nebulaeImage = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/nebulae.png"));


  /**
   * Image used for calculation text width and heights.
   */
  private static final BufferedImage tempImage = new BufferedImage(100,100,
      BufferedImage.TYPE_4BYTE_ABGR);
  
  /**
   * Horizontal thumb
   */
  public final static BufferedImage IMAGE_SCROLL_BAR_THUMB = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/thumb.png"));

  /**
   * Vertical thumb
   */
  public final static BufferedImage IMAGE_SCROLL_BAR_THUMB2 = IOUtilities.
      loadImage(Tiles.class.getResource("/resources/images/thumb2.png"));

  
  /**
   * Centaur race selection image
   */
  public final static BufferedImage IMAGE_CENTAUR_RACE = IOUtilities.loadImage(
  GuiStatics.class.getResource("/resources/images/centaur_race.png"));
  /**
   * Greyan race selection image
   */
  public final static BufferedImage IMAGE_GREYAN_RACE = IOUtilities.loadImage(
  GuiStatics.class.getResource("/resources/images/greyan_race.png"));
  /**
   * Mechion race selection image
   */
  public final static BufferedImage IMAGE_MECHION_RACE = IOUtilities.loadImage(
  GuiStatics.class.getResource("/resources/images/mechion_race.png"));
  /**
   * Spork race selection image
   */
  public final static BufferedImage IMAGE_SPORK_RACE = IOUtilities.loadImage(
  GuiStatics.class.getResource("/resources/images/spork_race.png"));

  /**
   * Human race selection image
   */
  public final static BufferedImage IMAGE_HUMAN_RACE = IOUtilities.loadImage(
  GuiStatics.class.getResource("/resources/images/human_race.png"));

  /**
   * Get text width for certain font
   * @param font Font to use
   * @param text String
   * @return Text width in pixels
   */
  public static int getTextWidth(Font font, String text) {
    if (font != null && text != null) {
      Graphics2D g2d = tempImage.createGraphics();
      return (int) font.getStringBounds(text, g2d.getFontRenderContext()).getWidth();
    }
    return 0;
  }

  /**
   * Get text height for certain font
   * @param font Font to use
   * @param text String
   * @return Text height in pixels
   */
  public static int getTextHeight(Font font, String text) {
    if (font != null && text != null) {
      Graphics2D g2d = tempImage.createGraphics();
      return (int) font.getStringBounds(text, g2d.getFontRenderContext()).getHeight();
    }
    return 0;
  }

}
