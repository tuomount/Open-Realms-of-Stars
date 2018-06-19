package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.history.HistoryTurn;
import org.openRealmOfStars.starMap.history.event.CombatEvent;
import org.openRealmOfStars.starMap.history.event.DiplomaticEvent;
import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;
import org.openRealmOfStars.starMap.history.event.PlayerStartEvent;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2018  Tuomo Untinen
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
 * History view showing history events
 *
 */
public class HistoryView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Starmap containing history and starmap itself
   */
  private StarMap map;

  /**
   * Map Panel handling for map drawing
   */
  private MapPanel mapPanel;

  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Space label containing turn and event info
   */
  private SpaceLabel turnLabel;

  /**
   * Turn Number
   */
  private int turnNumber;

  /**
   * Event number
   */
  private int eventNumber;

  /**
   * Target coordinate
   */
  private Coordinate targetCoordinate;

  /**
   * Constructor for history view
   * @param starMap StarMap containing the history
   * @param listener ActionListener
   */
  public HistoryView(final StarMap starMap, final ActionListener listener) {
    map = starMap;
    turnNumber = 0;
    eventNumber = 0;
    targetCoordinate = null;
    this.setLayout(new BorderLayout());
    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setLayout(new BorderLayout());
    centerPanel.setTitle("History");
    mapPanel = new MapPanel(false);
    mapPanel.setHistoryCultures(starMap.getHistory().calculateCulture(0,
        starMap));
    centerPanel.add(mapPanel, BorderLayout.CENTER);
    InfoPanel infoPanel = new InfoPanel();
    infoPanel.setLayout(new BorderLayout());
    infoPanel.setTitle("History Event");
    textArea = new InfoTextArea(5, 10);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    textArea.setFont(GuiStatics.getFontCubellanSmaller());
    textArea.setCharacterWidth(8);
    infoPanel.add(textArea, BorderLayout.CENTER);
    EmptyInfoPanel panel = new EmptyInfoPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    IconButton iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_YEAR,
        this);
    iBtn.setToolTipText("Previous turn");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_EVENT,
        this);
    iBtn.setToolTipText("Jump to first event on current turn.");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    turnLabel = new SpaceLabel("Turn 1000/1000 Event 1000/1000");
    panel.add(turnLabel);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_EVENT,
        this);
    iBtn.setToolTipText("Move to next event or"
        + " next turn if event is last event on this turn.");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_YEAR,
        this);
    iBtn.setToolTipText("Next turn");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    EmptyInfoPanel panel2 = new EmptyInfoPanel();
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
    panel2.add(panel);
    infoPanel.add(panel2, BorderLayout.SOUTH);
    centerPanel.add(infoPanel, BorderLayout.SOUTH);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Done with history",
        GameCommands.COMMAND_DONE_HISTORY);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);


    // Add panels to base
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
    // Update screen
    updateTurnLabel();
    updateTextArea();

  }

  /**
   * Update turn label with current turn number and event number.
   */
  public void updateTurnLabel() {
    int historyTurns = map.getHistory().getLatestTurn().getTurn();
    HistoryTurn turn = map.getHistory().getByIndex(turnNumber);
    int events = turn.getNumberOfTextualEvents();
    String text = "Turn " + turn.getTurn() + "/" + historyTurns;
    text = text + " Event " + turn.getEventNumber(eventNumber) + "/" + events;
    turnLabel.setText(text);
  }

  /**
   * Update text area with historical information
   */
  public void updateTextArea() {
    HistoryTurn turn = map.getHistory().getByIndex(turnNumber);
    Event event = turn.getEvent(eventNumber);
    if (event != null) {
      if (event.getType() == EventType.DIPLOMATIC_RELATION_CHANGE) {
        DiplomaticEvent diplomatic = (DiplomaticEvent) event;
        String startText;
        if (diplomatic.getCoordinate().getX() == 0
            && diplomatic.getCoordinate().getY() == 0) {
          targetCoordinate = null;
          startText = "Diplomatic meeting happened somewhere in deep and"
              + " cold space. ";
        } else {
          targetCoordinate = diplomatic.getCoordinate();
          startText = "Diplomatic meeting happened somewhere in deep space. ";
          if (diplomatic.getPlanetName() != null) {
            startText = "Diplomatic meeting in planet called "
                + diplomatic.getPlanetName() + ". ";
          }
        }
        textArea.setText(startText + diplomatic.getText());
      }
      if (event.getType() == EventType.GALATIC_NEWS) {
        GalacticEvent galactic = (GalacticEvent) event;
        textArea.setText(galactic.getText());
        targetCoordinate = null;
      }
      if (event.getType() == EventType.PLANET_COLONIZED
          || event.getType() == EventType.PLANET_CONQUERED) {
        EventOnPlanet planetary = (EventOnPlanet) event;
        textArea.setText("Event on planet called " + planetary.getName() + ": "
            + planetary.getText());
        targetCoordinate = planetary.getCoordinate();
      }
      if (event.getType() == EventType.SPACE_COMBAT) {
        CombatEvent combat = (CombatEvent) event;
        if (combat.getPlanetName() != null) {
          textArea.setText("Combat happened at orbit of "
              + combat.getPlanetName() + ". " + combat.getText());
        } else {
          textArea.setText("Combat happened in deep space. "
              + combat.getText());
        }
        targetCoordinate = combat.getCoordinate();
      }
      if (event.getType() == EventType.PLAYER_START) {
        PlayerStartEvent start = (PlayerStartEvent) event;
        PlayerInfo info = map.getPlayerList().getPlayerInfoByIndex(
            start.getPlayerIndex());
        textArea.setText(info.getEmpireName() + " starts from "
            + start.getName() + "!");
        targetCoordinate = start.getCoordinate();
      }
      PlayerInfo[] infos = map.getPlayerList().findRealmNamesFromText(
          textArea.getText());
      if (event.getType() == EventType.GALATIC_NEWS) {
        if (infos.length == 1) {
          mapPanel.setLeftSpaceImage(GuiStatics.IMAGE_ANDROID);
          mapPanel.setRightSpaceImage(infos[0].getRace().getRaceImage());
        } else if (infos.length >= 2) {
          mapPanel.setLeftSpaceImage(infos[0].getRace().getRaceImage());
          mapPanel.setRightSpaceImage(infos[1].getRace().getRaceImage());
        } else {
          mapPanel.setLeftSpaceImage(GuiStatics.IMAGE_ANDROID);
        }
      } else {
        if (infos.length >= 1) {
          mapPanel.setLeftSpaceImage(infos[0].getRace().getRaceImage());
        } else {
          mapPanel.setLeftSpaceImage(null);
        }
        if (infos.length >= 2) {
          mapPanel.setRightSpaceImage(infos[1].getRace().getRaceImage());
        } else {
          mapPanel.setRightSpaceImage(null);
        }
      }
    }
  }

  /**
   * Change turn in history view. This method can increase and
   * decrease the turn. It jumps to turn where happened
   * something interesting.
   * @param increase If true it will increase the turn.
   *        With false it will decrease the turn.
   */
  private void changeTurn(final boolean increase) {
    boolean done = false;
    do {
      if (increase) {
        if (turnNumber < map.getHistory().numberOfTurns() - 1) {
          turnNumber++;
          eventNumber = 0;
        } else {
          done = true;
        }
      } else {
        if (turnNumber > 0) {
          turnNumber--;
          eventNumber = 0;
        } else {
          done = true;
        }
      }
      HistoryTurn turn = map.getHistory().getByIndex(turnNumber);
      if (turn != null && turn.getNumberOfTextualEvents() > 0) {
        done = true;
      }
    } while (!done);
  }
  /**
   * Handle actions for History view
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (targetCoordinate != null) {
        mapPanel.getHistoryCoordinates().moveTowards(targetCoordinate);
      }
      mapPanel.drawHistoryMap(this.map);
      mapPanel.repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_YEAR)
        && turnNumber > 0) {
      SoundPlayer.playMenuSound();
      changeTurn(false);
      mapPanel.setHistoryCultures(map.getHistory().calculateCulture(
          turnNumber, map));
      updateTurnLabel();
      updateTextArea();
      turnLabel.repaint();
      textArea.repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_YEAR)
        && turnNumber < map.getHistory().numberOfTurns() - 1) {
      SoundPlayer.playMenuSound();
      changeTurn(true);
      mapPanel.setHistoryCultures(map.getHistory().calculateCulture(
          turnNumber, map));
      updateTurnLabel();
      updateTextArea();
      turnLabel.repaint();
      textArea.repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_EVENT)) {
      SoundPlayer.playMenuSound();
      eventNumber = 0;
      updateTurnLabel();
      updateTextArea();
      turnLabel.repaint();
      textArea.repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_EVENT)) {
      SoundPlayer.playMenuSound();
      HistoryTurn turn = map.getHistory().getByIndex(turnNumber);
      int originalEventNumber = eventNumber;
      if (turn != null) {
        eventNumber++;
        Event event = turn.getEvent(eventNumber);
        while (event != null && event.getType() == EventType.CULTURE_CHANGE) {
          eventNumber++;
          event = turn.getEvent(eventNumber);
        }
        if (event == null) {
          if (turnNumber < map.getHistory().numberOfTurns() - 1) {
            changeTurn(true);
            mapPanel.setHistoryCultures(map.getHistory().calculateCulture(
                turnNumber, map));
            updateTurnLabel();
            updateTextArea();
            turnLabel.repaint();
            textArea.repaint();
          } else {
            eventNumber = originalEventNumber;
          }
        } else {
          updateTurnLabel();
          updateTextArea();
          turnLabel.repaint();
          textArea.repaint();
        }
      }
    }
  }

  /**
   * Get Turn number from view
   * @return Turn number
   */
  public int getTurn() {
    return turnNumber;
  }

  /**
   * Get Event number from view
   * @return Turn number
   */
  public int getEventNumber() {
    return eventNumber;
  }

}
