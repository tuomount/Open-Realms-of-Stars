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
 * Ship damage containig how bad damage was and textual information
 * 
 */
public class ShipDamage {

  public static int NO_DAMAGE_NO_DENT = 1;
  public static int NO_DAMAGE = 0;
  public static int DAMAGED = -1;
  public static int DESTROYED = -2;
  
  /** 1 No damage, not even dent
  *   0 No damage, but shield or armor got lower
  *   -1 Got damage
  *   -2 Destroyed
  */
  private int value;
  
  /**
   * Textual description of damage(s)
   * These can be splitted with LF
   */
  private String message;
  
  /**
   * Create ship damage information
   * @param value 1 No damage, not even dent
  *   0 No damage, but shield or armor got lower
  *   -1 Got damage
  *   -2 Destroyed
   * @param message Textual description of damages. Can be multiline, 
   * just split with LF
   */
  public ShipDamage(int value, String message) {
    this.value = value;
    this.message = message;
  }
  
  /**
   * Add extra line to message
   * @param text As a string
   */
  public void addText(String text) {
    message = message+"\n"+text;
  }
  
  public void setValue(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return value;
  }
  public String getMessage() {
    return message;
  }
}
