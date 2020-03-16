package org.openRealmOfStars.gui.panels;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.mapPanel.PlanetAnimation;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;

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
* JUnits for Big image panel
*
*/
public class BigImagePanelTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    BigImagePanel panel = new BigImagePanel(planet, true, "Planet view");
    assertEquals(null, panel.getAnimation());
    PlanetAnimation animation = Mockito.mock(PlanetAnimation.class);
    panel.setAnimation(animation);
    assertEquals(animation, panel.getAnimation());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    panel.setPlayer(info);
    assertEquals(info, panel.getPlayer());
    panel.setText("Test text");
    panel.setTitle("Test title");
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasicDrawing() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(planet, true, "Planet view");
    panel.paint(image.getGraphics());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingWithTitle() {
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(null, true, "Planet view");
    panel.setTitle("Example title");
    panel.paint(image.getGraphics());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingWithPlanet() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    planet.setPlanetType(PlanetTypes.DESERTWORLD1);
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(planet, true, null);
    panel.setText("Example text \n for testing");
    panel.paint(image.getGraphics());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingWithPlanetShips() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    planet.setPlanetType(PlanetTypes.DESERTWORLD1);
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(planet, true, null);
    BufferedImage[] ships = new BufferedImage[9];
    for (int i = 0; i < ships.length; i++) {
      ships[i] = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
    }
    panel.setShipImage(ships);
    panel.setText("Example text \n for testing");
    panel.paint(image.getGraphics());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingWithPlanetAnimation() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    planet.setPlanetType(PlanetTypes.DESERTWORLD1);
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(planet, true, null);
    PlanetAnimation animation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_TURRET, 50, 59, 70, 70);
    panel.setAnimation(animation);
    panel.paint(image.getGraphics());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingWithPlanetAnimation2() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    planet.setPlanetType(PlanetTypes.DESERTWORLD1);
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(planet, true, null);
    PlanetAnimation animation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_AIM, 50, 59, 70, 70);
    panel.setAnimation(animation);
    BufferedImage[] ships = new BufferedImage[9];
    for (int i = 0; i < ships.length; i++) {
      ships[i] = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
    }
    panel.setShipImage(ships);
    panel.paint(image.getGraphics());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDrawingWithPlanetAnimation3() {
    Planet planet = new Planet(new Coordinate(5,5), "Test", 1, false);
    planet.setPlanetType(PlanetTypes.DESERTWORLD1);
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_4BYTE_ABGR);
    BigImagePanel panel = new BigImagePanel(planet, true, null);
    PlanetAnimation animation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_NUKE_AIM, 50, 59, 70, 70);
    panel.setAnimation(animation);
    BufferedImage[] ships = new BufferedImage[9];
    for (int i = 0; i < ships.length; i++) {
      ships[i] = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
    }
    panel.setShipImage(ships);
    panel.paint(image.getGraphics());
  }

}
