package org.openRealmOfStars.player.combat;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Diocto
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
* Coordinate used only in combat
*/
public class CombatCoordinate {
    /**
     * coordinate x for combat map
     */
    private int x;
    /**
     * coordinate y for combat map
     */
    private int y;

    /**
     * CombatCoordinate Constructor
     * @param x coordinate x for combat map
     * @param y coordinate y for combat map
     */
    CombatCoordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * @return coordinate x for combat map
     */
    public int getX() {
        return x;
    }
    /**
     * @param x coordinate x for combat map
     */
    public void setX(final int x) {
        this.x = x;
    }
    /**
     * @return coordinate y for combat map
     */
    public int getY() {
        return y;
    }
    /**
     * @param y coordinate y for combat map
     */
    public void setY(final int y) {
        this.y = y;
    }
}
