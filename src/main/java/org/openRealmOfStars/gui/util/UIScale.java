package org.openRealmOfStars.gui.util;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;

/**
 * UI Scaling utility class for high-DPI display support.
 * Provides centralized scaling calculations based on screen resolution.
 *
 * Base resolution is 768 pixels height (from minimum 1024x768).
 * All pixel values should be scaled relative to this baseline.
 */
public final class UIScale {

  /** Base height resolution for scaling calculations. */
  private static final int BASE_HEIGHT = 768;

  /** Base width resolution for scaling calculations. */
  private static final int BASE_WIDTH = 1024;

  /** Minimum scale factor to prevent UI from becoming too small. */
  private static final double MIN_SCALE = 1.0;

  /** Maximum scale factor to prevent UI from becoming too large. */
  private static final double MAX_SCALE = 4.0;

  /** Resolution height threshold for 1080p. */
  private static final int RES_1080P = 1080;

  /** Resolution height threshold for 1440p. */
  private static final int RES_1440P = 1440;

  /** Resolution height threshold for 4K (2160p). */
  private static final int RES_4K = 2160;

  /** Resolution tier for 4K displays. */
  private static final int TIER_4K = 3;

  /** Resolution tier for 1440p displays. */
  private static final int TIER_1440P = 2;

  /** Current scale factor based on resolution. */
  private static double scaleFactor = 1.0;

  /** Current screen width. */
  private static int screenWidth = BASE_WIDTH;

  /** Current screen height. */
  private static int screenHeight = BASE_HEIGHT;

  /** Hiding the constructor for utility class. */
  private UIScale() {
    // Nothing to do
  }

  /**
   * Initialize the scaling system with the current screen dimensions.
   * Should be called when the game starts or resolution changes.
   * @param width Screen width in pixels
   * @param height Screen height in pixels
   */
  public static void initialize(final int width, final int height) {
    screenWidth = width;
    screenHeight = height;
    double newScale = (double) height / BASE_HEIGHT;
    scaleFactor = Math.max(MIN_SCALE, Math.min(MAX_SCALE, newScale));
  }

  /**
   * Get the current scale factor.
   * @return Scale factor (1.0 = base resolution, 2.0 = double, etc.)
   */
  public static double getScaleFactor() {
    return scaleFactor;
  }

  /**
   * Get the current screen width.
   * @return Screen width in pixels
   */
  public static int getScreenWidth() {
    return screenWidth;
  }

  /**
   * Get the current screen height.
   * @return Screen height in pixels
   */
  public static int getScreenHeight() {
    return screenHeight;
  }

  /**
   * Scale an integer pixel value based on current scale factor.
   * @param baseValue Base pixel value at 768p resolution
   * @return Scaled pixel value
   */
  public static int scale(final int baseValue) {
    return (int) Math.round(baseValue * scaleFactor);
  }

  /**
   * Scale a double pixel value based on current scale factor.
   * @param baseValue Base pixel value at 768p resolution
   * @return Scaled pixel value
   */
  public static double scaleDouble(final double baseValue) {
    return baseValue * scaleFactor;
  }

  /**
   * Scale a font size based on current scale factor.
   * @param baseFontSize Base font size at 768p resolution
   * @return Scaled font size
   */
  public static float scaleFont(final float baseFontSize) {
    return (float) (baseFontSize * scaleFactor);
  }

  /**
   * Create a scaled Dimension from base pixel values.
   * @param baseWidth Base width at 768p resolution
   * @param baseHeight Base height at 768p resolution
   * @return Scaled Dimension
   */
  public static Dimension scaledDimension(final int baseWidth,
      final int baseHeight) {
    return new Dimension(scale(baseWidth), scale(baseHeight));
  }

  /**
   * Create a scaled rigid area Component for use with BoxLayout.
   * Replacement for Box.createRigidArea(new Dimension(w, h)).
   * @param baseWidth Base width at 768p resolution
   * @param baseHeight Base height at 768p resolution
   * @return Scaled rigid area Component
   */
  public static Component scaledRigidArea(final int baseWidth,
      final int baseHeight) {
    return Box.createRigidArea(scaledDimension(baseWidth, baseHeight));
  }

  /**
   * Check if the current resolution is considered high-DPI.
   * @return true if height is greater than 1080 pixels
   */
  public static boolean isHighDpi() {
    return screenHeight > RES_1080P;
  }

  /**
   * Check if the current resolution is 4K or higher.
   * @return true if height is 2160 pixels or more
   */
  public static boolean is4K() {
    return screenHeight >= RES_4K;
  }

  /**
   * Get a resolution-tier identifier for breakpoint-based scaling.
   * Useful for assets that have discrete size variants.
   * @return Resolution tier: 0=768p, 1=1080p, 2=1440p, 3=2160p+
   */
  public static int getResolutionTier() {
    if (screenHeight >= RES_4K) {
      return TIER_4K;
    } else if (screenHeight >= RES_1440P) {
      return TIER_1440P;
    } else if (screenHeight >= RES_1080P) {
      return 1;
    }
    return 0;
  }

  /**
   * Scale a value only if above a certain resolution threshold.
   * Useful for elements that should remain fixed at lower resolutions.
   * @param baseValue Base pixel value
   * @param minHeight Minimum height before scaling kicks in
   * @return Scaled or original value depending on resolution
   */
  public static int scaleIfAbove(final int baseValue, final int minHeight) {
    if (screenHeight > minHeight) {
      double partialScale = (double) screenHeight / minHeight;
      return (int) Math.round(baseValue * partialScale);
    }
    return baseValue;
  }

  /**
   * Convert a percentage of screen width to pixels.
   * @param percentage Percentage of screen width (0.0 to 1.0)
   * @return Pixel value
   */
  public static int percentWidth(final double percentage) {
    return (int) Math.round(screenWidth * percentage);
  }

  /**
   * Convert a percentage of screen height to pixels.
   * @param percentage Percentage of screen height (0.0 to 1.0)
   * @return Pixel value
   */
  public static int percentHeight(final double percentage) {
    return (int) Math.round(screenHeight * percentage);
  }
}
