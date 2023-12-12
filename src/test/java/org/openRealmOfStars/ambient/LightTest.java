package org.openRealmOfStars.ambient;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020 Tuomo Untinen
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

import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Light test.
*
*/
public class LightTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Light light = new Light("1");
    JSONObject json = light.updateLampJson();
    assertEquals("{}",json.toString());
    light.setHumanReadablename("Test room light 1");
    light.setOn(true);
    light.setBri(255);
    light.setSat(0);
    light.setHue(4000);
    json = light.updateLampJson();
    assertEquals("{\"sat\":0,\"bri\":255,\"hue\":4000,\"on\":true}",
        json.toString());
    json = light.updateLampJson();
    assertEquals("{}",json.toString());
  }

}
