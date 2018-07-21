package org.openRealmOfStars.player.fleet;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.ship.Ship;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Fleet visibility helper class
*
*/
public class FleetVisibility {

  /**
   * Is fleet drawable aka visible
   */
  private boolean drawShip = false;
  /**
   * Is fleet owner recognized
   */
  private boolean recognized = false;
  /**
   * Is Fleet possible espionage module detected
   */
  private boolean espionageDetected = false;

  /**
   * Construct fleet visibility
   * @param info PlayerInfo who is watching
   * @param fleet Fleet which to watch
   * @param fleetOwnerIndex Fleet's owner index
   */
  public FleetVisibility(final PlayerInfo info, final Fleet fleet,
      final int fleetOwnerIndex) {
    int x = fleet.getCoordinate().getX();
    int y = fleet.getCoordinate().getY();
    if (info.getSectorVisibility(fleet.getCoordinate())
        == PlayerInfo.VISIBLE) {
     if (fleet != null && (info.getSectorCloakDetection(x, y)
        >= fleet.getFleetCloackingValue()
        || info.getFleets().isFleetOnList(fleet))) {
      drawShip = true;
     }
     if (drawShip && fleet.getEspionageBonus() > 0
         && info.getSectorCloakDetection(x, y)
            >= fleet.getFleetCloackingValue() + Ship.ESPIONAGE_HIDE) {
       espionageDetected = true;
     }
    if (!fleet.isPrivateerFleet()) {
      recognized = true;
    } else {
      EspionageList espionage = info.getEspionage().getByIndex(
          fleetOwnerIndex);
      if (espionage != null && espionage.getTotalBonus() >= 4) {
        FleetType fleetType = fleet.getFleetType();
        recognized = espionage.isFleetTypeRecognized(fleetType);
      }
    }
  } else {
    EspionageList espionage = info.getEspionage().getByIndex(
        fleetOwnerIndex);
    if (espionage != null && espionage.getTotalBonus() >= 4) {
      FleetType fleetType = fleet.getFleetType();
      recognized = espionage.isFleetTypeRecognized(fleetType);
      if (recognized) {
        drawShip = true;
      }
    }
  }
  }
  /**
   * Is fleet visible aka drawable?
   * @return True if visible
   */
  public boolean isFleetVisible() {
    return drawShip;
  }

  /**
   * Is fleet owner recognized?
   * @return True if fleet owner is known
   */
  public boolean isRecognized() {
    return recognized;
  }

  /**
   * Has fleet's possible espionage module detected
   * @return True if espionage is detected. False means
   * that espionage module is not detect or fleet does not have it.
   */
  public boolean isEspionageDetected() {
    return espionageDetected;
  }
}
