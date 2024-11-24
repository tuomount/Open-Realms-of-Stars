package org.openRealmOfStars.gui.scheme;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
 */

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
*
* Grey Scheme for UI.
*
*/
public class YellowScheme extends BaseScheme {

  /**
   * Panel background
   */
  private static final Color COLOR_SPACE_GREY = new Color(76, 76, 76,
      255);

  /**
   * Cool space color
   */
  public static final Color COLOR_COOL_SPACE_YELLOW = new Color(247, 233, 0);
  /**
   * Cool space color, opacity 128
   */
  public static final Color COLOR_COOL_SPACE_YELLOW_TRANS = new Color(247, 233,
      0, 128);
  /**
   * Cool space color dark
   */
  public static final Color COLOR_COOL_SPACE_ORANGE = new Color(254, 194,
      0);
  /**
   * Cool space color darker
   */
  public static final Color COLOR_COOL_SPACE_ORANGE_DARK = new Color(255, 154,
      0);
  /**
   * Cool space color darker transparent
   */
  public static final Color COLOR_COOL_SPACE_ORANGE_DARK_TRANSPARENT
      = new Color(255, 154, 0, 128);
  /**
   * Green text
   */
  public static final Color COLOR_GREEN_TEXT = new Color(163, 255, 0, 255);
  /**
   * Green text dark one
   */
  public static final Color COLOR_GREEN_TEXT_DARK = new Color(191, 89, 7, 255);

  /**
   * Deep space gray
   */
  public static final Color COLOR_DEEP_SPACE_GRAY = new Color(44, 44, 44);

  /**
   * Deep space gray dark
   */
  public static final Color COLOR_DEEP_SPACE_GRAY_DARK = new Color(22, 22, 22);

  /**
   * Deep Space Blue
   */
  public static final Color COLOR_DEEP_SPACE_ACT = new Color(218, 141, 14);

  /** Scifi border text */
  public static final Color COLOR_SCIFI_BORDER_TEXT = new Color(222, 188, 0,
      230);

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
   * Image for horizontal thumb for scroll bar
   */
  private BufferedImage horizontalThumb;
  /**
   * Image for vertical thumb for scroll bar
   */
  private BufferedImage verticalThumb;

  /**
   * Initialize Grey Scheme.
   */
  public YellowScheme() {
    smallScrollUp = Icons.loadSmallIcon("/resources/images/yellow_arrows.png",
        0, 0, Icons.ICON_SCROLL_UP);
    smallScrollUpPressed = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 0, 1,
        Icons.ICON_SCROLL_UP_PRESSED);
    smallScrollDown = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 1, 0, Icons.ICON_SCROLL_DOWN);
    smallScrollDownPressed = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 1, 1,
        Icons.ICON_SCROLL_DOWN_PRESSED);
    smallScrollLeft = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 2, 0, Icons.ICON_SCROLL_LEFT);
    smallScrollLeftPressed = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 2, 1,
        Icons.ICON_SCROLL_LEFT_PRESSED);
    smallScrollRight = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 3, 0, Icons.ICON_SCROLL_RIGHT);
    smallScrollRightPressed = Icons.loadSmallIcon(
        "/resources/images/yellow_arrows.png", 3, 1,
        Icons.ICON_SCROLL_RIGHT_PRESSED);
    scrollLeft = IOUtilities
        .loadImage("/resources/images/yellow_left_arrow.png");
    scrollLeftPressed = IOUtilities.loadImage(
        "/resources/images/yellow_left_arrow_pressed.png");
    scrollRight = IOUtilities
        .loadImage("/resources/images/yellow_right_arrow.png");
    scrollRightPressed = IOUtilities.loadImage(
        "/resources/images/yellow_right_arrow_pressed.png");
    horizontalThumb = IOUtilities.loadImage(
        "/resources/images/yellow_thumb.png");
    verticalThumb = IOUtilities.loadImage(
        "/resources/images/yellow_thumb2.png");
  }
  @Override
  public Color getPanelBackground() {
    return COLOR_SPACE_GREY;
  }

  @Override
  public SchemeType getType() {
    return SchemeType.DANGEROUS_YELLOW;
  }

  @Override
  public Color getCoolSpaceColor() {
    return COLOR_COOL_SPACE_YELLOW;
  }

  @Override
  public Color getCoolSpaceColorTransparent() {
    return COLOR_COOL_SPACE_YELLOW_TRANS;
  }

  @Override
  public Color getInfoTextColor() {
    return COLOR_GREEN_TEXT;
  }

  @Override
  public Color getInfoTextColorDark() {
    return COLOR_GREEN_TEXT_DARK;
  }

  @Override
  public Color getCoolSpaceColorDark() {
    return COLOR_COOL_SPACE_ORANGE;
  }

  @Override
  public Color getCoolSpaceColorDarker() {
    return COLOR_COOL_SPACE_ORANGE_DARK;
  }

  @Override
  public Color getDeepSpaceColor() {
    return COLOR_DEEP_SPACE_GRAY;
  }
  @Override
  public Color getDeepSpaceDarkColor() {
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
    return COLOR_COOL_SPACE_ORANGE_DARK_TRANSPARENT;
  }
  @Override
  public Color getDeepSpaceActivityColor() {
    return COLOR_DEEP_SPACE_ACT;
  }
  @Override
  public Color getScifiBorderTextColor() {
    return COLOR_SCIFI_BORDER_TEXT;
  }

  @Override
  public BufferedImage getHorizontalThumb() {
    return horizontalThumb;
  }

  @Override
  public BufferedImage getVerticalThumb() {
    return verticalThumb;
  }

}
