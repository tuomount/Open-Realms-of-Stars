package org.openRealmOfStars.gui.scheme;
/*
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
 */

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.icons.Icon16x16;

/**
*
* Base scheme which all scheme extend.
*
*/
public abstract class BaseScheme {

  /**
   * Get Panel background color. This is used almost every panel.
   * @return Color
   */
  public abstract Color getPanelBackground();

  /**
   * Get cool space color.
   * @return Color
   */
  public abstract Color getCoolSpaceColor();

  /**
   * Get cool space color dark.
   * @return Color
   */
  public abstract Color getCoolSpaceColorDark();

  /**
   * Get cool space color darker.
   * @return Color
   */
  public abstract Color getCoolSpaceColorDarker();
  /**
   * Get cool space color darker transparent.
   * @return Color
   */
  public abstract Color getCoolSpaceColorDarkerTransparent();

  /**
   * Get cool space color transparent.
   * @return Color
   */
  public abstract Color getCoolSpaceColorTransparent();

  /**
   * Get deep space color.
   * @return Color
   */
  public abstract Color getDeepSpaceColor();

  /**
   * Get deep space dark color.
   * @return Color
   */
  public abstract Color getDeepSpaceDarkColor();

  /**
   * Get deep space activity color.
   * @return Color
   */
  public abstract Color getDeepSpaceActivityColor();

  /**
   * Get activation color.
   * @return Color
   */
  public abstract Color getActivationColor();

  /**
   * Get scifi border text color
   * @return Color
   */
  public abstract Color getScifiBorderTextColor();

  /**
   * Get Info text color.
   * @return Color
   */
  public abstract Color getInfoTextColorDark();
  /**
   * Get Info text color.
   * @return Color
   */
  public abstract Color getInfoTextColor();
  /**
   * Get Scheme type
   * @return Scheme Type
   */
  public abstract SchemeType getType();

  /**
   * Get small arrow icon based on icon name.
   * @param name Icon name
   * @return Icon16x16
   */
  public abstract Icon16x16 getSmallArrowIcon(String name);

  /**
   * Get Arrow left as buffered image.
   * @return BufferedImage
   */
  public abstract BufferedImage getArrowLeft();
  /**
   * Get Arrow left pressed as buffered image.
   * @return BufferedImage
   */
  public abstract BufferedImage getArrowLeftPressed();
  /**
   * Get Arrow right as buffered image.
   * @return BufferedImage
   */
  public abstract BufferedImage getArrowRight();
  /**
   * Get Arrow right pressed as buffered image.
   * @return BufferedImage
   */
  public abstract BufferedImage getArrowRightPressed();
  /**
   * Get Horizontal thumb for scroll bar.
   * @return BufferedImage
   */
  public abstract BufferedImage getHorizontalThumb();
  /**
   * Get Vertical thumb for scroll bar.
   * @return BufferedImage
   */
  public abstract BufferedImage getVerticalThumb();

  /**
   * Get Selection activity color.
   * @return Color
   */
  public abstract Color getSeclectionActivityColor();

  /**
   * Get Color for checkbox enabled
   * @return Color
   */
  public abstract Color getCheckBoxEnabledColor();

  /**
   * Get Color for checkbox disabled
   * @return Color
   */
  public abstract Color getCheckBoxDisabledColor();

  /**
   * Get warning color active.
   * @return Color
   */
  public abstract Color getWarningColorActive();
  /**
   * Get warning color shadow.
   * @return Color
   */
  public abstract Color getWarningColorShadow();

}
