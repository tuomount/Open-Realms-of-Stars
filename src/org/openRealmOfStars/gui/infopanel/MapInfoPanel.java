package org.openRealmOfStars.gui.infopanel;

import org.openRealmOfStars.starMap.Planet;

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
 * Handling info on next to the star map
 * 
 */

public class MapInfoPanel extends InfoPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Show info about the planet
   */
  private Planet planet;
  
  /**
   * Show planet on info panel
   * @param planet
   */
  public void showPlanet(Planet planet) {
    this.planet = planet;
  }
  
  /**
   * Show empty planet
   */
  public void showEmpty() {
    this.planet = null;
    
  }
}
