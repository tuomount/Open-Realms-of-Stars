package org.openRealmOfStars.gui.mapPanel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
*
* Test for planet animations.
*
*/
public class PlanetAnimationTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_AIM, 100, 120, 50, 60);
    assertNotEquals(null, planetAnimation.getParticles());
    assertEquals(PlanetAnimation.ANIMATION_TYPE_AIM,
        planetAnimation.getAnimationType());
    planetAnimation.setAnimationType(PlanetAnimation.ANIMATION_TYPE_BOMBING);
    assertEquals(PlanetAnimation.ANIMATION_TYPE_BOMBING,
        planetAnimation.getAnimationType());
    planetAnimation.setShipIndex(5);
    assertEquals(5, planetAnimation.getShipIndex());
    assertEquals(100, planetAnimation.getSx());
    assertEquals(120, planetAnimation.getSy());
    assertEquals(50, planetAnimation.getEx());
    assertEquals(60, planetAnimation.getEy());
    assertNotEquals(null, planetAnimation.getBeamColor());
    assertNotEquals(null, planetAnimation.getNukeColor());
    planetAnimation.setCoords(90, 80, 45, 40);
    assertEquals(90, planetAnimation.getSx());
    assertEquals(80, planetAnimation.getSy());
    assertEquals(45, planetAnimation.getEx());
    assertEquals(40, planetAnimation.getEy());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAim() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_AIM, 100, 120, 50, 60);
    assertEquals(false, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
    for (int i = 0;i < 40; i++) {
      planetAnimation.doAnimation();
    }
    assertEquals(true, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTurret() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_TURRET, 100, 120, 50, 60);
    assertEquals(false, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
    while(!planetAnimation.isAnimationFinished()) {
      planetAnimation.doAnimation();
    }
    assertEquals(null, planetAnimation.getAnimFrame());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBombingAim() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM, 100, 120, 50, 60);
    assertEquals(false, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
    while(!planetAnimation.isAnimationFinished()) {
      planetAnimation.doAnimation();
    }
    assertEquals(null, planetAnimation.getAnimFrame());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBombing() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_BOMBING_AIM, 100, 120, 50, 60);
    assertEquals(false, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
    planetAnimation.setAnimationType(PlanetAnimation.ANIMATION_TYPE_BOMBING);
    while(!planetAnimation.isAnimationFinished()) {
      planetAnimation.doAnimation();
    }
    assertEquals(null, planetAnimation.getAnimFrame());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNukeAim() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_NUKE_AIM, 100, 120, 50, 60);
    assertEquals(false, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
    while(!planetAnimation.isAnimationFinished()) {
      planetAnimation.doAnimation();
    }
    assertEquals(null, planetAnimation.getAnimFrame());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNuking() {
    PlanetAnimation planetAnimation = new PlanetAnimation(
        PlanetAnimation.ANIMATION_TYPE_NUKE_AIM, 100, 120, 50, 60);
    assertEquals(false, planetAnimation.isAnimationFinished());
    assertEquals(null, planetAnimation.getAnimFrame());
    planetAnimation.setAnimationType(PlanetAnimation.ANIMATION_TYPE_NUKING);
    while(!planetAnimation.isAnimationFinished()) {
      planetAnimation.doAnimation();
    }
    assertEquals(null, planetAnimation.getAnimFrame());
  }

}
