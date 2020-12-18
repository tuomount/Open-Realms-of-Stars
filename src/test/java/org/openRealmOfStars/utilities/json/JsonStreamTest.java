package org.openRealmOfStars.utilities.json;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
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
*
*
* JUnit for JsonStream
*
*/
public class JsonStreamTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() throws IOException {
    byte[] buffer = "Test Text".getBytes(StandardCharsets.US_ASCII);
    JsonStream stream = new JsonStream(buffer);
    assertEquals("Length: 9 Offset: 0 Next: 'T' 84", stream.toString());
    assertEquals(9, stream.available());
    int value = stream.read();
    assertEquals(84, value);
    assertEquals("Length: 9 Offset: 1 Next: 'e' 101", stream.toString());
    assertEquals(8, stream.available());
    stream.skip(8);
    assertEquals("Length: 9 Offset: 9 Next: EOS", stream.toString());
    assertEquals(0, stream.available());
    value = stream.read();
    assertEquals(-1, value);
    stream.close();
    assertEquals("Closed stream", stream.toString());
  }

  @Test(expected=IOException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testClosedStream() throws IOException {
    byte[] buffer = "Test Text".getBytes(StandardCharsets.US_ASCII);
    JsonStream stream = new JsonStream(buffer);
    stream.close();
    stream.read();
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReadWhiteSpace() throws IOException {
    byte[] buffer = " \n\r Text\tTest End".getBytes(StandardCharsets.US_ASCII);
    JsonStream stream = new JsonStream(buffer);
    assertEquals("Length: 17 Offset: 0 Next: ' ' 32", stream.toString());
    stream.readWhiteSpace();
    assertEquals("Length: 17 Offset: 4 Next: 'T' 84", stream.toString());
    stream.skip(4);
    stream.readWhiteSpace();
    assertEquals("Length: 17 Offset: 9 Next: 'T' 84", stream.toString());
    stream.skip(4);
    stream.readWhiteSpace();
    assertEquals("Length: 17 Offset: 14 Next: 'E' 69", stream.toString());
    stream.close();
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCurrent() throws IOException {
    byte[] buffer = "{ }".getBytes(StandardCharsets.US_ASCII);
    JsonStream stream = new JsonStream(buffer);
    assertEquals(true, stream.isCurrent(JsonStream.BEGIN_OBJECT));
    stream.readWhiteSpace();
    assertEquals(true, stream.isCurrent(JsonStream.END_OBJECT));
    stream.close();
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReadString() throws IOException {
    byte[] buffer = "\"Hello world\"".getBytes(StandardCharsets.US_ASCII);
    JsonStream stream = new JsonStream(buffer);
    String str = stream.readString();
    assertEquals("Hello world", str);
    stream.close();
  }

}
