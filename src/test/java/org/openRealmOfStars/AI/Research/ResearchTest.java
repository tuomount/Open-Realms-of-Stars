package org.openRealmOfStars.AI.Research;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
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
 * Tests for AI Research
 */
public class ResearchTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResearchHandlingHuman() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    Research.handle(info);
    assertEquals(Research.VERY_HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    info.getTechList().addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.handle(info);
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Combat));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Defense));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Hulls));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Propulsion));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Electrics));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResearchHandlingCentaurs() {
    PlayerInfo info = new PlayerInfo(SpaceRace.CENTAURS);
    Research.handle(info);
    assertEquals(Research.VERY_HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    info.getTechList().addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.handle(info);
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Combat));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Defense));
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Hulls));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Propulsion));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Electrics));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResearchHandlingGreyans() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    Research.handle(info);
    assertEquals(Research.VERY_HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    info.getTechList().addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.handle(info);
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Combat));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Defense));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Hulls));
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Propulsion));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Electrics));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResearchHandlingSporks() {
    PlayerInfo info = new PlayerInfo(SpaceRace.SPORKS);
    Research.handle(info);
    assertEquals(Research.VERY_HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    info.getTechList().addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.handle(info);
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Combat));
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Defense));
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Hulls));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    assertEquals(Research.LOW_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Propulsion));
    assertEquals(Research.LOW_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Electrics));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResearchHandlingMechions() {
    PlayerInfo info = new PlayerInfo(SpaceRace.MECHIONS);
    Research.handle(info);
    assertEquals(Research.VERY_HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    info.getTechList().addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.handle(info);
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Combat));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Defense));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Hulls));
    assertEquals(Research.DEFAULT_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Improvements));
    assertEquals(Research.HIGH_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Propulsion));
    assertEquals(Research.LOW_FOCUS_LEVEL, 
        info.getTechList().getTechFocus(TechType.Electrics));
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdateCombatTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    Research.checkUpdateCombat(info, Attitude.MILITARISTIC);
    assertEquals(2, list.getTechLevel(TechType.Combat));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    list.addTech(TechFactory.createCombatTech("Photon torpedo Mk1", 1));
    assertEquals(1, list.getTechLevel(TechType.Combat));
    Research.checkUpdateCombat(info, Attitude.AGGRESSIVE);
    assertEquals(2, list.getTechLevel(TechType.Combat));
  }
}
