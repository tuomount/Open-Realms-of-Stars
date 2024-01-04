package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019-2022 Tuomo Untinen
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
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;

/**
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
   * Button for abstain.
   */
  private SpaceButton voteAbstain;
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
    voteTitle = new SpaceLabel("<html>Participate to "
        + VotingType.SECOND_CANDIDATE_MILITARY.getDescription()
        + "\nAnother line</html>");
    voteTitle.setAlignmentX(LEFT_ALIGNMENT);
    panel.add(voteTitle);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    votingTime = new SpaceLabel("<html>Participate to "
        + VotingType.SECOND_CANDIDATE_MILITARY.getDescription()
        + "\nAnother line</html>");
    votingTime.setAlignmentX(LEFT_ALIGNMENT);
    panel.add(votingTime);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    panel.setAlignmentX(LEFT_ALIGNMENT);
    InfoPanel votingPanel = new InfoPanel();
    votingPanel.setLayout(new BorderLayout());
    votingPanel.setTitle("Voting results");
    votedText = new InfoTextArea();
    votedText.setEditable(false);
    votedText.setFont(GuiFonts.getFontCubellanSmaller());
    votingPanel.add(votedText, BorderLayout.CENTER);
    center.add(votingPanel);
    votingInfoTitle = new InfoPanel();
    votingInfoTitle.setLayout(new BorderLayout());
    votingInfoTitle.setTitle("Your promises");
    votingInfoText = new InfoTextArea();
    votingInfoText.setEditable(false);
    votingInfoText.setFont(GuiFonts.getFontCubellanSmaller());
    votingInfoText.setWrapStyleWord(true);
    votingInfoText.setLineWrap(true);
    votingInfoTitle.add(votingInfoText, BorderLayout.CENTER);
    center.add(votingInfoTitle);
    EmptyInfoPanel panelx = new EmptyInfoPanel();
    panelx.setLayout(new BorderLayout());
    EmptyInfoPanel panelButtons = new EmptyInfoPanel();
    GridLayout layout = new GridLayout(0, 5);
    layout.setVgap(50);
    panelButtons.setLayout(layout);
    voteYesBtn = new SpaceButton("Participate vote",
        GameCommands.COMMAND_VOTE_YES);
    voteYesBtn.addActionListener(listener);
    panelButtons.add(voteYesBtn);
    panelButtons.add(Box.createRigidArea(new Dimension(50, 10)));
    panelx.add(Box.createRigidArea(new Dimension(50, 10)), BorderLayout.WEST);
    panelx.add(Box.createRigidArea(new Dimension(50, 10)), BorderLayout.EAST);
    voteAbstain = new SpaceButton("Abstain", GameCommands.COMMAND_ABSTAIN);
    voteAbstain.addActionListener(listener);
    panelButtons.add(voteAbstain);
    panelButtons.add(Box.createRigidArea(new Dimension(50, 10)));
    voteNoBtn = new SpaceButton("Boycott voting", GameCommands.COMMAND_VOTE_NO);
    voteNoBtn.addActionListener(listener);
    panelButtons.add(voteNoBtn);
    panelx.add(panelButtons, BorderLayout.CENTER);
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
    IconButton iBtn = new IconButton(GuiStatics.getArrowLeft(),
        GuiStatics.getArrowLeftPressed(), false,
        GameCommands.COMMAND_PREV_VOTE, this);
    iBtn.setToolTipText("Previous vote");
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 10)));
    ArrayList<Vote> votes = map.getVotes().getVotableVotes();
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
    iBtn = new IconButton(GuiStatics.getArrowRight(),
        GuiStatics.getArrowRightPressed(), false,
        GameCommands.COMMAND_NEXT_VOTE, this);
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
  private static InfoPanel createBottomPanel(final ActionListener listener) {
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
    if (map.getVotes().getVotableVotes().size() > 0) {
      voteLabel.setText("Vote " + (voteIndex + 1) + "/"
          + map.getVotes().getVotableVotes().size());
      Vote vote = map.getVotes().getVotableVotes().get(voteIndex);
      if (vote.getType() == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
        voteTitle.setText("Participate to " + vote.getType().getDescription());
      } else {
        voteTitle.setText("<html>Vote for " + vote.getDescription(map)
            + "</htlm>");
      }
      if (vote.getTurnsToVote() != 1) {
        votingTime.setText("Voting time: " + vote.getTurnsToVote()
            + " star years");
      } else {
        votingTime.setText("Voting time: " + vote.getTurnsToVote()
            + " star year");
      }
      StringBuilder sb = new StringBuilder(80);
      for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
        VotingChoice choice = vote.getChoice(i);
        sb.append(map.getPlayerList().getPlayerInfoByIndex(i).getEmpireName());
        sb.append(": ");
        if (vote.getType() == VotingType.RULER_OF_GALAXY) {
          if (choice == VotingChoice.VOTED_YES) {
            sb.append("1st candidate");
          } else if (choice == VotingChoice.VOTED_NO) {
            sb.append("2nd candidate");
          } else {
            sb.append("Not voted");
          }
        } else {
          sb.append(choice.getDescription());
        }
        sb.append("\n");
      }
      votedText.setText(sb.toString());
      sb = new StringBuilder(80);
      if (vote.getTurnsToVote() == 0) {
        votingInfoTitle.setTitle("Voting results");
        voteYesBtn.setEnabled(false);
        voteNoBtn.setEnabled(false);
        voteAbstain.setEnabled(false);
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
        if (vote.getType() == VotingType.RULER_OF_GALAXY) {
          sb.append("1st candidate: ");
        } else {
          sb.append("Yes: ");
        }
        sb.append(yes);
        sb.append("\n");
        if (vote.getType() == VotingType.RULER_OF_GALAXY) {
          sb.append("2nd candidate: ");
        } else {
          sb.append("No: ");
        }
        sb.append(no);
        sb.append("\n");
      } else {
        voteYesBtn.setEnabled(true);
        voteNoBtn.setEnabled(true);
        voteAbstain.setEnabled(false);
        if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
          if (vote.getType() == VotingType.RULER_OF_GALAXY) {
            voteYesBtn.setText("1st Candidate");
            voteNoBtn.setText("2nd Candiate");
            voteAbstain.setEnabled(true);
          } else {
            voteYesBtn.setText("Vote YES");
            voteNoBtn.setText("Vote NO");
          }
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
                if (vote.getType() == VotingType.RULER_OF_GALAXY) {
                  sb.append("You promised to vote 1st candidate for ");
                } else {
                  sb.append("You promised to vote YES for ");
                }
                sb.append(info.getEmpireName());
                sb.append("\n");
                count++;
              } else if (info.getDiplomacy().getDiplomacyList(currentRealmIndex)
                  .isBonusType(DiplomacyBonusType.PROMISED_VOTE_NO)) {
                if (vote.getType() == VotingType.RULER_OF_GALAXY) {
                  sb.append("You promised to vote 2nd candidate for ");
                } else {
                  sb.append("You promised to vote NO for ");
                }
                sb.append(info.getEmpireName());
                sb.append("\n");
                count++;
              }
            }
          }
          if (count == 0) {
            sb.append("You made no promises to anyone!\n");
          }
          sb.append("\nVoting promises others made to you:\n");
          count = 0;
          for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
            PlayerInfo info = map.getPlayerList().getPlayerInfoByIndex(
                currentRealmIndex);
            PlayerInfo realm = map.getPlayerList().getPlayerInfoByIndex(i);
            if (info != null && realm != null
                && info.getDiplomacy().getDiplomacyList(i)
                != null) {
              if (info.getDiplomacy().getDiplomacyList(i)
                  .isBonusType(DiplomacyBonusType.PROMISED_VOTE_YES)) {
                sb.append(realm.getEmpireName());
                if (vote.getType() == VotingType.RULER_OF_GALAXY) {
                  sb.append(" promised to vote 1st candidate for you.");
                } else {
                  sb.append(" promised to vote YES for you");
                }
                sb.append("\n");
                count++;
              } else if (info.getDiplomacy().getDiplomacyList(i)
                  .isBonusType(DiplomacyBonusType.PROMISED_VOTE_NO)) {
                sb.append(realm.getEmpireName());
                if (vote.getType() == VotingType.RULER_OF_GALAXY) {
                  sb.append(" promised to vote 2nd candidate for you");
                } else {
                  sb.append(" promised to vote NO for you");
                }
                sb.append("\n");
                count++;
              }
            }
          }
          if (count == 0) {
            sb.append("Others made no promises to you!");
          }
        } else {
          voteYesBtn.setText("Participate");
          voteNoBtn.setText("Boycott");
          votingInfoTitle.setTitle("Galactic Olympics");
          sb.append("Galactic Olympics are being organized by ");
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
      voteYesBtn.setEnabled(false);
      voteNoBtn.setEnabled(false);
      voteAbstain.setEnabled(false);
      voteLabel.setText("Vote 0/0");
      voteTitle.setText("No vote available!");
      votingTime.setText("Voting time: 0 star years");
      votingInfoText.setText("No vote");
      votedText.setText("No votes");
    }
  }

  /**
   * Handle actions in voting view
   * @param arg0 ActionEvent
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_VOTE_YES)) {
      Vote vote = map.getVotes().getVotableVotes().get(voteIndex);
      int index = map.getPlayerList().getCurrentPlayer();
      vote.setChoice(index, VotingChoice.VOTED_YES);
      updatePanels();
      SoundPlayer.playMenuSound();
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_VOTE_NO)) {
      Vote vote = map.getVotes().getVotableVotes().get(voteIndex);
      int index = map.getPlayerList().getCurrentPlayer();
      vote.setChoice(index, VotingChoice.VOTED_NO);
      updatePanels();
      SoundPlayer.playMenuSound();
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ABSTAIN)) {
      Vote vote = map.getVotes().getVotableVotes().get(voteIndex);
      int index = map.getPlayerList().getCurrentPlayer();
      vote.setChoice(index, VotingChoice.ABSTAIN);
      updatePanels();
      SoundPlayer.playMenuSound();
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_VOTE)
        && voteIndex > 0) {
      voteIndex--;
      updatePanels();
      SoundPlayer.playMenuSound();
      return;
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_VOTE)
        && voteIndex < map.getVotes().getVotableVotes().size() - 1) {
      voteIndex++;
      updatePanels();
      SoundPlayer.playMenuSound();
      return;
    }
  }
}
