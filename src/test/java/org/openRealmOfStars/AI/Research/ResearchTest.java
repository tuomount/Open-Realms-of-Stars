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
  public void testShipDesignHandlingHuman() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    assertEquals(2, info.getShipStatList().length);
    Research.handleShipDesigns(info);
    assertEquals(2, info.getShipStatList().length);
    info.getTechList().addTech(TechFactory.createHullTech("Small freighter", 2));
    info.getTechList().addTech(TechFactory.createHullTech("Small starbase Mk1", 2));
    info.getTechList().addTech(TechFactory.createCombatTech("Planetary invasion module", 2));
    Research.handleShipDesigns(info);
    assertEquals(5, info.getShipStatList().length);
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
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdateDefenseTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createDefenseTech("Shield Mk1", 1));
    list.addTech(TechFactory.createDefenseTech("Armor plating Mk1", 1));
    assertEquals(2, list.getTechLevel(TechType.Defense));
    list.addTech(TechFactory.createDefenseTech("Shield Mk2", 2));
    list.addTech(TechFactory.createDefenseTech("Armor plating Mk2", 2));
    Research.checkUpdateDefense(info, Attitude.MILITARISTIC);
    assertEquals(3, list.getTechLevel(TechType.Defense));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createDefenseTech("Shield Mk1", 1));
    list.addTech(TechFactory.createDefenseTech("Armor plating Mk1", 1));
    list.addTech(TechFactory.createDefenseTech("Shield Mk2", 2));
    list.addTech(TechFactory.createDefenseTech("Armor plating Mk2", 2));
    Research.checkUpdateDefense(info, Attitude.AGGRESSIVE);
    assertEquals(3, list.getTechLevel(TechType.Defense));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createDefenseTech("Shield Mk1", 1));
    list.addTech(TechFactory.createDefenseTech("Armor plating Mk1", 1));
    list.addTech(TechFactory.createDefenseTech("Shield Mk2", 2));
    list.addTech(TechFactory.createDefenseTech("Planetary defense turret Mk1",
        2));
    assertEquals(2, list.getTechLevel(TechType.Defense));
    Research.checkUpdateDefense(info, Attitude.PEACEFUL);
    assertEquals(3, list.getTechLevel(TechType.Defense));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdateHullTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createHullTech("Scout Mk1", 1));
    list.addTech(TechFactory.createHullTech("Colony", 1));
    assertEquals(1, list.getTechLevel(TechType.Hulls));
    Research.checkUpdateHull(info, Attitude.MILITARISTIC);
    assertEquals(1, list.getTechLevel(TechType.Hulls));
    Research.checkUpdateHull(info, Attitude.MERCHANTICAL);
    assertEquals(2, list.getTechLevel(TechType.Hulls));
    list.addTech(TechFactory.createHullTech("Probe", 2));
    list.addTech(TechFactory.createHullTech("Small starbase Mk1", 2));
    Research.checkUpdateHull(info, Attitude.MERCHANTICAL);
    assertEquals(2, list.getTechLevel(TechType.Hulls));
    Research.checkUpdateHull(info, Attitude.DIPLOMATIC);
    assertEquals(3, list.getTechLevel(TechType.Hulls));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createHullTech("Scout Mk1", 1));
    list.addTech(TechFactory.createHullTech("Colony", 1));
    Research.checkUpdateHull(info, Attitude.EXPANSIONIST);
    assertEquals(2, list.getTechLevel(TechType.Hulls));
    list.addTech(TechFactory.createHullTech("Small freighter", 2));
    list.addTech(TechFactory.createHullTech("Small starbase Mk1", 2));
    Research.checkUpdateHull(info, Attitude.MILITARISTIC);
    assertEquals(3, list.getTechLevel(TechType.Hulls));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createHullTech("Scout Mk1", 1));
    list.addTech(TechFactory.createHullTech("Colony", 1));
    Research.checkUpdateHull(info, Attitude.EXPANSIONIST);
    assertEquals(2, list.getTechLevel(TechType.Hulls));
    list.addTech(TechFactory.createHullTech("Small freighter", 2));
    list.addTech(TechFactory.createHullTech("Small starbase Mk1", 2));
    Research.checkUpdateHull(info, Attitude.AGGRESSIVE);
    assertEquals(3, list.getTechLevel(TechType.Hulls));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createHullTech("Scout Mk1", 1));
    list.addTech(TechFactory.createHullTech("Colony", 1));
    Research.checkUpdateHull(info, Attitude.EXPANSIONIST);
    assertEquals(2, list.getTechLevel(TechType.Hulls));
    list.addTech(TechFactory.createHullTech("Small freighter", 2));
    list.addTech(TechFactory.createHullTech("Small starbase Mk1", 2));
    Research.checkUpdateHull(info, Attitude.BACKSTABBING);
    assertEquals(3, list.getTechLevel(TechType.Hulls));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdateImprovementTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createImprovementTech("Barracks", 1));
    list.addTech(TechFactory.createImprovementTech("Tax center", 1));
    assertEquals(1, list.getTechLevel(TechType.Improvements));
    Research.checkUpdateImprovement(info, Attitude.MILITARISTIC);
    assertEquals(1, list.getTechLevel(TechType.Improvements));
    list.addTech(TechFactory.createImprovementTech("Basic lab", 1));
    assertEquals(2, list.getTechLevel(TechType.Improvements));
    list.addTech(TechFactory.createImprovementTech("Advanced farm", 2));
    list.addTech(TechFactory.createImprovementTech("Advanced mine", 2));
    list.addTech(TechFactory.createImprovementTech("Advanced factory", 2));
    Research.checkUpdateImprovement(info, Attitude.DIPLOMATIC);
    assertEquals(2, list.getTechLevel(TechType.Improvements));
    Research.checkUpdateImprovement(info, Attitude.PEACEFUL);
    assertEquals(3, list.getTechLevel(TechType.Improvements));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createImprovementTech("Barracks", 1));
    list.addTech(TechFactory.createImprovementTech("Basic lab", 1));
    assertEquals(1, list.getTechLevel(TechType.Improvements));
    Research.checkUpdateImprovement(info, Attitude.MILITARISTIC);
    assertEquals(2, list.getTechLevel(TechType.Improvements));
    list.addTech(TechFactory.createImprovementTech("Advanced mine", 2));
    list.addTech(TechFactory.createImprovementTech("Advanced factory", 2));
    list.addTech(TechFactory.createImprovementTech("Starbase music hall", 2));
    Research.checkUpdateImprovement(info, Attitude.DIPLOMATIC);
    assertEquals(3, list.getTechLevel(TechType.Improvements));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createImprovementTech("Barracks", 1));
    list.addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.checkUpdateImprovement(info, Attitude.SCIENTIFIC);
    assertEquals(2, list.getTechLevel(TechType.Improvements));
    list.addTech(TechFactory.createImprovementTech("Advanced mine", 2));
    list.addTech(TechFactory.createImprovementTech("Advanced factory", 2));
    list.addTech(TechFactory.createImprovementTech("Starbase music hall", 2));
    Research.checkUpdateImprovement(info, Attitude.MERCHANTICAL);
    assertEquals(3, list.getTechLevel(TechType.Improvements));
    list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createImprovementTech("Barracks", 1));
    list.addTech(TechFactory.createImprovementTech("Basic lab", 1));
    Research.checkUpdateImprovement(info, Attitude.EXPANSIONIST);
    assertEquals(2, list.getTechLevel(TechType.Improvements));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdatePropulsionTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createPropulsionTech("Ion drive Mk1", 1));
    list.addTech(TechFactory.createPropulsionTech("Fission source Mk1", 1));
    list.addTech(TechFactory.createPropulsionTech("Nuclear drive Mk1", 1));
    assertEquals(1, list.getTechLevel(TechType.Propulsion));
    Research.checkUpdatePropulsion(info, Attitude.MILITARISTIC);
    assertEquals(2, list.getTechLevel(TechType.Propulsion));
    list.addTech(TechFactory.createPropulsionTech("Ion drive Mk3", 2));
    list.addTech(TechFactory.createPropulsionTech("Hyper drive Mk1", 2));
    Research.checkUpdatePropulsion(info, Attitude.LOGICAL);
    assertEquals(3, list.getTechLevel(TechType.Propulsion));
    list.addTech(TechFactory.createPropulsionTech("Warp drive Mk1", 3));
    list.addTech(TechFactory.createPropulsionTech("Nuclear drive Mk2", 3));
    Research.checkUpdatePropulsion(info, Attitude.MERCHANTICAL);
    assertEquals(4, list.getTechLevel(TechType.Propulsion));
    list.addTech(TechFactory.createPropulsionTech("Warp drive Mk2", 4));
    list.addTech(TechFactory.createPropulsionTech("Tachyon source Mk1", 4));
    Research.checkUpdatePropulsion(info, Attitude.EXPANSIONIST);
    assertEquals(5, list.getTechLevel(TechType.Propulsion));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdateElectronicsTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList list = new TechList();
    Mockito.when(info.getTechList()).thenReturn(list);
    list.addTech(TechFactory.createElectronicsTech("Scanner Mk1", 1));
    list.addTech(TechFactory.createElectronicsTech("Cloaking device Mk1", 1));
    assertEquals(1, list.getTechLevel(TechType.Electrics));
    Research.checkUpdateElectronics(info, Attitude.AGGRESSIVE);
    assertEquals(2, list.getTechLevel(TechType.Electrics));
    list.addTech(TechFactory.createElectronicsTech("Cloaking device Mk2", 2));
    Research.checkUpdateElectronics(info, Attitude.MILITARISTIC);
    assertEquals(2, list.getTechLevel(TechType.Electrics));
    Research.checkUpdateElectronics(info, Attitude.BACKSTABBING);
    assertEquals(3, list.getTechLevel(TechType.Electrics));
    list.addTech(TechFactory.createElectronicsTech("Jammer Mk1", 3));
    list.addTech(TechFactory.createElectronicsTech("Planetary scanner Mk2", 3));
    Research.checkUpdateElectronics(info, Attitude.MILITARISTIC);
    assertEquals(4, list.getTechLevel(TechType.Electrics));
    list.addTech(TechFactory.createElectronicsTech("LR scanner Mk1", 4));
    list.addTech(TechFactory.createElectronicsTech("Cloaking device Mk3", 4));
    Research.checkUpdateElectronics(info, Attitude.EXPANSIONIST);
    assertEquals(5, list.getTechLevel(TechType.Electrics));
    list.addTech(TechFactory.createElectronicsTech("Planetary scanner Mk3", 5));
    list.addTech(TechFactory.createElectronicsTech("Scanner Mk3", 5));
    Research.checkUpdateElectronics(info, Attitude.LOGICAL);
    assertEquals(6, list.getTechLevel(TechType.Electrics));
  }

}
