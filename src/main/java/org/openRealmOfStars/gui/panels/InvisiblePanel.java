package org.openRealmOfStars.gui.panels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
 * Copyright (C) 2016-2017 Tuomo Untinen
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
 */

import java.awt.Component;

import javax.swing.JPanel;

/**
 *
 * A transparent panel that allows the parent's background to show through.
 * Used as a container for UI elements that need to be overlaid on custom
 * painted backgrounds like BigImagePanel.
 *
 */
public class InvisiblePanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new invisible panel.
   * @param parent Parent component (kept for API compatibility)
   */
  @SuppressWarnings("PMD.UnusedFormalParameter")
  public InvisiblePanel(final Component parent) {
    // Mark as non-opaque so Swing handles parent repainting automatically
    setOpaque(false);
  }

}
