package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.FleetListRenderer;
import org.openRealmOfStars.gui.ListRenderers.PlanetListRenderer;
import org.openRealmOfStars.gui.ListRenderers.SpeechLineRenderer;
import org.openRealmOfStars.gui.ListRenderers.TechListRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechFactory;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechLine;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

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
   * Human planet list offerings
   */
  private JList<Planet> humanPlanetListOffer;
  /**
   * AI planet list offerings
   */
  private JList<Planet> aiPlanetListOffer;

  /**
   * Human credit offering UI component
   */
  private WorkerProductionPanel humanCreditOffer;
  /**
   * Actual human credit offering
   */
  private int humanCredits;
  /**
   * AI credit offering UI component
   */
  private WorkerProductionPanel aiCreditOffer;
  /**
   * Actual ai credit offering
   */
  private int aiCredits;
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
   * Label indicating if AI player likeness value of human player.
   */
  private TransparentLabel likenessLabel;

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
    humanCredits = 0;
    aiCredits = 0;
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
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(label);
    humanTechListOffer = createTechList(trade.getTradeableTechListForSecond());
    JScrollPane scroll = new JScrollPane(humanTechListOffer);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(scroll);
    humanMapOffer = new SpaceCheckBox("Trade map");
    humanMapOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(humanMapOffer);
    label = new TransparentLabel(humanOffer, "Fleets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(label);
    humanFleetListOffer = createFleetList(
        trade.getTradeableFleetListForFirst());
    scroll = new JScrollPane(humanFleetListOffer);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(scroll);
    label = new TransparentLabel(humanOffer, "Planets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(label);
    humanPlanetListOffer = createPlanetList(
        trade.getTradeablePlanetListForFirst());
    scroll = new JScrollPane(humanPlanetListOffer);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(scroll);
    humanCreditOffer = new WorkerProductionPanel(humanOffer,
        GameCommands.COMMAND_MINUS_HUMAN_CREDIT,
        GameCommands.COMMAND_PLUS_HUMAN_CREDIT, Icons.ICON_CREDIT, "0 Credits",
        "How much credits you are offering.", listener);
    humanCreditOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(humanCreditOffer);
    center.add(humanOffer);

    InvisiblePanel panel = new InvisiblePanel(center);
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    RaceImagePanel aiImg = new RaceImagePanel();
    aiImg.setRaceToShow(ai.getRace().getNameSingle());
    aiImg.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(aiImg);
    likenessLabel = new TransparentLabel(panel, ai.getDiplomacy()
        .getLikingAsString(humanIndex));
    likenessLabel.setForeground(ai.getDiplomacy().getLikingAsColor(humanIndex));
    likenessLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(likenessLabel);
    infoText = new InfoTextArea();
    infoText.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(infoText);
    SpeechLine[] lines = createOfferLines();
    humanLines = new JList<>(lines);
    humanLines.setCellRenderer(new SpeechLineRenderer());
    humanLines.setBackground(Color.BLACK);
    humanLines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scroll = new JScrollPane(humanLines);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(scroll);
    SpaceButton acceptButton = new SpaceButton("Make an offer",
        GameCommands.COMMAND_OK);
    acceptButton.addActionListener(listener);
    acceptButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(acceptButton);
    center.add(panel);

    InfoPanel aiOffer = new InfoPanel();
    aiOffer.setTitle(ai.getEmpireName() + " offer");
    aiTechListOffer = createTechList(trade.getTradeableTechListForFirst());
    label = new TransparentLabel(aiOffer, "Techs to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(label);
    scroll = new JScrollPane(aiTechListOffer);
    aiOffer.add(scroll);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiMapOffer = new SpaceCheckBox("Trade map");
    aiMapOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(aiMapOffer);
    label = new TransparentLabel(aiOffer, "Fleets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(label);
    aiFleetListOffer = createFleetList(trade.getTradeableFleetListForSecond());
    scroll = new JScrollPane(aiFleetListOffer);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(scroll);
    label = new TransparentLabel(aiOffer, "Planets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(label);
    aiPlanetListOffer = createPlanetList(
        trade.getTradeablePlanetListForSecond());
    scroll = new JScrollPane(aiPlanetListOffer);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(scroll);
    aiCreditOffer = new WorkerProductionPanel(aiOffer,
        GameCommands.COMMAND_MINUS_AI_CREDIT,
        GameCommands.COMMAND_PLUS_AI_CREDIT, Icons.ICON_CREDIT, "0 Credits",
        "How much credits " + ai.getEmpireName() + "is offering.", listener);
    aiCreditOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(aiCreditOffer);
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
    updatePanel();
  }

  /**
   * Create regular offer lines according the current diplomacy relationship
   * between AI and human player.
   * @return SpeechLines for human players
   */
  private SpeechLine[] createOfferLines() {
    int humanIndex = starMap.getPlayerList().getIndex(human);
    ArrayList<SpeechLine> speechLines = new ArrayList<>();
    speechLines.add(SpeechFactory.createLine(SpeechType.TRADE,
        human.getRace()));
    speechLines.add(SpeechFactory.createLine(SpeechType.DEMAND,
        human.getRace()));
    if (ai.getDiplomacy().isTradeAlliance(humanIndex)) {
      speechLines.add(SpeechFactory.createLine(SpeechType.ALLIANCE,
          human.getRace()));
    } else if (ai.getDiplomacy().isAlliance(humanIndex)) {
      speechLines.add(SpeechFactory.createLine(SpeechType.TRADE_ALLIANCE,
          human.getRace()));
    } else {
      speechLines.add(SpeechFactory.createLine(SpeechType.TRADE_ALLIANCE,
          human.getRace()));
    }
    if (!ai.getDiplomacy().isWar(humanIndex)) {
      speechLines.add(SpeechFactory.createLine(SpeechType.MAKE_WAR,
          human.getRace()));
    }
    SpeechLine[] lines = new SpeechLine[speechLines.size()];
    for (int i = 0; i < lines.length; i++) {
      lines[i] = speechLines.get(i);
    }
    return lines;
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
   * Create Planet List from planet array
   * @param planets Which are used for creating planet List
   * @return JList full of planets
   */
  private JList<Planet> createPlanetList(final Planet[] planets) {
    JList<Planet> planetList = new JList<>(planets);
    planetList.setCellRenderer(new PlanetListRenderer());
    planetList.setBackground(Color.BLACK);
    planetList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    return planetList;
  }
  /**
   * Get Diplomatic trade
   * @return DiplomaticTrade
   */
  public DiplomaticTrade getTrade() {
    return trade;
  }

  /**
   * Get currently trade offer about AI credits
   * @return Ai credits on offer
   */
  public int getAiCredits() {
    return aiCredits;
  }
  /**
   * Get currently trade offer about Human credits
   * @return Human credits on offer
   */
  public int getHumanCredits() {
    return humanCredits;
  }

  /**
   * Update panel's texts
   */
  public void updatePanel() {
    int humanIndex = starMap.getPlayerList().getIndex(human);
    String text = ai.getDiplomacy().getLikingAsString(humanIndex);
    String relation = ai.getDiplomacy().getDiplomaticRelation(humanIndex);
    if (!relation.isEmpty()) {
      text = text + " " + relation;
    }
    likenessLabel.setText(text);
    likenessLabel.setForeground(ai.getDiplomacy().getLikingAsColor(humanIndex));
    SpeechType type = SpeechType.NEUTRAL_GREET;
    switch (ai.getDiplomacy().getLiking(humanIndex)) {
    case Diplomacy.DISLIKE: type = SpeechType.DISLIKE_GREET; break;
    case Diplomacy.LIKE: type = SpeechType.LIKE_GREET; break;
    case Diplomacy.HATE: type = SpeechType.HATE_GREET; break;
    case Diplomacy.FRIENDS: type = SpeechType.FRIENDS_GREET; break;
    case Diplomacy.NEUTRAL: type = SpeechType.NEUTRAL_GREET; break;
    default: type = SpeechType.NEUTRAL_GREET; break;
    }
    text = SpeechFactory.createLine(type, ai.getRace()).getLine();
    infoText.setText(text);
  }

  /**
   * Handle events for DiplomacyView.
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (GameCommands.COMMAND_MINUS_HUMAN_CREDIT.equals(
        arg0.getActionCommand())) {
      if (humanCredits > 0) {
        humanCredits--;
      }
      humanCreditOffer.setText(humanCredits + " Credits");
    }
    if (GameCommands.COMMAND_PLUS_HUMAN_CREDIT.equals(
        arg0.getActionCommand())) {
      if (humanCredits < human.getTotalCredits()) {
        humanCredits++;
      }
      humanCreditOffer.setText(humanCredits + " Credits");
    }
    if (GameCommands.COMMAND_MINUS_AI_CREDIT.equals(arg0.getActionCommand())) {
      if (aiCredits > 0) {
        aiCredits--;
      }
      aiCreditOffer.setText(aiCredits + " Credits");
    }
    if (GameCommands.COMMAND_PLUS_AI_CREDIT.equals(arg0.getActionCommand())) {
      if (aiCredits < ai.getTotalCredits()) {
        aiCredits++;
      }
      aiCreditOffer.setText(aiCredits + " Credits");
    }
  }
}
