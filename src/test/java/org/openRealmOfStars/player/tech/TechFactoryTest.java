package org.openRealmOfStars.player.tech;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2023 Tuomo Untinen
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

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
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
    assertEquals(14, TechFactory.getTechCost(4, 300));
    assertEquals(17, TechFactory.getTechCost(5, 300));
    assertEquals(23, TechFactory.getTechCost(6, 300));
    assertEquals(31, TechFactory.getTechCost(7, 300));
    assertEquals(37, TechFactory.getTechCost(8, 300));
    assertEquals(45, TechFactory.getTechCost(9, 300));
    assertEquals(52, TechFactory.getTechCost(10, 300));
    assertEquals(62, TechFactory.getTechCost(11, 300));
    assertEquals(74, TechFactory.getTechCost(12, 300));
    assertEquals(87, TechFactory.getTechCost(13, 300));
    assertEquals(93, TechFactory.getTechCost(14, 300));
    assertEquals(108, TechFactory.getTechCost(15, 300));
    assertEquals(124, TechFactory.getTechCost(16, 300));
    assertEquals(141, TechFactory.getTechCost(17, 300));
    assertEquals(149, TechFactory.getTechCost(18, 300));
    assertEquals(168, TechFactory.getTechCost(19, 300));
    assertEquals(188, TechFactory.getTechCost(20, 300));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostVeryShort() {
    assertEquals(3, TechFactory.getTechCost(1, 200));
    assertEquals(5, TechFactory.getTechCost(2, 200));
    assertEquals(8, TechFactory.getTechCost(3, 200));
    assertEquals(12, TechFactory.getTechCost(4, 200));
    assertEquals(14, TechFactory.getTechCost(5, 200));
    assertEquals(20, TechFactory.getTechCost(6, 200));
    assertEquals(28, TechFactory.getTechCost(7, 200));
    assertEquals(35, TechFactory.getTechCost(8, 200));
    assertEquals(43, TechFactory.getTechCost(9, 200));
    assertEquals(44, TechFactory.getTechCost(10, 200));
    assertEquals(54, TechFactory.getTechCost(11, 200));
    assertEquals(66, TechFactory.getTechCost(12, 200));
    assertEquals(79, TechFactory.getTechCost(13, 200));
    assertEquals(83, TechFactory.getTechCost(14, 200));
    assertEquals(98, TechFactory.getTechCost(15, 200));
    assertEquals(114, TechFactory.getTechCost(16, 200));
    assertEquals(131, TechFactory.getTechCost(17, 200));
    assertEquals(131, TechFactory.getTechCost(18, 200));
    assertEquals(150, TechFactory.getTechCost(19, 200));
    assertEquals(170, TechFactory.getTechCost(20, 200));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostMedium() {
    assertEquals(5, TechFactory.getTechCost(1, 400));
    assertEquals(7, TechFactory.getTechCost(2, 400));
    assertEquals(10, TechFactory.getTechCost(3, 400));
    assertEquals(14, TechFactory.getTechCost(4, 400));
    assertEquals(19, TechFactory.getTechCost(5, 400));
    assertEquals(25, TechFactory.getTechCost(6, 400));
    assertEquals(33, TechFactory.getTechCost(7, 400));
    assertEquals(40, TechFactory.getTechCost(8, 400));
    assertEquals(48, TechFactory.getTechCost(9, 400));
    assertEquals(54, TechFactory.getTechCost(10, 400));
    assertEquals(64, TechFactory.getTechCost(11, 400));
    assertEquals(76, TechFactory.getTechCost(12, 400));
    assertEquals(89, TechFactory.getTechCost(13, 400));
    assertEquals(101, TechFactory.getTechCost(14, 400));
    assertEquals(116, TechFactory.getTechCost(15, 400));
    assertEquals(132, TechFactory.getTechCost(16, 400));
    assertEquals(149, TechFactory.getTechCost(17, 400));
    assertEquals(159, TechFactory.getTechCost(18, 400));
    assertEquals(178, TechFactory.getTechCost(19, 400));
    assertEquals(198, TechFactory.getTechCost(20, 400));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostLong() {
    assertEquals(5, TechFactory.getTechCost(1, 600));
    assertEquals(7, TechFactory.getTechCost(2, 600));
    assertEquals(10, TechFactory.getTechCost(3, 600));
    assertEquals(14, TechFactory.getTechCost(4, 600));
    assertEquals(19, TechFactory.getTechCost(5, 600));
    assertEquals(25, TechFactory.getTechCost(6, 600));
    assertEquals(33, TechFactory.getTechCost(7, 600));
    assertEquals(42, TechFactory.getTechCost(8, 600));
    assertEquals(50, TechFactory.getTechCost(9, 600));
    assertEquals(59, TechFactory.getTechCost(10, 600));
    assertEquals(69, TechFactory.getTechCost(11, 600));
    assertEquals(81, TechFactory.getTechCost(12, 600));
    assertEquals(94, TechFactory.getTechCost(13, 600));
    assertEquals(108, TechFactory.getTechCost(14, 600));
    assertEquals(123, TechFactory.getTechCost(15, 600));
    assertEquals(139, TechFactory.getTechCost(16, 600));
    assertEquals(156, TechFactory.getTechCost(17, 600));
    assertEquals(174, TechFactory.getTechCost(18, 600));
    assertEquals(193, TechFactory.getTechCost(19, 600));
    assertEquals(213, TechFactory.getTechCost(20, 600));
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
    assertEquals(10, TechFactory.getTechCost(1, 800));
    assertEquals(14, TechFactory.getTechCost(2, 800));
    assertEquals(20, TechFactory.getTechCost(3, 800));
    assertEquals(28, TechFactory.getTechCost(4, 800));
    assertEquals(40, TechFactory.getTechCost(5, 800));
    assertEquals(52, TechFactory.getTechCost(6, 800));
    assertEquals(68, TechFactory.getTechCost(7, 800));
    assertEquals(87, TechFactory.getTechCost(8, 800));
    assertEquals(103, TechFactory.getTechCost(9, 800));
    assertEquals(124, TechFactory.getTechCost(10, 800));
    assertEquals(144, TechFactory.getTechCost(11, 800));
    assertEquals(168, TechFactory.getTechCost(12, 800));
    assertEquals(194, TechFactory.getTechCost(13, 800));
    assertEquals(228, TechFactory.getTechCost(14, 800));
    assertEquals(258, TechFactory.getTechCost(15, 800));
    assertEquals(290, TechFactory.getTechCost(16, 800));
    assertEquals(324, TechFactory.getTechCost(17, 800));
    assertEquals(363, TechFactory.getTechCost(18, 800));
    assertEquals(401, TechFactory.getTechCost(19, 800));
    assertEquals(441, TechFactory.getTechCost(20, 800));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechCostMassive() {
    assertEquals(12, TechFactory.getTechCost(1, 1000));
    assertEquals(16, TechFactory.getTechCost(2, 1000));
    assertEquals(22, TechFactory.getTechCost(3, 1000));
    assertEquals(30, TechFactory.getTechCost(4, 1000));
    assertEquals(43, TechFactory.getTechCost(5, 1000));
    assertEquals(55, TechFactory.getTechCost(6, 1000));
    assertEquals(71, TechFactory.getTechCost(7, 1000));
    assertEquals(93, TechFactory.getTechCost(8, 1000));
    assertEquals(109, TechFactory.getTechCost(9, 1000));
    assertEquals(131, TechFactory.getTechCost(10, 1000));
    assertEquals(151, TechFactory.getTechCost(11, 1000));
    assertEquals(175, TechFactory.getTechCost(12, 1000));
    assertEquals(201, TechFactory.getTechCost(13, 1000));
    assertEquals(239, TechFactory.getTechCost(14, 1000));
    assertEquals(269, TechFactory.getTechCost(15, 1000));
    assertEquals(301, TechFactory.getTechCost(16, 1000));
    assertEquals(335, TechFactory.getTechCost(17, 1000));
    assertEquals(375, TechFactory.getTechCost(18, 1000));
    assertEquals(413, TechFactory.getTechCost(19, 1000));
    assertEquals(453, TechFactory.getTechCost(20, 1000));
  }

}
