package org.openRealmOfStars.gui.mapPanel;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.Tiles;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* JUnit for blackhole effect
*
*/

public class BlackHoleEffectTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    BufferedImage img = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
        BufferedImage.TYPE_4BYTE_ABGR);
    BlackHoleEffect effect = new BlackHoleEffect(img);
    BufferedImage img2 = new BufferedImage(Tile.MAX_WIDTH, Tile.MAX_HEIGHT,
        BufferedImage.TYPE_4BYTE_ABGR);
    effect.drawBlackholeTile(img2.createGraphics(), 0, 0, Tiles.getTileByIndex(5));
    effect.updateBackground(img2);
  }

}
