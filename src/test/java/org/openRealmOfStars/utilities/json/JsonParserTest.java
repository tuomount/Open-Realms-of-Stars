package org.openRealmOfStars.utilities.json;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.utilities.json.values.ObjectValue;

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
* JUnit for JsonParser
*
*/
public class JsonParserTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSimplestJson() throws IOException {
    String jsonText = "{}";
    byte[] buf = jsonText.getBytes(StandardCharsets.UTF_8);
    JsonStream stream = new JsonStream(buf);
    ObjectValue root = JsonParser.parseJson(stream);
    stream.close();
    assertEquals("{}", root.getValueAsString());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSimpleJson() throws IOException {
    String jsonText = "{\"Widget\": {\"Debug\": \"On\", \"Run\": \"Off\", \"Loop\": 128}}";
    byte[] buf = jsonText.getBytes(StandardCharsets.UTF_8);
    JsonStream stream = new JsonStream(buf);
    ObjectValue root = JsonParser.parseJson(stream);
    stream.close();
    assertEquals("{\"Widget\": {\"Debug\": \"On\",\"Run\": \"Off\",\"Loop\": 128}}",
        root.getValueAsString());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSimpleJson2() throws IOException {
    String jsonText = "{\"Widget\": {\"Debug\": false, \"Run\": true, \"Loop\": 0.01}}";
    byte[] buf = jsonText.getBytes(StandardCharsets.UTF_8);
    JsonStream stream = new JsonStream(buf);
    ObjectValue root = JsonParser.parseJson(stream);
    stream.close();
    assertEquals("{\"Widget\": {\"Debug\": false,\"Run\": true,\"Loop\": 0.01}}",
        root.getValueAsString());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSimpleJson3() throws IOException {
    String jsonText = "{\"Widget\": {\"Debug\": false, \"Run\": true, \"Values\": [1,2,3,4], \"NotDefined\": null}}";
    byte[] buf = jsonText.getBytes(StandardCharsets.UTF_8);
    JsonStream stream = new JsonStream(buf);
    ObjectValue root = JsonParser.parseJson(stream);
    stream.close();
    assertEquals("{\"Widget\": {\"Debug\": false,\"Run\": true,\"Values\": [1,2,3,4],\"NotDefined\": null}}",
        root.getValueAsString());
  }

}
