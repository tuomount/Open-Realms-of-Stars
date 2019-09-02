package org.openRealmOfStars.starMap.randomEvent;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* JUnits for Random Event
*
*/
public class RandomEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGoodType() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    RandomEvent event = new RandomEvent(GoodRandomType.MYSTERIOUS_SIGNAL,
        info);
    assertEquals(null, event.getBadType());
    assertEquals(GoodRandomType.MYSTERIOUS_SIGNAL, event.getGoodType());
    assertEquals(info, event.getRealm());
    assertEquals(null, event.getPlanet());
    Planet planet = Mockito.mock(Planet.class);
    event.setPlanet(planet);
    assertEquals(planet, event.getPlanet());
    assertEquals(null, event.getSun());
    Sun sun = Mockito.mock(Sun.class);
    event.setSun(sun);
    assertEquals(sun, event.getSun());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBadType() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    RandomEvent event = new RandomEvent(BadRandomType.RAIDERS, info);
    assertEquals(BadRandomType.RAIDERS, event.getBadType());
    assertEquals(null, event.getGoodType());
    assertEquals(info, event.getRealm());
    assertEquals(null, event.getPlanet());
    assertEquals(null, event.getSun());
    Sun sun = Mockito.mock(Sun.class);
    event.setSun(sun);
    assertEquals(sun, event.getSun());
  }

}
