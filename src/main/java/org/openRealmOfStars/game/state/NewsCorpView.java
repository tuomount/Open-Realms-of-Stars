package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2020 Tuomo Untinen
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.NewsImagePanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.gui.util.UIScale;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.newsCorp.NewsData;

/**
*
* News corp view to show highlights from previous turn
*
*/
public class NewsCorpView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * News image
   */
  private NewsImagePanel newsImage;

  /**
   * List of news
   */
  private NewsData[] newsList;
  /**
   * Current news index
   */
  private int newsIndex = 0;

  /**
   * News list label
   */
  private SpaceLabel newsLabel;

  /**
   * Width of head line picture
   */
  private int widthHeadLine;
  /**
   * Height of head line picture
   */
  private int heightHeadLine;
  /**
   * Construtor for News Corp View.
   * @param news News Data
   * @param listener ActionListener
   */
  public NewsCorpView(final NewsData[] news, final ActionListener listener) {
    this.setLayout(new BorderLayout());
    newsList = news;
    InfoPanel base = new InfoPanel();
    boolean musicChanged = false;
    if (news[0].getNewsText().contains("Today is sad day for ")
        || news[0].getNewsText().contains("was killed in battle!")) {
      MusicPlayer.play(MusicPlayer.DEATH_IS_JUST_ANOTHER_PATH);
      musicChanged = true;
    }
    if (!musicChanged
        && MusicPlayer.getNowPlaying() != MusicPlayer.SPACE_THEME) {
      MusicPlayer.play(MusicPlayer.SPACE_THEME);
    }
    base.setLayout(new BorderLayout());
    base.setTitle("Galactic Broadcasting News Company");
    widthHeadLine = 800;
    heightHeadLine = 400;
    if (listener instanceof Game) {
      Game game = (Game) listener;
      heightHeadLine = game.getHeight() - UIScale.scale(100);
      widthHeadLine = game.getWidth() - 4;
    }
    newsImage = new NewsImagePanel(widthHeadLine, heightHeadLine);
    BufferedImage image = new BufferedImage(newsImage.getNewsImageWidth(),
        newsImage.getNewsImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image,
        news[0].getImageInstructions());
    newsImage.setNewsImage(image);
    newsImage.setText(newsList[newsIndex].getNewsText());
    base.add(newsImage, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.add(Box.createRigidArea(new Dimension(widthHeadLine / 2 - 160 / 2,
        5)));
    IconButton iBtn = new IconButton(GuiStatics.getArrowLeft(),
        GuiStatics.getArrowLeftPressed(), false,
        GameCommands.COMMAND_PREV_TARGET, this);
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    panel.add(Box.createRigidArea(new Dimension(10, 5)));
    newsLabel = new SpaceLabel("100/100");
    newsLabel.setText("1/" + newsList.length);
    panel.add(newsLabel);
    iBtn = new IconButton(GuiStatics.getArrowRight(),
        GuiStatics.getArrowRightPressed(), false,
        GameCommands.COMMAND_NEXT_TARGET, this);
    iBtn.addActionListener(listener);
    panel.add(iBtn);
    base.add(panel, BorderLayout.SOUTH);
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
   * Handle events for NewsCorpView.
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_TARGET)
        && newsIndex < newsList.length - 1) {
      newsIndex++;
      BufferedImage image = new BufferedImage(newsImage.getNewsImageWidth(),
          newsImage.getNewsImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      image = ImageInstruction.parseImageInstructions(image,
          newsList[newsIndex].getImageInstructions());
      newsImage.setNewsImage(image);
      newsImage.setText(newsList[newsIndex].getNewsText());
      newsLabel.setText(newsIndex + 1 + "/" + newsList.length);
      repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_TARGET)
        && newsIndex > 0) {
      newsIndex--;
      BufferedImage image = new BufferedImage(newsImage.getNewsImageWidth(),
          newsImage.getNewsImageHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      image = ImageInstruction.parseImageInstructions(image,
          newsList[newsIndex].getImageInstructions());
      newsImage.setNewsImage(image);
      newsImage.setText(newsList[newsIndex].getNewsText());
      newsLabel.setText(newsIndex + 1 + "/" + newsList.length);
      repaint();
    }
  }

  /**
   * Get current news index
   * @return current news index
   */
  public int getNewsIndex() {
    return newsIndex;
  }

}
