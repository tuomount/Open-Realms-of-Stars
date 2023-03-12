package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.EspionagePanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceSliderPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.espionage.Espionage;
import org.openRealmOfStars.player.espionage.EspionageList;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2021,2022  Tuomo Untinen
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
* Espionage view for spying and espionage
*
*/
public class EspionageView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Player info
   */
  private PlayerInfo player;

  /**
   * Fake military slider
   */
  private SpaceSliderPanel fakeMilitarySlider;

  /**
   * Explain fake military text
   */
  private InfoTextArea fakeMilitaryText;

  /**
   * How much military human player has according the news organization.
   */
  private int humanNewsMilitarySize;

  /**
   * Espionage View constructor
   * @param playerList List of all players
   * @param info PlayerInfo who is doing the spying
   * @param militaryNews Galaxy news about military stats
   * @param listener Action Listener
   */
  public EspionageView(final PlayerList playerList, final PlayerInfo info,
      final GalaxyStat militaryNews, final ActionListener listener) {
    player = info;
    this.setLayout(new BorderLayout());

    InfoPanel topPanel = new InfoPanel();
    topPanel.setTitle("Faking the military size for GBNC");
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    fakeMilitarySlider = new SpaceSliderPanel(
        GameCommands.COMMAND_MINUS_MILITARY,
        GameCommands.COMMAND_PLUS_MILITARY, Icons.ICON_TROOPS,
        "Lie military size (100%)", 50, 200, info.getFakeMilitarySize(),
        GameCommands.COMMAND_FAKE_MILITARY, listener);
    fakeMilitarySlider.setSliderMinorTick(10);
    fakeMilitarySlider.setSliderMajorTick(10);
    topPanel.add(fakeMilitarySlider);
    topPanel.add(Box.createRigidArea(new Dimension(15, 10)));
    fakeMilitaryText = new InfoTextArea();
    fakeMilitaryText.setEditable(false);
    fakeMilitaryText.setFont(GuiStatics.getFontCubellanSmaller());
    fakeMilitaryText.setCharacterWidth(8);
    fakeMilitaryText.setLineWrap(true);
    JScrollPane scroll = new JScrollPane(fakeMilitaryText);
    scroll.setBackground(GuiStatics.COLOR_DEEP_SPACE_PURPLE_DARK);
    topPanel.add(scroll);
    int humanIndex = playerList.getIndex(info);
    humanNewsMilitarySize = militaryNews.getLatest(humanIndex);

    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setTitle("Espionage");
    centerPanel.setLayout(new GridLayout(4, 4));
    int maxPlayer = playerList.getCurrentMaxRealms();
    for (int i = 0; i < maxPlayer; i++) {
      PlayerInfo realmInfo = playerList.getPlayerInfoByIndex(i);
      EspionageList espionageList = player.getEspionage().getByIndex(i);
      if (espionageList != null) {
        int bonus = player.getEspionage().getByIndex(i).getTotalBonus();
        String desc = EspionageList.getTotalBonusAsDescriptions(bonus);
        int militaryValue = 0;
        if (bonus > 0) {
          militaryValue = NewsCorpData.calculateMilitaryValue(realmInfo);
          militaryValue = espionageList.estimateMilitaryPower(militaryValue);
        }
        int realmIndex = playerList.getIndex(realmInfo);
        int newsValue = militaryNews.getLatest(realmIndex);
        desc = "Military value: " + militaryValue + ".\n"
             + "News value: " + newsValue + ".\n" + desc;
        int spyTrade = player.getDiplomacy().getSpyTradeLasting(i);
        if (spyTrade > 0) {
          desc = desc + "\nSpy trading: " + spyTrade + " star years.";
        }
        String text;
        Color relationColor = null;
        if (i != humanIndex) {
          text = realmInfo.getDiplomacy().generateRelationText(
              humanIndex);
          relationColor = realmInfo.getDiplomacy().getLikingAsColor(
              humanIndex);
        } else {
          text = "Own Realm";
          relationColor = GuiStatics.COLOR_GREEN_TEXT;
        }
        EspionagePanel panel = new EspionagePanel(realmInfo.getEmpireName(),
            desc, bonus, text, relationColor, listener);
        centerPanel.add(panel);
      }
    }

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
    JScrollPane scroll2 = new JScrollPane(centerPanel);
    scroll2.getVerticalScrollBar().setUnitIncrement(20);
    this.add(scroll2, BorderLayout.CENTER);
    this.add(topPanel, BorderLayout.NORTH);
    updatePanel();
  }

  /**
   * Get Player Info
   * @return PlayerInfo given in constructor
   */
  public PlayerInfo getPlayer() {
    return player;
  }

  /**
   * How big military human player has according the
   * news organization. Value of this does not
   * tell if there has been faked the size or not.
   * @return Human news military size.
   */
  public int getHumanNewsMilitarySize() {
    return humanNewsMilitarySize;
  }
  /**
   * Update panel
   */
  public void updatePanel() {
    fakeMilitarySlider.setText("Lie military size ("
      + fakeMilitarySlider.getSliderValue() + "%)");
    int value = fakeMilitarySlider.getSliderValue();
    int cost = Espionage.calculateEspionageCost(value);
    fakeMilitaryText.setText("Lying military power costs " + cost + " credits."
        + "Lying military might cause other realms wrongly evalutate your "
        + " military power and start war or not start war with you.\n"
        + "GBNC claimed that you had military size of "
        + getHumanNewsMilitarySize() + ".");
    this.repaint();
  }
  /**
   * Handle actions for Espionage view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_MINUS_MILITARY)
        && fakeMilitarySlider.getSliderValue() > fakeMilitarySlider
        .getMinimumValue()) {
      fakeMilitarySlider.setSliderValue(fakeMilitarySlider.getSliderValue()
          - 10);
      player.setFakeMilitarySize(fakeMilitarySlider.getSliderValue());
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PLUS_MILITARY)
        && fakeMilitarySlider.getSliderValue() < fakeMilitarySlider
        .getMaximumValue()) {
      fakeMilitarySlider.setSliderValue(fakeMilitarySlider.getSliderValue()
          + 10);
      player.setFakeMilitarySize(fakeMilitarySlider.getSliderValue());
      SoundPlayer.playMenuSound();
      updatePanel();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_FAKE_MILITARY)) {
      player.setFakeMilitarySize(fakeMilitarySlider.getSliderValue());
      SoundPlayer.playMenuSound();
      updatePanel();
    }
  }

}
