package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.HyperLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.RaceImagePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2020,2021 Tuomo Untinen
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
* Realm view for Open Realm of Stars
*
*/
public class RealmView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Realm view constructor
   * @param realm Realm which is being shown
   * @param listener ActionListener
   * @param knowledgeBonus Knowledge bonus between 0-10.
   * @param meetings Number of meetings human player has with this realm.
   */
  public RealmView(final PlayerInfo realm, final ActionListener listener,
      final int knowledgeBonus, final int meetings) {
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BoxLayout(base, BoxLayout.Y_AXIS));
    base.setTitle(realm.getEmpireName());
    this.add(base, BorderLayout.CENTER);
    RaceImagePanel raceImage = new RaceImagePanel();
    raceImage.setRaceToShow(realm.getRace().getNameSingle());
    raceImage.setSize(220, 220);
    raceImage.setMaximumSize(new Dimension(220, 220));
    base.add(raceImage);
    EmptyInfoPanel panelX = new EmptyInfoPanel();
    panelX.setLayout(new GridLayout(1, 0));
    InfoPanel info = new InfoPanel();
    info.setTitle("Racial information");
    info.setLayout(new BorderLayout());
    HyperLabel raceDescription = new HyperLabel(
        realm.getRace().getFullDescription(false, false));
    info.add(raceDescription, BorderLayout.CENTER);
    panelX.add(info);
    info = new InfoPanel();
    info.setTitle("Government information");
    info.setLayout(new GridLayout(0, 1));
    HyperLabel governmentDescription = new HyperLabel(
        realm.getGovernment().getDescription(false));
    info.add(governmentDescription, BorderLayout.CENTER);
    String leaderDesc = "<html>No leader as ruler.</html>";
    if (realm.getRuler() != null) {
      StringBuilder sb = new StringBuilder();
      ArrayList<Perk> perkList = realm.getRuler().getPerkList();
      for (Perk perk : perkList) {
        if (perk.isKnownPerk(knowledgeBonus)) {
          sb.append("<li> ");
          sb.append(perk.getName());
          sb.append("<br>");
        }
      }
      if (sb.length() > 0) {
        leaderDesc = "<html>" + realm.getRuler().getDescription()
            + "<ht><br>Perks:<br>" + sb.toString()
            + "<hr><br>" + LeaderUtility.createBioForLeader(realm.getRuler(),
                realm) + "</html>";
      } else {
        leaderDesc = "<html>" + realm.getRuler().getDescription()
            + "<hr><br>" + LeaderUtility.createBioForLeader(realm.getRuler(),
                realm) + "</html>";
      }
      leaderDesc = leaderDesc.replace("\n", "<br>");
    }
    if (knowledgeBonus == 0 && meetings == 0) {
      leaderDesc = "<html>No knowledge about the leader.</html>";
    }
    if (knowledgeBonus == 0 && meetings == 1) {
      if (realm.getRuler() != null) {
        leaderDesc = "<html>" + realm.getRuler().getCallName() + "</html>";
      } else {
        leaderDesc = "<html>No leader as ruler.</html>";
      }
    }
    HyperLabel leaderDescription = new HyperLabel(leaderDesc);
    info.add(leaderDescription);
    panelX.add(info);
    base.add(panelX);
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
}
