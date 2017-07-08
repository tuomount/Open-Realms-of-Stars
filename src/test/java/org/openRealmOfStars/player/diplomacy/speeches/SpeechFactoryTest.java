package org.openRealmOfStars.player.diplomacy.speeches;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
* Speech factory for generating speeches
*
*/
public class SpeechFactoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    int maxRaces = SpaceRace.values().length;
    for (int i = 0; i < maxRaces; i++) {
      SpaceRace race = SpaceRace.values()[i];
      SpeechLine line = SpeechFactory.createLine(SpeechType.AGREE, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.AGREE, line.getType());
      line = SpeechFactory.createLine(SpeechType.ALLIANCE, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.ALLIANCE, line.getType());
      line = SpeechFactory.createLine(SpeechType.TRADE_ALLIANCE, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.TRADE_ALLIANCE, line.getType());
      line = SpeechFactory.createLine(SpeechType.TRADE, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.TRADE, line.getType());
      line = SpeechFactory.createLine(SpeechType.DECLINE, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.DECLINE, line.getType());
      line = SpeechFactory.createLine(SpeechType.MAKE_WAR, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.MAKE_WAR, line.getType());
      line = SpeechFactory.createLine(SpeechType.DECLINE_ANGER, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.DECLINE_ANGER, line.getType());
      line = SpeechFactory.createLine(SpeechType.DECLINE_WAR, race);
      assertEquals(SpeechType.DECLINE_WAR, line.getType());
      assertNotEquals(null, line);
      line = SpeechFactory.createLine(SpeechType.DEMAND, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.DEMAND, line.getType());
      line = SpeechFactory.createLine(SpeechType.NEUTRAL_GREET, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.NEUTRAL_GREET, line.getType());
      line = SpeechFactory.createLine(SpeechType.DISLIKE_GREET, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.DISLIKE_GREET, line.getType());
      line = SpeechFactory.createLine(SpeechType.LIKE_GREET, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.LIKE_GREET, line.getType());
      line = SpeechFactory.createLine(SpeechType.HATE_GREET, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.HATE_GREET, line.getType());
      line = SpeechFactory.createLine(SpeechType.FRIENDS_GREET, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.FRIENDS_GREET, line.getType());
      line = SpeechFactory.createLine(SpeechType.PEACE_OFFER, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.PEACE_OFFER, line.getType());
      line = SpeechFactory.createLine(SpeechType.INSULT_RESPOND, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.INSULT_RESPOND, line.getType());
      line = SpeechFactory.createLine(SpeechType.OFFER_REJECTED, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.OFFER_REJECTED, line.getType());
      line = SpeechFactory.createLine(SpeechType.OFFER_ACCEPTED, race);
      assertNotEquals(null, line);
      assertEquals(SpeechType.OFFER_ACCEPTED, line.getType());
    }
  }

}
