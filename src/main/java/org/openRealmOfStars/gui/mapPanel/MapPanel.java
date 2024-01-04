package org.openRealmOfStars.gui.mapPanel;
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.util.GraphRoutines;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatAnimation;
import org.openRealmOfStars.player.combat.CombatAnimationType;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetVisibility;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.SquareInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.namegenerators.RandomSystemNameGenerator;

/**
 *
 * Class for handling the star map drawing to JPanel
 *
 */

public class MapPanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Map drawing area size width
   */
  private static final int DEFAULT_WIDTH = 864;
  /**
   * Map drawing area size height
   */
  private static final int DEFAULT_HEIGHT = 608;

  /**
   * Battle Map view size. This size is both width and height
   */
  private static final int BATTLE_VIEW_SIZE = 576;

  /**
   * Delay for animation update.
   */
  private static final int ANIMATION_COUNT_MAX = 16;

  /**
   * How many tiles can be fitted on half of the panel
   * This is for X axel.
   */
  private int viewPointX;
  /**
   * How many tiles can be fitted on half of the panel.
   * This is for Y axel.
   */
  private int viewPointY;

  /**
   * View point offset for x axel
   */
  private int viewPointOffsetX;

  /**
   * View point offset for Y axel
   */
  private int viewPointOffsetY;

  /**
   * Where the map is actually drawn.
   */
  private BufferedImage screen;

  /**
   * Background image for drawing partial screen.
   */
  private BufferedImage backgroundScreen;
  /**
   * Start value for flicker blue
   */
  private static final int START_VALUE_FOR_FLICKER_BLUE = 64;

  /**
   * Value used to create flickering blue grid
   */
  private int flickerBlue = START_VALUE_FOR_FLICKER_BLUE;

  /**
   * Is flicker value moving up
   */
  private boolean flickerGoUp = true;

  /**
   * Last drawn map center coordinate on x axel
   */
  private int lastDrawnCenterX;
  /**
   * Last drawn map center coordinate on y axel
   */
  private int lastDrawnCenterY;

  /**
   * Route to draw
   */
  private Route route;

  /**
   * Is this for battle panel or not
   */
  private boolean battle;

  /**
   * Worm hole animation frame
   */
  private int wormHoleAnimation = 0;

  /**
   * History cultural information
   */
  private int[][] historyCultures;

  /** Whether were history map coordinates initialized */
  private boolean historyCoordInitialized = false;

  /** History map coordinate X component */
  private int historyCoordX;

  /** History map coordinate Y component */
  private int historyCoordY;

  /**
   * Image to be drawn on lower left side of the map in history view
   */
  private BufferedImage leftSpaceImage;

  /**
   * Image to be drawn on lower right side of the map in history view
   */
  private BufferedImage rightSpaceImage;

  /**
   * Popup to shown on top of map
   */
  private PopupPanel popup;

  /**
   * Tile override. Currently used with Artificial planets
   */
  private int[][] tileOverride;

  /**
   * Array which tiles needs to be redrawn.
   */
  private boolean[][] redrawTile;
  /**
   * Flag for full draw.
   */
  private boolean fullDraw;
  /**
   * Cursor focus. Over 0 it means to draw focus lines.
   */
  private int cursorFocus;

  /**
   * Minimap handler/drawer
   */
  private Minimap minimap;

  /**
   * Flag for showing minimap
   */
  private boolean showMiniMap;

  /**
   * Mini map top X coordinate in map panel.
   */
  private int miniMapTopX;
  /**
   * Mini map top Y coordinate in map panel.
   */
  private int miniMapTopY;
  /**
   * Mini map bottom X coordinate in map panel.
   */
  private int miniMapBotX;
  /**
   * Mini map bottom Y coordinate in map panel.
   */
  private int miniMapBotY;
  /**
   * Pulsating transparency value.
   */
  private int transparency;
  /**
   * Flag for improved parallax.
   */
  private boolean improvedParallax;

  /**
   * Flag for update animation.
   */
  private boolean updateAnimation;
  /**
   * Update animation count
   */
  private int updateAnimationCount;
  /**
   * Draw weapon range for combat.
   */
  private boolean drawWeaponRange;

  /**
   * Panel type based on last drawing.
   */
  private MapPanelType panelType;

  /**
   * Last Cursor position x coordinate
   */
  private int lastCursorPosX;
  /**
   * Last cursor position y coordinate.
   */
  private int lastCursorPosY;
  /**
   * Last Zoom level which was used for drawing.
   */
  private int lastZoomLevel;
  /**
   * Flag for redoing viewpoints.
   */
  private boolean redoViewPoints;
  /**
   * Constructor for Map Panel. This can be used for drawing star map
   * or battle map
   * @param game GameFrame containing frame size
   * @param battle True if drawing battle map.
   */
  public MapPanel(final Game game, final boolean battle) {
    initMapPanel(game, battle);
  }

  /**
   * Constructor for Map Panel. This can be used for drawing star map
   * or battle map
   * @param battle True if drawing battle map.
   */
  public MapPanel(final boolean battle) {
    initMapPanel(null, battle);
  }

  /**
   * Constructor for Map Panel. This can be used for drawing battle map
   * @param game GameFrame containing frame size
   */
  public MapPanel(final Game game) {
    initMapPanel(game, true);
  }

  /**
   * Initialize actual map panel.
   * @param game GameFrame for fetching frame size in battle mode
   * @param battleMode True if drawing battle map.
   */
  private void initMapPanel(final Game game, final boolean battleMode) {
    cursorFocus = 0;
    battle = battleMode;
    int width = DEFAULT_WIDTH;
    int height = DEFAULT_HEIGHT;
    historyCultures = null;
    historyCoordInitialized = false;
    setShowMiniMap(false);
    tileOverride = null;
    improvedParallax = false;
    lastCursorPosX = -1;
    lastCursorPosY = -1;
    if (game != null) {
      improvedParallax = game.isImprovedParallax();
    }
    if (battle && game == null) {
      width = BATTLE_VIEW_SIZE;
      height = BATTLE_VIEW_SIZE;
    } else if (battle && game != null) {
      width = game.getWidth() - 470;
      height = game.getHeight() - 210;
      if (width < BATTLE_VIEW_SIZE) {
        width = BATTLE_VIEW_SIZE;
      }
      if (height < BATTLE_VIEW_SIZE) {
        height = BATTLE_VIEW_SIZE;
      }
    }
    panelType = MapPanelType.STARMAP;
    if (battle) {
      panelType = MapPanelType.BATTLE;
      screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Dimension size = new Dimension(width, height);
      this.setSize(size);
      this.setPreferredSize(size);
      this.setMinimumSize(size);
      this.setMaximumSize(size);
      calculateViewPoints();
    }
    this.setBackground(Color.black);
    setRoute(null);
    wormHoleAnimation = 0;
    transparency = 32;
  }
  /**
   * Calculate view according the actual panel size;
   */
  protected void calculateViewPoints() {
    calculateViewPoints(-1, -1, Tile.ZOOM_NORMAL);
  }

  /**
   * Calculate view according the actual panel size;
   * @param mapX Map max x size
   * @param mapY Map max y size
   * @param zoomLevel Zoomlevel for map
   */
  protected void calculateViewPoints(final int mapX, final int mapY,
      final int zoomLevel) {
    int tileWidth = Tile.getMaxWidth(zoomLevel);
    int tileHeight = Tile.getMaxHeight(zoomLevel);
    if (battle) {
      // Combat does not have scroll screen
      viewPointX = (Combat.MAX_X - 1) / 2;
      viewPointY = (Combat.MAX_Y - 1) / 2;
    } else {
      viewPointX = (this.getWidth() / tileWidth - 1) / 2;
      viewPointY = (this.getHeight() / tileHeight - 1) / 2;
      if (viewPointX * 2 + 1 > mapX && mapX != -1) {
        viewPointX = (mapX - 1) / 2;
        if (viewPointX * 2 + 1 == mapX - 1) {
          viewPointX++;
        }
      }
      if (viewPointY * 2 + 1 > mapY && mapY != -1) {
        viewPointY = (mapY - 1) / 2;
        if (viewPointY * 2 + 1 == mapY - 1) {
          viewPointY++;
        }
      }
    }
    if (viewPointX < 1) {
      viewPointX = 1;
    }
    if (viewPointY < 1) {
      viewPointY = 1;
    }
    redrawTile = new boolean[viewPointX * 2 + 1][viewPointY * 2 + 1];
    if (battle) {
      viewPointOffsetX = screen.getWidth()
          - (2 * viewPointX * ShipImage.MAX_WIDTH + ShipImage.MAX_WIDTH);
      viewPointOffsetX = viewPointOffsetX / 2;
      viewPointOffsetY = screen.getHeight()
          - (2 * viewPointY * ShipImage.MAX_HEIGHT + ShipImage.MAX_HEIGHT);
      viewPointOffsetY = viewPointOffsetY / 2;
    } else {
      viewPointOffsetX = this.getWidth()
          - (2 * viewPointX * tileWidth + tileWidth);
      viewPointOffsetX = viewPointOffsetX / 2;
      viewPointOffsetY = this.getHeight()
          - (2 * viewPointY * tileHeight + tileHeight);
      viewPointOffsetY = viewPointOffsetY / 2;
    }
  }

  @Override
  public void paint(final Graphics arg0) {
    super.paint(arg0);
    if (screen != null) {
      if (popup != null) {
        popup.drawPopup(screen);
      }
      arg0.drawImage(screen, 0, 0, null);
    }
  }

  /**
   * Safety offset for parallax scrolling components
   */
  private static final int SAFETY_OFFSET = 20;
  /**
   * Parallax offset drawing for actual scrolling components
   */
  private static final int PARALLAX_OFFSET = 10;

  /**
   * Flicker color green start
   */
  private static final int FLICKER_GREEN = 118;
  /**
   * Flicker color blue start
   */
  private static final int FLICKER_BLUE = 150;

  /**
   * Color component divider
   */
  private static final int COLOR_COMPONENT_DIVIDER = 256;
  /**
   * Single color component maximum value
   */
  private static final int COLOR_COMPONENT_MAX = 255;
  /**
   * Single color component half value
   */
  private static final int COLOR_COMPONENT_HALF = 128;
  /**
   * Flicker upper limit
   */
  private static final int FLICKER_UPPER_LIMIT = 384;


  /**
   * Draw Fleet into map panel screen.
   * @param gr Graphics2D for screen
   * @param fleetMap Precalculated fleet map from starmap.
   * @param cx Center of screen
   * @param cy Center of screen
   * @param i  X coordinate modifier of center of screen
   * @param j  Y coordinate modifier of center of screen
   * @param starMap StarMap
   * @param info Realm which is viewing the starmap
   * @param pixelX Pixel X Coordinate in screen
   * @param pixelY Pixel Y Coordinate in screen
   */
  private void drawFleet(final Graphics2D gr, final FleetTileInfo[][] fleetMap,
      final int cx, final int cy, final int i, final int j,
      final StarMap starMap, final PlayerInfo info,
      final int pixelX, final int pixelY) {
    Fleet fleet = null;
    PlayerInfo fleetOwner = null;
    int fleetOwnerIndex = -1;
    if (fleetMap[i + cx][j + cy] != null) {
      fleet = starMap.getFleetByFleetTileInfo(fleetMap[i + cx]
          [j + cy]);
      if (fleet != null) {
        fleetOwner = starMap.getPlayerInfoByFleet(fleet);
        fleetOwnerIndex = starMap.getPlayerList().getIndex(fleetOwner);
      }
    }
    if (info != null && fleet != null) {
      FleetVisibility visibility = new FleetVisibility(info, fleet,
          fleetOwnerIndex);
      boolean drawShip = visibility.isFleetVisible();
      boolean recognized = visibility.isRecognized();
      boolean espionageDetected = visibility.isEspionageDetected();
      boolean moved = false;
      if (fleet.getMovesLeft() == 0 && fleet.getRoute() == null) {
        moved = true;
      }
      if (recognized && fleetOwnerIndex != -1 && drawShip) {
        redrawTile[i + viewPointX][j + viewPointY] = true;
        PlayerInfo shipInfo = starMap.getPlayerByIndex(fleetOwnerIndex);
        Tile fleetColor = Tiles.getTileByName(
            shipInfo.getColor().getShipTile(), starMap.getZoomLevel());
        if (fleetColor != null) {
          fleetColor.draw(gr, pixelX, pixelY);
        }
        if (fleetOwner != info && Game.getTutorial() != null
            && starMap.isTutorialEnabled() && info.isHuman()) {
          String tutorialText = Game.getTutorial().showTutorialText(50);
          if (tutorialText != null) {
            Message msg = new Message(MessageType.INFORMATION,
                tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
            msg.setCoordinate(new Coordinate(i + cx, j + cy));
            info.getMsgList().addNewMessage(msg);
          }
          tutorialText = Game.getTutorial().showTutorialText(90);
          if (tutorialText != null) {
            Message msg = new Message(MessageType.INFORMATION,
                tutorialText, Icons.getIconByName(Icons.ICON_TUTORIAL));
            msg.setCoordinate(new Coordinate(i + cx, j + cy));
            info.getMsgList().addNewMessage(msg);
          }
        }
      }
      if (drawShip) {
        BufferedImage img = ShipImages
            .getByRace(fleetMap[i + cx][j + cy].getRace())
            .getShipZoomedImage(fleetMap[i + cx][j + cy].getImageIndex(),
                starMap.getZoomLevel());
        gr.drawImage(img, pixelX, pixelY, null);
        int offsetx = Icon16x16.MAX_WIDTH;
        int offsety = Icon16x16.MAX_HEIGHT;
        if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
          offsetx = 48;
          offsety = 48;
        }
        if (starMap.getZoomLevel() > Tile.ZOOM_OUT1) {
          if (espionageDetected) {
            Icon16x16 icon = Icons.getIconByName(Icons.ICON_SPY_GOGGLES);
            icon.draw(gr, pixelX + offsetx,
                pixelY + offsety);
          }
          if (fleet.getRoute() != null && fleetOwner == info) {
            if (fleet.getRoute().getFtlSpeed() > 0
              || fleet.getRoute().getRegularSpeed() > 0) {
              Icon16x16 icon = Icons.getIconByName(
                  Icons.ICON_ENROUTED_MOVES);
              icon.draw(gr, pixelX + offsetx,
                  pixelY + offsety);
            }
          } else if (moved) {
            Icon16x16 icon = Icons.getIconByName(Icons.ICON_MOVES_DONE);
            icon.draw(gr, pixelX + offsetx,
                pixelY + offsety);
          } else {
            Icon16x16 icon = Icons.getIconByName(Icons.ICON_MOVES_LEFT_1);
            if (flickerGoUp) {
              icon = Icons.getIconByName(Icons.ICON_MOVES_LEFT_2);
            }
            icon.draw(gr, pixelX + offsetx,
                pixelY + offsety);
          }
        }
      }
    }
    if (fleet == null && fleetMap[i + cx][j + cy] != null) {
      Planet planetOrbital = starMap.getPlanetByFleetTileInfo(
          fleetMap[i + cx][j + cy]);
      if (planetOrbital != null && planetOrbital.getOrbital() != null
          && info != null
          && info.getSectorVisibility(planetOrbital.getCoordinate())
          >= PlayerInfo.VISIBLE) {
        // Draw orbital
        BufferedImage img = ShipImages
            .getByRace(fleetMap[i + cx][j + cy].getRace())
            .getNormalShipImage(fleetMap[i + cx][j + cy].getImageIndex());
        gr.drawImage(img, pixelX, pixelY, null);
      }
    }
  }

  /**
   * Draw Tile
   * @param gr Graphics2D
   * @param cx Center of screen
   * @param cy Center of screen
   * @param i  X coordinate modifier of center of screen
   * @param j  Y coordinate modifier of center of screen
   * @param starMap StarMap
   * @param info Realm which is viewing the starmap
   * @param pixelX Pixel X Coordinate in screen
   * @param pixelY Pixel Y Coordinate in screen
   * @param planet Planet info at target tile
   * @return Tile
   */
  private Tile drawTile(final Graphics2D gr,
      final int cx, final int cy, final int i, final int j,
      final StarMap starMap, final PlayerInfo info,
      final int pixelX, final int pixelY, final Planet planet) {
    Tile tile = starMap.getTile(i + cx, j + cy);
    if (tile == null) {
      return null;
    }
    if (tile.getAnimationIndex() != tile.getIndex()) {
      if (updateAnimation) {
        // Change map tile for next drawing
        starMap.setTile(i + cx, j + cy,
            Tiles.getTileByIndex(tile.getAnimationIndex(),
                starMap.getZoomLevel()));
      }
      redrawTile[i + viewPointX][j + viewPointY] = true;
    }
    // Draw only non empty tiles
    if (info != null && !tile.getName().equals(TileNames.EMPTY)
        && !tile.getName().equals(TileNames.ASCENSION_PORT_CLOSED)
        && info.getSectorVisibility(new Coordinate(i + cx,
            j + cy)) != PlayerInfo.UNCHARTED
        || starMap.getTileInfo(i + cx, j + cy)
            .getType() == SquareInfo.TYPE_SUN
        || starMap.getTileInfo(i + cx, j + cy)
            .getType() == SquareInfo.TYPE_BLACKHOLE_CENTER) {
      tile.draw(gr, pixelX, pixelY);
    }
    // Draw home world marker
    if (planet != null && !planet.isGasGiant() && info != null
        && info.getSectorVisibility(new Coordinate(i + cx,
            j + cy)) != PlayerInfo.UNCHARTED
        && planet.getHomeWorldIndex() != -1
        && starMap.getZoomLevel() > Tile.ZOOM_OUT1) {
      Icon16x16 icon = Icons.getIconByName(Icons.ICON_CULTURE);
      int offset = Icon16x16.MAX_WIDTH;
      if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
        offset = 40;
      }
      icon.draw(gr, pixelX + offset, pixelY + offset);
    }
    // Draw deep space anchor marker
    if ((tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
        || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2))
        && info != null && info.getSectorVisibility(new Coordinate(i + cx,
            j + cy)) != PlayerInfo.UNCHARTED) {
      if (starMap.getZoomLevel() > Tile.ZOOM_OUT1) {
        Icon16x16 icon = Icons.getIconByName(Icons.ICON_STARBASE);
        int offset = Icon16x16.MAX_WIDTH;
        if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
          offset = 40;
        }
        icon.draw(gr, pixelX + offset, pixelY + offset);
      }
      redrawTile[i + viewPointX][j + viewPointY] = true;
    }
    return tile;
  }

  /**
   * Draw Fog of War
   * @param gr Graphics2D
   * @param cx Center of screen
   * @param cy Center of screen
   * @param i  X coordinate modifier of center of screen
   * @param j  Y coordinate modifier of center of screen
   * @param starMap StarMap
   * @param info Realm which is viewing the starmap
   * @param pixelX Pixel X Coordinate in screen
   * @param pixelY Pixel Y Coordinate in screen
   */
  private static void drawFogOfWar(final Graphics2D gr,
      final int cx, final int cy, final int i, final int j,
      final StarMap starMap, final PlayerInfo info,
      final int pixelX, final int pixelY) {
    if (info != null) {
      switch (info.getSectorVisibility(new Coordinate(i + cx,
          j + cy))) {
      case PlayerInfo.UNCHARTED: {
        if (starMap.getTileInfo(i + cx, j + cy)
            .getType() != SquareInfo.TYPE_SUN) {
          Tiles.getTileByName(TileNames.UNCHARTED, starMap.getZoomLevel())
              .draw(gr, pixelX, pixelY);
        }
        break;
      }
      case PlayerInfo.FOG_OF_WAR: {
        if (starMap.getTileInfo(i + cx, j + cy)
            .getType() != SquareInfo.TYPE_SUN) {
          Tiles.getTileByName(TileNames.FOG_OF_WAR, starMap.getZoomLevel())
              .draw(gr, pixelX, pixelY);
        }
        break;
      }
      default:
        // Do nothing
        break;
      }
    }
  }

  /**
   * Draw Tile text.
   * @param gr Graphics2D
   * @param cx Center of screen
   * @param cy Center of screen
   * @param i  X coordinate modifier of center of screen
   * @param j  Y coordinate modifier of center of screen
   * @param starMap StarMap
   * @param info Realm which is viewing the starmap
   * @param pixelX Pixel X Coordinate in screen
   * @param pixelY Pixel Y Coordinate in screen
   * @param planet Planet info at target tile
   */
  private void drawTileText(final Graphics2D gr,
      final int cx, final int cy, final int i, final int j,
      final StarMap starMap, final PlayerInfo info,
      final int pixelX, final int pixelY, final Planet planet) {
    Tile tile = starMap.getTile(i + cx, j + cy);
    // Draw sun's text
    if ((tile.getName().equals(TileNames.SUN_E)
        || tile.getName().equals(TileNames.BLUE_STAR_E)
        || tile.getName().equals(TileNames.STAR_E))
        && i > -viewPointX + 1) {
      Sun sun = starMap.getSunByCoordinate(i + cx, j + cy);
      if (sun != null) {
        redrawTile[i + viewPointX][j + viewPointY] = true;
        Font font = GuiFonts.getFontCubellanSC();
        if (starMap.getZoomLevel() == Tile.ZOOM_OUT1) {
          font = GuiFonts.getFontCubellanVerySmall();
        }
        int textWidth = (int) font.getStringBounds(sun.getName(),
            gr.getFontRenderContext()).getWidth();
        int offset = Tile.getMaxWidth(starMap.getZoomLevel()) / 2
            + textWidth / 2 - 2;
        gr.setStroke(GuiStatics.TEXT_LINE);
        if (tile.getName().equals(TileNames.SUN_E)) {
          gr.setColor(GuiStatics.COLOR_GOLD_TRANS);
        }
        if (tile.getName().equals(TileNames.STAR_E)) {
          gr.setColor(GuiStatics.COLOR_SPACE_YELLOW);
        }
        if (tile.getName().equals(TileNames.BLUE_STAR_E)) {
          gr.setColor(GuiStatics.getCoolSpaceColor());
        }
        gr.drawLine(pixelX - offset,
            pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2 - 3,
            pixelX - Tile.getMaxWidth(starMap.getZoomLevel()) + offset,
            pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2 - 3);
        gr.setColor(Color.BLACK);
        gr.setFont(font);
        gr.drawString(sun.getName(),
            pixelX - Tile.getMaxWidth(starMap.getZoomLevel()) / 2
            - textWidth / 2,
            pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2);
      }
    }

    // Draw Gas giant text
    if ((tile.getName().equals(TileNames.GAS_GIANT_1_SE)
        && i > -viewPointX
        || tile.getName().equals(TileNames.GAS_GIANT_2_SE)
            && i > -viewPointX
        || tile.getName().equals(TileNames.GAS_GIANT_3_SE)
            && i > -viewPointX
        || tile.getName().equals(TileNames.JUPITER_SE)
            && i > -viewPointX
        || tile.getName().equals(TileNames.SATURN_SE)
            && i > -viewPointX
        || tile.getName().equals(TileNames.ICEGIANT1_SE)
            && i > -viewPointX
        || tile.getName().equals(TileNames.ICEGIANT2_SE)
            && i > -viewPointX)
        && planet != null && info != null && info
            .getSectorVisibility(new Coordinate(i + cx,
                j + cy)) != PlayerInfo.UNCHARTED) {
      redrawTile[i + viewPointX][j + viewPointY] = true;
      Font font = GuiFonts.getFontCubellanSC();
      if (starMap.getZoomLevel() == Tile.ZOOM_OUT1) {
        font = GuiFonts.getFontCubellanVerySmall();
      }
      int textWidth = (int) font.getStringBounds(
          RandomSystemNameGenerator.numberToRoman(
              planet.getOrderNumber()), gr.getFontRenderContext())
          .getWidth();
      int offset = textWidth / 2 - 2;
      gr.setStroke(GuiStatics.TEXT_LINE);
      gr.setColor(GuiStatics.COLOR_GREYBLUE_NO_OPAGUE);
      gr.drawLine(pixelX - offset, pixelY - 3, pixelX + offset,
          pixelY - 3);
      gr.setColor(Color.BLACK);
      gr.setFont(font);
      gr.drawString(
          RandomSystemNameGenerator.numberToRoman(
              planet.getOrderNumber()), pixelX - textWidth / 2, pixelY);
    }

    // Draw planet text
    if (planet != null && !planet.isGasGiant()
        && planet.getOrderNumber() != 0 && info != null && info
        .getSectorVisibility(new Coordinate(i + cx,
            j + cy)) != PlayerInfo.UNCHARTED) {
      Font font = GuiFonts.getFontCubellanSC();
      if (starMap.getZoomLevel() == Tile.ZOOM_OUT1) {
        font = GuiFonts.getFontCubellanVerySmall();
      }
      int textWidth = (int) font.getStringBounds(
          RandomSystemNameGenerator.numberToRoman(
              planet.getOrderNumber()), gr.getFontRenderContext()).getWidth();
      int offset = Tile.getMaxWidth(starMap.getZoomLevel()) / 2
          - textWidth / 2 - 2;
      gr.setStroke(GuiStatics.TEXT_LINE);
      gr.setColor(GuiStatics.COLOR_GREYBLUE);
      gr.drawLine(pixelX + offset,
          pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2 - 3,
          pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - offset,
          pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2 - 3);
      gr.setColor(Color.BLACK);
      gr.setFont(font);
      gr.drawString(
          RandomSystemNameGenerator.numberToRoman(
              planet.getOrderNumber()),
          pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) / 2 - textWidth / 2,
          pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2);
    }
  }
  /**
   * Draw star map to Map panel
   * @param starMap Star map to draw
   */
  public void drawMap(final StarMap starMap) {
    PlayerInfo info = starMap.getCurrentPlayerInfo();
    updateAnimationCount++;
    if (updateAnimationCount >= ANIMATION_COUNT_MAX) {
      if (updateAnimation) {
        updateAnimation = false;
      } else {
        updateAnimation = true;
      }
      updateAnimationCount = 0;
    }
    lastZoomLevel = starMap.getZoomLevel();
    if (minimap == null) {
      minimap = new Minimap(starMap);
      minimap.setDrawPoint(0, 0);
      minimap.drawMinimap();
    } else {
      if (starMap.getMaxX() > 128) {
        // Quickly relocate minimap on scrolling minimap
        int cx = starMap.getDrawX();
        int cy = starMap.getDrawY();
        int rx = minimap.getCenterX() - cx;
        int ry = minimap.getCenterY() - cy;
        if (rx > 64
            || rx < -64
            || ry > 64
            || ry < -64) {
          minimap.setCenterPoint(cx, cy);
        }
      }
      if (isShowMiniMap()) {
        minimap.drawMinimap();
      }
    }
    if (redoViewPoints) {
      calculateViewPoints(starMap.getMaxX(), starMap.getMaxY(),
          starMap.getZoomLevel());
      redoViewPoints = false;
    }
    if (screen == null) {
      calculateViewPoints(starMap.getMaxX(), starMap.getMaxY(),
          starMap.getZoomLevel());
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
        backgroundScreen = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
      } else {
        return;
      }
    }
    byte[][] routeData = null;
    if (route != null) {
      routeData = route.getRouteOnMap(starMap.getMaxX(), starMap.getMaxY());
    }
    Graphics2D gr = screen.createGraphics();
    Graphics2D bgGr = backgroundScreen.createGraphics();
    int cursorPixelX = 0;
    int cursorPixelY = 0;
    // Center coordinates
    int cx = starMap.getDrawX();
    int cy = starMap.getDrawY();
    if (cx < viewPointX) {
      cx = viewPointX;
    }
    if (cx > starMap.getMaxX() - viewPointX - 1) {
      cx = starMap.getMaxX() - viewPointX - 1;
    }
    if (cy < viewPointY) {
      cy = viewPointY;
    }
    if (cy > starMap.getMaxY() - viewPointY - 1) {
      cy = starMap.getMaxY() - viewPointY - 1;
    }
    starMap.setDrawPos(cx, cy);
    int countRedraw = 0;
    int maxTiles = 0;
    for (int i = 0; i < viewPointX * 2 + 1; i++) {
      for (int j = 0; j < viewPointY * 2 + 1; j++) {
        maxTiles++;
        if (redrawTile[i][j]) {
          countRedraw++;
        }
      }
    }
    int drawingPercetange = countRedraw * 100 / maxTiles;
    if (lastDrawnCenterX != cx || lastDrawnCenterY != cy
        || drawingPercetange > 65 || starMap.isForceRedraw()) {
      fullDraw = true;
    } else {
      fullDraw = false;
    }
    if (popup != null) {
      fullDraw = true;
    }
    starMap.setForceRedraw(false);
    // -20 for safety
    int speedX = (GuiStatics.NEBULAE_IMAGE.getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedY = (GuiStatics.NEBULAE_IMAGE.getHeight() - this.getHeight()
        - SAFETY_OFFSET) / starMap.getMaxY();
    if (speedX < 5) {
      speedX = 5;
    }
    if (speedY < 5) {
      speedY = 5;
    }
    int speedStarX = (GuiStatics.getStarField().getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedStarY = (GuiStatics.getStarField().getHeight() - this.getHeight()
        - SAFETY_OFFSET) / starMap.getMaxY();
    if (speedStarX < 3) {
      speedStarX = 3;
    }
    if (speedStarY < 3) {
      speedStarY = 3;
    }
    if (fullDraw) {
      if (!improvedParallax) {
        GraphRoutines.drawTiling(gr, GuiStatics.getStarNebulae(),
            -PARALLAX_OFFSET - cx * speedStarX,
            -PARALLAX_OFFSET - cy * speedStarY,
            this.getWidth(), this.getHeight());
        GraphRoutines.drawTiling(bgGr, GuiStatics.getStarNebulae(),
            -PARALLAX_OFFSET - cx * speedStarX,
            -PARALLAX_OFFSET - cy * speedStarY,
            this.getWidth(), this.getHeight());
      } else {
        // Parallax Scrolling with just two lines!!!
        GraphRoutines.drawTiling(gr, GuiStatics.getStarField(),
            -PARALLAX_OFFSET - cx * speedStarX,
            -PARALLAX_OFFSET - cy * speedStarY,
            this.getWidth(), this.getHeight());
        GraphRoutines.drawTiling(gr, GuiStatics.NEBULAE_IMAGE,
            -PARALLAX_OFFSET - cx * speedX,
            -PARALLAX_OFFSET - cy * speedY,
            this.getWidth(), this.getHeight());
        GraphRoutines.drawTiling(bgGr, GuiStatics.getStarField(),
            -PARALLAX_OFFSET - cx * speedStarX,
            -PARALLAX_OFFSET - cy * speedStarY,
            this.getWidth(), this.getHeight());
        GraphRoutines.drawTiling(bgGr, GuiStatics.NEBULAE_IMAGE,
            -PARALLAX_OFFSET - cx * speedX,
            -PARALLAX_OFFSET - cy * speedY,
            this.getWidth(), this.getHeight());
      }
      for (int i = 0; i < viewPointX * 2 + 1; i++) {
        for (int j = 0; j < viewPointY * 2 + 1; j++) {
          redrawTile[i][j] = false;
        }
      }
    }
    lastDrawnCenterX = cx;
    lastDrawnCenterY = cy;
    int lastCursorIndexX = -1;
    int lastCursorIndexY = -1;
    int scaled = 16 * (flickerBlue - COLOR_COMPONENT_HALF)
        / COLOR_COMPONENT_DIVIDER;
    Color colorDarkBlue = new Color(0, FLICKER_GREEN + scaled,
        FLICKER_BLUE + scaled);
    Color colorFlickerBlue = new Color(0, 0, 16);
    if (flickerBlue < COLOR_COMPONENT_DIVIDER) {
      colorFlickerBlue = new Color(0, 0, flickerBlue);
    } else {
      int above = flickerBlue - COLOR_COMPONENT_DIVIDER;
      colorFlickerBlue = new Color(above, above, COLOR_COMPONENT_MAX);
    }
    if (flickerGoUp) {
      flickerBlue = flickerBlue + 16;
    } else {
      flickerBlue = flickerBlue - 16;
    }
    if (flickerBlue > FLICKER_UPPER_LIMIT) {
      flickerGoUp = false;
    }
    if (flickerBlue < COLOR_COMPONENT_HALF) {
      flickerGoUp = true;
    }

    FleetTileInfo[][] fleetMap = starMap.getFleetTiles(false);

    boolean cursorDrawn = false;
    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    for (int j = -viewPointY; j < viewPointY + 1; j++) {
      for (int i = -viewPointX; i < viewPointX + 1; i++) {
        if (!starMap.isValidCoordinate(i + cx, j + cy)) {
          continue;
        }
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {0.1f, 4.5f }, 0);
        if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
          dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
              BasicStroke.JOIN_BEVEL, 1, new float[] {2.0f, 2.0f }, 0);
        }
        if (starMap.getZoomLevel() == Tile.ZOOM_OUT1) {
          dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
              BasicStroke.JOIN_MITER, 1, new float[] {0.5f, 4.0f }, 0);
        }
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(dashed);
        gr.setColor(colorDarkBlue);
        boolean drawMapPos = false;
        if (routeData != null && routeData[i + cx][j + cy] > 0) {
          drawMapPos = true;
        }
        if (fullDraw || redrawTile[i + viewPointX][j + viewPointY]) {
          drawMapPos = true;
        }
        if (drawMapPos) {
          if (!fullDraw
              && pixelX + Tile.getMaxWidth(starMap.getZoomLevel())
              <= backgroundScreen.getWidth()
              && pixelY + Tile.getMaxHeight(starMap.getZoomLevel())
              <= backgroundScreen.getHeight()) {
            gr.drawImage(backgroundScreen.getSubimage(pixelX, pixelY,
                Tile.getMaxWidth(starMap.getZoomLevel()),
                Tile.getMaxHeight(starMap.getZoomLevel())), null, pixelX,
                pixelY);
          }
          if (i != viewPointX) {
            // Right line
            gr.drawLine(pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1,
                pixelY,
                pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1,
                pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1);
          }
          if (j != viewPointY) {
            // Bottom line
            gr.drawLine(pixelX,
                pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1,
                pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1,
                pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1);
          }
          if (info != null && info.getSectorVisibility(new Coordinate(i + cx,
              j + cy)) != PlayerInfo.UNCHARTED) {
            CulturePower culture = starMap.getSectorCulture(i + cx, j + cy);
            if (culture != null) {
              int index = culture.getHighestCulture();
              if (index != -1) {
                PlayerInfo cultureInfo = starMap.getPlayerByIndex(index);
                Tile tile = Tiles.getTileByName(
                    cultureInfo.getColor().getCultureTile(),
                    starMap.getZoomLevel());
                if (tile != null) {
                  tile.draw(gr, pixelX, pixelY);
                }
              }
            }
          }
          Planet planet = starMap.getPlanetByCoordinate(i + cx, j + cy);
          // Draw tile
          Tile tile = drawTile(gr, cx, cy, i, j, starMap, info, pixelX,
              pixelY, planet);
          Tile tile2 = starMap.getTile(i + cx - 1, j + cy);
          if (tile2 != null && (tile2.getName().equals(TileNames.SUN_E)
              || tile2.getName().equals(TileNames.BLUE_STAR_E)
              || tile2.getName().equals(TileNames.STAR_E))) {
            redrawTile[i + viewPointX][j + viewPointY] = false;
            if (i > -viewPointX) {
              redrawTile[i + viewPointX - 1][j + viewPointY] = true;
            }
          }
          if (tile == null) {
            continue;
          }
          // Draw fleet
          drawFleet(gr, fleetMap, cx, cy, i, j, starMap, info, pixelX, pixelY);

          // Draw fog of war and uncharted tiles
          drawFogOfWar(gr, cx, cy, i, j, starMap, info, pixelX, pixelY);

          // Draw Tile text
          drawTileText(gr, cx, cy, i, j, starMap, info, pixelX,
              pixelY, planet);

          if (routeData != null && routeData[i + cx][j + cy] == 1) {
            int offsetX = 0;
            int offsetY = 0;
            if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
              offsetX = 16;
              offsetY = 16;
            }
            redrawTile[i + viewPointX][j + viewPointY] = true;
            if (route.isDefending()) {
              gr.drawImage(Route.getDefenseDot(starMap.getZoomLevel()),
                  pixelX + offsetX,
                  pixelY + offsetY, null);
            } else if (route.isFixing()) {
              gr.drawImage(Route.getRepairDot(starMap.getZoomLevel()),
                  pixelX + offsetX,
                  pixelY + offsetY, null);
            } else if (route.isBombing()) {
              gr.drawImage(Route.getBombedDot(starMap.getZoomLevel()),
                  pixelX + offsetX,
                  pixelY + offsetY, null);
            } else if (route.isExploring()) {
              gr.drawImage(Route.getExploredDot(starMap.getZoomLevel()),
                  pixelX + offsetX,
                  pixelY + offsetY, null);
            } else {
              gr.drawImage(Route.getRouteDot(starMap.getZoomLevel()),
                  pixelX + offsetX,
                  pixelY + offsetY, null);
            }
          }
          if (routeData != null && routeData[i + cx][j + cy] == 2) {
            int offsetX = 0;
            int offsetY = 0;
            if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
              offsetX = 16;
              offsetY = 16;
            }
            gr.drawImage(Route.getGreenRouteDot(starMap.getZoomLevel()),
                pixelX + offsetX,
                pixelY + offsetY, null);
            redrawTile[i + viewPointX][j + viewPointY] = true;
          }
          if (routeData != null && routeData[i + cx][j + cy] == 3) {
            int offsetX = 0;
            int offsetY = 0;
            if (starMap.getZoomLevel() == Tile.ZOOM_IN) {
              offsetX = 16;
              offsetY = 16;
            }
            gr.drawImage(Route.getYellowRouteDot(starMap.getZoomLevel()),
                pixelX + offsetX,
                pixelY + offsetY, null);
            redrawTile[i + viewPointX][j + viewPointY] = true;
          }
        }
        // Draw the map cursor
        if (i + cx == starMap.getCursorX()
            && j + cy == starMap.getCursorY()) {
          redrawTile[i + viewPointX][j + viewPointY] = true;
          cursorDrawn = true;
          cursorPixelX = pixelX;
          cursorPixelY = pixelY;
          lastCursorIndexX = i + viewPointX;
          lastCursorIndexY = j + viewPointY;
          gr.setStroke(full);
          gr.setColor(colorFlickerBlue);
          // Top line
          gr.drawLine(pixelX, pixelY,
              pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1, pixelY);
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX,
              pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1);
          // Right line
          gr.drawLine(pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1,
              pixelY,
              pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1,
              pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1);
          // Bottom line
          gr.drawLine(pixelX,
              pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1,
              pixelX + Tile.getMaxWidth(starMap.getZoomLevel()) - 1,
              pixelY + Tile.getMaxHeight(starMap.getZoomLevel()) - 1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }
        // Old position
        if (i + cx == lastCursorPosX
            && j + cy == lastCursorPosY) {
          redrawTile[i + viewPointX][j + viewPointY] = true;
        }
        pixelX = pixelX + Tile.getMaxWidth(starMap.getZoomLevel());
      }
      pixelX = viewPointOffsetX;
      pixelY = pixelY + Tile.getMaxHeight(starMap.getZoomLevel());
    }
    lastCursorPosX = starMap.getCursorX();
    lastCursorPosY = starMap.getCursorY();
    if (isShowMiniMap() && minimap != null) {
      // Draw minimap itself
      BufferedImage miniMapImg = minimap.getDrawnImage();
      int redrawTilesX = miniMapImg.getWidth()
          / Tile.getMaxWidth(starMap.getZoomLevel()) + 1;
      int redrawTilesY = miniMapImg.getHeight()
          / Tile.getMaxHeight(starMap.getZoomLevel()) + 1;
      for (int i = redrawTile.length - redrawTilesX; i < redrawTile.length;
          i++) {
        for (int j = redrawTile[i].length - redrawTilesY;
            j < redrawTile[i].length; j++) {
          redrawTile[i][j] = true;
        }
      }
      // Calculate viewport rectangle
      int topX = screen.getWidth() - 10 - miniMapImg.getWidth();
      int topY = screen.getHeight() - 10 - miniMapImg.getHeight();
      int botX = topX + miniMapImg.getWidth();
      int botY = topY + miniMapImg.getHeight();
      miniMapTopX = topX;
      miniMapTopY = topY;
      miniMapBotX = botX;
      miniMapBotY = botY;
      gr.drawImage(miniMapImg, topX, topY, null);
      gr.setColor(GuiStatics.COLOR_GOLD_TRANS);
      Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
      gr.setStroke(full);
      int sectorSize = minimap.getSectorSize();
      int dx = minimap.getDrawPointX();
      int dy = minimap.getDrawPointY();
      int rtopX = topX + (starMap.getDrawX() - dx) * sectorSize
          - viewPointX * sectorSize;
      int rtopY = topY + (starMap.getDrawY() - dy) * sectorSize
          - viewPointY * sectorSize;
      int rbotX = topX + (starMap.getDrawX() - dx) * sectorSize
          + (viewPointX + 1) * sectorSize;
      int rbotY = topY + (starMap.getDrawY() - dy) * sectorSize
          + (viewPointY + 1) * sectorSize;
      // Limit rectangle and require update on minimap
      if (rtopX < topX) {
        rtopX = topX;
        minimap.updateMapX(-1);
      }
      if (rtopY < topY) {
        rtopY = topY;
        minimap.updateMapY(-1);
      }
      if (rbotX > botX) {
        rbotX = botX;
        minimap.updateMapX(1);
      }
      if (rbotY > botY) {
        rbotY = botY;
        minimap.updateMapY(1);
      }
      // Draw rectangle
      gr.drawLine(rtopX, rtopY, rbotX, rtopY);
      gr.drawLine(rtopX, rtopY, rtopX, rbotY);
      gr.drawLine(rbotX, rtopY, rbotX, rbotY);
      gr.drawLine(rtopX, rbotY, rbotX, rbotY);
    }
    if (cursorFocus > 0 && cursorDrawn) {
      cursorFocus--;
      if (lastCursorIndexY != -1 && lastCursorIndexX != -1) {
        for (int i = 0; i < redrawTile.length; i++) {
          redrawTile[i][lastCursorIndexY] = true;
        }
        for (int i = 0; i < redrawTile[lastCursorIndexX].length; i++) {
          redrawTile[lastCursorIndexX][i] = true;
        }
      }
      Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
      gr.setStroke(full);
      gr.setColor(new Color(255, 50, 50, cursorFocus * 5));
      gr.drawLine(viewPointOffsetX,
          cursorPixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2,
          cursorPixelX, cursorPixelY
          + Tile.getMaxHeight(starMap.getZoomLevel()) / 2);
      gr.drawLine(
          cursorPixelX + Tile.getMaxWidth(starMap.getZoomLevel()), cursorPixelY
          + Tile.getMaxHeight(starMap.getZoomLevel()) / 2,
          screen.getWidth() - viewPointOffsetX,
          cursorPixelY + Tile.getMaxHeight(starMap.getZoomLevel()) / 2);
      gr.drawLine(cursorPixelX + Tile.getMaxWidth(starMap.getZoomLevel()) / 2,
          viewPointOffsetY,
          cursorPixelX + Tile.getMaxWidth(starMap.getZoomLevel()) / 2,
          cursorPixelY);
      gr.drawLine(cursorPixelX + Tile.getMaxWidth(starMap.getZoomLevel()) / 2,
          cursorPixelY + Tile.getMaxHeight(starMap.getZoomLevel()),
          cursorPixelX + Tile.getMaxWidth(starMap.getZoomLevel()) / 2,
          screen.getHeight() - viewPointOffsetY);
    }
    updateAnimation = false;
    gr.dispose();
  }

  /**
   * Draw star map with history to Map panel
   * @param starMap Star map to draw
   */
  public void drawHistoryMap(final StarMap starMap) {
    lastZoomLevel = Tile.ZOOM_NORMAL;
    starMap.setZoomLevel(Tile.ZOOM_NORMAL);
    if (screen == null) {
      calculateViewPoints();
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
      } else {
        return;
      }
    }
    int cursorPixelX = 0;
    int cursorPixelY = 0;
    Color colorRed = null;
    int tileWidth = Tile.getMaxWidth(Tile.ZOOM_NORMAL);
    int tileHeight = Tile.getMaxHeight(Tile.ZOOM_NORMAL);
    Graphics2D gr = screen.createGraphics();
    // Center coordinates
    if (!historyCoordInitialized) {
      historyCoordX = starMap.getDrawX();
      historyCoordY = starMap.getDrawY();
      historyCoordInitialized = true;
    }
    int cx = historyCoordX;
    int cy = historyCoordY;
    starMap.setCursorPos(cx, cy);
    if (cx < viewPointX) {
      cx = viewPointX;
    }
    if (cx > starMap.getMaxX() - viewPointX - 1) {
      cx = starMap.getMaxX() - viewPointX - 1;
    }
    if (cy < viewPointY) {
      cy = viewPointY;
    }
    if (cy > starMap.getMaxY() - viewPointY - 1) {
      cy = starMap.getMaxY() - viewPointY - 1;
    }
    starMap.setDrawPos(cx, cy);
    // -20 for safety
    int speedX = (GuiStatics.NEBULAE_IMAGE.getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedY = (GuiStatics.NEBULAE_IMAGE.getHeight() - this.getHeight()
        - SAFETY_OFFSET) / starMap.getMaxY();
    if (speedX < 5) {
      speedX = 5;
    }
    if (speedY < 5) {
      speedY = 5;
    }
    int speedStarX = (GuiStatics.getStarField().getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedStarY = (GuiStatics.getStarField().getHeight() - this.getHeight()
        - SAFETY_OFFSET) / starMap.getMaxY();
    if (speedStarX < 3) {
      speedStarX = 3;
    }
    if (speedStarY < 3) {
      speedStarY = 3;
    }
    // Parallax Scrolling with just two lines!!!
    GraphRoutines.drawTiling(gr, GuiStatics.getStarField(),
        -PARALLAX_OFFSET - cx * speedStarX, -PARALLAX_OFFSET - cy * speedStarY,
        this.getWidth(), this.getHeight());
    GraphRoutines.drawTiling(gr, GuiStatics.NEBULAE_IMAGE,
        -PARALLAX_OFFSET - cx * speedX,
        -PARALLAX_OFFSET - cy * speedY,
        this.getWidth(), this.getHeight());

    lastDrawnCenterX = cx;
    lastDrawnCenterY = cy;
    int scaled = 16 * (flickerBlue - COLOR_COMPONENT_HALF)
        / COLOR_COMPONENT_DIVIDER;
    Color colorDarkBlue = new Color(0, FLICKER_GREEN + scaled,
        FLICKER_BLUE + scaled);
    Color colorFlickerBlue = new Color(0, 0, 16);
    if (flickerBlue < COLOR_COMPONENT_DIVIDER) {
      colorFlickerBlue = new Color(0, 0, flickerBlue);
      colorRed = new Color(flickerBlue, 0, 0);
    } else {
      int above = flickerBlue - COLOR_COMPONENT_DIVIDER;
      colorFlickerBlue = new Color(above, above, COLOR_COMPONENT_MAX);
      colorRed = new Color(COLOR_COMPONENT_MAX, above, above);
    }
    if (flickerGoUp) {
      flickerBlue = flickerBlue + 16;
    } else {
      flickerBlue = flickerBlue - 16;
    }
    if (flickerBlue > FLICKER_UPPER_LIMIT) {
      flickerGoUp = false;
    }
    if (flickerBlue < COLOR_COMPONENT_HALF) {
      flickerGoUp = true;
    }

    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    for (int j = -viewPointY; j < viewPointY + 1; j++) {
      for (int i = -viewPointX; i < viewPointX + 1; i++) {
        if (!starMap.isValidCoordinate(i + cx, j + cy)) {
          continue;
        }
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {0.1f, 4.5f }, 0);
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(dashed);
        gr.setColor(colorDarkBlue);
        if (i != viewPointX) {
          // Right line
          gr.drawLine(pixelX + tileWidth - 1, pixelY,
              pixelX + tileWidth - 1, pixelY + tileHeight - 1);
        }
        if (j != viewPointY) {
          // Bottom line
          gr.drawLine(pixelX, pixelY + tileHeight - 1,
              pixelX + tileWidth - 1, pixelY + tileHeight - 1);
        }
        Tile tile = starMap.getTile(i + cx, j + cy);
        if (tile == null) {
          continue;
        }
        if (tile.getName().equals(TileNames.ARTIFICIALWORLD1)
            && tileOverride != null) {
          tile = Tiles.getTileByIndex(tileOverride[i + cx][j + cy]);
        }
        if (historyCultures != null) {
          int index = historyCultures[i + cx][j + cy];
          if (index != -1) {
            PlayerInfo cultureInfo = starMap.getPlayerByIndex(index);
            Tile cultureTile = Tiles.getTileByName(
                cultureInfo.getColor().getCultureTile());
            if (cultureTile != null) {
              cultureTile.draw(gr, pixelX, pixelY);
            }
          }
        }
        if (tile.getAnimationIndex() != tile.getIndex()) {
          // Change map tile for next drawing
          starMap.setTile(i + cx, j + cy,
              Tiles.getTileByIndex(tile.getAnimationIndex()));
        }
        // Draw only non empty tiles
        if (!tile.getName().equals(TileNames.EMPTY)) {
          tile.draw(gr, pixelX, pixelY);
        }
        // Draw deep space anchor marker
        if (tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
            || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2)) {
          Icon16x16 icon = Icons.getIconByName(Icons.ICON_STARBASE);
          icon.draw(gr, pixelX + Icon16x16.MAX_WIDTH,
              pixelY + Icon16x16.MAX_HEIGHT);
        }
        Planet planet = starMap.getPlanetByCoordinate(i + cx, j + cy);
        if (planet != null && !planet.isGasGiant()
            && planet.getHomeWorldIndex() != -1) {
          Icon16x16 icon = Icons.getIconByName(Icons.ICON_CULTURE);
          icon.draw(gr, pixelX + Icon16x16.MAX_WIDTH,
              pixelY + Icon16x16.MAX_HEIGHT);
        }


        // Draw the map cursor
        if (i + cx == starMap.getCursorX() && j + cy == starMap.getCursorY()) {
          gr.setStroke(full);
          gr.setColor(colorFlickerBlue);
          cursorPixelX = pixelX;
          cursorPixelY = pixelY;
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX + tileWidth - 1, pixelY);
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX, pixelY + tileHeight - 1);
          // Right line
          gr.drawLine(pixelX + tileWidth - 1, pixelY,
              pixelX + tileWidth - 1, pixelY + tileHeight - 1);
          // Bottom line
          gr.drawLine(pixelX, pixelY + tileHeight - 1,
              pixelX + tileWidth - 1, pixelY + tileHeight - 1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }

        // Draw sun's text
        if ((tile.getName().equals(TileNames.SUN_E)
            || tile.getName().equals(TileNames.BLUE_STAR_E)
            || tile.getName().equals(TileNames.STAR_E))
            && i > -viewPointX + 1) {
          Sun sun = starMap.getSunByCoordinate(i + cx, j + cy);
          if (sun != null) {
            int textWidth = (int) GuiFonts.getFontCubellanSC()
                .getStringBounds(sun.getName(), gr.getFontRenderContext())
                .getWidth();
            int offset = tileWidth / 2 + textWidth / 2 - 2;
            gr.setStroke(GuiStatics.TEXT_LINE);
            gr.setColor(GuiStatics.COLOR_GOLD_TRANS);
            gr.drawLine(pixelX - offset, pixelY + tileHeight / 2 - 3,
                pixelX - tileWidth + offset,
                pixelY + tileHeight / 2 - 3);
            gr.setColor(Color.BLACK);
            gr.setFont(GuiFonts.getFontCubellanSC());
            gr.drawString(sun.getName(),
                pixelX - tileWidth / 2 - textWidth / 2,
                pixelY + tileHeight / 2);
          }
        }

        // Draw Gas giant text
        if ((tile.getName().equals(TileNames.GAS_GIANT_1_SE) && i > -viewPointX
            || tile.getName().equals(TileNames.GAS_GIANT_2_SE)
                && i > -viewPointX
            || tile.getName().equals(TileNames.GAS_GIANT_3_SE)
                && i > -viewPointX
            || tile.getName().equals(TileNames.JUPITER_SE)
                && i > -viewPointX
            || tile.getName().equals(TileNames.SATURN_SE)
                && i > -viewPointX
            || tile.getName().equals(TileNames.ICEGIANT1_SE)
                && i > -viewPointX
            || tile.getName().equals(TileNames.ICEGIANT2_SE)
                && i > -viewPointX)
            && planet != null) {
          int textWidth = (int) GuiFonts.getFontCubellanSC()
              .getStringBounds(RandomSystemNameGenerator.numberToRoman(
                  planet.getOrderNumber()), gr.getFontRenderContext())
              .getWidth();
          int offset = textWidth / 2 - 2;
          gr.setStroke(GuiStatics.TEXT_LINE);
          gr.setColor(GuiStatics.COLOR_GREYBLUE_NO_OPAGUE);
          gr.drawLine(pixelX - offset, pixelY - 3, pixelX + offset, pixelY - 3);
          gr.setColor(Color.BLACK);
          gr.setFont(GuiFonts.getFontCubellanSC());
          gr.drawString(
              RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
              pixelX - textWidth / 2, pixelY);
        }

        // Draw planet text
        if (planet != null && !planet.isGasGiant()
            && planet.getOrderNumber() != 0) {
          int textWidth = (int) GuiFonts.getFontCubellanSC()
              .getStringBounds(RandomSystemNameGenerator.numberToRoman(
                  planet.getOrderNumber()), gr.getFontRenderContext())
              .getWidth();
          int offset = tileWidth / 2 - textWidth / 2 - 2;
          gr.setStroke(GuiStatics.TEXT_LINE);
          gr.setColor(GuiStatics.COLOR_GREYBLUE);
          gr.drawLine(pixelX + offset, pixelY + tileHeight / 2 - 3,
              pixelX + tileWidth - offset,
              pixelY + tileHeight / 2 - 3);
          gr.setColor(Color.BLACK);
          gr.setFont(GuiFonts.getFontCubellanSC());
          gr.drawString(
              RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
              pixelX + tileWidth / 2 - textWidth / 2,
              pixelY + tileHeight / 2);
        }
        pixelX = pixelX + tileWidth;
      }
      pixelX = viewPointOffsetX;
      pixelY = pixelY + tileHeight;
    }
    Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
        BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
    gr.setStroke(full);
    gr.setColor(colorRed);
    gr.drawLine(0, cursorPixelY + tileHeight / 2, cursorPixelX,
        cursorPixelY + tileHeight / 2);
    gr.drawLine(cursorPixelX + tileWidth, cursorPixelY + tileHeight / 2,
        screen.getWidth(), cursorPixelY + tileHeight / 2);
    gr.drawLine(cursorPixelX + tileWidth / 2, 0, cursorPixelX + tileWidth / 2,
        cursorPixelY);
    gr.drawLine(cursorPixelX + tileWidth / 2, cursorPixelY + tileHeight,
        cursorPixelX + tileWidth / 2, screen.getHeight());
    if (getLeftSpaceImage() != null) {
      gr.drawImage(leftSpaceImage, 10,
          screen.getHeight() - leftSpaceImage.getHeight() - 10,
          null);
    }
    if (getRightSpaceImage() != null) {
      gr.drawImage(rightSpaceImage,
          screen.getWidth() - rightSpaceImage.getWidth() - 10,
          screen.getHeight() - leftSpaceImage.getHeight() - 10,
          null);
    }
    gr.dispose();
  }

  /**
   * Draw battle map to Map panel
   * @param combat to draw on map panel
   * @param info PlayerInfo
   * @param starMap StarMap
   */
  public void drawBattleMap(final Combat combat, final PlayerInfo info,
      final StarMap starMap) {
    lastZoomLevel = Tile.ZOOM_NORMAL;
    transparency = transparency * 2;
    if (transparency >= 160) {
      transparency = 32;
    }
    wormHoleAnimation++;
    if (wormHoleAnimation > GuiStatics.WORMHOLE.getMaxFrames() - 1) {
      wormHoleAnimation = 0;
    }
    if (screen == null) {
      calculateViewPoints();
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
      } else {
        return;
      }
    }
    Graphics2D gr = screen.createGraphics();
    // Center coordinates, to match star map background
    int cx = starMap.getDrawX();
    int cy = starMap.getDrawY();

    // -20 for safety
    int speedStarX = (GuiStatics.getStarField().getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedStarY = (GuiStatics.getStarField().getHeight()
        - this.getHeight() - SAFETY_OFFSET) / starMap.getMaxY();
    GraphRoutines.drawTiling(gr, GuiStatics.getStarField(),
        -PARALLAX_OFFSET - cx * speedStarX, -PARALLAX_OFFSET - cy * speedStarY,
        this.getWidth(), this.getHeight());
    if (combat.getPlanet() != null && !combat.getPlanet().isGasGiant()) {
      BufferedImage backgroundImg = combat.getPlanet().getBigImage();
      gr.drawImage(backgroundImg, 100 - backgroundImg.getWidth() / 25,
          100 - backgroundImg.getHeight() / 25, null);
    }

    int scaled = 16 * (flickerBlue - COLOR_COMPONENT_HALF)
        / COLOR_COMPONENT_MAX;
    Color colorDarkBlue = new Color(0, FLICKER_GREEN + scaled,
        FLICKER_BLUE + scaled);
    Color colorFlickerBlue = new Color(0, 0, 16);
    if (flickerBlue < COLOR_COMPONENT_DIVIDER) {
      colorFlickerBlue = new Color(0, 0, flickerBlue);
    } else {
      int above = flickerBlue - COLOR_COMPONENT_DIVIDER;
      colorFlickerBlue = new Color(above, above, COLOR_COMPONENT_MAX);
    }
    if (flickerGoUp) {
      flickerBlue = flickerBlue + 16;
    } else {
      flickerBlue = flickerBlue - 16;
    }
    if (flickerBlue > FLICKER_UPPER_LIMIT) {
      flickerGoUp = false;
    }
    if (flickerBlue < COLOR_COMPONENT_HALF) {
      flickerGoUp = true;
    }

    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    lastDrawnCenterX = (Combat.MAX_X - 1) / 2;
    lastDrawnCenterY = (Combat.MAX_Y - 1) / 2;
    viewPointX = (Combat.MAX_X - 1) / 2;
    viewPointY = (Combat.MAX_Y - 1) / 2;
    for (int j = 0; j < Combat.MAX_Y; j++) {
      for (int i = 0; i < Combat.MAX_X; i++) {
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {0.1f, 4.5f }, 0);
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        if (i == combat.getCursorX() && j == combat.getCursorY()) {
          gr.setStroke(full);
          gr.setColor(colorFlickerBlue);
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX + ShipImage.MAX_WIDTH - 1,
              pixelY);
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX,
              pixelY + ShipImage.MAX_HEIGHT - 1);
          // Right line
          gr.drawLine(pixelX + ShipImage.MAX_WIDTH - 1, pixelY,
              pixelX + ShipImage.MAX_WIDTH - 1,
              pixelY + ShipImage.MAX_HEIGHT - 1);
          // Bottom line
          gr.drawLine(pixelX, pixelY + ShipImage.MAX_HEIGHT - 1,
              pixelX + ShipImage.MAX_WIDTH - 1,
              pixelY + ShipImage.MAX_HEIGHT - 1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }
        if (combat.getCurrentShip() != null
            && i == combat.getCurrentShip().getX()
            && j == combat.getCurrentShip().getY()) {
          gr.setStroke(full);
          gr.setColor(GuiStatics.getInfoTextColor());
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX + ShipImage.MAX_WIDTH - 1, pixelY);
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX,
              pixelY + ShipImage.MAX_HEIGHT - 1);
          // Right line
          gr.drawLine(pixelX + ShipImage.MAX_WIDTH - 1, pixelY,
              pixelX + ShipImage.MAX_WIDTH - 1,
              pixelY + ShipImage.MAX_HEIGHT - 1);
          // Bottom line
          gr.drawLine(pixelX, pixelY + ShipImage.MAX_HEIGHT - 1,
              pixelX + ShipImage.MAX_WIDTH - 1,
              pixelY + ShipImage.MAX_HEIGHT - 1);
        }
        if (combat.getCurrentShip() != null && drawWeaponRange) {
          CombatShip combatShip = combat.getCurrentShip();
          int min = combatShip.getShip().getMinWeaponRange();
          int max = combatShip.getShip().getMaxWeaponRange();
          if (min < Ship.MAX_WEAPON_RANGE && max > 0) {
            int disX = Math.abs(i - combatShip.getX());
            int disY = Math.abs(j - combatShip.getY());
            if (disX <= min && disY <= min) {
              gr.setColor(GuiStatics.COLOR_WEAPON_RANGE_MIN);
              gr.fillRect(pixelX, pixelY, ShipImage.MAX_WIDTH,
                  ShipImage.MAX_HEIGHT);
            } else if (disX <= max && disY <= max) {
              gr.setColor(GuiStatics.COLOR_WEAPON_RANGE_MAX);
              gr.fillRect(pixelX, pixelY, ShipImage.MAX_WIDTH,
                  ShipImage.MAX_HEIGHT);
            }
          }
        }
        gr.setStroke(dashed);
        gr.setColor(colorDarkBlue);
        if (i == 0) {
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX,
              pixelY + ShipImage.MAX_HEIGHT - 1);
        }
        if (j == 0) {
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX + ShipImage.MAX_WIDTH - 1, pixelY);
        }
        // Right line
        gr.drawLine(pixelX + ShipImage.MAX_WIDTH - 1, pixelY,
            pixelX + ShipImage.MAX_WIDTH - 1,
            pixelY + ShipImage.MAX_HEIGHT - 1);
        // Bottom line
        gr.drawLine(pixelX, pixelY + ShipImage.MAX_HEIGHT - 1,
            pixelX + ShipImage.MAX_WIDTH - 1,
            pixelY + ShipImage.MAX_HEIGHT - 1);
        // Worm hole
        Coordinate wormHoleCoordinate = combat.getWormHoleCoordinate();
        if (wormHoleCoordinate != null
            && i == wormHoleCoordinate.getX()
            && j == wormHoleCoordinate.getY()) {
          BufferedImage wormHole = GuiStatics.WORMHOLE.getFrame(
              wormHoleAnimation);
          gr.drawImage(wormHole, pixelX, pixelY, null);
        }
        // Draw fleet
        CombatShip ship = combat.getShipFromCoordinate(i, j);
        if (ship != null) {
          BufferedImage img = ShipImages
              .getByRace(ship.getShip().getHull().getRace())
              .getShipImage(ship.getShip().getHull().getImageIndex());
          if (ship.isCloakOverloaded()) {
            img = GraphRoutines.greyTransparent(img, transparency);
          } else if (ship.isCloaked()) {
            img = GraphRoutines.transparent(img, transparency);
          }
          if (ship.isFlipY()) {
            AffineTransform at = AffineTransform.getScaleInstance(1, -1);
            at.translate(0, -img.getHeight(null));
            AffineTransformOp ato = new AffineTransformOp(at,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage flipImg = ato.filter(img, null);
            gr.drawImage(flipImg, pixelX, pixelY, null);
          } else {
            gr.drawImage(img, pixelX, pixelY, null);
          }
        }

        if (i == combat.getCursorX() && j == combat.getCursorY()
            && combat.getCurrentShip() != null) {
          ShipComponent weapon = combat.getCurrentShip().getShip()
              .getComponent(combat.getComponentUse());
          if (combat.getComponentUse() != -1 && weapon != null
              && (weapon.isWeapon() || weapon.isPrivateer()
              || weapon.isTractor())) {
            CombatShip target = combat.getShipFromCoordinate(
                combat.getCursorX(), combat.getCursorY());
            if (target != null && combat.getCurrentShip().getPlayer().isHuman()
                && (combat.isClearShot(combat.getCurrentShip(), target)
                   || combat.canPrivateer(combat.getCurrentShip(), target)
                   || combat.canTractor(combat.getCurrentShip(), target))) {
              gr.drawImage(GuiStatics.CROSSHAIR, pixelX, pixelY, null);
              int accuracy = combat.calculateAccuracy(combat.getCurrentShip(),
                  weapon, target);
              String accuracyStr = accuracy + "%";
              if (accuracy == -1) {
                accuracyStr = "NO LOCK";
              }
              int textWidth = (int) GuiFonts.getFontCubellan()
                  .getStringBounds(accuracyStr, gr.getFontRenderContext())
                  .getWidth();
              gr.setColor(GuiStatics.getCoolSpaceColorDarker());
              int offsetY = 3;
              int offsetX = 2;
              gr.drawString(accuracyStr,
                  pixelX + ShipImage.MAX_WIDTH / 2 - textWidth / 2 + offsetX
                  - 1, pixelY + ShipImage.MAX_HEIGHT / 2 + offsetY);
              gr.drawString(accuracyStr,
                  pixelX + ShipImage.MAX_WIDTH / 2 - textWidth / 2 + offsetX
                  + 1, pixelY + ShipImage.MAX_HEIGHT / 2 + offsetY);
              gr.drawString(accuracyStr,
                  pixelX + ShipImage.MAX_WIDTH / 2 - textWidth / 2 + offsetX,
                  pixelY + ShipImage.MAX_HEIGHT / 2 + offsetY + 1);
              gr.drawString(accuracyStr,
                  pixelX + ShipImage.MAX_WIDTH / 2 - textWidth / 2 + offsetX,
                  pixelY + ShipImage.MAX_HEIGHT / 2 + offsetY - 1);
              gr.setColor(GuiStatics.getCoolSpaceColor());
              gr.drawString(accuracyStr,
                  pixelX + ShipImage.MAX_WIDTH / 2 - textWidth / 2 + offsetX,
                  pixelY + ShipImage.MAX_HEIGHT / 2 + offsetY);
            } else {
              gr.drawImage(GuiStatics.RED_CROSSHAIR, pixelX, pixelY, null);
            }
          }
        }

        pixelX = pixelX + ShipImage.MAX_WIDTH;
      }
      pixelX = viewPointOffsetX;
      pixelY = pixelY + ShipImage.MAX_HEIGHT;
    }
    if (combat.getAnimation() != null) {
      CombatAnimation anim = combat.getAnimation();
      if (anim.isFirstDraw()) {
        anim.setFirstDraw(false);
        switch (anim.getType()) {
        case LASER_BEAM:
        case PHASOR_BEAM:
        case ANTIMATTER_BEAM: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_BEAM);
          break;
          }
        case PHOTON_TORPEDO: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_TORPEDO);
          break;
        }
        case PLASMA_CANNON: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_PLASMA_CANNON);
          break;
        }
        case ION_CANNON: {
          SoundPlayer.playSound(SoundPlayer.ION_CANNON);
          break;
          }
        case RAILGUN: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_RAILGUN);
          break;
          }
        case MULTICANNON: {
          SoundPlayer.playSound(SoundPlayer.MACHINEGUN);
          break;
          }
        case GRAVITY_RIPPER: {
          SoundPlayer.playSound(SoundPlayer.GRAVITY_RIPPER);
          break;
          }
        case ECM_TORPEDO:
        case HE_MISSILE: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_MISSILE);
          break;
          }
        case PRIVATEERING: {
          SoundPlayer.playSound(SoundPlayer.ALARM);
          break;
          }
        case LIGHTNING: {
          SoundPlayer.playSound(SoundPlayer.ELECTRIC);
          break;
          }
        case SCANNING: {
          SoundPlayer.playSound(SoundPlayer.SCANNER_OVERLOAD);
          break;
          }
        case SHIELD: {
          SoundPlayer.playShieldSound();
          break;
          }
        case EXPLOSION: {
          SoundPlayer.playSound(SoundPlayer.EXPLOSION);
          break;
          }
        case TRACTOR_BEAM: {
          SoundPlayer.playSound(SoundPlayer.TRACTORBEAM);
          break;
          }
        case BITE: {
          SoundPlayer.playSound(SoundPlayer.BITE);
          break;
          }
        case TENTACLE: {
          SoundPlayer.playSound(SoundPlayer.TENTACLE);
          break;
          }
        default: {
          ErrorLogger.log("Unexpected weapon type, sound effect is missing!");
        }
        }
      }

      if (anim.getType() == CombatAnimationType.ANTIMATTER_BEAM
          || anim.getType() == CombatAnimationType.LASER_BEAM
          || anim.getType() == CombatAnimationType.PHASOR_BEAM) {
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(full);
        gr.setColor(anim.getBeamColor());
        gr.drawLine(anim.getSx() + viewPointOffsetX,
            anim.getSy() + viewPointOffsetY, anim.getEx() + viewPointOffsetX,
            anim.getEy() + viewPointOffsetY);
      } else if (anim.getType() == CombatAnimationType.PHOTON_TORPEDO) {
        gr.drawImage(GuiStatics.PHOTON_TORPEDO,
            anim.getSx() + viewPointOffsetX
                - GuiStatics.PHOTON_TORPEDO.getWidth() / 2,
            anim.getSy() + viewPointOffsetY
                - GuiStatics.PHOTON_TORPEDO.getHeight() / 2,
            null);
      } else if (anim.getType() == CombatAnimationType.TENTACLE) {
        int tx = anim.getShooter().getX() * ShipImage.MAX_WIDTH
            + ShipImage.MAX_WIDTH / 2;
        int ty = anim.getShooter().getY() * ShipImage.MAX_HEIGHT
            + ShipImage.MAX_HEIGHT / 2;
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(full);
        gr.setColor(GuiStatics.TENTACLE_COLOR_1);
        gr.drawLine(tx + viewPointOffsetX,
            ty + viewPointOffsetY, anim.getSx() + viewPointOffsetX,
            anim.getSy() + viewPointOffsetY);
        Stroke dots = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_BEVEL, 1.0f, new float[] {2.0f, 9.0f }, 0.0f);
        gr.setStroke(dots);
        gr.setColor(GuiStatics.TENTACLE_COLOR_2);
        gr.drawLine(tx + viewPointOffsetX,
            ty + viewPointOffsetY, anim.getSx() + viewPointOffsetX,
            anim.getSy() + viewPointOffsetY);
      } else if (anim.getType() == CombatAnimationType.PLASMA_CANNON) {
        gr.drawImage(GuiStatics.PLASMA_BULLET,
            anim.getSx() + viewPointOffsetX
                - GuiStatics.PLASMA_BULLET.getWidth() / 2,
            anim.getSy() + viewPointOffsetY
                - GuiStatics.PLASMA_BULLET.getHeight() / 2,
            null);
      } else if (anim.getType() == CombatAnimationType.ION_CANNON) {
        Stroke ionBeam = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {0.1f, 4.5f },
            transparency / 100);
        gr.setStroke(ionBeam);
        gr.setColor(anim.getBeamColor());
        gr.drawLine(anim.getSx() + viewPointOffsetX,
            anim.getSy() + viewPointOffsetY, anim.getEx() + viewPointOffsetX,
            anim.getEy() + viewPointOffsetY);
      } else if (anim.getType() == CombatAnimationType.TRACTOR_BEAM) {
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(full);
        for (int beam = 0; beam < 10; beam++) {
          gr.setColor(new Color(133 - beam * 3, 179 - beam * 4, 255,
              30 + beam * 6));
          int ox = DiceGenerator.getRandom(ShipImage.MAX_WIDTH / 2);
          int oy = DiceGenerator.getRandom(ShipImage.MAX_HEIGHT / 2);
          if (DiceGenerator.getBoolean()) {
            ox = ox * -1;
          }
          if (DiceGenerator.getBoolean()) {
            oy = oy * -1;
          }
          gr.drawLine(anim.getSx() + viewPointOffsetX,
              anim.getSy() + viewPointOffsetY,
              anim.getEx() + viewPointOffsetX + ox,
              anim.getEy() + viewPointOffsetY + oy);
        }
      } else if (anim.getType() == CombatAnimationType.ECM_TORPEDO
          || anim.getType() == CombatAnimationType.HE_MISSILE
          || anim.getType() == CombatAnimationType.RAILGUN) {
        gr.setColor(GuiStatics.COLOR_GREY_160);
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(full);
        gr.drawLine(anim.getSx() + viewPointOffsetX,
            anim.getSy() + viewPointOffsetY,
            anim.getSx() + viewPointOffsetX + 1,
            anim.getSy() + viewPointOffsetY + 1);
      }
      if (anim.getAnimFrame() != null) {
        gr.drawImage(anim.getAnimFrame(),
            anim.getEx() + viewPointOffsetX
                - anim.getAnimFrame().getWidth() / 2,
            anim.getEy() + viewPointOffsetY
                - anim.getAnimFrame().getHeight() / 2,
            null);
      }
      if (anim.getShieldAnimFrame() != null) {
        gr.drawImage(anim.getShieldAnimFrame(),
            anim.getEx() + viewPointOffsetX
                - anim.getShieldAnimFrame().getWidth() / 2,
            anim.getEy() + viewPointOffsetY
                - anim.getShieldAnimFrame().getHeight() / 2,
            null);
      }
      List<ParticleEffect> particles = anim.getParticles();
      for (ParticleEffect part : particles) {
        int px = part.getX() + viewPointOffsetX;
        int py = part.getY() + viewPointOffsetY;
        if (px >= 0 && py >= 0 && px < screen.getWidth()
            && py < screen.getHeight()) {
          screen.setRGB(part.getX() + viewPointOffsetX,
              part.getY() + viewPointOffsetY, part.getColor().getRGB());
        }
      }
      anim.doAnimation();
      if (anim.isAnimationFinished()) {
        if (anim.getTarget().getShip().getHullPoints() <= 0) {
          // Ship has no more hull points so destroying it
          combat.destroyShip(anim.getTarget());
          if (combat.isCombatOver()) {
            combat.handleEndCombat();
          }
        }
        combat.setAnimation(null);
      }
    }
    gr.dispose();
  }

  /**
   * Get Last Drawn X coordinate
   * @return X coordinate
   */
  public int getLastDrawnX() {
    return lastDrawnCenterX;
  }

  /**
   * Get Last Drawn Y coordinate
   * @return Y coordinate
   */
  public int getLastDrawnY() {
    return lastDrawnCenterY;
  }

  /**
   * Get offset for X axel in pixel
   * @return Offset for x axel in pixel
   */
  public int getOffsetX() {
    return viewPointOffsetX;
  }

  /**
   * Get offset for Y axel in pixel
   * @return Offset for Y axel in pixel
   */
  public int getOffsetY() {
    return viewPointOffsetY;
  }

  /**
   * Get view point size in X axel. View point is half of the total screen
   * width in tiles.
   * @return View point x size
   */
  public int getViewPointX() {
    return viewPointX;
  }

  /**
   * Get view point size in Y axel. View point is half of the total screen
   * width in tiles.
   * @return View point y size
   */
  public int getViewPointY() {
    return viewPointY;
  }

  /**
   * Is there route visible on map
   * @return Route for drawing
   */
  public Route getRoute() {
    return route;
  }

  /**
   * Set new route to draw on top of map
   * @param route to draw
   */
  public void setRoute(final Route route) {
    this.route = route;
  }

  /**
   * set historical culture information.
   * @param culture as integer array
   */
  public void setHistoryCultures(final int[][] culture) {
    historyCultures = culture;
    panelType = MapPanelType.HISTORY;
  }

  /**
   * Move history view towards targeted decomposed Coordinate.
   * @param targetX Coordinate's X
   * @param targetY Coordinate's Y
   */
  public void moveHistoryTowards(final int targetX, final int targetY) {
    if (targetX > historyCoordX) {
      historyCoordX++;
    } else if (targetX < historyCoordX) {
      historyCoordX--;
    }
    if (targetY > historyCoordY) {
      historyCoordY++;
    } else if (targetY < historyCoordY) {
      historyCoordY--;
    }
  }

  /**
   * set override tiles for history map drawing
   * @param overrideTiles as integer array
   */
  public void setTileOverride(final int[][] overrideTiles) {
    this.tileOverride = overrideTiles;
  }

  /**
   * Get Left Space image from history map
   * @return the leftSpaceImage
   */
  public BufferedImage getLeftSpaceImage() {
    return leftSpaceImage;
  }

  /**
   * Set Left Space image to history map
   * @param leftSpaceImage the leftSpaceImage to set
   */
  public void setLeftSpaceImage(final BufferedImage leftSpaceImage) {
    this.leftSpaceImage = leftSpaceImage;
  }

  /**
   * Get Right Space image from history map
   * @return the rightSpaceImage
   */
  public BufferedImage getRightSpaceImage() {
    return rightSpaceImage;
  }

  /**
   * Set Right Space image to history map
   * @param rightSpaceImage the rightSpaceImage to set
   */
  public void setRightSpaceImage(final BufferedImage rightSpaceImage) {
    this.rightSpaceImage = rightSpaceImage;
  }

  /**
   * Set popup on top of map
   * @param popup PopupPanel
   */
  public void setPopup(final PopupPanel popup) {
    this.popup = popup;
  }

  /**
   * Get popup panel.
   * @return PopupPanel or null
   */
  public PopupPanel getPopup() {
    return popup;
  }

  /**
   * Get cursor focus value
   * @return the cursorFocus
   */
  public int getCursorFocus() {
    return cursorFocus;
  }

  /**
   * Set cursor focus value
   * @param cursorFocus the cursorFocus to set
   */
  public void setCursorFocus(final int cursorFocus) {
    if (cursorFocus > 50) {
      this.cursorFocus = 50;
    } else {
      this.cursorFocus = cursorFocus;
    }
  }

  /**
   * Is minimap visible or not
   * @return the showMiniMap
   */
  public boolean isShowMiniMap() {
    return showMiniMap;
  }

  /**
   * Set flag for showing minimap
   * @param showMiniMap the showMiniMap to set
   */
  public void setShowMiniMap(final boolean showMiniMap) {
    this.showMiniMap = showMiniMap;
  }

  /**
   * Is draw weapon range enabled.
   * This only affects combat drawing.
   * @return boolean
   */
  public boolean isDrawWeaponRange() {
    return drawWeaponRange;
  }

  /**
   * Set Draw weapon range. This only affects to combat drawing.
   * @param drawWeaponRange True to draw weapon range.
   */
  public void setDrawWeaponRange(final boolean drawWeaponRange) {
    this.drawWeaponRange = drawWeaponRange;
  }
  /**
   * Get minimap Top corner's x coordinate.
   * @return X coordinate
   */
  public int getMiniMapTopX() {
    return miniMapTopX;
  }
  /**
   * Get minimap Top corner's y coordinate.
   * @return Y coordinate
   */
  public int getMiniMapTopY() {
    return miniMapTopY;
  }
  /**
   * Get minimap Bottom corner's x coordinate.
   * @return X coordinate
   */
  public int getMiniMapBotX() {
    return miniMapBotX;
  }
  /**
   * Get minimap Bottom corner's Y coordinate.
   * @return Y coordinate
   */
  public int getMiniMapBotY() {
    return miniMapBotY;
  }

  /**
   * Get minimap.
   * @return Minimap
   */
  public Minimap getMinimap() {
    return minimap;
  }

  /**
   * Get Panel type.
   * @return MapPanelType
   */
  public MapPanelType getPanelType() {
    return panelType;
  }

  /**
   * Get last zoom level.
   * @return Zoom level
   */
  public int getLastZoomLevel() {
    return lastZoomLevel;
  }

  /**
   * Set redo viewpoints.
   */
  public void redoViewPoints() {
    redoViewPoints = true;
  }
}