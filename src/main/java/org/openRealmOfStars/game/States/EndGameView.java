package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.labels.StarFieldTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* End Game view for Open Realm of Stars
*
*/

public class EndGameView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
  * Number of lines to show in text area
  */
  private static final int NUMBER_OF_LINES = 60;

  /**
   * Text area to show end of game story
   */
  private StarFieldTextArea textArea;

  /**
   * Constructor for End Game view.
   * @param listener ActionListener
   * @param info Realm that lost.
   */
  public EndGameView(final ActionListener listener, final PlayerInfo info) {
    String scrollText = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        + "\n\n\n\n\n\n\n\n\n\n\n" + "# Error unknown realm lost!\n\n";
    String title = null;
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        title = "REALM LOST!";
        break;
      }
      case 1: {
        title = "EXTERMINATION!";
        break;
      }
      case 3: {
        title = "ANHILATION!";
        break;
      }
    }
    if (info != null) {
      NewsData newsData = NewsFactory.makeLostNews(info);
      scrollText = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
          + "\n\n\n\n\n\n\n\n\n\n\n" + "# " + info.getEmpireName()
          + " has lost !\n\n"
          + "Realm of " + info.getEmpireName() + " was proud people of "
          + info.getRace().getName() + ". Unfortunately their existince in"
          + " known galaxy has ended.\n\n" + "# " + title + "\n\n"
          + newsData.getNewsText() + "\n\n# Next choices..."
          + "\n\nYou can now let AI finish the whole"
          + " game or just see current game history and statistics.";
    }
    this.setLayout(new BorderLayout());
    textArea = new StarFieldTextArea();
    textArea.setScrollText(scrollText, NUMBER_OF_LINES);
    textArea.setText(scrollText);
    textArea.setSmoothScroll(true);
    textArea.setEditable(false);
    this.add(textArea, BorderLayout.CENTER);

    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BorderLayout());
    SpaceButton btn = new SpaceButton("End game immediately",
        GameCommands.COMMAND_OK);
    btn.addActionListener(listener);
    panel.add(btn, BorderLayout.WEST);
    btn = new SpaceButton("Let AI finish the game",
        GameCommands.COMMAND_AI_FINISH);
    btn.addActionListener(listener);
    panel.add(btn, BorderLayout.EAST);
    this.add(panel, BorderLayout.SOUTH);
  }

  /**
   * Update Text area
   */
  public void updateTextArea() {
    if (textArea.getSmoothScrollNextRow()) {
      textArea.getNextLine();
    }
    repaint();
  }
}
