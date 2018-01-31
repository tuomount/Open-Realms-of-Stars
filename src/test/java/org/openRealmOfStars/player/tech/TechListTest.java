package org.openRealmOfStars.player.tech;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.message.MessageType;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* TechList test
*
*/
public class TechListTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeTechs() {
    Tech[] ownTechs = new Tech[5];
    Tech[] tradeTechs = new Tech[3];
    ownTechs[0] = new Tech("MilTest1", TechType.Combat, 1);
    ownTechs[1] = new Tech("MilTest2", TechType.Combat, 1);
    ownTechs[2] = new Tech("MilTest3", TechType.Combat, 1);
    ownTechs[3] = new Tech("MilTest4", TechType.Combat, 1);
    ownTechs[4] = new Tech("MilTest5", TechType.Combat, 1);
    tradeTechs[0] = new Tech("MilTest1", TechType.Combat, 1);
    tradeTechs[1] = new Tech("MilTest0", TechType.Combat, 1);
    tradeTechs[2] = new Tech("MilTest6", TechType.Combat, 1);
    Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
    assertEquals(2, techs.length);
    assertEquals("MilTest0", techs[0].getName());
    assertEquals("MilTest6", techs[1].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeWeaponsTechs() {
    Tech[] ownTechs = new Tech[2];
    Tech[] tradeTechs = new Tech[2];
    ownTechs[0] = TechFactory.createCombatTech("Laser Mk1", 1);
    ownTechs[1] = TechFactory.createCombatTech("Laser Mk3", 3);
    tradeTechs[0] = TechFactory.createCombatTech("Laser Mk1", 1);
    tradeTechs[1] = TechFactory.createCombatTech("Laser Mk2", 2);
    Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
    assertEquals(0, techs.length);
    techs = TechList.getTechDifference(ownTechs, tradeTechs);
    assertEquals(1, techs.length);
    assertEquals("Laser Mk3", techs[0].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTradeHullTechs() {
    Tech[] ownTechs = new Tech[2];
    Tech[] tradeTechs = new Tech[2];
    ownTechs[0] = TechFactory.createHullTech("Scout Mk1", 1);
    ownTechs[1] = TechFactory.createHullTech("Scout Mk3", 7);
    tradeTechs[0] = TechFactory.createHullTech("Scout Mk1", 1);
    tradeTechs[1] = TechFactory.createHullTech("Scout Mk2", 4);
    Tech[] techs = TechList.getTechDifference(tradeTechs, ownTechs);
    assertEquals(0, techs.length);
    techs = TechList.getTechDifference(ownTechs, tradeTechs);
    assertEquals(1, techs.length);
    assertEquals("Scout Mk3", techs[0].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    TechList list = new TechList();
    assertEquals(20, list.getTechFocus(TechType.Combat));
    assertEquals(16, list.getTechFocus(TechType.Defense));
    assertEquals(16, list.getTechFocus(TechType.Electrics));
    assertEquals(16, list.getTechFocus(TechType.Hulls));
    assertEquals(16, list.getTechFocus(TechType.Improvements));
    assertEquals(16, list.getTechFocus(TechType.Propulsion));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    assertEquals(1, list.getTechLevel(TechType.Defense));
    assertEquals(1, list.getTechLevel(TechType.Electrics));
    assertEquals(1, list.getTechLevel(TechType.Hulls));
    assertEquals(1, list.getTechLevel(TechType.Improvements));
    assertEquals(1, list.getTechLevel(TechType.Propulsion));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissingTechCombat() {
    TechList list = new TechList();
    assertEquals(false, list.isUpgradeable(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    Tech[] missing = list.getListMissingTech(TechType.Combat, 1);
    assertEquals(1, missing.length);
    assertEquals("Photon torpedo Mk1", missing[0].getName());
    assertEquals(true, list.isUpgradeable(TechType.Combat));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissingTechDefense() {
    TechList list = new TechList();
    Tech[] missing = list.getListMissingTech(TechType.Defense, 5);
    assertEquals(3, missing.length);
    assertEquals("Shield Mk5", missing[0].getName());
    assertEquals("Armor plating Mk5", missing[1].getName());
    assertEquals("Planetary defense turret Mk2", missing[2].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissingTechHull() {
    TechList list = new TechList();
    list.addTech(TechFactory.createHullTech("Large freighter", 6));
    Tech[] missing = list.getListMissingTech(TechType.Hulls, 6);
    assertEquals(2, missing.length);
    assertEquals("Large starbase", missing[0].getName());
    assertEquals("Corvette Mk2", missing[1].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissingTechImprovement() {
    TechList list = new TechList();
    list.addTech(TechFactory.createImprovementTech("VR movie center", 7));
    Tech[] missing = list.getListMissingTech(TechType.Improvements, 7);
    assertEquals(3, missing.length);
    assertEquals("New technology center", missing[0].getName());
    assertEquals("Advanced recycle center", missing[1].getName());
    assertEquals("Starbase nano lab", missing[2].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissingTechPropulsion() {
    TechList list = new TechList();
    list.addTech(TechFactory.createPropulsionTech("Antimatter source Mk2", 8));
    Tech[] missing = list.getListMissingTech(TechType.Propulsion, 8);
    assertEquals(4, missing.length);
    assertEquals("Warp drive Mk6", missing[0].getName());
    assertEquals("Hyper drive Mk6", missing[1].getName());
    assertEquals("Impulse engine Mk2", missing[2].getName());
    assertEquals("Nuclear drive Mk4", missing[3].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMissingTechElectronics() {
    TechList list = new TechList();
    list.addTech(TechFactory.createElectronicsTech("Planetary scanner Mk4", 8));
    Tech[] missing = list.getListMissingTech(TechType.Electrics, 8);
    assertEquals(2, missing.length);
    assertEquals("Cloaking device Mk5", missing[0].getName());
    assertEquals("LR scanner Mk3", missing[1].getName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAddTech() {
    TechList list = new TechList();
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Photon torpedo Mk1", 1));
    assertEquals(2, list.getTechLevel(TechType.Combat));
    list = new TechList();
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Laser Mk2", 2));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Railgun Mk2", 2));
    list.addTech(TechFactory.createCombatTech("Photon torpedo Mk2", 2));
    list.addTech(TechFactory.createCombatTech("Planetary invasion module", 2));
    assertEquals(3, list.getTechLevel(TechType.Combat));
    list = new TechList();
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Laser Mk2", 2));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Railgun Mk2", 2));
    list.addTech(TechFactory.createCombatTech("Planetary invasion module", 2));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Photon torpedo Mk1", 1));
    assertEquals(2, list.getTechLevel(TechType.Combat));
    list.addTech(TechFactory.createCombatTech("Photon torpedo Mk2", 2));
    assertEquals(3, list.getTechLevel(TechType.Combat));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFullList() {
    TechList list = new TechList();
    for (int i = 1; i < 11; i++) {
      String[] names = TechFactory.getListByTechLevel(TechType.Combat, i);
      assertEquals(i, list.getTechLevel(TechType.Combat));
      for (String name : names) {
        list.addTech(TechFactory.createCombatTech(name, i));
      }
      if (i < 10) {
        assertEquals(i+1, list.getTechLevel(TechType.Combat));
      } else {
        assertEquals(i, list.getTechLevel(TechType.Combat));
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarbaseTech() {
    TechList list = new TechList();
    list.addTech(TechFactory.createImprovementTech("Starbase music hall", 2));
    list.addTech(TechFactory.createImprovementTech("Starbase market", 3));
    list.addTech(TechFactory.createImprovementTech("Starbase lab", 4));
    list.addTech(TechFactory.createImprovementTech("Starbase sports hall", 6));
    list.addTech(TechFactory.createImprovementTech("Starbase nano lab", 7));
    list.addTech(TechFactory.createImprovementTech("Starbase bank", 8));
    Tech tech = list.getBestStarbaseLab();
    assertEquals("Starbase nano lab", tech.getName());
    tech = list.getBestStarbaseCredit();
    assertEquals("Starbase bank", tech.getName());
    tech = list.getBestStarbaseCulture();
    assertEquals("Starbase sports hall", tech.getName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTechResearch() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MOTHOIDS, 2, 0);
    info.getTechList().updateResearchPointByTurn(30, info);
    assertEquals(1, info.getTechList().getTechLevel(TechType.Combat));
    assertEquals(MessageType.RESEARCH, info.getMsgList().getMsg().getType());
    info.getMsgList().clearMessages();
    info.getTechList().updateResearchPointByTurn(30, info);
    assertEquals(MessageType.RESEARCH, info.getMsgList().getMsg().getType());
    String msg = info.getMsgList().getMsg().getMessage();
    assertEquals(true, msg.contains(" has advenced"));
    assertEquals(true, msg.contains("next level."));
    assertEquals(2, info.getTechList().getTechLevel(TechType.Combat));
  }

}
