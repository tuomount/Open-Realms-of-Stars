package org.openRealmOfStars.starMap.history.event;

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
* Galactic event
*
*/
public class GalacticEvent extends Event {

  /**
   * Text for Galactic Event
   */
  private String text;

  /**
   * Create Galactic Event
   * @param text Text for galactic event
   */
  public GalacticEvent(final String text) {
    setType(EventType.GALATIC_NEWS);
    setText(text);
  }

  /**
   * Get historical text about the event
   * @return Historical text
   */
  public String getText() {
    return text;
  }

  /**
   * Set historical text for galactic event
   * @param historicalText to set
   */
  public void setText(final String historicalText) {
    text = historicalText;
  }
}
