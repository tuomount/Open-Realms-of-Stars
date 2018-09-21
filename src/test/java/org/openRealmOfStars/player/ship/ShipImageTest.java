package org.openRealmOfStars.player.ship;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* JUnit for Static ship images for different races
*
*/
public class ShipImageTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHumans() {
    assertNotNull(ShipImages.humans());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaurs() {
    assertNotNull(ShipImages.centaurs());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyans() {
    assertNotNull(ShipImages.greyans());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMechions() {
    assertNotNull(ShipImages.mechions());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuhtidaes() {
    assertNotNull(ShipImages.teuthidaes());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaurians() {
    assertNotNull(ShipImages.scaurians());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporks() {
    assertNotNull(ShipImages.sporks());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoids() {
    assertNotNull(ShipImages.mothoids());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpacePirates() {
    assertNotNull(ShipImages.spacePirates());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHomarians() {
    assertNotNull(ShipImages.homarians());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testChiraloids() {
    assertNotNull(ShipImages.chiraloids());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    for (SpaceRace race : SpaceRace.values()) {
      assertNotNull(ShipImages.getByRace(race));
    }
  }

}
