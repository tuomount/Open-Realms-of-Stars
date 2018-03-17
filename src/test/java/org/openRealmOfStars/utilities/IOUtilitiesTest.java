package org.openRealmOfStars.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBits() throws IOException {
    byte value = 0;
    int k = 1;
    for (int i = 0; i<8; i++) {
      value = 0;
      value = IOUtilities.setFlag(value, i, true);
      assertEquals(k, (0xff)  & value);
      k = k * 2; 
      assertEquals(true, IOUtilities.getFlag(value, i));
    }
    k = 1;
    int total = 0;
    value = 0;
    for (int i = 0; i<8; i++) {
      total = total +k;
      value = IOUtilities.setFlag(value, i, true);
      assertEquals(total, (0xff)  & value);
      k = k * 2; 
      assertEquals(true, IOUtilities.getFlag(value, i));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUTF8String() throws IOException {
    String test = "Hello world of Java! ÄÄ";
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    IOUtilities.writeUTF8String(os, test);
    byte[] buf = os.toByteArray();
    ByteArrayInputStream is = new ByteArrayInputStream(buf);
    String result = IOUtilities.readUTF8String(is);
    assertEquals(test, result);
  }

}
