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
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatAnimation;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.SquareInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
  private static final int WIDTH = 864;
  /**
   * Map drawing area size height
   */
  private static final int HEIGHT = 608;

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
   * Map scaling
   */
  private int scale;

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
   * Constructor for Map Panel. This can be used for drawing star map
   * or battle map
   * @param battle True if drawing battle map.
   */
  public MapPanel(final boolean battle) {
    this.battle = battle;
    int width = WIDTH;
    int height = HEIGHT;
    setScale(Tile.SCALED_NORMAL);
    historyCultures = null;
    historyCoordinates = null;
    if (battle) {
      width = BATTLE_VIEW_SIZE;
      height = BATTLE_VIEW_SIZE;
    }
    screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Dimension size = new Dimension(width, height);
    this.setSize(size);
    this.setPreferredSize(size);
    this.setMinimumSize(size);
    this.setMaximumSize(size);
    calculateViewPoints();
    this.setBackground(Color.black);
    setRoute(null);
    wormHoleAnimation = 0;
  }

  /**
   * Calculate view according the actual panel size;
   */
  private void calculateViewPoints() {
    int tileWidth = Tile.maxWidth(scale);
    int tileHeight = Tile.maxHeight(scale);
    if (battle) {
      viewPointX = (this.getWidth() / ShipImage.MAX_WIDTH - 1) / 2;
      viewPointY = (this.getHeight() / ShipImage.MAX_HEIGHT - 1) / 2;
    } else {
      viewPointX = (this.getWidth() / tileWidth - 1) / 2;
      viewPointY = (this.getHeight() / tileHeight - 1) / 2;
    }
    if (viewPointX < 1) {
      viewPointX = 1;
    }
    if (viewPointY < 1) {
      viewPointY = 1;
    }
    if (battle) {
      viewPointOffsetX = this.getWidth()
          - (2 * viewPointX * ShipImage.MAX_WIDTH + ShipImage.MAX_WIDTH);
      viewPointOffsetX = viewPointOffsetX / 2;
      viewPointOffsetY = this.getHeight()
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
  public void setMaximumSize(final Dimension maximumSize) {
    super.setMaximumSize(maximumSize);
    screen = new BufferedImage(this.getWidth(), this.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    calculateViewPoints();
  }

  @Override
  public void setMinimumSize(final Dimension minimumSize) {
    super.setMinimumSize(minimumSize);
    calculateViewPoints();
  }

  @Override
  public void setPreferredSize(final Dimension preferredSize) {
    super.setPreferredSize(preferredSize);
    calculateViewPoints();
  }

  @Override
  public void setSize(final Dimension d) {
    super.setSize(d);
    calculateViewPoints();
  }

  @Override
  public void setSize(final int width, final int height) {
    super.setSize(width, height);
    calculateViewPoints();
  }

  @Override
  public void paint(final Graphics arg0) {
    super.paint(arg0);
    if (screen != null) {
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
   * Draw star map to Map panel
   * @param starMap Star map to draw
   */
  public void drawMap(final StarMap starMap) {
    PlayerInfo info = starMap.getCurrentPlayerInfo();
    if (screen == null) {
      calculateViewPoints();
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(),
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
    int speedStarX = (GuiStatics.STAR_FIELD_IMAGE.getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedStarY = (GuiStatics.STAR_FIELD_IMAGE.getHeight() - this.getHeight()
        - SAFETY_OFFSET) / starMap.getMaxY();
    // Parallax Scrolling with just two lines!!!
    gr.drawImage(GuiStatics.STAR_FIELD_IMAGE,
        -PARALLAX_OFFSET - cx * speedStarX, -PARALLAX_OFFSET - cy * speedStarY,
        null);
    gr.drawImage(GuiStatics.NEBULAE_IMAGE, -PARALLAX_OFFSET - cx * speedX,
        -PARALLAX_OFFSET - cy * speedY, null);

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
              Tile tile = Tiles.getTileByName("Player_" + index);
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
        if (tile.getAnimationIndex() != tile.getIndex()) {
          // Change map tile for next drawing
          starMap.setTile(i + cx, j + cy,
              Tiles.getTileByIndex(tile.getAnimationIndex()));
        }
        // Draw only non empty tiles
        if (info != null && !tile.getName().equals(TileNames.EMPTY)
            && info.getSectorVisibility(new Coordinate(i + cx,
                j + cy)) != PlayerInfo.UNCHARTED
            || starMap.getTileInfo(i + cx, j + cy)
                .getType() == SquareInfo.TYPE_SUN) {
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
            && info.getSectorVisibility(new Coordinate(i + cx,
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
          boolean drawShip = false;
          boolean recognized = false;
          boolean espionageDetected = false;
           if (info.getSectorVisibility(new Coordinate(i + cx,
                j + cy)) == PlayerInfo.VISIBLE) {
             if (fleet != null && (info.getSectorCloakDetection(i + cx, j + cy)
                >= fleet.getFleetCloackingValue()
                || info.getFleets().isFleetOnList(fleet))) {
              drawShip = true;
             }
             if (drawShip && fleet.getEspionageBonus() > 0
                 && info.getSectorCloakDetection(i + cx, j + cy)
                    >= fleet.getFleetCloackingValue() + Ship.ESPIONAGE_HIDE) {
               espionageDetected = true;
             }
            if (!fleet.isPrivateerFleet()) {
              recognized = true;
            }
          } else {
            EspionageList espionage = info.getEspionage().getByIndex(
                fleetOwnerIndex);
            if (espionage != null && espionage.getTotalBonus() >= 4) {
              FleetType fleetType = fleet.getFleetType();
              recognized = espionage.isFleetTypeRecognized(fleetType);
              if (recognized) {
                drawShip = true;
              }
            }
          }
          if (recognized && fleetOwnerIndex != -1) {
            Tile fleetColor = Tiles.getTileByName("Player_Ship_"
                + fleetOwnerIndex);
            if (fleetColor != null) {
              fleetColor.draw(gr, pixelX, pixelY);
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
        if (planet != null && !planet.isGasGiant() && info != null && info
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
          } else {
            gr.drawImage(Route.getRouteDot(), pixelX, pixelY, null);
          }
        }
        pixelX = pixelX + Tile.MAX_WIDTH;
      }
      pixelX = viewPointOffsetX;
      pixelY = pixelY + Tile.MAX_HEIGHT;
    }
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
    int tileWidth = Tile.maxWidth(scale);
    int tileHeight = Tile.maxHeight(scale);
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
    int speedStarX = (GuiStatics.STAR_FIELD_IMAGE.getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedStarY = (GuiStatics.STAR_FIELD_IMAGE.getHeight() - this.getHeight()
        - SAFETY_OFFSET) / starMap.getMaxY();
    // Parallax Scrolling with just two lines!!!
    gr.drawImage(GuiStatics.STAR_FIELD_IMAGE,
        -PARALLAX_OFFSET - cx * speedStarX, -PARALLAX_OFFSET - cy * speedStarY,
        null);
    gr.drawImage(GuiStatics.NEBULAE_IMAGE, -PARALLAX_OFFSET - cx * speedX,
        -PARALLAX_OFFSET - cy * speedY, null);

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
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {0.1f, 4.5f }, 0);
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(dashed);
        gr.setColor(colorDarkBlue);
        if (i != viewPointX && scale < Tile.SCALED_FIFTH) {
          // Right line
          gr.drawLine(pixelX + tileWidth - 1, pixelY,
              pixelX + tileWidth - 1, pixelY + tileHeight - 1);
        }
        if (j != viewPointY && scale < Tile.SCALED_FIFTH) {
          // Bottom line
          gr.drawLine(pixelX, pixelY + tileHeight - 1,
              pixelX + tileWidth - 1, pixelY + tileHeight - 1);
        }
        Tile tile = starMap.getTile(i + cx, j + cy);
        if (tile == null) {
          continue;
        }
        if (historyCultures != null) {
          int index = historyCultures[i + cx][j + cy];
          if (index != -1) {
            Tile cultureTile = Tiles.getTileByName("Player_" + index);
            if (cultureTile != null) {
              cultureTile.drawScaled(gr, pixelX, pixelY, scale);
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
          tile.drawScaled(gr, pixelX, pixelY, scale);
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
        if (scale == Tile.SCALED_NORMAL
            && tile.getName().equals(TileNames.SUN_E) && i > -viewPointX + 1) {
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
            && planet != null && scale == Tile.SCALED_NORMAL) {
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
            && scale == Tile.SCALED_NORMAL) {
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
  }

  /**
   * Draw battle map to Map panel
   * @param combat to draw on map panel
   * @param info PlayerInfo
   * @param starMap StarMap
   */
  public void drawBattleMap(final Combat combat, final PlayerInfo info,
      final StarMap starMap) {
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
    int speedStarX = (GuiStatics.STAR_FIELD_IMAGE.getWidth() - this.getWidth()
        - SAFETY_OFFSET) / starMap.getMaxX();
    int speedStarY = (GuiStatics.STAR_FIELD_IMAGE.getHeight()
        - this.getHeight() - SAFETY_OFFSET) / starMap.getMaxY();
    gr.drawImage(GuiStatics.STAR_FIELD_IMAGE, -PARALLAX_OFFSET - cx
        * speedStarX, -PARALLAX_OFFSET - cy * speedStarY, null);
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
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);

        } else {
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }
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
              && (weapon.isWeapon() || weapon.isPrivateer())) {
            CombatShip target = combat.getShipFromCoordinate(
                combat.getCursorX(), combat.getCursorY());
            if (target != null && combat.getCurrentShip().getPlayer().isHuman()
                && (combat.isClearShot(combat.getCurrentShip(), target)
                   || combat.canPrivateer(combat.getCurrentShip(), target))) {
              gr.drawImage(GuiStatics.CROSSHAIR, pixelX, pixelY, null);
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
        switch (anim.getWeapon().getType()) {
        case WEAPON_BEAM: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_BEAM);
          break;
          }
        case WEAPON_PHOTON_TORPEDO: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_TORPEDO);
          break;
          }
        case WEAPON_RAILGUN: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_RAILGUN);
          break;
          }
        case WEAPON_ECM_TORPEDO:
        case WEAPON_HE_MISSILE: {
          SoundPlayer.playSound(SoundPlayer.WEAPON_MISSILE);
          break;
          }
        case PRIVATEERING_MODULE: {
          //FIXME Change better privateer sound
          SoundPlayer.playSound(SoundPlayer.REPAIR);
          break;
          }
        default: {
          ErrorLogger.log("Unexpected weapon type, sound effect is missing!");
        }
        }
      }

      if (anim.getWeapon().getType() == ShipComponentType.WEAPON_BEAM) {
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1, new float[] {1f }, 0);
        gr.setStroke(full);
        gr.setColor(anim.getBeamColor());
        gr.drawLine(anim.getSx() + viewPointOffsetX,
            anim.getSy() + viewPointOffsetY, anim.getEx() + viewPointOffsetX,
            anim.getEy() + viewPointOffsetY);
      } else if (anim.getWeapon()
          .getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
        gr.drawImage(GuiStatics.PHOTON_TORPEDO,
            anim.getSx() + viewPointOffsetX
                - GuiStatics.PHOTON_TORPEDO.getWidth() / 2,
            anim.getSy() + viewPointOffsetY
                - GuiStatics.PHOTON_TORPEDO.getHeight() / 2,
            null);
      } else {
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
   * Get map scale value
   * @return the scale
   */
  public int getScale() {
    return scale;
  }

  /**
   * Set map scale value
   * @param mapScale the scale to set
   */
  public void setScale(final int mapScale) {
    screen = null;
    scale = mapScale;
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
}
