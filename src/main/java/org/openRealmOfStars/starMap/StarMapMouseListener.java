package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.MapInfoPanel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.PlanetaryEvent;
import org.openRealmOfStars.utilities.PixelsToMapCoordinate;

/**
 *
 * Mouse Listener for Star Map
 *
 */

public class StarMapMouseListener extends MouseAdapter {

  /**
   * How many pixel is requred to move on sector
   */
  private static final int PIXEL_SCROLL_AMOUNT = 16;
  /**
   * Star map which to use
   */
  private StarMap starMap;

  /**
   * Map panel which to use
   */
  private MapPanel mapPanel;

  /**
   * Info panel next to map panel
   */
  private MapInfoPanel mapInfoPanel;

  /**
   * Is route being planned
   */
  private boolean routePlanning;
  /**
   * Is route regular or FTL planning.
   */
  private boolean regularRoute;

  /**
   * Constructor for StarMap Mouse Listener
   * @param starMap StarMap which to use.
   * @param panel Map Panel where Star Map is being drawn.
   * @param mapInfoPanel Info panel next to map panel.
   */
  public StarMapMouseListener(final StarMap starMap, final MapPanel panel,
      final MapInfoPanel mapInfoPanel) {
    this.starMap = starMap;
    this.mapPanel = panel;
    this.mapInfoPanel = mapInfoPanel;
  }

  /**
   * Is cursor on border
   */
  private boolean onBorder;

  /**
   * Last know coordinates
   */
  private PixelsToMapCoordinate coord;

  /**
   * Last clicked planet
   * This is Planet which was last clicked, null if empty space or
   * other was clicked
   */
  private Planet lastClickedPlanet;

  /**
   * Last clicked fleet
   * This is fleet which was last clicked, null if empty space or
   * other was clicked
   */
  private Fleet lastClickedFleet;

  /**
   * Something has been double clicked
   */
  private boolean doubleClicked;

  /**
   * Move clicked
   */
  private boolean moveClicked;

  /**
   * Warning shown if trying to move on top another fleet.
   */
  private boolean warningShown;
  /**
   * Move point where clicked on map X coord
   */
  private int moveX;
  /**
   * Move point where clicked on map Y coord
   */
  private int moveY;

  /**
   * Original dragging point on screen, X-Coordinate
   */
  private int dragOrigX;
  /**
   * Original dragging point on screen, y-Coordinate
   */
  private int dragOrigY;
  /**
   * Original map coordinate when dragging, X-Coordinate.
   */
  private int dragOrigMapX;
  /**
   * Original map coordinate when dragging, Y-Coordinate.
   */
  private int dragOrigMapY;
  /**
   * Is dragging done?
   */
  private boolean dragging = false;
  /**
   * Get the move point where clicked on map X coord
   * @return move point where clicked on map X coord
   */
  public int getMoveX() {
    return moveX;
  }

  /**
   * Get the move point where clicked on map Y coord
   * @return move point where clicked on map Y coord
   */
  public int getMoveY() {
    return moveY;
  }

  /**
   * Update drawing coordinates if mouse cursor is on map border
   */
  public void updateScrollingIfOnBorder() {
    if (coord != null && !coord.isOutOfPanel() && onBorder) {
      if (coord.getRelativeX() == mapPanel.getViewPointX()) {
        starMap.setDrawPos(starMap.getDrawX() + 1, starMap.getDrawY());
      }
      if (coord.getRelativeX() == -mapPanel.getViewPointX()) {
        starMap.setDrawPos(starMap.getDrawX() - 1, starMap.getDrawY());
      }
      if (coord.getRelativeY() == mapPanel.getViewPointY()) {
        starMap.setDrawPos(starMap.getDrawX(), starMap.getDrawY() + 1);
      }
      if (coord.getRelativeY() == -mapPanel.getViewPointY()) {
        starMap.setDrawPos(starMap.getDrawX(), starMap.getDrawY() - 1);
      }
    }

  }

