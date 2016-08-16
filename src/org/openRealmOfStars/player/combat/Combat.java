package org.openRealmOfStars.player.combat;

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;


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
 * All the combat data, containing ships in single list in initiative order
 * 
 */

public class Combat {

  /**
   * Ships in list in initiative order
   */
  private ArrayList<CombatShip> shipList;
  
  
  /**
   * Build shipList in initiative order
   * @param fleet1 Player1 fleet
   * @param fleet2 Player2 fleet
   * @param info1 Player1 info
   * @param info2 Player2 Info
   */
  public Combat(Fleet fleet1, Fleet fleet2, PlayerInfo info1, PlayerInfo info2) {
    Ship[] ships = fleet1.getShips();
    for (Ship ship : ships) {
      CombatShip combatShp = new CombatShip(ship, info1, 4, 7);
      shipList.add(combatShp);
    }
    ships = fleet2.getShips();
    for (Ship ship : ships) {
      CombatShip combatShp = new CombatShip(ship, info2, 4, 7);
      shipList.add(combatShp);
    }
    Collections.sort(shipList);
  }
}
