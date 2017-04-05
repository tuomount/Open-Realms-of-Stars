package org.openRealmOfStars.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
 * Test for IOUtilies class
 */

public class IOUtilitiesTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testReadAll() throws IOException {
    byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05};
    ByteArrayInputStream is = new ByteArrayInputStream(data);
    byte[] buf = IOUtilities.readAll(is);
    if (data.length == buf.length) {
      for (int i=0;i<data.length;i++) {
        assertEquals(data[i],buf[i]);
      }
    } else {
      assertFalse(false);
    }
  }

}
