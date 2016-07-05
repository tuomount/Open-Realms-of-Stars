package org.openRealmOfStars.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.TransparentLabel;

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
 * Message panel for showing messages
 * 
 */

public class MessagePanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Prev button
   */
  private IconButton btnPrev;
  
  /**
   * Next button
   */
  private IconButton btnNext;
  
  /**
   * Info Text for message
   */
  private InfoTextArea msgText;

  /**
   * Title label
   */
  private TransparentLabel titleLabel;
  /**
   * Focus button
   */
  private SpaceButton btnFocus;

  /**
   * Constructor for message panel
   * @param prevCommand Prev button command
   * @param nextCommand Next button command
   * @param focusCommand Focut button command
   * @param listener Action listener
   */
  public MessagePanel(String prevCommand, String nextCommand,
      String focusCommand,ActionListener listener) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBorder(new SimpleBorder());
    this.setBackground(GuiStatics.COLOR_SPACE_GREY_BLUE);
    JPanel pane = new JPanel();
    pane.setBackground(GuiStatics.COLOR_SPACE_GREY_BLUE);
    pane.setBorder(new SimpleBorder());
    pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
    btnPrev = new IconButton(Icons.getIconByName(Icons.ICON_SCROLL_LEFT),
        Icons.getIconByName(Icons.ICON_SCROLL_LEFT_PRESSED), false, 
        prevCommand, pane);
    btnPrev.addActionListener(listener);
    pane.add(btnPrev);
    titleLabel = new TransparentLabel(pane, "1000/1000");
    pane.add(titleLabel);
    btnNext = new IconButton(Icons.getIconByName(Icons.ICON_SCROLL_RIGHT),
        Icons.getIconByName(Icons.ICON_SCROLL_RIGHT_PRESSED), false, 
        nextCommand, pane);
    btnFocus = new SpaceButton("Focus", focusCommand);
    btnFocus.addActionListener(listener);
    pane.add(btnFocus);
    btnNext.addActionListener(listener);
    pane.add(btnNext);
    this.add(pane);
    
    msgText = new InfoTextArea(3,15);
    msgText.setEditable(false);
    JScrollPane scroll = new JScrollPane(msgText);
    this.add(scroll);

  }
}
