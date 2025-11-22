package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Richard Smit
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
import java.awt.Graphics;
import java.awt.FontMetrics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.gui.util.UIScale;

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
   this.setOpaque(false);
   Dimension size = this.getPreferredSize();
   size.width = GuiStatics.getTextWidth(GuiFonts.getFontCubellan(), text)
       + UIScale.scale(10);
   size.height = GuiStatics.getTextHeight(GuiFonts.getFontCubellan(), text)
       + UIScale.scale(4);
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

 @Override
 protected void paintComponent(final Graphics g) {
   // Non-opaque component - manually paint text without calling super
   // to avoid default JLabel rendering issues with transparent backgrounds

   // Early exit if no text to draw
   String text = this.getText();
   if (text == null || text.isEmpty()) {
     return;
   }

   int x = UIScale.scale(5);
   g.setFont(this.getFont());
   // Center text vertically using font metrics for proper baseline positioning
   FontMetrics fm = g.getFontMetrics();
   int y = (this.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
   g.setColor(this.getForeground());
   g.drawString(text, x, y);
 }

}
