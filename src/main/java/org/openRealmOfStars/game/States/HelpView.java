package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.tutorial.HelpLine;
import org.openRealmOfStars.game.tutorial.TutorialList;
import org.openRealmOfStars.gui.ListRenderers.TutorialTreeCellRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
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
public class HelpView extends BlackPanel implements TreeSelectionListener {

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
   * Check box for enabling the tutorial
   */
  private SpaceCheckBox checkBox;
  /**
   * Constructor for help view.
   * @param tutorial Tutorial list of help lines
   * @param tutorialEnabled Flag for if tutorial is enabled
   * @param listener ActionListener
   */
  public HelpView(final TutorialList tutorial, final boolean tutorialEnabled,
      final ActionListener listener) {
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
    tutorialTree.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);
    tutorialTree.setBackground(Color.BLACK);
    tutorialTree.setCellRenderer(new TutorialTreeCellRenderer());
    tutorialTree.setFont(GuiStatics.getFontCubellanSmaller());
    tutorialTree.addTreeSelectionListener(this);
    JScrollPane scroll = new JScrollPane(tutorialTree);
    base.add(scroll, BorderLayout.WEST);
    infoText = new InfoTextArea();
    infoText.setLineWrap(true);
    infoText.setCharacterWidth(7);
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    base.add(infoText, BorderLayout.CENTER);
    checkBox = new SpaceCheckBox("Tutorial enabled");
    checkBox.setToolTipText("If checked then tutorial is enabled in game.");
    if (tutorialEnabled) {
      checkBox.setSelected(true);
    }
    base.add(checkBox, BorderLayout.NORTH);
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

  /**
   * Replace all "\n" character sequence with line feed chars.
   * @param line String to handle
   * @return String
   */
  private String addLineChanges(final String line) {
    String result = line;
    int index = result.indexOf("\\");
    while (index > -1) {
      if (result.length() > index + 2 && result.charAt(index + 1) == 'n') {
        result = result.substring(0, index) + "\n"
            + result.substring(index + 2, result.length());
      }
      index = result.indexOf("\\");
    }
    return result;
  }
  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    SoundPlayer.playMenuSound();
    if (tutorialTree.getSelectionPath().getLastPathComponent()
        instanceof DefaultMutableTreeNode) {
      Object object = ((DefaultMutableTreeNode)
          tutorialTree.getSelectionPath().getLastPathComponent())
          .getUserObject();
      if (object instanceof HelpLine) {
        HelpLine line = (HelpLine) object;
        infoText.setText(addLineChanges(line.getText()));
        this.repaint();
      }
    }
  }

  /**
   * Is Tutorial enabled or not
   * @return True if tutorial is enabled.
   */
  public boolean isTutorialEnabled() {
    return checkBox.isSelected();
  }
}