  @Override
  public void mouseExited(final MouseEvent e) {
    coord = null;
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    Coordinate center = new Coordinate(mapPanel.getLastDrawnX(),
        mapPanel.getLastDrawnY());
    coord = new PixelsToMapCoordinate(center, e.getX(), e.getY(),
        mapPanel.getOffsetX(), mapPanel.getOffsetY(), mapPanel.getViewPointX(),
        mapPanel.getViewPointY(), false, mapPanel.getLastZoomLevel());
    if (!coord.isOutOfPanel()) {
      starMap.setCursorPos(coord.getMapX(), coord.getMapY());
      if (routePlanning && lastClickedFleet != null) {
        Route route = new Route(lastClickedFleet.getX(),
            lastClickedFleet.getY(), coord.getMapX(), coord.getMapY(),
            lastClickedFleet.getFleetFtlSpeed());
        if (isRegularRoute()) {
          route.setRegularSpeed(lastClickedFleet.getFleetSpeed());
        }
        mapPanel.setRoute(route);
      } else {
        routePlanning = false;
        mapPanel.setRoute(null);
      }
      boolean tempBorder = false;
      if (coord.getRelativeX() == mapPanel.getViewPointX()) {
        tempBorder = true;
      }
      if (coord.getRelativeX() == -mapPanel.getViewPointX()) {
        tempBorder = true;
      }
      if (coord.getRelativeY() == mapPanel.getViewPointY()) {
        tempBorder = true;
      }
      if (coord.getRelativeY() == -mapPanel.getViewPointY()) {
        tempBorder = true;
      }
      onBorder = tempBorder;
    }
  }

  /**
   * Handle fix/trade button in mapinfo panel
   * @param fleet Fleet which was just clicked
   * @param owner Owner of the fleet
   */
  public void handleFixTradeButton(final Fleet fleet,
      final PlayerInfo owner) {
    if (!fleet.allFixed()) {
      mapInfoPanel.setFixBtn();
    } else if (fleet.isTradeFleet()) {
      Planet nearByPlanet = starMap.getPlanetNextToCoordinate(
          fleet.getCoordinate());
      if (nearByPlanet != null
          && nearByPlanet.getPlanetPlayerInfo() != null
          && nearByPlanet.getPlanetPlayerInfo() == owner) {
        mapInfoPanel.setTradeBtn();
      } else if (nearByPlanet != null
          && nearByPlanet.getPlanetPlayerInfo() != null
          && nearByPlanet.getPlanetPlayerInfo() != owner) {
        mapInfoPanel.setTradeBtn();
        int index = nearByPlanet.getPlanetOwnerIndex();
        DiplomacyBonusList list = owner.getDiplomacy().getDiplomacyList(index);
        if (list != null && !list.isBonusType(DiplomacyBonusType.IN_ALLIANCE)
            && !list.isBonusType(DiplomacyBonusType.IN_TRADE_ALLIANCE)
            && !list.isBonusType(DiplomacyBonusType.IN_DEFENSIVE_PACT)) {
          mapInfoPanel.disableFixTradeBtn();
        }
      } else {
        mapInfoPanel.disableFixTradeBtn();
      }
    } else if (fleet.getCommander() != null) {
      Planet nearByPlanet = starMap.getPlanetNextToCoordinate(
          fleet.getCoordinate());
      if (nearByPlanet != null
          && nearByPlanet.getPlanetPlayerInfo() == null
          && nearByPlanet.getPlanetaryEvent() != PlanetaryEvent.NONE
          && !nearByPlanet.isEventActivated()) {
        mapInfoPanel.setExploreBtn();
      } else {
        mapInfoPanel.disableFixTradeBtn();
      }
    } else {
      mapInfoPanel.disableFixTradeBtn();
    }
    if (!owner.isHuman()) {
      mapInfoPanel.disableFixTradeBtn();
    }
  }

