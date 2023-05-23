package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.ListRenderers.ShipStatRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.BaseInfoTextArea;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
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
 * Ship view for showing ship design and stats
 *
 */

public class ShipView extends BlackPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Player Info
   */
  private PlayerInfo player;

  /**
   * List of ship design
   */
  private JList<ShipStat> shipList;

  /**
   * Ship image label
   */
  private ImageLabel shipImage;

  /**
   * Text is containing information about the ship design and stats
   */
  private BaseInfoTextArea infoText;

  /**
   * Copy button was clicked
   */
  private boolean copyClicked;

  /**
   * Last selected ship design index
   */
  private int lastSelectedIndex = -1;

  /**
   * Obsolete button for ship
   */
  private SpaceButton obsoleteBtn;

  /**
   * Delete button for ship
   */
  private SpaceButton deleteBtn;

  /**
   * Create new ship view
   * @param player Player Info
   * @param listener Action Listener
   */
  public ShipView(final PlayerInfo player, final ActionListener listener) {
    this.player = player;
    this.copyClicked = false;
    this.setLayout(new BorderLayout());
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Ships");
    shipList = new JList<>();
    shipList.setCellRenderer(new ShipStatRenderer());
    updateList();
    shipList.setBackground(Color.BLACK);
    shipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scroll = new JScrollPane(shipList);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(scroll);
    obsoleteBtn = new SpaceButton("Obsolete design",
        GameCommands.COMMAND_OBSOLETE_SHIP);
    obsoleteBtn.addActionListener(listener);
    panel.add(obsoleteBtn);
    deleteBtn = new SpaceButton("Delete design",
        GameCommands.COMMAND_DELETE_SHIP);
    deleteBtn.addActionListener(listener);
    deleteBtn.setEnabled(false);
    panel.add(deleteBtn);
    SpaceButton btn = new SpaceButton("Copy design",
        GameCommands.COMMAND_COPY_SHIP);
    btn.addActionListener(listener);
    panel.add(btn);
    btn = new SpaceButton("New design", GameCommands.COMMAND_SHIPDESIGN);
    btn.addActionListener(listener);
    panel.add(btn);
    base.add(panel, BorderLayout.WEST);

    panel = new SpaceGreyPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    shipImage = new ImageLabel(
        ShipImages.humans().getShipImage(ShipImage.SCOUT), true);
    shipImage.setFillColor(Color.BLACK);
    panel.add(shipImage);
    panel.add(Box.createRigidArea(new Dimension(5, 5)));
    infoText = new BaseInfoTextArea(30, 30);
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    scroll = new JScrollPane(infoText);
    scroll.setBackground(GuiStatics.getDeepSpaceDarkColor());
    panel.add(scroll);
    base.add(panel, BorderLayout.CENTER);

    this.add(base, BorderLayout.CENTER);

    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);

    // updatePanel();
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);

  }

  /**
   * Handle actions for ship view.
   * @param arg0 ActionEvent command what player did
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)) {
      if (shipList.getSelectedIndex() != -1) {
        if (shipList.getSelectedIndex() != lastSelectedIndex) {
          lastSelectedIndex = shipList.getSelectedIndex();
          ShipStat stat = shipList.getSelectedValue();
          if (stat.isObsolete()) {
            obsoleteBtn.setText("Activate");
            if (stat.getNumberOfBuilt() == 0) {
              deleteBtn.setEnabled(true);
            } else {
              deleteBtn.setEnabled(false);
            }
          } else {
            obsoleteBtn.setText("Obsolete");
            deleteBtn.setEnabled(false);
          }
          infoText.setText(stat.toString());
          shipImage.setImage(stat.getDesign().getHull().getImage());
          this.repaint();
        }
      } else {
        lastSelectedIndex = -1;
        infoText.setText("");
        this.repaint();
        shipImage.setImage(ShipImages.humans().getShipImage(ShipImage.COLONY));
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_OBSOLETE_SHIP)
        && shipList.getSelectedIndex() != -1) {
      ShipStat stat = shipList.getSelectedValue();
      if (!stat.isObsolete()) {
        if (stat.getDesign().getHull().getName().equals("Minor orbital")) {
         int count = player.countMinorOrbitalDesign();
         if (count > 1) {
           stat.setObsolete(true);
           if (stat.getNumberOfBuilt() == 0) {
             deleteBtn.setEnabled(true);
           }
           SoundPlayer.playMenuSound();
         } else {
           SoundPlayer.playMenuDisabled();
         }
        } else {
          stat.setObsolete(true);
          if (stat.getNumberOfBuilt() == 0) {
            deleteBtn.setEnabled(true);
          }
          SoundPlayer.playMenuSound();
        }
      } else {
        stat.setObsolete(false);
        deleteBtn.setEnabled(false);
        SoundPlayer.playMenuSound();
      }
      updateList();
    }
  }

  /**
   * Update ship list
   */
  public void updateList() {
    shipList.setListData(this.player.getShipStatListInOrder());
  }

  /**
   * Get PlayerInfo using ship View.
   * @return PlayerInfo
   */
  public PlayerInfo getPlayerInfo() {
    return player;
  }

  /**
   * Get Selected Stat.
   * @return Ship stat or null if no stat selected
   */
  public ShipStat getSelectedStat() {
    if (shipList.getSelectedIndex() != -1) {
      ShipStat stat = shipList.getSelectedValue();
      return stat;
    }
    return null;
  }

  /**
   * Get Ship design which is selected
   * @return Get selected ship design or null
   */
  public ShipDesign getSelectedShip() {
    if (shipList.getSelectedIndex() != -1) {
      ShipStat stat = shipList.getSelectedValue();
      return stat.getDesign();
    }
    return null;
  }

  /**
   * @return Copy button was clicked
   */
  public boolean isCopyClicked() {
    return copyClicked;
  }

  /**
   * Set the copy button was clicked
   * @param copyClicked copy button was clicked
   */
  public void setCopyClicked(final boolean copyClicked) {
    this.copyClicked = copyClicked;
  }

}
