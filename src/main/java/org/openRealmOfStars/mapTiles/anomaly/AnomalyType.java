package org.openRealmOfStars.mapTiles.anomaly;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2020,2022 Tuomo Untinen
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
* Space anomaly type
*
*/
public enum AnomalyType {

  /**
   * Space anomaly containing credit, one time anomaly
   */
  CREDIT,
  /**
   * Space anomaly containing tech, one time anomaly
   */
  TECH,
  /**
   * Space anomaly containing map, one time anomaly
   */
  MAP,
  /**
   * Space anomaly containing free ship, one time anomaly
   */
  SHIP,
  /**
   * Space anomaly containing worm hole which teleports randomly
   */
  WORMHOLE,
  /**
   * Space anomaly containing pirate ship
   */
  PIRATE,
  /**
   * Space anomaly containing space monster
   */
  MONSTER,
  /**
   * Space anomaly containing pirate space station
   */
  LAIR,
  /**
   * Space anomaly containing Deep space anchor
   */
  DEEP_SPACE_ANCHOR,
  /**
   * Space anomaly containing ancient mechion or synthdroid leader
   */
  ANCIENT_ROBOT,
  /**
   * Space anomaly containing time warp.
   */
  TIME_WARP,
  /**
   * Rare technology in space anomaly. one time anomaly.
   */
  RARE_TECH,
  /**
   * Ancient artifact found in space. One time anomaly.
   */
  ANCIENT_ARTIFACT,
  /**
   * Ancient News station broadcasting the galactic news.
   */
  NEWS_STATION;
}
