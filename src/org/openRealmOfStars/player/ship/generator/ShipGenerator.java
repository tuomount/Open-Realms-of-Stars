package org.openRealmOfStars.player.ship.generator;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
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
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    Tech hullTech = TechList.getBestTech(hullTechs,"Scout");
    Tech[] defenseTechs = player.getTechList().getListForType(TechType.Defense);
    if ( hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),player.getRace());
      result = new ShipDesign(hull);
      result.setName("Scout");
      ShipComponent engine = ShipComponentFactory.createByName(player.
          getTechList().getBestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName(player.
          getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      ShipComponent weapon = ShipComponentFactory.createByName(player.
          getTechList().getBestWeapon().getComponent());
      result.addComponent(weapon);
      Tech shield = TechList.getBestTech(defenseTechs,"Shield");
      Tech armor = TechList.getBestTech(defenseTechs,"Armor plating");
      ShipComponent shieldComp = null;
      ShipComponent armorComp = null;
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(
          shield.getComponent());
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(
          armor.getComponent());
      }
      if (shieldComp != null && 
          result.getFreeEnergy()>=shieldComp.getEnergyRequirement()) {
        result.addComponent(shieldComp);
      } else if (armorComp != null){
        result.addComponent(armorComp);
      }
    }
    return result;
  }
  

}
