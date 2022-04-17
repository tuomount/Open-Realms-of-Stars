package org.openRealmOfStars.gui.mapPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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
import org.openRealmOfStars.gui.mapPanel.Minimap.Minimap;
import org.openRealmOfStars.gui.utilies.GraphRoutines;
import org.openRealmOfStars.gui.utilies.GuiStatics;
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
 *
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
   * Where the map is actually drawn
   */
  private BufferedImage screen;

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

  /**
   * History map coordinate
   */
  private Coordinate historyCoordinates;

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
   * Draw weapon range for combat.
   */
  private boolean drawWeaponRange;
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
    historyCoordinates = null;
    setShowMiniMap(false);
    tileOverride = null;
    improvedParallax = false;
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
    if (battle) {
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
    calculateViewPoints(-1, -1);
  }

  /**
   * Calculate view according the actual panel size;
   * @param mapX Map max x size
   * @param mapY Map max y size
   */
  protected void calculateViewPoints(final int mapX, final int mapY) {
    int tileWidth = Tile.MAX_WIDTH;
    int tileHeight = Tile.MAX_HEIGHT;
    if (battle) {
      // Combat does not have scroll screen
      viewPointX = (Combat.MAX_X - 1) / 2;
      viewPointY = (Combat.MAX_Y - 1) / 2;
    } else {
      viewPointX = (this.getWidth() / tileWidth - 1) / 2;
      viewPointY = (this.getHeight() / tileHeight - 1) / 2;
      if (viewPointX * 2 + 1 > mapX && mapX != -1) {
        viewPointX = (mapX - 1) / 2;
      }
      if (viewPointY * 2 + 1 > mapY && mapY != -1) {
        viewPointY = (mapY - 1) / 2;
      }
    }
    if (viewPointX < 1) {
      viewPointX = 1;
    }
    if (viewPointY < 1) {
      viewPointY = 1;
    }
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
   * Update black hole effect while drawing the map
   * @param pixelX Pixel coordinate for X
   * @param pixelY Pixel coordinate for Y
   * @param i Map position in X coordinate
   * @param j Map position in Y coordinate
   * @param tile Black hole tile
   */
  public void updateBlackHoleEffect(final int pixelX, final int pixelY,
      final int i, final int j, final Tile tile) {
    int safePixelX = pixelX;
    int safePixelY = pixelY;
    if (safePixelX - Tile.MAX_WIDTH < 0) {
      safePixelX = Tile.MAX_WIDTH;
    }
    if (safePixelX + Tile.MAX_WIDTH > screen.getWidth()) {
      safePixelX = screen.getWidth() - Tile.MAX_WIDTH;
    }
    if (safePixelY - Tile.MAX_HEIGHT < 0) {
      safePixelY = Tile.MAX_HEIGHT;
    }
    if (safePixelY + Tile.MAX_HEIGHT > screen.getHeight()) {
      safePixelY = screen.getHeight() - Tile.MAX_HEIGHT;
    }
    if (tile.getName() == TileNames.BLACKHOLE_NW
        && j + 1 < viewPointY && i + 1 < viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX + Tile.MAX_WIDTH,
          safePixelY + Tile.MAX_HEIGHT, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_N
        && j + 1 < viewPointY && i < viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX,
          safePixelY + Tile.MAX_HEIGHT, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_NE
        && j + 1 < viewPointY && i - 1 >= -viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX - Tile.MAX_WIDTH,
          safePixelY + Tile.MAX_HEIGHT, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_W
        && j < viewPointY && i < viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX + Tile.MAX_WIDTH,
          safePixelY, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_E
        && j < viewPointY && i + 1 < viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX - Tile.MAX_WIDTH,
          safePixelY, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_SW
        && j - 1  >= -viewPointY && i + 1 < viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX + Tile.MAX_WIDTH,
          safePixelY - Tile.MAX_HEIGHT, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_SW
        && j - 1  >= -viewPointY && i < viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX,
          safePixelY - Tile.MAX_HEIGHT, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else if (tile.getName() == TileNames.BLACKHOLE_SE
        && j - 1  >= -viewPointY && i - 1 >= -viewPointX) {
      BufferedImage tmp = screen.getSubimage(safePixelX - Tile.MAX_WIDTH,
          safePixelY - Tile.MAX_HEIGHT, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    } else {
      BufferedImage tmp = screen.getSubimage(safePixelX,
          safePixelY, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
      Tile.updateBlackHoleEffect(tmp);
    }
  }
  /**
   * Draw star map to Map panel
   * @param starMap Star map to draw
   */
  public void drawMap(final StarMap starMap) {
    PlayerInfo info = starMap.getCurrentPlayerInfo();
    if (updateAnimation) {
      updateAnimation = false;
    } else {
      updateAnimation = true;
    }
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
    if (screen == null) {
      calculateViewPoints(starMap.getMaxX(), starMap.getMaxY());
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
      } else {
        return;
      }
    }
    boolean blackholeUpdated = false;
    byte[][] routeData = null;
    if (route != null) {
      routeData = route.getRouteOnMap(starMap.getMaxX(), starMap.getMaxY());
    }
    Graphics2D gr = screen.createGraphics();
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
    if (!improvedParallax) {
      GraphRoutines.drawTiling(gr, GuiStatics.getStarNebulae(),
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
    }

    lastDrawnCenterX = cx;
    lastDrawnCenterY = cy;
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

    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    for (int j = -viewPointY; j < viewPointY + 1; j++) {
      for (int i = -viewPointX; i < viewPointX + 1; i++) {
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {0.1f, 4.5f }, 0);
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(dashed);
        gr.setColor(colorDarkBlue);
        Tile blackholeTile = starMap.getTile(i + cx, j + cy);
        if (blackholeTile != null && blackholeTile.isBlackhole()
            && !blackholeUpdated) {
          updateBlackHoleEffect(pixelX, pixelY, i, j, blackholeTile);
          blackholeUpdated = true;
        }
        if (i != viewPointX) {
          // Right line
          gr.drawLine(pixelX + Tile.MAX_WIDTH - 1, pixelY,
              pixelX + Tile.MAX_WIDTH - 1, pixelY + Tile.MAX_HEIGHT - 1);
        }
        if (j != viewPointY) {
          // Bottom line
          gr.drawLine(pixelX, pixelY + Tile.MAX_HEIGHT - 1,
              pixelX + Tile.MAX_WIDTH - 1, pixelY + Tile.MAX_HEIGHT - 1);
        }
        if (info != null && info.getSectorVisibility(new Coordinate(i + cx,
            j + cy)) != PlayerInfo.UNCHARTED) {
          CulturePower culture = starMap.getSectorCulture(i + cx, j + cy);
          if (culture != null) {
            int index = culture.getHighestCulture();
            if (index != -1) {
              PlayerInfo cultureInfo = starMap.getPlayerByIndex(index);
              Tile tile = Tiles.getTileByName(
                  cultureInfo.getColor().getCultureTile());
              if (tile != null) {
                tile.draw(gr, pixelX, pixelY);
              }
            }
          }
        }
        Tile tile = starMap.getTile(i + cx, j + cy);
        if (tile == null) {
          continue;
        }
        if (tile.getAnimationIndex() != tile.getIndex() && updateAnimation) {
          // Change map tile for next drawing
          starMap.setTile(i + cx, j + cy,
              Tiles.getTileByIndex(tile.getAnimationIndex()));
        }
        // Draw only non empty tiles
        if (info != null && !tile.getName().equals(TileNames.EMPTY)
            && info.getSectorVisibility(new Coordinate(i + cx,
                j + cy)) != PlayerInfo.UNCHARTED
            || starMap.getTileInfo(i + cx, j + cy)
                .getType() == SquareInfo.TYPE_SUN
            || starMap.getTileInfo(i + cx, j + cy)
                .getType() == SquareInfo.TYPE_BLACKHOLE_CENTER) {
          tile.draw(gr, pixelX, pixelY);
        }

        // Draw home world marker
        Planet planet = starMap.getPlanetByCoordinate(i + cx, j + cy);
        if (planet != null && !planet.isGasGiant() && info != null
            && info.getSectorVisibility(new Coordinate(i + cx,
                j + cy)) != PlayerInfo.UNCHARTED
            && planet.getHomeWorldIndex() != -1) {
          Icon16x16 icon = Icons.getIconByName(Icons.ICON_CULTURE);
          icon.draw(gr, pixelX + Icon16x16.MAX_WIDTH,
              pixelY + Icon16x16.MAX_HEIGHT);
        }

        // Draw deep space anchor marker
        if ((tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
            || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2))
            && info != null && info.getSectorVisibility(new Coordinate(i + cx,
                j + cy)) != PlayerInfo.UNCHARTED) {
          Icon16x16 icon = Icons.getIconByName(Icons.ICON_STARBASE);
          icon.draw(gr, pixelX + Icon16x16.MAX_WIDTH,
              pixelY + Icon16x16.MAX_HEIGHT);
        }

        // Draw fleet
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
          if (recognized && fleetOwnerIndex != -1 && drawShip) {
            PlayerInfo shipInfo = starMap.getPlayerByIndex(fleetOwnerIndex);
            Tile fleetColor = Tiles.getTileByName(
                shipInfo.getColor().getShipTile());
            if (fleetColor != null) {
              fleetColor.draw(gr, pixelX, pixelY);
            }
            if (fleetOwner != info && Game.getTutorial() != null
                && starMap.isTutorialEnabled() && info.isHuman()) {
              String tutorialText = Game.getTutorial().showTutorialText(50);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                msg.setCoordinate(new Coordinate(i + cx, j + cy));
                info.getMsgList().addNewMessage(msg);
              }
              tutorialText = Game.getTutorial().showTutorialText(90);
              if (tutorialText != null) {
                Message msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                msg.setCoordinate(new Coordinate(i + cx, j + cy));
                info.getMsgList().addNewMessage(msg);
              }
            }
          }
          if (drawShip) {
            BufferedImage img = ShipImages
                .getByRace(fleetMap[i + cx][j + cy].getRace())
                .getSmallShipImage(fleetMap[i + cx][j + cy].getImageIndex());
            gr.drawImage(img, pixelX, pixelY, null);
            if (espionageDetected) {
              Icon16x16 icon = Icons.getIconByName(Icons.ICON_SPY_GOGGLES);
              icon.draw(gr, pixelX + Icon16x16.MAX_WIDTH,
                  pixelY + Icon16x16.MAX_HEIGHT);
            }
          }
        }
        if (fleet == null && fleetMap[i + cx][j + cy] != null) {
          Planet planetOrbital = starMap.getPlanetByFleetTileInfo(
              fleetMap[i + cx][j + cy]);
          if (planetOrbital.getOrbital() != null && info != null
              && info.getSectorVisibility(planetOrbital.getCoordinate())
              == PlayerInfo.VISIBLE) {
            // Draw orbital
            BufferedImage img = ShipImages
                .getByRace(fleetMap[i + cx][j + cy].getRace())
                .getSmallShipImage(fleetMap[i + cx][j + cy].getImageIndex());
            gr.drawImage(img, pixelX, pixelY, null);
          }
        }
        // Draw fog of war and uncharted tiles
        if (info != null) {
          switch (info.getSectorVisibility(new Coordinate(i + cx,
              j + cy))) {
          case PlayerInfo.UNCHARTED: {
            if (starMap.getTileInfo(i + cx, j + cy)
                .getType() != SquareInfo.TYPE_SUN) {
              Tiles.getTileByName(TileNames.UNCHARTED).draw(gr, pixelX, pixelY);
            }
            break;
          }
          case PlayerInfo.FOG_OF_WAR: {
            if (starMap.getTileInfo(i + cx, j + cy)
                .getType() != SquareInfo.TYPE_SUN) {
              Tiles.getTileByName(TileNames.FOG_OF_WAR).draw(gr, pixelX,
                  pixelY);
            }
            break;
          }
          default:
            // Do nothing
            break;
          }
        }

        // Draw the map cursor
        if (i + cx == starMap.getCursorX() && j + cy == starMap.getCursorY()) {
          cursorPixelX = pixelX;
          cursorPixelY = pixelY;
          gr.setStroke(full);
          gr.setColor(colorFlickerBlue);
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX + Tile.MAX_WIDTH - 1, pixelY);
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX, pixelY + Tile.MAX_HEIGHT - 1);
          // Right line
          gr.drawLine(pixelX + Tile.MAX_WIDTH - 1, pixelY,
              pixelX + Tile.MAX_WIDTH - 1, pixelY + Tile.MAX_HEIGHT - 1);
          // Bottom line
          gr.drawLine(pixelX, pixelY + Tile.MAX_HEIGHT - 1,
              pixelX + Tile.MAX_WIDTH - 1, pixelY + Tile.MAX_HEIGHT - 1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }

        // Draw sun's text
        if (tile.getName().equals(TileNames.SUN_E) && i > -viewPointX + 1) {
          Sun sun = starMap.getSunByCoordinate(i + cx, j + cy);
          if (sun != null) {
            int textWidth = (int) GuiStatics.getFontCubellanSC()
                .getStringBounds(sun.getName(), gr.getFontRenderContext())
                .getWidth();
            int offset = Tile.MAX_WIDTH / 2 + textWidth / 2 - 2;
            gr.setStroke(GuiStatics.TEXT_LINE);
            gr.setColor(GuiStatics.COLOR_GOLD_TRANS);
            gr.drawLine(pixelX - offset, pixelY + Tile.MAX_HEIGHT / 2 - 3,
                pixelX - Tile.MAX_WIDTH + offset,
                pixelY + Tile.MAX_HEIGHT / 2 - 3);
            gr.setColor(Color.BLACK);
            gr.setFont(GuiStatics.getFontCubellanSC());
            gr.drawString(sun.getName(),
                pixelX - Tile.MAX_WIDTH / 2 - textWidth / 2,
                pixelY + Tile.MAX_HEIGHT / 2);
          }
        }

        // Draw Gas giant text
        if ((tile.getName().equals(TileNames.GAS_GIANT_1_SE) && i > -viewPointX
            || tile.getName().equals(TileNames.GAS_GIANT_2_SE)
                && i > -viewPointX
            || tile.getName().equals(TileNames.GAS_GIANT_3_SE)
                && i > -viewPointX)
            && planet != null && info != null && info
                .getSectorVisibility(new Coordinate(i + cx,
                    j + cy)) != PlayerInfo.UNCHARTED) {
          int textWidth = (int) GuiStatics.getFontCubellanSC()
              .getStringBounds(RandomSystemNameGenerator.numberToRoman(
                  planet.getOrderNumber()), gr.getFontRenderContext())
              .getWidth();
          int offset = textWidth / 2 - 2;
          gr.setStroke(GuiStatics.TEXT_LINE);
          gr.setColor(GuiStatics.COLOR_GREYBLUE);
          gr.drawLine(pixelX - offset, pixelY - 3, pixelX + offset, pixelY - 3);
          gr.setColor(Color.BLACK);
          gr.setFont(GuiStatics.getFontCubellanSC());
          gr.drawString(
              RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
              pixelX - textWidth / 2, pixelY);
        }

        // Draw planet text
        if (planet != null && !planet.isGasGiant()
            && planet.getOrderNumber() != 0 && info != null && info
            .getSectorVisibility(new Coordinate(i + cx,
                j + cy)) != PlayerInfo.UNCHARTED) {
          int textWidth = (int) GuiStatics.getFontCubellanSC()
              .getStringBounds(RandomSystemNameGenerator.numberToRoman(
                  planet.getOrderNumber()), gr.getFontRenderContext())
              .getWidth();
          int offset = Tile.MAX_WIDTH / 2 - textWidth / 2 - 2;
          gr.setStroke(GuiStatics.TEXT_LINE);
          gr.setColor(GuiStatics.COLOR_GREYBLUE);
          gr.drawLine(pixelX + offset, pixelY + Tile.MAX_HEIGHT / 2 - 3,
              pixelX + Tile.MAX_WIDTH - offset,
              pixelY + Tile.MAX_HEIGHT / 2 - 3);
          gr.setColor(Color.BLACK);
          gr.setFont(GuiStatics.getFontCubellanSC());
          gr.drawString(
              RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
              pixelX + Tile.MAX_WIDTH / 2 - textWidth / 2,
              pixelY + Tile.MAX_HEIGHT / 2);
        }
        if (routeData != null && routeData[i + cx][j + cy] == 1) {
          if (route.isDefending()) {
            gr.drawImage(Route.getDefenseDot(), pixelX, pixelY, null);
          } else if (route.isFixing()) {
            gr.drawImage(Route.getRepairDot(), pixelX, pixelY, null);
          } else if (route.isBombing()) {
            gr.drawImage(Route.getBombedDot(), pixelX, pixelY, null);
          } else {
            gr.drawImage(Route.getRouteDot(), pixelX, pixelY, null);
          }
        }
        if (routeData != null && routeData[i + cx][j + cy] == 2) {
          gr.drawImage(Route.getGreenRouteDot(), pixelX, pixelY, null);
        }
        if (routeData != null && routeData[i + cx][j + cy] == 3) {
          gr.drawImage(Route.getYellowRouteDot(), pixelX, pixelY, null);
        }
        pixelX = pixelX + Tile.MAX_WIDTH;
      }
      pixelX = viewPointOffsetX;
      pixelY = pixelY + Tile.MAX_HEIGHT;
    }
    if (isShowMiniMap() && minimap != null) {
      // Draw minimap itself
      BufferedImage miniMapImg = minimap.getDrawnImage();
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
    if (cursorFocus > 0) {
      cursorFocus--;
      Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
      gr.setStroke(full);
      gr.setColor(new Color(255, 50, 50, cursorFocus * 5));
      gr.drawLine(0, cursorPixelY + Tile.MAX_HEIGHT / 2, cursorPixelX,
          cursorPixelY + Tile.MAX_HEIGHT / 2);
      gr.drawLine(cursorPixelX + Tile.MAX_WIDTH, cursorPixelY
          + Tile.MAX_HEIGHT / 2, screen.getWidth(), cursorPixelY
          + Tile.MAX_HEIGHT / 2);
      gr.drawLine(cursorPixelX + Tile.MAX_WIDTH / 2, 0, cursorPixelX
          + Tile.MAX_WIDTH / 2, cursorPixelY);
      gr.drawLine(cursorPixelX + Tile.MAX_WIDTH / 2, cursorPixelY
          + Tile.MAX_HEIGHT, cursorPixelX + Tile.MAX_WIDTH / 2,
          screen.getHeight());
    }
    gr.dispose();
  }

  /**
   * Draw star map with history to Map panel
   * @param starMap Star map to draw
   */
  public void drawHistoryMap(final StarMap starMap) {
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
    boolean blackholeUpdated = false;
    int tileWidth = Tile.MAX_WIDTH;
    int tileHeight = Tile.MAX_HEIGHT;
    Graphics2D gr = screen.createGraphics();
    // Center coordinates
    if (historyCoordinates == null) {
      historyCoordinates = new Coordinate(starMap.getDrawX(),
          starMap.getDrawY());
    }
    int cx = historyCoordinates.getX();
    int cy = historyCoordinates.getY();
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
        Tile blackholeTile = starMap.getTile(i + cx, j + cy);
        if (blackholeTile.isBlackhole() && !blackholeUpdated) {
          updateBlackHoleEffect(pixelX, pixelY, i, j, blackholeTile);
          blackholeUpdated = true;
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
        if (tile.getName().equals(TileNames.SUN_E) && i > -viewPointX + 1) {
          Sun sun = starMap.getSunByCoordinate(i + cx, j + cy);
          if (sun != null) {
            int textWidth = (int) GuiStatics.getFontCubellanSC()
                .getStringBounds(sun.getName(), gr.getFontRenderContext())
                .getWidth();
            int offset = tileWidth / 2 + textWidth / 2 - 2;
            gr.setStroke(GuiStatics.TEXT_LINE);
            gr.setColor(GuiStatics.COLOR_GOLD_TRANS);
            gr.drawLine(pixelX - offset, pixelY + tileHeight / 2 - 3,
                pixelX - tileWidth + offset,
                pixelY + tileHeight / 2 - 3);
            gr.setColor(Color.BLACK);
            gr.setFont(GuiStatics.getFontCubellanSC());
            gr.drawString(sun.getName(),
                pixelX - tileWidth / 2 - textWidth / 2,
                pixelY + tileHeight / 2);
          }
        }

        // Draw Gas giant text
        if ((tile.getName().equals(TileNames.GAS_GIANT_1_SE) && i > -viewPointX
            || tile.getName().equals(TileNames.GAS_GIANT_2_SE)
                && i > -viewPointX)
            && planet != null) {
          int textWidth = (int) GuiStatics.getFontCubellanSC()
              .getStringBounds(RandomSystemNameGenerator.numberToRoman(
                  planet.getOrderNumber()), gr.getFontRenderContext())
              .getWidth();
          int offset = textWidth / 2 - 2;
          gr.setStroke(GuiStatics.TEXT_LINE);
          gr.setColor(GuiStatics.COLOR_GREYBLUE);
          gr.drawLine(pixelX - offset, pixelY - 3, pixelX + offset, pixelY - 3);
          gr.setColor(Color.BLACK);
          gr.setFont(GuiStatics.getFontCubellanSC());
          gr.drawString(
              RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
              pixelX - textWidth / 2, pixelY);
        }

        // Draw planet text
        if (planet != null && !planet.isGasGiant()
            && planet.getOrderNumber() != 0) {
          int textWidth = (int) GuiStatics.getFontCubellanSC()
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
          gr.setFont(GuiStatics.getFontCubellanSC());
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
          screen.getHeight() - leftSpaceImage.getHeight() - 10, null);
    }
    if (getRightSpaceImage() != null) {
      gr.drawImage(rightSpaceImage,
          screen.getWidth() - rightSpaceImage.getWidth() - 10,
          screen.getHeight() - leftSpaceImage.getHeight() - 10, null);
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
          gr.setColor(GuiStatics.COLOR_GREEN_TEXT);
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
              int textWidth = (int) GuiStatics.getFontCubellan()
                  .getStringBounds(accuracyStr, gr.getFontRenderContext())
                  .getWidth();
              gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE_DARKER);
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
              gr.setColor(GuiStatics.COLOR_COOL_SPACE_BLUE);
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
          if (DiceGenerator.getRandom(1) == 1) {
            ox = ox * -1;
          }
          if (DiceGenerator.getRandom(1) == 1) {
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
  }

  /**
   * Get history coordinates.
   * @return Coordinates
   */
  public Coordinate getHistoryCoordinates() {
    if (historyCoordinates == null) {
      historyCoordinates = new Coordinate(15, 15);
    }
    return historyCoordinates;
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
}