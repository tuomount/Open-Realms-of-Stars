package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.VotingListRenderer;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ImagePanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.vote.Vote;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2022 Tuomo Untinen
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
* Voting selection view.
*
*/
public class VotingSelectionView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * News text
   */
  private InfoTextArea textArea;

  /**
   * ComboBox where to select next voting
   */
  private JComboBox<Vote> votingSelect;

  /**
   * StarMap
   */
  private StarMap map;
  /**
   * Voting selection view.
   * @param map StarMap
   * @param listener ActionListener
   */
  public VotingSelectionView(final StarMap map,
      final ActionListener listener) {
    this.setLayout(new BorderLayout());
    this.map = map;
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Next Galactic Voting");
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(ImageInstruction.OLD_DESK);
    int widthHeadLine = 800;
    int heightHeadLine = 600;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      widthHeadLine = game.getWidth();
    }
    BufferedImage image = new BufferedImage(widthHeadLine, heightHeadLine,
        BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image,
        instructions.build());
    ImagePanel imagePanel = new ImagePanel(image);
    base.add(imagePanel, BorderLayout.NORTH);
    this.add(base, BorderLayout.NORTH);
    int turns = map.getScoreVictoryTurn() * 5 / 100;
    Vote[] votes = map.getVotes().getListOfVoteables(
        map.getScoreDiplomacy() + 1, map.getPlayerList().getCurrentMaxRealms(),
        turns);
    votingSelect = new JComboBox<>(votes);
    votingSelect.setSelectedIndex(0);
    votingSelect.addActionListener(listener);
    votingSelect.setActionCommand(GameCommands.COMMAND_VOTING_SELECTED);
    votingSelect.setBackground(GuiStatics.COLOR_COOL_SPACE_BLUE_DARK);
    votingSelect.setForeground(GuiStatics.COLOR_COOL_SPACE_BLUE);
    VotingListRenderer renderer = new VotingListRenderer();
    renderer.setStarMap(map);
    votingSelect.setRenderer(renderer);
    votingSelect.setBorder(new SimpleBorder());
    votingSelect.setFont(GuiStatics.getFontCubellan());
    votingSelect.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        GuiStatics.TEXT_FIELD_HEIGHT));
    InfoPanel centerPanel = new InfoPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.add(votingSelect);
    centerPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    textArea.setFont(GuiStatics.getFontCubellanSmaller());
    Vote vote = (Vote) votingSelect.getSelectedItem();
    textArea.setText(vote.getDescription(map));
    centerPanel.add(textArea);
    this.add(centerPanel, BorderLayout.CENTER);

    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Selecting voting and continue",
        GameCommands.COMMAND_END_TURN);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Handle action events for Voting Slection
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand()
        .equals(GameCommands.COMMAND_VOTING_SELECTED)) {
      SoundPlayer.playMenuSound();
      Vote vote = (Vote) votingSelect.getSelectedItem();
      textArea.setText(vote.getDescription(map));
      this.repaint();
    }
  }

  /**
   * Get Voting selected.
   * @return Vote
   */
  public Vote getSelectedVote() {
    Vote vote = (Vote) votingSelect.getSelectedItem();
    return vote;
  }
}
