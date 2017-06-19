package org.openRealmOfStars.player.diplomacy.speeches;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Speech lines test
*
*/
public class SpeechLineTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    SpeechLine line = new SpeechLine(SpeechType.TRADE, "You want trade?");
    assertEquals(SpeechType.TRADE, line.getType());
    assertEquals("You want trade?", line.getLine());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testToString() {
    SpeechLine line = new SpeechLine(SpeechType.TRADE, "You want trade?");
    assertEquals("Trade/You want trade?", line.toString());
    line = new SpeechLine(SpeechType.AGREE, "fubar?");
    assertEquals("Agree/fubar?", line.toString());
    line = new SpeechLine(SpeechType.TRADE_ALLIANCE, "fubar?");
    assertEquals("Trade Alliance/fubar?", line.toString());
    line = new SpeechLine(SpeechType.ALLIANCE, "fubar?");
    assertEquals("Alliance/fubar?", line.toString());
    line = new SpeechLine(SpeechType.DECLINE, "fubar?");
    assertEquals("Decline/fubar?", line.toString());
    line = new SpeechLine(SpeechType.DECLINE_ANGER, "fubar?");
    assertEquals("Decline with Anger/fubar?", line.toString());
    line = new SpeechLine(SpeechType.DECLINE_WAR, "fubar?");
    assertEquals("Decline with War/fubar?", line.toString());
    line = new SpeechLine(SpeechType.DEMAND, "fubar?");
    assertEquals("Demand/fubar?", line.toString());
    line = new SpeechLine(SpeechType.MAKE_WAR, "fubar?");
    assertEquals("Make War/fubar?", line.toString());
  }

}
