package org.openRealmOfStars.player.tech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Test for Tech
 *
 */
public class TechTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTech() {
    Tech tech = new Tech("Test tech", TechType.Combat, 1);
    tech.setComponent("Test component");
    assertEquals("Test component", tech.getComponent());
    tech.setHull("Test hull");
    assertEquals("Test hull", tech.getHull());
    Icon16x16 icon = Mockito.mock(Icon16x16.class);
    tech.setIcon(icon);
    assertEquals(icon,tech.getIcon());
    assertEquals(1,tech.getLevel());
    tech.setLevel(2);
    assertEquals(2,tech.getLevel());
    assertEquals("Test tech",tech.getName());
    tech.setName("Test tech2");
    assertEquals("Test tech2",tech.getName());
    assertEquals(TechType.Combat,tech.getType());
    tech.setType(TechType.Electrics);
    assertEquals(TechType.Electrics,tech.getType());
    assertEquals("Test tech2",tech.toString());
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechImprovementDescription() {
    Tech tech = new Tech("Basic lab", TechType.Improvements, 1);
    tech.setImprovement("Basic lab");
    assertTrue("Basic lab",tech.getTechInfo(SpaceRace.HUMAN)
        .contains("Improvement: \nBasic lab"));
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechHullDescription() {
    Tech tech = new Tech("Destroyer Mk1", TechType.Hulls, 1);
    tech.setHull("Destroyer Mk1");
    assertTrue("Destroyer Mk1",tech.getTechInfo(SpaceRace.HUMAN)
        .contains("Ship design: \nDestroyer Mk1"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechComponentDescription() {
    Tech tech = new Tech("Scanner Mk1", TechType.Electrics, 1);
    tech.setComponent("Scanner Mk1");
    assertTrue("Scanner Mk1",tech.getTechInfo(SpaceRace.HUMAN)
        .contains("Ship component: \nScanner Mk1"));
  }
  


}
