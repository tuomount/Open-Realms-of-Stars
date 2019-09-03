package org.openRealmOfStars.starMap.randomEvent;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Random Event
*
*/
public class RandomEvent {

  /**
   * Bad Random event type. Event should have either bad or good type.
   * Not both.
   */
  private BadRandomType badType;

  /**
   * Good Random event type. Event should have either bad or good type.
   * Not both.
   */
  private GoodRandomType goodType;

  /**
   * Realm info who is getting the random event.
   */
  private PlayerInfo realm;

  /**
   * Planet where event happens. This can be null if
   * event does not happen on certain planet.
   */
  private Planet planet;

  /**
   * Solar system where event happens. This can be null
   * if event does not affect for the whole solar system.
   */
  private Sun sun;
  /**
   * Fleet where event happens. This can be null if
   * event does not happen on certain fleet.
   */
  private Fleet fleet;
  /**
   * Constructor for Bad random event type.
   * @param badType Bad random event type
   * @param info Realm info who is experiencing bad event.
   */
  public RandomEvent(final BadRandomType badType, final PlayerInfo info) {
    this.badType = badType;
    this.goodType = null;
    this.realm = info;
    this.setPlanet(null);
    this.setSun(null);
    this.setFleet(null);
  }

  /**
   * Constructor for Good random event type.
   * @param goodType Good random event type
   * @param info Realm info who is experiencing good event.
   */
  public RandomEvent(final GoodRandomType goodType, final PlayerInfo info) {
    this.badType = null;
    this.goodType = goodType;
    this.realm = info;
  }

  /**
   * Get random event's bad type.
   * @return Bad Event type or null.
   */
  public BadRandomType getBadType() {
    return badType;
  }

  /**
   * Get random event's good type.
   * @return Good event type or null.
   */
  public GoodRandomType getGoodType() {
    return goodType;
  }

  /**
   * Get realm who is experiencing the random event.
   * @return PlayerInfo
   */
  public PlayerInfo getRealm() {
    return realm;
  }

  /**
   * Get planet where event happens.
   * @return the planet or null
   */
  public Planet getPlanet() {
    return planet;
  }

  /**
   * Set planet where event happens.
   * @param planet the planet to set
   */
  public void setPlanet(final Planet planet) {
    this.planet = planet;
  }

  /**
   * Get the sun where event happens.
   * @return the sun or null
   */
  public Sun getSun() {
    return sun;
  }

  /**
   * Set the sun where event happens.
   * @param sun the sun to set
   */
  public void setSun(final Sun sun) {
    this.sun = sun;
  }

  /**
   * Get the fleet where event happens.
   * @return the fleet
   */
  public Fleet getFleet() {
    return fleet;
  }

  /**
   * Set the fleet where event happens.
   * @param fleet the fleet to set
   */
  public void setFleet(final Fleet fleet) {
    this.fleet = fleet;
  }
}
