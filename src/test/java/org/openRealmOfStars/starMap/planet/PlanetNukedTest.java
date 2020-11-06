package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
* JUnits for nuked planet information.
*
*/
public class PlanetNukedTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PlanetNuked nuked = new PlanetNuked();
    assertEquals(0, nuked.getBuildingsDestroyed());
    assertEquals(0, nuked.getPopulationKilled());
    assertEquals(null, nuked.getText());
    nuked.setBuildingsDestroyed(5);
    nuked.setPopulationKilled(7);
    nuked.setText("Test text");
    assertEquals(5, nuked.getBuildingsDestroyed());
    assertEquals(7, nuked.getPopulationKilled());
    assertEquals("Test text", nuked.getText());
  }

}
