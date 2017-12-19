package org.openRealmOfStars.AI.Mission;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017 Tuomo Untinen
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
 * Mission types for AI
 *
 */

public enum MissionType {

  /**
   * Explore new unknown space and boldly go where no one
   * has gone before.
   */
  EXPLORE,
  /**
   * Colonize uncolonized planet
   */
  COLONIZE,
  /**
   * Defend planet
   */
  DEFEND,
  /**
   * Attack the planet
   */
  ATTACK,
  /**
   * Move to target, simple mission just moving the fleet
   */
  MOVE,
  /**
   * Gather specific type of ship for attack
   */
  GATHER,
  /**
   * Build and deploy starbase
   */
  DEPLOY_STARBASE,
  /**
   * Destroy enemy starbase
   */
  DESTROY_STARBASE,
  /**
   * Fleet contains trade ship which travel between trade alliance planets
   */
  TRADE_FLEET,
  /**
   * Fleet mostly moving quite randomly exploring the galaxy, tries to
   * rob trade ships
   */
  PRIVATEER;

  /**
   * Get Mission type with index
   * @return index
   */
  public int getIndex() {
    switch (this) {
    case EXPLORE:
      return 0;
    case COLONIZE:
      return 1;
    case DEFEND:
      return 2;
    case ATTACK:
      return 3;
    case MOVE:
      return 4;
    case GATHER:
      return 5;
    case DEPLOY_STARBASE:
      return 6;
    case DESTROY_STARBASE:
      return 7;
    case TRADE_FLEET:
      return 8;
    case PRIVATEER:
      return 9;
    default:
      return 0;
    }
  }

  @Override
  public String toString() {
    switch (this) {
    case EXPLORE:
      return "Explore";
    case COLONIZE:
      return "Colonize";
    case DEFEND:
      return "Defend";
    case ATTACK:
      return "Attack";
    case MOVE:
      return "Move";
    case GATHER:
      return "Gather";
    case DEPLOY_STARBASE:
      return "Deploy starbase";
    case DESTROY_STARBASE:
      return "Destroy starbase";
    case TRADE_FLEET:
      return "Trade fleet";
    case PRIVATEER:
      return "Privateer";
    default:
      return "Unknown";
    }
  }

}
