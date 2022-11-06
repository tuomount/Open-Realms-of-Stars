package org.openRealmOfStars.ambient;

import java.io.IOException;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020, 2021 Tuomo Untinen
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

  /**
   * Wait for certain amount of milli seconds.
   * @param time in milli seconds.
   */
  private void sleepFor(final long time) {
    try {
      BridgeThread.sleep(time);
    } catch (InterruptedException e) {
      // Nothing to do.
    }
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
        sleepFor(100);
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
          if (bridge.locateBridge()) {
            bridge.setNextCommand(BridgeCommandType.TEST);
          }
        }
      } else if (command == BridgeCommandType.FETCH_LIGHTS) {
        try {
          bridge.setNextCommand(null);
          bridge.updateAllLights();
        } catch (IOException e) {
          bridge.setStatus(BridgeStatusType.NOT_CONNECTED);
          bridge.setLastErrorMsg(e.getMessage());
          if (bridge.locateBridge()) {
            bridge.setNextCommand(BridgeCommandType.FETCH_LIGHTS);
          }
        }
      } else if (command == BridgeCommandType.WARM_WHITE) {
         bridge.setNextCommand(null);
         bridge.effectWarmWhiteLight();
      } else if (command == BridgeCommandType.BRIGHT_CYAN) {
        bridge.setNextCommand(null);
        bridge.effectBrightCyanLight();
      } else if (command == BridgeCommandType.BLUEISH_WHITE) {
        bridge.setNextCommand(null);
        bridge.effectBlueishWhiteLight();
      } else if (command == BridgeCommandType.GREYBLUE) {
        bridge.setNextCommand(null);
        bridge.effectGreyBlueLight();
      } else if (command == BridgeCommandType.DARK_ORANGE) {
        bridge.setNextCommand(null);
        bridge.effectDarkOrangeLight();
      } else if (command == BridgeCommandType.DARK_RED) {
        bridge.setNextCommand(null);
        bridge.effectDarkRedLight();
      } else if (command == BridgeCommandType.PURPLE_DREAM) {
        bridge.setNextCommand(null);
        bridge.effectPurpleDreamLight();
      } else if (command == BridgeCommandType.GREEN_CONSOLE) {
        bridge.setNextCommand(null);
        bridge.effectGreenConsole();
      } else if (command == BridgeCommandType.DARKEST) {
        bridge.setNextCommand(null);
        bridge.effectDarkest();
      } else if (command == BridgeCommandType.RED_ALERT) {
        bridge.effectAlert(200);
      } else if (command == BridgeCommandType.FLOAT_IN_SPACE) {
        bridge.effectBlueSpace();
      } else if (command == BridgeCommandType.SPACE_CONSOLE) {
        bridge.effectSpaceConsole(1);
      } else if (command == BridgeCommandType.SPACE_CONSOLE2) {
        bridge.effectSpaceConsole(2);
      } else if (command == BridgeCommandType.SPACE_CONSOLE3) {
        bridge.effectSpaceConsole(3);
      } else if (command == BridgeCommandType.YELLOW_ALERT) {
        bridge.effectAlert(8000);
      } else if (command == BridgeCommandType.NUKE_START) {
        bridge.effectInitNuke();
        bridge.setNextCommand(BridgeCommandType.NUKE_FADE);
      } else if (command == BridgeCommandType.NUKE_FADE) {
        bridge.effectFadeNuke();
        if (bridge.isEffectEnded()) {
          bridge.setNextCommand(null);
        }
      } else if (command == BridgeCommandType.FADE_IN_START) {
        bridge.effectInitFadeIn();
        bridge.setNextCommand(BridgeCommandType.FADE_IN);
      } else if (command == BridgeCommandType.FADE_IN) {
        bridge.effectFadeIn();
        if (bridge.isEffectEnded()) {
          bridge.setNextCommand(null);
        }
      } else if (command == BridgeCommandType.ORANGE_BLINK) {
        bridge.effectBlinkOrange();
      } else if (command == BridgeCommandType.ORANGE_BLUE) {
        bridge.effectOrangeBlue();
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
