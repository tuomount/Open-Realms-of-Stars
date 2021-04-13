package org.openRealmOfStars.starMap.planet;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* Ship containing extra information while bombing and conquering the planet.
*
*/
public class BombingShip {

  /**
   * Max actions from start of conquer.
   */
  private int maxActions;

  /**
   * Current actions left in conquer.
   */
  private int actions;

  /**
   * Constructor for bombing ship.
   * Both maxActions and actions are equal on creation.
   * @param maxActions Max actions for ship.
   */
  public BombingShip(final int maxActions) {
    this.setMaxActions(maxActions);
    this.setActions(maxActions);
  }

  /**
   * Get max actions for bombing ship.
   * @return Max Actions.
   */
  public int getMaxActions() {
    return maxActions;
  }

  /**
   * Set Max actions for bombing ship.
   * @param maxActions Max actions for bombing ship.
   */
  public void setMaxActions(final int maxActions) {
    this.maxActions = maxActions;
  }

  /**
   * Get current amount of actions ship has left.
   * @return Actions left.
   */
  public int getActions() {
    return actions;
  }

  /**
   * Set current amount of actions left.
   * @param actions New current amount of actions left.
   */
  public void setActions(final int actions) {
    this.actions = actions;
  }
}
