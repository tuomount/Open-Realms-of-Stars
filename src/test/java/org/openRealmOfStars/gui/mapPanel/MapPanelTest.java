package org.openRealmOfStars.gui.mapPanel;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2018, 2019 Tuomo Untinen
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
* Class for handling the star map drawing to JPanel
*
*/
public class MapPanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRegular() {
    MapPanel panel = new MapPanel(false);
    assertEquals(0, panel.getCursorFocus());
    panel.calculateViewPoints();
    assertEquals(0, panel.getLastDrawnX());
    assertEquals(0, panel.getLastDrawnY());
    assertEquals(-48, panel.getOffsetX());
    assertEquals(-48, panel.getOffsetY());
    assertEquals(1, panel.getViewPointX());
    assertEquals(1, panel.getViewPointY());
    assertEquals(null, panel.getRoute());
    Route route = Mockito.mock(Route.class);
    panel.setRoute(route);
    assertEquals(route, panel.getRoute());
    Coordinate coord = panel.getHistoryCoordinates();
    assertEquals(15, coord.getX());
    assertEquals(15, coord.getY());
    BufferedImage left = Mockito.mock(BufferedImage.class);
    assertEquals(null, panel.getLeftSpaceImage());
    panel.setLeftSpaceImage(left);
    assertEquals(left, panel.getLeftSpaceImage());
    BufferedImage right = Mockito.mock(BufferedImage.class);
    assertEquals(null, panel.getRightSpaceImage());
    panel.setRightSpaceImage(right);
    assertEquals(right, panel.getRightSpaceImage());
    PopupPanel popup = Mockito.mock(PopupPanel.class);
    assertEquals(null, panel.getPopup());
    panel.setPopup(popup);
    assertEquals(popup, panel.getPopup());
    panel.setCursorFocus(25);
    assertEquals(25, panel.getCursorFocus());
    panel.setCursorFocus(60);
    assertEquals(50, panel.getCursorFocus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBattle() {
    MapPanel panel = new MapPanel(true);
    panel.calculateViewPoints();
    assertEquals(0, panel.getLastDrawnX());
    assertEquals(0, panel.getLastDrawnY());
    assertEquals(0, panel.getOffsetX());
    assertEquals(0, panel.getOffsetY());
    assertEquals(4, panel.getViewPointX());
    assertEquals(4, panel.getViewPointY());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBattle2() {
    Game game = Mockito.mock(Game.class);
    Mockito.when(game.getHeight()).thenReturn(960);
    Mockito.when(game.getWidth()).thenReturn(1440);
    MapPanel panel = new MapPanel(game);
    panel.calculateViewPoints();
    assertEquals(0, panel.getLastDrawnX());
    assertEquals(0, panel.getLastDrawnY());
    assertEquals(197, panel.getOffsetX());
    assertEquals(87, panel.getOffsetY());
    assertEquals(4, panel.getViewPointX());
    assertEquals(4, panel.getViewPointY());
    assertEquals(null, panel.getRoute());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHistory() {
    MapPanel panel = new MapPanel(false);
    panel.calculateViewPoints();
    assertEquals(0, panel.getLastDrawnX());
    assertEquals(0, panel.getLastDrawnY());
    assertEquals(-48, panel.getOffsetX());
    assertEquals(-48, panel.getOffsetY());
    assertEquals(1, panel.getViewPointX());
    assertEquals(1, panel.getViewPointY());
    Coordinate coordinate = panel.getHistoryCoordinates();
    assertEquals(15, coordinate.getX());
    assertEquals(15, coordinate.getY());
    BufferedImage image = Mockito.mock(BufferedImage.class);
    assertNull(panel.getLeftSpaceImage());
    panel.setLeftSpaceImage(image);
    assertEquals(image, panel.getLeftSpaceImage());
    assertNull(panel.getRightSpaceImage());
    panel.setRightSpaceImage(image);
    assertEquals(image, panel.getRightSpaceImage());
  }

}
