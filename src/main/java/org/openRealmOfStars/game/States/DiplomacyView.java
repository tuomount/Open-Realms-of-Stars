package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.FleetListRenderer;
import org.openRealmOfStars.gui.ListRenderers.SpeechLineRenderer;
import org.openRealmOfStars.gui.ListRenderers.TechListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechFactory;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechLine;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Diplomacy View for between two players: Human and AI
*
*/
public class DiplomacyView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;
  /**
   * Player Info for human
   */
  private PlayerInfo human;
  /**
   * Player Info for AI
   */
  private PlayerInfo ai;

  /**
   * Star Map containing important data for diplomacy
   */
  private StarMap starMap;

  /**
   * Trade which is about to happen
   */
  private DiplomaticTrade trade;

  /**
   * Human tech list offerings
   */
  private JList<Tech> humanTechListOffer;
  /**
   * AI tech list offerings
   */
  private JList<Tech> aiTechListOffer;
  /**
   * Human fleet list offerings
   */
  private JList<Fleet> humanFleetListOffer;
  /**
   * AI fleet list offerings
   */
  private JList<Fleet> aiFleetListOffer;

  /**
   * Human player lines
   */
  private JList<SpeechLine> humanLines;

  /**
   * Human offering a map trade
   */
  private SpaceCheckBox humanMapOffer;

  /**
   * AI offering a map trade
   */
  private SpaceCheckBox aiMapOffer;

  /**
   * Text area for AI talks
   */
  private InfoTextArea infoText;
  /**
   * Diplomacy View constructor
   * @param info1 Human player PlayerInfo
   * @param info2 AI player PlayerInfo
   * @param map StarMap
   * @param listener ActionListener
   */
  public DiplomacyView(final PlayerInfo info1, final PlayerInfo info2,
      final StarMap map, final ActionListener listener) {
    this.setLayout(new BorderLayout());
    human = info1;
    ai = info2;
    starMap = map;
    int humanIndex = starMap.getPlayerList().getIndex(human);
    int aiIndex = starMap.getPlayerList().getIndex(ai);
    trade = new DiplomaticTrade(starMap, humanIndex, aiIndex);
    InfoPanel center = new InfoPanel();
    center.setTitle("Diplomacy with " + ai.getEmpireName());
    center.setLayout(new GridLayout(0, 3));
    InfoPanel humanOffer = new InfoPanel();
    humanOffer.setTitle("Your offer");
    humanOffer.setLayout(new BoxLayout(humanOffer, BoxLayout.Y_AXIS));
    TransparentLabel label = new TransparentLabel(humanOffer,
        "Techs to trade:");
    humanOffer.add(label);
    humanTechListOffer = createTechList(trade.getTradeableTechListForSecond());
    JScrollPane scroll = new JScrollPane(humanTechListOffer);
    humanOffer.add(scroll);
    humanMapOffer = new SpaceCheckBox("Trade map");
    humanOffer.add(humanMapOffer);
    label = new TransparentLabel(humanOffer, "Fleets to trade:");
    humanOffer.add(label);
    humanFleetListOffer = createFleetList(
        trade.getTradeableFleetListForFirst());
    scroll = new JScrollPane(humanFleetListOffer);
    humanOffer.add(scroll);
    center.add(humanOffer);

    InvisiblePanel panel = new InvisiblePanel(center);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    RaceImagePanel aiImg = new RaceImagePanel();
    aiImg.setRaceToShow(ai.getRace().getNameSingle());
    panel.add(aiImg);
    infoText = new InfoTextArea();
    panel.add(infoText);
    SpeechLine[] lines = new SpeechLine[2];
    lines[0] = SpeechFactory.createLine(SpeechType.TRADE, human.getRace());
    lines[1] = SpeechFactory.createLine(SpeechType.MAKE_WAR, human.getRace());
    humanLines = new JList<>(lines);
    humanLines.setCellRenderer(new SpeechLineRenderer());
    humanLines.setBackground(Color.BLACK);
    humanLines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scroll = new JScrollPane(humanLines);
    panel.add(scroll);
    center.add(panel);

    InfoPanel aiOffer = new InfoPanel();
    aiOffer.setTitle(ai.getEmpireName() + " offer");
    aiTechListOffer = createTechList(trade.getTradeableTechListForFirst());
    label = new TransparentLabel(aiOffer, "Techs to trade:");
    aiOffer.add(label);
    scroll = new JScrollPane(aiTechListOffer);
    aiOffer.add(scroll);
    aiMapOffer = new SpaceCheckBox("Trade map");
    aiOffer.add(aiMapOffer);
    label = new TransparentLabel(aiOffer, "Fleets to trade:");
    aiOffer.add(label);
    aiFleetListOffer = createFleetList(trade.getTradeableFleetListForSecond());
    scroll = new JScrollPane(aiFleetListOffer);
    aiOffer.add(scroll);
    center.add(aiOffer);

    this.add(center);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Create Tech List from tech
   * @param techs Which are used for creating Tech List
   * @return JList full of tech
   */
  private JList<Tech> createTechList(final Tech[] techs) {
    JList<Tech> techList = new JList<>(techs);
    techList.setCellRenderer(new TechListRenderer());
    techList.setBackground(Color.BLACK);
    techList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    return techList;
  }

  /**
   * Create Fleet List from fleet array
   * @param fleets Which are used for creating Fleet List
   * @return JList full of fleets
   */
  private JList<Fleet> createFleetList(final Fleet[] fleets) {
    JList<Fleet> fleetList = new JList<>(fleets);
    fleetList.setCellRenderer(new FleetListRenderer());
    fleetList.setBackground(Color.BLACK);
    fleetList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    return fleetList;
  }
  /**
   * Get Diplomatic trade
   * @return DiplomaticTrade
   */
  public DiplomaticTrade getTrade() {
    return trade;
  }
}
