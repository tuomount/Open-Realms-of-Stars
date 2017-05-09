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
* abstract class for Combat starting position list
*/
public abstract class CombatPositionList {
    /**
     * constructor
     * @param combatShipIndex index of combatShip
     * @return CombatCoordinate
     */
    public CombatCoordinate getCoordinate(final int combatShipIndex) {
        return null;
    }
    /**
     * Get starting position X for ships
     * @param combatShipIndex Ship Index in fleet
     * @return Coordinate X
     */
    public int getStartPosX(final int combatShipIndex) {
        CombatCoordinate combatCoordinate = getCoordinate(combatShipIndex);
        int coordinateX = combatCoordinate.getX();
        return coordinateX;
    }

    /**
     * Get starting position Y for ships
     * @param combatShipIndex Ship Index in fleet
     * @return Coordinate Y
     */
    public int getStartPosY(final int combatShipIndex) {
        CombatCoordinate combatCoordinate = getCoordinate(combatShipIndex);
        int coordinateY = combatCoordinate.getY();
        return coordinateY;
    }
}
