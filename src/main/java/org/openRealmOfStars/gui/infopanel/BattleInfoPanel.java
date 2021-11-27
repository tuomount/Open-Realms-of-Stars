package org.openRealmOfStars.gui.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.buttons.ComponentButton;
import org.openRealmOfStars.gui.buttons.SpaceCheckBox;
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.SpaceGreyPanel;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipImage;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
 * Handling info on next to the battle map
 *
 */

public class BattleInfoPanel extends InfoPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Image Label for image
   */
  private ImageLabel imageLabel;

  /**
   * Text area containing info
   */
  private InfoTextArea textArea;

  /**
   * Show info about the current ship
   */
  private Ship ship;

  /**
   * Check box to automatically fire all weapons.
   */
  private SpaceCheckBox useAllWeapons;
  /**
   * Maximum number of buttons on panel. These are the component buttons.
   */
  private static final int MAX_BTN = 12;

  /**
   * Component Buttons
   */
  private ComponentButton[] cBtn = new ComponentButton[MAX_BTN];


  /**
   * Width of rigid box
   */
  private static final int RIGID_BOX_WIDTH = 130;
  /**
   * Height of rigid box
   */
  private static final int RIGID_BOX_HEIGHT = 25;
  /**
   * Constructor for battle info panel. This draws information for current
   * combat ship
   * @param ship CombatShip which information is shown
   * @param shipInfo TextArea where longer description can be shown
   * @param overloadInfo TextArea where overload info is being shown
   * @param combat True if this is being called from bombat, false for bombing.
   * @param listener ActionListener for weapons and other components
   */
  public BattleInfoPanel(final Ship ship, final InfoTextArea shipInfo,
      final InfoTextArea overloadInfo, final boolean combat,
      final ActionListener listener) {
    this.add(Box.createRigidArea(new Dimension(RIGID_BOX_WIDTH,
        RIGID_BOX_HEIGHT)));
    BufferedImage img = new BufferedImage(Tile.MAX_WIDTH * 2,
        Tile.MAX_HEIGHT * 2, BufferedImage.TYPE_4BYTE_ABGR);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setTitle("Battle info");
    imageLabel = new ImageLabel(img, true);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    imageLabel.setFillColor(Color.black);
    Graphics2D g2d = img.createGraphics();
    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    g2d.dispose();
    this.add(imageLabel);
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(textArea);
    if (overloadInfo != null) {
      this.add(Box.createRigidArea(new Dimension(10, 10)));
      this.add(overloadInfo);
    }
    if (combat) {
      this.add(Box.createRigidArea(new Dimension(5, 5)));
      useAllWeapons = new SpaceCheckBox("Use all weapons");
      useAllWeapons.setSelected(true);
      useAllWeapons.addActionListener(listener);
      useAllWeapons.setActionCommand(GameCommands.COMMAND_USE_ALL_WEAPONS);
      useAllWeapons.setAlignmentX(Component.CENTER_ALIGNMENT);
      this.add(useAllWeapons);
    }
    SpaceGreyPanel panel = new SpaceGreyPanel();
    panel.setLayout(new GridLayout(6, 2));
    for (int i = 0; i < MAX_BTN; i++) {
      cBtn[i] = new ComponentButton(ship, i);
      cBtn[i].setActionCommand(GameCommands.COMMAND_COMPONENT_USE + i);
      cBtn[i].addActionListener(listener);
      panel.add(cBtn[i]);
    }
    this.add(panel);
    if (shipInfo != null) {
      this.add(Box.createRigidArea(new Dimension(10, 10)));
      this.add(shipInfo);
    }
    showShip(ship);
  }

  /**
   * Get Best weapon for distance
   * @param distance Distance where to shoot
   * @return Index of best weapon or -1 if none available.
   */
  public int getBestWeaponForDistance(final int distance) {
    int bestDamage = -1;
    int bestIndex = -1;
    for (int i = 0; i < MAX_BTN; i++) {
      if (cBtn[i] != null) {
        ShipComponent component = ship.getComponent(i);
        if (component != null && component.isWeapon()
            && ship.getWeaponRange(component) >= distance
            && !cBtn[i].isUsed()
            && ship.componentIsWorking(i)) {
          int damage = component.getDamage();
          if (component.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
            // ECM should be fired first
            damage = damage + 10;
          }
          if (component.getType() == ShipComponentType.ION_CANNON) {
            // Ion cannon also cause damage to shield
            damage = damage + 2;
          }
          if (component.getType() == ShipComponentType.PLASMA_CANNON) {
            // Nothing stops fully plasma cannon
            damage = damage + 5;
          }
          if (damage > bestDamage) {
            bestIndex = i;
            bestDamage = damage;
          }
        }
      }
    }
    return bestIndex;
  }
  /**
   * Show ship on info panel
   * @param shipToShow The ship to show on info panel
   */
  public void showShip(final Ship shipToShow) {
    this.ship = shipToShow;
    for (int i = 0; i < MAX_BTN; i++) {
      cBtn[i].setComponent(this.ship, i);
      cBtn[i].setUsed(false);
    }
    updatePanel();
  }

  /**
   * Update current ship information on buttons.
   */
  public void updateShip() {
    if (this.ship != null) {
      for (int i = 0; i < MAX_BTN; i++) {
        cBtn[i].setComponent(this.ship, i);
      }
    }
  }

  /**
   * Mark component as used
   * @param index The component index
   */
  public void useComponent(final int index) {
    if (index >= 0 && index < MAX_BTN) {
      cBtn[index].setUsed(true);
    }
  }

  /**
   * Set component botton enabling or disable
   * @param enabled True to enable buttons
   */
  public void setBtnEnabled(final boolean enabled) {
    for (int i = 0; i < MAX_BTN; i++) {
      cBtn[i].setEnabled(enabled);
    }
  }

  /**
   * Reset Ship Component uses.
   */
  public void resetComponentUses() {
    for (int i = 0; i < MAX_BTN; i++) {
      cBtn[i].setUsed(false);
    }
  }

  /**
   * Update panels according set data
   * @param extraText This text will be added after ships's description.
   *                  This can be null and nothing is added.
   */
  public void updatePanel(final String extraText) {
    if (ship != null) {
      BufferedImage img = new BufferedImage(ShipImage.MAX_WIDTH,
          ShipImage.MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      g2d.dispose();
      imageLabel.setImage(ship.getHull().getImage());
      setTitle(ship.getName());
      if (extraText != null) {
        textArea.setText(ship.getDescription() + "\n\n" + extraText);
      } else {
        textArea.setText(ship.getDescription());
      }
      this.repaint();
    } else {
      setTitle("Battle info");
      BufferedImage img = new BufferedImage(ShipImage.MAX_WIDTH,
          ShipImage.MAX_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      g2d.dispose();
      imageLabel.setImage(img);
      if (extraText != null) {
        textArea.setText(extraText);
      } else {
        textArea.setText("");
      }
      this.repaint();
    }
  }

  /**
   * Is use all weapons selected?
   * @return True if selected.
   */
  public boolean isUseAllWeapons() {
    if (useAllWeapons != null) {
      return useAllWeapons.isSelected();
    }
    return false;
  }

  /**
   * Toggle check on Use All Weapons checkbox.
   */
  public void toggleUseAllWeapons() {
    if (useAllWeapons != null) {
      if (useAllWeapons.isSelected()) {
        useAllWeapons.setSelected(false);
      } else {
        useAllWeapons.setSelected(true);
      }
    }
  }

  /**
   * Deselect all weapons checkbox.
   */
  public void disableAllWeapons() {
    if (useAllWeapons != null) {
      useAllWeapons.setSelected(false);
    }
  }
  /**
   * Update panels according set data
   */
  public void updatePanel() {
    updatePanel(null);
  }
}
