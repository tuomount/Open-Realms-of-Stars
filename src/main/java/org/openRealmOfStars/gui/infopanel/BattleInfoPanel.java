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
import org.openRealmOfStars.gui.labels.ImageLabel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.player.ship.Ship;

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
  
  private static final int MAX_BTN = 12;
  
  /**
   * Component Buttons
   */
  private ComponentButton[] cBtn = new ComponentButton[MAX_BTN];
  
  
  public BattleInfoPanel(Ship ship,InfoTextArea shipInfo, ActionListener listener) {
    this.add(Box.createRigidArea(new Dimension(130,25)));
    BufferedImage img = new BufferedImage(Tile.MAX_WIDTH*2, Tile.MAX_HEIGHT*2,
        BufferedImage.TYPE_4BYTE_ABGR);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    imageLabel = new ImageLabel(img, true);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    imageLabel.setFillColor(Color.black);
    Graphics2D g2d = img.createGraphics();
    g2d.setColor(Color.black);
    g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
    this.add(imageLabel);
    this.add(Box.createRigidArea(new Dimension(10,10)));
    textArea = new InfoTextArea();
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(textArea);
    this.add(Box.createRigidArea(new Dimension(10,10)));
    InvisiblePanel invisible = new InvisiblePanel(this);
    invisible.setLayout(new GridLayout(6, 2));
    for (int i=0;i<MAX_BTN;i++) {
      cBtn[i] = new ComponentButton(ship, i);
      cBtn[i].setActionCommand(GameCommands.COMMAND_COMPONENT_USE+i);
      cBtn[i].addActionListener(listener);
      invisible.add(cBtn[i]);
    }    
    this.add(invisible);
    if (shipInfo != null) {
      this.add(Box.createRigidArea(new Dimension(10,10)));
      this.add(shipInfo);
    }
  }
  

  /**
   * Show ship on info panel
   * @param shipToShow The ship to show on info panel
   */
  public void showShip(Ship shipToShow) {
    this.ship = shipToShow;
    for (int i=0;i<MAX_BTN;i++) {
      cBtn[i].setComponent(this.ship, i);
      cBtn[i].setUsed(false);
    }
    updatePanel();
  }

  /**
   * Mark component as used
   * @param index The component index
   */
  public void useComponent(int index) {
    if (index >= 0 && index < MAX_BTN) {
      cBtn[index].setUsed(true);
    }
  }
  
  /**
   * Update panels according set data
   */
  public void updatePanel() {
    if (ship != null) {
      BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      imageLabel.setImage(ship.getHull().getImage());
      setTitle(ship.getName());
      textArea.setText(ship.getDescription());
      this.repaint();
    }else {
      setTitle("Battle info");
      BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g2d = img.createGraphics();
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
      imageLabel.setImage(img);
      textArea.setText("");
      this.repaint();
    }
  }
}
