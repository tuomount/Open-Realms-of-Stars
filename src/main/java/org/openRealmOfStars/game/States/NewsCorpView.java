package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ImagePanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
  private ImagePanel newsImage;

  /**
   * News text
   */
  private InfoTextArea textArea;
  /**
   * Construtor for News Corp View.
   * @param listener ActionListener
   */
  public NewsCorpView(final ActionListener listener) {
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Galactic Broadcasting News Company");
    // TODO: This image needs to be changed
    ImagePanel imagePanel = new ImagePanel(SpaceRace.HUMAN.getRaceImage());
    base.add(imagePanel, BorderLayout.WEST);
    InfoPanel newsPanel = new InfoPanel();
    newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
    newsPanel.setTitle("News headline");
    // TODO: This image needs to be changed
    newsImage = new ImagePanel(SpaceRace.HUMAN.getRaceImage());
    newsPanel.add(newsImage, BorderLayout.WEST);
    newsPanel.add(Box.createRigidArea(new Dimension(15, 10)));
    textArea = new InfoTextArea();
    textArea.setCharacterWidth(10);
    newsPanel.add(textArea);
    this.add(base, BorderLayout.CENTER);
    InvisiblePanel invis = new InvisiblePanel(newsPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.X_AXIS));
    IconButton iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_TARGET,
        this);
    iBtn.addActionListener(listener);
    invis.add(iBtn);
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_TARGET,
        this);
    iBtn.addActionListener(listener);
    invis.add(iBtn);
    newsPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    newsPanel.add(invis);
    base.add(newsPanel, BorderLayout.CENTER);
    
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
