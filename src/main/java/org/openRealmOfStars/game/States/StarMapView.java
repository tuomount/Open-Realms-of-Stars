package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.infopanel.MapInfoPanel;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.mapPanel.PopupPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.MessagePanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.mapTiles.FleetTileInfo;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapMouseListener;
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
 * Star Map view for handling star map
 *
 */
public class StarMapView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Star map to show
   */
  private StarMap map;
  /**
   * Players list
   */
  private PlayerList players;

  /**
   * MapPanel for drawing the star map
   */
  private MapPanel mapPanel;

  /**
   * Info panel next to starMap
   */
  private MapInfoPanel infoPanel;

  /**
   * Mouse listener aka mouse handler for star map.
   */
  private StarMapMouseListener starMapMouseListener;

  /**
   * End Turn Button
   */
  private SpaceButton endTurnButton;

  /**
   * View research Button
   */
  private SpaceButton viewResearchButton;

  /**
   * View space ship stat and designs.
   */
  private SpaceButton viewSpaceShips;

  /**
   * View player stats
   */
  private SpaceButton viewStats;

  /**
   * View news button
   */
  private SpaceButton viewNews;
  /**
   * Credit production
   */
  private IconLabel credProd;

  /**
   * Research production
   */
  private IconLabel reseProd;

  /**
   * Happiness average
   */
  private IconLabel happinessMeter;

  /**
   * Amount of fleets.
   */
  private IconLabel fleetMeter;

  /**
   * Is map ready to move with keys
   */
  private boolean readyToMove;

  /**
   * Message panel for showing messages
   */
  private MessagePanel msgPanel;

  /**
   * Should auto focus on newest message
   */
  private boolean autoFocus;

  /**
   * Main game
   */
  private Game game;

  /**
   * Amount space after research label in pixels.
   */
  private static final int SPACE_AFTER_RESEARCH_LABEL = 100;
  /**
   * Star Map view
   * @param map Star map to view
   * @param players Player List
   * @param game Action Listener and actual game
   */
  public StarMapView(final StarMap map, final PlayerList players,
      final Game game) {
    this.map = map;
    this.players = players;
    this.game = game;
    setAutoFocus(false);

    BlackPanel base = new BlackPanel();
    mapPanel = new MapPanel(false);
    // Side panel
    infoPanel = new MapInfoPanel(game);
    mapPanel.drawMap(this.map);
    starMapMouseListener = new StarMapMouseListener(this.map, mapPanel,
        infoPanel);
    Planet planet = this.map.getPlanetByCoordinate(this.map.getCursorX(),
        this.map.getCursorY());
    if (planet != null) {
      setShowPlanet(planet);
      starMapMouseListener.setLastClickedPlanet(planet);
    }
    mapPanel.addMouseListener(starMapMouseListener);
    mapPanel.addMouseMotionListener(starMapMouseListener);

    // Bottom panel is created here
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setTitle(players.getCurrentPlayerInfo().getEmpireName());
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
    bottomPanel.add(Box.createRigidArea(new Dimension(15, 25)));
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    credProd = new IconLabel(panel, Icons.getIconByName(Icons.ICON_CREDIT),
        ": " + this.players.getCurrentPlayerInfo().getTotalCredits() + "("
            + this.map.getTotalProductionByPlayerPerTurn(
                Planet.PRODUCTION_CREDITS, this.players.getCurrentPlayer())
            + ")");
    credProd.setToolTipText("Amount of credit your realm has"
        + " and how much it is producing in one turn.");
    panel.add(credProd);
    reseProd = new IconLabel(panel, Icons.getIconByName(Icons.ICON_RESEARCH),
        ": " + this.map.getTotalProductionByPlayerPerTurn(
            Planet.PRODUCTION_RESEARCH, this.players.getCurrentPlayer()));
    reseProd.setToolTipText("Amount of research your realm produces in"
        + " one turn.");
    panel.add(reseProd);
    String happiness = "-";
    int happyValue = 0;
    if (!this.players.getCurrentPlayerInfo().getGovernment()
        .isImmuneToHappiness()) {
      happyValue = this.map.calculateAverageHappiness(
          this.players.getCurrentPlayer());
      happiness = Integer.toString(happyValue);
    }
    happinessMeter = new IconLabel(panel, Icons.getHappyFace(happyValue),
        ": " + happiness);
    happinessMeter.setToolTipText("Average happiness of your people.");
    panel.add(happinessMeter);
    fleetMeter = new IconLabel(panel,
        Icons.getIconByName(Icons.ICON_HULL_TECH), ": "
        + String.format("%.0f",
            map.getCurrentPlayerInfo().getFleets().getTotalFleetCapacity())
        + "/" + map.getTotalFleetCapacity(map.getCurrentPlayerInfo()));
    fleetMeter.setToolTipText("Fleet capacity in use."
        + " If fleet size is bigger than capacity,"
        + " over capacity is upkeep for the fleet.");
    panel.add(fleetMeter);
    bottomPanel.add(panel);
    int panelHeight = SPACE_AFTER_RESEARCH_LABEL;
    if (!game.getCurrentResolution().equals("1024x768")) {
      int height = game.getHeight() - SPACE_AFTER_RESEARCH_LABEL;
      int viewPointY = (height / Tile.MAX_HEIGHT - 1) / 2;
      panelHeight = game.getHeight() - (viewPointY * 2 * Tile.MAX_HEIGHT
          + Tile.MAX_HEIGHT);
    }
    bottomPanel.add(Box.createRigidArea(new Dimension(10,
        panelHeight)));

    SpaceGreyPanel bottomBtnPanel = new SpaceGreyPanel();
    bottomBtnPanel.setLayout(new GridLayout(3, 2));

    bottomPanel.add(Box.createRigidArea(new Dimension(10, 5)));

    viewResearchButton = new SpaceButton("Research",
        GameCommands.COMMAND_VIEW_RESEARCH);
    viewResearchButton.addActionListener(game);
    bottomBtnPanel.add(viewResearchButton);

    viewSpaceShips = new SpaceButton("Ships", GameCommands.COMMAND_SHIPS);
    viewSpaceShips.addActionListener(game);
    bottomBtnPanel.add(viewSpaceShips);

    viewStats = new SpaceButton("Stats", GameCommands.COMMAND_VIEW_STATS);
    viewStats.addActionListener(game);
    bottomBtnPanel.add(viewStats);

    viewNews = new SpaceButton("News", GameCommands.COMMAND_NEWS);
    viewNews.addActionListener(game);
    bottomBtnPanel.add(viewNews);
    if (map.getNewsCorpData().getNewsList().length == 0) {
      viewNews.setEnabled(false);
    }
    SpaceButton btn = new SpaceButton("Spying", GameCommands.COMMAND_SPY);
    btn.addActionListener(game);
    bottomBtnPanel.add(btn);
    btn = new SpaceButton("Planets", GameCommands.COMMAND_SHOW_PLANET_LIST);
    btn.addActionListener(game);
    bottomBtnPanel.add(btn);
    btn = new SpaceButton("Vote", GameCommands.COMMAND_VIEW_VOTING);
    btn.addActionListener(game);
    bottomBtnPanel.add(btn);
    // Button for debugging battle, disabled for now
/*    btn = new SpaceButton("Battle", GameCommands.COMMAND_BATTLE);
    btn.addActionListener(game);
    bottomBtnPanel.add(btn);*/
    // Button for debugging history view
/*    btn = new SpaceButton("History", GameCommands.COMMAND_SHOW_HISTORY);
    btn.addActionListener(game);
    bottomBtnPanel.add(btn);*/

    bottomPanel.add(bottomBtnPanel);

    msgPanel = new MessagePanel(GameCommands.COMMAND_PREV_MSG,
        GameCommands.COMMAND_NEXT_MSG, GameCommands.COMMAND_FOCUS_MSG,
        players.getCurrentPlayerInfo().getMsgList().getMsg(),
        players.getCurrentPlayerInfo().getMsgList().getCurrentMsgIndex(),
        players.getCurrentPlayerInfo().getMsgList().getMaxMsg(), game);
    bottomPanel.add(msgPanel);

    bottomPanel.add(Box.createRigidArea(new Dimension(10, 5)));

    endTurnButton = new SpaceButton(
        "\t\n\t\nEnd Turn \n" + this.map.getTurn() + "\t\n\t\n\t\n",
        GameCommands.COMMAND_END_TURN);
    endTurnButton.addActionListener(game);
    bottomPanel.add(endTurnButton);

    base.setLayout(new BorderLayout());
    base.add(mapPanel, BorderLayout.CENTER);
    base.add(infoPanel, BorderLayout.EAST);
    base.add(bottomPanel, BorderLayout.SOUTH);
    this.setLayout(new BorderLayout());
    this.add(base, BorderLayout.CENTER);

    Message msg = players.getCurrentPlayerInfo().getMsgList().getMsg();
    msgPanel.updatePanel(msg,
        players.getCurrentPlayerInfo().getMsgList().getCurrentMsgIndex(),
        players.getCurrentPlayerInfo().getMsgList().getMaxMsg());
    setAutoFocus(true);

  }

  /**
   * Get Star Map mouse listener
   * @return The map mouse listener
   */
  public StarMapMouseListener getStarMapMouseListener() {
    return starMapMouseListener;
  }

  /**
   * Show planet info on map info panel
   * @param planet to show
   */
  public void setShowPlanet(final Planet planet) {
    infoPanel.showPlanet(planet, true);
  }

  /**
   * Show fleet info on map info panel
   * @param fleet to show
   */
  public void setShowFleet(final Fleet fleet) {
    FleetTileInfo[][] tiles = map.getFleetTiles();
    FleetTileInfo tileInfo = tiles[fleet.getX()][fleet.getY()];
    if (tileInfo != null) {
      PlayerInfo owner = players.getPlayerInfoByIndex(
          tileInfo.getPlayerIndex());
      infoPanel.showFleet(fleet, owner, map.isDebug());
      starMapMouseListener.handleFixTradeButton(fleet, owner);
    } else {
      infoPanel.showEmpty();
    }
  }

  /**
   * Update panels on StarMapView
   */
  public void updatePanels() {
    credProd.setText(
        ": " + this.players.getCurrentPlayerInfo().getTotalCredits() + "("
        + this.map.getTotalProductionByPlayerPerTurn(
            Planet.PRODUCTION_CREDITS, this.players.getCurrentPlayer())
        + ")");
    infoPanel.updatePanel(map.isDebug());
  }

  /**
   * Update message panel
   */
  public void updateMessagePanel() {
    Message msg = players.getCurrentPlayerInfo().getMsgList().getMsg();
    if (msg.getX() != -1 && msg.getY() != -1) {
      map.setCursorPos(msg.getX(), msg.getY());
      map.setDrawPos(msg.getX(), msg.getY());
    }
    msgPanel.updatePanel(msg,
        players.getCurrentPlayerInfo().getMsgList().getCurrentMsgIndex(),
        players.getCurrentPlayerInfo().getMsgList().getMaxMsg());
  }

  /**
   * Handle actions for Star Map view
   * @param arg0 Action Event
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (starMapMouseListener != null) {
        starMapMouseListener.updateScrollingIfOnBorder();
      }
      if (mapPanel.getRoute() == null && infoPanel.getFleetShowing() != null
          && infoPanel.getFleetOwner() == players.getCurrentPlayerInfo()) {
        mapPanel.setRoute(infoPanel.getFleetShowing().getRoute());
      }
      mapPanel.drawMap(this.map);
      mapPanel.repaint();
      readyToMove = true;
    }
    if (getPopup() != null) {
      // Exit immediately if popup is visible
      return;
    }
    // Star map
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_MSG)) {
      Message msg = players.getCurrentPlayerInfo().getMsgList()
          .getPrevMessage();
      if (msg.getX() != -1 && msg.getY() != -1) {
        map.setCursorPos(msg.getX(), msg.getY());
        map.setDrawPos(msg.getX(), msg.getY());
        game.focusOnMessage(true);
      }
      SoundPlayer.playMenuSound();
      msgPanel.updatePanel(msg,
          players.getCurrentPlayerInfo().getMsgList().getCurrentMsgIndex(),
          players.getCurrentPlayerInfo().getMsgList().getMaxMsg());
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_MSG)) {
      Message msg = players.getCurrentPlayerInfo().getMsgList()
          .getNextMessage();
      if (msg.getX() != -1 && msg.getY() != -1) {
        map.setCursorPos(msg.getX(), msg.getY());
        map.setDrawPos(msg.getX(), msg.getY());
        game.focusOnMessage(true);
      }
      SoundPlayer.playMenuSound();
      msgPanel.updatePanel(msg,
          players.getCurrentPlayerInfo().getMsgList().getCurrentMsgIndex(),
          players.getCurrentPlayerInfo().getMsgList().getMaxMsg());
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_DEFEND_SECTOR)
        && getStarMapMouseListener().getLastClickedFleet() != null
        && infoPanel.getFleetOwner() == players.getCurrentPlayerInfo()) {
      SoundPlayer.playMenuSound();
      SoundPlayer.playShieldSound();
      Fleet fleet = getStarMapMouseListener().getLastClickedFleet();
      // Make fleet to defend
      fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(),
          fleet.getY(), Route.ROUTE_DEFEND));
      infoPanel.updatePanel(map.isDebug());
    }
    if (arg0.getActionCommand().equalsIgnoreCase(GameCommands.COMMAND_FIX_FLEET)
        && getStarMapMouseListener().getLastClickedFleet() != null
        && infoPanel.getFleetOwner() == players.getCurrentPlayerInfo()) {
      SoundPlayer.playSound(SoundPlayer.REPAIR);
      Fleet fleet = getStarMapMouseListener().getLastClickedFleet();
      // Make fleet to fix itself
      fleet.setRoute(new Route(fleet.getX(), fleet.getY(), fleet.getX(),
          fleet.getY(), Route.ROUTE_FIX));
      infoPanel.updatePanel(map.isDebug());
    }
    if (arg0.getActionCommand()
        .equalsIgnoreCase(GameCommands.COMMAND_ROUTE_FLEET)
        && getStarMapMouseListener().getLastClickedFleet() != null
        && infoPanel.getFleetOwner() == players.getCurrentPlayerInfo()) {
      SoundPlayer.playMenuSound();
      SoundPlayer.playSound(SoundPlayer.WARP_ENGINE_ENGAGE);
      getStarMapMouseListener().setRoutePlanning(true);
    }

  }

  /**
   * Is auto focus enabled
   * @return True if auto focus is enabled
   */
  public boolean isAutoFocus() {
    return autoFocus;
  }

  /**
   * Set autofocus. If autofocus is enabled then messages will
   * be automatically focused when changed.
   * @param autoFocus true to enable auto focus
   */
  public void setAutoFocus(final boolean autoFocus) {
    this.autoFocus = autoFocus;
  }

  /**
   * Get End Turn button
   * @return SpaceButton
   */
  public SpaceButton getEndTurnButton() {
    return endTurnButton;
  }

  /**
   * Get Credits production label
   * @return IconLabel
   */
  public IconLabel getCredProd() {
    return credProd;
  }

  /**
   * Get Research production label
   * @return IconLabel
   */
  public IconLabel getReseProd() {
    return reseProd;
  }

  /**
   * Is map ready to move with keys.
   * @return True if map has already moved one sector on previous drawn
   */
  public boolean getReadyToMove() {
    return readyToMove;
  }

  /**
   * Set Map Ready to move flag. This should be set only if map has been
   * drawn after move.
   * @param readyToMove true if map can move when pressing arrow keys.
   */
  public void setReadyToMove(final boolean readyToMove) {
    this.readyToMove = readyToMove;
  }

  /**
   * Set popup on top of map
   * @param popup PopupPanel
   */
  public void setPopup(final PopupPanel popup) {
    mapPanel.setPopup(popup);
  }

  /**
   * Get popup panel.
   * @return PopupPanel or null
   */
  public PopupPanel getPopup() {
    return mapPanel.getPopup();
  }

}
