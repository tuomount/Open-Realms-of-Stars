package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* OptionsView for configuring volumes and resolution
*
*/
public class OptionsView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Game for changing resolution
   */
  private Game game;

  /**
   * Constructor for OptionsView
   * @param gameFrame Game frame
   * @param listener ActionListener
   */
  public OptionsView(final Game gameFrame, final ActionListener listener) {
    game = gameFrame;
    game.setResizable(true);
    InfoPanel base = new InfoPanel();
    base.setTitle("Options");
    this.setLayout(new BorderLayout());
    base.setLayout(new BorderLayout());
    InfoPanel acceptPanel = new InfoPanel();
    acceptPanel.setTitle("Options accept and apply");
    acceptPanel.setLayout(new BoxLayout(acceptPanel, BoxLayout.X_AXIS));
    SpaceButton btn = new SpaceButton("Apply", GameCommands.COMMAND_APPLY);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Apply changes but stay in options");
    acceptPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    acceptPanel.add(btn);
    acceptPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    btn = new SpaceButton("Cancel", GameCommands.COMMAND_CANCEL);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Cancel changes and exit");
    acceptPanel.add(btn);
    acceptPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    btn = new SpaceButton("OK", GameCommands.COMMAND_OK);
    btn.addActionListener(listener);
    btn.createToolTip();
    btn.setToolTipText("Apply changes and exit");
    acceptPanel.add(btn);
    base.add(acceptPanel, BorderLayout.NORTH);
    this.add(base, BorderLayout.CENTER);
  }
}
