package org.openRealmOfStars.gui.labels;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2026 Tuomo Untinen
 * Copyright (C) 2025 Richard Smit
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
import java.awt.Insets;
import java.awt.FontMetrics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import org.openRealmOfStars.gui.util.GuiFonts;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.gui.util.UIScale;
import org.openRealmOfStars.utilities.TextUtilities;

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

 /**
  * Quick and dirty way to convert HTML more understandable format.
  * NOTE: This is not full working conversion.
  * @return Converted string.
  */
 private String convertHtml() {
   String text = getText();
   if (text.startsWith("<html>") && text.endsWith("</html>")) {
     text = TextUtilities.removeLineChanges(text);
     text = text.replace("<html>", "");
     text = text.replace("</html>", "");
     text = text.replace("<br>", "\n");
     text = text.replace("<p>", "\n");
     text = text.replace("</p>", "\n");
   }
   return text;
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
   if (getText().startsWith("<html>") || getText().startsWith("<HTML>")) {
     int sx = 0;
     int sy = 0;
     int width = getWidth() - 30;
     int height = getHeight();
     if (this.getBorder() != null) {
       Insets inset = this.getBorder().getBorderInsets(this);
       sx = inset.left;
       sy = inset.top;
       width = getWidth() - inset.left - inset.right;
       height = getHeight() - inset.top - inset.bottom;
     }
     // These can be uncomment to see size of the label.
/*     g.setColor(Color.red);
     g.fillRect(sx, sx, width, height);
     g.setFont(this.getFont());*/
     text = convertHtml();
     int lastSpace = -1;
     int rowLen = 0;
     int maxRowLen = width / 6;
     int customCharWidth = GuiStatics.getTextWidth(getFont(),
         "EeAaRrIiOoTtSs");
     customCharWidth = customCharWidth / 14;
     if (customCharWidth > 0) {
       maxRowLen = width / customCharWidth;
     }
     StringBuilder sb = new StringBuilder(text);
     for (int i = 0; i < sb.length(); i++) {
       if (sb.charAt(i) == ' ') {
         lastSpace = i;
       }
       if (sb.charAt(i) == '\n') {
         lastSpace = -1;
         rowLen = 0;
       } else {
         rowLen++;
       }
       if (rowLen > maxRowLen) {
         if (lastSpace != -1) {
           sb.setCharAt(lastSpace, '\n');
           rowLen = i - lastSpace;
           lastSpace = -1;
         } else {
           sb.setCharAt(i, '\n');
           lastSpace = -1;
           rowLen = 0;
         }
       }
     }
     String[] texts = sb.toString().split("\n");
     int yTotal = 0;
     int yCur = 0;
     int lines = 0;
     int totalLines = 0;
     for (int i = 0; i < texts.length; i++) {
       int yHeight = GuiStatics.getTextHeight(getFont(), texts[i]);
       if (texts[i].contains("<ht>") && yCur > yTotal) {
         yTotal = yCur;
         totalLines = lines;
         yCur = 0;
         lines = 0;
       }
       yCur = yCur + yHeight;
       lines++;
     }
     if (yCur > yTotal) {
       yTotal = yCur;
       totalLines = lines;
     }
     if (totalLines == 0) {
       totalLines = 1;
     }
     int yAvg = yTotal / totalLines;
     /* Place text in middle of Y coordinates */
     //int yOffset = height / 2 - yTotal / 2;
     /* Place text on top of Y Coordinates */
     int yOffset = yAvg;
     if (yAvg * 2 > height) {
       yOffset = 0;
     }
     g.setColor(getForeground());
     int y = 0;
     x = UIScale.scale(5);
     int biggestY = 0;
     int longestLine = 0;
     for (int i = 0; i < texts.length; i++) {
       String tmpText = texts[i];
       int xOffset = 0;
       if (tmpText.contains("<ht>")) {
         tmpText = tmpText.replace("<ht>", "");
         y = 0;
         x = x + longestLine + 25;
       }
       if (tmpText.contains("<hr>")) {
         tmpText = tmpText.replace("<hr>", "");
         y = biggestY;
         x = UIScale.scale(5);
       }
       if (tmpText.contains("<li>")) {
         tmpText = tmpText.replace("<li>", "");
         int xWidth = GuiStatics.getTextWidth(getFont(), " ");
         xOffset = xWidth * 5;
         g.fillOval(x + xOffset - xWidth + sx + 2,
             sy + y * yAvg + 5 + yOffset, 8, 8);
       }
       int lineLength = GuiStatics.getTextWidth(getFont(), tmpText);
       if (lineLength > longestLine) {
         longestLine = lineLength;
       }
       g.drawString(tmpText, x + xOffset + sx + 2,
           sy + y * yAvg + yAvg + yOffset);
       y++;
       if (y > biggestY) {
         biggestY = y;
       }
     }
   } else {
     int y = (this.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
     g.setColor(this.getForeground());
     g.drawString(text, x, y);
   }
 }

}
