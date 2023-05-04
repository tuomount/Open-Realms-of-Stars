package org.openRealmOfStars.game;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021,2023 Tuomo Untinen
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
 * Key Event Dispatcher for the game
 *
 */
public class GameKeyAdapter implements KeyEventDispatcher {

  /**
   * Handle movement. This works for fleet moving and scrolling the map.
   * @param x where to move in x coordinates
   * @param y where to move in y coordinates
   */
  private void handleMovement(final int x, final int y) {
    if (x < -1 || x > 1 || y < -1 || y > 1) {
      // Illegal coordinates so not doing anything
      return;
    }
    if (game.getStarMapView().getStarMapMouseListener()
        .getLastClickedFleet() != null) {
      // Fleet move
      Fleet fleet = game.getStarMapView().getStarMapMouseListener()
          .getLastClickedFleet();
      if (fleet.getRoute() != null && fleet.getRoute().isBombing()) {
        return;
      }
      if (game.getStarMapView().checkForConflict()) {
        return;
      }
      fleet.setRoute(null);
      game.getStarMapView().getStarMapMouseListener().setWarningShown(false);
      int nx = fleet.getX() + x;
      int ny = fleet.getY() + y;
      game.fleetMakeMove(game.getPlayers().getCurrentPlayerInfo(),
          fleet, nx, ny);
    } else {
      // Map scrolling
      game.getStarMap().setDrawPos(game.getStarMap().getDrawX() + x,
          game.getStarMap().getDrawY() + y);
      game.getStarMapView().setReadyToMove(false);
    }
  }
  @Override
  public boolean dispatchKeyEvent(final KeyEvent arg0) {
    // Common keys that work every where
    if (arg0.getKeyCode() == KeyEvent.VK_F10
        && arg0.getID() == KeyEvent.KEY_PRESSED) {
      BufferedImage result = new BufferedImage(game.getWidth(),
          game.getHeight(), BufferedImage.TYPE_INT_RGB);
      game.paint(result.getGraphics());
      IOUtilities.saveScreenShot(result);
      return true;
    }
    if (arg0.getKeyCode() == KeyEvent.VK_F1
        && arg0.getID() == KeyEvent.KEY_PRESSED) {
      game.setBridgeCommand(BridgeCommandType.WARM_WHITE);
      return true;
    }
    if (arg0.getKeyCode() == KeyEvent.VK_F2
        && arg0.getID() == KeyEvent.KEY_PRESSED) {
      game.setBridgeCommand(BridgeCommandType.DARKEST);
      return true;
    }
    if (arg0.getKeyCode() == KeyEvent.VK_F3
        && arg0.getID() == KeyEvent.KEY_PRESSED) {
      if (MusicPlayer.isMusicEnabled()) {
        MusicPlayer.setMusicEnabled(false);
      } else {
        MusicPlayer.setMusicEnabled(true);
      }
      return true;
    }

    if (game.getGameState() == GameState.STARMAP
        && game.getStarMapView() != null) {
      if (game.getStarMapView().getPopup() != null) {
        if (arg0.getID() == KeyEvent.KEY_PRESSED
            && arg0.getKeyCode() == KeyEvent.VK_ENTER
            || arg0.getKeyCode() == KeyEvent.VK_SPACE) {
          game.getStarMapView().getPopup().dismiss();
        }
        return true;
      }
      // Star Map Keys
      if (arg0.getKeyCode() == KeyEvent.VK_R
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        game.getStarMapView().handleActions(new ActionEvent(this,
            ActionEvent.ACTION_PERFORMED, GameCommands.COMMAND_ROUTE_FLEET));
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_TAB
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        if (arg0.isShiftDown()) {
          game.handleCommandStarmapPrevTarget();
        } else {
          game.handleCommandStarmapNextTarget();
        }
      }
      if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        String saveFilename = "current.save";
        if (game.getSaveGameFile() != null
            && !game.getSaveGameFile().equals("autosave.save")) {
          saveFilename = game.getSaveGameFile();
        }
        new GameRepository().saveGame(GameRepository.DEFAULT_SAVE_FOLDER,
                                      saveFilename, game.getStarMap());
        game.changeGameState(GameState.MAIN_MENU);
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_SPACE
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        Message msg = game.getStarMap().getCurrentPlayerInfo().getMsgList()
            .getMsg();
        if (msg.getX() != -1 && msg.getY() != -1) {
          if (game.getStarMap().getCursorX() == msg.getX()
              && game.getStarMap().getCursorY() == msg.getY()) {
            game.getStarMap().getCurrentPlayerInfo().getMsgList()
                .getNextMessage();
          }
        } else {
          game.getStarMap().getCurrentPlayerInfo().getMsgList()
              .getNextMessage();
        }
        game.getStarMapView().updateMessagePanel();
        game.focusOnMessage(true);
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_F
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        game.getStarMapView().setCursorFocus(50);
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_P
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        game.getStarMap().nextPlayer();
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_C
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        System.out.println("Cursor X: " + game.getStarMap().getCursorX()
            + " Y: " + game.getStarMap().getCursorY());
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_M
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        PlayerInfo info = game.getStarMap().getCurrentPlayerInfo();
        MissionList list = info.getMissions();
        if (list != null) {
          System.out.println(info.getEmpireName() + " missions\n");
          System.out.println(list.toString());
        }
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD7
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(-1, -1);
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD9
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(+1, -1);
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD3
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(+1, +1);
        return true;
      }
      if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD1
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(-1, +1);
        return true;
      }
      if ((arg0.getKeyCode() == KeyEvent.VK_LEFT
          || arg0.getKeyCode() == KeyEvent.VK_NUMPAD4)
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(-1, 0);
        return true;
      }
      if ((arg0.getKeyCode() == KeyEvent.VK_RIGHT
          || arg0.getKeyCode() == KeyEvent.VK_NUMPAD6)
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(+1, 0);
        return true;
      }
      if ((arg0.getKeyCode() == KeyEvent.VK_DOWN
          || arg0.getKeyCode() == KeyEvent.VK_NUMPAD2)
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(0, +1);
        return true;
      }
      if ((arg0.getKeyCode() == KeyEvent.VK_UP
          || arg0.getKeyCode() == KeyEvent.VK_NUMPAD8)
          && arg0.getID() == KeyEvent.KEY_PRESSED
          && game.getStarMapView().getReadyToMove()) {
        handleMovement(0, -1);
        return true;
      }
    }
    if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE
        && arg0.getID() == KeyEvent.KEY_PRESSED) {
      handleEscapeKey();
    }
    return false;
  }

  /**
   * Method for handling escape key.
   */
  private void handleEscapeKey() {
    if (game.getGameState() == GameState.FLEETVIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.FLEET_TRADE_VIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.GALAXY_CREATION) {
      game.changeGameState(GameState.MAIN_MENU);
    }
    if (game.getGameState() == GameState.PLAYER_SETUP) {
      game.changeGameState(GameState.GALAXY_CREATION);
    }
    if (game.getGameState() == GameState.LOAD_GAME) {
      game.changeGameState(GameState.MAIN_MENU);
    }
    if (game.getGameState() == GameState.OPTIONS_VIEW) {
      game.changeGameState(GameState.MAIN_MENU);
    }
    if (game.getGameState() == GameState.SETUP_AMBIENT_LIGHTS) {
      game.changeGameState(GameState.OPTIONS_VIEW);
    }
    if (game.getGameState() == GameState.CREDITS) {
      game.changeGameState(GameState.MAIN_MENU);
    }
    if (game.getGameState() == GameState.CHANGE_LOG) {
      game.changeGameState(GameState.MAIN_MENU);
    }
    if (game.getGameState() == GameState.PLANETBOMBINGVIEW) {
      if (game.getPreviousState() == GameState.AITURN) {
        game.changeGameState(game.getPreviousState());
        return;
      }
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.DIPLOMACY_VIEW
        && game.getDiplomacyView() != null
        && game.getDiplomacyView().isExitAllowed()) {
      if (game.getPreviousState() == GameState.AITURN) {
        game.changeGameState(game.getPreviousState());
        return;
      }
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.ESPIONAGE_VIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.HELP_VIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.VIEWSTATS) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.VOTE_VIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.RESEARCHVIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.PLANET_LIST_VIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.VIEWSHIPS) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.SHIPDESIGN) {
      game.changeGameState(GameState.VIEWSHIPS);
    }
    if (game.getGameState() == GameState.SHIP_UPGRADE_VIEW) {
      game.changeGameState(GameState.FLEETVIEW);
    }
    if (game.getGameState() == GameState.LEADER_VIEW) {
      game.changeGameState(GameState.STARMAP);
    }
    if (game.getGameState() == GameState.PLANETVIEW) {
      game.changeGameState(game.getPreviousState());
    }
    if (game.getGameState() == GameState.REALM_VIEW) {
      game.changeGameState(game.getPreviousState());
    }
    if (game.getGameState() == GameState.PLANETVIEW) {
      if (game.getPreviousState() == GameState.PLANET_LIST_VIEW) {
        game.changeGameState(game.getPreviousState());
      } else {
        game.changeGameState(GameState.STARMAP);
      }
    }
  }
  /**
   * Reference to the game
   */
  private Game game;

  /**
   * Initializes Keyboard adapter for Open Realm of Stars.
   * @param game Game itself
   */
  public GameKeyAdapter(final Game game) {
    super();
    this.game = game;
  }
}
