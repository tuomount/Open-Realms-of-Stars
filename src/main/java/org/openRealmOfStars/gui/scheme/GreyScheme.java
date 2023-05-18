package org.openRealmOfStars.gui.scheme;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
* Grey Scheme for UI.
*
*/
public class GreyScheme extends BaseScheme {

  /**
   * Panel background
   */
  private static final Color COLOR_SPACE_GREY = new Color(81, 87, 109,
      255);

  /**
   * Cool space color
   */
  public static final Color COLOR_COOL_SPACE_BLUE = new Color(160, 160, 185);
  /**
   * Cool space color, opacity 128
   */
  public static final Color COLOR_COOL_SPACE_BLUE_TRANS = new Color(90, 210,
      215, 128);
  /**
   * Cool space color dark
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARK = new Color(120, 120,
      145);
  /**
   * Cool space color darker
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARKER = new Color(80, 80,
      115);
  /**
   * Cool space color darker transparent
   */
  public static final Color COLOR_COOL_SPACE_BLUE_DARKER_TRANSPARENT
      = new Color(80, 80, 115, 128);
  /**
   * Green text dark one
   */
  public static final Color COLOR_GREEN_TEXT_DARK = new Color(2, 102, 0, 255);

  /**
   * Deep space gray
   */
  public static final Color COLOR_DEEP_SPACE_GRAY = new Color(46, 36, 44);

  /**
   * Deep space gray dark
   */
  public static final Color COLOR_DEEP_SPACE_GRAY_DARK = new Color(38, 38, 65);

  /**
   * Deep Space Blue
   */
  public static final Color COLOR_DEEP_SPACE_ACT = new Color(43, 43, 60);

  /**
   * Icon for small scroll up.
   */
  private Icon16x16 smallScrollUp;
  /**
   * Image for small scroll up pressed.
   */
  private Icon16x16 smallScrollUpPressed;
  /**
   * Image for small scroll down.
   */
  private Icon16x16 smallScrollDown;
  /**
   * Image for small scroll down pressed.
   */
  private Icon16x16 smallScrollDownPressed;
  /**
   * Image for small scroll left.
   */
  private Icon16x16 smallScrollLeft;
  /**
   * Image for small scroll left pressed.
   */
  private Icon16x16 smallScrollLeftPressed;
  /**
   * Image for small scroll right.
   */
  private Icon16x16 smallScrollRight;
  /**
   * Image for small scroll right pressed.
   */
  private Icon16x16 smallScrollRightPressed;
  /**
   * Image for small scroll left.
   */
  private BufferedImage scrollLeft;
  /**
   * Image for small scroll left pressed.
   */
  private BufferedImage scrollLeftPressed;
  /**
   * Image for small scroll right.
   */
  private BufferedImage scrollRight;
  /**
   * Image for small scroll right pressed.
   */
  private BufferedImage scrollRightPressed;

  /**
   * Initialize Grey Scheme.
   */
  public GreyScheme() {
    smallScrollUp = Icons.loadSmallIcon("/resources/images/grey_arrows.png",
        0, 0, Icons.ICON_SCROLL_UP);
    smallScrollUpPressed = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 0, 1,
        Icons.ICON_SCROLL_UP_PRESSED);
    smallScrollDown = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 1, 0, Icons.ICON_SCROLL_DOWN);
    smallScrollDownPressed = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 1, 1,
        Icons.ICON_SCROLL_DOWN_PRESSED);
    smallScrollLeft = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 1, 0, Icons.ICON_SCROLL_LEFT);
    smallScrollLeftPressed = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 1, 1,
        Icons.ICON_SCROLL_LEFT_PRESSED);
    smallScrollRight = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 1, 0, Icons.ICON_SCROLL_RIGHT);
    smallScrollRightPressed = Icons.loadSmallIcon(
        "/resources/images/grey_arrows.png", 1, 1,
        Icons.ICON_SCROLL_RIGHT_PRESSED);
    scrollLeft = IOUtilities
        .loadImage(Tiles.class.getResource(
            "/resources/images/grey_left_arrow.png"));
    scrollLeftPressed = IOUtilities.loadImage(Tiles.class.getResource(
        "/resources/images/grey_left_arrow_pressed.png"));
    scrollRight = IOUtilities
        .loadImage(Tiles.class.getResource(
            "/resources/images/grey_right_arrow.png"));
    scrollRightPressed = IOUtilities.loadImage(Tiles.class.getResource(
        "/resources/images/grey_right_arrow_pressed.png"));
  }
  @Override
  public Color getPanelBackground() {
    return COLOR_SPACE_GREY;
  }

  @Override
  public SchemeType getType() {
    return SchemeType.SPACE_GREY;
  }

  @Override
  public Color getCoolSpaceColor() {
    return COLOR_COOL_SPACE_BLUE;
  }

  @Override
  public Color getCoolSpaceColorTransparent() {
    return COLOR_COOL_SPACE_BLUE_TRANS;
  }

  @Override
  public Color getInfoTextColor() {
    return COLOR_GREEN_TEXT_DARK;
  }

  @Override
  public Color getCoolSpaceColorDark() {
    return COLOR_COOL_SPACE_BLUE_DARK;
  }

  @Override
  public Color getCoolSpaceColorDarker() {
    return COLOR_COOL_SPACE_BLUE_DARKER;
  }

  @Override
  public Color getDeepSpaceColor() {
    return COLOR_DEEP_SPACE_GRAY;
  }
  @Override
  public Color getDeepSpaceDarkerColor() {
    return COLOR_DEEP_SPACE_GRAY_DARK;
  }
  @Override
  public Icon16x16 getSmallArrowIcon(final String name) {
    if (name.equals(Icons.ICON_SCROLL_UP)) {
      return smallScrollUp;
    }
    if (name.equals(Icons.ICON_SCROLL_UP_PRESSED)) {
      return smallScrollUpPressed;
    }
    if (name.equals(Icons.ICON_SCROLL_DOWN)) {
      return smallScrollDown;
    }
    if (name.equals(Icons.ICON_SCROLL_DOWN_PRESSED)) {
      return smallScrollDownPressed;
    }
    if (name.equals(Icons.ICON_SCROLL_LEFT)) {
      return smallScrollLeft;
    }
    if (name.equals(Icons.ICON_SCROLL_LEFT_PRESSED)) {
      return smallScrollLeftPressed;
    }
    if (name.equals(Icons.ICON_SCROLL_RIGHT)) {
      return smallScrollRight;
    }
    if (name.equals(Icons.ICON_SCROLL_RIGHT_PRESSED)) {
      return smallScrollRightPressed;
    }
    return smallScrollUp;
  }
  @Override
  public BufferedImage getArrowLeft() {
    return scrollLeft;
  }
  @Override
  public BufferedImage getArrowLeftPressed() {
    return scrollLeftPressed;
  }
  @Override
  public BufferedImage getArrowRight() {
    return scrollRight;
  }
  @Override
  public BufferedImage getArrowRightPressed() {
    return scrollRightPressed;
  }
  @Override
  public Color getCoolSpaceColorDarkerTransparent() {
    return COLOR_COOL_SPACE_BLUE_DARKER_TRANSPARENT;
  }
  @Override
  public Color getDeepSpaceActivityColor() {
    return COLOR_DEEP_SPACE_ACT;
  }
}
