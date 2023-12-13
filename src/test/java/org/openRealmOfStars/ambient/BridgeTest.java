package org.openRealmOfStars.ambient;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2021 Tuomo Untinen
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

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Hue Bridge JUnit
*
*/
public class BridgeTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Bridge bridge = new Bridge("127.0.0.1");
    assertEquals(3, bridge.getIntense());
    assertEquals("127.0.0.1", bridge.getHostname());
    assertEquals(null, bridge.getUsername());
    bridge.setHostname("127.0.0.2");
    bridge.setUsername("Username");
    assertEquals("127.0.0.2", bridge.getHostname());
    assertEquals("Username", bridge.getUsername());
    assertEquals("", bridge.getLastErrorMsg());
    assertEquals(null, bridge.getLigths());
    assertEquals(null, bridge.getId());
    bridge.setId("1234");
    assertEquals("1234", bridge.getId());
    bridge.setIntense(5);
    assertEquals(5, bridge.getIntense());
    assertEquals("none", bridge.getLeftLightName());
    assertEquals("none", bridge.getRightLightName());
    assertEquals("none", bridge.getCenterLightName());
    bridge.setLeftLightName("left");
    assertEquals("left", bridge.getLeftLightName());
    bridge.setCenterLightName("center");
    assertEquals("center", bridge.getCenterLightName());
    bridge.setRightLightName("right");
    assertEquals("right", bridge.getRightLightName());
    assertEquals(false, bridge.areLightsEnabled());
    bridge.setLightsEnabled(true);
    assertEquals(true, bridge.areLightsEnabled());
    assertEquals(BridgeStatusType.NOT_CONNECTED, bridge.getStatus());
    bridge.setStatus(BridgeStatusType.ERROR);
    assertEquals(BridgeStatusType.ERROR, bridge.getStatus());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSuccessPairing() {
    Bridge bridge = new Bridge("127.0.0.1");
    String json = "[{\"success\":{\"username\":\"longtestusername\"}}]";
    bridge.registerParsing(json.getBytes(StandardCharsets.UTF_8));
    assertEquals("", bridge.getLastErrorMsg());
    assertEquals("longtestusername", bridge.getUsername());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFailedPairing() {
    Bridge bridge = new Bridge("127.0.0.1");
    String json = "[{\"error\":{\"type\":101,\"address\":\"\","
        + "\"description\":\"link button not pressed\"}}]";
    bridge.registerParsing(json.getBytes(StandardCharsets.UTF_8));
    assertEquals("Remember press sync button"
        + " before clicking register. link button not pressed",
        bridge.getLastErrorMsg());
    assertNull(bridge.getUsername());
  }

}
