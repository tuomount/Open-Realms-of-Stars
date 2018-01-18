package org.openRealmOfStars.starMap.planet.construction;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.starMap.planet.BuildingFactory;

/**
*
* Open Realm of Stars game project Copyright (C) 2018 Tuomo Untinen
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
* Test for Building
*
*/
public class BuildingTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFullDescription() {
    Icon16x16 icon = Mockito.mock(Icon16x16.class);
    Building building = new Building(0, "Test Bank", icon, BuildingType.CREDIT);
    building.setCredBonus(1);
    String generic = building.getFullDescription();
    String scaurian = building.getFullDescription(SpaceRace.SCAURIANS);
    assertEquals(true, generic.contains("Cred.: +1"));
    assertEquals(true, scaurian.contains("Cred.: +2"));
    building = new Building(0, "Test Farm", icon, BuildingType.FARM);
    building.setFarmBonus(1);
    generic = building.getFullDescription();
    scaurian = building.getFullDescription(SpaceRace.SCAURIANS);
    assertEquals(false, generic.contains("Cred.: +1"));
    assertEquals(true, generic.contains("Food: +1"));
    assertEquals(false, scaurian.contains("Cred.: +2"));
    assertEquals(true, scaurian.contains("Food: +1"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientLab() {
    Building building = BuildingFactory.createByName("Ancient lab");
    assertEquals("Ancient lab", building.getName());
    assertEquals(1, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientFactory() {
    Building building = BuildingFactory.createByName("Ancient factory");
    assertEquals("Ancient factory", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(0, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(1, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientTemple() {
    Building building = BuildingFactory.createByName("Ancient temple");
    assertEquals("Ancient temple", building.getName());
    assertEquals(0, building.getReseBonus());
    assertEquals(0, building.getCredBonus());
    assertEquals(1, building.getCultBonus());
    assertEquals(0, building.getMineBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFactBonus());
    assertEquals(0, building.getFarmBonus());
  }

}
