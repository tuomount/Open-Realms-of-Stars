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
* Historical event on planet
*
*/
public class EventOnPlanet extends Event {

  /**
   * Coordinate for planet
   */
  private Coordinate coordinate;

  /**
   * Planet name
   */
  private String name;

  /**
   * Text for planetary event
   */
  private String text;

  /**
   * Creates historical event on planet. Text on event is empty
   * at constructor.
   * @param type Type should be EventType.PLANET_COLONIZED
   *                         or EventType.PLANET_CONQUERED
   * @param coord Coordinate where planet is.
   * @param planetName Planet name
   */
  public EventOnPlanet(final EventType type, final Coordinate coord,
      final String planetName) {
    if (type == EventType.PLANET_COLONIZED
        || type == EventType.PLANET_CONQUERED) {
      setType(type);
    } else {
      throw new IllegalArgumentException("Type is not planetary event!");
    }
    coordinate = coord;
    name = planetName;
    text = "";
  }

  /**
   * Get planet coordinate
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get Planet name.
   * @return Planet name
   */
  public String getName() {
    return name;
  }

  /**
   * Get historical text about the event
   * @return Historical text
   */
  public String getText() {
    return text;
  }

  /**
   * Set historical text for event on planet
   * @param historicalText to set
   */
  public void setText(final String historicalText) {
    text = historicalText;
  }
}
