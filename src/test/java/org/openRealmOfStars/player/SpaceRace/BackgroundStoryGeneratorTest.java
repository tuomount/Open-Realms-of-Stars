package org.openRealmOfStars.player.SpaceRace;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.GovernmentType;

/**
*
* Open Realm of Stars game project 
* Copyright (C) 2023 Tuomo Untinen
*
* This program is free software; you can redistribute it and/or modify it under
* the terms of the GNU General Public License as published by the Free Software
* Foundation; either version 2 of the License, or (at your option) any later
* version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
*
* You should have received a copy of the GNU General Public License along with
* this program; if not, see http://www.gnu.org/licenses/
*
*
* Tests for Background story generator
*
*/
public class BackgroundStoryGeneratorTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateBackgroundStoryForHumans() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Human federation");
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.FEDERATION);

    Planet startingPlanet = Mockito.mock(Planet.class);
    Mockito.when(startingPlanet.getName()).thenReturn("Earth III");
    Mockito.when(startingPlanet.getPlanetType())
        .thenReturn(PlanetTypes.WATERWORLD9);
    int starYear = 2400;
    String result = BackgroundStoryGenerator.generateBackgroundStory(
        info, startingPlanet, starYear);
    System.out.println(result);
  }

}
