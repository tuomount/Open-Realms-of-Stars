package org.openRealmOfStars.gui.panels;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.borders.SimpleBorder;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.BaseInfoTextArea;
import org.openRealmOfStars.gui.labels.IconLabel;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;

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
  private BaseInfoTextArea msgText;

  /**
   * Message count label
   */
  private TransparentLabel countLabel;

  /**
   * Title label
   */
  private IconLabel titleLabel;
  /**
   * Focus button
   */
  private SpaceButton btnFocus;

  /**
   * Constructor for message panel
   * @param prevCommand Prev button command
   * @param nextCommand Next button command
   * @param focusCommand Focus button command
   * @param msg Message to shown
   * @param index for current message
   * @param maxIndex maximum index
   * @param listener Action listener
   */
  public MessagePanel(final String prevCommand, final String nextCommand,
      final String focusCommand, final Message msg, final int index,
      final int maxIndex, final ActionListener listener) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBorder(new SimpleBorder());
    this.setBackground(GuiStatics.COLOR_SPACE_GREY_BLUE);
    JPanel pane = new JPanel();
    pane.setBackground(GuiStatics.COLOR_SPACE_GREY_BLUE);
    pane.setBorder(new SimpleBorder());
    pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
    btnPrev = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, prevCommand, pane);
    btnPrev.addActionListener(listener);
    pane.add(Box.createRigidArea(new Dimension(5, 30)));
    pane.add(btnPrev);
    countLabel = new TransparentLabel(pane, "1000/1000");
    pane.add(countLabel);
    titleLabel = new IconLabel(pane, Icons.getIconByName(Icons.ICON_PEOPLE),
        MessageType.CONSTRUCTION.toString());
    pane.add(titleLabel);
    btnNext = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, nextCommand, pane);
    btnFocus = new SpaceButton("Focus", focusCommand);
    btnFocus.addActionListener(listener);
    pane.add(btnFocus);
    btnNext.addActionListener(listener);
    pane.add(btnNext);
    pane.add(Box.createRigidArea(new Dimension(5, 30)));
    this.add(pane);

    msgText = new BaseInfoTextArea(2, 15);
    msgText.setEditable(false);
    JScrollPane scroll = new JScrollPane(msgText);
    this.add(scroll);

    updatePanel(msg, index, maxIndex);
  }

  /**
   * Update Message panel with new message and indexes
   * @param msg Message to show
   * @param index Message index
   * @param max Maximum messages
   */
  public void updatePanel(final Message msg, final int index, final int max) {
    int msgIndex = index + 1;
    countLabel.setText(msgIndex + "/" + max);
    titleLabel.setText(msg.getType().toString());
    titleLabel.setLeftIcon(msg.getIcon());
    msgText.setText(msg.getMessage());
    if (msg.getType() == MessageType.INFORMATION) {
      btnFocus.setEnabled(false);
    } else {
      btnFocus.setEnabled(true);
    }
  }
}
