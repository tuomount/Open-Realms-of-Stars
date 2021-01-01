package org.openRealmOfStars.ambient;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* JUnits of UPnP Device of Hue Bridge
*
*/
public class BridgeDeviceTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    BridgeDevice device = new BridgeDevice("1234", "127.0.0.1");
    assertEquals("127.0.0.1", device.getAddress());
    assertEquals("1234", device.getId());
    assertEquals("127.0.0.1:1234", device.toString());
    BridgeDevice device2 = new BridgeDevice("12345", "127.0.0.1");
    BridgeDevice device3 = new BridgeDevice("1234", "127.0.0.2");
    assertEquals(true, device.equals(device3));
    assertEquals(false, device.equals(device2));
    assertEquals(false, device2.equals(device));
    assertEquals(false, device2.equals(device3));
    assertEquals(device3.hashCode(), device.hashCode());
    assertNotEquals(device2.hashCode(), device.hashCode());
  }

}
