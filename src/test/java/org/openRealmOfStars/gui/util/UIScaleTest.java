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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.Dimension;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Unit tests for UIScale utility class.
 */
public class UIScaleTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInitializeBaseResolution() {
    // Initialize at base resolution (768p)
    UIScale.initialize(1024, 768);

    assertEquals(1.0, UIScale.getScaleFactor(), 0.001);
    assertEquals(1024, UIScale.getScreenWidth());
    assertEquals(768, UIScale.getScreenHeight());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInitialize1080p() {
    // Initialize at 1080p
    UIScale.initialize(1920, 1080);

    // 1080 / 768 = 1.40625
    assertEquals(1.40625, UIScale.getScaleFactor(), 0.001);
    assertEquals(1920, UIScale.getScreenWidth());
    assertEquals(1080, UIScale.getScreenHeight());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInitialize1440p() {
    // Initialize at 1440p
    UIScale.initialize(2560, 1440);

    // 1440 / 768 = 1.875
    assertEquals(1.875, UIScale.getScaleFactor(), 0.001);
    assertEquals(2560, UIScale.getScreenWidth());
    assertEquals(1440, UIScale.getScreenHeight());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInitialize4K() {
    // Initialize at 4K (2160p)
    UIScale.initialize(3840, 2160);

    // 2160 / 768 = 2.8125
    assertEquals(2.8125, UIScale.getScaleFactor(), 0.001);
    assertEquals(3840, UIScale.getScreenWidth());
    assertEquals(2160, UIScale.getScreenHeight());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaleInteger() {
    UIScale.initialize(1920, 1080);

    // Scale factor is 1.40625
    // 10 * 1.40625 = 14.0625 -> rounds to 14
    assertEquals(14, UIScale.scale(10));

    // 100 * 1.40625 = 140.625 -> rounds to 141
    assertEquals(141, UIScale.scale(100));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaleAtBaseResolution() {
    UIScale.initialize(1024, 768);

    // At base resolution, scale factor is 1.0
    assertEquals(10, UIScale.scale(10));
    assertEquals(100, UIScale.scale(100));
    assertEquals(32, UIScale.scale(32));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaleFont() {
    UIScale.initialize(2560, 1440);

    // Scale factor is 1.875
    // 12.0f * 1.875 = 22.5f
    assertEquals(22.5f, UIScale.scaleFont(12.0f), 0.001f);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaledDimension() {
    UIScale.initialize(1920, 1080);

    Dimension dim = UIScale.scaledDimension(100, 50);
    assertNotNull(dim);

    // 100 * 1.40625 = 141, 50 * 1.40625 = 70
    assertEquals(141, dim.width);
    assertEquals(70, dim.height);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaledRigidArea() {
    UIScale.initialize(1024, 768);

    Component rigidArea = UIScale.scaledRigidArea(10, 5);
    assertNotNull(rigidArea);

    Dimension size = rigidArea.getPreferredSize();
    assertEquals(10, size.width);
    assertEquals(5, size.height);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsHighDpi() {
    UIScale.initialize(1024, 768);
    assertFalse(UIScale.isHighDpi());

    UIScale.initialize(1920, 1080);
    assertFalse(UIScale.isHighDpi());

    UIScale.initialize(2560, 1440);
    assertTrue(UIScale.isHighDpi());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIs4K() {
    UIScale.initialize(1920, 1080);
    assertFalse(UIScale.is4K());

    UIScale.initialize(2560, 1440);
    assertFalse(UIScale.is4K());

    UIScale.initialize(3840, 2160);
    assertTrue(UIScale.is4K());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGetResolutionTier() {
    UIScale.initialize(1024, 768);
    assertEquals(0, UIScale.getResolutionTier());

    UIScale.initialize(1920, 1080);
    assertEquals(1, UIScale.getResolutionTier());

    UIScale.initialize(2560, 1440);
    assertEquals(2, UIScale.getResolutionTier());

    UIScale.initialize(3840, 2160);
    assertEquals(3, UIScale.getResolutionTier());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPercentWidth() {
    UIScale.initialize(1920, 1080);

    assertEquals(960, UIScale.percentWidth(0.5));
    assertEquals(1920, UIScale.percentWidth(1.0));
    assertEquals(192, UIScale.percentWidth(0.1));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPercentHeight() {
    UIScale.initialize(1920, 1080);

    assertEquals(540, UIScale.percentHeight(0.5));
    assertEquals(1080, UIScale.percentHeight(1.0));
    assertEquals(108, UIScale.percentHeight(0.1));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMinScaleClamping() {
    // Very small resolution should clamp to minimum scale of 1.0
    UIScale.initialize(640, 480);

    // 480 / 768 = 0.625, but should clamp to 1.0
    assertEquals(1.0, UIScale.getScaleFactor(), 0.001);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMaxScaleClamping() {
    // Very large resolution should clamp to maximum scale of 4.0
    UIScale.initialize(7680, 4320);

    // 4320 / 768 = 5.625, but should clamp to 4.0
    assertEquals(4.0, UIScale.getScaleFactor(), 0.001);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaleDouble() {
    UIScale.initialize(1920, 1080);

    // Scale factor is 1.40625
    // 10.0 * 1.40625 = 14.0625
    assertEquals(14.0625, UIScale.scaleDouble(10.0), 0.001);

    // 100.5 * 1.40625 = 141.328125
    assertEquals(141.328125, UIScale.scaleDouble(100.5), 0.001);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaleIfAbove() {
    // Initialize at 1440p
    UIScale.initialize(2560, 1440);

    // Below threshold - should return original value
    assertEquals(100, UIScale.scaleIfAbove(100, 1440));

    // Above threshold (1440 > 1080) - should scale
    // 100 * (1440 / 1080) = 100 * 1.333... = 133
    assertEquals(133, UIScale.scaleIfAbove(100, 1080));

    // At 768p, testing threshold
    UIScale.initialize(1024, 768);
    // 768 is not > 768, so no scaling
    assertEquals(50, UIScale.scaleIfAbove(50, 768));
    // 768 > 600, so scale: 50 * (768/600) = 64
    assertEquals(64, UIScale.scaleIfAbove(50, 600));
  }
}
