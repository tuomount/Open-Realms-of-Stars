package org.openRealmOfStars.starMap.history.event;

import org.openRealmOfStars.starMap.Coordinate;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Diplomatic Event
*
*/
public class DiplomaticEvent extends Event {

  /**
   * Coordinate where diplomatic event happened
   */
  private Coordinate coordinate;

  /**
   * Planet name if diplomatic event happened on planet. Null if on space
   */
  private String planetName;

  /**
   * Text for diplomatic event
   */
  private String text;

  /**
   * Constructor for diplomatic event
   * @param coord Coordinate for event
   */
  public DiplomaticEvent(final Coordinate coord) {
    setType(EventType.DIPLOMATIC_RELATION_CHANGE);
    setText("");
    setPlanetName(null);
    coordinate = coord;
  }
  /**
   * Get coordinate where diplomatic happened.
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get Planet name or null if diplomatic happened in space
   * @return Planet name or null
   */
  public String getPlanetName() {
    return planetName;
  }

  /**
   * Set planet name where diplomatic event happened. Set null
   * if diplomatic happened in space
   * @param name Planet name or null
   */
  public void setPlanetName(final String name) {
    planetName = name;
  }

  /**
   * Get historical text about the event
   * @return Historical text
   */
  public String getText() {
    return text;
  }

  /**
   * Set historical text for diplomatic event
   * @param historicalText to set
   */
  public void setText(final String historicalText) {
    text = historicalText;
  }
}
