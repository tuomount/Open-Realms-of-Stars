package org.openRealmOfStars.mapTiles;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018,2020 Tuomo Untinen
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
    Mockito.when(img.getType()).thenReturn(BufferedImage.TYPE_4BYTE_ABGR);
    Mockito.when(img2.getType()).thenReturn(BufferedImage.TYPE_4BYTE_ABGR);
    
    Tile tile = new Tile(img, 0, 0, "Test");
    assertEquals("", tile.getDescription());
    assertEquals("Test",tile.getName());
    assertEquals("Test (-1)",tile.toString());
    assertEquals(-1,tile.getIndex());
    assertEquals(-1,tile.getAnimationIndex());
    tile.setIndex(0);
    assertEquals("Test (0)",tile.toString());
    assertEquals(0,tile.getIndex());
    assertEquals(0,tile.getAnimationIndex());
    tile.setAnimationIndex(1);
    assertEquals(0,tile.getIndex());
    assertEquals(1,tile.getAnimationIndex());
    Graphics2D g2d = Mockito.mock(Graphics2D.class);
    tile.draw(g2d, 0, 0);
    tile.setDescription("Test description");
    assertEquals("Test description", tile.getDescription());

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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReadyTiles() {
    Tile tile = Tiles.getTileByName(TileNames.SUN_C);
    assertEquals(TileNames.STAR_DESCRIPTION, tile.getDescription());
    assertEquals(true, tile.isStarTile());
    assertEquals(false, tile.isDangerous());
    assertEquals(false, tile.isSpaceAnomaly());
    tile = Tiles.getTileByName(TileNames.IRONPLANET1);
    assertEquals(false, tile.isStarTile());
    assertEquals(false, tile.isDangerous());
    assertEquals(false, tile.isSpaceAnomaly());
    assertEquals("", tile.getDescription());
    tile = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR1);
    assertEquals(false, tile.isDangerous());
    assertEquals(false, tile.isSpaceAnomaly());
    assertEquals(TileNames.DEEP_SPACE_ANCHOR_DESCRIPTION,
        tile.getDescription());
    tile = Tiles.getTileByName(TileNames.DEEP_SPACE_ANCHOR2);
    assertEquals(false, tile.isDangerous());
    assertEquals(false, tile.isSpaceAnomaly());
    assertEquals(TileNames.DEEP_SPACE_ANCHOR_DESCRIPTION,
        tile.getDescription());
    tile = Tiles.getTileByName(TileNames.SPACE_ANOMALY_LAIR);
    assertEquals(true, tile.isSpaceAnomaly());
    assertEquals(true, tile.isDangerous());
    tile = Tiles.getTileByName(TileNames.WORM_HOLE1);
    assertEquals(true, tile.isDangerous());
    assertEquals(false, tile.isSpaceAnomaly());
    tile = Tiles.getTileByName(TileNames.WORM_HOLE2);
    assertEquals(true, tile.isDangerous());
    assertEquals(false, tile.isSpaceAnomaly());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAnomalyTileNames() {
    DiceGenerator.initializeGenerators(0,0,0,8,7,8);
    String str = TileNames.getRandomSpaceAnomaly(true, true, false);
    assertEquals(TileNames.SPACE_ANOMALY_CREDITS, str);
    str = TileNames.getRandomSpaceAnomaly(true, false, false);
    assertEquals(TileNames.SPACE_ANOMALY_CREDITS, str);
    str = TileNames.getRandomSpaceAnomaly(false, false, false);
    assertEquals(TileNames.SPACE_ANOMALY_CREDITS, str);
    str = TileNames.getRandomSpaceAnomaly(true, true, false);
    assertEquals(TileNames.SPACE_ANOMALY_TIME_WARP, str);
    str = TileNames.getRandomSpaceAnomaly(true, true, true);
    assertEquals(TileNames.SPACE_ANOMALY, str);
  }

}
