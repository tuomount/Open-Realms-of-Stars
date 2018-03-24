package org.openRealmOfStars.starMap.history.event;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;

public class PlayerStartEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    PlayerStartEvent event = new PlayerStartEvent(coord,
        "Test planet", 0);
    assertEquals(EventType.PLAYER_START, event.getType());
    assertEquals(coord, event.getCoordinate());
    assertEquals("Test planet", event.getName());
    assertEquals(0, event.getPlayerIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    PlayerStartEvent event = new PlayerStartEvent(coord, "Test I", 1);
    byte[] buf = event.createByteArray();
    PlayerStartEvent event2 = PlayerStartEvent.createStartEvent(buf);
    assertEquals(EventType.PLAYER_START, event2.getType());
    assertEquals(event.getName(), event2.getName());
    assertEquals(22, event2.getCoordinate().getX());
    assertEquals(11, event2.getCoordinate().getY());
    assertEquals(1, event2.getPlayerIndex());
  }

}
