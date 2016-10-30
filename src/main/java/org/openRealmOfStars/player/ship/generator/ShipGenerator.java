package org.openRealmOfStars.player.ship.generator;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.utilities.DiceGenerator;

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
   * Score components for battle ship
   * @param design Design for ship
   * @param player Player doing the ship
   * @param components Component list available
   * @return array of scores
   */
  private static int[] scoreComponents(ShipDesign design, PlayerInfo player, ArrayList<ShipComponent> components) {
    int[] scores = new int[components.size()];
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      scores[i] = 0;
      scores[i] = scores[i] -comp.getEnergyRequirement();
      scores[i] = scores[i] +comp.getEnergyResource();
      scores[i] = scores[i] -comp.getCost()/4;
      scores[i] = scores[i] -comp.getMetalCost()/4;
      switch (comp.getType()) {
      case ARMOR: {
        scores[i] = scores[i] +comp.getDefenseValue()*5;
        scores[i] = scores[i] +5; // No need for electricity
        break;
      }
      case ORBITAL_BOMBS: {
        if (!design.gotCertainType(ShipComponentType.ORBITAL_BOMBS) &&
            !design.gotCertainType(ShipComponentType.ORBITAL_NUKE)) {
          if (design.getHull().getSize() == ShipSize.MEDIUM) {
            scores[i] = scores[i] +comp.getDamage()/10;
          }
          if (design.getHull().getSize() == ShipSize.LARGE) {
            scores[i] = scores[i] +comp.getDamage()/5;
          }
          if (design.getHull().getSize() == ShipSize.HUGE) {
            scores[i] = scores[i] +comp.getDamage()/3;
          }
          if (design.getHull().getSize() == ShipSize.SMALL) {
            scores[i] = scores[i] +comp.getDamage()/20;
          }
        } else  {
          scores[i] = -1;
        }
        break;
      }
      case ORBITAL_NUKE: {
        if (!design.gotCertainType(ShipComponentType.ORBITAL_BOMBS) &&
            !design.gotCertainType(ShipComponentType.ORBITAL_NUKE)) {
          if (design.getHull().getSize() == ShipSize.MEDIUM) {
            scores[i] = scores[i] +comp.getDamage()/10;
          }
          if (design.getHull().getSize() == ShipSize.LARGE) {
            scores[i] = scores[i] +comp.getDamage()/5;
          }
          if (design.getHull().getSize() == ShipSize.HUGE) {
            scores[i] = scores[i] +comp.getDamage()/3;
          }
          if (design.getHull().getSize() == ShipSize.SMALL) {
            scores[i] = scores[i] +comp.getDamage()/20;
          }
          if (player.getRace() == SpaceRace.CENTAURS) {
            // Centaurs do not like nukes
            scores[i] = scores[i] -5;
          }
          if (player.getRace() == SpaceRace.MECHIONS) {
            // Mechions use nukes more likely
            scores[i] = scores[i] +5;
          }
          if (player.getRace() == SpaceRace.GREYANS) {
            // Greyans use nukes more likely
            scores[i] = scores[i] +2;
          }
        } else  {
          scores[i] = -1;
        }
        break;
      }
      case CLOAKING_DEVICE: {
        if (!design.gotCertainType(ShipComponentType.CLOAKING_DEVICE)) { 
          scores[i] = scores[i] +comp.getCloaking()/10;
        } else  {
          scores[i] = -1;
        }
        break;
      }
      case JAMMER: {
        if (!design.gotCertainType(ShipComponentType.JAMMER)) { 
          scores[i] = scores[i] +comp.getDefenseValue()*2;
        } else {
          scores[i] = -1;
        }
        break;
      }
      case SCANNER: {
        if (!design.gotCertainType(ShipComponentType.CLOAKING_DEVICE)) { 
          scores[i] = scores[i] +comp.getScannerRange()*2;
          scores[i] = scores[i] +comp.getCloakDetection()/10;
        } else {
          scores[i] = -1;
        }
        break;
      }
      case SHIELD: {
        scores[i] = scores[i] +comp.getDefenseValue();
        scores[i] = scores[i] +5; // Recharge
        break;
      }
      case SHIELD_GENERATOR: {
        if (design.getTotalShield() > 0 &&
            !design.gotCertainType(ShipComponentType.SHIELD_GENERATOR)) { 
          scores[i] = scores[i] +25; 
        } else {
          scores[i] = -1; // No shield
        }
        break;
      }
      case TARGETING_COMPUTER: {
        if (!design.gotCertainType(ShipComponentType.TARGETING_COMPUTER)) { 
          scores[i] = scores[i] +comp.getDamage(); 
        } else {
          scores[i] = -1; // No shield
        }
        break;
      }
      case WEAPON_BEAM:
      case WEAPON_ECM_TORPEDO:
      case WEAPON_HE_MISSILE:
      case WEAPON_PHOTON_TORPEDO:
      case WEAPON_RAILGUN:{
        scores[i] = scores[i] +comp.getDamage()*5; 
        break;
      }
      default: { break; }
      }
    }
    return scores;
  }
  
  /**
   * Design new battle ship for certain size
   * @param player Player doing the design
   * @param size Ship Size
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createBattleShip(PlayerInfo player, ShipSize size) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    ShipHull hull = null;
    int value = 0;
    for (Tech tech : hullTechs) {
       ShipHull tempHull = ShipHullFactory.createByName(tech.getHull(), player.getRace());
       if (tempHull.getHullType() ==ShipHullType.NORMAL && tempHull.getSize() == size) {
         int tempValue = tempHull.getMaxSlot()*tempHull.getSlotHull();
         if (tempValue > value) {
           value = tempValue;
           hull = tempHull;
         }
       }
    }
    if (hull != null) {
      result = new ShipDesign(hull);
      String[] part = hull.getName().split("Mk");
      result.setName(part[0].trim() +" Mk"+(player.getShipStatStartsWith(part[0])+1));
      ShipComponent engine = ShipComponentFactory.createByName(player.
          getTechList().getBestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName(player.
          getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      ShipComponent weapon = ShipComponentFactory.createByName(player.
          getTechList().getBestWeapon().getComponent());
      result.addComponent(weapon);
      
      Tech[] defenseTechs = player.getTechList().getListForType(TechType.Defense);
      Tech[] electricsTechs = player.getTechList().getListForType(TechType.Electrics);
      Tech[] combatTechs = player.getTechList().getListForType(TechType.Combat);
      Tech shield = TechList.getBestTech(defenseTechs,"Shield");
      Tech armor = TechList.getBestTech(defenseTechs,"Armor plating");
      Tech shieldGen = TechList.getBestTech(electricsTechs,"Shield generator");
      ShipComponent shieldComp = null;
      ShipComponent shieldGenComp = null;
      ShipComponent armorComp = null;
      ArrayList<ShipComponent> components = new ArrayList<>();
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(
          shield.getComponent());
        components.add(shieldComp);
      }
      if (shieldGen != null) {
        shieldGenComp = ShipComponentFactory.createByName(
          shieldGen.getComponent());
        components.add(shieldGenComp);
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(
          armor.getComponent());
        components.add(armorComp);
      }
      if (shieldComp != null && 
          result.getFreeEnergy()>=shieldComp.getEnergyRequirement()) {
        result.addComponent(shieldComp);
      } else if (armorComp != null){
        result.addComponent(armorComp);
      }
      Tech weapTech = player.getTechList().getBestWeapon(ShipComponentType.WEAPON_BEAM);
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList().getBestWeapon(ShipComponentType.WEAPON_ECM_TORPEDO);
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList().getBestWeapon(ShipComponentType.WEAPON_HE_MISSILE);
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList().getBestWeapon(ShipComponentType.WEAPON_PHOTON_TORPEDO);
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList().getBestWeapon(ShipComponentType.WEAPON_RAILGUN);
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      Tech elecTech = TechList.getBestTech(electricsTechs,"Jammer");
      if (elecTech != null) {
        components.add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs,"Targeting computer");
      if (elecTech != null) {
        components.add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs,"Scanner");
      if (elecTech != null) {
        components.add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs,"Cloaking device");
      if (elecTech != null) {
        components.add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs,"LR scanner");
      if (elecTech != null) {
        components.add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(combatTechs, "Orbital bombs");
      weapTech = TechList.getBestTech(combatTechs, "Orbital smart bombs");
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      } else if (elecTech != null) {
        components.add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      weapTech = TechList.getBestTech(combatTechs, "Orbital nuke");
      if (weapTech != null) {
        components.add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      int[] componentScores = new int[components.size()]; 
      int safetyCount = 500;
      while(result.getFreeSlots()>0 && safetyCount > 0) {
        safetyCount--;
        componentScores = scoreComponents(result, player, components);
        int sum = 0;
        for (int i=0;i<componentScores.length;i++) {
          if (componentScores[i] > 0) {
            sum = sum + componentScores[i];
          }
        }
        int choice = DiceGenerator.getRandom(sum);
        int total = 0;
        for (int i=0;i<componentScores.length;i++) {
          if (componentScores[i] > 0) {            
            if (choice < total+componentScores[i]) {
              if (result.getFreeEnergy() >=components.get(i).getEnergyRequirement()) {
                result.addComponent(components.get(i));
              }  else if (result.getFreeSlots() > 1 
                  && power.getEnergyResource()+result.getFreeEnergy() >=
                  components.get(i).getEnergyRequirement()) {
                result.addComponent(components.get(i));
                result.addComponent(power);
              }
              break;
            }
            total = total +componentScores[i];
          }
        }
        
      }


    }
    return result;
  }

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
      if (player.getRace() == SpaceRace.CENTAURS) {
        // Centaurs could ignore defense since they got more hull points
        result.addComponent(weapon);
      } else {
        if (shieldComp != null && 
            result.getFreeEnergy()>=shieldComp.getEnergyRequirement()) {
          result.addComponent(shieldComp);
        } else if (armorComp != null){
          result.addComponent(armorComp);
        }
      }
    }
    return result;
  }

  /**
   * Create colony/troop ship with best possible technology. This is used
   * for human players in beginning and AI every time they design
   * new colony/troop ship.
   * @param player whom is designing the new ship
   * @param troop Is ship going to be troop carrier
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createColony(PlayerInfo player,boolean troop) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    int value = -1;
    Tech hullTech = null;
    for (Tech tech : hullTechs) {
      ShipHull hull = ShipHullFactory.createByName(tech.getHull(), player.getRace());
      if (hull.getMaxSlot() > value && hull.getHullType() == ShipHullType.FREIGHTER) {
        hullTech = tech;
        value = hull.getMaxSlot();
      }
      if (!troop && hullTech != null &&
          hullTech.getName().equals("Medium freighter")) {
        break;
      }
    }
    Tech[] defenseTechs = player.getTechList().getListForType(TechType.Defense);
    if ( hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),player.getRace());
      result = new ShipDesign(hull);
      if (!troop) {
        result.setName("Colony");
      } else {
        result.setName("Trooper");
      }
      ShipComponent engine = ShipComponentFactory.createByName(player.
          getTechList().getBestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName(player.
          getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      if (!troop) {
        ShipComponent colony = ShipComponentFactory.createByName("Colonization module");
        result.addComponent(colony);
        
      } else {
        Tech[] electricsTechs = player.getTechList().getListForType(TechType.Electrics);
        Tech invasionModule = TechList.getBestTech(electricsTechs,"Planetary invasion module");
        Tech shockModule = TechList.getBestTech(electricsTechs,"Shock trooper module");
        if (shockModule != null) {
          ShipComponent trooper = ShipComponentFactory.createByName(shockModule.getComponent());
          result.addComponent(trooper);
        } else if (invasionModule != null) {
          ShipComponent trooper = ShipComponentFactory.createByName(invasionModule.getComponent());
          result.addComponent(trooper);          
        } else {
          // Cannot build a trooper
          return null;
        }
      }
      if (result.getFreeSlots() > 2) {
        Tech armor = TechList.getBestTech(defenseTechs,"Armor plating");
        ShipComponent armorComp = null;
        if (armor != null) {
          armorComp = ShipComponentFactory.createByName(
            armor.getComponent());
          result.addComponent(armorComp);
        }
      }
      if (result.getFreeSlots() > 2) {
        Tech shield = TechList.getBestTech(defenseTechs,"Shield");
        ShipComponent shieldComp = null;
        if (shield != null) {
          shieldComp = ShipComponentFactory.createByName(
            shield.getComponent());
          if (shieldComp.getEnergyRequirement() <= result.getFreeEnergy()) {
            result.addComponent(shieldComp);
          }
        }
      }
    }
    return result;
  }


}
