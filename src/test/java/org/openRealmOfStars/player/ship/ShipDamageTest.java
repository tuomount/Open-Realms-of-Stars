package org.openRealmOfStars.player.ship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Test for Ship damage class
 */
public class ShipDamageTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testShipDamage() {
    ShipDamage damage = new ShipDamage(ShipDamage.NO_DAMAGE_NO_DENT,
        "Test damage");
    assertEquals(ShipDamage.NO_DAMAGE_NO_DENT, damage.getValue());
    damage.addText("Saved by shields!");
    damage.setValue(ShipDamage.NO_DAMAGE);
    assertEquals(ShipDamage.NO_DAMAGE, damage.getValue());
    assertEquals("Test damage\nSaved by shields!", damage.getMessage());
    
    
    assertTrue("Damage is ready too early",
        !damage.isReady());
    damage.logged();
    assertTrue("Damage is ready too early",
        !damage.isReady());
    damage.ready();
    assertTrue("Damage isn't ready yet!",
        damage.isReady());
  }



}
