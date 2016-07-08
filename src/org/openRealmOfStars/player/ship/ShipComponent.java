package org.openRealmOfStars.player.ship;

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
 * Ship component for handling shields, armors, weapons, engines etc.
 * 
 */
public class ShipComponent {

  /**
   * Component name must match one on techs
   */
  private String name;
  
  /**
   * Component energy requirement
   */
  private int energyRequirement;
  
  /**
   * Faster than light speed aka route speed
   */
  private int ftlSpeed;

  /**
   * Regular speed
   */
  private int speed;

  /**
   * Tactic speed in combat
   */
  private int tacticSpeed;

  /**
   * RadiationLevel, component radiates so might kill population on ship
   * Between 1-10;
   */
  private int radiation;
  
  /**
   * Scanner range if component is scanner
   */
  private int scannerRange;
  
  /**
   * Cloak detection in scanner
   */
  private int cloakDetection;

  /**
   * Cloaking device value
   */
  private int cloaking;

  /**
   * defense value, could be either shield or armor
   */
  private int defenseValue;
  
  /**
   * weapon damage
   */
  private int damage;


  
}
