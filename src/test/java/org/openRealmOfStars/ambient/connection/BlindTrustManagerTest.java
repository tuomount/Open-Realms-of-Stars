package org.openRealmOfStars.ambient.connection;

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
* JUnits for BlindTrustManager class.
*
*/
public class BlindTrustManagerTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    BlindTrustManager manager = new BlindTrustManager();
    String cn = manager.parsePrincipal("CN=Common name, O=Object, C=Country", "CN");
    assertEquals("Common name", cn);
    String o = manager.parsePrincipal("CN=Common name, O=Object, C=Country", "O");
    assertEquals("Object", o);
    String c = manager.parsePrincipal("CN=Common name, O=Object, C=Country", "C");
    assertEquals("Country", c);
    String unknown = manager.parsePrincipal("CN=Common name, O=Object, C=Country", "U");
    assertEquals(null, unknown);
  }

}
