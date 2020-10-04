package org.openRealmOfStars.player.ship;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016, 2017, 2020  Tuomo Untinen
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
* Ship component test
*
*/
public class ShipComponentTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ShipComponent component = new ShipComponent(0, "Test", 10, 5,
        ShipComponentType.ARMOR);
    assertEquals("Test", component.getName());
    assertEquals(0, component.getIndex());
    assertEquals(10, component.getCost());
    assertEquals(5, component.getMetalCost());
    assertEquals(ShipComponentType.ARMOR, component.getType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testArmor() {
    ShipComponent component = new ShipComponent(0, "Armor", 5, 10,
        ShipComponentType.ARMOR);
    assertEquals("Armor", component.getName());
    assertEquals(0, component.getIndex());
    assertEquals(5, component.getCost());
    assertEquals(10, component.getMetalCost());
    assertEquals(ShipComponentType.ARMOR, component.getType());
    assertEquals(0, component.getDefenseValue());
    component.setDefenseValue(1);
    assertEquals(1, component.getDefenseValue());
    assertEquals(false, component.isWeapon());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFighterBay() {
    ShipComponent component = new ShipComponent(0, "Bay", 2, 5,
        ShipComponentType.FIGHTER_BAY);
    assertEquals("Bay", component.getName());
    assertEquals(0, component.getIndex());
    assertEquals(2, component.getCost());
    assertEquals(5, component.getMetalCost());
    assertEquals(ShipComponentType.FIGHTER_BAY, component.getType());
    assertEquals(0, component.getBaySize());
    component.setBaySize(2);
    assertEquals(2, component.getBaySize());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWeapon() {
    ShipComponent component = new ShipComponent(0, "Laser Mk2", 3, 3,
        ShipComponentType.WEAPON_BEAM);
    assertEquals("Laser Mk2", component.getName());
    assertEquals(0, component.getIndex());
    assertEquals(3, component.getCost());
    assertEquals(3, component.getMetalCost());
    assertEquals(ShipComponentType.WEAPON_BEAM, component.getType());
    assertEquals(0, component.getDamage());
    assertEquals(0, component.getWeaponRange());
    assertEquals(0, component.getEnergyRequirement());
    component.setDamage(2);
    component.setWeaponRange(1);
    component.setEnergyRequirement(1);
    assertEquals(2, component.getDamage());
    assertEquals(1, component.getWeaponRange());
    assertEquals(1, component.getEnergyRequirement());
    assertEquals(true, component.isWeapon());
    assertEquals(100, component.getHitChance());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPrivateerModule() {
    ShipComponent component = new ShipComponent(0, "Laser Mk2", 3, 3,
        ShipComponentType.WEAPON_BEAM);
    assertEquals(false, component.isPrivateer());
    component = new ShipComponent(0, "Privateer Module", 3, 3,
        ShipComponentType.PRIVATEERING_MODULE);
    assertEquals(true, component.isPrivateer());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEcm() {
    ShipComponent component = new ShipComponent(0, "ECM", 3, 3,
        ShipComponentType.WEAPON_ECM_TORPEDO);
    assertEquals("ECM", component.getName());
    component.setDamage(2);
    component.setWeaponRange(4);
    component.setEnergyRequirement(0);
    assertEquals("ECM\nCost: 3 Metal: 3\nShield damage: 2 Range: 4\nHit: 50%, damages only shields\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHeMissile() {
    ShipComponent component = new ShipComponent(0, "HE missile", 3, 3,
        ShipComponentType.WEAPON_HE_MISSILE);
    assertEquals("HE missile", component.getName());
    component.setDamage(3);
    component.setWeaponRange(3);
    component.setEnergyRequirement(0);
    assertEquals("HE missile\nCost: 3 Metal: 3\nDamage: 3 Range: 3\nHit: 50%, 50% penetrates shields\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCloackingDevice() {
    ShipComponent component = new ShipComponent(0, "Cloack", 3, 3,
        ShipComponentType.CLOAKING_DEVICE);
    assertEquals("Cloack", component.getName());
    component.setCloaking(40);
    assertEquals("Cloack\nCost: 3 Metal: 3\nCloaking:40\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testJammer() {
    ShipComponent component = new ShipComponent(0, "Jammer", 3, 3,
        ShipComponentType.JAMMER);
    component.setDefenseValue(10);
    assertEquals("Jammer\nCost: 3 Metal: 3\nJammer: -10%\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDistortionShield() {
    ShipComponent component = new ShipComponent(0, "Distortion shield", 8, 3,
        ShipComponentType.DISTORTION_SHIELD);
    component.setDefenseValue(3);
    component.setDamage(5);
    assertEquals("Distortion shield\nCost: 8 Metal: 3\nShield value: 3 Jammer: -5%\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShieldGenerator() {
    ShipComponent component = new ShipComponent(0, "Shield generator", 3, 3,
        ShipComponentType.SHIELD_GENERATOR);
    component.setDefenseValue(1);
    assertEquals("Shield generator\nCost: 3 Metal: 3\nShield generator: 1\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOrbitalBombs() {
    ShipComponent component = new ShipComponent(0, "Bombs", 3, 3,
        ShipComponentType.ORBITAL_BOMBS);
    component.setDamage(10);
    assertEquals("Bombs\nCost: 3 Metal: 3\nOrbital bombs: 10% hit chance\n", component.toString());
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTargetingComputer() {
    ShipComponent component = new ShipComponent(0, "Computer", 3, 3,
        ShipComponentType.TARGETING_COMPUTER);
    component.setDamage(1);
    component.setInitiativeBoost(1);
    assertEquals("Computer\nCost: 3 Metal: 3\nTargeting computer: +1% to hit\nInitiative boost: +1\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInvasionModule() {
    ShipComponent component = new ShipComponent(0, "Invasion module", 3, 3,
        ShipComponentType.PLANETARY_INVASION_MODULE);
    component.setDamage(110);
    assertEquals("Invasion module\nCost: 3 Metal: 3\nTroop combat: 110%\n", component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDrive() {
    ShipComponent component = new ShipComponent(0, "Nuclear Drive Mk1", 3, 3,
        ShipComponentType.ENGINE);
    assertEquals("Nuclear Drive Mk1", component.getName());
    assertEquals(0, component.getIndex());
    assertEquals(3, component.getCost());
    assertEquals(3, component.getMetalCost());
    assertEquals(ShipComponentType.ENGINE, component.getType());
    assertEquals(0, component.getEnergyResource());
    assertEquals(0, component.getFtlSpeed());
    assertEquals(0, component.getTacticSpeed());
    assertEquals(0, component.getSpeed());
    component.setEnergyResource(1);
    component.setFtlSpeed(2);
    component.setTacticSpeed(1);
    component.setSpeed(2);
    assertEquals(1, component.getEnergyResource());
    assertEquals(2, component.getFtlSpeed());
    assertEquals(1, component.getTacticSpeed());
    assertEquals(2, component.getSpeed());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScannerAndCloak() {
    ShipComponent component = new ShipComponent(0, "ScanCloak", 3, 3,
        ShipComponentType.SCANNER);
    assertEquals("ScanCloak", component.getName());
    assertEquals(0, component.getIndex());
    assertEquals(3, component.getCost());
    assertEquals(3, component.getMetalCost());
    assertEquals(ShipComponentType.SCANNER, component.getType());
    assertEquals(0, component.getScannerRange());
    assertEquals(0, component.getCloakDetection());
    assertEquals(0, component.getCloaking());
    component.setScannerRange(5);
    component.setCloakDetection(40);
    component.setCloaking(20);
    assertEquals(5, component.getScannerRange());
    assertEquals(40, component.getCloakDetection());
    assertEquals(20, component.getCloaking());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEspionageModule() {
    ShipComponent component = new ShipComponent(0, "Spy kit", 3, 3,
        ShipComponentType.ESPIONAGE_MODULE);
    assertEquals("Spy kit", component.getName());
    assertEquals(ShipComponentType.ESPIONAGE_MODULE, component.getType());
    assertEquals(0, component.getEspionageBonus());
    component.setEspionageBonus(-5);
    assertEquals(0, component.getEspionageBonus());
    component.setEspionageBonus(15);
    assertEquals(10, component.getEspionageBonus());
    component.setEspionageBonus(3);
    assertEquals(3, component.getEspionageBonus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarbaseThingy() {
    ShipComponent component = new ShipComponent(0, "Module", 3, 3,
        ShipComponentType.STARBASE_COMPONENT);
    assertEquals("Module", component.getName());
    assertEquals(0, component.getResearchBonus());
    assertEquals(0, component.getCreditBonus());
    assertEquals(0, component.getCultureBonus());
    assertEquals(0, component.getFleetCapacityBonus());
    component.setResearchBonus(1);
    component.setCreditBonus(2);
    component.setCultureBonus(3);
    component.setFleetCapacityBonus(4);
    assertEquals(1, component.getResearchBonus());
    assertEquals(2, component.getCreditBonus());
    assertEquals(3, component.getCultureBonus());
    assertEquals(4, component.getFleetCapacityBonus());
    assertEquals("Module\nCost: 3 Metal: 3"
        + "\nCulture bonus: 3\nResearch bonus: 1\nCredit bonus: 2\n"
        + "Fleet capacity bonus: 4\n",
        component.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipCompentTypeIndex() {
    String[] expected = {"Beam weapon", "Railgun", "Photon torpedo",
        "ECM torpedo", "HE missile", "Scanner", "Shield", "Armor",
        "Shield generator", "Engine", "Powersource", "Cloaking device",
        "Jammer", "Targeting computer", "Planetary invasion module",
        "Colony module", "Privateering module", "Orbital bombs",
        "Orbital nuke", "Starbase module", "Espionage module"};
    for (int i = 0; i < 21; i++) {
      ShipComponentType type = ShipComponentType.getTypeByIndex(i);
      assertNotEquals(null, type);
      assertEquals(expected[i], type.toString());
      assertEquals(i, type.getIndex());
    }
  }

}
