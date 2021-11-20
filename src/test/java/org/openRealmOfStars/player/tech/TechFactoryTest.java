package org.openRealmOfStars.player.tech;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018, 2020 Tuomo Untinen
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
* TechFactory test
*
*/
public class TechFactoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostShort() {
    assertEquals(5, TechFactory.getTechCost(1, 300));
    assertEquals(7, TechFactory.getTechCost(2, 300));
    assertEquals(10, TechFactory.getTechCost(3, 300));
    assertEquals(18, TechFactory.getTechCost(4, 300));
    assertEquals(25, TechFactory.getTechCost(5, 300));
    assertEquals(40, TechFactory.getTechCost(6, 300));
    assertEquals(60, TechFactory.getTechCost(7, 300));
    assertEquals(84, TechFactory.getTechCost(8, 300));
    assertEquals(104, TechFactory.getTechCost(9, 300));
    assertEquals(130, TechFactory.getTechCost(10, 300));
    assertEquals(168, TechFactory.getTechCost(11, 300));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostVeryShort() {
    assertEquals(5, TechFactory.getTechCost(1, 200));
    assertEquals(7, TechFactory.getTechCost(2, 200));
    assertEquals(8, TechFactory.getTechCost(3, 200));
    assertEquals(16, TechFactory.getTechCost(4, 200));
    assertEquals(20, TechFactory.getTechCost(5, 200));
    assertEquals(35, TechFactory.getTechCost(6, 200));
    assertEquals(50, TechFactory.getTechCost(7, 200));
    assertEquals(74, TechFactory.getTechCost(8, 200));
    assertEquals(89, TechFactory.getTechCost(9, 200));
    assertEquals(115, TechFactory.getTechCost(10, 200));
    assertEquals(153, TechFactory.getTechCost(11, 200));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostMedium() {
    assertEquals(5, TechFactory.getTechCost(1, 400));
    assertEquals(7, TechFactory.getTechCost(2, 400));
    assertEquals(10, TechFactory.getTechCost(3, 400));
    assertEquals(18, TechFactory.getTechCost(4, 400));
    assertEquals(27, TechFactory.getTechCost(5, 400));
    assertEquals(42, TechFactory.getTechCost(6, 400));
    assertEquals(65, TechFactory.getTechCost(7, 400));
    assertEquals(89, TechFactory.getTechCost(8, 400));
    assertEquals(112, TechFactory.getTechCost(9, 400));
    assertEquals(138, TechFactory.getTechCost(10, 400));
    assertEquals(176, TechFactory.getTechCost(11, 400));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostLong() {
    assertEquals(5, TechFactory.getTechCost(1, 600));
    assertEquals(7, TechFactory.getTechCost(2, 600));
    assertEquals(12, TechFactory.getTechCost(3, 600));
    assertEquals(20, TechFactory.getTechCost(4, 600));
    assertEquals(30, TechFactory.getTechCost(5, 600));
    assertEquals(45, TechFactory.getTechCost(6, 600));
    assertEquals(70, TechFactory.getTechCost(7, 600));
    assertEquals(94, TechFactory.getTechCost(8, 600));
    assertEquals(119, TechFactory.getTechCost(9, 600));
    assertEquals(145, TechFactory.getTechCost(10, 600));
    assertEquals(183, TechFactory.getTechCost(11, 600));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomRareTech() {
    Tech[] list = new Tech[0];
    assertNotNull(TechFactory.getRandomRareTech(list));
    assertNotNull(TechFactory.getRandomRareTech(null));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomRareTech2() {
    Tech[] list = new Tech[7];
    list[0] = TechFactory.createDefenseTech("Solar armor Mk1", 3);
    list[1] = TechFactory.createDefenseTech("Distortion shield Mk1", 4);
    list[2] = TechFactory.createDefenseTech("Organic armor Mk1", 3);
    list[3] = TechFactory.createCombatTech("Plasma cannon Mk1", 2);
    list[4] = TechFactory.createCombatTech("Ion cannon Mk1", 3);
    list[5] = TechFactory.createElectronicsTech("Improved engineer", 3);
    list[6] = TechFactory.createImprovementTech("Orbital lift", 4);
    Tech tech = TechFactory.getRandomRareTech(list);
    assertNotNull(tech);
    assertEquals(true, tech.getName().contains("Mk2"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostVeryLong() {
    assertEquals(5, TechFactory.getTechCost(1, 800));
    assertEquals(7, TechFactory.getTechCost(2, 800));
    assertEquals(14, TechFactory.getTechCost(3, 800));
    assertEquals(22, TechFactory.getTechCost(4, 800));
    assertEquals(33, TechFactory.getTechCost(5, 800));
    assertEquals(48, TechFactory.getTechCost(6, 800));
    assertEquals(75, TechFactory.getTechCost(7, 800));
    assertEquals(99, TechFactory.getTechCost(8, 800));
    assertEquals(124, TechFactory.getTechCost(9, 800));
    assertEquals(150, TechFactory.getTechCost(10, 800));
    assertEquals(188, TechFactory.getTechCost(11, 800));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostMassive() {
    assertEquals(5, TechFactory.getTechCost(1, 1000));
    assertEquals(7, TechFactory.getTechCost(2, 1000));
    assertEquals(16, TechFactory.getTechCost(3, 1000));
    assertEquals(24, TechFactory.getTechCost(4, 1000));
    assertEquals(40, TechFactory.getTechCost(5, 1000));
    assertEquals(55, TechFactory.getTechCost(6, 1000));
    assertEquals(80, TechFactory.getTechCost(7, 1000));
    assertEquals(104, TechFactory.getTechCost(8, 1000));
    assertEquals(134, TechFactory.getTechCost(9, 1000));
    assertEquals(160, TechFactory.getTechCost(10, 1000));
    assertEquals(198, TechFactory.getTechCost(11, 1000));
  }

}
