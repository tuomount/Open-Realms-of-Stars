package org.openRealmOfStars.gui.panels;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.labels.IconLabel;

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
 * Class for handling worker production. Basically a label with two buttons:
 * plus and minus buttons.
 *
 */

public class WorkerProductionPanel extends InvisiblePanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Minus button
   */
  private IconButton btnMinus;

  /**
   * Plus button
   */
  private IconButton btnPlus;

  /**
   * Label to show
   */
  private IconLabel label;

  /**
   * Create Worker Production panel with - and + buttons.
   * @param parent Parent component to draw opaque
   * @param actionMinus ActionCommand for minus button
   * @param actionPlus ActionCommand for plus button
   * @param iconName Icon name to show
   * @param text Text for label
   * @param toolTip Tool tip for label
   * @param listener Action Listener
   */
  public WorkerProductionPanel(final Component parent, final String actionMinus,
      final String actionPlus, final String iconName, final String text,
      final String toolTip, final ActionListener listener) {
    super(parent);
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    btnMinus = new IconButton(Icons.getIconByName(Icons.ICON_MINUS),
        Icons.getIconByName(Icons.ICON_MINUS_PRESSED), false, actionMinus,
        this);
    btnMinus.addActionListener(listener);
    btnMinus.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.add(btnMinus);
    label = new IconLabel(this, Icons.getIconByName(iconName), text);
    label.setToolTipText(toolTip);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(label);
    btnPlus = new IconButton(Icons.getIconByName(Icons.ICON_PLUS),
        Icons.getIconByName(Icons.ICON_PLUS_PRESSED), false, actionPlus, this);
    btnPlus.addActionListener(listener);
    btnPlus.setAlignmentX(Component.RIGHT_ALIGNMENT);
    this.add(btnPlus);
  }

  /**
   * Disable or enable buttons on worker production panel
   * @param interactive True to enable buttons
   */
  public void setInteractive(final boolean interactive) {
    btnMinus.setEnabled(interactive);
    btnPlus.setEnabled(interactive);
  }

  /**
   * Set label text
   * @param text to show
   */
  public void setText(final String text) {
    label.setText(text);
  }

  /**
   * Set Minus button tool tip
   * @param text Tool tip to show
   */
  public void setMinusToolTip(final String text) {
    btnMinus.setToolTipText(text);
  }

  /**
   * Set Plus button tool tip
   * @param text Tool tip to show
   */
  public void setPlusToolTip(final String text) {
    btnPlus.setToolTipText(text);
  }

}
