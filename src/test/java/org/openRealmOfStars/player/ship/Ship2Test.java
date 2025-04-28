package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
 * Copyright (C) 2017 Lucas
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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;

import static org.junit.Assert.*;

/**
 * Test for Ship class using mock
 */

public class Ship2Test {

    final String SHIP_DESIGN_NAME = "shipDesignNo1";
    final String SHIP_DESIGN_INFO = "DesignInfoNo1";

    final int SHIP_HULL_SLOT = 5;
    final int SHIP_DESIGN_COST = 100;
    final int SHIP_DESIGN_METALCOST = 50;
    final int SHIP_DESIGN_TOTALSHIELD = 20;
    final int SHIP_DESIGN_TOTALARMOR = 10;
    final int SHIP_IMAGE_INDEX = 0;
    final int SHIP_COMPONENT_INDEX = 0;

    /**
     * Purpose: Make sure the constructor works correctly about the inherited
     * arguments in Construction.class Input: ship = new Ship(final ShipDesign
     * design) Expected: design.getName() == ship.getName() design.getProdCost()
     * == ship.getCost() design.getMetalCost() == ship.getMetalCost()
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestConstructorInheritedArgu() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(SHIP_DESIGN_NAME, ship.getName());
        assertEquals(SHIP_DESIGN_COST, ship.getProdCost());
        assertEquals(SHIP_DESIGN_METALCOST, ship.getMetalCost());
        // assertEquals(SHIP_DESIGN_INFO, ship.getDescription());
    }

    /**
     * Purpose: Make sure the constructor works correctly about the InnerClass
     * named ShipHull in ShipDesign.class Input: ship = new Ship(final
     * ShipDesign design) Expected: design.getHull() == ship.getHull()
     * design.getHull().getImage() == ship.getHull().getImage()
     * design.getHull().getSlotHull() == ship.getHullPointForComponent(0)
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestConstructorShipHullInDesignArgu() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
        Ship ship = new Ship(shipdesign);

        assertEquals(shiphull, ship.getHull());
        assertEquals(ShipImageFactor.create("Human")
            .getShipImage(SHIP_IMAGE_INDEX), ship.getHull().getImage());
        assertEquals(SHIP_HULL_SLOT, ship.getHullPointForComponent(SHIP_COMPONENT_INDEX));
    }

    /**
     * Purpose: Make sure the constructor works correctly about the ShipDesign
     * argument class Input: ship = new Ship(final ShipDesign design) Expected:
     * design.getTotalShield() == ship.getShield() design.getTotalArmor() ==
     * ship.getArmor()
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestConstructorDesignArgu() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(SHIP_DESIGN_TOTALSHIELD, ship.getShield());
        assertEquals(SHIP_DESIGN_TOTALARMOR, ship.getArmor());
    }

    /**
     * Purpose: Make sure the constructor works correctly about variables to add
     * in Constructor Input: ship = new Ship(final ShipDesign design) Expected:
     * ship.getColonist() == 0 ship.getMetal() == 0 ship.getExperience() == 0
     * ship.getCulture() == 0
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestConstructorVariables() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);
        assertEquals(0, ship.getColonist());
        assertEquals(0, ship.getMetal());
        assertEquals(0, ship.getExperience());
        assertEquals(0, ship.getCulture());
    }

    /**
     * Purpose: Confirm invalidIndex in getHullPointForComponent Method Input:
     * index < 0, index >= hullPoints.length Expected: return = 0
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetHullPointForComponentWithInvalidIndex() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(0, ship.getHullPointForComponent(-2));
        assertEquals(0, ship.getHullPointForComponent(-1));
        assertEquals(0, ship.getHullPointForComponent(6));
        assertEquals(0, ship.getHullPointForComponent(7));
    }

    /**
     * Purpose: Confirm RegenerateShield Input: shield = 0 shield generator
     * Defense Value = 1 getTotalShield() = 9 Expected: if(shield <
     * getTotalShield()) shield = shield + 1 if(shield < getTotalShield())
     * shield = shield + 1(Shield generator Mk1) else shield = getTotalShield()
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestRegenerateShield() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        ship.setShield(0);
        assertEquals(0, ship.getShield());
        ship.regenerateShield();
        assertEquals(2, ship.getShield());
        ship.regenerateShield();
        assertEquals(4, ship.getShield());
        ship.regenerateShield();
        assertEquals(6, ship.getShield());
        ship.regenerateShield();
        assertEquals(8, ship.getShield());
        ship.regenerateShield();
        assertEquals(9, ship.getShield());
        ship.regenerateShield();
        assertEquals(9, ship.getShield());

    }

    /**
     * Purpose: Confirm RegenerateShield Without ShieldComponent() Input: shield
     * = 0
     *
     * Expected: shield = 0
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestRegenerateShieldWithoutShieldComponent() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);
        ship.regenerateShield();
        assertEquals(0, ship.getShield());
    }

    /**
     * Purpose: Confirm getSpeed Input: ship with engine named Nuclear drive Mk1
     *
     * Expected: getspeed = 2 getFtlSpeed = 2 getTacticSpeed = 1
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetSpeedWithEngine() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { engine, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(2, ship.getSpeed());
        assertEquals(2, ship.getFtlSpeed());
        assertEquals(1, ship.getTacticSpeed());
    }

    /**
     * Purpose: Confirm getSpeed Input: ship without engine
     *
     * Expected: getspeed = 2 getFtlSpeed = 2 getTacticSpeed = 1
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetSpeedWithoutEngine() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(0, ship.getSpeed());
        assertEquals(0, ship.getFtlSpeed());
        assertEquals(0, ship.getTacticSpeed());
    }

    /**
     * Purpose: Confirm hasWeapon Input: ship with weapon and energy
     *
     * Expected:
     *
     * hasWeapon() = true
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestHasWeapon() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent weaponLaser = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent weaponRailgun = ShipComponentFactory.createByName("Railgun Mk1");
        ShipComponent weaponPhotoTorpedo = ShipComponentFactory.createByName("Photon torpedo Mk1");
        ShipComponent weaponECMTorpedo = ShipComponentFactory.createByName("ECM torpedo Mk1");
        ShipComponent weaponHeMissile = ShipComponentFactory.createByName("HE Missile Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents1 = new ShipComponent[] { weaponLaser, energy };
        ShipComponent[] shipcomponents2 = new ShipComponent[] { weaponRailgun, energy };
        ShipComponent[] shipcomponents3 = new ShipComponent[] { weaponPhotoTorpedo, energy };
        ShipComponent[] shipcomponents4 = new ShipComponent[] { weaponECMTorpedo, energy };
        ShipComponent[] shipcomponents5 = new ShipComponent[] { weaponHeMissile, energy };

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship = new Ship(shipdesign);
        assertTrue(ship.hasWeapons());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents2);
        Ship ship2 = new Ship(shipdesign);
        assertTrue(ship2.hasWeapons());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents3);
        Ship ship3 = new Ship(shipdesign);
        assertTrue(ship3.hasWeapons());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents4);
        Ship ship4 = new Ship(shipdesign);
        assertTrue(ship4.hasWeapons());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents5);
        Ship ship5 = new Ship(shipdesign);
        assertTrue(ship5.hasWeapons());

        assertEquals(100, ship.getHitChance(weaponLaser));
    }

    /**
     * Purpose: Confirm hasWeapon Input: ship without weapon
     *
     * Expected:
     *
     * hasWeapon() = false
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestHasWeaponWithoutWeapon() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents1 = new ShipComponent[] { energy };

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship = new Ship(shipdesign);
        assertFalse(ship.hasWeapons());
    }

    /**
     * Purpose: Confirm hasWeapon Input: ship without energy
     *
     * Expected:
     *
     * hasWeapon() = false
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestHasWeaponWithoutEnergy() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent weaponLaser = ShipComponentFactory.createByName("Laser Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents1 = new ShipComponent[] { weaponLaser };

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship = new Ship(shipdesign);
        assertFalse(ship.hasWeapons());
    }

    /**
     * Purpose: Confirm hasComponentEnergy Input: energy -
     * com.getEnergyRequirement() Fission source energy 4 Laser Mk1 requirement
     * energy 1 ECM torpedo Mk1 requirement energy 0 Photon torpedo Mk9
     * requirement energy 4
     *
     * Expected:
     *
     * if Input >= 0 return true else false
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestHasComponentEnergy() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent weaponLaser = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent weaponECMTorpedo = ShipComponentFactory.createByName("ECM torpedo Mk1");
        ShipComponent weaponPhotoTorpedo = ShipComponentFactory.createByName("Photon torpedo Mk9");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents1 = new ShipComponent[] { weaponLaser, weaponECMTorpedo, weaponPhotoTorpedo,
                energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship = new Ship(shipdesign);
        assertTrue(ship.hasComponentEnergy(0));
        assertTrue(ship.hasComponentEnergy(1));
        assertFalse(ship.hasComponentEnergy(2));

    }

    /**
     * Purpose: Confirm hasComponentEnergy Input: components.size = 0
     *
     * Expected:
     *
     * return false
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestHasComponentEnergyWithoutComponent() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents1 = new ShipComponent[] {};

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship = new Ship(shipdesign);
        assertFalse(ship.hasComponentEnergy(0));
    }

    /**
     * Purpose: Confirm hasBomb Input: ship with bomb
     *
     * Expected: if ship has bomb return true else return false
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestHasBombs() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent orbitalBomb = ShipComponentFactory.createByName("Orbital bombs Mk1");
        ShipComponent orbitalNuke = ShipComponentFactory.createByName("Orbital nuke");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] {};
        ShipComponent[] shipcomponents1 = new ShipComponent[] { orbitalBomb, energy };
        ShipComponent[] shipcomponents2 = new ShipComponent[] { orbitalNuke, energy };
        ShipComponent[] shipcomponents3 = new ShipComponent[] { orbitalBomb, orbitalNuke, energy };

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
        Ship ship = new Ship(shipdesign);
        assertFalse(ship.hasBombs());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship1 = new Ship(shipdesign);
        assertTrue(ship1.hasBombs());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents2);
        Ship ship2 = new Ship(shipdesign);
        assertTrue(ship2.hasBombs());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents3);
        Ship ship3 = new Ship(shipdesign);
        assertTrue(ship3.hasBombs());
    }

    /**
     * Purpose: Confirm getInitiative() Input: Various shipHull Size
     *
     * Expected:
     *
     * return appropriate various result
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetInitiativeByShipHullSize() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] {};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.SMALL);
        Ship ship1 = new Ship(shipdesign);
        assertEquals(12, ship1.getInitiative());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.MEDIUM);
        Ship ship2 = new Ship(shipdesign);
        assertEquals(8, ship2.getInitiative());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.LARGE);
        Ship ship3 = new Ship(shipdesign);
        assertEquals(4, ship3.getInitiative());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.HUGE);
        Ship ship4 = new Ship(shipdesign);
        assertEquals(0, ship4.getInitiative());
    }

    /**
     * Purpose: Confirm getInitiative()
     * Input: Various Empty Space -> 0 2 4 5 6 11
     * Expected: return appropriate various result -> 0 1 2 3 4 0
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetInitiativeByEmptySpace() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent orbitalBomb = ShipComponentFactory.createByName("Orbital bombs Mk1");
        ShipComponent orbitalNuke = ShipComponentFactory.createByName("Orbital nuke");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] { orbitalBomb, orbitalNuke, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shiphull.getSize()).thenReturn(ShipSize.HUGE);

        Mockito.when(shiphull.getMaxSlot()).thenReturn(3);
        Ship shipEmptySpaceIs0 = new Ship(shipdesign);
        assertEquals(0, shipEmptySpaceIs0.getInitiative());

        Mockito.when(shiphull.getMaxSlot()).thenReturn(5);
        Ship shipEmptySpaceIs2 = new Ship(shipdesign);
        assertEquals(1, shipEmptySpaceIs2.getInitiative());

        Mockito.when(shiphull.getMaxSlot()).thenReturn(7);
        Ship shipEmptySpaceIs4 = new Ship(shipdesign);
        assertEquals(2, shipEmptySpaceIs4.getInitiative());

        Mockito.when(shiphull.getMaxSlot()).thenReturn(8);
        Ship shipEmptySpaceIs5 = new Ship(shipdesign);
        assertEquals(3, shipEmptySpaceIs5.getInitiative());

        Mockito.when(shiphull.getMaxSlot()).thenReturn(10);
        Ship shipEmptySpaceIs6 = new Ship(shipdesign);
        assertEquals(4, shipEmptySpaceIs6.getInitiative());

        Mockito.when(shiphull.getMaxSlot()).thenReturn(20);
        Ship shipEmptySpaceIsDefault = new Ship(shipdesign);
        assertEquals(0, shipEmptySpaceIsDefault.getInitiative());

    }

    /**
     * Purpose: Confirm getInitiative()
     * Input: Nuclear Engine speed = 2, tactic speed = 1
     * Expected: 3
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetInitiativeByEngine() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] { engine, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shiphull.getSize()).thenReturn(ShipSize.HUGE);

        Mockito.when(shiphull.getMaxSlot()).thenReturn(2);
        Ship shipHasNuclearEngine = new Ship(shipdesign);
        assertEquals(3, shipHasNuclearEngine.getInitiative());
    }

    /**
     * Purpose: Confirm getInitiative()
     * Input: targetingComputer Mk1 initiativeBoost = 1
     * Expected: 3
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetInitiativeByTargetingComputer() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent targetingComputer = ShipComponentFactory.createByName("Targeting computer Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] { targetingComputer, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shiphull.getSize()).thenReturn(ShipSize.HUGE);

        Mockito.when(shiphull.getMaxSlot()).thenReturn(2);
        Ship shipHasTargetingComputer = new Ship(shipdesign);
        assertEquals(1, shipHasTargetingComputer.getInitiative());
    }

    /**
     * Purpose: Confirm getDefenseValue()
     * Input: Various shipHull Size
     * Expected:
     * return appropriate various result
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetDefenseValueByShipHullSize() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] {engine, energy};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.SMALL);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(10);
        Ship ship1 = new Ship(shipdesign);
        assertEquals(10, ship1.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.MEDIUM);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(5);
        Ship ship2 = new Ship(shipdesign);
        assertEquals(5, ship2.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.LARGE);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(0);
        Ship ship3 = new Ship(shipdesign);
        assertEquals(0, ship3.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.HUGE);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(-5);
        Ship ship4 = new Ship(shipdesign);
        assertEquals(-5, ship4.getDefenseValue());
    }

    /**
     * Purpose: Confirm getDefenseValue()
     * Input: Various shipHull Size with jammer
     * Expected:
     * return appropriate various result
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetDefenseValueByShipHullSizeWithJammer() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent jammer = ShipComponentFactory.createByName("Jammer Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] {engine, energy, jammer};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.SMALL);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(10);
        Ship ship1 = new Ship(shipdesign);
        assertEquals(20, ship1.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.MEDIUM);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(5);
        Ship ship2 = new Ship(shipdesign);
        assertEquals(15, ship2.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.LARGE);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(0);
        Ship ship3 = new Ship(shipdesign);
        assertEquals(10, ship3.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.HUGE);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(-5);
        Ship ship4 = new Ship(shipdesign);
        assertEquals(5, ship4.getDefenseValue());
    }
    /**
     * Purpose: Confirm getDefenseValue()
     * Input: Various shipHull Size with empty engine
     * Expected:
     * return appropriate various result
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetDefenseValueByShipHullSizeWithEmptyEngine() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents1 = new ShipComponent[] {energy};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.SMALL);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(10);
        Ship ship1 = new Ship(shipdesign);
        assertEquals(-5, ship1.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.MEDIUM);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(5);
        Ship ship2 = new Ship(shipdesign);
        assertEquals(-10, ship2.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.LARGE);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(0);
        Ship ship3 = new Ship(shipdesign);
        assertEquals(-15, ship3.getDefenseValue());

        Mockito.when(shipdesign.getHull().getSize()).thenReturn(ShipSize.HUGE);
        Mockito.when(shipdesign.getHull().getDefenseValueByShipHullSize()).thenReturn(-5);
        Ship ship4 = new Ship(shipdesign);
        assertEquals(-20, ship4.getDefenseValue());
    }

    /**
     * Purpose: Confirm getHitChance Method Input: ship with various weapon
     *
     * Expected:
     *
     * return getScannerLvl() = scanner.getScannerRange()
     * getScannerDetctionLvl() = scanner.getCloakDection()
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestScanner() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent scanner = ShipComponentFactory.createByName("Scanner Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);
        ShipComponent[] shipcomponents = new ShipComponent[] { scanner, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);
        assertEquals(2, ship.getScannerLvl());
        assertEquals(20, ship.getScannerDetectionLvl());
    }

    /**
     * Purpose: Confirm () Input: ship with scanner
     *
     * Expected:
     *
     * return appropriate various result weapon_beam = 100 weapon_railgun,
     * weapon_photon_torpedo = 75 weapon_ecm_torpedo, weapon_he_missile = 50
     * targeting_computer = +10
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetHitChance() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);
        ShipComponent weaponLaser = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent weaponRailgun = ShipComponentFactory.createByName("Railgun Mk1");
        ShipComponent weaponPhotoTorpedo = ShipComponentFactory.createByName("Photon torpedo Mk1");
        ShipComponent weaponECMTorpedo = ShipComponentFactory.createByName("ECM torpedo Mk1");
        ShipComponent weaponHeMissile = ShipComponentFactory.createByName("HE Missile Mk1");
        ShipComponent targetingComputer = ShipComponentFactory.createByName("Targeting computer Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents1 = new ShipComponent[] { weaponLaser, energy };
        ShipComponent[] shipcomponents2 = new ShipComponent[] { weaponRailgun, energy };
        ShipComponent[] shipcomponents3 = new ShipComponent[] { weaponPhotoTorpedo, energy };
        ShipComponent[] shipcomponents4 = new ShipComponent[] { weaponECMTorpedo, energy };
        ShipComponent[] shipcomponents5 = new ShipComponent[] { weaponHeMissile, energy };
        ShipComponent[] shipcomponents6 = new ShipComponent[] { weaponLaser, targetingComputer, energy };

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents1);
        Ship ship = new Ship(shipdesign);
        assertEquals(100, ship.getHitChance(weaponLaser));

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents2);
        Ship ship2 = new Ship(shipdesign);
        assertEquals(75, ship2.getHitChance(weaponRailgun));

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents3);
        Ship ship3 = new Ship(shipdesign);
        assertEquals(75, ship3.getHitChance(weaponPhotoTorpedo));

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents4);
        Ship ship4 = new Ship(shipdesign);
        assertEquals(50, ship4.getHitChance(weaponECMTorpedo));

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents5);
        Ship ship5 = new Ship(shipdesign);
        assertEquals(50, ship5.getHitChance(weaponHeMissile));

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents6);
        Ship ship6 = new Ship(shipdesign);
        assertEquals(110, ship6.getHitChance(weaponLaser));
    }

    /**
     * Purpose: Test ship Damage Input: damage Expected:
     *
     * result(0) = damage(5) - hullpoint(5) fixship => 25->30 (true) 25->26
     * (false) 30->30 (false) hullpoints = count of ShipComponent(6) *
     * hullpoint(5) = 30
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestDamageMethod() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);
        assertEquals(0, ship.damageComponent(5, new ShipDamage(-2, "Damaged!")));
        assertEquals(25, ship.getHullPoints());
        ship.fixShip(true);
        assertEquals(30, ship.getHullPoints());
        assertEquals(0, ship.damageComponent(5, new ShipDamage(-2, "Damaged!")));
        assertEquals(25, ship.getHullPoints());
        ship.fixShip(false);
        assertEquals(26, ship.getHullPoints());
        ship.fixShip(true);
        assertEquals(30, ship.getHullPoints());
        ship.fixShip(false);
        assertEquals(30, ship.getHullPoints());
    }

    /**
     * Purpose: Confirm FLag Input: ship Expected: ship() -> all flag false
     * setFlag(true)->getFlag = true setFlag(false)->getFlag = false
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestFlag() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk9");
        ShipComponent shieldgenerator = ShipComponentFactory.createByName("Shield generator Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weapon, engine, energy, armor, shield, shieldgenerator };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(false, ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED));
        assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
        assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
        ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
        ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, true);
        ship.setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD, true);
        assertEquals(true, ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED));
        assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
        assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
        ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, false);
        ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, false);
        ship.setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD, false);
        assertEquals(false, ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED));
        assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
        assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    }

    /**
     * Purpose: damageBy various weapon Input: ship Expected:
     *
     * shield = 1 armor = 1 hullpoints = 10 Laser Mk1 1 Railgun Mk1 1 Photo
     * torpedo Mk1 1 ECM torpedo Mk1 3 HE MIssile Mk1 3
     *
     * WEAPON_BEAM, WEAPON_PHOTON_TORPEDO getShield -damage getArmor -damage
     * when shield is null
     *
     * WEAPON_RAILGUN, WEAPON_HE_MISSILE getArmor -damage getShield -damage when
     * shield is null
     *
     * WEAPON_ECM_TORPEDO getShield -damage
     *
     * if (shield == 0 && armor == 0 && damage > 0) damageComponent
     *
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestDamageBy() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { engine, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        ShipComponent weaponLaser = ShipComponentFactory.createByName("Laser Mk1");
        ShipComponent weaponRailgun = ShipComponentFactory.createByName("Railgun Mk1");
        ShipComponent weaponPhotoTorpedo = ShipComponentFactory.createByName("Photon torpedo Mk1");
        ShipComponent weaponECMTorpedo = ShipComponentFactory.createByName("ECM torpedo Mk1");
        ShipComponent weaponHeMissile = ShipComponentFactory.createByName("HE Missile Mk1");

        ship.setShield(1);
        ship.setArmor(1);
        assertEquals(1, ship.getShield());
        assertEquals(1, ship.getArmor());
        ship.damageBy(weaponLaser, 0);
        assertEquals(0, ship.getShield());
        assertEquals(1, ship.getArmor());
        ship.damageBy(weaponLaser, 0);
        assertEquals(0, ship.getShield());
        assertEquals(0, ship.getArmor());
        ship.damageBy(weaponLaser, 0);

        ship.setShield(1);
        ship.setArmor(1);
        assertEquals(1, ship.getShield());
        assertEquals(1, ship.getArmor());
        ship.damageBy(weaponRailgun, 0);
        assertEquals(0, ship.getShield());
        assertEquals(0, ship.getArmor());
        ship.damageBy(weaponRailgun, 0);
        assertEquals(0, ship.getShield());
        assertEquals(0, ship.getArmor());
        ship.damageBy(weaponRailgun, 0);

        ship.setShield(1);
        ship.setArmor(1);
        assertEquals(1, ship.getShield());
        assertEquals(1, ship.getArmor());
        ship.damageBy(weaponPhotoTorpedo, 0);
        assertEquals(0, ship.getShield());
        assertEquals(1, ship.getArmor());
        ship.damageBy(weaponPhotoTorpedo, 0);
        assertEquals(0, ship.getShield());
        assertEquals(0, ship.getArmor());
        ship.damageBy(weaponPhotoTorpedo, 0);

        ship.setShield(4);
        ship.setArmor(4);
        assertEquals(4, ship.getShield());
        assertEquals(4, ship.getArmor());
        ship.damageBy(weaponECMTorpedo, 0);
        assertEquals(1, ship.getShield());
        assertEquals(4, ship.getArmor());
        ship.damageBy(weaponECMTorpedo, 0);
        assertEquals(0, ship.getShield());
        assertEquals(4, ship.getArmor());
        ship.damageBy(weaponECMTorpedo, 0);
        assertEquals(0, ship.getShield());
        assertEquals(4, ship.getArmor());

        ship.setShield(2);
        ship.setArmor(2);
        assertEquals(2, ship.getShield());
        assertEquals(2, ship.getArmor());
        ship.damageBy(weaponHeMissile, 0);
        assertEquals(1, ship.getShield());
        assertEquals(1, ship.getArmor());
        ship.damageBy(weaponHeMissile, 0);
        assertEquals(0, ship.getShield());
        assertEquals(0, ship.getArmor());
        ship.damageBy(weaponHeMissile, 0);
        assertEquals(0, ship.getShield());
        assertEquals(0, ship.getArmor());

    }

    /**
     * Purpose: Confirm power Input: ship power + WEAPON_HE_MISSILE.getDamage()
     * 10(HE Missile Mk8) + WEAPON_ECM_TORPEDO 5(ECM torpedo Mk8) +
     * ARMOR.getDefenseValue() 10(Armor plating Mk10) + SHIELD.getDefenseValue()
     * 10(Shield Mk10) + ENGINE.getTacticSpeed() - 1 1(Impulse engine Mk4)
     * Expected: power = 36
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetTotalMilitaryPower() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weaponMissile = ShipComponentFactory.createByName("HE Missile Mk8");
        ShipComponent weaponTorpedo = ShipComponentFactory.createByName("ECM torpedo Mk8");

        ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk10");
        ShipComponent shield = ShipComponentFactory.createByName("Shield Mk10");
        ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weaponMissile, weaponTorpedo, armor, shield, engine,
                energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(36, ship.getTotalMilitaryPower());
    }

    /**
     * Purpose: Confirm power Input: ship has nulcear weapon that has 10 damage
     * -> 10 ship has targeting computer that has 20 damage -> 2 ship has jammer
     * that has 20 defensevalue -> 2 ship has energy
     *
     * Expected: power = 14
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetTotalMilitaryPowerWithNuclearAndJammer() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class);

        ShipComponent weaponMissile = ShipComponentFactory.createByName("HE Missile Mk8");
        ShipComponent targetingComputer = ShipComponentFactory.createByName("Targeting computer Mk2");
        ShipComponent jammer = ShipComponentFactory.createByName("Jammer Mk4");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { weaponMissile, targetingComputer, jammer, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        assertEquals(16, ship.getTotalMilitaryPower());
    }

    /**
     * Purpose: Confirm TroopPower Input: ship has 5 colonists ship has shiphull
     * that has 10 trooperpower ship has Planetary invasion module that has 50
     * damage ship has energy module named Fission source Mk2
     *
     * Expected: result = 75
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetTroopPower() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class, Mockito.RETURNS_DEEP_STUBS);

        ShipComponent planetaryInvasion = ShipComponentFactory.createByName("Planetary invasion module");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { planetaryInvasion, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        ship.setColonist(5);
        Mockito.when(shiphull.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));

        assertEquals(75, ship.getTroopPower());
    }

    /**
     * Purpose: Confirm TroopPower Input: ship has 5 colonists ship has shiphull
     * that has 10 trooperpower ship doesn't have trooper planetaryInvasion
     * module ship has energy named Fission source Mk2 Expected: result = 0
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestGetTroopPowerWithoutPlanetaryInvasion() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class, Mockito.RETURNS_DEEP_STUBS);

        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);

        Ship ship = new Ship(shipdesign);

        ship.setColonist(5);
        Mockito.when(shiphull.getRace()).thenReturn(SpaceRaceFactory.createOne("HUMANS"));

        assertEquals(0, ship.getTroopPower());
    }

    /**
     * Purpose: Confirm Component Module Input: ship has Colonization module
     * Expected: result = true
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestIsColonyModule() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class, Mockito.RETURNS_DEEP_STUBS);

        ShipComponent colonizationmodule = ShipComponentFactory.createByName("Colonization module");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { colonizationmodule };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
        Ship ship = new Ship(shipdesign);
        assertTrue(ship.isColonyModule());

        ShipComponent[] shipcomponentWithoutModule = new ShipComponent[] {};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentWithoutModule);
        Ship ship2 = new Ship(shipdesign);
        assertFalse(ship2.isColonyModule());
    }

    /**
     * Purpose: Confirm Component Module Input: ship has Shock trooper module
     * Expected: result = true
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestIsTrooperModule() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class, Mockito.RETURNS_DEEP_STUBS);

        ShipComponent troopermodule = ShipComponentFactory.createByName("Shock trooper module");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent[] shipcomponents = new ShipComponent[] { troopermodule };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponents);
        Ship ship = new Ship(shipdesign);
        assertTrue(ship.isTrooperModule());

        ShipComponent[] shipcomponentWithoutModule = new ShipComponent[] {};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentWithoutModule);
        Ship ship2 = new Ship(shipdesign);
        assertFalse(ship2.isTrooperModule());
    }

    /**
     * Purpose: Confirm Ship Type Input: colonyShip = ship with ShipComponent
     * that is the Colonization module trooperShip = ship with ShipComponent
     * that is the Shock trooper module privateerShip = ship with ShipHull that
     * is the ShipHullType.PRIVATEER
     *
     *
     * Expected: colonyShip -> isColonyShip() return true trooperShip ->
     * isTrooperShip() return true privateerShip -> isPrivateeringShip() return
     * true
     */
    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void TestIsTypeShip() {
        ShipDesign shipdesign = Mockito.mock(ShipDesign.class, Mockito.RETURNS_DEEP_STUBS);
        ShipHull shiphull = Mockito.mock(ShipHull.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(shiphull.getName()).thenReturn("Freighter");

        Mockito.when(shipdesign.getName()).thenReturn(SHIP_DESIGN_NAME);
        Mockito.when(shipdesign.getCost()).thenReturn(SHIP_DESIGN_COST);
        Mockito.when(shipdesign.getMetalCost()).thenReturn(SHIP_DESIGN_METALCOST);
        Mockito.when(shipdesign.getHull()).thenReturn(shiphull);
        Mockito.when(shipdesign.getHull().getSlotHull()).thenReturn(SHIP_HULL_SLOT);
        Mockito.when(shipdesign.getTotalShield()).thenReturn(SHIP_DESIGN_TOTALSHIELD);
        Mockito.when(shipdesign.getTotalArmor()).thenReturn(SHIP_DESIGN_TOTALARMOR);
        Mockito.when(shipdesign.getHull().getImage())
                .thenReturn(ShipImageFactor.create("Human")
                    .getShipImage(SHIP_IMAGE_INDEX));
        Mockito.when(shipdesign.getDesignInfo()).thenReturn(SHIP_DESIGN_INFO);

        ShipComponent colonizationmodule = ShipComponentFactory.createByName("Colonization module");
        ShipComponent troppermodule = ShipComponentFactory.createByName("Shock trooper module");
        ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk2");

        ShipComponent[] shipcomponentWithColonyModule = new ShipComponent[] { colonizationmodule, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentWithColonyModule);
        Ship colonyShip = new Ship(shipdesign);
        colonyShip.setColonist(5);
        assertTrue(colonyShip.isColonyShip());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentWithColonyModule);
        Ship colonyShipWithoutColonist = new Ship(shipdesign);
        assertTrue(colonyShipWithoutColonist.isColonyShip());

        ShipComponent[] shipcomponentWithoutColonyModule = new ShipComponent[] {};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentWithoutColonyModule);
        Ship colonyShipWithoutModule = new Ship(shipdesign);
        colonyShipWithoutModule.setColonist(5);
        assertFalse(colonyShipWithoutModule.isColonyShip());

        ShipComponent[] shipcomponentsWithTropper = new ShipComponent[] { troppermodule, energy };
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentsWithTropper);
        Ship tropperShip = new Ship(shipdesign);
        tropperShip.setColonist(5);
        assertTrue(tropperShip.isTrooperShip());

        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentsWithTropper);
        Ship tropperShipWithoutColonist = new Ship(shipdesign);
        assertFalse(tropperShipWithoutColonist.isTrooperShip());

        ShipComponent[] shipcomponentWithoutModule = new ShipComponent[] {};
        Mockito.when(shipdesign.getComponentList()).thenReturn(shipcomponentWithoutModule);
        Ship tropperShipWithoutModule = new Ship(shipdesign);
        tropperShipWithoutModule.setColonist(5);
        assertFalse(tropperShipWithoutModule.isTrooperShip());

        Mockito.when(shiphull.getHullType()).thenReturn(ShipHullType.PRIVATEER);
        Ship privateerShip = new Ship(shipdesign);
        assertTrue(privateerShip.isPrivateeringShip());

        Mockito.when(shiphull.getHullType()).thenReturn(ShipHullType.NORMAL);
        Ship normalShip = new Ship(shipdesign);
        assertFalse(normalShip.isPrivateeringShip());
    }

}