  /**
   * Show new route planning
   */
  public void showRoutePlanning() {
    if (routePlanning && lastClickedFleet != null) {
      if (coord != null) {
        Route route = new Route(lastClickedFleet.getX(),
            lastClickedFleet.getY(), coord.getMapX(), coord.getMapY(),
            lastClickedFleet.getFleetFtlSpeed());
        if (isRegularRoute()) {
          route.setRegularSpeed(lastClickedFleet.getFleetSpeed());
        }
        mapPanel.setRoute(route);
      }
    } else {
      routePlanning = false;
      mapPanel.setRoute(null);
    }
  }
  /**
   * Hide current route planning
   */
  public void hideRoutePlanning() {
    routePlanning = false;
    mapPanel.setRoute(null);
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    super.mouseDragged(e);
    if (!dragging) {
      dragOrigMapX = starMap.getDrawX();
      dragOrigMapY = starMap.getDrawY();
      dragOrigX = e.getX();
      dragOrigY = e.getY();
      dragging = true;
    }
    int diffX = dragOrigX - e.getX();
    int diffY = dragOrigY - e.getY();
    diffX = diffX / PIXEL_SCROLL_AMOUNT;
    diffY = diffY / PIXEL_SCROLL_AMOUNT;
    starMap.setDrawPos(dragOrigMapX + diffX, dragOrigMapY + diffY);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    super.mouseReleased(e);
    dragging = false;
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
    int rotation = e.getWheelRotation();
    if (rotation < 0) {
      int zoomLevel = starMap.getZoomLevel();
      zoomLevel++;
      if (zoomLevel > Tile.ZOOM_IN) {
        zoomLevel = Tile.ZOOM_IN;
      }
      starMap.setZoomLevel(zoomLevel);
      mapPanel.redoViewPoints();
      starMap.setForceRedraw(true);
    }
    if (rotation > 0) {
      int zoomLevel = starMap.getZoomLevel();
      zoomLevel--;
      if (zoomLevel < Tile.ZOOM_OUT1) {
        zoomLevel = Tile.ZOOM_OUT1;
      }
      starMap.setZoomLevel(zoomLevel);
      mapPanel.redoViewPoints();
      starMap.setForceRedraw(true);
    }
    super.mouseWheelMoved(e);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    setDoubleClicked(false);
    setMoveClicked(false);
    if (mapPanel.isShowMiniMap()
        && e.getX() >= mapPanel.getMiniMapTopX()
        && e.getX() <= mapPanel.getMiniMapBotX()
        && e.getY() >= mapPanel.getMiniMapTopY()
        && e.getY() <= mapPanel.getMiniMapBotY()) {
      int x = e.getX() - mapPanel.getMiniMapTopX();
      int y = e.getY() - mapPanel.getMiniMapTopY();
      x = x / mapPanel.getMinimap().getSectorSize();
      y = y / mapPanel.getMinimap().getSectorSize();
      starMap.setDrawPos(mapPanel.getMinimap().getDrawPointX() + x,
          mapPanel.getMinimap().getDrawPointY() + y);
      return;
    }
    Coordinate center = new Coordinate(mapPanel.getLastDrawnX(),
        mapPanel.getLastDrawnY());
    coord = new PixelsToMapCoordinate(center, e.getX(), e.getY(),
        mapPanel.getOffsetX(), mapPanel.getOffsetY(), mapPanel.getViewPointX(),
        mapPanel.getViewPointY(), false, mapPanel.getLastZoomLevel());
    if (!coord.isOutOfPanel()) {
      if (mapPanel.getPopup() != null) {
        mapPanel.getPopup().dismiss();
        starMap.setForceRedraw(true);
        SoundPlayer.playMenuSound();
        return;
      }
      if (getLastClickedFleet() != null && mapPanel.getRoute() != null) {
        getLastClickedFleet().setRoute(mapPanel.getRoute());
        mapPanel.setRoute(null);
        routePlanning = false;
      }
      if (starMap.getCurrentPlayerInfo().getSectorVisibility(
          coord.getMapCoordinate()) >= PlayerInfo.VISIBLE
          || e.getButton() == MouseEvent.BUTTON3) {
        Planet planet = starMap.getPlanetByCoordinate(coord.getMapX(),
            coord.getMapY());
        Fleet fleet = starMap.getFleetByCoordinate(coord.getMapX(),
            coord.getMapY());
        int index = starMap.getTileIndex(coord.getMapX(), coord.getMapY());
        Tile tile = Tiles.getTileByIndex(index, Tile.ZOOM_NORMAL);
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
          // Double click Button1
          setDoubleClicked(true);
          if (planet != null) {
            setLastClickedPlanet(planet);
            setLastClickedFleet(null);
            setWarningShown(false);
            mapInfoPanel.showPlanet(planet, true,
                starMap.getCurrentPlayerInfo());
            if (!planet.isEventActivated()
                && planet.getPlanetaryEvent() != PlanetaryEvent.NONE
                && Game.getTutorial() != null && starMap.isTutorialEnabled()) {
                String tutorialText = Game.getTutorial().showTutorialText(8);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION,
                    tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
                starMap.getCurrentPlayerInfo().getMsgList()
                   .addNewMessage(msg);
              }
            }
          } else if (fleet != null) {
            setLastClickedPlanet(null);
            setWarningShown(false);
            FleetTileInfo[][] tiles = starMap.getFleetTiles();
            PlayerInfo owner = starMap.getPlayerByIndex(
                tiles[fleet.getX()][fleet.getY()].getPlayerIndex());
            PlayerInfo info = starMap.getCurrentPlayerInfo();
            if (info.getSectorVisibility(coord.getMapCoordinate())
                >= PlayerInfo.VISIBLE
                && (info.getSectorCloakDetection(
                 coord.getMapX(), coord.getMapY()) >= fleet
                     .getFleetCloackingValue()
                 || info.getFleets().isFleetOnList(fleet))) {
              setLastClickedFleet(fleet);
              mapInfoPanel.showFleet(fleet, owner, starMap.isDebug());
              handleFixTradeButton(fleet, owner);
            }
          }
        } else if (e.getClickCount() == 1
            && e.getButton() == MouseEvent.BUTTON3) {
          // single click right button
          if (lastClickedFleet != null) {
            moveX = coord.getMapX();
            moveY = coord.getMapY();
            Coordinate lastClickedFleetCoordinate = new Coordinate(
                lastClickedFleet.getX(), lastClickedFleet.getY());
            Coordinate moveCoordinate = new Coordinate(moveX, moveY);
            double distance = lastClickedFleetCoordinate.calculateDistance(
                moveCoordinate);
            if (distance < 2 && distance > 0) {
              moveClicked = true;
            } else {
              FleetTileInfo[][] tiles = starMap.getFleetTiles();
              PlayerInfo owner = starMap.getPlayerByIndex(
                  tiles[lastClickedFleetCoordinate.getX()]
                      [lastClickedFleetCoordinate.getY()].getPlayerIndex());
              if (owner == starMap.getCurrentPlayerInfo()
                  && !lastClickedFleet.isStarBaseDeployed()) {
                setRoutePlanning(true);
                setRegularRoute(true);
                showRoutePlanning();
              } else {
                SoundPlayer.playMenuDisabled();
              }
            }
          }
        } else if (e.getClickCount() == 1
            && e.getButton() == MouseEvent.BUTTON1) {
          // Single click on left button
          if (lastClickedPlanet == planet && fleet != null) {
            int detection = starMap.getCurrentPlayerInfo()
                .getSectorCloakDetection(fleet.getX(), fleet.getY());
            FleetTileInfo[][] tiles = starMap.getFleetTiles();
            PlayerInfo owner = starMap.getPlayerByIndex(
                tiles[fleet.getX()][fleet.getY()].getPlayerIndex());
            if (detection >= fleet.getFleetCloackingValue()
                  || owner == starMap.getCurrentPlayerInfo()) {
              mapInfoPanel.showFleet(fleet, owner, starMap.isDebug());
              handleFixTradeButton(fleet, owner);
              setLastClickedFleet(fleet);
              setLastClickedPlanet(null);
              setWarningShown(false);
            } else {
              setLastClickedPlanet(null);
              setLastClickedFleet(null);
              setWarningShown(false);
              if (!tile.getDescription().isEmpty()) {
                mapInfoPanel.showTile(tile);
              } else {
                mapInfoPanel.showEmpty();
              }
            }
          } else if (planet != null) {
            mapInfoPanel.showPlanet(planet, true,
                starMap.getCurrentPlayerInfo());
            if (!planet.isEventActivated()
                && planet.getPlanetaryEvent() != PlanetaryEvent.NONE
                && Game.getTutorial() != null && starMap.isTutorialEnabled()) {
                String tutorialText = Game.getTutorial().showTutorialText(8);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION,
                    tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
                starMap.getCurrentPlayerInfo().getMsgList()
                   .addNewMessage(msg);
              }
            }
            setLastClickedPlanet(planet);
            setLastClickedFleet(null);
            setWarningShown(false);
          } else if (fleet != null) {
            int detection = starMap.getCurrentPlayerInfo()
                .getSectorCloakDetection(fleet.getX(), fleet.getY());
            FleetTileInfo[][] tiles = starMap.getFleetTiles();
            PlayerInfo owner = starMap.getPlayerByIndex(
                tiles[fleet.getX()][fleet.getY()].getPlayerIndex());
            if (detection >= fleet.getFleetCloackingValue()
                || owner == starMap.getCurrentPlayerInfo()) {
              mapInfoPanel.showFleet(fleet, owner, starMap.isDebug());
              handleFixTradeButton(fleet, owner);
              setLastClickedFleet(fleet);
            } else {
              if (!tile.getDescription().isEmpty()) {
                mapInfoPanel.showTile(tile);
              } else {
                mapInfoPanel.showEmpty();
              }
              setLastClickedPlanet(null);
              setLastClickedFleet(null);
              setWarningShown(false);
            }
          } else {
            if (!tile.getDescription().isEmpty()) {
              mapInfoPanel.showTile(tile);
            } else {
              mapInfoPanel.showEmpty();
            }
            setLastClickedPlanet(null);
            setLastClickedFleet(null);
            setWarningShown(false);
          }
        }
      } else if (starMap.getCurrentPlayerInfo().getSectorVisibility(
          coord.getMapCoordinate()) == PlayerInfo.FOG_OF_WAR
          && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
        Planet planet = starMap.getPlanetByCoordinate(coord.getMapX(),
            coord.getMapY());
        if (planet != null) {
          setLastClickedPlanet(planet);
          setLastClickedFleet(null);
          mapInfoPanel.showPlanet(planet, false,
              starMap.getCurrentPlayerInfo());
        } else {
          int index = starMap.getTileIndex(coord.getMapX(), coord.getMapY());
          Tile tile = Tiles.getTileByIndex(index, Tile.ZOOM_NORMAL);
          if (!tile.getDescription().isEmpty()) {
            mapInfoPanel.showTile(tile);
          } else {
            mapInfoPanel.showEmpty();
          }
        }
      } else {
        Tile tile = starMap.getTile(coord.getMapX(), coord.getMapY());
        if (!tile.getDescription().isEmpty()
            && starMap.getCurrentPlayerInfo().getSectorVisibility(
                coord.getMapCoordinate()) == PlayerInfo.FOG_OF_WAR
            || tile.isStarTile()) {
          mapInfoPanel.showTile(tile);
        } else {
          mapInfoPanel.showEmpty();
        }
        setLastClickedPlanet(null);
        setLastClickedFleet(null);
      }
    }

  }

  /**
   * @return the lastClickedPlanet
   */
  public Planet getLastClickedPlanet() {
    return lastClickedPlanet;
  }

  /**
   * @param lastClickedPlanet the lastClickedPlanet to set
   */
  public void setLastClickedPlanet(final Planet lastClickedPlanet) {
    this.lastClickedPlanet = lastClickedPlanet;
  }

  /**
   * @return Last clicked fleet
   */
  public Fleet getLastClickedFleet() {
    return lastClickedFleet;
  }

  /**
   * @param lastClickedFleet Last clicked fleet
   */
  public void setLastClickedFleet(final Fleet lastClickedFleet) {
    this.lastClickedFleet = lastClickedFleet;
  }

  /**
   * @return Is route being planned
   */
  public boolean isRoutePlanning() {
    return routePlanning;
  }

  /**
   * @param routePlanning Is route being planned
   */
  public void setRoutePlanning(final boolean routePlanning) {
    this.routePlanning = routePlanning;
  }

  /**
   * Is regular or ftl route planning in use.
   * @return True for regular.
   */
  public boolean isRegularRoute() {
    return regularRoute;
  }

  /**
   * Set regular or FTL route planning.
   * @param regularRoute True for regular planning.
   */
  public void setRegularRoute(final boolean regularRoute) {
    this.regularRoute = regularRoute;
  }

  /**
   * @return Something has been double clicked
   */
  public boolean isDoubleClicked() {
    return doubleClicked;
  }

  /**
   * @param doubleClicked Something has been double clicked
   */
  public void setDoubleClicked(final boolean doubleClicked) {
    this.doubleClicked = doubleClicked;
  }

  /**
   * @return Move clicked
   */
  public boolean isMoveClicked() {
    return moveClicked;
  }

  /**
   * @param moveClicked Move clicked
   */
  public void setMoveClicked(final boolean moveClicked) {
    this.moveClicked = moveClicked;
  }

  /**
   * Is warning shown for player?
   * @return True if warning has been shown
   */
  public boolean isWarningShown() {
    return warningShown;
  }

  /**
   * Set warning shown flag.
   * @param warningShown True if shown.
   */
  public void setWarningShown(final boolean warningShown) {
    this.warningShown = warningShown;
  }

}
