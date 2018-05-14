package org.openRealmOfStars.gui.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.starMap.planet.Planet;

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
 * Handling info on next to the star map
 *
 */

public class MapInfoPanel extends InfoPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Image Label for image
   */
  private ImageLabel imageLabel;

  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Show info about the planet
   */
  private Planet planet;

  /**
   * True if planet was actively scanned in one turn
   */
  private boolean activeScanned;

  /**
   * Show info about the fleet
   */
  private Fleet fleet;

  /**
   * Show info about the tile
   */
  private Tile tile;

  /**
   * Fleet owner as PlayerInfo
   */
  private PlayerInfo fleetOwner;

  /**
   * View planet or fleet
   */
  private SpaceButton viewBtn;

  /**
   * Defend button for fleet
   */
  private SpaceButton defendBtn;

  /**
   * Fix/Trade fleet button for fleet
   */
  private SpaceButton fixTradeFleetBtn;

  /**
   * Route button for fleet
   */
  private SpaceButton routeBtn;

  /**
   * Width of rigid box
   */
  private static final int RIGID_BOX_WIDTH = 130;
  /**
   * Height of rigid box
   */
  private static final int RIGID_BOX_HEIGHT = 50;

  /**
   * Constructor for MapInfoPanel
   * @param listener ActionListener
   */
  public MapInfoPanel(final ActionListener listener) {
    this.add(Box.createRigidArea(new Dimension(RIGID_BOX_WIDTH,
        RIGID_BOX_HEIGHT)));
    BufferedImage img = new BufferedImage(Tile.MAX_WIDTH * 2,
        Tile.MAX_HEIGHT * 2, BufferedImage.TYPE_4BYTE_ABGR);
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    IconButton iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_TARGET,
        this);
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    imageLabel = new ImageLabel(img, true);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    imageLabel.setFillColor(Color.black);
    Graphics2D g2d = img.createGraphics();
    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    panel.add(imageLabel);
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_TARGET,
        this);
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    this.add(panel);
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    textArea.setCharacterWidth(7);
    routeBtn = new SpaceButton("Route", GameCommands.COMMAND_ROUTE_FLEET);
    routeBtn.addActionListener(listener);
    routeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    routeBtn.setToolTipText("R - Route fleet with FTL. Route is direct line"
        + " from A to B using FTL speed.");
    defendBtn = new SpaceButton("Defend", GameCommands.COMMAND_DEFEND_SECTOR);
    defendBtn.addActionListener(listener);
    defendBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    defendBtn.setToolTipText("Defend sector, +5% accuracy");
    fixTradeFleetBtn = new SpaceButton("Fix fleet",
        GameCommands.COMMAND_FIX_FLEET);
    fixTradeFleetBtn.addActionListener(listener);
    fixTradeFleetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    fixTradeFleetBtn.setToolTipText("Fix fleet on hull point per turn.");
    fixTradeFleetBtn.setEnabled(true);
    this.add(textArea);
    viewBtn = new SpaceButton("View planet", GameCommands.COMMAND_VIEW_PLANET);
    viewBtn.addActionListener(listener);
    viewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    this.add(routeBtn);
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    this.add(fixTradeFleetBtn);
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    this.add(defendBtn);
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    this.add(viewBtn);
  }

  /**
   * Show planet on info panel
   * @param planetToShow The planet to show
   * @param activeScan Set true if planet is scanned in one turn
   */
  public void showPlanet(final Planet planetToShow, final boolean activeScan) {
    this.planet = planetToShow;
    this.fleet = null;
    this.activeScanned = activeScan;
    this.viewBtn.setEnabled(activeScan);
    this.viewBtn.setText("View planet");
    this.viewBtn.setActionCommand(GameCommands.COMMAND_VIEW_PLANET);
    this.defendBtn.setEnabled(false);
    this.fixTradeFleetBtn.setEnabled(false);
    this.routeBtn.setEnabled(false);
    updatePanel(false);
  }

  /**
   * Show tile on info panel, if tile has description.
   * @param tileToShow Tile to show
   */
  public void showTile(final Tile tileToShow) {
    this.planet = null;
    this.fleet = null;
    if (!tileToShow.getDescription().isEmpty()) {
      tile = tileToShow;
    } else {
      tile = null;
    }
    this.viewBtn.setEnabled(false);
    this.defendBtn.setEnabled(false);
    this.fixTradeFleetBtn.setEnabled(false);
    this.routeBtn.setEnabled(false);
    updatePanel(false);
  }

  /**
   * Show fleet on info panel
   * @param fleetToShow The fleet to show
   * @param owner The fleet owner
   * @param debug Show debug information
   */
  public void showFleet(final Fleet fleetToShow, final PlayerInfo owner,
      final boolean debug) {
    this.planet = null;
    this.fleet = fleetToShow;
    this.fleetOwner = owner;
    this.viewBtn.setEnabled(true);
    this.viewBtn.setText("View fleet");
    this.viewBtn.setActionCommand(GameCommands.COMMAND_VIEW_FLEET);
    this.defendBtn.setEnabled(true);
    this.fixTradeFleetBtn.setEnabled(true);
    this.routeBtn.setEnabled(true);
    updatePanel(debug);
  }

  /**
   * Get Fleet currently showing. Can be null if not showing the fleet.
   * @return Fleet or null
   */
  public Fleet getFleetShowing() {
    return fleet;
  }
  /**
   * Get the fleet owner whose fleet is being shown
   * @return PlayerInfo for fleet owner
   */
  public PlayerInfo getFleetOwner() {
    return fleetOwner;
  }

  /**
   * Show empty planet
   */
  public void showEmpty() {
    this.planet = null;
    this.fleet = null;
    this.tile = null;
    this.viewBtn.setEnabled(false);
    this.defendBtn.setEnabled(false);
    this.fixTradeFleetBtn.setEnabled(false);
    this.routeBtn.setEnabled(false);
    updatePanel(false);
  }

  /**
   * Update panels according set data
   * @param debug Show debug information on true
   */
  public void updatePanel(final boolean debug) {
    if (planet != null) {
      BufferedImage img = new BufferedImage(Tile.MAX_WIDTH * 2,
          Tile.MAX_HEIGHT * 2, BufferedImage.TYPE_4BYTE_ABGR);
      Tile tmpTile = Tiles.getTileByIndex(planet.getPlanetType()
          .getTileIndex());
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      if (!planet.isGasGiant()) {
        tmpTile.draw(g2d, Tile.MAX_WIDTH / 2, Tile.MAX_HEIGHT / 2);
      } else {
        switch (planet.getPlanetTypeIndex()) {
        default:
        case 0: {
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_NW);
          tmpTile.draw(g2d, 0, 0);
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_NE);
          tmpTile.draw(g2d, Tile.MAX_WIDTH, 0);
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_SW);
          tmpTile.draw(g2d, 0, Tile.MAX_HEIGHT);
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_1_SE);
          tmpTile.draw(g2d, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);

          break;
        }
        case 1: {
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_2_NW);
          tmpTile.draw(g2d, 0, 0);
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_2_NE);
          tmpTile.draw(g2d, Tile.MAX_WIDTH, 0);
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_2_SW);
          tmpTile.draw(g2d, 0, Tile.MAX_HEIGHT);
          tmpTile = Tiles.getTileByName(TileNames.GAS_GIANT_2_SE);
          tmpTile.draw(g2d, Tile.MAX_WIDTH, Tile.MAX_HEIGHT);
          break;
        }
        }
      }
      imageLabel.setImage(img);
      setTitle(planet.getName());
      textArea.setText(planet.generateInfoText(this.activeScanned));
      this.repaint();
    } else if (fleet != null) {
      BufferedImage img = new BufferedImage(ShipImage.MAX_WIDTH,
          ShipImage.MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      if (fleet.getFirstShip() != null) {
        imageLabel.setImage(fleet.getFirstShip().getHull().getImage());
      } else {
        imageLabel.setImage(img);
      }
      setTitle(fleet.getName());
      textArea.setText(fleet.getInfoAsText(fleetOwner, debug));
      this.repaint();
    } else if (tile != null) {
      BufferedImage img = new BufferedImage(ShipImage.MAX_WIDTH,
          ShipImage.MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      if (tile.isStarTile()) {
        Tile star1 = Tiles.getTileByName(TileNames.SUN_NW);
        Tile star2 = Tiles.getTileByName(TileNames.SUN_NE);
        Tile star3 = Tiles.getTileByName(TileNames.SUN_SE);
        Tile star4 = Tiles.getTileByName(TileNames.SUN_SW);
        star1.draw(g2d, 0, 0);
        star2.draw(g2d, 32, 0);
        star3.draw(g2d, 32, 32);
        star4.draw(g2d, 0, 32);
      } else {
        tile.draw(g2d, 16, 16);
      }
      if (tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR1)
          || tile.getName().equals(TileNames.DEEP_SPACE_ANCHOR2)) {
        Icon16x16 icon = Icons.getIconByName(Icons.ICON_STARBASE);
        icon.draw(g2d, 32, 32);
      }
      imageLabel.setImage(img);
      setTitle("Galatic info");
      textArea.setText(tile.getDescription());
      this.repaint();
    } else {
      setTitle("Galactic info");
      BufferedImage img = new BufferedImage(ShipImage.MAX_WIDTH,
          ShipImage.MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      imageLabel.setImage(img);
      textArea.setText("");
      this.repaint();
    }
  }

  /**
   * Set Fix button. Button is always enabled after this.
   */
  public void setFixBtn() {
    fixTradeFleetBtn.setText("Fix fleet");
    fixTradeFleetBtn.setActionCommand(GameCommands.COMMAND_FIX_FLEET);
    fixTradeFleetBtn.setToolTipText("Fix fleet on hull point per turn.");
    fixTradeFleetBtn.setEnabled(true);
  }

  /**
   * Set Trade button. Button is always enabled after this.
   */
  public void setTradeBtn() {
    fixTradeFleetBtn.setText("Trade");
    fixTradeFleetBtn.setActionCommand(GameCommands.COMMAND_TRADE_FLEET);
    fixTradeFleetBtn.setToolTipText("Trade with nearby planet");
    fixTradeFleetBtn.setEnabled(true);
  }

  /**
   * Disable Fix/Trade button.
   */
  public void disableFixTradeBtn() {
    fixTradeFleetBtn.setEnabled(false);
  }

  /**
   * Get Fix/Trade button
   * @return Fix/Trade button
   */
  protected SpaceButton getFixTradeBtn() {
    return fixTradeFleetBtn;
  }
}
