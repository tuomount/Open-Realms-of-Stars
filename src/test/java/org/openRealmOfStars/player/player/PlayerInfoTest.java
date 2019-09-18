package org.openRealmOfStars.player.player;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.experimental.categories.Category;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.randomEvent.RandomEvent;
import org.mockito.Mockito;

/**
 * 
 * Open Realm of Stars game project 
 * Copyright (C) 2017 GodBeom
 * Copyright (C) 2017-2019 Tuomo Untinen
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
 * Test for PlayerInfo class
 */

public class PlayerInfoTest {

    private static final byte UNCHARTED = 0;
    /**
     * Fog of war, no fleets are drawn
     */
    private static final byte FOG_OF_WAR = 1;
    /**
     * Every thing are drawn
     */
    private static final byte VISIBLE = 2;

    /**
     * input : SpaceRace.HUMAN output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1 }, PlayerInfo's ShipStatList = { scout have random weapon,
     * random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony
     * have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose : test
     * PlayerInfo constructor Human
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoHuman() {
        PlayerInfo human = new PlayerInfo(SpaceRace.HUMAN);
        assertEquals(false, human.isBoard());
        TechList techList = human.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = human.getShipStatList();

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());

        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(human));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(human, false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
        human.removeShipStat(expectedStat);
        statList = human.getShipStatList();
        assertEquals(1, statList.length);
        human.setAttitude(Attitude.PEACEFUL);
        assertEquals(false, human.researchSpyShips());
        human.setAttitude(Attitude.BACKSTABBING);
        assertEquals(true, human.researchSpyShips());

        assertEquals(0, human.getObsoleteShips().length);
        human.getShipStat(0).setObsolete(true);
        assertEquals(1, human.getObsoleteShips().length);
    }

    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoSpacePirate() {
        PlayerInfo pirate = new PlayerInfo(SpaceRace.SPACE_PIRATE);
        assertEquals(true, pirate.isBoard());
        ShipStat[] statList = pirate.getShipStatList();
        assertEquals("Scout Mk1", statList[0].getDesign().getName());
        assertEquals("Small starbase Mk1", statList[1].getDesign().getName());
    }

    /**
     * input : SpaceRace.HOMARIANS output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1 }, PlayerInfo's ShipStatList = { scout have random weapon,
     * random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony
     * have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose : test
     * PlayerInfo constructor Homarian
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoHomarians() {
        PlayerInfo homarian = new PlayerInfo(SpaceRace.HOMARIANS);
        assertEquals(false, homarian.isBoard());
        TechList techList = homarian.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = homarian.getShipStatList();

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());

        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(homarian));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(homarian, false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
        homarian.removeShipStat(expectedStat);
        statList = homarian.getShipStatList();
        assertEquals(1, statList.length);
    }

    /**
     * input : SpaceRace.MECHIONS output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1 }, PlayerInfo's ShipStatList = { scout have random weapon,
     * random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony
     * have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose : test
     * PlayerInfo constructor Mechions
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoMechions() {
        SpaceRace race = SpaceRace.MECHIONS;
        PlayerInfo mechion = new PlayerInfo(race);
        assertEquals(false, mechion.isBoard());
        TechList techList = mechion.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = mechion.getShipStatList();
        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(mechion));

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(mechion,
            false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /**
     * input : SpaceRace.CENTAURS output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1 }, PlayerInfo's ShipStatList = { scout have random weapon,
     * random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony
     * have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose : test
     * PlayerInfo constructor Centaurs
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoCentaurs() {
        SpaceRace race = SpaceRace.CENTAURS;
        PlayerInfo centaur = new PlayerInfo(race);
        assertEquals(false, centaur.isBoard());
        TechList techList = centaur.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = centaur.getShipStatList();
        ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(centaur));

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(centaur,
            false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /**
     * input : SpaceRace.SPORKS output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random weapon in Combat TechType
     * level 1, one random shield or armor in Defense Type level 1, Hulltech
     * level 1 Colony, Hulltech level 1 Scout Mk1, PropulsionTech level 1 Ion
     * drive Mk1, PropulsionTech level 1 Fission source Mk1 }, PlayerInfo's
     * ShipStatList = { scout have random weapon in two weapons, random armor,
     * Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony have Colony
     * Hull, Ion drive Mk1, Fission source Mk1 } purpose : test PlayerInfo
     * constructor Sporks
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoSporks() {
        SpaceRace race = SpaceRace.SPORKS;
        PlayerInfo spork = new PlayerInfo(race);
        assertEquals(false, spork.isBoard());
        TechList techList = spork.getTechList();
        Tech[] tech = techList.getList();

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Combat, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals(TechType.Defense, tech[2].getType());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Colony", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Scout Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Ion drive Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());
        assertEquals("Fission source Mk1", tech[6].getName());
        assertEquals(1, tech[6].getLevel());
    }

    /**
     * input : SpaceRace.GREYANS output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1, one random engine or powersources in propulsion TechType
     * level 1, one random scanners or jammer or cloaking devices in Electronics
     * TechType level 1 }, PlayerInfo's ShipStatList = { scout have random
     * weapon, random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1
     * colony have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose :
     * test PlayerInfo constructor Greyans
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoGreyans() {
        SpaceRace race = SpaceRace.GREYANS;
        PlayerInfo greyan = new PlayerInfo(race);
        assertEquals(false, greyan.isBoard());
        TechList techList = greyan.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = greyan.getShipStatList();
        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());
        assertEquals(TechType.Propulsion, tech[6].getType());
        assertEquals(1, tech[6].getLevel());
        assertEquals(TechType.Electrics, tech[7].getType());
        assertEquals(1, tech[7].getLevel());
        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(greyan));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(greyan, false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /**
     * input : SpaceRace.Mothoids output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, Hulltech level 1 Colony,
     * Hulltech level 1 Scout Mk1, PropulsionTech level 1 Ion drive Mk1,
     * PropulsionTech level 1 Fission source Mk1},
     * PlayerInfo's ShipStatList = { scout have two weapon, Scout Mk1 Hull,
     * Ion drive Mk1, Fission source Mk1, colony have Colony Hull,
     * Ion drive Mk1, Fission source Mk1 } purpose :
     * test PlayerInfo constructor Mothoids
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoMothoids() {
        SpaceRace race = SpaceRace.MOTHOIDS;
        PlayerInfo mothoid = new PlayerInfo(race);
        assertEquals(false, mothoid.isBoard());
        TechList techList = mothoid.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = mothoid.getShipStatList();
        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals("Colony", tech[1].getName());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Scout Mk1", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals(TechType.Improvements, tech[3].getType());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());
        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(mothoid));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(mothoid, false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /**
     * input : SpaceRace.TEUTHIDAE output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1 }, PlayerInfo's ShipStatList = { scout have random weapon,
     * random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony
     * have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose : test
     * PlayerInfo constructor TEUTHIDAE
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoTeuthidae() {
        PlayerInfo teuthidae = new PlayerInfo(SpaceRace.TEUTHIDAES);
        assertEquals(false, teuthidae.isBoard());
        TechList techList = teuthidae.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = teuthidae.getShipStatList();

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Ion drive Mk1", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Fission source Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());

        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(teuthidae));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(teuthidae, false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /**
     * input : SpaceRace.SCAURIANS output : PlayerInfo's techList ={ one random
     * weapon in Combat TechType level 1, one random shield or armor in Defense
     * Type level 1, Hulltech level 1 Colony, Hulltech level 1 Scout Mk1,
     * PropulsionTech level 1 Ion drive Mk1, PropulsionTech level 1 Fission
     * source Mk1, Improvement level 1 Tax center },
     * PlayerInfo's ShipStatList = { scout have random weapon,
     * random armor, Scout Mk1 Hull, Ion drive Mk1, Fission source Mk1 colony
     * have Colony Hull, Ion drive Mk1, Fission source Mk1 } purpose : test
     * PlayerInfo constructor SCAURIANS
     */
    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testPlayerInfoScaurians() {
        PlayerInfo scaurian = new PlayerInfo(SpaceRace.SCAURIANS);
        assertEquals(false, scaurian.isBoard());
        TechList techList = scaurian.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = scaurian.getShipStatList();

        assertEquals(TechType.Combat, tech[0].getType());
        assertEquals(1, tech[0].getLevel());
        assertEquals(TechType.Defense, tech[1].getType());
        assertEquals(1, tech[1].getLevel());
        assertEquals("Colony", tech[2].getName());
        assertEquals(1, tech[2].getLevel());
        assertEquals("Scout Mk1", tech[3].getName());
        assertEquals(1, tech[3].getLevel());
        assertEquals("Tax center", tech[4].getName());
        assertEquals(1, tech[4].getLevel());
        assertEquals("Ion drive Mk1", tech[5].getName());
        assertEquals(1, tech[5].getLevel());
        assertEquals("Fission source Mk1", tech[6].getName());
        assertEquals(1, tech[6].getLevel());

        ShipStat expectedStat = new ShipStat(ShipGenerator
            .createScout(scaurian));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(scaurian, false));
        // Change name back to Mk1
        expectedStat.getDesign().setName("Colony Mk1");
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /**
     * Tests diplomacy fetching from player info.
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testDiplomacy() {
      PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 8, 0);
      info.setAttitude(Attitude.AGGRESSIVE);
      assertEquals(Attitude.AGGRESSIVE, info.getAttitude());
      for (int i = 0; i < 10; i++) {
        Attitude attitude = info.getAiAttitude();
        if (attitude != Attitude.AGGRESSIVE
            && attitude != Attitude.DIPLOMATIC) {
          assertEquals(Attitude.DIPLOMATIC, attitude);
          assertEquals(Attitude.AGGRESSIVE, attitude);
        }
      }
      assertEquals(null, info.getDiplomacy().getDiplomacyList(0));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(1));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(2));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(3));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(4));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(5));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(6));
      assertNotEquals(null, info.getDiplomacy().getDiplomacyList(7));
      assertEquals(0, info.getDiplomacy().getPlayerIndex());
      info = new PlayerInfo(SpaceRace.HUMAN, 8, 5);
      assertEquals(5, info.getDiplomacy().getPlayerIndex());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testWarFatigue() {
      PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 8, 0);
      info.setGovernment(GovernmentType.DEMOCRACY);
      assertEquals(0, info.getTotalWarFatigue());
      info.setWarFatigue(-80);
      assertEquals(-1, info.getTotalWarFatigue());
      info.setWarFatigue(-SpaceRace.HUMAN.getWarFatigueResistance() * 7);
      assertEquals(-6, info.getTotalWarFatigue());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testBoard() {
      PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 8, 0);
      assertEquals(false, info.isBoard());
      assertEquals(false, info.isHuman());
      info.setBoard(true);
      assertEquals(true, info.isBoard());
      info.setHuman(true);
      assertEquals(true, info.isHuman());
    }

    /**
     * Tests Government and war fatigue
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGovernment() {
      PlayerInfo info = new PlayerInfo(SpaceRace.SCAURIANS, 8, 0);
      assertEquals(GovernmentType.AI, info.getGovernment());
      info.setGovernment(GovernmentType.DEMOCRACY);
      assertEquals(GovernmentType.DEMOCRACY, info.getGovernment());
      assertEquals(0, info.getWarFatigue());
      info.setWarFatigue(5);
      assertEquals(5, info.getWarFatigue());
    }

    /**
     * Tests espionage fetching from player info.
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testEspionage() {
      PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 8, 0);
      assertNotNull(info.getEspionage());
      assertNotNull(info.getEspionage().getByIndex(0));
      assertNotEquals(null, info.getEspionage().getByIndex(1));
      assertNotEquals(null, info.getEspionage().getByIndex(2));
      assertNotEquals(null, info.getEspionage().getByIndex(3));
      assertNotEquals(null, info.getEspionage().getByIndex(4));
      assertNotEquals(null, info.getEspionage().getByIndex(5));
      assertNotEquals(null, info.getEspionage().getByIndex(6));
      assertNotEquals(null, info.getEspionage().getByIndex(7));
    }

    /**
     * Tests espionage fetching from player info.
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testRandomEvent() {
      PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 8, 0);
      assertNull(info.getRandomEventOccured());
      RandomEvent event = Mockito.mock(RandomEvent.class);
      info.setRandomEventOccured(event);
      assertEquals(event, info.getRandomEventOccured());
      info.setRandomEventOccured(null);
      assertNull(info.getRandomEventOccured());
    }

    
    /**
     * input : sun, fleet output : unchartedSector percent purpose : test
     * getUnchatedValueSystem method
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGetUnchartedValueSystem() {
        Sun sun = Mockito.mock(Sun.class);
        Fleet fleet = Mockito.mock(Fleet.class);
        Mockito.when(sun.getCenterX()).thenReturn(3);
        Mockito.when(sun.getCenterY()).thenReturn(3);
        int maxX = 256, maxY = 256;
        PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
        player.initMapData(maxX, maxY);
        int expect = 0;
        expect = 100;
        int result = player.getUnchartedValueSystem(sun, fleet);
        assertEquals(expect, result);

        player.setSectorVisibility(7, 7, FOG_OF_WAR);
        expect = 99;
        result = player.getUnchartedValueSystem(sun, fleet);
        assertEquals(expect, result);
    }

    /**
     * input : sun, fleet output : pathPoint(5,1) when all sector uncharted
     * pathPoint(1,5) when only (5,1) sector visible pathPoint(5,1) when
     * (5,1),(3,1) sector visible null when all sector visible purpose : test
     * getUnchartedSector method four case
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGetUnchartedSector() {
        Sun sun = Mockito.mock(Sun.class);
        Fleet fleet = Mockito.mock(Fleet.class);
        Mockito.when(sun.getCenterX()).thenReturn(3);
        Mockito.when(sun.getCenterY()).thenReturn(3);
        Mockito.when(fleet.getX()).thenReturn(2);
        Mockito.when(fleet.getY()).thenReturn(2);
        Mockito.when(fleet.getCoordinate()).thenReturn(new Coordinate(2, 2));
        Mockito.when(fleet.getFleetScannerLvl()).thenReturn(0);
        PathPoint result;
        int maxX = 10, maxY = 10;
        PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
        player.initMapData(maxX, maxY);
        result = player.getUnchartedSector(sun, fleet);
        assertEquals(5, result.getX());
        assertEquals(1, result.getY());
        player.setSectorVisibility(5, 1, VISIBLE);
        result = player.getUnchartedSector(sun, fleet);
        assertEquals(1, result.getX());
        assertEquals(5, result.getY());
        player.setSectorVisibility(3, 1, VISIBLE);
        result = player.getUnchartedSector(sun, fleet);
        assertEquals(5, result.getX());
        assertEquals(3, result.getY());
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                player.setSectorVisibility(i, j, VISIBLE);
            }
        }
        result = player.getUnchartedSector(sun, fleet);
        assertEquals(null, result);
    }

    /**
     * input : Coordinate output : VISIBLE FOG_OF_WAR UNCHARTED purpose : test
     * getSectorVisibility method
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testGetSectorVisibility() {
        Coordinate coord = Mockito.mock(Coordinate.class);
        Mockito.when(coord.getX()).thenReturn(0);
        Mockito.when(coord.getY()).thenReturn(0);
        PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
        player.initMapData(10, 10);
        player.setSectorVisibility(0, 0, VISIBLE);
        byte result = player.getSectorVisibility(coord);
        assertEquals(VISIBLE, result);

        player.setSectorVisibility(0, 0, FOG_OF_WAR);
        result = player.getSectorVisibility(coord);
        assertEquals(FOG_OF_WAR, result);

        player.setSectorVisibility(0, 0, UNCHARTED);
        result = player.getSectorVisibility(coord);
        assertEquals(UNCHARTED, result);
    }

    /**
     * input : cloakingDetection level, VISIBLE state output : cloakingDetection
     * 0 setting state change VISIBLE to FOG_OF_WAR purpose : test
     * resetVisibilityDataAfterTurn method
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testResetVisibilityDataAfterTurn() {
        Coordinate coord = Mockito.mock(Coordinate.class);
        Mockito.when(coord.getX()).thenReturn(1);
        Mockito.when(coord.getY()).thenReturn(1);
        PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN);
        player.initMapData(10, 10);
        player.setSectorCloakingDetection(4, 4, 1);
        player.setSectorCloakingDetection(5, 5, 1);
        player.setSectorVisibility(1, 1, VISIBLE);
        player.resetVisibilityDataAfterTurn();
        int cloakResult;
        cloakResult = player.getSectorCloakDetection(4, 4);
        assertEquals(0, cloakResult);
        cloakResult = player.getSectorCloakDetection(5, 5);
        assertEquals(0, cloakResult);
        byte stateResult;
        stateResult = player.getSectorVisibility(coord);
        assertEquals(FOG_OF_WAR, stateResult);
    }
    
    /**
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testCheckDuplicateShipDesign() {
      PlayerInfo player = new PlayerInfo(SpaceRace.SCAURIANS, 2, 0);
      ShipDesign design1 = Mockito.mock(ShipDesign.class);
      Mockito.when(design1.getName()).thenReturn("Ship 1");
      ShipStat stat1 = Mockito.mock(ShipStat.class);
      Mockito.when(stat1.getDesign()).thenReturn(design1);
      ShipDesign design2 = Mockito.mock(ShipDesign.class);
      Mockito.when(design2.getName()).thenReturn("Ship 2");
      ShipStat stat2 = Mockito.mock(ShipStat.class);
      Mockito.when(stat2.getDesign()).thenReturn(design2);
      player.addShipStat(stat1);
      player.addShipStat(stat2);
      assertEquals(true, player.duplicateShipDesignName("Ship 1"));
      assertEquals(true, player.duplicateShipDesignName("Ship 2"));
      assertEquals(false, player.duplicateShipDesignName("Ship 3"));
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testFakeMilitarySetting() {
      PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
      player.setAttitude(Attitude.DIPLOMATIC);
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.MECHIONS, 2, 0);
      player.setAttitude(Attitude.LOGICAL);
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(90, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(80, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(50, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.SPORKS, 2, 0);
      player.setAttitude(Attitude.AGGRESSIVE);
      assertEquals(120, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(130, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(80, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(70, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.GREYANS, 2, 0);
      player.setAttitude(Attitude.SCIENTIFIC);
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.MOTHOIDS, 2, 0);
      player.setAttitude(Attitude.EXPANSIONIST);
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(130, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(140, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(150, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(150, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.TEUTHIDAES, 2, 0);
      player.setAttitude(Attitude.MILITARISTIC);
      assertEquals(120, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(150, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(170, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(200, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(200, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.SCAURIANS, 2, 0);
      player.setAttitude(Attitude.MERCHANTICAL);
      assertEquals(110, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(130, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(130, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(140, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(140, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.HOMARIANS, 2, 0);
      player.setAttitude(Attitude.PEACEFUL);
      assertEquals(100, player.getFakeMilitarySetting(GameLengthState.START_GAME));
      assertEquals(140, player.getFakeMilitarySetting(GameLengthState.EARLY_GAME));
      assertEquals(140, player.getFakeMilitarySetting(GameLengthState.MIDDLE_GAME));
      assertEquals(150, player.getFakeMilitarySetting(GameLengthState.LATE_GAME));
      assertEquals(150, player.getFakeMilitarySetting(GameLengthState.END_GAME));
      player = new PlayerInfo(SpaceRace.TEUTHIDAES, 2, 0);
      player.setAttitude(Attitude.MILITARISTIC);
      player.setFakeMilitarySize(160);
      player.tuneFakeMilitarySetting(GameLengthState.END_GAME);
      assertEquals(170, player.getFakeMilitarySize());
      player.tuneFakeMilitarySetting(GameLengthState.END_GAME);
      assertEquals(180, player.getFakeMilitarySize());
      player.tuneFakeMilitarySetting(GameLengthState.END_GAME);
      assertEquals(190, player.getFakeMilitarySize());
      player.tuneFakeMilitarySetting(GameLengthState.END_GAME);
      assertEquals(200, player.getFakeMilitarySize());
      player.tuneFakeMilitarySetting(GameLengthState.END_GAME);
      assertEquals(200, player.getFakeMilitarySize());
      player.tuneFakeMilitarySetting(GameLengthState.START_GAME);
      assertEquals(190, player.getFakeMilitarySize());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testFakeMilitaryCost() {
      PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
      player.setTotalCredits(0);
      player.setFakeMilitarySize(200);
      player.handleFakeMilitarySizeCost();
      assertEquals(120, player.getFakeMilitarySize());
      player = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
      player.setTotalCredits(0);
      player.setFakeMilitarySize(50);
      player.handleFakeMilitarySizeCost();
      assertEquals(80, player.getFakeMilitarySize());
    }

}
