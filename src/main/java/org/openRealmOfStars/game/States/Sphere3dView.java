package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.gui.panels.BigSpherePanel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2022 Tuomo Untinen
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
* 3D Sphere view
*
*/
public class Sphere3dView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for Sphere 3D view
   * @param text Optional Title text, for showing planet with text
   */
  public Sphere3dView(final String text) {
    this.setLayout(new BorderLayout());

    BigSpherePanel imgBase = new BigSpherePanel(true, text);
    InvisiblePanel invisible = new InvisiblePanel(imgBase);
    invisible.setLayout(new BoxLayout(invisible, BoxLayout.Y_AXIS));
    invisible.add(Box.createRigidArea(new Dimension(500, 250)));

    this.add(imgBase, BorderLayout.CENTER);

  }
}
