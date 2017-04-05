package org.openRealmOfStars.gui.panels;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

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
 * Handling invisible panel. Forces redrawing parent component
 *
 */
public class InvisiblePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Parent component
   */
  private Component parent;

  /**
   * Create a new invisible panel
   * @param parent Parent component
   */
  public InvisiblePanel(final Component parent) {
    this.parent = parent;
  }

  @Override
  public void paintComponent(final Graphics g) {
    parent.repaint();
    // Invisible panel does not paint anything
  }

}
