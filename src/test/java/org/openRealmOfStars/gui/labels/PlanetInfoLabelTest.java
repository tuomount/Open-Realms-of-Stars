package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.GovernmentFactory;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;

/**
 * Test for PlanetInfoLabel
 */
public class PlanetInfoLabelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    Planet target = Mockito.mock(Planet.class);
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getRace()).thenReturn(SpaceRaceFactory.createOne(
        "HUMANS"));
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentFactory
        .createOne("AI"));
    Mockito.when(target.getPlanetPlayerInfo()).thenReturn(realm);
    Mockito.when(target.getPlanetType()).thenReturn(PlanetTypes.ICEWORLD1);
    Construction[] list = new Construction[1];
    list[0] = Mockito.mock(Construction.class);
    Mockito.when(list[0].getName()).thenReturn("Test building");
    Mockito.when(target.getProductionList()).thenReturn(list);
    Mockito.when(target.getUnderConstruction()).thenReturn(list[0]);
    PlanetInfoLabel label = new PlanetInfoLabel(target, listener);
    assertEquals(list[0], label.getSelectedConstruction());
  }

}
