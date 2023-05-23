package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
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
   * Matches text
   */
  private SpaceLabel matchesText;

  /**
   * Root node.
   */
  private DefaultMutableTreeNode root;
  /**
   * Number of matches
   */
  private int numberOfMatches;
  /**
   * Last search text
   */
  private String lastSearchText;
  /**
   * Current match index;
   */
  private int currentMatch;
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
    root = new DefaultMutableTreeNode("Tutorial");
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
    tutorialTree.setForeground(GuiStatics.getInfoTextColor());
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
    searchText.setForeground(GuiStatics.getInfoTextColor());
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          applySearch(true);
        }
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
        GuiStatics.getSmallArrow(Icons.ICON_SCROLL_UP).getIcon(),
        GuiStatics.getSmallArrow(Icons.ICON_SCROLL_UP_PRESSED).getIcon(),
        false, GameCommands.COMMAND_SEARCH_BACKWARDS, searchPanel);
    button.addActionListener(listener);
    searchPanel.add(button);
    button = new IconButton(
        GuiStatics.getSmallArrow(Icons.ICON_SCROLL_DOWN).getIcon(),
        GuiStatics.getSmallArrow(Icons.ICON_SCROLL_DOWN_PRESSED).getIcon(),
        false, GameCommands.COMMAND_SEARCH_FORWARDS, searchPanel);
    button.addActionListener(listener);
    searchPanel.add(button);
    searchPanel.setAlignmentX(LEFT_ALIGNMENT);
    searchPanel.add(Box.createRigidArea(new Dimension(2, 2)));
    matchesText = new SpaceLabel("No matches");
    searchPanel.add(matchesText);
    greyPanel.add(searchPanel);
    greyPanel.add(Box.createRigidArea(new Dimension(2, 2)));

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

  /**
   * Search method for find text in help lines.
   * @param textToMatch Text to search
   * @param forward True for forward, false for backward
   * @return TreeNode array for path
   */
  public TreeNode[] search(final String textToMatch, final boolean forward) {
    if (lastSearchText != null && lastSearchText.equals(textToMatch)) {
      lastSearchText = textToMatch;
      if (forward) {
        currentMatch++;
      } else {
        currentMatch--;
      }
      if (currentMatch > numberOfMatches) {
        currentMatch = 1;
      }
      if (currentMatch <= 0) {
        currentMatch = numberOfMatches;
      }
    } else {
      lastSearchText = textToMatch;
      currentMatch = 1;
      numberOfMatches = -1;
    }
    int count = 0;
    TreeNode[] result = new TreeNode[3];
    boolean match = false;
    for (int i = 0; i < root.getChildCount(); i++) {
      TreeNode node = root.getChildAt(i);
      for (int j = 0; j < node.getChildCount(); j++) {
        TreeNode leaf = node.getChildAt(j);
        if (leaf instanceof DefaultMutableTreeNode) {
          DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) leaf;
          Object object = treeNode.getUserObject();
          if (object instanceof HelpLine) {
            HelpLine line = (HelpLine) object;
            if (line.getText().toLowerCase().contains(
                lastSearchText.toLowerCase())) {
              count++;
              if (count == currentMatch) {
                result[0] = root;
                result[1] = node;
                result[2] = leaf;
                match = true;
              }
            }
          }
        }
      }
    }
    if (numberOfMatches == -1) {
      numberOfMatches = count;
    }
    if (match) {
      return result;
    }
    return null;
  }
  /**
   * Apply actual search.
   * @param forward True to search forward.
   */
  public void applySearch(final boolean forward) {
    TreeNode[] path = search(searchText.getText(), forward);
    if (path != null) {
      tutorialTree.setSelectionPath(new TreePath(path));
      infoText.setHighlightText(searchText.getText());
    } else {
      tutorialTree.setSelectionPath(null);
      infoText.setHighlightText(null);
    }
    if (numberOfMatches > 0) {
      matchesText.setText(currentMatch + "/" + numberOfMatches
          + " matches");
    } else {
      matchesText.setText("Not found");
    }
    repaint();
  }
  /**
   * Handle actions in help view.
   * @param arg0 Action event to handle
   */
  public void handleActions(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_SEARCH_FORWARDS)) {
      if (!searchText.getText().isEmpty()) {
        SoundPlayer.playMenuSound();
        applySearch(true);
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }
    if (arg0.getActionCommand().equals(
        GameCommands.COMMAND_SEARCH_BACKWARDS)) {
      if (!searchText.getText().isEmpty()) {
        SoundPlayer.playMenuSound();
        applySearch(false);
      } else {
        SoundPlayer.playMenuDisabled();
      }
    }

  }
}
