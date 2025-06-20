package org.openRealmOfStars.player.ship.shipdesign;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017 wksdn18
 * Copyright (C) 2017-2021 Tuomo Untinen
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

/**
* ShipDesignConsts Message for handling errors.
*/
public final class ShipDesignConsts {

  /**
   * Message when ship design is good to go
   */
  public static final String DESIGN_OK = "Design is OK!";

  /**
   * Message when ship is missing engine
   */
  public static final String ENGINE_IS_MISSING = "Engine is missing!\n";
  /**
   * Message when orbital has engine
   */
  public static final String ORBITAL_HAS_ENGINE = "Orbital does not need"
      + " engine!\n";
  /**
   * Message when ship has both colonization and planetary invasion modules.
   */
  public static final String BOTH_COLONY_AND_INVASION = "Only colony or"
      + " invasion module is allowed!\n";
  /**
   * Message when ship has no cargo space.
   */
  public static final String NO_CARGO = "No cargo space!\n";
  /**
   * Message when ship is has two different engines
   */
  public static final String TWO_ENGINES = "Two different engines are not"
      + " allowed\n";
  /**
   * Message when ship contains weapons even hull does not allow them.
   */
  public static final String NO_WEAPONS_ALLOWED = "No weapons allowed in ";
  /**
   * Message when ship contains weapons more weapons than single, even one is
   * allowd.
   */
  public static final String SINGLE_WEAPON_ALLOWED = "Single weapon allowed"
      + " in ";

  /**
   * Message when ship contains more than one jammer
   */
  public static final String MANY_JAMMERS = "Only one jammer is allowed!";
  /**
   * Message when ship contains more than one targeting computer
   */
  public static final String MANY_COMPUTERS = "Only one targeting computer"
   + " is allowed!";
  /**
   * Message when ship contains more than one espionage module
   */
  public static final String MANY_ESPIONAGE = "Only one espionage module"
   + " is allowed!";
  /**
   * Message when ship contains starbase modules
   */
  public static final String STARBASE_MODULE_IN_NOT_STARBASE = "No starbase"
      + " modules allowed in regular ships!";
  /**
   * Message when ship contains privateer modules
   */
  public static final String PRIVATEER_MODULE_IN_NOT_PRIVATEER = "No privateer"
      + " modules allowed in regular ships!";
  /**
   * Message when privateer ship contains no privateer module
   */
  public static final String PRIVATEER_MODULE_MISSING = "No privateer"
      + " module in privateer ship!";
  /**
   * Message when privateer ships are banned
   */
  public static final String PRIVATEERS_ARE_BANNED = "Privateer"
      + " designs are banned!";
  /**
   * Message when ship contains orbital nukes and those are banned
   */
  public static final String NUKES_ARE_BANNED = "Orbital nuclear"
      + " weapons are banned!";
  /**
   * Message when ship does not contain any components.
   */
  public static final String NO_COMPONENTS = "No components yet";
  /**
   * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
   * and so on. Thus, the caller should be prevented from constructing objects
   * of this class, by declaring this private constructor.
   */
  private ShipDesignConsts() {
// this prevents even the native class from
// calling this ctor as well :
    throw new AssertionError();
  }
}
