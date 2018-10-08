package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.audio.music.MusicPlayer;
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
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ShipInteriorPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.panels.WorkerProductionPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationList;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationOffer;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechFactory;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechLine;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
   * Human player starts diplomacy view in regular way
   */
  public static final int HUMAN_REGULAR = 0;
  /**
   * Human player starts diplomacy view so that AI's ship
   * has crossed the border
   */
  public static final int HUMAN_BORDER_CROSS = 1;
  /**
   * AI player starts diplomacy view in regular way
   */
  public static final int AI_REGULAR = 2;
  /**
   * AI player starts diplomacy view so that Human's ship
   * has crossed the border
   */
  public static final int AI_BORDER_CROSS = 3;
  /**
   * Human player starts diplomacy view so that AI's espionage ship
   * has crossed the border
   */
  public static final int HUMAN_ESPIONAGE = 4;
  /**
   * AI player starts diplomacy view so that Human's espionage ship
   * has crossed the border
   */
  public static final int AI_ESPIONAGE = 5;
  /**
   * Player Info for human
   */
  private PlayerInfo human;

  /**
   * Fleet which has crossed the border
   */
  private Fleet borderCrossedFleet;
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
  private SpaceLabel likenessLabel;

  /**
   * Button for exiting the diplomacy view.
   */
  private SpaceButton endBtn;

  /**
   * Meeting place for news generator
   */
  private Object meetingPlace;

  /**
   * Trade actually happened
   */
  private boolean tradeHappened;

  /**
   * AI's image on diplomacy screen;
   */
  private ShipInteriorPanel aiImg;
  /**
   * Text counter how many screen current text has been showed.
   */
  private int textCounter;
  /**
   * Last shown speech type.
   */
  private SpeechType lastSpeechType;
  /**
   * Embargo line
   */
  private SpeechLine embargoLine;
  /**
   * Diplomacy View constructor
   * @param info1 Human player PlayerInfo
   * @param info2 AI player PlayerInfo
   * @param map StarMap
   * @param startType There are four choices:
   *        HUMAN_REGULAR
   *        AI_REGULAR
   *        HUMAN_BORDER_CROSS
   *        AI_BORDER_CROSS
   *        AI_ESPIONAGE
   *        HUMAN_ESPIONAGE
   * @param fleet Fleet which has crossed the border. Can be null
   * @param planet Planet where meeting happended. Can be null
   * @param listener ActionListener
   */
  public DiplomacyView(final PlayerInfo info1, final PlayerInfo info2,
      final StarMap map, final int startType, final Fleet fleet,
      final Planet planet, final ActionListener listener) {
    this.setLayout(new BorderLayout());
    human = info1;
    borderCrossedFleet = fleet;
    tradeHappened = false;
    embargoLine = null;
    ai = info2;
    if (MusicPlayer.getNowPlaying() != ai.getRace().getDiplomacyMusic()) {
      MusicPlayer.play(ai.getRace().getDiplomacyMusic());
    }
    starMap = map;
    humanCredits = 0;
    aiCredits = 0;
    meetingPlace = null;
    if (fleet != null) {
      meetingPlace = fleet;
    }
    if (planet != null) {
      meetingPlace = planet;
    }
    int humanIndex = starMap.getPlayerList().getIndex(human);
    int aiIndex = starMap.getPlayerList().getIndex(ai);
    if (startType == AI_REGULAR || startType == AI_BORDER_CROSS
        || startType == AI_ESPIONAGE) {
      trade = new DiplomaticTrade(starMap, aiIndex, humanIndex);
      if (startType == AI_BORDER_CROSS) {
        ai.getDiplomacy().getDiplomacyList(humanIndex).addBonus(
            DiplomacyBonusType.BORDER_CROSSED, ai.getRace());
      }
      if (startType == AI_ESPIONAGE) {
        ai.getDiplomacy().getDiplomacyList(humanIndex).addBonus(
            DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, ai.getRace());
      }
    } else {
      trade = new DiplomaticTrade(starMap, humanIndex, aiIndex);
    }
    InfoPanel center = new InfoPanel();
    center.setTitle("Diplomacy with " + ai.getEmpireName());
    center.setLayout(new GridLayout(0, 3));
    InfoPanel humanOffer = new InfoPanel();
    humanOffer.setTitle("Your offer");
    humanOffer.setLayout(new BoxLayout(humanOffer, BoxLayout.Y_AXIS));
    SpaceLabel label = new SpaceLabel("Techs to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(label);
    humanTechListOffer = createTechList(trade.getTradeableTechListForSecond());
    JScrollPane scroll = new JScrollPane(humanTechListOffer);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(scroll);
    humanMapOffer = new SpaceCheckBox("Trade map");
    humanMapOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(humanMapOffer);
    label = new SpaceLabel("Fleets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(label);
    humanFleetListOffer = createFleetList(
        trade.getTradeableFleetListForFirst());
    scroll = new JScrollPane(humanFleetListOffer);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(scroll);
    label = new SpaceLabel("Planets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(label);
    humanPlanetListOffer = createPlanetList(
        trade.getTradeablePlanetListForFirst());
    scroll = new JScrollPane(humanPlanetListOffer);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(scroll);
    humanCreditOffer = new WorkerProductionPanel(
        GameCommands.COMMAND_MINUS_HUMAN_CREDIT,
        GameCommands.COMMAND_PLUS_HUMAN_CREDIT, Icons.ICON_CREDIT, "0 Credits",
        "How much credits you are offering.", listener);
    humanCreditOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    humanOffer.add(humanCreditOffer);
    center.add(humanOffer);

    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    BufferedImage planetImage = null;
    if (planet != null) {
      planetImage = planet.getBigImage();
    }
    aiImg = new ShipInteriorPanel(ai.getRace(), planetImage);
    aiImg.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(aiImg);
    likenessLabel = new SpaceLabel("Friends for ever Defensive pact");
    likenessLabel.setForeground(ai.getDiplomacy().getLikingAsColor(humanIndex));
    likenessLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(likenessLabel);
    infoText = new InfoTextArea();
    infoText.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoText.setLineWrap(true);
    infoText.setCharacterWidth(8);
    infoText.setEditable(false);
    panel.add(infoText);
    SpeechLine[] lines = createOfferLines(startType);
    humanLines = new JList<>(lines);
    humanLines.setCellRenderer(new SpeechLineRenderer());
    humanLines.setBackground(Color.BLACK);
    humanLines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scroll = new JScrollPane(humanLines);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
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
    label = new SpaceLabel("Techs to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(label);
    scroll = new JScrollPane(aiTechListOffer);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    aiOffer.add(scroll);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiMapOffer = new SpaceCheckBox("Trade map");
    aiMapOffer.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(aiMapOffer);
    label = new SpaceLabel("Fleets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(label);
    aiFleetListOffer = createFleetList(trade.getTradeableFleetListForSecond());
    scroll = new JScrollPane(aiFleetListOffer);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(scroll);
    label = new SpaceLabel("Planets to trade:");
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(label);
    aiPlanetListOffer = createPlanetList(
        trade.getTradeablePlanetListForSecond());
    scroll = new JScrollPane(aiPlanetListOffer);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    aiOffer.add(scroll);
    aiCreditOffer = new WorkerProductionPanel(
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
    endBtn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    endBtn.addActionListener(listener);
    bottomPanel.add(endBtn, BorderLayout.CENTER);
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
    if (startType == AI_REGULAR) {
      trade.generateOffer();
      endBtn.setEnabled(false);
      setOfferingList(startType);
      updatePanel(trade.getSpeechTypeByOffer());
      humanLines.setListData(createOfferLines(AI_REGULAR));
      if (trade.getSpeechTypeByOffer() == SpeechType.NOTHING_TO_TRADE) {
        // AI did not have anything to trade, maybe human has
        resetChoices();
      }
    } else if (startType == AI_BORDER_CROSS) {
      endBtn.setEnabled(false);
      trade.generateOffer();
      setOfferingList(startType);
      updatePanel(SpeechType.ASK_MOVE_FLEET);
    } else if (startType == AI_ESPIONAGE) {
      endBtn.setEnabled(false);
      trade.generateOffer();
      setOfferingList(startType);
      updatePanel(SpeechType.ASK_MOVE_SPY);
    } else if (startType == HUMAN_BORDER_CROSS) {
      trade.generateOffer();
      setOfferingList(startType);
      updatePanel(getGreetLine());
    } else if (startType == HUMAN_ESPIONAGE) {
      trade.generateOffer();
      setOfferingList(startType);
      updatePanel(getGreetLine());
    } else {
      updatePanel(getGreetLine());
    }
  }

  /**
   * Create regular offer lines according the current diplomacy relationship
   * between AI and human player.
   * @param startType There are four choices:
   *        HUMAN_REGULAR
   *        AI_REGULAR
   *        HUMAN_BORDER_CROSS
   *        AI_BORDER_CROSS
   * @return SpeechLines for human players
   */
  private SpeechLine[] createOfferLines(final int startType) {
    int humanIndex = starMap.getPlayerList().getIndex(human);
    ArrayList<SpeechLine> speechLines = new ArrayList<>();
    if (startType == AI_REGULAR) {
      if (trade.getFirstOffer() != null
          && trade.getFirstOffer().isWarInOffer()) {
        speechLines.add(SpeechFactory.createAgreeWithWarLine(
            human.getRace()));
      } else {
        speechLines.add(SpeechFactory.createLine(SpeechType.AGREE,
            human.getRace(), null));
        speechLines.add(SpeechFactory.createLine(SpeechType.DECLINE,
            human.getRace(), null));
        if (human.getRace() != SpaceRace.MECHIONS) {
          speechLines.add(SpeechFactory.createLine(SpeechType.DECLINE_ANGER,
              human.getRace(), null));
        }
        speechLines.add(SpeechFactory.createLine(SpeechType.DECLINE_WAR,
            human.getRace(), null));
      }
    } else if (startType == AI_BORDER_CROSS) {
      speechLines.add(SpeechFactory.createLine(SpeechType.MOVE_FLEET,
          human.getRace(), null));
      speechLines.add(SpeechFactory.createLine(SpeechType.DECLINE_WAR,
          human.getRace(), null));
    } else if (startType == AI_ESPIONAGE) {
      speechLines.add(SpeechFactory.createLine(SpeechType.MOVE_FLEET,
          human.getRace(), null));
      speechLines.add(SpeechFactory.createLine(SpeechType.DECLINE_WAR,
          human.getRace(), null));
    } else {
      if (!ai.getDiplomacy().isPeace(humanIndex)) {
        speechLines.add(SpeechFactory.createLine(SpeechType.PEACE_OFFER,
            human.getRace(), null));
      }
      if (startType == HUMAN_BORDER_CROSS) {
        speechLines.add(SpeechFactory.createLine(SpeechType.ASK_MOVE_FLEET,
            human.getRace(), borderCrossedFleet.getName()));
      }
      if (startType == HUMAN_ESPIONAGE) {
        speechLines.add(SpeechFactory.createLine(SpeechType.ASK_MOVE_SPY,
            human.getRace(), borderCrossedFleet.getName()));
      }
      speechLines.add(SpeechFactory.createLine(SpeechType.TRADE,
          human.getRace(), null));
      speechLines.add(SpeechFactory.createLine(SpeechType.DEMAND,
          human.getRace(), null));
      if (!ai.getDiplomacy().hasAlliance()
          && !human.getDiplomacy().hasAlliance()
          && ai.getDiplomacy().isTradeAlliance(humanIndex)
          && ai.getDiplomacy().isPeace(humanIndex)
          && !ai.getDiplomacy().isTradeEmbargo(humanIndex)
          && ai.getDiplomacy().isDefensivePact(humanIndex)) {
        speechLines.add(SpeechFactory.createLine(SpeechType.ALLIANCE,
            human.getRace(), null));
      } else if (ai.getDiplomacy().isTradeAlliance(humanIndex)
          && ai.getDiplomacy().isPeace(humanIndex)
          && !ai.getDiplomacy().isTradeEmbargo(humanIndex)
          && !ai.getDiplomacy().isDefensivePact(humanIndex)) {
        speechLines.add(SpeechFactory.createLine(SpeechType.DEFESIVE_PACT,
            human.getRace(), null));
      } else if (!ai.getDiplomacy().isDefensivePact(humanIndex)
          && !ai.getDiplomacy().isTradeEmbargo(humanIndex)
          && ai.getDiplomacy().isPeace(humanIndex)) {
        speechLines.add(SpeechFactory.createLine(SpeechType.TRADE_ALLIANCE,
            human.getRace(), null));
      }
      if (ai.getDiplomacy().isTradeAlliance(humanIndex)
          && !ai.getDiplomacy().isAlliance(humanIndex)
          && ai.getDiplomacy().isPeace(humanIndex)
          && !ai.getDiplomacy().isSpyTrade(humanIndex)
          && !ai.getDiplomacy().isTradeEmbargo(humanIndex)
          && human.getEspionage().isSpyTradePossible()
          && ai.getEspionage().isSpyTradePossible()) {
        speechLines.add(SpeechFactory.createLine(SpeechType.OFFER_SPY_TRADE,
            human.getRace(), null));
      }
      if (ai.getDiplomacy().isPeace(humanIndex)) {
        if (embargoLine == null) {
          speechLines.add(SpeechFactory.createEmbargoSuggestion());
        } else {
          speechLines.add(embargoLine);
        }
      }
      if (!ai.getDiplomacy().isWar(humanIndex)) {
        speechLines.add(SpeechFactory.createLine(SpeechType.MAKE_WAR,
            human.getRace(), null));
      }
    }
    SpeechLine[] lines = new SpeechLine[speechLines.size()];
    for (int i = 0; i < lines.length; i++) {
      lines[i] = speechLines.get(i);
    }
    return lines;
  }
  /**
   * Create trade embargo lines for human UI
   * @return Trade embargo lines
   */
  private SpeechLine[] createTradeEmbargoChoicesLines() {
    ArrayList<PlayerInfo> listOfRealms = new ArrayList<>();
    int maxRealms = starMap.getPlayerList().getCurrentMaxRealms();
    for (int i = 0; i < maxRealms; i++) {
      PlayerInfo realm = starMap.getPlayerByIndex(i);
      if (realm != human && realm != ai
          && (human.getDiplomacy().getDiplomacyList(i).getNumberOfMeetings() > 0
              || !human.getDiplomacy().getDiplomaticRelation(i).isEmpty())) {
        listOfRealms.add(realm);
      }
    }
    if (!listOfRealms.isEmpty()) {
      SpeechLine[] lines = new SpeechLine[listOfRealms.size()];
      for (int i = 0; i < listOfRealms.size(); i++) {
        int index = starMap.getPlayerList().getIndex(listOfRealms.get(i));
        String relation = human.getDiplomacy().getDiplomaticRelation(index);
        lines[i] = new SpeechLine(SpeechType.TRADE_EMBARGO_REALM_CHOICE,
            listOfRealms.get(i).getEmpireName() + " - " + relation);
      }
      return lines;
    }
    return new SpeechLine[0];
  }
  /**
   * Get realm from string which isn't human or AI.
   * @param text Where to search realm name
   * @return PlayerInfo found or null
   */
  public PlayerInfo getRealmFromString(final String text) {
    int maxRealms = starMap.getPlayerList().getCurrentMaxRealms();
    for (int i = 0; i < maxRealms; i++) {
      PlayerInfo realm = starMap.getPlayerByIndex(i);
      if (realm != human && realm != ai
          && text.contains(realm.getEmpireName())) {
        return realm;
      }
    }
    return null;
  }
  /**
   * Create trade embargo line for human UI
   * @param choice Text choice
   */
  public void createTradeEmbargoLine(final String choice) {
    embargoLine = null;
    PlayerInfo realm = getRealmFromString(choice);
    if (realm != null) {
      embargoLine = SpeechFactory.createLine(SpeechType.TRADE_EMBARGO,
          human.getRace(), realm.getEmpireName());
    }
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
   * Get Greet line type
   * @return SpeechType for starting greet
   */
  public SpeechType getGreetLine() {
    int humanIndex = starMap.getPlayerList().getIndex(human);
    SpeechType type = SpeechType.NEUTRAL_GREET;
    switch (ai.getDiplomacy().getLiking(humanIndex)) {
    case Diplomacy.DISLIKE: type = SpeechType.DISLIKE_GREET; break;
    case Diplomacy.LIKE: type = SpeechType.LIKE_GREET; break;
    case Diplomacy.HATE: type = SpeechType.HATE_GREET; break;
    case Diplomacy.FRIENDS: type = SpeechType.FRIENDS_GREET; break;
    case Diplomacy.NEUTRAL: type = SpeechType.NEUTRAL_GREET; break;
    default: type = SpeechType.NEUTRAL_GREET; break;
    }
    return type;
  }
  /**
   * Update panel's texts
   * @param type Speech Type
   */
  public void updatePanel(final SpeechType type) {
    lastSpeechType = type;
    int humanIndex = starMap.getPlayerList().getIndex(human);
    String text = ai.getDiplomacy().generateRelationText(humanIndex);
    likenessLabel.setText(text);
    likenessLabel.setForeground(ai.getDiplomacy().getLikingAsColor(humanIndex));
    if (type == SpeechType.NEUTRAL_GREET) {
      text = SpeechFactory.createLine(type, ai.getRace(), human.getRace()
          .getNameSingle()).getLine();
    } else if (type == SpeechType.ASK_MOVE_FLEET) {
      text = SpeechFactory.createLine(type, ai.getRace(),
          borderCrossedFleet.getName()).getLine();
    } else {
      text = SpeechFactory.createLine(type, ai.getRace(), null).getLine();
    }
    infoText.setText(text);
    textCounter = 0;
    this.repaint();
  }

  /**
   * Get offering list from UI components
   * @param playerTechList Human or AI list
   * @param mapOffer Human or AI map offering
   * @param playerFleetList Human or AI list
   * @param playerPlanetList Human or AI list
   * @param credits Human or AI credits
   * @return Negotation list for that player
   */
  private NegotiationList getOfferingList(final JList<Tech> playerTechList,
      final boolean mapOffer, final JList<Fleet> playerFleetList,
      final JList<Planet> playerPlanetList, final int credits) {
    NegotiationList list = new NegotiationList();
    List<Tech> techList = playerTechList.getSelectedValuesList();
    for (Tech tech : techList) {
      NegotiationOffer offer = new NegotiationOffer(NegotiationType.TECH,
          tech);
      list.add(offer);
    }
    if (mapOffer) {
      NegotiationOffer offer = new NegotiationOffer(NegotiationType.MAP,
          null);
      list.add(offer);
    }
    List<Fleet> fleetList = playerFleetList.getSelectedValuesList();
    for (Fleet fleet : fleetList) {
      NegotiationOffer offer = new NegotiationOffer(NegotiationType.FLEET,
          fleet);
      list.add(offer);
    }
    List<Planet> planetList = playerPlanetList.getSelectedValuesList();
    for (Planet planet : planetList) {
      NegotiationOffer offer = new NegotiationOffer(NegotiationType.PLANET,
          planet);
      list.add(offer);
    }
    if (credits > 0) {
      NegotiationOffer offer = new NegotiationOffer(NegotiationType.CREDIT,
          new Integer(credits));
      list.add(offer);
    }
    return list;
  }

  /**
   * Set Ai offering list according negotiationList
   * @param offerList Negotiation list for ai
   */
  private void setAiOfferingList(final NegotiationList offerList) {
    ArrayList<Fleet> fleetArray = new ArrayList<>();
    ArrayList<Planet> planetArray = new ArrayList<>();
    ArrayList<Tech> techArray = new ArrayList<>();
    aiMapOffer.setSelected(false);
    if (offerList != null) {
      for (int i = 0; i < offerList.getSize(); i++) {
        NegotiationOffer offer = offerList.getByIndex(i);
        switch (offer.getNegotiationType()) {
          case CREDIT: {
            aiCredits = offer.getCreditValue();
            aiCreditOffer.setText(aiCredits + " Credits");
            break;
          }
          case FLEET: {
            fleetArray.add(offer.getFleet());
            break;
          }
          case MAP: {
            aiMapOffer.setSelected(true);
            break;
          }
          case PLANET: {
            planetArray.add(offer.getPlanet());
            break;
          }
          case TECH: {
            techArray.add(offer.getTech());
            break;
          }
          case ALLIANCE:
          case TRADE_ALLIANCE:
          case DIPLOMAT:
          case PEACE:
          case WAR:
          default: {
            // Nothing to set with these
            break;
          }
        }
      }
    }
    aiTechListOffer.setListData(techArray.toArray(new Tech[techArray.size()]));
    aiPlanetListOffer.setListData(planetArray.toArray(
        new Planet[planetArray.size()]));
    aiFleetListOffer.setListData(fleetArray.toArray(
        new Fleet[fleetArray.size()]));
  }

  /**
   * Set Human offering list according negotiationList
   * @param offerList Negotiation list for human
   */
  private void setHumanOfferingList(final NegotiationList offerList) {
    ArrayList<Fleet> fleetArray = new ArrayList<>();
    ArrayList<Planet> planetArray = new ArrayList<>();
    ArrayList<Tech> techArray = new ArrayList<>();
    humanMapOffer.setSelected(false);
    if (offerList != null) {
      for (int i = 0; i < offerList.getSize(); i++) {
        NegotiationOffer offer = offerList.getByIndex(i);
        switch (offer.getNegotiationType()) {
          case CREDIT: {
            humanCredits = offer.getCreditValue();
            humanCreditOffer.setText(humanCredits + " Credits");
            break;
          }
          case FLEET: {
            fleetArray.add(offer.getFleet());
            break;
          }
          case MAP: {
            humanMapOffer.setSelected(true);
            break;
          }
          case PLANET: {
            planetArray.add(offer.getPlanet());
            break;
          }
          case TECH: {
            techArray.add(offer.getTech());
            break;
          }
          case ALLIANCE:
          case TRADE_ALLIANCE:
          case DIPLOMAT:
          case PEACE:
          case WAR:
          default: {
            // Nothing to set with these
            break;
          }
        }
      }
    }
    humanTechListOffer.setListData(techArray.toArray(
        new Tech[techArray.size()]));
    humanPlanetListOffer.setListData(planetArray.toArray(
        new Planet[planetArray.size()]));
    humanFleetListOffer.setListData(fleetArray.toArray(
        new Fleet[fleetArray.size()]));
  }
  /**
   * Set Offering list according the trade.
   * @param startType How diplomacy screen was started.
   *        There are four choices:
   *        HUMAN_REGULAR
   *        AI_REGULAR
   *        HUMAN_BORDER_CROSS
   *        AI_BORDER_CROSS
   *        HUMAN_ESPIONAGE
   *        AI_ESPIONGE
   */
  public void setOfferingList(final int startType) {
    if (startType == AI_REGULAR || startType == AI_BORDER_CROSS
        || startType == AI_ESPIONAGE) {
      setHumanOfferingList(trade.getFirstOffer());
      setAiOfferingList(trade.getSecondOffer());
    } else {
      setHumanOfferingList(trade.getSecondOffer());
      setAiOfferingList(trade.getFirstOffer());
    }
  }
  /**
   * Reset trade choices. This should be called
   * after trade has been done.
   */
  public void resetChoices() {
    endBtn.setEnabled(true);
    humanCredits = 0;
    embargoLine = null;
    aiCredits = 0;
    int humanIndex = starMap.getPlayerList().getIndex(human);
    int aiIndex = starMap.getPlayerList().getIndex(ai);
    trade = new DiplomaticTrade(starMap, humanIndex, aiIndex);
    humanTechListOffer.setListData(trade.getTradeableTechListForSecond());
    humanMapOffer.setSelected(false);
    humanFleetListOffer.setListData(trade.getTradeableFleetListForFirst());
    humanPlanetListOffer.setListData(trade.getTradeablePlanetListForFirst());
    humanCreditOffer.setText("0 Credits");

    humanLines.setListData(createOfferLines(HUMAN_REGULAR));

    aiTechListOffer.setListData(trade.getTradeableTechListForFirst());
    aiMapOffer.setSelected(false);
    aiFleetListOffer.setListData(trade.getTradeableFleetListForSecond());
    aiPlanetListOffer.setListData(trade.getTradeablePlanetListForSecond());
    aiCreditOffer.setText("0 Credits");
  }
  /**
   * Handle events for DiplomacyView.
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (GameCommands.COMMAND_ANIMATION_TIMER.equals(arg0.getActionCommand())) {
      aiImg.repaint();
      textCounter++;
      if (textCounter > 40) {
        textCounter = 40;
        if (lastSpeechType == SpeechType.AGREE
            || lastSpeechType == SpeechType.DECLINE
            || lastSpeechType == SpeechType.OFFER_ACCEPTED
            || lastSpeechType == SpeechType.OFFER_REJECTED
            || lastSpeechType == SpeechType.NOTHING_TO_TRADE) {
          infoText.setText("");
          infoText.repaint();
        }
      }
      if (humanLines.getSelectedValue() != null
          && humanLines.getSelectedValue().getType() == SpeechType.TRADE_EMBARGO
          && humanLines.getSelectedValue().getLine().equals(
              SpeechFactory.TRADE_EMBARGO_SUGGESTION)) {
        SpeechLine[] lines = createTradeEmbargoChoicesLines();
        if (lines.length > 0) {
          humanLines.setListData(lines);
        }
      }
      if (humanLines.getSelectedValue() != null
          && humanLines.getSelectedValue().getType()
          == SpeechType.TRADE_EMBARGO_REALM_CHOICE) {
        createTradeEmbargoLine(humanLines.getSelectedValue().getLine());
        humanLines.setListData(createOfferLines(HUMAN_REGULAR));
      }
    }
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
    if (GameCommands.COMMAND_OK.equals(arg0.getActionCommand())) {
      SpeechLine speechSelected = humanLines.getSelectedValue();
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.AGREE) {
        trade.doTrades();
        tradeHappened = true;
        int aiIndex = starMap.getPlayerList().getIndex(ai);
        if (trade.getFirstOffer().isTypeInOffer(NegotiationType.WAR)
            && !human.getDiplomacy().isWar(aiIndex)) {
          StarMapUtilities.addWarDeclatingReputation(starMap, ai);
          NewsData newsData = NewsFactory.makeWarNews(ai, human,
              meetingPlace, starMap);
          starMap.getNewsCorpData().addNews(newsData);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
          String[] list = human.getDiplomacy().activateDefensivePact(
              starMap, ai);
          if (list != null) {
            starMap.getNewsCorpData().addNews(
                NewsFactory.makeDefensiveActivation(ai, list));
          }
        }
        if (trade.getFirstOffer().isTypeInOffer(NegotiationType.ALLIANCE)) {
          NewsData newsData = NewsFactory.makeAllianceNews(ai, human,
              meetingPlace);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
          starMap.getNewsCorpData().addNews(newsData);
        }
        if (trade.getFirstOffer().isTypeInOffer(
            NegotiationType.TRADE_ALLIANCE)) {
          NewsData newsData = NewsFactory.makeTradeAllianceNews(ai, human,
              meetingPlace);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
          starMap.getNewsCorpData().addNews(newsData);
        }
        if (trade.getFirstOffer().isTypeInOffer(NegotiationType.PEACE)) {
          NewsData newsData = NewsFactory.makePeaceNews(ai, human,
              meetingPlace);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
          starMap.getNewsCorpData().addNews(newsData);
          ai.getMissions().removeAttackAgainstPlayer(human, starMap);
        }
        updatePanel(SpeechType.OFFER_ACCEPTED);
        resetChoices();
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.MOVE_FLEET) {
        updatePanel(SpeechType.OFFER_ACCEPTED);
        Message msg = new Message(MessageType.FLEET,
            "Your fleet has crossed the borders! You have promised to move"
            + " it away.", Icons.getIconByName(Icons.ICON_HULL_TECH));
        if (meetingPlace != null && meetingPlace instanceof Fleet) {
          Fleet fleet = (Fleet) meetingPlace;
          msg.setCoordinate(fleet.getCoordinate());
          msg.setMatchByString(fleet.getName());
        }
        human.getMsgList().addUpcomingMessage(msg);
        resetChoices();
      }
      if (speechSelected != null
          && (speechSelected.getType() == SpeechType.DECLINE
          || speechSelected.getType() == SpeechType.DECLINE_ANGER
          || speechSelected.getType() == SpeechType.DECLINE_WAR)) {
        int humanIndex = starMap.getPlayerList().getIndex(human);
        DiplomacyBonusList list = ai.getDiplomacy().getDiplomacyList(
            humanIndex);
        Attitude attitude = ai.getAiAttitude();
        int liking = ai.getDiplomacy().getLiking(humanIndex);
        if (speechSelected.getType() == SpeechType.DECLINE_ANGER) {
          list.addBonus(DiplomacyBonusType.INSULT, ai.getRace());
        }
        int warChance = DiplomaticTrade.getWarChanceForDecline(
            trade.getSpeechTypeByOffer(), attitude, liking);
        if (speechSelected.getType() == SpeechType.DECLINE_WAR) {
          warChance = 100;
        }
        int value = DiceGenerator.getRandom(99);
        if (value < warChance) {
          trade.generateEqualTrade(NegotiationType.WAR);
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.MAKE_WAR);
          resetChoices();
          int aiIndex = starMap.getPlayerList().getIndex(ai);
          if (!human.getDiplomacy().isWar(aiIndex)) {
            StarMapUtilities.addWarDeclatingReputation(starMap, ai);
            NewsData newsData = NewsFactory.makeWarNews(ai, human,
                meetingPlace, starMap);
            starMap.getNewsCorpData().addNews(newsData);
            starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
                meetingPlace, newsData));
            String[] defenseList = human.getDiplomacy().activateDefensivePact(
                starMap, ai);
            if (defenseList != null) {
              starMap.getNewsCorpData().addNews(
                  NewsFactory.makeDefensiveActivation(ai, defenseList));
            }
          }
        } else {
          if (speechSelected.getType() == SpeechType.DECLINE_ANGER) {
            updatePanel(SpeechType.INSULT_RESPOND);
          } else {
            updatePanel(SpeechType.OFFER_REJECTED);
          }
          resetChoices();
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.TRADE_ALLIANCE) {
        NegotiationList list1 = getOfferingList(humanTechListOffer,
            humanMapOffer.isSelected(), humanFleetListOffer,
            humanPlanetListOffer, humanCredits);
        list1.add(new NegotiationOffer(NegotiationType.TRADE_ALLIANCE, null));
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        list2.add(new NegotiationOffer(NegotiationType.TRADE_ALLIANCE, null));
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
          NewsData newsData = NewsFactory.makeTradeAllianceNews(human, ai,
              meetingPlace);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
          starMap.getNewsCorpData().addNews(newsData);
        } else {
          updatePanel(SpeechType.DECLINE);
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.ASK_MOVE_FLEET) {
        trade.generateRecallFleetOffer(borderCrossedFleet);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.MOVE_FLEET);
          resetChoices();
        } else {
          trade.generateEqualTrade(NegotiationType.WAR);
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.DECLINE_WAR);
          resetChoices();
          int aiIndex = starMap.getPlayerList().getIndex(ai);
          if (!human.getDiplomacy().isWar(aiIndex)) {
            StarMapUtilities.addWarDeclatingReputation(starMap, ai);
            NewsData newsData = NewsFactory.makeWarNews(ai, human,
                meetingPlace, starMap);
            starMap.getNewsCorpData().addNews(newsData);
            starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
                meetingPlace, newsData));
            String[] defenseList = human.getDiplomacy().activateDefensivePact(
                starMap, ai);
            if (defenseList != null) {
              starMap.getNewsCorpData().addNews(
                  NewsFactory.makeDefensiveActivation(ai, defenseList));
            }
          }
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.ASK_MOVE_SPY) {
        trade.generateRecallFleetOffer(borderCrossedFleet);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.MOVE_FLEET);
          resetChoices();
        } else {
          trade.generateEqualTrade(NegotiationType.WAR);
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.DECLINE_WAR);
          resetChoices();
          int aiIndex = starMap.getPlayerList().getIndex(ai);
          if (!human.getDiplomacy().isWar(aiIndex)) {
            StarMapUtilities.addWarDeclatingReputation(starMap, ai);
            NewsData newsData = NewsFactory.makeWarNews(ai, human,
                meetingPlace, starMap);
            starMap.getNewsCorpData().addNews(newsData);
            starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
                meetingPlace, newsData));
            String[] defenseList = human.getDiplomacy().activateDefensivePact(
                starMap, ai);
            if (defenseList != null) {
              starMap.getNewsCorpData().addNews(
                  NewsFactory.makeDefensiveActivation(ai, defenseList));
            }
          }
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.ALLIANCE) {
        NegotiationList list1 = getOfferingList(humanTechListOffer,
            humanMapOffer.isSelected(), humanFleetListOffer,
            humanPlanetListOffer, humanCredits);
        list1.add(new NegotiationOffer(NegotiationType.ALLIANCE, null));
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        list2.add(new NegotiationOffer(NegotiationType.ALLIANCE, null));
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
          NewsData newsData = NewsFactory.makeAllianceNews(human, ai,
              meetingPlace);
          starMap.getNewsCorpData().addNews(newsData);
          starMap.getHistory().addEvent(
              NewsFactory.makeDiplomaticEvent(meetingPlace, newsData));
        } else {
          updatePanel(SpeechType.DECLINE);
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.TRADE_EMBARGO
          && !speechSelected.getLine().equals(
              SpeechFactory.TRADE_EMBARGO_SUGGESTION)) {
        PlayerInfo realm = getRealmFromString(speechSelected.getLine());
        if (realm != null) {
          NegotiationList list1 = getOfferingList(humanTechListOffer,
              humanMapOffer.isSelected(), humanFleetListOffer,
              humanPlanetListOffer, humanCredits);
          list1.add(new NegotiationOffer(NegotiationType.TRADE_EMBARGO,
              realm));
          NegotiationList list2 = getOfferingList(aiTechListOffer,
              aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
              aiCredits);
          list2.add(new NegotiationOffer(NegotiationType.TRADE_EMBARGO, realm));
          trade.setFirstOffer(list2);
          trade.setSecondOffer(list1);
          if (trade.isOfferGoodForBoth()) {
            trade.doTrades();
            tradeHappened = true;
            updatePanel(SpeechType.AGREE);
            resetChoices();
            NewsData newsData = NewsFactory.makeTradeEmbargoNews(human, ai,
                realm, meetingPlace);
            starMap.getNewsCorpData().addNews(newsData);
            starMap.getHistory().addEvent(
                NewsFactory.makeDiplomaticEvent(meetingPlace, newsData));
            StarMapUtilities.addEmbargoReputation(starMap, human, ai, realm);
          } else {
            embargoLine = null;
            updatePanel(SpeechType.DECLINE);
          }
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.DEFESIVE_PACT) {
        NegotiationList list1 = getOfferingList(humanTechListOffer,
            humanMapOffer.isSelected(), humanFleetListOffer,
            humanPlanetListOffer, humanCredits);
        list1.add(new NegotiationOffer(NegotiationType.DEFENSIVE_PACT, null));
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        list2.add(new NegotiationOffer(NegotiationType.DEFENSIVE_PACT, null));
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
          NewsData newsData = NewsFactory.makeDefensivePactNews(human, ai,
              meetingPlace);
          starMap.getNewsCorpData().addNews(newsData);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
        } else {
          updatePanel(SpeechType.DECLINE);
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.DEMAND) {
        NegotiationList list1 = new NegotiationList();
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        int humanIndex = starMap.getPlayerList().getIndex(human);
        ai.getDiplomacy().getDiplomacyList(humanIndex).addBonus(
            DiplomacyBonusType.MADE_DEMAND, ai.getRace());
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
        } else {
          Attitude attitude = ai.getAiAttitude();
          int liking = ai.getDiplomacy().getLiking(humanIndex);
          int warChance = DiplomaticTrade.getWarChanceForDecline(
              SpeechType.DEMAND, attitude, liking);
          int value = DiceGenerator.getRandom(99);
          if (value < warChance) {
            trade.generateEqualTrade(NegotiationType.WAR);
            trade.doTrades();
            tradeHappened = true;
            updatePanel(SpeechType.DECLINE_WAR);
            resetChoices();
            int aiIndex = starMap.getPlayerList().getIndex(ai);
            if (!human.getDiplomacy().isWar(aiIndex)) {
              StarMapUtilities.addWarDeclatingReputation(starMap, ai);
              NewsData newsData = NewsFactory.makeWarNews(ai, human,
                  meetingPlace, starMap);
              starMap.getNewsCorpData().addNews(newsData);
              starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
                  meetingPlace, newsData));
              String[] defenseList = human.getDiplomacy().activateDefensivePact(
                  starMap, ai);
              if (defenseList != null) {
                starMap.getNewsCorpData().addNews(
                    NewsFactory.makeDefensiveActivation(ai, defenseList));
              }
            }
          } else {
            updatePanel(SpeechType.DECLINE_ANGER);
            resetChoices();
          }
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.TRADE) {
        NegotiationList list1 = getOfferingList(humanTechListOffer,
            humanMapOffer.isSelected(), humanFleetListOffer,
            humanPlanetListOffer, humanCredits);
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
        } else {
          updatePanel(SpeechType.DECLINE);
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.OFFER_SPY_TRADE) {
        NegotiationList list1 = getOfferingList(humanTechListOffer,
            humanMapOffer.isSelected(), humanFleetListOffer,
            humanPlanetListOffer, humanCredits);
        list1.add(new NegotiationOffer(NegotiationType.SPY_TRADE, null));
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        list2.add(new NegotiationOffer(NegotiationType.SPY_TRADE, null));
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
        } else {
          updatePanel(SpeechType.DECLINE);
        }
      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.MAKE_WAR) {
        int humanIndex = starMap.getPlayerList().getIndex(human);
        int aiIndex = starMap.getPlayerList().getIndex(ai);
        ai.getDiplomacy().getDiplomacyList(humanIndex).addBonus(
            DiplomacyBonusType.IN_WAR, ai.getRace());
        human.getDiplomacy().getDiplomacyList(aiIndex).addBonus(
            DiplomacyBonusType.IN_WAR, human.getRace());
        updatePanel(SpeechType.MAKE_WAR);
        if (!human.getDiplomacy().isWar(aiIndex)) {
          StarMapUtilities.addWarDeclatingReputation(starMap, human);
          NewsData newsData = NewsFactory.makeWarNews(human, ai,
              meetingPlace, starMap);
          starMap.getNewsCorpData().addNews(newsData);
          starMap.getHistory().addEvent(NewsFactory.makeDiplomaticEvent(
              meetingPlace, newsData));
          String[] defenseList = ai.getDiplomacy().activateDefensivePact(
              starMap, human);
          if (defenseList != null) {
            starMap.getNewsCorpData().addNews(
                NewsFactory.makeDefensiveActivation(human, defenseList));
          }
        }

      }
      if (speechSelected != null
          && speechSelected.getType() == SpeechType.PEACE_OFFER) {
        NegotiationList list1 = getOfferingList(humanTechListOffer,
            humanMapOffer.isSelected(), humanFleetListOffer,
            humanPlanetListOffer, humanCredits);
        list1.add(new NegotiationOffer(NegotiationType.PEACE, null));
        NegotiationList list2 = getOfferingList(aiTechListOffer,
            aiMapOffer.isSelected(), aiFleetListOffer, aiPlanetListOffer,
            aiCredits);
        list2.add(new NegotiationOffer(NegotiationType.PEACE, null));
        trade.setFirstOffer(list2);
        trade.setSecondOffer(list1);
        if (trade.isOfferGoodForBoth()) {
          trade.doTrades();
          tradeHappened = true;
          updatePanel(SpeechType.AGREE);
          resetChoices();
          NewsData newsData = NewsFactory.makePeaceNews(human, ai,
              meetingPlace);
          starMap.getNewsCorpData().addNews(newsData);
          starMap.getHistory().addEvent(
              NewsFactory.makeDiplomaticEvent(meetingPlace, newsData));
          ai.getMissions().removeAttackAgainstPlayer(human, starMap);
        } else {
          updatePanel(SpeechType.DECLINE);
        }
      }

    }
  }

  /**
   * Increase meeting numbers for both parties.
   */
  public void updateMeetingNumbers() {
    trade.updateMeetingNumbers();
  }

  /**
   * Get human lines component
   * @return JList of human lines
   */
  public JList<SpeechLine> getHumanLines() {
    return humanLines;
  }

  /**
   * Return meeting place.
   * @return This can be null, Planet or Fleet
   */
  public Object getMeetingPlace() {
    return meetingPlace;
  }

  /**
   * Did trade happen
   * @return True if trade happened between AI and Human
   */
  public boolean didTradeHappen() {
    return tradeHappened;
  }

  /**
   * Add nothing to trade to AI's diplomacy list
   */
  public void addNothingToTrade() {
    int humanIndex = starMap.getPlayerList().getIndex(human);
    if (ai.getDiplomacy() != null
        && ai.getDiplomacy().getDiplomacyList(humanIndex) != null) {
      DiplomacyBonusList list = ai.getDiplomacy().getDiplomacyList(humanIndex);
      list.addBonus(DiplomacyBonusType.NOTHING_TO_TRADE, ai.getRace());
    }
  }
  /**
   * Getter for embargo line
   * @return Embargo line
   */
  public SpeechLine getEmbargoLine() {
    return embargoLine;
  }
}
