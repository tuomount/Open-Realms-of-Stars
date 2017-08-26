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
  public void testMissingTech() {
    TechList list = new TechList();
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    String[] missing = list.getListMissingTech(TechType.Combat, 1);
    assertEquals(1, missing.length);
    assertEquals("Photon torpedo Mk1", missing[0]);
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
