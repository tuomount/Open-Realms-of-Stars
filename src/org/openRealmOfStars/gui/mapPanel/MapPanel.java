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

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatAnimation;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.SquareInfo;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;

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
  private final static int WIDTH = 864;
  /**
   * Map drawing area size height
   */
  private final static int HEIGHT = 672;
  
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
   * Value used to create flickering blue grid
   */
  private int flickerBlue=64;
  
  /**
   * Is flicker value moving up 
   */
  private boolean flickerGoUp=true;
  
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

  public MapPanel(boolean battle) {
    this.battle = battle;
    int width = WIDTH;
    int height = HEIGHT;
    if (battle) {
      width = 576;
      height = 576;
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
  }
  

  /**
   * Calculate view according the actual panel size;
   */
  private void calculateViewPoints() {
    if (battle) {
      viewPointX = ((this.getWidth()/ShipImage.MAX_WIDTH)-1)/2;
      viewPointY = ((this.getHeight()/ShipImage.MAX_HEIGHT)-1)/2;
    } else {
      viewPointX = ((this.getWidth()/Tile.MAX_WIDTH)-1)/2;
      viewPointY = ((this.getHeight()/Tile.MAX_HEIGHT)-1)/2;
    }
    if (viewPointX < 1) {
      viewPointX = 1;
    }
    if (viewPointY < 1) {
      viewPointY = 1;
    }
    if (battle) {
      viewPointOffsetX = this.getWidth()-(2*viewPointX*ShipImage.MAX_WIDTH+
          ShipImage.MAX_WIDTH);
      viewPointOffsetX = viewPointOffsetX/2;
      viewPointOffsetY = this.getHeight()-(2*viewPointY*ShipImage.MAX_HEIGHT+
          ShipImage.MAX_HEIGHT);
      viewPointOffsetY = viewPointOffsetY/2;
    } else {
      viewPointOffsetX = this.getWidth()-(2*viewPointX*Tile.MAX_WIDTH+
          Tile.MAX_WIDTH);
      viewPointOffsetX = viewPointOffsetX/2;
      viewPointOffsetY = this.getHeight()-(2*viewPointY*Tile.MAX_HEIGHT+
          Tile.MAX_HEIGHT);
      viewPointOffsetY = viewPointOffsetY/2;
    }
  }
  @Override
  public void setMaximumSize(Dimension maximumSize) {
    super.setMaximumSize(maximumSize);
    screen = new BufferedImage(this.getWidth(), this.getHeight(),
                       BufferedImage.TYPE_INT_ARGB);
    calculateViewPoints();
  }


  @Override
  public void setMinimumSize(Dimension minimumSize) {
    super.setMinimumSize(minimumSize);
    calculateViewPoints();
  }


  @Override
  public void setPreferredSize(Dimension preferredSize) {    
    super.setPreferredSize(preferredSize);
    calculateViewPoints();
  }


  @Override
  public void setSize(Dimension d) {
    super.setSize(d);
    calculateViewPoints();
  }


  @Override
  public void setSize(int width, int height) {
    super.setSize(width, height);
    calculateViewPoints();
  }


  @Override
  public void paint(Graphics arg0) {
    super.paint(arg0);
    if (screen != null) {
      arg0.drawImage(screen, 0, 0, null);
    }
  }
  
  /**
   * Draw star map to Map panel
   * @param starMap Star map to draw
   */
  public void drawMap(StarMap starMap) {
    PlayerInfo info = starMap.getCurrentPlayerInfo();
    if (screen == null) {
      calculateViewPoints();
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
    if (cx > starMap.getMaxX()-viewPointX-1) {
      cx = starMap.getMaxX()-viewPointX-1;
    }
    if (cy < viewPointY) {
      cy = viewPointY;
    }
    if (cy > starMap.getMaxY()-viewPointY-1) {
      cy = starMap.getMaxY()-viewPointY-1;
    }
    starMap.setDrawPos(cx, cy);
    // -20 for safety
    int speedX = (GuiStatics.nebulaeImage.getWidth()-this.getWidth()-20)/starMap.getMaxX(); 
    int speedY = (GuiStatics.nebulaeImage.getHeight()-this.getHeight()-20)/starMap.getMaxY(); 
    int speedStarX = (GuiStatics.starFieldImage.getWidth()-this.getWidth()-20)/starMap.getMaxX(); 
    int speedStarY = (GuiStatics.starFieldImage.getHeight()-this.getHeight()-20)/starMap.getMaxY(); 
    // Parallax Scrolling with just two lines!!!
    gr.drawImage(GuiStatics.starFieldImage, -10-cx*speedStarX, -10-cy*speedStarY, null);
    gr.drawImage(GuiStatics.nebulaeImage, -10-cx*speedX, -10-cy*speedY, null);
    
    lastDrawnCenterX = cx;
    lastDrawnCenterY = cy;
    int scaled = 16*(flickerBlue-128)/256;
    Color colorDarkBlue = new Color(0, 118+scaled, 150+scaled);
    Color colorFlickerBlue = new Color(0, 0, 16);
    if (flickerBlue < 256) {
      colorFlickerBlue = new Color(0, 0, flickerBlue);
    } else {
      int above = flickerBlue -256;
      colorFlickerBlue = new Color(above, above, 255);
    }
    if (flickerGoUp) {
      flickerBlue = flickerBlue +16;
    } else {
      flickerBlue = flickerBlue -16;
    }
    if (flickerBlue > 384) {
      flickerGoUp = false;
    }
    if (flickerBlue < 128) {
      flickerGoUp = true;
    }

    FleetTileInfo[][] fleetMap = starMap.getFleetTiles();
    
    int[][] map = starMap.getTiles();
    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    for (int j=-viewPointY;j<viewPointY+1;j++) {
      for (int i=-viewPointX;i<viewPointX+1;i++) {
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.
            JOIN_BEVEL, 1, new float[]{0.1f,4.5f}, 0);
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.
            JOIN_BEVEL, 1, new float[]{1f}, 0);
        if (i+cx == starMap.getCursorX() && j+cy == starMap.getCursorY()) {
          gr.setStroke(full);
          gr.setColor(colorFlickerBlue);
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX+Tile.MAX_WIDTH-1, pixelY);
          // Left line          
          gr.drawLine(pixelX, pixelY, pixelX, pixelY+Tile.MAX_HEIGHT-1);
          // Right line
          gr.drawLine(pixelX+Tile.MAX_WIDTH-1, pixelY, pixelX+Tile.MAX_WIDTH-1,
              pixelY+Tile.MAX_HEIGHT-1);
          // Bottom line
          gr.drawLine(pixelX, pixelY+Tile.MAX_HEIGHT-1, pixelX+Tile.MAX_WIDTH-1,
              pixelY+Tile.MAX_HEIGHT-1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        } else {
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }
        if (i!=viewPointX) {
          // Right line
          gr.drawLine(pixelX+Tile.MAX_WIDTH-1, pixelY, pixelX+Tile.MAX_WIDTH-1,
              pixelY+Tile.MAX_HEIGHT-1);
        }
        if (j!=viewPointY) {
          // Bottom line
          gr.drawLine(pixelX, pixelY+Tile.MAX_HEIGHT-1, pixelX+Tile.MAX_WIDTH-1,
              pixelY+Tile.MAX_HEIGHT-1);
        }
        CulturePower culture =starMap.getSectorCulture(i+cx, j+cy);
        if (culture != null) {
          int index = culture.getHighestCulture();
          if (index != -1) {
            Tile tile = Tiles.getTileByName("Player_"+index);
            if (tile != null) {
              tile.draw(gr, pixelX, pixelY);
            }
          }
        }
        Tile tile = Tiles.getTileByIndex(map[i+cx][j+cy]);
        // Draw tiles
        if (!tile.getName().equals(TileNames.EMPTY)) {
          // Draw only non empty tiles
          if ((info != null && info.getSectorVisibility(i+cx, j+cy)!=PlayerInfo.UNCHARTED) ||
              starMap.getTileInfo(i+cx, j+cy).getType() == SquareInfo.TYPE_SUN) {
            tile.draw(gr, pixelX, pixelY);
          }
        }
        
        // Draw fleet
        if (info != null &&
            info.getSectorVisibility(i+cx, j+cy)==PlayerInfo.VISIBLE &&
            fleetMap[i+cx][j+cy] != null) {
          BufferedImage img = ShipImages.getByRace(
              fleetMap[i+cx][j+cy].getRace()).getSmallShipImage(
                  fleetMap[i+cx][j+cy].getImageIndex());
          gr.drawImage(img, pixelX, pixelY, null);
        }
        
        // Draw fog of war and uncharted tiles
        if (info != null ) {
          switch (info.getSectorVisibility(i+cx, j+cy)) {
          case PlayerInfo.UNCHARTED: {
            if (starMap.getTileInfo(i+cx, j+cy).getType() != SquareInfo.TYPE_SUN) {
              Tiles.getTileByName(TileNames.UNCHARTED).draw(gr, pixelX, pixelY); 
            }
            break;
          }
          case PlayerInfo.FOG_OF_WAR: {
            if (starMap.getTileInfo(i+cx, j+cy).getType() != SquareInfo.TYPE_SUN) {
              Tiles.getTileByName(TileNames.FOG_OF_WAR).draw(gr, pixelX, pixelY);
            }
            break;
          }
          }
        }
        
        // Draw sun's text
        if (tile.getName().equals(TileNames.SUN_E) && i > -viewPointX+1) {
          Sun sun = starMap.getSunByCoordinate(i+cx, j+cy);
          if (sun != null) {
            int textWidth = (int) GuiStatics.getFontCubellanSC().getStringBounds(
              sun.getName(), gr.getFontRenderContext()).getWidth();
            int offset = Tile.MAX_WIDTH/2+textWidth/2-2;
            gr.setStroke(GuiStatics.TEXT_LINE);
            gr.setColor(GuiStatics.COLOR_GOLD_TRANS);
            gr.drawLine(pixelX-offset, pixelY+Tile.MAX_HEIGHT/2-3,
              pixelX-Tile.MAX_WIDTH+offset, pixelY+Tile.MAX_HEIGHT/2-3);
            gr.setColor(Color.BLACK);
            gr.setFont(GuiStatics.getFontCubellanSC());
            gr.drawString(sun.getName(), pixelX-Tile.MAX_WIDTH/2-textWidth/2, 
                pixelY+Tile.MAX_HEIGHT/2);
          }
        }
        
        // Draw Gas giant text
        if ((tile.getName().equals(TileNames.GAS_GIANT_1_SE) && i > -viewPointX) ||
            (tile.getName().equals(TileNames.GAS_GIANT_2_SE) && i > -viewPointX )) {
          Planet planet = starMap.getPlanetByCoordinate(i+cx, j+cy);
          if (planet != null && info != null &&
              info.getSectorVisibility(i+cx, j+cy)!=PlayerInfo.UNCHARTED) {
            int textWidth = (int) GuiStatics.getFontCubellanSC().getStringBounds(
                RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
                gr.getFontRenderContext()).getWidth();
              int offset = textWidth/2-2;
              gr.setStroke(GuiStatics.TEXT_LINE);
              gr.setColor(GuiStatics.COLOR_GREYBLUE);
              gr.drawLine(pixelX-offset, pixelY-3,
                pixelX+offset, pixelY-3);
              gr.setColor(Color.BLACK);
              gr.setFont(GuiStatics.getFontCubellanSC());
              gr.drawString(RandomSystemNameGenerator.numberToRoman(planet.
                  getOrderNumber()), pixelX-textWidth/2, pixelY);
            
          }
        }
        
        // Draw planet text
        Planet planet = starMap.getPlanetByCoordinate(i+cx, j+cy);
        if (planet != null && !planet.isGasGiant() && info != null && 
            info.getSectorVisibility(i+cx, j+cy)!=PlayerInfo.UNCHARTED) {
          int textWidth = (int) GuiStatics.getFontCubellanSC().getStringBounds(
              RandomSystemNameGenerator.numberToRoman(planet.getOrderNumber()),
              gr.getFontRenderContext()).getWidth();
            int offset = Tile.MAX_WIDTH/2-textWidth/2-2;
            gr.setStroke(GuiStatics.TEXT_LINE);
            gr.setColor(GuiStatics.COLOR_GREYBLUE);
            gr.drawLine(pixelX+offset, pixelY+Tile.MAX_HEIGHT/2-3,
              pixelX+Tile.MAX_WIDTH-offset, pixelY+Tile.MAX_HEIGHT/2-3);
            gr.setColor(Color.BLACK);
            gr.setFont(GuiStatics.getFontCubellanSC());
            gr.drawString(RandomSystemNameGenerator.numberToRoman(planet.
                getOrderNumber()), pixelX+Tile.MAX_WIDTH/2-textWidth/2, pixelY+Tile.MAX_HEIGHT/2);
          
        }
        if (routeData != null) {
          if (routeData[i+cx][j+cy]==1) {
            gr.drawImage(Route.getRouteDot(), pixelX, pixelY, null);
          }
        }
        pixelX=pixelX+Tile.MAX_WIDTH;
      }
      pixelX = viewPointOffsetX;
      pixelY=pixelY+Tile.MAX_HEIGHT;
    }
  }

  /**
   * Draw battle map to Map panel
   * @param Combat to draw on map panel
   */
  public void drawBattleMap(Combat combat, PlayerInfo info, StarMap starMap) {
    if (screen == null) {
      calculateViewPoints();
      if (this.getWidth() > 0 && this.getHeight() > 0) {
        screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
      } else {
        return;
      }
    }
    Graphics2D gr = screen.createGraphics();
    // Center coordinates, to match starmap background
    int cx = starMap.getDrawX();
    int cy = starMap.getDrawY();

    // -20 for safety
    int speedStarX = (GuiStatics.starFieldImage.getWidth()-this.getWidth()-20)/starMap.getMaxX(); 
    int speedStarY = (GuiStatics.starFieldImage.getHeight()-this.getHeight()-20)/starMap.getMaxY(); 
    gr.drawImage(GuiStatics.starFieldImage, -10-cx*speedStarX, -10-cy*speedStarY, null);
    
    int scaled = 16*(flickerBlue-128)/256;
    Color colorDarkBlue = new Color(0, 118+scaled, 150+scaled);
    Color colorFlickerBlue = new Color(0, 0, 16);
    if (flickerBlue < 256) {
      colorFlickerBlue = new Color(0, 0, flickerBlue);
    } else {
      int above = flickerBlue -256;
      colorFlickerBlue = new Color(above, above, 255);
    }
    if (flickerGoUp) {
      flickerBlue = flickerBlue +16;
    } else {
      flickerBlue = flickerBlue -16;
    }
    if (flickerBlue > 384) {
      flickerGoUp = false;
    }
    if (flickerBlue < 128) {
      flickerGoUp = true;
    }

    int pixelX = viewPointOffsetX;
    int pixelY = viewPointOffsetY;
    lastDrawnCenterX = (Combat.MAX_X-1)/2;
    lastDrawnCenterY = (Combat.MAX_Y-1)/2;
    viewPointX = (Combat.MAX_X-1)/2;
    viewPointY = (Combat.MAX_Y-1)/2;
    for (int j=0;j<Combat.MAX_Y;j++) {
      for (int i=0;i<Combat.MAX_X;i++) {
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.
            JOIN_BEVEL, 1, new float[]{0.1f,4.5f}, 0);
        Stroke full = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.
            JOIN_BEVEL, 1, new float[]{1f}, 0);
        if (i == combat.getCursorX() && j == combat.getCursorY()) {
          gr.setStroke(full);
          gr.setColor(colorFlickerBlue);
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX+ShipImage.MAX_WIDTH-1, pixelY);
          // Left line          
          gr.drawLine(pixelX, pixelY, pixelX, pixelY+ShipImage.MAX_HEIGHT-1);
          // Right line
          gr.drawLine(pixelX+ShipImage.MAX_WIDTH-1, pixelY, pixelX+ShipImage.MAX_WIDTH-1,
              pixelY+ShipImage.MAX_HEIGHT-1);
          // Bottom line
          gr.drawLine(pixelX, pixelY+ShipImage.MAX_HEIGHT-1, pixelX+ShipImage.MAX_WIDTH-1,
              pixelY+ShipImage.MAX_HEIGHT-1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        } if (i == combat.getCurrentShip().getX() &&
            j == combat.getCurrentShip().getY()) {
          gr.setStroke(full);
          gr.setColor(GuiStatics.COLOR_GREEN_TEXT);
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX+ShipImage.MAX_WIDTH-1, pixelY);
          // Left line          
          gr.drawLine(pixelX, pixelY, pixelX, pixelY+ShipImage.MAX_HEIGHT-1);
          // Right line
          gr.drawLine(pixelX+ShipImage.MAX_WIDTH-1, pixelY, pixelX+ShipImage.MAX_WIDTH-1,
              pixelY+ShipImage.MAX_HEIGHT-1);
          // Bottom line
          gr.drawLine(pixelX, pixelY+ShipImage.MAX_HEIGHT-1, pixelX+ShipImage.MAX_WIDTH-1,
              pixelY+ShipImage.MAX_HEIGHT-1);
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
          
        } else {
          gr.setStroke(dashed);
          gr.setColor(colorDarkBlue);
        }
        if (i==0) {
          // Left line
          gr.drawLine(pixelX, pixelY, pixelX, pixelY+ShipImage.MAX_HEIGHT-1);
        }
        if (j==0) {
          // Top line
          gr.drawLine(pixelX, pixelY, pixelX+ShipImage.MAX_WIDTH-1, pixelY);
        }
        // Right line
        gr.drawLine(pixelX+ShipImage.MAX_WIDTH-1, pixelY, pixelX+ShipImage.MAX_WIDTH-1,
              pixelY+ShipImage.MAX_HEIGHT-1);
        // Bottom line
        gr.drawLine(pixelX, pixelY+ShipImage.MAX_HEIGHT-1, pixelX+ShipImage.MAX_WIDTH-1,
              pixelY+ShipImage.MAX_HEIGHT-1);
        // Draw fleet
        CombatShip ship = combat.getShipFromCoordinate(i, j);
        if (ship != null) {
          BufferedImage img = ShipImages.getByRace(ship.getShip().getHull().
              getRace()).getShipImage(ship.getShip().getHull().getImageIndex());
          if (ship.isFlipY()) {
            AffineTransform at = AffineTransform.getScaleInstance(1, -1);
            at.translate(0,-img.getHeight(null));
            AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage flipImg = ato.filter(img, null);
            gr.drawImage(flipImg, pixelX, pixelY, null); 
          } else {
            gr.drawImage(img, pixelX, pixelY, null);
          }
        }
        
        if (i == combat.getCursorX() && j == combat.getCursorY()) {
          ShipComponent weapon = combat.getCurrentShip().getShip().getComponent(combat.getComponentUse());
          if (combat.getComponentUse() != -1 && weapon != null && weapon.isWeapon()) {
            CombatShip target = combat.getShipFromCoordinate(combat.getCursorX(), combat.getCursorY());
            if (target !=  null && combat.getCurrentShip().getPlayer().isHuman() 
                && combat.isClearShot(combat.getCurrentShip(), target)) {
              gr.drawImage(GuiStatics.CROSSHAIR, pixelX, pixelY, null);
            } else {
              gr.drawImage(GuiStatics.RED_CROSSHAIR, pixelX, pixelY, null);
            }
          }
        }

        
        pixelX=pixelX+ShipImage.MAX_WIDTH;
      }
      pixelX = viewPointOffsetX;
      pixelY=pixelY+ShipImage.MAX_HEIGHT;
    }
    if (combat.getAnimation() != null) {
      CombatAnimation anim = combat.getAnimation();
      if (anim.getWeapon().getType() == ShipComponentType.WEAPON_BEAM) {
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.
            JOIN_BEVEL, 1, new float[]{1f}, 0);
        gr.setStroke(full);
        gr.setColor(anim.getBeamColor());
        gr.drawLine(anim.getSx()+viewPointOffsetX, anim.getSy()+viewPointOffsetY,
            anim.getEx()+viewPointOffsetX, anim.getEy()+viewPointOffsetY);
      }else if (anim.getWeapon().getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
        gr.drawImage(GuiStatics.PHOTON_TORPEDO, 
            anim.getSx()+viewPointOffsetX-GuiStatics.PHOTON_TORPEDO.getWidth()/2, 
            anim.getSy()+viewPointOffsetY-GuiStatics.PHOTON_TORPEDO.getHeight()/2,null);
      } else {
        gr.setColor(GuiStatics.COLOR_GREY_160);
        Stroke full = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.
            JOIN_BEVEL, 1, new float[]{1f}, 0);
        gr.setStroke(full);
        gr.drawLine(anim.getSx()+viewPointOffsetX, anim.getSy()+viewPointOffsetY,
            anim.getSx()+viewPointOffsetX+1, anim.getSy()+viewPointOffsetY+1);
      }
      if (anim.getAnimFrame() != null) {
        gr.drawImage(anim.getAnimFrame(), 
            anim.getEx()+viewPointOffsetX-anim.getAnimFrame().getWidth()/2,
            anim.getEy()+viewPointOffsetY-anim.getAnimFrame().getHeight()/2,null);
      }
      List<ParticleEffect> particles = anim.getParticles();
      for (ParticleEffect part : particles) {
        screen.setRGB(part.getX()+viewPointOffsetX, part.getY()+viewPointOffsetY,
            part.getColor().getRGB());
      }
      anim.doAnimation();
      if (anim.isAnimationFinished()) {
        if (anim.getTarget().getShip().getHullPoints()<=0) {
          // Ship has no more hull points so destroying it
          combat.destroyShip(anim.getTarget());
        }
        combat.setAnimation(null);
      }
    }

  }

  public int getLastDrawnX() {
    return lastDrawnCenterX;
  }
  
  public int getLastDrawnY() {
    return lastDrawnCenterY;
  }
  
  public int getOffsetX() {
    return viewPointOffsetX;
  }

  public int getOffsetY() {
    return viewPointOffsetY;
  }

  public int getViewPointX() {
    return viewPointX;
  }

  public int getViewPointY() {
    return viewPointY;
  }


  public Route getRoute() {
    return route;
  }


  public void setRoute(Route route) {
    this.route = route;
  }



}
