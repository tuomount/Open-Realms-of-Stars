package org.openRealmOfStars.gui.util;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023-2024 BottledByte
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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import org.openRealmOfStars.utilities.DataSources;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 * Manages loading, caching and scaling of GUI fonts.
 */
public final class GuiFonts {

  /** Monospace font size 10. Used as a fallback. */
  public static final Font FONT_SMALL = new Font("monospaced", Font.PLAIN, 10);
  /** Monospace font size 12. Used as a fallback.  */
  public static final Font FONT_NORMAL = new Font("monospaced", Font.BOLD, 12);

  /** Cubellan Font URL */
  static final URL CUBELLAN_FONT_URL = DataSources
      .getDataUrl("resources/fonts/Cubellan_v_0_7/Cubellan.ttf")
      .orElse(null);
  /** Cubellan Bold Font URL */
  static final URL CUBELLAN_BOLD_FONT_URL = DataSources
      .getDataUrl("resources/fonts/Cubellan_v_0_7/Cubellan_Bold.ttf")
      .orElse(null);
  /** Cubellan SC Font URL */
  static final URL CUBELLAN_SC_FONT_URL = DataSources
      .getDataUrl("resources/fonts/Cubellan_v_0_7/Cubellan_SC.ttf")
      .orElse(null);
  /** Squarion hinted Font URL */
  static final URL SQUARION_HINTED_FONT_URL = DataSources
      .getDataUrl("resources/fonts/Squarion/hinted-Squarion.ttf")
      .orElse(null);

  /** The Font cache. It caches fonts... End of mandatory Javadoc comment. */
  private static HashMap<String, Font> fontCache = new HashMap<>();

  /** Use larger fonts */
  private static float fontScalingFactor = 1.0F;

  /**
   * Is larger fonts in use?
   * @return True if larger fonts are in use
   */
  public static boolean isLargerFonts() {
    return fontScalingFactor > 1.0;
  }

  /**
   * Set use of larger fonts
   * @param largerFonts the useLargerFonts to set
   */
  public static void setLargerFonts(final boolean largerFonts) {
    // Invalidate font cache
    fontCache.clear();
    if (largerFonts) {
      fontScalingFactor = 1.15F;
    } else {
      fontScalingFactor = 1.0F;
    }
  }

  /**
   * Get Regular Cubellan font very small
   * @return Cubellan font
   */
  public static Font getFontCubellanVerySmall() {
    // Don't auto-scale this font
    return getFontFromCache(CUBELLAN_FONT_URL, 9, 1).orElse(FONT_SMALL);
  }

  /**
   * Get Regular Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellan() {
    return getFontFromCache(CUBELLAN_FONT_URL, 16).orElse(FONT_NORMAL);
  }

  /**
   * Get Regular Cubellan font but smaller
   * @return Cubellan font
   */
  public static Font getFontCubellanSmaller() {
    return getFontFromCache(CUBELLAN_FONT_URL, 13).orElse(FONT_SMALL);
  }

  /**
   * Get bold Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellanBold() {
    return getFontFromCache(CUBELLAN_BOLD_FONT_URL, 24).orElse(FONT_NORMAL);
  }

  /**
   * Get bold Cubellan font
   * @return Cubellan font
   */
  public static Font getFontCubellanBoldBig() {
    return getFontFromCache(CUBELLAN_BOLD_FONT_URL, 35).orElse(FONT_NORMAL);
  }

  /**
   * Get Cubellan font with Small Caps
   * @return Cubellan font
   */
  public static Font getFontCubellanSC() {
    return getFontFromCache(CUBELLAN_SC_FONT_URL, 13).orElse(FONT_NORMAL);
  }

  /**
   * Get Regular Generale Station font
   * @return Generale Station font
   */
  public static Font getFontSquarion() {
    return getFontFromCache(SQUARION_HINTED_FONT_URL, 14).orElse(FONT_NORMAL);
  }

  /**
   * Get Regular Generale Station font
   * @return Generale Station font
   */
  public static Font getFontSquarionBold() {
    // A little hacky method, but will do
    // Having proper handling of Font forms (italic, bold, etc.) is in proper
    final var fontUrlAsString = SQUARION_HINTED_FONT_URL.toExternalForm();
    final var fontId = buildFontId(fontUrlAsString, 18);
    final var cachedBoldFont = fontCache.get(fontId);
    if (cachedBoldFont != null) {
      return cachedBoldFont;
    }

    // This will cache the "base" font, but it will be discarded
    var fontOpt = getFontFromCache(SQUARION_HINTED_FONT_URL, 18);
    if (fontOpt.isEmpty()) {
      return FONT_NORMAL;
    }

    final var processedFont = fontOpt.get().deriveFont(Font.BOLD);
    fontCache.put(fontId, processedFont);
    return processedFont;
  }

  /**
   * Load a TrueType font in it's basic form.
   * @param fontUrl URL to Font
   * @return Loaded Font or empty
   */
  private static Optional<Font> loadTrueTypeFont(
      final URL fontUrl) {
    try (InputStream is = fontUrl.openStream()) {
      return Optional.of(Font.createFont(Font.TRUETYPE_FONT, is));
    } catch (IOException | FontFormatException e) {
      ErrorLogger.log("Font loading error:" + e.getMessage());
      return Optional.empty();
    }
  }

  /**
   * Creates a "Font ID" from input. Used for Font caching identifiers.
   * @param fontResource Font URL as String
   * @param baseSize base size of the font
   * @return ID of Font for fontCache
   */
  private static String buildFontId(final String fontResource,
      final float baseSize) {
    return fontResource.toString() + "SIZE:" + baseSize;
  }

  /**
   * Lazy-load a font of specified base size, using internal scaling factor,
   * and store resulting font to cache
   * @param fontUrl Font URL
   * @param baseSize base size of the font
   * @return Font or empty if loading failed\
   */
  private static Optional<Font> getFontFromCache(final URL fontUrl,
      final float baseSize) {
    return GuiFonts.getFontFromCache(fontUrl, baseSize,
        GuiFonts.fontScalingFactor);
  }

  /**
   * Lazy-load a font of specified base size, using specified scaling factor,
   * and store resulting font to cache
   * @param fontUrl Font URL
   * @param baseSize base size of the font
   * @param scalingFactor load the font using specified scaling
   * @return Font or empty if loading failed
   */
  private static Optional<Font> getFontFromCache(final URL fontUrl,
      final float baseSize, final float scalingFactor) {
    if (fontUrl == null) {
      return Optional.empty();
    }
    final var fontId = buildFontId(fontUrl.toExternalForm(), baseSize);
    Font result = fontCache.get(fontId);
    if (result != null) {
      return Optional.of(result);
    }
    final var fontOpt = loadTrueTypeFont(fontUrl);
    final var fontSize = baseSize * scalingFactor;

    if (fontOpt.isPresent()) {
      final var tmpFont = fontOpt.get().deriveFont(fontSize);
      fontCache.put(fontId, tmpFont);
      return Optional.of(tmpFont);
    }
    return Optional.empty();
  }

  /** Hidden constructor */
  private GuiFonts() {
  }
}
