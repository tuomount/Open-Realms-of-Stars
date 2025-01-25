package org.openRealmOfStars.starMap.history;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2025 Tuomo Untinen
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
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.history.event.CombatEvent;
import org.openRealmOfStars.starMap.history.event.CultureEvent;
import org.openRealmOfStars.starMap.history.event.DiplomaticEvent;
import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;
import org.openRealmOfStars.starMap.history.event.PlayerStartEvent;

/**
*
* Historical information about game being played
*
*/
public class HistoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    History history = new History();
    assertEquals(0, history.numberOfTurns());
    assertEquals(null, history.getByIndex(0));
    history.addTurn(1);
    assertEquals(1, history.numberOfTurns());
    GalacticEvent event = Mockito.mock(GalacticEvent.class);
    history.addEvent(event);
    assertEquals(event, history.getByIndex(0).getEvent(0));
    assertEquals(history.getByIndex(0), history.getLatestTurn());
    GalacticEvent event2 = Mockito.mock(GalacticEvent.class);
    history.addEvent(event2);
    assertEquals(1, history.numberOfTurns());
    assertEquals(event2, history.getByIndex(0).getEvent(1));
    assertEquals(history.getByIndex(0), history.getLatestTurn());
    history.addTurn(2);
    assertEquals(2, history.numberOfTurns());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    CombatEvent combatEvent = new CombatEvent(coord);
    combatEvent.setText("Historical");
    combatEvent.setPlanetName("Test I");
    CultureEvent cultureEvent = new CultureEvent(coord, 0);
    DiplomaticEvent diplomaticEvent = new DiplomaticEvent(coord);
    diplomaticEvent.setText("Historical");
    diplomaticEvent.setPlanetName("Test planet");
    EventOnPlanet colonizedEvent = new EventOnPlanet(EventType.PLANET_COLONIZED, coord,
        "Test planet", 0);
    colonizedEvent.setText("Historical");
    EventOnPlanet conqueredEvent = new EventOnPlanet(EventType.PLANET_CONQUERED, coord,
        "Test planet", 0);
    conqueredEvent.setText("Historical");
    GalacticEvent event = new GalacticEvent("Test text");
    PlayerStartEvent startEvent = new PlayerStartEvent(coord, "Test I", 1);
    History history = new History();
    history.addTurn(1);
    history.addEvent(conqueredEvent);
    history.addEvent(event);
    history.addTurn(2);
    history.addEvent(colonizedEvent);
    history.addEvent(diplomaticEvent);
    history.addEvent(cultureEvent);
    history.addTurn(3);
    history.addEvent(combatEvent);
    history.addTurn(5);
    history.addEvent(startEvent);
    byte[] buffer = null;
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      history.writeToStream(bos);
      buffer = bos.toByteArray();
    }
    try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer)) {
      History history2 = History.readFromStream(bis);
      assertEquals(history.numberOfTurns(), history2.numberOfTurns());
      assertEquals(2, history2.getByIndex(0).getNumberOfEvents());
      assertEquals(3, history2.getByIndex(1).getNumberOfEvents());
      assertEquals(1, history2.getByIndex(2).getNumberOfEvents());
      assertEquals(1, history2.getByIndex(3).getNumberOfEvents());
      assertEquals(5, history2.getByIndex(3).getTurn());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureUpdateClearCulture() {
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getMaxX()).thenReturn(5);
    Mockito.when(map.getMaxY()).thenReturn(5);
    History history = new History();
    history.addTurn(0);
    CultureEvent event = new CultureEvent(new Coordinate(1,1), 1);
    history.addEvent(event);
    history.addTurn(1);
    assertEquals(0, history.getLatestTurn().getNumberOfEvents());
    history.updateCultureEventMap(map);
    assertEquals(1, history.getLatestTurn().getNumberOfEvents());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCultureUpdate() {
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getMaxX()).thenReturn(5);
    Mockito.when(map.getMaxY()).thenReturn(5);
    CulturePower culturePower = Mockito.mock(CulturePower.class);
    Mockito.when(culturePower.getHighestCulture()).thenReturn(4);
    Mockito.when(map.getSectorCulture(1, 1)).thenReturn(culturePower);
    Mockito.when(map.getSectorCulture(2, 2)).thenReturn(culturePower);
    Mockito.when(map.getSectorCulture(3, 3)).thenReturn(culturePower);
    Mockito.when(map.getSectorCulture(4, 4)).thenReturn(culturePower);
    History history = new History();
    history.addTurn(0);
    CultureEvent event = new CultureEvent(new Coordinate(1,1), 1);
    history.addEvent(event);
    event = new CultureEvent(new Coordinate(2,2), 2);
    history.addEvent(event);
    history.addTurn(1);
    event = new CultureEvent(new Coordinate(3,3), 3);
    history.addEvent(event);
    history.addTurn(2);
    assertEquals(0, history.getLatestTurn().getNumberOfEvents());
    history.updateCultureEventMap(map);
    assertEquals(4, history.getLatestTurn().getNumberOfEvents());
    Event event2 = history.getLatestTurn().getEvent(0);
    if (event2 instanceof CultureEvent) {
      CultureEvent cultureEvent = (CultureEvent) event2;
      assertEquals(4, cultureEvent.getPlayerIndex());
      assertEquals(1, cultureEvent.getCoordinate().getX());
    } else {
      assertFalse(true);
    }
    event2 = history.getLatestTurn().getEvent(1);
    if (event2 instanceof CultureEvent) {
      CultureEvent cultureEvent = (CultureEvent) event2;
      assertEquals(4, cultureEvent.getPlayerIndex());
      assertEquals(2, cultureEvent.getCoordinate().getX());
    } else {
      assertFalse(true);
    }
    event2 = history.getLatestTurn().getEvent(2);
    if (event2 instanceof CultureEvent) {
      CultureEvent cultureEvent = (CultureEvent) event2;
      assertEquals(4, cultureEvent.getPlayerIndex());
      assertEquals(3, cultureEvent.getCoordinate().getX());
    } else {
      assertFalse(true);
    }
    event2 = history.getLatestTurn().getEvent(3);
    if (event2 instanceof CultureEvent) {
      CultureEvent cultureEvent = (CultureEvent) event2;
      assertEquals(4, cultureEvent.getPlayerIndex());
      assertEquals(4, cultureEvent.getCoordinate().getX());
    } else {
      assertFalse(true);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTileOverrideUpdate() {
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getMaxX()).thenReturn(5);
    Mockito.when(map.getMaxY()).thenReturn(5);
    History history = new History();
    history.addTurn(0);
    CultureEvent event = new CultureEvent(new Coordinate(1,1), 1);
    history.addEvent(event);
    event = new CultureEvent(new Coordinate(2,2), 2);
    history.addEvent(event);
    history.addTurn(1);
    event = new CultureEvent(new Coordinate(3,3), 3);
    history.addEvent(event);
    history.addTurn(2);
    EventOnPlanet planetEvent = new EventOnPlanet(
        EventType.ARTIFICAL_PLANET_CREATED, new Coordinate(3,3),
        "Steel sphere 1", 0);
    history.addEvent(planetEvent);
    history.addTurn(3);
    int[][] tiles = history.calculateOverrideTiles(0, map);
    assertEquals(0, tiles[3][3]);
    tiles = history.calculateOverrideTiles(1, map);
    assertEquals(0, tiles[3][3]);
    tiles = history.calculateOverrideTiles(3, map);
    assertEquals(Tiles.getTileByName(TileNames.ARTIFICIALWORLD1).getIndex(),
        tiles[3][3]);
  }


}
