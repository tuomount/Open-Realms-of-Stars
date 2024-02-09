package org.openRealmOfStars.player.fleet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.espionage.Intelligence;
import org.openRealmOfStars.player.espionage.IntelligenceList;
import org.openRealmOfStars.starMap.Coordinate;

/**
*
* Fleet visibility helper class
*
*/

public class FleetVisibilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Intelligence espionage = Mockito.mock(Intelligence.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getIntelligence()).thenReturn(espionage);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(10);
    Mockito.when(coord.getY()).thenReturn(12);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.VISIBLE);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    int fleetOwnerIndex = 1;
    FleetVisibility visiblity = new FleetVisibility(info, fleet, fleetOwnerIndex);
    assertEquals(true, visiblity.isFleetVisible());
    assertEquals(false, visiblity.isEspionageDetected());
    assertEquals(true, visiblity.isRecognized());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEspionage() {
    Intelligence espionage = Mockito.mock(Intelligence.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getIntelligence()).thenReturn(espionage);
    Mockito.when(info.getSectorCloakDetection(10, 12)).thenReturn(80);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(10);
    Mockito.when(coord.getY()).thenReturn(12);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.VISIBLE);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    Mockito.when(fleet.getEspionageBonus()).thenReturn(1);

    int fleetOwnerIndex = 1;
    FleetVisibility visiblity = new FleetVisibility(info, fleet, fleetOwnerIndex);
    assertEquals(true, visiblity.isFleetVisible());
    assertEquals(true, visiblity.isEspionageDetected());
    assertEquals(true, visiblity.isRecognized());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEspionageVisibility() {
    IntelligenceList espionageList = Mockito.mock(IntelligenceList.class);
    Mockito.when(espionageList.getTotalBonus()).thenReturn(10);
    Mockito.when(espionageList.isFleetTypeRecognized(FleetType.PRIVATEER)).thenReturn(true);
    Intelligence espionage = Mockito.mock(Intelligence.class);
    Mockito.when(espionage.getByIndex(1)).thenReturn(espionageList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getIntelligence()).thenReturn(espionage);
    Mockito.when(info.getSectorCloakDetection(10, 12)).thenReturn(80);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(10);
    Mockito.when(coord.getY()).thenReturn(12);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.UNCHARTED);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    Mockito.when(fleet.getEspionageBonus()).thenReturn(1);
    Mockito.when(fleet.getFleetType()).thenReturn(FleetType.PRIVATEER);

    int fleetOwnerIndex = 1;
    FleetVisibility visiblity = new FleetVisibility(info, fleet, fleetOwnerIndex);
    assertEquals(true, visiblity.isFleetVisible());
    assertEquals(false, visiblity.isEspionageDetected());
    assertEquals(true, visiblity.isRecognized());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPrivateer() {
    Intelligence espionage = Mockito.mock(Intelligence.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getIntelligence()).thenReturn(espionage);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(10);
    Mockito.when(coord.getY()).thenReturn(12);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.VISIBLE);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    Mockito.when(fleet.isPrivateerFleet()).thenReturn(true);
    int fleetOwnerIndex = 1;
    FleetVisibility visiblity = new FleetVisibility(info, fleet, fleetOwnerIndex);
    assertEquals(true, visiblity.isFleetVisible());
    assertEquals(false, visiblity.isEspionageDetected());
    assertEquals(false, visiblity.isRecognized());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHidden() {
    Intelligence espionage = Mockito.mock(Intelligence.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getIntelligence()).thenReturn(espionage);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(10);
    Mockito.when(coord.getY()).thenReturn(12);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.UNCHARTED);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    int fleetOwnerIndex = 1;
    FleetVisibility visiblity = new FleetVisibility(info, fleet, fleetOwnerIndex);
    assertEquals(false, visiblity.isFleetVisible());
    assertEquals(false, visiblity.isEspionageDetected());
    assertEquals(false, visiblity.isRecognized());
  }

}
