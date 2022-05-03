package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.utilities.DiceGenerator;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2022 Tuomo Untinen
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
* Test for planet types
*
*/
public class PlanetTypesTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomType() {
    DiceGenerator.initializeGenerators(4,1,12,15,23,25);
    PlanetTypes type = PlanetTypes.getRandomPlanetType(true, true, true);
    assertEquals(PlanetTypes.GASGIANT1, type);
    type = PlanetTypes.getRandomPlanetType(true, true, true);
    assertEquals(PlanetTypes.WATERWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(true, true, true);
    assertEquals(PlanetTypes.CARBONWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(true, true, true);
    assertEquals(PlanetTypes.IRONWORLD4, type);
    type = PlanetTypes.getRandomPlanetType(true, true, true);
    assertEquals(PlanetTypes.DESERTWORLD2, type);
    type = PlanetTypes.getRandomPlanetType(true, true, true);
    assertEquals(PlanetTypes.IRONWORLD5, type);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomGasGiants() {
    DiceGenerator.initializeGenerators(0,1,2,3);
    PlanetTypes type = PlanetTypes.getRandomPlanetType(true, false, false);
    assertEquals(PlanetTypes.GASGIANT1, type);
    type = PlanetTypes.getRandomPlanetType(true, false, false);
    assertEquals(PlanetTypes.GASGIANT2, type);
    type = PlanetTypes.getRandomPlanetType(true, false, false);
    assertEquals(PlanetTypes.GASGIANT3, type);
    type = PlanetTypes.getRandomPlanetType(true, false, false);
    assertEquals(PlanetTypes.ICEGIANT1, type);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomArtificialPlanets() {
    DiceGenerator.initializeGenerators(0,1,2,3);
    PlanetTypes type = PlanetTypes.getRandomPlanetType(false, false, true);
    assertEquals(PlanetTypes.ARTIFICIALWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(false, false, true);
    assertEquals(PlanetTypes.ARTIFICIALWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(false, false, true);
    assertEquals(PlanetTypes.ARTIFICIALWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(false, false, true);
    assertEquals(PlanetTypes.ARTIFICIALWORLD1, type);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNormalPlanets() {
    DiceGenerator.initializeGenerators(0,1,2,3);
    PlanetTypes type = PlanetTypes.getRandomPlanetType(false, true, false);
    assertEquals(PlanetTypes.SILICONWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(false, true, false);
    assertEquals(PlanetTypes.WATERWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(false, true, false);
    assertEquals(PlanetTypes.WATERWORLD2, type);
    type = PlanetTypes.getRandomPlanetType(false, true, false);
    assertEquals(PlanetTypes.IRONWORLD1, type);
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNormalAndGasPlanets() {
    DiceGenerator.initializeGenerators(0,1,2,4);
    PlanetTypes type = PlanetTypes.getRandomPlanetType(true, true, false);
    assertEquals(PlanetTypes.SILICONWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(true, true, false);
    assertEquals(PlanetTypes.WATERWORLD1, type);
    type = PlanetTypes.getRandomPlanetType(true, true, false);
    assertEquals(PlanetTypes.WATERWORLD2, type);
    type = PlanetTypes.getRandomPlanetType(true, true, false);
    assertEquals(PlanetTypes.GASGIANT1, type);
  }

}
