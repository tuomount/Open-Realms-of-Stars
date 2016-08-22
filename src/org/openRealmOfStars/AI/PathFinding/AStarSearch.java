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
  private static final int BLOCKED = Integer.MAX_VALUE;
  
  /**
   * Map value for unblocked
   */
  private static final int UNBLOCKED = 200000;
  
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
   * Found target point after search
   */
  private PathPoint targetPoint;
  
  /**
   * Route Index
   */
  private int routeIndex;
  
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
    blockMap[point1.getX()][point1.getY()] = 0;
    targetPoint = null;
    routeIndex = -1;
  }
  
  /**
   * Is coordinate valid position on map
   * @param x X Coordinate
   * @param y Y Coordinate
   * @return true if valid otherwise false
   */
  private boolean isValidPos(int x, int y) {
    if (x >= 0 && y >= 0 && x < maxX && y < maxY) {
      return true;
    }
    return false;
  }

  
  /**
   * Do actual A Star search with initialized values
   * @return True if successful and false if not
   */
  public boolean doSearch() {
    boolean noMorePoints = false;
    int count = 0;
    while (!noMorePoints) {
      count++;
      if (points.size() > 0) {
        PathPoint point = points.get(0);
        points.remove(0);
        for (int y = -1;y<2;y++) {
          for (int x = -1;x<2;x++) {
            if (y==0 && x== 0) {
              continue;
            }
            int mx = x+point.getX();
            int my = y+point.getY();
            if (isValidPos(mx,my) && blockMap[mx][my] > count) {
              blockMap[mx][my] = count;
              double dist = StarMap.getDistance(mx, my, tx, ty);
              PathPoint newPoint = new PathPoint(mx, my, dist);
              if (dist < point.getDistance()) {
                // Seems to be closer, so adding it to first one
                points.add(0, newPoint);
              } else {
                // Seems to be more far away so adding it to end
                points.add(newPoint);
              }
              if ((int) Math.ceil(dist) == targetDistance) {
                // Target found and acquired
                targetPoint = newPoint;
                return true;
              }
            }
          }
        }
      } else {
        noMorePoints = true;
      }
    }
    // Target is not found, no path available
    return false;
  }
  
  /**
   * Calculate Route
   */
  public void doRoute() {
    boolean targetReached = false;
    if (targetPoint != null) {
      points = new ArrayList<>();
      points.add(targetPoint);
      int count = UNBLOCKED;
      while (!targetReached) {
        PathPoint point = points.get(points.size()-1);
        for (int y = -1;y<2;y++) {
          for (int x = -1;x<2;x++) {
            if (y==0 && x== 0) {
              continue;
            }
            int mx = x+point.getX();
            int my = y+point.getY();
            if (isValidPos(mx,my) && blockMap[mx][my] < count) {
              count = blockMap[mx][my];
              double dist = StarMap.getDistance(mx, my, tx, ty);
              PathPoint newPoint = new PathPoint(mx, my, dist);
              points.add(newPoint);
              if (blockMap[mx][my] == 0) {
                targetReached = true;
              }
            }
          }
        }
      }
      routeIndex = points.size()-1;
    }
  }
  
  /**
   * Get next move by return PathPoint
   * @return PathPoint or null if cannot move
   */
  public PathPoint getNextMove() {
    if (targetPoint != null && points.size() > 1 && routeIndex != -1)  {
      if (routeIndex > 0) {
        routeIndex--;
      }
      return points.get(routeIndex);
    }
    return null;
  }
  
  /**
   * Is last move reached
   * @return True if last move reached or false if not
   */
  public boolean isLastMove() {
    if (routeIndex == 0) {
      return true;
    }
    return false;
  }
}

