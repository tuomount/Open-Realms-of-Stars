package org.openRealmOfStars.player.message;

import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017  God Beom
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
 * Contains runnable main method which runs the Game class.
 *
 */

public class ChangeMessagePlanet extends ChangeMessage {

	/**
	 * constructor ChangeMessagePlanet
	 * @param starMap StarMap of game class
	 * @param starMapView StarMapView of game class
	 */
    public ChangeMessagePlanet(final StarMap starMap,
            final StarMapView starMapView) {
        super(starMap, starMapView);
    }
    /**
	 * constructor ChangeMessage
	 * @param data is planet
	 */
    @Override
    public <T> void changeMessage(final T data) {
        Planet planet = (Planet) data;
        if (planet != null) {
            starMap.setCursorPos(planet.getX(), planet.getY());
            starMap.setDrawPos(planet.getX(), planet.getY());
            starMapView.setShowPlanet(planet);
            starMapView.getStarMapMouseListener().setLastClickedFleet(null);
            starMapView.getStarMapMouseListener().setLastClickedPlanet(planet);
        }
    }
}
