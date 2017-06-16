package org.openRealmOfStars.gui.labels;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 * Tests for ImageLabel
 */
public class ImageLabelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreatingImageLabel() {
    BufferedImage image = Mockito.mock(BufferedImage.class);
    ImageLabel label = new ImageLabel(image, true);
    assertEquals(true, label.isBorder());
    assertEquals(null, label.getFillColor());
    Color color = Mockito.mock(Color.class);
    label.setFillColor(color);
    assertEquals(color, label.getFillColor());
    label.setBorder(false);
    assertEquals(false, label.isBorder());
    label = new ImageLabel(image, false);
    assertEquals(false, label.isBorder());
  }

}
