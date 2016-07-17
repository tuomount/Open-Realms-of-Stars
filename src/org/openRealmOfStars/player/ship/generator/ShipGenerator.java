package org.openRealmOfStars.player.ship.generator;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;

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
 * Ship Generator for creating a new design best with current technology
 * 
 */

public class ShipGenerator {
  
  /**
   * Create scout ship with best possible technology. This is used
   * for human players in beginning and AI every time they design
   * new scout ship.
   * @param player whom is designing the new ship
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createScout(PlayerInfo player) {
    ShipDesign result = null;
    Tech[] hullTech = player.getTechList().getListForType(TechType.Hulls);
    Tech hull = TechList.getBestTech(hullTech,"Scout");
    if ( hull != null) {
      
    }
    return result;
  }
  

}
