package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.EmptyInfoPanel;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Vote view for voting and showing voting result.
*
*/
public class VoteView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Label containing number of votes
   */
  private SpaceLabel voteLabel;
  /**
   * Voting index
   */
  private int voteIndex;
  /**
   * StarMap for whole starmap.
   */
  private StarMap map;
  /**
   * Voting title label.
   */
  private SpaceLabel voteTitle;
  /**
   * Voting time label.
   */
  private SpaceLabel votingTime;
  /**
   * Button for voting yes.
   */
  private SpaceButton voteYesBtn;
  /**
   * Button for voting no.
   */
  private SpaceButton voteNoBtn;
  /**
   * Info text area which shows how realms voted or not have voted yet.
   */
  private InfoTextArea votedText;
  /**
   * Info text area which shows informations about voting.
   */
  private InfoTextArea votingInfoText;
  /**
   * Infopanel for voting info, so that title can be changed.
   */
  private InfoPanel votingInfoTitle;

  /**
   * Create new vote view
   * @param map StarMap which contains players and planet lists.
   * @param listener Action Listener
   */
  public VoteView(final StarMap map, final ActionListener listener) {
    this.setLayout(new BorderLayout());
    this.map = map;
    this.add(createTopPanel(listener), BorderLayout.NORTH);
    this.add(createCenterPanel(listener), BorderLayout.CENTER);
    // Bottom panel
    this.add(createBottomPanel(listener), BorderLayout.SOUTH);
    updatePanels();
  }

  /**
   * Create center panel for view.
   * @param listener ActionListener
   * @return Center panel
   */
  private InfoPanel createCenterPanel(final ActionListener listener) {
    InfoPanel mainCenter = new InfoPanel();
    mainCenter.setLayout(new BorderLayout());
    mainCenter.setTitle("Voting");
    InfoPanel center = new EmptyInfoPanel();
    center.setLayout(new GridLayout(1, 0));
    InfoPanel panel = new EmptyInfoPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    voteTitle = new SpaceLabel("Participate to "
        + VotingType.GALACTIC_OLYMPIC_PARTICIPATE.getDescription());
    voteTitle.setAlignmentX(CENTER_ALIGNMENT);
    panel.add(voteTitle);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    votingTime = new SpaceLabel("Voting time: 20 turns");
    votingTime.setAlignmentX(CENTER_ALIGNMENT);
    panel.add(votingTime);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    panel.setAlignmentX(CENTER_ALIGNMENT);
    InfoPanel votingPanel = new InfoPanel();
    votingPanel.setLayout(new BorderLayout());
    votingPanel.setTitle("Voting results");
    votedText = new InfoTextArea();
    votedText.setEditable(false);
    votedText.setFont(GuiStatics.getFontCubellanSmaller());
    votingPanel.add(votedText, BorderLayout.CENTER);
    center.add(votingPanel);
    votingInfoTitle = new InfoPanel();
    votingInfoTitle.setLayout(new BorderLayout());
    votingInfoTitle.setTitle("Your promises");
    votingInfoText = new InfoTextArea();
    votingInfoText.setEditable(false);
    votingInfoText.setFont(GuiStatics.getFontCubellanSmaller());
    votingInfoTitle.add(votingInfoText, BorderLayout.CENTER);
    center.add(votingInfoTitle);
    EmptyInfoPanel panelx = new EmptyInfoPanel();
    panelx.setLayout(new BorderLayout());
    voteYesBtn = new SpaceButton("Participate", GameCommands.COMMAND_VOTE_YES);
    voteYesBtn.addActionListener(listener);
    panelx.add(voteYesBtn, BorderLayout.WEST);
    panelx.add(Box.createRigidArea(new Dimension(50, 10)));
    voteNoBtn = new SpaceButton("Boycott", GameCommands.COMMAND_VOTE_YES);
    voteNoBtn.addActionListener(listener);
    panelx.add(voteNoBtn, BorderLayout.EAST);
    EmptyInfoPanel north = new EmptyInfoPanel();
    north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
    north.add(panel);
    mainCenter.add(north, BorderLayout.NORTH);
    mainCenter.add(panelx, BorderLayout.SOUTH);
    mainCenter.add(center, BorderLayout.CENTER);
    return mainCenter;
  }
  /**
   * Create top panel for view.
   * @param listener ActionListener
   * @return Top panel for view.
   */
  private InfoPanel createTopPanel(final ActionListener listener) {
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Voting list");
    EmptyInfoPanel panel = new EmptyInfoPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    IconButton iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_VOTE,
        this);
    iBtn.setToolTipText("Previous vote");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    ArrayList<Vote> votes = map.getVotes().getVotes();
    voteIndex = votes.size() - 1;
    for (int i = 0; i < votes.size(); i++) {
      if (votes.get(i).getTurnsToVote() > 0) {
        voteIndex = i;
        break;
      }
    }
    voteLabel = new SpaceLabel("Vote " + votes.size() + "/" + votes.size());
    panel.add(voteLabel);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_VOTE,
        this);
    iBtn.setToolTipText("Next vote");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    EmptyInfoPanel panel2 = new EmptyInfoPanel();
    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
    panel2.add(panel);
    base.add(panel2, BorderLayout.CENTER);
    return base;
  }
  /**
   * Create Bottom panel for view.
   * @param listener ActionListener
   * @return BottomPanel
   */
  private InfoPanel createBottomPanel(final ActionListener listener) {
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton backBtn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    backBtn.addActionListener(listener);
    bottomPanel.add(backBtn, BorderLayout.CENTER);
    return bottomPanel;
  }
  /**
   * Update panels in view.
   */
  public void updatePanels() {
    if (map.getVotes().getVotes().size() > 0) {
      voteLabel.setText("Vote " + (voteIndex + 1) + "/"
          + map.getVotes().getVotes().size());
      Vote vote = map.getVotes().getVotes().get(voteIndex);
      if (vote.getType() == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
        voteTitle.setText("Participate to " + vote.getType().getDescription());
      } else {
        voteTitle.setText("Vote for " + vote.getType().getDescription());
      }
      if (vote.getTurnsToVote() != 1) {
        votingTime.setText("Voting time: " + vote.getTurnsToVote() + " turns");
      } else {
        votingTime.setText("Voting time: " + vote.getTurnsToVote() + " turn");
      }
      StringBuilder sb = new StringBuilder(80);
      for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
        VotingChoice choice = vote.getChoice(i);
        sb.append(map.getPlayerList().getPlayerInfoByIndex(i).getEmpireName());
        sb.append(": ");
        sb.append(choice.getDescription());
        sb.append("\n");
      }
      votedText.setText(sb.toString());
      sb = new StringBuilder(80);
      if (vote.getTurnsToVote() == 0) {
        votingInfoTitle.setTitle("Voting results");
        int yes = 0;
        int no = 0;
        for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
          VotingChoice choice = vote.getChoice(i);
          if (choice == VotingChoice.VOTED_YES) {
            yes = yes + map.getTotalNumberOfPopulation(i);
          }
          if (choice == VotingChoice.VOTED_NO) {
            no = no + map.getTotalNumberOfPopulation(i);
          }
        }
        sb.append("Votes:\n");
        sb.append("Yes: ");
        sb.append(yes);
        sb.append("\n");
        sb.append("No: ");
        sb.append(no);
        sb.append("\n");
      } else {
        if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
          votingInfoTitle.setTitle("Promises");
          sb.append("Voting promises you made to others:\n");
          int currentRealmIndex = map.getPlayerList().getCurrentPlayer();
          int count = 0;
          for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
            PlayerInfo info = map.getPlayerList().getPlayerInfoByIndex(i);
            if (info != null
                && info.getDiplomacy().getDiplomacyList(currentRealmIndex)
                != null) {
              if (info.getDiplomacy().getDiplomacyList(currentRealmIndex)
                  .isBonusType(DiplomacyBonusType.PROMISED_VOTE_YES)) {
                sb.append("You promised to vote YES for ");
                sb.append(info.getEmpireName());
                sb.append("\n");
                count++;
              } else if (info.getDiplomacy().getDiplomacyList(currentRealmIndex)
                  .isBonusType(DiplomacyBonusType.PROMISED_VOTE_NO)) {
                sb.append("You promised to vote NO for ");
                sb.append(info.getEmpireName());
                sb.append("\n");
                count++;
              }
            }
          }
          if (count == 0) {
            sb.append("You made no promises to anyone!");
          }
        } else {
          votingInfoTitle.setTitle("Galactic Olympics");
          sb.append("Galactic Olympics are being organized by");
          PlayerInfo info = map.getPlayerList().getPlayerInfoByIndex(
              vote.getOrganizerIndex());
          if (info != null) {
            sb.append(info.getEmpireName());
          }
          sb.append(" on planet called ");
          sb.append(vote.getPlanetName());
          sb.append(".\n");
        }
      }
      votingInfoText.setText(sb.toString());
    } else {
      voteLabel.setText("Vote 0/0");
      voteTitle.setText("No vote available!");
      votingTime.setText("Voting time: 0 turns");
      votingInfoText.setText("No vote");
      votedText.setText("No votes");
    }
  }
}
