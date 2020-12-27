package org.openRealmOfStars.ambient;

import java.io.IOException;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Bridge thread for running HTTPS request on another thread towards bridge.
* This makes all requests non-blocking.
*
*/
public class BridgeThread extends Thread {

  /**
   * Bridge where to send requests.
   */
  private Bridge bridge;

  /**
   * Has thread started?
   */
  private boolean started;

  /**
   * Is thread running
   */
  private boolean running;

  /**
   * Constructor for bridge thread
   * @param bridge Bridge where to run request.
   */
  public BridgeThread(final Bridge bridge) {
    this.bridge = bridge;
    started = false;
    running = false;
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
    boolean endThread = false;
    do {
      BridgeCommandType command = bridge.getNextCommand();
      if (command == null) {
        try {
          BridgeThread.sleep(100);
        } catch (InterruptedException e) {
          // Nothing to do.
        }
      } else if (command == BridgeCommandType.REGISTER) {
        try {
          bridge.setNextCommand(null);
          bridge.register();
        } catch (IOException e) {
          bridge.setStatus(BridgeStatusType.ERROR);
          bridge.setLastErrorMsg(e.getMessage());
        }
      } else if (command == BridgeCommandType.TEST) {
        try {
          bridge.setNextCommand(null);
          bridge.testConnection();
        } catch (IOException e) {
          bridge.setStatus(BridgeStatusType.NOT_CONNECTED);
          bridge.setLastErrorMsg(e.getMessage());
        }
      } else if (command == BridgeCommandType.EXIT) {
        bridge.setNextCommand(null);
        endThread = true;
      }
    } while (!endThread);
    synchronized (this) {
      running = false;
    }
  }

}
