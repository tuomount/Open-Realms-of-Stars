package org.openRealmOfStars.mapTiles;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
 * Test for Tile
 *
 */
public class TileTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTile() {
    BufferedImage img = Mockito.mock(BufferedImage.class);
    BufferedImage img2 = Mockito.mock(BufferedImage.class);
    Mockito.when(img.getWidth()).thenReturn(512);
    Mockito.when(img.getHeight()).thenReturn(512);
    Mockito.when(img.getSubimage(0 * Tile.MAX_WIDTH, 0 * Tile.MAX_HEIGHT,
        Tile.MAX_WIDTH, Tile.MAX_HEIGHT)).thenReturn(img2);
    
    Tile tile = new Tile(img, 0, 0, "Test");
    assertEquals("Test",tile.getName());
    assertEquals(-1,tile.getIndex());
    tile.setIndex(0);
    assertEquals(0,tile.getIndex());
    Graphics2D g2d = Mockito.mock(Graphics2D.class);
    tile.draw(g2d, 0, 0);
  }

  @Test(expected=RasterFormatException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTileFail() {
    BufferedImage img = Mockito.mock(BufferedImage.class);
    BufferedImage img2 = Mockito.mock(BufferedImage.class);
    Mockito.when(img.getWidth()).thenReturn(16);
    Mockito.when(img.getHeight()).thenReturn(16);
    Mockito.when(img.getSubimage(2 * Tile.MAX_WIDTH, 2 * Tile.MAX_HEIGHT,
        Tile.MAX_WIDTH, Tile.MAX_HEIGHT)).thenReturn(img2);
    
    Tile tile = new Tile(img, 2, 2, "Test");
    tile.getName();
  }

}
