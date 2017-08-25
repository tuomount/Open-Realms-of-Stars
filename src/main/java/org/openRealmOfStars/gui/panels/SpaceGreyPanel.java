package org.openRealmOfStars.gui.panels;

import javax.swing.JPanel;

import org.openRealmOfStars.gui.GuiStatics;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* JPanel with new default background color, space grey
*
*/
public class SpaceGreyPanel extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for SpaceGreyPanel
   */
  public SpaceGreyPanel() {
    setBackground(GuiStatics.COLOR_SPACE_GREY_BLUE);
  }
}
