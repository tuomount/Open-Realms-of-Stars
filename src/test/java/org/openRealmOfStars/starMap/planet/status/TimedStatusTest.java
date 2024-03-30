package org.openRealmOfStars.starMap.planet.status;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TimedStatusTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PlanetaryStatus planetaryStatus = Mockito.mock(PlanetaryStatus.class);
    Mockito.when(planetaryStatus.getId()).thenReturn("TestStatus");
    TimedStatus status = new TimedStatus(planetaryStatus, TimedStatusType.GAME_START, 5);
    assertEquals("TestStatus GAME_START count: 5", status.toString());
  }

}
