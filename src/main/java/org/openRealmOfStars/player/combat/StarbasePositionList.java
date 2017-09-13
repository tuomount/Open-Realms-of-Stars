package org.openRealmOfStars.player.combat;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* Starbase starting position list
*/
public class StarbasePositionList extends CombatPositionList {
    @Override
    public CombatCoordinate getCoordinate(final int combatShipIndex) {
        switch (combatShipIndex) {
        case 0:
            return new CombatCoordinate(4, 2);
        case 1:
            return new CombatCoordinate(3, 2);
        case 2:
            return new CombatCoordinate(5, 2);
        case 3:
            return new CombatCoordinate(2, 2);
        case 4:
            return new CombatCoordinate(6, 2);
        case 5:
            return new CombatCoordinate(1, 2);
        case 6:
            return new CombatCoordinate(7, 2);
        default:
          throw new IllegalArgumentException("Fleet contains too many ships!");
        }
    }
}
