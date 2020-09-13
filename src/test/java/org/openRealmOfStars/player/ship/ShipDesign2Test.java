package org.openRealmOfStars.player.ship;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017 wksdn18
 * Copyright (C) 2017, 2020 Tuomo Untinen
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
 * Class for handling text without repainting content
 *
 */

public class ShipDesign2Test {

    /**
     * addComponentTest purpose : Make sure Component ADD input : ShipComponent
     * weapon Expected : ShipDesign.getComponent(0) == weapon
     */
    @Test
    public void addComponentTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        design.addComponent(weapon);
        assertEquals(weapon, design.getComponent(0));
    }

    /**
     * getNumberOfComponentTest purpose : Make sure getNumber of Component input
     * : ShipComponent weapon Expected : ShipDesign.getNumberOfComponents() == 1
     */
    @Test
    public void getNumberOfComponentsTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        design.addComponent(weapon);
        assertEquals(1, design.getNumberOfComponents());
    }

    /**
     * getComponetTest purpose : Make sure getComponent is equal to added
     * component input : getComponent NUll index getComponent INVLID index
     * ShipComponent weapon expected : ShipDesign.getComponnet(-1) == NULL
     * ShipDesign.getComponnet(0) == NULL ShipDesign.getComponent(0) == weapon
     */
    @Test
    public void getComponentTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        assertNull(design.getComponent(-1));
        assertNull(design.getComponent(0));

        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        design.addComponent(weapon);
        assertEquals(weapon, design.getComponent(0));
    }

    /**
     * removeComponentTest purpose : Make sure removeCompoennt is executed input
     * : ShipComponent weapon expected : ShipDesign.getComponent(0) == NULL
     */
    @Test
    public void removeComponetTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        design.addComponent(weapon);
        design.removeComponent(0);
        assertNull(design.getComponent(0));
    }

    /**
     * getName,setName,gethullTest purpose : Make sure getName,SetName,gethul;
     * is executed input : Initiative name "Design" SetName "TestDesign"
     * ShipHull shiphull expected : ShipDesign.getName == "Design"
     * ShipDesign.getName == "TestDesign" ShipDesign.getHull == shiphull
     */
    @Test
    public void nameTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        assertEquals("Design", design.getName());
        design.setName("TestDesign");
        assertEquals("TestDesign", design.getName());
        assertEquals(shiphull, design.getHull());
    }

    /**
     * getFreeEnergyTest purpose : Make sure getFreeEnery is executed input :
     * ShipCompoennt weapon ( WEAPON_ENERGY_REQUIREMENT is 1 ) ShipCompoennt
     * engine ( ENGINE_ENERGY_RESOURCE is 1 ) expected :
     * ShipDesign.getFreeEnergy() is -1; ShipDesign.getFreeEnergy() is 0;
     */
    @Test
    public void getFreeEnergyTest() {
        final int WEAPON_ENERGY_REQUIREMENT = 1;
        final int ENGINE_ENERGY_RESOURCE = 1;
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);

        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon.getEnergyRequirement()).thenReturn(WEAPON_ENERGY_REQUIREMENT);
        ShipComponent engine = Mockito.mock(ShipComponent.class);
        Mockito.when(engine.getEnergyResource()).thenReturn(ENGINE_ENERGY_RESOURCE);
        design.addComponent(weapon);
        assertEquals(-1, design.getFreeEnergy());
        design.addComponent(engine);
        assertEquals(0, design.getFreeEnergy());
    }

    /**
     * getTotalShieldTest purpose : Make Sure getTotal is executed input :
     * ShipComponent shield ( SHIELD_VALUE is 1 ) expected :
     * ShipDesign.getTotalShield == 1
     */
    @Test
    public void getTotalShieldTest() {
        final int SHIELD_VALUE = 1;

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent shield = Mockito.mock(ShipComponent.class);
        Mockito.when(shield.getType()).thenReturn(ShipComponentType.SHIELD);
        Mockito.when(shield.getDefenseValue()).thenReturn(SHIELD_VALUE);
        assertEquals(0, design.getTotalShield());
        design.addComponent(shield);
        assertEquals(1, design.getTotalShield());
    }

    /**
     * gotCertainTypeTest purpose : Make sure getCertainType is executed input :
     * ShipComponent shield ( TYPE is SHIELD ) expected :
     * ShipDesign.getCertainType == ShipCompoenntType.SHIELD
     */
    @Test
    public void gotCertainTypeTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent shield = Mockito.mock(ShipComponent.class);
        Mockito.when(shield.getType()).thenReturn(ShipComponentType.SHIELD);
        assertEquals(design.gotCertainType(ShipComponentType.SHIELD), false);
        design.addComponent(shield);
        assertTrue(design.gotCertainType(ShipComponentType.SHIELD));
    }

    /**
     * getTotalArmorTest purpose : Make sure getTotalArmorTest is executed input
     * : ShipComponent armor ( ARMOR_VALUE is 1 ) expected :
     * ShipDesign.getTotalArmor == 1
     */
    @Test
    public void getTotalArmorTest() {
        final int ARMOR_VALUE = 1;

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent armor = Mockito.mock(ShipComponent.class);
        Mockito.when(armor.getType()).thenReturn(ShipComponentType.ARMOR);
        Mockito.when(armor.getDefenseValue()).thenReturn(ARMOR_VALUE);
        assertEquals(0, design.getTotalArmor());
        design.addComponent(armor);
        assertEquals(1, design.getTotalArmor());
    }

    /**
     * hasWeaponsTest purpose : Make sure hasWeapon is executed input :
     * ShipComponent weapon ( ShipComponentType.WEAPON_BEAM ) ShipComponent
     * weapon2 ( ShipComponentType.WEAPON_ECM_TORPEDO ) ShipComponent weapon3 (
     * ShipComponentType.WEAPON_HE_MISSILE ) ShipComponent weapon4 (
     * ShipComponentType.WEAPON_PHOTON_TORPEDO ) ShipComponent weapon5 (
     * ShipComponentType.WEAPON_RAILGUN )
     * 
     * expected : ShipDesign.hasWeapons() == true
     */
    @Test
    public void hasWeaponsTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon.getType()).thenReturn(ShipComponentType.WEAPON_BEAM);
        assertEquals(design.hasWeapons(), false);
        design.addComponent(weapon);
        assertTrue(design.hasWeapons());
        design.removeComponent(0);

        ShipComponent weapon2 = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon2.getType()).thenReturn(ShipComponentType.WEAPON_ECM_TORPEDO);
        design.addComponent(weapon2);
        assertTrue(design.hasWeapons());
        design.removeComponent(0);

        ShipComponent weapon3 = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon3.getType()).thenReturn(ShipComponentType.WEAPON_HE_MISSILE);
        design.addComponent(weapon3);
        assertTrue(design.hasWeapons());
        design.removeComponent(0);

        ShipComponent weapon4 = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon4.getType()).thenReturn(ShipComponentType.WEAPON_PHOTON_TORPEDO);
        design.addComponent(weapon4);
        assertTrue(design.hasWeapons());
        design.removeComponent(0);

        ShipComponent weapon5 = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon5.getType()).thenReturn(ShipComponentType.WEAPON_RAILGUN);
        design.addComponent(weapon5);
        assertTrue(design.hasWeapons());
        design.removeComponent(0);
    }

    /**
     * hasEngineTest purpose : Make sure hasEngine is executed input :
     * ShipComponent weapon ( ShipComponentType.ENGINE ) expected :
     * ShipDesign.hasEngine() == true
     */
    @Test
    public void hasEngineTest() {
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent engine = Mockito.mock(ShipComponent.class);
        Mockito.when(engine.getType()).thenReturn(ShipComponentType.ENGINE);
        assertEquals(design.hasEngine(), false);
        design.addComponent(engine);
        assertTrue(design.hasEngine());
    }

    /**
     * getFreeSlotsTest purpose : Make sure getFreeSlots is executed input :
     * ShipComponent shipcomponent1,shipcomponent2 expected :
     * ShipDesign.getFreeSlots == 8
     */
    @Test
    public void getFreeSlotsTest() {
        final int MAX_SLOT = 10;
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        Mockito.when(shiphull.getMaxSlot()).thenReturn(MAX_SLOT);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent shipcomponent1 = Mockito.mock(ShipComponent.class);
        ShipComponent shipcomponent2 = Mockito.mock(ShipComponent.class);
        design.addComponent(shipcomponent1);
        design.addComponent(shipcomponent2);
        assertEquals(8, design.getFreeSlots());
    }

    /**
     * getCostTest purpose : Make sure getCost is executed input : ShipHull
     * shiphull ( HULL_COST is 1 ) ShipComponent weapon ( WEAPON_COST is 5 )
     * ShipComponent engine ( ENGINE_COST is 5 ) expected : ShipDesign.getCost
     * == TOTAL_COST ( 16 )
     */
    @Test
    public void getCostTest() {
        final int HULL_COST = 1;
        final int WEAPON_COST = 5;
        final int ENGINE_COST = 10;
        final int TOTAL_COST = 16;

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        Mockito.when(shiphull.getCost()).thenReturn(HULL_COST);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        ShipComponent engine = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon.getCost()).thenReturn(WEAPON_COST);
        Mockito.when(engine.getCost()).thenReturn(ENGINE_COST);
        design.addComponent(weapon);
        design.addComponent(engine);
        assertEquals(TOTAL_COST, design.getCost());
    }

    /**
     * getMetalCostTest purpose : Make sure getMetalCost is executed input :
     * ShipHull shiphull ( HULL_METAL_COST is 1 ) ShipComponent weapon (
     * WEAPON_METAL_COST is 5 ) ShipComponent engine ( ENGINE_METAL_COST is 5 )
     * expected : ShipDesign.getMetalCost() == TOTAL_METAL_COST ( 6 )
     */
    @Test
    public void getMetalCostTest() {
        final int HULL_METAL_COST = 1;
        final int WEAPON_METAL_COST = 2;
        final int ENGINE_METAL_COST = 3;
        final int TOTAL_METAL_COST = 6;

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        Mockito.when(shiphull.getMetalCost()).thenReturn(HULL_METAL_COST);
        ShipDesign design = new ShipDesign(shiphull);
        ShipComponent weapon = Mockito.mock(ShipComponent.class);
        ShipComponent engine = Mockito.mock(ShipComponent.class);
        Mockito.when(weapon.getMetalCost()).thenReturn(WEAPON_METAL_COST);
        Mockito.when(engine.getMetalCost()).thenReturn(ENGINE_METAL_COST);
        design.addComponent(weapon);
        design.addComponent(engine);
        assertEquals(TOTAL_METAL_COST, design.getMetalCost());
    }

    /**
     * getTotalMilitaryPowerTest purpose : Make sure getTotalMilitaryPower is
     * executed input : ShipHull shiphull ( SLUT_HULL is 5 ) ShipComponent
     * weaponBeam ( WEAPON_BEAM_POWER is 2 ) ShipComponent weaponEcmTorpedo (
     * WEAPON_ECM_TORPEDO_POWER is 4 ) ShipComponent shield ( SHIELD_VALUE is 2
     * ) ShipComponent armor ( ARMOR_VALUE is 4 ) ShipComponent engine (
     * ENGINE_VALUE is 2 ) ShipComponent targettingComputer (
     * TARGETING_COMPUTER_VALUE is 10 ) ShipComponent jammer ( JAMMER_VALUE is
     * 10 ) expected : ShipDesign.getTotalMilitaryPower() == TOTAL_POWER ( 41 )
     */
    @Test
    public void getTotalMilitaryPowerTest() {
        final int SLUT_HULL = 5;

        final int WEAPON_BEAM_POWER = 2;
        final int WEAPON_ECM_TORPEDO_POWER = 4;
        final int SHIELD_VALUE = 2;
        final int ARMOR_VALUE = 4;
        final int ENGINE_VALUE = 2;
        final int TARGETING_COMPUTER_VALUE = 10;
        final int JAMMER_VALUE = 10;

        final int NO_MILITARY_SHIP_POWER = 0;
        final int TOTAL_POWER = SLUT_HULL * 7 + WEAPON_BEAM_POWER + WEAPON_ECM_TORPEDO_POWER / 2 + SHIELD_VALUE
                + ARMOR_VALUE + (ENGINE_VALUE - 1) + TARGETING_COMPUTER_VALUE / 10 + JAMMER_VALUE / 5;

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        Mockito.when(shiphull.getSlotHull()).thenReturn(SLUT_HULL);
        ShipDesign design = new ShipDesign(shiphull);
        assertEquals(design.getTotalMilitaryPower(), NO_MILITARY_SHIP_POWER);

        ShipComponent weaponBeam = Mockito.mock(ShipComponent.class);
        Mockito.when(weaponBeam.getType()).thenReturn(ShipComponentType.WEAPON_BEAM);
        Mockito.when(weaponBeam.getDamage()).thenReturn(WEAPON_BEAM_POWER);

        ShipComponent weaponEcmTorpedo = Mockito.mock(ShipComponent.class);
        Mockito.when(weaponEcmTorpedo.getType()).thenReturn(ShipComponentType.WEAPON_ECM_TORPEDO);
        Mockito.when(weaponEcmTorpedo.getDamage()).thenReturn(WEAPON_ECM_TORPEDO_POWER);

        ShipComponent shield = Mockito.mock(ShipComponent.class);
        Mockito.when(shield.getType()).thenReturn(ShipComponentType.SHIELD);
        Mockito.when(shield.getDefenseValue()).thenReturn(SHIELD_VALUE);

        ShipComponent armor = Mockito.mock(ShipComponent.class);
        Mockito.when(armor.getType()).thenReturn(ShipComponentType.ARMOR);
        Mockito.when(armor.getDefenseValue()).thenReturn(ARMOR_VALUE);

        ShipComponent engine = Mockito.mock(ShipComponent.class);
        Mockito.when(engine.getType()).thenReturn(ShipComponentType.ENGINE);
        Mockito.when(engine.getTacticSpeed()).thenReturn(ENGINE_VALUE);

        ShipComponent targettingComputer = Mockito.mock(ShipComponent.class);
        Mockito.when(targettingComputer.getType()).thenReturn(ShipComponentType.TARGETING_COMPUTER);
        Mockito.when(targettingComputer.getDamage()).thenReturn(TARGETING_COMPUTER_VALUE);

        ShipComponent jammer = Mockito.mock(ShipComponent.class);
        Mockito.when(jammer.getType()).thenReturn(ShipComponentType.JAMMER);
        Mockito.when(jammer.getDamage()).thenReturn(JAMMER_VALUE);

        design.addComponent(weaponBeam);
        design.addComponent(weaponEcmTorpedo);
        design.addComponent(shield);
        design.addComponent(armor);
        design.addComponent(engine);
        design.addComponent(targettingComputer);
        design.addComponent(jammer);

        assertEquals(TOTAL_POWER, design.getTotalMilitaryPower());
    }

    /**
     * getTotalTrooperPowerTest purpose : Make sure getTotalTrooperPower is
     * executed input : ShipHull shiphull ( SLUT_HULL is 5 , MAX_SLOT is 10)
     * ShipComponent invasionModule ( PLANETARY_INVASION_MODULE_DAMAGE is 5 )
     * ShipComponent weaponEcmTorpedo ( WEAPON_ECM_TORPEDO_POWER is 4 )
     * ShipComponent shield ( SHIELD_VALUE is 2 ) ShipComponent armor (
     * ARMOR_VALUE is 4 ) ShipComponent engine ( ENGINE_VALUE is 2 ) expected :
     * ShipDesign.getTotalTrooperPower() == TOTAL_POWER ( 31 )
     */
    @Test
    public void getTotalTrooperPowerTest() {
        final int SLUT_HULL = 5;
        final int MAX_SLOT = 10;

        final int PLANETARY_INVASION_MODULE_DAMAGE = 5;
        final int SHIELD_VALUE = 2;
        final int ARMOR_VALUE = 4;
        final int ENGINE_VALUE = 2;

        final int NO_TROOPER_SHIP_POWER = 0;
        final int TOTAL_POWER = SLUT_HULL * 4 + PLANETARY_INVASION_MODULE_DAMAGE + SHIELD_VALUE + ARMOR_VALUE
                + (MAX_SLOT - 4) + (ENGINE_VALUE);

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        Mockito.when(shiphull.getSlotHull()).thenReturn(SLUT_HULL);
        Mockito.when(shiphull.getMaxSlot()).thenReturn(MAX_SLOT);
        ShipDesign design = new ShipDesign(shiphull);
        assertEquals(NO_TROOPER_SHIP_POWER, design.getTotalTrooperPower());

        ShipComponent invasionModule = Mockito.mock(ShipComponent.class);
        Mockito.when(invasionModule.getType()).thenReturn(ShipComponentType.PLANETARY_INVASION_MODULE);
        Mockito.when(invasionModule.getDamage()).thenReturn(PLANETARY_INVASION_MODULE_DAMAGE);

        ShipComponent shield = Mockito.mock(ShipComponent.class);
        Mockito.when(shield.getType()).thenReturn(ShipComponentType.ARMOR);
        Mockito.when(shield.getDefenseValue()).thenReturn(ARMOR_VALUE);

        ShipComponent armor = Mockito.mock(ShipComponent.class);
        Mockito.when(armor.getType()).thenReturn(ShipComponentType.SHIELD);
        Mockito.when(armor.getDefenseValue()).thenReturn(SHIELD_VALUE);

        ShipComponent engine = Mockito.mock(ShipComponent.class);
        Mockito.when(engine.getType()).thenReturn(ShipComponentType.ENGINE);
        Mockito.when(engine.getEnergyResource()).thenReturn(ENGINE_VALUE);

        design.addComponent(invasionModule);
        design.addComponent(shield);
        design.addComponent(armor);
        design.addComponent(engine);

        assertEquals(TOTAL_POWER, design.getTotalTrooperPower());

        ShipHull shiphull2 = Mockito.mock(ShipHull.class);
        Mockito.when(shiphull2.getSlotHull()).thenReturn(SLUT_HULL);
        Mockito.when(shiphull2.getMaxSlot()).thenReturn(1);
        ShipDesign design2 = new ShipDesign(shiphull2);
        ShipComponent invasionModule2 = Mockito.mock(ShipComponent.class);
        Mockito.when(invasionModule2.getType()).thenReturn(ShipComponentType.PLANETARY_INVASION_MODULE);
        Mockito.when(invasionModule2.getDamage()).thenReturn(PLANETARY_INVASION_MODULE_DAMAGE);
        design2.addComponent(invasionModule2);
        assertEquals(NO_TROOPER_SHIP_POWER, design2.getTotalTrooperPower());

    }

    /**
     * getComponentListTest purpose : Make sure getComponentList is executed
     * input : ShipComponent[] testResult ( engine,shield,armor) expected :
     * ShipDesign.getComponentList() == testResult
     */
    @Test
    public void getComponentListTest() {
        final int COMPONENT_NUM = 3;
        ShipComponent[] testResult = new ShipComponent[COMPONENT_NUM];

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);

        ShipComponent engine = Mockito.mock(ShipComponent.class);
        ShipComponent shield = Mockito.mock(ShipComponent.class);
        ShipComponent armor = Mockito.mock(ShipComponent.class);

        testResult[0] = engine;
        testResult[1] = shield;
        testResult[2] = armor;

        design.addComponent(engine);
        design.addComponent(shield);
        design.addComponent(armor);

        assertEquals(testResult.length, design.getComponentList().length);
        for (int i = 0; i < testResult.length; i++) {
          assertEquals(testResult[i], design.getComponent(i));
        }
    }

    /**
     * changePriorityTest purpose : Make sure changePriority is executed input :
     * UNVALID_INDEX is 3 TEST_INDEX is 1 ShipComponent engine ShipComponent
     * shield ShipComponent armor expected : ShipDesign.changePriority(
     * UNVALID_INDEX, true ) == UNVALID_INDEX (3) ShipDesign.changePriority(
     * TEST_INDEX, true ) == 0 ShipDesign.changePriority( TEST_INDEX, false ) ==
     * 2
     */
    @Test
    public void changePriorityTest() {
        final int TEST_INDEX = 1;
        final int UNVALID_INDEX = 3;

        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipDesign design = new ShipDesign(shiphull);

        ShipComponent engine = Mockito.mock(ShipComponent.class);
        ShipComponent shield = Mockito.mock(ShipComponent.class);
        ShipComponent armor = Mockito.mock(ShipComponent.class);

        design.addComponent(engine);
        design.addComponent(shield);
        design.addComponent(armor);

        assertEquals(UNVALID_INDEX, design.changePriority(UNVALID_INDEX, true));
        assertEquals(0, design.changePriority(TEST_INDEX, true));
        assertEquals(2, design.changePriority(TEST_INDEX, false));

    }

}
