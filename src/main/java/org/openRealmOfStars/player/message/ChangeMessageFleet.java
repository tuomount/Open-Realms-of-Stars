package org.openRealmOfStars.player.message;

import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;

/**
 * Open Realm of Stars game project
 * Copyright (C) 2017 God Beom
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
 * ChangeMessage class used to move focus to fleet
 *
 */

public class ChangeMessageFleet extends ChangeMessage {

    /**
     * constructor ChangeMessageFleet
     * @param starMap StarMap of game class
     * @param starMapView StarMapView of game class
     */
    public ChangeMessageFleet(final StarMap starMap,
            final StarMapView starMapView) {
          super(starMap, starMapView);
    }

    /**
     * Change Message method associated with a Fleet
     * @param data is fleet
     */
    @Override
    public <T> void changeMessage(final T data) {
        Fleet fleet = (Fleet) data;
        if (fleet != null) {
            getStarMap().setCursorPos(fleet.getX(), fleet.getY());
            getStarMap().setDrawPos(fleet.getX(), fleet.getY());
            getStarMapView().setShowFleet(fleet);
            getStarMapView().getStarMapMouseListener()
            .setLastClickedFleet(fleet);
            getStarMapView().getStarMapMouseListener()
            .setLastClickedPlanet(null);
        }
    }
}
