package org.openRealmOfStars.gui.mapPanel;

import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
* Thread for handling MapPanel drawing.
*
*/
public class MapPanelThread extends Thread {

  /**
   * MapPanel
   */
  private MapPanel panel;

  /**
   * Star map;
   */
  private StarMap map;
  /**
   * Has thread started?
   */
  private boolean started;

  /**
   * Is thread running
   */
  private boolean running;

  /**
   * Time now in milliseconds.
   */
  private long now;
  /**
   * Last time in milliseconds.
   */
  private long lastTime;
  /**
   * Delta time between frames.
   */
  private long deltaBetweenFrames;
  /**
   * Constructor for MapPanel Thread.
   * @param panel Panel.
   * @param map Starmap
   */
  public MapPanelThread(final MapPanel panel, final StarMap map) {
    this.panel = panel;
    this.map = map;
    started = false;
    running = false;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Has thread started yet
   * @return True if started
   */
  public synchronized boolean isStarted() {
    return started;
  }

  /**
   * Is thread still running
   * @return True if running
   */
  public synchronized boolean isRunning() {
    return running;
  }

  @Override
  public synchronized void start() {
    started = true;
    super.start();
  }

  @Override
  public void run() {
    synchronized (this) {
      running = true;
    }
    do {
      now = System.currentTimeMillis();
      deltaBetweenFrames = deltaBetweenFrames + (now - lastTime);
      lastTime = now;
      if (deltaBetweenFrames > 16) {
        if (panel.getPanelType() == MapPanelType.STARMAP) {
          panel.drawMap(this.map);
          panel.repaint();
        }
        deltaBetweenFrames = deltaBetweenFrames - 16;
      }
    } while (!panel.isStopDrawing());
    synchronized (this) {
      running = false;
    }
  }
}
