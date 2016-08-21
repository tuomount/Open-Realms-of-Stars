package org.openRealmOfStars.AI.PathFinding;

import java.util.ArrayList;
import java.util.List;

import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.starMap.StarMap;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * A Star path searching algorithm. This works with BattleMap and StarMap.
 * 
 */

public class AStarSearch {

  
  /**
   * Map X size
   */
  private int maxX;
  /**
   * Map Y Size
   */
  private int maxY;
  
  /**
   * Map value for blocked
   */
  private static final int BLOCKED = 999;
  
  /**
   * Map value for unblocked
   */
  private static final int UNBLOCKED = 0;
  
  /**
   * Map containing the block information
   */
  private int[][] blockMap;
  
  /**
   * Points to still to check
   */
  private List<PathPoint> points;
  
  /**
   * Target X coordinate
   */
  private int tx;
  /**
   * Target Y Coordinate
   */
  private int ty;
  
  /**
   * Target Distance where to stop
   */
  private int targetDistance;
  
  /**
   * Initialize A Star Search for combat map.
   * @param combat Actual combat map
   * @param start Where to start looking the path
   * @param target Where to end
   * @param targetDistance How near is enough
   */
  public AStarSearch(Combat combat,CombatShip start, CombatShip target, 
      int targetDistance) {
    maxX = Combat.MAX_X;
    maxY = Combat.MAX_Y;
    blockMap = new int[maxX][maxY];
    this.targetDistance = targetDistance;
    for (int y=0;y<maxY;y++) {
      for (int x=0;x<maxX;x++) {
        if (combat.isBlocked(x, y)) {
          blockMap[x][y] = BLOCKED;
        } else {
          blockMap[x][y] = UNBLOCKED;
        }
      }
    }
    points = new ArrayList<>();
    tx = target.getX();
    ty = target.getY();
    this.targetDistance = targetDistance;
    PathPoint point1 = new PathPoint(start.getX(), start.getY(), 
        StarMap.getDistance(start.getX(), start.getY(), tx, ty));
    points.add(point1);
    
  }
}
