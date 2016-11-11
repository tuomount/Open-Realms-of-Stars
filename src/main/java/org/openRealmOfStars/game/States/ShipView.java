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

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.ListRenderers.ShipStatRenderer;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.BaseInfoTextArea;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipImage;
import org.openRealmOfStars.player.ship.ShipImages;
import org.openRealmOfStars.player.ship.ShipStat;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
   * Obsolete button for ship
   */
  private SpaceButton obsoleteBtn;

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
    shipList.setListData(this.player.getShipStatListInOrder());
    shipList.setBackground(Color.BLACK);
    shipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scroll = new JScrollPane(shipList);
    InvisiblePanel invisible = new InvisiblePanel(base);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(scroll);
    obsoleteBtn = new SpaceButton("Obsolete design",
        GameCommands.COMMAND_OBSOLETE_SHIP);
    obsoleteBtn.addActionListener(listener);
    invisible.add(obsoleteBtn);
    SpaceButton btn = new SpaceButton("Copy design",
        GameCommands.COMMAND_COPY_SHIP);
    btn.addActionListener(listener);
    invisible.add(btn);
    btn = new SpaceButton("New design", GameCommands.COMMAND_SHIPDESIGN);
    btn.addActionListener(listener);
    invisible.add(btn);
    base.add(invisible, BorderLayout.WEST);

    invisible = new InvisiblePanel(base);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    shipImage = new ImageLabel(
        ShipImages.humans().getShipImage(ShipImage.SCOUT), true);
    shipImage.setFillColor(Color.BLACK);
    invisible.add(shipImage);
    invisible.add(Box.createRigidArea(new Dimension(5, 5)));
    infoText = new BaseInfoTextArea(30, 30);
    infoText.setEditable(false);
    infoText.setFont(GuiStatics.getFontCubellanSmaller());
    scroll = new JScrollPane(infoText);
    invisible.add(scroll);
    base.add(invisible, BorderLayout.CENTER);

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
        ShipStat stat = shipList.getSelectedValue();
        if (stat.isObsolete()) {
          obsoleteBtn.setText("Activate");
        } else {
          obsoleteBtn.setText("Obsolete");
        }
        infoText.setText(stat.toString());
        shipImage.setImage(stat.getDesign().getHull().getImage());
        this.repaint();
      } else {
        infoText.setText("");
        this.repaint();
        shipImage.setImage(ShipImages.humans().getShipImage(ShipImage.COLONY));
      }
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_OBSOLETE_SHIP)
        && shipList.getSelectedIndex() != -1) {
      ShipStat stat = shipList.getSelectedValue();
      if (!stat.isObsolete()) {
        stat.setObsolete(true);
      } else {
        stat.setObsolete(false);
      }
      shipList.setListData(this.player.getShipStatListInOrder());
    }
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
