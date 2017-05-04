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
* Bottom team starting position list
*/
public class BottomPositionList extends CombatPositionList {
    @Override
    public CombatCoordinate getCoordinate(int combatShipIndex) {
        switch (combatShipIndex) {
        case 0:
            return new CombatCoordinate(4, 7);
        case 1:
            return new CombatCoordinate(3, 7);
        case 2:
            return new CombatCoordinate(5, 7);
        case 3:
            return new CombatCoordinate(2, 7);
        case 4:
            return new CombatCoordinate(6, 7);
        case 5:
            return new CombatCoordinate(4, 8);
        case 6:
            return new CombatCoordinate(1, 7);
        case 7:
            return new CombatCoordinate(7, 7);
        case 8:
            return new CombatCoordinate(3, 8);
        case 9:
            return new CombatCoordinate(5, 8);
        case 10:
            return new CombatCoordinate(2, 8);
        case 11:
            return new CombatCoordinate(6, 8);
        case 12:
            return new CombatCoordinate(0, 7);
        case 13:
            return new CombatCoordinate(8, 7);
        case 14:
            return new CombatCoordinate(1, 8);
        case 15:
            return new CombatCoordinate(7, 8);
        case 16:
            return new CombatCoordinate(0, 8);
        case 17:
            return new CombatCoordinate(8, 8);
        default:
          throw new IllegalArgumentException("Fleet contains too many ships!");
        }
    }
}
