package org.openRealmOfStars.player.message;

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
import org.openRealmOfStars.game.States.StarMapView;
import org.openRealmOfStars.starMap.StarMap;

public abstract class ChangeMessage {
	/**
	 * StarMap in game
	 */
    public StarMap starMap;
    
    /**
	 * StarMapView in game
	 */
    public StarMapView starMapView;

    /**
	 * constructor ChangeMessage
	 * @param starMap StarMap of game class
	 * @param starMapView StarMapView of game class
	 */
    public ChangeMessage(final StarMap starMap, final StarMapView starMapView) {
        this.starMap = starMap;
        this.starMapView = starMapView;
    }
    
    /**
	 * Inherited part to use Template Method Pattern
	 * @param data depend on inherited class
	 */
    public abstract <T> void changeMessage(T data);
}
