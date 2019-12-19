package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.tutorial.HelpLine;
import org.openRealmOfStars.game.tutorial.TutorialList;
import org.openRealmOfStars.gui.ListRenderers.TutorialTreeCellRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019  Tuomo Untinen
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
* Help view for showing all the help/tutorial texts.
*
*/
public class HelpView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Info Text for help/tutorial
   */
  private InfoTextArea infoText;

  /**
   * JTree containing all the tutorials.
   */
  private JTree tutorialTree;

  /**
   * Constructor for help view.
   * @param tutorial Tutorial list of help lines
   * @param listener ActionListener
   */
  public HelpView(final TutorialList tutorial, final ActionListener listener) {
    InfoPanel base = new InfoPanel();
    base.setTitle("Game help");
    this.setLayout(new BorderLayout());
    base.setLayout(new BorderLayout());

    String[] categories = tutorial.getCategories();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tutorial");
    for (String category : categories) {
      DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(
          category);
      root.add(categoryNode);
      HelpLine[] lines = tutorial.getTopics(category);
      for (HelpLine line : lines) {
        DefaultMutableTreeNode helpNode = new DefaultMutableTreeNode(line);
        categoryNode.add(helpNode);
      }
    }
    tutorialTree = new JTree(root);
    tutorialTree.setBackground(Color.BLACK);
    tutorialTree.setCellRenderer(new TutorialTreeCellRenderer());
    tutorialTree.setFont(GuiStatics.getFontCubellanSmaller());
    JScrollPane scroll = new JScrollPane(tutorialTree);
    base.add(scroll, BorderLayout.WEST);
    infoText = new InfoTextArea();
    base.add(infoText, BorderLayout.CENTER);
    this.add(base, BorderLayout.CENTER);

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
