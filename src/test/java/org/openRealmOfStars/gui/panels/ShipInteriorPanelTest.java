package org.openRealmOfStars.gui.panels;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017 Tuomo Untinen
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
 * Race Image Panel Test
 * 
 */
public class ShipInteriorPanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ShipInteriorPanel panel = new ShipInteriorPanel(SpaceRace.GREYANS,
        GuiStatics.BIG_PLANET_WATERWORLD1);
    assertEquals(SpaceRace.GREYANS, panel.getRace());
    assertEquals(GuiStatics.BIG_PLANET_WATERWORLD1, panel.getPlanetImage());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNull() {
    DiceGenerator.initializeGenerators(90,90,90);
    ShipInteriorPanel panel = new ShipInteriorPanel(SpaceRace.GREYANS, null);
    assertEquals(SpaceRace.GREYANS, panel.getRace());
    assertEquals(null, panel.getPlanetImage());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNebulae() {
    DiceGenerator.initializeGenerators(10,10,10);
    ShipInteriorPanel panel = new ShipInteriorPanel(SpaceRace.GREYANS,
        GuiStatics.NEBULAE_IMAGE);
    assertEquals(SpaceRace.GREYANS, panel.getRace());
    assertEquals(GuiStatics.NEBULAE_IMAGE, panel.getPlanetImage());
  }

}
