package org.openRealmOfStars.player.ship.shipdesign;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 wksdn18
* Copyright (C) 2017 Tuomo Untinen
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
* ShipDesignConsts Message for handling errors.
*
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
   * Message when ship contains weapons even hull does not allow them.
   */
  public static final String NO_WEAPONS_ALLOWED = "No weapons allowed in ";

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
