package org.openRealmOfStars.player.espionage;

import static org.junit.Assert.*;

import org.junit.Test;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Espionage Test
*
*/
public class EspionageTest {

  @Test
  public void testBasic() {
    Espionage espionage = new Espionage(4, 0);
    assertEquals(4, espionage.getSize());
    assertEquals(null, espionage.getByIndex(0));
    assertEquals(null, espionage.getByIndex(-1));
    assertNotNull(espionage.getByIndex(1));
    assertNotNull(espionage.getByIndex(2));
    assertNotNull(espionage.getByIndex(3));
    assertEquals(null, espionage.getByIndex(4));
  }

}
