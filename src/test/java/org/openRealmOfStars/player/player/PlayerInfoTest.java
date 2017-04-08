package org.openRealmOfStars.player.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.mockito.Mockito;

/**
 * 
 * Open Realm of Stars game project Copyright (C) 2016, 2017 Tuomo Untinen
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

    /*
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
    public void testPlayerInfoHuman() {
        PlayerInfo human = new PlayerInfo(SpaceRace.HUMAN);
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

        ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(human, false));
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /*
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
    public void testPlayerInfoMechions() {
        SpaceRace race = SpaceRace.MECHIONS;
        PlayerInfo human = new PlayerInfo(race);
        TechList techList = human.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = human.getShipStatList();
        ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));

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
        expectedStat = new ShipStat(ShipGenerator.createColony(human, false));
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /*
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
    public void testPlayerInfoCentaurs() {
        SpaceRace race = SpaceRace.CENTAURS;
        PlayerInfo human = new PlayerInfo(race);
        TechList techList = human.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = human.getShipStatList();
        ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));

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
        expectedStat = new ShipStat(ShipGenerator.createColony(human, false));
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /*
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
    public void testPlayerInfoSporks() {
        SpaceRace race = SpaceRace.SPORKS;
        PlayerInfo human = new PlayerInfo(race);
        TechList techList = human.getTechList();
        Tech[] tech = techList.getList();
        ShipStat[] statList = human.getShipStatList();

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

        ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(human, false));
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /*
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
    public void testPlayerInfoGreyans() {
        SpaceRace race = SpaceRace.GREYANS;
        PlayerInfo human = new PlayerInfo(race);
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
        assertEquals(TechType.Propulsion, tech[6].getType());
        assertEquals(1, tech[6].getLevel());
        assertEquals(TechType.Electrics, tech[7].getType());
        assertEquals(1, tech[7].getLevel());

        ShipStat expectedStat = new ShipStat(ShipGenerator.createScout(human));
        assertEquals(expectedStat.toString(), statList[0].toString());
        expectedStat = new ShipStat(ShipGenerator.createColony(human, false));
        assertEquals(expectedStat.toString(), statList[1].toString());
    }

    /*
     * input : sun, fleet output : unchartedSector percent purpose : test
     * getUnchatedValueSystem method
     */
    @Test
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

    /*
     * input : sun, fleet output : pathPoint(5,1) when all sector uncharted
     * pathPoint(1,5) when only (5,1) sector visible pathPoint(5,1) when
     * (5,1),(3,1) sector visible null when all sector visible purpose : test
     * getUnchartedSector method four case
     */
    @Test
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
        PathPoint expect;
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

    /*
     * input : Coordinate output : VISIBLE FOG_OF_WAR UNCHARTED purpose : test
     * getSectorVisibility method
     */
    @Test
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

    /*
     * input : cloakingDetection level, VISIBLE state output : cloakingDetection
     * 0 setting state change VISIBLE to FOG_OF_WAR purpose : test
     * resetVisibilityDataAfterTurn method
     */
    @Test
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
}
