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
 * Ship damage containing how bad damage was and textual information
 *
 */
public class ShipDamage {

  /**
   * Damage did not cause even a dent
   */
  public static final int NO_DAMAGE_NO_DENT = 1;
  /**
   * No damage
   */
  public static final int NO_DAMAGE = 0;
  /**
   * Damage some component
   */
  public static final int DAMAGED = -1;
  /**
   * Ship is destroyed
   */
  public static final int DESTROYED = -2;

  /**
   * Ship Damage is not ready yet
   */
  public static final int NOT_READY_YET = 0;
  /**
   * Ship damage is ready if logging
   */
  public static final int READY_FOR_LOGGING = 1;
  /**
   * Has been logged
   */
  public static final int LOGGED = 2;

  /** 1 No damage, not even dent
  *   0 No damage, but shield or armor got lower
  *   -1 Got damage
  *   -2 Destroyed
  */
  private int value;

  /**
   * Status for ship damage
   * 0 : Not ready yet
   * 1 : Ready for logging
   * 2 : Logged
   */
  private int status;

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
  public ShipDamage(final int value, final String message) {
    this.value = value;
    this.message = message;
    this.status = NOT_READY_YET;
  }

  /**
   * Is Ship Damage ready for logging
   * @return is the status ready
   */
  public boolean isReady() {
    if (status == READY_FOR_LOGGING) {
      return true;
    }
    return false;
  }

  /**
   * Change ship damage status got logged
   */
  public void logged() {
    status = LOGGED;
  }

  /**
   * Change ship damage status got logged
   */
  public void ready() {
    status = READY_FOR_LOGGING;
  }

  /**
   * Add extra line to message
   * @param text As a string
   */
  public void addText(final String text) {
    message = message + "\n" + text;
  }

  /**
   * Set Ship Damage value four choices:
   * NO_DAMAGE_NO_DENT
   * NO_DAMAGE
   * DAMAGED
   * DESTROYED
   * @param value Ship Damage value
   */
  public void setValue(final int value) {
    this.value = value;
  }

  /**
   * Get ship damage value. There are four choices:
   * NO_DAMAGE_NO_DENT
   * NO_DAMAGE
   * DAMAGED
   * DESTROYED
   * @return Ship Damage value
   */
  public int getValue() {
    return value;
  }

  /**
   * Get Ship damage message
   * @return Ship damage message
   */
  public String getMessage() {
    return message;
  }
}
