package org.openRealmOfStars.ai.pathfinding;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
 */

import java.util.ArrayList;
import java.util.List;

import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;

/**
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
   * Start distance for PathPoint. This number just needs to bigger
   * than it ever can be calculated from map.
   */
  private static final int START_DISTANCE = 999999;

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
  public AStarSearch(final Combat combat, final CombatShip start,
      final CombatShip target, final int targetDistance) {
    maxX = Combat.MAX_X;
    maxY = Combat.MAX_Y;
    boolean moreShips = combat.biggerFleet(start.getPlayer());
    blockMap = new int[maxX][maxY];
    this.targetDistance = targetDistance;
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (combat.isBlocked(x, y, true)) {
          blockMap[x][y] = BLOCKED;
        } else {
          blockMap[x][y] = UNBLOCKED;
        }
        if (target.getShip().isStarBaseOrOrbital() && !moreShips) {
          if (x > 0 && x < 8 && y > 1 && y < 6) {
            blockMap[x][y] = BLOCKED;
          }
          if (blockMap[x][y] == BLOCKED) {
            CombatShip blockShip = combat.getShipFromCoordinate(x, y);
            if (blockShip != null
                && blockShip.getPlayer() == start.getPlayer()) {
              blockMap[x][y] = UNBLOCKED;
            }
          }
        }
      }
    }
    points = new ArrayList<>();
    tx = target.getX();
    ty = target.getY();
    this.targetDistance = targetDistance;
    Coordinate startCoordinate = new Coordinate(start.getX(), start.getY());
    Coordinate targetCoordinate = new Coordinate(target.getX(), target.getY());
    PathPoint point1 = new PathPoint(start.getX(), start.getY(),
            startCoordinate.calculateDistance(targetCoordinate));
    points.add(point1);
    blockMap[point1.getX()][point1.getY()] = 0;
    targetPoint = null;
    routeIndex = -1;
  }

  /**
   * Initialize A Star Search for combat map.
   * @param combat Actual combat map
   * @param start Where to start looking the path
   * @param target Where to end
   * @param targetDistance How near is enough
   */
  public AStarSearch(final Combat combat, final CombatShip start,
      final Coordinate target, final int targetDistance) {
    maxX = Combat.MAX_X;
    maxY = Combat.MAX_Y;
    blockMap = new int[maxX][maxY];
    this.targetDistance = targetDistance;
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (combat.isBlocked(x, y, true)) {
          blockMap[x][y] = BLOCKED;
        } else {
          blockMap[x][y] = UNBLOCKED;
        }
        if (combat.getWormHoleCoordinate() != null
            && combat.getWormHoleCoordinate().sameAs(target)
            && x == target.getX() && y == target.getY()) {
          blockMap[x][y] = UNBLOCKED;
        }
      }
    }
    points = new ArrayList<>();
    tx = target.getX();
    ty = target.getY();
    this.targetDistance = targetDistance;
    Coordinate startCoordinate = new Coordinate(start.getX(), start.getY());
    Coordinate targetCoordinate = new Coordinate(target.getX(), target.getY());
    PathPoint point1 = new PathPoint(start.getX(), start.getY(),
            startCoordinate.calculateDistance(targetCoordinate));
    points.add(point1);
    blockMap[point1.getX()][point1.getY()] = 0;
    targetPoint = null;
    routeIndex = -1;
  }

  /**
   * Initialize A Star Search for star map to find a route over
   * some obstacle.
   * @param map StarMap
   * @param sx Starting point X coordinate
   * @param sy Starting point Y coordinate
   * @param tx Target X coordinate
   * @param ty Target Y coordinate
   * @param radius What is the search radius
   * @param dangerousBlocked are dangerous tiles considered as blocked
   */
  public AStarSearch(final StarMap map, final int sx, final int sy,
      final int tx, final int ty, final int radius,
      final boolean dangerousBlocked) {
    maxX = map.getMaxX();
    maxY = map.getMaxY();
    blockMap = new int[maxX][maxY];
    FleetTileInfo[][] fleetTiles = map.getFleetTiles();
    int ownerIndex = -1;
    if (fleetTiles != null && fleetTiles[sx][sy] != null) {
      ownerIndex = fleetTiles[sx][sy].getPlayerIndex();
    }
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (map.isBlocked(x, y) || dangerousBlocked && map.isDangerous(x, y)) {
          blockMap[x][y] = BLOCKED;
        } else {
          blockMap[x][y] = UNBLOCKED;
        }
        if (ownerIndex != -1 && fleetTiles != null
            && fleetTiles[x][y] != null) {
          FleetTileInfo fleetTile = fleetTiles[x][y];
          if (fleetTile != null && fleetTile.getPlayerIndex() != ownerIndex) {
            blockMap[x][y] = BLOCKED;
          }
        }
      }
    }
    points = new ArrayList<>();
    this.tx = tx;
    this.ty = ty;
    Coordinate startCoordinate = new Coordinate(sx, sy);
    Coordinate targetCoordinate = new Coordinate(tx, ty);
    this.targetDistance = (int) Math
        .round(startCoordinate.calculateDistance(targetCoordinate)) - radius;
    if (this.targetDistance < 0) {
      // Target is actually in reroute area
      this.targetDistance = 0;
    }
    PathPoint point1 = new PathPoint(sx, sy,
            startCoordinate.calculateDistance(targetCoordinate));
    points.add(point1);
    blockMap[point1.getX()][point1.getY()] = 0;
    targetPoint = null;
    routeIndex = -1;
  }

  /**
   * Initialize A Star Search for star map to find a route to target point
   * @param map StarMap
   * @param sx Starting point X coordinate
   * @param sy Starting point Y coordinate
   * @param tx Target X coordinate
   * @param ty Target Y coordinate
   * @param dangerousBlocked are dangerous tiles considered as blocked
   */
  public AStarSearch(final StarMap map, final int sx, final int sy,
      final int tx, final int ty, final boolean dangerousBlocked) {
    maxX = map.getMaxX();
    maxY = map.getMaxY();
    blockMap = new int[maxX][maxY];
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (map.isBlocked(x, y) || dangerousBlocked && map.isDangerous(x, y)) {
          blockMap[x][y] = BLOCKED;
        } else {
          blockMap[x][y] = UNBLOCKED;
        }
      }
    }
    points = new ArrayList<>();
    this.tx = tx;
    this.ty = ty;
    this.targetDistance = 0;
    Coordinate startCoordinate = new Coordinate(sx, sy);
    Coordinate targetCoordinate = new Coordinate(tx, ty);
    PathPoint point1 = new PathPoint(sx, sy,
        startCoordinate.calculateDistance(targetCoordinate));
    points.add(point1);
    blockMap[point1.getX()][point1.getY()] = 0;
    targetPoint = null;
    routeIndex = -1;
  }

  /**
   * Initialize A Star Search for star map to find a route to ascension portal
   * @param map StarMap
   * @param sx Starting point X coordinate
   * @param sy Starting point Y coordinate
   * @param tx Target X coordinate
   * @param ty Target Y coordinate
   */
  public AStarSearch(final StarMap map, final int sx, final int sy,
      final int tx, final int ty) {
    maxX = map.getMaxX();
    maxY = map.getMaxY();
    blockMap = new int[maxX][maxY];
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (map.isGoodForAscension(x, y)) {
          blockMap[x][y] = UNBLOCKED;
        } else {
          blockMap[x][y] = BLOCKED;
        }
      }
    }
    points = new ArrayList<>();
    this.tx = tx;
    this.ty = ty;
    this.targetDistance = 0;
    Coordinate startCoordinate = new Coordinate(sx, sy);
    Coordinate targetCoordinate = new Coordinate(tx, ty);
    PathPoint point1 = new PathPoint(sx, sy,
        startCoordinate.calculateDistance(targetCoordinate));
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
  private boolean isValidPos(final int x, final int y) {
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
    if (isValidPos(tx, ty) && blockMap[tx][ty] == BLOCKED
        && targetDistance == 0) {
      targetDistance = 1;
    }
    while (!noMorePoints) {
      count++;
      if (points.size() > 0) {
        PathPoint point = points.get(0);
        points.remove(0);
        for (int y = -1; y < 2; y++) {
          for (int x = -1; x < 2; x++) {
            if (y == 0 && x == 0) {
              continue;
            }
            int mx = x + point.getX();
            int my = y + point.getY();
            if (isValidPos(mx, my) && blockMap[mx][my] > count
                && blockMap[mx][my] != BLOCKED) {
              blockMap[mx][my] = count;
              Coordinate coordinate = new Coordinate(mx, my);
              Coordinate targetCoordinate = new Coordinate(tx, ty);
              double distance = coordinate.calculateDistance(targetCoordinate);
              PathPoint newPoint = new PathPoint(mx, my, distance);
              if (distance < point.getDistance()) {
                if (points.size() > 0) {
                  PathPoint first = points.get(0);
                  if (first != null && distance < first.getDistance()) {
                    // Seems to be closer, so adding it to first one
                    points.add(0, newPoint);
                  } else {
                    // Seems to be closer, but not close as first one in list
                    points.add(1, newPoint);
                  }
                } else {
                  // Seems to be closer, so adding it to first one
                  points.add(0, newPoint);

                }
              } else {
                // Seems to be more far away so adding it to end
                points.add(newPoint);
              }
              if ((int) Math.ceil(distance) == targetDistance) {
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
   * Do actual A Star search but with square moves only initialized values
   * @return True if successful and false if not
   */
  public boolean doSquareSearch() {
    boolean noMorePoints = false;
    int count = 0;
    if (isValidPos(tx, ty) && blockMap[tx][ty] == BLOCKED
        && targetDistance == 0) {
      targetDistance = 1;
    }
    while (!noMorePoints) {
      count++;
      if (points.size() > 0) {
        PathPoint point = points.get(0);
        points.remove(0);
        for (int y = -1; y < 2; y++) {
          for (int x = -1; x < 2; x++) {
            if (y == -1 && x == -1) {
              continue;
            }
            if (y == -1 && x == 1) {
              continue;
            }
            if (y == 1 && x == -1) {
              continue;
            }
            if (y == 1 && x == 1) {
              continue;
            }
            if (y == 0 && x == 0) {
              continue;
            }
            int mx = x + point.getX();
            int my = y + point.getY();
            if (isValidPos(mx, my) && blockMap[mx][my] > count
                && blockMap[mx][my] != BLOCKED) {
              blockMap[mx][my] = count;
              Coordinate coordinate = new Coordinate(mx, my);
              Coordinate targetCoordinate = new Coordinate(tx, ty);
              double distance = coordinate.calculateDistance(targetCoordinate);
              PathPoint newPoint = new PathPoint(mx, my, distance);
              if (distance < point.getDistance()) {
                if (points.size() > 0) {
                  PathPoint first = points.get(0);
                  if (first != null && distance < first.getDistance()) {
                    // Seems to be closer, so adding it to first one
                    points.add(0, newPoint);
                  } else {
                    // Seems to be closer, but not close as first one in list
                    points.add(1, newPoint);
                  }
                } else {
                  // Seems to be closer, so adding it to first one
                  points.add(0, newPoint);

                }
              } else {
                // Seems to be more far away so adding it to end
                points.add(newPoint);
              }
              if ((int) Math.ceil(distance) == targetDistance) {
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
   * Do actual A Star search with initialized values.
   * Prefer straight lines.
   * @return True if successful and false if not
   */
  public boolean doSearchPreferStraightLines() {
    boolean noMorePoints = false;
    int count = 0;
    if (isValidPos(tx, ty) && blockMap[tx][ty] == BLOCKED
        && targetDistance == 0) {
      targetDistance = 1;
    }
    while (!noMorePoints) {
      count++;
      if (points.size() > 0) {
        PathPoint point = points.get(0);
        points.remove(0);
        for (int k = 0; k < 8; k++) {
          int x = 0;
          int y = 0;
          switch (k) {
          default:
          case 0: {
            x = -1;
            y = 0;
            break;
          }
          case 1: {
            x = 1;
            y = 0;
            break;
          }
          case 2: {
            x = 0;
            y = -1;
            break;
          }
          case 3: {
            x = 0;
            y = 1;
            break;
          }
          case 4: {
            x = -1;
            y = -1;
            break;
          }
          case 5: {
            x = 1;
            y = -1;
            break;
          }
          case 6: {
            x = -1;
            y = 1;
            break;
          }
          case 7: {
            x = 1;
            y = 1;
            break;
          }
          }
          int mx = x + point.getX();
          int my = y + point.getY();
          if (isValidPos(mx, my) && blockMap[mx][my] > count
              && blockMap[mx][my] != BLOCKED) {
            blockMap[mx][my] = count;
            Coordinate coordinate = new Coordinate(mx, my);
            Coordinate targetCoordinate = new Coordinate(tx, ty);
            double distance = coordinate.calculateDistance(targetCoordinate);
            PathPoint newPoint = new PathPoint(mx, my, distance);
            if (distance < point.getDistance()) {
              if (points.size() > 0) {
                PathPoint first = points.get(0);
                if (first != null && distance < first.getDistance()) {
                  // Seems to be closer, so adding it to first one
                  points.add(0, newPoint);
                } else {
                  // Seems to be closer, but not close as first one in list
                  points.add(1, newPoint);
                }
              } else {
                // Seems to be closer, so adding it to first one
                points.add(0, newPoint);
              }
            } else {
              // Seems to be more far away so adding it to end
              points.add(newPoint);
            }
            if ((int) Math.ceil(distance) == targetDistance) {
              // Target found and acquired
              targetPoint = newPoint;
              return true;
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
        PathPoint point = points.get(points.size() - 1);
        int bx = 0;
        int by = 0;
        double bestDistance = START_DISTANCE;
        for (int y = -1; y < 2; y++) {
          for (int x = -1; x < 2; x++) {
            if (y == 0 && x == 0) {
              continue;
            }
            int mx = x + point.getX();
            int my = y + point.getY();
            Coordinate coordinate = new Coordinate(mx, my);
            Coordinate targetCoordinate = new Coordinate(tx, ty);
            double distance = coordinate.calculateDistance(targetCoordinate);
            if (isValidPos(mx, my) && blockMap[mx][my] < count
                && distance <= bestDistance) {
              bx = mx;
              by = my;
              bestDistance = distance;
              count = blockMap[bx][by];
            }
          }
        }
        PathPoint newPoint = new PathPoint(bx, by, bestDistance);
        if (blockMap[bx][by] != 0) {
          points.add(newPoint);
        } else {
          targetReached = true;
        }

      }
      routeIndex = points.size() - 1;
    }
  }

  /**
   * Calculate Route
   */
  public void doSquareRoute() {
    boolean targetReached = false;
    if (targetPoint != null) {
      points = new ArrayList<>();
      points.add(targetPoint);
      int count = UNBLOCKED;
      while (!targetReached) {
        PathPoint point = points.get(points.size() - 1);
        int bx = 0;
        int by = 0;
        double bestDistance = START_DISTANCE;
        for (int y = -1; y < 2; y++) {
          for (int x = -1; x < 2; x++) {
            if (y == -1 && x == -1) {
              continue;
            }
            if (y == -1 && x == 1) {
              continue;
            }
            if (y == 1 && x == -1) {
              continue;
            }
            if (y == 1 && x == 1) {
              continue;
            }
            if (y == 0 && x == 0) {
              continue;
            }
            int mx = x + point.getX();
            int my = y + point.getY();
            Coordinate coordinate = new Coordinate(mx, my);
            Coordinate targetCoordinate = new Coordinate(tx, ty);
            double distance = coordinate.calculateDistance(targetCoordinate);
            if (isValidPos(mx, my) && blockMap[mx][my] < count
                && distance <= bestDistance) {
              bx = mx;
              by = my;
              bestDistance = distance;
              count = blockMap[bx][by];
            }
          }
        }
        PathPoint newPoint = new PathPoint(bx, by, bestDistance);
        if (blockMap[bx][by] != 0) {
          points.add(newPoint);
        } else {
          targetReached = true;
        }

      }
      routeIndex = points.size() - 1;
    }
  }

  /**
   * Get next move by return PathPoint
   * @return PathPoint or null if cannot move
   */
  public PathPoint getMove() {
    if (targetPoint != null && points.size() > 0 && routeIndex != -1) {
      return points.get(routeIndex);
    }
    return null;
  }

  /**
   * Move route index to next move point on found path.
   */
  public void nextMove() {
    if (targetPoint != null && points.size() > 1 && routeIndex != -1
        && routeIndex > 0) {
      routeIndex--;
    }
  }

  /**
   * Is last move reached
   * @return True if last move reached or false if not
   */
  public boolean isLastMove() {
    if (routeIndex == 0 || targetPoint == null) {
      return true;
    }
    return false;
  }

  /**
   * Get target distance. Zero means that target is going to be reached.
   * @return target distance
   */
  public int getTargetDistance() {
    return targetDistance;
  }
}
