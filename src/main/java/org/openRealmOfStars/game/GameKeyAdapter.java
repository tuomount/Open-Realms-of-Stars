package org.openRealmOfStars.game;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.gui.mapPanel.PopupPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.repository.GameRepository;

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
      fleet.setRoute(null);
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
    if (game.getGameState() == GameState.STARMAP
        && game.getStarMapView() != null) {
      // Star Map Keys
      if (arg0.getKeyCode() == KeyEvent.VK_R
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        game.getStarMapView().getStarMapMouseListener().setRoutePlanning(true);
      }
      if (arg0.getKeyCode() == KeyEvent.VK_Q
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        if (game.getStarMapView().getPopup() == null) {
          PopupPanel popup = new PopupPanel("This is just text to try out"
              + " popup panel text drawing capabilities. There are no line"
              + " changes here at all. Popup panel should automatically"
              + " handle line changes without no hickup. Mostly this text"
              + " is just text flow without any purpose. This could basicly"
              + " contain whatever content, but it needs to be polite and"
              + " correct. This is the reason I did not want to copy paste"
              + " random lorem ipsum text from the internet. Plus I would"
              + " need to check the license for that. This was just easier.",
              "Test title");
          popup.setImage(GuiStatics.IMAGE_ANDROID);
          game.getStarMapView().setPopup(popup);
        } else {
          game.getStarMapView().setPopup(null);
        }
      }
      if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE
          && arg0.getID() == KeyEvent.KEY_PRESSED) {
        new GameRepository().saveGame(GameRepository.DEFAULT_SAVE_FOLDER,
                                      "current.save", game.getStarMap());
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

    // Common keys that work every where
    if (arg0.getKeyCode() == KeyEvent.VK_F10
        && arg0.getID() == KeyEvent.KEY_PRESSED) {
      BufferedImage result = new BufferedImage(game.getWidth(),
          game.getHeight(), BufferedImage.TYPE_INT_RGB);
      game.paint(result.getGraphics());
      IOUtilities.saveScreenShot(result);
      return true;
    }
    return false;
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
