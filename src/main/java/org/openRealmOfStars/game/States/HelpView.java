package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.SpaceLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.utilities.TextUtilities;

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
   * Search text field
   */
  private JTextField searchText;
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
    checkBox.setAlignmentX(LEFT_ALIGNMENT);
    if (tutorialEnabled) {
      checkBox.setSelected(true);
    }
    SpaceGreyPanel greyPanel = new SpaceGreyPanel();
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    greyPanel.add(checkBox);
    searchText = new JTextField();
    searchText.setFont(GuiStatics.getFontCubellan());
    searchText.setForeground(GuiStatics.COLOR_GREEN_TEXT);
    searchText.setBackground(Color.BLACK);
    searchText.setMaximumSize(new Dimension(300,
        GuiStatics.TEXT_FIELD_HEIGHT));
    searchText.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyReleased(final KeyEvent e) {
        // Nothing to do here
      }

      @Override
      public void keyPressed(final KeyEvent e) {
        // Nothing to do here
      }
    });
    greyPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    SpaceGreyPanel searchPanel = new SpaceGreyPanel();
    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
    SpaceLabel label = new SpaceLabel("Search:");
    searchPanel.add(label);
    searchPanel.add(Box.createRigidArea(new Dimension(2, 2)));
    searchPanel.add(searchText);
    searchPanel.add(Box.createRigidArea(new Dimension(2, 2)));
    IconButton button = new IconButton(
        Icons.getIconByName(Icons.ICON_SCROLL_UP).getIcon(),
        Icons.getIconByName(Icons.ICON_SCROLL_UP_PRESSED).getIcon(), false,
        GameCommands.COMMAND_SEARCH_BACKWARDS, searchPanel);
    searchPanel.add(button);
    button = new IconButton(
        Icons.getIconByName(Icons.ICON_SCROLL_DOWN).getIcon(),
        Icons.getIconByName(Icons.ICON_SCROLL_DOWN_PRESSED).getIcon(), false,
        GameCommands.COMMAND_SEARCH_FORWARDS, searchPanel);
    searchPanel.add(button);
    searchPanel.setAlignmentX(LEFT_ALIGNMENT);
    greyPanel.add(searchPanel);

    base.add(greyPanel, BorderLayout.NORTH);
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

  @Override
  public void valueChanged(final TreeSelectionEvent e) {
    SoundPlayer.playMenuSound();
    if (tutorialTree.getSelectionPath() != null
        && tutorialTree.getSelectionPath().getLastPathComponent()
        instanceof DefaultMutableTreeNode) {
      Object object = ((DefaultMutableTreeNode)
          tutorialTree.getSelectionPath().getLastPathComponent())
          .getUserObject();
      if (object instanceof HelpLine) {
        HelpLine line = (HelpLine) object;
        infoText.setText(TextUtilities.handleEscapes(line.getText()));
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
