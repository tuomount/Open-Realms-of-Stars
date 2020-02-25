package org.openRealmOfStars.starMap.history.event;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.starMap.Coordinate;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2020  Tuomo Untinen
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
* Combat event test
*
*/
public class LeaderEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Leader leader = Mockito.mock(Leader.class);
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getLeaderIndex(leader)).thenReturn(1);
    Coordinate coord = Mockito.mock(Coordinate.class);
    LeaderEvent event = new LeaderEvent(leader, realm, 0, coord);
    event.setPlanetName("Test Planet I");
    event.setText("Text");
    assertEquals(1, event.getLeaderIndex());
    assertEquals(0, event.getPlayerIndex());
    assertEquals(EventType.LEADER_EVENT, event.getType());
    assertEquals(coord, event.getCoordinate());
    assertEquals("Test Planet I", event.getPlanetName());
    assertEquals("Text", event.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Leader leader = Mockito.mock(Leader.class);
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getLeaderIndex(leader)).thenReturn(1);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(5);
    Mockito.when(coord.getY()).thenReturn(6);
    LeaderEvent event = new LeaderEvent(leader, realm, 0, coord);
    event.setPlanetName("Test Planet I");
    event.setText("Text");
    byte[] buffer = event.createByteArray();
    LeaderEvent event2 = LeaderEvent.createLeaderEvent(buffer);
    assertEquals(1, event2.getLeaderIndex());
    assertEquals(0, event2.getPlayerIndex());
    assertEquals(EventType.LEADER_EVENT, event2.getType());
    assertEquals(coord.getX(), event2.getCoordinate().getX());
    assertEquals(coord.getY(), event2.getCoordinate().getY());
    assertEquals("Test Planet I", event2.getPlanetName());
    assertEquals("Text", event2.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testParsingVeryShortLeaderEvent() throws IOException {
    byte[] buffer = {9, 0, 14, 6, 0, 0, 0, 64, 0, 64, 0, 0, 0, 0};
    LeaderEvent event = LeaderEvent.createLeaderEvent(buffer);
    assertEquals(64, event.getCoordinate().getX());
  }

}
