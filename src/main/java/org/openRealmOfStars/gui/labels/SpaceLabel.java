package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;

/**
*
* Handling label which draws text which is on label.
*
*/
public class SpaceLabel extends JLabel {

  /**
  *
  */
 private static final long serialVersionUID = 1L;

 /**
  * Create label with transparency without borders or auto wrap.
  * @param text Text to show
  */
 public SpaceLabel(final String text) {
   super(text);
   this.setForeground(GuiStatics.getCoolSpaceColor());
   this.setFont(GuiFonts.getFontCubellan());
   Dimension size = this.getPreferredSize();
   size.width = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), text)
       + 10;
   size.height = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(), text)
       + 4;
   this.setMinimumSize(size);
   this.setPreferredSize(size);
   this.setMaximumSize(size);
 }

 @Override
 public JToolTip createToolTip() {
   JToolTip toolTip = super.createToolTip();
   toolTip.setForeground(GuiStatics.getCoolSpaceColor());
   toolTip.setBackground(GuiStatics.getCoolSpaceColorDark());
   toolTip.setBorder(BorderFactory
       .createLineBorder(GuiStatics.getCoolSpaceColorDarker()));
   return toolTip;
 }


}
